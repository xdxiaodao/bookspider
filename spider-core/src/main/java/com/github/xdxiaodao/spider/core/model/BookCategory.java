package com.github.xdxiaodao.spider.core.model;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Created by xiaohuilang on 17/5/13.
 */
public class BookCategory {

    private String categoryName;

    private Map<String, Book> bookMap = Maps.newConcurrentMap();

    public static BookCategory newBookCategory(String categoryName) {
        BookCategory bookCategory = new BookCategory();
        bookCategory.setCategoryName(categoryName);
        return bookCategory;
    }

    public void addBook(Book book) {

    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Map<String, Book> getBookMap() {
        return bookMap;
    }

    public void setBookMap(Map<String, Book> bookMap) {
        this.bookMap = bookMap;
    }
}
