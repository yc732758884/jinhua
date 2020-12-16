package com.hzwc.intelligent.lock.model.bean;

/**
 * Created by mayn on 2018/7/14.
 */

import java.util.List;

/**
 * 用户安装的所有的环网柜
 */
public class InstallAllRimBean {


    /**
     * msg : success
     * InstallUserCabinets : [{"cabinetId":467,"cabinetName":"sfsf","areaId":null,"addr":"88888","locationX":13333,"locationY":8888,"installTime":"2018-07-13 20:09:07"},{"cabinetId":468,"cabinetName":"dgdsg","areaId":null,"addr":"sgsg","locationX":455,"locationY":66,"installTime":"2018-07-13 20:31:41"}]
     * code : 0
     */

    private String msg;
    private int code;
    private List<InstallUserCabinetsBean> InstallUserCabinets;

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

    public List<InstallUserCabinetsBean> getInstallUserCabinets() {
        return InstallUserCabinets;
    }

    public void setInstallUserCabinets(List<InstallUserCabinetsBean> InstallUserCabinets) {
        this.InstallUserCabinets = InstallUserCabinets;
    }

    public static class InstallUserCabinetsBean {
        /**
         * cabinetId : 467
         * cabinetName : sfsf
         * areaId : null
         * addr : 88888
         * locationX : 13333
         * locationY : 8888
         * installTime : 2018-07-13 20:09:07
         */

        private int cabinetId;
        private String cabinetName;
        private int areaId;
        private String addr;
        private int locationX;
        private int locationY;
        private String installTime;

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

        public int getAreaId() {
            return areaId;
        }

        public void setAreaId(int areaId) {
            this.areaId = areaId;
        }

        public String getAddr() {
            return addr;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }

        public int getLocationX() {
            return locationX;
        }

        public void setLocationX(int locationX) {
            this.locationX = locationX;
        }

        public int getLocationY() {
            return locationY;
        }

        public void setLocationY(int locationY) {
            this.locationY = locationY;
        }

        public String getInstallTime() {
            return installTime;
        }

        public void setInstallTime(String installTime) {
            this.installTime = installTime;
        }
    }
}
