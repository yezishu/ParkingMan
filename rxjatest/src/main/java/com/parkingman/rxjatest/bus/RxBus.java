package com.parkingman.rxjatest.bus;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Des：
 * creat by Zishu.Ye on 2016/4/8  19:18
 */
public class RxBus {
    private final Subject<Object,Object> bus=new SerializedSubject<>(PublishSubject.create());

    public void send(Object o){
        bus.onNext(o);
    }

    public Observable<Object> toObserverable(){
        return  bus;
    }

    public boolean hasObservers() {
        return bus.hasObservers();
    }
}
