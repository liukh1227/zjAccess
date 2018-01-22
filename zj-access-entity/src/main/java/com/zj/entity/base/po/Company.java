package com.zj.entity.base.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.zj.common.Constant;
public class Company implements Serializable {
	private static final long serialVersionUID = -3149579191824840601L;
	private String id;
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
	private Integer status;
	private String invoiceTitle;
	private String IDCardNumber;
	private Date createTime;
	private BigDecimal totalAmount;
	private BigDecimal disposableAmount;
	private BigDecimal lockedAmount;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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
	 @JSONField(format="yyyy-MM-dd HH:mm:ss" ) 
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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
	public boolean isLeasingSide() {
		return Constant.COMPANY_BUSSINESS_LEASINGSID.equals(getCompanyBusinessType());
	}
	@JSONField(serialize=false) 
	public static boolean isLeasingSide(String companyBusinessType) {
		return Constant.COMPANY_BUSSINESS_LEASINGSID.equals(companyBusinessType);
	}
	@JSONField(serialize=false) 
	public boolean isLesseeSide() {
		return Constant.COMPANY_BUSSINESS_LESSEESIDE.equals(getCompanyBusinessType());
	}
	@JSONField(serialize=false) 
	public static boolean isLesseeSide(String companyBusinessType) {
		return Constant.COMPANY_BUSSINESS_LESSEESIDE.equals(companyBusinessType);
	}

}