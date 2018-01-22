package com.zj.access.base.mapper;

import java.util.List;
import java.util.Map;

import com.zj.entity.base.po.InqueryRentQuote;
import com.zj.entity.tm.dto.InqueryRentQuotoAndInqueryRentDto;
import com.zj.entity.tm.form.InqueryRentQuoteQueryForm;

public interface InqueryRentQuoteMapper extends BaseMapper{
	
	public  List<InqueryRentQuote> getInqueryRentQuotePageList(InqueryRentQuoteQueryForm form);
	
	public  List<InqueryRentQuote> getInqueryRentAndQuotePageList(InqueryRentQuoteQueryForm form);
	
	public InqueryRentQuotoAndInqueryRentDto getInqueryRentAndQuoteDetail(Map<String,Object> params);
	
}