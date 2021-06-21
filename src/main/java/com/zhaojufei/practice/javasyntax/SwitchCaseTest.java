package com.zhaojufei.practice.javasyntax;

public class SwitchCaseTest {
    public static void main(String[] args) {
        int score = 13;
        switch (score) {
        case 10:
        case 9:
            System.out.println("法拉利");
            break;
        case 8:
            System.out.println("宝马");
            break;
        case 7:
            System.out.println("大黄蜂");
            break;
        case 6:
            System.out.println("拖拉机");
            break;
        case 5:
        case 4:
        case 3:
        case 2:
        case 1:
        case 0:
            System.out.println("滚犊子");
            break;
        default:
            System.out.println("你的分数有误");
            break;
        }
    }
}
