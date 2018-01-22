package com.zj.access.base.mapper;

import java.util.List;

import com.zj.entity.base.po.DeviceModel;
import com.zj.entity.bm.form.DeviceModelQueryForm;
public interface DeviceModelMapper extends BaseMapper{
    public  List<DeviceModel> getDeviceModelPageList(DeviceModelQueryForm form);
    
    public List<DeviceModel> getAllDeviceModeIdlList();
}