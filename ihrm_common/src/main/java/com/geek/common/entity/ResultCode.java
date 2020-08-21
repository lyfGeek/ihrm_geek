package com.geek.common.entity;

public enum ResultCode {

    SUCCESS(true, 10000, "操作成功。"),
    FAIL(false, 10001, "操作失败。"),
    UNAUTHENTICATED(false, 10002, "您还未登录。"),
    UNAUTHENRISE(false, 10003, "权限不足。"),
    SERVER_ERROR(false, 99999, "抱歉，系统繁忙，请稍后重试。"),

    // 用户操作返回码。
    MOBILE_OR_PASSWORD_ERROR(false, 20001, "用户名或密码错误。");

    // 操作是否成功。
    boolean success;
    // 操作代码。
    int code;
    // 提示信息。
    String message;

    ResultCode(boolean success, int code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
