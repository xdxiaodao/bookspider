package com.github.xdxiaodao.spider.core.model;

/**
 * Created by xiaohuilang on 17/5/13.
 */
public class Chapter {
    private Long id;
    private String name;
    private String content;
    private Integer contentSize;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getContentSize() {
        return contentSize;
    }

    public void setContentSize(Integer contentSize) {
        this.contentSize = contentSize;
    }
}
