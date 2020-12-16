package com.hzwc.intelligent.lock.model.bean;

/**
 *
 * @author Administrator
 * @date 2017/8/10
 */

public class MessageBean {


    /**
     * code : 0
     * identifyCode : 3572
     * returnStatement : 发送成功！
     * register : 0    0表示未注册用户 1表示注册用户
     */

    private int code;
    private int identifyCode;
    private String returnStatement;
    private int register;

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
