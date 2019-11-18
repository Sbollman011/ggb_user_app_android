package com.tv.GreenGrubBox.config;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 19/01/18.
 */

public class Custom2 {

    @SerializedName("icon")
    @Expose
    private String icon;
    @SerializedName("icon-open")
    @Expose
    private String iconOpen;
    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("action")
    @Expose
    private Action_ action;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIconOpen() {
        return iconOpen;
    }

    public void setIconOpen(String iconOpen) {
        this.iconOpen = iconOpen;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Action_ getAction() {
        return action;
    }

    public void setAction(Action_ action) {
        this.action = action;
    }
}
