package com.github.xdxiaodao.spider.core.service.biz;

import com.github.xdxiaodao.spider.core.model.Chapter;

/**
 * Created with bookspider
 * User zhangmuzhao
 * Date 2017/8/8
 * Time 18:19
 * Desc
 */
public interface ChapterService {

    /**
     * 添加章节内容
     * @param chapter 章节信息
     * @return 添加后的章节ID
     */
    public int addChapter(Chapter chapter);
}
