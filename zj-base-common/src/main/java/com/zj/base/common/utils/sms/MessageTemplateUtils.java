package com.zj.base.common.utils.sms;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MessageTemplateUtils {
	
	public static Properties getMessageTemplateProperties(){
		Properties properties = null;
		try {
			InputStream in = MessageTemplateUtils.class.getClassLoader().getResourceAsStream("messageTemplate.properties");
		    properties = new Properties();
			properties.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return properties;
	}
	
	/**
	 * 询价商家回复信息
	 * XX公司已经回复您的询价，请及时查看；
	 * {1}公司已经回复您的询价，请及时查看。
	 * @author liukh
	 * @date 2016-12-22 上午11:09:15
	 * @return
	 */
	public static String getInqueryOrderQuotoMessageTemplateInfo() {
		String messageTemplate = null;
		try {
			Properties properties = getMessageTemplateProperties();
		if(properties != null){
			messageTemplate = properties.getProperty("inqueryOrderQuotoMessageTemplate").trim();
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return messageTemplate;
	}
	
/**
 * 设备上传：通过后： 提示信息
 * 您的自编号为“挖掘机007”的设备已经通过审核，承租方能通过“找机”找到您的设备，您也可以将此设备用于“找活”和“抢单”。
 * 您的自编号为“{1}”的设备已经通过审核，承租方能通过“找机”找到您的设备，你也可以将此设备用于“找活”和“抢单”。
 * @author liukh
 * @date 2016-12-22 上午11:03:34
 * @return
 */

	public static String getDevicePassedMessageTemplateInfo() {
		String messageTemplate = null;
		try {
			Properties properties = getMessageTemplateProperties();
		if(properties != null){
			messageTemplate = properties.getProperty("devicePassedMessageTemplate").trim();
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return messageTemplate;
	}
	/**
	 * 设备上传：未通过： 提示信息
	 * 您的自编号为“挖掘机007”的设备未能通过审核，请在设备列表的“未通过”中查看审核原因，请修改完成后重新上传。请电话联系我们的客服人员400-851-0158。
	 * 您的自编号为“{1}”的设备未能通过审核，请在设备列表的“未通过”中查看审核原因，请修改完成后重新上传。请电话联系我们的客服人员400-851-0158。
	 * @author liukh
	 * @date 2016-12-22 上午11:13:33
	 * @return
	 */
	public static String getDeviceUnPassedMessageTemplateInfo() {
		String messageTemplate = null;
		try {
			Properties properties = getMessageTemplateProperties();
			if(properties != null){
				messageTemplate = properties.getProperty("deviceUnPassedMessageTemplate").trim();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return messageTemplate;
	}
	
	/**
	 * 支付成功
	 * 您对结算单号为2016080809的订单支付了30,000元。如有疑问，请电话联系我们的客服人员400-851-0158。
	 * 您的结算单号为{1}的订单支付了{2}元。如有疑问，请电话联系我们的客服人员400-851-0158。
	 * @author liukh
	 * @date 2016-12-22 上午11:18:31
	 * @return
	 */
	public static String getOrderStatementPaySuccessMessageTemplateInfo() {
		String messageTemplate = null;
		try {
			Properties properties = getMessageTemplateProperties();
			if(properties != null){
				messageTemplate = properties.getProperty("orderStatementPaySuccessMessageTemplate").trim();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return messageTemplate;
	}
	
	
}
