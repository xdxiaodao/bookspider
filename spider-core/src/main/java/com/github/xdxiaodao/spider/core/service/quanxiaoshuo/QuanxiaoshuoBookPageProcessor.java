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
 * Time 12:35
 * Desc
 */
public class QuanxiaoshuoBookPageProcessor implements PageProcessor {

    private String name;
    private String url;

    /**
     * process the page, extract urls to fetch, extract the data and store
     *
     * @param page page
     */
    @Override
    public void process(Page page) {
        Html html = page.getHtml();
        Selectable chapterSelectableList = html.xpath("//div[@class=chapter]/a");
        if (CollectionUtils.isNotEmpty(chapterSelectableList.nodes())) {
            for (Selectable tmpSelectable : chapterSelectableList.nodes()) {
                String chapterInfo = tmpSelectable.toString();
                String[] chapterInfoArr = HtmlUtil.extractHrefInfoFromA(chapterInfo);
                System.out.println("chapter name:" + chapterInfoArr[0] + ",chapter url:" + chapterInfoArr[1]);
                QuanxiaoshuoChapterPageProcessor.newQuanxiaoshuoChapterPageProcessor(chapterInfoArr[1], chapterInfoArr[0], name).start();
            }
        }
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

    public static QuanxiaoshuoBookPageProcessor newQuanxiaoshuoBookPageProcessor(String url, String name) {
        QuanxiaoshuoBookPageProcessor quanxiaoshuoBookPageProcessor = new QuanxiaoshuoBookPageProcessor();
        quanxiaoshuoBookPageProcessor.url = url;
        quanxiaoshuoBookPageProcessor.name = name;

        return quanxiaoshuoBookPageProcessor;
    }

    public void start() {
        Spider.create(this).addUrl(url).thread(1).run();
    }

    public static void main(String[] args) {
        QuanxiaoshuoBookPageProcessor.newQuanxiaoshuoBookPageProcessor("http://quanxiaoshuo.com/167454/", "佣兵的战争").start();
    }
}
