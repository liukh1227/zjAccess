package com.zj.base.entity.base.dto;


/**
 * 基本分页po
 * @author:tjhua
 * @date:2015-9-24 上午10:47:59
 * <p>description:</p>
 */
public class BaseListDto<T> extends BaseDto {

	private static final long serialVersionUID = 1L;
	private Pages<T> data;

	public Pages<T> getData() {
		return data;
	}

	public void setData(Pages<T> data) {
		this.data = data;
	}
}
