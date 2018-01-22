package com.zj.access.service;

import java.io.InputStream;

/**
 * 文件FTP上传
 * @author tanjianhua
 * @date 2016-10-13 下午6:15:46
 */
public interface IAppFtpUploadService {

	/**
	 * 上传头像
	 * @author tanjianhua
	 * @date 2016-11-9 下午3:03:25
	 * @param in
	 * @param userId
	 * @param suffix
	 * @param realPath
	 * @return
	 */
	public String addUserHead(InputStream in,String userId,String suffix,String realPath);

	/**
	 * 上传意见反馈图片
	 * @author tanjianhua
	 * @date 2016-11-23 下午4:09:38
	 * @param in
	 * @param userId
	 * @param suffix
	 * @param realPath
	 * @return
	 */
	public String addFeedBack(InputStream in,String userId,String suffix,String realPath);
}
