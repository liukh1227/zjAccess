package com.zj.access.base.mapper;

import java.util.List;

import com.zj.entity.base.po.InqueryOrder;
import com.zj.entity.tm.dto.InqueryOrderDto;
import com.zj.entity.tm.form.InqueryOrderQueryForm;

public interface InqueryOrderMapper extends BaseMapper{
	
	public  List<InqueryOrder> getInqueryOrderPageList(InqueryOrderQueryForm form);
	
	public  List<InqueryOrderDto> getInqueryOrderDetailPageList(InqueryOrderQueryForm form);
	
	public Long getInqueryOrderCount(InqueryOrderQueryForm form);
	
}