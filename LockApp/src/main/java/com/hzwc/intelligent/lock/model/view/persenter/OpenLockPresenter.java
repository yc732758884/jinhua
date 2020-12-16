package com.hzwc.intelligent.lock.model.view.persenter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.GsonBuilder;
import com.hzwc.intelligent.lock.R;
import com.hzwc.intelligent.lock.model.bean.BaseBean;
import com.hzwc.intelligent.lock.model.bean.UnlocksBean;
import com.hzwc.intelligent.lock.model.bean.Update;
import com.hzwc.intelligent.lock.model.bean.WebTempApplyBean;
import com.hzwc.intelligent.lock.model.bluetooth.ClientManager;
import com.hzwc.intelligent.lock.model.http.request.OpenLockRequest;
import com.hzwc.intelligent.lock.model.http.stomp.Stomp;
import com.hzwc.intelligent.lock.model.http.stomp.StompClient;
import com.hzwc.intelligent.lock.model.utils.SpUtils;
import com.hzwc.intelligent.lock.model.view.view.OpenLockView;
import com.hzwc.intelligent.lock.mvpframework.presenter.BaseMvpPresenter;
import com.hzwc.intelligent.lock.mvpframework.view.bezier.DialogTool;
import com.inuker.bluetooth.library.connect.listener.BluetoothStateListener;
import com.inuker.bluetooth.library.search.SearchRequest;
import com.inuker.bluetooth.library.search.SearchResult;
import com.inuker.bluetooth.library.search.response.SearchResponse;
import com.inuker.bluetooth.library.utils.BluetoothLog;
import com.mylhyl.acp.Acp;
import com.mylhyl.acp.AcpListener;
import com.mylhyl.acp.AcpOptions;

import java.util.HashSet;
import java.util.List;

import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.hzwc.intelligent.lock.model.http.stomp.RestClient.ANDROID_EMULATOR_LOCALHOST;


/**
 * @author Administrator
 * @date 2018/1/15
 */

public class OpenLockPresenter extends BaseMvpPresenter<OpenLockView> {
    private final OpenLockRequest mOpenLockRequest;
    private HashSet<String> mMacList = new HashSet<>();
    private Context mContext;
    private StompClient mStompClient;

    public OpenLockPresenter() {
        this.mOpenLockRequest = new OpenLockRequest();
    }

