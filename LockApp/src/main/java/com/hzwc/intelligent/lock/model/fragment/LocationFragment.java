package com.hzwc.intelligent.lock.model.fragment;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.Projection;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.google.gson.Gson;
import com.hzwc.intelligent.lock.R;
import com.hzwc.intelligent.lock.model.activity.MineActivity;
import com.hzwc.intelligent.lock.model.bean.LocationBean;
import com.hzwc.intelligent.lock.model.bean.MarkerBean;
import com.hzwc.intelligent.lock.model.utils.ActivityUtils;
import com.hzwc.intelligent.lock.model.utils.FunctionUtils;
import com.hzwc.intelligent.lock.model.utils.SpUtils;
import com.hzwc.intelligent.lock.model.view.persenter.LocationPresenter;
import com.hzwc.intelligent.lock.model.view.view.LocationView;
import com.hzwc.intelligent.lock.mvpframework.factory.CreatePresenter;
import com.hzwc.intelligent.lock.mvpframework.view.AbstractBaseFragment;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.beta.UpgradeInfo;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by anna on 2018/2/24.
 */
@CreatePresenter(LocationPresenter.class)
public class LocationFragment extends AbstractBaseFragment<LocationView, LocationPresenter> implements LocationView, AMap.OnMapClickListener, AMap.OnMarkerClickListener, AMap.OnCameraChangeListener, AMapLocationListener {

    Unbinder unbinder;

    @BindView(R.id.map)
    MapView mapView;
    @BindView(R.id.iv_title_return)
    ImageView ivTitleReturn;

