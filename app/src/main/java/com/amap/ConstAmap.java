package com.amap;

/**
 * 项目名称：ParkingMan
 * 类描述：amap 常量类
 *
 * 创建人：Yiming.Gan
 * 创建时间：2016/3/17 9:52
 * 修改人：Yiming.Gan
 * 修改时间：2016/3/17 9:52
 * 修改备注：
 */
public class ConstAmap {

    /**
     * 搜索关键词
     */
    public static final String SEARCH_KEYWORD="停车场";


    /**
     * amap 地图配置
     */
    public static  final String AMAP_TABLE_ID="56e818557bbf197f399e56e3";
    public static final int SEARCH_AROUND = 1000000;//搜索范围
    public static  final int INIT_ZOOM_SC=14;//地图初始化 放大的比例

    /**
     * camera 动画时间
     */
    public static  final long CAMERA_ANIMATE_DURATION=1500;
    /**
     * aMap cloud search result
     */
    public static final int ERROR_CODE_SOCKE_TIME_OUT = 23;//请求超时
    public static final int ERROR_CODE_UNKNOW_HOST = 27;//网络异常
    public static final int NO_ERROR =1000;//请求成功无异常
    /**
     * Local request success
     */
    public static final int NO_ERROR_LOCAL=0;

    /**
     * poi 搜索范围
     * 单位米
     */
    public static final int POI_BOUNG=5000;

}
