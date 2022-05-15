package com.ledao.service;

import com.ledao.entity.Book;

import java.util.List;
import java.util.Map;

/**
 * 图书Service接口
 *
 * @author LeDao
 * @company
 * @create 2022-01-22 0:44
 */
public interface BookService {

    /**
     * 分页条件查询图书
     *
     * @param map
     * @return
     */
    List<Book> list(Map<String, Object> map);

    /**
     * 获取记录数
     *
     * @param map
     * @return
     */
    Long getCount(Map<String, Object> map);

    /**
     * 添加图书
     *
     * @param book
     * @return
     */
    int add(Book book);

    /**
     * 修改图书
     *
     * @param book
     * @return
     */
    int update(Book book);

    /**
     * 根据id获取图书
     *
     * @param id
     * @return
     */
    Book findById(Integer id);

    /**
     * 根据id删除图书
     *
     * @param id
     * @return
     */
    int deleteById(Integer id);

    /**
     * 根据书号查询图书
     *
     * @param bookNumber
     * @return
     */
    Book findByBookNumber(String bookNumber);

    /**
     * 根据书名模糊查询图书获取id集合
     *
     * @param name
     * @return
     */
    List<Integer> selectIdByNameLike(String name);

    /**
     * 获取某个图书类别的图书数量
     *
     * @param bookTypeId
     * @return
     */
    Integer getCountByBookTypeId(Integer bookTypeId);

    /**
     * 根据书名查找
     *
     * @param name
     * @return
     */
    List<Book> findListByName(String name);
}
