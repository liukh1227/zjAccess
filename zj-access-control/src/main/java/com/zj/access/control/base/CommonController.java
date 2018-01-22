package com.zj.access.control.base;

import java.beans.PropertyEditorSupport;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.multipart.MultipartFile;

import com.zj.base.common.Constant;
/**
 * 
 * @Description: 继承该类的子类不需要权限验证
 * @Author: sunsf
 * @UpdateUser: sunsf
 * @UpdateDate: 2012-7-18 下午03:24:54
 * @UpdateRemark: 第一个版本功能完善
 * @Version: V1.0
 */
public class CommonController {

	private final static Logger logger = Logger.getLogger(CommonController.class);

	/**
	 * 根据HTTPServletRequest获取客户端IP <br>
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return String IPAddr
	 */
	public String getClientIPAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("http_client_ip");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		// 如果是多级代理，那么取第一个ip为客户ip
		if (ip != null && ip.indexOf(",") != -1) {
			ip = ip.substring(ip.lastIndexOf(",") + 1, ip.length()).trim();
		}
		return ip;
	}

	/**
	 * 返回当前登录的Visitor
	 * 
	 * @param request
	 * @return
	 */
	protected Object getVisitor(HttpServletRequest request) {
		return request.getSession().getAttribute(Constant.SYSTEM_VISITOR);
	}

	/**
	 * 设置访客
	 * @author tanjianhua
	 * @date 2016-11-9 下午2:52:30
	 * @param request
	 * @param obj
	 */
	protected void setVisitor(HttpServletRequest request, Object obj) {
		request.getSession().setAttribute(Constant.SYSTEM_VISITOR, obj);
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		final DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		final DateFormat dateFormat3 = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
			public void setAsText(String value) {
				try {
					setValue(dateFormat.parse(value));
				} catch (ParseException e1) {
					try {
						setValue(dateFormat2.parse(value));
					} catch (ParseException e2) {
						try {
							setValue(dateFormat3.parse(value));
						} catch (ParseException e3) {
							setValue(null);
						}
					}
				}
			}

			public String getAsText() {
				return dateFormat.format((Date) getValue());
			}

		});
	}

	/**
	 * 文件上传共用方法
	 * 
	 * @param file
	 * @param fullFileName
	 * @return 上传操作结果
	 * @throws java.io.IOException
	 * @throws IllegalStateException
	 */
	public boolean uploadFile(MultipartFile file, String fullFileName)
			throws IllegalStateException, IOException {
		logger.info("上传文件：" + fullFileName);
		if (file == null || fullFileName == null
				|| "".equals(fullFileName.trim())) {
			logger.info("上传文件：文件对象为空或文件名为空！");
			return false;
		}
		file.transferTo(new File(fullFileName));
		logger.info("上传文件:成功!");
		return true;
	}

	/**
	 * 文件下载
	 * 
	 * @param request
	 * @param response
	 * @param path
	 */
	public void downLoad(HttpServletRequest request,
			HttpServletResponse response, String path) {
		try {
			String filePath = new String(path.getBytes("ISO-8859-1"), "UTF-8");
			File f = new File(filePath);
			if (!f.exists()) {
				response.sendError(404, "File not found!");
				return;
			}
			BufferedInputStream br = new BufferedInputStream(
					new FileInputStream(f));
			byte[] buf = new byte[1024];
			int len = 0;
			response.reset();
			response.setContentType("application/x-msdownload");
			response.setCharacterEncoding("UTF-8");
			String fileName = URLEncoder.encode(f.getName(), "UTF-8");
			response.addHeader("Content-Disposition", "attachment; filename=\""
					+ fileName + "\"");
			OutputStream out = response.getOutputStream();
			while ((len = br.read(buf)) > 0)
				out.write(buf, 0, len);
			br.close();
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
