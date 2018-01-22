package com.zj.entity.tm.form;

import java.io.Serializable;

import com.zj.base.entity.base.form.PageQueryForm;

/**
 * 
 * @author liukh
 * @date 2017-2-23 上午10:06:11
 */
public class InqueryRentQuoteQueryForm extends PageQueryForm implements Serializable {
	private static final long serialVersionUID = -1839572308504119588L;
		private String inqueryRentId;
	    private String deviceModelId;
	    private String payMethod;	   
	    private String leasingSideId;
	    private String status;
	    private String currentHandler;
		private Integer[] statusArrayList;
	    
		public String getInqueryRentId() {
			return inqueryRentId;
		}
		public void setInqueryRentId(String inqueryRentId) {
			this.inqueryRentId = inqueryRentId;
		}
		public String getDeviceModelId() {
			return deviceModelId;
		}
		public void setDeviceModelId(String deviceModelId) {
			this.deviceModelId = deviceModelId;
		}
		public String getPayMethod() {
			return payMethod;
		}
		public void setPayMethod(String payMethod) {
			this.payMethod = payMethod;
		}
		public String getLeasingSideId() {
			return leasingSideId;
		}
		public void setLeasingSideId(String leasingSideId) {
			this.leasingSideId = leasingSideId;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public String getCurrentHandler() {
			return currentHandler;
		}
		public void setCurrentHandler(String currentHandler) {
			this.currentHandler = currentHandler;
		}
	    
		public Integer[] getStatusArrayList() {
			String[] strArratList = getStatus().split(",");
			if(strArratList!= null){
				statusArrayList = new Integer[strArratList.length];
				for(int i=0;i<strArratList.length;i++){
					statusArrayList[i]=Integer.parseInt(strArratList[i]);   
				}
				
			}
			return statusArrayList;
		}
		
		
		public void setStatusArrayList(Integer[] statusArrayList) {
			this.statusArrayList = statusArrayList;
		}
	

}
