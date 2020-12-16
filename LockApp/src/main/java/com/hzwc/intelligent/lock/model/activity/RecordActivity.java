package com.hzwc.intelligent.lock.model.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hzwc.intelligent.lock.R;
import com.hzwc.intelligent.lock.model.adapter.ListAdapter;
import com.hzwc.intelligent.lock.model.bean.UnlockApplyBean;
import com.hzwc.intelligent.lock.model.utils.ActivityUtils;
import com.hzwc.intelligent.lock.model.utils.SpUtils;
import com.hzwc.intelligent.lock.model.utils.ToastUtil;
import com.hzwc.intelligent.lock.model.view.persenter.RecordPresenter;
import com.hzwc.intelligent.lock.model.view.view.RecordView;
import com.hzwc.intelligent.lock.mvpframework.factory.CreatePresenter;
import com.hzwc.intelligent.lock.mvpframework.view.AbstractMvpBaseActivity;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.hzwc.intelligent.lock.model.adapter.ListAdapter.OPEN_LOCK_HISTORY;


/**
 * Created by Administrator on 2018/6/26.
 */
@CreatePresenter(RecordPresenter.class)
public class RecordActivity extends AbstractMvpBaseActivity<RecordView, RecordPresenter> implements RecordView, View.OnClickListener {

