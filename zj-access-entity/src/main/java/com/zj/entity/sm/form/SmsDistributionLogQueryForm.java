package com.zj.entity.sm.form;

import java.io.Serializable;
import java.util.Date;

import com.zj.base.entity.base.form.PageQueryForm;
/**
 * 
 * @author liukh
 * @date 2017-3-8 上午11:09:44
 */
public class SmsDistributionLogQueryForm extends PageQueryForm implements Serializable {
	private String smsPort;

    private String smsPortMessageId;

    private String cellphone;

    private Integer status;

    private Integer responseStatus;
        
    private Date startTime;
    
    private Date endTime;

    private static final long serialVersionUID = 1L;

    public String getSmsPort() {
        return smsPort;
    }

    public void setSmsPort(String smsPort) {
        this.smsPort = smsPort == null ? null : smsPort.trim();
    }

    public String getSmsPortMessageId() {
        return smsPortMessageId;
    }

    public void setSmsPortMessageId(String smsPortMessageId) {
        this.smsPortMessageId = smsPortMessageId == null ? null : smsPortMessageId.trim();
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone == null ? null : cellphone.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(Integer responseStatus) {
        this.responseStatus = responseStatus;
    }

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

   
}
