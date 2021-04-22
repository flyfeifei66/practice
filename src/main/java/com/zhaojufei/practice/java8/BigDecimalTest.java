package com.zhaojufei.practice.java8;

import java.math.BigDecimal;

/**
 * @author zhaojufei
 * @description
 * @Date 2019-03-29
 **/
public class BigDecimalTest {
    public static void main(String[] args) {

        BigDecimal a = new BigDecimal("10");

        BigDecimal b = new BigDecimal("3");

        BigDecimal c = a.divide(b,2, BigDecimal.ROUND_HALF_UP);

        System.out.println(c);


        System.out.println(a.add(null));

    }
}
