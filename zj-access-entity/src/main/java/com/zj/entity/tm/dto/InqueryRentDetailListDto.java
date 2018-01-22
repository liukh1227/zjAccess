package com.zj.entity.tm.dto;

import com.zj.entity.base.po.InqueryRent;

public class InqueryRentDetailListDto  extends  InqueryRent{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3632485010140369157L;
	private DateTypeDto dateType;
	private String DeviceTypeSpecDefinitionName;
	private String deviceTypeSpecDataValue;
	private String unit;
	private String distance;
	public DateTypeDto getDateType() {
		return dateType;
	}
	public void setDateType(DateTypeDto dateType) {
		this.dateType = dateType;
	}
	public String getDeviceTypeSpecDefinitionName() {
		return DeviceTypeSpecDefinitionName;
	}
	public void setDeviceTypeSpecDefinitionName(String deviceTypeSpecDefinitionName) {
		DeviceTypeSpecDefinitionName = deviceTypeSpecDefinitionName;
	}
	public String getDeviceTypeSpecDataValue() {
		return deviceTypeSpecDataValue;
	}
	public void setDeviceTypeSpecDataValue(String deviceTypeSpecDataValue) {
		this.deviceTypeSpecDataValue = deviceTypeSpecDataValue;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	

}
