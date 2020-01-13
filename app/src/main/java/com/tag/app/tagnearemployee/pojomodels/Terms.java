package com.tag.app.tagnearemployee.pojomodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Terms {
    @SerializedName("terms")
    @Expose
    private List<TermsCondition> terms = null;
    @SerializedName("staus")
    @Expose
    private Integer staus;

    public List<TermsCondition> getTerms() {
        return terms;
    }

    public void setTerms(List<TermsCondition> terms) {
        this.terms = terms;
    }

    public Integer getStaus() {
        return staus;
    }

    public void setStaus(Integer staus) {
        this.staus = staus;
    }


}
