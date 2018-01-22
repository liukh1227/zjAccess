package com.zj.access.base.mapper;

import java.util.List;
import java.util.Map;

import com.zj.entity.base.po.InqueryDevice;
import com.zj.entity.tm.dto.InqueryDeviceAllListDto;
import com.zj.entity.tm.dto.InqueryDeviceListDto;
import com.zj.entity.tm.form.InqueryDeviceQueryForm;

public interface InqueryDeviceMapper  extends BaseMapper{
	
	public  InqueryDeviceListDto selectInquerDevieDeatilinfo(Map<String,Object> params);
	
	public  List<InqueryDeviceAllListDto> getInqueryDevicePageList(InqueryDeviceQueryForm form);
	
	public  List<InqueryDevice> getInqueryDeviceAllList(String id);
	
	public  List<InqueryDevice> getInqueryDeviceList(InqueryDeviceQueryForm form);
	
	public  List<InqueryDeviceListDto> getInqueryDeviceMoreInforList(InqueryDeviceQueryForm form);
	
	public  List<InqueryDeviceListDto> getInqueryDeviceMoreInforAllList(String inqueryOrderId);
	
}