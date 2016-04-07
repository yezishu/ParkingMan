package com.parkingman.rxjatest;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.parkingman.rxjatest.base.BaseActivity;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * Des：
 * creat by Zishu.Ye on 2016/4/7  11:20
 */
public class MoreActivity extends BaseActivity {

    @Override
    protected int setViewLayout() {return R.layout.activity_more;}

    @Bind(R.id.tv) TextView tv;

    final String[] mWords={"A","B","C","D","E","f"};
    final List<String> mWordsList= Arrays.asList(mWords);

    private Action1<String> textViewAc=new Action1<String>() {
        @Override
        public void call(String s) {
            tv.setText(s);
        }
    };

    private Action1<String> toastAc=new Action1<String>() {
        @Override
        public void call(String s) {
            Toast.makeText(MoreActivity.this,s,Toast.LENGTH_SHORT).show();
        }
    };

    private Func1<List<String>, Observable<String>> mOnletterFunc= new Func1<List<String>, Observable<String>>() {
        @Override
        public Observable<String> call(List<String> strings) {
            return Observable.from(strings);
        }
    };

    private Func1<String,String> mUpperLetterFunc=new Func1<String, String>() {
        @Override
        public String call(String s) {
            return s.toUpperCase();
        }
    };

    private Func2<String,String,String> mMergeStringFunc=new Func2<String, String, String>() {
        @Override
        public String call(String s, String s2) {
            return String.format("%s %s",s,s2);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //省略 action 只使用 onnext
        Observable<String> obShow=Observable.just(sayMyName());

        //先映射，再设置textview
        obShow.observeOn(AndroidSchedulers.mainThread())
                .map(mUpperLetterFunc)
                .subscribe(textViewAc);

        //单独显示数组中的每个元素
        Observable<String> obMap=Observable.from(mWords);

        //优化之后代码 直接获取数组 - 在分发 - 再合并 - 再显示Toast
        Observable.just(mWordsList)
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(mOnletterFunc)
                .reduce(mMergeStringFunc)
                .subscribe(toastAc);
    }

    // 创建字符串
    private String sayMyName() {
        return "Hello, I am your friend, Spike!";
    }

}
