package com.zhaojufei.practice.java8.bean;


import lombok.Getter;

@Getter
public class Person {
    private String id;
    private String name;

    public Person(String id,String name){
        this.id = id;
        this.name = name;
    }
}
