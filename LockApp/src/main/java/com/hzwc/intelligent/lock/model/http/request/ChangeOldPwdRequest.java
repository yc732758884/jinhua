package com.hzwc.intelligent.lock.model.http.request;


import com.hzwc.intelligent.lock.model.bean.BaseBean;
import com.hzwc.intelligent.lock.model.http.ConstantUrl;
import com.hzwc.intelligent.lock.model.http.HttpService;

import com.hzwc.intelligent.lock.model.utils.SecurityRSA;

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

public class ChangeOldPwdRequest {
    private Call<BaseBean> mChangePwdCall;


    public void request(String token ,String mobile, String oldPassword ,String newPassword,String id, Callback<BaseBean> callback){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstantUrl.PUBLIC_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        HttpService apiService = retrofit.create(HttpService.class);
        String  oldPwd= SecurityRSA.encode(oldPassword);
        String  newPwd=SecurityRSA.encode(newPassword);
        mChangePwdCall = apiService.renameByPassword(token,SecurityRSA.encode(mobile), oldPwd,newPwd,id);
        mChangePwdCall.enqueue(callback);
    }

    public void interruptHttp(){
        if(mChangePwdCall != null && !mChangePwdCall.isCanceled()){
            mChangePwdCall.cancel();
        }
    }
}
