package com.tv.GreenGrubBox.data.modal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 01/02/18.
 */

public class User {
    @SerializedName("checkOutTime")
    @Expose
    private Integer checkOutTime;
    @SerializedName("_id")
    @Expose
    private String id;

    public Integer getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(Integer checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
