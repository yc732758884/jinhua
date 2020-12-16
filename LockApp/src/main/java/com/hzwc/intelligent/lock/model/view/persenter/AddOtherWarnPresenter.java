package com.hzwc.intelligent.lock.model.view.persenter;

import android.util.Log;

import com.google.gson.GsonBuilder;
import com.hzwc.intelligent.lock.model.bean.BaseBean;
import com.hzwc.intelligent.lock.model.bean.LockByIdBean;
import com.hzwc.intelligent.lock.model.bean.MarkerBean;
import com.hzwc.intelligent.lock.model.bean.WarnTypesBean;
import com.hzwc.intelligent.lock.model.bean.WebIsWarnBean;
import com.hzwc.intelligent.lock.model.bean.WebLocationWarningBean;
import com.hzwc.intelligent.lock.model.bean.WebPowerWarnBean;
import com.hzwc.intelligent.lock.model.http.request.AddOtherWarnRequest;
import com.hzwc.intelligent.lock.model.http.stomp.RestClient;
import com.hzwc.intelligent.lock.model.http.stomp.Stomp;
import com.hzwc.intelligent.lock.model.http.stomp.StompClient;
import com.hzwc.intelligent.lock.model.view.view.AddOtherWarnView;
import com.hzwc.intelligent.lock.mvpframework.presenter.BaseMvpPresenter;

import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.hzwc.intelligent.lock.model.http.stomp.RestClient.ANDROID_EMULATOR_LOCALHOST;


/**
 * Created by Administrator on 2018-6-7 16:47:06
 */
public class AddOtherWarnPresenter extends BaseMvpPresenter<AddOtherWarnView> {
    private final AddOtherWarnRequest mAddOtherWarnRequest;


    public AddOtherWarnPresenter() {
        this.mAddOtherWarnRequest = new AddOtherWarnRequest();
    }


    public void clickRimRequest(final String token, double locationX, double locationY) {
        if (getMvpView() != null) {
            getMvpView().requestLoading();
        }
        mAddOtherWarnRequest.requestRim(token, locationX, locationY, new Callback<MarkerBean>() {
            @Override
            public void onResponse(Call<MarkerBean> call, Response<MarkerBean> response) {
                if (getMvpView() != null) {
                    Log.e("awj", response.message() + "=====");
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

    //搜索环网柜
    public void searchCabinetsByNo(String mobile, int id) {
        if (getMvpView() != null) {
            getMvpView().requestLoading();
        }
        mAddOtherWarnRequest.searchCabinetsByNo(mobile, id, new Callback<LockByIdBean>() {
            @Override
            public void onResponse(Call<LockByIdBean> call, Response<LockByIdBean> response) {
                if (getMvpView() != null) {
                    Log.e("awj", response.message() + "=====");
                    getMvpView().searchCabinetsByNoSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<LockByIdBean> call, Throwable t) {
                if (getMvpView() != null) {
                    getMvpView().resultFailure(Log.getStackTraceString(t));
                }
            }
        });
    }

    //获取告警类型
    public void getWarnType(String token) {
        if (getMvpView() != null) {
            getMvpView().requestLoading();
        }
        mAddOtherWarnRequest.getWarnType(token, new Callback<WarnTypesBean>() {
            @Override
            public void onResponse(Call<WarnTypesBean> call, Response<WarnTypesBean> response) {
                if (getMvpView() != null) {
                    getMvpView().getWarnTypeSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<WarnTypesBean> call, Throwable t) {
                if (getMvpView() != null) {
                    getMvpView().resultFailure(Log.getStackTraceString(t));
                }
            }
        });
    }

    //添加位置告警
    public void installLocationWarn(String token, String lockId, int userId, int cabinetId, int warnInfoId, double locationLon, double locationLat, String infos, String details) {
        if (getMvpView() != null) {
            getMvpView().requestLoading();
        }
        mAddOtherWarnRequest.installLocationWarn(token, lockId, userId, cabinetId, warnInfoId, locationLon, locationLat, infos, details, new Callback<BaseBean>() {
            @Override
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                if (getMvpView() != null) {
                    getMvpView().installLocationWarnSuccess(response.body());
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

    //添加电量告警
    public void installPowerWarn(String token, String lockId, int userId, int cabinetId, int warnInfoId, String infos, int power, String details) {
        if (getMvpView() != null) {
            getMvpView().requestLoading();
        }
        mAddOtherWarnRequest.installPowerWarn(token, lockId, userId, cabinetId, warnInfoId, power, infos, details, new Callback<BaseBean>() {
            @Override
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                if (getMvpView() != null) {
                    getMvpView().installLocationWarnSuccess(response.body());
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

    //添加其他告警
    public void installRestsWarn(String token, String lockId, int userId, int cabinetId, int warnInfoId, String infos, String details) {
        if (getMvpView() != null) {
            getMvpView().requestLoading();
        }
        mAddOtherWarnRequest.installRestsWarn(token, lockId, userId, cabinetId, warnInfoId, infos, details, new Callback<BaseBean>() {
            @Override
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                if (getMvpView() != null) {
                    getMvpView().installLocationWarnSuccess(response.body());
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

    //web序列化其他告警
    public String serializationWebIsWarnBean(String s, int i) {
        return new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss.SSS").create().toJson(new WebIsWarnBean(s, i));
    }

    //web序列化电量告警
    public String serializationWebPowerWarnBean(String s, int i) {
        return new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss.SSS").create().toJson(new WebPowerWarnBean(s, i));
    }

    //web序列化位置告警
    public String serializationWebLocationWarningBean(String s, int i) {
        return new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss.SSS").create().toJson(new WebLocationWarningBean(s, i));
    }

    public StompClient connectStomp() {
//        StompClient mStompClient = Stomp.over(Stomp.ConnectionProvider.JWS, "ws://" + ANDROID_EMULATOR_LOCALHOST
//                + ":" + RestClient.SERVER_PORT + "/zssea/endpointWc/websocket");
        StompClient mStompClient = Stomp.over(Stomp.ConnectionProvider.JWS, "ws://" + ANDROID_EMULATOR_LOCALHOST
                + "/btlockold/endpointWc/websocket");

        mStompClient.lifecycle()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(lifecycleEvent -> {
                    switch (lifecycleEvent.getType()) {
                        case OPENED:

                            break;
                        case ERROR:

                            break;
                        case CLOSED:
                    }
                });

        // Receive greetings 订阅/topic/getPoint
        mStompClient.topic("/topic/getPoint")
                .subscribeOn(Schedulers.io())
                .observeOn(io.reactivex.schedulers.Schedulers.newThread())
                .subscribe(topicMessage -> {
                    try {
//                        System.out.println(topicMessage.getPayload());
//                        StaffPark staffPark = JSON.parseObject(topicMessage.getPayload(), new TypeReference<StaffPark>() {
//                        });

                    } catch (Exception e) {
                    }
                });

        mStompClient.connect();
        return mStompClient;
    }

    @Override
    public void onDestroyPersenter() {
        super.onDestroyPersenter();
    }

    public void interruptHttp() {
        mAddOtherWarnRequest.interruptHttp();
    }
}
