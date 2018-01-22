package com.zj.common.utils;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 * @author liukh
 * @date 2016-11-23 下午8:08:33
 */
public class JAXBUtil {
	private static Map<String, JAXBContext> map = new ConcurrentHashMap<String, JAXBContext>();

	/**
	 * 
	 * @author liukh
	 * @date 2016-11-23 下午8:51:04
	 * @param clazz
	 *            xml绑定的对象的类
	 * @param object
	 *            xml绑定的对象
	 * @return解析后字符编码为UTF-8的xml字符串
	 * 
	 * @throws Exception
	 */
	public static <T> String toXml(Class<T> clazz, Object object)
			throws Exception {
		// 组装xml
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		Marshaller marshaller = null;
		JAXBContext context = null;
		String returnString = null;
		try {
			try {
				context = map.get(clazz.toString());
				if (context == null) {
					context = JAXBContext.newInstance(clazz);
					map.put(clazz.toString(), context);
				}
				marshaller = context.createMarshaller();
				// marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
				// Boolean.TRUE);
				// marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
				marshaller.marshal(object, os);
			} catch (Exception e) {
				throw e;
			}

			// 转换为UTF-8的字符串
			if (os != null) {
				returnString = os.toString("UTF-8");
			}
		} finally {
			marshaller = null;
			if (os != null) {
				os.close();
			}
			os = null;
		}
		return returnString;
	}

	/**
	 * 
	 * @author liukh
	 * @date 2016-11-23 下午8:49:55
	 * @param clazz
	 *            xml绑定的对象的类
	 * @param xmlString
	 *            符编码为UTF-8的xml字符串
	 * @return 返回xml绑定的对象
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static <T> T toObject(Class<T> clazz, String xmlString)
			throws Exception {
		ByteArrayInputStream stream = new ByteArrayInputStream(
				xmlString.getBytes("UTF-8"));
		Unmarshaller unmarshaller = null;
		JAXBContext context = null;
		Object returnObject = null;
		try {
			context = map.get(clazz.toString());
			if (context == null) {
				context = JAXBContext.newInstance(clazz);
				map.put(clazz.toString(), context);
			}
			unmarshaller = context.createUnmarshaller();
			returnObject = unmarshaller.unmarshal(stream);
		} catch (Exception e) {
			throw e;
		} finally {
			unmarshaller = null;
			if (stream != null) {
				stream.close();
			}
			stream = null;
		}
		return (T) returnObject;
	}


}
