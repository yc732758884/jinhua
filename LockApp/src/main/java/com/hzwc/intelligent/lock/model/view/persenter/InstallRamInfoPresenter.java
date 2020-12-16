package com.hzwc.intelligent.lock.model.view.persenter;

import android.util.Log;

import com.hzwc.intelligent.lock.model.bean.AdCodeBean;
import com.hzwc.intelligent.lock.model.bean.BaseBean;
import com.hzwc.intelligent.lock.model.http.request.AdCodeRequest;
import com.hzwc.intelligent.lock.model.http.request.InstallLockInfoRequest;
import com.hzwc.intelligent.lock.model.http.request.InstallRamInfoRequest;
import com.hzwc.intelligent.lock.model.view.view.InstallInfoLockView;
import com.hzwc.intelligent.lock.model.view.view.InstallRamInfoView;
import com.hzwc.intelligent.lock.mvpframework.presenter.BaseMvpPresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Administrator on 2018-6-7 16:47:06
 */
public class InstallRamInfoPresenter extends BaseMvpPresenter<InstallRamInfoView> {
    private final InstallRamInfoRequest mInstallRamInfoRequest;

    public InstallRamInfoPresenter() {
        this.mInstallRamInfoRequest = new InstallRamInfoRequest();
    }

    public void clickRequest(final String token,String addr,int cabinetId,String cabinetName,double locationX,double locationY,int areaId) {
        if (getMvpView() != null) {
            getMvpView().requestLoading();
        }
        mInstallRamInfoRequest.request(token, addr,  cabinetId,cabinetName,locationX,locationY,areaId,new Callback<BaseBean>() {
            @Override
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                if (getMvpView() != null) {
                    getMvpView().installRamInfoSuccess(response.body());
                }
            }


            @Override
            public void onFailure(Call<BaseBean> call, Throwable t) {
                if (getMvpView() != null) {
                    getMvpView().resultFailure(Log.getStackTraceString(t));
                }
            }
        });
    }



    public void clickCodeRequest(final String token, String adcode) {
        if (getMvpView() != null) {
            getMvpView().requestLoading();
        }
        mInstallRamInfoRequest.requestCode(token,adcode , new Callback<AdCodeBean>() {
            @Override
            public void onResponse(Call<AdCodeBean> call, Response<AdCodeBean> response) {
                if (getMvpView() != null) {
                    Log.e("awj",response.message()+"=====");
                    getMvpView().dataSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<AdCodeBean> call, Throwable t) {
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
        mInstallRamInfoRequest.interruptHttp();
    }
}
