package com.zhaojufei.practice.weichat;

import java.math.BigDecimal;

/**
 * 自定义字符串处理类，优先使用apache common包的工具类
 * 工具类中没有的，组合使用工具类的API构建自定义的方法
 * 组合API也无法实现的，参考工具类的源码写自己的方法
 * 
 * @author zhaojufei
 */
public class DmStringUtil {

    public static void main(String[] args) {

        // System.out.println(String.format("%08d",12));

        System.out.println(version2Num("102.3568.4.3", VersionTypeEnum.APP_VERSION));
    }

    /**
     * 获取以空格符隔开的字符串的前缀，以第一个空白符为界)。
     * 
     * @param str
     * @return
     */
    public static String getPrefixByWhitespace(String str) {
        if (org.apache.commons.lang3.StringUtils.isNotBlank(str)) {
            int strLen = str.length();
            // 默认取到最后
            int index = strLen;
            for (int i = 0; i < strLen; ++i) {
                if (Character.isWhitespace(str.charAt(i))) {
                    index = i;
                    break;
                }
            }
            return str.substring(0, index);
        }

        return str;
    }

    /**
     * 将.隔开的版本号，转成纯数字形式，段数不够补充段数，每段位数不够补零
     * 
     * @param version 字符串形式版本号，例如 8.7.5.100
     * @param versionTypeEnum 版本枚举
     * @return
     */
    public static BigDecimal version2Num(String version, VersionTypeEnum versionTypeEnum) {
        String[] numArray = version.split("\\.");
        if (numArray.length > versionTypeEnum.getSegment()) {
            throw new BusinessException(ResponseCodeEnum.SEGMENT_TOO_MUCH);
        }

        StringBuilder numStr = new StringBuilder();
        for (String segNum : numArray) {
            if (segNum.length() > versionTypeEnum.getDigit()) {
                throw new BusinessException(ResponseCodeEnum.DIGIT_TOO_LONG);
            }
            String numTemp = String.format(versionTypeEnum.getFormat(), Integer.valueOf(segNum));
            numStr.append(numTemp);
        }

        int lessSegment = versionTypeEnum.getSegment() - numArray.length;

        if (lessSegment > 0) {
            for (int i = 0; i < lessSegment; i++) {
                numStr.append(String.format(versionTypeEnum.getFormat(), 0));
            }
        }
        return new BigDecimal(numStr.toString());
    }
}
