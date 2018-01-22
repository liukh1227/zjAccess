package com.zj.entity.base.po;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

public class InqueryOrder extends InqueryOrderBase {

    
    private List<InqueryDevice> inqueryDevices;

    private static final long serialVersionUID = 1L;


    @JSONField(name = "data")
	public List<InqueryDevice> getInqueryDevices() {
		return inqueryDevices;
	}

	public void setInqueryDevices(List<InqueryDevice> inqueryDevices) {
		this.inqueryDevices = inqueryDevices;
	}

	@Override
	public String toString() {
		return "InqueryOrder [inqueryDevices=" + inqueryDevices + "]";
	}

	

}