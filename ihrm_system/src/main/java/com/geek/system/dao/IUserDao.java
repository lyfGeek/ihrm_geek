package com.geek.system.dao;

import com.geek.domain.system.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IUserDao extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {

    User findByMobile(String mobile);
}
