package com.zhaojufei.practice.myfeature.repeatsubmit.annotation;

import java.lang.annotation.*;

/**
 * 防重复提交TenantId字段注解
 * 使用了该注解的字段将会被视作tenantId
 */
@Target({ ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UnRepeatTenantIdField {

    String value() default "tenant_id";
}
