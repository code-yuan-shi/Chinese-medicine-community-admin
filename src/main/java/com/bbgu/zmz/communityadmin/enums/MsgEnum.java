package com.bbgu.zmz.communityadmin.enums;


public enum MsgEnum {

    OK(0,""),
    COMMON_USER(0,"社区用户"),
    ALLOWLIMIT(1,"权限不足！"),
    DEL_SUCCESS(0,"删除成功"),
    ADD_SUCCESS(0,"添加成功"),
    ADD_FAILE(1,"添加失败"),
    UPDATE_SUCCESS(0,"更新成功"),
    UPLOAD_SUCCESS(0,"上传成功"),
    UPLOAD_FAILE(0,"上传失败"),
    USER_EXIT(1,"用户已存在"),
    EMAIL_EXIT(1,"邮箱已存在"),
    OLD_NEW_SAME(1,"不能与原密码相同"),
    OLD_PWD_INCORRECT(1,"原密码错误"),
    CODE_INCORRECT(1,"验证码错误！"),
    PWD_ATYPISM(1,"两次输入密码不一致"),
    USER_PWD_INCORRECT(1,"用户名或者密码错误！"),
    LOGIN_SUCCESS(0,"登录成功"),

    ;
    private int code;
    private String message;

    MsgEnum(int code, String message) {
        this.message = message;
        this.code = code;
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
