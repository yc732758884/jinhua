package com.hzwc.intelligent.lock.model.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hzwc.intelligent.lock.R;
import com.hzwc.intelligent.lock.model.bean.LoginBean;
import com.hzwc.intelligent.lock.model.utils.ActivityUtils;
import com.hzwc.intelligent.lock.model.utils.FunctionUtils;
import com.hzwc.intelligent.lock.model.utils.PhoneUtils;
import com.hzwc.intelligent.lock.model.utils.SecurityAES;
import com.hzwc.intelligent.lock.model.utils.SpUtils;
import com.hzwc.intelligent.lock.model.utils.UniqueIDUtils;
import com.hzwc.intelligent.lock.model.view.SafeKeyboard;
import com.hzwc.intelligent.lock.model.view.persenter.LoginPresenter;
import com.hzwc.intelligent.lock.model.view.view.LoginView;
import com.hzwc.intelligent.lock.mvpframework.factory.CreatePresenter;
import com.hzwc.intelligent.lock.mvpframework.view.AbstractMvpBaseActivity;

import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;


@CreatePresenter(LoginPresenter.class)
public class LoginActivity extends AbstractMvpBaseActivity<LoginView, LoginPresenter> implements LoginView {

    @BindView(R.id.et_login_username)
    EditText etLoginUsername;
    @BindView(R.id.et_login_password)
    EditText etLoginPassword;
    @BindView(R.id.tv_login_forget)
    TextView tvLoginForget;
    @BindView(R.id.bt_login)
    Button btLogin;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    @BindView(R.id.tv_login_id)
    TextView tvLoginId;
    private boolean empty;
    private Boolean mIsLogin;

    private SafeKeyboard safeKeyboard;

    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";
                    Log.e("awj", logs);
                    // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                    Toast.makeText(LoginActivity.this, "成功", Toast.LENGTH_SHORT).show();
                    break;
                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    Log.e("awj", logs);
                    // 延迟 60 秒来调用 Handler 设置别名
                    mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                    break;
                default:
                    logs = "Failed with errorCode = " + code;
                    Log.e("awj  default", logs);
                    break;
            }
//            Toast.makeText(LoginActivity.this, "log=" + logs, Toast.LENGTH_SHORT).show();
        }
    };

    private static final int MSG_SET_ALIAS = 1001;
    @SuppressLint("HandlerLeak")
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SET_ALIAS:
                    JPushInterface.setAliasAndTags(getApplication(),
                            (String) msg.obj,
                            null,
                            mAliasCallback);
                    Log.e("awj", "Set alias in handler.");


                    // 调用 JPush 接口来设置别名。
                    break;
                default:
                    Log.e("awj", "Unhandled msg - " + msg.what);
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);

        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        //返回值为true时，通知栏打开，false未打开
//        if (NotificationsUtils.isNotificationEnabled(LoginActivity.this)) {
//
//        } else {
//            NotificationsUtils.goToSet(LoginActivity.this);
//
//        }

        mIsLogin = SpUtils.getBoolean(LoginActivity.this, "isLogin", false);

        EditText safeEdit = findViewById(R.id.et_login_password);
       LinearLayout keyboardContainer = findViewById(R.id.keyboardViewPlace);
