package com.github.xdxiaodao.spider.core.service.store;

import com.github.xdxiaodao.spider.core.base.model.SpiderNode;

/**
 * Created with bookspider
 * User zhangmuzhao
 * Date 2017/8/8
 * Time 18:38
 * Desc
 */
public interface SpiderStore {

    /**
     * 存储节点信息
     * @param spiderNode 采集节点
     * @return 存储是否成功
     */
    public boolean store(SpiderNode spiderNode);
}
