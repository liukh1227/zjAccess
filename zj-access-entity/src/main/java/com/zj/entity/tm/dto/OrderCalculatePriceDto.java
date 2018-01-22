package com.zj.entity.tm.dto;

import java.math.BigDecimal;

public class OrderCalculatePriceDto extends DateTypeDto {
	private BigDecimal totalPrice;
	private BigDecimal unitPrice;
	private BigDecimal timeTotal;
	private BigDecimal otherExpense;
	private Integer quantity;
	public BigDecimal getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}
	public BigDecimal getTimeTotal() {
		return timeTotal;
	}
	public void setTimeTotal(BigDecimal timeTotal) {
		this.timeTotal = timeTotal;
	}
	public BigDecimal getOtherExpense() {
		return otherExpense;
	}
	public void setOtherExpense(BigDecimal otherExpense) {
		this.otherExpense = otherExpense;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
}