//        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(this).inflate(R.layout.layout_keyboard_containor, null);
        safeKeyboard = new SafeKeyboard(getApplicationContext(), keyboardContainer, safeEdit, R.layout.layout_keyboard_containor, view.findViewById(R.id.safeKeyboardLetter).getId());
        safeKeyboard.setDelDrawable(this.getResources().getDrawable(R.drawable.icon_del));
        safeKeyboard.setLowDrawable(this.getResources().getDrawable(R.drawable.icon_capital_default));
        safeKeyboard.setUpDrawable(this.getResources().getDrawable(R.drawable.icon_capital_selected));


        setPhoneStateManifest();


        if (mIsLogin) {
//           getMvpPresenter().clickRequest(FunctionUtils.replaceBlank(SpUtils.getString(LoginActivity.this,"username","")),SpUtils.getString(LoginActivity.this,"password",""));
            ActivityUtils.startActivityAndFinish(LoginActivity.this, MainActivity.class);

        }


    }

    @Override
    protected void initIntentData() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }


    @Override
    public void requestLoading() {

    }

    @Override
    public void loginSuccess(LoginBean result) {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(result);
        Log.e("awj loginSuccess =", jsonStr);

        if (result == null) {
            return;
        }

        if (result.getCode() == 0&&result.getState()==1){

            AlertDialog  dialog=new AlertDialog.Builder(this)
                    .setMessage("要修改初始密码么")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent=new Intent(LoginActivity.this,MessageActivity.class);
                            startActivity(intent);

                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            HandleLogin(result);
                        }
                    }).show();


        }else {
            HandleLogin(result);
        }



    }



    public  void  HandleLogin(LoginBean result){

        if (result != null && result.getCode() == 0) {
            SpUtils.setString(LoginActivity.this, "token", result.getToken());
            SpUtils.setString(LoginActivity.this, "username", etLoginUsername.getText().toString());
            SpUtils.setInt(LoginActivity.this, "userId", result.getUserId());
            mIsLogin = true;
            SpUtils.setBoolean(LoginActivity.this, "isLogin", mIsLogin);
//            Toast.makeText(LoginActivity.this, result.getMsg(), Toast.LENGTH_SHORT).show();
            ActivityUtils.startActivityAndFinish(LoginActivity.this, MainActivity.class);

            Log.e("awj userId =", result.getUserId() + "=");
            mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, "" + result.getUserId()));
            dissmissProgressDialog();
        } else if (result != null && result.getCode() == 10000) {
            dissmissProgressDialog();
            LoginPresenter.showDialog(LoginActivity.this, result.getMsg());

        } else {

            mIsLogin = false;
            dissmissProgressDialog();
            Toast.makeText(LoginActivity.this, result.getMsg(), Toast.LENGTH_SHORT).show();


        }
    }

    @Override
    public void resultFailure(String result) {
        dissmissProgressDialog();
        mIsLogin = false;
        Toast.makeText(LoginActivity.this, "服务器异常", Toast.LENGTH_SHORT).show();
        return;
    }

    @OnClick({R.id.tv_login_forget, R.id.bt_login, R.id.tv_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_login_forget:
                if (FunctionUtils.isFastClick()) {
                    return;
                }

                ActivityUtils.startActivity(LoginActivity.this, MessageActivity.class);

                break;
            case R.id.bt_login:
                if (FunctionUtils.isFastClick()) {
                    return;

                }
                if (isEmpty()) {
                    return;
                }

//                CrashReport.testJavaCrash();

                getMvpPresenter().clickRequest(etLoginUsername.getText().toString().trim(),

                        SecurityAES.encryptAES(etLoginPassword.getText().toString().trim()),
                        UniqueIDUtils.getUniqueID(this),JPushInterface.getRegistrationID(this));
                showProgressDialog(this, "加载中......");
                break;
            case R.id.tv_register:
                if (FunctionUtils.isFastClick()) {
                    return;
                }
                ActivityUtils.startActivity(LoginActivity.this, RegisterActivity.class);

                break;
        }
    }

    public boolean isEmpty() {
        if (TextUtils.isEmpty(FunctionUtils.replaceBlank(etLoginUsername.getText().toString().trim()))
                ||etLoginUsername.getText().toString().trim().length()!=11) {
            Toast.makeText(LoginActivity.this, getString(R.string.login_message_username), Toast.LENGTH_SHORT).show();
            return true;
        }
        if (TextUtils.isEmpty(etLoginPassword.getText())) {
            Toast.makeText(LoginActivity.this, getString(R.string.et_please_input_password), Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (safeKeyboard.isShow()) {
                safeKeyboard.hideKeyboard();
                return false;
            }
            return super.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }


    private void setPhoneStateManifest() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {


            tvLoginId.setText("AppId: "+UniqueIDUtils.getUniqueID(this));


        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            boolean isallGranted = true;
            for (int i = 0; i < permissions.length; i++) {

                if (grantResults[i] != PERMISSION_GRANTED) {
                    isallGranted = false;
                    break;

                }

            }

            if (isallGranted) {

                tvLoginId.setText("AppId: "+UniqueIDUtils.getUniqueID(this));

            } else {
                finish();
            }
        }


    }

}
