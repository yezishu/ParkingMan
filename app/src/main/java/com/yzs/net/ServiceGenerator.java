package com.yzs.net;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 项目名称：ParkingMan
 * 类描述：retrofit 创建器
 * 创建人：Yiming.Gan
 * 创建时间：2016/3/31 17:12
 * 修改人：Yiming.Gan
 * 修改时间：2016/3/31 17:12
 * 修改备注：
 */
public class ServiceGenerator {
//    public static final String API_BASE_URL="http://192.168.5.202:9090/park/";
    public static final String API_BASE_URL="http://192.168.0.202:8080/app/";

    private static OkHttpClient.Builder httpClient=new OkHttpClient.Builder();

    private static Retrofit.Builder builder=new Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    public static <S> S createService(Class<S> serviceClass){

        //使用拦截器 拦截并且添加头部 请求内容方式、Token等信息
        httpClient.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original =chain.request();

                //添加请求头部
                Request.Builder requestBuidle=original.newBuilder()
                        .header("Content-Type","application/json")
                        .method(original.method(),original.body());

                Request request=requestBuidle.build();

                return chain.proceed(request);
            }
        });

        OkHttpClient client=httpClient.build();
        Retrofit retrofit=builder.client(client).build();
        return  retrofit.create(serviceClass);
    }

}
