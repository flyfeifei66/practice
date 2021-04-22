package com.zhaojufei.practice.repeatsubmit.common;


/**
 * @author scl
 * @param: 返回码
 * @date 2018/12/26 18:55
 */
public enum ResponseCodeEnum {

    /**
     * SUCCESS
     */
    SUCCESS("200", "SUCCESS"),
    /**
     * 系统错误
     */
    SYSTEM_ERROR("500", "系统错误"),
    /**
     * 业务异常：如添加的数据重复等
     */
    BUSINESS_ERROR("501", "业务异常"),
    /**
     * 暂无数据
     */
    EMPTY_DATA("", "暂无数据"),
    /**
     * 接口不存在
     */
    NOT_FOUND("404", "接口不存在"),
    /**
     * 请求无效
     */
    FAIL("400", "请求无效"),
    /**
     * 参数错误
     */
    PARAM_ERROR("1", "参数错误"),
    /**
     * 商户id不能为空
     */
    TENANT_ID_NOT_EXIST("301000", "商户id不能为空"),
    /**
     * id不能为空
     */
    ID_NOT_EXIST("301001", "id不能为空"),
    /**
     * 添加数据失败
     */
    ADD_FIALD("301002", "添加数据失败"),
    /**
     * 更新数据失败
     */
    UPDATE_FIALD("301003", "更新数据失败"),
    /**
     * 删除数据失败
     */
    DELETE_FIALD("301004", "删除数据失败"),
    /**
     * 操作失败
     */
    AUDIT_FIALD("301005", "操作失败");
    /**
     * 状态码
     */
    private final String code;
    /**
     * 描述信息
     */
    private final String desc;

    ResponseCodeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
