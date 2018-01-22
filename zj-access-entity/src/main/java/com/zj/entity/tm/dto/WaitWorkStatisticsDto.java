package com.zj.entity.tm.dto;

import java.util.List;

import com.zj.entity.dm.dto.StatisticsCountDto;

public class WaitWorkStatisticsDto extends StatisticsCountDto {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Object> data;

	public List<Object> getData() {
		return data;
	}

	public void setData(List<Object> data) {
		this.data = data;
	}
	
}
