package com.zj.entity.bm.dto;

import java.io.Serializable;

import com.zj.entity.base.po.DeviceTypeSpecData;

public class DeviceTypeSpecDataDto extends DeviceTypeSpecData implements Serializable{
	private static final long serialVersionUID = -6983968160379458882L;

	private String definitionName;
	private String unit;
	private String deviceTypeName;

	public String getDefinitionName() {
		return definitionName;
	}
	public void setDefinitionName(String definitionName) {
		this.definitionName = definitionName;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	public String getDeviceTypeName() {
		return deviceTypeName;
	}
	public void setDeviceTypeName(String deviceTypeName) {
		this.deviceTypeName = deviceTypeName;
	}
	
	
}
