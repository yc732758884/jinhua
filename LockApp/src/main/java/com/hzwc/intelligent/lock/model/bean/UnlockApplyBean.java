package com.hzwc.intelligent.lock.model.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/7/14.
 */

public class UnlockApplyBean {


    /**
     * code : 0
     * msg : success
     * unlockRecordEntity : {"currPage":1,"list":[{"cabinetName":"网策试点","company":"华云","department":"维修部","id":222,"isTemp":0,"lockNo":"0C:B2:B7:69:02:12","name":"安安","phone":"15669967820","post":"维修员","power":100,"tempUnlock":"是","unlockTime":"2018-08-01 10:28:39","userId":42},{"cabinetName":"网策试点","company":"华云","department":"维修部","id":223,"isTemp":0,"lockNo":"0C:B2:B7:69:02:12","name":"安安","phone":"15669967820","post":"维修员","power":100,"tempUnlock":"是","unlockTime":"2018-08-01 10:28:58","userId":42},{"cabinetName":"网策试点","company":"华云","department":"维修部","id":224,"isTemp":0,"lockNo":"0C:B2:B7:69:02:12","name":"安安","phone":"15669967820","post":"维修员","power":100,"tempUnlock":"是","unlockTime":"2018-08-01 10:29:10","userId":42},{"cabinetName":"网策试点","company":"华云","department":"维修部","id":225,"isTemp":0,"lockNo":"0C:B2:B7:69:02:12","name":"安安","phone":"15669967820","post":"维修员","power":100,"tempUnlock":"是","unlockTime":"2018-08-01 10:29:39","userId":42}],"pageSize":10,"totalCount":4,"totalPage":1}
     */

    private int code;
    private String msg;
    private UnlockRecordEntityBean unlockRecordEntity;

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

    public UnlockRecordEntityBean getUnlockRecordEntity() {
        return unlockRecordEntity;
    }

    public void setUnlockRecordEntity(UnlockRecordEntityBean unlockRecordEntity) {
        this.unlockRecordEntity = unlockRecordEntity;
    }

    public static class UnlockRecordEntityBean {
        /**
         * currPage : 1
         * list : [{"cabinetName":"网策试点","company":"华云","department":"维修部","id":222,"isTemp":0,"lockNo":"0C:B2:B7:69:02:12","name":"安安","phone":"15669967820","post":"维修员","power":100,"tempUnlock":"是","unlockTime":"2018-08-01 10:28:39","userId":42},{"cabinetName":"网策试点","company":"华云","department":"维修部","id":223,"isTemp":0,"lockNo":"0C:B2:B7:69:02:12","name":"安安","phone":"15669967820","post":"维修员","power":100,"tempUnlock":"是","unlockTime":"2018-08-01 10:28:58","userId":42},{"cabinetName":"网策试点","company":"华云","department":"维修部","id":224,"isTemp":0,"lockNo":"0C:B2:B7:69:02:12","name":"安安","phone":"15669967820","post":"维修员","power":100,"tempUnlock":"是","unlockTime":"2018-08-01 10:29:10","userId":42},{"cabinetName":"网策试点","company":"华云","department":"维修部","id":225,"isTemp":0,"lockNo":"0C:B2:B7:69:02:12","name":"安安","phone":"15669967820","post":"维修员","power":100,"tempUnlock":"是","unlockTime":"2018-08-01 10:29:39","userId":42}]
         * pageSize : 10
         * totalCount : 4
         * totalPage : 1
         */

        private int currPage;
        private int pageSize;
        private int totalCount;
        private int totalPage;
        private List<ListBean> list;

        public int getCurrPage() {
            return currPage;
        }

        public void setCurrPage(int currPage) {
            this.currPage = currPage;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * cabinetName : 网策试点
             * company : 华云
             * department : 维修部
             * id : 222
             * isTemp : 0
             * lockNo : 0C:B2:B7:69:02:12
             * name : 安安
             * phone : 15669967820
             * post : 维修员
             * power : 100
             * tempUnlock : 是
             * unlockTime : 2018-08-01 10:28:39
             * userId : 42
             */

            private String cabinetName;
            private String company;
            private String department;
            private int id;
            private int isTemp;
            private String lockNo;
            private String name;
            private String phone;
            private String post;
            private int power;
            private String tempUnlock;
            private String unlockTime;
            private int userId;

            public String getCabinetName() {
                return cabinetName;
            }

            public void setCabinetName(String cabinetName) {
                this.cabinetName = cabinetName;
            }

            public String getCompany() {
                return company;
            }

            public void setCompany(String company) {
                this.company = company;
            }

            public String getDepartment() {
                return department;
            }

            public void setDepartment(String department) {
                this.department = department;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getIsTemp() {
                return isTemp;
            }

            public void setIsTemp(int isTemp) {
                this.isTemp = isTemp;
            }

            public String getLockNo() {
                return lockNo;
            }

            public void setLockNo(String lockNo) {
                this.lockNo = lockNo;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getPost() {
                return post;
            }

            public void setPost(String post) {
                this.post = post;
            }

            public int getPower() {
                return power;
            }

            public void setPower(int power) {
                this.power = power;
            }

            public String getTempUnlock() {
                return tempUnlock;
            }

            public void setTempUnlock(String tempUnlock) {
                this.tempUnlock = tempUnlock;
            }

            public String getUnlockTime() {
                return unlockTime;
            }

            public void setUnlockTime(String unlockTime) {
                this.unlockTime = unlockTime;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }
        }
    }
}
