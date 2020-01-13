package com.tag.app.tagnearemployee.pojomodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Profiledatum
{
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("permissions")
    @Expose
    private String permissions;
    @SerializedName("last_login")
    @Expose
    private String lastLogin;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("role")
    @Expose
    private String role;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("profileImg")
    @Expose
    private String profileImg;
    @SerializedName("authtoken")
    @Expose
    private String authtoken;
    @SerializedName("present_address")
    @Expose
    private String presentAddress;
    @SerializedName("access_key")
    @Expose
    private Integer accessKey;
    @SerializedName("gender")
    @Expose
    private Integer gender;
    @SerializedName("acc_num")
    @Expose
    private String accNum;
    @SerializedName("pf_num")
    @Expose
    private String pfNum;
    @SerializedName("esi_num")
    @Expose
    private String esiNum;
    @SerializedName("finger_keys")
    @Expose
    private String fingerKeys;
    @SerializedName("finger_hand")
    @Expose
    private String fingerHand;
    @SerializedName("age")
    @Expose
    private Integer age;
    @SerializedName("uan_no")
    @Expose
    private String uanNo;
    @SerializedName("qualification")
    @Expose
    private String qualification;
    @SerializedName("blood_group")
    @Expose
    private String bloodGroup;
    @SerializedName("known_language")
    @Expose
    private String knownLanguage;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("date_of_join")
    @Expose
    private String dateOfJoin;
    @SerializedName("nominee_name")
    @Expose
    private String nomineeName;
    @SerializedName("relatioship")
    @Expose
    private String relatioship;
    @SerializedName("shift")
    @Expose
    private Integer shift;
    @SerializedName("cast")
    @Expose
    private String cast;
    @SerializedName("religion")
    @Expose
    private String religion;
    @SerializedName("releaved_date")
    @Expose
    private String releavedDate;
    @SerializedName("is_leader")
    @Expose
    private Integer isLeader;
    @SerializedName("salary_id")
    @Expose
    private Integer salaryId;
    @SerializedName("marital_status")
    @Expose
    private Integer maritalStatus;
    @SerializedName("refered_by")
    @Expose
    private String referedBy;
    @SerializedName("refered_phone")
    @Expose
    private String referedPhone;
    @SerializedName("user_vendor_id")
    @Expose
    private Object userVendorId;
    @SerializedName("branch_id")
    @Expose
    private Object branchId;
    @SerializedName("emp_id")
    @Expose
    private String empId;
    @SerializedName("service_dept")
    @Expose
    private String serviceDept;
    @SerializedName("ifsc_num")
    @Expose
    private String ifscNum;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("ref1_rel")
    @Expose
    private String ref1Rel;
    @SerializedName("refered2_by")
    @Expose
    private String refered2By;
    @SerializedName("refered2_phone")
    @Expose
    private String refered2Phone;
    @SerializedName("ref2_rel")
    @Expose
    private String ref2Rel;
    @SerializedName("father_name")
    @Expose
    private String fatherName;
    @SerializedName("mother_name")
    @Expose
    private String motherName;
    @SerializedName("bank_name")
    @Expose
    private String bankName;
    @SerializedName("ref1doc_copy")
    @Expose
    private String ref1docCopy;
    @SerializedName("ref2doc_copy")
    @Expose
    private String ref2docCopy;
    @SerializedName("idproof_copy")
    @Expose
    private String idproofCopy;
    @SerializedName("color")
    @Expose
    private String color;
    @SerializedName("service_id")
    @Expose
    private Integer serviceId;
    @SerializedName("date_of_leave")
    @Expose
    private String dateOfLeave;
    @SerializedName("reason")
    @Expose
    private String reason;
    @SerializedName("branch_name")
    @Expose
    private String branchName;
    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("vender_sl_no")
    @Expose
    private Integer venderSlNo;
    @SerializedName("prefix")
    @Expose
    private String prefix;
    @SerializedName("salary")
    @Expose
    private Integer salary;
    @SerializedName("nick")
    @Expose
    private String nick;
    @SerializedName("billingstatus")
    @Expose
    private Integer billingstatus;
    @SerializedName("branch_access")
    @Expose
    private String branchAccess;
    @SerializedName("sattled_payment")
    @Expose
    private Integer sattledPayment;
    @SerializedName("not_ofattedance")
    @Expose
    private Integer notOfattedance;
    @SerializedName("tinyshop_id")
    @Expose
    private Integer tinyshopId;
    @SerializedName("structured_type")
    @Expose
    private Integer structuredType;
    @SerializedName("duty_status")
    @Expose
    private String dutyStatus;
    @SerializedName("device_id")
    @Expose
    private String deviceId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public String getAuthtoken() {
        return authtoken;
    }

    public void setAuthtoken(String authtoken) {
        this.authtoken = authtoken;
    }

    public String getPresentAddress() {
        return presentAddress;
    }

    public void setPresentAddress(String presentAddress) {
        this.presentAddress = presentAddress;
    }

    public Integer getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(Integer accessKey) {
        this.accessKey = accessKey;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getAccNum() {
        return accNum;
    }

    public void setAccNum(String accNum) {
        this.accNum = accNum;
    }

    public String getPfNum() {
        return pfNum;
    }

    public void setPfNum(String pfNum) {
        this.pfNum = pfNum;
    }

    public String getEsiNum() {
        return esiNum;
    }

    public void setEsiNum(String esiNum) {
        this.esiNum = esiNum;
    }

    public String getFingerKeys() {
        return fingerKeys;
    }

    public void setFingerKeys(String fingerKeys) {
        this.fingerKeys = fingerKeys;
    }

    public String getFingerHand() {
        return fingerHand;
    }

    public void setFingerHand(String fingerHand) {
        this.fingerHand = fingerHand;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getUanNo() {
        return uanNo;
    }

    public void setUanNo(String uanNo) {
        this.uanNo = uanNo;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getKnownLanguage() {
        return knownLanguage;
    }

    public void setKnownLanguage(String knownLanguage) {
        this.knownLanguage = knownLanguage;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getDateOfJoin() {
        return dateOfJoin;
    }

    public void setDateOfJoin(String dateOfJoin) {
        this.dateOfJoin = dateOfJoin;
    }

    public String getNomineeName() {
        return nomineeName;
    }

    public void setNomineeName(String nomineeName) {
        this.nomineeName = nomineeName;
    }

    public String getRelatioship() {
        return relatioship;
    }

    public void setRelatioship(String relatioship) {
        this.relatioship = relatioship;
    }

    public Integer getShift() {
        return shift;
    }

    public void setShift(Integer shift) {
        this.shift = shift;
    }

    public String getCast() {
        return cast;
    }

    public void setCast(String cast) {
        this.cast = cast;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getReleavedDate() {
        return releavedDate;
    }

    public void setReleavedDate(String releavedDate) {
        this.releavedDate = releavedDate;
    }

    public Integer getIsLeader() {
        return isLeader;
    }

    public void setIsLeader(Integer isLeader) {
        this.isLeader = isLeader;
    }

    public Integer getSalaryId() {
        return salaryId;
    }

    public void setSalaryId(Integer salaryId) {
        this.salaryId = salaryId;
    }

    public Integer getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(Integer maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getReferedBy() {
        return referedBy;
    }

    public void setReferedBy(String referedBy) {
        this.referedBy = referedBy;
    }

    public String getReferedPhone() {
        return referedPhone;
    }

    public void setReferedPhone(String referedPhone) {
        this.referedPhone = referedPhone;
    }

    public Object getUserVendorId() {
        return userVendorId;
    }

    public void setUserVendorId(Object userVendorId) {
        this.userVendorId = userVendorId;
    }

    public Object getBranchId() {
        return branchId;
    }

    public void setBranchId(Object branchId) {
        this.branchId = branchId;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getServiceDept() {
        return serviceDept;
    }

    public void setServiceDept(String serviceDept) {
        this.serviceDept = serviceDept;
    }

    public String getIfscNum() {
        return ifscNum;
    }

    public void setIfscNum(String ifscNum) {
        this.ifscNum = ifscNum;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRef1Rel() {
        return ref1Rel;
    }

    public void setRef1Rel(String ref1Rel) {
        this.ref1Rel = ref1Rel;
    }

    public String getRefered2By() {
        return refered2By;
    }

    public void setRefered2By(String refered2By) {
        this.refered2By = refered2By;
    }

    public String getRefered2Phone() {
        return refered2Phone;
    }

    public void setRefered2Phone(String refered2Phone) {
        this.refered2Phone = refered2Phone;
    }

    public String getRef2Rel() {
        return ref2Rel;
    }

    public void setRef2Rel(String ref2Rel) {
        this.ref2Rel = ref2Rel;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getRef1docCopy() {
        return ref1docCopy;
    }

    public void setRef1docCopy(String ref1docCopy) {
        this.ref1docCopy = ref1docCopy;
    }

    public String getRef2docCopy() {
        return ref2docCopy;
    }

    public void setRef2docCopy(String ref2docCopy) {
        this.ref2docCopy = ref2docCopy;
    }

    public String getIdproofCopy() {
        return idproofCopy;
    }

    public void setIdproofCopy(String idproofCopy) {
        this.idproofCopy = idproofCopy;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public String getDateOfLeave() {
        return dateOfLeave;
    }

    public void setDateOfLeave(String dateOfLeave) {
        this.dateOfLeave = dateOfLeave;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getVenderSlNo() {
        return venderSlNo;
    }

    public void setVenderSlNo(Integer venderSlNo) {
        this.venderSlNo = venderSlNo;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public Integer getBillingstatus() {
        return billingstatus;
    }

    public void setBillingstatus(Integer billingstatus) {
        this.billingstatus = billingstatus;
    }

    public String getBranchAccess() {
        return branchAccess;
    }

    public void setBranchAccess(String branchAccess) {
        this.branchAccess = branchAccess;
    }

    public Integer getSattledPayment() {
        return sattledPayment;
    }

    public void setSattledPayment(Integer sattledPayment) {
        this.sattledPayment = sattledPayment;
    }

    public Integer getNotOfattedance() {
        return notOfattedance;
    }

    public void setNotOfattedance(Integer notOfattedance) {
        this.notOfattedance = notOfattedance;
    }

    public Integer getTinyshopId() {
        return tinyshopId;
    }

    public void setTinyshopId(Integer tinyshopId) {
        this.tinyshopId = tinyshopId;
    }

    public Integer getStructuredType() {
        return structuredType;
    }

    public void setStructuredType(Integer structuredType) {
        this.structuredType = structuredType;
    }

    public String getDutyStatus() {
        return dutyStatus;
    }

    public void setDutyStatus(String dutyStatus) {
        this.dutyStatus = dutyStatus;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

}
