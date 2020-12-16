package com.hzwc.intelligent.lock.model.bean;

import java.util.List;

/**
 * Created by mayn on 2018/7/23.
 */

public class DepartmentListBean {

    /**
     * msg : success
     * code : 0
     * departments : [{"departmentId":1,"departmentName":"施工1部"},{"departmentId":2,"departmentName":"施工2部"},{"departmentId":3,"departmentName":"坑死你部门"},{"departmentId":4,"departmentName":"研发部"},{"departmentId":28,"departmentName":"1"},{"departmentId":29,"departmentName":"测试部"},{"departmentId":30,"departmentName":"fdgdfhg"},{"departmentId":31,"departmentName":"qqq"},{"departmentId":32,"departmentName":"55432543654"},{"departmentId":33,"departmentName":"7689879"},{"departmentId":34,"departmentName":"1111"},{"departmentId":35,"departmentName":"111111"},{"departmentId":36,"departmentName":"11"},{"departmentId":37,"departmentName":"124234234"},{"departmentId":38,"departmentName":"抢2"},{"departmentId":39,"departmentName":"1243"},{"departmentId":40,"departmentName":"2222"},{"departmentId":41,"departmentName":"4"},{"departmentId":42,"departmentName":"dddd"},{"departmentId":43,"departmentName":"33333"},{"departmentId":44,"departmentName":"wetwet"},{"departmentId":45,"departmentName":"212"},{"departmentId":46,"departmentName":"112"},{"departmentId":47,"departmentName":"110"},{"departmentId":48,"departmentName":"111"},{"departmentId":49,"departmentName":"sdgt"},{"departmentId":50,"departmentName":"23"},{"departmentId":51,"departmentName":"fasd"},{"departmentId":52,"departmentName":"41221"},{"departmentId":53,"departmentName":"12143214"},{"departmentId":54,"departmentName":"13333"},{"departmentId":55,"departmentName":"222"},{"departmentId":56,"departmentName":"sdfsd"},{"departmentId":57,"departmentName":"aaaa"},{"departmentId":58,"departmentName":"ddd"},{"departmentId":59,"departmentName":"1214320"},{"departmentId":60,"departmentName":"12143200"},{"departmentId":61,"departmentName":"sd"},{"departmentId":62,"departmentName":"de"},{"departmentId":63,"departmentName":"12143"},{"departmentId":64,"departmentName":"sdf"},{"departmentId":65,"departmentName":"2"},{"departmentId":66,"departmentName":"4444"},{"departmentId":67,"departmentName":"不知道部门"},{"departmentId":68,"departmentName":"123"},{"departmentId":69,"departmentName":"12"},{"departmentId":70,"departmentName":"00000"},{"departmentId":71,"departmentName":"00000000"},{"departmentId":72,"departmentName":"00000000q"},{"departmentId":73,"departmentName":"180226005"},{"departmentId":74,"departmentName":"3"},{"departmentId":75,"departmentName":""}]
     */

    private String msg;
    private int code;
    private List<DepartmentsBean> departments;

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

    public List<DepartmentsBean> getDepartments() {
        return departments;
    }

    public void setDepartments(List<DepartmentsBean> departments) {
        this.departments = departments;
    }

    public static class DepartmentsBean {
        /**
         * departmentId : 1
         * departmentName : 施工1部
         */

        private int departmentId;
        private String departmentName;

        public int getDepartmentId() {
            return departmentId;
        }

        public void setDepartmentId(int departmentId) {
            this.departmentId = departmentId;
        }

        public String getDepartmentName() {
            return departmentName;
        }

        public void setDepartmentName(String departmentName) {
            this.departmentName = departmentName;
        }
    }
}
