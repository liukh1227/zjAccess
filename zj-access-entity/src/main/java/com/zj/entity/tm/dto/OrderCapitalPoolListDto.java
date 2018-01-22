package com.zj.entity.tm.dto;
import java.math.BigDecimal;
import java.util.List;

import com.zj.entity.base.po.OrderCapitalPool;
import com.zj.entity.base.po.RentOrderDevice;

/**
 * 
 * @author liukh
 * @date 2017-3-27 下午4:30:35
 */
public class OrderCapitalPoolListDto extends OrderCapitalPool{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String capitalSideName;
	private String capitalOppositeSideName;

	private List<RentOrderDeviceReleateOrderCapitalPool> data;
	public List<RentOrderDeviceReleateOrderCapitalPool> getData() {
		return data;
	}

	public void setData(List<RentOrderDeviceReleateOrderCapitalPool> data) {
		this.data = data;
	}

	public String getCapitalOppositeSideName() {
		return capitalOppositeSideName;
	}

	public void setCapitalOppositeSideName(String capitalOppositeSideName) {
		this.capitalOppositeSideName = capitalOppositeSideName;
	}
	
	
public String getCapitalSideName() {
		return capitalSideName;
	}

	public void setCapitalSideName(String capitalSideName) {
		this.capitalSideName = capitalSideName;
	}

public void copyFromOther(OrderCapitalPool other){
	setId(other.getId());
	setRentOrderId(other.getRentOrderId());
	setType(other.getType());
	setCapitalSideId(other.getCapitalSideId());
	setAmount(other.getAmount());
	setStatus(other.getStatus());
	setCreateTime(other.getCreateTime());
}

	public static class RentOrderDeviceReleateOrderCapitalPool {
		private String deviceModelName;
		private Integer quantity;
		private BigDecimal saleAmount;
		private String startTime;
		private String endTime;
		private String picture;

		public String getDeviceModelName() {
			return deviceModelName;
		}

		public Integer getQuantity() {
			return quantity;
		}

		public BigDecimal getSaleAmount() {
			return saleAmount;
		}

		public String getStartTime() {
			return startTime;
		}

		public String getEndTime() {
			return endTime;
		}

		public void setDeviceModelName(String deviceModelName) {
			this.deviceModelName = deviceModelName;
		}

		public void setQuantity(Integer quantity) {
			this.quantity = quantity;
		}

		public void setSaleAmount(BigDecimal saleAmount) {
			this.saleAmount = saleAmount;
		}

		public void setStartTime(String startTime) {
			this.startTime = startTime;
		}

		public void setEndTime(String endTime) {
			this.endTime = endTime;
		}

		public String getPicture() {
			return picture;
		}

		public void setPicture(String picture) {
			this.picture = picture;
		}
		
		public void copyFromDevie(RentOrderDevice other){
			setDeviceModelName(other.getDeviceModelName());
			setQuantity(other.getQuantity());
			setSaleAmount(other.getSaleAmount());
			setStartTime(other.getStartTime());
			setEndTime(other.getEndTime());
			setPicture(other.getPicture());
		}

	}

}
