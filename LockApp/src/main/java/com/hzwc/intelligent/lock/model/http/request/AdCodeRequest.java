package com.hzwc.intelligent.lock.model.http.request;


import android.util.Log;

import com.hzwc.intelligent.lock.model.bean.AdCodeBean;
import com.hzwc.intelligent.lock.model.bean.BaseBean;
import com.hzwc.intelligent.lock.model.http.ConstantUrl;
import com.hzwc.intelligent.lock.model.http.HttpService;

import java.net.Proxy;

import okhttp3.OkHttpClient;
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

public class AdCodeRequest {
    private Call<AdCodeBean> mAdCodeCall;
    public void request(String token, String adCode, Callback<AdCodeBean> callback){

         OkHttpClient  client=new OkHttpClient().newBuilder().build();
        Retrofit retrofit = new Retrofit.Builder()
                 .client(client)
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
    }
}
