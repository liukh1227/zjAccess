package com.zj.access.base.mapper;

import java.util.List;
import java.util.Map;

import com.zj.entity.base.po.InqueryRent;
import com.zj.entity.tm.dto.InqueryRentDetailDto;
import com.zj.entity.tm.dto.InqueryRentDetailListDto;
import com.zj.entity.tm.form.InqueryRentQueryForm;

public interface InqueryRentMapper extends BaseMapper{
	
	public  List<InqueryRent> getInqueryRentPageList(InqueryRentQueryForm form);
	
	public InqueryRentDetailDto getInqueryRentDetail(Map<String,Object> params);
	
	public List<InqueryRentDetailListDto> getCanQuotoInqueryRentPageList(InqueryRentQueryForm form);
	
	public List<InqueryRentDetailDto> getInqueryRentDetailPageList(InqueryRentQueryForm form);
	
	public long getInqueryRentCount(InqueryRentQueryForm form);
	
	
	
}