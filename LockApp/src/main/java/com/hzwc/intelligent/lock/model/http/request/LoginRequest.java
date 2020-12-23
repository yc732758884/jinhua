package com.hzwc.intelligent.lock.model.http.request;


import android.util.Log;

import com.hzwc.intelligent.lock.model.bean.LoginBean;
import com.hzwc.intelligent.lock.model.http.ConstantUrl;
import com.hzwc.intelligent.lock.model.http.HttpService;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
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


        HttpLoggingInterceptor.Level level = HttpLoggingInterceptor.Level.BODY;

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {

                Log.e("login", "OkHttp====Message:" + message);

            }
        });
        loggingInterceptor.setLevel(level);



        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(ConstantUrl.PUBLIC_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        HttpService apiService = retrofit.create(HttpService.class);
        mLoginCall = apiService.login(mobile, password,id,rid);
        mLoginCall.enqueue(callback);
    }

    public void interruptHttp(){
        if(mLoginCall != null && !mLoginCall.isCanceled()){
            mLoginCall.cancel();
        }
    }
}
