package com.hzwc.intelligent.lock.model.view.persenter;

import android.util.Log;

import com.hzwc.intelligent.lock.model.bean.MarkerBean;
import com.hzwc.intelligent.lock.model.bean.MessageBean;
import com.hzwc.intelligent.lock.model.http.request.LocationRequest;
import com.hzwc.intelligent.lock.model.http.request.MessageRequest;
import com.hzwc.intelligent.lock.model.view.view.AlarmView;
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

public class LocationPresenter extends BaseMvpPresenter<LocationView> {
    private final LocationRequest mLocationRequest;

    public LocationPresenter() {
        this.mLocationRequest = new LocationRequest();
    }

    public void clickLocationRequest(final String token, String cabinetName ) {
        if (getMvpView() != null) {
            getMvpView().requestLoading();
        }
        mLocationRequest.request(token, cabinetName , new Callback<MarkerBean>() {
            @Override
            public void onResponse(Call<MarkerBean> call, Response<MarkerBean> response) {
                if (getMvpView() != null) {
                    Log.e("awj",response.message()+"=====");
                    getMvpView().LocationSuccess(response.body());
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
    public void clickRimRequest(final String token, double locationX, double locationY ) {
        if (getMvpView() != null) {
            getMvpView().requestLoading();
        }
        mLocationRequest.requestRim(token, locationX,locationY , new Callback<MarkerBean>() {
            @Override
            public void onResponse(Call<MarkerBean> call, Response<MarkerBean> response) {
                if (getMvpView() != null) {

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
