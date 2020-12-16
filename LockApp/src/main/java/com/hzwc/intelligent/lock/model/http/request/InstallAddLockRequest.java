package com.hzwc.intelligent.lock.model.http.request;


import android.util.Log;

import com.hzwc.intelligent.lock.model.bean.BaseBean;
import com.hzwc.intelligent.lock.model.bean.LockMacBean;
import com.hzwc.intelligent.lock.model.bean.MarkerBean;
import com.hzwc.intelligent.lock.model.http.ConstantUrl;
import com.hzwc.intelligent.lock.model.http.HttpService;

import java.util.HashMap;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * @author Administrator
 * @date 2018/1/15
 */

public class InstallAddLockRequest {
    private Call<BaseBean> mInstallLockCall;
    private Call<MarkerBean> mLocationCall;
    private Call<LockMacBean> mLockMacCall;

    public void addLockRequest(String token, HashMap<String, String> cabinetId, HashMap<String, String> cabinetNo, int userId, Callback<BaseBean> callback) {



        HttpLoggingInterceptor.Level level = HttpLoggingInterceptor.Level.BODY;

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {

                Log.e("install", "OkHttp====Message:" + message);

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
        mInstallLockCall = apiService.installLocks(token, cabinetId, cabinetNo,userId);
        mInstallLockCall.enqueue(callback);
    }


    public void request(String mobile, String cabinetName, Callback<MarkerBean> callback) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstantUrl.PUBLIC_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        HttpService apiService = retrofit.create(HttpService.class);
        mLocationCall = apiService.searchCabinetsByNo(mobile, cabinetName);
        mLocationCall.enqueue(callback);
    }

    public void requestRim(String mobile, double locationX, double locationY, Callback<MarkerBean> callback) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstantUrl.PUBLIC_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        HttpService apiService = retrofit.create(HttpService.class);
        mLocationCall = apiService.getCabinetsByLocation(mobile, locationX, locationY);
        mLocationCall.enqueue(callback);
    }

    public void requestMac(String mobile, String lockNo, Callback<LockMacBean> callback) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstantUrl.PUBLIC_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        HttpService apiService = retrofit.create(HttpService.class);
        mLockMacCall = apiService.getLockNoByCode(mobile, lockNo);
        mLockMacCall.enqueue(callback);
    }

    public void requestCheckLockNo(String mobile, String lockNo, Callback<ResponseBody> callback) {




        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstantUrl.PUBLIC_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
         HttpService apiService = retrofit.create(HttpService.class);
         apiService.checkLockNo(mobile, lockNo).enqueue(callback);

    }



    public void interruptHttp() {
        if (mInstallLockCall != null && !mInstallLockCall.isCanceled()) {
            mInstallLockCall.cancel();
        }

        if (mLocationCall != null && !mLocationCall.isCanceled()) {
            mLocationCall.cancel();
        }
        if (mLockMacCall != null && !mLockMacCall.isCanceled()) {
            mLockMacCall.cancel();
        }
    }
}
