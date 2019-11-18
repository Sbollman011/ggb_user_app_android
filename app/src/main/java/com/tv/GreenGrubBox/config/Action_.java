package com.tv.GreenGrubBox.config;

import android.graphics.drawable.Icon;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by admin on 19/01/18.
 */

public class Action_ {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("icons")
    @Expose
    private List<Icon> icons = null;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Icon> getIcons() {
        return icons;
    }

    public void setIcons(List<Icon> icons) {
        this.icons = icons;
    }
}
