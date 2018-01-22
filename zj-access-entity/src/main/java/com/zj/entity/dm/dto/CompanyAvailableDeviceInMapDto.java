package com.zj.entity.dm.dto;

/**
 * 
 * @author liukh
 * @date 2017-3-16 下午6:31:06
 */
public class CompanyAvailableDeviceInMapDto {
	private String companyId;
	private String companyName;
	private Integer total;
	private String latitude;
	private String longitude;
	private Double distance;
	private String address;
	
	public String getCompanyId() {
		return companyId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public String getLatitude() {
		return latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public Double getDistance() {
		return distance;
	}
	public void setDistance(Double distance) {
		this.distance = distance;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	
}
