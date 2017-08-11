package com.github.xdxiaodao.spider.core.service.biz.impl;

import com.github.xdxiaodao.spider.core.dao.mapper.ChapterMapper;
import com.github.xdxiaodao.spider.core.model.Chapter;
import com.github.xdxiaodao.spider.core.service.biz.ChapterService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with bookspider
 * User zhangmuzhao
 * Date 2017/8/8
 * Time 18:21
 * Desc
 */
@Service
public class ChapterServiceImpl implements ChapterService {
    private static Logger logger = LoggerFactory.getLogger(ChapterServiceImpl.class);

    @Autowired(required = false)
    private ChapterMapper chapterMapper;

    /**
     * 添加章节内容
     *
     * @param chapter 章节信息
     * @return 添加后的章节ID
     */
    @Override
    public Long addChapter(Chapter chapter) {
        try {
            if (null == chapter || StringUtils.isBlank(chapter.getName())) {
                return -1l;
            }
            com.github.xdxiaodao.spider.core.model.po.Chapter chapterPo = new com.github.xdxiaodao.spider.core.model.po.Chapter();
            BeanUtils.copyProperties(chapterPo, chapter);
            int result = chapterMapper.insert(chapterPo);
            if (result > 0) {
                chapterPo.setId(null);
                com.github.xdxiaodao.spider.core.model.po.Chapter insertChapter = chapterMapper.selectOne(chapterPo);
                if (null != insertChapter) {
                    return insertChapter.getId();
                }
            }
            return -1l;
        } catch (Exception e) {
            logger.error("章节信息信息写入失败", e);
        }
        return -1l;
    }
}
