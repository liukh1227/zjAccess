package com.zj.access.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zj.access.base.dao.IBaseDao;
import com.zj.access.base.mapper.AdvertisementMapper;
import com.zj.access.base.mapper.FeedbackMapper;
import com.zj.access.base.mapper.LogMapper;
import com.zj.access.base.mapper.MessageMapper;
import com.zj.access.base.mapper.SmsDistributionLogMapper;
import com.zj.access.common.FrameworkUtils;
import com.zj.access.utils.APPVersionUtil;
import com.zj.base.common.Constant;
import com.zj.base.common.ItemPage;
import com.zj.base.common.utils.sms.SmsUtil;
import com.zj.base.entity.base.dto.BaseDto;
import com.zj.base.entity.base.dto.BaseObjDto;
import com.zj.entity.base.po.Advertisement;
import com.zj.entity.base.po.Feedback;
import com.zj.entity.base.po.Log;
import com.zj.entity.base.po.Message;
import com.zj.entity.base.po.SmsDistributionLog;
import com.zj.entity.sm.dto.MessageDto;
import com.zj.entity.sm.form.AdvertisementQueryForm;
import com.zj.entity.sm.form.FeedbackQueryForm;
import com.zj.entity.sm.form.LogQueryForm;
import com.zj.entity.sm.form.MessageQueryForm;
import com.zj.entity.sm.form.SmsDistributionLogQueryForm;

@Service("baseSMService")
@Scope("prototype")
public class BaseSMServiceImpl implements BaseSMService {
	private static final Logger log = Logger.getLogger(BaseSMServiceImpl.class);
	@Resource
	private IBaseDao baseDao;

