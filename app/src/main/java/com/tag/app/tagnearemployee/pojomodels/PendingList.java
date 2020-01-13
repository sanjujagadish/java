package com.tag.app.tagnearemployee.pojomodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PendingList implements Serializable
{
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("tinyshops")
    @Expose
    private Tinyshops tinyshops;
    @SerializedName("status")
    @Expose
    private Integer status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Tinyshops getTinyshops() {
        return tinyshops;
    }

    public void setTinyshops(Tinyshops tinyshops) {
        this.tinyshops = tinyshops;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
