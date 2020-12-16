package com.hzwc.intelligent.lock.model.activity;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hzwc.intelligent.lock.R;
import com.hzwc.intelligent.lock.model.bean.BaseBean;
import com.hzwc.intelligent.lock.model.bean.RegisterBean;
import com.hzwc.intelligent.lock.model.utils.FunctionUtils;
import com.hzwc.intelligent.lock.model.utils.PhoneUtils;
import com.hzwc.intelligent.lock.model.view.persenter.RegisterPresenter;
import com.hzwc.intelligent.lock.model.view.view.RegisterView;
import com.hzwc.intelligent.lock.mvpframework.factory.CreatePresenter;
import com.hzwc.intelligent.lock.mvpframework.view.AbstractMvpBaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@CreatePresenter(RegisterPresenter.class)
public class RegisterActivity extends AbstractMvpBaseActivity<RegisterView, RegisterPresenter> implements RegisterView {

    @BindView(R.id.iv_title_return)
    ImageView ivTitleReturn;
    @BindView(R.id.tv_title_text)
    TextView tvTitleText;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.et_register_username)
    EditText etRegisterUsername;
    @BindView(R.id.et_register_code)
    EditText etRegisterCode;
    @BindView(R.id.et_register_new)
    EditText etRegisterNew;
    @BindView(R.id.iv_register_eye)
    ImageView ivRegisterEye;
    @BindView(R.id.et_register_new_again)
    EditText etRegisterNewAgain;
    @BindView(R.id.iv_register_eyes)
    ImageView ivRegisterEyes;
    @BindView(R.id.tv_register_getCode)
    TextView tvRegisterGetCode;
    private int time = 60;
    private Boolean isCheck = false;
    private Boolean isVisible = false;
    private Boolean isVisibleSure = false;
    private Object cancelObject = new Object();
    private static final int REGISTER = 0x001;
    private static final int CHECK_CODE = 0x002;
    private int identifyCode = -1;

    private MainBroadcastReceiver receiver;
    private IntentFilter intentFilter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        receiver = new MainBroadcastReceiver();
        intentFilter = new IntentFilter("com.hzwc.person.lock.register");
        registerReceiver(receiver, intentFilter);
    }

    @Override
    protected void initView() {
        tvTitleText.setText(getString(R.string.title_register));
        tvSearch.setText(getString(R.string.install_rim_location));
        ivTitleReturn.setVisibility(View.VISIBLE);
        tvSearch.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initData() {
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                mHandler.postDelayed(timeRunnable, 0);
                tvRegisterGetCode.setText(getString(R.string.forget_get_code));
                tvRegisterGetCode.setClickable(true);
                time = 60;
            }
        }

    };
    Runnable timeRunnable = new Runnable() {
        @Override
        public void run() {
            if (time > -1) {
                if (tvRegisterGetCode==null){
                    return;
                }
                tvRegisterGetCode.setText(String.format(getString(R.string.forget_get_code_time), time));
                time--;
                mHandler.postDelayed(this, 1000);
            } else {
                mHandler.removeCallbacks(this);
                tvRegisterGetCode.setText(getString(R.string.forget_get_code));
                tvRegisterGetCode.setClickable(true);
            }

        }
    };

    @OnClick({R.id.iv_title_return, R.id.tv_search, R.id.iv_register_eye, R.id.iv_register_eyes, R.id.tv_register_getCode})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_title_return:
                finish();
                break;
            case R.id.tv_search:
                isEmpty();

                if ((identifyCode + "").equals(etRegisterCode.getText().toString().trim())) {

                    Intent intent = new Intent(this, RegisterNextActivity.class);
                    intent.putExtra("mobile", etRegisterUsername.getText().toString());
                    intent.putExtra("password", etRegisterNewAgain.getText().toString());
                    intent.putExtra("verifyCode", etRegisterCode.getText().toString());
                    startActivity(intent);
                }

