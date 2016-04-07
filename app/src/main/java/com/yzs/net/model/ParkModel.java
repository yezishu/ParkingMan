package com.yzs.net.model;

import com.google.gson.annotations.Expose;

import java.math.BigDecimal;
import java.util.List;

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
    @Expose
    private String provinceName;
    @Expose
    private String cityName;
    @Expose
    private String countyName;
    @Expose
    private String parkType;
    @Expose
    private String cooperationFlag;
    @Expose
    private int allSpace;
    @Expose
    private BigDecimal price;
    @Expose
    private List<ParkDetailModel> parkDetailModels;


    public static class ParkDetailModel{
        @Expose
        private String title;
        @Expose
        private String remark;
        @Expose
        private List<ParkDetailParamsModel> params;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public List<ParkDetailParamsModel> getParams() {
            return params;
        }

        public void setParams(List<ParkDetailParamsModel> params) {
            this.params = params;
        }

        @Override
        public String toString() {
            return "ParkDetailModel{" +
                    "title='" + title + '\'' +
                    ", remark='" + remark + '\'' +
                    ", params=" + params +
                    '}';
        }
    }

    public static class ParkDetailParamsModel {

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
            return " {ParkDetailParamsModel" +
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

    public String getCooperationFlag() {
        return cooperationFlag;
    }

    public void setCooperationFlag(String cooperationFlag) {
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

    public double getParkLat() {
        return parkLat;
    }

    public void setParkLat(double parkLat) {
        this.parkLat = parkLat;
    }

    public double getParkLon() {
        return parkLon;
    }

    public void setParkLon(double parkLon) {
        this.parkLon = parkLon;
    }

    public List<ParkDetailModel> getParkDetailModels() {
        return parkDetailModels;
    }

    public void setParkDetailModels(List<ParkDetailModel> parkDetailModels) {
        this.parkDetailModels = parkDetailModels;
    }

    @Override
    public String toString() {
        return "ParkModel{" +
                "'respCode'="+getRespCode()+
                "'respMsg'="+getRespMsg()+
                "parkID='" + parkID + '\'' +
                ", parkName='" + parkName + '\'' +
                ", address='" + address + '\'' +
                ", parkLon=" + parkLon +
                ", parkLat=" + parkLat +
                ", provinceName='" + provinceName + '\'' +
                ", cityName='" + cityName + '\'' +
                ", countyName='" + countyName + '\'' +
                ", parkType='" + parkType + '\'' +
                ", cooperationFlag='" + cooperationFlag + '\'' +
                ", allSpace=" + allSpace +
                ", price=" + price +
                ", parkDetailModels=" + parkDetailModels +
                '}';
    }
}
