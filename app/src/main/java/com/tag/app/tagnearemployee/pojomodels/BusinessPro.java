package com.tag.app.tagnearemployee.pojomodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BusinessPro implements Serializable {
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("shop_id")
    @Expose
    private String shopId;
    @SerializedName("countryCode")
    @Expose
    private Integer countryCode;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("shop_phone")
    @Expose
    private String shopPhone;
    @SerializedName("live_photo")
    @Expose
    private String livePhoto;
    @SerializedName("is_moving")
    @Expose
    private Integer isMoving;
    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("tinyshop_name")
    @Expose
    private String tinyshopName;
    @SerializedName("shop_name")
    @Expose
    private String shop_name;
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
    @SerializedName( "otp" )
    @Expose
    private String otp;
    @SerializedName("profileImg")
    @Expose
    private String profileImg;
    @SerializedName( "signature" )
    @Expose
    private String signature;
    @SerializedName( "ref1doc_copy" )
    @Expose
    private String ref1doc_copy;
    @SerializedName( "idproof_copy" )
    @Expose
    private String idproof_copy;
    @SerializedName( "office_address" )
    @Expose
    private String officeAddress;
    @SerializedName( "id" )
    @Expose
    private String id;
    @SerializedName( "office_email" )
    @Expose
    private String officeEmail;
    @SerializedName("office_name")
    @Expose
    private String officeName;
    @SerializedName( "verified_doc" )
    @Expose
    private String verifiedDoc;
    @SerializedName( "note" )
    @Expose
    private String note;
    @SerializedName( "password" )
    @Expose
    private String password;
    @SerializedName( "categoryName" )
    @Expose
    private String categoryName;
    @SerializedName( "open_time" )
    @Expose
    private String openTime;
    @SerializedName( "close_time" )
    @Expose
    private String closeTime;
    @SerializedName( "parcel_allowed" )
    @Expose
    private int parcelAllowed;
    @SerializedName( "inside_allocate" )
    @Expose
    private int tableAllotment;
    @SerializedName( "service_delivery_athome" )
    @Expose
    private int delivery_service;
    @SerializedName( "pay_later" )
    @Expose
    private int paylater;
    @SerializedName( "structured_type" )
    @Expose
    private String structured_type;
    @SerializedName("shopimage_1")
    @Expose
    private String shopimage1;
    @SerializedName("shopimage_2")
    @Expose
    private String shopimage2;

    public String getOtp() { return otp; }

    public void setOtp(String otp) { this.otp = otp; }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) { this.shopId = shopId; }

    public Integer getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(Integer countryCode) { this.countryCode = countryCode; }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getShopPhone() {
        return shopPhone;
    }

    public void setShopPhone(String shopPhone) { this.shopPhone = shopPhone; }

    public String getLivePhoto() {
        return livePhoto;
    }

    public void setLivePhoto(String livePhoto) {
        this.livePhoto = livePhoto;
    }

    public Integer getIsMoving() {
        return isMoving;
    }

    public void setIsMoving(Integer isMoving) { this.isMoving = isMoving; }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
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

    public void setLng(String lng) { this.lng = lng; }

    public String getProfileImg() { return profileImg; }

    public void setProfileImg(String profileImg) { this.profileImg = profileImg; }

    public void setSignature(String signature) { this.signature = signature; }

    public String getSignature() { return signature; }

    public String getRef1doc_copy() { return ref1doc_copy; }

    public void setRef1doc_copy(String ref_doc1) { this.ref1doc_copy = ref_doc1; }

    public String getIdproof_copy() { return idproof_copy; }

    public void setIdproof_copy(String idproof_copy) { this.idproof_copy = idproof_copy; }

    public String getOfficeAddress() { return officeAddress; }

    public void setOfficeAddress(String officeAddress) { this.officeAddress = officeAddress; }

    public String getId() { return id; }

    public void setId(String id) {
        this.id = id;
    }

    public String getOfficeEmail() {
        return officeEmail;
    }

    public void setOfficeEmail(String officeEmail) {
        this.officeEmail = officeEmail;
    }

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) { this.officeName = officeName; }

    public String getVerifiedDoc() {
        return verifiedDoc;
    }

    public void setVerifiedDoc(String verifiedDoc) {
        this.verifiedDoc = verifiedDoc;
    }

    public String getNote() { return note; }

    public void setNote(String note) { this.note = note; }

    public String getShop_name() { return shop_name; }

    public void setShop_name(String shop_name) { this.shop_name = shop_name; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }

    public String getCategoryName() { return categoryName; }

    public String getOpenTime() { return openTime; }

    public void setOpenTime(String openTime) { this.openTime = openTime; }

    public String getCloseTime() { return closeTime; }

    public void setCloseTime(String closeTime) { this.closeTime = closeTime; }

    public void setParcelAllowed(int parcelAllowed) { this.parcelAllowed = parcelAllowed; }

    public int getParcelAllowed() {
        return parcelAllowed;
    }

    public void setTableAllotment(int tableAllotment) { this.tableAllotment = tableAllotment; }

    public int getTableAllotment() { return tableAllotment; }

    public int getDelivery_service() { return delivery_service; }

    public void setDelivery_service(int delivery_service) { this.delivery_service = delivery_service; }

    public int getPaylater() { return paylater; }

    public void setPaylater(int paylater) { this.paylater = paylater; }

    public String getStructured_type() { return structured_type; }

    public void setStructured_type(String structured_type) { this.structured_type = structured_type; }

    public String getShopimage1() {
        return shopimage1;
    }

    public void setShopimage1(String shopimage1) {
        this.shopimage1 = shopimage1;
    }

    public String getShopimage2() {
        return shopimage2;
    }

    public void setShopimage2(String shopimage2) {
        this.shopimage2 = shopimage2;
    }

    //RESPONSE
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private Integer status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @SerializedName("shop_city")
    @Expose
    private String shopCity;
    @SerializedName("shop_state")
    @Expose
    private String shopState;
    @SerializedName("shop_country")
    @Expose
    private String shopCountry;

    public String getShopCity() {
        return shopCity;
    }

    public void setShopCity(String shopCity) {
        this.shopCity = shopCity;
    }

    public String getShopState() {
        return shopState;
    }

    public void setShopState(String shopState) {
        this.shopState = shopState;
    }

    public String getShopCountry() {
        return shopCountry;
    }

    public void setShopCountry(String shopCountry) {
        this.shopCountry = shopCountry;
    }

}
