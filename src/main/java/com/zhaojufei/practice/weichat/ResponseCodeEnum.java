package com.zhaojufei.practice.weichat;

/**
 * 返回Code枚举，建议统一在此处定义全局唯一异常码
 * 码的使用除了200代表成功，其他码应该尽量避开HTTP状态码，防止歧义。
 * 建议2XX代表警告，5XX代表错误
 *
 * @author zhaojufei
 */
public enum ResponseCodeEnum {


    /**
     * SUCCESS
     */
    SUCCESS("200", "SUCCESS"),

    /**
     * 其他门店在使用警告
     */
    WARN_OTHER_STORE("210", "此设备在其他门店使用过，在此店启用，其他店铺会禁用，是否继续？"),

    /**
     * 鉴权未通过
     */
    AUTH_NOT_PASS("520", "非法访问"),

    /**
     * 参数错误
     */
    PARAM_ERROR("521", "参数错误"),

    /**
     * 系统错误
     */
    SYSTEM_ERROR("522", "系统错误"),

    /**
     * 业务异常：如添加的数据重复等
     */
    BUSINESS_ERROR("523", "业务异常"),

    /**
     *
     */
    STORE_NOT_EXIST("524", "没有门店信息"),

    /**
     *
     */
    TENANT_NOT_EXIST("525", "没有商户信息"),

    /**
     *
     */
    APP_NOT_EXIST("526", "没有应用信息，请先调用心跳接口注册设备、应用。"),

    /**
     *
     */
    CAN_NOT_ENABLE("527", "设备最新状态不能启用，请刷新页面后重新选择。"),

    /**
     *
     */
    CAN_NOT_DISABLE("528", "设备最新状态为停用，无需再停用。"),

    /**
     *
     */
    CAN_NOT_DELETE("529", "设备最新状态有不能被删除的，请刷新页面后重新选择。"),

    /**
     *
     */
    BIND_EXIST("530", "该门店下已有该设备，请勿重复申请激活。"),

    /**
     *
     */
    CAN_NOT_AUDIT("531", "设备当前最新状态无法审批通过，请刷新页面后确认。"),

    /**
     *
     */
    CAN_NOT_REJECT("532", "设备当前最新状态无法审批不过，请刷新页面后确认。"),

    /**
     *
     */
    RECORD_EXIST("530", "该门店下已有该设备，无法补录。"),

    /**
     *
     */
    EQP_NOT_EXIST("531", "没有设备信息，请先调用心跳接口注册设备、应用。"),

    /**
     *
     */
    NO_LOG_INSERT("526", "上报的日志没有被记录，请确认设备和应用信息的正确性。"),

    /**
     *
     */
    FAIL_FIND_USER("527", "获取当前用户信息失败。"),

    /**
     * 未获取到分布式锁key
     */
    NOT_FIND_LOCKKEY("528", "未找到到分布式锁Key，请求处理失败。"),

    /**
     * 登录超时
     */
    LOGIN_TIME_OUT("529", "登录超时，请重新登录。"),

    /**
     * 未获取到分布式锁key
     */
    NOT_GET_LOCK("530", "操作等待超时，请稍后重试。"),

    /**
     * 日期逻辑错误
     */
    BEGIN_END_TIME_ERROR("531", "开始时间必须在结束时间之前。"),

    /**
     * 未获取到分布式锁key
     */
    NOT_GET_LOCK_TASK("532", "其他机器正在执行定时任务，本机不再执行"),

    /**
     *
     */
    BILL_TYPE_NOT_EXISTS("533", "业务类型不存在"),

    /**
     *
     */
    DEVELOPER_DUP("534", "业务类型不存在"),

    /**
     *
     */
    APP_HAVE_VERSION("535", "应用下已有版本，无法删除。"),

    /**
     *
     */
    VERSION_HAVE_EXISTS("536", "当前版本已存在，无法再次。"),

    /**
     *
     */
    APP_NOT_EXISTS("537", "应用下已有版本，无法删除。"),

    /**
     *
     */
    SEGMENT_TOO_MUCH("538", "版本号不合法：版本号段数过多"),

    /**
     *
     */
    DIGIT_TOO_LONG("539", "版本号不合法：版本号位数过多");

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
