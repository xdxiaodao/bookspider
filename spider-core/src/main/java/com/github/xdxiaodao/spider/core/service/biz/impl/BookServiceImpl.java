package com.github.xdxiaodao.spider.core.service.biz.impl;

import com.github.xdxiaodao.spider.core.dao.mapper.BookMapper;
import com.github.xdxiaodao.spider.core.model.Book;
import com.github.xdxiaodao.spider.core.service.biz.BookService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with bookspider
 * User zhangmuzhao
 * Date 2017/8/8
 * Time 17:36
 * Desc
 */
@Service
public class BookServiceImpl implements BookService {
    private static Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);

    @Autowired
    private BookMapper bookMapper;

    /**
     * 添加书籍
     * @param book 书籍信息
     * @return 新加书籍信息编号
     */
    @Override
    public int addBook(Book book) {
        try {
            if (null == book || StringUtils.isBlank(book.getName())) {
                return -1;
            }
            com.github.xdxiaodao.spider.core.model.po.Book bookPo = new com.github.xdxiaodao.spider.core.model.po.Book();
            BeanUtils.copyProperties(bookPo, book);
            int result = bookMapper.insert(bookPo);
            return result;
        } catch (Exception e) {
            logger.error("书籍信息写入失败", e);
        }
        return -1;
    }

    /**
     * 根据书名查询书籍信息
     *
     * @param bookName 书名
     * @return 书籍信息
     */
    @Override
    public Book getBookByName(String bookName) {
        return null;
    }
}
