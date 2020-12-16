package com.hzwc.intelligent.lock.model.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hzwc.intelligent.lock.R;
import com.hzwc.intelligent.lock.model.activity.AddOtherWarnActivity;
import com.hzwc.intelligent.lock.model.activity.ElseWarningActivity;
import com.hzwc.intelligent.lock.model.activity.LoginActivity;
import com.hzwc.intelligent.lock.model.activity.WarnActivity;
import com.hzwc.intelligent.lock.model.adapter.ListAdapter;
import com.hzwc.intelligent.lock.model.bean.BaseBean;
import com.hzwc.intelligent.lock.model.bean.LocationWarnsBean;
import com.hzwc.intelligent.lock.model.bean.NoCloseInfo;
import com.hzwc.intelligent.lock.model.bean.Page;
import com.hzwc.intelligent.lock.model.bean.PowerWarnsBean;
import com.hzwc.intelligent.lock.model.bean.RestsWarnBean;
import com.hzwc.intelligent.lock.model.bean.WarnTypesBean;
import com.hzwc.intelligent.lock.model.utils.ActivityUtils;
import com.hzwc.intelligent.lock.model.utils.FunctionUtils;
import com.hzwc.intelligent.lock.model.utils.SpUtils;
import com.hzwc.intelligent.lock.model.view.persenter.AlarmPresenter;
import com.hzwc.intelligent.lock.model.view.view.AlarmView;
import com.hzwc.intelligent.lock.mvpframework.factory.CreatePresenter;
import com.hzwc.intelligent.lock.mvpframework.view.AbstractBaseFragment;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.hzwc.intelligent.lock.model.adapter.ListAdapter.LOCATION;
import static com.hzwc.intelligent.lock.model.adapter.ListAdapter.UNDERVOLTAGE;
import static com.hzwc.intelligent.lock.model.adapter.ListAdapter.WARNING_ELSE;

/**
 * Created by anna on 2018/2/24.
 */
@CreatePresenter(AlarmPresenter.class)
public class AlarmFragment extends AbstractBaseFragment<AlarmView, AlarmPresenter> implements AlarmView, View.OnClickListener {

