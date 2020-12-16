package com.hzwc.intelligent.lock.model.view.persenter;

import android.util.Log;

import com.hzwc.intelligent.lock.model.bean.BaseBean;
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

public class ChangePwdPresenter extends BaseMvpPresenter<ChangePwdView> {
    private final ChangePasswordRequest mChangePwdRequest;

    public ChangePwdPresenter() {
        this.mChangePwdRequest = new ChangePasswordRequest();
    }

    public void clickMessageRequest(final String mobile, int verifyCode,String password,String id) {
        if (getMvpView() != null) {
            getMvpView().dataLoading();
        }
        mChangePwdRequest.request(mobile, verifyCode,password, id,new Callback<BaseBean>() {
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
