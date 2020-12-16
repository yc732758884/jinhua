package com.hzwc.intelligent.lock.model.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hzwc.intelligent.lock.R;
import com.hzwc.intelligent.lock.model.utils.FunctionUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/7/19.
 */

public class ElseWarningActivity extends Activity implements View.OnClickListener {
    @BindView(R.id.iv_title_return)
    ImageView ivTitleReturn;
    @BindView(R.id.tv_title_text)
    TextView tvTitleText;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.tv_warning_type)
    TextView tvWarningType;
    @BindView(R.id.tv_cabinet_name)
    TextView tvCabinetName;
    @BindView(R.id.tv_warning_lock_mac)
    TextView tvWarningLockMac;
    @BindView(R.id.tv_warning_summarize)
    TextView tvWarningSummarize;
    @BindView(R.id.tv_warning_particulars)
    TextView tvWarningParticulars;
    private double locationLon;
    private double locationLat;
    private String lockNo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_else_warning);
        ButterKnife.bind(this);
        tvTitleText.setText(getString(R.string.look_else_warning));
        tvTitleText.setVisibility(View.VISIBLE);
        tvSearch.setVisibility(View.VISIBLE);
        ivTitleReturn.setVisibility(View.VISIBLE);
        tvSearch.setText(getString(R.string.tv_info_lock_location));
        Intent intent = getIntent();
        tvWarningType.setText(intent.getStringExtra("warnType"));
        tvCabinetName.setText(intent.getStringExtra("cabinetName"));
        lockNo = intent.getStringExtra("lockNo");
        tvWarningLockMac.setText(lockNo);
        tvWarningSummarize.setText(intent.getStringExtra("infos"));
        tvWarningParticulars.setText(intent.getStringExtra("details"));
        locationLon = intent.getDoubleExtra("locationLon", -1);
        locationLat = intent.getDoubleExtra("locationLat", -1);
        ivTitleReturn.setOnClickListener(this);
        tvSearch.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_title_return:
                if(FunctionUtils.isFastClick()){
                    return;
                }
                finish();
                break;
            case R.id.tv_search:
                if(FunctionUtils.isFastClick()){
                    return;
                }
                Intent intentLocation = new Intent(ElseWarningActivity.this , WarnActivity.class);
                intentLocation.putExtra("flag",3);
                intentLocation.putExtra("locationLat", locationLat);
                intentLocation.putExtra("locationLon", locationLon);
                intentLocation.putExtra("lockNo", lockNo);

                startActivity(intentLocation);

                break;
        }
    }
}
