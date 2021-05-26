package com.hzwc.intelligent.lock.model.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
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
import com.google.zxing.BinaryBitmap;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.GlobalHistogramBinarizer;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.google.zxing.multi.qrcode.QRCodeMultiReader;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.qrcode.encoder.QRCode;
import com.hzwc.intelligent.lock.R;
import com.hzwc.intelligent.lock.model.adapter.MyAdapter;
import com.hzwc.intelligent.lock.model.adapter.MyInterface;
import com.hzwc.intelligent.lock.model.bean.BaseBean;
import com.hzwc.intelligent.lock.model.bean.InstallLock;
import com.hzwc.intelligent.lock.model.bean.LockMacBean;
import com.hzwc.intelligent.lock.model.bean.MarkerBean;
import com.hzwc.intelligent.lock.model.bean.Scan;
import com.hzwc.intelligent.lock.model.bean.SearchAddressInfo;
import com.hzwc.intelligent.lock.model.bluetooth.ClientManager;
import com.hzwc.intelligent.lock.model.http.ConstantUrl;
import com.hzwc.intelligent.lock.model.http.HttpService;
import com.hzwc.intelligent.lock.model.utils.ActivityUtils;
import com.hzwc.intelligent.lock.model.utils.FunctionUtils;
import com.hzwc.intelligent.lock.model.utils.LogUtils;
import com.hzwc.intelligent.lock.model.utils.SpUtils;
import com.hzwc.intelligent.lock.model.utils.ToastUtil;
import com.hzwc.intelligent.lock.model.view.persenter.InstallAddLockPresenter;
import com.hzwc.intelligent.lock.model.view.view.InstallAddLockView;
import com.hzwc.intelligent.lock.mvpframework.factory.CreatePresenter;
import com.hzwc.intelligent.lock.mvpframework.view.AbstractMvpBaseActivity;
import com.inuker.bluetooth.library.connect.listener.BluetoothStateListener;
import com.inuker.bluetooth.library.search.SearchRequest;
import com.inuker.bluetooth.library.search.SearchResult;
import com.inuker.bluetooth.library.search.response.SearchResponse;
import com.inuker.bluetooth.library.utils.BluetoothLog;
import com.journeyapps.barcodescanner.CaptureActivity;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


@CreatePresenter(InstallAddLockPresenter.class)
public class InstallLockActivity extends AbstractMvpBaseActivity<InstallAddLockView, InstallAddLockPresenter> implements InstallAddLockView,
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
    @BindView(R.id.tv_text)
    TextView tvText;
    @BindView(R.id.install_map)
    MapView installMap;
    @BindView(R.id.et_install_location)
    EditText etInstallLocation;
    @BindView(R.id.et_install_name)
    EditText etInstallName;
    @BindView(R.id.iv_install_center_image)
    ImageView ivInstallCenterImage;
    @BindView(R.id.rv_install_lock)
    RecyclerView rvInstallLock;
    @BindView(R.id.btn_add)
    ImageView btnAdd;
    @BindView(R.id.btn_delete)
    Button btnDelete;
    @BindView(R.id.tv_install_lock_tip)
    TextView tvInstallLockTip;


    //    private String city;
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
    List<MarkerBean.CabinetsBean> rimList = new ArrayList();
    private MarkerOptions markerOptions;
    private Marker marker;
    private MyAdapter mListAdapter;
    private ArrayList<Scan> list;  // 扫描的mac
    private List<String> macList = new ArrayList<>();  // 服务器获取的到的Mac

    private int cabinetId;
    private InstallLock installLock;
    public int REQUEST_CODE_SCAN_LOCK = 0x03;
    private int mPositionFlag = -1;
    private String content;
    private HashMap<String, String> mCabinetIdMap;
    private HashMap<String, String> mLockNoMap;
    private  HashMap<String,String>  codeMap;
    private List<MarkerBean.CabinetsBean.LocksBean> locksBeans;

    private  Button btn_scan;
    private  int Scanposition=-1;
    private  String cabinetCode;

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
        tvTitleText.setText(R.string.title_install_lock);
        tvSearch.setVisibility(View.GONE);
        tvSearch.setText("修改");
        ivTitleReturn.setVisibility(View.VISIBLE);
        tvSearchOver.setVisibility(View.VISIBLE);


        btn_scan=findViewById(R.id.btn_scan);

        btn_scan.setOnClickListener(v -> {




                    AndPermission.with(this)
                            .permission( Permission.CAMERA)
                            .onGranted(new Action() {
                                @Override

                                public void onAction(List<String> permissions) {

                                    if (permissions!=null&&permissions.size()>0) {
                                        Intent intent = new Intent(InstallLockActivity.this, CaptureActivity.class);
                                        startActivityForResult(intent, 102);

                                    }


                                }
                            }).onDenied(new Action() {
                        @Override
                        public void onAction(List<String> permissions) {
                           ToastUtil.show("相机拒绝");

                        }
                    }).start();



                }
                );

        etInstallLocation.setCursorVisible(false);
        etInstallLocation.setFocusable(false);
        etInstallLocation.setFocusableInTouchMode(false);


        etInstallName.setCursorVisible(false);
        etInstallName.setFocusable(false);
        etInstallName.setFocusableInTouchMode(false);


        double longitude = 0;
        double latitude = 0;
