package com.amap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.services.core.PoiItem;
import com.parkingman.R;
import com.parkingman.Util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：ParkingMan
 * 类描述：自定义 poi over lay
 * 创建人：Yiming.Gan
 * 创建时间：2016/3/18 11:49
 * 修改人：Yiming.Gan
 * 修改时间：2016/3/18 11:49
 * 修改备注：
 */
public class PoiOverlay {

    /**
     * marker 标记的size 60dp
     */
    private static  final int MARKER_HEI=54;





    private Context context;
    private AMap mamap;
    private List<PoiItem> mPois;
    private ArrayList<Marker> mPoiMarks = new ArrayList<Marker>();

    public PoiOverlay(AMap amap, List<PoiItem> pois, Context context) {
        mamap = amap;
        mPois = pois;
        this.context=context;
    }

    /**
     * 添加Marker到地图中。
     *
     * @since V2.1.0
     */
    public void addToMap() {
        for (int i = 0; i < mPois.size(); i++) {
            Marker marker = mamap.addMarker(getMarkerOptions(i));
            PoiItem item = mPois.get(i);
            marker.setObject(item);
            mPoiMarks.add(marker);
        }
    }

    /**
     * 去掉PoiOverlay上所有的Marker。
     *
     * @since V2.1.0
     */
    public void removeFromMap() {
        for (Marker mark : mPoiMarks) {
            mark.remove();
        }
    }

    /**
     * 移动镜头到当前的视角。
     *  根据搜索的范围
     * @since V2.1.0
     */
    public void zoomToSpan() {
        if (mPois != null && mPois.size() > 0) {
            if (mamap == null)
                return;
            LatLngBounds bounds = getLatLngBounds();
            mamap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
        }
    }
    /**
     * 移动镜头到当前的视角。
     *  根据经纬度.
     * @since V2.1.0
     */
    public void zoomToSpan(AMapLocation aMapLocation) {
        if (mPois != null && mPois.size() > 0) {
            if (mamap == null)
                return;

            AmapAnimateHelper.localChangeCamera(mamap, new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude()), null);

        }
    }

    private LatLngBounds getLatLngBounds() {
        LatLngBounds.Builder b = LatLngBounds.builder();
        for (int i = 0; i < mPois.size(); i++) {
            b.include(new LatLng(mPois.get(i).getLatLonPoint().getLatitude(),
                    mPois.get(i).getLatLonPoint().getLongitude()));
        }
        return b.build();
    }

    private MarkerOptions getMarkerOptions(int index) {
        return new MarkerOptions()
                .position(
                        new LatLng(mPois.get(index).getLatLonPoint()
                                .getLatitude(), mPois.get(index)
                                .getLatLonPoint().getLongitude()))
                .icon(getBitmapDescriptor(index))
                .snippet("12");
    }

    protected String getTitle(int index) {
        return mPois.get(index).getTitle();
    }

    protected String getSnippet(int index) {
        return mPois.get(index).getSnippet();
    }

    /**
     * 从marker中得到poi在list的位置。
     *
     * @param marker 一个标记的对象。
     * @return 返回该marker对应的poi在list的位置。
     * @since V2.1.0
     */
    public int getPoiIndex(Marker marker) {
        for (int i = 0; i < mPoiMarks.size(); i++) {
            if (mPoiMarks.get(i).equals(marker)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 获取 marker 标记的图片
     * @param index
     * @return
     */
    protected BitmapDescriptor getBitmapDescriptor(int index) {
        View view= LayoutInflater.from(context).inflate(R.layout.mapview_marklayout,null);
        FrameLayout.LayoutParams params=new FrameLayout.LayoutParams(Util.dip2px(context,MARKER_HEI),Util.dip2px(context,MARKER_HEI));
        view.setLayoutParams(params);
        TextView textView=(TextView)view.findViewById(R.id.tv_num);
        PoiItem poiItem=mPois.get(index);
        textView.setText(poiItem.getAdCode());
        return BitmapDescriptorFactory.fromView(view);
    }
}
