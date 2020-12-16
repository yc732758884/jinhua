package com.hzwc.intelligent.lock.model.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hzwc.intelligent.lock.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/7/20.
 */

public class RecordDetailsActivity extends Activity implements View.OnClickListener {
    @BindView(R.id.iv_title_return)
    ImageView ivTitleReturn;
    @BindView(R.id.tv_title_text)
    TextView tvTitleText;
    @BindView(R.id.tv_warning_type)
    TextView tvWarningType;
    @BindView(R.id.tv_cabinet_name)
    TextView tvCabinetName;
    @BindView(R.id.tv_warning_lock_mac)
    TextView tvWarningLockMac;
    @BindView(R.id.tv_record_open_time)
    TextView tvRecordOpenTime;
    @BindView(R.id.tv_open_lock_name)
    TextView tvOpenLockName;
    @BindView(R.id.tv_open_lock_phone)
    TextView tvOpenLockPhone;
    @BindView(R.id.tv_open_lock_unit)
    TextView tvOpenLockUnit;
    @BindView(R.id.tv_open_lock_section)
    TextView tvOpenLockSection;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_details);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        tvWarningType.setText( intent.getStringExtra("cabinetName"));
        tvCabinetName.setText( intent.getStringExtra("lockNo"));
        tvWarningLockMac.setText( intent.getStringExtra("power"));
        tvRecordOpenTime.setText( intent.getStringExtra("unlockTime"));
        tvOpenLockName.setText( intent.getStringExtra("name"));
        tvOpenLockPhone.setText( intent.getStringExtra("phone"));
        tvOpenLockUnit.setText( intent.getStringExtra("department"));
        tvOpenLockSection.setText( intent.getStringExtra("post"));

        ivTitleReturn.setVisibility(View.VISIBLE);
        tvTitleText.setText(getString(R.string.open_lock_record_details));
        ivTitleReturn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_title_return:
                finish();
                break;
        }
    }
}
