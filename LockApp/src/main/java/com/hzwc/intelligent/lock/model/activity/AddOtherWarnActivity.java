package com.hzwc.intelligent.lock.model.activity;

import android.Manifest;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.google.gson.Gson;
import com.hzwc.intelligent.lock.R;
import com.hzwc.intelligent.lock.model.bean.BaseBean;
import com.hzwc.intelligent.lock.model.bean.LockByIdBean;
import com.hzwc.intelligent.lock.model.bean.MarkerBean;
import com.hzwc.intelligent.lock.model.bean.WarnTypesBean;
import com.hzwc.intelligent.lock.model.http.stomp.StompClient;
import com.hzwc.intelligent.lock.model.spinner.NiceSpinner;
import com.hzwc.intelligent.lock.model.utils.ActivityUtils;
import com.hzwc.intelligent.lock.model.utils.FunctionUtils;
import com.hzwc.intelligent.lock.model.utils.SpUtils;
import com.hzwc.intelligent.lock.model.utils.ToastUtil;
import com.hzwc.intelligent.lock.model.view.persenter.AddOtherWarnPresenter;
import com.hzwc.intelligent.lock.model.view.view.AddOtherWarnView;
import com.hzwc.intelligent.lock.mvpframework.factory.CreatePresenter;
import com.hzwc.intelligent.lock.mvpframework.view.AbstractMvpBaseActivity;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


@CreatePresenter(AddOtherWarnPresenter.class)
public class AddOtherWarnActivity extends AbstractMvpBaseActivity<AddOtherWarnView, AddOtherWarnPresenter> implements AddOtherWarnView, AMap.OnMapClickListener, AMap.OnCameraChangeListener, AMapLocationListener, AMap.OnMarkerClickListener {


