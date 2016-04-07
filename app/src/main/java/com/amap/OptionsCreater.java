package com.amap;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.maps.AMap;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.MyLocationStyle;
import com.parkingman.R;

/**
 * 项目名称：ParkingMan
 * 类描述：高德地图 选项构造类  为定位，云检索，设置option
 * 创建人：Yiming.Gan
 * 创建时间：2016/3/17 10:57
 * 修改人：Yiming.Gan
 * 修改时间：2016/3/17 10:57
 * 修改备注：
 */
public class OptionsCreater {

    /**
     *  获取定位配置信息
     * @return locationOption
     */
    public static AMapLocationClientOption getLocalOption(){

        AMapLocationClientOption locationOption = new AMapLocationClientOption();
        // 设置定位模式为低功耗模式
//        locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
        // 设置定位模式为仅设备模式 室外精度高 室内成功率低 耗电高
//         locationOption.setLocationMode(AMapLocationMode.Device_Sensors);
         locationOption.setLocationMode(AMapLocationMode.Hight_Accuracy);

        // 设置是否 单次定位
        locationOption.setOnceLocation(false);
        //  设置定位请求时间间隔
        locationOption.setInterval(Long.valueOf(2000));

        // 设置是否需要显示地址信息
        locationOption.setNeedAddress(true);
        /**
         * 设置是否优先返回GPS定位结果，如果30秒内GPS没有返回定位结果则进行网络定位
         * 注意：只有在高精度模式下的单次定位有效，其他方式无效
         */
        locationOption.setGpsFirst(true);
        return  locationOption;
    }

    /**
     * 获取amap ui 设置
     * @param map
     * @return  uiSettings
     */
    public static UiSettings getAMapUisetting(AMap map){
        UiSettings uiSettings  = map.getUiSettings();
        uiSettings.setZoomControlsEnabled(false);
        uiSettings.setScaleControlsEnabled(true);
        uiSettings.setMyLocationButtonEnabled(false);


        //uiSettings.setRotateGesturesEnabled(true);
        //uiSettings.setTiltGesturesEnabled(true);
        return  uiSettings;
    }

    /**
     * 设置 定位我的位置的小蓝点
     * @return
     */
    public static MyLocationStyle getMaylocalStyle(Context context){
        // 自定义系统定位蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
         // 自定义定位蓝点图标
//        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher));
        // 自定义精度范围的圆形边框颜色
        myLocationStyle.strokeColor(R.color.colorAccent) ;

        myLocationStyle.radiusFillColor(ContextCompat.getColor(context,R.color.colorAccent_al));

       return  myLocationStyle;
    }

}
