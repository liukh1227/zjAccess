package com.zj.entity.tm.dto;

import com.zj.entity.base.po.InqueryDeviceBase;

public class InqueryDeviceAllListDto extends  InqueryDeviceBase{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5408163792675662767L;
	private String inqueryOrderId;
	
	public String getInqueryOrderId() {
		return inqueryOrderId;
	}
	public void setInqueryOrderId(String inqueryOrderId) {
		this.inqueryOrderId = inqueryOrderId;
	}
	
	
}
