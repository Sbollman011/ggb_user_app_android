package com.tv.GreenGrubBox.data.modal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.twitter.sdk.android.core.models.User;

/**
 * Created by admin on 01/02/18.
 */

public class BoxHistoryDatum {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("returnInventory")
    @Expose
    private boolean returnInventory;
    @SerializedName("boxId")
    @Expose
    private String boxId;
    @SerializedName("updatedAt")
    @Expose
    private long updatedAt;
    @SerializedName("createdAt")
    @Expose
    private long createdAt;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("__v")
    @Expose
    private Integer v;

    @SerializedName("inventoryTime")
    @Expose
    private String inventoryTime;

    public String getInventoryTime() {
        return inventoryTime;
    }

    public void setInventoryTime(String inventoryTime) {
        this.inventoryTime = inventoryTime;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    @SerializedName("vendorName")
    @Expose
    private String vendorName;

    public String getBoxIcon() {
        return boxIcon;
    }

    public void setBoxIcon(String boxIcon) {
        this.boxIcon = boxIcon;
    }

    @SerializedName("boxIcon")
    @Expose
    private String boxIcon;

    public String getUserCheckOutTime() {
        return userCheckOutTime;
    }

    public void setUserCheckOutTime(String userCheckOutTime) {
        this.userCheckOutTime = userCheckOutTime;
    }

    @SerializedName("userCheckOutTime")
    @Expose
    private String userCheckOutTime;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean getReturnInventory() {
        return returnInventory;
    }

    public void setReturnInventory(boolean returnInventory) {
        this.returnInventory = returnInventory;
    }

    public String getBoxId() {
        return boxId;
    }

    public void setBoxId(String boxId) {
        this.boxId = boxId;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }
}
