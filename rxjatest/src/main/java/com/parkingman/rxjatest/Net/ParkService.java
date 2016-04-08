package com.parkingman.rxjatest.Net;

import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Des：
 * creat by Zishu.Ye on 2016/4/8  8:55
 */
public interface ParkService {

    @POST("parkManager")
    @Headers("Content-Type:application/json")
    Observable<ParkModel> alterPark(@Body ParkCommand command);
}
