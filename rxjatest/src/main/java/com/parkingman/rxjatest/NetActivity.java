package com.parkingman.rxjatest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.parkingman.rxjatest.Net.ParkCommand;
import com.parkingman.rxjatest.Net.ParkModel;
import com.parkingman.rxjatest.base.BaseActivity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Des：
 * creat by Zishu.Ye on 2016/4/7  11:22
 */
public class NetActivity extends BaseActivity {

    @Bind(R.id.tv) TextView tv;

    @Override
    protected int setViewLayout() {
        return R.layout.activity_net;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Subscription s = Observable.just(getCommand())
                .flatMap(parkService::alterPark)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(ParkModel::toString)
                .subscribe(tv::setText, this::loadError);
        addSubscription(s);

    }


    private void loadError(Throwable throwable) {
        throwable.printStackTrace();
    }


    private ParkCommand getCommand() {
        ParkCommand command = new ParkCommand();
        command.setParkID("45O9I750G9");
        command.setParkName("AAAA测试停车场");
        command.setAddress("湖滨南路66号");
        command.setParkLat(2.0);
        command.setParkLon(2.0);
        command.setProvinceName("福建省");
        command.setCityName("厦门市");
        command.setCountyName("思明区");
        command.setParkType("路边");
        command.setCooperationFlag(false);
        command.setAllSpace(0);
        command.setPrice(new BigDecimal(2.0));
        command.setRemark("ceshi");

        ParkCommand.ParkDetailCommand detailModel = new ParkCommand.ParkDetailCommand();
        detailModel.setTitle("白天 7:00-18:00");


        ParkCommand.ParkDetailParamsCommand params = new ParkCommand.ParkDetailParamsCommand();
        params.setParam("323");
        params.setParamValue("ceshi");

        List<ParkCommand.ParkDetailParamsCommand> paramses = new ArrayList<>();
        List<ParkCommand.ParkDetailCommand> parkDetailCommands = new ArrayList<>();
        paramses.add(params);
        detailModel.setParams(paramses);

        parkDetailCommands.add(detailModel);
        command.setParkDetailCommands(parkDetailCommands);
        return command;
    }


}
