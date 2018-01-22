package com.zj.entity.tm.dto;
import java.util.List;


/**
 * @author liukh
 * @date 2016-11-6 下午2:33:18
 */
public class DeliveryOrderListDto {
	
	private List<DeliveryOrderDto> data;

	public List<DeliveryOrderDto> getData() {
		return data;
	}
	public void setData(List<DeliveryOrderDto> data) {
		this.data = data;
	}
	public static class DeliveryOrderDto{
		private String rentOrderId;
		private String rentOrderDeviceId;
		private Integer status;
		private String creator;
		private List<DeliveryOrderDeviceDto> data;
		public String getRentOrderId() {
			return rentOrderId;
		}
		public String getRentOrderDeviceId() {
			return rentOrderDeviceId;
		}
		public Integer getStatus() {
			return status;
		}
		public String getCreator() {
			return creator;
		}
		public List<DeliveryOrderDeviceDto> getData() {
			return data;
		}
		public void setRentOrderId(String rentOrderId) {
			this.rentOrderId = rentOrderId;
		}
		public void setRentOrderDeviceId(String rentOrderDeviceId) {
			this.rentOrderDeviceId = rentOrderDeviceId;
		}
		public void setStatus(Integer status) {
			this.status = status;
		}
		public void setCreator(String creator) {
			this.creator = creator;
		}
		public void setData(List<DeliveryOrderDeviceDto> data) {
			this.data = data;
		}

	}
	public static class DeliveryOrderDeviceDto{
		private String id;
		private String deviceName;

		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getDeviceName() {
			return deviceName;
		}
		public void setDeviceName(String deviceName) {
			this.deviceName = deviceName;
		}
		
	}
	


}
