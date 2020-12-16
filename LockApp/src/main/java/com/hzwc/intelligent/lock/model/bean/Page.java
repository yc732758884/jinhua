package com.hzwc.intelligent.lock.model.bean;

import java.util.List;

/**
 * Created by Administrator on 2020/3/24.
 */

public class Page<T> {



        private int totalCount;
        private int pageSize;
        private int totalPage;
        private int currPage;
        private List<T> list;


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

        public List<T> getList() {
                return list;
        }

        public void setList(List<T> list) {
                this.list = list;
        }
}
