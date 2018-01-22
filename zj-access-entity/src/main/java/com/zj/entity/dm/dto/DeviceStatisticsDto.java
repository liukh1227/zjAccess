package com.zj.entity.dm.dto;

import java.io.Serializable;


/**
 * @author liukh
 * @date 2016-11-25 上午10:23:47
 */
public class DeviceStatisticsDto extends StatisticsCountDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9024021711444913593L;
	 private Integer isRealDevice;
	 private Integer status;
	 
	public Integer getIsRealDevice() {
		return isRealDevice;
	}
	public void setIsRealDevice(Integer isRealDevice) {
		this.isRealDevice = isRealDevice;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	

}
