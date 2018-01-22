package com.zj.access.base.mapper;

import java.util.List;

import com.zj.entity.base.po.DeliveryOrder;
import com.zj.entity.tm.form.DeliveryOrderQueryForm;

public interface DeliveryOrderMapper extends BaseMapper{
	
	public  List<DeliveryOrder> getDeliveryOrderPageList(DeliveryOrderQueryForm form);
	
	public List<DeliveryOrder> getDeliveryOrderAllList(DeliveryOrderQueryForm form);
	
}