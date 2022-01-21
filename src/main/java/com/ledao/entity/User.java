package com.ledao.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * 用户实体类
 *
 * @author LeDao
 * @company
 * @create 2022-01-21 16:26
 */
@Data
@Entity
@Table(name = "t_user")
public class User {

    /**
     * 编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 用户名
     */
    @Column(name = "userName", length = 100)
    private String userName;
    /**
     * 密码
     */
    @Column(length = 100)
    private String password;
    /**
     * 用户类型,1代表管理员,2代表普通用户
     */
    private Integer type;
    /**
     * 用户状态,1代表正常,2代表被封禁
     */
    private Integer state;
    /**
     * 是否可借书
     */
    @Column(name = "isBorrow")
    private Integer isBorrow;
}
