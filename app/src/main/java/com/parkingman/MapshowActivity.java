package com.parkingman;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.widget.TextView;

import com.amap.AmapUtils;
import com.amap.ConstAmap;
import com.amap.MarkDetailLayout;
import com.amap.OptionsCreater;
import com.amap.PoiOverlay;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.services.cloud.CloudItem;
import com.amap.api.services.cloud.CloudItemDetail;
import com.amap.api.services.cloud.CloudResult;
import com.amap.api.services.cloud.CloudSearch;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.arlib.floatingsearchview.FloatingSearchView;
import com.parkingman.Util.ToastUtil;
import com.parkingman.Util.Util;
import com.parkingman.view.util.FastBlur;
import com.parkingman.views.CircularRevealFab;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;


/**
 * 项目名称：ParkingMan
 * 类描述：
 * 创建人：Yiming.Gan
 * 创建时间：2016/3/16 17:23
 * 修改人：Yiming.Gan
 * 修改时间：2016/3/16 17:23
 * 修改备注：
 */
public class MapshowActivity extends AppCompatActivity implements View.OnClickListener, AMap.OnCameraChangeListener,
        CloudSearch.OnCloudSearchListener, AMapLocationListener, LocationSource, AMap.OnMarkerClickListener,
        AMap.OnMapLoadedListener, AMap.OnMapClickListener, AMap.OnMapLongClickListener, SensorEventListener,
        ViewTreeObserver.OnGlobalLayoutListener {


    private boolean isInitLoad = true;//是否初始化加载标志 1. 判断是否显示 定位camera的动画 2.toolbar显示动画

    /**
     * 定位模块
     */
    private AMapLocationClient mapLocationClient;
    private LatLonPoint latLonPoint;//定位获得的经纬度
    private OnLocationChangedListener onLocationChangedListener;
    private TextView tv_ceshishu;
    /**
     * 手机传感器监听
     */
    private SensorManager sensorManager;
    private float[] accelerometerValues = new float[3];//地磁场传感器值
    private float[] magneticFieldValues = new float[3];//加速传感器值

    /**
     * 地图模块
     */
    private AMap map;
    private MapView mapView;
    private UiSettings uiSettings;

    /**
     * 云搜索模块
     */
    private CloudSearch cloudSearch;//云搜索
    private CloudSearch.Query query;//搜索关键字
    private CloudSearch.SearchBound bound;//搜索范围
    private boolean isFist = true;

    /**
     * poi 搜索
     */
    private PoiSearch poiSearch;

    private SlidingUpPanelLayout sliding_layout;
    private CircularRevealFab fab;


    /**
     * 标记详情 draView
     */
    private DrawerLayout drawerLayout;
    private View markerInfo_backGround;
    private View markinfo_top;
    private boolean isShowTool = true;
    private FloatingSearchView searchView;//顶部搜索栏


    /**
     * 数据展示部分
     */
    private MarkDetailLayout markDetailLayout;//标记详情布局

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapshow);
        initIntent();
        init(savedInstanceState);
        initData();
    }

    public void initIntent() {
        Intent intent = getIntent();
        if (intent == null)
            return;
    }

    public void init(Bundle savedInstanceState) {
        initDrawView();
        initSlidingView();
        initLayers();
        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        //初始化 地图
        if (map == null) {
            map = mapView.getMap();
            setUpMap();
        }
        steupSensorListent();

    }

    private void initData(){
        //init mark detail layout
        TextView tv_title=(TextView)findViewById(R.id.parking_name);
        TextView tv_adName=(TextView)findViewById(R.id.tv_adname);
        markDetailLayout=new MarkDetailLayout(tv_title,tv_adName);
    }

    /**
     * 加载 菜单选项
     */
    private void initDrawView() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        searchView = (FloatingSearchView) findViewById(R.id.floating_search_view);

        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                searchView.closeMenu(true);
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });


        searchView.setOnLeftMenuClickListener(new FloatingSearchView.OnLeftMenuClickListener() {
            @Override
            public void onMenuOpened() {
                drawerLayout.openDrawer(GravityCompat.START);
            }

            @Override
            public void onMenuClosed() {
                drawerLayout.closeDrawer(GravityCompat.END);
            }
        });


    }

    /**
     * 加载底部滑动 视图
     */
    private void initSlidingView() {
        sliding_layout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        markerInfo_backGround =findViewById(R.id.backGround);
        markinfo_top=findViewById(R.id.markinfo_top);


        try {
            fab = (CircularRevealFab) findViewById(R.id.fab);
            fab.setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(this);

        sliding_layout.setAnchorPoint(0.7f);
//        sliding_layout.setPanelState(SlidingUpPanelLayout.PanelState.ANCHORED);
//        sliding_layout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);

        sliding_layout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                markerInfo_backGround.setAlpha(slideOffset/0.7f);
                markinfo_top.setAlpha(slideOffset/0.7f*0.2f+0.8f);
            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                if (newState.equals(SlidingUpPanelLayout.PanelState.COLLAPSED)) {
                    fab.show();
                } else {
                    fab.hide();
                }
            }
        });

        sliding_layout.setFadeOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    /**
     * 初始化图层界面
     */
    private void initLayers() {
        findViewById(R.id.Lk).setOnClickListener(this);
        findViewById(R.id.WX).setOnClickListener(this);
    }

    /**
     * 初始化定位
     */
    public void steupLocal() {
        mapLocationClient = new AMapLocationClient(this.getApplicationContext());
        mapLocationClient.setLocationOption(OptionsCreater.getLocalOption());
        mapLocationClient.setLocationListener(this);
        mapLocationClient.startLocation();
    }

    /**
     * 初始化地图
     */
    private void setUpMap() {
        setMapUi();
        setMapListener();
    }

    /**
     * 初始化传感器
     */
    private void steupSensorListent() {
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
    }

    /**
     * 初始化云搜索
     *
     * @param lat  经度
     * @param lont 纬度
     */
    public void steupCloudSearch(double lat, double lont) {
        if (!isFist)
            return;
        isFist = false;
        cloudSearch = new CloudSearch(this);
        cloudSearch.setOnCloudSearchListener(this);

        latLonPoint = new LatLonPoint(lat, lont);
        bound = new CloudSearch.SearchBound(latLonPoint, ConstAmap.SEARCH_AROUND);
        try {
            query = new CloudSearch.Query(ConstAmap.AMAP_TABLE_ID, "停车场", bound);
        } catch (AMapException e) {
            e.printStackTrace();
        }
        cloudSearch.searchCloudAsyn(query);

    }


    /**
     * 设置地图
     */
    private void setMapUi() {
        uiSettings = OptionsCreater.getAMapUisetting(map);
        map.setMyLocationStyle(OptionsCreater.getMaylocalStyle(this));
        map.setLocationSource(this);
        map.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);

        // 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        map.setMyLocationEnabled(true);

    }

    /**
     * 为地图增加一些事件监听
     */
    private void setMapListener() {
//        map.setOnMarkerDragListener(this);// 设置marker可拖拽事件监听器
//        map.setOnMapLoadedListener(this);// 设置amap加载成功事件监听器
        map.setOnMarkerClickListener(this);// 设置点击marker事件监听器
//        map.setOnInfoWindowClickListener(this);// 设置点击infoWindow事件监听器
        map.setOnMapLoadedListener(this);
        map.setOnCameraChangeListener(this);
//        map.setInfoWindowAdapter(this);// 设置自定义InfoWindow样式
    }

    private void loadPositionPOI(final AMapLocation aMapLocation) {
        PoiSearch.Query query = new PoiSearch.Query(ConstAmap.SEARCH_KEYWORD, "", "0592");// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        query.setPageSize(30);// 设置每页最多返回多少条poiitem
        query.setPageNum(10);// 设置查第一页

        poiSearch = new PoiSearch(this, query);
        poiSearch.setBound(new PoiSearch.SearchBound(new LatLonPoint(aMapLocation.getLatitude(), aMapLocation.getLongitude()), ConstAmap.POI_BOUNG, true));//
        poiSearch.setOnPoiSearchListener(new PoiSearch.OnPoiSearchListener() {
            @Override
            public void onPoiSearched(PoiResult poiResult, int resultCode) {
                if (resultCode == ConstAmap.NO_ERROR) {
//                    if (result.getQuery().equals(query)) {// 是否是同一条
                    map.clear(true);
                    PoiOverlay poiOverlay = new PoiOverlay(map, poiResult.getPois(), MapshowActivity.this);
                    poiOverlay.addToMap();
                    if (isInitLoad) {
                        poiOverlay.zoomToSpan(aMapLocation);
                        isInitLoad = false;
                    }
                } else {
                    ToastUtil.show(MapshowActivity.this,getString(R.string.error_poiSearch));
                }
            }

            @Override
            public void onPoiItemSearched(PoiItem poiItem, int i) {

            }
        });


        // 设置搜索区域为以lp点为圆心，其周围5000米范围
        poiSearch.searchPOIAsyn();// 异步搜索

    }

    /**
     * 定位变化
     *
     * @param aMapLocation
     */
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation == null) {//没有定位结果处理

        }
        if (aMapLocation.getErrorCode() != ConstAmap.NO_ERROR_LOCAL) {//定位错误处理  getErrorInfo

        } else {
            doLocalChange(aMapLocation);

        }

    }

    /**
     * 处理定位
     *
     * @param aMapLocation
     */
    public void doLocalChange(AMapLocation aMapLocation) {
        if (aMapLocation != null && aMapLocation.getErrorCode() == 0) {

            onLocationChangedListener.onLocationChanged(aMapLocation);
//            OptionsCreater.setMylocationMarker(aMapLocation,map);

            if (isInitLoad) {//是否第一次定位
                if (AmapUtils.isReLoadPoi(aMapLocation)) {
                    loadPositionPOI(aMapLocation);
                }
            }
        }
//      steupCloudSearch(aMapLocation.getLatitude(),aMapLocation.getLongitude());
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        this.onLocationChangedListener = onLocationChangedListener;
        steupLocal();
    }

    @Override
    public void deactivate() {
        onLocationChangedListener = null;
        if (mapLocationClient != null) {
            mapLocationClient.stopLocation();
            mapLocationClient.onDestroy();
        }
        mapLocationClient = null;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            accelerometerValues = event.values;
        }
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            magneticFieldValues = event.values;
        }
        float angle = Util.calculateOrientation(accelerometerValues, magneticFieldValues);
        map.setMyLocationRotateAngle(angle);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    /**
     * 处理底部高斯模糊  未使用
     */
    private void doBlurBottom() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                map.getMapScreenShot(new AMap.OnMapScreenShotListener() {
                    @Override
                    public void onMapScreenShot(Bitmap bitmap) {
                        blur(bitmap, null);
                    }

                    @Override
                    public void onMapScreenShot(Bitmap bitmap, int i) {

                    }
                });
            }
        }).run();

    }

    private void blur(Bitmap bkg, View view) {
        long startMs = System.currentTimeMillis();
        float scaleFactor = 1;
        float radius = 20;
        scaleFactor = 8;
        radius = 2;

        Bitmap overlay = Bitmap.createBitmap((int) (view.getMeasuredWidth() / scaleFactor),
                (int) (view.getMeasuredHeight() / scaleFactor), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(overlay);
        canvas.translate(-view.getLeft() / scaleFactor, -view.getTop() / scaleFactor);
        canvas.scale(1 / scaleFactor, 1 / scaleFactor);
        Paint paint = new Paint();
        paint.setFlags(Paint.FILTER_BITMAP_FLAG);
        canvas.drawBitmap(bkg, 0, 0, paint);

        overlay = FastBlur.doBlur(overlay, (int) radius, true);
//        view_detail.setBackground(new BitmapDrawable(getResources(), overlay));
    }

    /**
     * on  load
     */
    @Override
    public void onMapLoaded() {

    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        if (!isInitLoad)
            showToolBar(false);
    }

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
        if (!isInitLoad)
            showToolBar(true);
    }

    @Override
    public void onMapLongClick(LatLng latLng) {

    }

    @Override
    public void onMapClick(LatLng latLng) {
        showToolBar(true);
    }

    /**
     * 设置 toolbar 状态
     *
     * @param is true 显示   false 隐藏
     */
    private void showToolBar(boolean is) {
        if (is && !isShowTool) {//显示toolbar
            isShowTool = true;
            searchView.animate().translationY(0).setInterpolator(new AccelerateInterpolator(2));
        } else if (!is && isShowTool) {// 隐藏
            isShowTool = false;
            searchView.animate().translationY(-searchView.getHeight()).setInterpolator(new AccelerateInterpolator(2));
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        sliding_layout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        doShowMarkerDet(marker);

        return true;
    }

    /**
     * 显示marker的详细信息
     * @param marker
     */
    private void doShowMarkerDet(Marker marker){
        markDetailLayout.setInfo(marker);
    }

    /**
     * 云搜索
     *
     * @param result    搜索结果
     * @param errorCode 错误码
     */
    @Override
    public void onCloudSearched(CloudResult result, int errorCode) {

        if (result != null) {
            ArrayList<CloudItem> cloudResult = result.getClouds();
            if (cloudResult != null && cloudResult.size() > 0) {
                for (CloudItem itemn : cloudResult) {
                    System.out.print(itemn.toString() + "/n");
                }

            } else {

            }

        } else if (errorCode == ConstAmap.ERROR_CODE_SOCKE_TIME_OUT) {//网络超时
            ToastUtil.show(this.getApplicationContext(),
                    R.string.error_socket_timeout);
        } else if (errorCode == ConstAmap.ERROR_CODE_UNKNOW_HOST) {//网络错误
            ToastUtil
                    .show(this.getApplicationContext(), R.string.error_network);
        } else {
            ToastUtil.show(this.getApplicationContext(),
                    getString(R.string.error_other) + errorCode);
        }
    }

    /**
     * 云搜索
     *
     * @param cloudItemDetail
     * @param i
     */
    @Override
    public void onCloudItemDetailSearched(CloudItemDetail cloudItemDetail, int i) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Lk:
//                map.setTrafficEnabled(true);

                break;
            case R.id.WX://卫星地图
//                map.setMapType(AMap.MAP_TYPE_SATELLITE);
                sliding_layout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                sliding_layout.setAnchorPoint(1f);
                break;

            case R.id.fab:
                ToastUtil.show(this, "fdfd");
                break;
            default:
                break;
        }
    }

    @Override
    public void onGlobalLayout() {
        if (Build.VERSION.SDK_INT >= 16) {
            getWindow().getDecorView().getViewTreeObserver().removeOnGlobalLayoutListener(this);
        } else {
            getWindow().getDecorView().getViewTreeObserver().removeGlobalOnLayoutListener(this);
        }
        fab.getHitRect(null);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_camera:
                ToastUtil.show(this, "camera");
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
        Sensor sensor_orientation = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        sensorManager.registerListener(this, sensor_orientation, SensorManager.SENSOR_DELAY_UI);

        Sensor sensor_accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, sensor_accelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this); //取消所有传感器的监听
        mapView.onPause();
        deactivate();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        deactivate();
    }


}
