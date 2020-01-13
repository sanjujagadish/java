package com.tag.app.tagnearemployee.pojomodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UpdateShopResponse implements Serializable
{
    @SerializedName("request")
    @Expose
    private PendingDatum request;
    @SerializedName("otpobject")
    @Expose
    private Otpobject otpobject;
    @SerializedName("status")
    @Expose
    private Integer status;

    public PendingDatum getRequest() {
        return request;
    }

    public void setRequest(PendingDatum request) {
        this.request = request;
    }

    public Otpobject getOtpobject() {
        return otpobject;
    }

    public void setOtpobject(Otpobject otpobject) {
        this.otpobject = otpobject;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
