package com.zj.entity.dm.form;

import java.io.Serializable;

import com.zj.base.entity.base.form.PageQueryForm;

/**
 * 
 * @author liukh
 * @date 2017-2-21 下午2:32:17
 */
public class DeviceStatusTraceQueryForm extends PageQueryForm implements Serializable {
	
	private static final long serialVersionUID = 33202841052513811L;
		private String deviceId;
	    private String deviceName;
	    private String orderId;
		public String getDeviceId() {
			return deviceId;
		}
		public void setDeviceId(String deviceId) {
			this.deviceId = deviceId;
		}
		public String getDeviceName() {
			return deviceName;
		}
		public void setDeviceName(String deviceName) {
			this.deviceName = deviceName;
		}
		public String getOrderId() {
			return orderId;
		}
		public void setOrderId(String orderId) {
			this.orderId = orderId;
		}
	    

	
}
