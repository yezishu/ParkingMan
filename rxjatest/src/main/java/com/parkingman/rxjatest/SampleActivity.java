package com.parkingman.rxjatest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.parkingman.rxjatest.base.BaseActivity;

import butterknife.Bind;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Des：
 * creat by Zishu.Ye on 2016/4/7  11:18
 */
public class SampleActivity extends BaseActivity {

    @Bind(R.id.tv_recerive)TextView tv_recerive;

    @Override
    protected int setViewLayout() {return R.layout.activity_sample;}

    //观察事件发生
    Observable.OnSubscribe observableOnsub = new Observable.OnSubscribe<String>() {
        @Override
        public void call(Subscriber<? super String> subscriber) {
            subscriber.onNext(sayName());//发送事件
            subscriber.onCompleted();//完成事件
        }
    };


    private String sayName() {
        return "hello,Zishu.Ye!";
    }

    //订阅者
    Subscriber<String> textSubscriber = new Subscriber<String>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(String s) {
            tv_recerive.setText(s);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Observable<String> observable = Observable.create(observableOnsub);
        observable.observeOn(AndroidSchedulers.mainThread()).subscribe(textSubscriber);


    }
}
