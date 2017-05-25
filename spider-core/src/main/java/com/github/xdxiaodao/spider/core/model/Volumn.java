package com.github.xdxiaodao.spider.core.model;

import java.util.List;

/**
 * Created by xiaohuilang on 17/5/13.
 */
public class Volumn {

    private Long id;
    private String name;
    private Integer chapterNum;
    private List<Chapter> chapterList;

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

    public Integer getChapterNum() {
        return chapterNum;
    }

    public void setChapterNum(Integer chapterNum) {
        this.chapterNum = chapterNum;
    }

    public List<Chapter> getChapterList() {
        return chapterList;
    }

    public void setChapterList(List<Chapter> chapterList) {
        this.chapterList = chapterList;
    }
}
