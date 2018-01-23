package com.zj.entity.tm.form;

import java.io.Serializable;
import com.zj.base.entity.base.form.PageQueryForm;

/**
 * 
 * @author liukh
 * @date 2017-2-20 下午5:19:22
 */
public class InspectionDeviceQueryForm extends PageQueryForm implements Serializable {
	
	private static final long serialVersionUID = 1L;
    
   
    private String deviceId;


	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
    
    
    
      
}
