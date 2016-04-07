package com.amap;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Created by 叶子叔 on 2016/3/20.
 */
public class MySensorEventListener  implements SensorEventListener {

    @Override
    public void onSensorChanged(SensorEvent event) {
        //方向传感器
        if(event.sensor.getType()==Sensor.TYPE_ORIENTATION){
            //x表示手机指向的方位，0表示北90表示东，180表示南，270表示西
            float x = event.values[SensorManager.DATA_X];
            float y = event.values[SensorManager.DATA_Y];
            float z = event.values[SensorManager.DATA_Z];


        }
        //重力传感器
   /*else{
    float x = event.values[SensorManager.DATA_X];
    float y = event.values[SensorManager.DATA_Y];
    float z = event.values[SensorManager.DATA_Z];
    tv_accelerometer.setText(\"Accelerometer:\"+x+\"\"+y+\"\"+z);
   }*/
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


}
