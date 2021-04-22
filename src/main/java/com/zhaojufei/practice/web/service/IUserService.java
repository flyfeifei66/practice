package com.zhaojufei.practice.web.service;

import com.zhaojufei.practice.web.entity.User;

import java.util.List;

public interface  IUserService {
    public List<User> listUser();

    public User queryById();
}
