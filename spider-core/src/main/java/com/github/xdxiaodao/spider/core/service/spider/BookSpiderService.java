package com.github.xdxiaodao.spider.core.service.spider;

import com.alibaba.fastjson.JSON;
import com.github.xdxiaodao.spider.core.base.model.Node;
import com.github.xdxiaodao.spider.core.base.model.SpiderNode;
import com.github.xdxiaodao.spider.core.base.service.platform.BaseThreadFactory;
import com.github.xdxiaodao.spider.core.base.service.webmagic.SafelyHttpDownloader;
import com.github.xdxiaodao.spider.core.common.enums.ParseProcessType;
import com.github.xdxiaodao.spider.core.common.enums.StoreType;
import com.github.xdxiaodao.spider.core.common.interfaces.BookPageProcessor;
import com.github.xdxiaodao.spider.core.common.interfaces.ISpider;
import com.github.xdxiaodao.spider.core.model.Book;
import com.github.xdxiaodao.spider.core.model.BookCategory;
import com.github.xdxiaodao.spider.core.model.Chapter;
import com.github.xdxiaodao.spider.core.service.parser.kanunu.KanunuBookPageProcessor;
import com.github.xdxiaodao.spider.core.service.parser.quanxiaoshuo.QuanxiaoshuoMainPageProcessor;
import com.github.xdxiaodao.spider.core.service.store.SpiderStore;
import com.github.xdxiaodao.spider.core.service.store.SpiderStoreFactory;
import com.github.xdxiaodao.spider.core.util.SpringBeanUtil;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
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

    @Autowired
    private QuanxiaoshuoMainPageProcessor quanxiaoshuoMainPageProcessor;

    @Autowired
    private SafelyHttpDownloader httpDownloader;

    public void start() {
        startQuanxiaoshuo();
    }

    private void startQuanxiaoshuo() {
        BookCategory bookCategory = BookCategory.me();
        bookCategory.setName("武侠");
        Book book = Book.me();
        book.setName("清微驭邪录");
        book.setParent(bookCategory);
        book.setUrl("http://www.kanunu8.com/book3/5956/index.html");
        KanunuBookPageProcessor kanunuBookPageProcessor = SpringBeanUtil.getBean(KanunuBookPageProcessor.class);
        kanunuBookPageProcessor.register(this, book);
        Spider.create(kanunuBookPageProcessor).addUrl("http://www.kanunu8.com/book3/5956/index.html").thread(1).run();
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
                        SpiderStore spiderStore = SpiderStoreFactory.getSpiderStore();
                        if (null != spiderStore) {
                            spiderStore.store(spiderNode);
                        }
                    }
                } catch (Exception e) {
                    logger.error("书籍收集失败", e);
                }
            }
        }
    }

    private void parseNode(SpiderNode unParseNode) {
        unParseNode.setParseStartTime(new Timestamp(System.currentTimeMillis()));
        String url = unParseNode.getUrl();
        BookPageProcessor bookPageProcessor = unParseNode.getBookPageProcessor();
        bookPageProcessor.register(this, unParseNode);
        Spider.create(bookPageProcessor).addUrl(url).setDownloader(httpDownloader).thread((unParseNode).getThreadNum()).run();
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

        for (int i = 0; i < 5; i++) {
            bookSpiderExecutor.submit(new BookSpiderWorker());
        }

        for (int i = 0; i < 1; i++) {
            bookCollectExecutor.submit(new BookCollector());
        }
    }
}
