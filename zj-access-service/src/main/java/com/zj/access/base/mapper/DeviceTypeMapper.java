package com.zj.access.base.mapper;

import java.util.List;

import com.zj.entity.base.po.DeviceType;
import com.zj.entity.bm.form.DeviceTypeQueryForm;

public interface DeviceTypeMapper extends BaseMapper{
	 public  List<DeviceType> getDeviceTypePageList(DeviceTypeQueryForm form);
}