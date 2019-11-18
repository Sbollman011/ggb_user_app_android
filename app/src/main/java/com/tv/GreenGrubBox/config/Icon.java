package com.tv.GreenGrubBox.config;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 19/01/18.
 */

public class Icon {
    @SerializedName("icon")
    @Expose
    private String icon;
    @SerializedName("api-call")
    @Expose
    private String apiCall;
    @SerializedName("label")
    @Expose
    private String label;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getApiCall() {
        return apiCall;
    }

    public void setApiCall(String apiCall) {
        this.apiCall = apiCall;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