    @BindView(R.id.et_title_text)
    EditText etTitleText;
    @BindView(R.id.iv_search)
    ImageView ivSearch;
    @BindView(R.id.tv_title_text)
    TextView tvTitleText;
    @BindView(R.id.iv_main_info)
    ImageView ivMainInfo;
    @BindView(R.id.iv_center_image)
    ImageView ivCenterImage;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.iv_navigation)
    ImageView ivNavigation;
    @BindView(R.id.ll_navigation_bg)
    LinearLayout llNavigationBg;
    @BindView(R.id.iv_navigation_left)
    ImageView ivNaLeft;
    @BindView(R.id.iv_red)
    ImageView ivRed;
    private AMap aMap;
    private View mView;
    private String mUrlGD;

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;

    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;

    private Marker marker;
    private MarkerOptions markerOption;
    private double lng;
    private double lat;
    private Marker finalMarker;
    private Animation centerAnimation;
    List<MarkerBean.CabinetsBean> list = new ArrayList();
    List<MarkerBean.CabinetsBean> rimList = new ArrayList();
    private MarkerOptions markerOptions;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_station, container, false);

        unbinder = ButterKnife.bind(this, mView);
        mapView = (MapView) mView.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        initView();
        initData();
        return mView;
    }

    private void initView() {
        tvTitleText.setText(R.string.title_location_info);
        llNavigationBg.setVisibility(View.GONE);
        UpgradeInfo upgradeInfo = Beta.getUpgradeInfo();
        if (upgradeInfo == null) {
//            Toast.makeText(getActivity(), "无升级信息", Toast.LENGTH_SHORT).show();
            return;
        } else {
            Log.e("awj ======name", "info =" + upgradeInfo.versionName);
            String newVersion = FunctionUtils.getVersionName(getActivity());
            if (newVersion.equals(upgradeInfo.versionName)) {
                ivRed.setVisibility(View.GONE);
            } else {
                ivRed.setVisibility(View.VISIBLE);
            }
        }

    }

    @OnClick({R.id.iv_title_return, R.id.iv_search, R.id.iv_main_info, R.id.tv_cancel, R.id.iv_navigation, R.id.iv_navigation_left})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_title_return:
                ivTitleReturn.setVisibility(View.GONE);
                tvTitleText.setVisibility(View.VISIBLE);
                ivMainInfo.setVisibility(View.VISIBLE);
                etTitleText.setVisibility(View.GONE);
                tvCancel.setVisibility(View.GONE);
                ivSearch.setVisibility(View.VISIBLE);
                etTitleText.clearComposingText();
                break;
            case R.id.iv_search:
                ivTitleReturn.setVisibility(View.VISIBLE);
                ivMainInfo.setVisibility(View.GONE);
                tvTitleText.setVisibility(View.GONE);
                etTitleText.setVisibility(View.VISIBLE);
                tvCancel.setVisibility(View.VISIBLE);
                ivSearch.setVisibility(View.GONE);
                break;
            case R.id.iv_main_info:
                if (FunctionUtils.isFastClick()) {
                    return;
                }
                ActivityUtils.startActivity(getActivity(), MineActivity.class);
                break;
            case R.id.tv_cancel:
                if (FunctionUtils.isFastClick()) {
                    return;
                }

                getMvpPresenter().clickLocationRequest(SpUtils.getString(getActivity(), "token", ""), etTitleText.getText().toString().trim());

                break;
            case R.id.iv_navigation:
                if (FunctionUtils.isFastClick()) {
                    return;
                }

                if (finalMarker!=null){

                }

                showDialog(finalMarker);
                break;
            case R.id.iv_navigation_left:
                if (FunctionUtils.isFastClick()) {
                    return;
                }
                llNavigationBg.setVisibility(View.GONE);
                break;

        }
    }

    //初始化地图数据
    private void initData() {


        AndPermission.with(getActivity())
                .permission(Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                .onGranted(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        Log.e("awj", "==========成功 token =" + SpUtils.getString(getActivity(), "token", ""));
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

            centerAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.center_anim);
            aMap.getUiSettings().setZoomControlsEnabled(false);
            aMap.getUiSettings().setAllGesturesEnabled(true);//设置默认定位按钮是否显示，非必需设置。


            //初始化定位
            mLocationClient = new AMapLocationClient(getActivity());
            //设置定位回调监听
            mLocationClient.setLocationListener(this);
            ////初始化AMapLocationClientOption对象
            mLocationOption = new AMapLocationClientOption();


            //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位模式为AMapLocationMode.Battery_Saving，低功耗模式。
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
            //设置定位模式为AMapLocationMode.Device_Sensors，仅设备模式。
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Device_Sensors);
            //获取一次定位结果：
            //该方法默认为false。
            mLocationOption.setOnceLocation(false);

            //获取最近3s内精度最高的一次定位结果：
            //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
            mLocationOption.setOnceLocationLatest(true);

            //关闭缓存机制
            mLocationOption.setLocationCacheEnable(false);


            //设置是否返回地址信息（默认返回地址信息）
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


    /**
     * 设置初始化地图位置
     * CameraPosition() 方法
     * var1 target 目标
     * var2 zoom  变焦
     * var3 tilt 倾斜
     * var4 bearing 方位
     */
    private void setPosition() {
        LatLng mLatlng = new LatLng(30.216246906858395, 120.22501069269869);
        changeCamera(
                CameraUpdateFactory.newCameraPosition(new CameraPosition(
                        mLatlng, 15, 30, 30)));
    }

    private void changeCamera(CameraUpdate cameraUpdate) {
        aMap.moveCamera(cameraUpdate);
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


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

        if (mapView != null) {
            mapView.onDestroy();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mapView.onResume();
        mLocationClient.startLocation();
    }

    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mapView.onPause();
        mLocationClient.stopLocation();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mapView != null) {
            mapView.onDestroy();
            mLocationClient.onDestroy();//销毁定位客户端，同时销毁本地定位服务。
            mLocationClient.stopLocation();
        }
    }


    @Override
    public void onMapClick(LatLng latLng) {
        Log.e("awj", "L=" + latLng);
        Log.e("awj", "La=" + latLng.latitude);
        Log.e("awj", "Lo=" + latLng.longitude);
        LocationBean locationBean = new LocationBean(latLng.latitude, latLng.longitude);
        locationBean.setLatitude(latLng.latitude);
        locationBean.setLongitude(latLng.longitude);
//        markerOptions.

    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        Log.e("mark",marker.getPosition().latitude+"~~~~"+marker.getPosition().longitude);


        if (aMap != null & marker.getTitle() != null) {
            jumpPoint(marker);

            String str = marker.getTitle();
            String[] strs = str.split(",");
//            etInstallLocation.setText(strs[0].toString());
//            cabinetId = Integer.parseInt(strs[1]);
//            Log.e("awj", "CabinetId =" + cabinetId);

            markerOptions = new MarkerOptions().icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_RED))
                    .position(marker.getPosition())
                    .title("设施信息")
                    .snippet("设施地址：" + strs[0].toString() + "\n" + "设施名字：" + strs[2].toString())
                    .draggable(true);


            marker = aMap.addMarker(markerOptions);
            marker.showInfoWindow();

            llNavigationBg.setVisibility(View.VISIBLE);
        }


//        showDialog(marker);

        return true;
    }

    private void showDialog(final Marker marker) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("导航");
        builder.setMessage("是否进入高德地图导航？");
        final Marker finalMarker1 = marker;
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

