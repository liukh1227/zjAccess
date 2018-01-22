package com.zj.entity.tm.dto;
import java.math.BigDecimal;

/**
 * @author liukh
 * @date 2016-10-29 下午5:39:10
 */
public class DateTypeDto {
	private String type;
	private BigDecimal month;
	private BigDecimal day;
	private BigDecimal dateAmount;
	 
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public BigDecimal getMonth() {
		return month;
	}
	public void setMonth(BigDecimal month) {
		this.month = month;
	}
	public BigDecimal getDay() {
		return day;
	}
	public void setDay(BigDecimal day) {
		this.day = day;
	}
	
	public BigDecimal getDateAmount() {
		return dateAmount;
	}
	public void setDateAmount(BigDecimal dateAmount) {
		this.dateAmount = dateAmount;
	}
	public void copyFrom(DateTypeDto other){
		setDay(other.getDay());
		setMonth(other.getMonth());
		setType(other.getType());
		setDateAmount(other.getDateAmount());
	}

}
