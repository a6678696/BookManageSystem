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
     * 借阅天数
     */
    private Integer day;
    /**
     * 当前状态,1代表第一次借阅,2代表续借,3代表已还书
     */
    private Integer state;
    /**
     * 借书人id
     */
    private Integer userId;
    /**
     * 图书id
     */
    private Integer bookId;
}
