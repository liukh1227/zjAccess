package com.zj.entity.tm.dto;



import java.math.BigDecimal;
import java.util.List;

/**
 * 
 * @author liukh
 * @date 2017-3-16 下午6:33:31
 */
public class InqueryDeviceUpdatePreview {
	private String payMethod;
	private BigDecimal otherExpense;
	private String otherExpenseComment;
	private Boolean isIncludeShippingFee;
	private Boolean isIncludeInvoice;
	private Boolean isInlcudeJiShou;
	private Boolean isInlcudeFuel;
	private Boolean isInlcudeInsurance;
	private Integer status;
	private List<InqueryDeviceUpdatePreview.DeviceUpdatePrice> data;
	
	public String getPayMethod() {
		return payMethod;
	}
	public BigDecimal getOtherExpense() {
		return otherExpense;
	}
	public String getOtherExpenseComment() {
		return otherExpenseComment;
	}
	public Boolean getIsIncludeShippingFee() {
		return isIncludeShippingFee;
	}
	public Boolean getIsIncludeInvoice() {
		return isIncludeInvoice;
	}
	public Boolean getIsInlcudeJiShou() {
		return isInlcudeJiShou;
	}
	public Boolean getIsInlcudeFuel() {
		return isInlcudeFuel;
	}
	public Boolean getIsInlcudeInsurance() {
		return isInlcudeInsurance;
	}
	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}
	public void setOtherExpense(BigDecimal otherExpense) {
		this.otherExpense = otherExpense;
	}
	public void setOtherExpenseComment(String otherExpenseComment) {
		this.otherExpenseComment = otherExpenseComment;
	}
	public void setIsIncludeShippingFee(Boolean isIncludeShippingFee) {
		this.isIncludeShippingFee = isIncludeShippingFee;
	}
	public void setIsIncludeInvoice(Boolean isIncludeInvoice) {
		this.isIncludeInvoice = isIncludeInvoice;
	}
	public void setIsInlcudeJiShou(Boolean isInlcudeJiShou) {
		this.isInlcudeJiShou = isInlcudeJiShou;
	}
	public void setIsInlcudeFuel(Boolean isInlcudeFuel) {
		this.isInlcudeFuel = isInlcudeFuel;
	}
	public void setIsInlcudeInsurance(Boolean isInlcudeInsurance) {
		this.isInlcudeInsurance = isInlcudeInsurance;
	}
	public List<InqueryDeviceUpdatePreview.DeviceUpdatePrice> getData() {
		return data;
	}
	public void setData(List<InqueryDeviceUpdatePreview.DeviceUpdatePrice> data) {
		this.data = data;
	}
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}

	public static class DeviceUpdatePrice{
		
		private String inqueryDeviceId;
		private BigDecimal unitPrice;
		public String getInqueryDeviceId() {
			return inqueryDeviceId;
		}
		public void setInqueryDeviceId(String inqueryDeviceId) {
			this.inqueryDeviceId = inqueryDeviceId;
		}
		public BigDecimal getUnitPrice() {
			return unitPrice;
		}
		public void setUnitPrice(BigDecimal unitPrice) {
			this.unitPrice = unitPrice;
		}
	}

}
