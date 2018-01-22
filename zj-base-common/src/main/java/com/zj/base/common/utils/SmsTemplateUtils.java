package com.zj.base.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SmsTemplateUtils {

	/**
	 * 解析模板信息
	 * @author tanjianhua
	 * @date 2016-10-31 下午3:11:18
	 * @return
	 */
	public static String getSmsTemplateInfo() {
		String phoneTemplate = null;
		try {
			InputStream in = SmsTemplateUtils.class.getClassLoader().getResourceAsStream("sms/template.properties");
			Properties properties = new Properties();
			properties.load(in);
			phoneTemplate = properties.getProperty("phoneTemplate").trim();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return phoneTemplate;
	}
}
