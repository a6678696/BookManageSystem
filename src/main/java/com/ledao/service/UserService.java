package com.ledao.service;

import com.ledao.entity.User;

import java.util.List;
import java.util.Map;

/**
 * 用户Service接口
 *
 * @author LeDao
 * @company
 * @create 2022-01-21 19:17
 */
public interface UserService {

    /**
     * 分页条件查询用户
     *
     * @param map
     * @return
     */
    List<User> list(Map<String, Object> map);

    /**
     * 获取记录数
     *
     * @param map
     * @return
     */
    Long getCount(Map<String, Object> map);

    /**
     * 添加用户
     *
     * @param user
     * @return
     */
    int add(User user);

    /**
     * 修改用户
     *
     * @param user
     * @return
     */
    int update(User user);

    /**
     * 根据用户名获取用户
     *
     * @param userName
     * @return
     */
    User findByUserName(String userName);

    /**
     * 根据id获取用户
     *
     * @param id
     * @return
     */
    User findById(Integer id);

    /**
     * 根据id删除用户
     *
     * @param id
     * @return
     */
    int deleteById(Integer id);
}
