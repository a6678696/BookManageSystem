package com.ledao.mapper;

import com.ledao.entity.BookType;

import java.util.List;
import java.util.Map;

/**
 * 图书类别Mapper接口
 *
 * @author LeDao
 * @company
 * @create 2022-01-21 23:49
 */
public interface BookTypeMapper {

    /**
     * 分页条件查询图书类别
     *
     * @param map
     * @return
     */
    List<BookType> list(Map<String, Object> map);

    /**
     * 获取记录数
     *
     * @param map
     * @return
     */
    Long getCount(Map<String, Object> map);

    /**
     * 添加图书类别
     *
     * @param bookType
     * @return
     */
    int add(BookType bookType);

    /**
     * 修改图书类别
     *
     * @param bookType
     * @return
     */
    int update(BookType bookType);

    /**
     * 根据id获取图书类别
     *
     * @param id
     * @return
     */
    BookType findById(Integer id);

    /**
     * 根据id删除图书类别
     *
     * @param id
     * @return
     */
    int deleteById(Integer id);

    /**
     * 根据名称查找图书类别
     *
     * @param name
     * @return
     */
    BookType findByName(String name);
}
