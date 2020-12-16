package com.hzwc.intelligent.lock.model.bean;

/**
 * Created by anna on 2018/6/7.
 */

public class LoginBean {


    /**
     * msg : success
     * code : 0
     * userId : 3
     * token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIzIiwiaWF0IjoxNTMxNDQ5MzE4fQ.sMT7Jb7JcNZbcRUegTwV0IlyNK9CzO4B98abxF8Bvmo
     */

    private String msg;
    private int code;
    private int userId;
    private String token;
    private   int state;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
