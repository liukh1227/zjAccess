package com.zj.entity.tm.dto;

import com.zj.entity.base.po.InqueryDevice;

public class InqueryDeviceListDto extends InqueryDevice{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6001442920118868987L;
	private String deviceBrandName;
	private String deviceTypeName;
	private String unitPriceTag;
	private String inqueryOrderId;
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
	public String getUnitPriceTag() {
		return unitPriceTag;
	}
	public void setUnitPriceTag(String unitPriceTag) {
		this.unitPriceTag = unitPriceTag;
	}
	public DateTypeDto getDateType() {
		return dateType;
	}
	public void setDateType(DateTypeDto dateType) {
		this.dateType = dateType;
	}
	public String getInqueryOrderId() {
		return inqueryOrderId;
	}
	public void setInqueryOrderId(String inqueryOrderId) {
		this.inqueryOrderId = inqueryOrderId;
	}
	
	
}
