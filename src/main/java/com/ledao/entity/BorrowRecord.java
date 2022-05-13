package com.ledao.entity;

import lombok.Data;

import java.util.Date;

/**
 * 借阅记录实体类
 *
 * @author LeDao
 * @company
 * @create 2022-01-22 2:09
 */
@Data
public class BorrowRecord {

    /**
     * 编号
     */
    private Integer id;
    /**
     * 借阅时间
     */
    private Date time;
    /**
     * 归还时间
     */
    private Date returnTime;
    /**
     * 借阅天数
     */
    private Integer day;
    /**
     * 当前状态,1代表正在借阅,2代表已还书,3代表逾期
     */
    private Integer state;
    /**
     * 借书人id
     */
    private Integer userId;
    /**
     * 借书人用户名
     */
    private String userName;
    /**
     * 图书id
     */
    private Integer bookId;
    /**
     * 图书名称，用于模糊搜索
     */
    private String bookName;
    /**
     * 借阅的书籍图片
     */
    private String imageName;
}
