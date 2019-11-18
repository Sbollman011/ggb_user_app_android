package com.tv.GreenGrubBox.data.modal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by tv-1 on 31/01/18.
 */

public class CheckOutBoxResponse implements Serializable {


    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("message")
    @Expose
    private String message;


    public Integer getIsRenewRequire() {
        if (isRenewRequire==null){
            return 0;
        }
        return isRenewRequire;
    }

    public void setIsRenewRequire(Integer isRenewRequire) {
        this.isRenewRequire = isRenewRequire;
    }

    @SerializedName("isRenewRequire")
    @Expose
    private Integer isRenewRequire = 0;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
