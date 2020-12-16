package com.hzwc.intelligent.lock.model.view.persenter;

import android.util.Log;

import com.google.gson.Gson;
import com.hzwc.intelligent.lock.model.bean.BaseBean;
import com.hzwc.intelligent.lock.model.bean.InstallInfo;
import com.hzwc.intelligent.lock.model.bean.LockMacBean;
import com.hzwc.intelligent.lock.model.bean.MarkerBean;
import com.hzwc.intelligent.lock.model.http.request.InstallAddLockRequest;
import com.hzwc.intelligent.lock.model.http.request.LocationRequest;
import com.hzwc.intelligent.lock.model.view.view.InstallAddLockView;
import com.hzwc.intelligent.lock.mvpframework.presenter.BaseMvpPresenter;


import java.io.IOException;
import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Administrator on 2018-6-7 16:47:06
 */
public class InstallAddLockPresenter extends BaseMvpPresenter<InstallAddLockView> {
    private final InstallAddLockRequest mInstallLockRequest;


    public InstallAddLockPresenter() {
        this.mInstallLockRequest = new InstallAddLockRequest();
    }

    public void clickRequest(final String token, HashMap<String, String> cabinetId, HashMap<String, String> cabinetNo,int userId) {
        if (getMvpView() != null) {
            getMvpView().requestLoading();
        }
        mInstallLockRequest.addLockRequest(token, cabinetId, cabinetNo, userId,new Callback<BaseBean>() {
            @Override
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                if (getMvpView() != null) {
                    getMvpView().installAddLockSuccess(response.body());
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

    public  void checkLockNo(String token,String str){
        mInstallLockRequest.requestCheckLockNo(token, str, new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    String  res=response.body().string();
                    Log.e("res",res);

                     InstallInfo   ii=new Gson().fromJson(res,InstallInfo.class);
                     getMvpView().installSuccess(str,ii.isData());

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {



            }
        });




    }





    public void clickRimRequest(final String token, double locationX, double locationY ) {
        if (getMvpView() != null) {
            getMvpView().requestLoading();
        }
        mInstallLockRequest.requestRim(token, locationX,locationY , new Callback<MarkerBean>() {
            @Override
            public void onResponse(Call<MarkerBean> call, Response<MarkerBean> response) {
                if (getMvpView() != null) {
                    Log.e("awj",response.message()+"=====");
                    getMvpView().RimSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<MarkerBean> call, Throwable t) {
                if (getMvpView() != null) {
                    getMvpView().resultFailure(Log.getStackTraceString(t));
                }
            }
        });
    }

    public void clickMacRequest(final String token, String lockCode ) {
        if (getMvpView() != null) {
            getMvpView().requestLoading();
        }
        mInstallLockRequest.requestMac(token, lockCode , new Callback<LockMacBean>() {
            @Override
            public void onResponse(Call<LockMacBean> call, Response<LockMacBean> response) {
                if (getMvpView() != null) {
                    Log.e("awj",response.message()+"=====");
                    getMvpView().macSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<LockMacBean> call, Throwable t) {
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
        mInstallLockRequest.interruptHttp();
    }
}
