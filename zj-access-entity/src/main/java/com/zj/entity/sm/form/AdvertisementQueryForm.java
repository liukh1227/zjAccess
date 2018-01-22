package com.zj.entity.sm.form;

import java.io.Serializable;

import com.zj.base.entity.base.form.PageQueryForm;

/**
 * 
 * @author five
 * @date 2017年2月23日11:59:20
 */
public class AdvertisementQueryForm extends PageQueryForm implements Serializable {


	    private String title;

	    private Integer orderNum;

	    private Integer status;

	    private String adaptBSideType;

	    private String adaptTerminal;
	    
	    private String companyId;

	    private static final long serialVersionUID = 1L;

	    public String getTitle() {
	        return title;
	    }

	    public void setTitle(String title) {
	        this.title = title == null ? null : title.trim();
	    }
	  

	    public Integer getOrderNum() {
			return orderNum;
		}

		public void setOrderNum(Integer orderNum) {
			this.orderNum = orderNum;
		}

		public Integer getStatus() {
	        return status;
	    }

	    public void setStatus(Integer status) {
	        this.status = status;
	    }

	    public String getAdaptBSideType() {
	        return adaptBSideType;
	    }

	    public void setAdaptBSideType(String adaptBSideType) {
	        this.adaptBSideType = adaptBSideType == null ? null : adaptBSideType.trim();
	    }

	    public String getAdaptTerminal() {
	        return adaptTerminal;
	    }

	    public void setAdaptTerminal(String adaptTerminal) {
	        this.adaptTerminal = adaptTerminal == null ? null : adaptTerminal.trim();
	    }

		public String getCompanyId() {
			return companyId;
		}

		public void setCompanyId(String companyId) {
			this.companyId = companyId;
		}

	  
}
