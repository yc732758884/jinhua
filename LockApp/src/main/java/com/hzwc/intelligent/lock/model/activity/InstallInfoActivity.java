package com.hzwc.intelligent.lock.model.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.google.gson.Gson;
import com.hzwc.intelligent.lock.R;
import com.hzwc.intelligent.lock.model.base.BaseActivity;
import com.hzwc.intelligent.lock.model.bean.AdCodeBean;
import com.hzwc.intelligent.lock.model.bean.BaseBean;
import com.hzwc.intelligent.lock.model.bean.SearchAddressInfo;
import com.hzwc.intelligent.lock.model.utils.ActivityUtils;
import com.hzwc.intelligent.lock.model.utils.FunctionUtils;
import com.hzwc.intelligent.lock.model.utils.SpUtils;
import com.hzwc.intelligent.lock.model.utils.ToastUtil;
import com.hzwc.intelligent.lock.model.view.persenter.InstallRamInfoPresenter;
import com.hzwc.intelligent.lock.model.view.view.InstallRamInfoView;
import com.hzwc.intelligent.lock.mvpframework.factory.CreatePresenter;
import com.hzwc.intelligent.lock.mvpframework.view.AbstractMvpBaseActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@CreatePresenter(InstallRamInfoPresenter.class)
public class InstallInfoActivity extends AbstractMvpBaseActivity<InstallRamInfoView, InstallRamInfoPresenter> implements InstallRamInfoView,
        AMapLocationListener,
        AMap.OnMapClickListener,
        AMap.OnCameraChangeListener,
        AMap.OnMarkerClickListener,
        GeocodeSearch.OnGeocodeSearchListener {

    @BindView(R.id.iv_title_return)
    ImageView ivTitleReturn;
    @BindView(R.id.tv_title_text)
    TextView tvTitleText;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.tv_search_over)
    TextView tvSearchOver;
    @BindView(R.id.info_map)
    MapView infoMap;
    @BindView(R.id.iv_info_center_image)
    ImageView ivInfoCenterImage;
    @BindView(R.id.et_info_ram_name)
    EditText etInfoRamName;
    @BindView(R.id.et_info_ram_location)
    EditText etInfoRamLocation;
    @BindView(R.id.tv_info_ram_time)
    TextView tvInfoRamTime;
    private double locationX;
    private double locationY;
    private String ramName;
    private String locationInfo;
    private String ramTime;
    private LatLng latLonPoint;
    private Animation centerAnimation;

    public SearchAddressInfo mAddressInfoFirst = null;
    private boolean isHandDrag = true;
    private boolean isFirstLoad = true;
    private boolean isBackFromSearch = false;
    private int currentPage = 0;// 当前页面，从0开始计数
    private PoiSearch.Query query;// Poi查询条件类
    private PoiSearch poiSearch;
    private String city = "";

    private AMap aMap;
    private UiSettings uiSettings;
    private MarkerOptions markerOptions;
    private LatLng mFinalChoosePosition;
    private GeocodeSearch geocoderSearch;
    private Marker marker;
    private boolean isChange = false;
    private int cabinetId;
    private String jsonStr;
    private String adCode;
    private int areaId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_install_info);
        ButterKnife.bind(this);
        infoMap.onCreate(savedInstanceState);
    }

    @Override
    protected void initIntentData() {


        locationY = getIntent().getDoubleExtra("locationX", 0);
        locationX = getIntent().getDoubleExtra("locationY", 0);
        ramName = getIntent().getStringExtra("ramName");
        locationInfo = getIntent().getStringExtra("locationInfo");
        ramTime = getIntent().getStringExtra("ramTime");
        cabinetId = getIntent().getIntExtra("cabinetId", -1);
        Log.e("awj", "locationX" + locationX);
        Log.e("awj", "locationY" + locationY);
        Log.e("awj", "ramName" + ramName);
        Log.e("awj", "locationInfo" + locationInfo);
        Log.e("awj", "ramTime" + ramTime);

    }

    @Override
    protected void initView() {
        ivTitleReturn.setVisibility(View.VISIBLE);
        tvTitleText.setText(R.string.title_install_ram_info);
        tvSearch.setVisibility(View.VISIBLE);
        etInfoRamName.setCursorVisible(false);
        etInfoRamName.setFocusable(false);
        etInfoRamName.setFocusableInTouchMode(false);
        etInfoRamName.setText(ramName);
        etInfoRamLocation.setCursorVisible(false);
        etInfoRamLocation.setFocusable(false);
        etInfoRamLocation.setFocusableInTouchMode(false);
        etInfoRamLocation.setText(locationInfo);
        tvInfoRamTime.setText(ramTime);
        tvSearch.setText("修改");
    }

    @Override
    protected void initData() {
        //经纬度信息
        latLonPoint = new LatLng(locationX, locationY);

        centerAnimation = AnimationUtils.loadAnimation(this, R.anim.center_anim);
        initMap(latLonPoint);
    }

    @OnClick({R.id.iv_title_return, R.id.tv_search, R.id.tv_search_over})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_title_return:
                if (FunctionUtils.isFastClick()) {
                    return;
                }
                finish();
                break;
            case R.id.tv_search:
                if (FunctionUtils.isFastClick()) {
                    return;
                }


                etInfoRamName.setFocusable(true);
                etInfoRamName.setCursorVisible(true);
                etInfoRamName.setFocusableInTouchMode(true);
                etInfoRamName.requestFocus();

