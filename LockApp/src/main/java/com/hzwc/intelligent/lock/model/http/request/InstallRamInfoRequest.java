package com.hzwc.intelligent.lock.model.http.request;


import com.hzwc.intelligent.lock.model.bean.AdCodeBean;
import com.hzwc.intelligent.lock.model.bean.BaseBean;
import com.hzwc.intelligent.lock.model.http.ConstantUrl;
import com.hzwc.intelligent.lock.model.http.HttpService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 *
 * @author Administrator
 * @date 2018/1/15
 */

public class InstallRamInfoRequest {
    private Call<BaseBean> mInstallRamInfoCall;

    private Call<AdCodeBean> mAdCodeCall;
    public void request(String token,String addr,int cabinetId,String cabinetName,double locationX,double locationY,int areaId, Callback<BaseBean> callback){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstantUrl.PUBLIC_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        HttpService apiService = retrofit.create(HttpService.class);
        mInstallRamInfoCall = apiService.updateCabinet(token, addr,  cabinetId,cabinetName,locationX, locationY,areaId);
        mInstallRamInfoCall.enqueue(callback);
    }


    public void requestCode(String token, String adCode, Callback<AdCodeBean> callback){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstantUrl.PUBLIC_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        HttpService apiService = retrofit.create(HttpService.class);
        mAdCodeCall = apiService.getAreaIdByCode(token,adCode);
        mAdCodeCall.enqueue(callback);
    }

    public void interruptHttp(){
        if(mAdCodeCall != null && !mAdCodeCall.isCanceled()){
            mAdCodeCall.cancel();
        }
        if(mInstallRamInfoCall != null && !mInstallRamInfoCall.isCanceled()){
            mInstallRamInfoCall.cancel();
        }
    }

}
