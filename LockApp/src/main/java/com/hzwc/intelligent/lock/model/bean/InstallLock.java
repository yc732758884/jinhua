package com.hzwc.intelligent.lock.model.bean;

/**
 * Created by mayn on 2018/7/16.
 */

public class InstallLock {
    private int cabinetId;
    private String lockNo;

    public int getCabinetId() {
        return cabinetId;
    }

    public void setCabinetId(int cabinetId) {
        this.cabinetId = cabinetId;
    }

    public String getLockNo() {
        return lockNo;
    }

    public void setLockNo(String lockNo) {
        this.lockNo = lockNo;
    }
}
