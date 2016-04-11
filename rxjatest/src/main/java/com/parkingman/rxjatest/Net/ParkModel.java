package com.parkingman.rxjatest.Net;

import com.google.gson.annotations.Expose;

/**
 * 项目名称：ParkingMan
 * 类描述：
 * 创建人：Yiming.Gan
 * 创建时间：2016/3/31 17:17
 * 修改人：Yiming.Gan
 * 修改时间：2016/3/31 17:17
 * 修改备注：
 */
public class ParkModel extends BaseModel{
    @Expose
    private String parkID;
    @Expose
    private String parkName;
    @Expose
    private String address;
    @Expose
    private double parkLon;
    @Expose
    private double parkLat;


    @Override
    public String toString() {
        return "ParkModel{" +
                "parkID='" + parkID + '\'' +
                ", parkName='" + parkName + '\'' +
                ", address='" + address + '\'' +
                ", parkLon=" + parkLon +
                ", parkLat=" + parkLat +
                '}';
    }

    public String getParkID() {
        return parkID;
    }

    public void setParkID(String parkID) {
        this.parkID = parkID;
    }

    public String getParkName() {
        return parkName;
    }

    public void setParkName(String parkName) {
        this.parkName = parkName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getParkLon() {
        return parkLon;
    }

    public void setParkLon(double parkLon) {
        this.parkLon = parkLon;
    }

    public double getParkLat() {
        return parkLat;
    }

    public void setParkLat(double parkLat) {
        this.parkLat = parkLat;
    }
}
