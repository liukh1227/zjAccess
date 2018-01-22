package com.zj.access.common;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;

public class ToolUtils {

	/**
	 * 获取参数
	 * 
	 * @author liukh
	 * @date 2017-2-24 下午3:00:47
	 * @param request
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	public static Map<String, Object> getParams(HttpServletRequest request) {
		Enumeration paramNames = request.getParameterNames();
		Map<String, Object> params = new HashMap<String, Object>();
		while (paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			String paramValue = request.getParameter(paramName);
			params.put(paramName, paramValue);
		}
		return params;
	}

	/**
	 * 将map转换为bean
	 * 
	 * @author liukh
	 * @date 2017-2-24 下午3:07:53
	 * @param map
	 * @param obj
	 */
	public static void transMap2Bean(Map<String, Object> map, Object obj) {
		if (map == null || obj == null) {
			return;
		}
		try {
			BeanUtils.populate(obj, map);
		} catch (Exception e) {
			System.out.println("transMap2Bean2 Error " + e);
		}
	}

	/**
	 * request 参数转成bean
	 * 
	 * @author liukh
	 * @date 2017-2-24 下午3:09:25
	 * @param request
	 * @param obj
	 */
	public static void request2JavaBean(HttpServletRequest request, Object obj) {
		Map<String, Object> map = getParams(request);
		transMap2Bean(map, obj);

	}

}
