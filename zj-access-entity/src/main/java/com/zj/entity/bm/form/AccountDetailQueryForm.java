package com.zj.entity.bm.form;

import java.io.Serializable;
import com.zj.base.entity.base.form.PageQueryForm;

/**
 * 
 * @author liukh
 * @date 2017-2-20 下午5:19:22
 */
public class AccountDetailQueryForm extends PageQueryForm implements Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 4520807643032958697L;
	private Integer businessType;
    private String companyId;
    private String expenseType;
	public Integer getBusinessType() {
		return businessType;
	}
	public void setBusinessType(Integer businessType) {
		this.businessType = businessType;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getExpenseType() {
		return expenseType;
	}
	public void setExpenseType(String expenseType) {
		this.expenseType = expenseType;
	}
    

      
}
