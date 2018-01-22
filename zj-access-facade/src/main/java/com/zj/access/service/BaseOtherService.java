package com.zj.access.service;

import com.zj.entity.base.po.CompanyDeviceType;
import com.zj.entity.bm.dto.UserDto;

public interface BaseOtherService {
	public UserDto getUserDtoByComapnyId(String companyId);
	
	public CompanyDeviceType getCompanyDeviceTypeByComapnyIdAndModelId(String companyId,String modelId);

}
