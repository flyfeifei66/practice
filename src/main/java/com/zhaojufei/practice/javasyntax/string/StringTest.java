package com.zhaojufei.practice.javasyntax.string;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class StringTest {

    public static void main(String[] args) {

        String s = "asd_1231_hehe";
        System.out.println(s.substring(0,s.lastIndexOf("_")));

        List<String> list = Lists.newArrayList();

        System.out.println(StringUtils.join(list,","));

        String thirdSkuId = "123";

        System.out.println(Long.parseLong(thirdSkuId) == 123L);



    }
}
