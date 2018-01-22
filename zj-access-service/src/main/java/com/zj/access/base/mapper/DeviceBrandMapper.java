package com.zj.access.base.mapper;

import java.util.List;

import com.zj.entity.base.po.DeviceBrand;
import com.zj.entity.bm.form.DeviceBrandQueryForm;

public interface DeviceBrandMapper extends BaseMapper {
    
    public  List<DeviceBrand> getDeviceBrandPageList(DeviceBrandQueryForm form);
    public  List<DeviceBrand> getDeviceBrandAllList(DeviceBrandQueryForm form);
    public  List<DeviceBrand> getExitsDeviceBrandAllList(DeviceBrandQueryForm form);
    
} 