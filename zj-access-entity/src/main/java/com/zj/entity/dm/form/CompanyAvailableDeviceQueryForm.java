package com.zj.entity.dm.form;

import java.io.Serializable;
import com.zj.base.entity.base.form.PageQueryForm;

/**
 * 
 * @author liukh
 * @date 2017-2-21 下午2:32:55
 */
public class CompanyAvailableDeviceQueryForm extends PageQueryForm implements Serializable {
	
	private static final long serialVersionUID = 4043803063553982507L;
	private String address;
	private String city;
	private String companyId;
	private String companyName;
	private String excludedCompanyId;
	private String deviceBrandId;
	private String deviceBrandName;
	private String deviceModelId;
	private String deviceModelName;
	private String parentDeviceTypeId;
	private String deviceTypeId;
	private String deviceTypeName;
	private String keyAttributeValueId;
	private Integer requreAmount;
	private String longitude;
	private String latitude;
	

	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getExcludedCompanyId() {
		return excludedCompanyId;
	}
	public void setExcludedCompanyId(String excludedCompanyId) {
		this.excludedCompanyId = excludedCompanyId;
	}
	public String getDeviceBrandId() {
		return deviceBrandId;
	}
	public void setDeviceBrandId(String deviceBrandId) {
		this.deviceBrandId = deviceBrandId;
	}
	public String getDeviceBrandName() {
		return deviceBrandName;
	}
	public void setDeviceBrandName(String deviceBrandName) {
		this.deviceBrandName = deviceBrandName;
	}
	public String getDeviceModelId() {
		return deviceModelId;
	}
	public void setDeviceModelId(String deviceModelId) {
		this.deviceModelId = deviceModelId;
	}
	public String getDeviceModelName() {
		return deviceModelName;
	}
	public void setDeviceModelName(String deviceModelName) {
		this.deviceModelName = deviceModelName;
	}
	public String getParentDeviceTypeId() {
		return parentDeviceTypeId;
	}
	public void setParentDeviceTypeId(String parentDeviceTypeId) {
		this.parentDeviceTypeId = parentDeviceTypeId;
	}
	public String getDeviceTypeId() {
		return deviceTypeId;
	}
	public void setDeviceTypeId(String deviceTypeId) {
		this.deviceTypeId = deviceTypeId;
	}
	public String getDeviceTypeName() {
		return deviceTypeName;
	}
	public void setDeviceTypeName(String deviceTypeName) {
		this.deviceTypeName = deviceTypeName;
	}
	public String getKeyAttributeValueId() {
		return keyAttributeValueId;
	}
	public void setKeyAttributeValueId(String keyAttributeValueId) {
		this.keyAttributeValueId = keyAttributeValueId;
	}
	public Integer getRequreAmount() {
		return requreAmount;
	}
	public void setRequreAmount(Integer requreAmount) {
		this.requreAmount = requreAmount;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
}
