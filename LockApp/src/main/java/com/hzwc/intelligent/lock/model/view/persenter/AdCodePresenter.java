package com.hzwc.intelligent.lock.model.view.persenter;

import android.util.Log;

import com.hzwc.intelligent.lock.model.bean.AdCodeBean;
import com.hzwc.intelligent.lock.model.bean.BaseBean;
import com.hzwc.intelligent.lock.model.http.request.AdCodeRequest;
import com.hzwc.intelligent.lock.model.http.request.ChangePasswordRequest;
import com.hzwc.intelligent.lock.model.view.view.AdCodeView;
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

public class AdCodePresenter extends BaseMvpPresenter<AdCodeView> {
    private final AdCodeRequest mAdCodeRequest;

    public AdCodePresenter() {
        this.mAdCodeRequest = new AdCodeRequest();
    }

    public void clickMessageRequest(final String token, String adcode) {


        if (getMvpView() != null) {
            getMvpView().dataLoading();
        }
        mAdCodeRequest.request(token,adcode , new Callback<AdCodeBean>() {
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
        mAdCodeRequest.interruptHttp();
    }
}
