package com.zj.entity.bm.form;

import java.io.Serializable;

import com.zj.base.entity.base.form.PageQueryForm;

/**
 * 
 * @author liukh
 * @date 2017-2-7 下午3:37:07
 */
public class DeviceBrandQueryForm extends PageQueryForm implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8572036251104740624L;
	private String brandName;
    private Integer isDisplay;
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public Integer getIsDisplay() {
		return isDisplay;
	}
	public void setIsDisplay(Integer isDisplay) {
		this.isDisplay = isDisplay;
	}
    
       

}
