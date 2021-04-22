package com.zhaojufei.practice.web.service.impl;

import com.zhaojufei.practice.web.dao.UserDao;
import com.zhaojufei.practice.web.entity.User;
import com.zhaojufei.practice.web.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserDao userDao;

    @Override
    public List<User> listUser() {
        return userDao.listUser();
    }

    @Override
    public User queryById() {
        return userDao.queryById();
    }


}
