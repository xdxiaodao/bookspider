package com.github.xdxiaodao.spider.core.service.quanxiaoshuo;

import com.github.xdxiaodao.spider.core.base.service.BaseBookProcessor;
import com.github.xdxiaodao.spider.core.base.model.Node;
import com.github.xdxiaodao.spider.core.base.model.SpiderNode;
import com.github.xdxiaodao.spider.core.common.SpiderConstants;
import com.github.xdxiaodao.spider.core.common.enums.ParseProcessType;
import com.github.xdxiaodao.spider.core.common.interfaces.BookPageProcessor;
import com.github.xdxiaodao.spider.core.model.Book;
import com.github.xdxiaodao.spider.core.model.Chapter;
import com.github.xdxiaodao.spider.core.util.HtmlUtil;
import com.github.xdxiaodao.spider.core.util.HttpProxyUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpHost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.selector.Html;

import java.util.List;

/**
 * Created with bookspider
 * User zhangmz
 * Date 2017/5/15
 * Time 12:35
 * Desc
 */
@Service
public class QuanxiaoshuoChapterPageProcessor extends BaseBookProcessor implements BookPageProcessor {
    private static Logger logger = LoggerFactory.getLogger(QuanxiaoshuoChapterPageProcessor.class);
    private String name;
    private String url;
    private String bookPath;

    @Override
    public void process(Page page) {
        ((SpiderNode) parentNode).setParseProcess(ParseProcessType.DOING);
        Html html = page.getHtml();
        String chapterContent = html.xpath("//div[@id=content]").toString();
        List<String> extractContent = HtmlUtil.extractContent(chapterContent, "<br>(\\S+)(\\s+)");

        // 解析叶子节点内容
        if (parentNode instanceof Chapter) {
            Chapter chapter = (Chapter) parentNode;
            extractContent.add(0, "\n\n" + chapter.getName() + "\n\n");
            chapter.setContent(StringUtils.join(extractContent, "\n"));
            chapter.setParseProcess(ParseProcessType.DONE);
            Node superNode = chapter.getParent();
            if (superNode instanceof Book) {
                ((Book) superNode).addParseDoneChild(chapter.getName());
            }
            logger.info("解析章节：{}成功", chapter.getName());
        }

    }

    @Override
    public Site getSite() {
        Site site = SpiderConstants.CHAPTER_SITE;
        HttpHost httpHost = HttpProxyUtil.getHttpProxy();
        if (null != httpHost) {
            site.setHttpProxy(httpHost);
        }
        return site;
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
