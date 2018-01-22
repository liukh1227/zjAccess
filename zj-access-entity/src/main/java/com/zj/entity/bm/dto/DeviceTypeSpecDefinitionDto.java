package com.zj.entity.bm.dto;

import java.io.Serializable;

import com.zj.entity.base.po.DeviceTypeSpecDefinition;

public class DeviceTypeSpecDefinitionDto extends DeviceTypeSpecDefinition implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8794591069096809630L;
	private String deviceTypeName;

	public String getDeviceTypeName() {
		return deviceTypeName;
	}

	public void setDeviceTypeName(String deviceTypeName) {
		this.deviceTypeName = deviceTypeName;
	}
	



}