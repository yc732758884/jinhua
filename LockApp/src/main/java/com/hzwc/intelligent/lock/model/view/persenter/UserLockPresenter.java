package com.hzwc.intelligent.lock.model.view.persenter;

import android.util.Log;

import com.hzwc.intelligent.lock.model.bean.LoginBean;
import com.hzwc.intelligent.lock.model.bean.UserLockBean;
import com.hzwc.intelligent.lock.model.http.request.LoginRequest;
import com.hzwc.intelligent.lock.model.http.request.UserLockRequest;
import com.hzwc.intelligent.lock.model.view.view.LoginView;
import com.hzwc.intelligent.lock.model.view.view.UserLockView;
import com.hzwc.intelligent.lock.mvpframework.presenter.BaseMvpPresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Administrator on 2018-6-7 16:47:06
 */
public class UserLockPresenter extends BaseMvpPresenter<UserLockView> {
    private final UserLockRequest mUserLockRequest;

    public UserLockPresenter() {
        this.mUserLockRequest = new UserLockRequest();
    }

    public void clickRequest(final String token, int userId) {
        if (getMvpView() != null) {
            getMvpView().requestLoading();
        }
        mUserLockRequest.request(token, userId, new Callback<UserLockBean>() {
            @Override
            public void onResponse(Call<UserLockBean> call, Response<UserLockBean> response) {
                if (getMvpView() != null) {
                    getMvpView().userLockSuccess(response.body());
                }
            }


            @Override
            public void onFailure(Call<UserLockBean> call, Throwable t) {
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
        mUserLockRequest.interruptHttp();
    }

}
