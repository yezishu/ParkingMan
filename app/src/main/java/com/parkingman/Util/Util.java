package com.parkingman.Util;

import android.content.Context;
import android.hardware.SensorManager;
import android.os.Build;

/**
 * 项目名称：ParkingMan
 * 类描述：项目 工具类
 * 创建人：Yiming.Gan
 * 创建时间：2016/3/18 19:02
 * 修改人：Yiming.Gan
 * 修改时间：2016/3/18 19:02
 * 修改备注：
 */
public class Util  {
    /**
     * 当前版本是否高于 lollipop
     * @return
     */
    public static  boolean hasLollipop(){
        return Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP;
    }



    /**
     * 根据 地磁场传感器 与 加速传感器 测量值计算人的朝向
     * @return  返回值 有转换成 高德地图map.setMyLocationRotateAngle
     */
    public static float calculateOrientation(float[] accelerometerValues,float[] magneticFieldValues) {
        float[] values = new float[3];
        float[] R = new float[9];

        SensorManager.getRotationMatrix(R, null, accelerometerValues, magneticFieldValues);
        SensorManager.getOrientation(R, values);

        values[0] = (float) Math.toDegrees(values[0]);
        if(values[0]>=0){
            return  360-values[0];
        }else{
            return -values[0];
        }

//        values[0] = (float) Math.toDegrees(values[0]);
//        if (values[0] >= -5 && values[0] < 5) {
//            azimuthAngle.setText("正北");
//        } else if (values[0] >= 5 && values[0] < 85) {
//            // Log.i(TAG, "东北");
//            azimuthAngle.setText("东北");
//        } else if (values[0] >= 85 && values[0] <= 95) {
//            // Log.i(TAG, "正东");
//            azimuthAngle.setText("正东");
//        } else if (values[0] >= 95 && values[0] < 175) {
//            // Log.i(TAG, "东南");
//            azimuthAngle.setText("东南");
//        } else if ((values[0] >= 175 && values[0] <= 180) || (values[0]) >= -180 && values[0] < -175) {
//            // Log.i(TAG, "正南");
//            azimuthAngle.setText("正南");
//        } else if (values[0] >= -175 && values[0] < -95) {
//            // Log.i(TAG, "西南");
//            azimuthAngle.setText("西南");
//        } else if (values[0] >= -95 && values[0] < -85) {
//            // Log.i(TAG, "正西");
//            azimuthAngle.setText("正西");
//        } else if (values[0] >= -85 && values[0] < -5) {
//            // Log.i(TAG, "西北");
//            azimuthAngle.setText("西北");
//        }
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
