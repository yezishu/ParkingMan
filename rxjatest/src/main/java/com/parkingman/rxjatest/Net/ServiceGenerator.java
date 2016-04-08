package com.parkingman.rxjatest.Net;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Des：
 * creat by Zishu.Ye on 2016/4/8  8:47
 */
public class ServiceGenerator {
    public static final String API_BASE_URL="http://192.168.5.202:9090/park/";

    private static OkHttpClient.Builder httpClient=new OkHttpClient.Builder(  );

    private static Retrofit.Builder builder=new Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create());


    public static <S> S createService(Class<S> serviceClass){
        OkHttpClient client=httpClient.build();
        Retrofit retrofit=builder.client(client).build();
        return retrofit.create(serviceClass);
    }
}
