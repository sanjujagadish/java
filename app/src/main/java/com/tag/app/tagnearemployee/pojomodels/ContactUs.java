package com.tag.app.tagnearemployee.pojomodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ContactUs {
    @SerializedName("contactus")
    @Expose
    private List<Contact> contactus = null;
    @SerializedName("staus")
    @Expose
    private Integer staus;

    public List<Contact> getContactus() {
        return contactus;
    }

    public void setContactus(List<Contact> contactus) {
        this.contactus = contactus;
    }

    public Integer getStaus() {
        return staus;
    }

    public void setStaus(Integer staus) {
        this.staus = staus;
    }

}
