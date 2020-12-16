package com.hzwc.intelligent.lock.model.activity;

import android.os.Bundle;
import android.view.View;

import com.hzwc.intelligent.lock.R;
import com.hzwc.intelligent.lock.model.bean.MessageBean;
import com.hzwc.intelligent.lock.model.view.persenter.NewRUNPresenter;
import com.hzwc.intelligent.lock.model.view.view.NewRUNView;
import com.hzwc.intelligent.lock.mvpframework.factory.CreatePresenter;
import com.hzwc.intelligent.lock.mvpframework.view.AbstractMvpBaseActivity;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/7/11.
 */

@CreatePresenter(NewRUNPresenter.class)
public class NewRUNActivity extends AbstractMvpBaseActivity<NewRUNView, NewRUNPresenter> implements NewRUNView, View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_run);
        ButterKnife.bind(this);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
//        getMvpPresenter().bluetoothState();
//        getMvpPresenter().searchDevice();
    }

    @Override
    protected void initIntentData() {

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void dataLoading() {

    }

    @Override
    public void dataSuccess(MessageBean result) {

    }

    @Override
    public void dataFailure(String result) {

    }
}
