package com.tv.GreenGrubBox.data.modal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by user on 6/2/18.
 */

public class MainLocationDatum implements Serializable {

    @SerializedName("_id")
    @Expose
    private String id;

    public String getWebLink() {
        return webLink;
    }

    public void setWebLink(String webLink) {
        this.webLink = webLink;
    }

    @SerializedName("webLink")
    @Expose
    private String webLink;


    public String getYelpLink() {
        return yelpLink;
    }

    public void setYelpLink(String yelpLink) {
        this.yelpLink = yelpLink;
    }

    @SerializedName("yelpLink")
    @Expose
    private String yelpLink;

    @SerializedName("mobile")
    @Expose
    private long mobile;

    @SerializedName("name")
    @Expose
    private String businessName;

    @SerializedName("miles")
    @Expose
    private String miles;

    public String getMiles() {
        return miles;
    }

    public void setMiles(String miles) {
        this.miles = miles;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @SerializedName("category")
    @Expose
    private String category;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("email")
    @Expose
    private String email;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @SerializedName("type")
    @Expose
    private int type;

    public float getRating() {
        return rating;

    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    @SerializedName("rating")
    @Expose
    private float rating = 0;
    @SerializedName("updatedAt")
    @Expose
    private long updatedAt;

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    @SerializedName("images")
    @Expose
    private ArrayList<String> images;
    @SerializedName("createdAt")
    @Expose
    private long createdAt;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("isProfileCompleted")
    @Expose
    private Boolean isProfileCompleted;
    @SerializedName("address")
    @Expose
    private Address address;
    @SerializedName("geo")
    @Expose
    private VendorGeo vendorGeo;
    @SerializedName("vendorIcon")
    @Expose
    private String vendorIcon;
    @SerializedName("__v")
    @Expose
    private Integer v;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getMobile() {
        return mobile;
    }

    public void setMobile(long mobile) {
        this.mobile = mobile;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
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

    public Boolean getIsProfileCompleted() {
        return isProfileCompleted;
    }

    public void setIsProfileCompleted(Boolean isProfileCompleted) {
        this.isProfileCompleted = isProfileCompleted;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public VendorGeo getVendorGeo() {
        return vendorGeo;
    }

    public void setVendorGeo(VendorGeo vendorGeo) {
        this.vendorGeo = vendorGeo;
    }

    public String getVendorIcon() {
        return vendorIcon;
    }

    public void setVendorIcon(String vendorIcon) {
        this.vendorIcon = vendorIcon;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }
}
