package com.zj.entity.sm.dto;

import com.zj.entity.base.po.Message;

public class MessageDto extends Message {
	

	private static final long serialVersionUID = 8783238976420322520L;
	private String operatorPicture;
	private String operatorCompanyName;
	public String getOperatorPicture() {
		return operatorPicture;
	}
	public void setOperatorPicture(String operatorPicture) {
		this.operatorPicture = operatorPicture;
	}
	public String getOperatorCompanyName() {
		return operatorCompanyName;
	}
	public void setOperatorCompanyName(String operatorCompanyName) {
		this.operatorCompanyName = operatorCompanyName;
	}
	
}
