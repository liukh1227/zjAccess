package com.zj.entity.bm.dto;

import java.util.List;

import com.zj.entity.base.po.DeviceBrand;

public class BrandsDto {
	private List<DeviceBrand> data;
	private String index;
	private Integer rowNum;
	
	public List<DeviceBrand> getData() {
		return data;
	}
	public String getIndex() {
		return index;
	}
	public Integer getRowNum() {
		return rowNum;
	}
	public void setData(List<DeviceBrand> data) {
		this.data = data;
	}
	public void setIndex(String index) {
		this.index = index;
	}
	public void setRowNum(Integer rowNum) {
		this.rowNum = rowNum;
	}
}
