package com.zhaojufei.practice.repeatsubmit.common;

/**
 * 存放redis key的类，所有的key均由静态方法生成
 * 注意：key的不同层级之间用:进行分割，如
 * choice-credit:user:1111
 */
public class RedisKey {
    private static String PREFIX = "scm-credit:";

    /**
     * 获取信用额度控制锁的Key。
     *
     * @param tenantId
     * @return
     */
    public static String getCreditLimitControlLockKey(String tenantId, String targetId) {
        return PREFIX + "creditLimitControlLockKey:" + tenantId + ":" + targetId;
    }

    /**
     * 获取信用用户控制锁的key
     *
     * @param tenantId
     * @param customerId
     * @return
     */
    public static String getCreditArchiveControlLockKey(String tenantId, String customerId) {
        return PREFIX + "creditArchiveControlLockKey:" + tenantId + ":" + customerId;
    }

    /**
     * 信用对象-期初单控制锁 ，用于控制添加期初单（防止几个人同时添加一个实体，导致重复）
     *
     * @param tenantId
     * @param targetId
     * @return
     */
    public static String getCreditLimitBeginTargetControlLockKey(String tenantId, String targetId) {
        return PREFIX + "creditLimitBeginTagrgtControlLockKey:" + tenantId + ":" + targetId;
    }

    /**
     * 获取期初单控制锁，用于期初单操作
     *
     * @param tenantId
     * @param id 期初单id
     * @return
     */
    public static String getCreditLimitBeginControlLockKey(String tenantId, String id) {
        return PREFIX + "creditLimitBeginControlLockKey:" + tenantId + ":" + id;
    }

    /**
     * 获取调整单控制锁，用于调整单操作
     *
     * @param tenantId
     * @param adjId
     * @return
     */
    public static String getCreditUsedLimitAdjControlLockKey(String tenantId, String adjId) {
        return PREFIX + "creditUsedLimitAdjControlLockKey:" + tenantId + ":" + adjId;
    }

    /**
     * 获取缓存当前用户信息的key
     *
     * @param tenantId
     * @param userId
     * @return
     */
    public static String getCurrentUserInfoKey(String tenantId, String userId) {
        return PREFIX + "userInfo:" + tenantId + ":" + userId;
    }

    /**
     * 获取用户form表单操作分布式锁key
     */
    public static String getUserFormOpsLockKey(String tenantId, String userId) {
        return PREFIX + "userFormOps:" + tenantId + ":"  + userId;
    }

    /**
     * 获取用户form表单操作TokenMap分布式锁key
     */
    public static String getUserFormOpsTokenMapLockKey(String tenantId, String userId) {
        return PREFIX + "UserFormOpsTokenMap:" + tenantId + ":"  + userId;
    }

    /**
     * 获取用户form表单操作TokenMap分布式锁key
     */
    public static String getUserFormOpsIsLockedLockKey(String tenantId, String userId) {
        return PREFIX + "UserFormOpsIsLocked:" + tenantId + ":"  + userId;
    }

}
