package com.zj.entity.base.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class InqueryRentQuoteBase implements Serializable {
    private String id;

    private String deviceModelId;

    private String deviceModelName;

    private Integer quantity;

    private BigDecimal price;

    private Boolean isIncludeShippingFee;

    private Boolean isIncludeInvoice;

    private Boolean isInlcudeJiShou;

    private Boolean isInlcudeFuel;

    private Boolean isInlcudeInsurance;

    private String payMethod;

    private BigDecimal otherExpense;

    private String otherExpenseComment;

    private String picture;

    private BigDecimal totalPrice;

    private String leasingSideId;

    private String leasingSideName;

    private String creator;

    private Integer status;

    private String currentHandler;

    private Date createTime;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }
    
 
	public String getDeviceModelId() {
        return deviceModelId;
    }

    public void setDeviceModelId(String deviceModelId) {
        this.deviceModelId = deviceModelId == null ? null : deviceModelId.trim();
    }

    public String getDeviceModelName() {
        return deviceModelName;
    }

    public void setDeviceModelName(String deviceModelName) {
        this.deviceModelName = deviceModelName == null ? null : deviceModelName.trim();
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture == null ? null : picture.trim();
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
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

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCurrentHandler() {
        return currentHandler;
    }

    public void setCurrentHandler(String currentHandler) {
        this.currentHandler = currentHandler == null ? null : currentHandler.trim();
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
		return "InqueryRentQuoteBase [id=" + id + ", deviceModelId="
				+ deviceModelId + ", deviceModelName=" + deviceModelName
				+ ", quantity=" + quantity + ", price=" + price
				+ ", isIncludeShippingFee=" + isIncludeShippingFee
				+ ", isIncludeInvoice=" + isIncludeInvoice
				+ ", isInlcudeJiShou=" + isInlcudeJiShou + ", isInlcudeFuel="
				+ isInlcudeFuel + ", isInlcudeInsurance=" + isInlcudeInsurance
				+ ", payMethod=" + payMethod + ", otherExpense=" + otherExpense
				+ ", otherExpenseComment=" + otherExpenseComment + ", picture="
				+ picture + ", totalPrice=" + totalPrice + ", leasingSideId="
				+ leasingSideId + ", leasingSideName=" + leasingSideName
				+ ", creator=" + creator + ", status=" + status
				+ ", currentHandler=" + currentHandler + ", createTime="
				+ createTime + "]";
	}

	


}