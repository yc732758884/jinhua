package com.hzwc.intelligent.lock.model.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hzwc.intelligent.lock.R;
import com.hzwc.intelligent.lock.model.base.BaseActivity;
import com.hzwc.intelligent.lock.model.bean.MineBean;
import com.hzwc.intelligent.lock.model.utils.ActivityUtils;
import com.hzwc.intelligent.lock.model.utils.FunctionUtils;
import com.hzwc.intelligent.lock.model.utils.NotificationsUtils;
import com.hzwc.intelligent.lock.model.utils.SecurityRSA;
import com.hzwc.intelligent.lock.model.utils.SpUtils;
import com.hzwc.intelligent.lock.model.utils.ToastUtil;
import com.hzwc.intelligent.lock.model.view.persenter.MinePresenter;
import com.hzwc.intelligent.lock.model.view.view.MineView;
import com.hzwc.intelligent.lock.mvpframework.factory.CreatePresenter;
import com.hzwc.intelligent.lock.mvpframework.view.AbstractMvpBaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


@CreatePresenter(MinePresenter.class)
public class MineActivity extends AbstractMvpBaseActivity<MineView, MinePresenter> implements MineView {

    @BindView(R.id.iv_title_return)
    ImageView ivTitleReturn;
    @BindView(R.id.tv_title_text)
    TextView tvTitleText;
    @BindView(R.id.iv_search)
    ImageView ivSearch;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.tv_mine_username)
    TextView tvMineUsername;
    @BindView(R.id.tv_username_nub)
    TextView tvUsernameNub;
    @BindView(R.id.iv_mine_info)
    ImageView ivMineInfo;
    @BindView(R.id.rl_mine_picture)
    RelativeLayout rlMinePicture;
    @BindView(R.id.tv_mine_tel)
    TextView tvMineTel;
    @BindView(R.id.rl_mine_name)
    LinearLayout rlMineName;
    @BindView(R.id.tv_mine_unit)
    TextView tvMineUnit;
    @BindView(R.id.tv_mine_department)
    TextView tvMineDepartment;
    @BindView(R.id.tv_mine_post)
    TextView tvMinePost;
    @BindView(R.id.rl_mine_set)
    RelativeLayout rlMineSet;
    private MainBroadcastReceiver receiver;
    private IntentFilter intentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine);
        ButterKnife.bind(this);
        receiver = new MainBroadcastReceiver();
        intentFilter = new IntentFilter("com.hzwc.person.lock.logout");
        registerReceiver(receiver, intentFilter);
    }

    @Override
    protected void initIntentData() {

    }

    @Override
    protected void initView() {
        ivTitleReturn.setVisibility(View.VISIBLE);
        ivSearch.setVisibility(View.VISIBLE);
        ivSearch.setImageResource(R.mipmap.ic_not_read);
        tvTitleText.setText("我的");
        ivSearch.setVisibility(View.GONE);


    }

    @Override
    protected void initData() {
        getMvpPresenter().clickRequest(SpUtils.getString(MineActivity.this, "token", ""));
    }

    @OnClick({R.id.iv_title_return, R.id.iv_search, R.id.rl_mine_picture, R.id.rl_mine_set})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_title_return:
                if (FunctionUtils.isFastClick()) {
                    return;
                }
                finish();
                break;
            case R.id.iv_search:
                if (FunctionUtils.isFastClick()) {
                    return;
                }
                break;
            case R.id.rl_mine_picture:
                if (FunctionUtils.isFastClick()) {
                    return;
                }



                Log.e("awj", "isTrue?="+NotificationsUtils.isNotificationEnabled(MineActivity.this));
                break;
            case R.id.rl_mine_set:
                if (FunctionUtils.isFastClick()) {
                    return;
                }
                ActivityUtils.startActivity(MineActivity.this, SettingActivity.class);
                break;
        }
    }

    @Override
    public void requestLoading() {

    }

    @Override
    public void mineSuccess(MineBean result) {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(result);
        Log.e("awj", "mineSuccess =" + jsonStr);

        if (result.getCode() == 0) {
            tvMineUsername.setText(result.getUserInfo().get(0).getName());
            tvUsernameNub.setText(String.format(getString(R.string.username), SecurityRSA.decode(result.getUserInfo().get(0).getUsername())));
            tvMineTel.setText(SecurityRSA.decode(result.getUserInfo().get(0).getPhone()));
            tvMineUnit.setText(result.getUserInfo().get(0).getCompany());
            tvMineDepartment.setText(result.getUserInfo().get(0).getDepartment());
            tvMinePost.setText(result.getUserInfo().get(0).getPost());
        }else if (result.getCode() == 95598) {
            SpUtils.setBoolean(MineActivity.this, "isLogin", false);
            Toast.makeText(MineActivity.this, result.getMsg(), Toast.LENGTH_SHORT).show();
            ActivityUtils.startActivityAndFinish(MineActivity.this, LoginActivity.class);
        } else {
            ToastUtil.show(MineActivity.this, result.getMsg());
        }
    }

    @Override
    public void resultFailure(String result) {
        ToastUtil.show(MineActivity.this, "服务器异常");
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
        if(receiver!=null){
            unregisterReceiver(receiver);
        }
    }
}
