package com.zj.base.entity.base.vo;

import java.io.Serializable;

public class BaseVo implements Serializable{

	private static final long serialVersionUID = 1L;
	private Integer rcode;
	private String rinfo;
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
}
