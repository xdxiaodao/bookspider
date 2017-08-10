package com.github.xdxiaodao.spider.core.model.po;

import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.*;

@Table(name = "tbl_chapter")
public class Chapter {
    @Id
    @GeneratedValue(generator="JDBC")
    private Long id;

    private String name;

    private String url;

    /**
     * 书籍章节索引
     */
    private Integer bookChapterIndex;

    /**
     * 卷章节索引
     */
    private Integer volumnChapterIndex;

    /**
     * 书籍ID
     */
    private Long bookId;

    /**
     * 卷ID
     */
    private Long volumnId;

    /**
     * 章节长度
     */
    private Integer contentSize;

    private Timestamp createTime;

    private Timestamp updateTime;

    /**
     * 状态，1：正常；0：删除
     */
    private Integer status;

    /**
     * 章节内容
     */
    private String content;

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
     * 获取书籍章节索引
     *
     * @return bookChapterIndex - 书籍章节索引
     */
    public Integer getBookChapterIndex() {
        return bookChapterIndex;
    }

    /**
     * 设置书籍章节索引
     *
     * @param bookChapterIndex 书籍章节索引
     */
    public void setBookChapterIndex(Integer bookChapterIndex) {
        this.bookChapterIndex = bookChapterIndex;
    }

    /**
     * 获取卷章节索引
     *
     * @return volumnChapterIndex - 卷章节索引
     */
    public Integer getVolumnChapterIndex() {
        return volumnChapterIndex;
    }

    /**
     * 设置卷章节索引
     *
     * @param volumnChapterIndex 卷章节索引
     */
    public void setVolumnChapterIndex(Integer volumnChapterIndex) {
        this.volumnChapterIndex = volumnChapterIndex;
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
     * 获取卷ID
     *
     * @return volumnId - 卷ID
     */
    public Long getVolumnId() {
        return volumnId;
    }

    /**
     * 设置卷ID
     *
     * @param volumnId 卷ID
     */
    public void setVolumnId(Long volumnId) {
        this.volumnId = volumnId;
    }

    /**
     * 获取章节长度
     *
     * @return contentSize - 章节长度
     */
    public Integer getContentSize() {
        return contentSize;
    }

    /**
     * 设置章节长度
     *
     * @param contentSize 章节长度
     */
    public void setContentSize(Integer contentSize) {
        this.contentSize = contentSize;
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
     * 获取章节内容
     *
     * @return content - 章节内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置章节内容
     *
     * @param content 章节内容
     */
    public void setContent(String content) {
        this.content = content;
    }
}