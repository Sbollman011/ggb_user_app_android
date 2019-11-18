package com.tv.GreenGrubBox.data.modal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by admin on 31/01/18.
 */

public class CheckBoxRequest implements Serializable {

    @SerializedName("boxId")
    @Expose
    private String boxId;

    @SerializedName("userId")
    @Expose
    private String userId;

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    @SerializedName("timezone")
    @Expose
    private String timezone;

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    @SerializedName("long")
    @Expose
    private String lng;
    @SerializedName("lat")
    @Expose
    private String lat;

    public String getBoxId() {
        return boxId;
    }

    public void setBoxId(String boxId) {
        this.boxId = boxId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
