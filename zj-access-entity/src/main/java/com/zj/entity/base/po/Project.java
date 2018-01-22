package com.zj.entity.base.po;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class Project implements Serializable {
    private String id;

    private String theme;

    private String publisherId;

    private Integer status;

    private String address;

    private String content;
    
    private Date createTime;
    
    private static final long serialVersionUID = 1L;

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme == null ? null : theme.trim();
    }

    public String getPublisherId() {
		return publisherId;
	}

	public void setPublisherId(String publisherId) {
		this.publisherId = publisherId;
	}

	public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
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
		return "Project [id=" + id + ", theme=" + theme
				+ ", publisherId=" + publisherId + ", status=" + status
				+ ", address=" + address + ", content=" + content
				+ ", createTime=" + createTime + "]";
	}


}