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

    private String coverImg;
    private Integer isUpdate; // 是否已更新完结，0：未更新完结，1：已完结，2：暂停更新
    private String author; // 作者
    private String category; // 类目
    private Integer chapterNum; // 章节数
    private Integer volumnNum; // 卷数
    private String submitTime; // 发表时间
    private String description; // 描述

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

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getChapterNum() {
        return chapterNum;
    }

    public void setChapterNum(Integer chapterNum) {
        this.chapterNum = chapterNum;
    }

    public Integer getVolumnNum() {
        return volumnNum;
    }

    public void setVolumnNum(Integer volumnNum) {
        this.volumnNum = volumnNum;
    }

    public String getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(String submitTime) {
        this.submitTime = submitTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
