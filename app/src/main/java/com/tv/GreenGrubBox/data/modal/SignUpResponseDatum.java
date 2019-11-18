package com.tv.GreenGrubBox.data.modal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by tv-1 on 30/01/18.
 */

public class SignUpResponseDatum implements Serializable {


    public boolean isRegistered() {
        return isRegistered;
    }

    public void setRegistered(boolean registered) {
        isRegistered = registered;
    }

    @SerializedName("isRegistered")
    @Expose
    private boolean isRegistered;
    @SerializedName("accountType")
    @Expose
    private Integer accountType;

    public Integer getIsRenewRequire() {
        if (isRenewRequire==null){
            return 0;
        }
        return isRenewRequire;
    }

    public void setIsRenewRequire(Integer isRenewRequire) {
        this.isRenewRequire = isRenewRequire;
    }

    @SerializedName("isRenewRequire")
    @Expose
    private Integer isRenewRequire = 0;

    @SerializedName("__v")
    @Expose
    private Integer v;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("updatedAt")
    @Expose
    private long updatedAt;
    @SerializedName("createdAt")
    @Expose
    private long createdAt;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("userGeoLocation")
    @Expose
    private SignUpUserGeoLocation userGeoLocation;

    public Package get_package() {
        return _package;
    }

    public void set_package(Package _package) {
        this._package = _package;
    }

    @SerializedName("Package")
    @Expose
    private Package _package;

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    public Integer getAccountType() {
        if (accountType == null){
            return 2;
        }
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SignUpUserGeoLocation getUserGeoLocation() {
        return userGeoLocation;
    }

    public void setUserGeoLocation(SignUpUserGeoLocation userGeoLocation) {
        this.userGeoLocation = userGeoLocation;
    }
}
