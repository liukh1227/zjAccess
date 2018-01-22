package com.zj.base.common.utils;

import java.io.InputStream;
import java.util.Properties;

/**
 * properties文件读取
 * 
 * @author tanjianhua
 * @date 2016-9-26 下午7:21:14
 */
public class PropertiesUtils {

	// 定义文件属性类
	private static Properties properties = null;
	
	/**
	 * 获取值
	 * @author tanjianhua
	 * @date 2016-9-26 下午7:23:05
	 * @param key
	 * @return
	 */
	public static String getProperty(String filePath, String key) {
		String value = "";
		try {
			// 加载资源
			properties = new Properties();
			InputStream in = PropertiesUtils.class.getResourceAsStream(filePath);
			properties.load(in);
			value = properties.getProperty(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}
}
