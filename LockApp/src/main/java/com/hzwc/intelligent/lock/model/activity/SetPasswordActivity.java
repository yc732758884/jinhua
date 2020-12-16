package com.hzwc.intelligent.lock.model.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hzwc.intelligent.lock.model.bean.BaseBean;
import com.hzwc.intelligent.lock.model.utils.FunctionUtils;
import com.hzwc.intelligent.lock.model.utils.SpUtils;
import com.hzwc.intelligent.lock.model.utils.UniqueIDUtils;
import com.hzwc.intelligent.lock.model.view.persenter.ChangePwdPresenter;
import com.hzwc.intelligent.lock.model.view.view.ChangePwdView;
import com.hzwc.intelligent.lock.R;
import com.hzwc.intelligent.lock.mvpframework.factory.CreatePresenter;
import com.hzwc.intelligent.lock.mvpframework.view.AbstractMvpBaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@CreatePresenter(ChangePwdPresenter.class)
public class SetPasswordActivity extends AbstractMvpBaseActivity<ChangePwdView, ChangePwdPresenter> implements ChangePwdView {

    @BindView(R.id.iv_title_return)
    ImageView ivTitleReturn;
    @BindView(R.id.tv_title_text)
    TextView tvTitleText;
    @BindView(R.id.et_set_pwd)
    EditText etSetPwd;
    @BindView(R.id.iv_set_eye)
    ImageView ivSetEye;
    @BindView(R.id.et_set_ps)
    EditText etSetPs;
    @BindView(R.id.iv_set_eyes)
    ImageView ivSetEyes;
    @BindView(R.id.bt_set_sure)
    Button btSetSure;
    private String username;
    private Boolean isEyeFirst = false;
    private Boolean isEyeSecond = false;
    private int identifyCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);
        ButterKnife.bind(this);
    }

    @Override
    protected void initIntentData() {
        username = getIntent().getStringExtra("codeTel");
        identifyCode = getIntent().getIntExtra("identifyCode", 0);
//        username = SpUtils.getString(SetPasswordActivity.this,"codeTel","");
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        tvTitleText.setText("修改密码");
        ivTitleReturn.setVisibility(View.VISIBLE);
        etSetPs.setTransformationMethod(PasswordTransformationMethod.getInstance());//隐藏
        etSetPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());//隐藏
    }

    @Override
    public void dataLoading() {

    }

    @Override
    public void dataSuccess(BaseBean result) {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(result);
        Log.e("awj", "setPwd result =" + jsonStr);
        if (result.getCode() == 0) {
            Toast.makeText(SetPasswordActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
            finish();
            Intent intent = new Intent("com.hzwc.person.lock.finish");
            sendBroadcast(intent);

        } else {
            Toast.makeText(SetPasswordActivity.this, result.getMsg(), Toast.LENGTH_SHORT).show();
            return;
        }
    }

    @Override
    public void dataFailure(String result) {
        Toast.makeText(SetPasswordActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
        return;
    }

    @OnClick({R.id.iv_title_return, R.id.iv_set_eye, R.id.iv_set_eyes, R.id.bt_set_sure})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_title_return:
                if (FunctionUtils.isFastClick()) {
                    return;
                }
                finish();
                break;
            case R.id.iv_set_eye:
                if (FunctionUtils.isFastClick()) {
                    return;
                }
                if (isEyeFirst) {
                    isEyeFirst = false;
                    ivSetEye.setImageResource(R.mipmap.icon_login_eye_close);
                    etSetPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());//显示
                } else {
                    isEyeFirst = true;
                    ivSetEye.setImageResource(R.mipmap.icon_login_eye_open);
                    etSetPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());//隐藏

                }
                break;
            case R.id.iv_set_eyes:
                if (FunctionUtils.isFastClick()) {
                    return;
                }
                if (isEyeSecond) {
                    isEyeFirst = false;
                    ivSetEye.setImageResource(R.mipmap.icon_login_eye_close);
                    etSetPs.setTransformationMethod(HideReturnsTransformationMethod.getInstance());//显示

                } else {
                    isEyeFirst = true;
                    ivSetEye.setImageResource(R.mipmap.icon_login_eye_open);
                    etSetPs.setTransformationMethod(PasswordTransformationMethod.getInstance());//隐藏

                }
                break;
            case R.id.bt_set_sure:
                if (FunctionUtils.isFastClick()) {
                    return;
                }
                if (isEmpty()) {
                    return;
                }
                getMvpPresenter().clickMessageRequest(username, identifyCode, etSetPs.getText().toString().trim(), UniqueIDUtils.getUniqueID(this));
                break;
        }
    }

    public boolean isEmpty() {

        if (TextUtils.isEmpty(etSetPwd.getText())) {
            Toast.makeText(SetPasswordActivity.this, getString(R.string.et_new_pwd), Toast.LENGTH_SHORT).show();
            return true;
        }
        if (TextUtils.isEmpty(etSetPs.getText())) {
            Toast.makeText(SetPasswordActivity.this, getString(R.string.et_new_pwd_sure), Toast.LENGTH_SHORT).show();
            return true;
        }
        if (!etSetPs.getText().toString().trim().equals(etSetPwd.getText().toString().trim())) {
            Toast.makeText(SetPasswordActivity.this, getString(R.string.et_new_pwd_equal), Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }
}
