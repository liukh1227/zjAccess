package com.zj.access.service;

import java.util.Map;

import com.zj.entity.sm.form.AdvertisementQueryForm;
import com.zj.entity.sm.form.FeedbackQueryForm;
import com.zj.entity.sm.form.LogQueryForm;
import com.zj.entity.sm.form.MessageQueryForm;
import com.zj.entity.sm.form.SmsDistributionLogQueryForm;


public interface BaseSMService {

	/**
	 * 
	 * @author five
	 * @date 2017年2月22日17:28:58
	 */
	public String addMessage(String data);
	
	/**
	 * 
	 * @author five
	 * @date 2017年2月22日17:28:58
	 */
	public String addLog(String data);
	
	/**
	 * 
	 * @author five
	 * @date 2017年2月22日17:28:58
	 */
	public String addFeedback(String data);
	
	/**
	 * 
	 * @author five
	 * @date 2017年2月22日17:28:58
	 */
	public String addSmsDistributionLog(String data);
	
	/**
	 * 
	 * @author five
	 * @date 2017年2月22日17:28:58
	 */
	public String addAdvertisement(String data);
	
	/**
	 * @author five
	 * @date 2017年2月23日10:43:02
	 */
	public String getFeedback(String feedbackId);

	/**
	 * @author five
	 * @date 2017年2月23日10:43:02
	 */
	public String getLog(String logId);

	/**
	 * @author five
	 * @date 2017年2月23日10:43:02
	 */
	public String getAdvertisement(String advertisementId);

	/**
	 * @author five
	 * @date 2017年2月23日10:43:02
	 */
	public String getSmsDistributionLog(String smsDistributionLogId);

	/**
	 * @author five
	 * @date 2017年2月23日10:43:02
	 */
	public String getMessage(String messageId);
	//-------------------
	/**
	 * 
	 * @author five
	 * @date 2017年2月23日10:43:02
	 */
	public String updateSmsDistributionLog(String smsDistributionLogId, String data);
	

	/**
	 * 
	 * @author five
	 * @date 2017年2月23日10:43:02
	 */
	public String updateAdvertisement(String advertisementId, String data);
	/**
	 * 
	 * @author five
	 * @date 2017年2月23日10:43:02
	 */
	public String updateLog(String logId, String data);
	/**
	 * 
	 * @author five
	 * @date 2017年2月23日10:43:02
	 */
	public String updateFeedback(String feedbackId, String data);

	/**
	 * 
	 * @author five
	 * @date 2017年2月23日10:43:02
	 */
	public String updateMessage(String messageId, String data);
	//-------------------
	/**
	 * 
	 * @author five
	 * @date 2017年2月23日10:44:10
	 */
	public String getFeedbackList(FeedbackQueryForm form) ;
	/**
	 * 
	 * @author five
	 * @date 2017年2月23日10:44:10
	 */
	public String getLogList(LogQueryForm form) ;
	/**
	 * 
	 * @author five
	 * @date 2017年2月23日10:44:10
	 */
	public String getAdvertisementList(AdvertisementQueryForm form) ;
	/**
	 * 
	 * @author five
	 * @date 2017年2月23日10:44:10
	 */
	public String getSmsDistributionLogList(SmsDistributionLogQueryForm form) ;
	/**
	 * 
	 * @author five
	 * @date 2017年2月23日10:44:10
	 */
	public String getMessageList(MessageQueryForm form) ;
	/**
	 * 获取包含图片的消息列表
	 * @author liukh
	 * @date 2017-3-25 下午3:03:04
	 * @param form
	 * @return
	 */
	public String getMessageContainPictureList(MessageQueryForm form);
	/**
	 * 获取版本更新信息
	 * @author liukh
	 * @date 2017-3-25 下午3:03:17
	 * @param params
	 * @return
	 */
	public String getLastAPPVersion(Map<String,Object> params);
	/**
	 * 获取未读的内部消息统计
	 * @author liukh
	 * @date 2017-3-25 下午3:04:14
	 * @param form
	 * @return
	 */
	public String getUnReadMessageAmount(MessageQueryForm form);
	/**
	 * 机主端广告位
	 * @author liukh
	 * @date 2017-3-25 下午3:05:59
	 * @param form
	 * @return
	 */
	public String getLeasingSideAdvertisingList(AdvertisementQueryForm form);
	/**
	 * 承租方广告位
	 * @author liukh
	 * @date 2017-3-25 下午3:05:48
	 * @param form
	 * @return
	 */
	public String getLesseeSideAdvertisingList(AdvertisementQueryForm form);
	
	// ------------------------------------------------------订单短信通知-----------------------------------------------
		/**
		 * 订单为线下协商通知短信
		 * 
		 * @author liukh
		 * @date 2016-12-22 下午2:15:13
		 * @param phoneNumber
		 * @param smsConent
		 */
		public void sendOrderMessageOfflineSms(String phoneNumber, String smsConent);

		/**
		 * 租赁方线上支付订单通知短信
		 * 
		 * @author liukh
		 * @date 2016-12-22 下午2:15:19
		 * @param phoneNumber
		 * @param smsConent
		 */
		public void sendOrderMessageOnlineLeasingSideSms(String phoneNumber,String smsConent,String smslockContent);

		/**
		 * 承租方线上支付订单通知短信
		 * 
		 * @author liukh
		 * @date 2016-12-22 下午2:15:23
		 * @param phoneNumber
		 * @param smsConent
		 */

		public void sendOrderMessageOnlineLesseeSideSms(String phoneNumber,String smsConent);
		/**
		 * 抢单成功后，承租方通知短信
		 * @author liukh
		 * @date 2016-12-22 下午4:31:23
		 * @param phoneNumber
		 * @param smsConent
		 */
		public void sendInqueryRentThrow4LesseeSideSms(String phoneNumber,String smsConent1,String smsConent2,String smsConent3,String smsConent4);
		/**
		 * 抢单成功后，租赁方通知短信
		 * @author liukh
		 * @date 2016-12-22 下午4:31:28
		 * @param phoneNumber
		 * @param smsConent
		 */
		public void sendInqueryRentThrow4ResponseLeasingSideSms(String phoneNumber,String smsConent1,String smsConent2);
		/**
		 * 公司信息通过通知短信
		 * @author liukh
		 * @date 2016-12-22 下午9:07:34
		 * @param phoneNumber
		 * @param smsConent1
		 * @param smsConent2
		 */
		public void sendCompanyCheckPassedInfoSms(String phoneNumber,String smsConent);
		/**
		 * 公司信息未通过通知短信
		 * @author liukh
		 * @date 2016-12-22 下午9:07:55
		 * @param phoneNumber
		 * @param smsConent1
		 * @param smsConent2
		 */
		public void sendCompanyCheckNOPassedInfoSms(String phoneNumber,String smsConent);
		
		/**
		 * 公司信息需要审核通知短信
		 * @author liukh
		 * @date 2016-12-23 上午9:22:18
		 * @param phoneNumber
		 */
		
		public void sendCompanyNeedCheckInfoSms(String phoneNumber);
	
 
}
