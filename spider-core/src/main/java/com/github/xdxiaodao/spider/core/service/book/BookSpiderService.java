package com.github.xdxiaodao.spider.core.service.book;

import com.alibaba.fastjson.JSON;
import com.github.xdxiaodao.spider.core.base.model.Node;
import com.github.xdxiaodao.spider.core.base.model.SpiderNode;
import com.github.xdxiaodao.spider.core.base.service.platform.BaseThreadFactory;
import com.github.xdxiaodao.spider.core.common.enums.ParseProcessType;
import com.github.xdxiaodao.spider.core.common.interfaces.BookPageProcessor;
import com.github.xdxiaodao.spider.core.common.interfaces.ISpider;
import com.github.xdxiaodao.spider.core.model.Book;
import com.github.xdxiaodao.spider.core.model.BookCategory;
import com.github.xdxiaodao.spider.core.model.Chapter;
import com.github.xdxiaodao.spider.core.model.Volumn;
import com.github.xdxiaodao.spider.core.service.quanxiaoshuo.QuanxiaoshuoMainPageProcessor;
import com.github.xdxiaodao.spider.core.service.quanxiaoshuo.QuanxiaoshuoMenuPageProcessor;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Spider;

import java.io.File;
import java.io.FileWriter;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.*;

/**
 * Created with bookspider
 * User zhangmz
 * Date 2017/5/23
 * Time 12:36
 * Desc
 */
@Service
public class BookSpiderService implements ISpider, InitializingBean{
    private static Logger logger = LoggerFactory.getLogger(BookSpiderService.class);

    private PriorityBlockingQueue<Node> nodeQueue = new PriorityBlockingQueue<Node>();
    private Map<String, Node> nodeMap = Maps.newConcurrentMap();

    // 待采集的book节点
    private PriorityBlockingQueue<SpiderNode> bookQueue = new PriorityBlockingQueue<SpiderNode>();

    private ExecutorService bookSpiderExecutor = Executors.newFixedThreadPool(30, new BaseThreadFactory("bookSpider-"));
    private ExecutorService bookCollectExecutor = Executors.newFixedThreadPool(5, new BaseThreadFactory("bookPipline-"));

    private static String ROOT_DIR_NAME = "D:\\personal\\data\\book\\quanxiaoshuo\\";

    @Autowired
    private QuanxiaoshuoMainPageProcessor quanxiaoshuoMainPageProcessor;

    public void start() {
        startQuanxiaoshuo();
    }

    private void startQuanxiaoshuo() {
        quanxiaoshuoMainPageProcessor.register(this, null);
        Spider.create(quanxiaoshuoMainPageProcessor).addUrl("http://quanxiaoshuo.com").thread(1).run();
    }

    private class BookSpiderWorker implements Runnable{

        /**
         * spiderworker
         */
        @Override
        public void run() {
            // 启动后一直运行
            while (true) {
                try {
                    Node node = nodeQueue.take();
                    logger.info("BookSpiderWorkder消费node：{}, currentSize:{}", JSON.toJSONString(node.getName()), nodeQueue.size());
                    if (null == node || StringUtils.isBlank(node.getUrl())) {
                        Thread.sleep(3000);
                        continue;
                    }

                    // 获取到内容开始执行爬取
                    SpiderNode spiderNode = (SpiderNode) node;
                    if (ParseProcessType.WAITING.equals(spiderNode.getParseProcess())
                            || (ParseProcessType.DOING.equals(spiderNode.getParseProcess()) && spiderNode.isParseOverTime())) {
                        parseNode(spiderNode);
                    }
                } catch (Exception e) {
                    logger.error("书籍爬虫运行失败", e);
                }
            }
        }
    }

    public class BookCollector implements Runnable {

