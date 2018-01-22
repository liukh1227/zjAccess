package com.zj.entity.tm.form;

import java.io.Serializable;
import com.zj.base.entity.base.form.PageQueryForm;

/**
 * 
 * @author liukh
 * @date 2017-2-23 上午10:06:32
 */
public class DeliveryOrderQueryForm extends PageQueryForm implements Serializable {

	private static final long serialVersionUID = -8785170824097412568L;
	private String rentOrderDeviceId;
    private String rentOrderId;
    private Integer status;
    
	public String getRentOrderDeviceId() {
		return rentOrderDeviceId;
	}
	public void setRentOrderDeviceId(String rentOrderDeviceId) {
		this.rentOrderDeviceId = rentOrderDeviceId;
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
