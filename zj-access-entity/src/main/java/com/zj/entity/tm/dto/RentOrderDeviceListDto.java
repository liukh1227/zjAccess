package com.zj.entity.tm.dto;

import com.zj.entity.base.po.RentOrderDevice;

public class RentOrderDeviceListDto extends RentOrderDevice {
	private static final long serialVersionUID = -7431940889523197328L;
	private String deviceBrandName;
	private String deviceTypeName;
	private String rentOrderId;
	private DateTypeDto dateType;
	public String getDeviceBrandName() {
		return deviceBrandName;
	}
	public void setDeviceBrandName(String deviceBrandName) {
		this.deviceBrandName = deviceBrandName;
	}
	public String getDeviceTypeName() {
		return deviceTypeName;
	}
	public void setDeviceTypeName(String deviceTypeName) {
		this.deviceTypeName = deviceTypeName;
	}
	public DateTypeDto getDateType() {
		return dateType;
	}
	public void setDateType(DateTypeDto dateType) {
		this.dateType = dateType;
	}
	public String getRentOrderId() {
		return rentOrderId;
	}
	public void setRentOrderId(String rentOrderId) {
		this.rentOrderId = rentOrderId;
	}
	
	
}
