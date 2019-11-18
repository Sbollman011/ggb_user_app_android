package com.tv.GreenGrubBox.config;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 19/01/18.
 */

public class Theme {
    @SerializedName("color-1")
    @Expose
    private String color1;
    @SerializedName("Color-2")
    @Expose
    private String color2;
    @SerializedName("font-1")
    @Expose
    private String font1;
    @SerializedName("font-size-1")
    @Expose
    private String fontSize1;
    @SerializedName("font-color-1")
    @Expose
    private String fontColor1;
    @SerializedName("font-bg-color-1")
    @Expose
    private String fontBgColor1;
    @SerializedName("font-style-1")
    @Expose
    private String fontStyle1;

    public String getColor1() {
        return color1;
    }

    public void setColor1(String color1) {
        this.color1 = color1;
    }

    public String getColor2() {
        return color2;
    }

    public void setColor2(String color2) {
        this.color2 = color2;
    }

    public String getFont1() {
        return font1;
    }

    public void setFont1(String font1) {
        this.font1 = font1;
    }

    public String getFontSize1() {
        return fontSize1;
    }

    public void setFontSize1(String fontSize1) {
        this.fontSize1 = fontSize1;
    }

    public String getFontColor1() {
        return fontColor1;
    }

    public void setFontColor1(String fontColor1) {
        this.fontColor1 = fontColor1;
    }

    public String getFontBgColor1() {
        return fontBgColor1;
    }

    public void setFontBgColor1(String fontBgColor1) {
        this.fontBgColor1 = fontBgColor1;
    }

    public String getFontStyle1() {
        return fontStyle1;
    }

    public void setFontStyle1(String fontStyle1) {
        this.fontStyle1 = fontStyle1;
    }
}
