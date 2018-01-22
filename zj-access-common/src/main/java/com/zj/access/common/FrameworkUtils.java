package com.zj.access.common;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zj.base.entity.base.dto.BaseDto;

/**
 * esb 帮助类
 * 
 * @author liukh
 * @date 2017-3-7 下午10:31:29
 */
public class FrameworkUtils {

	private static final Logger log = Logger.getLogger(FrameworkUtils.class);

	public static final String SUCCESS_RCODE = "0";

	public static final String NO_DATA_RCODE = "21";

	public static final String ERROR_RCODE = "1";

	public static final String DEFAULT_SUCCESS_INFO = "操作成功!";

	public static final String DEFAULT_NO_DATA_INFO = "暂无数据!";

	public static final String DEFAULT_ERROR_INFO = "操作失败!";

	/**
	 * 设置成功
	 * 
	 * @author liukh
	 * @date 2017-3-7 下午10:31:37
	 * @param baseDto
	 */
	public static void setSuccess(BaseDto baseDto) {
		baseDto.setRcode(Integer.valueOf(SUCCESS_RCODE));
		baseDto.setRinfo(DEFAULT_SUCCESS_INFO);
	}

	/**
	 * 设置无数据
	 * 
	 * @author liukh
	 * @date 2017-3-7 下午10:31:45
	 * @param baseDto
	 */
	public static void setNoData(BaseDto baseDto) {
		baseDto.setRcode(Integer.valueOf(NO_DATA_RCODE));
		baseDto.setRinfo(DEFAULT_NO_DATA_INFO);
	}

