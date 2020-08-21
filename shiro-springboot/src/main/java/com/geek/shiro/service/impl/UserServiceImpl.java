package com.geek.shiro.service.impl;

import com.geek.shiro.dao.IUserDao;
import com.geek.shiro.domain.User;
import com.geek.shiro.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserDao userDao;

    @Override
    public User findByName(String name) {
        return userDao.findByUsername(name);
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }
}
