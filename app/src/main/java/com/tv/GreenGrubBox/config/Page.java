package com.tv.GreenGrubBox.config;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 19/01/18.
 */

public class Page {

    @SerializedName("auto-swipe-time")
    @Expose
    private String autoSwipeTime;
    @SerializedName("image-default")
    @Expose
    private String imageDefault;
    @SerializedName("image-2000x1000")
    @Expose
    private String image2000x1000;
    @SerializedName("image-240x640")
    @Expose
    private String image240x640;
    @SerializedName("video-mode")
    @Expose
    private String videoMode;
    @SerializedName("video-default")
    @Expose
    private String videoDefault;
    @SerializedName("video-2000x1000")
    @Expose
    private String video2000x1000;
    @SerializedName("video-240x640")
    @Expose
    private String video240x640;

    public String getAutoSwipeTime() {
        return autoSwipeTime;
    }

    public void setAutoSwipeTime(String autoSwipeTime) {
        this.autoSwipeTime = autoSwipeTime;
    }

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

    public String getVideoMode() {
        return videoMode;
    }

    public void setVideoMode(String videoMode) {
        this.videoMode = videoMode;
    }

    public String getVideoDefault() {
        return videoDefault;
    }

    public void setVideoDefault(String videoDefault) {
        this.videoDefault = videoDefault;
    }

    public String getVideo2000x1000() {
        return video2000x1000;
    }

    public void setVideo2000x1000(String video2000x1000) {
        this.video2000x1000 = video2000x1000;
    }

    public String getVideo240x640() {
        return video240x640;
    }

    public void setVideo240x640(String video240x640) {
        this.video240x640 = video240x640;
    }
}
