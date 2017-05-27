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
    private String chapterUrl;

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

}
