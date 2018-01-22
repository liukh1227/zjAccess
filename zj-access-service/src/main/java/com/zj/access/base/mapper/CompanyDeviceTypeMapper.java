package com.zj.access.base.mapper;

import java.util.List;

import com.zj.entity.base.po.CompanyDeviceType;
import com.zj.entity.dm.form.CompanyDeviceTypeQueryForm;

public interface CompanyDeviceTypeMapper extends BaseMapper{
	
	public  List<CompanyDeviceType> getCompanyDeviceTypePageList(CompanyDeviceTypeQueryForm form);
	
}