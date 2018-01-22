package com.zj.access.base.mapper;

import java.util.List;

import com.zj.entity.tm.dto.RentOrderListDto;
import com.zj.entity.tm.form.RentOrderQueryForm;

public interface RentOrderMapper extends BaseMapper{
	
	public  List<RentOrderListDto> getRentOrderPageList(RentOrderQueryForm form);

	public long getRentOrderCount(RentOrderQueryForm form);
	
}