package com.zj.entity.tm.dto;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.zj.entity.base.po.RentOrderBase;

public class RentOrderListDto extends RentOrderBase{
	
	private static final long serialVersionUID = -2846238956429988385L;
	private List<RentOrderDeviceListDto> rentOrderDevices;

	  @JSONField(name="data")
	public List<RentOrderDeviceListDto> getRentOrderDevices() {
		return rentOrderDevices;
	}

	public void setRentOrderDevices(List<RentOrderDeviceListDto> rentOrderDevices) {
		this.rentOrderDevices = rentOrderDevices;
	}
	
}
