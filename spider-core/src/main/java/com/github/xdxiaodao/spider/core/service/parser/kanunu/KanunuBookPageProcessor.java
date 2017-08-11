package com.github.xdxiaodao.spider.core.service.parser.kanunu;

import com.github.xdxiaodao.spider.core.base.model.Node;
import com.github.xdxiaodao.spider.core.common.SpiderConstants;
import com.github.xdxiaodao.spider.core.base.service.BaseBookProcessor;
import com.github.xdxiaodao.spider.core.common.enums.ParseProcessType;
import com.github.xdxiaodao.spider.core.common.interfaces.BookPageProcessor;
import com.github.xdxiaodao.spider.core.model.Book;
import com.github.xdxiaodao.spider.core.model.Chapter;
import com.github.xdxiaodao.spider.core.service.biz.BookService;
import com.github.xdxiaodao.spider.core.service.spider.BookSpiderService;
import com.github.xdxiaodao.spider.core.util.HtmlUtil;
import com.github.xdxiaodao.spider.core.util.HttpProxyUtil;
import com.google.common.collect.Lists;
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

import java.util.List;

/**
 * Created with bookspider
 * User zhangmz
 * Date 2017/5/15
 * Time 12:35
 * Desc
 */
@Service
public class KanunuBookPageProcessor extends BaseBookProcessor implements PageProcessor, BookPageProcessor {

    private String name;
    private String url;

    @Autowired
    private KanunuChapterPageProcessor kanunuChapterPageProcessor;

    @Autowired
    private BookService bookService;

    @Override
    public void process(Page page) {
        Html html = page.getHtml();
        Selectable chapterSelectableList = html.xpath("//table/tbody/tr/td/table/tbody/tr/td/a");
        Book book = Book.me();
        if (parentNode instanceof Book) {
            book = (Book) parentNode;
        }
        book.setParseProcess(ParseProcessType.DOING);
        book.setUpdateTime(book.getCreateTime());
        Long bookId = bookService.addBook(book);
        if (CollectionUtils.isNotEmpty(chapterSelectableList.nodes())) {
            List<Node> nodeList = Lists.newArrayList();
            for (Selectable tmpSelectable : chapterSelectableList.nodes()) {
                String chapterInfo = tmpSelectable.toString();
                String[] chapterInfoArr = HtmlUtil.extractHrefInfoFromA(chapterInfo);
                System.out.println("chapter name:" + chapterInfoArr[0] + ",chapter url:" + chapterInfoArr[1]);

                if (StringUtils.isBlank(chapterInfoArr[0]) || StringUtils.isBlank(chapterInfoArr[1])) {
                    continue;
                }

                // 校验url是否为本书，如果章节url不包含书籍url，则跳过
                String bookUrlPrefix = this.parentNode.getUrl().replace("/index", "").replace(".html", "/");
                if (!chapterInfoArr[1].contains(bookUrlPrefix)) {
                    continue;
                }

                // 解析index
                Long index = 0l;
                String url = chapterInfoArr[1];
                String[] parseArr = StringUtils.split(url, "/");
                if (ArrayUtils.isNotEmpty(parseArr)) {
                    index = NumberUtils.toLong(parseArr[parseArr.length - 1].replace(".html", ""));
                }

                Node chapter = Chapter.me().bookPageProcessor(kanunuChapterPageProcessor).name(chapterInfoArr[0]).url(chapterInfoArr[1]).parent(parentNode);
                ((Chapter) chapter).setIndex(index);
                ((Chapter) chapter).setBookId(bookId);
                nodeList.add(chapter);
//                ((BookSpiderService) spider).newNode(chapter);
            }

            for (int i = 0; i < nodeList.size(); i++) {
                if (i == nodeList.size() - 1) {
                    book.setParseProcess(ParseProcessType.DONE);
                }

                ((BookSpiderService) spider).newNode(nodeList.get(i));
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

    public static KanunuBookPageProcessor newKanunuBookPageProcessor(String url, String name) {
        KanunuBookPageProcessor kanunuBookPageProcessor = new KanunuBookPageProcessor();
        kanunuBookPageProcessor.url = url;
        kanunuBookPageProcessor.name = name;

        return kanunuBookPageProcessor;
    }

    public void start() {
        Spider.create(this).addUrl(url).thread(1).run();
    }

    public static void main(String[] args) {
        KanunuBookPageProcessor.newKanunuBookPageProcessor("http://www.kanunu8.com/wuxia/201102/1726.html", "沧海").start();
    }
}
