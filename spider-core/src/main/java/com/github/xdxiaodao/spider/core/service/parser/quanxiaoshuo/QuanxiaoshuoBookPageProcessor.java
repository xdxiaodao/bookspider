package com.github.xdxiaodao.spider.core.service.parser.quanxiaoshuo;

import com.github.xdxiaodao.spider.core.base.model.Node;
import com.github.xdxiaodao.spider.core.common.SpiderConstants;
import com.github.xdxiaodao.spider.core.base.service.BaseBookProcessor;
import com.github.xdxiaodao.spider.core.common.enums.ParseProcessType;
import com.github.xdxiaodao.spider.core.common.interfaces.BookPageProcessor;
import com.github.xdxiaodao.spider.core.model.Book;
import com.github.xdxiaodao.spider.core.model.Chapter;
import com.github.xdxiaodao.spider.core.service.spider.BookSpiderService;
import com.github.xdxiaodao.spider.core.util.HtmlUtil;
import com.github.xdxiaodao.spider.core.util.HttpProxyUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.http.HttpHost;
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
 * Time 12:35
 * Desc
 */
@Service
public class QuanxiaoshuoBookPageProcessor extends BaseBookProcessor implements PageProcessor, BookPageProcessor {

    private String name;
    private String url;

    @Autowired
    private QuanxiaoshuoChapterPageProcessor quanxiaoshuoChapterPageProcessor;

    @Override
    public void process(Page page) {
        Html html = page.getHtml();
        Selectable chapterSelectableList = html.xpath("//div[@class=chapter]/a");
        Book book = Book.me();
        if (parentNode instanceof Book) {
            book = (Book) parentNode;
        }
        book.setParseProcess(ParseProcessType.DOING);
        if (CollectionUtils.isNotEmpty(chapterSelectableList.nodes())) {
            int i = 0;
            for (Selectable tmpSelectable : chapterSelectableList.nodes()) {
                String chapterInfo = tmpSelectable.toString();
                String[] chapterInfoArr = HtmlUtil.extractHrefInfoFromA(chapterInfo);
                System.out.println("chapter name:" + chapterInfoArr[0] + ",chapter url:" + chapterInfoArr[1]);

                // 解析index
                Long index = 0l;
                String url = chapterInfoArr[1];
                String[] parseArr = StringUtils.split(url, "/");
                if (ArrayUtils.isNotEmpty(parseArr)) {
                    index = NumberUtils.toLong(parseArr[parseArr.length - 1]);
                }

                // 判断解析是否完成
                if (++i == chapterSelectableList.nodes().size()) {
                    book.setParseProcess(ParseProcessType.DONE);
                }
                Node chapter = Chapter.me().bookPageProcessor(quanxiaoshuoChapterPageProcessor).name(chapterInfoArr[0]).url(chapterInfoArr[1]).parent(parentNode);
                ((Chapter) chapter).setIndex(index);
                ((BookSpiderService) spider).newNode(chapter);
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
