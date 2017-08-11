package com.github.xdxiaodao.spider.core.service.store;

import com.github.xdxiaodao.spider.core.common.enums.StoreType;
import com.github.xdxiaodao.spider.core.util.PropertiesUtil;
import com.github.xdxiaodao.spider.core.util.SpringBeanUtil;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.support.PropertiesLoaderUtils;

/**
 * Created with bookspider
 * User zhangmuzhao
 * Date 2017/8/10
 * Time 14:23
 * Desc
 */
public class SpiderStoreFactory {

    /**
     * 获取指定类型的爬虫存储
     * @param storeType 存储类型
     * @return 爬虫存储
     * @throws Exception
     */
    public static SpiderStore getSpiderStore(StoreType storeType) throws Exception {
        try {
            if (null == storeType) {
                storeType = StoreType.DB;
            }

            String beanName = storeType.toString().toLowerCase() + "SpiderStoreService";
            Object obj = SpringBeanUtil.getBean(beanName);
            if (obj instanceof SpiderStore) {
                return (SpiderStore) obj;
            }
        } catch (Exception e) {
            throw new Exception("获取 spiderStore处理器错误");
        }

        throw new Exception("获取 spiderStore处理器错误");
    }

    /**
     * 获取爬虫存储
     * @return 爬虫存储
     * @throws Exception
     */
    public static SpiderStore getSpiderStore() throws Exception {
        String spiderStore = PropertiesUtil.getProperties("spider.store.strategy");
        if (StringUtils.isBlank(spiderStore)) {
            throw new Exception("spider.store.strategy 未配置");
        }

        StoreType storeType = StoreType.valueOf(spiderStore);
        if (null == storeType) {
            throw new Exception("spider.store.strategy 不支持的配置");
        }

        return getSpiderStore(storeType);
    }
}
