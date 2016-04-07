package com.parkingman;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.yzs.net.ParkService;
import com.yzs.net.ServiceGenerator;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Des：
 * creat by Yiming.Gan on 2016/4/1  8:48
 */
public class NetTestUpImgActivity extends AppCompatActivity {
    TextView tv;

    ParkService parkService;
    RequestBody img1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nettest);

        parkService= ServiceGenerator.createService(ParkService.class);
        tv=(TextView)findViewById(R.id.tv);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doUpload();
            }
        });

    }

    private void doUpload(){

        String path= Environment.getExternalStorageDirectory().getPath()+"/HDMS/images/appLogo.png";
        File file=new File(path);
        img1 = RequestBody.create(MediaType.parse("image/png"), file);

        RequestBody time = RequestBody.create(MediaType.parse("text/plain"), "时间");
        RequestBody address = RequestBody.create(MediaType.parse("text/plain"), "地点");
        RequestBody type = RequestBody.create(MediaType.parse("text/plain"), "类型");

        Map<String, RequestBody> map = new HashMap<>();
//        map.put("checkTime",time);
//        map.put("checkAddress",address);
//        map.put("checkType",type);

        if (file != null) {
            RequestBody fileBody = RequestBody.create(MediaType.parse("image/png"), file);
            map.put("image\"; filename=\""+file.getName()+"", fileBody);
        }



//        Call call=parkService.getUserInfo();
        new Thread(){
            @Override
            public void run() {
                super.run();
                Call call=parkService.uploadImage(img1);
                call.enqueue(new Callback() {
                    @Override
                    public void onResponse(Call call, Response response) {
                        tv.setText(response.message());
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {

                    }
                });
            }
        }.run();


    }
}
