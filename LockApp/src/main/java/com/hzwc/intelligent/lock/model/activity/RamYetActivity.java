package com.hzwc.intelligent.lock.model.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hzwc.intelligent.lock.R;
import com.hzwc.intelligent.lock.model.adapter.ListAdapter;
import com.hzwc.intelligent.lock.model.bean.UserRamBean;
import com.hzwc.intelligent.lock.model.utils.ActivityUtils;
import com.hzwc.intelligent.lock.model.utils.FunctionUtils;
import com.hzwc.intelligent.lock.model.utils.SpUtils;
import com.hzwc.intelligent.lock.model.utils.ToastUtil;
import com.hzwc.intelligent.lock.model.view.persenter.UserRamPresenter;
import com.hzwc.intelligent.lock.model.view.view.UserRamView;
import com.hzwc.intelligent.lock.mvpframework.factory.CreatePresenter;
import com.hzwc.intelligent.lock.mvpframework.view.AbstractMvpBaseActivity;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.hzwc.intelligent.lock.model.adapter.ListAdapter.USER_RAM;

@CreatePresenter(UserRamPresenter.class)
public class RamYetActivity extends AbstractMvpBaseActivity<UserRamView, UserRamPresenter> implements UserRamView {


    @BindView(R.id.iv_title_return)
    ImageView ivTitleReturn;
    @BindView(R.id.tv_title_text)
    TextView tvTitleText;
    @BindView(R.id.rv_ram_list)
    RecyclerView rvRamList;
    @BindView(R.id.trl_ram)
    TwinklingRefreshLayout trlRam;
    @BindView(R.id.tv_no_data)
    TextView tvNoData;
    private ListAdapter mListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ram_yet);
        ButterKnife.bind(this);
    }

    @Override
    protected void initIntentData() {

    }


    @Override
    protected void onResume() {
        super.onResume();
        getMvpPresenter().clickRequest(SpUtils.getString(RamYetActivity.this, "token", ""), SpUtils.getInt(RamYetActivity.this, "userId", 0));

    }

    @Override
    protected void initView() {

        ivTitleReturn.setVisibility(View.VISIBLE);
        tvTitleText.setText(R.string.title_install_ram);
        rvRamList.setLayoutManager(new LinearLayoutManager(RamYetActivity.this));
        mListAdapter = new ListAdapter(this);
        mListAdapter.setViewType(USER_RAM);
        rvRamList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

    }

    @Override
    protected void initData() {
        Handler handler = new Handler();
        trlRam.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getMvpPresenter().clickRequest(SpUtils.getString(RamYetActivity.this, "token", ""), SpUtils.getInt(RamYetActivity.this, "userId", 0));

                        refreshLayout.finishRefreshing();
                    }
                }, 2000);
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.finishLoadmore();
                    }
                }, 2000);
            }
        });
    }

    @Override
    public void requestLoading() {

    }

    @Override
    public void userRamSuccess(UserRamBean result) {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(result);
        Log.e("awj userRamSuccess =", jsonStr);
        if (result.getCode() == 0) {
            if (result.getInstallUserCabinets().size() > 0) {
                tvNoData.setVisibility(View.GONE);
                mListAdapter.setUserRamList(result.getInstallUserCabinets());
                rvRamList.setAdapter(mListAdapter);
                mListAdapter.setOnItemClickListener(new ListAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(RamYetActivity.this, InstallInfoActivity.class);
                        intent.putExtra("locationX", result.getInstallUserCabinets().get(position).getLocationX());
                        intent.putExtra("locationY", result.getInstallUserCabinets().get(position).getLocationY());
                        intent.putExtra("ramName", result.getInstallUserCabinets().get(position).getCabinetName());
                        intent.putExtra("locationInfo", result.getInstallUserCabinets().get(position).getAddr());
                        intent.putExtra("ramTime", result.getInstallUserCabinets().get(position).getInstallTime());
                        intent.putExtra("cabinetId", result.getInstallUserCabinets().get(position).getCabinetId());
                        startActivity(intent);
                    }
                });

            } else {
                tvNoData.setVisibility(View.VISIBLE);
            }
        } else if (result.getCode() == 95598) {
            SpUtils.setBoolean(RamYetActivity.this, "isLogin", false);
            Toast.makeText(RamYetActivity.this, result.getMsg(), Toast.LENGTH_SHORT).show();
            ActivityUtils.startActivityAndFinish(RamYetActivity.this, LoginActivity.class);
        } else {
            ToastUtil.show(RamYetActivity.this, result.getMsg());
        }
    }

    @Override
    public void resultFailure(String result) {
        Log.e("awj", "userRaResultFailure =" + result);
        ToastUtil.show(RamYetActivity.this, "服务器异常");
    }

    @OnClick(R.id.iv_title_return)
    public void onViewClicked() {
        if (FunctionUtils.isFastClick()) {
            return;
        }
        finish();
    }
}
