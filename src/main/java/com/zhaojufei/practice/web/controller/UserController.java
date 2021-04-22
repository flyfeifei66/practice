package com.zhaojufei.practice.web.controller;

import com.zhaojufei.practice.web.entity.User;
import com.zhaojufei.practice.web.service.ICalService;
import com.zhaojufei.practice.web.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Demo class
 *
 * @author zhaojufei
 * @date 2019/2/27
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private List<ICalService> calServiceList;

    @GetMapping("list")
    @ResponseBody
    public List<User> listUser() {

        System.out.println("进入list");
        return userService.listUser();
    }

    @GetMapping("queryById")
    @ResponseBody
    public User queryById() {
        System.out.println("进入querybyid");
        return userService.queryById();
    }
}
