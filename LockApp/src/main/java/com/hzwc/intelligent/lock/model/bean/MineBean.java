package com.hzwc.intelligent.lock.model.bean;

import java.util.List;

/**
 * Created by mayn on 2018/7/23.
 */

public class MineBean {

    /**
     * msg : success
     * code : 0
     * UserInfo : [{"userId":1,"name":"你是谁123","username":"17605916588","phone":null,"company":"舟山","department":"施工2部","post":"施工人员"}]
     */

    private String msg;
    private int code;
    private List<UserInfoBean> UserInfo;

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

    public List<UserInfoBean> getUserInfo() {
        return UserInfo;
    }

    public void setUserInfo(List<UserInfoBean> UserInfo) {
        this.UserInfo = UserInfo;
    }

    public static class UserInfoBean {
        /**
         * userId : 1
         * name : 你是谁123
         * username : 17605916588
         * phone : null
         * company : 舟山
         * department : 施工2部
         * post : 施工人员
         */

        private int userId;
        private String name;
        private String username;
        private String phone;
        private String company;
        private String department;
        private String post;

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
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

        public String getPost() {
            return post;
        }

        public void setPost(String post) {
            this.post = post;
        }
    }
}
