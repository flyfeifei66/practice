package com.zhaojufei.practice.java8;

import com.zhaojufei.practice.java8.bean.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zhaojufei
 * @description
 * @Date 2019-03-29
 **/
public class StreamTest2 {

    public static void main(String[] args) {

        // 声明一个List集合
        List<Person> list = new ArrayList();

       // list.add(new Person("1001", "小A"));
      //  list.add(new Person("1002", "小B"));
      //  list.add(new Person("1003", "小C"));

        System.out.println(list);

        // 将list转换map
        Map<String, String> map = list.stream().collect(Collectors.toMap(Person::getId, Person::getName));

        System.out.println(map);


    }

}
