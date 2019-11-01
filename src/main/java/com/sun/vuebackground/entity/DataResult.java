package com.sun.vuebackground.entity;

public class DataResult {
    private int code;
    private int count;//数据长度
    private Object data;//存储数据
    private String msg;//后台返回前台的信息

    public DataResult() {
        this.code = 0;
        this.count = 0;
        this.data = "";
        this.msg = "";
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
