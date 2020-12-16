package com.hzwc.intelligent.lock.model.view.persenter;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.google.gson.GsonBuilder;
import com.hzwc.intelligent.lock.model.bean.BaseBean;
import com.hzwc.intelligent.lock.model.bean.WebUnlockBean;
import com.hzwc.intelligent.lock.model.bluetooth.BleLockProtocol;
import com.hzwc.intelligent.lock.model.bluetooth.BluetoothData;
import com.hzwc.intelligent.lock.model.bluetooth.ClientManager;
import com.hzwc.intelligent.lock.model.http.request.OpenLockActivityRequest;
import com.hzwc.intelligent.lock.model.http.stomp.RestClient;
import com.hzwc.intelligent.lock.model.http.stomp.Stomp;
import com.hzwc.intelligent.lock.model.http.stomp.StompClient;
import com.hzwc.intelligent.lock.model.view.view.OpenLockActivityView;
import com.hzwc.intelligent.lock.mvpframework.presenter.BaseMvpPresenter;
import com.inuker.bluetooth.library.connect.listener.BluetoothStateListener;
import com.inuker.bluetooth.library.connect.response.BleConnectResponse;
import com.inuker.bluetooth.library.connect.response.BleNotifyResponse;
import com.inuker.bluetooth.library.connect.response.BleWriteResponse;
import com.inuker.bluetooth.library.model.BleGattProfile;
import com.inuker.bluetooth.library.search.SearchRequest;
import com.inuker.bluetooth.library.search.SearchResult;
import com.inuker.bluetooth.library.search.response.SearchResponse;
import com.inuker.bluetooth.library.utils.BluetoothLog;

import java.util.UUID;

import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.hzwc.intelligent.lock.model.bluetooth.BleLockProtocol.BLELOCK_STATE_OPEN;
import static com.hzwc.intelligent.lock.model.http.stomp.RestClient.ANDROID_EMULATOR_LOCALHOST;





/**
 * @author Administrator
 * @date 2018/1/15
 */

public class OpenLockActivityPresenter extends BaseMvpPresenter<OpenLockActivityView> {
    private StompClient mStompClient;
    private final OpenLockActivityRequest mOpenLockActivityRequest;

    public OpenLockActivityPresenter() {
        this.mOpenLockActivityRequest = new OpenLockActivityRequest();
    }

