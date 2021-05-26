package com.hzwc.intelligent.lock.model.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hzwc.intelligent.lock.R;
import com.hzwc.intelligent.lock.model.bean.BaseBean;
import com.hzwc.intelligent.lock.model.bean.MessageBean;
import com.hzwc.intelligent.lock.model.http.ConstantUrl;
import com.hzwc.intelligent.lock.model.http.HttpService;
import com.hzwc.intelligent.lock.model.utils.ActivityUtils;
import com.hzwc.intelligent.lock.model.utils.FunctionUtils;
import com.hzwc.intelligent.lock.model.utils.LogUtils;
import com.hzwc.intelligent.lock.model.utils.PhoneTextWatcher;
import com.hzwc.intelligent.lock.model.utils.PhoneUtils;
import com.hzwc.intelligent.lock.model.utils.SecurityRSA;
import com.hzwc.intelligent.lock.model.utils.SpUtils;
import com.hzwc.intelligent.lock.model.utils.ToastUtil;
import com.hzwc.intelligent.lock.model.view.persenter.MessagePresenter;
import com.hzwc.intelligent.lock.model.view.view.MessageView;
import com.hzwc.intelligent.lock.mvpframework.factory.CreatePresenter;
import com.hzwc.intelligent.lock.mvpframework.view.AbstractMvpBaseActivity;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

@CreatePresenter(MessagePresenter.class)
public class MessageActivity extends AbstractMvpBaseActivity<MessageView, MessagePresenter> implements MessageView, View.OnClickListener {

    @BindView(R.id.iv_title_return)
    ImageView ivTitleReturn;
    @BindView(R.id.tv_title_text)
    TextView tvTitleText;
    @BindView(R.id.et_message_phone)
    EditText mEtPhone;
    @BindView(R.id.et_message_code)
    EditText mEtCode;
    @BindView(R.id.tv_message_getCode)
    TextView mTvGetCode;
    @BindView(R.id.bt_message_next)
    Button mBtNext;
    private int time = 60;
    private Boolean mIsNext = false;

