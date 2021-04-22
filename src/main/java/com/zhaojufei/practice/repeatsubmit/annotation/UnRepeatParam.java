package com.zhaojufei.practice.repeatsubmit.annotation;

import java.lang.annotation.*;

/**
 * 仿重复提交参数注解
 */
@Target({ ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UnRepeatParam {

}
