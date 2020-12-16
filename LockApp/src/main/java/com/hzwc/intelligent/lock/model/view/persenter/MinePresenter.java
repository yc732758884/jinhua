package com.hzwc.intelligent.lock.model.view.persenter;

import android.util.Log;

import com.hzwc.intelligent.lock.model.bean.LoginBean;
import com.hzwc.intelligent.lock.model.bean.MineBean;
import com.hzwc.intelligent.lock.model.http.request.LoginRequest;
import com.hzwc.intelligent.lock.model.http.request.MineRequest;
import com.hzwc.intelligent.lock.model.view.view.LoginView;
import com.hzwc.intelligent.lock.model.view.view.MineView;
import com.hzwc.intelligent.lock.mvpframework.presenter.BaseMvpPresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Administrator on 2018-6-7 16:47:06
 */
public class MinePresenter extends BaseMvpPresenter<MineView> {
    private final MineRequest mMineRequest;

    public MinePresenter() {
        this.mMineRequest = new MineRequest();
    }

    public void clickRequest(String token) {
        if (getMvpView() != null) {
            getMvpView().requestLoading();
        }
        mMineRequest.request(token, new Callback<MineBean>() {
            @Override
            public void onResponse(Call<MineBean> call, Response<MineBean> response) {
                if (getMvpView() != null) {
                    getMvpView().mineSuccess(response.body());
                }
            }


            @Override
            public void onFailure(Call<MineBean> call, Throwable t) {
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
        mMineRequest.interruptHttp();
    }
}
