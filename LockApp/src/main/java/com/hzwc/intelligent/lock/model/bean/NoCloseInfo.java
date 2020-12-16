package com.hzwc.intelligent.lock.model.bean;

/**
 * Created by Administrator on 2020/3/24.
 */

public class NoCloseInfo {

    /**
     * Auto-generated: 2020-03-24 10:3:59
     *
     * @author bejson.com (i@bejson.com)
     * @website http://www.bejson.com/java2pojo/
     */

        private String lockNo;
        private String cabinetName;
        private String recordtime;
        private String locationLon;
        private String locationLat;
        public void setLockNo(String lockNo) {
            this.lockNo = lockNo;
        }
        public String getLockNo() {
            return lockNo;
        }

        public void setCabinetName(String cabinetName) {
            this.cabinetName = cabinetName;
        }
        public String getCabinetName() {
            return cabinetName;
        }

        public void setRecordtime(String recordtime) {
            this.recordtime = recordtime;
        }
        public String getRecordtime() {
            return recordtime;
        }

        public void setLocationLon(String locationLon) {
            this.locationLon = locationLon;
        }
        public String getLocationLon() {
            return locationLon;
        }

        public void setLocationLat(String locationLat) {
            this.locationLat = locationLat;
        }
        public String getLocationLat() {
            return locationLat;
        }


}
