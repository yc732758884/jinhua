package com.hzwc.intelligent.lock.model.bean;

/**
 * Created by Administrator on 2018/7/22.
 */

public class RegisterBean {

    /**
     * msg : success
     * code : 0
     * identifyCode : 5383
     * returnStatement : 发送成功！
     * register : 0
     */

    private String msg;
    private int code;
    private int identifyCode;
    private String returnStatement;
    private int register;

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

    public int getIdentifyCode() {
        return identifyCode;
    }

    public void setIdentifyCode(int identifyCode) {
        this.identifyCode = identifyCode;
    }

    public String getReturnStatement() {
        return returnStatement;
    }

    public void setReturnStatement(String returnStatement) {
        this.returnStatement = returnStatement;
    }

    public int getRegister() {
        return register;
    }

    public void setRegister(int register) {
        this.register = register;
    }
}
