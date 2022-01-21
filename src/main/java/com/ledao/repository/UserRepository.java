package com.ledao.repository;

import com.ledao.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 用户Repository接口
 *
 * @author LeDao
 * @company
 * @create 2022-01-21 17:01
 */
public interface UserRepository extends JpaRepository<User, Long> {
}
