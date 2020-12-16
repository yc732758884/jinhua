package com.hzwc.intelligent.lock.model.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/7/18.
 */

public class RestsWarnBean {

    private String msg;
    private int code;
    private WarnsBean warns;

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

    public WarnsBean getWarns() {
        return warns;
    }

    public void setWarns(WarnsBean warns) {
        this.warns = warns;
    }

    public static class WarnsBean {
        private int totalCount; // FIXME check this code
        private int pageSize;
        private int totalPage;
        private int currPage;
        private List<ListBean> list;

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public int getCurrPage() {
            return currPage;
        }

        public void setCurrPage(int currPage) {
            this.currPage = currPage;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * warnId : 567
             * warnType : 其他告警
             * cabinetName : null
             * lockNo : 0C:B2:B7:69:02:12
             * infos : 锁坏了
             * details : 8899
             * locationLon : 120.23057895851836
             * locationLat : 30.20797670973144
             */

            private int warnId;
            private String warnType;
            private Object cabinetName;
            private String lockNo;
            private String infos;
            private String details;
            private double locationLon;
            private double locationLat;
            private String recordtime;

            public String getRecordtime() {
                return recordtime;
            }

            public void setRecordtime(String recordtime) {
                this.recordtime = recordtime;
            }

            public int getWarnId() {
                return warnId;
            }

            public void setWarnId(int warnId) {
                this.warnId = warnId;
            }

            public String getWarnType() {
                return warnType;
            }

            public void setWarnType(String warnType) {
                this.warnType = warnType;
            }

            public Object getCabinetName() {
                return cabinetName;
            }

            public void setCabinetName(Object cabinetName) {
                this.cabinetName = cabinetName;
            }

            public String getLockNo() {
                return lockNo;
            }

            public void setLockNo(String lockNo) {
                this.lockNo = lockNo;
            }

            public String getInfos() {
                return infos;
            }

            public void setInfos(String infos) {
                this.infos = infos;
            }

            public String getDetails() {
                return details;
            }

            public void setDetails(String details) {
                this.details = details;
            }

            public double getLocationLon() {
                return locationLon;
            }

            public void setLocationLon(double locationLon) {
                this.locationLon = locationLon;
            }

            public double getLocationLat() {
                return locationLat;
            }

            public void setLocationLat(double locationLat) {
                this.locationLat = locationLat;
            }
        }
    }
}
