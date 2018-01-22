package com.zj.entity.bm.dto;

import java.util.ArrayList;
import java.util.List;

import com.zj.entity.base.po.DeviceBrand;

public class BrandIndexListDto {
	
	private BrandsDto hotBrands;
	
	private ArrayList<BrandsDto> groupBrands;
	public BrandsDto getHotBrands() {
		return hotBrands;
	}
	public void setHotBrands(BrandsDto hotBrands) {
		this.hotBrands = hotBrands;
	}
	public ArrayList<BrandsDto> getGroupBrands() {
		return groupBrands;
	}
	public void setGroupBrands(ArrayList<BrandsDto> groupBrands) {
		this.groupBrands = groupBrands;
	}

	public static class BrandsDto{
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
	

}
