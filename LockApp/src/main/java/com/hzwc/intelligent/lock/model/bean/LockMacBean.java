package com.hzwc.intelligent.lock.model.bean;

/**
 * Created by mayn on 2018/8/1.
 */

public class LockMacBean {

    /**
     * msg : success
     * code : 0
     * lockNo :
     */

    private String msg;
    private int code;
    private String lockNo;

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

    public String getLockNo() {
        return lockNo;
    }

    public void setLockNo(String lockNo) {
        this.lockNo = lockNo;
    }
}
