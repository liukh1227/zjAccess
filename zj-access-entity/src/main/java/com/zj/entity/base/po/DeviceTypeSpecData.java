package com.zj.entity.base.po;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class DeviceTypeSpecData implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -2673190663860338921L;

	private String id;

    private String data;

    private String  definitionId;

    private String deviceTypeId;
    
    private String deviceTypeName;

    private String creator;

    private Date createTime;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data == null ? null : data.trim();
    }
	public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }
    
	public String getDeviceTypeId() {
		return deviceTypeId;
	}

	public void setDeviceTypeId(String deviceTypeId) {
		this.deviceTypeId = deviceTypeId;
	}

	public String getDeviceTypeName() {
		return deviceTypeName;
	}

	public void setDeviceTypeName(String deviceTypeName) {
		this.deviceTypeName = deviceTypeName;
	}

	public String getDefinitionId() {
		return definitionId;
	}

	public void setDefinitionId(String definitionId) {
		this.definitionId = definitionId;
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
		return "DeviceTypeSpecData [id=" + id + ", data=" + data
				+ ", definitionId=" + definitionId + ", deviceTypeId="
				+ deviceTypeId + ", deviceTypeName=" + deviceTypeName
				+ ", creator=" + creator + ", createTime=" + createTime + "]";
	}
	
	
}