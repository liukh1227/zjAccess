package com.zj.access.common;

/**
 * redis cache
 * @author tanjh
 * @date 2016-8-18 下午5:39:14
 */
public class DataCache {

	private static DataCache INSTANCE;

	/**
	 * redis
	 */
	private RedisSessionManager redisSessionManager;

	private DataCache() {
	}

	public static DataCache getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new DataCache();
		}
		return INSTANCE;
	}

	public static DataCache getInstance(RedisSessionManager redisSessionManager) {
		if (INSTANCE == null) {
			INSTANCE = new DataCache();
		}
		INSTANCE.redisSessionManager = redisSessionManager;
		return INSTANCE;
	}


	/**
	 * 保存验证码
	 * @author liukh
	 * @date 2016-10-31 下午3:59:33
	 * @param phone
	 * @param value
	 */
	public void setSmsCode(String phone, String value) {
		//格式:
		//   15920973190		1345|2016-10-31 11:56:00
		redisSessionManager.hset("zjsmscode", phone, value);
	}

	/**
	 * 获取验证码
	 * @author liukh
	 * @date 2016-10-31 上午11:57:31
	 * @param phone
	 * @return
	 */
	public String getSmsCode(String phone) {
		//格式:
		//   15920973190		1345|2016-10-31 11:56:00
		String smsCode = redisSessionManager.hget("zjsmscode", phone);
		return smsCode;
	}

	/**
	 * 删除验证码
	 * @author liukh
	 * @date 2016-11-9 上午10:58:10
	 * @param phone
	 * @return
	 */
	public void delSmsCode(String phone) {
		//格式:
		//   15920973190		1345|2016-10-31 11:56:00
		redisSessionManager.hdel("zjsmscode", phone);
	}

	/**
	 * redissessionManager
	 * @author tanjh
	 * @date 2016-8-18 下午5:45:08
	 * @return
	 */
	public RedisSessionManager getRedisSessionManager() {
		return redisSessionManager;
	}

	public void setRedisSessionManager(RedisSessionManager redisSessionManager) {
		this.redisSessionManager = redisSessionManager;
	}

}
