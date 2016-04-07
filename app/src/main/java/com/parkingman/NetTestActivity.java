package com.parkingman;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.yzs.net.ParkService;
import com.yzs.net.ServiceGenerator;
import com.yzs.net.command.ParkCommand;
import com.yzs.net.model.ParkModel;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NetTestActivity extends AppCompatActivity{
    TextView tv;
    ParkService parkService;
    String resString="请求失败";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nettest);

        parkService= ServiceGenerator.createService(ParkService.class);
        tv=(TextView)findViewById(R.id.tv);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doClient();
            }
        });
    }

    private void doClient(){
        ParkCommand command=getCommand();
        Call call=parkService.alterParkInfo(command);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                ParkModel model=(ParkModel)response.body();

                if (model == null) {
                    ResponseBody responseBody = response.errorBody();
                    if (responseBody != null) {
                        try {
                            resString="responseBody = " + responseBody.string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        resString="responseBody = null";
                    }
                } else {
                    resString=model.toString();
                }
                tv.setText(resString);
            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });
    }

    private ParkCommand getCommand(){
        ParkCommand command=new ParkCommand();
        command.setParkID("88L3YW90EG");
        command.setParkName("AAAA测试停车场");
        command.setAddress("湖滨南路66号");
        command.setParkLat(2.0);
        command.setParkLon(2.0);
        command.setProvinceName("福建省");
        command.setCityName("厦门市");
        command.setCountyName("思明区");
        command.setParkType("路边");
        command.setCooperationFlag(false);
        command.setAllSpace(0);
        command.setPrice(new BigDecimal(2.0));

        ParkCommand.ParkDetailCommand detailModel=new ParkCommand.ParkDetailCommand();
        detailModel.setTitle("白天 7:00-18:00");
        detailModel.setRemark("ceshi");

        ParkCommand.ParkDetailParamsCommand params=new ParkCommand.ParkDetailParamsCommand();
        params.setParam("323");
        params.setParamValue("ceshi");

        List<ParkCommand.ParkDetailParamsCommand> paramses=new ArrayList<>();
        List<ParkCommand.ParkDetailCommand> parkDetailCommands=new ArrayList<>();
        paramses.add(params);
        detailModel.setParams(paramses);

        parkDetailCommands.add(detailModel);
        command.setParkDetailCommands(parkDetailCommands);
        return command;
    }
}
