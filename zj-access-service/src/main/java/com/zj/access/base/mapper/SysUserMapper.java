package com.zj.access.base.mapper;

import java.util.List;
import java.util.Map;

import com.zj.entity.base.po.SysUser;
import com.zj.entity.sys.form.SysUserQueryForm;

public interface SysUserMapper extends BaseMapper {
	
	public List<SysUser> getSysUserPageList(SysUserQueryForm form);

	public SysUser getSysUserByAccount(Map<String, Object> params);

	public SysUser getSysUserByIdOrAccountId(Map<String, Object> params);
	
	 public SysUser isValidSysUser(Map<String,Object> params);

}