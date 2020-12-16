package com.hzwc.intelligent.lock.model.bean;

/**
 * Created by Administrator on 2018/7/19.
 */

public class WebIsWarnBean {
    //手动提交其他告警：{"lockNo": ,"isWarn": } //isWarn是1
    private String lockNo;

    public WebIsWarnBean(String lockNo, int isWarn) {
        this.lockNo = lockNo;
        this.isWarn = isWarn;
    }

    private int isWarn;
}
