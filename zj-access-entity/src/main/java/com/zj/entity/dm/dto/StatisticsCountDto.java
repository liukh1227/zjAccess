/**
 * 
 */
package com.zj.entity.dm.dto;

import java.io.Serializable;

/**
 * @author liukh
 * @date 2016-11-25 上午10:20:41
 */
public class StatisticsCountDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9110009414245746994L;
	private String type;
	private Integer amount;
	public String getType() {
		return type;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	
}
