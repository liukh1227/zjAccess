package com.zj.entity.base.po;

import java.io.Serializable;

public class InqueryDevice extends InqueryDeviceBase implements Serializable {
   
    private InqueryOrder inqueryOrder;

    private static final long serialVersionUID = 1L;


    public InqueryOrder getInqueryOrder() {
		return inqueryOrder;
	}

	public void setInqueryOrder(InqueryOrder inqueryOrder) {
		this.inqueryOrder = inqueryOrder;
	}


}