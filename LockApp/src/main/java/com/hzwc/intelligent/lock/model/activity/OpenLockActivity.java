package com.hzwc.intelligent.lock.model.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.GlobalHistogramBinarizer;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.multi.qrcode.QRCodeMultiReader;
import com.google.zxing.qrcode.QRCodeReader;
import com.huawei.hms.hmsscankit.ScanUtil;
import com.huawei.hms.ml.scan.HmsScan;
import com.huawei.hms.ml.scan.HmsScanAnalyzerOptions;
import com.hzwc.intelligent.lock.R;
import com.hzwc.intelligent.lock.model.bean.BaseBean;
import com.hzwc.intelligent.lock.model.bean.CloseTakePic;
import com.hzwc.intelligent.lock.model.bluetooth.BleLockProtocol;
import com.hzwc.intelligent.lock.model.bluetooth.ClientManager;
import com.hzwc.intelligent.lock.model.http.ConstantUrl;
import com.hzwc.intelligent.lock.model.http.HttpService;
import com.hzwc.intelligent.lock.model.http.stomp.StompClient;
import com.hzwc.intelligent.lock.model.utils.AmapLocationUtils;
import com.hzwc.intelligent.lock.model.utils.BitmapUtil;
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
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

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
    private Button btn_lock, btn_box;

    boolean isfirst = true;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_lock);
        ButterKnife.bind(this);
        mLocationUtils = AmapLocationUtils.getInstace(this);

        //getCode();



    }


    @Override
    protected void initView() {
        tvQuantityAtLarge.setText(String.format(getResources().getString(R.string.electric_quantity), getResources().getString(R.string.no_data), ""));
        tvBoxAtLarge.setText(String.format(getResources().getString(R.string.electri_name), getResources().getString(R.string.no_data)));
        ivReturn.setOnClickListener(this);
        tvRecord.setOnClickListener(this);
        tvUnlockingButton.setOnClickListener(this);
        btn_lock = findViewById(R.id.btn_code_lock);
        btn_box = findViewById(R.id.btn_code_box);

        btn_lock.setOnClickListener(v -> {
                    requestCamera();

                }
        );


        btn_box.setOnClickListener(v -> {

                    AndPermission.with(this)
                            .permission(Permission.CAMERA)
                            .onGranted(new Action() {
                                @Override

                                public void onAction(List<String> permissions) {

                                    if (permissions != null && permissions.size() > 0) {
                                        requestCamera();


                                        return;
                                    }
                                }
                            }).onDenied(new Action() {
                        @Override
                        public void onAction(List<String> permissions) {
                            ToastUtil.show("相机拒绝");

                        }
                    }).start();


                }
        );


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
                if (mTokenByte == null) {
                    ToastUtil.show(OpenLockActivity.this, "请等待锁连接初始化");
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
        Log.e("ssssssss1",mLockNo);
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

                if (!Comutil.isLocationEnabled(this)) {
                    Toast.makeText(this, "位置信息不可用，请打开位置信息", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (mTokenByte == null) {
                    ToastUtil.show(OpenLockActivity.this, "请等待锁连接初始化");
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
        if (result.getCode() == 0) {
            isfirst = false;
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
                mLockNo, SpUtils.getInt(this, "userId", -1) + "", mLockId + "");
    }

    private void saveLog(String data) {
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

                Log.e("ssss", SpUtils.getString(OpenLockActivity.this, "token", ""));

                Log.e("ssss", SpUtils.getInt(OpenLockActivity.this, "userId", -1) + "~~~");


                getMvpPresenter().unlockAfter(SpUtils.getString(OpenLockActivity.this, "token", ""), mLockId,
                        SpUtils.getInt(OpenLockActivity.this, "userId", -1), mPower, lon, lat);

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
        //   ToastUtil.show(sb.toString());

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
                    ToastUtil.show(this, "开锁成功");
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
                ToastUtil.show(this, "关锁成功");
                lockClose();
                if (isfirst) {
                    closeLockUpload();
                    saveLog(sb.toString());
                    isfirst = false;
                    AndPermission.with(this)
                            .permission(Permission.CAMERA)
                            .onGranted(new Action() {
                                @Override

                                public void onAction(List<String> permissions) {

                                    requestCamera();
                                }
                            }).onDenied(new Action() {
                        @Override
                        public void onAction(List<String> permissions) {
                            ToastUtil.show("相机拒绝");

                        }
                    }).start();
                }


                getMvpPresenter().onDestroy(mLockNo);

                //     Log.e("awj  lock close","close----success");
                // finish();

            } else if (bytes1[3] == 0x01) {//关锁失败
                Log.e("awj  lock close", "close----fail");
                //       ToastUtil.show(OpenLockActivity.this,"关锁失败");

            }
        } else if (bytes1[0] == 0x05 && bytes1[1] == 0x0D && bytes1[2] == 0x01) {//主动发送关锁(复位电机)
            if (bytes1[3] == 0x00) {//复位成功

                //  closeLockUpload();

                if (isfirst) {
                    ToastUtil.show(this, "关锁成功");
                    closeLockUpload();
                    saveLog(sb.toString());
                    isfirst = false;

                    AndPermission.with(this)
                            .permission(Permission.CAMERA)
                            .onGranted(new Action() {
                                @Override

                                public void onAction(List<String> permissions) {

                                    requestCamera();
                                }
                            }).onDenied(new Action() {
                        @Override
                        public void onAction(List<String> permissions) {
                            ToastUtil.show("相机拒绝");

                        }
                    }).start();
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 103:

               getCode();

//                String filePath = Environment.getExternalStorageDirectory() + File.separator + "output_image.jpg";
//                File outputImage = new File(filePath);
//
//
//                // String content = data.getStringExtra(Constant.CODED_CONTENT);
//                Bitmap bitmap = BitmapFactory.decodeFile(filePath);
//
//                HmsScanAnalyzerOptions options = new HmsScanAnalyzerOptions.Creator().setHmsScanTypes(HmsScan.QRCODE_SCAN_TYPE, HmsScan.DATAMATRIX_SCAN_TYPE).setPhotoMode(true).create();
//
//                HmsScan[] hmsScans = ScanUtil.decodeWithBitmap(this, bitmap, options);
//                if (hmsScans != null && hmsScans.length > 0) {
//
//                    //展示扫码结果
//                    Toast.makeText(this, "发现二维码", Toast.LENGTH_SHORT).show();
//                    String code = hmsScans[0].showResult;
//
//                    try {
//
//                        if (code.contains("115.29.204.20")) {
//
//                            try {
//                                getCode("", code);
//                            } catch (Exception e) {
//
//                            }
//
//                        } else {
//
//                        }
//
//                    } catch (Exception e) {
//                        ToastUtil.show("二维码不正确");
//                        requestCamera();
//                        e.printStackTrace();
//                    }
//                } else {
//
//                    Toast.makeText(this, "没有发现二维码", Toast.LENGTH_SHORT).show();
//                }


                break;
            default:
                break;
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


    private void requestCamera() {
        String filePath= Environment.getExternalStorageDirectory() + File.separator + "output_image.jpg";
        File outputImage = new File(filePath);

        try
        {
            if (!outputImage.getParentFile().exists()) {
                outputImage.getParentFile().mkdirs();
            }
            if (outputImage.exists()) {
                outputImage.delete();
            }

            outputImage.createNewFile();

            if (Build.VERSION.SDK_INT >= 24) {
                imageUri = FileProvider.getUriForFile(this,
                        "com.hzwc.intelligent.lock.fileprovider", outputImage);
            } else {
                imageUri = Uri.fromFile(outputImage);
            }

            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(intent, 103);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    void   getCode(){

        OkHttpClient client=new OkHttpClient().newBuilder().build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(ConstantUrl.PUBLIC_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        HttpService apiService = retrofit.create(HttpService.class);
        MediaType mediaType = MediaType.parse("image/jpeg");

        String filePath= Environment.getExternalStorageDirectory() + File.separator + "output_image.jpg";
        File outputImage = new File(filePath);
        RequestBody fileBody = RequestBody.create(mediaType, outputImage);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", outputImage.getName(), fileBody);


        apiService.closeLockTakePictures(SpUtils.getString(this, "token", ""),mLockNo,SpUtils.getInt(this, "userId", -1),
               body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseBean<CloseTakePic>>() {
                    @Override
                    public void accept(BaseBean<CloseTakePic> baseBean) throws Exception {
                        ToastUtil.show(baseBean.getMsg());

                        Log.e("sssss",new Gson().toJson(baseBean));
                        if (baseBean.getCode()!=0){
                            isfirst=true;
                            requestCamera();
                        }else {
                            if (baseBean.getData().getState()==0){
                                ToastUtil.show(" 箱锁不匹配，请注意查看告警！！");
                            }

                            if (baseBean.getData().getState()==1){
                                ToastUtil.show("关锁上报图片已上传！！");
                            }



                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                });

    }



}
