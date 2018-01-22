package com.zj.entity.base.po;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class InqueryRentBase implements Serializable {
    private String id;

    private String lesseeSideId;

    private String lesseeSideName;

    private String deviceTypeId;

    private String deviceTypeName;

    private String deviceTypeSpecDataId;

    private String startTime;

    private String endTime;

    private String payMethod;

    private String address;

    private String city;

    private String latitude;

    private String longitude;

    private Integer quantity;

    private String additionalRequirement;

    private String creator;

    private Integer status;

    private String leaseTerm;

    private Integer billingType;

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

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod == null ? null : payMethod.trim();
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
		return "InqueryRent [id=" + id + ", lesseeSideId=" + lesseeSideId
				+ ", lesseeSideName=" + lesseeSideName + ", deviceTypeId="
				+ deviceTypeId + ", deviceTypeName=" + deviceTypeName
				+ ", deviceTypeSpecDataId=" + deviceTypeSpecDataId
				+ ", startTime=" + startTime + ", endTime=" + endTime
				+ ", payMethod=" + payMethod + ", address=" + address
				+ ", city=" + city + ", latitude=" + latitude + ", longitude="
				+ longitude + ", quantity=" + quantity
				+ ", additionalRequirement=" + additionalRequirement
				+ ", creator=" + creator + ", status=" + status
				+ ", leaseTerm=" + leaseTerm + ", billingType=" + billingType
				+ ", projectId=" + projectId + ", createTime=" + createTime
				+  "]";
	}


}