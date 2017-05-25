package com.github.xdxiaodao.spider.core.util;

import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with freebook
 * User zhangmuzhao
 * Date 2017/5/15
 * Time 12:25
 * Desc
 */
public class HtmlUtil {

    public static String[] extractHrefInfoFromA(String aHref) {
        Matcher matcher = Pattern.compile("<a.*?href=[\\'|\\\"](.*?)[\\'|\\\"].*>(.*?)</a>").matcher(aHref);
        if (matcher.matches()) {
            String name = matcher.group(2);
            String href = matcher.group(1);
            return new String[]{name, href};
        }

        return new String[]{"", ""};
    }

    public static List<String> extractContent(String content, String regexp) {
        if (StringUtils.isBlank(content)) {
            return Lists.newArrayList();
        }

        List<String> resultList = Lists.newArrayList();
        if (StringUtils.isBlank(regexp)) {
            resultList.add(content);
            return resultList;
        }

        Matcher matcher = Pattern.compile(regexp).matcher(content);
        while (matcher.find()) {
            String groupContent = matcher.group(1);
            if (StringUtils.isBlank(groupContent)) {
                continue;
            }

            resultList.add(groupContent);
        }

        // 如果没有解析到内容，则直接添加整块内容
        if (CollectionUtils.isEmpty(resultList)) {
            resultList.add(content);
        }

        return resultList;
    }

    public static String filterContentHtmlTag(String content, String... htmlTags) {
        if (StringUtils.isBlank(content)) {
            return "";
        }

        if (ArrayUtils.isEmpty(htmlTags)) {
            return content;
        }

        // 将htmltags转换为set
        String tmpContent = content;
        for (String tmpHtmlTag : htmlTags) {
            tmpContent = content.replaceAll(tmpHtmlTag + ".*" + tmpHtmlTag.charAt(0) + "/" + tmpHtmlTag.substring(1), content);
        }

        return tmpContent;
    }
}
