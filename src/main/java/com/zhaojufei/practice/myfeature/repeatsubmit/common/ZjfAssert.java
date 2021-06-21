package com.zhaojufei.practice.myfeature.repeatsubmit.common;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.Collection;



/**
 * 断言工具类
 *
 * @author zhaojufei
 * @since 2019-6-21 16:45
 */
public class ZjfAssert {

    /**
     * 字符串不为空断言
     * 
     * @param para
     * @param message
     */
    public static void notBlank(String para, String message) {
        if (StringUtils.isBlank(para)) {
            throw new BusinessException(message);
        }
    }

    /**
     * 集合不为空断言
     * 
     * @param collection
     * @param message
     */
    public static void notEmpty(Collection<?> collection, String message) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new BusinessException(message);
        }
    }

    /**
     * 不为null断言
     * 
     * @param object
     * @param message
     */
    public static void notNull(Object object, String message) {
        if (object == null) {
            throw new BusinessException(message);
        }
    }

    /**
     * 判断给定值知否符合给定的正则表达式
     */
    public static void regex(String value, String regex, String message) {
        if (!value.matches(regex)) {
            throw new BusinessException(message);
        }
    }

}
