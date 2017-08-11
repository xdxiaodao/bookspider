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

    /**
     * 根据beanName获取bean实例
     * @param beanName 类名
     * @return 实例
     */
    public static Object getBean(String beanName) {
        return beanFactory.getBean(beanName);
    }

    /**
     * 根据类名获取bean实例
     * @param className 类名
     * @param args 参数
     * @return 实例
     */
    public static Object getBean(String className, Object... args) {
        return beanFactory.getBean(className, args);
    }
}
