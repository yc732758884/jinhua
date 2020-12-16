package com.hzwc.intelligent.lock.model.view.persenter;

import android.util.Log;

import com.hzwc.intelligent.lock.model.bean.BaseBean;
import com.hzwc.intelligent.lock.model.http.request.ChangeOldPwdRequest;
import com.hzwc.intelligent.lock.model.http.request.ChangePasswordRequest;
import com.hzwc.intelligent.lock.model.view.view.ChangePwdView;
import com.hzwc.intelligent.lock.mvpframework.presenter.BaseMvpPresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 *
 * @author Administrator
 * @date 2018/1/15
 */

public class ChangeOldPsdPresenter extends BaseMvpPresenter<ChangePwdView> {
    private final ChangeOldPwdRequest mChangePwdRequest;

    public ChangeOldPsdPresenter() {
        this.mChangePwdRequest = new ChangeOldPwdRequest();
    }

    public void clickMessageRequest(String token ,String mobile, String oldPassword ,String newPassword,String id) {
        if (getMvpView() != null) {
            getMvpView().dataLoading();
        }
        mChangePwdRequest.request(token,mobile, oldPassword,newPassword,id, new Callback<BaseBean>() {
            @Override
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                if (getMvpView() != null) {
                    Log.e("awj",response.message()+"=====");
                    getMvpView().dataSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<BaseBean> call, Throwable t) {
                if (getMvpView() != null) {
                    getMvpView().dataFailure(Log.getStackTraceString(t));
                }
            }
        });
    }
    @Override
    public void onDestroyPersenter() {
        super.onDestroyPersenter();
    }

    public void interruptHttp(){
        mChangePwdRequest.interruptHttp();
    }
}
