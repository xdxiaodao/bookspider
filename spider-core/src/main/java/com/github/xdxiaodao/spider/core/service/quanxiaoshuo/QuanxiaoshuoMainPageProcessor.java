package com.github.xdxiaodao.spider.core.service.quanxiaoshuo;

import com.github.xdxiaodao.spider.core.base.service.BaseBookProcessor;
import com.github.xdxiaodao.spider.core.base.model.Node;
import com.github.xdxiaodao.spider.core.common.SpiderConstants;
import com.github.xdxiaodao.spider.core.common.interfaces.BookPageProcessor;
import com.github.xdxiaodao.spider.core.model.BookCategory;
import com.github.xdxiaodao.spider.core.service.book.BookSpiderService;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with bookspider
 * User zhangmz
 * Date 2017/5/10
 * Time 19:15
 * Desc
 */
@Service
public class QuanxiaoshuoMainPageProcessor extends BaseBookProcessor implements PageProcessor, BookPageProcessor {
    private static Logger logger = LoggerFactory.getLogger(QuanxiaoshuoMainPageProcessor.class);

    private static Set<String> menuNameSet = Sets.newHashSet();

    @Autowired
    private QuanxiaoshuoMenuPageProcessor quanxiaoshuoMenuPageProcessor;

    @Override
    public void process(Page page) {
        Selectable menuLiSelectable = page.getHtml().xpath("//div[@class='meun']/ul/li/a");
        if (CollectionUtils.isNotEmpty(menuLiSelectable.nodes())) {
            for (Selectable tmpSelectable : menuLiSelectable.nodes()) {
                List<Selectable> aSelectableList = tmpSelectable.nodes();
                if (CollectionUtils.isNotEmpty(aSelectableList)) {
                    String aHref = aSelectableList.get(0).toString();
                    if (StringUtils.isNotBlank(aHref)) {
                        Matcher matcher = Pattern.compile("<a.*?href=[\\'|\\\"](.*)[\\'|\\\"]>(.*?)</a>").matcher(aHref);
                        if (matcher.matches()) {
                            String menuName = matcher.group(2);
                            String menuHref = matcher.group(1);
                            logger.info("menuName:{},menuHref:{}", menuHref, menuHref);
                            if (menuNameSet.contains(menuName)) {
                                continue;
                            }

                            if ("首页".equals(menuName) || "点击榜".equals(menuName)) {
                                continue;
                            }

                            Node bookCategory = BookCategory.me().bookPageProcessor(quanxiaoshuoMenuPageProcessor).name(menuName).url(menuHref).parent(parentNode);
                            ((BookSpiderService) spider).newNode(bookCategory);

                        }
                    }
                }
            }
        }
    }

    @Override
    public Site getSite() {
        return SpiderConstants.DEFAULT_SITE;
    }

    public static void main(String[] args) {
        Spider.create(new QuanxiaoshuoMainPageProcessor()).addUrl("http://quanxiaoshuo.com").thread(1).run();
    }
}
