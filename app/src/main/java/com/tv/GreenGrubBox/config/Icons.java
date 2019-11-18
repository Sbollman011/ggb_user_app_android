package com.tv.GreenGrubBox.config;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 19/01/18.
 */

public class Icons {
    @SerializedName("hangup")
    @Expose
    private String hangup;
    @SerializedName("switch-cam")
    @Expose
    private String switchCam;
    @SerializedName("mute")
    @Expose
    private String mute;
    @SerializedName("unmute")
    @Expose
    private String unmute;
    @SerializedName("cam-off")
    @Expose
    private String camOff;
    @SerializedName("cam-on")
    @Expose
    private String camOn;

    public String getHangup() {
        return hangup;
    }

    public void setHangup(String hangup) {
        this.hangup = hangup;
    }

    public String getSwitchCam() {
        return switchCam;
    }

    public void setSwitchCam(String switchCam) {
        this.switchCam = switchCam;
    }

    public String getMute() {
        return mute;
    }

    public void setMute(String mute) {
        this.mute = mute;
    }

    public String getUnmute() {
        return unmute;
    }

    public void setUnmute(String unmute) {
        this.unmute = unmute;
    }

    public String getCamOff() {
        return camOff;
    }

    public void setCamOff(String camOff) {
        this.camOff = camOff;
    }

    public String getCamOn() {
        return camOn;
    }

    public void setCamOn(String camOn) {
        this.camOn = camOn;
    }
}