    @BindView(R.id.iv_title_return)
    ImageView ivTitleReturn;
    @BindView(R.id.tv_title_text)
    TextView tvTitleText;
    @BindView(R.id.rv_electric_box)
    RecyclerView rvElectricBox;
    @BindView(R.id.iv_search)
    ImageView ivSearch;
    @BindView(R.id.tv_time_start)
    TextView tvTimeStart;
    @BindView(R.id.tv_reset)
    TextView tvReset;
    @BindView(R.id.tv_time_end)
    TextView tvTimeEnd;
    @BindView(R.id.trl_record)
    TwinklingRefreshLayout trlRecord;
    @BindView(R.id.tv_record_no_data)
    TextView tvRecordNoData;
    private ListAdapter mListAdapter;
    private String mStartTime;
    private boolean mActivityJudge;
    private String mLockNo;
    private String mCabinetName;
    private int mLimit = 10;
    private int mPage = 1;
    private String mEndTime;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        ButterKnife.bind(this);
    }

    @Override
    protected void initView() {
        ivTitleReturn.setVisibility(View.VISIBLE);
        ivTitleReturn.setOnClickListener(this);
        tvTimeStart.setOnClickListener(this);
        tvReset.setOnClickListener(this);
        tvTimeEnd.setOnClickListener(this);
        rvElectricBox.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        layoutManager = new LinearLayoutManager(this);
        rvElectricBox.setLayoutManager(layoutManager);
        mListAdapter = new ListAdapter(this);
        mListAdapter.setViewType(OPEN_LOCK_HISTORY);
        rvElectricBox.setAdapter(mListAdapter);
        Handler handler = new Handler();
        trlRecord.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPage = 1;
                        mListAdapter.clearUnlockApplyBeanList();
                        getMvpPresenter().getUserUnlockRecord(SpUtils.getString(RecordActivity.this, "token", ""),
                                mPage, mLimit, SpUtils.getInt(RecordActivity.this, "userId", -1),
                                mCabinetName, mLockNo, mStartTime, mEndTime);
                        refreshLayout.finishRefreshing();
                    }
                }, 1300);
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPage++;
                        getMvpPresenter().getUserUnlockRecord(SpUtils.getString(RecordActivity.this, "token", ""),
                                mPage, mLimit, SpUtils.getInt(RecordActivity.this, "userId", -1),
                                mCabinetName, mLockNo, mStartTime, mEndTime);
                        refreshLayout.finishLoadmore();
                    }
                }, 1300);
            }
        });
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        mActivityJudge = intent.getBooleanExtra("a", false);
        mLockNo = intent.getStringExtra("lockNo");
        mCabinetName = intent.getStringExtra("cabinetName");
        if (mActivityJudge) {
            ivSearch.setVisibility(View.VISIBLE);
            ivSearch.setOnClickListener(this);
            tvTitleText.setText(String.format(getResources().getString(R.string.open_lock_record), ""));
        } else {
            tvTitleText.setText(String.format(getResources().getString(R.string.open_lock_record), mLockNo));
        }
        getMvpPresenter().getUserUnlockRecord(SpUtils.getString(this, "token", ""),
                mPage, mLimit, SpUtils.getInt(RecordActivity.this, "userId", -1),
                mCabinetName, mLockNo, mStartTime, mEndTime);
    }

    @Override
    protected void initIntentData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_title_return:
                finish();
                break;
            case R.id.iv_search:
                startActivity(new Intent(RecordActivity.this, SearchStationActivity.class));
                break;
            case R.id.tv_time_start:
                getMvpPresenter().onStartTimePicker(this);
                tvTimeEnd.setText("");
                break;
            case R.id.tv_time_end:
                getMvpPresenter().onEndTimePicker(this);
                break;
            case R.id.tv_reset:
                tvTimeStart.setText(getString(R.string.start_time));
                tvTimeEnd.setText(getString(R.string.end_time));
                mStartTime = null;
                mEndTime = null;
                mListAdapter.clearUnlockApplyBeanList();
                getMvpPresenter().getUserUnlockRecord(SpUtils.getString(this, "token", ""),
                        mPage, mLimit, SpUtils.getInt(RecordActivity.this, "userId", -1),
                        mCabinetName, mLockNo, mStartTime, mEndTime);
                break;
        }
    }

    @Override
    public void dataLoading() {

    }

    @Override
    public void dataSuccess(UnlockApplyBean result) {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(result);
//        Log.e("awj dataSuccess =", jsonStr);
        if (result.getCode() == 0) {
            mListAdapter.setUnlockApplyBeanList(result.getUnlockRecordEntity().getList());
            mListAdapter.notifyDataSetChanged();

            if (result.getUnlockRecordEntity().getList().size() > 0) {
                tvRecordNoData.setVisibility(View.GONE);
            } else {
                if (mPage > 1) {
                    tvRecordNoData.setVisibility(View.GONE);
                } else {
                    tvRecordNoData.setVisibility(View.VISIBLE);
                }
            }

            mListAdapter.setOnItemClickListener(new ListAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    UnlockApplyBean.UnlockRecordEntityBean.ListBean list = mListAdapter.getUnlockRecordAllBeanList().get(position);
//                    UnlockApplyBean.UnlockRecordEntityBean.ListBean list = result.getUnlockRecordEntity().getList().get(position);
                    Intent intent = new Intent(RecordActivity.this, RecordDetailsActivity.class);
                    intent.putExtra("cabinetName", list.getCabinetName());
                    intent.putExtra("lockNo", list.getLockNo());
                    intent.putExtra("power", String.valueOf(list.getPower()));
                    intent.putExtra("unlockTime", list.getUnlockTime());
                    intent.putExtra("name", list.getName());
                    intent.putExtra("phone", list.getPhone());
                    intent.putExtra("department", list.getDepartment());
                    intent.putExtra("post", list.getPost());
                    startActivity(intent);

                }
            });
        } else if (result.getCode() == 95598) {
            SpUtils.setBoolean(RecordActivity.this, "isLogin", false);
            Toast.makeText(RecordActivity.this, result.getMsg(), Toast.LENGTH_SHORT).show();
            ActivityUtils.startActivityAndFinish(RecordActivity.this, LoginActivity.class);
        } else {
            tvRecordNoData.setVisibility(View.GONE);
            ToastUtil.show(RecordActivity.this, result.getMsg());
        }
    }

    @Override
    public void onEndTimePicker(String endTime) {
        mEndTime = endTime;
        tvTimeEnd.setText(endTime);
        mListAdapter.clearUnlockApplyBeanList();
        getMvpPresenter().getUserUnlockRecord(SpUtils.getString(this, "token", ""),
                mPage, mLimit, SpUtils.getInt(this, "userId", -1),
                mCabinetName, mLockNo, mStartTime, endTime);
    }

    @Override
    public void onStartTimePicker(String startTime) {
        tvTimeStart.setText(startTime);
        mStartTime = startTime;
    }


    @Override
    public void dataFailure(String result) {

    }
}
