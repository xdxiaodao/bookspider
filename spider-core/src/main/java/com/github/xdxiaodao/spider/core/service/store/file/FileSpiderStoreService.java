package com.github.xdxiaodao.spider.core.service.store.file;

import com.github.xdxiaodao.spider.core.base.model.Node;
import com.github.xdxiaodao.spider.core.base.model.SpiderNode;
import com.github.xdxiaodao.spider.core.model.Book;
import com.github.xdxiaodao.spider.core.model.Chapter;
import com.github.xdxiaodao.spider.core.service.store.SpiderStore;
import com.github.xdxiaodao.spider.core.util.PropertiesUtil;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileWriter;
import java.util.*;

/**
 * Created with bookspider
 * User zhangmuzhao
 * Date 2017/8/8
 * Time 18:41
 * Desc
 */
@Service("fileSpiderStoreService")
public class FileSpiderStoreService implements SpiderStore{
    private static Logger logger = LoggerFactory.getLogger(FileSpiderStoreService.class);

    private String filePath;

    @PostConstruct
    public void init() {
        filePath = PropertiesUtil.getProperties("spider.store.strategy.file.path");
    }


    /**
     * 存储节点信息
     *
     * @param spiderNode 采集节点
     * @return 存储是否成功
     */
    @Override
    public boolean store(SpiderNode spiderNode) {
        if (!(spiderNode instanceof Book)) {
            return false;
        }
        Map<String, Node> childNodeMap = spiderNode.getChildNodeMap();
        if (MapUtils.isEmpty(childNodeMap)) {
            return false;
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
        String bookDir = filePath + spiderNode.getParent().getName() + "/" + spiderNode.getName();
//            String bookPath = bookDir + "\\" + spiderNode.getName();
        File file = new File(bookDir);
        if (!file.exists()) {
            file.mkdirs();
        }

        int count = 1;
        for (Node node : nodeList) {
            if (!(node instanceof Chapter)) {
                continue;
            }
            FileWriter fileWriter = null;
            try {
                String bookPath = bookDir + "/" + String.format("%04d", count) + ".txt";
                File bookFile = new File(bookPath);
                if (bookFile.exists()) {
                    continue;
                }
                fileWriter = new FileWriter(bookPath, false);
                IOUtils.write(((Chapter) node).getContent(), fileWriter);
                count++;
            } catch (Exception e) {
                logger.error("写数据失败,数据内容：{}", ((Chapter) node).getContent());
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
        return true;
    }
}
