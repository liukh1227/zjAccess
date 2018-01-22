package com.zj.entity.tm.dto;

import java.io.Serializable;

import com.zj.entity.base.po.InqueryRentQuoteBase;

public class InqueryRentQuoteDto extends InqueryRentQuoteBase implements Serializable {
   private String inqueryRentId;

    private static final long serialVersionUID = 1L;

	public String getInqueryRentId() {
		return inqueryRentId;
	}

	public void setInqueryRentId(String inqueryRentId) {
		this.inqueryRentId = inqueryRentId;
	}

   

	


}