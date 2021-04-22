package com.zhaojufei.practice.API;

public interface MyInterface<T extends Exception> {

    void hello(T t);

    void hehe(T t);
}
