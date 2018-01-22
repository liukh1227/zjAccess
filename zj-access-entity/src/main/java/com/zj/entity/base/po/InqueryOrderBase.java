package com.zj.entity.base.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class InqueryOrderBase implements Serializable {
    private String id;

    private String leasingSideId;

    private String leasingSideName;

    private String lesseeSideId;

    private String lesseeSideName;

    private String additionalRequirement;

    private String address;

    private String city;

    private String latitude;

    private String longitude;

    private Boolean isIncludeShippingFee;

    private Boolean isIncludeInvoice;

    private Boolean isInlcudeJiShou;

    private Boolean isInlcudeFuel;

    private Boolean isInlcudeInsurance;

    private String currentHandler;

    private String payMethod;

    private BigDecimal otherExpense;

    private String otherExpenseComment;

    private BigDecimal totalOffer;

    private String commiterId;

    private String projectId;

    private Integer status;

    private Date createTime;
    

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
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

    public String getAdditionalRequirement() {
        return additionalRequirement;
    }

    public void setAdditionalRequirement(String additionalRequirement) {
        this.additionalRequirement = additionalRequirement == null ? null : additionalRequirement.trim();
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

    public String getCurrentHandler() {
        return currentHandler;
    }

    public void setCurrentHandler(String currentHandler) {
        this.currentHandler = currentHandler == null ? null : currentHandler.trim();
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod == null ? null : payMethod.trim();
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

 
    public BigDecimal getTotalOffer() {
        return totalOffer;
    }

    public void setTotalOffer(BigDecimal totalOffer) {
        this.totalOffer = totalOffer;
    }

    public String getCommiterId() {
        return commiterId;
    }

    public void setCommiterId(String commiterId) {
        this.commiterId = commiterId == null ? null : commiterId.trim();
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
		return "InqueryOrder [id=" + id + ", leasingSideId=" + leasingSideId
				+ ", leasingSideName=" + leasingSideName + ", lesseeSideId="
				+ lesseeSideId + ", lesseeSideName=" + lesseeSideName
				+ ", additionalRequirement=" + additionalRequirement
				+ ", address=" + address + ", city=" + city + ", latitude="
				+ latitude + ", longitude=" + longitude
				+ ", isIncludeShippingFee=" + isIncludeShippingFee
				+ ", isIncludeInvoice=" + isIncludeInvoice
				+ ", isInlcudeJiShou=" + isInlcudeJiShou + ", isInlcudeFuel="
				+ isInlcudeFuel + ", isInlcudeInsurance=" + isInlcudeInsurance
				+ ", currentHandler=" + currentHandler + ", payMethod="
				+ payMethod + ", otherExpense=" + otherExpense
				+ ", otherExpenseComment=" + otherExpenseComment
				+ ", totalOffer=" + totalOffer + ", commiterId=" + commiterId
				+ ", projectId=" + projectId + ", status=" + status
				+ ", createTime=" + createTime + "]";
	}


}