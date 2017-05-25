package com.github.xdxiaodao.spider.core.service.quanxiaoshuo;

import com.github.xdxiaodao.spider.core.common.SpiderConstants;
import com.github.xdxiaodao.spider.core.util.HtmlUtil;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import java.io.FileWriter;
import java.util.List;

/**
 * Created with freebook
 * User zhangmuzhao
 * Date 2017/5/15
 * Time 12:35
 * Desc
 */
public class QuanxiaoshuoChapterPageProcessor implements PageProcessor {

    private String name;
    private String url;
    private String bookPath;

    /**
     * process the page, extract urls to fetch, extract the data and store
     *
     * @param page page
     */
    @Override
    public void process(Page page) {
        Html html = page.getHtml();
        String chapterContent = html.xpath("//div[@id=content]").toString();
        List<String> extractContent = HtmlUtil.extractContent(chapterContent, "<br>(\\S+)(\\s+)");
        System.out.println(StringUtils.join(extractContent, "\n"));
        extractContent.add(0, "\n\n" + name + "\n\n");
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(bookPath, true);
            IOUtils.writeLines(extractContent, "\n", fileWriter);
        } catch (Exception e) {
            e.printStackTrace();
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

    public static QuanxiaoshuoChapterPageProcessor newQuanxiaoshuoChapterPageProcessor(String url, String name, String bookPath) {
        QuanxiaoshuoChapterPageProcessor quanxiaoshuoChapterPageProcessor = new QuanxiaoshuoChapterPageProcessor();
        quanxiaoshuoChapterPageProcessor.url = url;
        quanxiaoshuoChapterPageProcessor.name = name;
        quanxiaoshuoChapterPageProcessor.bookPath = bookPath;


        return quanxiaoshuoChapterPageProcessor;
    }

    public void start() {
        Spider.create(this).addUrl(url).thread(1).run();
    }

    public static void main(String[] args) {
        QuanxiaoshuoChapterPageProcessor.newQuanxiaoshuoChapterPageProcessor("http://quanxiaoshuo.com/37964/8499237/", "佣兵的战争", "").start();
    }
}