    //记录开锁
    public void unlockAfter(String token, int lockId, int userId, int power, double lon, double lat) {
        if (getMvpView() != null) {
            getMvpView().dataLoading();
        }
        mOpenLockActivityRequest.unlockAfter(token, lockId, userId, power, lon, lat, new Callback<BaseBean>() {
            @Override
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                if (getMvpView() != null) {
                    Log.e("awj", response.message() + "=====");
                    getMvpView().OpenLockRecord(response.body());
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


    //关锁上报

    public void openLockUpload(String token, String lockNo,String userId,String power) {
        if (getMvpView() != null) {
            getMvpView().dataLoading();
        }
        mOpenLockActivityRequest.unlockUpLoad(token, lockNo, userId, power,new Callback<BaseBean>() {
            @Override
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                if (getMvpView() != null) {
                    Log.e("awj", response.message() + "=====");
                    getMvpView().CloseLockRecord(response.body());
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

    //baocuo log

    public void saveLockLog(String token, String lockNo,String userId,String data) {
        if (getMvpView() != null) {
            getMvpView().dataLoading();
        }
        mOpenLockActivityRequest.saveLockLog(token, lockNo, userId, data,new Callback<BaseBean>() {
            @Override
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                if (getMvpView() != null) {
                    getMvpView().saveLockLog(response.body());
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



    //记录关锁
    public void lockRecord(String token, String lockNo, int userId) {
        if (getMvpView() != null) {
            getMvpView().dataLoading();
        }
        mOpenLockActivityRequest.lockRecord(token, lockNo, userId, new Callback<BaseBean>() {
            @Override
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                if (getMvpView() != null) {
                    Log.e("awj", response.message() + "=====");
                    getMvpView().OpenLockRecord(response.body());
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
            ClientManager.getClient().registerBluetoothStateListener(new BluetoothStateListener() {
                @Override
                public void onBluetoothStateChanged(boolean openOrClosed) {
                    if (!openOrClosed) {
                        ClientManager.getClient().openBluetooth();
                    }
                }
            });
        }

    //写入密码
    private void sendPassword(String mac, String password) {
        ClientManager.getClient().write(mac, BluetoothData.RX_SERVICE_UUID, BluetoothData.RX_CHAR_UUID, BleLockProtocol.buildAuthPassword(Integer.valueOf(password).intValue()), new BleWriteResponse() {
            @Override
            public void onResponse(int code) {

                    Log.e("chenggong",code+"~~~~~~~~~~");
                if (code == 0) {
                    System.out.println("写入密码成功");
                } else {
                    System.out.println("密码错误");
                }
            }
        });
    }

    //发送通信帧
    public void sendFrames(String mac, byte[] bytes) {
        ClientManager.getClient().write(mac, BluetoothData.Service_UUID, BluetoothData.Write_UUID, bytes, new BleWriteResponse() {
            @Override
            public void onResponse(int code) {
                if (code==0){
                    Log.e("awj   send","发送成功");
                }

            }
        });
    }


    //开始扫描
    public void searchDevice() {
        SearchRequest request = new SearchRequest.Builder()
                .searchBluetoothLeDevice(4000, 2).build();

        ClientManager.getClient().search(request, mSearchResponse);
//        searchAnim();
    }

    //连接指定的mac
    public void connectMac(String mac, String password, boolean isLock,byte[]  sKey) {
        ClientManager.getClient().connect(mac, new BleConnectResponse() {
            @Override
            public void onResponse(int code, BleGattProfile data) {

                if (code == 0) {
                    if (isLock) {

                        sendPassword(mac, password);

                    } else {//发送小锁初始通信帧
                        sendFrames(mac, BleLockProtocol.Encrypt(BleLockProtocol.sSrc,sKey));
                    }
                }
            }
        });
    }

    //开锁操作
    public void sendUnlockCommand(String mac) {
        ClientManager.getClient().write(mac, BluetoothData.RX_SERVICE_UUID, BluetoothData.RX_CHAR_UUID, BleLockProtocol.buildOperateBleLock(BLELOCK_STATE_OPEN), new BleWriteResponse() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(int code) {
                if (code == 0) {

//                    unlockRecord(令牌 锁的编号 用户id 部门 职位 机柜名称  锁的电量)
//                    Toast.makeText(MainActivity.this, "开锁成功", Toast.LENGTH_SHORT).show();
                } else {
//                    Toast.makeText(MainActivity.this, "开锁失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    //大锁状态回调
    public void bigLockNotify(String mac) {
        ClientManager.getClient().notify(mac, BluetoothData.RX_SERVICE_UUID, BluetoothData.TX_CHAR_UUID, new BleNotifyResponse() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onNotify(UUID service, UUID character, byte[] bytes) {
                getMvpView().bigLockNotify(bytes);
            }

            @Override
            public void onResponse(int code) {



            }

        });
    }


    //小锁状态回调
    public void littleLockNotify(String mac) {

        ClientManager.getClient().notify(mac, BluetoothData.Service_UUID, BluetoothData.Read_UUID0, new BleNotifyResponse() {
            @Override
            public void onNotify(UUID service, UUID character, byte[] bytes) {

//                Log.e("awj","bytes ="+service);
//                Log.e("awj","bytes ="+character);
//                for (int i=0;i<bytes.length;i++){
//
//                Log.e("awj","bytes ="+i+"="+bytes[i]);
//                }
                Log.e("littleLockNotify","---------------");
                getMvpView().littleLockNotify(bytes);
            }

            @Override
            public void onResponse(int code) {
             // Log.e("littleLockNotify",code+"~~~~~~~~~~~~~~");

            }
        });
    }

    public void unlock(String mac, byte[] bytes,byte[]  skey){
        ClientManager.getClient().write(mac, BluetoothData.Service_UUID, BluetoothData.Write_UUID, BleLockProtocol.sendUnlock(bytes,skey), new BleWriteResponse() {
            @Override
            public void onResponse(int code) {
            }
        });
    }

    //webSocket-stomp
    public StompClient connectStomp() {
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
//                        System.out.println(topicMessage.getPayload());
//                        StaffPark staffPark = JSON.parseObject(topicMessage.getPayload(), new TypeReference<StaffPark>() {
//                        });

                    } catch (Exception e) {
                    }
                });

        mStompClient.connect();
        return mStompClient;
    }

    public String serialization(String s, int i) {
        return new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss.SSS").create().toJson(new WebUnlockBean(s, i));
    }


    public void onDestroy(String mac) {
        if (mStompClient != null) {
            mStompClient.disconnect();
        }
        ClientManager.getClient().disconnect(mac);
    }

    private final SearchResponse mSearchResponse = new SearchResponse() {
        @Override
        public void onSearchStarted() {
//            initListTool("正在扫描....");
            BluetoothLog.w("MainActivity.onSearchStarted");

        }

        @Override
        public void onDeviceFounded(SearchResult device) {
//            device.getAddress() 附近mac
            getMvpView().onDeviceFounded(device);

        }

        @Override
        public void onSearchStopped() {

//            initListTool("扫描完成");

        }

        @Override
        public void onSearchCanceled() {
            BluetoothLog.w("MainActivity.onSearchCanceled");
        }
    };
    @Override
    public void onDestroyPersenter() {
        super.onDestroyPersenter();
    }

    public void interruptHttp(){
        mOpenLockActivityRequest.interruptHttp();
    }
}
