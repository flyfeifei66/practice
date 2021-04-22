package com.zhaojufei.practice.repeatsubmit.common;

import java.io.Serializable;
import java.util.Objects;

/**
 * 统一返回
 *
 * @author scl
 * @date 2018/12/22
 */
public class MessageBody<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private String code;
    private Boolean success;
    private int errorCode;
    private String errorInfo;
    private T data;
    private String message;
    //中台返回是否成功用的
    private String msg;
    private String stackTrace;

    public MessageBody() {
    }

    public Boolean getSuccess() {
        return this.success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorInfo() {
        return this.errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStackTrace() {
        return this.stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static MessageBody getMessageBody(boolean success, Object data) {
        MessageBody body = new MessageBody();
        body.setCode("200");
        body.setData(data);
        body.setSuccess(success);
        return body;
    }

    public static MessageBody getMessageBody(boolean success, Object data, String message) {
        MessageBody body = new MessageBody();
        body.setCode("200");
        body.setData(data);
        body.setMessage(message);
        body.setSuccess(success);
        return body;
    }

    public static MessageBody getInfoMessageBody(boolean success, String message) {
        return getMessageBody(success, null, message);
    }

    public static MessageBody getErrorMessageBody(String errorInfo) {
        MessageBody body = getMessageBody(false);
        body.setErrorInfo(errorInfo);
        body.setMessage(errorInfo);
        return body;
    }

    public static MessageBody getErrorMessageBody(String errorInfo, int errorCode) {
        MessageBody body = getMessageBody(false);
        body.setErrorInfo(errorInfo);
        body.setErrorCode(errorCode);
        return body;
    }

    public static MessageBody getSytemErrorMessageBody(String errorInfo) {
        MessageBody body = new MessageBody();
        body.setSuccess(false);
        body.setCode("500");
        body.setErrorInfo(errorInfo);
        return body;
    }

    public static MessageBody getMessageBody(boolean success) {
        MessageBody body = new MessageBody();
        body.setCode("200");
        body.setSuccess(Boolean.valueOf(success));
        return body;
    }

    public static MessageBody getMessageBody(boolean success, int code) {
        MessageBody body = new MessageBody();
        body.setCode(Objects.toString(Integer.valueOf(code)));
        body.setSuccess(Boolean.valueOf(success));
        return body;
    }

    public static MessageBody getMessageBody(Object data) {
        MessageBody body = new MessageBody();
        body.setCode("200");
        body.setData(data);
        body.setSuccess(Boolean.valueOf(true));
        return body;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static MessageBody getInsideError() {
        MessageBody body = new MessageBody();
        body.setCode("200");
        body.setSuccess(false);
        body.setErrorCode(10011);
        body.setErrorInfo("系统繁忙，请稍后重试！");
        body.setMessage("系统繁忙，请稍后重试！");
        return body;
    }

    public static MessageBody getMsgSuccc() {
        MessageBody body = new MessageBody();
        body.setCode("200");
        body.setSuccess(true);
        body.setErrorCode(2);
        body.setErrorInfo("服务成功！");
        body.setMessage("服务成功！");
        return body;
    }

    public static MessageBody getCreditMessageBody(boolean success, int errorCode, String message, Object data) {
        MessageBody body = getMessageBody(success, data, message);
        body.setErrorCode(errorCode);
        body.setErrorInfo(message);
        return body;
    }

    /**
     * 服务间接口调用无权限返回体
     *
     * @return
     */
    public static MessageBody getAccessErrorForApi() {
        MessageBody body = getMessageBody(false);
        body.setErrorInfo("no access");
        body.setMessage("no access");
        body.setErrorCode(403);
        body.setCode("403");
        return body;
    }
}
