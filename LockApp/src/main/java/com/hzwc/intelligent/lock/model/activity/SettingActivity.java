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
import com.hzwc.intelligent.lock.model.bean.BaseBean;
import com.hzwc.intelligent.lock.model.http.ConstantUrl;
import com.hzwc.intelligent.lock.model.http.HttpService;
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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
////            Toast.makeText(SettingActivity.this, "???????????????", Toast.LENGTH_SHORT).show();
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
            //????????????????????????????????? ?????????????????????????????????

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
     * ??????????????????
     */
    private void checkVersionUpdate() {
        //??????????????????
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
                Intent in=new Intent(SettingActivity.this, MessageActivity.class);
                in.putExtra("from","change");
                startActivity(in);
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

                loginout(this);
                JPushInterface.cleanTags(SettingActivity.this,SpUtils.getInt(this,"userId",0));

                SpUtils.setBoolean(SettingActivity.this, "isLogin", false);
                Intent intent = new Intent("com.hzwc.person.lock.logout");
                sendBroadcast(intent);
                ActivityUtils.startActivityAndFinish(SettingActivity.this, LoginActivity.class);


                break;
        }
    }


    public  void   loginout(Context context){

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(ConstantUrl.PUBLIC_URL)
                .build();

        HttpService service = retrofit.create(HttpService.class);

        Call<BaseBean> call = service.loginout(SpUtils.getString(context, "token", ""));

        call.enqueue(new Callback<BaseBean>() {
            @Override
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {



            }

            @Override
            public void onFailure(Call<BaseBean> call, Throwable t) {


            }
        });


    }
    private void loadUpgradeInfo() {


        /***** ?????????????????? *****/
        UpgradeInfo upgradeInfo = Beta.getUpgradeInfo();
        if (upgradeInfo == null) {
            Toast.makeText(SettingActivity.this, "???????????????", Toast.LENGTH_SHORT).show();
            return;
        }

        StringBuilder info = new StringBuilder();
        info.append("id: ").append(upgradeInfo.id).append("\n");
        info.append("??????: ").append(upgradeInfo.title).append("\n");
        info.append("????????????: ").append(upgradeInfo.newFeature).append("\n");
        info.append("versionCode: ").append(upgradeInfo.versionCode).append("\n");
        info.append("versionName: ").append(upgradeInfo.versionName).append("\n");
        info.append("????????????: ").append(upgradeInfo.publishTime).append("\n");
        info.append("?????????Md5: ").append(upgradeInfo.apkMd5).append("\n");
        info.append("?????????????????????: ").append(upgradeInfo.apkUrl).append("\n");
        info.append("???????????????: ").append(upgradeInfo.fileSize).append("\n");
        info.append("???????????????ms???: ").append(upgradeInfo.popInterval).append("\n");
        info.append("????????????: ").append(upgradeInfo.popTimes).append("\n");
        info.append("???????????????0:?????? 1:?????????: ").append(upgradeInfo.publishType).append("\n");
        info.append("???????????????1:?????? 2:?????? 3:?????????: ").append(upgradeInfo.upgradeType);

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
