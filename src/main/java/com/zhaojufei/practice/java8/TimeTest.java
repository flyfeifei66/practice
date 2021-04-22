package com.zhaojufei.practice.java8;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;

public class TimeTest {
    public static void main(String[] args) {
        LocalDate localDate = LocalDate.now();

        System.out.println(localDate.toString());

        LocalDate localDate1 = LocalDate.of(2018,12,2);

        System.out.println(localDate1.toString());

        LocalDate localDate2 = LocalDate.parse("2018-01-30");

        System.out.println(localDate2);

        LocalDate firstDayOfMonth = localDate.with(TemporalAdjusters.lastDayOfYear());

        System.out.println(firstDayOfMonth);

        LocalDate secondDayOfMonth = localDate.withDayOfMonth(22);

        System.out.println(secondDayOfMonth);

        LocalDate lastDayOfMonth = localDate.with(TemporalAdjusters.lastDayOfMonth());


        System.out.println( lastDayOfMonth.lengthOfMonth());


        // 1. 获取当前时间，包含毫秒数 -----打印输出----- 21:03:26.315
        LocalTime localTime = LocalTime.now();
        System.out.println(localTime.toString());

        // 2. 构建时间 -----打印输出----- 12:15:30
        LocalTime localTime1 = LocalTime.of(12, 15, 30);
        System.out.println(localTime1);

        // 3. 获取当前时间，不包含毫秒数 -----打印输出----- 21:01:56
        LocalTime localTime2 = localTime.withNano(0);
        System.out.println(localTime2);

        // 4. 字符串转为时间，还可以有其他格式，比如12:15, 12:15:23.233
        // -----打印输出----- 12:15:30
        LocalTime localTime3 = LocalTime.parse("12:15:30");
        System.out.println(localTime3);

        System.out.println(localTime3.withHour(10));

    }
}
