package com.tv.GreenGrubBox.data.modal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ValidateVersionRequestModal implements Serializable {

    @SerializedName("version")
    @Expose
    private Integer version;
    @SerializedName("os")
    @Expose
    private String os = "Android";

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

}
