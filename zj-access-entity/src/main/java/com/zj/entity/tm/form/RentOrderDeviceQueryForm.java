package com.zj.entity.tm.form;

import java.io.Serializable;
import com.zj.base.entity.base.form.PageQueryForm;

/**
 * 
 * @author liukh
 * @date 2017-2-23 上午10:06:38
 */
public class RentOrderDeviceQueryForm extends PageQueryForm implements Serializable {

	private static final long serialVersionUID = 7831227662092146705L;
	private String rentOrderId;
    private String deviceModelId;
	
	public String getRentOrderId() {
		return rentOrderId;
	}
	public void setRentOrderId(String rentOrderId) {
		this.rentOrderId = rentOrderId;
	}
	public String getDeviceModelId() {
		return deviceModelId;
	}
	public void setDeviceModelId(String deviceModelId) {
		this.deviceModelId = deviceModelId;
	}
      
}
