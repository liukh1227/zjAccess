package com.zj.entity.base.po;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class SmsDistributionLog implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

	private String smsPort;

    private String smsPortMessageId;

    private String cellphone;

    private String content;

    private Integer status;

    private Integer responseStatus;

    private Date responseTime;

    private Integer totalDistributionCount;

    private Date smsPushTime;

    private Date createTime;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
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

    public Integer getTotalDistributionCount() {
        return totalDistributionCount;
    }

    public void setTotalDistributionCount(Integer totalDistributionCount) {
        this.totalDistributionCount = totalDistributionCount;
    }

	public Date getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(Date responseTime) {
		this.responseTime = responseTime;
	}

	public Date getSmsPushTime() {
		return smsPushTime;
	}

	public void setSmsPushTime(Date smsPushTime) {
		this.smsPushTime = smsPushTime;
	}
	 @JSONField(format="yyyy-MM-dd HH:mm:ss" ) 
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "SmsDistributionLog [id=" + id + ", smsPort=" + smsPort
				+ ", smsPortMessageId=" + smsPortMessageId + ", cellphone="
				+ cellphone + ", content=" + content + ", status=" + status
				+ ", responseStatus=" + responseStatus + ", responseTime="
				+ responseTime + ", totalDistributionCount="
				+ totalDistributionCount + ", smsPushTime=" + smsPushTime
				+ ", createTime=" + createTime + "]";
	}

  
}