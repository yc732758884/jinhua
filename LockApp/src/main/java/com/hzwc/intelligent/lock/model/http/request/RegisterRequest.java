package com.hzwc.intelligent.lock.model.http.request;


import com.hzwc.intelligent.lock.model.bean.BaseBean;
import com.hzwc.intelligent.lock.model.bean.RegisterBean;
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

public class RegisterRequest {
    private Call<RegisterBean> mMessageCall;
    private Call<BaseBean> mVerificationCall;

    public void sendMessage(String token, String phonenumber, String sendDepartment, Callback<RegisterBean> callback) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstantUrl.PUBLIC_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        HttpService apiService = retrofit.create(HttpService.class);
        mMessageCall = apiService.sendMessage(token, phonenumber, sendDepartment);
        mMessageCall.enqueue(callback);
    }

    public void verification(String token, String phonenumber, String verifyCode, Callback<BaseBean> callback) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstantUrl.PUBLIC_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        HttpService apiService = retrofit.create(HttpService.class);
        mVerificationCall = apiService.verification(token, phonenumber, verifyCode);
        mVerificationCall.enqueue(callback);
    }

    public void interruptHttp() {
        if (mMessageCall != null && !mMessageCall.isCanceled()) {
            mMessageCall.cancel();
        }
    }
}
