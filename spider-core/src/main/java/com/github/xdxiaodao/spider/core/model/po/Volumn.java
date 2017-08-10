package com.github.xdxiaodao.spider.core.model.po;

import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.*;

@Table(name = "tbl_volumn")
public class Volumn {
    @Id
    @GeneratedValue(generator="JDBC")
    private Long id;

    private String name;

    private String url;

    /**
     * 书籍ID
     */
    private Long bookId;

    /**
     * 卷编号
     */
    private Integer volumnIndex;

    /**
     * 章节数
     */
    private Integer chapterNum;

    private Timestamp createTime;

    private Timestamp updateTime;

    /**
     * 状态，1：正常；0：删除
     */
    private Integer status;

    /**
     * 卷描述
     */
    private String volumnDesc;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 获取书籍ID
     *
     * @return bookId - 书籍ID
     */
    public Long getBookId() {
        return bookId;
    }

    /**
     * 设置书籍ID
     *
     * @param bookId 书籍ID
     */
    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    /**
     * 获取卷编号
     *
     * @return volumnIndex - 卷编号
     */
    public Integer getVolumnIndex() {
        return volumnIndex;
    }

    /**
     * 设置卷编号
     *
     * @param volumnIndex 卷编号
     */
    public void setVolumnIndex(Integer volumnIndex) {
        this.volumnIndex = volumnIndex;
    }

    /**
     * 获取章节数
     *
     * @return chapterNum - 章节数
     */
    public Integer getChapterNum() {
        return chapterNum;
    }

    /**
     * 设置章节数
     *
     * @param chapterNum 章节数
     */
    public void setChapterNum(Integer chapterNum) {
        this.chapterNum = chapterNum;
    }

    /**
     * @return createTime
     */
    public Timestamp getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    /**
     * @return updateTime
     */
    public Timestamp getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     */
    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取状态，1：正常；0：删除
     *
     * @return status - 状态，1：正常；0：删除
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态，1：正常；0：删除
     *
     * @param status 状态，1：正常；0：删除
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取卷描述
     *
     * @return volumnDesc - 卷描述
     */
    public String getVolumnDesc() {
        return volumnDesc;
    }

    /**
     * 设置卷描述
     *
     * @param volumnDesc 卷描述
     */
    public void setVolumnDesc(String volumnDesc) {
        this.volumnDesc = volumnDesc;
    }
}