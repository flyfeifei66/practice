package com.zhaojufei.practice.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/apollo")
public class ApolloTest {

    // @Value("${apollo.test}")
    private String test;

    // @Value("${hh}")
    private String hh;

    @GetMapping("/test")
    public String test() {
        return test + ", " + hh;
    }
}
