package com.hzwc.intelligent.lock.model.http.request;


import android.util.Log;

import com.hzwc.intelligent.lock.model.bean.BaseBean;
import com.hzwc.intelligent.lock.model.bean.UnlocksBean;
import com.hzwc.intelligent.lock.model.bean.Update;
import com.hzwc.intelligent.lock.model.http.ConstantUrl;
import com.hzwc.intelligent.lock.model.http.HttpService;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * @author Administrator
 * @date 2018/1/15
 */

public class OpenLockRequest {
    private Call<UnlocksBean> mUnlockCall;
    private Call<BaseBean> mUnlockApplyCall;
    private Call<Update> update;


    public void unlock(String token, String lockNo, int userId, Callback<UnlocksBean> callback) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstantUrl.PUBLIC_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        HttpService apiService = retrofit.create(HttpService.class);
        mUnlockCall = apiService.unlock(token, lockNo, userId);
        mUnlockCall.enqueue(callback);
    }

    public void unlockApply(String token, String lockNo, int userId, Callback<BaseBean> callback) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstantUrl.PUBLIC_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        HttpService apiService = retrofit.create(HttpService.class);
        mUnlockApplyCall = apiService.unlockApply(token, lockNo, userId);
        mUnlockApplyCall.enqueue(callback);
    }


    public void update(Callback<Update> callback) {


        Retrofit retrofit = new Retrofit.Builder()

                .baseUrl(ConstantUrl.PUBLIC_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        HttpService apiService = retrofit.create(HttpService.class);
        update = apiService.update();
        update.enqueue(callback);
    }




    public void interruptHttp() {
        if (mUnlockCall != null && !mUnlockCall.isCanceled()) {
            mUnlockCall.cancel();
        }
    }
}
