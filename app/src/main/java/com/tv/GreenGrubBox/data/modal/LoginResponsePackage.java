package com.tv.GreenGrubBox.data.modal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 31/01/18.
 */

public class LoginResponsePackage {


    @SerializedName("expiraryDate")
    @Expose
    private long expiraryDate;
    @SerializedName("packageId")
    @Expose
    private String packageId;

    public long getExpiraryDate() {
        return expiraryDate;
    }

    public void setExpiraryDate(long expiraryDate) {
        this.expiraryDate = expiraryDate;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }
}
