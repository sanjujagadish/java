package com.tag.app.tagnearemployee.pojomodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Privacy {
    @SerializedName("h1")
    @Expose
    private String h1;
    @SerializedName("p")
    @Expose
    private String p;

    public String getH1() {
        return h1;
    }

    public void setH1(String h1) {
        this.h1 = h1;
    }

    public String getP() {
        return p;
    }

    public void setP(String p) {
        this.p = p;
    }

}
