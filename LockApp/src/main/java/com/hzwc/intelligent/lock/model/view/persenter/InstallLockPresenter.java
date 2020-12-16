package com.hzwc.intelligent.lock.model.view.persenter;

import android.util.Log;

import com.hzwc.intelligent.lock.model.bean.MarkerBean;
import com.hzwc.intelligent.lock.model.http.request.LocationRequest;
import com.hzwc.intelligent.lock.model.view.view.InstallLockView;
import com.hzwc.intelligent.lock.model.view.view.LocationView;
import com.hzwc.intelligent.lock.mvpframework.presenter.BaseMvpPresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 *
 * @author Administrator
 * @date 2018/1/15
 */

public class InstallLockPresenter extends BaseMvpPresenter<InstallLockView> {
    private final LocationRequest mLocationRequest;

    public InstallLockPresenter() {
        this.mLocationRequest = new LocationRequest();
    }


    public void clickRimRequest(final String token, double locationX, double locationY ) {
        if (getMvpView() != null) {
            getMvpView().requestLoading();
        }
        mLocationRequest.requestRim(token, locationX,locationY , new Callback<MarkerBean>() {
            @Override
            public void onResponse(Call<MarkerBean> call, Response<MarkerBean> response) {
                if (getMvpView() != null) {
                    Log.e("awj",response.message()+"=====");
                    getMvpView().RimSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<MarkerBean> call, Throwable t) {
                if (getMvpView() != null) {
                    getMvpView().resultFailure(Log.getStackTraceString(t));
                }
            }
        });
    }
    @Override
    public void onDestroyPersenter() {
        super.onDestroyPersenter();
    }

    public void interruptHttp(){
        mLocationRequest.interruptHttp();
    }
}
