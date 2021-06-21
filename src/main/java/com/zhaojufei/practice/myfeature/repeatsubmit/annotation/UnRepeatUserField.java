package com.zhaojufei.practice.myfeature.repeatsubmit.annotation;

import java.lang.annotation.*;

/**
 * 
 * 防重复提交user字段注解
 * 使用了该注解的字段将会被视作userId
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UnRepeatUserField {

}
