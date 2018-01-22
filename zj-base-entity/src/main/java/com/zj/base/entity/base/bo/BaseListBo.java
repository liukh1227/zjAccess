package com.zj.base.entity.base.bo;

import java.io.Serializable;

import com.zj.base.entity.base.dto.BaseListDto;


public class BaseListBo<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	private BaseListDto<T> baseObjList;

	public BaseListDto<T> getBaseObjList() {
		return baseObjList;
	}

	public void setBaseObjList(BaseListDto<T> baseObjList) {
		this.baseObjList = baseObjList;
	}

}
