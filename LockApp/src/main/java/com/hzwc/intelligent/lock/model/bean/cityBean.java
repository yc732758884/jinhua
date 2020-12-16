package com.hzwc.intelligent.lock.model.bean;

import java.util.List;

/**
 * Created by Administrator on 2020/1/2.
 */

public class cityBean {

 private    String msg;
    private String  code;
  private   List<city>  data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<city> getData() {
        return data;
    }

    public void setData(List data) {
        this.data = data;
    }



   public static class city{
        private String cityId;
        private  String  city;

        public String getCityId() {
            return cityId;
        }

        public void setCityId(String cityId) {
            this.cityId = cityId;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }
    }


}

