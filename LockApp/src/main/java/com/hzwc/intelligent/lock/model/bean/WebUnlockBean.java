package com.hzwc.intelligent.lock.model.bean;

/**
 * Created by Administrator on 2018/7/16.
 */

public class WebUnlockBean {
    /*
websocket发送：开/关锁：{"lockNo": ,"flag": }//关锁时flag是0，开锁时flag是1


*/

    private String lockNo;
    private int flag;//关锁时flag是0，开锁时

    public WebUnlockBean(String lockNo, int flag) {
        this.lockNo = lockNo;
        this.flag = flag;
    }
}
