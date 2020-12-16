package com.hzwc.intelligent.lock.model.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/7/18.
 */

public class WarnTypesBean {

    /**
     * msg : success
     * code : 0
     * warnType : [{"warnInfoId":1,"warnType":"欠压告警"},{"warnInfoId":2,"warnType":"位置告警"},{"warnInfoId":3,"warnType":"其他告警"}]
     */

    private String msg;
    private int code;
    private List<WarnTypeBean> warnType;

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

    public List<WarnTypeBean> getWarnType() {
        return warnType;
    }

    public void setWarnType(List<WarnTypeBean> warnType) {
        this.warnType = warnType;
    }

    public static class WarnTypeBean {
        /**
         * warnInfoId : 1
         * warnType : 欠压告警
         */

        private int warnInfoId;
        private String warnType;

        public int getWarnInfoId() {
            return warnInfoId;
        }

        public void setWarnInfoId(int warnInfoId) {
            this.warnInfoId = warnInfoId;
        }

        public String getWarnType() {
            return warnType;
        }

        public void setWarnType(String warnType) {
            this.warnType = warnType;
        }
    }
}
