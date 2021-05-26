package com.hzwc.intelligent.lock.model.http.request;


import android.util.Log;

import com.hzwc.intelligent.lock.model.bean.BaseBean;
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

public class OpenLockActivityRequest {
    private Call<BaseBean> mUnlockCall;
    private Call<BaseBean> mLockCall;

    private Call<BaseBean> afterOpen;
    private Call<BaseBean> save;
    public void unlockAfter(String token, int lockNo, int userId, int power, Double lon, Double lat, Callback<BaseBean> callback) {



        Retrofit retrofit = new Retrofit.Builder()

                .baseUrl(ConstantUrl.PUBLIC_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        HttpService apiService = retrofit.create(HttpService.class);
        mUnlockCall = apiService.unlockAfter(token, lockNo, userId, power, lon, lat);
        mUnlockCall.enqueue(callback);
    }

    public void unlockUpLoad(String token, String lockNo, String userId, String power, Callback<BaseBean> callback) {





        Retrofit retrofit = new Retrofit.Builder()

                .baseUrl(ConstantUrl.PUBLIC_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        HttpService apiService = retrofit.create(HttpService.class);
        save = apiService.closeLock(token, lockNo, userId, power);
        save.enqueue(callback);
    }


    public void saveLockLog(String token, String lockNo, String userId, String data, Callback<BaseBean> callback) {



        Retrofit retrofit = new Retrofit.Builder()

                .baseUrl(ConstantUrl.PUBLIC_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        HttpService apiService = retrofit.create(HttpService.class);
        afterOpen = apiService.saveLockLog(token, lockNo, userId, data);
        afterOpen.enqueue(callback);
    }




    public void lockRecord(String token, String lockNo, int userId, Callback<BaseBean> callback) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstantUrl.PUBLIC_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        HttpService apiService = retrofit.create(HttpService.class);
        mLockCall = apiService.lockRecord(token, lockNo, userId);
        mLockCall.enqueue(callback);
    }

    public void interruptHttp() {
        if (mUnlockCall != null && !mUnlockCall.isCanceled()) {
            mUnlockCall.cancel();
        }
    }
}
