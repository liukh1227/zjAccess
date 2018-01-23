package com.zj.access.base.mapper;

import java.util.List;

import com.zj.entity.base.po.DeviceInspection;
import com.zj.entity.bm.form.DeviceInspectionQueryForm;

public interface DeviceInspectionMapper extends BaseMapper {
	public  List<DeviceInspection> getDeviceInspectionPageList(DeviceInspectionQueryForm form);
}