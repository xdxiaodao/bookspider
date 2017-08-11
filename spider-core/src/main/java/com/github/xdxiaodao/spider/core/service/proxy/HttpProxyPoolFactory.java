package com.github.xdxiaodao.spider.core.service.proxy;

import com.github.xdxiaodao.spider.core.service.proxy.py.PyHttpProxyPool;
import com.github.xdxiaodao.spider.core.util.SpringBeanUtil;

/**
 * Created with bookspider
 * User zhangmuzhao
 * Date 2017/8/11
 * Time 14:37
 * Desc
 */
public class HttpProxyPoolFactory {

    /**
     * 获取代理池
     * @return 代理池
     */
    public static HttpProxyPool getHttpProxyPool() {
        return SpringBeanUtil.getBean(PyHttpProxyPool.class);
    }
}
