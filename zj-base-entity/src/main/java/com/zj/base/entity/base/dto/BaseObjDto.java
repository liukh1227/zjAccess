package com.zj.base.entity.base.dto;

/**
 * 基本objPo
 * @author tanjianhua
 * @date 2016-9-13 下午4:23:28
 */
public class BaseObjDto<T> extends BaseDto {

	private static final long serialVersionUID = 1L;
	public T data;

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
