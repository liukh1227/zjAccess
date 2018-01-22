package com.zj.entity.base.po;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class Device implements Serializable {
    private String id;

    private String deviceBrand;

    private String deviceType;

    private String deviceName;

    private String deviceModel;

    private String sequenceNum;

    private String plateNumber;

    private String manufactureYear;

    private String address;

    private String latitude;

    private String longitude;

    private Boolean isImported;

    private Integer workingTime;

    private String picture;

    private String rentPerDay;

    private String rentPerMonth;

    private Integer status;

    private String companyId;

    private Integer isRealDevice;

    private String realDeviceId;

    private String creator;

    private Date createTime;
    
    private Boolean isTempData = Boolean.FALSE;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getDeviceBrand() {
        return deviceBrand;
    }

    public void setDeviceBrand(String deviceBrand) {
        this.deviceBrand = deviceBrand == null ? null : deviceBrand.trim();
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType == null ? null : deviceType.trim();
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName == null ? null : deviceName.trim();
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel == null ? null : deviceModel.trim();
    }

    public String getSequenceNum() {
        return sequenceNum;
    }

    public void setSequenceNum(String sequenceNum) {
        this.sequenceNum = sequenceNum == null ? null : sequenceNum.trim();
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber == null ? null : plateNumber.trim();
    }

    public String getManufactureYear() {
        return manufactureYear;
    }

    public void setManufactureYear(String manufactureYear) {
        this.manufactureYear = manufactureYear == null ? null : manufactureYear.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
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

    public Boolean getIsImported() {
        return isImported;
    }

    public void setIsImported(Boolean isImported) {
        this.isImported = isImported;
    }

    public Integer getWorkingTime() {
        return workingTime;
    }

    public void setWorkingTime(Integer workingTime) {
        this.workingTime = workingTime;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture == null ? null : picture.trim();
    }

    public String getRentPerDay() {
        return rentPerDay;
    }

    public void setRentPerDay(String rentPerDay) {
        this.rentPerDay = rentPerDay == null ? null : rentPerDay.trim();
    }

    public String getRentPerMonth() {
        return rentPerMonth;
    }

    public void setRentPerMonth(String rentPerMonth) {
        this.rentPerMonth = rentPerMonth == null ? null : rentPerMonth.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId == null ? null : companyId.trim();
    }

    public Integer getIsRealDevice() {
        return isRealDevice;
    }

    public void setIsRealDevice(Integer isRealDevice) {
        this.isRealDevice = isRealDevice;
    }

    public String getRealDeviceId() {
        return realDeviceId;
    }

    public void setRealDeviceId(String realDeviceId) {
        this.realDeviceId = realDeviceId == null ? null : realDeviceId.trim();
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }
    @JSONField(format="yyyy-MM-dd HH:mm:ss" ) 
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
     
    public Boolean getIsTempData() {
		return isTempData;
	}

	public void setIsTempData(Boolean isTempData) {
		this.isTempData = isTempData;
	}

	@JSONField(serialize=false) 
    public void copyFrom(Device other){
    	setAddress(other.getAddress());
    	setCompanyId(other.getCompanyId());
    	setCreateTime(other.getCreateTime());
    	setCreator(other.getCreator());
    	setDeviceBrand(other.getDeviceBrand());
    	setDeviceModel(other.getDeviceModel());
    	setDeviceType(other.getDeviceType());
    	setIsImported(other.getIsImported());
    	setIsRealDevice(other.getIsRealDevice());
    	setLatitude(other.getLatitude());
    	setLongitude(other.getLongitude());
    	setManufactureYear(other.getManufactureYear());
    	setPicture(other.getPicture());
    	setPlateNumber(other.getPlateNumber());
    	setRealDeviceId(other.getRealDeviceId());
    	setRentPerDay(other.getRentPerDay());
    	setRentPerMonth(other.getRentPerMonth());
    	setSequenceNum(other.getSequenceNum());
    	setStatus(other.getStatus());
    	setWorkingTime(other.getWorkingTime());
    	setDeviceName(other.getDeviceName());
    	setIsTempData(other.getIsTempData());
    	
    }

	@Override
	public String toString() {
		return "Device [id=" + id + ", deviceBrand=" + deviceBrand
				+ ", deviceType=" + deviceType + ", deviceName=" + deviceName
				+ ", deviceModel=" + deviceModel + ", sequenceNum="
				+ sequenceNum + ", plateNumber=" + plateNumber
				+ ", manufactureYear=" + manufactureYear + ", address="
				+ address + ", latitude=" + latitude + ", longitude="
				+ longitude + ", isImported=" + isImported + ", workingTime="
				+ workingTime + ", picture=" + picture + ", rentPerDay="
				+ rentPerDay + ", rentPerMonth=" + rentPerMonth + ", status="
				+ status + ", companyId=" + companyId + ", isRealDevice="
				+ isRealDevice + ", realDeviceId=" + realDeviceId
				+ ", creator=" + creator + ", createTime=" + createTime
				+ ", isTempData=" + isTempData + "]";
	}
    
   
}