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
public class Volumn extends SpiderNode {

    private Integer chapterNum;

    private Volumn() {
        this.setNodeType(NodeType.VOLUMN);
        this.setThreadNum(1);
        this.setParseProcess(ParseProcessType.WAITING);
        this.setCreateTime(new Timestamp(System.currentTimeMillis()));
    }

    public static Volumn me() {
        return new Volumn();
    }

}
