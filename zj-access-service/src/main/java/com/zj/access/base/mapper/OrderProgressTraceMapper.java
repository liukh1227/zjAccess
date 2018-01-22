package com.zj.access.base.mapper;

import java.util.List;

import com.zj.entity.base.po.OrderProgressTrace;
import com.zj.entity.tm.form.OrderProgressTraceQueryForm;

public interface OrderProgressTraceMapper  extends BaseMapper{
	
	public  List<OrderProgressTrace> getOrderProgressTracePageList(OrderProgressTraceQueryForm form);
	
}