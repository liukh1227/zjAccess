package com.zj.entity.tm.dto;
import com.zj.entity.base.po.OrderStatement;
public class OrderStatementContainComanyNameDto extends OrderStatement {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4134841591264498326L;
	private String leasingSideId;
	private String leasingSideName;
     private String lesseeSideId;
	private String lesseeSideName;
	public String getLeasingSideId() {
		return leasingSideId;
	}
	public void setLeasingSideId(String leasingSideId) {
		this.leasingSideId = leasingSideId;
	}
	public String getLeasingSideName() {
		return leasingSideName;
	}
	public void setLeasingSideName(String leasingSideName) {
		this.leasingSideName = leasingSideName;
	}
	public String getLesseeSideId() {
		return lesseeSideId;
	}
	public void setLesseeSideId(String lesseeSideId) {
		this.lesseeSideId = lesseeSideId;
	}
	public String getLesseeSideName() {
		return lesseeSideName;
	}
	public void setLesseeSideName(String lesseeSideName) {
		this.lesseeSideName = lesseeSideName;
	}
	
	
}
