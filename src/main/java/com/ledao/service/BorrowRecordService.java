package com.ledao.service;

import com.ledao.entity.BorrowRecord;

import java.util.List;
import java.util.Map;

/**
 * 借阅记录Service接口
 *
 * @author LeDao
 * @company
 * @create 2022-01-24 10:25
 */
public interface BorrowRecordService {

    /**
     * 分页条件查询借阅记录
     *
     * @param map
     * @return
     */
    List<BorrowRecord> list(Map<String, Object> map);

    /**
     * 获取记录数
     *
     * @param map
     * @return
     */
    Long getCount(Map<String, Object> map);

    /**
     * 添加借阅记录
     *
     * @param borrowRecord
     * @return
     */
    int add(BorrowRecord borrowRecord);

    /**
     * 修改借阅记录
     *
     * @param borrowRecord
     * @return
     */
    int update(BorrowRecord borrowRecord);

    /**
     * 根据id获取借阅记录
     *
     * @param id
     * @return
     */
    BorrowRecord findById(Integer id);

    /**
     * 根据id删除借阅记录
     *
     * @param id
     * @return
     */
    int deleteById(Integer id);
}
