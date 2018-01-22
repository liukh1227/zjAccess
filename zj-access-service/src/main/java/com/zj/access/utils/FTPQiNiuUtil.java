package com.zj.access.utils;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.zj.base.common.ConstConfig;
import com.zj.entity.unit.FTPQiNiuInfo;
import com.zj.entity.unit.FTPQiNiuPutRet;

public class FTPQiNiuUtil {

	private static Logger logger = Logger.getLogger(FTPQiNiuUtil.class);
	
	private static FTPQiNiuInfo ftpQiNiuInfo;
/**
 * 解析七牛上传配置文件
 * @author liukh
 * @date 2017-4-16 下午2:22:44
 * @return
 */
	public static FTPQiNiuInfo readFTPQiNiuInfo() {
		
		FTPQiNiuInfo ftpInfo = new FTPQiNiuInfo();
		
		String accessKey = "";
		String secretKey = "";
		String bucket_device = "";
		String bucket_user = "";
		String bucket_user_photo = "";
		String domainFilePath_device = "";
		String domainFilePath_user = "";
		String domainFilePath_user_photo = "";
		
		InputStream in = null;
		if(StringUtils.equals(ConstConfig.ENV, "development")) {
			in = FTPQiNiuUtil.class.getClassLoader().getResourceAsStream("ftp_qiniu_development.properties");
		} else if(StringUtils.equals(ConstConfig.ENV, "test")) {
			in = FTPQiNiuUtil.class.getClassLoader().getResourceAsStream("ftp_qiniu_test.properties");
		} else if(StringUtils.equals(ConstConfig.ENV, "product")) {
			in = FTPQiNiuUtil.class.getClassLoader().getResourceAsStream("ftp_qiniu_product.properties");
		} else {
			in = FTPQiNiuUtil.class.getClassLoader().getResourceAsStream("ftp_qiniu_development.properties");
		}
		if (in == null) {
			logger.info("配置文件ftpqiniu.properties读取失败");
		} else {
			Properties properties = new Properties();
			try {
				properties.load(in);
				accessKey = properties.getProperty("accessKey");
				secretKey = properties.getProperty("secretKey");
				bucket_device = properties.getProperty("bucket_device");
				bucket_user = properties.getProperty("bucket_user");
				bucket_user_photo = properties.getProperty("bucket_user_photo");
				domainFilePath_device = properties.getProperty("domainFilePath_device");
				domainFilePath_user = properties.getProperty("domainFilePath_user");
				domainFilePath_user_photo = properties.getProperty("domainFilePath_user_photo");
				
				ftpInfo.setAccessKey(accessKey);
				ftpInfo.setSecretKey(secretKey);
				ftpInfo.setBucketDevice(bucket_device);
				ftpInfo.setBucketUser(bucket_user);
				ftpInfo.setBucketUserPhoto(bucket_user_photo);
				ftpInfo.setDomainDeviceFilePath(domainFilePath_device);
				ftpInfo.setDomainUserFilePath(domainFilePath_user);
				ftpInfo.setDomainUserPhotoFilePath(domainFilePath_user_photo);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return ftpInfo;
	}
	
	/**
	 * 获取FTP七牛配置实例
	 * @author liukh
	 * @date 2017-4-16 下午2:28:14
	 * @return
	 */
	public static FTPQiNiuInfo getFTPInstance() {
		if(ftpQiNiuInfo == null) {
			ftpQiNiuInfo = readFTPQiNiuInfo();
		}
		return ftpQiNiuInfo;
	}
	
	/**
	 * 上传
	 * @author liukh
	 * @date 2017-4-16 下午2:57:08
	 * @param in 上传的文件
	 * @param accessKey 密钥 AK
	 * @param secretKey 密钥 SK
	 * @param bucket 存储域
	 * @param key 默认不指定key的情况下，以文件内容的hash值作为文件名
	 * @return
	 */
	public static FTPQiNiuPutRet upload(InputStream in,String accessKey,String  secretKey,String bucket,String key){
		 FTPQiNiuPutRet putRet = null;
		try {
		    Auth auth = Auth.create(accessKey, secretKey);
		    String upToken = auth.uploadToken(bucket);
		  //构造一个带指定Zone对象的配置类
		    Configuration cfg = new Configuration(Zone.zone2());
			//...其他参数参考类注释
			UploadManager uploadManager = new UploadManager(cfg);
			  byte[] uploadBytes = com.zj.access.utils.IOUtils.toByteArray(in);
		    try {
				   Response response = uploadManager.put(uploadBytes, key, upToken);
				   putRet  = JSON.parseObject(response.bodyString(), FTPQiNiuPutRet.class);
		  
		    } catch (QiniuException ex) {
		    	if(in != null){
					in.close();
				}
		        Response r = ex.response;
		        System.err.println(r.toString());
		        try {
		            System.err.println(r.bodyString());
		        } catch (QiniuException ex2) {
		        	ex2.printStackTrace();
		        }
		    }
		} catch (Exception ex) {
			if(in != null){
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		   ex.printStackTrace();
		}
		try {
			if(in != null){
				in.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return putRet;
		
	}
	
	 
}
