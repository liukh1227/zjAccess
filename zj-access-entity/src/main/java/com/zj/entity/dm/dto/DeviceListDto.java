package com.zj.entity.dm.dto;

import java.io.Serializable;

import com.zj.entity.base.po.Device;

public class DeviceListDto extends Device implements Serializable{
	private static final long serialVersionUID = 6614639640958859280L;
	private String deviceTypeName;
	private String deviceBrandName;
	private String deviceModelName;
	private String companyName;
	
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
	public String getDeviceModelName() {
		return deviceModelName;
	}
	public void setDeviceModelName(String deviceModelName) {
		this.deviceModelName = deviceModelName;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	 
}