	/**
	 * 新增内部消息
	 * 
	 * @author liukh
	 * @date 2017-3-8 下午2:31:15
	 * @param data
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseSMService#addMessage(java.lang.String)
	 */
	@Override
	public String addMessage(String data) {
		String jsonStr = "";
		BaseDto dto = new BaseDto();
		if (StringUtils.isNotBlank(data)) {
			try {
				Message message = JSON.parseObject(data, Message.class);
				if (message == null) {
					dto.setRcode(1);
					dto.setRinfo("The data parseObject to Message is null !");
					return JSON.toJSONString(dto);
				}
				message.setCreateTime(new Date());
				dto = baseDao.insertSelective(MessageMapper.class, message);
				if (FrameworkUtils.isSuccess(dto)) {
					log.info("addMessage success!");
				} else {
					log.error("addMessage failure!");
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("addMessage exception!");
				throw new RuntimeException("addMessage Exception!");
			}
		} else {
			log.error("---addMessage -------- data is null ==== ");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 获取内部消息
	 * 
	 * @author liukh
	 * @date 2017-3-8 下午2:32:04
	 * @param messageId
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseSMService#getMessage(java.lang.String)
	 */
	@Override
	public String getMessage(String messageId) {
		String jsonStr = "";
		BaseObjDto<Message> dto = new BaseObjDto<Message>();
		try {
			if (StringUtils.isBlank(messageId)) {
				dto.setRinfo("messageId is null");
				return JSON.toJSONString(dto);
			}
			BaseObjDto<Message> messageDto = baseDao.selectByPrimaryKey(
					MessageMapper.class, StringUtils.trim(messageId));
			if (FrameworkUtils.isSuccess(messageDto)) {
				Message message = messageDto.getData();
				dto.setData(message);
				FrameworkUtils.setSuccess(dto);
				log.info("getMessage success!");
			} else {
				FrameworkUtils.setNoData(dto);
				log.error("getMessage failure");
			}
		} catch (Exception e) {
			log.error("getMessage exception!");
			e.printStackTrace();
			throw new RuntimeException("getMessage Exception!");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 更新内部消息
	 * 
	 * @author liukh
	 * @date 2017-3-8 下午2:32:44
	 * @param messageId
	 * @param data
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseSMService#updateMessage(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public String updateMessage(String messageId, String data) {
		String jsonStr = "";
		BaseDto dto = new BaseDto();
		if (StringUtils.isNotBlank(messageId) && StringUtils.isNotBlank(data)) {
			try {
				Message message = JSON.parseObject(data, Message.class);
				if (message == null) {
					dto.setRcode(1);
					dto.setRinfo("The data parseObject to Message is null !");
					return JSON.toJSONString(dto);
				}
				message.setId(messageId);
				dto = baseDao.updateByPrimaryKeySelective(MessageMapper.class,
						message);
				if (FrameworkUtils.isSuccess(dto)) {
					log.info("updateMessage success!");
				} else {
					log.error("updateMessage failure!");
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("updateMessage exception!");
				throw new RuntimeException("updateMessage Exception!");
			}

		} else {
			log.error("---updateMessage -------- data or messageId is null ==== ");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 获取内部消息列表
	 * 
	 * @author liukh
	 * @date 2017-3-8 下午2:33:04
	 * @param form
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseSMService#getMessageList(com.zj.entity.sm.form.MessageQueryForm)
	 */
	@Override
	public String getMessageList(MessageQueryForm form) {
		String jsonStr = "";
		BaseObjDto<ItemPage<Message>> dto = new BaseObjDto<ItemPage<Message>>();
		try {
			BaseObjDto<ItemPage<Message>> pagesDto = baseDao.getPageList(
					MessageMapper.class, Message.class, form,
					"getMessagePageList");
			if (FrameworkUtils.isSuccess(pagesDto)) {
				dto = pagesDto;
				log.info("getMessageList success");
			} else {
				FrameworkUtils.setNoData(dto);
				log.error("getMessageList failure");
				throw new RuntimeException("getMessageList Exception!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 增加日志管理信息
	 * 
	 * @author liukh
	 * @date 2017-3-8 下午2:33:54
	 * @param data
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseSMService#addLog(java.lang.String)
	 */
	@Override
	public String addLog(String data) {
		String jsonStr = "";
		BaseDto dto = new BaseDto();
		if (StringUtils.isNotBlank(data)) {
			try {
				Log logs = JSON.parseObject(data, Log.class);
				if (logs == null) {
					dto.setRcode(1);
					dto.setRinfo("The data parseObject to Log is null !");
					return JSON.toJSONString(dto);
				}
				Date crateDate = new Date();
				logs.setCreateTime(crateDate);
				dto = baseDao.insertSelective(LogMapper.class, logs);

				if (FrameworkUtils.isSuccess(dto)) {
					log.info("addLog success!");
				} else {
					log.error("addLog failure!");
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("addLog exception!");
				throw new RuntimeException("addLog Exception!");
			}
		} else {
			log.error("---addLog -------- data is null ==== ");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 获取日志管理信息
	 * 
	 * @author liukh
	 * @date 2017-3-8 下午2:40:24
	 * @param logId
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseSMService#getLog(java.lang.String)
	 */
	@Override
	public String getLog(String logId) {
		String jsonStr = "";
		BaseObjDto<Log> dto = new BaseObjDto<Log>();
		try {
			if (StringUtils.isBlank(logId)) {
				dto.setRinfo("logId is null");
				return JSON.toJSONString(dto);
			}
			BaseObjDto<Log> logDto = baseDao.selectByPrimaryKey(
					LogMapper.class, StringUtils.trim(logId));
			if (FrameworkUtils.isSuccess(logDto)) {
				Log logs = logDto.getData();
				dto.setData(logs);
				FrameworkUtils.setSuccess(dto);
				log.info("getLog success!");
			} else {
				FrameworkUtils.setNoData(dto);
				log.error("getLog failure");
			}
		} catch (Exception e) {
			log.error("getLog exception!");
			e.printStackTrace();
			throw new RuntimeException("getLog Exception!");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 更新日志信息
	 * 
	 * @author liukh
	 * @date 2017-3-8 下午2:55:07
	 * @param logId
	 * @param data
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseSMService#updateLog(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public String updateLog(String logId, String data) {
		String jsonStr = "";
		BaseDto dto = new BaseDto();
		if (StringUtils.isNotBlank(logId) && StringUtils.isNotBlank(data)) {
			try {
				Log logs = JSON.parseObject(data, Log.class);
				if (logs == null) {
					dto.setRcode(1);
					dto.setRinfo("The data parseObject to Log is null !");
					return JSON.toJSONString(dto);
				}
				logs.setId(logId);
				dto = baseDao
						.updateByPrimaryKeySelective(LogMapper.class, logs);
				if (FrameworkUtils.isSuccess(dto)) {
					log.info("updateLog success!");
				} else {
					log.error("updateLog failure!");
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("updateLog exception!");
				throw new RuntimeException("updateLog Exception!");
			}

		} else {
			log.error("---updateLog -------- data or messageId is null ==== ");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 获取日志管理信息列表
	 * 
	 * @author liukh
	 * @date 2017-3-8 下午2:40:40
	 * @param form
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseSMService#getLogList(com.zj.entity.sm.form.LogQueryForm)
	 */
	@Override
	public String getLogList(LogQueryForm form) {
		String jsonStr = "";
		BaseObjDto<ItemPage<Log>> dto = new BaseObjDto<ItemPage<Log>>();
		try {
			BaseObjDto<ItemPage<Log>> pagesDto = baseDao.getPageList(
					LogMapper.class, Log.class, form, "getLogPageList");
			if (FrameworkUtils.isSuccess(pagesDto)) {
				dto = pagesDto;
				log.info("getLogList success");
			} else {
				FrameworkUtils.setNoData(dto);
				log.error("getLogList failure");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("getLogList exception");
			throw new RuntimeException("getLogList Exception!");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 增加短信下发历史记录
	 * 
	 * @author liukh
	 * @date 2017-3-8 下午2:43:17
	 * @param data
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseSMService#addSmsDistributionLog(java.lang.String)
	 */
	@Override
	public String addSmsDistributionLog(String data) {
		String jsonStr = "";
		BaseDto dto = new BaseDto();
		if (StringUtils.isNotBlank(data)) {
			try {
				SmsDistributionLog smsDistributionLog = JSON.parseObject(data,
						SmsDistributionLog.class);
				if (smsDistributionLog == null) {
					dto.setRcode(1);
					dto.setRinfo("The data parseObject to SmsDistributionLog is null !");
					return JSON.toJSONString(dto);
				}
				Date createDate = new Date();
				smsDistributionLog.setCreateTime(createDate);
				dto = baseDao.insertSelective(SmsDistributionLogMapper.class,
						smsDistributionLog);
				if (FrameworkUtils.isSuccess(dto)) {
					log.info("addSmsDistributionLog success!");
				} else {
					log.error("addSmsDistributionLog failure!");
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("addSmsDistributionLog exception!");
				throw new RuntimeException("addSmsDistributionLog Exception!");
			}
		} else {
			log.error("---addSmsDistributionLog -------- data is null ==== ");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 获取短信下发历史记录
	 * 
	 * @author liukh
	 * @date 2017-3-8 下午2:46:20
	 * @param smsDistributionLogId
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseSMService#getSmsDistributionLog(java.lang.String)
	 */
	@Override
	public String getSmsDistributionLog(String smsDistributionLogId) {
		String jsonStr = "";
		BaseObjDto<SmsDistributionLog> dto = new BaseObjDto<SmsDistributionLog>();
		try {
			if (StringUtils.isBlank(smsDistributionLogId)) {
				dto.setRinfo("smsDistributionLogId is null");
				return JSON.toJSONString(dto);
			}
			BaseObjDto<SmsDistributionLog> smsDistributionLogDto = baseDao
					.selectByPrimaryKey(SmsDistributionLogMapper.class,
							StringUtils.trim(smsDistributionLogId));
			if (FrameworkUtils.isSuccess(smsDistributionLogDto)) {
				SmsDistributionLog smsDistributionLog = smsDistributionLogDto
						.getData();
				dto.setData(smsDistributionLog);
				FrameworkUtils.setSuccess(dto);
				log.info("getSmsDistributionLog success!");
			} else {
				FrameworkUtils.setNoData(dto);
				log.error("getSmsDistributionLog failure");
			}
		} catch (Exception e) {
			log.error("getSmsDistributionLog exception!");
			e.printStackTrace();
			throw new RuntimeException("getSmsDistributionLog Exception!");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 更新短信下发历史记录
	 * 
	 * @author liukh
	 * @date 2017-3-8 下午2:46:58
	 * @param smsDistributionLogId
	 * @param data
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseSMService#updateSmsDistributionLog(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public String updateSmsDistributionLog(String smsDistributionLogId,
			String data) {
		String jsonStr = "";
		BaseDto dto = new BaseDto();
		if (StringUtils.isNotBlank(smsDistributionLogId)
				&& StringUtils.isNotBlank(data)) {
			try {
				SmsDistributionLog smsDistributionLog = JSON.parseObject(data,
						SmsDistributionLog.class);
				if (smsDistributionLog == null) {
					dto.setRcode(1);
					dto.setRinfo("The data parseObject to SmsDistributionLog is null !");
					return JSON.toJSONString(dto);
				}
				smsDistributionLog.setId(smsDistributionLogId);
				dto = baseDao.updateByPrimaryKeySelective(
						SmsDistributionLogMapper.class, smsDistributionLog);
				if (FrameworkUtils.isSuccess(dto)) {
					log.info("updateSmsDistributionLog success!");
				} else {
					log.error("updateSmsDistributionLog failure!");
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("updateSmsDistributionLog exception!");
				throw new RuntimeException(
						"updateSmsDistributionLog Exception!");
			}

		} else {
			log.error("---updateSmsDistributionLog -------- data or messageId is null ==== ");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 获取短信下发历史记录列表
	 * 
	 * @author liukh
	 * @date 2017-3-8 下午2:47:22
	 * @param form
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseSMService#getSmsDistributionLogList(com.zj.entity.sm.form.SmsDistributionLogQueryForm)
	 */
	@Override
	public String getSmsDistributionLogList(SmsDistributionLogQueryForm form) {
		String jsonStr = "";
		BaseObjDto<ItemPage<SmsDistributionLog>> dto = new BaseObjDto<ItemPage<SmsDistributionLog>>();
		try {
			BaseObjDto<ItemPage<SmsDistributionLog>> pagesDto = baseDao
					.getPageList(SmsDistributionLogMapper.class,
							SmsDistributionLog.class, form,
							"getSmsDistributionLogPageList");
			if (FrameworkUtils.isSuccess(pagesDto)) {
				dto = pagesDto;
				log.info("getSmsDistributionLogList success");
			} else {
				FrameworkUtils.setNoData(dto);
				log.error("getSmsDistributionLogList failure");

			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("getSmsDistributionLogList exception");
			throw new RuntimeException("getSmsDistributionLogList Exception!");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 新增意见反馈
	 * 
	 * @author liukh
	 * @date 2017-3-8 下午2:48:21
	 * @param data
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseSMService#addFeedback(java.lang.String)
	 */
	@Override
	public String addFeedback(String data) {
		String jsonStr = "";
		BaseDto dto = new BaseDto();
		if (StringUtils.isNotBlank(data)) {
			try {
				Feedback feedback = JSON.parseObject(data, Feedback.class);
				if (feedback == null) {
					dto.setRcode(1);
					dto.setRinfo("The data parseObject to Feedback is null !");
					return JSON.toJSONString(dto);
				}
				Date createDate = new Date();
				feedback.setCreateTime(createDate);
				dto = baseDao.insertSelective(FeedbackMapper.class, feedback);
				if (FrameworkUtils.isSuccess(dto)) {
					log.info("addFeedback success!");
				} else {
					log.error("addFeedback failure!");
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("addFeedback exception!");
				throw new RuntimeException("addFeedback Exception!");
			}
		} else {
			log.error("---addFeedback -------- data is null ==== ");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 获取意见反馈信息
	 * 
	 * @author liukh
	 * @date 2017-3-8 下午2:50:08
	 * @param feedbackId
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseSMService#getFeedback(java.lang.String)
	 */
	@Override
	public String getFeedback(String feedbackId) {
		String jsonStr = "";
		BaseObjDto<Feedback> dto = new BaseObjDto<Feedback>();
		try {
			if (StringUtils.isBlank(feedbackId)) {
				dto.setRinfo("feedbackId is null");
				return JSON.toJSONString(dto);
			}
			BaseObjDto<Feedback> FeedbackDto = baseDao.selectByPrimaryKey(
					FeedbackMapper.class, StringUtils.trim(feedbackId));
			if (FrameworkUtils.isSuccess(FeedbackDto)) {
				Feedback feedback = FeedbackDto.getData();
				dto.setData(feedback);
				FrameworkUtils.setSuccess(dto);
				log.info("getFeedback success!");
			} else {
				FrameworkUtils.setNoData(dto);
				log.error("getFeedback failure");
			}
		} catch (Exception e) {
			log.error("getFeedback exception!");
			e.printStackTrace();
			throw new RuntimeException("getFeedback Exception!");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 更新意见反馈信息
	 * 
	 * @author liukh
	 * @date 2017-3-8 下午2:56:33
	 * @param feedbackId
	 * @param data
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseSMService#updateFeedback(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public String updateFeedback(String feedbackId, String data) {
		String jsonStr = "";
		BaseDto dto = new BaseDto();
		if (StringUtils.isNotBlank(feedbackId) && StringUtils.isNotBlank(data)) {
			try {
				Feedback feedback = JSON.parseObject(data, Feedback.class);
				if (feedback == null) {
					dto.setRcode(1);
					dto.setRinfo("The data parseObject to Feedback is null !");
					return JSON.toJSONString(dto);
				}
				feedback.setId(feedbackId);
				dto = baseDao.updateByPrimaryKeySelective(FeedbackMapper.class,
						feedback);
				if (FrameworkUtils.isSuccess(dto)) {
					log.info("updateFeedback success!");
				} else {
					log.error("updateFeedback failure!");
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("updateFeedback exception!");
				throw new RuntimeException("updateFeedback Exception!");
			}

		} else {
			log.error("---updateFeedback -------- data or messageId is null ==== ");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 获取意见反馈信息列表
	 * 
	 * @author liukh
	 * @date 2017-3-8 下午2:51:02
	 * @param form
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseSMService#getFeedbackList(com.zj.entity.sm.form.FeedbackQueryForm)
	 */
	@Override
	public String getFeedbackList(FeedbackQueryForm form) {
		String jsonStr = "";
		BaseObjDto<ItemPage<Feedback>> dto = new BaseObjDto<ItemPage<Feedback>>();
		try {
			BaseObjDto<ItemPage<Feedback>> pagesDto = baseDao.getPageList(
					FeedbackMapper.class, Feedback.class, form,
					"getFeedbackPageList");
			if (FrameworkUtils.isSuccess(pagesDto)) {
				dto = pagesDto;
				log.info("getFeedbackList success");
			} else {
				FrameworkUtils.setNoData(dto);
				log.error("getFeedbackList failure");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("getFeedbackList exception");
			throw new RuntimeException("getFeedbackList Exception!");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 新增广告信息
	 * 
	 * @author liukh
	 * @date 2017-3-8 下午3:03:06
	 * @param data
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseSMService#addAdvertisement(java.lang.String)
	 */
	@Override
	public String addAdvertisement(String data) {
		String jsonStr = "";
		BaseDto dto = new BaseDto();
		if (StringUtils.isNotBlank(data)) {
			try {
				Advertisement advertisement = JSON.parseObject(data,
						Advertisement.class);
				if (advertisement == null) {
					dto.setRcode(1);
					dto.setRinfo("The data parseObject to Advertisement is null !");
					return JSON.toJSONString(dto);
				}
				Date createDate = new Date();
				advertisement.setCreateTime(createDate);
				dto = baseDao.insertSelective(AdvertisementMapper.class,
						advertisement);
				if (FrameworkUtils.isSuccess(dto)) {
					log.info("addAdvertisement success!");
				} else {
					log.error("addAdvertisement failure!");
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("addAdvertisement exception!");
				throw new RuntimeException("addAdvertisement Exception!");
			}
		} else {
			log.error("---addAdvertisement -------- data is null ==== ");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 获取广告信息
	 * 
	 * @author liukh
	 * @date 2017-3-8 下午3:03:23
	 * @param advertisementId
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseSMService#getAdvertisement(java.lang.String)
	 */
	@Override
	public String getAdvertisement(String advertisementId) {
		String jsonStr = "";
		BaseObjDto<Advertisement> dto = new BaseObjDto<Advertisement>();
		try {
			if (StringUtils.isBlank(advertisementId)) {
				dto.setRinfo("advertisementId is null");
				return JSON.toJSONString(dto);
			}
			BaseObjDto<Advertisement> advertisementDto = baseDao
					.selectByPrimaryKey(AdvertisementMapper.class,
							StringUtils.trim(advertisementId));
			if (FrameworkUtils.isSuccess(advertisementDto)) {
				Advertisement advertisement = advertisementDto.getData();
				dto.setData(advertisement);
				FrameworkUtils.setSuccess(dto);
				log.info("getAdvertisement success!");
			} else {
				FrameworkUtils.setNoData(dto);
				log.error("getAdvertisement failure");
			}
		} catch (Exception e) {
			log.error("getAdvertisement exception!");
			e.printStackTrace();
			throw new RuntimeException("getAdvertisement Exception!");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 更新广告信息
	 * 
	 * @author liukh
	 * @date 2017-3-8 下午3:03:50
	 * @param advertisementId
	 * @param data
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseSMService#updateAdvertisement(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public String updateAdvertisement(String advertisementId, String data) {
		String jsonStr = "";
		BaseDto dto = new BaseDto();
		if (StringUtils.isNotBlank(advertisementId)
				&& StringUtils.isNotBlank(data)) {
			try {
				Advertisement advertisement = JSON.parseObject(data,
						Advertisement.class);
				if (advertisement == null) {
					dto.setRcode(1);
					dto.setRinfo("The data parseObject to Advertisement is null !");
					return JSON.toJSONString(dto);
				}
				advertisement.setId(advertisementId);
				dto = baseDao.updateByPrimaryKeySelective(
						AdvertisementMapper.class, advertisement);
				if (FrameworkUtils.isSuccess(dto)) {
					log.info("updateAdvertisement success!");
				} else {
					log.error("updateAdvertisement failure!");
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("updateAdvertisement exception!");
				throw new RuntimeException("updateAdvertisement Exception!");
			}

		} else {
			log.error("---updateAdvertisement -------- data or messageId is null ==== ");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 获取广告信息列表
	 * 
	 * @author liukh
	 * @date 2017-3-8 下午2:52:52
	 * @param form
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseSMService#getAdvertisementList(com.zj.entity.sm.form.AdvertisementQueryForm)
	 */
	@Override
	public String getAdvertisementList(AdvertisementQueryForm form) {
		String jsonStr = "";
		BaseObjDto<ItemPage<Advertisement>> dto = new BaseObjDto<ItemPage<Advertisement>>();
		try {
			BaseObjDto<ItemPage<Advertisement>> pagesDto = baseDao.getPageList(
					AdvertisementMapper.class, Advertisement.class, form,
					"getAdvertisementPageList");
			if (FrameworkUtils.isSuccess(pagesDto)) {
				dto = pagesDto;
				log.info("getAdvertisementList success");
			} else {
				FrameworkUtils.setNoData(dto);
				log.error("getAdvertisementList failure");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("getAdvertisementList exception");
			throw new RuntimeException("getAdvertisementList Exception!");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 获取消息详细信息列表
	 * 
	 * @author liukh
	 * @date 2017-3-25 下午3:33:30
	 * @param form
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseSMService#getMessageContainPictureList(com.zj.entity.sm.form.MessageQueryForm)
	 */
	@Override
	public String getMessageContainPictureList(MessageQueryForm form) {
		String jsonStr = "";
		BaseObjDto<ItemPage<MessageDto>> dto = new BaseObjDto<ItemPage<MessageDto>>();
		try {
			BaseObjDto<ItemPage<MessageDto>> pagesDto = baseDao.getPageList(
					MessageMapper.class, MessageDto.class, form,
					"getMessageDeatilPageList");
			if (FrameworkUtils.isSuccess(pagesDto)) {
				dto = pagesDto;
				log.info("getMessageContainPictureList success");
			} else {
				FrameworkUtils.setNoData(dto);
				log.error("getMessageContainPictureList failure");
				throw new RuntimeException(
						"getMessageContainPictureList Exception!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}
/**
 * 获取版本信息
 * @author liukh
 * @date 2017-3-25 下午6:07:45 
 * @param params
 * @return
 * (non-Javadoc)
 * @see com.zj.access.service.BaseSMService#getLastAPPVersion(java.util.Map)
 */
	@Override
	public String getLastAPPVersion(Map<String, Object> params) {
		String jsonStr = null;
		BaseDto dto = new BaseDto();
		InputStream in = null;
		ByteArrayOutputStream baos = null;
		try {
			if (params == null) {
				dto.setRcode(BaseDto.ERROR_RCODE);
				dto.setRinfo("The paramas is null !");
				return JSON.toJSONString(dto);
			} else if (params.get("channel") == null) {
				dto.setRcode(BaseDto.ERROR_RCODE);
				dto.setRinfo("The paramas's  channel is null !");
				return JSON.toJSONString(dto);
			} else if (params.get("clientType") == null) {
				dto.setRcode(BaseDto.ERROR_RCODE);
				dto.setRinfo("The paramas's  clientType is null !");
				return JSON.toJSONString(dto);
			} else if (!(params.get("clientType").toString()
					.equals(Constant.COMPANY_BUSSINESS_LEASINGSID) || params
					.get("clientType").toString()
					.equals(Constant.COMPANY_BUSSINESS_LESSEESIDE))) {
				dto.setRcode(BaseDto.ERROR_RCODE);
				dto.setRinfo("The paramas's  clientType is not right value !");
				return JSON.toJSONString(dto);
			}

			return APPVersionUtil.getVersionMessage(params.get("channel")
					.toString(), params.get("clientType").toString());

		} catch (Exception e) {
			log.error("getLastAPPVersion ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (baos != null) {
				try {
					baos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		log.info("getLastAPPVersion ---- jsonStr ========= " + jsonStr);
		return jsonStr;
	}
/**
 * 获取未读的内部消息数量
 * @author liukh
 * @date 2017-3-25 下午6:08:03 
 * @param form
 * @return
 * (non-Javadoc)
 * @see com.zj.access.service.BaseSMService#getUnReadMessageAmount(com.zj.entity.sm.form.MessageQueryForm)
 */
	@Override
	public String getUnReadMessageAmount(MessageQueryForm form) {
		BaseObjDto<Object> dto = new BaseObjDto<Object>();
		String jsonStr = "";
		try {
			if (form == null) {
				dto.setRcode(BaseDto.ERROR_RCODE);
				dto.setRinfo("The request params is null !");
				return JSON.toJSONString(dto);
			} else if (form.getUserId() == null) {
				dto.setRcode(BaseDto.ERROR_RCODE);
				dto.setRinfo("The request's userId is null !");
				return JSON.toJSONString(dto);
			}
			form.setStatus(Constant.MESSAGE_STATUS_UNREAD);
			Long amount = baseDao.getCount(MessageMapper.class,
					"getUnReadMessageAmount", form);
			JSONObject js = new JSONObject();
			js.put("amount", amount);
			dto.setRcode(BaseDto.SUCCESS_RCODE);
			dto.setRinfo(BaseDto.DEFAULT_SUCCESS_INFO);
			dto.setData(js);

		} catch (Exception e) {
			e.printStackTrace();
			log.error("getUnReadMessageAmount exception");
			throw new RuntimeException("getUnReadMessageAmount Exception!");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	@Override
	public String getLeasingSideAdvertisingList(AdvertisementQueryForm form) {
		BaseObjDto<ItemPage<Advertisement>> listDto = new BaseObjDto<ItemPage<Advertisement>>();

		listDto.setRcode(BaseDto.SUCCESS_RCODE);
		listDto.setRinfo(BaseDto.DEFAULT_SUCCESS_INFO);
		ItemPage<Advertisement> pages = new ItemPage<Advertisement>();

		List<Advertisement> list = new ArrayList<Advertisement>();

		Advertisement advertisement1 = new Advertisement();
		String pictureUrl1 = "http://api.chebaotec.com/app/device/2016122217335490448823050/20170105/9df1c053a96a54738146a6c4640535e5.jpg";
		advertisement1.setPictureURL(pictureUrl1);
		list.add(advertisement1);

		Advertisement advertisement2 = new Advertisement();
		String pictureUrl2 = "http://api.chebaotec.com/app/device/2016122217335490448823050/20170105/b79759a57a3bc419b9719178338e550a.jpg";
		advertisement2.setPictureURL(pictureUrl2);
		list.add(advertisement2);
		pages.setItems(list);
		listDto.setData(pages);

		return JSON.toJSONString(listDto);
	}

	@Override
	public String getLesseeSideAdvertisingList(AdvertisementQueryForm form) {
		BaseObjDto<ItemPage<Advertisement>> listDto = new BaseObjDto<ItemPage<Advertisement>>();

		listDto.setRcode(BaseDto.SUCCESS_RCODE);
		listDto.setRinfo(BaseDto.DEFAULT_SUCCESS_INFO);
		ItemPage<Advertisement> pages = new ItemPage<Advertisement>();

		List<Advertisement> list = new ArrayList<Advertisement>();

		Advertisement advertisement1 = new Advertisement();
		String pictureUrl1 = "http://api.chebaotec.com/app/device/2016122217335490448823050/20170105/ded13793ee5c8a979eb9b650ef754435.jpg";
		advertisement1.setPictureURL(pictureUrl1);
		list.add(advertisement1);

		Advertisement advertisement2 = new Advertisement();
		String pictureUrl2 = "http://api.chebaotec.com/app/device/2016122217335490448823050/20170105/4ef8ddcaa5848fd8bf42b75e22753e62.jpg";
		advertisement2.setPictureURL(pictureUrl2);
		list.add(advertisement2);
		pages.setItems(list);
		listDto.setData(pages);

		return JSON.toJSONString(listDto);
	}

	// ------------------------------------------------------订单短信通知-----------------------------------------------
	/**
	 * 订单线下协商订单短信
	 * 
	 * @author liukh
	 * @date 2017-3-25 下午4:40:05
	 * @param phoneNumber
	 * @param smsConent
	 *            (non-Javadoc)
	 * @see com.zj.access.service.BaseSMService#sendOrderMessageOfflineSms(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public void sendOrderMessageOfflineSms(String phoneNumber, String smsConent) {
		String orderMessageStr = null;
		BaseDto dto = new BaseDto();
		orderMessageStr = JSON.toJSONString(dto);
		try {
			orderMessageStr = SmsUtil.sendOrderMessageOfflineSms(phoneNumber,
					smsConent);
			log.info("sendOrderMessageOfflineSms ---- orderMessageStr ========= "
					+ orderMessageStr);
			BaseDto baseDto = JSON.parseObject(orderMessageStr, BaseDto.class);

			if (FrameworkUtils.isSuccess(baseDto)) {
				SmsDistributionLog addSmsDistributionLog = new SmsDistributionLog();
				addSmsDistributionLog.setCellphone(phoneNumber);
				addSmsDistributionLog.setContent(orderMessageStr);
				addSmsDistributionLog.setStatus(Constant.SENDCODE_SENDED);
				addSmsDistributionLog.setTotalDistributionCount(1);

				this.addSmsDistributionLog(JSON
						.toJSONString(addSmsDistributionLog));
			}

		} catch (Exception e) {
			log.error("sendOrderMessageOfflineSms ---- 异常,message = "
					+ e.getMessage());
			e.printStackTrace();
		}

	}

	/**
	 * 租赁方线上支付订单通知短信
	 * 
	 * @author liukh
	 * @date 2017-3-25 下午4:39:49
	 * @param phoneNumber
	 * @param smsConent
	 * @param smslockContent
	 *            (non-Javadoc)
	 * @see com.zj.access.service.BaseSMService#sendOrderMessageOnlineLeasingSideSms(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	@Override
	public void sendOrderMessageOnlineLeasingSideSms(String phoneNumber,
			String smsConent, String smslockContent) {
		String orderMessageStr = null;
		BaseDto dto = new BaseDto();
		orderMessageStr = JSON.toJSONString(dto);
		try {
			orderMessageStr = SmsUtil.sendOrderMessageOnlineLeasingSideSms(
					phoneNumber, smsConent, smslockContent);
			log.info("sendOrderMessageOnlineLeasingSideSms ---- orderMessageStr ========= "
					+ orderMessageStr);
			BaseDto baseDto = JSON.parseObject(orderMessageStr, BaseDto.class);

			if (FrameworkUtils.isSuccess(baseDto)) {
				SmsDistributionLog addSmsDistributionLog = new SmsDistributionLog();
				addSmsDistributionLog.setCellphone(phoneNumber);
				addSmsDistributionLog.setContent(orderMessageStr);
				addSmsDistributionLog.setStatus(Constant.SENDCODE_SENDED);
				addSmsDistributionLog.setTotalDistributionCount(1);

				this.addSmsDistributionLog(JSON
						.toJSONString(addSmsDistributionLog));
			}

		} catch (Exception e) {
			log.error("sendOrderMessageOnlineLeasingSideSms ---- 异常,message = "
					+ e.getMessage());
			e.printStackTrace();
		}

	}

	/**
	 * 承租方线上支付订单通知短信
	 * 
	 * @author liukh
	 * @date 2017-3-25 下午4:39:26
	 * @param phoneNumber
	 * @param smsConent
	 *            (non-Javadoc)
	 * @see com.zj.access.service.BaseSMService#sendOrderMessageOnlineLesseeSideSms(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public void sendOrderMessageOnlineLesseeSideSms(String phoneNumber,
			String smsConent) {
		String orderMessageStr = null;
		BaseDto dto = new BaseDto();
		orderMessageStr = JSON.toJSONString(dto);
		try {
			orderMessageStr = SmsUtil.sendOrderMessageOnlineLesseeSideSms(
					phoneNumber, smsConent);
			log.info("sendOrderMessageOnlineLesseeSideSms ---- orderMessageStr ========= "
					+ orderMessageStr);
			BaseDto baseDto = JSON.parseObject(orderMessageStr, BaseDto.class);

			if (FrameworkUtils.isSuccess(baseDto)) {
				SmsDistributionLog addSmsDistributionLog = new SmsDistributionLog();
				addSmsDistributionLog.setCellphone(phoneNumber);
				addSmsDistributionLog.setContent(orderMessageStr);
				addSmsDistributionLog.setStatus(Constant.SENDCODE_SENDED);
				addSmsDistributionLog.setTotalDistributionCount(1);

				this.addSmsDistributionLog(JSON
						.toJSONString(addSmsDistributionLog));
			}

		} catch (Exception e) {
			log.error("sendOrderMessageOnlineLesseeSideSms ---- 异常,message = "
					+ e.getMessage());
			e.printStackTrace();
		}

	}

	/**
	 * 抛单抢单成功后，承租方提示信息
	 * 
	 * @author liukh
	 * @date 2017-3-25 下午4:39:10
	 * @param phoneNumber
	 * @param smsConent1
	 * @param smsConent2
	 * @param smsConent3
	 * @param smsConent4
	 *            (non-Javadoc)
	 * @see com.zj.access.service.BaseSMService#sendInqueryRentThrow4LesseeSideSms(java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public void sendInqueryRentThrow4LesseeSideSms(String phoneNumber,
			String smsConent1, String smsConent2, String smsConent3,
			String smsConent4) {
		String orderMessageStr = null;
		BaseDto dto = new BaseDto();
		orderMessageStr = JSON.toJSONString(dto);
		try {
			orderMessageStr = SmsUtil
					.sendInqueryRentThrow4LesseeSideSms(phoneNumber,
							smsConent1, smsConent2, smsConent3, smsConent4);
			log.info("sendInqueryRentThrow4LesseeSideSms ---- orderMessageStr ========= "
					+ orderMessageStr);
			BaseDto baseDto = JSON.parseObject(orderMessageStr, BaseDto.class);

			if (FrameworkUtils.isSuccess(baseDto)) {
				SmsDistributionLog addSmsDistributionLog = new SmsDistributionLog();
				addSmsDistributionLog.setCellphone(phoneNumber);
				addSmsDistributionLog.setContent(orderMessageStr);
				addSmsDistributionLog.setStatus(Constant.SENDCODE_SENDED);
				addSmsDistributionLog.setTotalDistributionCount(1);

				this.addSmsDistributionLog(JSON
						.toJSONString(addSmsDistributionLog));
			}

		} catch (Exception e) {
			log.error("sendInqueryRentThrow4LesseeSideSms ---- 异常,message = "
					+ e.getMessage());
			e.printStackTrace();
		}

	}

	/**
	 * 抛单抢单成功后，租赁方提示信息
	 * 
	 * @author liukh
	 * @date 2017-3-25 下午4:38:50
	 * @param phoneNumber
	 * @param smsConent1
	 * @param smsConent2
	 *            (non-Javadoc)
	 * @see com.zj.access.service.BaseSMService#sendInqueryRentThrow4ResponseLeasingSideSms(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	@Override
	public void sendInqueryRentThrow4ResponseLeasingSideSms(String phoneNumber,
			String smsConent1, String smsConent2) {
		String orderMessageStr = null;
		BaseDto dto = new BaseDto();
		orderMessageStr = JSON.toJSONString(dto);
		try {
			orderMessageStr = SmsUtil
					.sendInqueryRentThrow4ResponseLeasingSideSms(phoneNumber,
							smsConent1, smsConent2);
			log.info("sendInqueryRentThrow4ResponseLeasingSideSms ---- orderMessageStr ========= "
					+ orderMessageStr);
			BaseDto baseDto = JSON.parseObject(orderMessageStr, BaseDto.class);

			if (FrameworkUtils.isSuccess(baseDto)) {
				SmsDistributionLog addSmsDistributionLog = new SmsDistributionLog();
				addSmsDistributionLog.setCellphone(phoneNumber);
				addSmsDistributionLog.setContent(orderMessageStr);
				addSmsDistributionLog.setStatus(Constant.SENDCODE_SENDED);
				addSmsDistributionLog.setTotalDistributionCount(1);

				this.addSmsDistributionLog(JSON
						.toJSONString(addSmsDistributionLog));
			}

		} catch (Exception e) {
			log.error("sendInqueryRentThrow4ResponseLeasingSideSms ---- 异常,message = "
					+ e.getMessage());
			e.printStackTrace();
		}

	}

	/**
	 * 公司信息审核通过
	 * 
	 * @author liukh
	 * @date 2017-3-25 下午4:38:28
	 * @param phoneNumber
	 * @param smsConent
	 *            (non-Javadoc)
	 * @see com.zj.access.service.BaseSMService#sendCompanyCheckPassedInfoSms(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public void sendCompanyCheckPassedInfoSms(String phoneNumber,
			String smsConent) {
		String orderMessageStr = null;
		BaseDto dto = new BaseDto();
		orderMessageStr = JSON.toJSONString(dto);
		try {
			orderMessageStr = SmsUtil.sendCompanyCheckPassedInfoSms(
					phoneNumber, smsConent);
			log.info("sendCompanyCheckPassedInfoSms ---- orderMessageStr ========= "
					+ orderMessageStr);
			BaseDto baseDto = JSON.parseObject(orderMessageStr, BaseDto.class);

			if (FrameworkUtils.isSuccess(baseDto)) {
				SmsDistributionLog addSmsDistributionLog = new SmsDistributionLog();
				addSmsDistributionLog.setCellphone(phoneNumber);
				addSmsDistributionLog.setContent(orderMessageStr);
				addSmsDistributionLog.setStatus(Constant.SENDCODE_SENDED);
				addSmsDistributionLog.setTotalDistributionCount(1);

				this.addSmsDistributionLog(JSON
						.toJSONString(addSmsDistributionLog));
			}

		} catch (Exception e) {
			log.error("sendCompanyCheckPassedInfoSms ---- 异常,message = "
					+ e.getMessage());
			e.printStackTrace();
		}

	}

	/**
	 * 公司信息审核未通过
	 * 
	 * @author liukh
	 * @date 2017-3-25 下午4:37:50
	 * @param phoneNumber
	 * @param smsConent
	 *            (non-Javadoc)
	 * @see com.zj.access.service.BaseSMService#sendCompanyCheckNOPassedInfoSms(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public void sendCompanyCheckNOPassedInfoSms(String phoneNumber,
			String smsConent) {
		String orderMessageStr = null;
		BaseDto dto = new BaseDto();
		orderMessageStr = JSON.toJSONString(dto);
		try {
			orderMessageStr = SmsUtil.sendCompanyCheckNOPassedInfoSms(
					phoneNumber, smsConent);
			log.info("sendCompanyCheckNOPassedInfoSms ---- orderMessageStr ========= "
					+ orderMessageStr);
			BaseDto baseDto = JSON.parseObject(orderMessageStr, BaseDto.class);

			if (FrameworkUtils.isSuccess(baseDto)) {
				SmsDistributionLog addSmsDistributionLog = new SmsDistributionLog();
				addSmsDistributionLog.setCellphone(phoneNumber);
				addSmsDistributionLog.setContent(orderMessageStr);
				addSmsDistributionLog.setStatus(Constant.SENDCODE_SENDED);
				addSmsDistributionLog.setTotalDistributionCount(1);

				this.addSmsDistributionLog(JSON
						.toJSONString(addSmsDistributionLog));
			}

		} catch (Exception e) {
			log.error("sendCompanyCheckNOPassedInfoSms ---- 异常,message = "
					+ e.getMessage());
			e.printStackTrace();
		}

	}

	/**
	 * 公司信息需要审核
	 * 
	 * @author liukh
	 * @date 2017-3-25 下午4:37:28
	 * @param phoneNumber
	 *            (non-Javadoc)
	 * @see com.zj.access.service.BaseSMService#sendCompanyNeedCheckInfoSms(java.lang.String)
	 */
	@Override
	public void sendCompanyNeedCheckInfoSms(String phoneNumber) {
		// TODO Auto-generated method stub
		String orderMessageStr = null;
		BaseDto dto = new BaseDto();
		orderMessageStr = JSON.toJSONString(dto);
		try {
			orderMessageStr = SmsUtil
					.sendCompanyNeedCheckTemplateInfoSms(phoneNumber);
			log.info("sendCompanyNeedCheckInfoSms ---- orderMessageStr ========= "
					+ orderMessageStr);
			BaseDto baseDto = JSON.parseObject(orderMessageStr, BaseDto.class);

			if (FrameworkUtils.isSuccess(baseDto)) {
				SmsDistributionLog addSmsDistributionLog = new SmsDistributionLog();
				addSmsDistributionLog.setCellphone(phoneNumber);
				addSmsDistributionLog.setContent(orderMessageStr);
				addSmsDistributionLog.setStatus(Constant.SENDCODE_SENDED);
				addSmsDistributionLog.setTotalDistributionCount(1);

				this.addSmsDistributionLog(JSON
						.toJSONString(addSmsDistributionLog));
			}

		} catch (Exception e) {
			log.error("sendCompanyNeedCheckInfoSms ---- 异常,message = "
					+ e.getMessage());
			e.printStackTrace();
		}

	}

}
