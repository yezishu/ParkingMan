package com.parkingman.rxjatest.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * Des：
 * creat by Zishu.Ye on 2016/4/7  11:11
 */
public abstract class BaseActivity extends AppCompatActivity {

    abstract protected int setViewLayout();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setViewLayout());
        ButterKnife.bind(this);
    }
}
