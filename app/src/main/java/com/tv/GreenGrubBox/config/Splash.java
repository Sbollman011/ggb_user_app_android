package com.tv.GreenGrubBox.config;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 19/01/18.
 */

public class Splash {

    @SerializedName("image-default")
    @Expose
    private String imageDefault;
    @SerializedName("image-2000x1000")
    @Expose
    private String image2000x1000;
    @SerializedName("image-240x640")
    @Expose
    private String image240x640;
    @SerializedName("wait-time")
    @Expose
    private String waitTime;

    public String getImageDefault() {
        return imageDefault;
    }

    public void setImageDefault(String imageDefault) {
        this.imageDefault = imageDefault;
    }

    public String getImage2000x1000() {
        return image2000x1000;
    }

    public void setImage2000x1000(String image2000x1000) {
        this.image2000x1000 = image2000x1000;
    }

    public String getImage240x640() {
        return image240x640;
    }

    public void setImage240x640(String image240x640) {
        this.image240x640 = image240x640;
    }

    public String getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(String waitTime) {
        this.waitTime = waitTime;
    }
}
