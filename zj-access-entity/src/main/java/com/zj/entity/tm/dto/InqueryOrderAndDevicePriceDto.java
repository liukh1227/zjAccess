package com.zj.entity.tm.dto;



import java.math.BigDecimal;

/**
 * 
 * @author liukh
 * @date 2017-3-16 下午6:33:44
 */
public class InqueryOrderAndDevicePriceDto {
private String inqueryOrderId;
private BigDecimal totalOrderPrice;
private String inqueryDeviceId;
private BigDecimal totalDevicePrice;
public String getInqueryOrderId() {
	return inqueryOrderId;
}
public BigDecimal getTotalOrderPrice() {
	return totalOrderPrice;
}
public String getInqueryDeviceId() {
	return inqueryDeviceId;
}
public BigDecimal getTotalDevicePrice() {
	return totalDevicePrice;
}
public void setInqueryOrderId(String inqueryOrderId) {
	this.inqueryOrderId = inqueryOrderId;
}
public void setTotalOrderPrice(BigDecimal totalOrderPrice) {
	this.totalOrderPrice = totalOrderPrice;
}
public void setInqueryDeviceId(String inqueryDeviceId) {
	this.inqueryDeviceId = inqueryDeviceId;
}
public void setTotalDevicePrice(BigDecimal totalDevicePrice) {
	this.totalDevicePrice = totalDevicePrice;
}

}
