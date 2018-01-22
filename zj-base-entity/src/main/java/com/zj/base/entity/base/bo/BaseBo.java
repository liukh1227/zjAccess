package com.zj.base.entity.base.bo;

import java.io.Serializable;

import com.zj.base.entity.base.dto.BaseDto;


public class BaseBo implements Serializable {

	private static final long serialVersionUID = 1L;
	private BaseDto baseDto;

	public BaseDto getBaseDto() {
		return baseDto;
	}

	public void setBaseDto(BaseDto baseDto) {
		this.baseDto = baseDto;
	}

}
