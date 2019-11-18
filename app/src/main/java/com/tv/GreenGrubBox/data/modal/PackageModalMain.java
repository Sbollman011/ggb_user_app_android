package com.tv.GreenGrubBox.data.modal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by tv-1 on 30/01/18.
 */

public class PackageModalMain implements Serializable {


    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("totalUsers")
    @Expose
    private int totalUsers;
    @SerializedName("data")
    @Expose
    private List<PackageDatum> data = null;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(int totalUsers) {
        this.totalUsers = totalUsers;
    }

    public List<PackageDatum> getData() {
        return data;
    }

    public void setData(List<PackageDatum> data) {
        this.data = data;
    }
}

