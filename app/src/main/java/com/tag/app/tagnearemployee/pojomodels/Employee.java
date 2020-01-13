package com.tag.app.tagnearemployee.pojomodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Employee implements Serializable
{
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("shop_name")
    @Expose
    private String shopName;
    @SerializedName("countryCode")
    @Expose
    private String countryCode;
    @SerializedName("profileImg")
    @Expose
    private String profileImg;
    @SerializedName("otp")
    @Expose
    private String otp;
    @SerializedName("permission")
    @Expose
    private String permission;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName( "latitude" )
    @Expose
    private String latitude;
    @SerializedName( "longitude" )
    @Expose
    private String longitude;

    public Employee() { }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String  getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) { this.profileImg = profileImg; }

    public String getPermission() { return permission; }

    public void setPermission(String permission) { this.permission = permission; }

    public void setLatitude(String latitude) { this.latitude = latitude; }

    public String getLatitude() {
        return latitude;
    }

    public void setLongitude(String longitude) { this.longitude = longitude; }

    public String getLongitude() { return longitude; }

    public String getCity() {
        return city;
    }

    public void setCity(String city) { this.city=city; }

    public String getOtp() { return otp; }

    public void setOtp(String otp) { this.otp = otp; }

    //LOGIN CONSTRUCTOR
    public Employee(String phone, String password, String countrycode)
    {   this.phone=phone;
        this.password=password;
        this.countryCode=countryCode; }

    //GET PASSWORD FROM RESET PASSWORD AFTER RESET
    public Employee(String password)
    { this.password=getPassword(); }

    //LOGIN RESPONSE
    @SerializedName("isUser")
    @Expose
    private Boolean isUser;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("structured_type")
    @Expose
    private Integer structuredType;

    public Boolean getIsUser() {
        return isUser;
    }

    public void setIsUser(Boolean isUser) { this.isUser = isUser; }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getStructuredType() {
        return structuredType;
    }

    public void setStructuredType(Integer structuredType) {
        this.structuredType = structuredType;
    }

    @SerializedName("user")
    @Expose
    private RegisterData user;

    public RegisterData getUser() {
        return user;
    }

    public void setUser(RegisterData user) {
        this.user = user;
    }

    //IMAGE UPLOAD RESPONSE

    @SerializedName("imageURL")
    @Expose
    private String imageURL;

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

}
