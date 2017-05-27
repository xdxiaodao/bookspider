package com.github.xdxiaodao.spider.core.base.service;

import com.github.xdxiaodao.spider.core.base.model.Node;
import com.github.xdxiaodao.spider.core.common.interfaces.BookPageProcessor;
import com.github.xdxiaodao.spider.core.common.interfaces.ISpider;

/**
 * Created with bookspider
 * User zhangmz
 * Date 2017/5/26
 * Time 18:25
 * Desc
 */
public abstract class BaseBookProcessor implements BookPageProcessor {

    protected ISpider spider;
    protected Node parentNode;

    @Override
    public void register(ISpider spider, Node parentNode) {
        this.spider = spider;
        this.parentNode = parentNode;
    }
}
