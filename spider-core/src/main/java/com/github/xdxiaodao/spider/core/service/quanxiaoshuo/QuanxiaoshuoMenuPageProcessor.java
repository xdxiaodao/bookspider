package com.github.xdxiaodao.spider.core.service.quanxiaoshuo;

import com.github.xdxiaodao.spider.core.base.model.Node;
import com.github.xdxiaodao.spider.core.common.SpiderConstants;
import com.github.xdxiaodao.spider.core.base.service.BaseBookProcessor;
import com.github.xdxiaodao.spider.core.common.interfaces.BookPageProcessor;
import com.github.xdxiaodao.spider.core.model.Book;
import com.github.xdxiaodao.spider.core.service.book.BookSpiderService;
import com.github.xdxiaodao.spider.core.util.HtmlUtil;
import com.github.xdxiaodao.spider.core.util.HttpProxyUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.http.HttpHost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

/**
 * Created with bookspider
 * User zhangmz
 * Date 2017/5/15
 * Time 11:07
 * Desc
 */
@Service
public class QuanxiaoshuoMenuPageProcessor extends BaseBookProcessor implements PageProcessor, BookPageProcessor {
    private static Logger logger = LoggerFactory.getLogger(QuanxiaoshuoMenuPageProcessor.class);

    private String url;
    private String name;

    @Autowired
    private QuanxiaoshuoBookPageProcessor quanxiaoshuoMenuPageProcessor;

    @Override
    public void process(Page page) {

        Html html = page.getHtml();
        Selectable bookSelectableList = html.xpath("//ul[@class=list_content]");
        if (CollectionUtils.isNotEmpty(bookSelectableList.nodes())) {
            for (Selectable tmpBookSelectable : bookSelectableList.nodes()) {
                String bookInfo = tmpBookSelectable.xpath("//li[@class=cc2]/a").toString();
                String authorInfo = tmpBookSelectable.xpath("//li[@class=cc4]/a").toString();

                String[] bookInfoArr = HtmlUtil.extractHrefInfoFromA(bookInfo);
                String[] authorInfoArr = HtmlUtil.extractHrefInfoFromA(authorInfo);

                logger.info("book Name:" + bookInfoArr[0] + ", bookUrl:" + bookInfoArr[1]);

                Node book = Book.me().bookPageProcessor(quanxiaoshuoMenuPageProcessor).name(bookInfoArr[0]).url(bookInfoArr[1]).parent(parentNode);
                ((BookSpiderService) spider).newNode(book);
            }
        }
    }

    @Override
    public Site getSite() {
        Site site = SpiderConstants.DEFAULT_SITE;
        HttpHost httpHost = HttpProxyUtil.getHttpProxy();
        if (null != httpHost) {
            site.setHttpProxy(httpHost);
        }
        return site;
    }

    public static QuanxiaoshuoMenuPageProcessor newQuanxiaoshuoMenuPageProcessor(String url, String name) {
        QuanxiaoshuoMenuPageProcessor quanxiaoshuoMenuPageProcessor = new QuanxiaoshuoMenuPageProcessor();
        quanxiaoshuoMenuPageProcessor.url = url;
        quanxiaoshuoMenuPageProcessor.name = name;

        return quanxiaoshuoMenuPageProcessor;
    }

    public void start() {
        Spider.create(this).addUrl(url).thread(1).run();
    }

    public static void main(String[] args) {
        QuanxiaoshuoMenuPageProcessor.newQuanxiaoshuoMenuPageProcessor("http://quanxiaoshuo.com/junshi/", "junshi").start();
    }
}
