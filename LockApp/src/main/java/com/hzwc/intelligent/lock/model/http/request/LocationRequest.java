package com.hzwc.intelligent.lock.model.http.request;


import com.hzwc.intelligent.lock.model.bean.MarkerBean;
import com.hzwc.intelligent.lock.model.bean.MessageBean;
import com.hzwc.intelligent.lock.model.http.ConstantUrl;
import com.hzwc.intelligent.lock.model.http.HttpService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * @author Administrator
 * @date 2018/1/15
 */

public class LocationRequest {
    private Call<MarkerBean> mLocationCall;

    public void request(String mobile, String cabinetName, Callback<MarkerBean> callback) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstantUrl.PUBLIC_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        HttpService apiService = retrofit.create(HttpService.class);
        mLocationCall = apiService.searchCabinetsByNo(mobile, cabinetName);
        mLocationCall.enqueue(callback);
    }

    public void requestRim(String mobile, double locationX, double locationY, Callback<MarkerBean> callback) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstantUrl.PUBLIC_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        HttpService apiService = retrofit.create(HttpService.class);
        mLocationCall = apiService.getCabinetsByLocation(mobile, locationX, locationY);
        mLocationCall.enqueue(callback);
    }

    public void interruptHttp() {
        if (mLocationCall != null && !mLocationCall.isCanceled()) {
            mLocationCall.cancel();
        }
    }
}
