package com.hzwc.intelligent.lock.model.bean;

import java.util.List;

/**
 *
 * @author apple
 * @date 2017/12/16
 */

public class Response {


    /**
     * result : {"success":true,"errorCode":"","errorMsg":""}
     * content : [{"total_install_base":"1000000","total_number_households":"100","total_gen_cap":"5000","completion_rate":"60","role":"company1"},{"total_install_base":"1000000","total_number_households":"100","total_gen_cap":"5000","completion_rate":"60","role":"company2"},{"total_install_base":"1000000","total_number_households":"100","total_gen_cap":"5000","completion_rate":"60","role":"company3"}]
     */

    private ResultBean result;
    private List<ContentBean> content;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }

    public static class ResultBean {
        /**
         * success : true
         * errorCode :
         * errorMsg :
         */

        private boolean success;
        private String errorCode;
        private String errorMsg;

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public String getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(String errorCode) {
            this.errorCode = errorCode;
        }

        public String getErrorMsg() {
            return errorMsg;
        }

        public void setErrorMsg(String errorMsg) {
            this.errorMsg = errorMsg;
        }
    }

    public static class ContentBean {
        /**
         * total_install_base : 1000000
         * total_number_households : 100
         * total_gen_cap : 5000
         * completion_rate : 60
         * role : company1
         */

        private String total_install_base;
        private String total_number_households;
        private String total_gen_cap;
        private String completion_rate;
        private String role;

        public String getTotal_install_base() {
            return total_install_base;
        }

        public void setTotal_install_base(String total_install_base) {
            this.total_install_base = total_install_base;
        }

        public String getTotal_number_households() {
            return total_number_households;
        }

        public void setTotal_number_households(String total_number_households) {
            this.total_number_households = total_number_households;
        }

        public String getTotal_gen_cap() {
            return total_gen_cap;
        }

        public void setTotal_gen_cap(String total_gen_cap) {
            this.total_gen_cap = total_gen_cap;
        }

        public String getCompletion_rate() {
            return completion_rate;
        }

        public void setCompletion_rate(String completion_rate) {
            this.completion_rate = completion_rate;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }
    }
}
