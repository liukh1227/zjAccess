package com.zj.entity.base.po;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class DeviceInspection implements Serializable {
	
    private String id;

    private String deviceId;

    private String userId;

    private String projectId;
    
    private String remark;

    private Date inspectionTime;
    
    private String status;
    
	private static final long serialVersionUID = 1L;
	
	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public String getDeviceId() {
		return deviceId;
	}



	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}



	public String getUserId() {
		return userId;
	}



	public void setUserId(String userId) {
		this.userId = userId;
	}



	public String getProjectId() {
		return projectId;
	}



	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}



	public String getRemark() {
		return remark;
	}



	public void setRemark(String remark) {
		this.remark = remark;
	}


	@JSONField(format="yyyy-MM-dd HH:mm:ss" ) 
	public Date getInspectionTime() {
		return inspectionTime;
	}



	public void setInspectionTime(Date inspectionTime) {
		this.inspectionTime = inspectionTime;
	}



	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}



    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", deviceId=").append(deviceId);
        sb.append(", userId=").append(userId);
        sb.append(", projectId=").append(projectId);
        sb.append(", remark=").append(remark);
        sb.append(", inspectionTime=").append(inspectionTime);
        sb.append(", status=").append(status);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}