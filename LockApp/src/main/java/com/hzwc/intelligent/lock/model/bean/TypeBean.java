package com.hzwc.intelligent.lock.model.bean;

import java.util.List;

/**
 * Created by mayn on 2018/8/17.
 */

public class TypeBean {


    /**
     * msg : success
     * code : 0
     * cabinetType : [{"typeId":1,"typeName":"环网柜"},{"typeId":2,"typeName":"开闭所"}]
     */

    private String msg;
    private int code;
    private List<CabinetTypeBean> cabinetType;

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

    public List<CabinetTypeBean> getCabinetType() {
        return cabinetType;
    }

    public void setCabinetType(List<CabinetTypeBean> cabinetType) {
        this.cabinetType = cabinetType;
    }

    public static class CabinetTypeBean {
        /**
         * typeId : 1
         * typeName : 环网柜
         */

        private int typeId;
        private String typeName;

        public int getTypeId() {
            return typeId;
        }

        public void setTypeId(int typeId) {
            this.typeId = typeId;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }
    }
}