    @BindView(R.id.iv_title_return)
    ImageView ivTitleReturn;
    @BindView(R.id.tv_title_text)
    TextView tvTitleText;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.tv_search_over)
    TextView tvSearchOver;
    @BindView(R.id.mapview_warn)
    MapView mapView;
    @BindView(R.id.center_image)
    ImageView centerImage;
    @BindView(R.id.iv_search)
    ImageView ivSearch;
    @BindView(R.id.ns_warning_type)
    NiceSpinner nsWarningType;
    @BindView(R.id.tv_cabinet_name)
    TextView tvCabinetName;
    @BindView(R.id.ns_mac)
    NiceSpinner nsMac;
    @BindView(R.id.et_info_ten)
    EditText etInfoTen;
    @BindView(R.id.rl_info)
    RelativeLayout rlInfo;
    @BindView(R.id.et_info_wrong)
    EditText etInfoWrong;

    @BindView(R.id.rl_other)
    RelativeLayout rlOther;

    private AMap aMap;

    public AMapLocationClientOption mLocationOption = null;
    private MarkerOptions markerOptions;
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    private Animation centerAnimation;
    List<MarkerBean.CabinetsBean> rimList = new ArrayList();
    private Marker marker;
    private int cabinetId;
    private HashMap<String, Integer> mWarnTpeMap;
    private int mCabinetId = -1;
    private List<String> dataSetType;
    private List<String> dataSetMac;
    final List<WarnTypesBean.WarnTypeBean> dataTypeBean = new LinkedList<>();
    private int warnType = -1;
    private List<LockByIdBean.CabinetBean.LocksBean> locksBeans;
    private int lockId = -1;
    private String updateLockId;
    private StompClient mStompClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_other_warn);
        ButterKnife.bind(this);
        mapView.onCreate(savedInstanceState);
        initMap();
    }

    @Override
    protected void initView() {
        tvTitleText.setText(getString(R.string.add_warning));
        tvSearch.setText(getString(R.string.tv_update));
        tvSearch.setVisibility(View.VISIBLE);
        ivTitleReturn.setVisibility(View.VISIBLE);

        initRl();
    }

    private void initRl() {

    }

    @Override
    protected void initData() {
        dataSetType = new ArrayList<>();
        dataSetMac = new ArrayList<>();
        locksBeans = new ArrayList<>();
        mStompClient = getMvpPresenter().connectStomp();
        getMvpPresenter().getWarnType(SpUtils.getString(this, "token", ""));
    }

    @Override
    public void requestLoading() {

    }

    @Override
    public void RimSuccess(MarkerBean result) {
        clearMarkers("1");
        Gson gson = new Gson();
        String jsonStr = gson.toJson(result);
        Log.e("awj RimSuccess =", jsonStr);
        rimList = new ArrayList<>();
        if (result.getCode() == 0) {
            rimList = result.getCabinets();
            initDataMarker(rimList, "1");

        }else if (result.getCode() == 95598) {
            SpUtils.setBoolean(AddOtherWarnActivity.this, "isLogin", false);
            Toast.makeText(AddOtherWarnActivity.this, result.getMsg(), Toast.LENGTH_SHORT).show();
            ActivityUtils.startActivityAndFinish(AddOtherWarnActivity.this, LoginActivity.class);
        }else {
            Toast.makeText(AddOtherWarnActivity.this, result.getMsg(), Toast.LENGTH_SHORT).show();
            return;
        }
    }

    @Override
    public void searchCabinetsByNoSuccess(LockByIdBean result) {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(result);
        Log.e("awj", "searchCabinetsByNoSuccess =" + jsonStr);

        if (result.getCode() == 0) {
            if(locksBeans.size()!=0 || dataSetMac.size()!=0){
                locksBeans.clear();
                dataSetMac.clear();
            }

            for (int i = 0; i < result.getCabinet().getLocks().size(); i++) {
                locksBeans.add(result.getCabinet().getLocks().get(i));
                dataSetMac.add(result.getCabinet().getLocks().get(i).getLockNo());
            }
            updateLockId = String.valueOf(result.getCabinet().getLocks().get(0).getLockId());
            if (result.getCabinet().getLocks().size() > 0) {

                nsMac.attachDataSource(dataSetMac);
            }

        }else if (result.getCode() == 95598) {
            SpUtils.setBoolean(AddOtherWarnActivity.this, "isLogin", false);
            Toast.makeText(AddOtherWarnActivity.this, result.getMsg(), Toast.LENGTH_SHORT).show();
            ActivityUtils.startActivityAndFinish(AddOtherWarnActivity.this, LoginActivity.class);
        }else {
            Toast.makeText(AddOtherWarnActivity.this, result.getMsg(), Toast.LENGTH_SHORT).show();
            return;
        }
    }

    @Override
    public void getWarnTypeSuccess(WarnTypesBean result) {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(result);
        Log.e("awj", "getWarnTypeSuccess=" + jsonStr);
        if (result.getCode() == 0) {
            mWarnTpeMap = new HashMap<>();
            for (WarnTypesBean.WarnTypeBean warnTypeBean : result.getWarnType()) {
                mWarnTpeMap.put(warnTypeBean.getWarnType(), warnTypeBean.getWarnInfoId());

            }

            for (int i = 0; i < result.getWarnType().size(); i++) {
                dataTypeBean.add(result.getWarnType().get(i));
                dataSetType.add(result.getWarnType().get(i).getWarnType());
            }
            warnType = dataTypeBean.get(0).getWarnInfoId();
            nsWarningType.attachDataSource(dataSetType);
        }else if (result.getCode() == 95598) {
            SpUtils.setBoolean(AddOtherWarnActivity.this, "isLogin", false);
            Toast.makeText(AddOtherWarnActivity.this, result.getMsg(), Toast.LENGTH_SHORT).show();
            ActivityUtils.startActivityAndFinish(AddOtherWarnActivity.this, LoginActivity.class);
        }else {
            Toast.makeText(AddOtherWarnActivity.this, result.getMsg(), Toast.LENGTH_SHORT).show();
            return;
        }
    }

    @Override
    public void installLocationWarnSuccess(BaseBean result) {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(result);
        Log.e("awj", "installLocationWarnSuccess =" + jsonStr);
        if (result.getCode() == 0) {
            Toast.makeText(AddOtherWarnActivity.this, getString(R.string.add_succeed), Toast.LENGTH_SHORT).show();
            finish();
        } else if (result.getCode() == 95598) {
            SpUtils.setBoolean(AddOtherWarnActivity.this, "isLogin", false);
            Toast.makeText(AddOtherWarnActivity.this, result.getMsg(), Toast.LENGTH_SHORT).show();
            ActivityUtils.startActivityAndFinish(AddOtherWarnActivity.this, LoginActivity.class);
        }else {
            Toast.makeText(AddOtherWarnActivity.this, result.getMsg(), Toast.LENGTH_SHORT).show();
            return;
        }
    }

    @Override
    public void resultFailure(String result) {
        Toast.makeText(AddOtherWarnActivity.this, "请检查网络", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    private void initMap() {


        AndPermission.with(AddOtherWarnActivity.this)
                .permission(Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                .onGranted(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        Log.e("awj", "==========成功 token =" + SpUtils.getString(AddOtherWarnActivity.this, "token", ""));
                    }
                }).onDenied(new Action() {
            @Override
            public void onAction(List<String> permissions) {
                //用户拒绝

            }
        }).start();

        if (aMap == null) {
            aMap = mapView.getMap();
            markerOptions = new MarkerOptions();


            aMap.setOnMapClickListener(this);
            aMap.setOnMarkerClickListener(this);
            aMap.setOnCameraChangeListener(this);
            MyLocationStyle myLocationStyle;
            myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
            myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);
            aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
            aMap.getUiSettings().setMyLocationButtonEnabled(true);//设置默认定位按钮是否显示，非必需设置。
            aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。

            centerAnimation = AnimationUtils.loadAnimation(AddOtherWarnActivity.this, R.anim.center_anim);
            aMap.getUiSettings().setZoomControlsEnabled(false);
            aMap.getUiSettings().setAllGesturesEnabled(true);//设置默认定位按钮是否显示，非必需设置。


            //初始化定位
            mLocationClient = new AMapLocationClient(AddOtherWarnActivity.this);
            //设置定位回调监听
            mLocationClient.setLocationListener(this);
            ////初始化AMapLocationClientOption对象
            mLocationOption = new AMapLocationClientOption();
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Device_Sensors);
            mLocationOption.setOnceLocation(false);
            mLocationOption.setOnceLocationLatest(true);
            mLocationOption.setLocationCacheEnable(false);
            mLocationOption.setNeedAddress(true);
            //给定位客户端对象设置定位参数
            mLocationClient.setLocationOption(mLocationOption);
            //启动定位
            mLocationClient.startLocation();

            aMap.setOnMapLoadedListener(new AMap.OnMapLoadedListener() {
                @Override
                public void onMapLoaded() {
                    //设置缩放比例
                    aMap.moveCamera(CameraUpdateFactory.zoomTo(16));
                }
            });


        }


    }


    private void initDataMarker(List<MarkerBean.CabinetsBean> list, String s) {


        Log.e("awj", "initDataMarker =" + list.size());

        for (int i = 0; i < list.size(); i++) {
            Log.e("awj", "initDataMarker size=" + list.get(i).getLocks().size());
            LatLng mLatlng = new LatLng(list.get(i).getLocationY(), list.get(i).getLocationX());

            Gson gson = new Gson();
            String jsonStr = gson.toJson(list.get(i).getLocks());
            Log.e("awj", "string json =" + jsonStr);
            markerOptions.title(list.get(i).getAddr() + "," + list.get(i).getCabinetId() + "," + list.get(i).getCabinetName());

            markerOptions.position(mLatlng)
                    .draggable(false)
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_ram_blue));
            //将数据添加到地图上
            marker = aMap.addMarker(markerOptions);
            marker.setObject(s);
        }
    }

    //删除指定Marker
    private void clearMarkers(String s) {

        List<Marker> mapScreenMarkers = aMap.getMapScreenMarkers();
        for (int i = 0; i < mapScreenMarkers.size(); i++) {
            Marker marker = mapScreenMarkers.get(i);
            if (marker.getObject() instanceof Object) {
                if (marker.getObject().equals("1")) {
                    marker.remove();//移除当前Marker
                }
            }
        }
        mapView.invalidate();//刷新地图
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {

    }

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {

//        mFinalChoosePosition = cameraPosition.target;

        centerImage.startAnimation(centerAnimation);

        getMvpPresenter().clickRimRequest(SpUtils.getString(AddOtherWarnActivity.this, "token", ""), cameraPosition.target.longitude, cameraPosition.target.latitude);

    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        String str = marker.getTitle();
        String[] strs = str.split(",");
        //环网柜Id
        cabinetId = Integer.parseInt(strs[1]);

        markerOptions = new MarkerOptions().icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                .position(marker.getPosition())
                .title("地址:" + strs[0].toString())
                .snippet("设施名字:" + strs[2].toString())
                .draggable(true);
        tvCabinetName.setText(strs[2].toString());

        mCabinetId = Integer.parseInt(strs[1]);

        getMvpPresenter().searchCabinetsByNo(SpUtils.getString(this, "token", ""), cabinetId);
        marker = aMap.addMarker(markerOptions);
        marker.showInfoWindow();

        return true;
    }

    @OnClick({R.id.iv_title_return, R.id.tv_search, R.id.ns_warning_type, R.id.ns_mac})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.iv_title_return:
                if (FunctionUtils.isFastClick()) {
                    return;
                }
                finish();
                break;
            case R.id.tv_search:

                getWarn();
                break;

            case R.id.ns_warning_type:
                if (FunctionUtils.isFastClick()) {
                    return;
                }

                nsWarningType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        warnType = dataTypeBean.get(position).getWarnInfoId();

                        if (position == 2) {
                            rlOther.setVisibility(View.VISIBLE);
                        } else {
                            rlOther.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


                break;
            case R.id.ns_mac:

                if (FunctionUtils.isFastClick()) {
                    return;
                }

                if (locksBeans.size() == 0) {
                    ToastUtil.show(AddOtherWarnActivity.this, "请选择设施或者该设施没有锁");
                }
                nsMac.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        lockId = locksBeans.get(position).getLockId();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                ToastUtil.show(AddOtherWarnActivity.this, "type =" + nsWarningType.getText());

                break;
        }
    }

    private void getWarn() {
        if (mWarnTpeMap == null) return;
        String lockNo = nsMac.getText().toString();
        String warningSummarize = etInfoTen.getText().toString();
        String warningParticulars = etInfoWrong.getText().toString();
        String warningType = nsWarningType.getText().toString().trim();
        if (lockId != -1) {
            updateLockId = String.valueOf(lockId);
        }
        if (TextUtils.isEmpty(warningType) && TextUtils.isEmpty(lockNo) && TextUtils.isEmpty(etInfoTen.getText()) && mCabinetId == -1) {
            Toast.makeText(AddOtherWarnActivity.this, getString(R.string.hint_06), Toast.LENGTH_SHORT).show();
        }
        if (warningType.equals(getString(R.string.warning_location))) {//提交位置告警
            mStompClient.send("/topic/getPoint", getMvpPresenter().serializationWebLocationWarningBean(lockNo, 1));
            getMvpPresenter().installLocationWarn(SpUtils.getString(AddOtherWarnActivity.this, "token", ""),
                    updateLockId, SpUtils.getInt(AddOtherWarnActivity.this, "userId", -1),
                    mCabinetId, warnType, markerOptions.getPosition().longitude, markerOptions.getPosition().latitude
                    , warningSummarize, warningParticulars);
        } else if (warningType.equals(getString(R.string.warning_time))) {//提交电量告警
            mStompClient.send("/topic/getPoint", getMvpPresenter().serializationWebPowerWarnBean(lockNo, 1));
            getMvpPresenter().installPowerWarn(SpUtils.getString(AddOtherWarnActivity.this, "token", ""),
                    updateLockId, SpUtils.getInt(AddOtherWarnActivity.this, "userId", -1),
                    mCabinetId, warnType
                    , warningSummarize, 5, warningParticulars);
        } else if (warningType.equals(getString(R.string.warning_else))) {//提交其他告警
            mStompClient.send("/topic/getPoint", getMvpPresenter().serializationWebIsWarnBean(lockNo, 1));
            getMvpPresenter().installRestsWarn(SpUtils.getString(AddOtherWarnActivity.this, "token", ""),
                    updateLockId, SpUtils.getInt(AddOtherWarnActivity.this, "userId", -1),
                    mCabinetId, warnType
                    , warningSummarize, warningParticulars);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mStompClient.disconnect();
    }

}
