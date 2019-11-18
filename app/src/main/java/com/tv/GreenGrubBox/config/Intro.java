package com.tv.GreenGrubBox.config;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by admin on 19/01/18.
 */

public class Intro {
    @SerializedName("skip-intro-text")
    @Expose
    private String skipIntroText;
    @SerializedName("pages")
    @Expose
    private List<Page> pages = null;

    public String getSkipIntroText() {
        return skipIntroText;
    }

    public void setSkipIntroText(String skipIntroText) {
        this.skipIntroText = skipIntroText;
    }

    public List<Page> getPages() {
        return pages;
    }

    public void setPages(List<Page> pages) {
        this.pages = pages;
    }
}
