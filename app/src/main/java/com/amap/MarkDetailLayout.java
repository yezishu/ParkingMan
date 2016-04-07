package com.amap;

import android.widget.TextView;

import com.amap.api.maps.model.Marker;
import com.amap.api.services.core.PoiItem;

/**
 * 项目名称：ParkingMan
 * 类描述：标记显示详情布局 实体类
 * 创建人：Yiming.Gan
 * 创建时间：2016/3/23 18:27
 * 修改人：Yiming.Gan
 * 修改时间：2016/3/23 18:27
 * 修改备注：
 */
public class MarkDetailLayout {

    private TextView tv_name;
    private TextView tv_addr;

    public MarkDetailLayout(TextView tv_name,TextView tv_addr){
        this.tv_addr=tv_addr;
        this.tv_name=tv_name;
    }

    /**
     * setInfo 2 detailview
     * @param marker
     */
    public void setInfo(Marker marker){
        PoiItem poiItem=(PoiItem)marker.getObject();
        tv_name.setText(poiItem.getTitle());
        tv_addr.setText(poiItem.getAdName());
    }

    public TextView getTv_name() {
        return tv_name;
    }

    public void setTv_name(TextView tv_name) {
        this.tv_name = tv_name;
    }

    public TextView getTv_addr() {
        return tv_addr;
    }

    public void setTv_addr(TextView tv_addr) {
        this.tv_addr = tv_addr;
    }
}
