package com.ledao.entity;

import lombok.Data;

/**
 * 图书实体类
 *
 * @author LeDao
 * @company
 * @create 2022-01-22 0:31
 */
@Data
public class Book {

    /**
     * 编号
     */
    private Integer id;
    /**
     * 名称
     */
    private String name;
    /**
     * 描述
     */
    private String bookDescribe;
    /**
     * 图书类别id
     */
    private Integer bookTypeId;
    /**
     * 图书类别名称
     */
    private String bookTypeName;
    /**
     * 书号
     */
    private String bookNumber;
    /**
     * 状态,1代表可借阅,2代表已借出,3代表不可借出(由管理员设置)
     */
    private Integer state;
    /**
     * 图书位置
     */
    private String location;
}
