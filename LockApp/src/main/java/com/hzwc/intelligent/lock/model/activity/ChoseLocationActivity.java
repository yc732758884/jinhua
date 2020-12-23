package com.hzwc.intelligent.lock.model.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.google.gson.Gson;
import com.hzwc.intelligent.lock.R;
import com.hzwc.intelligent.lock.model.adapter.AddressAdapter;
import com.hzwc.intelligent.lock.model.base.BaseActivity;
import com.hzwc.intelligent.lock.model.bean.AdCodeBean;
import com.hzwc.intelligent.lock.model.bean.SearchAddressInfo;
import com.hzwc.intelligent.lock.model.utils.ActivityUtils;
import com.hzwc.intelligent.lock.model.utils.FunctionUtils;
import com.hzwc.intelligent.lock.model.utils.SpUtils;
import com.hzwc.intelligent.lock.model.utils.ToastUtil;
import com.hzwc.intelligent.lock.model.view.persenter.AdCodePresenter;
import com.hzwc.intelligent.lock.model.view.view.AdCodeView;
import com.hzwc.intelligent.lock.mvpframework.factory.CreatePresenter;
import com.hzwc.intelligent.lock.mvpframework.view.AbstractMvpBaseActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@CreatePresenter(AdCodePresenter.class)
public class ChoseLocationActivity extends AbstractMvpBaseActivity<AdCodeView, AdCodePresenter> implements AdCodeView, GeocodeSearch.OnGeocodeSearchListener, AMap.OnMapClickListener, AMap.OnCameraChangeListener, PoiSearch.OnPoiSearchListener, AdapterView.OnItemClickListener, AMapLocationListener {

    @BindView(R.id.iv_title_return)
    ImageView ivTitleReturn;
    @BindView(R.id.tv_title_text)
    TextView tvTitleText;
    @BindView(R.id.tv_search)
    TextView send;

