package com.zj.access.control.sm;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.zj.access.common.ToolUtils;
import com.zj.access.control.base.CommonController;
import com.zj.access.service.BaseSMService;
import com.zj.base.entity.base.dto.BaseDto;
import com.zj.entity.sm.form.AdvertisementQueryForm;
import com.zj.entity.sm.form.FeedbackQueryForm;
import com.zj.entity.sm.form.LogQueryForm;
import com.zj.entity.sm.form.MessageQueryForm;
import com.zj.entity.sm.form.SmsDistributionLogQueryForm;

@Controller
@Scope("prototype")
@RequestMapping(value = "/sm")
public class BaseSMController extends CommonController {

	private static final Logger log = Logger.getLogger(BaseSMController.class);

	@Resource
	private BaseSMService baseSMService;

	/**
	 * 新增内部消息
	 * 
	 * @author liukh
	 * @date 2017-3-8 下午3:19:08
	 * @param request
	 * @param response
	 * @param data
	 * @return
	 */
	@RequestMapping(value = "/message", method = { RequestMethod.POST })
	@ResponseBody
	public String addMessage(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "data", required = true) String data) {

		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseSMService.addMessage(data);
		} catch (Exception e) {
			log.error("addMessage ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;
	}

	/**
	 * 获取内部消息
	 * 
	 * @author liukh
	 * @date 2017-3-8 下午3:20:23
	 * @param request
	 * @param response
	 * @param messageId
	 * @return
	 */
	@RequestMapping(value = "/message/{messageId}", method = { RequestMethod.GET })
	@ResponseBody
	public String getMessage(HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable(value = "messageId") String messageId) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseSMService.getMessage(messageId);
		} catch (Exception e) {
			log.error("getMessage ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;

	}

	/**
	 * 更新内部消息
	 * 
	 * @author liukh
	 * @date 2017-3-8 下午3:28:45
	 * @param request
	 * @param response
	 * @param messageId
	 * @param data
	 * @return
	 */
	@RequestMapping(value = "/message/{messageId}", method = { RequestMethod.POST })
	@ResponseBody
	public String updateMessage(HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable(value = "messageId") String messageId,
			@RequestParam(value = "data", required = true) String data) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseSMService.updateMessage(messageId, data);
		} catch (Exception e) {
			log.error("updateMessage ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;
	}

	/**
	 * 获取内部消息列表
	 * 
	 * @author liukh
	 * @date 2017-3-8 下午3:29:34
	 * @param request
	 * @param response
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "/message/list", method = { RequestMethod.GET })
	@ResponseBody
	public String getMessageList(HttpServletRequest request,
			HttpServletResponse response, MessageQueryForm form) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			//jsonStr = baseSMService.getMessageList(form);
			jsonStr = baseSMService.getMessageContainPictureList(form);
		} catch (Exception e) {
			log.error("getMessageList ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;
	}

	/**
	 * 新增短信下发历史记录
	 * 
	 * @author liukh
	 * @date 2017-3-8 下午3:29:56
	 * @param request
	 * @param response
	 * @param data
	 * @return
	 */
	@RequestMapping(value = "/smsDistributionLog", method = { RequestMethod.POST })
	@ResponseBody
	public String addSmsDistributionLog(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "data", required = true) String data) {

		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseSMService.addSmsDistributionLog(data);
		} catch (Exception e) {
			log.error("addSmsDistributionLog ---- 异常,message = "
					+ e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;
	}

	/**
	 * 获取短信下发历史记录
	 * 
	 * @author liukh
	 * @date 2017-3-8 下午3:30:42
	 * @param request
	 * @param response
	 * @param smsDistributionLogId
	 * @return
	 */
	@RequestMapping(value = "/smsDistributionLog/{smsDistributionLogId}", method = { RequestMethod.GET })
	@ResponseBody
	public String getSmsDistributionLog(
			HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable(value = "smsDistributionLogId") String smsDistributionLogId) {

		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseSMService.getSmsDistributionLog(smsDistributionLogId);
		} catch (Exception e) {
			log.error("getSmsDistributionLog ---- 异常,message = "
					+ e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;
	}

	/**
	 * 更新短信下发历史记录
	 * 
	 * @author liukh
	 * @date 2017-3-8 下午3:31:47
	 * @param request
	 * @param response
	 * @param smsDistributionLogId
	 * @param data
	 * @return
	 */
	@RequestMapping(value = "/smsDistributionLog/{smsDistributionLogId}", method = { RequestMethod.POST })
	@ResponseBody
	public String updateSmsDistributionLog(
			HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable(value = "smsDistributionLogId") String smsDistributionLogId,
			@RequestParam(value = "data", required = true) String data) {

		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseSMService.updateSmsDistributionLog(
					smsDistributionLogId, data);
		} catch (Exception e) {
			log.error("updateSmsDistributionLog ---- 异常,message = "
					+ e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;

	}

	/**
	 * 获取短信下发历史记录列表
	 * 
	 * @author liukh
	 * @date 2017-3-8 下午3:32:23
	 * @param request
	 * @param response
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "/smsDistributionLog/list", method = { RequestMethod.GET })
	@ResponseBody
	public String getSmsDistributionLogList(HttpServletRequest request,
			HttpServletResponse response, SmsDistributionLogQueryForm form) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseSMService.getSmsDistributionLogList(form);
		} catch (Exception e) {
			log.error("getSmsDistributionLogList ---- 异常,message = "
					+ e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;
	}

	/**
	 * 新增日志管理信息
	 * 
	 * @author liukh
	 * @date 2017-3-8 下午3:32:53
	 * @param request
	 * @param response
	 * @param data
	 * @return
	 */
	@RequestMapping(value = "/log", method = { RequestMethod.POST })
	@ResponseBody
	public String addLog(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "data", required = true) String data) {

		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseSMService.addLog(data);
		} catch (Exception e) {
			log.error("addLog ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;
	}

	/**
	 * 获取日志管理信息
	 * 
	 * @author liukh
	 * @date 2017-3-8 下午3:33:42
	 * @param request
	 * @param response
	 * @param logId
	 * @return
	 */
	@RequestMapping(value = "/log/{logId}", method = { RequestMethod.GET })
	@ResponseBody
	public String getLog(HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable(value = "logId") String logId) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseSMService.getLog(logId);
		} catch (Exception e) {
			log.error("getLog ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;
	}

	/**
	 * 更新日志管理信息
	 * 
	 * @author liukh
	 * @date 2017-3-8 下午3:34:33
	 * @param request
	 * @param response
	 * @param logId
	 * @param data
	 * @return
	 */
	@RequestMapping(value = "/log/{logId}", method = { RequestMethod.POST })
	@ResponseBody
	public String updateLog(HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable(value = "logId") String logId,
			@RequestParam(value = "data", required = true) String data) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseSMService.updateLog(logId, data);
		} catch (Exception e) {
			log.error("updateLog ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;
	}

	/**
	 * 获取日志管理信息列表
	 * 
	 * @author liukh
	 * @date 2017-3-8 下午3:35:20
	 * @param request
	 * @param response
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "/log/list", method = { RequestMethod.GET })
	@ResponseBody
	public String getLogList(HttpServletRequest request,
			HttpServletResponse response, LogQueryForm form) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseSMService.getLogList(form);
		} catch (Exception e) {
			log.error("getLogList ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;

	}

	/**
	 * 新增意见反馈信息
	 * 
	 * @author liukh
	 * @date 2017-3-8 下午3:36:33
	 * @param request
	 * @param response
	 * @param data
	 * @return
	 */
	@RequestMapping(value = "/feedback", method = { RequestMethod.POST })
	@ResponseBody
	public String addFeedback(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "data", required = true) String data) {

		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseSMService.addFeedback(data);
		} catch (Exception e) {
			log.error("addFeedback ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;
	}

	/**
	 * 获取意见反馈信息
	 * 
	 * @author liukh
	 * @date 2017-3-8 下午3:37:23
	 * @param request
	 * @param response
	 * @param feedbackId
	 * @return
	 */
	@RequestMapping(value = "/feedback/{feedbackId}", method = { RequestMethod.GET })
	@ResponseBody
	public String getFeedback(HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable(value = "feedbackId") String feedbackId) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseSMService.getFeedback(feedbackId);
		} catch (Exception e) {
			log.error("getFeedback ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;
	}

	/**
	 * 更新意见反馈信息
	 * 
	 * @author liukh
	 * @date 2017-3-8 下午3:38:40
	 * @param request
	 * @param response
	 * @param feedbackId
	 * @param data
	 * @return
	 */
	@RequestMapping(value = "/feedback/{feedbackId}", method = { RequestMethod.POST })
	@ResponseBody
	public String updateFeedback(HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable(value = "feedbackId") String feedbackId,
			@RequestParam(value = "data", required = true) String data) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseSMService.updateFeedback(feedbackId, data);
		} catch (Exception e) {
			log.error("updateFeedback ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;
	}

	/**
	 * 获取意见反馈信息列表
	 * 
	 * @author liukh
	 * @date 2017-3-8 下午3:39:34
	 * @param request
	 * @param response
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "/feedback/list", method = { RequestMethod.GET })
	@ResponseBody
	public String getFeedbackList(HttpServletRequest request,
			HttpServletResponse response, FeedbackQueryForm form) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseSMService.getFeedbackList(form);
		} catch (Exception e) {
			log.error("getFeedbackList ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;

	}

	/**
	 * 新增广告管理信息
	 * 
	 * @author liukh
	 * @date 2017-3-8 下午3:40:12
	 * @param request
	 * @param response
	 * @param data
	 * @return
	 */
	@RequestMapping(value = "/advertisement", method = { RequestMethod.POST })
	@ResponseBody
	public String addAdvertisement(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "data", required = true) String data) {

		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseSMService.addAdvertisement(data);
		} catch (Exception e) {
			log.error("addAdvertisement ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;
	}

	/**
	 * 获取广告管理信息
	 * 
	 * @author liukh
	 * @date 2017-3-8 下午3:41:05
	 * @param request
	 * @param response
	 * @param advertisementId
	 * @return
	 */
	@RequestMapping(value = "/advertisement/{advertisementId}", method = { RequestMethod.GET })
	@ResponseBody
	public String getAdvertisement(HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable(value = "advertisementId") String advertisementId) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseSMService.getAdvertisement(advertisementId);
		} catch (Exception e) {
			log.error("getAdvertisement ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;
	}

	/**
	 * 更新广告管理信息
	 * 
	 * @author liukh
	 * @date 2017-3-8 下午3:42:20
	 * @param request
	 * @param response
	 * @param advertisementId
	 * @param data
	 * @return
	 */
	@RequestMapping(value = "/advertisement/{advertisementId}", method = { RequestMethod.POST })
	@ResponseBody
	public String updateAdvertisement(HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable(value = "advertisementId") String advertisementId,
			@RequestParam(value = "data", required = true) String data) {

		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseSMService.updateAdvertisement(advertisementId, data);
		} catch (Exception e) {
			log.error("updateAdvertisement ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;
	}

	/**
	 * 获取广告管理信息列表
	 * 
	 * @author liukh
	 * @date 2017-3-8 下午3:43:05
	 * @param request
	 * @param response
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "/advertisement/list", method = { RequestMethod.GET })
	@ResponseBody
	public String getAdvertisementList(HttpServletRequest request,
			HttpServletResponse response, AdvertisementQueryForm form) {

		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseSMService.getAdvertisementList(form);
		} catch (Exception e) {
			log.error("getAdvertisementList ---- 异常,message = "
					+ e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;
	}
	/**
	 * 获取更新版本信息
	 * @author liukh
	 * @date 2017-3-25 下午4:56:57
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/lastVersion", method = { RequestMethod.GET })
	@ResponseBody
	public String getLastAPPVersion(HttpServletRequest request,
			HttpServletResponse response) {
		String jsonStr = "";
		try {
			Map replaceParams = ToolUtils.getParams(request);
			jsonStr = baseSMService.getLastAPPVersion(replaceParams);
		} catch (Exception e) {
			log.error("getLastAPPVersion ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		log.info("getLastAPPVersion ---- jsonStr ========= " + jsonStr);
		return jsonStr;
	}
	
	/**
	 * 获取未读的内部消息统计
	 * @author liukh
	 * @date 2017-3-25 下午4:57:41
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/unReadMessageAmount", method = { RequestMethod.GET })
	@ResponseBody
	public String getUnReadMessageAmount(HttpServletRequest request,
			HttpServletResponse response,MessageQueryForm form) {
		String jsonStr = "";
		try {
		
			jsonStr = baseSMService.getUnReadMessageAmount(form);
				
		} catch (Exception e) {
			log.error("getUnReadMessageAmount ---- 异常,message = "
					+ e.getMessage());
			e.printStackTrace();
		}
		log.info("getUnReadMessageAmount ---- jsonStr ========= " + jsonStr);
		return jsonStr;
	}
	/**
	 * 机主端广告
	 * @author liukh
	 * @date 2017-3-25 下午4:59:57
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/leasingSide/advertising/list", method = { RequestMethod.GET })
	@ResponseBody
	public String getLeasingSideAdvertisingList(HttpServletRequest request,
			HttpServletResponse response,AdvertisementQueryForm form){
		String jsonStr = "";
		try {
		
			jsonStr = baseSMService.getLeasingSideAdvertisingList(form);
				
		} catch (Exception e) {
			log.error("getLeasingSideAdvertisingList ---- 异常,message = "
					+ e.getMessage());
			e.printStackTrace();
		}
		log.info("getLeasingSideAdvertisingList ---- jsonStr ========= " + jsonStr);
		return jsonStr;
		}
	/**
	 * 承租方广告位
	 * @author liukh
	 * @date 2017-1-5 下午2:28:40
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/lesseeSide/advertising/list", method = { RequestMethod.GET })
	@ResponseBody
	public String getLesseeSideAdvertisingList(HttpServletRequest request,
			HttpServletResponse response,AdvertisementQueryForm form){
		String jsonStr = "";
		try {
		
			jsonStr = baseSMService.getLesseeSideAdvertisingList(form);
				
		} catch (Exception e) {
			log.error("getLesseeSideAdvertisingList ---- 异常,message = "
					+ e.getMessage());
			e.printStackTrace();
		}
		log.info("getLesseeSideAdvertisingList ---- jsonStr ========= " + jsonStr);
		return jsonStr;
	}


}
