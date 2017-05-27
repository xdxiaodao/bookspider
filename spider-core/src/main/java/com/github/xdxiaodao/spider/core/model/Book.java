package com.github.xdxiaodao.spider.core.model;

import com.github.xdxiaodao.spider.core.base.model.SpiderNode;
import com.github.xdxiaodao.spider.core.common.enums.NodeType;
import com.github.xdxiaodao.spider.core.common.enums.ParseProcessType;
import com.google.common.collect.Maps;

import java.sql.Timestamp;
import java.util.Map;

/**
 * Created with bookspider
 * User zhangmz
 * Date 2017/5/13
 * Time 12:36
 * Desc
 */
public class Book extends SpiderNode {

    private String coverImgUrl;
    private String bookUrl; // 原始网站书籍url
    private Integer isUpdate; // 是否已更新完结，0：未更新完结，1：已完结，2：暂停更新

    public final Integer UPDATE_DOING = 0;
    public final Integer UPDATE_CLOSED = 1;
    public final Integer UPDATE_PUASE = 2;

    private Book() {
        this.setNodeType(NodeType.BOOK);
        this.setThreadNum(1);
        this.setParseProcess(ParseProcessType.WAITING);
        this.setCreateTime(new Timestamp(System.currentTimeMillis()));
    }

    public static Book me() {
        return new Book();
    }

    public Integer getIsUpdate() {
        return isUpdate;
    }

    public void setIsUpdate(Integer isUpdate) {
        this.isUpdate = isUpdate;
    }
}