//TODO 高德地图
                if (isInstallByread("com.autonavi.minimap")) {


                    startGaoDeMap(marker.getPosition());

                } else {
                    mUrlGD = "http://shouji.baidu.com/software/24277012.html";
                    openLinkBySystem(mUrlGD);
                }
                llNavigationBg.setVisibility(View.GONE);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                llNavigationBg.setVisibility(View.GONE);



            }
        });
        builder.show();
    }


    private void startGaoDeMap(LatLng position) {
        Log.e("posi",position.latitude+"~~~~~~"+position.longitude);
        Intent intent = new Intent("android.intent.action.VIEW",
                Uri.parse("androidamap://navi?sourceApplication=amap&lat=" + position.latitude + "&lon=" + position.longitude + "&dev=0&style=0"));
//                Uri.parse("amapuri://route/plan/?sid=BGVIS1&slat=" + position.latitude + "&lon=" + position.longitude + "&dev=1&style=0"));
        intent.setPackage("com.autonavi.minimap");
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if (isInstallByread("com.autonavi.minimap")) {
            startActivity(intent);
        } else {
            Toast.makeText(getActivity(), "没有安装高德地图客户端", Toast.LENGTH_SHORT).show();
        }
    }


    private void jumpPoint(Marker marker) {

        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        Projection proj = aMap.getProjection();
        final LatLng markerLatlng = marker.getPosition();
        Point markerPoint = proj.toScreenLocation(markerLatlng);
        markerPoint.offset(0, -100);
        final LatLng startLatLng = proj.fromScreenLocation(markerPoint);
        final long duration = 1500;

        final Interpolator interpolator = new BounceInterpolator();
        finalMarker = marker;
        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation((float) elapsed
                        / duration);
                lng = t * markerLatlng.longitude + (1 - t)
                        * startLatLng.longitude;
                lat = t * markerLatlng.latitude + (1 - t)
                        * startLatLng.latitude;


                finalMarker.setPosition(new LatLng(lat, lng));
                if (t < 1.0) {
                    handler.postDelayed(this, 16);
                }
            }

        });

    }

    /**
     * 判断是否安装目标应用
     *
     * @param packageName 目标应用安装后的包名
     * @return 是否已安装目标应用
     */
    private boolean isInstallByread(String packageName) {
        return new File("/data/data/" + packageName).exists();
    }

    private void intentGDMap(String str) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        ComponentName cn = new ComponentName("com.autonavi.minimap", "com.autonavi.map.activity.NewMapActivity");
        intent.setAction("com.autonavi.minimap.ACTION");
//                intent.setData(Uri.parse("androidamap://openFeature?featureName=GoldCoin"));//这个是直接打开电子狗页面
        intent.setData(Uri.parse("androidamap://arroundpoi?sourceApplication=softname&keywords=" +
                str +
                "&dev=0"));//这个是直接打开电子狗页面

        intent.setComponent(cn);
        startActivity(intent);
    }

    /**
     * 调用系统浏览器打开网页
     *
     * @param url 地址
     */
    private void openLinkBySystem(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    /*
     * @param sourceApplication 必填 第三方调用应用名称。如 amap
     * @param poiname 非必填 POI 名称
     * @param lat 必填 纬度
     * @param lon 必填 经度
     * @param dev 必填 是否偏移(0:lat 和 lon 是已经加密后的,不需要国测加密; 1:需要国测加密)
     * @param style 必填 导航方式(0 速度快; 1 费用少; 2 路程短; 3 不走高速；4 躲避拥堵；5 不走高速且避免收费；6 不走高速且躲避拥堵；7 躲避收费和拥堵；8 不走高速躲避收费和拥堵))
     */
    public static void goToNaviActivity(Context context, String sourceApplication, String poiname, String lat, String lon, String dev, String style) {
        StringBuffer stringBuffer = new StringBuffer("androidamap://navi?sourceApplication=")
                .append(sourceApplication);
        if (!TextUtils.isEmpty(poiname)) {
            stringBuffer.append("&poiname=").append(poiname);
        }
        stringBuffer.append("&lat=").append(lat)
                .append("&lon=").append(lon)
                .append("&dev=").append(dev)
                .append("&style=").append(style);

        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(stringBuffer.toString()));
        intent.setPackage("com.autonavi.minimap");
        context.startActivity(intent);
    }


    @Override
    public void onCameraChange(CameraPosition cameraPosition) {

    }

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
        ivCenterImage.startAnimation(centerAnimation);
        Log.e("awj", "onCameraChangeFinish latitude =" + cameraPosition.target.latitude);
        Log.e("awj", "onCameraChangeFinish longitude =" + cameraPosition.target.longitude);

        getMvpPresenter().clickRimRequest(SpUtils.getString(getActivity(), "token", ""), cameraPosition.target.longitude, cameraPosition.target.latitude);

    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                //可在其中解析amapLocation获取相应内容。

                aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                aMapLocation.getLatitude();//获取纬度
                aMapLocation.getLongitude();//获取经度
                aMapLocation.getAccuracy();//获取精度信息
                aMapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                aMapLocation.getCountry();//国家信息
                aMapLocation.getProvince();//省信息
                aMapLocation.getCity();//城市信息
                aMapLocation.getDistrict();//城区信息
                aMapLocation.getStreet();//街道信息
                aMapLocation.getStreetNum();//街道门牌号信息
                aMapLocation.getCityCode();//城市编码
                aMapLocation.getAdCode();//地区编码
                aMapLocation.getAoiName();//获取当前定位点的AOI信息
                aMapLocation.getBuildingId();//获取当前室内定位的建筑物Id
                aMapLocation.getFloor();//获取当前室内定位的楼层
                aMapLocation.getGpsAccuracyStatus();//获取GPS的当前状态
                //获取定位时间
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(aMapLocation.getTime());
                df.format(date);
                Log.e("awj", "location "
                        + aMapLocation.getErrorCode() + ", el:"
                        + aMapLocation.getLatitude() + ", ed:"
                        + aMapLocation.getLongitude());


                //当前位置设置为地图中心
