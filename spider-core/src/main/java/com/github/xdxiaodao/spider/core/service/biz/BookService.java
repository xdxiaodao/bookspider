package com.github.xdxiaodao.spider.core.service.biz;

import com.github.xdxiaodao.spider.core.model.Book;

/**
 * Created with bookspider
 * User zhangmuzhao
 * Date 2017/8/8
 * Time 17:35
 * Desc
 */
public interface BookService {

    /**
     * 添加书籍
     * @param book 书籍信息
     * @return 新加书籍信息编号
     */
    public int addBook(Book book);

    /**
     * 根据书名查询书籍信息
     * @param bookName 书名
     * @return 书籍信息
     */
    public Book getBookByName(String bookName);
}
