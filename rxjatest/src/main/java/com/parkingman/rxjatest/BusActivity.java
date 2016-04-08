package com.parkingman.rxjatest;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.parkingman.rxjatest.base.BaseActivity;
import com.parkingman.rxjatest.bus.RxBus;

import butterknife.OnClick;

/**
 * Des：
 * creat by Zishu.Ye on 2016/4/7  11:22
 */
public class BusActivity extends BaseActivity {

    @Override
    protected int setViewLayout() {return R.layout.activity_netsafe;}

    private RxBus rxBus;

    @OnClick(R.id.btn)public void clickEven(){}


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rxBus=getRxBusSingleton();
    }
}
