package com.zj.base.common.utils;

import org.apache.shiro.crypto.hash.SimpleHash;

import com.zj.base.common.Constant;


/**
 * shiro 密码加载工具
 * 当用户新增/修改密码必须调用当前该帮助类生成password
 * @author tanjh
 * @date 2016-8-19 下午3:27:21
 */
public class PasswordUtils {

	/**
	 * 生成密码
	 * passwordSalt = new SecureRandomNumberGenerator().nextBytes().toHex();
	 * @author tanjh
	 * @date 2016-8-19 下午3:27:54
	 * @return
	 */
	public static String encyptPassword(String account, String password,
			String passwordSalt) {
		String algorithmName = Constant.ALGORITHMNAME;//加密方式
		int hashIterations = Constant.HASHITERATIONS;//循环次数
		SimpleHash hash = new SimpleHash(algorithmName, password, account
				+ passwordSalt, hashIterations);
		String encodedPassword = hash.toHex();
		return encodedPassword;
	}
}
