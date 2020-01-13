package com.tag.app.tagnearemployee.pojomodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchList {

    @SerializedName("customer_name")
    @Expose
    private String customerName;
    @SerializedName("customer_phone")
    @Expose
    private String customerPhone;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("customer_id")
    @Expose
    private Integer customerId;
    @SerializedName("vendor_id")
    @Expose
    private Object vendorId;
    @SerializedName("discount_name")
    @Expose
    private String discountName;
    @SerializedName("discount_type")
    @Expose
    private String discountType;
    @SerializedName("discount_amount")
    @Expose
    private Integer discountAmount;
    @SerializedName("expected_time")
    @Expose
    private Object expectedTime;
    @SerializedName("discount_code")
    @Expose
    private String discountCode;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("order_state")
    @Expose
    private Integer orderState;
    @SerializedName("total_amount")
    @Expose
    private String totalAmount;
    @SerializedName("final_amount")
    @Expose
    private String finalAmount;
    @SerializedName("tax_percent")
    @Expose
    private String taxPercent;
    @SerializedName("sub_total")
    @Expose
    private String subTotal;
    @SerializedName("paid_amount")
    @Expose
    private String paidAmount;
    @SerializedName("tax_id")
    @Expose
    private Integer taxId;
    @SerializedName("cgst")
    @Expose
    private Integer cgst;
    @SerializedName("sgst")
    @Expose
    private Integer sgst;
    @SerializedName("created_at")
    @Expose
    private Object createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("tinyshop_id")
    @Expose
    private Integer tinyshopId;
    @SerializedName("orderstatus")
    @Expose
    private String orderstatus;
    @SerializedName("delivery_id")
    @Expose
    private Object deliveryId;
    @SerializedName("order_type")
    @Expose
    private Integer orderType;
    @SerializedName("details")
    @Expose
    private List<SearchDetail> details = null;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Object getVendorId() {
        return vendorId;
    }

    public void setVendorId(Object vendorId) {
        this.vendorId = vendorId;
    }

    public String getDiscountName() {
        return discountName;
    }

    public void setDiscountName(String discountName) {
        this.discountName = discountName;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public Integer getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Integer discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Object getExpectedTime() {
        return expectedTime;
    }

    public void setExpectedTime(Object expectedTime) {
        this.expectedTime = expectedTime;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getOrderState() {
        return orderState;
    }

    public void setOrderState(Integer orderState) {
        this.orderState = orderState;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getFinalAmount() {
        return finalAmount;
    }

    public void setFinalAmount(String finalAmount) {
        this.finalAmount = finalAmount;
    }

    public String getTaxPercent() {
        return taxPercent;
    }

    public void setTaxPercent(String taxPercent) {
        this.taxPercent = taxPercent;
    }

    public String getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(String subTotal) {
        this.subTotal = subTotal;
    }

    public String getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(String paidAmount) {
        this.paidAmount = paidAmount;
    }

    public Integer getTaxId() {
        return taxId;
    }

    public void setTaxId(Integer taxId) {
        this.taxId = taxId;
    }

    public Integer getCgst() {
        return cgst;
    }

    public void setCgst(Integer cgst) {
        this.cgst = cgst;
    }

    public Integer getSgst() {
        return sgst;
    }

    public void setSgst(Integer sgst) {
        this.sgst = sgst;
    }

    public Object getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Object createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getTinyshopId() {
        return tinyshopId;
    }

    public void setTinyshopId(Integer tinyshopId) {
        this.tinyshopId = tinyshopId;
    }

    public String getOrderstatus() {
        return orderstatus;
    }

    public void setOrderstatus(String orderstatus) {
        this.orderstatus = orderstatus;
    }

    public Object getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(Object deliveryId) {
        this.deliveryId = deliveryId;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public List<SearchDetail> getDetails() {
        return details;
    }

    public void setDetails(List<SearchDetail> details) {
        this.details = details;
    }


}
