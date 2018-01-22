package com.zj.entity.sm.form;

import java.io.Serializable;

import com.zj.base.entity.base.form.PageQueryForm;

/**
 * 
 * @author five
 * @date 2017年2月23日11:59:20
 */
public class LogQueryForm extends PageQueryForm implements Serializable {
	 
	    private String businessName;

	    private String processTypeName;

	    private String clientSide;

	    private String clientSideType;

	    private String operator;

	    private String operatorContent;

	    private static final long serialVersionUID = 1L;

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
}
