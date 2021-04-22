package com.zhaojufei.practice.repeatsubmit.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 仿重复提交方法注解
 * @author zhaojufei
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)

/**
 * 限流注解
 */
public @interface UnRepeatSubmit {

}
