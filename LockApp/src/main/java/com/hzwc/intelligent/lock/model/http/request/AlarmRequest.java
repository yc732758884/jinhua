package com.hzwc.intelligent.lock.model.http.request;


import android.util.Log;

import com.hzwc.intelligent.lock.model.bean.BaseBean;
import com.hzwc.intelligent.lock.model.bean.LocationWarnsBean;
import com.hzwc.intelligent.lock.model.bean.NoCloseInfo;
import com.hzwc.intelligent.lock.model.bean.Page;
import com.hzwc.intelligent.lock.model.bean.PowerWarnsBean;
import com.hzwc.intelligent.lock.model.bean.RestsWarnBean;
import com.hzwc.intelligent.lock.model.bean.WarnTypesBean;
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
 * @author Administrator
 * @date 2018/1/15
 */

public class AlarmRequest {
    private Call<WarnTypesBean> mWarnTypeCall;
    private Call<PowerWarnsBean> mPowerWarnsCall;
    private Call<LocationWarnsBean> mLocationWarnsCall;
    private Call<RestsWarnBean> mRestsWarnCall;
    private  Call<BaseBean<Page<NoCloseInfo>>>  noclosecall;
    public void getWarnType(String token, Callback<WarnTypesBean> callback) {



        HttpLoggingInterceptor.Level level = HttpLoggingInterceptor.Level.BODY;

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {

                Log.e("warntype", "OkHttp====Message:" + message);

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
        mWarnTypeCall = apiService.getWarnType(token);
        mWarnTypeCall.enqueue(callback);
    }

    public void getPowerWarn(String token, int page, int limit, int userId, int warnInfoId, Callback<PowerWarnsBean> callback) {
        HttpLoggingInterceptor.Level level = HttpLoggingInterceptor.Level.BODY;

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {

                Log.e("pow", "OkHttp====Message:" + message);

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
        mPowerWarnsCall = apiService.getPowerWarn(token, page, limit, userId, warnInfoId);
        mPowerWarnsCall.enqueue(callback);
    }

    public void getLocationWarn(String token, int page, int limit, int userId, int warnInfoId, Callback<LocationWarnsBean> callback) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstantUrl.PUBLIC_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        HttpService apiService = retrofit.create(HttpService.class);
        mLocationWarnsCall = apiService.getLocationWarn(token, page, limit, userId, warnInfoId);
        mLocationWarnsCall.enqueue(callback);
    }

    public void getRestsWarn(String token, int page, int limit, int userId, int warnInfoId, Callback<RestsWarnBean> callback) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstantUrl.PUBLIC_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        HttpService apiService = retrofit.create(HttpService.class);
        mRestsWarnCall = apiService.getRestsWarn(token, page, limit, userId, warnInfoId);
        mRestsWarnCall.enqueue(callback);
    }

    public void getNoCloseWarn(String token, int page, int limit, int userId, int warnInfoId, Callback<BaseBean<Page<NoCloseInfo>>> callback) {




        HttpLoggingInterceptor.Level level = HttpLoggingInterceptor.Level.BODY;

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {

                Log.e("warn", "OkHttp====Message:" + message);

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
        noclosecall = apiService.getNoCloseWarn(token, page, limit, userId, warnInfoId);
        noclosecall.enqueue(callback);
    }




    public void interruptHttp() {
        if (mWarnTypeCall != null && !mWarnTypeCall.isCanceled()) {
            mWarnTypeCall.cancel();
        }
    }
}
