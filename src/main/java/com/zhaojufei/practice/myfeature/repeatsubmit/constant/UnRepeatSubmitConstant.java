package com.zhaojufei.practice.myfeature.repeatsubmit.constant;

import org.springframework.beans.factory.annotation.Value;

public class UnRepeatSubmitConstant {
    /**
     * token存活时间（秒）
     */
    @Value("${credit.formtoken.expiretime}")
    public static int TOKEN_EXPIRE_TIME = 30 * 60;

    /**
     * token数量超出后等待时间（秒）
     */
    @Value("${credit.formtoken.waitime}")
    public static int TOKEN_OUTNUMBER_WAITTIME = 3 * 60;

    /**
     * 让用户等待一段时间的token累积数量阈值，这个值小于等于1没有意义。
     * 这个值用来防止用户恶意刷Token，防止撑爆redis，但是为了照顾用户体验，这个值可以设置的大点。
     */
    @Value("${credit.formtoken.maxnum}")
    public static int MAX_TOKEN_NUM = 3;
}
