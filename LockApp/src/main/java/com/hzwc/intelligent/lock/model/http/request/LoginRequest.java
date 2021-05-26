package com.hzwc.intelligent.lock.model.http.request;


import android.util.Log;

import com.hzwc.intelligent.lock.model.bean.LoginBean;
import com.hzwc.intelligent.lock.model.http.ConstantUrl;
import com.hzwc.intelligent.lock.model.http.HttpService;
import com.hzwc.intelligent.lock.model.utils.SecurityRSA;

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

public class LoginRequest {
    private Call<LoginBean> mLoginCall;

    public void request(String mobile, String password, String id,String rid,Callback<LoginBean> callback){



        OkHttpClient client = new OkHttpClient.Builder()
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(ConstantUrl.PUBLIC_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        HttpService apiService = retrofit.create(HttpService.class);
        mLoginCall = apiService.login(SecurityRSA.encode(mobile), password,id,rid);
        mLoginCall.enqueue(callback);
    }

    public void interruptHttp(){
        if(mLoginCall != null && !mLoginCall.isCanceled()){
            mLoginCall.cancel();
        }
    }
}
