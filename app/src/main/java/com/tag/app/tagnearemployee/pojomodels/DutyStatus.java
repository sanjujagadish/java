package com.tag.app.tagnearemployee.pojomodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DutyStatus
{
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("shop_open")
    @Expose
    private Boolean shopOpen;
    @SerializedName("status")
    @Expose
    private Integer status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getShopOpen() {
        return shopOpen;
    }

    public void setShopOpen(Boolean shopOpen) {
        this.shopOpen = shopOpen;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    //INPUT
    @SerializedName("shop_status")
    @Expose
    private String shop_status;

    public String getShop_status()
    { return shop_status; }

    public void setShop_status(String shop_status)
    { this.shop_status = shop_status; }

}
