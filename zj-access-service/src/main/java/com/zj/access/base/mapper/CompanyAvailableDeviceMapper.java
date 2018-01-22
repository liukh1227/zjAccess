package com.zj.access.base.mapper;

import java.util.List;
import java.util.Map;
import com.zj.entity.dm.dto.CompanyAvailableDevice;
import com.zj.entity.dm.form.CompanyAvailableDeviceQueryForm;

public interface CompanyAvailableDeviceMapper extends BaseMapper{
	
	public  List<CompanyAvailableDevice> getCompanyAvailableDevicePageList(CompanyAvailableDeviceQueryForm form);
	
	public  List<CompanyAvailableDevice> getCompanyAvailableDeviceList(CompanyAvailableDeviceQueryForm form);
	
    public CompanyAvailableDevice getCompanyAvailableDevice(Map<String,Object> params);
    
    public CompanyAvailableDevice getCompanyNotAvailableDevice(Map<String,Object> params);
    
    
	
}