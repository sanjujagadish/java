package com.tag.app.tagnearemployee.pojomodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ForgotPassword implements Serializable {

    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("countryCode")
    @Expose
    private String countryCode;
    @SerializedName("otp")
    @Expose
    private String otp;
    @SerializedName( "confirmpassword" )
    @Expose
    private String confirmPassword;

    public ForgotPassword(String phone, String countrycode)
    { this.phone=phone;
        this.countryCode=countrycode; }

    public ForgotPassword() { }

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

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public void setConfirmPassword(String confirmPassword) { this.confirmPassword = confirmPassword; }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    //RESPONSE
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("verified")
    @Expose
    private Boolean verified;
    @SerializedName("status")
    @Expose
    private Integer status;

    public String getMessage() { return message; }

    public void setMessage(String message) { this.message = message; }

    public Boolean getVerified() { return verified; }

    public void setVerified(Boolean verified) { this.verified = verified; }

    public Integer getStatus() { return status; }

    public void setStatus(Integer status) { this.status = status; }


}
