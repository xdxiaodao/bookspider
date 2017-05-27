package com.github.xdxiaodao.spider.core.base.service.platform;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created with bookspider
 * User zhangmz
 * Date 2017/5/27
 * Time 15:37
 * Desc
 */
public class BaseThreadFactory implements ThreadFactory{
    private String namePrefix;
    private AtomicLong count;

    public BaseThreadFactory(final String namePrefix) {
        this.namePrefix = namePrefix;
        this.count = new AtomicLong();
    }

    @Override
    public Thread newThread(final Runnable target) {
        return new Thread(null, target, this.namePrefix + "-"  + this.count.incrementAndGet());
    }
}
