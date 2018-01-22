package com.zj.entity.bm.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import com.alibaba.fastjson.annotation.JSONField;
import com.zj.entity.base.po.User;

public class UserDto implements Serializable{
	
	private static final long serialVersionUID = 5946535497895817451L;
	private String id;
    private String userName;
    private String logonId;
    private String cellPhone;
    private String headPortrait;
    private Integer status;
    private String companyId;
	private Integer companyType;
	private String companyName;
	private String license;
	private String representative;
	private String representativeTelephone;
	private String address;
	private String city;
	private String latitude;
	private String longitude;
	private String companyBusinessType;
	private Integer comapnyStatus;
	private String invoiceTitle;
	private String IDCardNumber;
	private BigDecimal totalAmount;
	private BigDecimal disposableAmount;
	private BigDecimal lockedAmount;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getLogonId() {
		return logonId;
	}
	public void setLogonId(String logonId) {
		this.logonId = logonId;
	}
	public String getCellPhone() {
		return cellPhone;
	}
	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}
	public String getHeadPortrait() {
		return headPortrait;
	}
	public void setHeadPortrait(String headPortrait) {
		this.headPortrait = headPortrait;
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
		this.companyId = companyId;
	}
	public Integer getCompanyType() {
		return companyType;
	}
	public void setCompanyType(Integer companyType) {
		this.companyType = companyType;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getLicense() {
		return license;
	}
	public void setLicense(String license) {
		this.license = license;
	}
	public String getRepresentative() {
		return representative;
	}
	public void setRepresentative(String representative) {
		this.representative = representative;
	}
	public String getRepresentativeTelephone() {
		return representativeTelephone;
	}
	public void setRepresentativeTelephone(String representativeTelephone) {
		this.representativeTelephone = representativeTelephone;
	}
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
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getCompanyBusinessType() {
		return companyBusinessType;
	}
	public void setCompanyBusinessType(String companyBusinessType) {
		this.companyBusinessType = companyBusinessType;
	}
	public Integer getComapnyStatus() {
		return comapnyStatus;
	}
	public void setComapnyStatus(Integer comapnyStatus) {
		this.comapnyStatus = comapnyStatus;
	}
	public String getInvoiceTitle() {
		return invoiceTitle;
	}
	public void setInvoiceTitle(String invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}
	public String getIDCardNumber() {
		return IDCardNumber;
	}
	public void setIDCardNumber(String iDCardNumber) {
		IDCardNumber = iDCardNumber;
	}
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	public BigDecimal getDisposableAmount() {
		return disposableAmount;
	}
	public void setDisposableAmount(BigDecimal disposableAmount) {
		this.disposableAmount = disposableAmount;
	}
	public BigDecimal getLockedAmount() {
		return lockedAmount;
	}
	public void setLockedAmount(BigDecimal lockedAmount) {
		this.lockedAmount = lockedAmount;
	}
	@JSONField(serialize=false) 
	public void copyFromUser(User other){
		setId(other.getId());
		setUserName(other.getUserName());
		setCellPhone(other.getCellPhone());
		setHeadPortrait(other.getHeadPortrait());
		setStatus(other.getStatus());
		
		
	}

}
