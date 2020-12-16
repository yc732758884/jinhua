package com.hzwc.intelligent.lock.model.bean;

import java.io.Serializable;

/**
 * 2017-12-18 09:27:47
 * Anna
 * @author apple
 */

public class BaseBean<T> implements Serializable{

    private int code;

    private String msg;

    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
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
}
