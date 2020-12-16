package com.hzwc.intelligent.lock.model.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.hzwc.intelligent.lock.R;
import com.hzwc.intelligent.lock.model.base.BaseActivity;
import com.hzwc.intelligent.lock.model.utils.FunctionUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WarnActivity extends BaseActivity implements AMap.OnMapClickListener, AMap.OnMarkerClickListener {

    @BindView(R.id.iv_title_return)
    ImageView ivTitleReturn;
    @BindView(R.id.tv_title_text)
    TextView tvTitleText;
    @BindView(R.id.mv_warn_map)
    MapView mvWarnMap;
    private double locationLat;
    private double locationLon;
    private String lockNo;
    private int power;
    private MarkerOptions markerOption;
    private LatLng latLng;
    private AMap aMap;
    private Marker marker;
    private MarkerOptions markerOptions;
    private int mFlag;
    private double locationX;
    private double locationY;
    private LatLng latLngBlue;
    private LatLng latLngRed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_undervoltage_warn);
        ButterKnife.bind(this);
        mvWarnMap.onCreate(savedInstanceState); // 此方法必须重写
    }

    @Override
    protected void initIntentData() {


        mFlag = getIntent().getIntExtra("flag", -1);

        if (mFlag == 1) {

            locationLat = getIntent().getDoubleExtra("locationLat", 0);
            locationLon = getIntent().getDoubleExtra("locationLon", 0);

            lockNo = getIntent().getStringExtra("lockNo");
            power = getIntent().getIntExtra("power", -1);
            latLng = new LatLng(locationLat, locationLon);

        } else if (mFlag == 2) {


            locationLat = getIntent().getDoubleExtra("locationLat", 0);
            locationLon = getIntent().getDoubleExtra("locationLon", 0);
            locationX = getIntent().getDoubleExtra("locationX", 0);
            locationY = getIntent().getDoubleExtra("locationY", 0);
            latLngBlue = new LatLng(locationY,locationX);
            latLngRed = new LatLng(locationLat, locationLon);

        }else if(mFlag == 3){
            locationLat = getIntent().getDoubleExtra("locationLat", 0);
            locationLon = getIntent().getDoubleExtra("locationLon", 0);

            lockNo = getIntent().getStringExtra("lockNo");

            latLng = new LatLng(locationLat, locationLon);
        }else if (mFlag==4){
            locationLat = Double.parseDouble(getIntent().getStringExtra("locationLat"));
            locationLon =Double.parseDouble(getIntent().getStringExtra("locationLon"));

            lockNo = getIntent().getStringExtra("lockNo");
            latLng = new LatLng(locationLat, locationLon);
        }


        Log.e("awj", "locationLat=" + locationLat);
        Log.e("awj", "locationLon=" + locationLon);
        Log.e("awj", "locationX=" + locationX);
        Log.e("awj", "locationY=" + locationY);
        Log.e("awj", "lockNo=" + lockNo);
        Log.e("awj", "power=" + power);

    }

    @Override
    protected void initView() {
        ivTitleReturn.setVisibility(View.VISIBLE);
        if (aMap == null) {
            aMap = mvWarnMap.getMap();
            markerOptions = new MarkerOptions();
            aMap.setOnMarkerClickListener(this);
        }

        if (mFlag == 1) {
            tvTitleText.setText("欠压告警");
            addMarkersToMap(latLng);// 往地图上添加marker
        } else if (mFlag == 2) {
            tvTitleText.setText("位置告警");
            addMarkersToMap(latLngRed, 1);// 往地图上添加marker
            addMarkersToMap(latLngBlue, 2);// 往地图上添加marker
        }else if(mFlag == 3){
            tvTitleText.setText("其他异常位置");
            addMarkersToMap(latLng);// 往地图上添加marker
        }else if (mFlag==4){
            tvTitleText.setText("锁未关告警");
            addMarkersToMap(latLng);
        }
    }

    private void addMarkersToMap(LatLng latLngBlue, int flag) {
        if (flag == 1) {
            markerOptions.position(latLngBlue)
                    .draggable(false)
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_warn_red));

            markerOptions.title("偏移位置");

            //将数据添加到地图上
            marker = aMap.addMarker(markerOptions);
            aMap.moveCamera(CameraUpdateFactory.changeLatLng(marker.getPosition()));
            aMap.moveCamera(CameraUpdateFactory.zoomTo(15));
        } else if (flag == 2) {
            markerOptions.position(latLngBlue)
                    .draggable(false)
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_location_blue));
            markerOptions.title("实际位置");
            //将数据添加到地图上
            marker = aMap.addMarker(markerOptions);

        }
    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.iv_title_return)
    public void onViewClicked() {
        if (FunctionUtils.isFastClick()) {
            return;
        }
        finish();
    }

    /**
     * 在地图上添加marker
     *
     * @param latLng
     */
    private void addMarkersToMap(LatLng latLng) {

        markerOptions.position(latLng)
                .draggable(false)
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_warn_red));
        //将数据添加到地图上
        marker = aMap.addMarker(markerOptions);
        aMap.moveCamera(CameraUpdateFactory.changeLatLng(marker.getPosition()));
        aMap.moveCamera(CameraUpdateFactory.zoomTo(15));

    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        if(mFlag == 1){

        markerOption = new MarkerOptions().icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                .position(latLng)
                .title("Mac:" + lockNo)
                .snippet("欠压:" + power)
                .draggable(true);
        }else if(mFlag == 2){
            markerOption = new MarkerOptions().icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                    .position(marker.getPosition())
                    .title(marker.getTitle())
                    .draggable(true);

        }else if(mFlag == 3){
            markerOption = new MarkerOptions().icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                    .position(latLng)
                    .title("Mac:" + lockNo)
                    .draggable(true);
        }
        marker = aMap.addMarker(markerOption);
        marker.showInfoWindow();
        return true;
    }
    @Override
    protected void onPause() {
        super.onPause();
        mvWarnMap.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mvWarnMap != null) {

            mvWarnMap.onDestroy();
        }
    }
}
