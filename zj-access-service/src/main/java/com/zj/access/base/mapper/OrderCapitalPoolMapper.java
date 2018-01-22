package com.zj.access.base.mapper;

import java.util.List;

import com.zj.entity.base.po.OrderCapitalPool;
import com.zj.entity.tm.form.OrderCapitalPoolQueryForm;

public interface OrderCapitalPoolMapper  extends BaseMapper{
	
	public  List<OrderCapitalPool> getOrderCapitalPoolPageList(OrderCapitalPoolQueryForm form);
	
	public  List<OrderCapitalPool> getOrderCapitalPoolAllList(OrderCapitalPoolQueryForm form);
	
	
}