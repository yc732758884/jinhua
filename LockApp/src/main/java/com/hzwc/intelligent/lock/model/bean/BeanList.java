package com.hzwc.intelligent.lock.model.bean;

import java.util.List;

/**
 *
 * @author apple
 * @date 2017/12/26
 */

public class BeanList extends BaseBean {


    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
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