    @BindView(R.id.center_image)
    ImageView centerImage;
    @BindView(R.id.position_btn)
    ImageButton locationButton;
    @BindView(R.id.top)
    RelativeLayout top;
    @BindView(R.id.lv_list)
    ListView listView;
    @BindView(R.id.activity_share_location)
    LinearLayout activityShareLocation;
    @BindView(R.id.mapview)
    MapView mapView;
//    private String city;
    private LatLonPoint latLonPoint;
    private Animation centerAnimation;
    private AddressAdapter addressAdapter;
    private ArrayList<SearchAddressInfo> mData = new ArrayList<>();
    private AMap aMap;
    private UiSettings uiSettings;
    private Marker locationMarker;
    private LatLng mFinalChoosePosition;
    private GeocodeSearch geocoderSearch;
    public SearchAddressInfo mAddressInfoFirst = null;
    private String addressName;
    private boolean isHandDrag = true;
    private boolean isFirstLoad = true;
    private boolean isBackFromSearch = false;
    private int currentPage = 0;// 当前页面，从0开始计数
    private PoiSearch.Query query;// Poi查询条件类
    private PoiSearch poiSearch;
    private PoiResult poiResult; // poi返回的结果
    private List<PoiItem> poiItems;// poi数据
    private static final int SEARCH_ADDDRESS = 1;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    private AMapLocationClient mLocationClient;
    private String adCode;
    private int areaId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chose_location);
        ButterKnife.bind(this);
        mapView.onCreate(savedInstanceState);
    }

    @Override
    protected void initIntentData() {

    }

    @Override
    protected void initView() {

        tvTitleText.setText(R.string.title_chose_location);
        send.setVisibility(View.VISIBLE);
        send.setText("确定");
        ivTitleReturn.setVisibility(View.VISIBLE);

//        double longitude = 120.22501069269869;
//        double latitude = 30.216246906858395;
        double longitude = 0;
        double latitude = 0;
//        city = getIntent().getStringExtra("cityCode");

        //经纬度信息
        latLonPoint = new LatLonPoint(latitude, longitude);

        centerAnimation = AnimationUtils.loadAnimation(this, R.anim.center_anim);
        addressAdapter = new AddressAdapter(this, mData);
        listView.setAdapter(addressAdapter);

        listView.setOnItemClickListener(this);
        initMap();
    }


    private void initMap() {

        if (aMap == null) {
            aMap = mapView.getMap();

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
            mLocationClient = new AMapLocationClient(this);
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
     * 把LatLng对象转化为LatLonPoint对象
     */
    public static LatLonPoint convertToLatLonPoint(LatLng latlon) {
        return new LatLonPoint(latlon.latitude, latlon.longitude);
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
            poiSearch.setOnPoiSearchListener(this);  // 实现  onPoiSearched  和  onPoiItemSearched
            poiSearch.setBound(new PoiSearch.SearchBound(llPoint, 5000, true));//
            // 设置搜索区域为以lpTemp点为圆心，其周围5000米范围
            poiSearch.searchPOIAsyn();// 异步搜索
        }
    }


    /**
     * poi没有搜索到数据，返回一些推荐城市的信息
     */
    private void showSuggestCity(List<SuggestionCity> cities) {
        String infomation = "推荐城市\n";
        for (int i = 0; i < cities.size(); i++) {
            infomation += "城市名称:" + cities.get(i).getCityName() + "城市区号:"
                    + cities.get(i).getCityCode() + "城市编码:"
                    + cities.get(i).getAdCode() + "\n";
        }
//        ToastUtil.show(this, infomation);
        Log.e("awj","infomation ="+infomation);
    }


    /**
     * 把LatLonPoint对象转化为LatLon对象
     */
    public LatLng convertToLatLng(LatLonPoint latLonPoint) {
        return new LatLng(latLonPoint.getLatitude(), latLonPoint.getLongitude());
    }

    private void sendLocaton() {
        SearchAddressInfo addressInfo = null;
        for (SearchAddressInfo info : mData) {
            if (info.isChoose) {
                addressInfo = info;
            }
        }
        if (addressInfo != null) {

            Intent intent = new Intent(ChoseLocationActivity.this, RingMainUnitActivity.class);
            intent.putExtra("addressName", addressName);
            intent.putExtra("latitude", addressInfo.latLonPoint.getLatitude());
            intent.putExtra("longitude", addressInfo.latLonPoint.getLongitude());
            intent.putExtra("areaId", areaId);

            setResult(0, intent);
            Log.e("awj", "要发送的数据："
                    + "\n 经度：" + addressInfo.latLonPoint.getLongitude()
                    + "\n 纬度：" + addressInfo.latLonPoint.getLatitude()
                    + "\n 地址：" + addressInfo.addressName);
            finish();
        } else {
            ToastUtil.show(this, "请选择地址");
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        mFinalChoosePosition = convertToLatLng(mData.get(position).latLonPoint);
        for (int i = 0; i < mData.size(); i++) {
            mData.get(i).isChoose = false;
        }
        mData.get(position).isChoose = true;

        isHandDrag = false;

        // 点击之后，改变了地图中心位置， onCameraChangeFinish 也会调用
        // 只要地图发生改变，就会调用 onCameraChangeFinish ，不是说非要手动拖动屏幕才会调用该方法
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mFinalChoosePosition.latitude, mFinalChoosePosition.longitude), 16));


    }

    /**
     * 逆地理编码查询回调
     *
     * @param result
     * @param i
     */


    @Override
    public void onRegeocodeSearched(RegeocodeResult result, int i) {
        if (i == 1000) {//转换成功
            if (result != null && result.getRegeocodeAddress() != null
                    && result.getRegeocodeAddress().getFormatAddress() != null) {
                //拿到详细地址
                addressName = result.getRegeocodeAddress().getFormatAddress(); // 逆转地里编码不是每次都可以得到对应地图上的opi
                adCode = result.getRegeocodeAddress().getAdCode();

                getMvpPresenter().clickMessageRequest(SpUtils.getString(ChoseLocationActivity.this,"token",""),adCode);
                Log.e("awj", "=====================" + addressName);
//               Log.e("awj","====================="+result.getRegeocodeAddress().getCityCode());
//               Log.e("awj","====================="+result.getRegeocodeAddress().getTowncode());

                //条目中第一个地址 也就是当前你所在的地址
                mAddressInfoFirst = new SearchAddressInfo(addressName, addressName, false, convertToLatLonPoint(mFinalChoosePosition));

                //其实也是可以在这就能拿到附近的兴趣点的

            } else {
                ToastUtil.show(this, "没有搜到");
            }
        } else {
            ToastUtil.showerror(this, i);
        }
    }

    /**
     * 地理编码查询回调
     *
     * @param geocodeResult
     * @param i
     */
    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }

    @Override
    public void onPoiSearched(PoiResult result, int rcode) {
        if (rcode == 1000) {
            if (result != null && result.getQuery() != null) {// 搜索poi的结果
                if (result.getQuery().equals(query)) {// 是否是同一条
                    poiResult = result;
                    poiItems = poiResult.getPois();// 取得第一页的poiitem数据，页数从数字0开始

                    List<SuggestionCity> suggestionCities = poiResult
                            .getSearchSuggestionCitys();// 当搜索不到poiitem数据时，会返回含有搜索关键字的城市信息

                    //搜索到数据
                    if (poiItems != null && poiItems.size() > 0) {

                        mData.clear();

                        //先将 逆地理编码过的当前地址 也就是条目中第一个地址 放到集合中
                        mData.add(mAddressInfoFirst);

                        SearchAddressInfo addressInfo = null;

                        for (PoiItem poiItem : poiItems) {

                            addressInfo = new SearchAddressInfo(poiItem.getTitle(), poiItem.getSnippet(), false, poiItem.getLatLonPoint());

                            mData.add(addressInfo);
                        }
                        if (isHandDrag) {
                            mData.get(0).isChoose = true;
                        }
                        addressAdapter.notifyDataSetChanged();

                    } else if (suggestionCities != null
                            && suggestionCities.size() > 0) {
                        showSuggestCity(suggestionCities);
                    } else {
//                        ToastUtil.show(ChoseLocationActivity.this,
//                                "对不起，没有搜索到相关数据");
                    }
                }
            } else {
//                Toast.makeText(this, "对不起，没有搜索到相关数据！", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }


    @Override
    public void onCameraChange(CameraPosition cameraPosition) {

    }

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
//每次移动结束后地图中心的经纬度
        mFinalChoosePosition = cameraPosition.target;

        centerImage.startAnimation(centerAnimation);


        if (isHandDrag || isFirstLoad) {//手动去拖动地图

            // 开始进行poi搜索
            getAddressFromLonLat(cameraPosition.target);
            doSearchQueryByPosition();

        } else if (isBackFromSearch) {
            //搜索地址返回后 拿到选择的位置信息继续搜索附近的兴趣点
            isBackFromSearch = false;
            doSearchQueryByPosition();
        } else {
            addressAdapter.notifyDataSetChanged();
        }
        isHandDrag = true;
        isFirstLoad = false;
    }

    @Override
    public void onMapClick(LatLng latLng) {
//        Toast.makeText(this, "latitude" + String.valueOf(latLng.latitude), Toast.LENGTH_SHORT).show();


    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mapView != null) {

            mapView.onDestroy();
        }
    }

    @OnClick({R.id.iv_title_return, R.id.tv_search, R.id.position_btn})
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
                sendLocaton();
                break;
            case R.id.position_btn:
                if (FunctionUtils.isFastClick()) {
                    return;
                }
                aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latLonPoint.getLatitude(), latLonPoint.getLongitude()), 16));

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SEARCH_ADDDRESS && resultCode == RESULT_OK) {
            SearchAddressInfo info = (SearchAddressInfo) data.getParcelableExtra("position");
            mAddressInfoFirst = info; // 上一个页面传过来的 位置信息
            info.isChoose = true;
            isBackFromSearch = true;
            isHandDrag = false;
            //移动地图
            aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(info.latLonPoint.getLatitude(), info.latLonPoint.getLongitude()), 20));
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
                Log.e("awj", "onLocationChanged "
                        + aMapLocation.getErrorCode() + ", el:"
                        + aMapLocation.getLatitude() + ", ed:"
                        + aMapLocation.getLongitude()
                        + aMapLocation.getAdCode());//地区编码());


                //当前位置设置为地图中心
//                    setMapCenter(mLongitude, mLatitude);
            } else {
                //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                Log.e("awj", "onLocationChanged Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
            }
        }

    }

    @Override
    public void dataLoading() {

    }

    @Override
    public void dataSuccess(AdCodeBean result) {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(result);
        Log.e("awj", "AdCodeBean ="+jsonStr);

        if(result.getCode() == 0){
            areaId = result.getAreaId();
        }else if (result.getCode() == 95598) {
            SpUtils.setBoolean(ChoseLocationActivity.this, "isLogin", false);
            Toast.makeText(ChoseLocationActivity.this, result.getMsg(), Toast.LENGTH_SHORT).show();
            ActivityUtils.startActivityAndFinish(ChoseLocationActivity.this, LoginActivity.class);
        }else {
            ToastUtil.show(ChoseLocationActivity.this,result.getMsg());
        }
    }

    @Override
    public void dataFailure(String result) {

    }
}