//                getMvpPresenter().verification("", etRegisterUsername.getText().toString(), etRegisterCode.getText().toString());
                break;
            case R.id.iv_register_eye:
                if (isVisible) {
                    ivRegisterEye.setBackgroundResource(R.mipmap.icon_gone);
                    etRegisterNew.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    isVisible = false;
                } else {
                    ivRegisterEye.setBackgroundResource(R.mipmap.icon_login_eye_close);
                    etRegisterNew.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    isVisible = true;
                }
                break;
            case R.id.iv_register_eyes:
                if (isVisibleSure) {
                    ivRegisterEyes.setBackgroundResource(R.mipmap.icon_gone);
                    etRegisterNewAgain.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    isVisibleSure = false;
                } else {
                    ivRegisterEyes.setBackgroundResource(R.mipmap.icon_login_eye_close);
                    etRegisterNewAgain.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    isVisibleSure = true;
                }
                break;
            case R.id.tv_register_getCode:
                String userName = etRegisterUsername.getText().toString();
                if (!userName.trim().equals("")) {
                    if (!PhoneUtils.isMobileNO(FunctionUtils.replaceBlank(userName.trim()))) {
                        Toast.makeText(RegisterActivity.this, getString(R.string.et_please_input_yourself_username), Toast.LENGTH_SHORT).show();
                    } else {
                        getMvpPresenter().sendMessage(null, userName, "test");
                        mHandler.sendEmptyMessage(0);
                        tvRegisterGetCode.setClickable(false);
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, getString(R.string.forget_please_tel), Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    //判空
    private boolean isEmpty() {

        if (TextUtils.isEmpty(FunctionUtils.replaceBlank(etRegisterUsername.getText().toString().trim())) || !PhoneUtils.isMobileNO(FunctionUtils.replaceBlank(etRegisterUsername.getText().toString().trim()))) {
            Toast.makeText(RegisterActivity.this, getString(R.string.et_please_input_yourself_username), Toast.LENGTH_SHORT).show();
            return true;
        }

        if (TextUtils.isEmpty(tvRegisterGetCode.getText())) {
            Toast.makeText(RegisterActivity.this, getString(R.string.et_please_input_code), Toast.LENGTH_SHORT).show();
            return true;
        }

        if (TextUtils.isEmpty(etRegisterNew.getText().toString().trim())) {
            Toast.makeText(RegisterActivity.this, getString(R.string.change_put_new_pwd), Toast.LENGTH_SHORT).show();
            return true;
        }
        if (TextUtils.isEmpty(etRegisterNewAgain.getText().toString().trim())) {
            Toast.makeText(RegisterActivity.this, getString(R.string.et_confirm_empty), Toast.LENGTH_SHORT).show();
            return true;
        }
        if (!(etRegisterNew.getText().toString().trim().equals(etRegisterNewAgain.getText().toString().trim()))) {
            Toast.makeText(RegisterActivity.this, getString(R.string.entered_passwords_differ), Toast.LENGTH_SHORT).show();
            return true;
        }


        return false;
    }

    @Override
    public void dataLoading() {

    }

    @Override
    public void sendMessageSuccess(RegisterBean result) {

        Gson gson = new Gson();
        String jsonStr = gson.toJson(result);
        Log.e("awj", "sendMessageSuccess =" + jsonStr);

        if (result.getCode() == 0) {
            identifyCode = result.getIdentifyCode();
            Toast.makeText(this, "发送成功", Toast.LENGTH_SHORT).show();
        } else if (result.getCode() == 20000) {
            Toast.makeText(this, result.getReturnStatement(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, result.getMsg(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void verificationSuccess(BaseBean result) {

        Gson gson = new Gson();
        String jsonStr = gson.toJson(result);
        Log.e("awj", "verificationSuccess =" + jsonStr);
        if (result.getCode() == 0) {

        }
        Toast.makeText(this, result.getMsg(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void dataFailure(String result) {

    }


    class MainBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);



    }
}
