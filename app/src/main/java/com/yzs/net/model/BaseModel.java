package com.yzs.net.model;

import com.google.gson.annotations.Expose;

/**
 * 项目名称：ParkingMan
 * 类描述：
 * 创建人：Yiming.Gan
 * 创建时间：2016/3/31 19:11
 * 修改人：Yiming.Gan
 * 修改时间：2016/3/31 19:11
 * 修改备注：
 */
public class BaseModel {
    @Expose
    private String respCode;

    @Expose
    private String respMsg;

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }

}