//        city = getIntent().getStringExtra("cityCode");

        //经纬度信息
        latLonPoint = new LatLonPoint(latitude, longitude);
        centerAnimation = AnimationUtils.loadAnimation(this, R.anim.center_anim);

        initMap();

        mCabinetIdMap = new HashMap<>();
        mLockNoMap = new HashMap<>();

    }

    public void bluetoothState() {
        ClientManager.getClient().registerBluetoothStateListener(mBluetoothStateListener);
        if (ClientManager.getClient().isBluetoothOpened()) {
            searchDevice();
        } else {

            boolean  isopen=ClientManager.getClient().openBluetooth();
            tvInstallLockTip.setText(R.string.tv_install_tip);
            Log.e("isopne",isopen+"");
        }
    }

    private BluetoothStateListener mBluetoothStateListener = new BluetoothStateListener() {
        @Override
        public void onBluetoothStateChanged(boolean openOrClosed) {
            if (openOrClosed) {//蓝牙打开
                searchDevice();
            } else {//蓝牙关闭

                tvInstallLockTip.setText(R.string.tv_install_tip);


            }
        }

    };

    //开始扫描
    public void searchDevice() {
        SearchRequest request = new SearchRequest.Builder()
                .searchBluetoothLeDevice(8000, 2).build();

        ClientManager.getClient().search(request, mSearchResponse);
//        searchAnim();
    }

    //扫描回调
    private final SearchResponse mSearchResponse = new SearchResponse() {
        @Override
        public void onSearchStarted() {

            tvInstallLockTip.setText("扫描中...");


        }

        @Override
        public void onDeviceFounded(SearchResult device) {



            //根据锁名来识别附近是否有蓝牙锁
                if (device.getName().equals("smart lock") || judgeName(device.getName())|| device.getName().contains("WANGCE")) {
                    boolean a = device.getName().equals("smart lock");
                    Log.e("name",device.getName()+"---"+device.getAddress());
                String token = SpUtils.getString(InstallLockActivity.this, "token", "");

                if (!macList.contains(device.getAddress())) {
                    macList.add(device.getAddress());

                    getMvpPresenter().checkLockNo(token, device.getAddress());
                }


            }

        }

        @Override
        public void onSearchStopped() {
            tvInstallLockTip.setText("扫描结束");

            if (macList.size()==0){
                tvInstallLockTip.setText(R.string.tv_install_open_lock);
            }



            //mListAdapter.notifyDataSetChanged();


        }

        @Override
        public void onSearchCanceled() {
            BluetoothLog.w("MainActivity.onSearchCanceled");
        }
    };


    private boolean judgeName(String name) {
        return name.length() > 5 && name.substring(0, 5).equals("OKGSS");
    }


    @Override
    protected void initData() {
        rvInstallLock.setLayoutManager(new LinearLayoutManager(InstallLockActivity.this));
//        mListAdapter.setViewType(INSTALL_LOCK);
        rvInstallLock.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        list = new ArrayList<>();

        bluetoothState();


        Scan s=new Scan();

        list.add(new Scan());
        mListAdapter = new MyAdapter(InstallLockActivity.this,  list);
//        mListAdapter.setInstallLockList(list);
        mListAdapter.setHasStableIds(true);
        rvInstallLock.setAdapter(mListAdapter);
        mListAdapter.setOnScanClickListener(new MyInterface() {
            @Override
            public void onScanClick(View view, int position) {
                AndPermission.with(InstallLockActivity.this)
                        .permission(Permission.WRITE_EXTERNAL_STORAGE, Permission.CAMERA)
                        .onGranted(new Action() {
                            @Override
                            public void onAction(List<String> permissions) {
                                mPositionFlag = position;
                                Scanposition=position;

                                //申请权限成功
                                //开启扫一扫界面

                                    Intent intent = new Intent(InstallLockActivity.this, CaptureActivity.class);
                                    startActivityForResult(intent, 101);

                            }
                        }).onDenied(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        //用户拒绝

                    }
                }).start();
            }

            @Override
            public void refresh() {
                searchDevice();


            }
        });

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

        getMvpPresenter().clickRimRequest(SpUtils.getString(InstallLockActivity.this, "token", ""), cameraPosition.target.longitude, cameraPosition.target.latitude);


    }

    @Override
    public void onMapClick(LatLng latLng) {
        Toast.makeText(this, "latitude" + String.valueOf(latLng.latitude), Toast.LENGTH_SHORT).show();

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
                String addressName = result.getRegeocodeAddress().getFormatAddress(); // 逆转地里编码不是每次都可以得到对应地图上的opi

                //条目中第一个地址 也就是当前你所在的地址
                mAddressInfoFirst = new SearchAddressInfo(addressName, addressName, false, convertToLatLonPoint(mFinalChoosePosition));

                //其实也是可以在这就能拿到附近的兴趣点的
//                ToastUtil.show(this, "info = " + mAddressInfoFirst.addressName);
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
    public boolean onMarkerClick(Marker marker) {


        Log.e("awj", marker.getTitle());
        String str = marker.getTitle();
        String[] strs = str.split(",");
        etInstallLocation.setText(strs[0].toString());
        cabinetId = Integer.parseInt(strs[1]);
        Log.e("awj", "CabinetId =" + cabinetId);
        etInstallName.setText(strs[2]);


        aMap.moveCamera(CameraUpdateFactory.changeLatLng(marker.getPosition()));


        return true;
    }

    private void initMap() {


        if (aMap == null) {
            aMap = installMap.getMap();
            markerOptions = new MarkerOptions();
            //地图ui界面设置
            aMap.getUiSettings().setMyLocationButtonEnabled(true);//设置默认定位按钮是否显示，非必需设置。
            aMap.setMyLocationEnabled(true);
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
        query = new PoiSearch.Query("", "", "");// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        query.setPageSize(20);// 设置每页最多返回多少条poiitem
        query.setPageNum(currentPage);// 设置查第一页

        LatLonPoint llPoint = convertToLatLonPoint(mFinalChoosePosition);

        if (llPoint != null) {
            poiSearch = new PoiSearch(this, query);
            poiSearch.setBound(new PoiSearch.SearchBound(llPoint, 5000, true));//
            // 设置搜索区域为以lpTemp点为圆心，其周围5000米范围
            poiSearch.searchPOIAsyn();// 异步搜索
        }
    }


    @Override
    public void requestLoading() {

    }

    @Override
    public void installAddLockSuccess(BaseBean result) {

        Gson gson = new Gson();
        String jsonStr = gson.toJson(result);
        Log.e("awj", "installAddLockSuccess =" + jsonStr);
        if (result.getCode() == 0) {

            tvSearchOver.setVisibility(View.GONE);
            tvSearch.setVisibility(View.VISIBLE);
            tvText.setVisibility(View.GONE);
//            rvInstallLock.setVisibility(View.GONE);
//            btnAdd.setVisibility(View.GONE);
            Toast.makeText(InstallLockActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
            finish();
        } else if (result.getCode() == 95598) {
            SpUtils.setBoolean(InstallLockActivity.this, "isLogin", false);
            Toast.makeText(InstallLockActivity.this, result.getMsg(), Toast.LENGTH_SHORT).show();
            ActivityUtils.startActivityAndFinish(InstallLockActivity.this, LoginActivity.class);
        } else {
            Toast.makeText(InstallLockActivity.this, result.getMsg(), Toast.LENGTH_SHORT).show();

        }

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
//            rvInstallLock.setVisibility(View.GONE);
//            btnAdd.setVisibility(View.GONE);
        } else if (result.getCode() == 95598) {
            SpUtils.setBoolean(InstallLockActivity.this, "isLogin", false);
            Toast.makeText(InstallLockActivity.this, result.getMsg(), Toast.LENGTH_SHORT).show();
            ActivityUtils.startActivityAndFinish(InstallLockActivity.this, LoginActivity.class);
        } else {
            Toast.makeText(InstallLockActivity.this, result.getMsg(), Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void macSuccess(LockMacBean result) {
//        clearMarkers("1");
        Gson gson = new Gson();
        String jsonStr = gson.toJson(result);
        Log.e("awj RimSuccess =", jsonStr);
        if (result.getCode() == 0) {
            String mac = result.getLockNo();
            list.get(mPositionFlag).setMac(mac);
            mListAdapter.notifyDataSetChanged();

        } else if (result.getCode() == 95598) {
            SpUtils.setBoolean(InstallLockActivity.this, "isLogin", false);
            Toast.makeText(InstallLockActivity.this, result.getMsg(), Toast.LENGTH_SHORT).show();
            ActivityUtils.startActivityAndFinish(InstallLockActivity.this, LoginActivity.class);
        } else {
            Toast.makeText(InstallLockActivity.this, result.getMsg(), Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void resultFailure(String result) {
        Log.e("awj", "resultFailure =" + result);
        Toast.makeText(InstallLockActivity.this, "服务器异常", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void installSuccess(String mac, boolean install) {

        if (install) {
            list.get(0).setMac(mac);
            mListAdapter.notifyDataSetChanged();

        }




    }


    private void initDataMarker(List<MarkerBean.CabinetsBean> list, String s) {


        Log.e("awj", "initDataMarker =" + list.size());
        for (int i = 0; i < list.size(); i++) {
            LatLng mLatlng = new LatLng(list.get(i).getLocationY(), list.get(i).getLocationX());
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
        installMap.invalidate();//刷新地图
    }


    @OnClick({R.id.btn_add, R.id.btn_delete, R.id.iv_title_return, R.id.tv_search, R.id.tv_search_over})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_add:
                if (FunctionUtils.isFastClick()) {
                    return;
                }
                Scan scan=new Scan();
                list.add(scan);
                mListAdapter.notifyDataSetChanged();
//                updateData();
                break;
            case R.id.btn_delete:
                if (list.size() > 1)
                    list.remove(1);
                mListAdapter.notifyItemRemoved(2);

                break;
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
                tvSearchOver.setVisibility(View.VISIBLE);
                tvSearch.setVisibility(View.GONE);

                rvInstallLock.setVisibility(View.VISIBLE);
                btnAdd.setVisibility(View.VISIBLE);
                tvText.setVisibility(View.VISIBLE);

                break;

            case R.id.tv_search_over:
                if (FunctionUtils.isFastClick()) {
                    return;
                }

//                if (TextUtils.isEmpty(cabinetCode)){
//                    ToastUtil.show("光交箱码不能未空");
//                    return;
//                }

//                if (!cabinetCode.startsWith("GJ")  ||  !(cabinetCode.length()==14)){
//                    ToastUtil.show("光交箱码格式不正确");
//                    return;
//                }

                for (int i = 0; i < list.size(); i++) {
                    if (!list.get(i).isCheck()){
                        ToastUtil.show("锁二维码未扫描或者无效状态");
                        return;
                    }
                }





                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i) != null) {
                        mLockNoMap.put("locks[" + i + "].lockNo", list.get(i).getMac());
                        mLockNoMap.put("locks[" + i + "].cabinetCode", list.get(i).getCode());
                        mCabinetIdMap.put("locks[" + i + "].cabinetId", cabinetId + "");
                    }
                }


                mListAdapter.notifyDataSetChanged();

                Log.e("awj", "caMap =" + mCabinetIdMap);
                Log.e("awj", "caNoMap =" + mLockNoMap);

                if (isEmpty()) {
                    return;
                } else {

                    getMvpPresenter().clickRequest(SpUtils.getString(InstallLockActivity.this, "token", ""), mCabinetIdMap, mLockNoMap, SpUtils.getInt(InstallLockActivity.this, "userId", -1));
                }


                break;
            default:
        }
    }

    private boolean isEmpty() {


        if (TextUtils.isEmpty(etInstallLocation.getText().toString().trim())) {// || etPassSure.getText().toString().trim().length() <= 6
            Toast.makeText(InstallLockActivity.this, "请选择设施", Toast.LENGTH_SHORT).show();
            return true;
        }

        if (TextUtils.isEmpty(etInstallName.getText().toString().trim())) {// || etPassSure.getText().toString().trim().length() <= 6
            Toast.makeText(InstallLockActivity.this, "请选择设施", Toast.LENGTH_SHORT).show();
            return true;
        }


        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       // IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);


        if (requestCode == 101 && resultCode == RESULT_OK) {
            if (data != null) {


                String content = data.getExtras().getString("SCAN_RESULT");

                if (content!=null){
                    getCode(content);
                }else {
                    ToastUtil.show("格式错误，请重新扫描");
                }

            }
        }

        if (requestCode == 102 && resultCode == RESULT_OK) {
            if (data != null) {
                content = data.getExtras().getString("SCAN_RESULT");
                cabinetCode=content;
                btn_scan.setText(cabinetCode);
            }
        }
    }



    void   getCode(String code){
        OkHttpClient client=new OkHttpClient().newBuilder().build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(ConstantUrl.PUBLIC_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        HttpService apiService = retrofit.create(HttpService.class);
        apiService.checkCabinetCode(SpUtils.getString(this, "token", ""),code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseBean<Boolean>>() {
                    @Override
                    public void accept(BaseBean<Boolean> booleanBaseBean) throws Exception {

                        if (booleanBaseBean.getData()){
                            list.get(Scanposition).setCheck(true);
                            list.get(Scanposition).setCode(code);
                            mListAdapter.notifyItemChanged(Scanposition);
                        }else {
                            list.remove(Scanposition);
                            mListAdapter.notifyItemRemoved(Scanposition);
                        }






                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                });

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
}
