package com.zj.access.base.mapper;

import java.util.List;

import com.zj.entity.bm.dto.DeviceTypeSpecDefinitionDto;
import com.zj.entity.bm.form.DeviceTypeSpecDefinitionQueryForm;

public interface DeviceTypeSpecDefinitionMapper extends BaseMapper {
	public List<DeviceTypeSpecDefinitionDto> getDeviceTypeSpecDefinitionPageList(
			DeviceTypeSpecDefinitionQueryForm form);
}
