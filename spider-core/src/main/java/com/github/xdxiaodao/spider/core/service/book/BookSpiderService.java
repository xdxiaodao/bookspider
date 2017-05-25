package com.github.xdxiaodao.spider.core.service.book;

import com.github.xdxiaodao.spider.core.model.BookCategory;
import com.github.xdxiaodao.spider.core.service.quanxiaoshuo.QuanxiaoshuoMainPageProcessor;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Spider;

import java.util.List;

/**
 * Created by xiaohuilang on 17/5/23.
 */
@Service
public class BookSpiderService {

    private List<BookCategory> bookCategoryList = Lists.newArrayList();

    @Autowired
    private QuanxiaoshuoMainPageProcessor quanxiaoshuoMainPageProcessor;

    public void start() {
        startQuanxiaoshuo();
    }

    private void startQuanxiaoshuo() {
        Spider.create(quanxiaoshuoMainPageProcessor).addUrl("http://quanxiaoshuo.com").thread(1).run();
    }

}
