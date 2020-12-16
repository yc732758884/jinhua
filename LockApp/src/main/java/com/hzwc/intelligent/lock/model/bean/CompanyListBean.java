package com.hzwc.intelligent.lock.model.bean;

import java.util.List;

/**
 * Created by mayn on 2018/7/23.
 */

public class CompanyListBean {

    /**
     * msg : success
     * code : 0
     * company : [{"companyId":1,"companyName":"舟山"},{"companyId":2,"companyName":"hzwc"},{"companyId":3,"companyName":"ASDASH"},{"companyId":4,"companyName":"2131"},{"companyId":5,"companyName":"SAD445455"},{"companyId":6,"companyName":"坑死你单位"},{"companyId":7,"companyName":"杭州网策"},{"companyId":29,"companyName":"12"},{"companyId":30,"companyName":"测试超"},{"companyId":31,"companyName":"test1"},{"companyId":32,"companyName":"dfhgfdh"},{"companyId":33,"companyName":"qqq"},{"companyId":34,"companyName":"432555"},{"companyId":35,"companyName":"56876867"},{"companyId":36,"companyName":"123"},{"companyId":37,"companyName":"12wedwe"},{"companyId":38,"companyName":"杭州网策通信技术有限公司"},{"companyId":39,"companyName":"111"},{"companyId":40,"companyName":"21324324"},{"companyId":41,"companyName":"测试"},{"companyId":42,"companyName":"sss"},{"companyId":43,"companyName":"dddd"},{"companyId":44,"companyName":"333"},{"companyId":45,"companyName":"ewrw"},{"companyId":46,"companyName":"1112"},{"companyId":47,"companyName":"1110"},{"companyId":48,"companyName":"11111"},{"companyId":49,"companyName":"dfhgdf"},{"companyId":50,"companyName":"111sdgd"},{"companyId":51,"companyName":"111sdgddfgfdg"},{"companyId":52,"companyName":"22"},{"companyId":53,"companyName":"1d"},{"companyId":54,"companyName":"1"},{"companyId":55,"companyName":"sad"},{"companyId":56,"companyName":"12321"},{"companyId":57,"companyName":"121321"},{"companyId":58,"companyName":"1213"},{"companyId":59,"companyName":"121321sdfaf"},{"companyId":60,"companyName":"121321ewfdewf"},{"companyId":61,"companyName":"121321111"},{"companyId":62,"companyName":"sad1"},{"companyId":63,"companyName":"sad11"},{"companyId":64,"companyName":"sad111"},{"companyId":65,"companyName":"222"},{"companyId":66,"companyName":"2"},{"companyId":67,"companyName":"22222"},{"companyId":68,"companyName":"de"},{"companyId":69,"companyName":"test2"},{"companyId":70,"companyName":"WC"},{"companyId":71,"companyName":"11"},{"companyId":72,"companyName":"180226005"},{"companyId":73,"companyName":"3"},{"companyId":74,"companyName":""}]
     */

    private String msg;
    private int code;
    private List<CompanyBean> company;

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

    public List<CompanyBean> getCompany() {
        return company;
    }

    public void setCompany(List<CompanyBean> company) {
        this.company = company;
    }

    public static class CompanyBean {
        /**
         * companyId : 1
         * companyName : 舟山
         */

        private int companyId;
        private String companyName;

        public int getCompanyId() {
            return companyId;
        }

        public void setCompanyId(int companyId) {
            this.companyId = companyId;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }
    }
}
