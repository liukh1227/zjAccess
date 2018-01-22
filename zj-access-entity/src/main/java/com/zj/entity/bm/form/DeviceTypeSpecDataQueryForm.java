package com.zj.entity.bm.form;

import java.io.Serializable;

import com.zj.base.entity.base.form.PageQueryForm;

/**
 * 
 * @author liukh
 * @date 2017-2-7 下午3:37:07
 */
public class DeviceTypeSpecDataQueryForm extends PageQueryForm implements Serializable {
	

	private static final long serialVersionUID = -915620519550253264L;
	private String definitionId;
    private String deviceTypeId;
	public String getDefinitionId() {
		return definitionId;
	}
	public void setDefinitionId(String definitionId) {
		this.definitionId = definitionId;
	}
	public String getDeviceTypeId() {
		return deviceTypeId;
	}
	public void setDeviceTypeId(String deviceTypeId) {
		this.deviceTypeId = deviceTypeId;
	}
 
  
}
