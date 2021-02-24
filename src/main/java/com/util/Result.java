package com.util;

public class Result {

    private String code;

    private Object data;

    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public static Result success(){
        Result result = new Result();
        result.setCode("200");
        return result;
    }
    public static Result success(Object data){
        Result result = new Result();
        result.setCode("200");
        result.setData(data);
        return result;
    }
    public static Result fail(String msg){
        Result result = new Result();
        result.setCode("500");
        result.setMsg(msg);
        return result;
    }
}
