package com.zj.access.service;

import java.util.Calendar;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.zj.access.common.DataCache;
import com.zj.access.common.RedisSessionManager;
import com.zj.access.service.ISmsService;
import com.zj.base.common.utils.CommonUtils;


/**
 * @desc 
 * @author liukh
 * @date 2016-12-9 上午10:31:50
 */
@Service("smsService")
@Scope("prototype")
public class SmsServiceImpl implements ISmsService {

	private static final Logger log = Logger.getLogger(SmsServiceImpl.class);

	/**
	 * @desc 初始化短信任务
	 * @author liukh
	 * @date 2016-12-9 上午10:31:11
	 */
	public void initSmsQuartz() {
		try {
			DataCache cache = DataCache.getInstance();
			if(cache != null) {
				RedisSessionManager redisSessionManager = cache.getRedisSessionManager();
				Map<String, String> smsCodeMap = redisSessionManager.hgetall("zjsmscode");
				Calendar c = Calendar.getInstance();
				if(smsCodeMap != null && !smsCodeMap.isEmpty()) {
					Set<String> keySet = smsCodeMap.keySet();
					if(keySet != null && keySet.size() > 0) {
						Iterator<String> keyIt = keySet.iterator();
						String key = null;
						String smsStr = null;
						String createTime = null;
						String[] sms = null;
						while(keyIt.hasNext()) {
							key = keyIt.next(); 
							smsStr = smsCodeMap.get(key);
							if(StringUtils.isNotBlank(smsStr)) {
								sms = smsStr.split("\\|");
								if(sms != null && sms.length == 2) {
									createTime = sms[1];
									if(StringUtils.isNotBlank(createTime)) {
										Calendar smsCreateTime = Calendar.getInstance();
										smsCreateTime.setTime(CommonUtils.parseDate(createTime, "yyyy-MM-dd HH:mm:ss"));
										long now = c.getTimeInMillis();
										long smsTime = smsCreateTime.getTimeInMillis();
										long minute = (now - smsTime) / (1000 * 60);// 转化minute
										if(minute >= 3) {//如果超过3分钟,就移除
											redisSessionManager.hdel("zjsmscode", key);
										}
									}
								}
							}
						}
					}
				}
			}
			log.info("移除短信验证码成功");
		} catch (Exception e) {
			log.error("短信验证码定时任务异常");
			e.printStackTrace();
			throw new RuntimeException("initSmsQuartz Exception!");
		}
	}
}
