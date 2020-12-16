package com.hzwc.intelligent.lock.model.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hzwc.intelligent.lock.R;
import com.hzwc.intelligent.lock.model.bean.BaseBean;
import com.hzwc.intelligent.lock.model.bluetooth.BleLockProtocol;
import com.hzwc.intelligent.lock.model.bluetooth.ClientManager;
import com.hzwc.intelligent.lock.model.http.stomp.StompClient;
import com.hzwc.intelligent.lock.model.utils.AmapLocationUtils;
import com.hzwc.intelligent.lock.model.utils.Comutil;
import com.hzwc.intelligent.lock.model.utils.LocationUtils;
import com.hzwc.intelligent.lock.model.utils.SpUtils;
import com.hzwc.intelligent.lock.model.utils.ToastUtil;
import com.hzwc.intelligent.lock.model.view.persenter.OpenLockActivityPresenter;
import com.hzwc.intelligent.lock.model.view.view.OpenLockActivityView;
import com.hzwc.intelligent.lock.mvpframework.factory.CreatePresenter;
import com.hzwc.intelligent.lock.mvpframework.view.AbstractMvpBaseActivity;
import com.inuker.bluetooth.library.Constants;
import com.inuker.bluetooth.library.search.SearchResult;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/7/11.
 */
@CreatePresenter(OpenLockActivityPresenter.class)
public class OpenLockActivity extends AbstractMvpBaseActivity<OpenLockActivityView, OpenLockActivityPresenter> implements OpenLockActivityView, View.OnClickListener {

    @BindView(R.id.tv_record)
    TextView tvRecord;
    @BindView(R.id.tv_lock_not_open)
    ImageView tvLockNotOpen;
    @BindView(R.id.tv_circular_bead)
    TextView tvCircularBead;
    @BindView(R.id.tv_lock_mac)
    TextView tvLockMac;
    @BindView(R.id.tv_quantity_at_large)
    TextView tvQuantityAtLarge;
    @BindView(R.id.tv_box_at_large)
    TextView tvBoxAtLarge;
    @BindView(R.id.tv_unlocking_button)
    TextView tvUnlockingButton;
    @BindView(R.id.tv_hint)
    TextView tvHint;
    @BindView(R.id.rl_main)
    RelativeLayout rlMain;
    @BindView(R.id.iv_return)
    ImageView ivReturn;
    @BindView(R.id.tv_close_button)
    TextView tvCloseButton;
    private String mLockNo;
    private String mPassword;
    private String mCabinetName;
    private int mLockId;
    private int mPower;
    private StompClient mStompClient;
    private Handler mHandler;
    private boolean mIsLock;
    private byte[] mTokenByte;
    private byte[] sKey;
    private AmapLocationUtils mLocationUtils;
    private boolean mIsUnlock;
    String power;

