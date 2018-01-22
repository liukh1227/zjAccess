package com.zj.entity.dm.form;

import java.io.Serializable;
import com.zj.base.entity.base.form.PageQueryForm;

/**
 * 
 * @author liukh
 * @date 2017-2-21 下午2:32:55
 */
public class CompanyDeviceTypeQueryForm extends PageQueryForm implements Serializable {
	
	private static final long serialVersionUID = 2065588168652569952L;

	private String companyId;
    private String deviceModelId;
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getDeviceModelId() {
		return deviceModelId;
	}
	public void setDeviceModelId(String deviceModelId) {
		this.deviceModelId = deviceModelId;
	}
    
}
