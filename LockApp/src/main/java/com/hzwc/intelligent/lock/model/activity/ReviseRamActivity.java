package com.hzwc.intelligent.lock.model.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
import com.hzwc.intelligent.lock.R;
import com.hzwc.intelligent.lock.model.base.BaseActivity;
import com.hzwc.intelligent.lock.model.bean.InstallLock;
import com.hzwc.intelligent.lock.model.bean.SearchAddressInfo;
import com.hzwc.intelligent.lock.model.utils.ToastUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReviseRamActivity extends BaseActivity implements AMapLocationListener, AMap.OnMapClickListener, AMap.OnCameraChangeListener,GeocodeSearch.OnGeocodeSearchListener {

    @BindView(R.id.iv_title_return)
    ImageView ivTitleReturn;
    @BindView(R.id.tv_title_text)
    TextView tvTitleText;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.install_map)
    MapView installMap;
    @BindView(R.id.et_install_location)
    EditText etInstallLocation;
    @BindView(R.id.et_install_name)
    EditText etInstallName;
    @BindView(R.id.et_install_mac)
    EditText etInstallMac;
    @BindView(R.id.et_install_zxing)
    ImageView etInstallZxing;
    @BindView(R.id.rl_ring_location)
    RelativeLayout rlRingLocation;
    @BindView(R.id.et_install_mac2)
    EditText etInstallMac2;
    @BindView(R.id.et_install_zxing2)
    ImageView etInstallZxing2;
    @BindView(R.id.rl_ring_location2)
    RelativeLayout rlRingLocation2;
    @BindView(R.id.iv_install_center_image)
    ImageView ivInstallCenterImage;
    private String city;
    private LatLonPoint latLonPoint;
    private Animation centerAnimation;
    private AMap aMap;
    private UiSettings uiSettings;
    private Marker locationMarker;
    public AMapLocationClientOption mLocationOption = null;

    private LatLng mFinalChoosePosition;
    private GeocodeSearch geocoderSearch;
    public SearchAddressInfo mAddressInfoFirst = null;
    private boolean isHandDrag = true;
    private boolean isFirstLoad = true;
    private boolean isBackFromSearch = false;
    private int currentPage = 0;// 当前页面，从0开始计数
    private PoiSearch.Query query;// Poi查询条件类
    private PoiSearch poiSearch;
    private List<InstallLock> installLockList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_install_lock);
        ButterKnife.bind(this);
        installMap.onCreate(savedInstanceState);
    }

    @Override
    protected void initIntentData() {

    }

    @Override
    protected void initView() {
        tvTitleText.setText(R.string.title_chose_location);
        tvSearch.setVisibility(View.VISIBLE);
        tvSearch.setText("修改");
        ivTitleReturn.setVisibility(View.VISIBLE);

        double longitude = 0;
        double latitude = 0;
        city = getIntent().getStringExtra("cityCode");

        //经纬度信息
        latLonPoint = new LatLonPoint(latitude, longitude);
        centerAnimation = AnimationUtils.loadAnimation(this, R.anim.center_anim);

        initMap();
        installLockList = new ArrayList<>();
//        installLockList.set()
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.iv_title_return, R.id.tv_search, R.id.et_install_zxing, R.id.et_install_zxing2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_title_return:
                break;
            case R.id.tv_search:
                break;
            case R.id.et_install_zxing:
                break;
            case R.id.et_install_zxing2:
                break;
        }
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


                //当前位置设置为地图中心
//                    setMapCenter(mLongitude, mLatitude);
            } else {
                //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                Log.e("awj", "Install onLocationChanged Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
            }
        }
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {

    }

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {


        mFinalChoosePosition = cameraPosition.target;

        ivInstallCenterImage.startAnimation(centerAnimation);


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
    public void onMapClick(LatLng latLng) {
        Toast.makeText(this, "latitude" + String.valueOf(latLng.latitude), Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onPause() {
        super.onPause();
        installMap.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (installMap != null) {

            installMap.onDestroy();
        }
    }
    /**
     * 把LatLng对象转化为LatLonPoint对象
     */
    public static LatLonPoint convertToLatLonPoint(LatLng latlon) {
        return new LatLonPoint(latlon.latitude, latlon.longitude);
    }


    @Override
    public void onRegeocodeSearched(RegeocodeResult result, int i) {
        if (i == 1000) {//转换成功
            if (result != null && result.getRegeocodeAddress() != null
                    && result.getRegeocodeAddress().getFormatAddress() != null) {
                //拿到详细地址
               String  addressName = result.getRegeocodeAddress().getFormatAddress(); // 逆转地里编码不是每次都可以得到对应地图上的opi

                //条目中第一个地址 也就是当前你所在的地址
                mAddressInfoFirst = new SearchAddressInfo(addressName, addressName, false, convertToLatLonPoint(mFinalChoosePosition));

                //其实也是可以在这就能拿到附近的兴趣点的
                ToastUtil.show(this, "info = "+mAddressInfoFirst.addressName);


                etInstallLocation.setText(mAddressInfoFirst.addressName);
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


    private void initMap() {

        if (aMap == null) {
            aMap = installMap.getMap();

            //地图ui界面设置
            aMap.getUiSettings().setMyLocationButtonEnabled(true);//设置默认定位按钮是否显示，非必需设置。
            aMap.setMyLocationEnabled(true);
            uiSettings = aMap.getUiSettings();
            //地图比例尺的开启
            uiSettings.setScaleControlsEnabled(true);

            //关闭地图缩放按钮 就是那个加号 和减号
            uiSettings.setZoomControlsEnabled(false);

            aMap.setOnMapClickListener(this);

            //对amap添加移动地图事件监听器
            aMap.setOnCameraChangeListener(this);

            locationMarker = aMap.addMarker(new MarkerOptions()
                    .anchor(0.5f, 0.5f)
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.marker))
                    .position(new LatLng(latLonPoint.getLatitude(), latLonPoint.getLongitude()))
            );

            MyLocationStyle myLocationStyle;
            myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
            myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);
            aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
            aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。


            //初始化定位
            AMapLocationClient mLocationClient = new AMapLocationClient(this);
            //设置定位回调监听
            mLocationClient.setLocationListener(this);
            mLocationOption = new AMapLocationClientOption();
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Device_Sensors);
            mLocationOption.setOnceLocation(true);
            mLocationOption.setOnceLocationLatest(true);
            mLocationOption.setLocationCacheEnable(false);
            mLocationOption.setNeedAddress(true);
            mLocationClient.setLocationOption(mLocationOption);
            //启动定位
            mLocationClient.startLocation();

            //拿到地图中心的经纬度
            mFinalChoosePosition = locationMarker.getPosition();
        }

        setMap();
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latLonPoint.getLatitude(), latLonPoint.getLongitude()), 16));
    }
    private void setMap() {
        geocoderSearch = new GeocodeSearch(getApplicationContext());

        //设置逆地理编码监听
        geocoderSearch.setOnGeocodeSearchListener(this);
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


}
