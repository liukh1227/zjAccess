package com.zj.access.base.mapper;

import java.util.List;
import java.util.Map;

import com.zj.entity.bm.dto.DeviceTypeSpecDataDto;
import com.zj.entity.bm.form.DeviceTypeSpecDataQueryForm;

public interface DeviceTypeSpecDataMapper extends BaseMapper {
	public List<DeviceTypeSpecDataDto> getDeviceTypeSpecDataPageList(DeviceTypeSpecDataQueryForm form);
	
	public List<DeviceTypeSpecDataDto> getKeyAttributeDeviceTypeSpecDataList(Map<String,Object> params);
}
