package com.tv.GreenGrubBox.config;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 19/01/18.
 */

public class Calling {
    @SerializedName("calling-text")
    @Expose
    private String callingText;
    @SerializedName("hangup-text")
    @Expose
    private String hangupText;

    public String getCallingText() {
        return callingText;
    }

    public void setCallingText(String callingText) {
        this.callingText = callingText;
    }

    public String getHangupText() {
        return hangupText;
    }

    public void setHangupText(String hangupText) {
        this.hangupText = hangupText;
    }
}
