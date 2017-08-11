package com.github.xdxiaodao.spider.core.service.proxy;

import org.apache.http.HttpHost;

/**
 * Created with bookspider
 * User zhangmuzhao
 * Date 2017/8/11
 * Time 11:04
 * Desc
 */
public interface HttpProxyPool {

    /**
     * 随机获取代理
     * @return
     */
    public HttpHost getProxy();

    /**
     * 获取到指定url的代理
     * @param url url
     * @return 代理
     */
    public HttpHost getProxy(String url);

    /**
     * 移除代理
     * @param httpHost 代理
     * @return 移除是否成功
     */
    public boolean delProxy(HttpHost httpHost);
}
