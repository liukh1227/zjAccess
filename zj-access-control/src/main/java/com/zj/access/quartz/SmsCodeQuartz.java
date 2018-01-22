package com.zj.access.quartz;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;

import com.zj.access.service.ISmsService;



/**
 * 短信验证码任务
 * @author tanjianhua
 * @date 2016-10-31 下午4:12:20
 */
public class SmsCodeQuartz {

	private static final Logger log = Logger.getLogger(SmsCodeQuartz.class);
	
	@Resource
	private ISmsService smsService;

	public void execute() {
		try {
			smsService.initSmsQuartz();
			log.info("移除短信验证码成功");
		} catch (BeansException e) {
			log.error("短信验证码定时任务异常");
			e.printStackTrace();
		}
		
	}
}
