package com.parkingman.rxjatest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.parkingman.rxjatest.base.BaseActivity;

import butterknife.Bind;
import butterknife.OnClick;

public class RxjaTest extends BaseActivity {

    @Bind(R.id.btn_sample) Button btn_sample;


    @OnClick(R.id.btn_sample)void toSample(){
        startActivity(new Intent(this,SampleActivity.class));}

    @OnClick(R.id.btn_more)void  toMore(){
        startActivity(new Intent(this,MoreActivity.class));}

    @OnClick(R.id.btn_lambda)void toLambda(){
        startActivity(new Intent(this,LambdaActivity.class));}

    @OnClick(R.id.btn_net)void  toNet(){
        startActivity(new Intent(this,NetActivity.class));}

    @OnClick(R.id.btn_netSafe)void toNetSafe(){
        startActivity(new Intent(this,BusActivity.class));}


    @Override
    protected int setViewLayout() {return R.layout.activity_rxjatest;}

    @Override
    protected void onCreate(@Nullable  Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        btn_sample.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RxjaTest.this,SampleActivity.class));
            }
        });
    }
}
