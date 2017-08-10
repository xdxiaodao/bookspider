package com.github.xdxiaodao.spider.core.model.po;

import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.*;

@Table(name = "tbl_book")
public class Book {
    @Id
    @GeneratedValue(generator="JDBC")
    private Long id;

    private String name;

    private String url;

    /**
     * 封面图
     */
    private String coverImg;

    /**
     * 是否已更新完结，0：未更新完结，1：已完结，2：暂停更新
     */
    private Integer isUpdate;

    /**
     * 作者
     */
    private String author;

    /**
     * 类目
     */
    private String category;

    /**
     * 章节数
     */
    private Integer chapterNum;

    /**
     * 卷数
     */
    private Integer volumnNum;

    private Timestamp createTime;

    private Timestamp updateTime;

    /**
     * 状态，1：正常;0:删除
     */
    private Integer status;

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
     * 获取封面图
     *
     * @return coverImg - 封面图
     */
    public String getCoverImg() {
        return coverImg;
    }

    /**
     * 设置封面图
     *
     * @param coverImg 封面图
     */
    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    /**
     * 获取是否已更新完结，0：未更新完结，1：已完结，2：暂停更新
     *
     * @return isUpdate - 是否已更新完结，0：未更新完结，1：已完结，2：暂停更新
     */
    public Integer getIsUpdate() {
        return isUpdate;
    }

    /**
     * 设置是否已更新完结，0：未更新完结，1：已完结，2：暂停更新
     *
     * @param isUpdate 是否已更新完结，0：未更新完结，1：已完结，2：暂停更新
     */
    public void setIsUpdate(Integer isUpdate) {
        this.isUpdate = isUpdate;
    }

    /**
     * 获取作者
     *
     * @return author - 作者
     */
    public String getAuthor() {
        return author;
    }

    /**
     * 设置作者
     *
     * @param author 作者
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * 获取类目
     *
     * @return category - 类目
     */
    public String getCategory() {
        return category;
    }

    /**
     * 设置类目
     *
     * @param category 类目
     */
    public void setCategory(String category) {
        this.category = category;
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
     * 获取卷数
     *
     * @return volumnNum - 卷数
     */
    public Integer getVolumnNum() {
        return volumnNum;
    }

    /**
     * 设置卷数
     *
     * @param volumnNum 卷数
     */
    public void setVolumnNum(Integer volumnNum) {
        this.volumnNum = volumnNum;
    }

    /**
     * @return createTime
     */
    public Date getCreateTime() {
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
     * 获取状态，1：正常;0:删除
     *
     * @return status - 状态，1：正常;0:删除
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态，1：正常;0:删除
     *
     * @param status 状态，1：正常;0:删除
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
}