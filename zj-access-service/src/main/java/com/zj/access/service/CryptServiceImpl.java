package com.zj.access.service;

import org.apache.commons.net.util.Base64;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.zj.base.common.Constant;
import com.zj.base.common.utils.Xxtea;

public class CryptServiceImpl {

	private static Logger log = Logger.getLogger(CryptServiceImpl.class);

	/**
	 * 解密
	 * @author:lliukh
	 * @date:2016-3-31 下午2:44:47
	 * <p>description:</p>
	 * @param data
	 * @return
	 */
	protected String decrypt(String data) {
		log.info("---decrypt ---- data ====== " + data);
		try {
			byte[] dataBytes = data.getBytes("UTF-8");
			byte[] base64Byte = Base64.decodeBase64(dataBytes);
			byte[] key = Constant.KEY.getBytes();
			byte[] deByte = Xxtea.decrypt(base64Byte, key);
			data = new String(new String(deByte).getBytes("UTF-8"), "UTF-8");
		} catch (Exception e) {
			log.error("---decrypt---- data decrypt error ---");
			e.printStackTrace();
			throw new RuntimeException("---decrypt---- data decrypt error ---");
		}
		log.info("---decrypt ---- data decrypt ====== " + data);
		
		try {
			JSON.parseObject(data);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("---decrypt ---- data decrypt to JSONObject error ====== ");
			throw new RuntimeException("---decrypt ---- data decrypt to JSONObject error ======");
		}
		return data;
	}
}
