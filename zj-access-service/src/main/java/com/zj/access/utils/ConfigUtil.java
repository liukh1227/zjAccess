package com.zj.access.utils;



import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import com.zj.base.common.ConstConfig;
import com.zj.entity.unit.ConfigInfo;

public class ConfigUtil {

	private static Logger logger = Logger.getLogger(ConfigUtil.class);
	
	private static ConfigInfo configInfo;


	public static ConfigInfo readConfigInfo() {
		
		ConfigInfo configInfo = new ConfigInfo();
		
		String config_logWriteTempFilePath = "";
		String config_jsonSecretKey = "";

		
		InputStream in = null;
		if(StringUtils.equals(ConstConfig.ENV, "development")) {
			in = ConfigUtil.class.getClassLoader().getResourceAsStream("config_development.properties");
		} else if(StringUtils.equals(ConstConfig.ENV, "test")) {
			in = ConfigUtil.class.getClassLoader().getResourceAsStream("config_test.properties");
		} else if(StringUtils.equals(ConstConfig.ENV, "product")) {
			in = ConfigUtil.class.getClassLoader().getResourceAsStream("config_product.properties");
		} else {
			in = ConfigUtil.class.getClassLoader().getResourceAsStream("config_development.properties");
		}
		if (in == null) {
			logger.info("配置文件config.properties读取失败");
		} else {
			Properties properties = new Properties();
			try {
				properties.load(in);
				config_logWriteTempFilePath = properties.getProperty("config_logWriteTempFilePath");
				config_jsonSecretKey = properties.getProperty("config_jsonSecretKey");
				
				configInfo.setLogWriteTempFilePath(config_logWriteTempFilePath);
				configInfo.setJsonSecretKey(config_jsonSecretKey);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return configInfo;
	}
	

	public static ConfigInfo getConfigInstance() {
		if(configInfo == null) {
			configInfo = readConfigInfo();
		}
		return configInfo;
	}
}
