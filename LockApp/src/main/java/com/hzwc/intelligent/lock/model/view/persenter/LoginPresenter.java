package com.hzwc.intelligent.lock.model.view.persenter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;

import com.amap.api.maps.model.Marker;
import com.hzwc.intelligent.lock.model.activity.LoginActivity;
import com.hzwc.intelligent.lock.model.bean.LoginBean;
import com.hzwc.intelligent.lock.model.http.request.LoginRequest;
import com.hzwc.intelligent.lock.model.view.view.LoginView;
import com.hzwc.intelligent.lock.mvpframework.presenter.BaseMvpPresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Administrator on 2018-6-7 16:47:06
 */
public class LoginPresenter extends BaseMvpPresenter<LoginView> {
    private final LoginRequest mLoginRequest;

    public LoginPresenter() {
        this.mLoginRequest = new LoginRequest();
    }

    public void clickRequest(final String mobile, String password,String id,String rid) {
        if (getMvpView() != null) {
            getMvpView().requestLoading();
        }
        mLoginRequest.request(mobile, password,id,rid, new Callback<LoginBean>() {
            @Override
            public void onResponse(Call<LoginBean> call, Response<LoginBean> response) {
                if (getMvpView() != null) {
                    getMvpView().loginSuccess(response.body());
                }
            }


            @Override
            public void onFailure(Call<LoginBean> call, Throwable t) {
                if (getMvpView() != null) {
                    getMvpView().resultFailure(Log.getStackTraceString(t));
                }
            }
        });
    }


    public static void showDialog(Context context, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("未通过审核");
        builder.setMessage(msg);

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    @Override
    public void onDestroyPersenter() {
        super.onDestroyPersenter();
    }

    public void interruptHttp(){
        mLoginRequest.interruptHttp();
    }
}
