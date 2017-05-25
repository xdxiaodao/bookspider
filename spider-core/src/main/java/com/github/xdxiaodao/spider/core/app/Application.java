package com.github.xdxiaodao.spider.core.app;

import com.github.xdxiaodao.spider.core.service.book.BookSpiderService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by xiaohuilang on 17/5/23.
 */
public class Application {

    public static void main(String[] args) {
        ApplicationContext context=new ClassPathXmlApplicationContext(
                new String[]{"classpath*:applicationContext.xml"});

        BookSpiderService bookSpiderService = context.getBean(BookSpiderService.class);
        bookSpiderService.start();
    }
}
