package com.hzwc.intelligent.lock.model.http.request;


import com.hzwc.intelligent.lock.model.bean.BaseBean;
import com.hzwc.intelligent.lock.model.bean.LoginBean;
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

public class InstallLockInfoRequest {
    private Call<BaseBean> mInstallLockInfoCall;

    public void request(String token,String lockNo,int lockId,int cabinetId , Callback<BaseBean> callback){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstantUrl.PUBLIC_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        HttpService apiService = retrofit.create(HttpService.class);
        mInstallLockInfoCall = apiService.updateLocks(token, lockNo, lockId,cabinetId);
        mInstallLockInfoCall.enqueue(callback);
    }

    public void interruptHttp(){
        if(mInstallLockInfoCall != null && !mInstallLockInfoCall.isCanceled()){
            mInstallLockInfoCall.cancel();
        }
    }
}
