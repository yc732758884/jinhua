package com.hzwc.intelligent.lock.model.view.persenter;

import android.util.Log;

import com.hzwc.intelligent.lock.model.bean.BaseBean;
import com.hzwc.intelligent.lock.model.bean.LocationWarnsBean;
import com.hzwc.intelligent.lock.model.bean.NoCloseInfo;
import com.hzwc.intelligent.lock.model.bean.Page;
import com.hzwc.intelligent.lock.model.bean.PowerWarnsBean;
import com.hzwc.intelligent.lock.model.bean.RestsWarnBean;
import com.hzwc.intelligent.lock.model.bean.WarnTypesBean;
import com.hzwc.intelligent.lock.model.http.request.AlarmRequest;
import com.hzwc.intelligent.lock.model.view.view.AlarmView;
import com.hzwc.intelligent.lock.mvpframework.presenter.BaseMvpPresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * @author Administrator
 * @date 2018/1/15
 */

public class AlarmPresenter extends BaseMvpPresenter<AlarmView> {
    private final AlarmRequest mAlarmRequest;

    public AlarmPresenter() {
        this.mAlarmRequest = new AlarmRequest();
    }

    //获取告警类型
    public void getWarnType(String token) {
        if (getMvpView() != null) {
            getMvpView().dataLoading();
        }
        mAlarmRequest.getWarnType(token, new Callback<WarnTypesBean>() {
            @Override
            public void onResponse(Call<WarnTypesBean> call, Response<WarnTypesBean> response) {
                if (getMvpView() != null) {
                    getMvpView().getWarnTypeSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<WarnTypesBean> call, Throwable t) {
                if (getMvpView() != null) {
                    getMvpView().dataFailure(Log.getStackTraceString(t));
                }
            }
        });
    }

    //获取欠压告警
    public void getPowerWarn(String token, int page, int limit, int userId, int warnInfoId) {
        if (getMvpView() != null) {
            getMvpView().dataLoading();
        }
        mAlarmRequest.getPowerWarn(token, page, limit, userId, warnInfoId, new Callback<PowerWarnsBean>() {
            @Override
            public void onResponse(Call<PowerWarnsBean> call, Response<PowerWarnsBean> response) {
                if (getMvpView() != null) {
                    getMvpView().getPowerWarnSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<PowerWarnsBean> call, Throwable t) {
                if (getMvpView() != null) {
                    getMvpView().dataFailure(Log.getStackTraceString(t));
                }
            }
        });
    }

    //获取位置告警
    public void getLocationWarn(String token, int page, int limit, int userId, int warnInfoId) {
        if (getMvpView() != null) {
            getMvpView().dataLoading();
        }
        mAlarmRequest.getLocationWarn(token, page, limit, userId, warnInfoId, new Callback<LocationWarnsBean>() {
            @Override
            public void onResponse(Call<LocationWarnsBean> call, Response<LocationWarnsBean> response) {
                if (getMvpView() != null) {
                    getMvpView().getLocationWarnSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<LocationWarnsBean> call, Throwable t) {
                if (getMvpView() != null) {
                    getMvpView().dataFailure(Log.getStackTraceString(t));
                }
            }
        });
    }

    //获取其他告警
    public void getRestsWarn(String token, int page, int limit, int userId, int warnInfoId) {
        if (getMvpView() != null) {
            getMvpView().dataLoading();
        }
        mAlarmRequest.getRestsWarn(token, page, limit, userId, warnInfoId, new Callback<RestsWarnBean>() {
            @Override
            public void onResponse(Call<RestsWarnBean> call, Response<RestsWarnBean> response) {
                if (getMvpView() != null) {
                    getMvpView().getRestsWarnSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<RestsWarnBean> call, Throwable t) {
                if (getMvpView() != null) {
                    getMvpView().dataFailure(Log.getStackTraceString(t));
                }
            }
        });
    }

    //获取锁未关告警


    public void getNoCloseWarn(String token, int page, int limit, int userId, int warnInfoId) {
        if (getMvpView() != null) {
            getMvpView().dataLoading();
        }
        mAlarmRequest.getNoCloseWarn(token, page, limit, userId, warnInfoId, new Callback<BaseBean<Page<NoCloseInfo>>>() {
            @Override
            public void onResponse(Call<BaseBean<Page<NoCloseInfo>>> call, Response<BaseBean<Page<NoCloseInfo>>> response) {
                if (getMvpView() != null) {
                    getMvpView().getNoCloseWarnSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<BaseBean<Page<NoCloseInfo>>> call, Throwable t) {
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
        mAlarmRequest.interruptHttp();
    }
}
