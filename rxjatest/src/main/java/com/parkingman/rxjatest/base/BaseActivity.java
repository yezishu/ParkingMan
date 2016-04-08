package com.parkingman.rxjatest.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.parkingman.rxjatest.Net.ParkService;
import com.parkingman.rxjatest.Net.ServiceGenerator;
import com.parkingman.rxjatest.bus.RxBus;

import butterknife.ButterKnife;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Des：
 * creat by Zishu.Ye on 2016/4/7  11:11
 */
public abstract class BaseActivity extends AppCompatActivity {

    abstract protected int setViewLayout();

    public static ParkService parkService= ServiceGenerator.createService(ParkService.class);

    private CompositeSubscription compositeSubscription;
    /**
     * 获取 订阅集合
     * @return 订阅集合
     */
    public CompositeSubscription getCompositeSubscription(){
        if (this.compositeSubscription==null)
            this.compositeSubscription=new CompositeSubscription();
        return this.compositeSubscription;
    }
    /**
     * 添加订阅 到订阅集合
     * @param subscription subscription
     */
    public void addSubscription(Subscription subscription){
        if(this.compositeSubscription==null)
            this.compositeSubscription=new CompositeSubscription();
        this.compositeSubscription.add(subscription);
    }


    private RxBus rxBus;
    public RxBus getRxBusSingleton(){
        if (rxBus==null)
            rxBus=new RxBus();
        return rxBus;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setViewLayout());
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注销订阅
        if(this.compositeSubscription!=null)
            this.compositeSubscription.unsubscribe();
    }
}
