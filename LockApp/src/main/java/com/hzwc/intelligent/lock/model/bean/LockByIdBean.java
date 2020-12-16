package com.hzwc.intelligent.lock.model.bean;

import java.util.List;

/**
 * Created by mayn on 2018/7/23.
 */

public class LockByIdBean {


    /**
     * msg : success
     * code : 0
     * cabinet : {"cabinetId":3,"cabinetName":"柜子8","areaId":198,"addr":"浙江省杭州市滨江区西兴街道西兴路绿城·明月江南东区","locationX":120.22894951656968,"locationY":30.20644451450032,"installTime":"2018-07-02 10:41:03","locks":[{"cabinetId":3,"lockId":1,"lockNo":"6942074224804"},{"cabinetId":3,"lockId":18,"lockNo":"44444"},{"cabinetId":3,"lockId":23,"lockNo":"111"},{"cabinetId":3,"lockId":24,"lockNo":"----555555"},{"cabinetId":3,"lockId":26,"lockNo":"=====uuuu"},{"cabinetId":3,"lockId":27,"lockNo":"55557777"},{"cabinetId":3,"lockId":32,"lockNo":"44445555"},{"cabinetId":3,"lockId":52,"lockNo":"//////***"},{"cabinetId":3,"lockId":54,"lockNo":"//////***,****1111"},{"cabinetId":3,"lockId":55,"lockNo":"//////***--"},{"cabinetId":3,"lockId":137,"lockNo":"22222"},{"cabinetId":3,"lockId":146,"lockNo":"dasdsad"}]}
     */

    private String msg;
    private int code;
    private CabinetBean cabinet;

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

    public CabinetBean getCabinet() {
        return cabinet;
    }

    public void setCabinet(CabinetBean cabinet) {
        this.cabinet = cabinet;
    }

    public static class CabinetBean {
        /**
         * cabinetId : 3
         * cabinetName : 柜子8
         * areaId : 198
         * addr : 浙江省杭州市滨江区西兴街道西兴路绿城·明月江南东区
         * locationX : 120.22894951656968
         * locationY : 30.20644451450032
         * installTime : 2018-07-02 10:41:03
         * locks : [{"cabinetId":3,"lockId":1,"lockNo":"6942074224804"},{"cabinetId":3,"lockId":18,"lockNo":"44444"},{"cabinetId":3,"lockId":23,"lockNo":"111"},{"cabinetId":3,"lockId":24,"lockNo":"----555555"},{"cabinetId":3,"lockId":26,"lockNo":"=====uuuu"},{"cabinetId":3,"lockId":27,"lockNo":"55557777"},{"cabinetId":3,"lockId":32,"lockNo":"44445555"},{"cabinetId":3,"lockId":52,"lockNo":"//////***"},{"cabinetId":3,"lockId":54,"lockNo":"//////***,****1111"},{"cabinetId":3,"lockId":55,"lockNo":"//////***--"},{"cabinetId":3,"lockId":137,"lockNo":"22222"},{"cabinetId":3,"lockId":146,"lockNo":"dasdsad"}]
         */

        private int cabinetId;
        private String cabinetName;
        private int areaId;
        private String addr;
        private double locationX;
        private double locationY;
        private String installTime;
        private List<LocksBean> locks;

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

        public List<LocksBean> getLocks() {
            return locks;
        }

        public void setLocks(List<LocksBean> locks) {
            this.locks = locks;
        }

        public static class LocksBean {
            /**
             * cabinetId : 3
             * lockId : 1
             * lockNo : 6942074224804
             */

            private int cabinetId;
            private int lockId;
            private String lockNo;

            public int getCabinetId() {
                return cabinetId;
            }

            public void setCabinetId(int cabinetId) {
                this.cabinetId = cabinetId;
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
        }
    }
}
