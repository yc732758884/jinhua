package com.hzwc.intelligent.lock.model.bean;

/**
 * Created by Administrator on 2018/7/19.
 */

public class WebLocationWarningBean {
    //手动提交位置告警：{"lockNo": ,"locationWarn": } //locationWarn是1
    private String lockNo;

    public WebLocationWarningBean(String lockNo, int locationWarn) {
        this.lockNo = lockNo;
        this.locationWarn = locationWarn;
    }

    private int locationWarn;
}
