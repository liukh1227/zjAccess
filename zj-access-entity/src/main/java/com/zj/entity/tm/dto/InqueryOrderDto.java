package com.zj.entity.tm.dto;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.zj.entity.base.po.InqueryOrderBase;

public class InqueryOrderDto extends  InqueryOrderBase{
	
	private static final long serialVersionUID = -6791732669354015429L;
	private List<InqueryDeviceListDto> inqueryDevicesProcess;
	  @JSONField(name = "data")
	public List<InqueryDeviceListDto> getInqueryDevicesProcess() {
		return inqueryDevicesProcess;
	}

	public void setInqueryDevicesProcess(List<InqueryDeviceListDto> inqueryDevicesProcess) {
		this.inqueryDevicesProcess = inqueryDevicesProcess;
	}
	
	@JSONField(serialize=false) 
	public void copyFrom(InqueryOrderBase other){
		setId(other.getId());
		setLeasingSideId(other.getLeasingSideId());
		setLeasingSideName(other.getLeasingSideName());
		setLesseeSideId(other.getLesseeSideId());
		setLesseeSideName(other.getLesseeSideName());
		setAdditionalRequirement(other.getAdditionalRequirement());
		setAddress(other.getAddress());
		setCity(other.getCity());
		setLatitude(other.getLatitude());
		setLongitude(other.getLongitude());
		setIsIncludeShippingFee(other.getIsIncludeShippingFee());
		setIsIncludeInvoice(other.getIsIncludeInvoice());
		setIsInlcudeJiShou(other.getIsInlcudeJiShou());
		setIsInlcudeFuel(other.getIsInlcudeFuel());
		setIsInlcudeInsurance(other.getIsInlcudeInsurance());
		setCurrentHandler(other.getCurrentHandler());
		setPayMethod(other.getPayMethod());
		setOtherExpense(other.getOtherExpense());
		setOtherExpenseComment(other.getOtherExpenseComment());
		setTotalOffer(other.getTotalOffer());
		setCommiterId(other.getCommiterId());
		setProjectId(other.getProjectId());
		setStatus(other.getStatus());
		setCreateTime(other.getCreateTime());
	
	}

	@Override
	public String toString() {
		return "InqueryOrderDto [inqueryDevicesProcess="
				+ inqueryDevicesProcess + "]";
	}
	
	
	 
}
