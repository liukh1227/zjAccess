package com.zj.entity.unit;


/**
 * 配置文件实体类
 * @author liukh
 * @date 2016-12-5 下午3:32:13
 */
public class ConfigInfo {
	
	private String logWriteTempFilePath;
	private String jsonSecretKey;
	
	public String getLogWriteTempFilePath() {
		return logWriteTempFilePath;
	}
	public String getJsonSecretKey() {
		return jsonSecretKey;
	}
	public void setLogWriteTempFilePath(String logWriteTempFilePath) {
		this.logWriteTempFilePath = logWriteTempFilePath;
	}
	public void setJsonSecretKey(String jsonSecretKey) {
		this.jsonSecretKey = jsonSecretKey;
	}
	
}
