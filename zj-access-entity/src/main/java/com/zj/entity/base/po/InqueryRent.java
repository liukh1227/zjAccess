package com.zj.entity.base.po;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

public class InqueryRent extends InqueryRentBase {
   
    
    private List<InqueryRentQuote> inqueryRentQuotes;

    private static final long serialVersionUID = 1L;

   
    @JSONField(name="data")  
	public List<InqueryRentQuote> getInqueryRentQuotes() {
		return inqueryRentQuotes;
	}

	public void setInqueryRentQuotes(List<InqueryRentQuote> inqueryRentQuotes) {
		this.inqueryRentQuotes = inqueryRentQuotes;
	}

	@Override
	public String toString() {
		return "InqueryRent [inqueryRentQuotes=" + inqueryRentQuotes + "]";
	}


}