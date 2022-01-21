package com.ledao.service.impl;

import com.ledao.entity.BookType;
import com.ledao.mapper.BookTypeMapper;
import com.ledao.service.BookTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 图书类别Service接口实现类
 *
 * @author LeDao
 * @company
 * @create 2022-01-21 23:55
 */
@Service("bookTypeService")
public class BookTypeServiceImpl implements BookTypeService {

    @Resource
    private BookTypeMapper bookTypeMapper;

    @Override
    public List<BookType> list(Map<String, Object> map) {
        return bookTypeMapper.list(map);
    }

    @Override
    public Long getCount(Map<String, Object> map) {
        return bookTypeMapper.getCount(map);
    }

    @Override
    public int add(BookType bookType) {
        return bookTypeMapper.add(bookType);
    }

    @Override
    public int update(BookType bookType) {
        return bookTypeMapper.update(bookType);
    }

    @Override
    public BookType findById(Integer id) {
        return bookTypeMapper.findById(id);
    }

    @Override
    public int deleteById(Integer id) {
        return bookTypeMapper.deleteById(id);
    }

    @Override
    public BookType findByName(String name) {
        return bookTypeMapper.findByName(name);
    }
}
