package com.hzwc.intelligent.lock.model.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hzwc.intelligent.lock.model.activity.InstallLockActivity;
import com.hzwc.intelligent.lock.model.activity.LockYetActivity;
import com.hzwc.intelligent.lock.model.activity.RamYetActivity;
import com.hzwc.intelligent.lock.model.activity.RingMainUnitActivity;
import com.hzwc.intelligent.lock.model.base.BaseFragment;
import com.hzwc.intelligent.lock.R;
import com.hzwc.intelligent.lock.model.utils.ActivityUtils;
import com.hzwc.intelligent.lock.model.utils.FunctionUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * Created by anna on 2018/2/24.
 */

public class InstallFragment extends BaseFragment {

    Unbinder unbinder;
    @BindView(R.id.tv_title_text)
    TextView tvTitleText;
    @BindView(R.id.rl_install_first)
    RelativeLayout rlInstallFirst;
    @BindView(R.id.rl_install_second)
    RelativeLayout rlInstallSecond;
    @BindView(R.id.rl_install_third)
    RelativeLayout rlInstallThird;
    @BindView(R.id.rl_install_fourth)
    RelativeLayout rlInstallFourth;
    private View mView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_real_time, container, false);
        unbinder = ButterKnife.bind(this, mView);
        tvTitleText.setText("安装");
        return mView;
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




    @OnClick({R.id.rl_install_first, R.id.rl_install_second, R.id.rl_install_third, R.id.rl_install_fourth})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_install_first:
                if (FunctionUtils.isFastClick()) {
                    return;
                }

                ActivityUtils.startActivity(getActivity(),RingMainUnitActivity.class);
                break;
            case R.id.rl_install_second:
                if (FunctionUtils.isFastClick()) {
                    return;
                }
                ActivityUtils.startActivity(getActivity(),InstallLockActivity.class);
                break;
            case R.id.rl_install_third:
                if (FunctionUtils.isFastClick()) {
                    return;
                }
                ActivityUtils.startActivity(getActivity(),RamYetActivity.class);
                break;
            case R.id.rl_install_fourth:
                if (FunctionUtils.isFastClick()) {
                    return;
                }
                ActivityUtils.startActivity(getActivity(),LockYetActivity.class);
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
