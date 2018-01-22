package com.zj.access.service;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import com.zj.access.utils.FTPQiNiuUtil;
import com.zj.base.common.utils.CommonUtils;
import com.zj.common.utils.MD5Util;
import com.zj.entity.unit.FTPQiNiuInfo;
import com.zj.entity.unit.FTPQiNiuPutRet;
/**
 * 文件FTP上传
 * @author liukh
 * @date 2017-3-7 上午10:19:35
 */
@Service("ftpUploadMyService")
@Scope("prototype")
public class FtpUploadMyServiceIm implements FtpUploadMyService{
	private static final Logger log = Logger.getLogger(FtpUploadMyServiceIm.class);
	String uploadSuccessCode="200";
	/**
	 * 设备上传文件
	 * @author liukh
	 * @date 2017-3-7 上午10:19:50 
	 * @param in
	 * @param type
	 * @param userId
	 * @param suffix
	 * @param realPath
	 * @return
	 * (non-Javadoc)
	 * @see com.zj.access.service.FtpUploadService#devicePicUpload(java.io.InputStream, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public String devicePicUpload(InputStream in, String type, String userId,
			String suffix, String realPath) {
		String result = "{\"rcode\" : 1}";
		// MD5加密，无规则，避免有规则的名称被恶意攻击
		//默认不指定key的情况下，以文件内容的hash值作为文件名
		String key = null;
		try {
			System.out.println(in.available());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
		FTPQiNiuInfo ftpInfo = FTPQiNiuUtil.getFTPInstance();
	
		try{
			key = MD5Util.GetMD5Code(new SimpleDateFormat(
					"yyyyMMddHHmmss").format(new Date())
					+ CommonUtils.getRandomString(8)) + ".jpg";
			
			FTPQiNiuPutRet purRet = FTPQiNiuUtil.upload(in, ftpInfo.getAccessKey(),  ftpInfo.getSecretKey(), ftpInfo.getBucketDevice(), key);
			if(purRet != null){
				if(purRet.getCode()!= null && !purRet.getCode().equals(uploadSuccessCode)){
					 result = "{\"rcode\" : 1,\"rinfo\":\""+purRet.getCode()+
							 purRet.getError()!= null ? purRet.getError():""
							 +"}";
				}else{
					if(purRet.getHash()!= null){
						result = "{\"rcode\" : 0,\"data\":{ \"path\" : \""
								+ ftpInfo.getDomainDeviceFilePath()+purRet.getKey()
								+"\", \"type\" : \"" + type + "\"}}";
					}
					
				}
			}else {
				log.error("---userPicUpload ---- error ====== ");
				result = "{\"rcode\" : 1}";
			}

		}catch(Exception e){
			e.printStackTrace();
		}
		
		return result;
	}
/**
 * 用户上传文件
 * @author liukh
 * @date 2017-3-7 上午10:19:56 
 * @param in
 * @param type
 * @param userId
 * @param suffix
 * @param realPath
 * @return
 * (non-Javadoc)
 * @see com.zj.access.service.FtpUploadService#userPicUpload(java.io.InputStream, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
 */
	@Override
	public String userPicUpload(InputStream in, String type, String userId,
			String suffix, String realPath) {
		String result = "{\"rcode\" : 1}";
		// MD5加密，无规则，避免有规则的名称被恶意攻击
		//默认不指定key的情况下，以文件内容的hash值作为文件名
		String key = null;
		try {
			System.out.println(in.available());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
		FTPQiNiuInfo ftpInfo = FTPQiNiuUtil.getFTPInstance();
	
		try{
			key = MD5Util.GetMD5Code(new SimpleDateFormat(
					"yyyyMMddHHmmss").format(new Date())
					+ CommonUtils.getRandomString(8)) + ".jpg";
			
			FTPQiNiuPutRet purRet = FTPQiNiuUtil.upload(in, ftpInfo.getAccessKey(),  ftpInfo.getSecretKey(), ftpInfo.getBucketUser(), key);
			if(purRet != null){
				if(purRet.getCode()!= null && !purRet.getCode().equals(uploadSuccessCode)){
					 result = "{\"rcode\" : 1,\"rinfo\":\""+purRet.getCode()+
							 purRet.getError()!= null ? purRet.getError():""
							 +"}";
				}else{
					if(purRet.getHash()!= null){
						result = "{\"rcode\" : 0,\"data\":{ \"path\" : \""
								+ ftpInfo.getDomainUserFilePath()+purRet.getKey()
								+"\", \"type\" : \"" + type + "\"}}";
					}
					
				}
			}else {
				log.error("---userPicUpload ---- error ====== ");
				result = "{\"rcode\" : 1}";
			}

		}catch(Exception e){
			e.printStackTrace();
		}
		
		return result;
	}

}
