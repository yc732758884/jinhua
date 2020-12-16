package com.hzwc.intelligent.lock.model.bean;

import java.util.List;

/**
 * Created by mayn on 2018/7/13.
 */

public class MarkerBean {


    /**
     * cabinets : [{"addr":"浙江省杭州市滨江区西兴街道西兴路绿城·明月江南东区","areaId":198,"cabinetId":3,"cabinetName":"柜子8","installTime":"2018-07-02 10:41:03","locationX":120.22894951656968,"locationY":30.20644451450032,"locks":[{"cabinetId":3,"lockId":1,"lockNo":"6942074224804"},{"cabinetId":3,"lockId":18,"lockNo":"44444"},{"cabinetId":3,"lockId":23,"lockNo":"111"},{"cabinetId":3,"lockId":24,"lockNo":"----555555"},{"cabinetId":3,"lockId":26,"lockNo":"=====uuuu"},{"cabinetId":3,"lockId":27,"lockNo":"55557777"},{"cabinetId":3,"lockId":32,"lockNo":"44445555"},{"cabinetId":3,"lockId":52,"lockNo":"//////***"},{"cabinetId":3,"lockId":54,"lockNo":"//////***,****1111"},{"cabinetId":3,"lockId":55,"lockNo":"//////***--"},{"cabinetId":3,"lockId":137,"lockNo":"22222"},{"cabinetId":3,"lockId":146,"lockNo":"dasdsad"}]},{"addr":"智慧e谷3","areaId":171,"cabinetId":5,"cabinetName":"柜子5","installTime":"2018-07-11 10:41:09","locationX":120.22501069269869,"locationY":30.21624690685839,"locks":[{"cabinetId":5,"lockId":3,"lockNo":"74:E1:82:04:2B:FC"},{"cabinetId":5,"lockId":59,"lockNo":"4"},{"cabinetId":5,"lockId":60,"lockNo":"4dd"},{"cabinetId":5,"lockId":61,"lockNo":"4dduy"},{"cabinetId":5,"lockId":62,"lockNo":"fsfs"},{"cabinetId":5,"lockId":64,"lockNo":"88889999"},{"cabinetId":5,"lockId":145,"lockNo":"dasd"},{"cabinetId":5,"lockId":147,"lockNo":"dsaddasd"}]},{"addr":"浙江省杭州市滨江区西兴街道左岸万国花园","areaId":1,"cabinetId":7,"cabinetName":"柜子7","installTime":"2018-07-12 16:36:37","locationX":120.21330016834001,"locationY":30.21917538202645,"locks":[{"cabinetId":7,"lockId":14,"lockNo":"4499"},{"cabinetId":7,"lockId":15,"lockNo":"9666"},{"cabinetId":7,"lockId":148,"lockNo":"156161"}]},{"addr":"物联网街与阡陌路交叉口东南150米","areaId":2,"cabinetId":30,"cabinetName":"测试","installTime":"2018-07-13 15:22:32","locationX":120.222922,"locationY":30.20624690685839,"locks":[{"cabinetId":30,"lockId":66,"lockNo":"a111112"},{"cabinetId":30,"lockId":67,"lockNo":"a11111245"},{"cabinetId":30,"lockId":79,"lockNo":"69420742248"},{"cabinetId":30,"lockId":80,"lockNo":"1234"},{"cabinetId":30,"lockId":81,"lockNo":"5678"},{"cabinetId":30,"lockId":83,"lockNo":"q\n"},{"cabinetId":30,"lockId":84,"lockNo":"fgq"},{"cabinetId":30,"lockId":86,"lockNo":"http://m.tb.cn/ZQW7Wf"},{"cabinetId":30,"lockId":87,"lockNo":"http://m.tb.cn/ZQ"},{"cabinetId":30,"lockId":88,"lockNo":"awww"},{"cabinetId":30,"lockId":89,"lockNo":"wdf"},{"cabinetId":30,"lockId":90,"lockNo":"cvv"},{"cabinetId":30,"lockId":91,"lockNo":"vbhhhw"},{"cabinetId":30,"lockId":92},{"cabinetId":30,"lockId":96,"lockNo":""},{"cabinetId":30,"lockId":97,"lockNo":"1"},{"cabinetId":30,"lockId":98,"lockNo":"w"},{"cabinetId":30,"lockId":117,"lockNo":"fff"},{"cabinetId":30,"lockId":118,"lockNo":"fgghh"},{"cabinetId":30,"lockId":119},{"cabinetId":30,"lockId":130,"lockNo":"rfgg"},{"cabinetId":30,"lockId":131,"lockNo":"Chubb"},{"cabinetId":30,"lockId":132,"lockNo":",hhhhhg"},{"cabinetId":30,"lockId":133,"lockNo":"the"}]},{"addr":"浙江省杭州市滨江区西兴街道新联路智慧e谷大楼","areaId":0,"cabinetId":475,"cabinetName":"66666666y","installTime":"2018-07-16 10:45:32","locationX":120.22475185953319,"locationY":30.20886449121176,"locks":[{"cabinetId":475,"lockId":63,"lockNo":"99999666666"},{"cabinetId":475,"lockId":73,"lockNo":"123"},{"cabinetId":475,"lockId":74,"lockNo":"456"},{"cabinetId":475,"lockId":75,"lockNo":"789"},{"cabinetId":475,"lockId":76,"lockNo":"012"}]},{"addr":"杭州市滨江区智慧e谷5楼","areaId":0,"cabinetId":478,"cabinetName":"我是环网柜","installTime":"2018-07-20 09:43:36","locationX":120.224922,"locationY":30.20824690685839,"locks":[{"cabinetId":478,"lockId":136,"lockNo":"769584321"}]}]
     * code : 0
     * msg : success
     */