//                    setMapCenter(mLongitude, mLatitude);
            } else {
                //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                Log.e("awj", "location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
            }
        }
    }


    @Override
    public void requestLoading() {

    }

    @Override
    public void LocationSuccess(MarkerBean result) {
        if (mapView != null) {

            clearMarkers("2");
        }
        Gson gson = new Gson();
        String jsonStr = gson.toJson(result);
        Log.e("awj LocationSuccess =", jsonStr);
        list = new ArrayList<>();

        if (result.getCode() == 0) {
            list = result.getCabinets();
            initDataMarker(list, "2");
        } else {

        }
    }

    @Override
    public void RimSuccess(MarkerBean result) {
        if (mapView != null) {

            clearMarkers("1");
        }
        Gson gson = new Gson();
        String jsonStr = gson.toJson(result);
        Log.e("awj RimSuccess =", jsonStr);
        rimList = new ArrayList<>();
        if (result.getCode() == 0) {
//            if (rimList.size() > 0) {
            rimList = result.getCabinets();
            initDataMarker(rimList, "1");
//            }else {
//                Toast.makeText(getActivity(), "周围没有设施", Toast.LENGTH_SHORT).show();
//            }
        } else {
            Toast.makeText(getActivity(), result.getMsg(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void resultFailure(String result) {

    }

    private void initDataMarker(List<MarkerBean.CabinetsBean> list, String s) {
//        clearMarkers(s);
        for (int i = 0; i < list.size(); i++) {


            LatLng mLatlng = new LatLng(list.get(i).getLocationY(), list.get(i).getLocationX());
            if (s.equals("1")) {



                //将年龄和性别拼接起来 点击Marker后的窗口取出
//            String groupDog = list.get(i).getAge() + "#" + list.get(i).getGender();

                markerOptions.position(mLatlng)
                        .title(list.get(i).getCabinetName())
                        .snippet(list.get(i).getAddr())
                        .draggable(false)
                        .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_ram_blue));

            } else if (s.equals("2")) {
                markerOptions.position(mLatlng)
                        .title(list.get(i).getCabinetName())
                        .snippet(list.get(i).getAddr())
                        .draggable(false)
                        .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_ram_search));
            }



            markerOptions.title(list.get(i).getAddr() + "," + list.get(i).getCabinetId() + "," + list.get(i).getCabinetName());

            //将数据添加到地图上
            marker = aMap.addMarker(markerOptions);
            marker.setObject(s);


        }
//        setPosition();
    }


    //删除指定Marker
    private void clearMarkers(String s) {

        List<Marker> mapScreenMarkers = aMap.getMapScreenMarkers();
        for (int i = 0; i < mapScreenMarkers.size(); i++) {
            Marker marker = mapScreenMarkers.get(i);
            if (marker.getObject() instanceof Object) {
                if (marker.getObject().equals("1") || marker.getObject().equals("2")) {

                    marker.remove();//移除当前Marker
                }
            }
        }
        mapView.invalidate();//刷新地图
    }
}
