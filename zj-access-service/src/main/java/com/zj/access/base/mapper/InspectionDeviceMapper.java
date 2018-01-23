package com.zj.access.base.mapper;

import java.util.List;
import java.util.Map;

import com.zj.entity.tm.dto.InspectionDevice;
import com.zj.entity.tm.form.InspectionDeviceQueryForm;

public interface InspectionDeviceMapper extends BaseMapper{
	
    public InspectionDevice getInspectionDevice(Map<String,Object> params);
    
    public  List<InspectionDevice> getInspectionDevicePageList(InspectionDeviceQueryForm form);
    
    
    
    
    
	
}