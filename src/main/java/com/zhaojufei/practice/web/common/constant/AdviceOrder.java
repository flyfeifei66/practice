package com.zhaojufei.practice.web.common.constant;

/**
 * 切面顺序常量类：值越小，切面会先执行
 * 定义新值设置一定的步长，给后面的留有一定空间，新增切面合适的位置可能在某些"空余"位置。
 * 如果order值不够用，可以重新定义切面order，但是不要改变order之前的先后顺序
 * 
 * @author zhaojufei
 */
public class AdviceOrder {

    /**
     * 参数校验
     */
    public static final int PARAM_VALIDATE = 1;

}
