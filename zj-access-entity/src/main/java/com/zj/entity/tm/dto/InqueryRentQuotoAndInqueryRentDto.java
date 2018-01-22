package com.zj.entity.tm.dto;

import java.math.BigDecimal;

/**
 * @author liukh
 * @date 2016-11-25 上午10:44:50
 */
public class InqueryRentQuotoAndInqueryRentDto {
	private String inqueryRentId;
	private String inqueryRentQuoteId;
	private String additionalRequirement;
	private String startTime;
	private String endTime;
	private String address;
	private String latitude;
	private String longitude;
	private String city;
	private Boolean isIncludeShippingFee;
	private Boolean isIncludeInvoice;
	private Boolean isInlcudeJiShou;
	private Boolean isInlcudeFuel;
	private Boolean isInlcudeInsurance;
	private String payMethod;
	private String leasingSideName;
	private String leasingSideId;
	private String lesseeSideId;
	private String lesseeSideName;
	private String deviceModelName;
    private String deviceBrandName;
	private String deviceTypeName;
	private Integer quantity;
	private BigDecimal price;
	private String otherExpense;
	private String otherExpenseComment;
	private String totalPrice;
	private String DeviceTypeSpecDefinitionName;
	private String deviceTypeSpecDataValue;
	private String unit;
	private String projectId;
	private String leaseTerm;
	private Integer billingType; 
	
	
	public String getInqueryRentId() {
		return inqueryRentId;
	}
	public String getInqueryRentQuoteId() {
		return inqueryRentQuoteId;
	}
	public void setInqueryRentId(String inqueryRentId) {
		this.inqueryRentId = inqueryRentId;
	}
	public void setInqueryRentQuoteId(String inqueryRentQuoteId) {
		this.inqueryRentQuoteId = inqueryRentQuoteId;
	}
	public String getAdditionalRequirement() {
		return additionalRequirement;
	}
	public String getStartTime() {
		return startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public String getAddress() {
		return address;
	}
	public String getLatitude() {
		return latitude;
	}
	public String getLongitude() {
		return longitude;
	}

	public String getPayMethod() {
		return payMethod;
	}
	public String getLeasingSideName() {
		return leasingSideName;
	}
	public String getOtherExpense() {
		return otherExpense;
	}
	public String getTotalPrice() {
		return totalPrice;
	}
	public void setAdditionalRequirement(String additionalRequirement) {
		this.additionalRequirement = additionalRequirement;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}
	public void setLeasingSideName(String leasingSideName) {
		this.leasingSideName = leasingSideName;
	}
	public void setOtherExpense(String otherExpense) {
		this.otherExpense = otherExpense;
	}
	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getDeviceModelName() {
		return deviceModelName;
	}
	public String getDeviceBrandName() {
		return deviceBrandName;
	}
	public String getDeviceTypeName() {
		return deviceTypeName;
	}
	public void setDeviceModelName(String deviceModelName) {
		this.deviceModelName = deviceModelName;
	}
	public void setDeviceBrandName(String deviceBrandName) {
		this.deviceBrandName = deviceBrandName;
	}
	public void setDeviceTypeName(String deviceTypeName) {
		this.deviceTypeName = deviceTypeName;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public String getDeviceTypeSpecDataValue() {
		return deviceTypeSpecDataValue;
	}
	public void setDeviceTypeSpecDataValue(String deviceTypeSpecDataValue) {
		this.deviceTypeSpecDataValue = deviceTypeSpecDataValue;
	}
	public String getDeviceTypeSpecDefinitionName() {
		return DeviceTypeSpecDefinitionName;
	}
	public void setDeviceTypeSpecDefinitionName(String deviceTypeSpecDefinitionName) {
		DeviceTypeSpecDefinitionName = deviceTypeSpecDefinitionName;
	}
	public String getOtherExpenseComment() {
		return otherExpenseComment;
	}
	public void setOtherExpenseComment(String otherExpenseComment) {
		this.otherExpenseComment = otherExpenseComment;
	}
	public Boolean getIsIncludeShippingFee() {
		return isIncludeShippingFee;
	}
	public Boolean getIsIncludeInvoice() {
		return isIncludeInvoice;
	}
	public Boolean getIsInlcudeJiShou() {
		return isInlcudeJiShou;
	}
	public Boolean getIsInlcudeFuel() {
		return isInlcudeFuel;
	}
	public Boolean getIsInlcudeInsurance() {
		return isInlcudeInsurance;
	}
	public void setIsIncludeShippingFee(Boolean isIncludeShippingFee) {
		this.isIncludeShippingFee = isIncludeShippingFee;
	}
	public void setIsIncludeInvoice(Boolean isIncludeInvoice) {
		this.isIncludeInvoice = isIncludeInvoice;
	}
	public void setIsInlcudeJiShou(Boolean isInlcudeJiShou) {
		this.isInlcudeJiShou = isInlcudeJiShou;
	}
	public void setIsInlcudeFuel(Boolean isInlcudeFuel) {
		this.isInlcudeFuel = isInlcudeFuel;
	}
	public void setIsInlcudeInsurance(Boolean isInlcudeInsurance) {
		this.isInlcudeInsurance = isInlcudeInsurance;
	}
	public String getLeasingSideId() {
		return leasingSideId;
	}
	public String getLesseeSideId() {
		return lesseeSideId;
	}
	public String getLesseeSideName() {
		return lesseeSideName;
	}
	public void setLeasingSideId(String leasingSideId) {
		this.leasingSideId = leasingSideId;
	}
	public void setLesseeSideId(String lesseeSideId) {
		this.lesseeSideId = lesseeSideId;
	}
	public void setLesseeSideName(String lesseeSideName) {
		this.lesseeSideName = lesseeSideName;
	}
	public String getProjectId() {
		return projectId;
	}
	public String getLeaseTerm() {
		return leaseTerm;
	}
	public Integer getBillingType() {
		return billingType;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public void setLeaseTerm(String leaseTerm) {
		this.leaseTerm = leaseTerm;
	}
	public void setBillingType(Integer billingType) {
		this.billingType = billingType;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	
}
