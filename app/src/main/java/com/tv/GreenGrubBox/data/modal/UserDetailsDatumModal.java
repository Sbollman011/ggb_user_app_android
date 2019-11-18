package com.tv.GreenGrubBox.data.modal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by tv-1 on 01/02/18.
 */

public class UserDetailsDatumModal {


    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("accountType")
    @Expose
    private Integer accountType;
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
    @SerializedName("totalBoxes")
    @Expose
    private Integer totalBoxes;
    @SerializedName("userGeoLocation")
    @Expose
    private UserGeoLocation userGeoLocation;
    @SerializedName("__v")
    @Expose
    private Integer v;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @SerializedName("name")
    @Expose
    private String name;

    public String getNextRenewOn() {
        return nextRenewOn;
    }

    public void setNextRenewOn(String nextRenewOn) {
        this.nextRenewOn = nextRenewOn;
    }

    @SerializedName("nextRenewOn")
    @Expose
    private String nextRenewOn;


    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    @SerializedName("webUrl")
    @Expose
    private String webUrl;

    public String getTermsAndCondition() {
        return termsAndCondition;
    }

    public void setTermsAndCondition(String termsAndCondition) {
        this.termsAndCondition = termsAndCondition;
    }

    @SerializedName("termsAndCondition")
    @Expose
    private String termsAndCondition;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @SerializedName("number")
    @Expose
    private String number;

    public String getGgb_email() {
        return ggb_email;
    }

    public void setGgb_email(String ggb_email) {
        this.ggb_email = ggb_email;
    }

    @SerializedName("ggb_email")
    @Expose
    private String ggb_email;

    public int getCurrentBoxes() {
        return currentBoxes;
    }

    public void setCurrentBoxes(int currentBoxes) {
        this.currentBoxes = currentBoxes;
    }

    @SerializedName("currentBoxes")
    @Expose
    private int currentBoxes;


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

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

}
