package com.zj.base.common.utils.sms;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SmsTemplateUtils {
	
	public static Properties getSmsTemplateProperties(){
		Properties properties = null;
		try {
			InputStream in = SmsTemplateUtils.class.getClassLoader().getResourceAsStream("smsTemplate.properties");
		    properties = new Properties();
			properties.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return properties;
	}

	/**
	 * 解析手机验证码模板信息
	 * @author tanjianhua
	 * @date 2016-10-31 下午3:11:18
	 * @return
	 */
	public static String getValidCodeSmsTemplateInfo() {
		String validCodeTemplate = null;
		try {
			Properties properties = getSmsTemplateProperties();
		if(properties != null){
			validCodeTemplate = properties.getProperty("validCodeTemplate").trim();
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return validCodeTemplate;
	}
	
	/**
	 * 解析线下协商订单通知模板信息
	 * 订单通知：您的订单已经成功确认。由于您的订单类型为线下协商，请及时与对方（{1}）进行协商以确保订单的正常进行。
	 * 订单通知：您的订单已经成功确认。由于您的订单类型为线下协商，请及时与对方（XX公司：188XXXX1234）进行协商以确保订单的正常进行。
	 * @author liukh
	 * @date 2016-12-22 下午1:46:10
	 * @return
	 */
	public static String getOrderMessageOfflineTemplateInfo(){
		String orderMessageTemplate = null;
		try {
			Properties properties = getSmsTemplateProperties();
		if(properties != null ){
			orderMessageTemplate = properties.getProperty("orderMessageOfflineTemplate").trim();
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orderMessageTemplate;
	}
	/**
	 * 解析租赁方线上支付订单通知模板信息
	 * 订单通知：您的订单已经成功确认。您的订单类型为线上交易，已经锁定对方（XX公司：188XXXX1234）30,000元预付款，请及时与对方公司协商后将设备出库。
	 * 订单通知：您的订单已经成功确认。您的订单类型为线上交易，已经锁定对方（{1}）{2}元预付款，请及时与对方公司协商后将设备出库。
	 * @author liukh
	 * @date 2016-12-22 下午1:46:15
	 * @return
	 */
	public static String getOrderMessageOnlineLeasingSideTemplateInfo(){
		String orderMessageTemplate = null;
		try {
			Properties properties = getSmsTemplateProperties();
			if(properties != null ){
				orderMessageTemplate = properties.getProperty("orderMessageOnlineLeasingSideTemplate").trim();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orderMessageTemplate;
	}
	/**
	 * 解析承租方线上支付订单通知模板信息
	 *  订单通知：您的订单已经成功确认。您的订单类型为线上交易，请及时与对方公司（XX公司：188XXXX1234）协商。
	 *  订单通知：您的订单已经成功确认。您的订单类型为线上交易，请及时与对方公司（{1}）协商。
	 * @author liukh
	 * @date 2016-12-22 下午1:46:20
	 * @return
	 */
	public static String getOrderMessageOnlineLesseeSideTemplateInfo(){
		String orderMessageTemplate = null;
		try {
			Properties properties = getSmsTemplateProperties();
			if(properties != null ){
				orderMessageTemplate = properties.getProperty("orderMessageOnlineLesseeSideTemplate").trim();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orderMessageTemplate;
	}
	/**
	 * 抛单形成订单时，提醒承租方
	 * 您发布的叫机已经被{1}抢单成功，已经锁定对方（{2}）{3}元的保证金，请及时与对方公司（{4}）联系。
	 * @author liukh
	 * @date 2016-12-22 下午4:02:46
	 * @return
	 */
	public static String getInqueryRentThrow4LesseeSideTemplateInfo(){
		String orderMessageTemplate = null;
		try {
			Properties properties = getSmsTemplateProperties();
			if(properties != null ){
				orderMessageTemplate = properties.getProperty("inqueryRentThrow4LesseeSideTemplate").trim();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orderMessageTemplate;
	}
	
	/**
	 * 抛单形成订单时，提醒租赁方
	 * 您已经抢单成功。该订单类型为线上交易，已经锁定对方（{1}）{2}元预付款，请及时与对方公司协商后将设备出库。
	 * @author liukh
	 * @date 2016-12-22 下午4:04:01
	 * @return
	 */
	public static String getInqueryRentThrow4ResponseLeasingSideTemplateInfo(){
		String orderMessageTemplate = null;
		try {
			Properties properties = getSmsTemplateProperties();
			if(properties != null ){
				orderMessageTemplate = properties.getProperty("inqueryRentThrow4ResponseLeasingSideTemplate").trim();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orderMessageTemplate;
	}
	
	/**
	 * 公司审核：提交审核后：
	 * 您的申请已提交后台审核，若审核通过，将在1个工作日内以短信告知，请注意查收。如有紧急情况，请电话联系我们的客服人员400-851-0158。
	 * @author liukh
	 * @date 2016-12-22 上午11:14:24
	 * @return
	 */
	public static String getCompanyNeedCheckTemplateInfo() {
		String messageTemplate = null;
		try {
			Properties properties = getSmsTemplateProperties();
			if(properties != null){
				messageTemplate = properties.getProperty("companyNeedCheckTemplate").trim();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return messageTemplate;
	}
	/**
	 * 公司审核：审核通过后：
	 * 您的公司信息（XX公司）已经通过审核，如有其他疑问请电话联系我们的客服人员400-851-0158。
	 * 您的公司信息（{1}）已经通过审核，如有其他疑问请电话联系我们的客服人员400-851-0158。
	 * @author liukh
	 * @date 2016-12-22 上午11:15:11
	 * @return
	 */
	public static String getCompanyCheckPassedTemplateInfo() {
		String messageTemplate = null;
		try {
			Properties properties = getSmsTemplateProperties();
			if(properties != null){
				messageTemplate = properties.getProperty("companyCheckPassedTemplate").trim();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return messageTemplate;
	}
	/**
	 * 公司审核：审核通过后：
	 * 您的公司信息（XX公司）已经未通过审核，如有其他疑问请电话联系我们的客服人员400-851-0158。
	 * 您的公司信息（{1}）已经未通过审核，如有其他疑问请电话联系我们的客服人员400-851-0158。
	 * @author liukh
	 * @date 2016-12-22 上午11:15:11
	 * @return
	 */
	public static String getCompanyCheckNOPassedTemplateInfo() {
		String messageTemplate = null;
		try {
			Properties properties = getSmsTemplateProperties();
			if(properties != null){
				messageTemplate = properties.getProperty("companyCheckNOPassedTemplate").trim();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return messageTemplate;
	}

}
