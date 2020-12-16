package com.hzwc.intelligent.lock.model.http.request;


import com.hzwc.intelligent.lock.model.bean.BaseBean;
import com.hzwc.intelligent.lock.model.bean.LockByIdBean;
import com.hzwc.intelligent.lock.model.bean.MarkerBean;
import com.hzwc.intelligent.lock.model.bean.WarnTypesBean;
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

public class AddOtherWarnRequest {
    private Call<LockByIdBean> mInstallLockCall;
    private Call<MarkerBean> mLocationCall;
    private Call<WarnTypesBean> mWarnTypeCall;
    private Call<BaseBean> mInstallLocationWarnCall;

    public void getWarnType(String token, Callback<WarnTypesBean> callback) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstantUrl.PUBLIC_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        HttpService apiService = retrofit.create(HttpService.class);
        mWarnTypeCall = apiService.getWarnType(token);
        mWarnTypeCall.enqueue(callback);
    }

    public void installLocationWarn(String token, String lockId, int userId, int cabinetId, int warnInfoId, double locationLon, double locationLat, String infos, String details, Callback<BaseBean> callback) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstantUrl.PUBLIC_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        HttpService apiService = retrofit.create(HttpService.class);
        mInstallLocationWarnCall = apiService.installLocationWarn(token, lockId, userId, cabinetId, warnInfoId, locationLon, locationLat, infos, details);
        mInstallLocationWarnCall.enqueue(callback);
    }

    public void installPowerWarn(String token, String lockId, int userId, int cabinetId, int warnInfoId, int power, String infos, String details, Callback<BaseBean> callback) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstantUrl.PUBLIC_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        HttpService apiService = retrofit.create(HttpService.class);
        mInstallLocationWarnCall = apiService.installPowerWarn(token, lockId, userId, cabinetId, warnInfoId, infos, power, details);
        mInstallLocationWarnCall.enqueue(callback);
    }

    public void installRestsWarn(String token, String lockId, int userId, int cabinetId, int warnInfoId, String infos, String details, Callback<BaseBean> callback) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstantUrl.PUBLIC_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        HttpService apiService = retrofit.create(HttpService.class);
        mInstallLocationWarnCall = apiService.installRestsWarn(token, lockId, userId, cabinetId, warnInfoId, infos, details);
        mInstallLocationWarnCall.enqueue(callback);
    }


    public void searchCabinetsByNo(String mobile, int id, Callback<LockByIdBean> callback) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstantUrl.PUBLIC_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        HttpService apiService = retrofit.create(HttpService.class);
        mInstallLockCall = apiService.getCabinetsById(mobile, id);
        mInstallLockCall.enqueue(callback);
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

    //    public void interruptHttp() {
//        if (mLocationCall != null && !mLocationCall.isCanceled()) {
//            mLocationCall.cancel();
//        }
//    }
    public void interruptHttp() {
        if (mInstallLockCall != null && !mInstallLockCall.isCanceled()) {
            mInstallLockCall.cancel();
        }

        if (mLocationCall != null && !mLocationCall.isCanceled()) {
            mLocationCall.cancel();
        }
    }
}
