package com.tag.app.tagnearemployee.pojomodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BusinessProResponse implements Serializable {
    @SerializedName("request")
    @Expose
    private BusinessPro request;
    @SerializedName("otpobject")
    @Expose
    private Otpobject otpobject;
    @SerializedName("status")
    @Expose
    private Integer status;

    public BusinessPro getRequest() {
        return request;
    }

    public void setRequest(BusinessPro request) {
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
