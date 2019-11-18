package com.tv.GreenGrubBox.data.modal;

/**
 * Created by admin on 18/01/18.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DeviceInfo implements Serializable{

    @SerializedName("os")
    @Expose
    private String os;
    @SerializedName("osVer")
    @Expose
    private String osVer;
    @SerializedName("vendorId")
    @Expose
    private String vendorId;
    @SerializedName("devModel")
    @Expose
    private String devModel;
    @SerializedName("resWidth")
    @Expose
    private Integer resWidth;
    @SerializedName("resHeight")
    @Expose
    private Integer resHeight;
    @SerializedName("appVer")
    @Expose
    private String appVer;

    public String getDevice_token() {
        return device_token;
    }

    public void setDevice_token(String device_token) {
        this.device_token = device_token;
    }

    @SerializedName("device_token")
    @Expose
    private String device_token;
    @SerializedName("battery")
    @Expose
    private Integer battery;

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getOsVer() {
        return osVer;
    }

    public void setOsVer(String osVer) {
        this.osVer = osVer;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getDevModel() {
        return devModel;
    }

    public void setDevModel(String devModel) {
        this.devModel = devModel;
    }

    public Integer getResWidth() {
        return resWidth;
    }

    public void setResWidth(Integer resWidth) {
        this.resWidth = resWidth;
    }

    public Integer getResHeight() {
        return resHeight;
    }

    public void setResHeight(Integer resHeight) {
        this.resHeight = resHeight;
    }

    public String getAppVer() {
        return appVer;
    }

    public void setAppVer(String appVer) {
        this.appVer = appVer;
    }

    public Integer getBattery() {
        return battery;
    }

    public void setBattery(Integer battery) {
        this.battery = battery;
    }

}
