package com.hzwc.intelligent.lock.model.bean;

/**
 * Created by Administrator on 2018/7/16.
 */

public class UnlocksBean {

    /**
     * msg : success
     * code : 0
     * unlock : {"lockId":1,"lockNo":"1202690000b7b20c","cabinetName":"网策1号机柜","power":100,"password":"000000","lockState":1}
     */

    private String msg;
    private int code;
    private UnlockBean unlock;

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

    public UnlockBean getUnlock() {
        return unlock;
    }

    public void setUnlock(UnlockBean unlock) {
        this.unlock = unlock;
    }

    public static class UnlockBean {
        /**
         * lockId : 1
         * lockNo : 1202690000b7b20c
         * cabinetName : 网策1号机柜
         * power : 100
         * password : 000000
         * lockState : 1
         */

        private int lockId;
        private String lockNo;
        private String cabinetName;
        private int power;
        private String password;
        private int lockState;
        private String lockCode;

        private String keyCode;
        private  int isCloseLock;

        public int getIsCloseLock() {
            return isCloseLock;
        }

        public void setIsCloseLock(int isCloseLock) {
            this.isCloseLock = isCloseLock;
        }

        public String getKeyCode() {
            return keyCode;
        }

        public void setKeyCode(String keyCode) {
            this.keyCode = keyCode;
        }

        public String getLockCode() {
            return lockCode;
        }

        public void setLockCode(String lockCode) {
            this.lockCode = lockCode;
        }

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

        public String getCabinetName() {
            return cabinetName;
        }

        public void setCabinetName(String cabinetName) {
            this.cabinetName = cabinetName;
        }

        public int getPower() {
            return power;
        }

        public void setPower(int power) {
            this.power = power;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public int getLockState() {
            return lockState;
        }

        public void setLockState(int lockState) {
            this.lockState = lockState;
        }
    }
}
