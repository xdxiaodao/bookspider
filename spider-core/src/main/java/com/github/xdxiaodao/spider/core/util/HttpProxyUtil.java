package com.github.xdxiaodao.spider.core.util;

import com.alibaba.fastjson.JSON;
import com.github.xdxiaodao.dynamicip.model.DynamicIp;
import com.github.xdxiaodao.dynamicip.service.DynamicIpService;
import com.google.common.collect.Maps;
import org.apache.http.HttpHost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with bookspider
 * User zhangmuzhao
 * Date 2017/6/21
 * Time 18:17
 * Desc
 */
public class HttpProxyUtil {
    private static Logger logger = LoggerFactory.getLogger(HttpProxyUtil.class);

//    private static DynamicIpService dynamicIpService = SpringBeanUtil.getBean(DynamicIpService.class);

    private static ConcurrentHashMap<HttpHost, DynamicIp> proxyMap = new ConcurrentHashMap<HttpHost, DynamicIp>();

    public static HttpHost getHttpProxy() {
//        DynamicIp dynamicIp = dynamicIpService.getEffectiveIp();
//        logger.info("获取到动态ip为{}", JSON.toJSONString(dynamicIp));
//        if (null == dynamicIp) {
//            return null;
//        }
//
//        if (dynamicIp.getIp().equalsIgnoreCase("localhost")) {
//            return null;
//        }
//
//        HttpHost httpHost = new HttpHost(dynamicIp.getIp(), dynamicIp.getPort());
//        proxyMap.put(httpHost, dynamicIp);
//        return httpHost;
        return null;
    }

    /**
     * 移除无效配置
     * @param httpHost httpHost
     * @return 是否成功
     */
    public static boolean removeHttpProxy(HttpHost httpHost) {
        if (null == httpHost) {
            return true;
        }
        if (proxyMap.containsKey(httpHost)) {
            proxyMap.get(httpHost).setIsEffective(false);
            return true;
        }

        return false;
    }
}
