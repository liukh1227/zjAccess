package com.zj.entity.dm.form;

import java.io.Serializable;
import java.util.Date;

import com.zj.base.entity.base.form.PageQueryForm;

/**
 * 
 * @author liukh
 * @date 2017-2-7 下午3:37:07
 */
public class DeviceQueryForm extends PageQueryForm implements Serializable {
	
	private static final long serialVersionUID = 6889565604267762693L;
	private String companyId;
	private String deviceBrand;
	private String deviceId;
	private String deviceModel;
	private String deviceName;
	private String deviceType;
	private Date startDate;
	private Date endDate;
    private Boolean isImported;
    private Integer isRealDevice;
    private String manufactureYear;
    private String realDeviceId;
    private String sequenceNum;
    private Integer workingTime;
    private Integer status;
    
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getDeviceBrand() {
		return deviceBrand;
	}
	public void setDeviceBrand(String deviceBrand) {
		this.deviceBrand = deviceBrand;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getDeviceModel() {
		return deviceModel;
	}
	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
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
	public Boolean getIsImported() {
		return isImported;
	}
	public void setIsImported(Boolean isImported) {
		this.isImported = isImported;
	}
	public Integer getIsRealDevice() {
		return isRealDevice;
	}
	public void setIsRealDevice(Integer isRealDevice) {
		this.isRealDevice = isRealDevice;
	}
	public String getManufactureYear() {
		return manufactureYear;
	}
	public void setManufactureYear(String manufactureYear) {
		this.manufactureYear = manufactureYear;
	}
	public String getRealDeviceId() {
		return realDeviceId;
	}
	public void setRealDeviceId(String realDeviceId) {
		this.realDeviceId = realDeviceId;
	}
	public String getSequenceNum() {
		return sequenceNum;
	}
	public void setSequenceNum(String sequenceNum) {
		this.sequenceNum = sequenceNum;
	}
	public Integer getWorkingTime() {
		return workingTime;
	}
	public void setWorkingTime(Integer workingTime) {
		this.workingTime = workingTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
    
}
