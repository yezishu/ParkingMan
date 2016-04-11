package com.parkingman.rxjatest.Net;

import com.google.gson.annotations.Expose;

import java.math.BigDecimal;
import java.util.List;

/**
 * Des：
 * creat by Zishu.Ye on 2016/4/8  8:58
 */
public class ParkCommand {

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
    @Expose
    private String provinceName;
    @Expose
    private String cityName;
    @Expose
    private String countyName;
    @Expose
    private String parkType;
    @Expose
    private boolean cooperationFlag;
    @Expose
    private int allSpace;
    @Expose
    private BigDecimal price;
    @Expose
    private String remark;
    @Expose
    private List<ParkDetailCommand> parkDetailCommands;


    public static class ParkDetailCommand{
        @Expose
        private String title;

        @Expose
        private List<ParkDetailParamsCommand> params;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }


        public List<ParkDetailParamsCommand> getParams() {
            return params;
        }

        public void setParams(List<ParkDetailParamsCommand> params) {
            this.params = params;
        }

        @Override
        public String toString() {

            return "ParkDetailModel{" +
                    "title='" + title + '\'' +
                    ", params=" + params +
                    '}';
        }
    }

    public static class ParkDetailParamsCommand{

        @Expose
        private String param;
        @Expose
        private String paramValue;

        public String getParam() {
            return param;
        }

        public void setParam(String param) {
            this.param = param;
        }

        public String getParamValue() {
            return paramValue;
        }

        public void setParamValue(String paramValue) {
            this.paramValue = paramValue;
        }

        @Override
        public String toString() {
            return "ParkDetailParamsCommand{" +
                    "param='" + param + '\'' +
                    ", paramValue='" + paramValue + '\'' +
                    '}';
        }
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

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public String getParkType() {
        return parkType;
    }

    public void setParkType(String parkType) {
        this.parkType = parkType;
    }

    public boolean isCooperationFlag() {
        return cooperationFlag;
    }

    public void setCooperationFlag(boolean cooperationFlag) {
        this.cooperationFlag = cooperationFlag;
    }

    public int getAllSpace() {
        return allSpace;
    }

    public void setAllSpace(int allSpace) {
        this.allSpace = allSpace;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<ParkDetailCommand> getParkDetailCommands() {
        return parkDetailCommands;
    }

    public void setParkDetailCommands(List<ParkDetailCommand> parkDetailCommands) {
        this.parkDetailCommands = parkDetailCommands;
    }



}
