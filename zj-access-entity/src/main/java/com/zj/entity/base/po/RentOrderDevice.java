package com.zj.entity.base.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class RentOrderDevice implements Serializable {
    private String id;

    private RentOrder rentOrder;

    private String deviceModelId;

    private String deviceModelName;

    private Integer quantity;

    private BigDecimal unitPrice;

    private String startTime;

    private String endTime;

    private BigDecimal saleAmount;

    private String picture;

    private String leaseTerm;

    private Integer billingType;

    private Date createTime;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public RentOrder getRentOrder() {
		return rentOrder;
	}

	public void setRentOrder(RentOrder rentOrder) {
		this.rentOrder = rentOrder;
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

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime == null ? null : startTime.trim();
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime == null ? null : endTime.trim();
    }

    public BigDecimal getSaleAmount() {
        return saleAmount;
    }

    public void setSaleAmount(BigDecimal saleAmount) {
        this.saleAmount = saleAmount;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture == null ? null : picture.trim();
    }

    public String getLeaseTerm() {
        return leaseTerm;
    }

    public void setLeaseTerm(String leaseTerm) {
        this.leaseTerm = leaseTerm == null ? null : leaseTerm.trim();
    }

    public Integer getBillingType() {
        return billingType;
    }

    public void setBillingType(Integer billingType) {
        this.billingType = billingType;
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
		return "RentOrderDevice [id=" + id + ", rentOrder=" + rentOrder
				+ ", deviceModelId=" + deviceModelId + ", deviceModelName="
				+ deviceModelName + ", quantity=" + quantity + ", unitPrice="
				+ unitPrice + ", startTime=" + startTime + ", endTime="
				+ endTime + ", saleAmount=" + saleAmount + ", picture="
				+ picture + ", leaseTerm=" + leaseTerm + ", billingType="
				+ billingType + ", createTime=" + createTime + "]";
	}
	
	@JSONField(serialize=false) 
	public void copyFrom(RentOrderDevice other) {

		setId(other.getId());
		if(other.getRentOrder()!= null && other.getRentOrder().getId()!= null){
			setRentOrder(other.getRentOrder());
		}
		setDeviceModelId(other.getDeviceModelId());
		setDeviceModelName(other.getDeviceModelName());
		setQuantity(other.getQuantity());
		setSaleAmount(other.getSaleAmount());
		setStartTime(other.getStartTime());
		setEndTime(other.getEndTime());
		setPicture(other.getPicture());
		setLeaseTerm(other.getLeaseTerm());
		setBillingType(other.getBillingType());
		setUnitPrice(other.getUnitPrice());
	}

	@JSONField(serialize=false) 
	public void copyFromInqueryDevice(InqueryDevice other) {
		setDeviceModelId(other.getDeviceModelId());
		setDeviceModelName(other.getDeviceModelName());
		setStartTime(other.getStartTime());
		setEndTime(other.getEndTime());
		setQuantity(other.getQuantity());
		setSaleAmount(other.getTotalPrice());
		setPicture(other.getPicture());
		setLeaseTerm(other.getLeaseTerm());
		setBillingType(other.getBillingType());
		setUnitPrice(other.getUnitPrice());
	}

	@JSONField(serialize=false) 
	public void copyFromInqueryRentAndInqueryRentQuote(InqueryRentQuote other, InqueryRent inqueryRent) {
		setDeviceModelId(other.getDeviceModelId());
		setDeviceModelName(other.getDeviceModelName());
		setStartTime(inqueryRent.getStartTime());
		setEndTime(inqueryRent.getEndTime());
		setQuantity(other.getQuantity());
		setSaleAmount(other.getPrice());
		setPicture(other.getPicture());
		setLeaseTerm(inqueryRent.getLeaseTerm());
		setBillingType(inqueryRent.getBillingType());
		setUnitPrice(other.getPrice());
		
	}


}