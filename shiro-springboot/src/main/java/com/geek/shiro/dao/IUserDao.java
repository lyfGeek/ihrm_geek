package com.geek.shiro.dao;

import com.geek.shiro.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 用户数据访问接口。
 */
public interface IUserDao extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {
    // 根据手机号（用户名）获取用户信息。
    User findByUsername(String name);
}
