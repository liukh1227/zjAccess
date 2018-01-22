package com.zj.entity.base.po;


public class InqueryRentQuote extends InqueryRentQuoteBase {
 

    private InqueryRent inqueryRent;

    
    private static final long serialVersionUID = 1L;


	public InqueryRent getInqueryRent() {
		return inqueryRent;
	}


	public void setInqueryRent(InqueryRent inqueryRent) {
		this.inqueryRent = inqueryRent;
	}


	@Override
	public String toString() {
		return "InqueryRentQuote [inqueryRent=" + inqueryRent + "]";
	}

  


}