        /**
         * 执行提
         */
        @Override
        public void run() {
            while (true) {
                try {
                    Node node = bookQueue.take();
                    logger.info("BookCollector消费node：{}, currentSize:{}", JSON.toJSONString(node.getName()), bookQueue.size());
                    if (null == node || StringUtils.isBlank(node.getUrl())) {
                        Thread.sleep(3000);
                        continue;
                    }

                    SpiderNode spiderNode = (SpiderNode) node;
                    spiderNode.addCount();

                    // 如果子节点未解析完成，则不进行书籍收集
                    if (spiderNode.getParseChildDoneSet().size() < spiderNode.getChildNodeMap().size()) {
                        Set<String> allChildNodeSet = spiderNode.getChildNodeMap().keySet();
                        Set<String> unParseChildNodeSet = Sets.newHashSet();
                        unParseChildNodeSet.addAll(allChildNodeSet);
                        unParseChildNodeSet.removeAll(spiderNode.getParseChildDoneSet());

                        for (String unParseChildNode : unParseChildNodeSet) {
                            SpiderNode unParseNode = (SpiderNode) spiderNode.getChildNode(unParseChildNode);
                            if (null == unParseChildNode) {
                                continue;
                            }

                            if (ParseProcessType.WAITING.equals(unParseNode.getParseProcess())
                                    || (ParseProcessType.DOING.equals(unParseNode.getParseProcess()) && unParseNode.isParseOverTime())) {
                                parseNode(unParseNode);
                            }
                        }
                        bookQueue.put(spiderNode);
                    } else {
                        doBookCollect(spiderNode);
                    }
                } catch (Exception e) {
                    logger.error("书籍收集失败", e);
                }
            }
        }

        private void doBookCollect(SpiderNode spiderNode) {
            Map<String, Node> childNodeMap = spiderNode.getChildNodeMap();
            if (MapUtils.isEmpty(childNodeMap)) {
                return;
            }

            List<Node> nodeList = new ArrayList<Node>(childNodeMap.values());
            Collections.sort(nodeList, new Comparator<Node>() {
                @Override
                public int compare(Node o1, Node o2) {
                    if (!(o1 instanceof Chapter) || !(o2 instanceof Chapter)) {
                        return 0;
                    }

                    return ((Chapter) o1).getIndex().compareTo(((Chapter) o2).getIndex());
                }
            });

            // 拼装书籍路径
            String bookDir = ROOT_DIR_NAME + spiderNode.getParent().getName();
            String bookPath = bookDir + "\\" + spiderNode.getName();
            File file = new File(bookDir);
            if (!file.exists()) {
                file.mkdirs();
            }
            FileWriter fileWriter = null;
            try {
                fileWriter = new FileWriter(bookPath, true);
                for (Node node : nodeList) {
                    if (!(node instanceof Chapter)) {
                        continue;
                    }

                    IOUtils.write(((Chapter) node).getContent(), fileWriter);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (null != fileWriter) {
                        fileWriter.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void parseNode(SpiderNode unParseNode) {
        unParseNode.setParseStartTime(new Timestamp(System.currentTimeMillis()));
        String url = unParseNode.getUrl();
        BookPageProcessor bookPageProcessor = unParseNode.getBookPageProcessor();
        bookPageProcessor.register(this, unParseNode);
        Spider.create(bookPageProcessor).addUrl(url).thread((unParseNode).getThreadNum()).run();
    }


    public boolean newNode(Node node) {
        if (null == node || StringUtils.isBlank(node.getName())) {
            return false;
        }

        Node findNode = node;
        Node parentNode = findNode.getParent();
        if (null != parentNode && null == parentNode.getChildNode(findNode.getName())) {
            parentNode.addChildNode(findNode);
        }

        try {
            nodeQueue.put(node);
            nodeMap.put(node.getName(), node);
//            logger.info("添加新节点到nodeQueue: node:{}", JSON.toJSONString(node.getName()));
        } catch (Exception e) {
            logger.info("添加新节点到nodeQueue，类目信息：{}", JSON.toJSONString(node.getName()), e);
        }

        if (parentNode instanceof Book && ((Book) parentNode).isParseDone()) {
            try {
                bookQueue.put((SpiderNode)parentNode);
//                logger.info("添加新节点到bookQueue，node:{}", JSON.toJSONString(parentNode.getName()));
            } catch (Exception e) {
                logger.error("添加新节点到bookQueue：书籍信息：{}", JSON.toJSONString(parentNode.getName()));
            }
        }

        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        for (int i = 0; i < 1; i++) {
            bookSpiderExecutor.submit(new BookSpiderWorker());
        }

        for (int i = 0; i < 1; i++) {
            bookCollectExecutor.submit(new BookCollector());
        }
    }
}
