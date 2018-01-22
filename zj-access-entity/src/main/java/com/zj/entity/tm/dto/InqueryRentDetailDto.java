package com.zj.entity.tm.dto;

import com.zj.entity.base.po.InqueryRentBase;
import com.zj.entity.base.po.InqueryRentQuote;

public class InqueryRentDetailDto  extends  InqueryRentBase{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7290619987556575814L;
	private DateTypeDto dateType;
	private String DeviceTypeSpecDefinitionName;
	private String deviceTypeSpecDataValue;
	private String unit;
	private String distance;
	private InqueryRentQuote data;
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
	public InqueryRentQuote getData() {
		return data;
	}
	public void setData(InqueryRentQuote data) {
		this.data = data;
	}
	
	
	
}
