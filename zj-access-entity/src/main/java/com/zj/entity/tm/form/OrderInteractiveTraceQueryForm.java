package com.zj.entity.tm.form;

import java.io.Serializable;

import com.zj.base.entity.base.form.PageQueryForm;

/**
 * 
 * @author liukh
 * @date 2017-2-23 上午10:06:32
 */
public class OrderInteractiveTraceQueryForm extends PageQueryForm implements
		Serializable {

	private static final long serialVersionUID = 6980094364895692985L;
	private String relatedStatementId;
	public String getRelatedStatementId() {
		return relatedStatementId;
	}
	public void setRelatedStatementId(String relatedStatementId) {
		this.relatedStatementId = relatedStatementId;
	}
	

}
