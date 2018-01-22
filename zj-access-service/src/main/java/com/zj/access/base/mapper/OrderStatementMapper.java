package com.zj.access.base.mapper;

import java.util.List;
import java.util.Map;

import com.zj.entity.base.po.OrderStatement;
import com.zj.entity.tm.dto.OrderStatementContainComanyNameDto;
import com.zj.entity.tm.form.OrderStatementQueryForm;

public interface OrderStatementMapper extends BaseMapper{
	
	public  List<OrderStatement> getOrderStatementPageList(OrderStatementQueryForm form);
	
	public  List<OrderStatementContainComanyNameDto> getOrderStatementDetailPageList(OrderStatementQueryForm form);
	
	public OrderStatementContainComanyNameDto getOrderStatementDetailInfor(Map<String,Object> paramsMap);
	
	public long getOrderStatementCount(OrderStatementQueryForm form);
	
}