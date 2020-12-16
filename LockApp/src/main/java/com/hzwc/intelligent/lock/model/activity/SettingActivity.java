package com.hzwc.intelligent.lock.model.activity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hzwc.intelligent.lock.model.base.BaseActivity;
import com.hzwc.intelligent.lock.model.utils.ActivityUtils;
import com.hzwc.intelligent.lock.model.utils.FunctionUtils;
import com.hzwc.intelligent.lock.model.utils.SpUtils;
import com.hzwc.intelligent.lock.R;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.beta.UpgradeInfo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

import static android.provider.UserDictionary.Words.APP_ID;
import static com.tencent.bugly.beta.Beta.checkUpgrade;

public class SettingActivity extends BaseActivity {

    @BindView(R.id.iv_title_return)
    ImageView ivTitleReturn;
    @BindView(R.id.tv_title_text)
    TextView tvTitleText;
    @BindView(R.id.rl_set_first)
    RelativeLayout rlSetFirst;
    @BindView(R.id.rl_set_clear)
    RelativeLayout rlSetClear;
    @BindView(R.id.tv_set_version)
    TextView tvSetVersion;
    @BindView(R.id.rl_set_version)
    RelativeLayout rlSetVersion;
    @BindView(R.id.tv_set_login_out)
    TextView tvSetLoginOut;

    @BindView(R.id.iv_version_red)
    ImageView ivVersionRed;
    private SetBroadcastReceiver receiver;
    private IntentFilter intentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);

        receiver = new SetBroadcastReceiver();
        intentFilter = new IntentFilter("com.hzwc.person.lock.logout");
        registerReceiver(receiver, intentFilter);
    }

    @Override
    protected void initIntentData() {
        Beta.autoCheckUpgrade = true;
    }

    @Override
    protected void initView() {

        tvSetVersion.setText(FunctionUtils.getVersionName(SettingActivity.this));
//        UpgradeInfo upgradeInfo = Beta.getUpgradeInfo();
//        if (upgradeInfo == null) {
////            Toast.makeText(SettingActivity.this, "无升级信息", Toast.LENGTH_SHORT).show();
////            return;
//        } else {
//
//            String newVersion = FunctionUtils.getVersionName(SettingActivity.this);
//            if (newVersion.equals(upgradeInfo.versionName)) {
//                ivVersionRed.setVisibility(View.GONE);
//            } else {
//                ivVersionRed.setVisibility(View.VISIBLE);
//            }
//        }

        UpgradeInfo upgradeInfo = Beta.getUpgradeInfo();
        if (upgradeInfo != null) {
            //进来就检测是否有新版本 有就提醒更新并显示红点

            checkVersionUpdate();
            loadUpgradeInfo();



            String newVersion = FunctionUtils.getVersionName(SettingActivity.this);
            if (newVersion.equals(upgradeInfo.versionName)) {
                ivVersionRed.setVisibility(View.GONE);
            } else {
                ivVersionRed.setVisibility(View.VISIBLE);
            }
        } else {
            ivVersionRed.setVisibility(View.GONE);
        }
    }
    /**
     * 设置更新属性
     */
    private void checkVersionUpdate() {
        //自动检测更新
        Beta.autoCheckUpgrade = true;
        Beta.upgradeCheckPeriod = 60 * 1000;
        Beta.initDelay = 1 * 1000;
//        Beta.largeIconId = R.drawable.logo_small;
//        Beta.smallIconId = R.drawable.logo_small;
        Beta.storageDir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        Beta.showInterruptedStrategy = true;
        Bugly.init(SettingActivity.this, APP_ID, true);
    }
    @Override
    protected void initData() {
        ivTitleReturn.setVisibility(View.VISIBLE);

        tvTitleText.setText(getResources().getString(R.string.title_set));
    }

    @SuppressLint("MissingPermission")
    @OnClick({R.id.iv_title_return, R.id.rl_set_first, R.id.rl_set_clear, R.id.rl_set_version, R.id.tv_set_login_out})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_title_return:
                if (FunctionUtils.isFastClick()) {
                    return;
                }
                finish();
                break;
            case R.id.rl_set_first:
                if (FunctionUtils.isFastClick()) {
                    return;
                }
                ActivityUtils.startActivity(SettingActivity.this, ChangePwdActivity.class);
                break;
            case R.id.rl_set_clear:
                break;
            case R.id.rl_set_version:

//                checkUpgrade();
//                loadUpgradeInfo();


                checkVersionUpdate();
                loadUpgradeInfo();
                checkUpgrade();
//                mUpdateVersionView.setGone();
                break;
            case R.id.tv_set_login_out:
                if (FunctionUtils.isFastClick()) {
                    return;
                }
                JPushInterface.cleanTags(SettingActivity.this,SpUtils.getInt(this,"userId",0));

                SpUtils.setBoolean(SettingActivity.this, "isLogin", false);
                Intent intent = new Intent("com.hzwc.person.lock.logout");
                sendBroadcast(intent);
                ActivityUtils.startActivityAndFinish(SettingActivity.this, LoginActivity.class);


                break;
        }
    }

    private void loadUpgradeInfo() {


        /***** 获取升级信息 *****/
        UpgradeInfo upgradeInfo = Beta.getUpgradeInfo();
        if (upgradeInfo == null) {
            Toast.makeText(SettingActivity.this, "无升级信息", Toast.LENGTH_SHORT).show();
            return;
        }

        StringBuilder info = new StringBuilder();
        info.append("id: ").append(upgradeInfo.id).append("\n");
        info.append("标题: ").append(upgradeInfo.title).append("\n");
        info.append("升级说明: ").append(upgradeInfo.newFeature).append("\n");
        info.append("versionCode: ").append(upgradeInfo.versionCode).append("\n");
        info.append("versionName: ").append(upgradeInfo.versionName).append("\n");
        info.append("发布时间: ").append(upgradeInfo.publishTime).append("\n");
        info.append("安装包Md5: ").append(upgradeInfo.apkMd5).append("\n");
        info.append("安装包下载地址: ").append(upgradeInfo.apkUrl).append("\n");
        info.append("安装包大小: ").append(upgradeInfo.fileSize).append("\n");
        info.append("弹窗间隔（ms）: ").append(upgradeInfo.popInterval).append("\n");
        info.append("弹窗次数: ").append(upgradeInfo.popTimes).append("\n");
        info.append("发布类型（0:测试 1:正式）: ").append(upgradeInfo.publishType).append("\n");
        info.append("弹窗类型（1:建议 2:强制 3:手工）: ").append(upgradeInfo.upgradeType);

        Log.e("awj", "info = " + info);
    }

    class SetBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (receiver != null) {
            unregisterReceiver(receiver);

        }
    }
}