//                etInfoRamLocation.setCursorVisible(true);
//                etInfoRamLocation.setFocusable(true);
//                etInfoRamLocation.setFocusableInTouchMode(true);
//                etInfoRamLocation.requestFocus();
                tvSearch.setText("完成");
                tvSearch.setVisibility(View.GONE);

                tvSearchOver.setVisibility(View.VISIBLE);
                isChange = true;

                break;
            case R.id.tv_search_over:
                if (FunctionUtils.isFastClick()) {
                    return;
                }
                etInfoRamName.setFocusable(false);
                etInfoRamName.setCursorVisible(false);
                etInfoRamName.setFocusableInTouchMode(false);

//                etInfoRamLocation.setCursorVisible(false);
//                etInfoRamLocation.setFocusable(false);
//                etInfoRamLocation.setFocusableInTouchMode(false);
                tvSearch.setVisibility(View.VISIBLE);

                tvSearchOver.setVisibility(View.GONE);
                isChange = false;


                Log.e("awj", "areaId 1= " + areaId);
                 getMvpPresenter().clickRequest(SpUtils.getString(InstallInfoActivity.this, "token", ""), etInfoRamLocation.getText().toString(), cabinetId, etInfoRamName.getText().toString(), mFinalChoosePosition.longitude, mFinalChoosePosition.latitude, areaId);
                break;
        }
    }


    private void initMap(LatLng latLonPoint) {


        if (aMap == null) {
            aMap = infoMap.getMap();
            markerOptions = new MarkerOptions();
            uiSettings = aMap.getUiSettings();
            //地图比例尺的开启
            uiSettings.setScaleControlsEnabled(true);
            //关闭地图缩放按钮 就是那个加号 和减号
            uiSettings.setZoomControlsEnabled(false);
            aMap.setOnMapClickListener(this);
            aMap.setOnMarkerClickListener(this);
            aMap.setOnCameraChangeListener(this);
            //对amap添加移动地图事件监听器
            aMap.setOnCameraChangeListener(this);
            markerOptions.position(latLonPoint)
                    .draggable(false)
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_ram_blue));
            //将数据添加到地图上
            marker = aMap.addMarker(markerOptions);

            mFinalChoosePosition = marker.getPosition();
        }

        setMap();

        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLonPoint, 16));


