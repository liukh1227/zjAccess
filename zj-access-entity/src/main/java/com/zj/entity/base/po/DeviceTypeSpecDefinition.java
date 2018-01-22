package com.zj.entity.base.po;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class DeviceTypeSpecDefinition implements Serializable {
	
	private static final long serialVersionUID = -8867434354591088351L;

	private String id;

    private String attributeName;

    private Boolean isDisplay;

    private Integer sequenceOrder;

    private Boolean isKeyattribute;

    private String creator;

    private String unit;

    private Date createTime;
     
    private String deviceTypeId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName == null ? null : attributeName.trim();
    }

	public Boolean getIsDisplay() {
		return isDisplay;
	}

    public void setIsDisplay(Boolean isDisplay) {
        this.isDisplay = isDisplay;
    }
    
    @JSONField(name = "sequence")
    public Integer getSequenceOrder() {
		return sequenceOrder;
	}

	public void setSequenceOrder(Integer sequenceOrder) {
		this.sequenceOrder = sequenceOrder;
	}

	public Boolean getIsKeyattribute() {
        return isKeyattribute;
    }

    public void setIsKeyattribute(Boolean isKeyattribute) {
        this.isKeyattribute = isKeyattribute;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }
    @JSONField(format="yyyy-MM-dd HH:mm:ss" ) 
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public String getDeviceTypeId() {
		return deviceTypeId;
	}

	public void setDeviceTypeId(String deviceTypeId) {
		this.deviceTypeId = deviceTypeId;
	}

}