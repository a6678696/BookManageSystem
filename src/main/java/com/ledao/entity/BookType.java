package com.ledao.entity;

import lombok.Data;

/**
 * 图书类别实体类
 *
 * @author LeDao
 * @company
 * @create 2022-01-21 23:46
 */
@Data
public class BookType {

    /**
     * 编号
     */
    private Integer id;
    /**
     * 名称
     */
    private String name;
    /**
     * 图书数量
     */
    private Integer num;
}
