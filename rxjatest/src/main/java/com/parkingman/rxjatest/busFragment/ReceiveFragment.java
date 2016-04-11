package com.parkingman.rxjatest.busFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parkingman.rxjatest.BusActivity;
import com.parkingman.rxjatest.R;
import com.parkingman.rxjatest.base.BaseActivity;
import com.parkingman.rxjatest.base.BaseFragment;
import com.parkingman.rxjatest.bus.RxBus;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.observables.ConnectableObservable;
import rx.subscriptions.CompositeSubscription;

/**
 * Des：
 * creat by Zishu.Ye on 2016/4/11  10:44
 */
public class ReceiveFragment extends BaseFragment {

    @Bind(R.id.tv_info)TextView tvInfo;
    @Bind(R.id.tv_count)TextView tvCount;

    private RxBus rxBus;

    private CompositeSubscription compositeSubscription;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_receive,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        rxBus=((BaseActivity)getActivity()).getRxBusSingleton();
        compositeSubscription=((BaseActivity)getActivity()).getCompositeSubscription();
    }

    @Override
    public void onStart() {
        super.onStart();
        ConnectableObservable<Object> tapEventEmitter=rxBus.toObserverable().publish();
        compositeSubscription.add(
                tapEventEmitter
                        .subscribe(this::showInfo)
        );

        compositeSubscription.add(
                tapEventEmitter.publish(stream->
                        stream.buffer(stream.debounce(1, TimeUnit.SECONDS)))
                    .observeOn(AndroidSchedulers.mainThread())
                    .map(taps->taps.size())
                    .subscribe(this::showCount)
        );

        compositeSubscription.add(tapEventEmitter.connect());
        //lambda 简化之前的写法
//        _subscriptions//
//                .add(tapEventEmitter.subscribe(new Action1<Object>() {
//                    @Override
//                    public void call(Object event) {
//                        if (event instanceof RxBusDemoFragment.TapEvent) {
//                            _showTapText();
//                        }
//                    }
//                }));
//        _subscriptions//
//                .add(tapEventEmitter.publish(new Func1<Observable<Object>, Observable<List<Object>>>() {
//                    @Override
//                    public Observable<List<Object>> call(Observable<Object> stream) {
//                        return stream.buffer(stream.debounce(1, TimeUnit.SECONDS));
//                    }})
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(new Action1<List<Object>>() {
//                            @Override
//                            public void call(List<Object> taps) {
//                                _showTapCount(taps.size());
//                            }
//                        }));


    }

    private void showInfo(Object event){
        Log.i(SendFragment.TAG,"recervei info ");
        if(event instanceof BusActivity.TapEvent){
            tvInfo.setVisibility(View.VISIBLE);
            tvInfo.setAlpha(1f);
            ViewCompat.animate(tvInfo).alpha(-1f).setDuration(400);

            Log.i(SendFragment.TAG,"recervei info  &take care ");
        }
    }

    private void showCount(int size){
        tvCount.setText(String.valueOf(size));
        tvCount.setVisibility(View.VISIBLE);
        tvCount.setScaleX(1f);
        tvCount.setScaleY(1f);
        ViewCompat.animate(tvCount)
                .scaleXBy(-1f)
                .scaleYBy(-1f)
                .setDuration(800)
                .setStartDelay(100);
    }


}
