package com.github.xdxiaodao.spider.core.common.interfaces;

import com.github.xdxiaodao.spider.core.base.model.Node;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * Created with bookspider
 * User zhangmz
 * Date 2017/5/26
 * Time 18:01
 * Desc
 */
public interface BookPageProcessor extends PageProcessor {

    public void register(ISpider spider, Node parentNode);
}
