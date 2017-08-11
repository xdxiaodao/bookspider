package com.github.xdxiaodao.spider.core.service.proxy.py;

import com.github.xdxiaodao.dynamicip.util.http.HttpWorker;
import com.github.xdxiaodao.dynamicip.util.http.Params;
import com.github.xdxiaodao.spider.core.service.proxy.HttpProxyPool;
import com.github.xdxiaodao.spider.core.util.PropertiesUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;

/**
 * Created with bookspider
 * User zhangmuzhao
 * Date 2017/8/11
 * Time 14:03
 * Desc
 */
@Service
public class PyHttpProxyPool implements HttpProxyPool{
    private static Logger logger = LoggerFactory.getLogger(PyHttpProxyPool.class);

    private String httpUrl;

    @PostConstruct
    public void init() {
        httpUrl = PropertiesUtil.getProperties("py.http.proxy.pool.url");
    }

    /**
     * 随机获取代理
     *
     * @return
     */
    @Override
    public HttpHost getProxy() {
        try {
            if (StringUtils.isBlank(httpUrl)) {
                return null;
            }
            String content = HttpWorker.getHttpContent(httpUrl, new ArrayList<Params>(), false, "utf-8");
            if (StringUtils.isBlank(content)) {
                return null;
            }

            String ip = StringUtils.substringBefore(content, ":");
            String port = StringUtils.substringAfter(content , ":");

            if (StringUtils.isNotBlank(ip) && StringUtils.isNotBlank(port)) {
                return new HttpHost(ip, Integer.valueOf(port));
            }

        } catch (Exception e) {
            logger.error("获取proxy失败", e);
        }
        return null;
    }

    /**
     * 获取到指定url的代理
     *
     * @param url url
     * @return 代理
     */
    @Override
    public HttpHost getProxy(String url) {
        return null;
    }

    /**
     * 移除代理
     *
     * @param httpHost 代理
     * @return 移除是否成功
     */
    @Override
    public boolean delProxy(HttpHost httpHost) {
        return false;
    }
}
