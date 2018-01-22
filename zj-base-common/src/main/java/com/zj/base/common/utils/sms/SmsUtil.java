package com.zj.base.common.utils.sms;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.zj.base.common.utils.HttpClientPost;

/**
 * 短信帮助类
 * 
 * @author:tjhua
 * @date:2016-3-22 上午11:26:51
 */
public class SmsUtil {
	// 找机网短信
	// https://tiancaibei.com/smsgate/smsGateController/sendSingleSms?phoneNumber=15920973190&content=%E4%BD%86%E6%84%BF%E4%BA%BA%E9%95%BF%E4%B9%85%EF%BC%8C%E5%8D%83%E9%87%8C%E5%85%B1%E5%A9%B5%E5%A8%9F&smsDownInterfaceType=zj
	// https://api.chebaotec.com/smsgate/smsGateController/sendSingleSms?phoneNumber=15920973190&content=%E4%BD%86%E6%84%BF%E4%BA%BA%E9%95%BF%E4%B9%85%EF%BC%8C%E5%8D%83%E9%87%8C%E5%85%B1%E5%A9%B5%E5%A8%9F&smsDownInterfaceType=zj
	/**
	 * appaccess sms url
	 */
	public static String APPACCESS_SMS_URL = "http://api.chebaotec.com/smsgate";
	/**
	 * /smsGateController/sendSingleSms POST:发送短信验证码 GET: 发送短信验证码
	 */
	public final static String SEND_SMS = "/smsGateController/sendSingleSms";

