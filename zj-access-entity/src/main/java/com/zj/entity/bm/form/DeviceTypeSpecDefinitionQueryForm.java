package com.zj.entity.bm.form;

import java.io.Serializable;

import com.zj.base.entity.base.form.PageQueryForm;

/**
 * 
 * @author liukh
 * @date 2017-2-7 下午3:37:07
 */
public class DeviceTypeSpecDefinitionQueryForm extends PageQueryForm implements Serializable  {
	
	private static final long serialVersionUID = -8853218995297543777L;
	private String attributeName;
    private String deviceTypeId;
    private Boolean isDisplay;
    private Boolean isKeyAttribute;
    
	public String getAttributeName() {
		return attributeName;
	}
	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}
	public String getDeviceTypeId() {
		return deviceTypeId;
	}
	public void setDeviceTypeId(String deviceTypeId) {
		this.deviceTypeId = deviceTypeId;
	}
	public Boolean getIsDisplay() {
		return isDisplay;
	}
	public void setIsDisplay(Boolean isDisplay) {
		this.isDisplay = isDisplay;
	}
	public Boolean getIsKeyAttribute() {
		return isKeyAttribute;
	}
	public void setIsKeyAttribute(Boolean isKeyAttribute) {
		this.isKeyAttribute = isKeyAttribute;
	}
}
