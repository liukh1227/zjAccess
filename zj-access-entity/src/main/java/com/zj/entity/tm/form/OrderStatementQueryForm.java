package com.zj.entity.tm.form;

import java.io.Serializable;

import com.zj.base.entity.base.form.PageQueryForm;

/**
 * 
 * @author liukh
 * @date 2017-2-23 上午10:06:32
 */
public class OrderStatementQueryForm extends PageQueryForm implements
		Serializable {

	private static final long serialVersionUID = 6980094364895692985L;
	private String rentOrderId;
	private Integer statementType;
	private String status;
	private String currentHandler;
	private Integer[] statusArrayList;
	
	public String getRentOrderId() {
		return rentOrderId;
	}

	public void setRentOrderId(String rentOrderId) {
		this.rentOrderId = rentOrderId;
	}

	public Integer getStatementType() {
		return statementType;
	}

	public void setStatementType(Integer statementType) {
		this.statementType = statementType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCurrentHandler() {
		return currentHandler;
	}

	public void setCurrentHandler(String currentHandler) {
		this.currentHandler = currentHandler;
	}
	public Integer[] getStatusArrayList() {
		String[] strArratList = getStatus().split(",");
		if(strArratList!= null){
			statusArrayList = new Integer[strArratList.length];
			for(int i=0;i<strArratList.length;i++){
				statusArrayList[i]=Integer.parseInt(strArratList[i]);   
			}
			
		}
		return statusArrayList;
	}
	
	
	public void setStatusArrayList(Integer[] statusArrayList) {
		this.statusArrayList = statusArrayList;
	}
	

}
