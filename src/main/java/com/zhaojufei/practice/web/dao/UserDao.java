package com.zhaojufei.practice.web.dao;

import com.zhaojufei.practice.web.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserDao {

    public List<User> listUser();

    public User queryById();
}

