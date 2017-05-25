package com.github.xdxiaodao.spider.core.service.quanxiaoshuo;

import com.github.xdxiaodao.spider.core.common.SpiderConstants;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.io.File;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with freebook
 * User zhangmuzhao
 * Date 2017/5/10
 * Time 19:15
 * Desc
 */
@Service
public class QuanxiaoshuoMainPageProcessor implements PageProcessor{

    private static Set<String> menuNameSet = Sets.newHashSet();

    private static String ROOT_DIR_NAME = "D:\\personal\\data\\book\\quanxiaoshuo";

    @Override
    public void process(Page page) {
//        page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/\\w+/\\w+)").all());
//        page.putField("author", page.getUrl().regex("https://github\\.com/(\\w+)/.*").toString());
//        page.putField("name", page.getHtml().xpath("//h1[@class='entry-title public']/strong/a/text()").toString());
//        if (page.getResultItems().get("name")==null){
//            //skip this page
//            page.setSkip(true);
//        }
//        page.putField("readme", page.getHtml().xpath("//div[@id='readme']/tidyText()"));

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
                            System.out.println("menuName:" + menuName + ",menuHref:" + menuHref);
                            if (menuNameSet.contains(menuName)) {
                                continue;
                            }

                            if ("点击榜".equals(menuName)) {
                                continue;
                            }

                            File file = new File(ROOT_DIR_NAME + "\\" + menuName);
                            if (!file.exists()) {
                                file.mkdirs();
                                menuNameSet.add(menuName);
                                QuanxiaoshuoMenuPageProcessor.newQuanxiaoshuoMenuPageProcessor(menuHref, ROOT_DIR_NAME + "\\" + menuName).start();;
                            }
                        }
                    }
                }
            }
        }

        System.out.println(menuLiSelectable);
    }

    @Override
    public Site getSite() {
        return SpiderConstants.DEFAULT_SITE;
    }

    public static void main(String[] args) {
        Spider.create(new QuanxiaoshuoMainPageProcessor()).addUrl("http://quanxiaoshuo.com").thread(1).run();
    }
}
