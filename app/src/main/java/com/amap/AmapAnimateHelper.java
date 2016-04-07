package com.amap;


import com.amap.api.maps.AMap;
import com.amap.api.maps.AMap.CancelableCallback;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;


/**
 * 项目名称：ParkingMan
 * 类描述：高德地图 动画 类
 * 创建人：Yiming.Gan
 * 创建时间：2016/3/18 10:25
 * 修改人：Yiming.Gan
 * 修改时间：2016/3/18 10:25
 * 修改备注：
 */
public class AmapAnimateHelper {


    /**
     * @param aMap
     * @param latLng
     * @param callback
     */
    public static void localChangeCamera(AMap aMap, LatLng latLng, CancelableCallback callback) {
        CameraUpdate update= CameraUpdateFactory.newCameraPosition(new CameraPosition(latLng, ConstAmap.INIT_ZOOM_SC, 0, 0));
        aMap.animateCamera(update, ConstAmap.CAMERA_ANIMATE_DURATION, callback);
    }
}
