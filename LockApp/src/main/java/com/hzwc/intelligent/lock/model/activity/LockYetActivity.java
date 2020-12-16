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
import com.hzwc.intelligent.lock.model.bean.UserLockBean;
import com.hzwc.intelligent.lock.model.utils.ActivityUtils;
import com.hzwc.intelligent.lock.model.utils.SpUtils;
import com.hzwc.intelligent.lock.model.utils.ToastUtil;
import com.hzwc.intelligent.lock.model.view.persenter.UserLockPresenter;
import com.hzwc.intelligent.lock.model.view.view.UserLockView;
import com.hzwc.intelligent.lock.mvpframework.factory.CreatePresenter;
import com.hzwc.intelligent.lock.mvpframework.view.AbstractMvpBaseActivity;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.hzwc.intelligent.lock.model.adapter.ListAdapter.USER_LOCK;

@CreatePresenter(UserLockPresenter.class)
public class LockYetActivity extends AbstractMvpBaseActivity<UserLockView, UserLockPresenter> implements UserLockView {


    @BindView(R.id.iv_title_return)
    ImageView ivTitleReturn;
    @BindView(R.id.tv_title_text)
    TextView tvTitleText;
    @BindView(R.id.rv_lock_list)
    RecyclerView rvRamList;
    @BindView(R.id.trl_lock)
    TwinklingRefreshLayout trlLock;
    @BindView(R.id.tv_no_data)
    TextView tvNoData;
    private ListAdapter mListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_yet);
        ButterKnife.bind(this);
    }

    @Override
    protected void initIntentData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        getMvpPresenter().clickRequest(SpUtils.getString(LockYetActivity.this, "token", ""), SpUtils.getInt(LockYetActivity.this, "userId", 0));

    }

    @Override
    protected void initView() {

        ivTitleReturn.setVisibility(View.VISIBLE);
        tvTitleText.setText(R.string.title_install_yet);
        rvRamList.setLayoutManager(new LinearLayoutManager(LockYetActivity.this));
        mListAdapter = new ListAdapter(this);
        mListAdapter.setViewType(USER_LOCK);
        rvRamList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

    }

    @Override
    protected void initData() {

        Handler handler = new Handler();
        trlLock.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        mPage = 1;
//                        if (mFlag == 0)
//                            mListAdapter.clearPowerWarnsBeanList();
//                        else if (mFlag == 1)
//                            mListAdapter.clearLocationWarnsBeanList();
//                        else if (mFlag == 2)
//                            mListAdapter.clearRestsWarnBeanBeanList();
//                        getWarn(mFlag, getContext().getString(R.string.warning_time), mPage, mLimit);
                        getMvpPresenter().clickRequest(SpUtils.getString(LockYetActivity.this, "token", ""), SpUtils.getInt(LockYetActivity.this, "userId", 0));

                        refreshLayout.finishRefreshing();
                    }
                }, 2000);
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        mPage++;
//                        getWarn(mFlag, getContext().getString(R.string.warning_time), mPage, mLimit);
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
    public void userLockSuccess(UserLockBean result) {

        Gson gson = new Gson();
        String jsonStr = gson.toJson(result);
        Log.e("awj userLockSuccess =", jsonStr);
        if (result.getCode() == 0) {
            if (result.getInstallUserLocks().size() > 0) {
                tvNoData.setVisibility(View.GONE);
                mListAdapter.setUserLockList(result.getInstallUserLocks());
                rvRamList.setAdapter(mListAdapter);
                mListAdapter.setOnItemClickListener(new ListAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(LockYetActivity.this, InstallLockInfoActivity.class);
                        intent.putExtra("locationX", result.getInstallUserLocks().get(position).getLocationX());
                        intent.putExtra("locationY", result.getInstallUserLocks().get(position).getLocationY());
                        intent.putExtra("ramName", result.getInstallUserLocks().get(position).getCabinetName());
                        intent.putExtra("locationInfo", result.getInstallUserLocks().get(position).getAddr());
                        intent.putExtra("ramTime", result.getInstallUserLocks().get(position).getInstallTime());
                        intent.putExtra("cabinetId", result.getInstallUserLocks().get(position).getCabinetId());
                        intent.putExtra("lockNo", result.getInstallUserLocks().get(position).getLockNo());
                        intent.putExtra("lockId", result.getInstallUserLocks().get(position).getLockId());
                        startActivity(intent);
                    }
                });

            }else {
                tvNoData.setVisibility(View.VISIBLE);
            }
        } else if (result.getCode() == 95598) {
            SpUtils.setBoolean(LockYetActivity.this, "isLogin", false);
            Toast.makeText(LockYetActivity.this, result.getMsg(), Toast.LENGTH_SHORT).show();
            ActivityUtils.startActivityAndFinish(LockYetActivity.this, LoginActivity.class);
        } else {
            ToastUtil.show(LockYetActivity.this, result.getMsg());
        }
    }

    @Override
    public void resultFailure(String result) {
        ToastUtil.show(LockYetActivity.this, "服务器异常");
    }

    @OnClick(R.id.iv_title_return)
    public void onViewClicked() {
        finish();
    }
}
