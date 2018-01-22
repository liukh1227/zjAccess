package com.zj.access.service;

import com.zj.base.common.utils.CommonUtils;
import com.zj.base.common.utils.PasswordUtils;

public class PasswordGener {

	/**
	 * @author liukh
	 * @date 2017-6-29 下午5:36:55
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String passwordSalt = CommonUtils.getRandomString(40).toLowerCase();
     System.out.println("----passwordSalt-----" + passwordSalt);
     String loginId="13433083068";
     String password="123456";
		String userPass = PasswordUtils.encyptPassword(loginId, password, passwordSalt);
		
	     System.out.println("----userPass-----" + userPass);
	}

}
