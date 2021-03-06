package com.hzwc.intelligent.lock.model.http.request;


import com.hzwc.intelligent.lock.model.bean.MessageBean;
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

public class MessageRequest {
    private Call<MessageBean> mMessageCall;
    OkHttpClient client = new OkHttpClient.Builder()
            .build();

    public void request(String mobile, String sendDepartment, Callback<MessageBean> callback){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstantUrl.PUBLIC_URL)
                .client(client)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        HttpService apiService = retrofit.create(HttpService.class);
        mMessageCall = apiService.sendMessage(SecurityRSA.encode(mobile), sendDepartment);
        mMessageCall.enqueue(callback);
    }

    public void interruptHttp(){
        if(mMessageCall != null && !mMessageCall.isCanceled()){
            mMessageCall.cancel();
        }
    }
}
