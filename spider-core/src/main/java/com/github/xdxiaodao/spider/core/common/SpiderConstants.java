package com.github.xdxiaodao.spider.core.common;

import us.codecraft.webmagic.Site;

/**
 * Created with freebook
 * User zhangmuzhao
 * Date 2017/5/15
 * Time 12:36
 * Desc
 */
public class SpiderConstants {

    public static final Site DEFAULT_SITE = Site.me().setRetryTimes(3).setSleepTime(100);
}
