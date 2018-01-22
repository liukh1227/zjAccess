package com.zj.entity.base.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

@SuppressWarnings("serial")
public class RentOrderBase implements Serializable {
    private String id;

    private String lesseeSideId;

    private String lesseeSideName;

    private String leasingSideId;

    private String leasingSideName;

    private String address;

    private String city;

    private String latitude;

    private String longitude;

    private String additionalRequirement;

    private String payMethod;

    private BigDecimal price;

    private Integer status;

    private Boolean isIncludeShippingFee;

    private Boolean isIncludeInvoice;

    private Boolean isInlcudeJiShou;

    private Boolean isInlcudeFuel;

    private Boolean isInlcudeInsurance;

    private Integer orderSourceType;

    private String orderSourceId;

    private BigDecimal otherExpense;

    private String otherExpenseComment;

    private String projectId;

    private Date createTime;
    

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getLesseeSideId() {
        return lesseeSideId;
    }

    public void setLesseeSideId(String lesseeSideId) {
        this.lesseeSideId = lesseeSideId == null ? null : lesseeSideId.trim();
    }

    public String getLesseeSideName() {
        return lesseeSideName;
    }

    public void setLesseeSideName(String lesseeSideName) {
        this.lesseeSideName = lesseeSideName == null ? null : lesseeSideName.trim();
    }

    public String getLeasingSideId() {
        return leasingSideId;
    }

    public void setLeasingSideId(String leasingSideId) {
        this.leasingSideId = leasingSideId == null ? null : leasingSideId.trim();
    }

    public String getLeasingSideName() {
        return leasingSideName;
    }

    public void setLeasingSideName(String leasingSideName) {
        this.leasingSideName = leasingSideName == null ? null : leasingSideName.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude == null ? null : latitude.trim();
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude == null ? null : longitude.trim();
    }

    public String getAdditionalRequirement() {
        return additionalRequirement;
    }

    public void setAdditionalRequirement(String additionalRequirement) {
        this.additionalRequirement = additionalRequirement == null ? null : additionalRequirement.trim();
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod == null ? null : payMethod.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Boolean getIsIncludeShippingFee() {
        return isIncludeShippingFee;
    }

    public void setIsIncludeShippingFee(Boolean isIncludeShippingFee) {
        this.isIncludeShippingFee = isIncludeShippingFee;
    }

    public Boolean getIsIncludeInvoice() {
        return isIncludeInvoice;
    }

    public void setIsIncludeInvoice(Boolean isIncludeInvoice) {
        this.isIncludeInvoice = isIncludeInvoice;
    }

    public Boolean getIsInlcudeJiShou() {
        return isInlcudeJiShou;
    }

    public void setIsInlcudeJiShou(Boolean isInlcudeJiShou) {
        this.isInlcudeJiShou = isInlcudeJiShou;
    }

    public Boolean getIsInlcudeFuel() {
        return isInlcudeFuel;
    }

    public void setIsInlcudeFuel(Boolean isInlcudeFuel) {
        this.isInlcudeFuel = isInlcudeFuel;
    }

    public Boolean getIsInlcudeInsurance() {
        return isInlcudeInsurance;
    }

    public void setIsInlcudeInsurance(Boolean isInlcudeInsurance) {
        this.isInlcudeInsurance = isInlcudeInsurance;
    }

    public Integer getOrderSourceType() {
        return orderSourceType;
    }

    public void setOrderSourceType(Integer orderSourceType) {
        this.orderSourceType = orderSourceType;
    }

    public String getOrderSourceId() {
        return orderSourceId;
    }

    public void setOrderSourceId(String orderSourceId) {
        this.orderSourceId = orderSourceId == null ? null : orderSourceId.trim();
    }

    public BigDecimal getOtherExpense() {
        return otherExpense;
    }

    public void setOtherExpense(BigDecimal otherExpense) {
        this.otherExpense = otherExpense;
    }

    public String getOtherExpenseComment() {
        return otherExpenseComment;
    }

    public void setOtherExpenseComment(String otherExpenseComment) {
        this.otherExpenseComment = otherExpenseComment == null ? null : otherExpenseComment.trim();
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }
    @JSONField(format="yyyy-MM-dd HH:mm:ss" ) 
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


	@Override
	public String toString() {
		return "RentOrder [id=" + id + ", lesseeSideId=" + lesseeSideId
				+ ", lesseeSideName=" + lesseeSideName + ", leasingSideId="
				+ leasingSideId + ", leasingSideName=" + leasingSideName
				+ ", address=" + address + ", city=" + city + ", latitude="
				+ latitude + ", longitude=" + longitude
				+ ", additionalRequirement=" + additionalRequirement
				+ ", payMethod=" + payMethod + ", price=" + price + ", status="
				+ status + ", isIncludeShippingFee=" + isIncludeShippingFee
				+ ", isIncludeInvoice=" + isIncludeInvoice
				+ ", isInlcudeJiShou=" + isInlcudeJiShou + ", isInlcudeFuel="
				+ isInlcudeFuel + ", isInlcudeInsurance=" + isInlcudeInsurance
				+ ", orderSourceType=" + orderSourceType + ", orderSourceId="
				+ orderSourceId + ", otherExpense=" + otherExpense
				+ ", otherExpenseComment=" + otherExpenseComment
				+ ", projectId=" + projectId + ", createTime=" + createTime
				;
	}
	
}