package com.geek.shiro.service;

import com.geek.shiro.domain.User;

import java.util.List;

public interface IUserService {

    User findByName(String name);

    List<User> findAll();
}
