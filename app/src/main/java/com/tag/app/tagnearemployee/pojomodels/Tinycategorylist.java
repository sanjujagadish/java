package com.tag.app.tagnearemployee.pojomodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Tinycategorylist {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("tinycat_name")
    @Expose
    private String tinycatName;
    @SerializedName("slug_name")
    @Expose
    private String slugName;
    @SerializedName("tinycat_image")
    @Expose
    private String tinycatImage;
    @SerializedName("tags")
    @Expose
    private String tags;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("created_at")
    @Expose
    private Object createdAt;
    @SerializedName("updated_at")
    @Expose
    private Object updatedAt;
    @SerializedName("vendorservice_id")
    @Expose
    private Integer vendorserviceId;
    @SerializedName("is_order_type")
    @Expose
    private Integer isOrderType;
    @SerializedName("unit_types")
    @Expose
    private String unitTypes;

    public String getId() { return id; }

    public void setId(String id) {
        this.id = id;
    }

    public String getTinycatName() {
        return tinycatName;
    }

    public void setTinycatName(String tinycatName) {
        this.tinycatName = tinycatName;
    }

    public String getSlugName() {
        return slugName;
    }

    public void setSlugName(String slugName) {
        this.slugName = slugName;
    }

    public String getTinycatImage() {
        return tinycatImage;
    }

    public void setTinycatImage(String tinycatImage) {
        this.tinycatImage = tinycatImage;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Object getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Object createdAt) {
        this.createdAt = createdAt;
    }

    public Object getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Object updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getVendorserviceId() {
        return vendorserviceId;
    }

    public void setVendorserviceId(Integer vendorserviceId) {
        this.vendorserviceId = vendorserviceId;
    }

    public Integer getIsOrderType() {
        return isOrderType;
    }

    public void setIsOrderType(Integer isOrderType) {
        this.isOrderType = isOrderType;
    }

    public String getUnitTypes() {
        return unitTypes;
    }

    public void setUnitTypes(String unitTypes) {
        this.unitTypes = unitTypes;
    }

}
