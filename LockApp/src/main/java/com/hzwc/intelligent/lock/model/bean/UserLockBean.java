package com.hzwc.intelligent.lock.model.bean;

import java.util.List;

/**
 * Created by mayn on 2018/7/14.
 */

public class UserLockBean {


    /**
     * msg : success
     * code : 0
     * InstallUserLocks : [{"lockId":9,"lockNo":"25555","installTime":"2018-07-13 18:18:32","cabinetId":5,"cabinetName":"柜子5","addr":"智慧e谷3","locationX":120.22501069269869,"locationY":30.21624690685839},{"lockId":23,"lockNo":"111","installTime":"2018-07-16 15:25:11","cabinetId":3,"cabinetName":"柜子3","addr":"智慧e谷1","locationX":120.2197669741643,"locationY":30.20775997857157}]
     */

    private String msg;
    private int code;
    private List<InstallUserLocksBean> InstallUserLocks;

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

    public List<InstallUserLocksBean> getInstallUserLocks() {
        return InstallUserLocks;
    }

    public void setInstallUserLocks(List<InstallUserLocksBean> InstallUserLocks) {
        this.InstallUserLocks = InstallUserLocks;
    }

    public static class InstallUserLocksBean {
        /**
         * lockId : 9
         * lockNo : 25555
         * installTime : 2018-07-13 18:18:32
         * cabinetId : 5
         * cabinetName : 柜子5
         * addr : 智慧e谷3
         * locationX : 120.22501069269869
         * locationY : 30.21624690685839
         */

        private int lockId;
        private String lockNo;
        private String installTime;
        private int cabinetId;
        private String cabinetName;
        private String addr;
        private double locationX;
        private double locationY;

        public int getLockId() {
            return lockId;
        }

        public void setLockId(int lockId) {
            this.lockId = lockId;
        }

        public String getLockNo() {
            return lockNo;
        }

        public void setLockNo(String lockNo) {
            this.lockNo = lockNo;
        }

        public String getInstallTime() {
            return installTime;
        }

        public void setInstallTime(String installTime) {
            this.installTime = installTime;
        }

        public int getCabinetId() {
            return cabinetId;
        }

        public void setCabinetId(int cabinetId) {
            this.cabinetId = cabinetId;
        }

        public String getCabinetName() {
            return cabinetName;
        }

        public void setCabinetName(String cabinetName) {
            this.cabinetName = cabinetName;
        }

        public String getAddr() {
            return addr;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }

        public double getLocationX() {
            return locationX;
        }

        public void setLocationX(double locationX) {
            this.locationX = locationX;
        }

        public double getLocationY() {
            return locationY;
        }

        public void setLocationY(double locationY) {
            this.locationY = locationY;
        }
    }

}
