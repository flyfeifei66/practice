package com.zhaojufei.practice.myfeature.repeatsubmit.common;

/**
 * 切面顺序常量类：值越小，切面会先执行
 * 定义新值设置一定的步长，给后面的留有一定空间，新增切面合适的位置可能在某些"空余"位置。
 * 如果order值不够用，可以重新定义切面order，但是不要改变order之前的先后顺序
 * 
 * @author zhaojufei
 */
public class AdviceOrder {

    /**
     * controller层打印日志
     */
    public static final int WEBLOG = 1;

    /**
     * 解析JWT，给DTO注入用户信息
     */
    public static final int JWT = 6;

    /**
     * 防重复提交（必须在JWT之后）
     */
    public static final int UN_REPEAT_SUBMIT = 11;

    /**
     * 数据库事务
     */
    public static final int DB_TRANSACTION = 99;

    /**
     * 版本控制
     */
    public static final int VCLOCK = 100;

}
