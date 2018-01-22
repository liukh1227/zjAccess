package com.zj.entity.sys.bo;

import java.io.Serializable;

import com.zj.entity.base.po.SysUser;

public class SysUserBo implements Serializable{
	
	private static final long serialVersionUID = 5296372251661240609L;
	private String returnJson;
	private SysUser sysUser;
	public String getReturnJson() {
		return returnJson;
	}
	public void setReturnJson(String returnJson) {
		this.returnJson = returnJson;
	}
	public SysUser getSysUser() {
		return sysUser;
	}
	public void setSysUser(SysUser sysUser) {
		this.sysUser = sysUser;
	}

	

}