    boolean  isfirst=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_lock);
        ButterKnife.bind(this);
        mLocationUtils = AmapLocationUtils.getInstace(this);

    }


    @Override
    protected void initView() {
        tvQuantityAtLarge.setText(String.format(getResources().getString(R.string.electric_quantity), getResources().getString(R.string.no_data), ""));
        tvBoxAtLarge.setText(String.format(getResources().getString(R.string.electri_name), getResources().getString(R.string.no_data)));
        ivReturn.setOnClickListener(this);
        tvRecord.setOnClickListener(this);
        tvUnlockingButton.setOnClickListener(this);

        tvCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                 getMvpPresenter().sendFrames(mLockNo,BleLockProtocol.SendOldPwd(mTokenByte,mPassword,sKey));
//
//                try {
//                    Thread.sleep(5000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                getMvpPresenter().sendFrames(mLockNo,BleLockProtocol.sendNewPwd(mTokenByte,"123456",sKey));
//
//
                         if (mTokenByte==null){
                             ToastUtil.show(OpenLockActivity.this,"请等待锁连接初始化");
                             return;
                         }
                getMvpPresenter().sendFrames(mLockNo, BleLockProtocol.resetCloseLock(mTokenByte, sKey));


            }
        });
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        mIsLock = intent.getBooleanExtra("isLock", true);
        mLockNo = intent.getStringExtra("lockNo");
        mPassword = intent.getStringExtra("password");
        //mPassword="000000";
        mCabinetName = intent.getStringExtra("cabinetName");
        mLockId = intent.getIntExtra("lockId", -1);
        sKey = intent.getByteArrayExtra("key");
        int close = intent.getIntExtra("close", 1);
        if (close == 1) {
              tvCloseButton.setVisibility(View.GONE);
        } else {
                tvCloseButton.setVisibility(View.VISIBLE);
        }
        //    sKey=BleLockProtocol.sKey;

        mStompClient = getMvpPresenter().connectStomp();
        mHandler = new Handler();
        mHandler.postDelayed(r, 0);


        getMvpPresenter().connectMac(mLockNo, mPassword, mIsLock, sKey);
    }

    Runnable r = new Runnable() {
        @Override
        public void run() {
            if (mIsLock) {

                getMvpPresenter().bigLockNotify(mLockNo);
            } else {//小锁状态回调

                getMvpPresenter().littleLockNotify(mLockNo);
            }
            connecSttate();
            mHandler.postDelayed(this, 1000);
        }
    };

    //mac设备状态
    private void connecSttate() {
        int status = ClientManager.getClient().getConnectStatus(mLockNo);

        if (status == Constants.STATUS_UNKNOWN) {//状态未知

        } else if (status == Constants.STATUS_DEVICE_CONNECTED) {//设备连接
            tvCircularBead.setText(getString(R.string.connected));
            tvHint.setVisibility(View.GONE);
            tvLockMac.setText(mLockNo);
            tvBoxAtLarge.setText(String.format(getResources().getString(R.string.electri_name), mCabinetName));
            Drawable drawable = getResources().getDrawable(R.mipmap.electric_box, null);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight());
            tvBoxAtLarge.setCompoundDrawables(null, drawable, null, null);
        } else if (status == Constants.STATUS_DEVICE_DISCONNECTED) {//设备断开连接
            tvHint.setVisibility(View.VISIBLE);
            tvCircularBead.setText(getString(R.string.ununited));
            tvQuantityAtLarge.setText(String.format(getResources().getString(R.string.electric_quantity), getResources().getString(R.string.no_data), ""));
            tvBoxAtLarge.setText(String.format(getResources().getString(R.string.electri_name), getResources().getString(R.string.no_data)));
            tvLockMac.setText("");
            Drawable drawable = getResources().getDrawable(R.mipmap.electric_box_at_large, null);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight());
            tvBoxAtLarge.setCompoundDrawables(null, drawable, null, null);
            Drawable drawable2 = getResources().getDrawable(R.mipmap.electric_quantity_at_large, null);
            drawable2.setBounds(0, 0, drawable2.getIntrinsicWidth(),
                    drawable2.getIntrinsicHeight());
            tvQuantityAtLarge.setCompoundDrawables(null, drawable2, null, null);
        }


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_return:
                mHandler.removeCallbacks(r);
                finish();
                break;
            case R.id.tv_unlocking_button://开锁
                if (ClientManager.getClient().getConnectStatus(mLockNo) == Constants.STATUS_DEVICE_DISCONNECTED) {
                    Toast.makeText(this, getString(R.string.ununited), Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!Comutil.isLocationEnabled(this)){
                    Toast.makeText(this, "位置信息不可用，请打开位置信息", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (mTokenByte==null){
                    ToastUtil.show(OpenLockActivity.this,"请等待锁连接初始化");
                    return;
                }
                if (mIsLock) {

                    getMvpPresenter().sendUnlockCommand(mLockNo);
                } else {

                    if (mTokenByte == null || mTokenByte.length < 7) return;

                    getMvpPresenter().sendFrames(mLockNo, BleLockProtocol.sendUnlock(mTokenByte, mPassword, sKey));
                }
                break;
            case R.id.tv_record://记录
                Intent intent = new Intent(OpenLockActivity.this, RecordActivity.class);
                intent.putExtra("a", false);
                intent.putExtra("lockNo", mLockNo);
                intent.putExtra("cabinetName", mCabinetName);
                startActivity(intent);
                break;

            default:
                break;
        }
    }

    @Override
    public void onDeviceFounded(SearchResult device) {

    }

    @Override
    public void dataLoading() {

    }

    @Override
    public void OpenLockRecord(BaseBean result) {
//        System.out.println("lllll: " + result.getMsg() + result.getCode());
    }


    @Override
    public void CloseLockRecord(BaseBean result) {
        if (result.getCode()==0){
            isfirst=false;
        }


    }

    //锁回包
    @Override
    public void bigLockNotify(byte[] bytes) {
        System.out.println("回包: " + Arrays.toString(bytes));
        if (Arrays.toString(bytes).equals(BleLockProtocol.connectRightCommand(Integer.valueOf(mPassword).intValue()))) {
            ToastUtil.show(this, "密码正确=====" + mPassword + "锁编号======" + mLockNo);
            Toast.makeText(OpenLockActivity.this, "密码正确:密码" + mPassword, Toast.LENGTH_SHORT).show();
            System.out.println("连接密码正确");
        } else if (Arrays.toString(bytes).equals(BleLockProtocol.connectErrorCommand())) {
            Toast.makeText(OpenLockActivity.this, "密码错误:密码" + mPassword, Toast.LENGTH_SHORT).show();
            ToastUtil.show(this, "密码错误=====" + mPassword + "锁编号======" + mLockNo);
            System.out.println("连接密码错误");
        }
        if (bytes[1] == 32) {//电量回包
            powerShow(bytes[2]);
        }
        if (bytes[1] == 48) {
            if (bytes[2] == 1) {//锁开
                if (!mIsUnlock) {
                    lockOpen();
                    mIsUnlock = true;
                    // destroy();
                    // finish();
                    Toast.makeText(OpenLockActivity.this, "锁开:密码" + mPassword, Toast.LENGTH_SHORT).show();
                }
            } else if (bytes[2] == 0) {//锁关
                lockClose();
            }
        }
    }

    private void lockClose() {
        getMvpPresenter().lockRecord(SpUtils.getString(this, "token", ""),
                mLockNo, SpUtils.getInt(this, "userId", -1));
        tvLockNotOpen.setBackground(getResources().getDrawable(R.mipmap.lock_not_open, null));
        mStompClient.send("/topic/getPoint", getMvpPresenter().serialization(mLockNo, 0));
    }

    private void closeLockUpload() {
        getMvpPresenter().openLockUpload(SpUtils.getString(this, "token", ""),
                mLockNo, SpUtils.getInt(this, "userId", -1) + "", mLockId+"");
    }

    private  void  saveLog(String data){
        getMvpPresenter().saveLockLog(SpUtils.getString(this, "token", ""),
                mLockNo, SpUtils.getInt(this, "userId", -1) + "", data);
    }

    private void powerShow(byte aByte) {
        Drawable drawable = getResources().getDrawable(R.mipmap.electric_quantity_gain, null);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight());
        tvQuantityAtLarge.setCompoundDrawables(null, drawable, null, null);
        power = String.valueOf(aByte);
        tvQuantityAtLarge.setText(String.format(getString(R.string.electric_quantity), power, "%"));
        mPower = aByte;
    }

    //开锁成功
    private void lockOpen() {


        mLocationUtils.init();
        mLocationUtils.setLocationListener(new AmapLocationUtils.getLocationListener() {
            @Override
            public void location(double lat, double lon) {


                getMvpPresenter().unlockAfter(SpUtils.getString(OpenLockActivity.this, "token", ""),
                        mLockId, SpUtils.getInt(OpenLockActivity.this, "userId", -1), mPower,lon
                        , lat);
                tvLockNotOpen.setBackground(getResources().getDrawable(R.mipmap.yidongopen, null));
            }
        });






      //  mStompClient.send("/topic/getPoint", getMvpPresenter().serialization(mLockNo, 1));
    }

    @Override
    public void littleLockNotify(byte[] bytes) {


        byte[] bytes1 = BleLockProtocol.Decrypt(bytes, sKey);


        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bytes1.length; i++) {
            sb.append(bytes1[i]);
        }
        Log.e("bytes", sb.toString());

