package com.zj.access.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.zj.access.utils.FTPUtil;
import com.zj.base.common.ConstConfig;
import com.zj.base.common.utils.CommonUtils;
import com.zj.common.utils.ImageUtils;
import com.zj.common.utils.MD5Util;
import com.zj.entity.unit.FTPInfo;
/**
 * 文件FTP上传
 * @author liukh
 * @date 2017-3-7 上午10:19:35
 */
@Service("ftpUploadService")
@Scope("prototype")
public class FtpUploadServiceIm implements FtpUploadService{
	private static final Logger log = Logger.getLogger(FtpUploadServiceIm.class);
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
		// MD5加密，无规则，避免有规则的名称被恶意攻击
		String mfile = "";
		try {
			System.out.println(in.available());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		File file = null;
		String ftpPath = "";
		String result = "{\"rcode\" : 1}";
		OutputStream out = null;
		BufferedOutputStream bos = null;
		if (StringUtils.isNotBlank(type)) {
			try {
				String dirs = "upload/device";
				mfile = MD5Util.GetMD5Code(new SimpleDateFormat(
						"yyyyMMddHHmmss").format(new Date())
						+ CommonUtils.getRandomString(8)) + ".jpg";
				String filePath = dirs + "/" + mfile;
				File dirFile = new File(realPath + dirs);
				if (!dirFile.exists()) {
					dirFile.mkdirs();
				}
				file = new File(realPath + filePath);
				if (!file.exists()) {
					dirFile.createNewFile();
				}

				byte[] bs = new byte[1024 * 20];
				out = new FileOutputStream(file);
				bos = new BufferedOutputStream(out);
				int length = 0;
				while ((length = IOUtils.read(in, bs)) > 0) {
					bos.write(bs, 0, length);
				}
				bos.flush();
				out.flush();
				bos.close();
				out.close();
				in.close();
				// 加水印的操作
				InputStream waterIn = FtpUploadServiceIm.class.getClassLoader().getResourceAsStream("watermarkPicture.png");
				ImageUtils.pressImage(file ,waterIn, -1, -1, 1);
				if(waterIn != null){
					waterIn.close();
				}
				// 上传至FTP服务器
				FTPInfo ftpInfo = FTPUtil.getFTPInstance();
				// ftpPath = /device/201232131233323/20151030
				ftpPath = "/device/" + userId + "/"
						+ CommonUtils.getDateString(new Date(), "yyyyMMdd")
						+ "/";
				// 原图
				boolean flag = FTPUtil.upload(ftpPath,
						ftpInfo.getFtpUserName(), ftpInfo.getFtpPassword(),
						ftpInfo.getFtpHost(),
						Integer.valueOf(ftpInfo.getFtpPort()), file);

				if (flag) {
					log.info("---devicePicUpload ---- success ====== ");
					//path = http://api.chebaotec.com/app/device/201232131233323/20151030/adi123213213213213.jpg
					result = "{\"rcode\" : 0,\"data\":{ \"path\" : \""
							+ ConstConfig.FTP_APP + ftpPath + mfile
							+ "\", \"type\" : \"" + type + "\"}}";
				} else {
					log.error("---devicePicUpload ---- error ====== ");
					result = "{\"rcode\" : 1}";
				}
			} catch (IOException e) {
				result = "{\"rcode\" : 1}";
				log.error("---devicePicUpload ---- IOException ====== ");
				e.printStackTrace();
			} catch (Exception e) {
				result = "{\"rcode\" : 1}";
				log.error("---devicePicUpload ---- exception ====== ");
				e.printStackTrace();
			}
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
		// MD5加密，无规则，避免有规则的名称被恶意攻击
		String mfile = "";
		try {
			System.out.println(in.available());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		File file = null;
		String ftpPath = "";
		String result = "{\"rcode\" : 1}";
		OutputStream out = null;
		BufferedOutputStream bos = null;
		if (StringUtils.isNotBlank(type)) {
			try {
				String dirs = "upload/user";
				mfile = MD5Util.GetMD5Code(new SimpleDateFormat(
						"yyyyMMddHHmmss").format(new Date())
						+ CommonUtils.getRandomString(8)) + ".jpg";
				String filePath = dirs + "/" + mfile;
				File dirFile = new File(realPath + dirs);
				if (!dirFile.exists()) {
					dirFile.mkdirs();
				}
				file = new File(realPath + filePath);
				if (!file.exists()) {
					dirFile.createNewFile();
				}
				
				byte[] bs = new byte[1024 * 20];
				out = new FileOutputStream(file);
				bos = new BufferedOutputStream(out);
				int length = 0;
				while ((length = IOUtils.read(in, bs)) > 0) {
					bos.write(bs, 0, length);
				}
				bos.flush();
				out.flush();
				bos.close();
				out.close();
				in.close();
				// 加水印的操作
				InputStream waterIn = FtpUploadServiceIm.class.getClassLoader().getResourceAsStream("watermarkPicture.png");
				try {
					System.out.println(waterIn.available());
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				ImageUtils.pressImage(file ,waterIn, -1, -1, 1);
				if(waterIn != null){
					waterIn.close();
				}
				// 上传至FTP服务器
				FTPInfo ftpInfo = FTPUtil.getFTPInstance();
				// ftpPath = /user/201232131233323/20151030
				ftpPath = "/user/" + userId + "/"
						+ CommonUtils.getDateString(new Date(), "yyyyMMdd")
						+ "/";
				// 原图
				boolean flag = FTPUtil.upload(ftpPath,
						ftpInfo.getFtpUserName(), ftpInfo.getFtpPassword(),
						ftpInfo.getFtpHost(),
						Integer.valueOf(ftpInfo.getFtpPort()), file);
				
				if (flag) {
					log.info("---userPicUpload ---- success ====== ");
					//path = http://api.chebaotec.com/app/user/201232131233323/20151030/adi123213213213213.jpg
					result = "{\"rcode\" : 0,\"data\":{ \"path\" : \""
							+ ConstConfig.FTP_APP + ftpPath + mfile
							+ "\", \"type\" : \"" + type + "\"}}";
				} else {
					log.error("---userPicUpload ---- error ====== ");
					result = "{\"rcode\" : 1}";
				}
			} catch (IOException e) {
				result = "{\"rcode\" : 1}";
				log.error("---userPicUpload ---- IOException ====== ");
				e.printStackTrace();
			} catch (Exception e) {
				result = "{\"rcode\" : 1}";
				log.error("---userPicUpload ---- exception ====== ");
				e.printStackTrace();
			}
		}
		return result;
	}

}
