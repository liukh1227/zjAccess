package com.zj.access.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.lang3.StringUtils;

import com.zj.base.common.ConstConfig;
import com.zj.base.common.Constant;
import com.zj.base.common.utils.sms.SmsUtil;


/**
 * 初始化环境选择
 * @author:tjhua
 * @date:2016-5-18
 * <p>description:</p>
 */
public class InitConfigListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext servletContext = sce.getServletContext();
        String env = servletContext.getInitParameter("spring.profiles.active");
        if(env == null) {
            ConstConfig.ENV = "development";
        } else {
        	ConstConfig.ENV = env;
        }
        servletContext.setAttribute(Constant.ENV, env);//环境变量
        config();
	}

	/**
	 * 配置信息
	 * @author tanjianhua
	 * @date 2016-11-8 下午4:50:44
	 */
	public void config() {
		if(StringUtils.isNotBlank(ConstConfig.ENV)) {
			if(StringUtils.equals(ConstConfig.ENV, "development")) {
				SmsUtil.APPACCESS_SMS_URL = "https://api.chebaotec.com/smsgate";
			} else if(StringUtils.equals(ConstConfig.ENV, "test")) {
				SmsUtil.APPACCESS_SMS_URL = "https://api.chebaotec.com/smsgate";
			} else if(StringUtils.equals(ConstConfig.ENV, "product")) {
				SmsUtil.APPACCESS_SMS_URL = "http://127.0.0.1:8092/smsgate";
			}
		}
	}
}
