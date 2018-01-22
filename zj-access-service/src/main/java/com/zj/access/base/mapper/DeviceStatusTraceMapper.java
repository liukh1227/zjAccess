package com.zj.access.base.mapper;

import java.util.List;

import com.zj.entity.base.po.DeviceStatusTrace;
import com.zj.entity.dm.form.DeviceStatusTraceQueryForm;

public interface DeviceStatusTraceMapper extends BaseMapper{
	
	public  List<DeviceStatusTrace> getDeviceStatusTracePageList(DeviceStatusTraceQueryForm form);
	
}