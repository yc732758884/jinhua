package com.hzwc.intelligent.lock.model.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hzwc.intelligent.lock.R;
import com.hzwc.intelligent.lock.model.base.BaseActivity;
import com.hzwc.intelligent.lock.model.bean.BaseBean;
import com.hzwc.intelligent.lock.model.utils.ActivityUtils;
import com.hzwc.intelligent.lock.model.utils.FunctionUtils;
import com.hzwc.intelligent.lock.model.utils.SpUtils;
import com.hzwc.intelligent.lock.model.utils.ToastUtil;
import com.hzwc.intelligent.lock.model.utils.UniqueIDUtils;
import com.hzwc.intelligent.lock.model.view.persenter.ChangeOldPsdPresenter;
import com.hzwc.intelligent.lock.model.view.view.ChangePwdView;
import com.hzwc.intelligent.lock.mvpframework.factory.CreatePresenter;
import com.hzwc.intelligent.lock.mvpframework.view.AbstractMvpBaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@CreatePresenter(ChangeOldPsdPresenter.class)
public class ChangePwdActivity extends AbstractMvpBaseActivity<ChangePwdView, ChangeOldPsdPresenter> implements ChangePwdView {

    @BindView(R.id.iv_title_return)
    ImageView ivTitleReturn;
    @BindView(R.id.tv_title_text)
    TextView tvTitleText;
    @BindView(R.id.et_old_pwd)
    EditText etOldPwd;
    @BindView(R.id.et_new_pwd)
    EditText etNewPwd;
    @BindView(R.id.iv_pwd_eye)
    ImageView ivPwdEye;
    @BindView(R.id.et_new_pwd_again)
    EditText etNewPwdAgain;
    @BindView(R.id.iv_pwd_eyes)
    ImageView ivPwdEyes;
    @BindView(R.id.bt_change_sure)
    Button btChangeSure;
    private Boolean isEyeFirst = false;
    private Boolean isEyeSecond = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pwd);
        ButterKnife.bind(this);
    }

    @Override
    protected void initIntentData() {

    }

    @Override
    protected void initView() {
        ivTitleReturn.setVisibility(View.VISIBLE);
        tvTitleText.setText("修改密码");

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.iv_pwd_eye, R.id.iv_pwd_eyes, R.id.bt_change_sure, R.id.iv_title_return})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_title_return:
                if (FunctionUtils.isFastClick()) {
                    return;
                }
                finish();
                break;
            case R.id.iv_pwd_eye:
                if (FunctionUtils.isFastClick()) {
                    return;
                }

                if (isEyeFirst) {
                    isEyeFirst = false;
                    ivPwdEye.setImageResource(R.mipmap.icon_login_eye_close);
                    etNewPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());//显示
                } else {
                    isEyeFirst = true;
                    ivPwdEye.setImageResource(R.mipmap.icon_login_eye_open);
                    etNewPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());//隐藏

                }
                break;
            case R.id.iv_pwd_eyes:
                if (FunctionUtils.isFastClick()) {
                    return;
                }

                if (isEyeSecond) {
                    isEyeFirst = false;
                    ivPwdEyes.setImageResource(R.mipmap.icon_login_eye_close);
                    etNewPwdAgain.setTransformationMethod(HideReturnsTransformationMethod.getInstance());//显示

                } else {
                    isEyeFirst = true;
                    ivPwdEyes.setImageResource(R.mipmap.icon_login_eye_open);
                    etNewPwdAgain.setTransformationMethod(PasswordTransformationMethod.getInstance());//隐藏

                }
                break;
            case R.id.bt_change_sure:
                if (FunctionUtils.isFastClick()) {
                    return;
                }
                isEmpty();
                getMvpPresenter().clickMessageRequest(SpUtils.getString(ChangePwdActivity.this,"token",""),SpUtils.getString(ChangePwdActivity.this,"username",""),
                        etOldPwd.getText().toString(),
                        etNewPwd.getText().toString(),
                        UniqueIDUtils.getUniqueID(this));

                break;
        }
    }

    @Override
    public void dataLoading() {

    }

    @Override
    public void dataSuccess(BaseBean result) {
        if(result.getCode() == 0){
            ToastUtil.show(ChangePwdActivity.this,"修改成功,重新登录");
            Intent intent = new Intent("com.hzwc.person.lock.logout");
            sendBroadcast(intent);
            ActivityUtils.startActivityAndFinish(ChangePwdActivity.this, LoginActivity.class);

        }else if (result.getCode() == 95598) {
            SpUtils.setBoolean(ChangePwdActivity.this, "isLogin", false);
            Toast.makeText(ChangePwdActivity.this, result.getMsg(), Toast.LENGTH_SHORT).show();
            ActivityUtils.startActivityAndFinish(ChangePwdActivity.this, LoginActivity.class);
        }else {
            ToastUtil.show(ChangePwdActivity.this,result.getMsg());
        }
    }

    @Override
    public void dataFailure(String result) {
        ToastUtil.show(ChangePwdActivity.this,"服务器异常，请稍后再试");
    }

    private boolean isEmpty() {


        if (TextUtils.isEmpty(etOldPwd.getText().toString().trim())) {//|| etPassNum.getText().toString().trim().length() <= 6
            Toast.makeText(ChangePwdActivity.this, getString(R.string.et_cold_pwd_empty), Toast.LENGTH_SHORT).show();
            return true;
        }
        if (TextUtils.isEmpty(etNewPwd.getText().toString().trim())) {// || etPassSure.getText().toString().trim().length() <= 6
            Toast.makeText(ChangePwdActivity.this, getString(R.string.et_confirm_empty), Toast.LENGTH_SHORT).show();
            return true;
        }
        if (TextUtils.isEmpty(etNewPwdAgain.getText().toString().trim())) {// || etPassSure.getText().toString().trim().length() <= 6
            Toast.makeText(ChangePwdActivity.this, getString(R.string.et_confirm_sure_empty), Toast.LENGTH_SHORT).show();
            return true;
        }
        if (!(etNewPwd.getText().toString().trim().equals(etNewPwdAgain.getText().toString().trim()))) {
            Toast.makeText(ChangePwdActivity.this, getString(R.string.entered_passwords_differ), Toast.LENGTH_SHORT).show();
            return true;
        }

        return false;
    }
}
