package com.zj.access.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.zj.access.service.IInitService;


/**
 * 初始化servlet
 * @author tanjh
 * @date 2016-8-19 下午2:45:58
 */
public class InitServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(InitServlet.class);

	/**
	 * init
	 * (non-Javadoc)
	 * @see javax.servlet.GenericServlet#init(javax.servlet.ServletConfig)
	 * @author tanjh
	 * @date 2016-8-19 下午2:51:06
	 * @param servletConfig
	 * @throws ServletException
	 */
	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		super.init(servletConfig);
		log.info("========初始化数据开始==========");
		try {
			String webAppRootValue = servletConfig.getServletContext().getRealPath("/");
			System.setProperty("webAppRootKey", webAppRootValue);
			// 得到容器环境
			WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletConfig.getServletContext());
			initData(ctx,servletConfig.getServletContext());
		} catch (Exception e) {
			log.error("========初始化数据失败，系统启动失败==========");
			log.error(this, e);
		}
		log.info("========初始化数据完成==========");
	}

	/**
	 * 初始化数据
	 * @author tanjh
	 * @date 2016-8-19 下午2:51:20
	 * @param context
	 * @param application
	 * @throws Exception
	 */
	protected void initData(WebApplicationContext context, ServletContext application) throws Exception {
		try {
			IInitService initService = (IInitService) context.getBean("initService");
			initService.initRedis();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
