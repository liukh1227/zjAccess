package com.zj.access.base.mapper;

import java.util.List;

import com.zj.entity.base.po.RentOrderDevice;
import com.zj.entity.tm.dto.RentOrderDeviceListDto;
import com.zj.entity.tm.form.RentOrderDeviceQueryForm;

public interface RentOrderDeviceMapper extends BaseMapper{
	
	public  List<RentOrderDevice> getRentOrderDevicePageList(RentOrderDeviceQueryForm form);
	
	public  List<RentOrderDeviceListDto> getRentOrderDeviceDeatilAllList(String rentOrderId);
	
	
}