//        setMap();
    }

    private void setMap() {
        geocoderSearch = new GeocodeSearch(getApplicationContext());

        //设置逆地理编码监听
        geocoderSearch.setOnGeocodeSearchListener(this);
    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {

    }

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {

        mFinalChoosePosition = cameraPosition.target;

        ivInfoCenterImage.startAnimation(centerAnimation);


        if (isHandDrag || isFirstLoad) {//手动去拖动地图

            // 开始进行poi搜索
            getAddressFromLonLat(cameraPosition.target);
            doSearchQueryByPosition();

        } else if (isBackFromSearch) {
            //搜索地址返回后 拿到选择的位置信息继续搜索附近的兴趣点
            isBackFromSearch = false;
            doSearchQueryByPosition();
        }
        isHandDrag = true;
        isFirstLoad = false;
    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult result, int i) {
        if (i == 1000) {//转换成功
            if (result != null && result.getRegeocodeAddress() != null
                    && result.getRegeocodeAddress().getFormatAddress() != null) {
                //拿到详细地址
                String addressName = result.getRegeocodeAddress().getFormatAddress(); // 逆转地里编码不是每次都可以得到对应地图上的opi

                //条目中第一个地址 也就是当前你所在的地址
                mAddressInfoFirst = new SearchAddressInfo(addressName, addressName, false, convertToLatLonPoint(mFinalChoosePosition));


                //TODO
                adCode = result.getRegeocodeAddress().getAdCode();
                getMvpPresenter().clickCodeRequest(SpUtils.getString(InstallInfoActivity.this,"token",""),adCode);

                //其实也是可以在这就能拿到附近的兴趣点的
                ToastUtil.show(this, "info = " + mAddressInfoFirst.addressName);
                Log.e("awj", "info = " + mFinalChoosePosition.longitude + "info = " + mFinalChoosePosition.latitude);
                if (isChange) {
                    etInfoRamLocation.setText(mAddressInfoFirst.addressName);
                }

            } else {
                ToastUtil.show(this, "没有搜到");
            }
        } else {
            ToastUtil.showerror(this, i);
        }
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {

                //获取定位时间
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(aMapLocation.getTime());
                df.format(date);
                Log.e("awj", "Install onLocationChanged "
                        + aMapLocation.getErrorCode() + ", el:"
                        + aMapLocation.getLatitude() + ", ed:"
                        + aMapLocation.getLongitude());


            } else {
                //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                Log.e("awj", "Install onLocationChanged Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
            }
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        infoMap.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (infoMap != null) {

            infoMap.onDestroy();
        }
    }

    /**
     * 根据经纬度得到地址
     */
    public void getAddressFromLonLat(final LatLng latLonPoint) {
        // 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
        RegeocodeQuery query = new RegeocodeQuery(convertToLatLonPoint(latLonPoint), 200, GeocodeSearch.AMAP);
        geocoderSearch.getFromLocationAsyn(query);// 设置同步逆地理编码请求
    }


    /**
     * 开始进行poi搜索
     * 通过经纬度获取附近的poi信息
     * <p>
     * 1、keyword 传 ""
     * 2、poiSearch.setBound(new PoiSearch.SearchBound(lpTemp, 5000, true)); 根据
     */
    protected void doSearchQueryByPosition() {

        currentPage = 0;
        query = new PoiSearch.Query("", "", city);// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        query.setPageSize(20);// 设置每页最多返回多少条poiitem
        query.setPageNum(currentPage);// 设置查第一页

        LatLonPoint llPoint = convertToLatLonPoint(mFinalChoosePosition);

        if (llPoint != null) {
            poiSearch = new PoiSearch(this, query);
//            poiSearch.setOnPoiSearchListener(this);  // 实现  onPoiSearched  和  onPoiItemSearched
            poiSearch.setBound(new PoiSearch.SearchBound(llPoint, 5000, true));//
            // 设置搜索区域为以lpTemp点为圆心，其周围5000米范围
            poiSearch.searchPOIAsyn();// 异步搜索
        }
    }

    /**
     * 把LatLng对象转化为LatLonPoint对象
     */
    public static LatLonPoint convertToLatLonPoint(LatLng latlon) {
        return new LatLonPoint(latlon.latitude, latlon.longitude);
    }

    @Override
    public void requestLoading() {

    }

    @Override
    public void installRamInfoSuccess(BaseBean result) {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(result);
        Log.e("awj", "installRamInfoSuccess =" + jsonStr);

        if (result.getCode() == 0) {
            ToastUtil.show(InstallInfoActivity.this, "修改成功");
            finish();
        } else if (result.getCode() == 95598) {
            SpUtils.setBoolean(InstallInfoActivity.this, "isLogin", false);
            Toast.makeText(InstallInfoActivity.this, result.getMsg(), Toast.LENGTH_SHORT).show();
            ActivityUtils.startActivityAndFinish(InstallInfoActivity.this, LoginActivity.class);
        } else {
            ToastUtil.show(InstallInfoActivity.this, result.getMsg());
        }
    }

    @Override
    public void dataSuccess(AdCodeBean result) {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(result);
        Log.e("awj", "dataSuccess =" + jsonStr);

        if (result.getCode() == 0) {
            areaId = result.getAreaId();
        } else if (result.getCode() == 95598) {
            SpUtils.setBoolean(InstallInfoActivity.this, "isLogin", false);
            Toast.makeText(InstallInfoActivity.this, result.getMsg(), Toast.LENGTH_SHORT).show();
            ActivityUtils.startActivityAndFinish(InstallInfoActivity.this, LoginActivity.class);
        } else {
            ToastUtil.show(InstallInfoActivity.this, result.getMsg());
        }
    }

    @Override
    public void resultFailure(String result) {
        ToastUtil.show(InstallInfoActivity.this, "服务器异常");
    }
}
