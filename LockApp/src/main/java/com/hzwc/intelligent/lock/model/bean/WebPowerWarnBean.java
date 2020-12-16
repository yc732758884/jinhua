package com.hzwc.intelligent.lock.model.bean;

/**
 * Created by Administrator on 2018/7/19.
 */

public class WebPowerWarnBean {
    //   手动提交欠压告警：{"lockNo": ,"powerWarn": } //powerWarn是1
    private String lockNo;

    public WebPowerWarnBean(String lockNo, int powerWarn) {
        this.lockNo = lockNo;
        this.powerWarn = powerWarn;
    }

    private int powerWarn;
}
