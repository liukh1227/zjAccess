package com.zj.access.control.ftp;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.zj.access.control.base.CommonController;
import com.zj.access.service.FtpService;

/**
 * 文件FTP上传Controller
 * @author liukh
 * @date 2017-3-7 上午11:09:34
 */
@Scope("prototype")
@RequestMapping(value="/uploadFile")
@Controller
public class FTPUploadController extends CommonController {
	
	private static final Logger log = Logger.getLogger(FTPUploadController.class);
	
	//@Autowired
	//private FtpUploadService ftpUploadService;
	//@Autowired
	//private FtpUploadMyService ftpUploadMyService;
	@Autowired
	private FtpService ftpService;

	

	/**
	 * 设备上传图片
	 * @author liukh
	 * @date 2017-3-7 上午11:17:37
	 * @param request
	 * @param response
	 * @param type
	 * @param userId
	 * @param ftpfile
	 * @return
	 */
	@RequestMapping(value = "devicePicUpload", method = {RequestMethod.POST})
	@ResponseBody
	public String devicePicUpload(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value="type", required = false) String type,
			@RequestParam(value="userId", required = true) String userId,
			@RequestParam(value = "ftpfile") MultipartFile ftpfile) {
		String result = null;
		String realPath = request.getServletContext().getRealPath("/");
		InputStream in = null;
		String suffix = null;
		try {
			String fileName = ftpfile.getOriginalFilename();
			suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
			in = ftpfile.getInputStream();
			result = ftpService.devicePicUpload(in, type, userId, suffix, realPath);
		} catch (Exception e) {
			result = "{\"rcode\" : 1}";
			log.error("设备上传异常!");
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
	
	/**
	 * 用户上传证件及头像
	 * @author liukh
	 * @date 2017-3-7 上午11:18:02
	 * @param request
	 * @param response
	 * @param type
	 * @param userId
	 * @param ftpfile
	 * @return
	 */
	@RequestMapping(value = "userPicUpload", method = {RequestMethod.POST})
	@ResponseBody
	public String userPicUpload(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value="type", required = false) String type,
			@RequestParam(value="userId", required = true) String userId,
			@RequestParam(value = "ftpfile") MultipartFile ftpfile) {
		String result = null;
		String realPath = request.getServletContext().getRealPath("/");
		InputStream in = null;
		String suffix = null;
		try {
			String fileName = ftpfile.getOriginalFilename();
			suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
			in = ftpfile.getInputStream();
			result = ftpService.userPicUpload(in, type, userId, suffix, realPath);
		} catch (Exception e) {
			result = "{\"rcode\" : 1}";
			log.error("用户上传异常!");
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
}
