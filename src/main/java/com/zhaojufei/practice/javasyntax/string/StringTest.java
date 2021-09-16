package com.zhaojufei.practice.javasyntax.string;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

public class StringTest {

    public static void main(String[] args) {

        List<String> list = Arrays.asList("node");

        Object o = JSON.parseObject(null, new TypeReference<Object>(){});

        System.out.println(o);
    }
}
