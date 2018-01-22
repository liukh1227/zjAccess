package com.zj.entity.system;

import java.io.Serializable;
import java.math.BigDecimal;

public class DeviceCheckData implements Serializable{
	
	private static final long serialVersionUID = 3157187927203054736L;
		private String deviceModeId;
	    private String deviceModeName;
	    private String deviceTypeName;
	    private String deviceBrandName;
	    private String deviceName;
	    private String sequenceNum;
	    private String plateNumber;
	    private String manufactureYear;
	    private Integer isImported;
	    private Integer workingTime;
	    private BigDecimal rentPerMonth;
	    private BigDecimal rentPerDay;
	    private String picture;
	    
		public String getDeviceModeId() {
			return deviceModeId;
		}
		public void setDeviceModeId(String deviceModeId) {
			this.deviceModeId = deviceModeId;
		}
		public String getDeviceModeName() {
			return deviceModeName;
		}
		public void setDeviceModeName(String deviceModeName) {
			this.deviceModeName = deviceModeName;
		}
		public String getDeviceTypeName() {
			return deviceTypeName;
		}
		public void setDeviceTypeName(String deviceTypeName) {
			this.deviceTypeName = deviceTypeName;
		}
		public String getDeviceBrandName() {
			return deviceBrandName;
		}
		public void setDeviceBrandName(String deviceBrandName) {
			this.deviceBrandName = deviceBrandName;
		}
		public String getDeviceName() {
			return deviceName;
		}
		public void setDeviceName(String deviceName) {
			this.deviceName = deviceName;
		}
		public String getSequenceNum() {
			return sequenceNum;
		}
		public void setSequenceNum(String sequenceNum) {
			this.sequenceNum = sequenceNum;
		}
		public String getPlateNumber() {
			return plateNumber;
		}
		public void setPlateNumber(String plateNumber) {
			this.plateNumber = plateNumber;
		}
		public String getManufactureYear() {
			return manufactureYear;
		}
		public void setManufactureYear(String manufactureYear) {
			this.manufactureYear = manufactureYear;
		}
		public Integer getIsImported() {
			return isImported;
		}
		public void setIsImported(Integer isImported) {
			this.isImported = isImported;
		}
		public Integer getWorkingTime() {
			return workingTime;
		}
		public void setWorkingTime(Integer workingTime) {
			this.workingTime = workingTime;
		}
		public BigDecimal getRentPerDay() {
			return rentPerDay;
		}
		public void setRentPerDay(BigDecimal rentPerDay) {
			this.rentPerDay = rentPerDay;
		}
		public BigDecimal getRentPerMonth() {
			return rentPerMonth;
		}
		public void setRentPerMonth(BigDecimal rentPerMonth) {
			this.rentPerMonth = rentPerMonth;
		}
		public String getPicture() {
			return picture;
		}
		public void setPicture(String picture) {
			this.picture = picture;
		}

	
}