    private String dataCode = "";
    private int mNobCode;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                mHandler.postDelayed(timeRunnable, 0);
                mTvGetCode.setText("获取验证码");
//                    tvGetCode.setBackgroundResource(R.mipmap.register_button_back);
                mTvGetCode.setClickable(true);
                time = 60;
            }
        }

    };
    private   MessBroadcastReceiver receiver;
    private IntentFilter intentFilter;

    Runnable timeRunnable = new Runnable() {
        @Override
        public void run() {
            if (time > -1) {
                mTvGetCode.setText(time + "s后再获取");
                mTvGetCode.setClickable(false);
                time--;
                mHandler.postDelayed(this, 1000);
            } else {
                mHandler.removeCallbacks(this);
                mTvGetCode.setText("获取验证码");
                mTvGetCode.setClickable(true);
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        ButterKnife.bind(this);
        //com.hzwc.person.lock.finish
        receiver = new MessBroadcastReceiver ();
        intentFilter = new IntentFilter("com.hzwc.person.lock.finish");
        registerReceiver(receiver, intentFilter);
    }

    @Override
    protected void initIntentData() {

    }

    @Override
    protected void initView() {
        ivTitleReturn.setVisibility(View.VISIBLE);
        tvTitleText.setText("验证");
    }

    @Override
    protected void initData() {
        mEtPhone.addTextChangedListener(new PhoneTextWatcher(mEtPhone));
        mEtPhone.setInputType(EditorInfo.TYPE_CLASS_PHONE);

    }

    @OnClick({R.id.tv_message_getCode, R.id.bt_message_next, R.id.iv_title_return})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_title_return:
                if (FunctionUtils.isFastClick()) {
                    return;
                }
                finish();
                break;
            case R.id.tv_message_getCode:

                if (FunctionUtils.isFastClick()) {
                    return;
                }
                if (!mEtPhone.getText().toString().equals("")) {
                    if (!PhoneUtils.isMobileNO(FunctionUtils.replaceBlank(mEtPhone.getText().toString().trim()))) {
                        Toast.makeText(MessageActivity.this, "不是手机号", Toast.LENGTH_SHORT).show();
                    } else {
                        getMvpPresenter().clickMessageRequest((FunctionUtils.replaceBlank(mEtPhone.getText().toString().trim())), "LYGuangFu");
                        mEtPhone.setClickable(false);
                        mHandler.sendEmptyMessage(0);
                        mIsNext = true;
                    }
                } else {
                    Toast.makeText(MessageActivity.this, "请输入手机号", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.bt_message_next:
                if (mIsNext) {
                    if (mNobCode == 1) {
                     int code=Integer.parseInt(mEtCode.getText().toString());
                        verification(mEtPhone.getText().toString(),code);



                    } else if (mNobCode == 0) {
                        Toast.makeText(MessageActivity.this, "该手机号未注册,不能找回密码", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MessageActivity.this, "请获取正确的验证码", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void dataLoading() {

    }

    private  class myHandle extends  Handler{


        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what==1){


                dataCode = mEtCode.getText().toString();
                SpUtils.setString(MessageActivity.this, "codeTel", FunctionUtils.replaceBlank(mEtPhone.getText().toString().trim()));
                SpUtils.setInt(MessageActivity.this, "identifyCode", Integer.parseInt(dataCode));

                String form=getIntent().getStringExtra("from");

                Intent intent;
                if (form!=null&&form.equals("change")){
                    intent = new Intent(MessageActivity.this, ChangePwdActivity.class);

                }else {

                    intent = new Intent(MessageActivity.this, SetPasswordActivity.class);
                    intent.putExtra("identifyCode",SpUtils.getInt(MessageActivity.this,"identifyCode",0));
                    intent.putExtra("codeTel",SpUtils.getString(MessageActivity.this, "codeTel", ""));

                }
                startActivity(intent);
            }
        }
    }


    myHandle    myHandle=new myHandle();

    public void verification( String phonenumber, int verifyCode) {


            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ConstantUrl.PUBLIC_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            HttpService apiService = retrofit.create(HttpService.class);
            Call<BaseBean> mVerificationCall = apiService.verification(SecurityRSA.encode(phonenumber), SecurityRSA.encode(verifyCode+""));
            mVerificationCall.enqueue(new Callback<BaseBean>() {
                @Override
                public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                    ToastUtil.show(MessageActivity.this,response.body().getMsg());
                     if(response.body().getCode()==0){
                         myHandle.sendEmptyMessage(1);
                     }
                }

                @Override
                public void onFailure(Call<BaseBean> call, Throwable t) {

                }
            });
        }






    @Override
    public void dataSuccess(MessageBean result) {
        Log.e("awj OpenLockRecord", result.getCode() + "");
        Log.e("awj OpenLockRecord", result.getReturnStatement() + "");
        Log.e("awj OpenLockRecord", result.getIdentifyCode() + "");
        Log.e("awj OpenLockRecord", result.getRegister() + "");
        if (result.getCode() == 0) {
            if (result.getRegister() == 1) {
                mNobCode = 1;

            } else {
                mNobCode = 0;
            }
            Toast.makeText(MessageActivity.this, result.getReturnStatement(), Toast.LENGTH_SHORT).show();
        } else if (result.getCode() == 20000) {
            Toast.makeText(MessageActivity.this, result.getReturnStatement(), Toast.LENGTH_SHORT).show();
            return;
        } else {
            Toast.makeText(MessageActivity.this, result.getReturnStatement(), Toast.LENGTH_SHORT).show();

        }

    }



    @Override
    public void dataFailure(String result) {
        Log.e("awj", "dataFailure result=" + result.toString() + "");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mHandler!=null){

        mHandler.removeCallbacksAndMessages(null);
        }
        if(receiver!= null){
            unregisterReceiver(receiver);
        }
    }


    class MessBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            finish();
        }
    }

}
