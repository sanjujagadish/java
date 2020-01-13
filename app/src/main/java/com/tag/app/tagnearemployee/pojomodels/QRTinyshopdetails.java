package com.tag.app.tagnearemployee.pojomodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QRTinyshopdetails {
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("profileImg")
    @Expose
    private String profileImg;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("office_phone")
    @Expose
    private Integer officePhone;
    @SerializedName("office_address")
    @Expose
    private String officeAddress;
    @SerializedName("office_name")
    @Expose
    private String officeName;
    @SerializedName("office_email")
    @Expose
    private String officeEmail;
    @SerializedName("photo")
    @Expose
    private String photo;
    @SerializedName("doc_copy")
    @Expose
    private String docCopy;
    @SerializedName("service_type")
    @Expose
    private Integer serviceType;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("proof_expire")
    @Expose
    private Object proofExpire;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("owner_user_id")
    @Expose
    private Integer ownerUserId;
    @SerializedName("logo")
    @Expose
    private String logo;
    @SerializedName("verified_doc")
    @Expose
    private Integer verifiedDoc;
    @SerializedName("vendor_id")
    @Expose
    private Integer vendorId;
    @SerializedName("shop_phone")
    @Expose
    private String shopPhone;
    @SerializedName("live_photo")
    @Expose
    private String livePhoto;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lng")
    @Expose
    private String lng;
    @SerializedName("is_moving")
    @Expose
    private Integer isMoving;
    @SerializedName("shop_open")
    @Expose
    private Integer shopOpen;
    @SerializedName("category_id")
    @Expose
    private Integer categoryId;
    @SerializedName("tinyrating")
    @Expose
    private Integer tinyrating;
    @SerializedName("tinyshop_name")
    @Expose
    private String tinyshopName;
    @SerializedName("shop_address")
    @Expose
    private String shopAddress;
    @SerializedName("shop_pincode")
    @Expose
    private Integer shopPincode;
    @SerializedName("shop_city")
    @Expose
    private String shopCity;
    @SerializedName("shop_state")
    @Expose
    private String shopState;
    @SerializedName("shop_country")
    @Expose
    private String shopCountry;
    @SerializedName("departments")
    @Expose
    private String departments;
    @SerializedName("facilities")
    @Expose
    private String facilities;
    @SerializedName("timings")
    @Expose
    private String timings;

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOfficePhone() {
        return officePhone;
    }

    public void setOfficePhone(Integer officePhone) {
        this.officePhone = officePhone;
    }

    public String getOfficeAddress() {
        return officeAddress;
    }

    public void setOfficeAddress(String officeAddress) {
        this.officeAddress = officeAddress;
    }

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    public String getOfficeEmail() {
        return officeEmail;
    }

    public void setOfficeEmail(String officeEmail) {
        this.officeEmail = officeEmail;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getDocCopy() {
        return docCopy;
    }

    public void setDocCopy(String docCopy) {
        this.docCopy = docCopy;
    }

    public Integer getServiceType() {
        return serviceType;
    }

    public void setServiceType(Integer serviceType) {
        this.serviceType = serviceType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Object getProofExpire() {
        return proofExpire;
    }

    public void setProofExpire(Object proofExpire) {
        this.proofExpire = proofExpire;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getOwnerUserId() {
        return ownerUserId;
    }

    public void setOwnerUserId(Integer ownerUserId) {
        this.ownerUserId = ownerUserId;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Integer getVerifiedDoc() {
        return verifiedDoc;
    }

    public void setVerifiedDoc(Integer verifiedDoc) {
        this.verifiedDoc = verifiedDoc;
    }

    public Integer getVendorId() {
        return vendorId;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    public String getShopPhone() {
        return shopPhone;
    }

    public void setShopPhone(String shopPhone) {
        this.shopPhone = shopPhone;
    }

    public String getLivePhoto() {
        return livePhoto;
    }

    public void setLivePhoto(String livePhoto) {
        this.livePhoto = livePhoto;
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

    public Integer getIsMoving() {
        return isMoving;
    }

    public void setIsMoving(Integer isMoving) {
        this.isMoving = isMoving;
    }

    public Integer getShopOpen() {
        return shopOpen;
    }

    public void setShopOpen(Integer shopOpen) {
        this.shopOpen = shopOpen;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getTinyrating() {
        return tinyrating;
    }

    public void setTinyrating(Integer tinyrating) {
        this.tinyrating = tinyrating;
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

    public Integer getShopPincode() {
        return shopPincode;
    }

    public void setShopPincode(Integer shopPincode) {
        this.shopPincode = shopPincode;
    }

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

    public String getDepartments() {
        return departments;
    }

    public void setDepartments(String departments) {
        this.departments = departments;
    }

    public String getFacilities() {
        return facilities;
    }

    public void setFacilities(String facilities) {
        this.facilities = facilities;
    }

    public String getTimings() {
        return timings;
    }

    public void setTimings(String timings) {
        this.timings = timings;
    }

}
