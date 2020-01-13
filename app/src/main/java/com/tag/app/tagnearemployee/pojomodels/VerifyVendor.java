package com.tag.app.tagnearemployee.pojomodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class VerifyVendor implements Serializable {
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("shop_id")
    @Expose
    private Integer shopId;
    @SerializedName("countryCode")
    @Expose
    private String countryCode;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("shop_phone")
    @Expose
    private Integer shopPhone;
    @SerializedName("live_photo")
    @Expose
    private Integer livePhoto;
    @SerializedName("is_moving")
    @Expose
    private Integer isMoving;
    @SerializedName("category_id")
    @Expose
    private Integer categoryId;
    @SerializedName("tinyshop_name")
    @Expose
    private String tinyshopName;
    @SerializedName("shop_address")
    @Expose
    private String shopAddress;
    @SerializedName("shop_pincode")
    @Expose
    private String shopPincode;
    @SerializedName("timings")
    @Expose
    private String timings;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lng")
    @Expose
    private String lng;
    @SerializedName("otp")
    @Expose
    private Integer otp;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getShopPhone() {
        return shopPhone;
    }

    public void setShopPhone(Integer shopPhone) {
        this.shopPhone = shopPhone;
    }

    public Integer getLivePhoto() {
        return livePhoto;
    }

    public void setLivePhoto(Integer livePhoto) {
        this.livePhoto = livePhoto;
    }

    public Integer getIsMoving() {
        return isMoving;
    }

    public void setIsMoving(Integer isMoving) {
        this.isMoving = isMoving;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getTinyshopName() {
        return tinyshopName;
    }

    public void setTinyshopName(String tinyshopName) {
        this.tinyshopName = tinyshopName;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public String getShopPincode() {
        return shopPincode;
    }

    public void setShopPincode(String shopPincode) {
        this.shopPincode = shopPincode;
    }

    public String getTimings() {
        return timings;
    }

    public void setTimings(String timings) {
        this.timings = timings;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public Integer getOtp() {
        return otp;
    }

    public void setOtp(Integer otp) {
        this.otp = otp;
    }
}
