package com.parkingman.Util;

import android.content.Context;
import android.widget.Toast;

/**
 * 项目名称：ParkingMan
 * 类描述：快速 Toast
 * 创建人：Yiming.Gan
 * 创建时间：2016/3/17 10:00
 * 修改人：Yiming.Gan
 * 修改时间：2016/3/17 10:00
 * 修改备注：
 */
public class ToastUtil {

    public static void show(Context context, String info) {
        Toast.makeText(context, info, Toast.LENGTH_SHORT).show();
    }

    public static void show(Context context, int info) {
        Toast.makeText(context, info, Toast.LENGTH_LONG).show();
    }
}
