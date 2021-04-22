package com.zhaojufei.practice.API;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class API {
    public static void main(String[] args) {
        Object rule = new Object();

        System.out.println(JSON.toJSONString(rule, SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullNumberAsZero, SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.WriteNullBooleanAsFalse));

    }
}
