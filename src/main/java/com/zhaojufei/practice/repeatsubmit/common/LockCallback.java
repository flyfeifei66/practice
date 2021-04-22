package com.zhaojufei.practice.repeatsubmit.common;


/**
 * 加Redis锁成功/失败后的执行类
 */
public abstract class LockCallback {

    /**
     * 加锁成功后执行方法。
     *
     * @return
     */
    public abstract Object success();

    /**
     * 默认加锁失败时执行方法。
     *
     * @return
     */
    public Object fail(){
        throw new BusinessException("未获取到分布式锁！");
    }
}
