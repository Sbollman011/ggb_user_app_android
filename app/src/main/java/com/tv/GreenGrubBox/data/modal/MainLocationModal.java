package com.tv.GreenGrubBox.data.modal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by user on 6/2/18.
 */

public class MainLocationModal implements Serializable{

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("totalUsers")
    @Expose
    private Integer totalUsers;
    @SerializedName("type")
    @Expose
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<MainLocationDatum> data = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(Integer totalUsers) {
        this.totalUsers = totalUsers;
    }

    public List<MainLocationDatum> getData() {
        return data;
    }

    public void setData(List<MainLocationDatum> data) {
        this.data = data;
    }
}
