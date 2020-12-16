package com.hzwc.intelligent.lock.model.view.persenter;

import android.util.Log;

import com.hzwc.intelligent.lock.model.bean.UserLockBean;
import com.hzwc.intelligent.lock.model.bean.UserRamBean;
import com.hzwc.intelligent.lock.model.http.request.UserLockRequest;
import com.hzwc.intelligent.lock.model.http.request.UserRamRequest;
import com.hzwc.intelligent.lock.model.view.view.UserLockView;
import com.hzwc.intelligent.lock.model.view.view.UserRamView;
import com.hzwc.intelligent.lock.mvpframework.presenter.BaseMvpPresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Administrator on 2018-6-7 16:47:06
 */
public class UserRamPresenter extends BaseMvpPresenter<UserRamView> {
    private final UserRamRequest mUserRamRequest;

    public UserRamPresenter() {
        this.mUserRamRequest = new UserRamRequest();
    }

    public void clickRequest(final String token, int userId) {
        if (getMvpView() != null) {
            getMvpView().requestLoading();
        }
        mUserRamRequest.request(token, userId, new Callback<UserRamBean>() {
            @Override
            public void onResponse(Call<UserRamBean> call, Response<UserRamBean> response) {
                if (getMvpView() != null) {
                    getMvpView().userRamSuccess(response.body());
                }
            }


            @Override
            public void onFailure(Call<UserRamBean> call, Throwable t) {
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

    public void interruptHttp() {
        mUserRamRequest.interruptHttp();
    }
}