//        if (bytes1[0]==0x05&&bytes1[1]==0x05){
//            byte res=bytes[3];
//            if (res==0x00){
//                Log.e("change","1");
//            }else {
//                Log.e("change","0");
//            }
//        }


        if (bytes1[0] == 0x06 && bytes1[1] == 0x02) {//token回包
            mTokenByte = bytes1;
            getMvpPresenter().sendFrames(mLockNo, BleLockProtocol.electric(bytes1, sKey));//获取电量
            getMvpPresenter().sendFrames(mLockNo, BleLockProtocol.sendState(bytes1, sKey));

        } else if (bytes1[0] == 0x02 && bytes1[1] == 0x02 && bytes1[2] == 0x01) {//电量回包
            powerShow(bytes1[3]);
        } else if (bytes1[0] == 0x05 && bytes1[1] == 0x02 && bytes1[2] == 0x01) {//开锁回包
            if (bytes1[3] == 0x00) {//开锁成功
                if (!mIsUnlock) {
                    ToastUtil.show(this,"开锁成功");
                    lockOpen();
                    mIsUnlock = true;
                    //  getMvpPresenter().sendFrames(mLockNo, BleLockProtocol.resetCloseLock(mTokenByte,sKey));//复位关锁
                    destroy();
                   finish();

                }

            } else if (bytes1[3] == 0x01) {//开锁失败


            }
        } else if (bytes1[0] == 0x05 && bytes1[1] == 0x08 && bytes1[2] == 0x01) {//锁关
            if (bytes1[3] == 0x00) {//关锁成功
                 ToastUtil.show(this,"关锁成功");
                lockClose();
                if (isfirst){
                    closeLockUpload();
                    saveLog(sb.toString());
                    isfirst=false;
                }

                getMvpPresenter().onDestroy(mLockNo);

                //     Log.e("awj  lock close","close----success");
                  finish();

            } else if (bytes1[3] == 0x01) {//关锁失败
                Log.e("awj  lock close", "close----fail");
                //       ToastUtil.show(OpenLockActivity.this,"关锁失败");

            }
        } else if (bytes1[0] == 0x05 && bytes1[1] == 0x0D && bytes1[2] == 0x01) {//主动发送关锁(复位电机)
            if (bytes1[3] == 0x00) {//复位成功
                ToastUtil.show(this,"关锁成功");
              //  closeLockUpload();

                if (isfirst){
                    closeLockUpload();
                    saveLog(sb.toString());
                    isfirst=false;
                }


                getMvpPresenter().onDestroy(mLockNo);
                // ToastUtil.show(OpenLockActivity.this,"主动关锁成功");
                //  finish();
            } else if (bytes1[3] == 0x01) {//复位失败
                Log.e("awj  phone close", "close----fail");
                //   ToastUtil.show(OpenLockActivity.this,"主动关锁失败");
            }
        }
    }

    @Override
    public void dataFailure(String result) {

    }

    @Override
    public void saveLockLog(BaseBean result) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroy();
    }

    private void destroy() {
        mHandler.removeCallbacks(r);
//        mHandler.removeCallbacks(r1);
        getMvpPresenter().onDestroy(mLockNo);
    }

}
