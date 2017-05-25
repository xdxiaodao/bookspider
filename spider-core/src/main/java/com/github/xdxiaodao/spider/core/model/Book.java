package com.github.xdxiaodao.spider.core.model;

import java.util.List;

/**
 * Created by xiaohuilang on 17/5/13.
 */
public class Book {

    private Long id;
    private String name;
    private String category;
    private Integer volumnNum;
    private String coverImgUrl;
    private List<Volumn> volumnList; // 卷列表
    private String oriWebUrl; // 原始网站URL
    private String oriWebBookUrl; // 原始网站书籍url
    private Integer isUpdate; // 是否已更新完结，0：未更新完结，1：已完结，2：暂停更新

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getVolumnNum() {
        return volumnNum;
    }

    public void setVolumnNum(Integer volumnNum) {
        this.volumnNum = volumnNum;
    }

    public String getCoverImgUrl() {
        return coverImgUrl;
    }

    public void setCoverImgUrl(String coverImgUrl) {
        this.coverImgUrl = coverImgUrl;
    }

    public List<Volumn> getVolumnList() {
        return volumnList;
    }

    public void setVolumnList(List<Volumn> volumnList) {
        this.volumnList = volumnList;
    }

    public String getOriWebUrl() {
        return oriWebUrl;
    }

    public void setOriWebUrl(String oriWebUrl) {
        this.oriWebUrl = oriWebUrl;
    }

    public String getOriWebBookUrl() {
        return oriWebBookUrl;
    }

    public void setOriWebBookUrl(String oriWebBookUrl) {
        this.oriWebBookUrl = oriWebBookUrl;
    }

    public Integer getIsUpdate() {
        return isUpdate;
    }

    public void setIsUpdate(Integer isUpdate) {
        this.isUpdate = isUpdate;
    }
}
