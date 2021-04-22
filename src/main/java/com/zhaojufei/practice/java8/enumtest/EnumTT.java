package com.zhaojufei.practice.java8.enumtest;

public class EnumTT {
    public static void main(String[] args) {
        String regex = "1[38]\\d{9}";//定义手机好规则
        boolean flag = "1388990683".matches(regex);//判断功能
        System.out.println("flag:"+flag);

    }
}
