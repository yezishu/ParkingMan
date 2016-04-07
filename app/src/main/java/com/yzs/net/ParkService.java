package com.yzs.net;

import com.yzs.net.command.ParkCommand;
import com.yzs.net.model.ParkModel;
import com.yzs.net.model.TestModel;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * 项目名称：ParkingMan
 * 类描述：网络请求 接口类
 * 创建人：Yiming.Gan
 * 创建时间：2016/3/31 17:13
 * 修改人：Yiming.Gan
 * 修改时间：2016/3/31 17:13
 * 修改备注：
 */
public interface ParkService {

    @POST("parkManager")
    Call<ParkModel> alterParkInfo(@Body ParkCommand command);

    @POST("index")
    Call<TestModel> getUserInfo();

    @Headers({"Cache-Control:Keep-Alive",
            "Content-Type:multipart/form-data"})
    @Multipart
    @POST("mc/updateHeadImage")
    Call<String> uploadImage(
            @Part("file\"; fileName=\"image.png\"") RequestBody img);

}