    //开锁
    public void unlock(String token, String lockNo, int userId) {
        if (getMvpView() != null) {
            getMvpView().dataLoading();
        }
        mOpenLockRequest.unlock(token, lockNo, userId, new Callback<UnlocksBean>() {
            @Override
            public void onResponse(Call<UnlocksBean> call, Response<UnlocksBean> response) {
                if (getMvpView() != null) {


                    getMvpView().unLockSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<UnlocksBean> call, Throwable t) {
                if (getMvpView() != null) {
                    getMvpView().dataFailure(Log.getStackTraceString(t));
                }
            }
        });
    }


    //update
    public void update() {
        if (getMvpView() != null) {
            getMvpView().dataLoading();
        }
        mOpenLockRequest.update(new Callback<Update>() {
            @Override
            public void onResponse(Call<Update> call, Response<Update> response) {
                if (getMvpView() != null) {


                    getMvpView().onUpdate(response.body());
                }
            }

            @Override
            public void onFailure(Call<Update> call, Throwable t) {
                if (getMvpView() != null) {
                    getMvpView().dataFailure(Log.getStackTraceString(t));
                }
            }
        });
    }


    //申请开锁
    public void unlockApply(String token, String lockNo, int userId) {
        if (getMvpView() != null) {
            getMvpView().dataLoading();
        }
        mOpenLockRequest.unlockApply(token, lockNo, userId, new Callback<BaseBean>() {
            @Override
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                if (getMvpView() != null) {
                    getMvpView().unlockApplySuccess(response.body());
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



    //判断蓝牙是否打开
    public void bluetoothState() {
        ClientManager.getClient().registerBluetoothStateListener(mBluetoothStateListener);
        if (ClientManager.getClient().isBluetoothOpened()) {
            searchDevice();
        } else {
            ClientManager.getClient().openBluetooth();
        }
    }

    //蓝牙状态监听
    private BluetoothStateListener mBluetoothStateListener = new BluetoothStateListener() {
        @Override
        public void onBluetoothStateChanged(boolean openOrClosed) {
            if (openOrClosed) {//蓝牙打开
                searchDevice();
            } else {//蓝牙关闭
            }
        }

    };

    //权限
    public void jurisdiction(Context context) {
        Acp.getInstance(context).request(new AcpOptions.Builder()
                .setPermissions(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                ).build(), new AcpListener() {
            @Override
            public void onGranted() {

            }

            @Override
            public void onDenied(List<String> permissions) {
            }
        });
    }

    //开始扫描
    public void searchDevice() {
        SearchRequest request = new SearchRequest.Builder()
                .searchBluetoothLeDevice(8000, 2).build();

        ClientManager.getClient().search(request, mSearchResponse);
//        searchAnim();
    }

    //停止扫描
    public void stopSearchDevice() {

        ClientManager.getClient().stopSearch();
//        searchAnim();
    }

    //扫描回调
    private final SearchResponse mSearchResponse = new SearchResponse() {
        @Override
        public void onSearchStarted() {
            mMacList.clear();
            getMvpView().onSearchStarted();

        }

        @Override
        public void onDeviceFounded(SearchResult device) {

            //根据锁名来识别附近是否有蓝牙锁
            if (device.getName().equals("smart lock") || judgeName(device.getName()) ||device.getName().contains("WANGCE")) {
                boolean a = device.getName().equals("smart lock");
                Log.e("nam21",device.getName()+"~~~~"+device.getAddress());
                int count = 0;
                if (mMacList.size() == 0) {
                    mMacList.add(device.getAddress());
                    getMvpView().onDeviceFounded(device.getAddress(), a);
                } else {
                    for (String s : mMacList) {
                        count++;
                        if (device.getAddress().equals(s)) {
                            count--;
                        } else if (count == mMacList.size()) {
                            mMacList.add(device.getAddress());
                            getMvpView().onDeviceFounded(device.getAddress(), a);
                        }
                    }
                }
            }

        }

        @Override
        public void onSearchStopped() {
            mMacList.clear();
            getMvpView().onSearchStopped();
//            initListTool("扫描完成");

        }

        @Override
        public void onSearchCanceled() {
            BluetoothLog.w("MainActivity.onSearchCanceled");
        }
    };

    private boolean judgeName(String name) {
        return name.length() > 5 && name.substring(0, 5).equals("OKGSS");
    }

    //弹窗
    public void getDialogView(String hint, Activity activity, final int a, String lockNo) {
        View dialogView = DialogTool.initPopWindow(activity);
        TextView tvHint = dialogView.findViewById(R.id.tv_hint);
        TextView tvQuitNavigation = dialogView.findViewById(R.id.tv_quit_navigation);
        LinearLayout llNavigation = dialogView.findViewById(R.id.ll_navigation);
        TextView tvQuit = dialogView.findViewById(R.id.tv_quit);
        TextView tvCancel = dialogView.findViewById(R.id.tv_cancel);
        if (a == 3) {//权限申请
            tvCancel.setText(activity.getResources().getString(R.string.cancel));
            tvQuitNavigation.setText(activity.getResources().getString(R.string.apply_jurisdiction));
            tvQuit.setVisibility(View.GONE);
            llNavigation.setVisibility(View.VISIBLE);
        } else if (a == 1) {//重新提交
            tvCancel.setText(activity.getResources().getString(R.string.cancel));
            tvQuitNavigation.setText(activity.getResources().getString(R.string.resubmit));
            tvQuit.setVisibility(View.GONE);
            llNavigation.setVisibility(View.VISIBLE);
        } else if (a == 4) {//提交成功
            tvQuitNavigation.setVisibility(View.GONE);
            tvQuit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogTool.close();
                }
            });
        }
        tvHint.setText(hint);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogTool.close();
            }
        });
        tvQuitNavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (a == 3 || a == 1) {
                    mStompClient.send("/topic/getPoint", serializationWebTempApplyBean(lockNo, 1));
                    unlockApply(SpUtils.getString(activity, "token", ""), lockNo,
                            SpUtils.getInt(activity, "userId", -1));
                }
                DialogTool.close();
            }
        });
    }

    //web序列化位置告警
    private String serializationWebTempApplyBean(String s, int i) {
        return new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss.SSS").create().toJson(new WebTempApplyBean(s, i));
    }

    //websocket
    public void connectStomp() {
//        mStompClient = Stomp.over(Stomp.ConnectionProvider.JWS, "ws://" + ANDROID_EMULATOR_LOCALHOST
//                + ":" + RestClient.SERVER_PORT + "/zssea/endpointWc/websocket");
        mStompClient = Stomp.over(Stomp.ConnectionProvider.JWS, "ws://" + ANDROID_EMULATOR_LOCALHOST
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
                            break;
                    }
                });

        // Receive greetings 订阅/topic/getPoint
        mStompClient.topic("/topic/getPoint")
                .subscribeOn(Schedulers.io())
                .observeOn(io.reactivex.schedulers.Schedulers.newThread())
                .subscribe(topicMessage -> {
                    try {

                    } catch (Exception e) {
                    }
                });

        mStompClient.connect();
    }

    public void onDestroy() {
        if (mStompClient != null)
            mStompClient.disconnect();
        ClientManager.getClient().unregisterBluetoothStateListener(mBluetoothStateListener);

    }

    @Override
    public void onDestroyPersenter() {
        super.onDestroyPersenter();
    }

    public void interruptHttp() {
        mOpenLockRequest.interruptHttp();
    }
}
