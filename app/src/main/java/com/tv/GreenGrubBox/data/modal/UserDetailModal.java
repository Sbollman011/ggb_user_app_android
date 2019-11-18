package com.tv.GreenGrubBox.data.modal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by tv-1 on 01/02/18.
 */

public class UserDetailModal {


    @SerializedName("status")
    @Expose
    private Integer status;

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
    private UserDetailsDatumModal data;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public UserDetailsDatumModal getData() {
        return data;
    }

    public void setData(UserDetailsDatumModal data) {
        this.data = data;
    }
}
