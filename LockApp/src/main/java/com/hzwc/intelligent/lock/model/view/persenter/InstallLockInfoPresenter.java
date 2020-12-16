package com.hzwc.intelligent.lock.model.view.persenter;

import android.util.Log;

import com.hzwc.intelligent.lock.model.bean.BaseBean;
import com.hzwc.intelligent.lock.model.bean.LoginBean;
import com.hzwc.intelligent.lock.model.http.request.InstallLockInfoRequest;
import com.hzwc.intelligent.lock.model.http.request.LoginRequest;
import com.hzwc.intelligent.lock.model.view.view.InstallInfoLockView;
import com.hzwc.intelligent.lock.model.view.view.LoginView;
import com.hzwc.intelligent.lock.mvpframework.presenter.BaseMvpPresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Administrator on 2018-6-7 16:47:06
 */
public class InstallLockInfoPresenter extends BaseMvpPresenter<InstallInfoLockView> {
    private final InstallLockInfoRequest mInstallLockInfoRequest;

    public InstallLockInfoPresenter() {
        this.mInstallLockInfoRequest = new InstallLockInfoRequest();
    }

    public void clickRequest(final String token,String lockNo,int lockId,int cabinetId) {
        if (getMvpView() != null) {
            getMvpView().requestLoading();
        }
        mInstallLockInfoRequest.request(token, lockNo, lockId,cabinetId,new Callback<BaseBean>() {
            @Override
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                if (getMvpView() != null) {
                    getMvpView().installLockInfoSuccess(response.body());
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
    @Override
    public void onDestroyPersenter() {
        super.onDestroyPersenter();
    }

    public void interruptHttp(){
        mInstallLockInfoRequest.interruptHttp();
    }

}
