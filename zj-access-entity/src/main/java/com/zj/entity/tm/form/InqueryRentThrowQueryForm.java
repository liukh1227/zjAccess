package com.zj.entity.tm.form;

import java.io.Serializable;
import java.util.Date;

import com.zj.base.entity.base.form.PageQueryForm;

/**
 * 
 * @author liukh
 * @date 2017-2-23 上午10:06:11
 */
public class InqueryRentThrowQueryForm extends PageQueryForm implements Serializable {
	
	private static final long serialVersionUID = 1587378005749701484L;
		private String lesseeSideId;
	    private String deviceTypeId;
	    private String parentDeviceTypeId;
	    private Date startDate;
	    private Date endDate;
	    private String city;
	    private Integer status;
	    private String responseLeasingSideId;
	    private String removeCompanyId;
	    private String projectId;
	    
		public String getLesseeSideId() {
			return lesseeSideId;
		}
		public void setLesseeSideId(String lesseeSideId) {
			this.lesseeSideId = lesseeSideId;
		}
		public String getDeviceTypeId() {
			return deviceTypeId;
		}
		public void setDeviceTypeId(String deviceTypeId) {
			this.deviceTypeId = deviceTypeId;
		}
		public String getParentDeviceTypeId() {
			return parentDeviceTypeId;
		}
		public void setParentDeviceTypeId(String parentDeviceTypeId) {
			this.parentDeviceTypeId = parentDeviceTypeId;
		}
		public Date getStartDate() {
			return startDate;
		}
		public void setStartDate(Date startDate) {
			this.startDate = startDate;
		}
		public Date getEndDate() {
			return endDate;
		}
		public void setEndDate(Date endDate) {
			this.endDate = endDate;
		}
		public String getCity() {
			return city;
		}
		public void setCity(String city) {
			this.city = city;
		}
		public Integer getStatus() {
			return status;
		}
		public void setStatus(Integer status) {
			this.status = status;
		}
		public String getResponseLeasingSideId() {
			return responseLeasingSideId;
		}
		public void setResponseLeasingSideId(String responseLeasingSideId) {
			this.responseLeasingSideId = responseLeasingSideId;
		}
		public String getRemoveCompanyId() {
			return removeCompanyId;
		}
		public void setRemoveCompanyId(String removeCompanyId) {
			this.removeCompanyId = removeCompanyId;
		}
		public String getProjectId() {
			return projectId;
		}
		public void setProjectId(String projectId) {
			this.projectId = projectId;
		}
	    
}
