package com.hzwc.intelligent.lock.model.http;

/**
 * Created by anna on 2018/2/27.
 */

public class  ConstantUrl {
    //  public static final String PUBLIC_URL ="http://192.168.1.123:8080/btlock/";
    public static final String PUBLIC_URL1 ="http://115.29.204.20:8010/btlockyidong/";


    //public static final String PUBLIC_URL = "http://192.168.1.211:8080/btlock/";

    //public static  String PUBLIC_URL ="http://115.29.204.20:8010/btlockyidong/";
    public static  String PUBLIC_URL ="http://115.29.204.20:8022/btlockyidongjh/";
   /// http://115.29.204.20:8022/btlockyidongjh/
   // public static  String PUBLIC_URL ="http://115.29.204.20:8010/btlockyidong/";
//    public static final String PUBLIC_URL = "http://192.168.1.230:8080/btlock/";
    /**
     * 天气
     */
    public static final String WEATHER_URL = "http://www.weather.com.cn/";

    //喀响 8015     8010  传动
  //http://115.29.204.20:8015/btlockyidong/


    public static void setPublicUrl(String publicUrl) {
        PUBLIC_URL = publicUrl;


    }
}

