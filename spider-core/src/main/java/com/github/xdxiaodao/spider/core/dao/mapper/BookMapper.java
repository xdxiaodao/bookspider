package com.github.xdxiaodao.spider.core.dao.mapper;

import com.github.xdxiaodao.spider.core.model.po.Book;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

@Component
public interface BookMapper extends Mapper<Book> {
}