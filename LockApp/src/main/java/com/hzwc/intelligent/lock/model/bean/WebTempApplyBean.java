package com.hzwc.intelligent.lock.model.bean;

/**
 * Created by Administrator on 2018/7/16.
 */

public class WebTempApplyBean {
    /*
申请临时开锁：{"lockNo": ,"isTempApply": } //isTempApply是1
*/

    private String lockNo;
    private int isTempApply;

    public WebTempApplyBean(String lockNo, int isTempApply) {
        this.lockNo = lockNo;
        this.isTempApply = isTempApply;
    }
}
