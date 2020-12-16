package com.hzwc.intelligent.lock.model.http.request;


import android.util.Log;

import com.hzwc.intelligent.lock.model.bean.AdCodeBean;
import com.hzwc.intelligent.lock.model.bean.BaseBean;
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

public class AdCodeRequest {
    private Call<AdCodeBean> mAdCodeCall;
    public void request(String token, String adCode, Callback<AdCodeBean> callback){

        HttpLoggingInterceptor.Level level = HttpLoggingInterceptor.Level.BODY;

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {

                Log.e("open", "OkHttp====Message:" + message);

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
        mAdCodeCall = apiService.getAreaIdByCode(token,adCode);
        mAdCodeCall.enqueue(callback);
    }

    public void interruptHttp(){
        if(mAdCodeCall != null && !mAdCodeCall.isCanceled()){
            mAdCodeCall.cancel();
        }
    }
}
