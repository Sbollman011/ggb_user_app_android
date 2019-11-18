package com.tv.GreenGrubBox.config;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 19/01/18.
 */

 public class Videochat {

    @SerializedName("hospots")
    @Expose
    private String hospots;
    @SerializedName("icons")
    @Expose
    private Icons icons;
    @SerializedName("custom-1")
    @Expose
    private Custom1 custom1;
    @SerializedName("custom-2")
    @Expose
    private Custom2 custom2;

    public String getHospots() {
        return hospots;
    }

    public void setHospots(String hospots) {
        this.hospots = hospots;
    }

    public Icons getIcons() {
        return icons;
    }

    public void setIcons(Icons icons) {
        this.icons = icons;
    }

    public Custom1 getCustom1() {
        return custom1;
    }

    public void setCustom1(Custom1 custom1) {
        this.custom1 = custom1;
    }

    public Custom2 getCustom2() {
        return custom2;
    }

    public void setCustom2(Custom2 custom2) {
        this.custom2 = custom2;
    }
}
