package com.zj.entity.bm.form;

import java.io.Serializable;

import com.zj.base.entity.base.form.PageQueryForm;

/**
 * 
 * @author liukh
 * @date 2017-2-7 下午3:37:07
 */
public class CompanyQueryForm extends PageQueryForm implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 5276047956482964567L;

	private String companyBusinessType;

	private String companyName;

	private Integer companyType;
	
	private String city;
	
	private Integer status;

	public String getCompanyBusinessType() {
		return companyBusinessType;
	}

	public void setCompanyBusinessType(String companyBusinessType) {
		this.companyBusinessType = companyBusinessType;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Integer getCompanyType() {
		return companyType;
	}

	public void setCompanyType(Integer companyType) {
		this.companyType = companyType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
}
