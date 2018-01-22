package com.zj.entity.unit;
/**
 *  qiniu配置信息实体
 * @author liukh
 * @date 2017-3-7 上午10:35:56
 */
public class FTPQiNiuInfo {
	private String accessKey;
	private String secretKey;
	private String bucketDevice;
	private String bucketUser;
	private String bucketUserPhoto;
	private String domainDeviceFilePath;
	private String domainUserFilePath;
	private String domainUserPhotoFilePath;

	
	public String getAccessKey() {
		return accessKey;
	}
	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}
	public String getSecretKey() {
		return secretKey;
	}
	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}
	public String getBucketDevice() {
		return bucketDevice;
	}
	public void setBucketDevice(String bucketDevice) {
		this.bucketDevice = bucketDevice;
	}
	public String getBucketUser() {
		return bucketUser;
	}
	public void setBucketUser(String bucketUser) {
		this.bucketUser = bucketUser;
	}
	public String getBucketUserPhoto() {
		return bucketUserPhoto;
	}
	public void setBucketUserPhoto(String bucketUserPhoto) {
		this.bucketUserPhoto = bucketUserPhoto;
	}
	public String getDomainDeviceFilePath() {
		return domainDeviceFilePath;
	}
	public void setDomainDeviceFilePath(String domainDeviceFilePath) {
		this.domainDeviceFilePath = domainDeviceFilePath;
	}
	public String getDomainUserFilePath() {
		return domainUserFilePath;
	}
	public void setDomainUserFilePath(String domainUserFilePath) {
		this.domainUserFilePath = domainUserFilePath;
	}
	public String getDomainUserPhotoFilePath() {
		return domainUserPhotoFilePath;
	}
	public void setDomainUserPhotoFilePath(String domainUserPhotoFilePath) {
		this.domainUserPhotoFilePath = domainUserPhotoFilePath;
	}
	
}
