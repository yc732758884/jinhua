package com.hzwc.intelligent.lock.model.utils;

import android.content.Context;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.inuker.bluetooth.library.beacon.Beacon;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2020/3/25.
 */

public class AmapLocationUtils  implements AMapLocationListener{

    Context context;
   static AmapLocationUtils instance;


    public  static AmapLocationUtils getInstace(Context context){
        if (instance==null){

           instance=new AmapLocationUtils(context);
        }
        return  instance;



    }


    private AmapLocationUtils(Context context) {
        this.context = context;
    }



    //声明mlocationClient对象
    public AMapLocationClient  mlocationClient;
    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;

     public void init(){
         mlocationClient=new AMapLocationClient(context);

         //初始化定位参数
         mLocationOption = new AMapLocationClientOption();
//设置定位监听
         mlocationClient.setLocationListener(this);
//设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
         mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
//设置定位间隔,单位毫秒,默认为2000ms
         mLocationOption.setInterval(2000);
         mLocationOption.setOnceLocation(true);
//设置定位参数
         mlocationClient.setLocationOption(mLocationOption);
// 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
// 注意设置合适的定位时间的间隔（最小间隔支持为1000ms），并且在合适时间调用stopLocation()方法来取消定位请求
// 在定位结束后，在合适的生命周期调用onDestroy()方法
// 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
//启动定位
         mlocationClient.startLocation();




     }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                double lat=amapLocation.getLatitude();//获取纬度
               double lon= amapLocation.getLongitude();//获取经度
                amapLocation.getAccuracy();//获取精度信息

                if (locationListener!=null){
                    locationListener.location(lat,lon);
                }



                mlocationClient.onDestroy();

            } else {
                Log.e("info", "location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());

            }
        }
    }

    public getLocationListener locationListener;

    public void setLocationListener(getLocationListener locationListener) {
        this.locationListener = locationListener;
    }

  public   interface getLocationListener{
         void  location(double lat,double lon);
    }







}




