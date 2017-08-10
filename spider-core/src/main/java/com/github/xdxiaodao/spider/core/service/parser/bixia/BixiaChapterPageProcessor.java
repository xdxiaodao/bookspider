package com.github.xdxiaodao.spider.core.service.parser.bixia;

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
import com.google.common.collect.Lists;
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
public class BixiaChapterPageProcessor extends BaseBookProcessor implements BookPageProcessor {
    private static Logger logger = LoggerFactory.getLogger(com.github.xdxiaodao.spider.core.service.parser.quanxiaoshuo.QuanxiaoshuoChapterPageProcessor.class);
    private String name;
    private String url;
    private String bookPath;

    @Override
    public void process(Page page) {
        ((SpiderNode) parentNode).setParseProcess(ParseProcessType.DOING);
        Html html = page.getHtml();
        String chapterContent = html.xpath("//div[@id=content]").toString();
        List<String> extractContent = HtmlUtil.extractContent(chapterContent, "<br>(\\S+)(\\s+)");

        List<String> filterContent = Lists.newArrayList();
        for (String oriContent : extractContent) {
            String filterData = oriContent.replaceAll("&nbsp;", "");
            if (StringUtils.isNotBlank(filterData)) {
                filterContent.add(filterData);
            }
        }

        // 解析叶子节点内容
        if (parentNode instanceof Chapter) {
            Chapter chapter = (Chapter) parentNode;
            filterContent.add(0, "\n\n" + chapter.getName() + "\n\n");
            chapter.setContent(StringUtils.join(filterContent, "\n"));
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

    public static BixiaChapterPageProcessor newBixiaChapterPageProcessor(String url, String name, String bookPath) {
        BixiaChapterPageProcessor bixiaChapterPageProcessor = new BixiaChapterPageProcessor();
        bixiaChapterPageProcessor.url = url;
        bixiaChapterPageProcessor.name = name;
        bixiaChapterPageProcessor.bookPath = bookPath;


        return bixiaChapterPageProcessor;
    }

    public void start() {
        Spider.create(this).addUrl(url).thread(1).run();
    }

    public static void main(String[] args) {
        BixiaChapterPageProcessor.newBixiaChapterPageProcessor("http://www.bxwx9.org/b/3/3134/526388.html", "佣兵的战争", "").start();
    }
}
