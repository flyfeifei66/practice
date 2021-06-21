package com.zhaojufei.practice.myfeature.repeatsubmit.common;

import java.util.UUID;

/**
 *  uuid生成
 *  @author scl
 */
public class UUIDGenerator {

    public static String getUUID(){
        return UUID.randomUUID().toString();
    }
}
