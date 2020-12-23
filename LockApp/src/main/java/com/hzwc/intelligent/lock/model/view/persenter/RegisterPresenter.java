package com.hzwc.intelligent.lock.model.view.persenter;

import android.util.Log;

import com.hzwc.intelligent.lock.model.bean.BaseBean;
import com.hzwc.intelligent.lock.model.bean.RegisterBean;
import com.hzwc.intelligent.lock.model.http.request.RegisterRequest;
import com.hzwc.intelligent.lock.model.view.view.RegisterView;
import com.hzwc.intelligent.lock.mvpframework.presenter.BaseMvpPresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 *
 * @author Administrator
 * @date 2018/1/15
 */

public class RegisterPresenter extends BaseMvpPresenter<RegisterView> {
    private final RegisterRequest mRegisterRequest;

    public RegisterPresenter() {
        this.mRegisterRequest = new RegisterRequest();
    }

    //发送验证码
    public void sendMessage(String token, String phonenumber, String sendDepartment) {
        if (getMvpView() != null) {
            getMvpView().dataLoading();
        }
        mRegisterRequest.sendMessage(token, phonenumber, sendDepartment, new Callback<RegisterBean>() {
            @Override
            public void onResponse(Call<RegisterBean> call, Response<RegisterBean> response) {
                if (getMvpView() != null) {
                    getMvpView().sendMessageSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<RegisterBean> call, Throwable t) {
                if (getMvpView() != null) {
                    getMvpView().dataFailure(Log.getStackTraceString(t));
                }
            }
        });
    }

    //验证验证码
    public void verification(String token, String phonenumber, int verifyCode) {
        if (getMvpView() != null) {
            getMvpView().dataLoading();
        }
        mRegisterRequest.verification(token, phonenumber, verifyCode,new Callback<BaseBean>() {
            @Override
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                if (getMvpView() != null) {
                    getMvpView().verificationSuccess(response.body());
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
        mRegisterRequest.interruptHttp();
    }

}
