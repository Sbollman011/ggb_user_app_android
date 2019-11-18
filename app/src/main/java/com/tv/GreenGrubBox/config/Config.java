package com.tv.GreenGrubBox.config;

import android.content.res.Resources;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 19/01/18.
 */

public class Config {

    @SerializedName("api-base")
    @Expose
    private String apiBase;
    @SerializedName("theme")
    @Expose
    private Resources.Theme theme;
    @SerializedName("screens")
    @Expose
    private Screens screens;

    public String getApiBase() {
        return apiBase;
    }

    public void setApiBase(String apiBase) {
        this.apiBase = apiBase;
    }

    public Resources.Theme getTheme() {
        return theme;
    }

    public void setTheme(Resources.Theme theme) {
        this.theme = theme;
    }

    public Screens getScreens() {
        return screens;
    }

    public void setScreens(Screens screens) {
        this.screens = screens;
    }
}
