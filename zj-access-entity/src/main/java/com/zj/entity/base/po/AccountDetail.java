package com.zj.entity.base.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.zj.common.Constant;

public class AccountDetail implements Serializable {
	private String id;

	private String companyId;

	private Integer businessType;

	private String expenseType;

	private BigDecimal transactionAmount;

	private String sequenceNum;

	private String memo;

	private Date recordTime;

	private static final long serialVersionUID = 1L;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId == null ? null : companyId.trim();
	}

	public Integer getBusinessType() {
		return businessType;
	}

	public void setBusinessType(Integer businessType) {
		this.businessType = businessType;
	}

	public String getExpenseType() {
		return expenseType;
	}

	public void setExpenseType(String expenseType) {
		this.expenseType = expenseType == null ? null : expenseType.trim();
	}

	public String getSequenceNum() {
		return sequenceNum;
	}

	public void setSequenceNum(String sequenceNum) {
		this.sequenceNum = sequenceNum == null ? null : sequenceNum.trim();
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo == null ? null : memo.trim();
	}
	 @JSONField(format="yyyy-MM-dd HH:mm:ss" ) 
	public Date getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}

	public BigDecimal getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(BigDecimal transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	@JSONField(serialize=false)  
	public boolean isAdd() {
		return getBusinessType() != null
				&& (getBusinessType() == Constant.ACCOUNTDETAIL_BUSSINESS_TOPUP || getBusinessType() == Constant.ACCOUNTDETAIL_BUSSINESS_GATHERING)
				&& getExpenseType() != null
				&& getExpenseType().equals(
						Constant.ACCOUNTDETAIL_EXPENSETYPE_ADD);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName());
		sb.append(" [");
		sb.append("Hash = ").append(hashCode());
		sb.append(", id=").append(id);
		sb.append(", companyId=").append(companyId);
		sb.append(", businessType=").append(businessType);
		sb.append(", expenseType=").append(expenseType);
		sb.append(", transactionAmount=").append(transactionAmount);
		sb.append(", sequenceNum=").append(sequenceNum);
		sb.append(", memo=").append(memo);
		sb.append(", recordTime=").append(recordTime);
		sb.append(", serialVersionUID=").append(serialVersionUID);
		sb.append("]");
		return sb.toString();
	}
}