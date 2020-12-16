package com.hzwc.intelligent.lock.model.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/7/18.
 */

public class PowerWarnsBean {

    /**
     * msg : success
     * code : 0
     * warns : {"totalCount":2,"pageSize":10,"totalPage":1,"currPage":1,"list":[{"lockNo":"99999666666","power":0,"locationLon":null,"locationLat":null},{"lockNo":"99999666666","power":0,"locationLon":null,"locationLat":null}]}
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
         * totalCount : 2
         * pageSize : 10
         * totalPage : 1
         * currPage : 1
         * list : [{"lockNo":"99999666666","power":0,"locationLon":null,"locationLat":null},{"lockNo":"99999666666","power":0,"locationLon":null,"locationLat":null}]
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
             * lockNo : 99999666666
             * power : 0
             * locationLon : null
             * locationLat : null
             */

            private String lockNo;
            private int power;
            private double locationLon;
            private double locationLat;

            public String getLockNo() {
                return lockNo;
            }

            public void setLockNo(String lockNo) {
                this.lockNo = lockNo;
            }

            public int getPower() {
                return power;
            }

            public void setPower(int power) {
                this.power = power;
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
