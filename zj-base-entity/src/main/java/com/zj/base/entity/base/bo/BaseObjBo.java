package com.zj.base.entity.base.bo;

import java.io.Serializable;

import com.zj.base.entity.base.dto.BaseObjDto;


public class BaseObjBo<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	private BaseObjDto<T> baseObjDto;

	public BaseObjDto<T> getBaseObjDto() {
		return baseObjDto;
	}

	public void setBaseObjDto(BaseObjDto<T> baseObjDto) {
		this.baseObjDto = baseObjDto;
	}

}
