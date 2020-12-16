package com.hzwc.intelligent.lock.model.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/7/18.
 */

public class LocationWarnsBean {

    /**
     * msg : success
     * code : 0
     * warns : {"totalCount":4,"pageSize":10,"totalPage":1,"currPage":1,"list":[{"lockNo":"9999","cabinetName":null,"locationX":null,"locationY":null,"locationLon":120.21909518228122,"locationLat":30.20570574302541},{"lockNo":"12345","cabinetName":null,"locationX":null,"locationY":null,"locationLon":120.21709518228123,"locationLat":30.21324690685839},{"lockNo":"4499","cabinetName":"柜子8","locationX":120.22894951656968,"locationY":30.20644451450032,"locationLon":120.220922,"locationLat":30.20924690685839},{"lockNo":null,"cabinetName":"柜子8","locationX":120.22894951656968,"locationY":30.20644451450032,"locationLon":120.231922,"locationLat":30.21224690685839}]}
     */

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
        /**
         * totalCount : 4
         * pageSize : 10
         * totalPage : 1
         * currPage : 1
         * list : [{"lockNo":"9999","cabinetName":null,"locationX":null,"locationY":null,"locationLon":120.21909518228122,"locationLat":30.20570574302541},{"lockNo":"12345","cabinetName":null,"locationX":null,"locationY":null,"locationLon":120.21709518228123,"locationLat":30.21324690685839},{"lockNo":"4499","cabinetName":"柜子8","locationX":120.22894951656968,"locationY":30.20644451450032,"locationLon":120.220922,"locationLat":30.20924690685839},{"lockNo":null,"cabinetName":"柜子8","locationX":120.22894951656968,"locationY":30.20644451450032,"locationLon":120.231922,"locationLat":30.21224690685839}]
         */

        private int totalCount;
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
             * lockNo : 9999
             * cabinetName : null
             * locationX : null
             * locationY : null
             * locationLon : 120.21909518228122
             * locationLat : 30.20570574302541
             */

            private String lockNo;
            private String cabinetName;
            private double locationX;
            private double locationY;
            private double locationLon;
            private double locationLat;

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
