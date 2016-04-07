package com.parkingman.rxjatest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;
import android.widget.Toast;

import com.parkingman.rxjatest.base.BaseActivity;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Des：
 * creat by Zishu.Ye on 2016/4/7  11:21
 */
public class LambdaActivity extends BaseActivity {

    @Bind(R.id.tv) TextView tv;

    final String[] myWords={"A","b","c","d","e"};
    final List<String> myWordLists= Arrays.asList(myWords);

    @Override
    protected int setViewLayout() {return R.layout.activity_lambda;}
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 添加字符串, 省略Action的其他方法, 只使用一个onNext.
        Observable<String> obShow=Observable.just(sayMyName());

        // 先映射, 再设置TextView
        obShow.observeOn(AndroidSchedulers.mainThread())
                .map(String::toUpperCase)
                .subscribe(tv::setText);

        Observable<String> obMap=Observable.from(myWords);

        obMap.observeOn(AndroidSchedulers.mainThread())
                .map(String::toUpperCase)
                .subscribe(this::showToast);

        Observable.just(myWordLists)
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(Observable::from)
                .reduce(this::mergeString)
                .subscribe(this::showToast);

    }

    // 创建字符串
    private String sayMyName() {
        return "Hello, I am your friend, Spike!";
    }
    // 显示Toast
    private void showToast(String s) {
        Toast.makeText(LambdaActivity.this, s, Toast.LENGTH_SHORT).show();
    }

    // 合并字符串
    private String mergeString(String s1, String s2) {
        return String.format("%s %s", s1, s2);
    }
}
