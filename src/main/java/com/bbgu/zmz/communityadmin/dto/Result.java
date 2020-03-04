package com.bbgu.zmz.communityadmin.dto;

import com.bbgu.zmz.communityadmin.enums.MsgEnum;

public class Result {

    private int code;
    private String msg;
    private Object data;
    private Long count;

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    // 几个构造方法
    public Result() {
    }

    public Result(Object data) {
        this.data = data;
    }

    public Result(MsgEnum msgEnum) {
        this.msg = msgEnum.getMessage();
        this.code = msgEnum.getCode();
    }

    public Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }


    public Result ok(MsgEnum msgEnum){
        return  new Result(msgEnum.getCode(), msgEnum.getMessage());
    }

    public Result ok(Object data) {
        return new Result(data);
    }

    public Result ok(MsgEnum msgEnum,Object data){
        return  new Result(msgEnum.getCode(), msgEnum.getMessage(),data);
    }


    public Result error(MsgEnum msgEnum) {
        return new Result(msgEnum.getCode(), msgEnum.getMessage());
    }

    public Result error(Object data) {
        return new Result(data);
    }

    public Result error(MsgEnum msgEnum,Object data) {
        return new Result(msgEnum.getCode(), msgEnum.getMessage(),data);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
