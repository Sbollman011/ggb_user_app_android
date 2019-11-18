package com.tv.GreenGrubBox.data.modal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 19/01/18.
 */

public class LoginResponse {

    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("expiryMessage")
    @Expose
    private String expiryMessage;
    @SerializedName("rsaPublicKey")
    @Expose
    private String rsaPublicKey = "";

    public String getExpiryMessage() {
        return expiryMessage;
    }

    public void setExpiryMessage(String expiryMessage) {
        this.expiryMessage = expiryMessage;
    }

    public String getRsaPublicKey() {
        if(rsaPublicKey == null){
            return "";
        }
        return rsaPublicKey;
    }

    public void setRsaPublicKey(String rsaPublicKey) {
        this.rsaPublicKey = rsaPublicKey;
    }

    @SerializedName("data")
    @Expose
    private SignUpResponseDatum data;

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public SignUpResponseDatum getData() {
        return data;
    }

    public void setData(SignUpResponseDatum data) {
        this.data = data;
    }

}
