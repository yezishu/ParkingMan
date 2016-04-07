package com.parkingman;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.AmapUtils;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;


public class MainActivity extends AppCompatActivity implements View.OnClickListener,AMapLocationListener,PoiSearch.OnPoiSearchListener{
    private TextView tv_info;

    private AMapLocationClient mapLocationClient;
    private AMapLocationClientOption locationOption;

    private PoiSearch poiSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        init();
        initPoi();

    }



    public void initPoi(){
        PoiSearch.Query query=new PoiSearch.Query("","停车场","0592");
//        query.setPageNum(1);
//        query.setPageSize(10);
        poiSearch=new PoiSearch(this,query);
        poiSearch.setOnPoiSearchListener(this);
        poiSearch.searchPOIAsyn();
    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        int size=0;
        if(i==1000){
            String s=poiResult.getPois().get(0).toString();
            size=poiResult.getPois().size();
            System.out.print(size);
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    /**
     * 高德地图设置模式
     *  1.AMapLocationMode.Battery_Saving 仅网络定位，耗电量低
     *  2.AMapLocationMode.Device_Sensors 仅设备 室外精度高 室内成功率低 耗电高
     *  3.AMapLocationMode.Hight_Accuracy 高精度模式 精度高耗电低
     */
    public  void init(){

        tv_info=(TextView)findViewById(R.id.tv_info);
        findViewById(R.id.btn_local).setOnClickListener(this);
        mapLocationClient = new AMapLocationClient(this.getApplicationContext());
        locationOption = new AMapLocationClientOption();
        // 设置定位模式为低功耗模式
        locationOption.setLocationMode( AMapLocationMode.Battery_Saving);
        // 设置定位模式为仅设备模式 室外精度高 室内成功率低 耗电高
        // locationOption.setLocationMode(AMapLocationMode.Device_Sensors);
        // 设置定位监听
        mapLocationClient.setLocationListener(this);
        // 设置是否 单次定位
        locationOption.setOnceLocation(false);
        //  设置定位请求时间间隔
        locationOption.setInterval(Long.valueOf(5000));

        // 设置是否需要显示地址信息
        locationOption.setNeedAddress(true);
        /**
        * 设置是否优先返回GPS定位结果，如果30秒内GPS没有返回定位结果则进行网络定位
                * 注意：只有在高精度模式下的单次定位有效，其他方式无效
                */
        locationOption.setGpsFirst(true);

    }


    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        Toast.makeText(this,"定位开启",Toast.LENGTH_SHORT).show();
        tv_info.setText(AmapUtils.getLocationStr(aMapLocation));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        /**
         * 如果AMapLocationClient是在当前Activity实例化的，
         * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
         */
        mapLocationClient.onDestroy();
        mapLocationClient = null;
        locationOption = null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_local:
                // 设置定位参数
                mapLocationClient.setLocationOption(locationOption);
                // 启动定位
                mapLocationClient.startLocation();
//                Intent intent=new Intent(this,MapshowActivity.class);
//                startActivity(intent);
                break;
            default:

                break;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }
}