	/**
	 * 是否成功
	 * 
	 * @author liukh
	 * @date 2017-3-7 下午10:31:55
	 * @param baseDto
	 * @return
	 */
	public static boolean isSuccess(BaseDto baseDto) {
		boolean result = false;
		try {
			if (baseDto != null) {
				String rcode = String.valueOf(baseDto.getRcode());
				if (StringUtils.equals(rcode, SUCCESS_RCODE)) {// 操作成功
					baseDto.setRinfo(DEFAULT_SUCCESS_INFO);
					result = true;
				} else if (StringUtils.equals(rcode, NO_DATA_RCODE)) {// 暂无数据
					baseDto.setRinfo(DEFAULT_NO_DATA_INFO);
				} else {// 其它
					baseDto.setRinfo(DEFAULT_ERROR_INFO);
				}
				log.info(baseDto);
			} else {
				log.error("baseDto is null");
			}
		} catch (Exception e) {
			log.error("baseDto exception");
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 是否成功
	 * 
	 * @author liukh
	 * @date 2017-3-7 下午10:32:06
	 * @param json
	 * @return
	 */
	public static boolean isSuccess(String json) {
		boolean result = false;
		try {
			if (StringUtils.isNotBlank(json)) {
				JSONObject jp = JSON.parseObject(json);
				String rcode = String.valueOf(jp.getInteger("rcode"));
				if (StringUtils.equals(rcode, SUCCESS_RCODE)) {// 操作成功
					result = true;
				}
			} else {
				log.error("json is null");
			}
		} catch (Exception e) {
			log.error("json exception");
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 是否成功
	 * 
	 * @author liukh
	 * @date 2017-3-7 下午10:32:18
	 * @param json
	 * @param clz
	 * @param sucRinfo
	 * @return
	 */
	public static BaseDto isSuccess(String json, Class<? extends BaseDto> clz,
			String sucRinfo) {
		BaseDto baseDto = null;
		try {
			if (StringUtils.isNotBlank(json)) {
				baseDto = JSON.parseObject(json, clz);
				if (baseDto != null) {
					String rcode = String.valueOf(baseDto.getRcode());
					if (StringUtils.equals(rcode, SUCCESS_RCODE)) {// 操作成功
						baseDto.setRinfo(StringUtils.isNotBlank(sucRinfo) ? StringUtils
								.trim(sucRinfo) : DEFAULT_SUCCESS_INFO);
					} else if (StringUtils.equals(rcode, NO_DATA_RCODE)) {// 暂无数据
						baseDto.setRinfo(DEFAULT_NO_DATA_INFO);
					} else {// 其它
						baseDto.setRinfo(DEFAULT_ERROR_INFO);
					}
					log.info(baseDto);
				} else {
					log.error("baseDto is null");
				}
			} else {
				log.error("json is null");
			}
		} catch (Exception e) {
			log.error("baseDto exception");
			e.printStackTrace();
		}
		return baseDto;
	}

	/**
	 * 是否成功
	 * 
	 * @author liukh
	 * @date 2017-3-7 下午10:32:49
	 * @param json
	 * @param clz
	 * @param sucRinfo
	 *            成功提示
	 * @param noDataRinfo
	 *            没有数据提示
	 * @return
	 */
	public static BaseDto isSuccess(String json, Class<? extends BaseDto> clz,
			String sucRinfo, String noDataRinfo) {
		BaseDto baseDto = null;
		try {
			if (StringUtils.isNoneBlank(json)) {
				baseDto = JSON.parseObject(json, clz);
				if (baseDto != null) {
					String rcode = String.valueOf(baseDto.getRcode());
					if (StringUtils.equals(rcode, SUCCESS_RCODE)) {// 操作成功
						baseDto.setRinfo(StringUtils.isNotBlank(sucRinfo) ? StringUtils
								.trim(sucRinfo) : DEFAULT_SUCCESS_INFO);
					} else if (StringUtils.equals(rcode, NO_DATA_RCODE)) {// 暂无数据
						baseDto.setRinfo(StringUtils.isNotBlank(noDataRinfo) ? StringUtils
								.trim(noDataRinfo) : DEFAULT_SUCCESS_INFO);
						baseDto.setRinfo(DEFAULT_NO_DATA_INFO);
					} else {// 其它
						baseDto.setRinfo(DEFAULT_ERROR_INFO);
					}
					log.info(baseDto);
				} else {
					log.error("baseDto is null");
				}
			} else {
				log.error("json is null");
			}
		} catch (Exception e) {
			log.error("baseDto exception");
			e.printStackTrace();
		}
		return baseDto;
	}

	/**
	 * 是否成功
	 * 
	 * @author liukh
	 * @date 2017-3-7 下午10:33:21
	 * @param json
	 * @param sucRinfo
	 *            成功提示
	 * @param noDataRinfo
	 *            没有数据提示
	 * @param errorRinfo
	 *            错误提示
	 * @return
	 */
	public static BaseDto isSuccess(String json, Class<? extends BaseDto> clz,
			String sucRinfo, String noDataRinfo, String errorRinfo) {
		BaseDto baseDto = null;
		try {
			if (StringUtils.isNoneBlank(json)) {
				baseDto = JSON.parseObject(json, clz);
				if (baseDto != null) {
					String rcode = String.valueOf(baseDto.getRcode());
					if (StringUtils.equals(rcode, SUCCESS_RCODE)) {// 操作成功
						baseDto.setRinfo(StringUtils.isNotBlank(sucRinfo) ? StringUtils
								.trim(sucRinfo) : DEFAULT_SUCCESS_INFO);
					} else if (StringUtils.equals(rcode, NO_DATA_RCODE)) {// 暂无数据
						baseDto.setRinfo(StringUtils.isNotBlank(noDataRinfo) ? StringUtils
								.trim(noDataRinfo) : DEFAULT_SUCCESS_INFO);
						baseDto.setRinfo(DEFAULT_NO_DATA_INFO);
					} else {// 其它
						baseDto.setRinfo(StringUtils.isNotBlank(errorRinfo) ? StringUtils
								.trim(errorRinfo) : DEFAULT_ERROR_INFO);
						baseDto.setRinfo(DEFAULT_ERROR_INFO);
					}
					log.info(baseDto);
				} else {
					log.error("baseDto is null");
				}
			} else {
				log.error("json is null");
			}
		} catch (Exception e) {
			log.error("baseDto exception");
			e.printStackTrace();
		}
		return baseDto;
	}

	/**
	 * 列表转换类型
	 * 
	 * @author liukh
	 * @date 2017-3-7 下午10:33:21
	 * @param clz
	 * @param list
	 * @return
	 */
	public static boolean translateList(Class clz, List list) {
		boolean result = false;
		try {
			if (list != null && list.size() > 0) {
				List resultList = new ArrayList();
				Object clzObj = null;
				for (Object obj : list) {
					clzObj = clz.newInstance();
					org.springframework.beans.BeanUtils.copyProperties(obj,
							clzObj);
					resultList.add(clzObj);
				}
				list.clear();
				list.addAll(resultList);
				result = true;
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 对象转换类型
	 * 
	 * @author liukh
	 * @date 2017-3-7 下午10:33:21
	 * @param clz
	 * @param obj
	 * @return
	 */
	public static boolean translateObject(Class clz, Object obj,
			Object returnObj) {
		boolean result = false;
		try {
			if (obj != null) {
				Object clzObj = clz.newInstance();
				BeanUtils.copyProperties(obj, clzObj);
				returnObj = clzObj;
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return result;
	}
}
