package com.parkingman.rxjatest.busFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.parkingman.rxjatest.BusActivity;
import com.parkingman.rxjatest.R;
import com.parkingman.rxjatest.base.BaseActivity;
import com.parkingman.rxjatest.base.BaseFragment;
import com.parkingman.rxjatest.bus.RxBus;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Des：发送事件 fragment
 * creat by Zishu.Ye on 2016/4/11  10:41
 */
public class SendFragment extends BaseFragment{
    public static final String TAG="SENDFRAGEMENT";

    private RxBus rxBus;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_send,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        rxBus=((BaseActivity)getActivity()).getRxBusSingleton();
    }
    @OnClick(R.id.btn_send)
    public void onSendClick(){
        Log.i(TAG,rxBus.hasObservers()+"");
        if (rxBus.hasObservers()){
            rxBus.send(new BusActivity.TapEvent());
            Log.i(TAG,"send ed");
        }
    }
}
