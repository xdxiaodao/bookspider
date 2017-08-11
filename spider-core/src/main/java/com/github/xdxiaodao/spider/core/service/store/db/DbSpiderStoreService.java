package com.github.xdxiaodao.spider.core.service.store.db;

import com.github.xdxiaodao.spider.core.base.model.Node;
import com.github.xdxiaodao.spider.core.base.model.SpiderNode;
import com.github.xdxiaodao.spider.core.model.Book;
import com.github.xdxiaodao.spider.core.model.Chapter;
import com.github.xdxiaodao.spider.core.service.biz.BookService;
import com.github.xdxiaodao.spider.core.service.biz.ChapterService;
import com.github.xdxiaodao.spider.core.service.store.SpiderStore;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created with bookspider
 * User zhangmuzhao
 * Date 2017/8/8
 * Time 18:39
 * Desc
 */
@Service("dbSpiderStoreService")
public class DbSpiderStoreService implements SpiderStore{
    private static Logger logger = LoggerFactory.getLogger(DbSpiderStoreService.class);

    @Autowired
    private BookService bookService;
    @Autowired
    private ChapterService chapterService;


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

        try {
            Long bookId = bookService.addBook((Book) spiderNode);
            for (Node node : nodeList) {
                if (node instanceof Chapter) {
                    Chapter chapter = (Chapter) node;
                    chapter.setBookId(bookId);
                    chapterService.addChapter(chapter);
                }
            }
        } catch (Exception e) {
            logger.error("书籍信息入库失败", e);
            return false;
        }

        return true;
    }
}
