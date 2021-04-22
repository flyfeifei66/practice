package com.zhaojufei.practice.java8.book;

import com.google.common.collect.Lists;

import java.util.List;

public class Chaptar1 {

    public static void main(String[] args) {
        List<Integer> list = Lists.newArrayList();


        list.add(1);

        list.add(4);

        list.add(2);

        System.out.println(list);

        list.sort(Integer::compareTo);

        System.out.println(list);


    }

}
