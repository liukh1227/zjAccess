package com.zj.entity.base.po;

import java.math.BigDecimal;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.zj.common.Constant;

public class RentOrder extends RentOrderBase {
   
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -7365238938797235593L;
	private List<RentOrderDevice> rentOrderDevices;

   
    @JSONField(name="data")
	public List<RentOrderDevice> getRentOrderDevices() {
		return rentOrderDevices;
	}

	public void setRentOrderDevices(List<RentOrderDevice> rentOrderDevices) {
		this.rentOrderDevices = rentOrderDevices;
	}

	
	
	
	@Override
	public String toString() {
		return "RentOrder [rentOrderDevices=" + rentOrderDevices + "]";
	}

	@JSONField(serialize=false) 
	public void copyFromInqueryOrder(InqueryOrder other) {
		setLesseeSideId(other.getLesseeSideId());
		setLesseeSideName(other.getLesseeSideName());
		setLeasingSideId(other.getLeasingSideId());
		setLeasingSideName(other.getLeasingSideName());
		setAdditionalRequirement(other.getAdditionalRequirement()!= null ? other.getAdditionalRequirement():"");
		setPayMethod(other.getPayMethod());
		BigDecimal totalOffer = BigDecimal.ZERO;
		if(other.getTotalOffer()!= null){
			totalOffer = totalOffer.add(other.getTotalOffer());
		}
		if(other.getOtherExpense() != null){
			totalOffer = totalOffer.add(other.getOtherExpense());
		}
		setPrice(other.getTotalOffer());
		setIsIncludeInvoice(other.getIsIncludeInvoice());
		setIsIncludeShippingFee(other.getIsIncludeShippingFee());
		setIsInlcudeFuel(other.getIsInlcudeFuel());
		setIsInlcudeInsurance(other.getIsInlcudeInsurance());
		setIsInlcudeJiShou(other.getIsInlcudeJiShou());
		setOrderSourceType(Constant.ORDERSOURCETYPE_INQUERYORDER);
		setOrderSourceId(other.getId());
		setAddress(other.getAddress() != null ? other.getAddress() : "");
		setLatitude(other.getLatitude() != null ? other.getLatitude() : "");
		setLongitude(other.getLongitude() != null ? other.getLongitude() : "");
		setOtherExpense(other.getOtherExpense() != null ? other.getOtherExpense():BigDecimal.ZERO);
		setOtherExpenseComment(other.getOtherExpenseComment() != null ? other.getOtherExpenseComment() : "");
		setCity(other.getCity());
		setProjectId(other.getProjectId());

	}
	
	@JSONField(serialize=false) 
	public void copyFromInqueryRentOrder(InqueryRent other,InqueryRentQuote rentQuote) {
		setLesseeSideId(other.getLesseeSideId());
		setLesseeSideName(other.getLesseeSideName());
		setLeasingSideId(rentQuote.getLeasingSideId());
		setLeasingSideName(rentQuote.getLeasingSideName());
		setAdditionalRequirement(other.getAdditionalRequirement()!= null ? other.getAdditionalRequirement():"");
		setPayMethod(other.getPayMethod());
		setPrice(rentQuote.getTotalPrice());
		setIsIncludeInvoice(rentQuote.getIsIncludeInvoice());
		setIsIncludeShippingFee(rentQuote.getIsIncludeShippingFee());
		setIsInlcudeFuel(rentQuote.getIsInlcudeFuel());
		setIsInlcudeInsurance(rentQuote.getIsInlcudeInsurance());
		setIsInlcudeJiShou(rentQuote.getIsInlcudeJiShou());
		setOrderSourceType(Constant.ORDERSOURCETYPE_INQUERYRENT);
		setOrderSourceId(other.getId());
		setAddress(other.getAddress() != null ? other.getAddress() : "");
		setLatitude(other.getLatitude() != null ? other.getLatitude() : "");
		setLongitude(other.getLongitude() != null ? other.getLongitude() : "");
		setOtherExpense(rentQuote.getOtherExpense() != null ? rentQuote.getOtherExpense():BigDecimal.ZERO);
		setOtherExpenseComment(rentQuote.getOtherExpenseComment() != null ? rentQuote.getOtherExpenseComment() : "");
		setCity(other.getCity());
		setProjectId(other.getProjectId());
	}

	@JSONField(serialize=false) 
	public void copyFromInqueryRentThrow(InqueryRentThrow other) {
		setLesseeSideId(other.getLesseeSideId());
		setLesseeSideName(other.getLesseeSideName());
		setLeasingSideId(other.getResponseLeasingSideId());
		setLeasingSideName(other.getResponseLeasingSideName());
		setAdditionalRequirement(other.getAdditionalRequirement()!= null ? other.getAdditionalRequirement():"");
		setPayMethod(Constant.PAYMETHOD_ONLINE);
		setStatus(Constant.RENTORDER_STATUS_UNGO);
		setPrice(other.getRentFee());
		setIsIncludeInvoice(other.getIsIncludeInvoice());
		setIsIncludeShippingFee(other.getIsIncludeShippingFee());
		setIsInlcudeFuel(other.getIsInlcudeFuel());
		setIsInlcudeInsurance(other.getIsInlcudeInsurance());
		setIsInlcudeJiShou(other.getIsInlcudeJiShou());
		setOrderSourceType(Constant.ORDERSOURCETYPE_INQUERYRENTTHROW);
		setOrderSourceId(other.getId());
		setAddress(other.getAddress() != null ? other.getAddress() : "");
		setLatitude(other.getLatitude() != null ? other.getLatitude() : "");
		setLongitude(other.getLongitude() != null ? other.getLongitude() : "");
		setProjectId(other.getProjectId());
	}


}