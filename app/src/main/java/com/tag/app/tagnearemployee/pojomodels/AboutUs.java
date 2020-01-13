package com.tag.app.tagnearemployee.pojomodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AboutUs {
    @SerializedName("aboutus")
    @Expose
    private List<About> aboutus = null;
    @SerializedName("staus")
    @Expose
    private Integer staus;

    public List<About> getAboutus() {
        return aboutus;
    }

    public void setAboutus(List<About> aboutus) {
        this.aboutus = aboutus;
    }

    public Integer getStaus() {
        return staus;
    }

    public void setStaus(Integer staus) {
        this.staus = staus;
    }

}
