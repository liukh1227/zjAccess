package com.zj.access.base.mapper;

import java.util.List;

import com.zj.entity.base.po.Device;
import com.zj.entity.dm.dto.DeviceStatisticsDto;
import com.zj.entity.dm.form.DeviceQueryForm;

public interface DeviceMapper extends BaseMapper{
	
	public  List<Device> getDevicePageList(DeviceQueryForm form);
	
	public List<Device> getDeviceDetailPageList(DeviceQueryForm form);
	
	public List<DeviceStatisticsDto> getDeviceStatisticsList(DeviceQueryForm form);
	
}