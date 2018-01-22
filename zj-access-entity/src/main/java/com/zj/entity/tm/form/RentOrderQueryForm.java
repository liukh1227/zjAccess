package com.zj.entity.tm.form;

import java.io.Serializable;
import java.util.Date;

import com.zj.base.entity.base.form.PageQueryForm;

/**
 * 
 * @author liukh
 * @date 2017-2-23 上午10:06:32
 */
public class RentOrderQueryForm extends PageQueryForm implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -7245800897950716382L;
	private String leasingSideId;
    private String lesseeSideId;
    private String city;
    private String currentHandler;
    private String payMethod;
    private String projectId;
    private String status;
    private Date startDate;
    private Date endDate;
    private Integer[] statusArrayList;
    
	public String getLeasingSideId() {
		return leasingSideId;
	}
	public void setLeasingSideId(String leasingSideId) {
		this.leasingSideId = leasingSideId;
	}
	public String getLesseeSideId() {
		return lesseeSideId;
	}
	public void setLesseeSideId(String lesseeSideId) {
		this.lesseeSideId = lesseeSideId;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCurrentHandler() {
		return currentHandler;
	}
	public void setCurrentHandler(String currentHandler) {
		this.currentHandler = currentHandler;
	}
	public String getPayMethod() {
		return payMethod;
	}
	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
