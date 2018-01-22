package com.zj.access.base.mapper;

import java.util.List;

import com.zj.entity.base.po.OrderInteractiveTrace;
import com.zj.entity.tm.form.OrderInteractiveTraceQueryForm;

public interface OrderInteractiveTraceMapper  extends BaseMapper{
	
	public  List<OrderInteractiveTrace> getOrderInteractiveTracePageList(OrderInteractiveTraceQueryForm form);
	
}