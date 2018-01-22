package com.zj.base.entity.base.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 基类po
 * @author:tjhua
 * @date:2015-9-9 上午11:17:27
 * <p>description:</p>
 */
public class BaseDto implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public static final Integer SUCCESS_RCODE = 0;

	public static final Integer NO_DATA_RCODE = 21;

	public static final Integer EXIST_RCODE = 22;

	public static final Integer ERROR_RCODE = 1;

	public static final String DEFAULT_SUCCESS_INFO = "操作成功!";
	
	public static final String DEFAULT_NO_DATA_INFO = "暂无数据!";

	public static final String DEFAULT_ERROR_INFO = "操作失败!";
	
	/**
	 * 返回码
	 */
	private Integer rcode = ERROR_RCODE;
	/**
	 * 返回信息
	 */
	private String rinfo = DEFAULT_ERROR_INFO;
	
	public Integer getRcode() {
		return rcode;
	}
	public void setRcode(Integer rcode) {
		this.rcode = rcode;
	}
	public String getRinfo() {
		return rinfo;
	}
	public void setRinfo(String rinfo) {
		this.rinfo = rinfo;
	}
	
	public Map<String, Object> toMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rinfo", rinfo);
		map.put("rcode", rcode);
		return map;
	}

	@Override
	public String toString() {
		String str = "{\"rcode\" : "+ rcode +", \"rinfo\" : \"" + rinfo + "\"}";
		return str;
	}
}
