package com.zj.entity.base.po;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class Log implements Serializable {
    private String id;

    private String businessName;

    private String processTypeName;

    private String clientSide;

    private String clientSideType;

    private String operator;

    private String operatorContent;
    
    private Date createTime;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName == null ? null : businessName.trim();
    }

    public String getProcessTypeName() {
        return processTypeName;
    }

    public void setProcessTypeName(String processTypeName) {
        this.processTypeName = processTypeName == null ? null : processTypeName.trim();
    }

    public String getClientSide() {
        return clientSide;
    }

    public void setClientSide(String clientSide) {
        this.clientSide = clientSide == null ? null : clientSide.trim();
    }

    public String getClientSideType() {
        return clientSideType;
    }

    public void setClientSideType(String clientSideType) {
        this.clientSideType = clientSideType == null ? null : clientSideType.trim();
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    public String getOperatorContent() {
        return operatorContent;
    }

    public void setOperatorContent(String operatorContent) {
        this.operatorContent = operatorContent == null ? null : operatorContent.trim();
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
		return "Log [id=" + id + ", businessName=" + businessName
				+ ", processTypeName=" + processTypeName + ", clientSide="
				+ clientSide + ", clientSideType=" + clientSideType
				+ ", operator=" + operator + ", operatorContent="
				+ operatorContent + ", createTime=" + createTime + "]";
	}


}