package com.parkingman.rxjatest;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.parkingman.rxjatest.base.BaseActivity;
import com.parkingman.rxjatest.busFragment.ReceiveFragment;
import com.parkingman.rxjatest.busFragment.SendFragment;

/**
 * Des：
 * creat by Zishu.Ye on 2016/4/7  11:22
 */
public class BusActivity extends BaseActivity {

    @Override
    protected int setViewLayout() {return R.layout.activity_bus;}

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.sendFragment,new SendFragment())
                .replace(R.id.receiveFragment,new ReceiveFragment())
                .commit();

    }

    public static class TapEvent{}
}
