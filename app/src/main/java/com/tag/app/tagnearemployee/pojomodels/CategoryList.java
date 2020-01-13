package com.tag.app.tagnearemployee.pojomodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoryList
{
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("tinycategorylist")
    @Expose
    private List<Tinycategorylist> tinycategorylist = null;
    @SerializedName("status")
    @Expose
    private Integer status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Tinycategorylist> getTinycategorylist() {
        return tinycategorylist;
    }

    public void setTinycategorylist(List<Tinycategorylist> tinycategorylist) {
        this.tinycategorylist = tinycategorylist;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
