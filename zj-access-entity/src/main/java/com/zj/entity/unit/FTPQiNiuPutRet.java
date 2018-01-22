package com.zj.entity.unit;

/**
 * 七牛存储返回实体类
 * @author liukh
 * @date 2017-4-16 下午1:57:04
 */
public class FTPQiNiuPutRet {

	private String hash;
	private String key;
	private String code;
	private String error;
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	
	
}
