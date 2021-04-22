package com.zhaojufei.practice.java8;


import java.util.Optional;

public class OptionalTest {


    public static void main(String[] args) {

        User user = new User();

        user.setName("赵举飞");

        String name = Optional.ofNullable(user).map(obj -> obj.getName()).orElse("No name");

        System.out.println(name);

        String s = null;

       Optional.ofNullable(s).ifPresent(e -> System.out.println(e));




    }
}

class User {
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}