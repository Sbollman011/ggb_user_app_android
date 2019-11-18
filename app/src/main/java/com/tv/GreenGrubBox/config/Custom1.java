package com.tv.GreenGrubBox.config;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 19/01/18.
 */

public class Custom1 {
    @SerializedName("icon")
    @Expose
    private String icon;
    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("action")
    @Expose
    private Action action;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }
}