    Unbinder unbinder;
    @BindView(R.id.iv_warning_line_left)
    ImageView ivWarningLineLeft;
    @BindView(R.id.ll_warning_time)
    LinearLayout llWarningTime;
    @BindView(R.id.iv_warning_line_right)
    ImageView ivWarningLineRight;
    @BindView(R.id.ll_warning_history)
    LinearLayout llWarningHistory;
    @BindView(R.id.iv_warning_else)
    ImageView ivWarningElse;
    @BindView(R.id.ll_warning_else)
    LinearLayout llWarningElse;
    @BindView(R.id.iv_warning_add)
    ImageView ivWarningAdd;
    @BindView(R.id.rl_alarm)
    RecyclerView rlAlarm;
    @BindView(R.id.trl_alarm)
    TwinklingRefreshLayout trlAlarm;
    @BindView(R.id.tv_alarm_no_data)
    TextView tvAlarmNoData;
    @BindView(R.id.iv_warning_close)
    ImageView ivWarningClose;
    @BindView(R.id.ll_warning_close)
    LinearLayout llWarningClose;
    private ListAdapter mListAdapter;
    private View mView;
    private Map<String, Integer> mWarnTpeMap;
    private int mFlag = 0;
    private int mLimit = 10;
    private int mPage = 1;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_alarm, container, false);
        unbinder = ButterKnife.bind(this, mView);
        initView();
        initData();
        return mView;
    }

    private void initData() {

        getMvpPresenter().getWarnType(SpUtils.getString(getContext(), "token", ""));


    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void findViews() {

    }

    @Override
    protected void setupListener() {

    }

    private void initView() {
        llWarningTime.setOnClickListener(this);
        llWarningHistory.setOnClickListener(this);
        llWarningElse.setOnClickListener(this);
        ivWarningAdd.setOnClickListener(this);
        llWarningClose.setOnClickListener(this);
        rlAlarm.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rlAlarm.setLayoutManager(layoutManager);
        mListAdapter = new ListAdapter(getContext());
        rlAlarm.setAdapter(mListAdapter);
        Handler handler = new Handler();
        trlAlarm.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPage = 1;
                        String s = null;
                        if (mFlag == 0) {
                            mListAdapter.clearPowerWarnsBeanList();
                            s = getContext().getString(R.string.warning_time);
                        } else if (mFlag == 1) {
                            mListAdapter.clearLocationWarnsBeanList();
                            s = getContext().getString(R.string.warning_location);
                        } else if (mFlag == 2) {
                            mListAdapter.clearRestsWarnBeanBeanList();
                            s = getContext().getString(R.string.warning_else);
                        }else if (mFlag==3){
//                            mListAdapter.clearRestsWarnBeanBeanList();
//                            s = getContext().getString(R.string.warning_else);
                        }
                        getWarn(mFlag, s, mPage, mLimit);
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
                        getWarn(mFlag, getContext().getString(R.string.warning_time), mPage, mLimit);
                        refreshLayout.finishLoadmore();
                    }
                }, 1300);
            }
        });
    }

    @Override
    public void dataLoading() {

    }

    @Override
    public void getWarnTypeSuccess(WarnTypesBean result) {

        if (result.getCode() == 0) {
            mWarnTpeMap = new HashMap<>();
            for (WarnTypesBean.WarnTypeBean warnTypeBean : result.getWarnType()) {

                mWarnTpeMap.put(warnTypeBean.getWarnType(), warnTypeBean.getWarnInfoId());
            }
            getWarn(0, getContext().getString(R.string.warning_time), 1, 10);
        }

    }


    private void getWarn(int flag, String string, int page, int limit) {
        if (mWarnTpeMap == null) return;
        int typeid=mWarnTpeMap.get(string);

            if (typeid!=0) {
                if (flag == 0) {
                    Log.e("1","0");
                    getMvpPresenter().getPowerWarn(SpUtils.getString(getContext(), "token", ""), page, limit,
                            SpUtils.getInt(getContext(), "userId", -1), typeid);
                } else if (flag == 1) {
                    Log.e("1","1");
                    getMvpPresenter().getLocationWarn(SpUtils.getString(getContext(), "token", ""), page, limit,
                            SpUtils.getInt(getContext(), "userId", -1), typeid);
                } else if (flag == 2) {
                    Log.e("1","2");
                    getMvpPresenter().getRestsWarn(SpUtils.getString(getContext(), "token", ""), page, limit,
                            SpUtils.getInt(getContext(), "userId", -1), typeid);
                }else if (flag==3){
                    Log.e("1","3");
                    getMvpPresenter().getNoCloseWarn(SpUtils.getString(getContext(), "token", ""), page, limit,
                            SpUtils.getInt(getContext(), "userId", -1),4);
                }

            }

    }

    @Override
    public void getPowerWarnSuccess(PowerWarnsBean result) {
        if (result.getCode() == 0 && result.getWarns() != null && result.getWarns().getList().size() != 0) {
            tvAlarmNoData.setVisibility(View.GONE);
            mListAdapter.setPowerWarnsBeanList(result.getWarns().getList());
            mListAdapter.setViewType(UNDERVOLTAGE);
            mListAdapter.notifyDataSetChanged();
            mListAdapter.setOnItemClickListener(new ListAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    PowerWarnsBean.WarnsBean.ListBean listBean = mListAdapter.getPowerWarnsBeanList().get(position);
                    Intent intent = new Intent(getActivity(), WarnActivity.class);
                    intent.putExtra("flag", 1);
                    intent.putExtra("locationLat", listBean.getLocationLat());
                    intent.putExtra("locationLon", listBean.getLocationLon());
                    intent.putExtra("lockNo", listBean.getLockNo());
                    intent.putExtra("power", listBean.getPower());

                    startActivity(intent);
                }
            });
        } else if (result.getCode() == 95598) {
            SpUtils.setBoolean(getContext(), "isLogin", false);
            Toast.makeText(getContext(), result.getMsg(), Toast.LENGTH_SHORT).show();
            ActivityUtils.startActivityAndFinish(getActivity(), LoginActivity.class);
        } else {
            if (mListAdapter.getPowerWarnsBeanList().size() == 0)
                tvAlarmNoData.setVisibility(View.VISIBLE);
            mListAdapter.setViewType(UNDERVOLTAGE);
            mListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void getLocationWarnSuccess(LocationWarnsBean result) {
        if (result.getCode() == 0 && result.getWarns() != null && result.getWarns().getList().size() != 0) {
            tvAlarmNoData.setVisibility(View.GONE);
            mListAdapter.setLocationWarnsBeanList(result.getWarns().getList());
            mListAdapter.setViewType(LOCATION);
            mListAdapter.notifyDataSetChanged();
            mListAdapter.setOnItemClickListener(new ListAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    LocationWarnsBean.WarnsBean.ListBean listBean = mListAdapter.getLocationWarnsBeanList().get(position);
                    Intent intentLocation = new Intent(getActivity(), WarnActivity.class);
                    intentLocation.putExtra("flag", 2);
                    intentLocation.putExtra("locationLat", listBean.getLocationLat());
                    intentLocation.putExtra("locationLon", listBean.getLocationLon());
//                    intentLocation.putExtra("lockNo", result.getWarns().getList().get(position).getLockNo());
                    intentLocation.putExtra("locationX", listBean.getLocationX());
                    intentLocation.putExtra("locationY", listBean.getLocationY());

                    startActivity(intentLocation);
                }
            });
        } else {
            if (mListAdapter.getLocationWarnsBeanList().size() == 0)
                tvAlarmNoData.setVisibility(View.VISIBLE);
            mListAdapter.setViewType(LOCATION);
            mListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void getRestsWarnSuccess(RestsWarnBean result) {

        if (result.getCode() == 0 && result.getWarns() != null && result.getWarns().getList().size() != 0) {

            tvAlarmNoData.setVisibility(View.GONE);
            mListAdapter.setViewType(WARNING_ELSE);
            mListAdapter.setRestsWarnBeanBeanList(result.getWarns().getList());
            mListAdapter.notifyDataSetChanged();
            mListAdapter.setOnItemClickListener(new ListAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent intent = new Intent(getActivity(), ElseWarningActivity.class);
                    RestsWarnBean.WarnsBean.ListBean listBean = mListAdapter.getRestsWarnBeanBeanList().get(position);
                    intent.putExtra("warnType", listBean.getWarnType());
                    intent.putExtra("cabinetName", String.valueOf(listBean.getCabinetName()));
                    intent.putExtra("lockNo", listBean.getLockNo());
                    intent.putExtra("infos", listBean.getInfos());
                    intent.putExtra("details", listBean.getDetails());
                    intent.putExtra("locationLon", listBean.getLocationLon());
                    intent.putExtra("locationLat", listBean.getLocationLat());
                    startActivity(intent);
                }
            });
        } else {
            if (mListAdapter.getRestsWarnBeanBeanList().size() == 0)
                tvAlarmNoData.setVisibility(View.VISIBLE);
            mListAdapter.setViewType(WARNING_ELSE);
            mListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void getNoCloseWarnSuccess(BaseBean<Page<NoCloseInfo>> result) {



        if (result.getCode() == 0 && result.getData() != null && result.getData().getList().size() != 0) {

            tvAlarmNoData.setVisibility(View.GONE);
            mListAdapter.setViewType(22);
            mListAdapter.setNoCloseInfoList(result.getData().getList());
            mListAdapter.notifyDataSetChanged();


            mListAdapter.setOnItemClickListener(new ListAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent intent = new Intent(getActivity(), WarnActivity.class);
                    NoCloseInfo listBean = mListAdapter.getNoCloseList().get(position);
                    intent.putExtra("cabinetName", String.valueOf(listBean.getCabinetName()));
                    intent.putExtra("lockNo", listBean.getLockNo());
                    intent.putExtra("locationLon", listBean.getLocationLon());
                    intent.putExtra("locationLat", listBean.getLocationLat());
                    intent.putExtra("flag", 4);


                    startActivity(intent);
                }
            });
        } else {
            if (mListAdapter.getRestsWarnBeanBeanList().size() == 0)
                tvAlarmNoData.setVisibility(View.VISIBLE);
            mListAdapter.setViewType(WARNING_ELSE);
            mListAdapter.notifyDataSetChanged();
        }




    }

    @Override
    public void dataFailure(String result) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_warning_time:
                if (FunctionUtils.isFastClick()) {
                    return;
                }
                ivWarningLineLeft.setVisibility(View.VISIBLE);
                ivWarningLineRight.setVisibility(View.GONE);
                ivWarningElse.setVisibility(View.GONE);
                ivWarningClose.setVisibility(View.GONE);
                mListAdapter.clearPowerWarnsBeanList();
                mFlag = 0;
                mPage = 1;
                getWarn(mFlag, getContext().getString(R.string.warning_time), mPage, mLimit);
                break;
            case R.id.ll_warning_history:
                if (FunctionUtils.isFastClick()) {
                    return;
                }
                ivWarningLineLeft.setVisibility(View.GONE);
                ivWarningLineRight.setVisibility(View.VISIBLE);
                ivWarningElse.setVisibility(View.GONE);
                ivWarningClose.setVisibility(View.GONE);

                mListAdapter.clearLocationWarnsBeanList();
                mFlag = 1;
                mPage = 1;
                getWarn(mFlag, getContext().getString(R.string.warning_location), mPage, mLimit);
                break;
            case R.id.ll_warning_else:
                if (FunctionUtils.isFastClick()) {
                    return;
                }
                ivWarningLineLeft.setVisibility(View.GONE);
                ivWarningLineRight.setVisibility(View.GONE);
                ivWarningElse.setVisibility(View.VISIBLE);
                ivWarningClose.setVisibility(View.GONE);

                mListAdapter.clearRestsWarnBeanBeanList();
                mFlag = 2;
                mPage = 1;
                getWarn(mFlag, getContext().getString(R.string.warning_else), mPage, mLimit);
                break;

            case R.id.ll_warning_close:

                ivWarningLineLeft.setVisibility(View.GONE);
                ivWarningLineRight.setVisibility(View.GONE);
                ivWarningElse.setVisibility(View.GONE);
                ivWarningClose.setVisibility(View.VISIBLE);

              mListAdapter.clearNoclose();
               mFlag = 3;
               mPage = 1;
              getWarn(mFlag, getContext().getString(R.string.warning_else), mPage, mLimit);

                break;
            case R.id.iv_warning_add:
                if (FunctionUtils.isFastClick()) {
                    return;
                }
                ActivityUtils.startActivity(getActivity(), AddOtherWarnActivity.class);
                break;
            default:
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
