package com.zj.entity.tm.form;

import java.io.Serializable;
import java.util.Date;

import com.zj.base.entity.base.form.PageQueryForm;

/**
 * 
 * @author liukh
 * @date 2017-2-23 上午10:06:24
 */
public class InqueryRentQueryForm extends PageQueryForm implements Serializable {

	private static final long serialVersionUID = -1185481121009534630L;
	private String lesseeSideId;
	private String removeQuotedCompanyId;
	private String deviceTypeId;
	private String parentDeviceTypeId;
	private Date startDate;
	private Date endDate;
	private String payMethod;
	private String address;
	private String city;
	private String status;
	private String projectId;
	private String longitude;
	private String latitude;
	private Integer[] statusArrayList;
    private Integer quoteStatus;
	
	public String getLesseeSideId() {
		return lesseeSideId;
	}

	public void setLesseeSideId(String lesseeSideId) {
		this.lesseeSideId = lesseeSideId;
	}

	public String getRemoveQuotedCompanyId() {
		return removeQuotedCompanyId;
	}

	public void setRemoveQuotedCompanyId(String removeQuotedCompanyId) {
		this.removeQuotedCompanyId = removeQuotedCompanyId;
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

	public String getPayMethod() {
		return payMethod;
	}

	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public Integer[] getStatusArrayList() {
		if(getStatus()!= null){
			String[] strArratList = getStatus().split(",");
			if(strArratList!= null){
				statusArrayList = new Integer[strArratList.length];
				for(int i=0;i<strArratList.length;i++){
					statusArrayList[i]=Integer.parseInt(strArratList[i]);   
				}
				
			}
			
		}
		return statusArrayList;
	}

	public void setStatusArrayList(Integer[] statusArrayList) {
		this.statusArrayList = statusArrayList;
	}

	public Integer getQuoteStatus() {
		return quoteStatus;
	}

	public void setQuoteStatus(Integer quoteStatus) {
		this.quoteStatus = quoteStatus;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	
}
