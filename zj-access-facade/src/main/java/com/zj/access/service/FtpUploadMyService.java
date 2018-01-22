package com.zj.access.service;

import java.io.InputStream;

/**
 * 文件FTP上传
 * @author liukh
 * @date 2017-4-16 上午9:27:13
 */
public interface FtpUploadMyService {

	/**
	 * 设备上传文件
	 * 
	 * @author liukh
	 * @date 2017-3-7 上午10:17:05
	 * @param in
	 * @param type
	 * @param userId
	 * @param suffix
	 * @param realPath
	 * @return
	 */
	public String devicePicUpload(InputStream in, String type, String userId,
			String suffix, String realPath);

	/**
	 * 用户上传文件
	 * 
	 * @author liukh
	 * @date 2017-3-7 上午10:17:16
	 * @param in
	 * @param type
	 * @param userId
	 * @param suffix
	 * @param realPath
	 * @return
	 */
	public String userPicUpload(InputStream in, String type, String userId,
			String suffix, String realPath);

}
