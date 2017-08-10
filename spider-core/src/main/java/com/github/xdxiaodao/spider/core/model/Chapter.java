package com.github.xdxiaodao.spider.core.model;

import com.github.xdxiaodao.spider.core.base.model.SpiderNode;
import com.github.xdxiaodao.spider.core.common.enums.NodeType;
import com.github.xdxiaodao.spider.core.common.enums.ParseProcessType;

import java.sql.Timestamp;

/**
 * Created with bookspider
 * User zhangmz
 * Date 2017/5/13
 * Time 12:36
 * Desc
 */
public class Chapter extends SpiderNode {
    private Long index;
    private String content;
    private Integer contentSize;
    private Integer bookChapterIndex; // 书籍章节索引
    private Integer volumnChapterIndex; // 卷章节索引
    private Long bookId; // 书籍ID
    private Long volumnId; // 卷ID

    private Chapter() {
        this.setNodeType(NodeType.CHAPTER);
        this.setThreadNum(10);
        this.setParseProcess(ParseProcessType.WAITING);
        this.setCreateTime(new Timestamp(System.currentTimeMillis()));
    }

    public static Chapter me() {
        return new Chapter();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getIndex() {
        return index;
    }

    public void setIndex(Long index) {
        this.index = index;
    }

    public Integer getContentSize() {
        return contentSize;
    }

    public void setContentSize(Integer contentSize) {
        this.contentSize = contentSize;
    }

    public Integer getBookChapterIndex() {
        return bookChapterIndex;
    }

    public void setBookChapterIndex(Integer bookChapterIndex) {
        this.bookChapterIndex = bookChapterIndex;
    }

    public Integer getVolumnChapterIndex() {
        return volumnChapterIndex;
    }

    public void setVolumnChapterIndex(Integer volumnChapterIndex) {
        this.volumnChapterIndex = volumnChapterIndex;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Long getVolumnId() {
        return volumnId;
    }

    public void setVolumnId(Long volumnId) {
        this.volumnId = volumnId;
    }
}
