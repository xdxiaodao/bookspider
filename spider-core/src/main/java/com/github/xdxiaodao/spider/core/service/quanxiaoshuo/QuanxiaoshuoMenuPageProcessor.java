package com.github.xdxiaodao.spider.core.service.quanxiaoshuo;

import com.github.xdxiaodao.spider.core.common.SpiderConstants;
import com.github.xdxiaodao.spider.core.util.HtmlUtil;
import org.apache.commons.collections.CollectionUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

/**
 * Created with freebook
 * User zhangmuzhao
 * Date 2017/5/15
 * Time 11:07
 * Desc
 */
public class QuanxiaoshuoMenuPageProcessor implements PageProcessor{

    private String url;
    private String name;

    /**
     * process the page, extract urls to fetch, extract the data and store
     *
     * @param page page
     */
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

                System.out.println("book Name:" + bookInfoArr[0] + ", bookUrl:" + bookInfoArr[1]);
                QuanxiaoshuoBookPageProcessor.newQuanxiaoshuoBookPageProcessor(bookInfoArr[1], name + "\\" + bookInfoArr[0] + ".txt").start();
            }
        }
        System.out.println("bookSelectableList");
    }

    /**
     * get the site settings
     *
     * @return site
     * @see us.codecraft.webmagic.Site
     */
    @Override
    public Site getSite() {
        return SpiderConstants.DEFAULT_SITE;
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
