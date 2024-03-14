package com.myblog.mangojuice.model;

public class ResModel<T> {

    private Integer code;
    private String msg;
    private T data;

//    public ResModel(Integer code, String msg){
//        Code = code;
//        Msg = msg;
//    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }


}
