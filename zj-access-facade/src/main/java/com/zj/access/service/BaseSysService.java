package com.zj.access.service;

import com.zj.entity.sys.bo.SysUserBo;
import com.zj.entity.sys.form.SysUserQueryForm;

public interface BaseSysService {

	/**
	 * 新增用户信息
	 * 
	 * @author liukh
	 * @date 2017-2-13 下午2:22:32
	 * @param data
	 * @return
	 */
	public String addSysUser(String data);

	/**
	 * 获取用户信息
	 * 
	 * @author liukh
	 * @date 2017-2-13 下午2:23:12
	 * @param userId
	 * @return
	 */

	public String getSysUser(String userId);

	/**
	 * 根据用户Id或账号获取用户信息
	 * 
	 * @author liukh
	 * @date 2017-3-2 上午9:58:07
	 * @param userId
	 * @param account
	 * @return
	 */
	public String getSysUserMoreInfo(String userId, String account);

	/**
	 * 更新用户信息
	 * 
	 * @author liukh
	 * @date 2017-2-13 下午2:23:25
	 * @param userId
	 * @param data
	 * @return
	 */
	public String updateSysUser(String userId, String data);

	/**
	 * 获取用户列表
	 * 
	 * @author liukh
	 * @date 2017-4-21 下午3:57:07
	 * @param form
	 * @return
	 */
	public String getSysUserList(SysUserQueryForm form);

	/**
	 * 验证登录账号是否已存在
	 * 
	 * @author liukh
	 * @date 2017-3-1 下午3:56:08
	 * @param account
	 * @return
	 */
	public String isValidSysUser(String account);

	/**
	 * 修改密码
	 * 
	 * @author liukh
	 * @date 2017-3-2 上午11:47:37
	 * @param account
	 * @param data
	 * @param data
	 *            ：{oldPassword,newPassword,newAgainPassword}
	 * @return
	 */
	public String updatePassword(String account, String data);

	/**
	 * 重置密码
	 * 
	 * @author liukh
	 * @date 2017-3-2 下午2:06:32
	 * @param data
	 * @param account
	 * @return
	 */
	public String updatePasswordReset(String account, String data);
	
	/**
	 * 登录并获取用户的详细信息
	 * @author liukh
	 * @date 2017-3-15 下午3:28:35
	 * @param data
	 * @return
	 */
	public SysUserBo getSysUserDetailInfo(String data);

}
