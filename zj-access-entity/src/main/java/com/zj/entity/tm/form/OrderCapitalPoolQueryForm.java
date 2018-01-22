package com.zj.entity.tm.form;

import java.io.Serializable;
import com.zj.base.entity.base.form.PageQueryForm;

/**
 * 
 * @author liukh
 * @date 2017-2-23 上午10:06:32
 */
public class OrderCapitalPoolQueryForm extends PageQueryForm implements Serializable {

	private static final long serialVersionUID = 2871838515185625969L;
	private String capitalSideId;
    private String rentOrderId;
    private Integer status;
    
	public String getCapitalSideId() {
		return capitalSideId;
	}
	public void setCapitalSideId(String capitalSideId) {
		this.capitalSideId = capitalSideId;
	}
	public String getRentOrderId() {
		return rentOrderId;
	}
	public void setRentOrderId(String rentOrderId) {
		this.rentOrderId = rentOrderId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
    
	

}