	/**
	 * 发送短信验证码
	 * 
	 * @author tanjianhua
	 * @date 2016-10-31 下午3:13:41
	 * @param phoneNumber
	 * @param validCode
	 * @return
	 */
	public static String sendSms(String phoneNumber, String smsCode) {
		String jsonStr = "";
		try {
			Map<String, String> params = new HashMap<String, String>();
			String smsTemplateInfo = SmsTemplateUtils
					.getValidCodeSmsTemplateInfo();
			if (StringUtils.isNotBlank(smsTemplateInfo)) {
				smsTemplateInfo = smsTemplateInfo.replace("{1}", smsCode);
			}

			params.put("phoneNumber", phoneNumber);
			params.put("content", smsTemplateInfo);
			params.put("smsDownInterfaceType", "zj");

			String url = APPACCESS_SMS_URL + SEND_SMS;
			jsonStr = HttpClientPost.postStringEntity(url, params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonStr;
	}

	/**
	 * 订单为线下协商通知短信
	 * 
	 * @author liukh
	 * @date 2016-12-22 下午1:59:39
	 * @param phoneNumber
	 * @param smsConent
	 * @return
	 */
	public static String sendOrderMessageOfflineSms(String phoneNumber,
			String smsConent) {
		String jsonStr = "";
		try {
			Map<String, String> params = new HashMap<String, String>();
			String smsTemplateInfo = SmsTemplateUtils
					.getOrderMessageOfflineTemplateInfo();
			if (StringUtils.isNotBlank(smsTemplateInfo)) {
				smsTemplateInfo = smsTemplateInfo.replace("{1}", smsConent);
			}

			params.put("phoneNumber", phoneNumber);
			params.put("content", smsTemplateInfo);
			params.put("smsDownInterfaceType", "zj");

			String url = APPACCESS_SMS_URL + SEND_SMS;
			jsonStr = HttpClientPost.postStringEntity(url, params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonStr;
	}

	/**
	 * 租赁方线上支付订单通知短信
	 * 
	 * @author liukh
	 * @date 2016-12-22 下午2:01:04
	 * @param phoneNumber
	 * @param smsConent
	 * @return
	 */
	public static String sendOrderMessageOnlineLeasingSideSms(
			String phoneNumber, String smsConent, String smslockContent) {
		String jsonStr = "";
		try {
			Map<String, String> params = new HashMap<String, String>();
			String smsTemplateInfo = SmsTemplateUtils
					.getOrderMessageOnlineLeasingSideTemplateInfo();
			if (StringUtils.isNotBlank(smsTemplateInfo)) {
				smsTemplateInfo = smsTemplateInfo.replace("{1}", smsConent);
				smsTemplateInfo = smsTemplateInfo
						.replace("{2}", smslockContent);
			}

			params.put("phoneNumber", phoneNumber);
			params.put("content", smsTemplateInfo);
			params.put("smsDownInterfaceType", "zj");

			String url = APPACCESS_SMS_URL + SEND_SMS;
			 jsonStr = HttpClientPost.postStringEntity(url, params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonStr;
	}

	/**
	 * 承租方线上支付订单通知短信
	 * 
	 * @author liukh
	 * @date 2016-12-22 下午2:02:36
	 * @param phoneNumber
	 * @param smsConent
	 * @return
	 */
	public static String sendOrderMessageOnlineLesseeSideSms(String phoneNumber,
			String smsConent) {
		String jsonStr = "";
		try {
			Map<String, String> params = new HashMap<String, String>();
			String smsTemplateInfo = SmsTemplateUtils
					.getOrderMessageOnlineLesseeSideTemplateInfo();
			if (StringUtils.isNotBlank(smsTemplateInfo)) {
				smsTemplateInfo = smsTemplateInfo.replace("{1}", smsConent);
			}

			params.put("phoneNumber", phoneNumber);
			params.put("content", smsTemplateInfo);
			params.put("smsDownInterfaceType", "zj");

			String url = APPACCESS_SMS_URL + SEND_SMS;
			 jsonStr = HttpClientPost.postStringEntity(url, params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonStr;
	}

	/**
	 * 抛单抢单成功后，提示承租方
	 * 
	 * @author liukh
	 * @date 2016-12-22 下午4:09:39
	 * @param phoneNumber
	 * @param smsConent1
	 * @param smsConent2
	 * @param smsConent3
	 * @param smsConent4
	 * @return
	 */

	public static String sendInqueryRentThrow4LesseeSideSms(String phoneNumber,
			String smsConent1, String smsConent2, String smsConent3,
			String smsConent4) {
		String jsonStr = "";
		try {
			Map<String, String> params = new HashMap<String, String>();
			String smsTemplateInfo = SmsTemplateUtils
					.getInqueryRentThrow4LesseeSideTemplateInfo();
			if (StringUtils.isNotBlank(smsTemplateInfo)) {
				smsTemplateInfo = smsTemplateInfo.replace("{1}", smsConent1);
				smsTemplateInfo = smsTemplateInfo.replace("{2}", smsConent2);
				smsTemplateInfo = smsTemplateInfo.replace("{3}", smsConent3);
				smsTemplateInfo = smsTemplateInfo.replace("{4}", smsConent4);
			}

			params.put("phoneNumber", phoneNumber);
			params.put("content", smsTemplateInfo);
			params.put("smsDownInterfaceType", "zj");

			String url = APPACCESS_SMS_URL + SEND_SMS;
			 jsonStr = HttpClientPost.postStringEntity(url, params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonStr;
	}

	/**
	 * 抛单抢单成功后，提示租赁方
	 * 
	 * @author liukh
	 * @date 2016-12-22 下午4:09:46
	 * @param phoneNumber
	 * @param smsConent1
	 * @param smsConent2
	 * @return
	 */
	public static String sendInqueryRentThrow4ResponseLeasingSideSms(
			String phoneNumber, String smsConent1, String smsConent2) {
		String jsonStr = "";
		try {
			Map<String, String> params = new HashMap<String, String>();
			String smsTemplateInfo = SmsTemplateUtils
					.getInqueryRentThrow4ResponseLeasingSideTemplateInfo();
			if (StringUtils.isNotBlank(smsTemplateInfo)) {
				smsTemplateInfo = smsTemplateInfo.replace("{1}", smsConent1);
				smsTemplateInfo = smsTemplateInfo.replace("{2}", smsConent2);

			}

			params.put("phoneNumber", phoneNumber);
			params.put("content", smsTemplateInfo);
			params.put("smsDownInterfaceType", "zj");

			String url = APPACCESS_SMS_URL + SEND_SMS;
			 jsonStr = HttpClientPost.postStringEntity(url, params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonStr;
	}
	/**
	 * 公司等待审核通知信息
	 * @author liukh
	 * @date 2016-12-22 下午8:48:52
	 * @param phoneNumber
	 * @return
	 */
	public static String sendCompanyNeedCheckTemplateInfoSms(String phoneNumber){
		String jsonStr = "";
		try {
			Map<String, String> params = new HashMap<String, String>();
			String smsTemplateInfo = SmsTemplateUtils.getCompanyNeedCheckTemplateInfo();
			params.put("phoneNumber", phoneNumber);
			params.put("content", smsTemplateInfo);
			params.put("smsDownInterfaceType", "zj");

			String url = APPACCESS_SMS_URL + SEND_SMS;
			 jsonStr = HttpClientPost.postStringEntity(url, params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonStr;
	}
	/**
	 * 公司审核通过通知信息
	 * @author liukh
	 * @date 2016-12-22 下午8:52:15
	 * @param phoneNumber
	 * @param smsContent
	 * @return
	 */
	public static String sendCompanyCheckPassedInfoSms(String phoneNumber,String smsContent){
		String jsonStr = "";
		try {
			Map<String, String> params = new HashMap<String, String>();
			String smsTemplateInfo = SmsTemplateUtils.getCompanyCheckPassedTemplateInfo();
			if (StringUtils.isNotBlank(smsTemplateInfo)) {
				smsTemplateInfo = smsTemplateInfo.replace("{1}", smsContent);

			}
			
			params.put("phoneNumber", phoneNumber);
			params.put("content", smsTemplateInfo);
			params.put("smsDownInterfaceType", "zj");
			
			String url = APPACCESS_SMS_URL + SEND_SMS;
			jsonStr = HttpClientPost.postStringEntity(url, params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonStr;
	}
	/**
	 * 公司审核未通过通知信息
	 * @author liukh
	 * @date 2016-12-22 下午8:52:15
	 * @param phoneNumber
	 * @param smsContent
	 * @return
	 */
	public static String sendCompanyCheckNOPassedInfoSms(String phoneNumber,String smsContent){
		String jsonStr = "";
		try {
			Map<String, String> params = new HashMap<String, String>();
			String smsTemplateInfo = SmsTemplateUtils.getCompanyCheckNOPassedTemplateInfo();
			if (StringUtils.isNotBlank(smsTemplateInfo)) {
				smsTemplateInfo = smsTemplateInfo.replace("{1}", smsContent);
				
			}
			
			params.put("phoneNumber", phoneNumber);
			params.put("content", smsTemplateInfo);
			params.put("smsDownInterfaceType", "zj");
			
			String url = APPACCESS_SMS_URL + SEND_SMS;
			jsonStr = HttpClientPost.postStringEntity(url, params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonStr;
	}

}