    private int code;
    private String msg;
    private List<CabinetsBean> cabinets;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<CabinetsBean> getCabinets() {
        return cabinets;
    }

    public void setCabinets(List<CabinetsBean> cabinets) {
        this.cabinets = cabinets;
    }

    public static class CabinetsBean {
        /**
         * addr : 浙江省杭州市滨江区西兴街道西兴路绿城·明月江南东区
         * areaId : 198
         * cabinetId : 3
         * cabinetName : 柜子8
         * installTime : 2018-07-02 10:41:03
         * locationX : 120.22894951656968
         * locationY : 30.20644451450032
         * locks : [{"cabinetId":3,"lockId":1,"lockNo":"6942074224804"},{"cabinetId":3,"lockId":18,"lockNo":"44444"},{"cabinetId":3,"lockId":23,"lockNo":"111"},{"cabinetId":3,"lockId":24,"lockNo":"----555555"},{"cabinetId":3,"lockId":26,"lockNo":"=====uuuu"},{"cabinetId":3,"lockId":27,"lockNo":"55557777"},{"cabinetId":3,"lockId":32,"lockNo":"44445555"},{"cabinetId":3,"lockId":52,"lockNo":"//////***"},{"cabinetId":3,"lockId":54,"lockNo":"//////***,****1111"},{"cabinetId":3,"lockId":55,"lockNo":"//////***--"},{"cabinetId":3,"lockId":137,"lockNo":"22222"},{"cabinetId":3,"lockId":146,"lockNo":"dasdsad"}]
         */

        private String addr;
        private int areaId;
        private int cabinetId;
        private String cabinetName;
        private String installTime;
        private double locationX;
        private double locationY;
        private List<LocksBean> locks;

        public String getAddr() {
            return addr;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }

        public int getAreaId() {
            return areaId;
        }

        public void setAreaId(int areaId) {
            this.areaId = areaId;
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

        public String getInstallTime() {
            return installTime;
        }

        public void setInstallTime(String installTime) {
            this.installTime = installTime;
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
