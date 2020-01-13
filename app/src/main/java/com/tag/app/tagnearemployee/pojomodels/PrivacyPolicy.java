package com.tag.app.tagnearemployee.pojomodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PrivacyPolicy {
    @SerializedName("privacy")
    @Expose
    private List<Privacy> privacy = null;
    @SerializedName("staus")
    @Expose
    private Integer staus;

    public List<Privacy> getPrivacy() {
        return privacy;
    }

    public void setPrivacy(List<Privacy> privacy) {
        this.privacy = privacy;
    }

    public Integer getStaus() {
        return staus;
    }

    public void setStaus(Integer staus) {
        this.staus = staus;
    }

}
