package com.zj.entity.unit;
/**
 *  FTP信息实体
 * @author liukh
 * @date 2017-3-7 上午10:35:56
 */
public class FTPInfo {

	private String ftpUserName;
	private String ftpPassword;
	private String ftpHost;
	private String ftpPort;
	private String ftpPath;
	private String writeTempFilePath;
	public String getFtpUserName() {
		return ftpUserName;
	}
	public void setFtpUserName(String ftpUserName) {
		this.ftpUserName = ftpUserName;
	}
	public String getFtpPassword() {
		return ftpPassword;
	}
	public void setFtpPassword(String ftpPassword) {
		this.ftpPassword = ftpPassword;
	}
	public String getFtpHost() {
		return ftpHost;
	}
	public void setFtpHost(String ftpHost) {
		this.ftpHost = ftpHost;
	}
	public String getFtpPort() {
		return ftpPort;
	}
	public void setFtpPort(String ftpPort) {
		this.ftpPort = ftpPort;
	}
	public String getFtpPath() {
		return ftpPath;
	}
	public void setFtpPath(String ftpPath) {
		this.ftpPath = ftpPath;
	}
	public String getWriteTempFilePath() {
		return writeTempFilePath;
	}
	public void setWriteTempFilePath(String writeTempFilePath) {
		this.writeTempFilePath = writeTempFilePath;
	}

}
