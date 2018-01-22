package com.zj.entity.base.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class InqueryRentThrow implements Serializable {
    private String id;

    private String lesseeSideId;

    private String lesseeSideName;

    private String deviceTypeId;

    private String deviceTypeName;

    private String deviceTypeSpecDataId;

    private String startTime;

    private String endTime;

    private String leaseTerm;

    private Integer billingType;

    private String address;

    private String city;

    private String latitude;

    private String longitude;

    private Integer quantity;

    private String additionalRequirement;

    private Boolean isIncludeShippingFee;

    private Boolean isIncludeInvoice;

    private Boolean isInlcudeJiShou;

    private Boolean isInlcudeFuel;

    private Boolean isInlcudeInsurance;

    private BigDecimal rentFee;

    private String creator;

    private Integer status;

    private String responseLeasingSideId;

    private String responseLeasingSideName;

    private String responseTime;

    private String responseUserId;

    private String deviceModelId;

    private String deviceModelName;

    private String picture;

    private String otherExpenseComment;

    private String projectId;

    private Date createTime;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getLesseeSideId() {
        return lesseeSideId;
    }

    public void setLesseeSideId(String lesseeSideId) {
        this.lesseeSideId = lesseeSideId == null ? null : lesseeSideId.trim();
    }

    public String getLesseeSideName() {
        return lesseeSideName;
    }

    public void setLesseeSideName(String lesseeSideName) {
        this.lesseeSideName = lesseeSideName == null ? null : lesseeSideName.trim();
    }

    public String getDeviceTypeId() {
        return deviceTypeId;
    }

    public void setDeviceTypeId(String deviceTypeId) {
        this.deviceTypeId = deviceTypeId == null ? null : deviceTypeId.trim();
    }

    public String getDeviceTypeName() {
        return deviceTypeName;
    }

    public void setDeviceTypeName(String deviceTypeName) {
        this.deviceTypeName = deviceTypeName == null ? null : deviceTypeName.trim();
    }

    public String getDeviceTypeSpecDataId() {
        return deviceTypeSpecDataId;
    }

    public void setDeviceTypeSpecDataId(String deviceTypeSpecDataId) {
        this.deviceTypeSpecDataId = deviceTypeSpecDataId == null ? null : deviceTypeSpecDataId.trim();
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime == null ? null : startTime.trim();
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime == null ? null : endTime.trim();
    }

    public String getLeaseTerm() {
        return leaseTerm;
    }

    public void setLeaseTerm(String leaseTerm) {
        this.leaseTerm = leaseTerm == null ? null : leaseTerm.trim();
    }

    public Integer getBillingType() {
        return billingType;
    }

    public void setBillingType(Integer billingType) {
        this.billingType = billingType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude == null ? null : latitude.trim();
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude == null ? null : longitude.trim();
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getAdditionalRequirement() {
        return additionalRequirement;
    }

    public void setAdditionalRequirement(String additionalRequirement) {
        this.additionalRequirement = additionalRequirement == null ? null : additionalRequirement.trim();
    }

    public Boolean getIsIncludeShippingFee() {
        return isIncludeShippingFee;
    }

    public void setIsIncludeShippingFee(Boolean isIncludeShippingFee) {
        this.isIncludeShippingFee = isIncludeShippingFee;
    }

    public Boolean getIsIncludeInvoice() {
        return isIncludeInvoice;
    }

    public void setIsIncludeInvoice(Boolean isIncludeInvoice) {
        this.isIncludeInvoice = isIncludeInvoice;
    }

    public Boolean getIsInlcudeJiShou() {
        return isInlcudeJiShou;
    }

    public void setIsInlcudeJiShou(Boolean isInlcudeJiShou) {
        this.isInlcudeJiShou = isInlcudeJiShou;
    }

    public Boolean getIsInlcudeFuel() {
        return isInlcudeFuel;
    }

    public void setIsInlcudeFuel(Boolean isInlcudeFuel) {
        this.isInlcudeFuel = isInlcudeFuel;
    }

    public Boolean getIsInlcudeInsurance() {
        return isInlcudeInsurance;
    }

    public void setIsInlcudeInsurance(Boolean isInlcudeInsurance) {
        this.isInlcudeInsurance = isInlcudeInsurance;
    }

    public BigDecimal getRentFee() {
        return rentFee;
    }

    public void setRentFee(BigDecimal rentFee) {
        this.rentFee = rentFee;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getResponseLeasingSideId() {
        return responseLeasingSideId;
    }

    public void setResponseLeasingSideId(String responseLeasingSideId) {
        this.responseLeasingSideId = responseLeasingSideId == null ? null : responseLeasingSideId.trim();
    }

    public String getResponseLeasingSideName() {
        return responseLeasingSideName;
    }

    public void setResponseLeasingSideName(String responseLeasingSideName) {
        this.responseLeasingSideName = responseLeasingSideName == null ? null : responseLeasingSideName.trim();
    }

    public String getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(String responseTime) {
        this.responseTime = responseTime == null ? null : responseTime.trim();
    }

    public String getResponseUserId() {
        return responseUserId;
    }

    public void setResponseUserId(String responseUserId) {
        this.responseUserId = responseUserId == null ? null : responseUserId.trim();
    }

    public String getDeviceModelId() {
        return deviceModelId;
    }

    public void setDeviceModelId(String deviceModelId) {
        this.deviceModelId = deviceModelId == null ? null : deviceModelId.trim();
    }

    public String getDeviceModelName() {
        return deviceModelName;
    }

    public void setDeviceModelName(String deviceModelName) {
        this.deviceModelName = deviceModelName == null ? null : deviceModelName.trim();
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture == null ? null : picture.trim();
    }

    public String getOtherExpenseComment() {
        return otherExpenseComment;
    }

    public void setOtherExpenseComment(String otherExpenseComment) {
        this.otherExpenseComment = otherExpenseComment == null ? null : otherExpenseComment.trim();
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }
    @JSONField(format="yyyy-MM-dd HH:mm:ss" ) 
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", lesseeSideId=").append(lesseeSideId);
        sb.append(", lesseeSideName=").append(lesseeSideName);
        sb.append(", deviceTypeId=").append(deviceTypeId);
        sb.append(", deviceTypeName=").append(deviceTypeName);
        sb.append(", deviceTypeSpecDataId=").append(deviceTypeSpecDataId);
        sb.append(", startTime=").append(startTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", leaseTerm=").append(leaseTerm);
        sb.append(", billingType=").append(billingType);
        sb.append(", address=").append(address);
        sb.append(", city=").append(city);
        sb.append(", latitude=").append(latitude);
        sb.append(", longitude=").append(longitude);
        sb.append(", quantity=").append(quantity);
        sb.append(", additionalRequirement=").append(additionalRequirement);
        sb.append(", isIncludeShippingFee=").append(isIncludeShippingFee);
        sb.append(", isIncludeInvoice=").append(isIncludeInvoice);
        sb.append(", isInlcudeJiShou=").append(isInlcudeJiShou);
        sb.append(", isInlcudeFuel=").append(isInlcudeFuel);
        sb.append(", isInlcudeInsurance=").append(isInlcudeInsurance);
        sb.append(", rentFee=").append(rentFee);
        sb.append(", creator=").append(creator);
        sb.append(", status=").append(status);
        sb.append(", responseLeasingSideId=").append(responseLeasingSideId);
        sb.append(", responseLeasingSideName=").append(responseLeasingSideName);
        sb.append(", responseTime=").append(responseTime);
        sb.append(", responseUserId=").append(responseUserId);
        sb.append(", deviceModelId=").append(deviceModelId);
        sb.append(", deviceModelName=").append(deviceModelName);
        sb.append(", picture=").append(picture);
        sb.append(", otherExpenseComment=").append(otherExpenseComment);
        sb.append(", projectId=").append(projectId);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}