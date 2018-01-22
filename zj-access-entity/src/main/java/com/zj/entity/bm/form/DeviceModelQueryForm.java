package com.zj.entity.bm.form;

import java.io.Serializable;
import com.zj.base.entity.base.form.PageQueryForm;

/**
 * 
 * @author liukh
 * @date 2017-2-7 下午3:37:07
 */
public class DeviceModelQueryForm extends PageQueryForm implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2922357133766976462L;
	private String modelName;
	private String deviceBrandId;
    private String deviceTypeId;
    
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	public String getDeviceBrandId() {
		return deviceBrandId;
	}
	public void setDeviceBrandId(String deviceBrandId) {
		this.deviceBrandId = deviceBrandId;
	}
	public String getDeviceTypeId() {
		return deviceTypeId;
	}
	public void setDeviceTypeId(String deviceTypeId) {
		this.deviceTypeId = deviceTypeId;
	}
       
}
