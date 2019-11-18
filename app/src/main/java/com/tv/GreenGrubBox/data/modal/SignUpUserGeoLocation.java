package com.tv.GreenGrubBox.data.modal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by tv-1 on 30/01/18.
 */

public class SignUpUserGeoLocation implements Serializable {


    @SerializedName("coordinates")
    @Expose
    private List<Long> coordinates = null;
    @SerializedName("type")
    @Expose
    private String type;

    public List<Long> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<Long> coordinates) {
        this.coordinates = coordinates;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
