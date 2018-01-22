package com.zj.entity.dm.dto;

import java.io.Serializable;

public class DeviceDetailInforListDto extends DeviceListDto implements Serializable{

	private static final long serialVersionUID = 2589497003361536079L;
	private Boolean isPassed;
	private String content;
	private String DeviceTypeSpecDefinitionName;
	private String deviceTypeSpecDataValue;
	private String unit;
    private String modelQuote;
    
	public Boolean getIsPassed() {
		return isPassed;
	}
	public void setIsPassed(Boolean isPassed) {
		this.isPassed = isPassed;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDeviceTypeSpecDefinitionName() {
		return DeviceTypeSpecDefinitionName;
	}
	public void setDeviceTypeSpecDefinitionName(String deviceTypeSpecDefinitionName) {
		DeviceTypeSpecDefinitionName = deviceTypeSpecDefinitionName;
	}
	public String getDeviceTypeSpecDataValue() {
		return deviceTypeSpecDataValue;
	}
	public void setDeviceTypeSpecDataValue(String deviceTypeSpecDataValue) {
		this.deviceTypeSpecDataValue = deviceTypeSpecDataValue;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	public String getModelQuote() {
		return modelQuote;
	}
	
	public void setModelQuote(String modelQuote) {
		this.modelQuote = modelQuote;
		if(modelQuote != null && modelQuote.indexOf("|")>-1 && modelQuote.split("\\|") != null && modelQuote.split("\\|").length > 1){
			String[] modelQutoArray = modelQuote.split("\\|");
			setRentPerMonth(modelQutoArray[0]);
			setRentPerDay(modelQutoArray[1]);
		}
	}

	
	 
}
