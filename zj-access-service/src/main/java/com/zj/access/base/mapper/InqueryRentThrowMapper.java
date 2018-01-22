package com.zj.access.base.mapper;

import java.util.List;

import com.zj.entity.base.po.InqueryRentThrow;
import com.zj.entity.tm.form.InqueryRentThrowQueryForm;

public interface InqueryRentThrowMapper extends BaseMapper{
	
	public  List<InqueryRentThrow> getInqueryRentThrowPageList(InqueryRentThrowQueryForm form);
	
}