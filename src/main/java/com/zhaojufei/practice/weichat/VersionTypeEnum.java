package com.zhaojufei.practice.weichat;

import lombok.Getter;

/**
 * 版本号枚举类
 *
 * @author zhaojufei
 */
@Getter
public enum VersionTypeEnum {

    APP_VERSION("app", 4, 3, "%03d"),
    OS_VERSION("os", 3, 5, "%05d");

    private String code;
    private int segment;
    private int digit;
    private String format;

    VersionTypeEnum(String code, int segment, int digit, String format) {
        this.code = code;
        this.segment = segment;
        this.digit = digit;
        this.format = format;
    }

    public static VersionTypeEnum getEnum(String code) {
        for (VersionTypeEnum t : VersionTypeEnum.values()) {
            if (t.getCode().equals(code)) {
                return t;
            }
        }
        return null;
    }

}
