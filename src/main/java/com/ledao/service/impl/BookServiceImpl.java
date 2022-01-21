package com.ledao.service.impl;

import com.ledao.entity.Book;
import com.ledao.mapper.BookMapper;
import com.ledao.service.BookService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 图书Service接口实现类
 *
 * @author LeDao
 * @company
 * @create 2022-01-22 0:45
 */
@Service("bookService")
public class BookServiceImpl implements BookService {

    @Resource
    private BookMapper bookMapper;

    @Override
    public List<Book> list(Map<String, Object> map) {
        return bookMapper.list(map);
    }

    @Override
    public Long getCount(Map<String, Object> map) {
        return bookMapper.getCount(map);
    }

    @Override
    public int add(Book book) {
        return bookMapper.add(book);
    }

    @Override
    public int update(Book book) {
        return bookMapper.update(book);
    }

    @Override
    public Book findById(Integer id) {
        return bookMapper.findById(id);
    }

    @Override
    public int deleteById(Integer id) {
        return bookMapper.deleteById(id);
    }

    @Override
    public Book findByBookNumber(String bookNumber) {
        return bookMapper.findByBookNumber(bookNumber);
    }
}
