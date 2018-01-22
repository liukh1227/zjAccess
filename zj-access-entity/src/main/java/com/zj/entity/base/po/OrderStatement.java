package com.zj.entity.base.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.zj.common.Constant;

public class OrderStatement implements Serializable {
    private String id;

    private String rentOrderId;

    private Integer statementType;

    private String startTime;

    private String endTime;

    private BigDecimal statementAmount;

    private String statementDescription;

    private Integer status;

    private String creator;

    private String currentHandler;

    private Date createTime;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getRentOrderId() {
        return rentOrderId;
    }

    public void setRentOrderId(String rentOrderId) {
        this.rentOrderId = rentOrderId == null ? null : rentOrderId.trim();
    }

    public Integer getStatementType() {
        return statementType;
    }

    public void setStatementType(Integer statementType) {
        this.statementType = statementType;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime == null ? null : startTime.trim();
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime == null ? null : endTime.trim();
    }

    public BigDecimal getStatementAmount() {
        return statementAmount;
    }

    public void setStatementAmount(BigDecimal statementAmount) {
        this.statementAmount = statementAmount;
    }

    public String getStatementDescription() {
        return statementDescription;
    }

    public void setStatementDescription(String statementDescription) {
        this.statementDescription = statementDescription == null ? null : statementDescription.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public String getCurrentHandler() {
        return currentHandler;
    }

    public void setCurrentHandler(String currentHandler) {
        this.currentHandler = currentHandler == null ? null : currentHandler.trim();
    }
    @JSONField(format="yyyy-MM-dd HH:mm:ss" ) 
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", rentOrderId=").append(rentOrderId);
        sb.append(", statementType=").append(statementType);
        sb.append(", startTime=").append(startTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", statementAmount=").append(statementAmount);
        sb.append(", statementDescription=").append(statementDescription);
        sb.append(", status=").append(status);
        sb.append(", creator=").append(creator);
        sb.append(", currentHandler=").append(currentHandler);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
    @JSONField(serialize=false) 
    public boolean isAllTime() {
		return Constant.ORDERSTATEMENT_STATUS_ALLTIME == getStatementType();
	}
    @JSONField(serialize=false) 
	public boolean isSomeTime() {
		return Constant.ORDERSTATEMENT_STATUS_SOMETIME == getStatementType();
	}
    @JSONField(serialize=false) 
	public static boolean isAllTime(Integer statementType) {
		return Constant.ORDERSTATEMENT_STATUS_ALLTIME == statementType;
	}
    @JSONField(serialize=false) 
	public static boolean isSomeTime(Integer statementType) {
		return Constant.ORDERSTATEMENT_STATUS_SOMETIME == statementType;
	}
}