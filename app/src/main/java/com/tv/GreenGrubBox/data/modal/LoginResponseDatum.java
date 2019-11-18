package com.tv.GreenGrubBox.data.modal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 31/01/18.
 */

public class LoginResponseDatum {
    @SerializedName("_id")
    @Expose
    private String id;

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
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("__v")
    @Expose
    private Integer v;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("updatedAt")
    @Expose
    private long updatedAt;
    @SerializedName("createdAt")
    @Expose
    private long createdAt;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("Package")
    @Expose
    private LoginResponsePackage _package;
    @SerializedName("totalBoxes")
    @Expose
    private Integer totalBoxes;
    @SerializedName("userGeoLocation")
    @Expose
    private UserGeoLocation userGeoLocation;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getAccountType() {
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

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public LoginResponsePackage getPackage() {
        return _package;
    }

    public void setPackage(LoginResponsePackage _package) {
        this._package = _package;
    }

    public Integer getTotalBoxes() {
        return totalBoxes;
    }

    public void setTotalBoxes(Integer totalBoxes) {
        this.totalBoxes = totalBoxes;
    }

    public UserGeoLocation getUserGeoLocation() {
        return userGeoLocation;
    }

    public void setUserGeoLocation(UserGeoLocation userGeoLocation) {
        this.userGeoLocation = userGeoLocation;
    }
}
