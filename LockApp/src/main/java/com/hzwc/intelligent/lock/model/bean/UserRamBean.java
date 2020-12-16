package com.hzwc.intelligent.lock.model.bean;

import java.util.List;

/**
 * Created by mayn on 2018/7/16.
 */

public class UserRamBean {


    /**
     * msg : success
     * InstallUserCabinets : [{"cabinetId":469,"cabinetName":"an","areaId":null,"addr":null,"locationX":39.92421106207774,"locationY":116.39786327434547,"installTime":"2018-07-14 10:48:54"},{"cabinetId":470,"cabinetName":"an","areaId":null,"addr":null,"locationX":116.39786327434547,"locationY":39.92421106207774,"installTime":"2018-07-14 13:42:04"},{"cabinetId":471,"cabinetName":"","areaId":null,"addr":"浙江省杭州市滨江区西兴街道智慧e谷A座智慧e谷大楼","locationX":30.20804740802793,"locationY":120.22319349612219,"installTime":"2018-07-14 14:59:25"},{"cabinetId":472,"cabinetName":"你好","areaId":null,"addr":"浙江省杭州市滨江区西兴街道智慧e谷A座智慧e谷大楼","locationX":30.20793,"locationY":120.222922,"installTime":"2018-07-14 15:09:11"},{"cabinetId":473,"cabinetName":"你好","areaId":null,"addr":"浙江省杭州市滨江区西兴街道智慧e谷A座智慧e谷大楼","locationX":30.20793,"locationY":120.222922,"installTime":"2018-07-14 15:09:12"}]
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
         * cabinetId : 469
         * cabinetName : an
         * areaId : null
         * addr : null
         * locationX : 39.92421106207774
         * locationY : 116.39786327434547
         * installTime : 2018-07-14 10:48:54
         */

        private int cabinetId;
        private String cabinetName;
        private int areaId;
        private String addr;
        private double locationX;
        private double locationY;
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

        public String getInstallTime() {
            return installTime;
        }

        public void setInstallTime(String installTime) {
            this.installTime = installTime;
        }
    }
}
