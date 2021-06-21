package com.zhaojufei.practice.myfeature.repeatsubmit.common;

/**
 * usedfor 锁执行器
 * Created by javahao on 2018/3/30
 *
 * @author JavaHao
 */
@FunctionalInterface
public interface Executor<T> {
    /**
     * 执行器执行内容
     * @return 执行结果
     * @throws Exception 异常
     */
    T exec() throws Exception;
}
