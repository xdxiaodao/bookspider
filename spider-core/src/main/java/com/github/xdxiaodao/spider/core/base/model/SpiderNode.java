package com.github.xdxiaodao.spider.core.base.model;

import com.github.xdxiaodao.spider.core.common.enums.ParseProcessType;
import com.github.xdxiaodao.spider.core.common.interfaces.BookPageProcessor;
import com.google.common.collect.Sets;

import java.sql.Timestamp;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created with bookspider
 * User zhangmz
 * Date 2017/5/27
 * Time 10:53
 * Desc
 */
public abstract class SpiderNode extends Node implements Comparable<SpiderNode>{
    private BookPageProcessor bookPageProcessor;
    private Integer threadNum;
    private ParseProcessType parseProcess;
    private Set<String> parseChildDoneSet = Sets.newHashSet();
    private AtomicInteger count = new AtomicInteger(0);
    private Timestamp createTime;
    private Timestamp parseStartTime;

    public SpiderNode bookPageProcessor(BookPageProcessor bookPageProcessor) {
        this.bookPageProcessor = bookPageProcessor;
        return this;
    }

    public SpiderNode threadNum(Integer threadNum) {
        this.threadNum = threadNum;
        return this;
    }

    public void addParseDoneChild(String childNodeName) {
        this.getParseChildDoneSet().add(childNodeName);
    }

    public BookPageProcessor getBookPageProcessor() {
        return bookPageProcessor;
    }

    public void setBookPageProcessor(BookPageProcessor bookPageProcessor) {
        this.bookPageProcessor = bookPageProcessor;
    }

    public Integer getThreadNum() {
        return threadNum;
    }

    public void setThreadNum(Integer threadNum) {
        this.threadNum = threadNum;
    }

    public ParseProcessType getParseProcess() {
        return parseProcess;
    }

    public void setParseProcess(ParseProcessType parseProcess) {
        this.parseProcess = parseProcess;
    }

    public Set<String> getParseChildDoneSet() {
        return parseChildDoneSet;
    }

    public void setParseChildDoneSet(Set<String> parseChildDoneSet) {
        this.parseChildDoneSet = parseChildDoneSet;
    }

    public boolean isParseDone() {
        return ParseProcessType.DONE.equals(parseProcess);
    }

    public AtomicInteger getCount() {
        return count;
    }

    public void setCount(AtomicInteger count) {
        this.count = count;
    }

    public void addCount() {
        this.count.addAndGet(1);
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getParseStartTime() {
        return parseStartTime;
    }

    public void setParseStartTime(Timestamp parseStartTime) {
        this.parseStartTime = parseStartTime;
    }

    public boolean isParseOverTime() {
        if (null == parseStartTime) {
            return false;
        }

        long diff = System.currentTimeMillis() - this.parseStartTime.getTime();
        if (diff / 1000 > 5) {
            return true;
        }

        return false;
    }

    /**
     * compare接口
     * @param o
     * @return
     */
    @Override
    public int compareTo(SpiderNode o) {
        if (this.count.equals(o.getCount())) {
            if (!o.getNodeType().equals(this.getNodeType())) {
                return o.getNodeType().compareTo(this.getNodeType());
            }
            return o.getCreateTime().compareTo(this.getCreateTime());
        }
        return o.count.get() - this.getCount().get();
    }
}
