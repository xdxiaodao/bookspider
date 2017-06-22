package com.github.xdxiaodao.spider.core.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

/**
 * Created with bookspider
 * User zhangmuzhao
 * Date 2017/6/21
 * Time 18:29
 * Desc
 */
public class SpringBeanUtil implements BeanFactoryAware {
    private static BeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        SpringBeanUtil.beanFactory = beanFactory;
    }

    /**
     * 获取bean
     * @param tClass class
     * @param <T> class类型
     * @return bean
     */
    public static <T> T getBean(Class<T> tClass) {
        return beanFactory.getBean(tClass);
    }
}
