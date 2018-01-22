package com.zj.entity.tm.form;

import java.io.Serializable;

import com.zj.base.entity.base.form.PageQueryForm;

/**
 * 
 * @author liukh
 * @date 2017-2-23 上午10:06:32
 */
public class OrderCommentQueryForm extends PageQueryForm implements Serializable {

	private static final long serialVersionUID = -2752843602249672768L;
	private String beCommentedCompanyId;
	private String commentSideId;
	private String rentOrderId;
	private Integer star;
    private String status;
	private Integer[] statusArrayList;
	public String getBeCommentedCompanyId() {
		return beCommentedCompanyId;
	}
	public void setBeCommentedCompanyId(String beCommentedCompanyId) {
		this.beCommentedCompanyId = beCommentedCompanyId;
	}
	public String getCommentSideId() {
		return commentSideId;
	}
	public void setCommentSideId(String commentSideId) {
		this.commentSideId = commentSideId;
	}
	public String getRentOrderId() {
		return rentOrderId;
	}
	public void setRentOrderId(String rentOrderId) {
		this.rentOrderId = rentOrderId;
	}
	public Integer getStar() {
		return star;
	}
	public void setStar(Integer star) {
		this.star = star;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
