package com.zj.entity.base.po;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class DeviceModel implements Serializable {
    private String id;

    private String modelName;

    private DeviceType deviceType;
    
    private DeviceBrand deviceBrand;
    
    private DeviceTypeSpecData deviceTypeSpecData;

    private String creator;
    
    private String modelPicture;

    private Date createTime;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }
    
    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName == null ? null : modelName.trim();
	}
	 @JSONField(format="yyyy-MM-dd HH:mm:ss" ) 
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public DeviceType getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(DeviceType deviceType) {
		this.deviceType = deviceType;
	}

	public DeviceBrand getDeviceBrand() {
		return deviceBrand;
	}

	public void setDeviceBrand(DeviceBrand deviceBrand) {
		this.deviceBrand = deviceBrand;
	}

	public DeviceTypeSpecData getDeviceTypeSpecData() {
		return deviceTypeSpecData;
	}

	public void setDeviceTypeSpecData(DeviceTypeSpecData deviceTypeSpecData) {
		this.deviceTypeSpecData = deviceTypeSpecData;
	}

	public String getModelPicture() {
		return modelPicture;
	}

	public void setModelPicture(String modelPicture) {
		this.modelPicture = modelPicture;
	}

	@Override
	public String toString() {
		return "DeviceModel [id=" + id + ", modelName=" + modelName
				+ ", deviceType=" + deviceType + ", deviceBrand=" + deviceBrand
				+ ", deviceTypeSpecData=" + deviceTypeSpecData + ", creator="
				+ creator + ", modelPicture=" + modelPicture + ", createTime="
				+ createTime + "]";
	}

	

}