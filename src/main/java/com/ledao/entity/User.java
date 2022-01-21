package com.ledao.entity;

import lombok.Data;

/**
 * 用户实体类
 *
 * @author LeDao
 * @company
 * @create 2022-01-21 16:26
 */
@Data
public class User {

    /**
     * 编号
     */
    private Integer id;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 密码
     */
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
    private Integer isBorrow;
}
