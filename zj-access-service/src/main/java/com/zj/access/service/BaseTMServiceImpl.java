package com.zj.access.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.zj.access.base.dao.IBaseDao;
import com.zj.access.base.mapper.AccountDetailMapper;
import com.zj.access.base.mapper.CompanyDeviceTypeMapper;
import com.zj.access.base.mapper.CompanyMapper;
import com.zj.access.base.mapper.DeliveryOrderMapper;
import com.zj.access.base.mapper.DeviceInspectionMapper;
import com.zj.access.base.mapper.DeviceMapper;
import com.zj.access.base.mapper.DeviceModelMapper;
import com.zj.access.base.mapper.DeviceStatusTraceMapper;
import com.zj.access.base.mapper.DeviceTypeMapper;
import com.zj.access.base.mapper.DeviceTypeSpecDataMapper;
import com.zj.access.base.mapper.InqueryDeviceMapper;
import com.zj.access.base.mapper.InqueryOrderMapper;
import com.zj.access.base.mapper.InqueryRentMapper;
import com.zj.access.base.mapper.InqueryRentQuoteMapper;
import com.zj.access.base.mapper.InqueryRentThrowMapper;
import com.zj.access.base.mapper.InspectionDeviceMapper;
import com.zj.access.base.mapper.MessageMapper;
import com.zj.access.base.mapper.OrderCapitalPoolMapper;
import com.zj.access.base.mapper.OrderCommentMapper;
import com.zj.access.base.mapper.OrderInteractiveTraceMapper;
import com.zj.access.base.mapper.OrderProgressTraceMapper;
import com.zj.access.base.mapper.OrderStatementMapper;
import com.zj.access.base.mapper.RentOrderDeviceMapper;
import com.zj.access.base.mapper.RentOrderMapper;
import com.zj.access.common.FrameworkUtils;
import com.zj.base.common.Constant;
import com.zj.base.common.ItemPage;
import com.zj.base.common.utils.CommonUtils;
import com.zj.base.common.utils.sms.SmsTemplateUtils;
import com.zj.base.entity.base.dto.BaseDto;
import com.zj.base.entity.base.dto.BaseObjDto;
import com.zj.common.utils.MapToolUtils;
import com.zj.common.utils.NumberUtils;
import com.zj.entity.base.po.AccountDetail;
import com.zj.entity.base.po.Company;
import com.zj.entity.base.po.CompanyDeviceType;
import com.zj.entity.base.po.DeliveryOrder;
import com.zj.entity.base.po.Device;
import com.zj.entity.base.po.DeviceInspection;
import com.zj.entity.base.po.DeviceModel;
import com.zj.entity.base.po.DeviceStatusTrace;
import com.zj.entity.base.po.DeviceType;
import com.zj.entity.base.po.InqueryDevice;
import com.zj.entity.base.po.InqueryOrder;
import com.zj.entity.base.po.InqueryRent;
import com.zj.entity.base.po.InqueryRentQuote;
import com.zj.entity.base.po.InqueryRentThrow;
import com.zj.entity.base.po.Message;
import com.zj.entity.base.po.OrderCapitalPool;
import com.zj.entity.base.po.OrderComment;
import com.zj.entity.base.po.OrderInteractiveTrace;
import com.zj.entity.base.po.OrderProgressTrace;
import com.zj.entity.base.po.OrderStatement;
import com.zj.entity.base.po.RentOrder;
import com.zj.entity.base.po.RentOrderDevice;
import com.zj.entity.bm.dto.DeviceTypeSpecDataDto;
import com.zj.entity.bm.dto.UserDto;
import com.zj.entity.dm.dto.DeviceListDto;
import com.zj.entity.dm.form.DeviceQueryForm;
import com.zj.entity.sm.form.MessageQueryForm;
import com.zj.entity.tm.dto.DateTypeDto;
import com.zj.entity.tm.dto.DeliveryOrderListDto;
import com.zj.entity.tm.dto.InqueryDeviceAllListDto;
import com.zj.entity.tm.dto.InqueryDeviceListDto;
import com.zj.entity.tm.dto.InqueryDeviceUpdatePreview;
import com.zj.entity.tm.dto.InqueryOrderDto;
import com.zj.entity.tm.dto.InqueryRentDetailDto;
import com.zj.entity.tm.dto.InqueryRentDetailListDto;
import com.zj.entity.tm.dto.InqueryRentQuotoAndInqueryRentDto;
import com.zj.entity.tm.dto.InspectionDevice;
import com.zj.entity.tm.dto.LockedMoneyTigDto;
import com.zj.entity.tm.dto.OrderCalculatePriceDto;
import com.zj.entity.tm.dto.OrderCapitalPoolListDto;
import com.zj.entity.tm.dto.OrderStatementContainComanyNameDto;
import com.zj.entity.tm.dto.RentOrderDeviceListDto;
import com.zj.entity.tm.dto.RentOrderListDto;
import com.zj.entity.tm.dto.WaitWorkStatisticsDto;
import com.zj.entity.tm.form.DeliveryOrderQueryForm;
import com.zj.entity.tm.form.InqueryDeviceQueryForm;
import com.zj.entity.tm.form.InqueryOrderQueryForm;
import com.zj.entity.tm.form.InqueryRentQueryForm;
import com.zj.entity.tm.form.InqueryRentQuoteQueryForm;
import com.zj.entity.tm.form.InqueryRentThrowQueryForm;
import com.zj.entity.tm.form.InspectionDeviceQueryForm;
import com.zj.entity.tm.form.OrderCapitalPoolQueryForm;
import com.zj.entity.tm.form.OrderCommentQueryForm;
import com.zj.entity.tm.form.OrderInteractiveTraceQueryForm;
import com.zj.entity.tm.form.OrderProgressTraceQueryForm;
import com.zj.entity.tm.form.OrderStatementQueryForm;
import com.zj.entity.tm.form.RentOrderDeviceQueryForm;
import com.zj.entity.tm.form.RentOrderQueryForm;

@Service("baseTMService")
@Scope("prototype")
public class BaseTMServiceImpl implements BaseTMService {
	private static final Logger log = Logger.getLogger(BaseTMServiceImpl.class);
	@Autowired
	private IBaseDao baseDao;
	@Autowired
	private BaseOtherService baseOtherService;
	@Autowired
	private BaseSMService baseSMService;

	/**
	 * 新增询价单
	 * 
	 * @author liukh
	 * @date 2017-2-23 下午5:04:42
	 * @param data
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseTMService#addInqueryOrder(java.lang.String)
	 */
	@Override
	public String addInqueryOrder(String data) {
		String jsonStr = "";
		BaseDto dto = new BaseDto();
		BaseObjDto<JSONObject> returndto = new BaseObjDto<JSONObject>();
		if (StringUtils.isNotBlank(data)) {
			try {
				InqueryOrder inqueryOrder = JSON.parseObject(data,
						InqueryOrder.class);
				if (inqueryOrder == null) {
					dto.setRcode(1);
					dto.setRinfo("The data parseObject to InqueryOrder is null !");
					return JSON.toJSONString(dto);
				}
				Date createDate = new Date();
				inqueryOrder.setCreateTime(createDate);
				dto = baseDao.insertSelective(InqueryOrderMapper.class,
						inqueryOrder);
				if (FrameworkUtils.isSuccess(dto)) {
					JSONObject jp = new JSONObject();
					jp.put("id", inqueryOrder.getId());
					returndto.setData(jp);
					returndto.setRcode(dto.getRcode());
					returndto.setRinfo(dto.getRinfo());
					dto = returndto;
					log.info("addInqueryOrder success!");
				} else {
					log.error("addInqueryOrder failure!");
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("addInqueryOrder exception!");
				throw new RuntimeException("addInqueryOrder Exception!");
			}
		} else {
			log.error("---addInqueryOrder -------- data is null ==== ");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 加入询价车时，如果存在草稿下的询价车，则返回此询价车，如果不存在，则新增一个询价单，并返回询价单Id
	 * 
	 * @author liukh
	 * @date 2017-3-21 下午4:55:00
	 * @param data
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseTMService#addOrQueryInqueryOrder(java.lang.String)
	 */
	@Override
	public String addOrQueryInqueryOrder(String data) {
		String jsonStr = "";
		BaseObjDto<JSONObject> dto = new BaseObjDto<JSONObject>();
		if (StringUtils.isNotBlank(data)) {
			try {
				InqueryOrder inqueryOrder = JSON.parseObject(data,
						InqueryOrder.class);
				if (inqueryOrder == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data parseObject to InqueryOrder is null !");
					return JSON.toJSONString(dto);
				}
				if (inqueryOrder.getLeasingSideId() == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's leasingSideId is null");
					return JSON.toJSONString(dto);
				}
				if (inqueryOrder.getLesseeSideId() == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's lesseeSideId is null");
					return JSON.toJSONString(dto);
				}
				BaseObjDto<Company> companyLeaDto = baseDao.selectByPrimaryKey(
						CompanyMapper.class,
						StringUtils.trim(inqueryOrder.getLeasingSideId()));
				if (!FrameworkUtils.isSuccess(companyLeaDto)) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's leasingSideId is not exist !");
					return JSON.toJSONString(dto);
				} else {
					if (companyLeaDto.getData() != null
							&& companyLeaDto.getData().getCompanyName() != null) {
						inqueryOrder.setLeasingSideName(companyLeaDto.getData()
								.getCompanyName());
					}

				}
				BaseObjDto<Company> companyLesDto = baseDao.selectByPrimaryKey(
						CompanyMapper.class,
						StringUtils.trim(inqueryOrder.getLesseeSideId()));
				if (!FrameworkUtils.isSuccess(companyLesDto)) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's lesseeSideId is not exist !");
					return JSON.toJSONString(dto);
				} else {
					if (companyLesDto.getData() != null
							&& companyLesDto.getData().getCompanyName() != null) {
						inqueryOrder.setLesseeSideName(companyLesDto.getData()
								.getCompanyName());
					}

				}

				InqueryOrderQueryForm form = new InqueryOrderQueryForm();
				form.setLeasingSideId(inqueryOrder.getLeasingSideId());
				form.setLesseeSideId(inqueryOrder.getLesseeSideId());
				form.setStatus(Constant.INQUERYORDER_STATUS_CREATE.toString());
				BaseObjDto<ItemPage<InqueryOrder>> pagesDto = baseDao
						.getPageList(InqueryOrderMapper.class,
								InqueryOrder.class, form,
								"getInqueryOrderPageList");
				if (FrameworkUtils.isSuccess(pagesDto)) {
					if (pagesDto.getData() != null
							&& pagesDto.getData().getItems() != null
							&& pagesDto.getData().getItems().size() > 0) {
						InqueryOrder queryInqueryOrder = pagesDto.getData()
								.getItems().get(0);
						JSONObject jp = new JSONObject();
						jp.put("id", queryInqueryOrder.getId());
						dto.setData(jp);
						dto.setRcode(BaseDto.SUCCESS_RCODE);
						dto.setRinfo(BaseDto.DEFAULT_SUCCESS_INFO);

					}

				} else {

					UserDto user = baseOtherService
							.getUserDtoByComapnyId(inqueryOrder
									.getLesseeSideId());
					if (user != null && user.getId() != null) {
						inqueryOrder.setCurrentHandler(user.getId());
					}
					if(inqueryOrder.getStatus() == null){
						inqueryOrder.setStatus(Constant.INQUERYORDER_STATUS_CREATE);
					}

					return addInqueryOrder(JSON.toJSONString(inqueryOrder));
				}

				if (FrameworkUtils.isSuccess(dto)) {
					log.info("addOrQueryInqueryOrder success!");
				} else {
					log.error("addOrQueryInqueryOrder failure!");
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("addOrQueryInqueryOrder exception!");
				throw new RuntimeException("addOrQueryInqueryOrder Exception!");
			}
		} else {
			log.error("---addOrQueryInqueryOrder -------- data is null ==== ");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 获取询价单
	 * 
	 * @author liukh
	 * @date 2017-2-23 下午5:04:25
	 * @param inqueryOrderId
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseTMService#getInqueryOrder(java.lang.String)
	 */
	@Override
	public String getInqueryOrder(String inqueryOrderId) {
		String jsonStr = "";
		BaseObjDto<InqueryOrder> dto = new BaseObjDto<InqueryOrder>();
		try {
			if (StringUtils.isBlank(inqueryOrderId)) {
				dto.setRinfo("inqueryOrderId is null");
				return JSON.toJSONString(dto);
			}
			BaseObjDto<InqueryOrder> inqueryOrderDto = baseDao
					.selectByPrimaryKey(InqueryOrderMapper.class,
							StringUtils.trim(inqueryOrderId));
			if (FrameworkUtils.isSuccess(inqueryOrderDto)) {
				InqueryOrder inqueryOrder = inqueryOrderDto.getData();
				dto.setData(inqueryOrder);
				FrameworkUtils.setSuccess(dto);
				log.info("getInqueryOrder success!");
			} else {
				FrameworkUtils.setNoData(dto);
				log.error("getInqueryOrder failure");
			}
		} catch (Exception e) {
			log.error("getInqueryOrder exception!");
			e.printStackTrace();
			throw new RuntimeException("getInqueryOrder Exception!");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 获取询价单及设备列表
	 * 
	 * @author liukh
	 * @date 2017-3-29 下午3:17:50
	 * @param inqueryOrderId
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseTMService#getInqueryOrderAndDeviceList(java.lang.String)
	 */
	public String getInqueryOrderAndDeviceList(String inqueryOrderId) {
		String jsonStr = "";
		BaseObjDto<InqueryOrder> dto = new BaseObjDto<InqueryOrder>();
		try {
			if (StringUtils.isBlank(inqueryOrderId)) {
				dto.setRinfo("inqueryOrderId is null");
				return JSON.toJSONString(dto);
			}
			BaseObjDto<InqueryOrder> inqueryOrderDto = baseDao
					.selectByPrimaryKey(InqueryOrderMapper.class,
							StringUtils.trim(inqueryOrderId));
			if (FrameworkUtils.isSuccess(inqueryOrderDto)) {
				InqueryOrder inqueryOrder = inqueryOrderDto.getData();
				dto.setData(inqueryOrder);
				FrameworkUtils.setSuccess(dto);
				log.info("getInqueryOrder success!");
			} else {
				FrameworkUtils.setNoData(dto);
				log.error("getInqueryOrder failure");
			}
		} catch (Exception e) {
			log.error("getInqueryOrder exception!");
			e.printStackTrace();
			throw new RuntimeException("getInqueryOrder Exception!");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 修改询价单信息
	 * 
	 * @author liukh
	 * @date 2017-2-24 上午10:04:05
	 * @param inqueryOrderId
	 * @param data
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseTMService#updateInqueryOrder(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public String updateInqueryOrder(String inqueryOrderId, String data) {
		String jsonStr = "";
		BaseDto dto = new BaseDto();
		if (StringUtils.isNotBlank(inqueryOrderId)
				&& StringUtils.isNotBlank(data)) {
			try {
				InqueryOrder inqueryOrder = JSON.parseObject(data,
						InqueryOrder.class);
				if (inqueryOrder == null) {
					dto.setRcode(1);
					dto.setRinfo("The data parseObject to InqueryOrder is null !");
					return JSON.toJSONString(dto);
				}
				inqueryOrder.setId(inqueryOrderId);
				dto = baseDao.updateByPrimaryKeySelective(
						InqueryOrderMapper.class, inqueryOrder);
				if (FrameworkUtils.isSuccess(dto)) {
					log.info("updateInqueryOrder success!");
				} else {
					log.error("updateInqueryOrder failure!");
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("updateInqueryOrder exception!");
				throw new RuntimeException("updateInqueryOrder Exception!");
			}

		} else {
			log.error("---updateInqueryOrder -------- data or inqueryOrderId is null ==== ");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 删除询价单
	 * 
	 * @author liukh
	 * @date 2017-2-24 上午9:58:39
	 * @param inqueryOrderId
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseTMService#deleteInqueryOrder(java.lang.String)
	 */
	@Override
	public String deleteInqueryOrder(String inqueryOrderId) {
		String jsonStr = "";
		BaseDto dto = new BaseDto();
		if (StringUtils.isNotBlank(inqueryOrderId)) {
			// 删除
			dto = baseDao.deleteByPrimaryKey(InqueryOrderMapper.class,
					StringUtils.trim(inqueryOrderId));
			if (FrameworkUtils.isSuccess(dto)) {
				log.info("deleteInqueryOrder success!");
			}
		} else {
			log.error("---deleteInqueryOrder -------- inqueryOrderId is null ==== ");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 获取询价单列表
	 * 
	 * @author liukh
	 * @date 2017-2-24 上午9:58:55
	 * @param form
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseTMService#getInqueryOrderList(com.zj.entity.tm.form.InqueryOrderQueryForm)
	 */
	@Override
	public String getInqueryOrderList(InqueryOrderQueryForm form) {
		String jsonStr = "";
		BaseObjDto<ItemPage<InqueryOrder>> dto = new BaseObjDto<ItemPage<InqueryOrder>>();
		try {
			BaseObjDto<ItemPage<InqueryOrder>> pagesDto = baseDao.getPageList(
					InqueryOrderMapper.class, InqueryOrder.class, form,
					"getInqueryOrderPageList");
			if (FrameworkUtils.isSuccess(pagesDto)) {
				dto = pagesDto;
				log.info("getInqueryOrderList success");
			} else {
				FrameworkUtils.setNoData(dto);
				log.error("getInqueryOrderList failure");

			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("getInqueryOrderList Exception!");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 获取询价单及设备列表
	 * 
	 * @author liukh
	 * @date 2017-3-21 下午3:20:45
	 * @param form
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseTMService#getInqueryOrderAndDeviceList(com.zj.entity.tm.form.InqueryOrderQueryForm)
	 */

	@Override
	public String getInqueryOrderListAndDeviceList(InqueryOrderQueryForm form) {
		String jsonStr = "";
		BaseObjDto<ItemPage<InqueryOrderDto>> dto = new BaseObjDto<ItemPage<InqueryOrderDto>>();
		try {
			BaseObjDto<ItemPage<InqueryOrderDto>> pagesDto = baseDao
					.getPageList(InqueryOrderMapper.class,
							InqueryOrderDto.class, form,
							"getInqueryOrderDetailPageList");
			if (FrameworkUtils.isSuccess(pagesDto)) {
				dto = pagesDto;

			} else {
				FrameworkUtils.setNoData(dto);
				log.error("getInqueryOrderListAndDeviceList failure");

			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(
					"getInqueryOrderListAndDeviceList Exception!");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 增加询价单设备
	 * 
	 * @author liukh
	 * @date 2017-2-24 上午10:04:43
	 * @param data
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseTMService#addInqueryDevice(java.lang.String)
	 */
	@Override
	public String addInqueryDevice(String data) {
		String jsonStr = "";
		BaseDto dto = new BaseDto();
		if (StringUtils.isNotBlank(data)) {
			try {
				InqueryDevice inqueryDevice = JSON.parseObject(data,
						InqueryDevice.class);
				if (inqueryDevice == null) {
					dto.setRcode(1);
					dto.setRinfo("The data parseObject to InqueryDevice is null !");
					return JSON.toJSONString(dto);
				}
				JSONObject jp = JSON.parseObject(data);
				if (jp == null) {
					dto.setRcode(1);
					dto.setRinfo("The data parseObject to JSONObject is null !");
					return JSON.toJSONString(dto);
				}

				String inqueryOrderId = jp.getString("inqueryOrderId");
				if (StringUtils.isBlank(inqueryOrderId)) {
					dto.setRcode(1);
					dto.setRinfo("The data's inqueryOrderId is null !");
					return JSON.toJSONString(dto);
				}
				InqueryOrder inqueryOrder = new InqueryOrder();
				inqueryOrder.setId(inqueryOrderId);
				inqueryDevice.setInqueryOrder(inqueryOrder);

				Date createDate = new Date();
				inqueryDevice.setCreateTime(createDate);
				dto = baseDao.insertSelective(InqueryDeviceMapper.class,
						inqueryDevice);
				if (FrameworkUtils.isSuccess(dto)) {
					log.info("addInqueryDevice success!");
				} else {
					log.error("addInqueryDevice failure!");
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("addInqueryDevice exception!");
				throw new RuntimeException("addInqueryDevice Exception!");
			}
		} else {
			log.error("---addInqueryDevice -------- data is null ==== ");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 获取询价单设备
	 * 
	 * @author liukh
	 * @date 2017-2-24 上午10:33:48
	 * @param inqueryDeviceId
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseTMService#getInqueryDevice(java.lang.String)
	 */
	@Override
	public String getInqueryDevice(String inqueryDeviceId) {
		String jsonStr = "";
		BaseObjDto<InqueryDevice> dto = new BaseObjDto<InqueryDevice>();
		try {
			if (StringUtils.isBlank(inqueryDeviceId)) {
				dto.setRinfo("inqueryDeviceId is null");
				return JSON.toJSONString(dto);
			}
			BaseObjDto<InqueryDevice> inqueryDeviceDto = baseDao
					.selectByPrimaryKey(InqueryDeviceMapper.class,
							StringUtils.trim(inqueryDeviceId));
			if (FrameworkUtils.isSuccess(inqueryDeviceDto)) {
				InqueryDevice inqueryDevice = inqueryDeviceDto.getData();
				dto.setData(inqueryDevice);
				FrameworkUtils.setSuccess(dto);
				log.info("getInqueryDevice success!");
			} else {
				FrameworkUtils.setNoData(dto);
				log.error("getInqueryDevice failure");
			}
		} catch (Exception e) {
			log.error("getInqueryDevice exception!");
			e.printStackTrace();
			throw new RuntimeException("getInqueryDevice Exception!");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 修改询价单设备
	 * 
	 * @author liukh
	 * @date 2017-2-24 上午10:38:37
	 * @param inqueryDeviceId
	 * @param data
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseTMService#updateInqueryDevice(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public String updateInqueryDevice(String inqueryDeviceId, String data) {
		String jsonStr = "";
		BaseDto dto = new BaseDto();
		if (StringUtils.isNotBlank(inqueryDeviceId)
				&& StringUtils.isNotBlank(data)) {
			try {
				InqueryDevice inqueryDevice = JSON.parseObject(data,
						InqueryDevice.class);
				if (inqueryDevice == null) {
					dto.setRcode(1);
					dto.setRinfo("The data parseObject to InqueryDevice is null !");
					return JSON.toJSONString(dto);
				}
				inqueryDevice.setId(inqueryDeviceId);
				dto = baseDao.updateByPrimaryKeySelective(
						InqueryDeviceMapper.class, inqueryDevice);
				if (FrameworkUtils.isSuccess(dto)) {
					log.info("updateInqueryDevice success!");
				} else {
					log.error("updateInqueryDevice failure!");
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("updateInqueryDevice exception!");
				throw new RuntimeException("updateInqueryDevice Exception!");
			}

		} else {
			log.error("---updateInqueryDevice -------- data or inqueryDeviceId is null ==== ");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 删除询价单设备
	 * 
	 * @author liukh
	 * @date 2017-2-24 上午10:39:52
	 * @param inqueryDeviceId
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseTMService#deleteInqueryDevice(java.lang.String)
	 */
	@Override
	public String deleteInqueryDevice(String inqueryDeviceId) {
		String jsonStr = "";
		BaseDto dto = new BaseDto();
		if (StringUtils.isNotBlank(inqueryDeviceId)) {
			Map<String, Object> paramsMap = new HashMap<String, Object>();
			paramsMap.put("id", StringUtils.trim(inqueryDeviceId));
			BaseObjDto<InqueryDeviceListDto> deviceDto = baseDao
					.getObjProperty(InqueryDeviceMapper.class,
							"selectInquerDevieDeatilinfo", paramsMap);
			if (!FrameworkUtils.isSuccess(deviceDto)) {
				dto.setRcode(BaseDto.ERROR_RCODE);
				dto.setRinfo("The data's  inqueryDeviceId is not exits !");
				return JSON.toJSONString(dto);
			}
			String inqueryOrderId = null;
			InqueryDeviceListDto queryDevice = deviceDto.getData();
			if(queryDevice.getInqueryOrderId()!= null){
				inqueryOrderId = queryDevice.getInqueryOrderId();
			}
			
			// 删除
			dto = baseDao.deleteByPrimaryKey(InqueryDeviceMapper.class,
					StringUtils.trim(inqueryDeviceId));
			if (FrameworkUtils.isSuccess(dto)) {
				if(StringUtils.isNotBlank(inqueryOrderId)){
					InqueryDeviceQueryForm form = new InqueryDeviceQueryForm();
					form.setInqueryOrderId(inqueryOrderId);
					BaseObjDto<ItemPage<InqueryDeviceAllListDto>> pagesDto = baseDao.getPageList(
							InqueryDeviceMapper.class, InqueryDeviceAllListDto.class, form,
							"getInqueryDevicePageList");
					if(!FrameworkUtils.isSuccess(pagesDto)&& pagesDto.getRcode() == BaseDto.NO_DATA_RCODE ){
						return deleteInqueryOrder(inqueryOrderId);
					}
				}
				log.info("deleteInqueryDevice success!");
			}
		} else {
			log.error("---deleteInqueryDevice -------- inqueryDeviceId is null ==== ");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 获取询价单设备
	 * 
	 * @author liukh
	 * @date 2017-2-24 上午10:43:02
	 * @param form
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseTMService#getInqueryDeviceList(com.zj.entity.tm.form.InqueryDeviceQueryForm)
	 */
	@Override
	public String getInqueryDeviceList(InqueryDeviceQueryForm form) {
		String jsonStr = "";
		BaseObjDto<ItemPage<InqueryDeviceAllListDto>> dto = new BaseObjDto<ItemPage<InqueryDeviceAllListDto>>();
		try {
			BaseObjDto<ItemPage<InqueryDeviceAllListDto>> pagesDto = baseDao.getPageList(
					InqueryDeviceMapper.class, InqueryDeviceAllListDto.class, form,
					"getInqueryDevicePageList");
			if (FrameworkUtils.isSuccess(pagesDto)) {
				dto = pagesDto;
				log.info("getInqueryDeviceList success");
			} else {
				FrameworkUtils.setNoData(dto);
				log.error("getInqueryDeviceList failure");

			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("getInqueryDeviceList Exception!");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 设备详细页面立即下单（设备加入询价单并立即下单） 加入询价单后，立即下单 1、生成询价单 2、添加询价单设备 3、更改询价单 4、生成订单
	 * 
	 * @author liukh
	 * @date 2017-3-21 下午7:33:10
	 * @param data
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseTMService#addFastInqueryOrderAndDevice(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String addFastInqueryOrderAndDevice(String data) {
		String jsonStr = "";
		BaseDto dto = new BaseDto();
		if (StringUtils.isNotBlank(data)) {
			try {
				Map<String, Object> params = (Map<String, Object>) JSON
						.parse(data);
				if (params == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data parse Map is null !");
					return JSON.toJSONString(dto);
				} else if (params.get("leasingSideId") == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's leasingSideId is null !");
					return JSON.toJSONString(dto);
				} else if (params.get("lesseeSideId") == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's lesseeSideId is null !");
					return JSON.toJSONString(dto);
				} else if (params.get("address") == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's address is null !");
					return JSON.toJSONString(dto);
				} else if (params.get("isIncludeInvoice") == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's isIncludeInvoice is null !");
					return JSON.toJSONString(dto);
				} else if (params.get("isIncludeShippingFee") == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's isIncludeShippingFee is null !");
					return JSON.toJSONString(dto);
				} else if (params.get("isInlcudeJiShou") == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's isInlcudeJiShou is null !");
					return JSON.toJSONString(dto);
				} else if (params.get("isInlcudeFuel") == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's isInlcudeFuel is null !");
					return JSON.toJSONString(dto);
				} else if (params.get("isInlcudeInsurance") == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's isInlcudeInsurance is null !");
					return JSON.toJSONString(dto);
				} else if (params.get("payMethod") == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's payMethod is null !");
					return JSON.toJSONString(dto);
				} else if (params.get("deviceModelId") == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's deviceModelId is null !");
					return JSON.toJSONString(dto);
				} else if (params.get("quantity") == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's quantity is null !");
					return JSON.toJSONString(dto);
				} else if (params.get("startTime") == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's startTime is null !");
					return JSON.toJSONString(dto);
				} else if (params.get("creator") == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's creator is null !");
					return JSON.toJSONString(dto);
				} else if (params.get("city") == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's city is null !");
					return JSON.toJSONString(dto);
				} else if (params.get("leaseTerm") == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's leaseTerm is null !");
					return JSON.toJSONString(dto);
				} else if (params.get("billingType") == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's billingType is null !");
					return JSON.toJSONString(dto);
				}
				InqueryOrder inqueryOrder = new InqueryOrder();

				BaseObjDto<Company> companyLeaDto = baseDao.selectByPrimaryKey(
						CompanyMapper.class, StringUtils.trim(params.get(
								"leasingSideId").toString()));
				if (!FrameworkUtils.isSuccess(companyLeaDto)) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's leasingSideId is not exist !");
					return JSON.toJSONString(dto);
				} else {
					inqueryOrder.setLeasingSideId(StringUtils.trim(params.get(
							"leasingSideId").toString()));
					if (companyLeaDto.getData() != null
							&& companyLeaDto.getData().getCompanyName() != null) {
						inqueryOrder.setLeasingSideName(companyLeaDto.getData()
								.getCompanyName());
					}

				}
				BaseObjDto<Company> companyLesDto = baseDao
						.selectByPrimaryKey(CompanyMapper.class, StringUtils
								.trim(params.get("lesseeSideId").toString()));
				if (!FrameworkUtils.isSuccess(companyLesDto)) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's lesseeSideId is not exist !");
					return JSON.toJSONString(dto);
				} else {
					inqueryOrder.setLesseeSideId(StringUtils.trim(params.get(
							"lesseeSideId").toString()));
					if (companyLesDto.getData() != null
							&& companyLesDto.getData().getCompanyName() != null) {
						inqueryOrder.setLesseeSideName(companyLesDto.getData()
								.getCompanyName());
					}

				}

				BaseObjDto<DeviceModel> deviceModelDto = baseDao
						.selectByPrimaryKey(DeviceModelMapper.class,
								StringUtils.trim(params.get("deviceModelId")
										.toString()));
				if (!FrameworkUtils.isSuccess(deviceModelDto)) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's deviceModelId is not exist !");
					return JSON.toJSONString(dto);
				}
				CompanyDeviceType companyDeviceType = baseOtherService
						.getCompanyDeviceTypeByComapnyIdAndModelId(StringUtils
								.trim(params.get("leasingSideId").toString()),
								StringUtils.trim(params.get("deviceModelId")
										.toString()));
				if (companyDeviceType == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The query companyDeviceType by data's lesseeSideId and  deviceModelId  is null !");
					return JSON.toJSONString(dto);
				}

				inqueryOrder.setAddress(params.get("address").toString());
				inqueryOrder.setCommiterId(params.get("creator").toString());
				inqueryOrder.setPayMethod(params.get("payMethod").toString());
				inqueryOrder.setStatus(Constant.INQUERYORDER_STATUS_UNMAKESURE);
				inqueryOrder.setIsIncludeInvoice(Boolean.valueOf(params.get(
						"isIncludeInvoice").toString()));
				inqueryOrder.setIsIncludeShippingFee(Boolean.valueOf(params
						.get("isIncludeShippingFee").toString()));
				inqueryOrder.setIsInlcudeJiShou(Boolean.valueOf(params.get(
						"isInlcudeJiShou").toString()));
				inqueryOrder.setIsInlcudeFuel(Boolean.valueOf(params.get(
						"isInlcudeFuel").toString()));
				inqueryOrder.setIsInlcudeInsurance(Boolean.valueOf(params.get(
						"isInlcudeInsurance").toString()));
				inqueryOrder.setCity(params.get("city").toString());
				if (params.get("additionalRequirement") != null) {
					inqueryOrder.setAdditionalRequirement(params.get(
							"additionalRequirement").toString());
				}
				if (params.get("projectId") != null) {
					inqueryOrder.setProjectId(params.get("projectId")
							.toString());
				}
				inqueryOrder.setCreateTime(new Date());
				InqueryDevice device = new InqueryDevice();
				int quantity = params.get("quantity") != null ? Integer
						.parseInt(params.get("quantity").toString()) : 0;
				String companyDeviceTypeModelQuote = companyDeviceType
						.getModelQuote();
				String leaseTerm = params.get("leaseTerm").toString();
				device.setLeaseTerm(leaseTerm);
				Integer billingType = Integer.parseInt(params
						.get("billingType").toString());
				device.setBillingType(billingType);

				BigDecimal unitPrice = NumberUtils.getMonthOrDayModelQuote(
						billingType, companyDeviceTypeModelQuote);
				BigDecimal totalPrice = NumberUtils.getModelQuotetotalPrice(
						billingType, leaseTerm, companyDeviceTypeModelQuote,
						quantity);
				device.setUnitPrice(unitPrice);
				device.setTotalPrice(totalPrice);
				device.setCreateTime(new Date());
				inqueryOrder.setTotalOffer(totalPrice);

				dto = baseDao.insertSelective(InqueryOrderMapper.class,
						inqueryOrder);
				if (!FrameworkUtils.isSuccess(dto)) {
					return JSON.toJSONString(dto);
				}

				device.setDeviceModelId(StringUtils.trim(params.get(
						"deviceModelId").toString()));
				if (deviceModelDto != null && deviceModelDto.getData() != null) {
					device.setDeviceModelName(deviceModelDto.getData()
							.getModelName());

				}
				String picture = Constant.REQUIRED_PICTURE_TEMP;
				if (companyDeviceType.getPicture() != null) {
					if (companyDeviceType.getPicture().indexOf("|") > -1
							&& companyDeviceType.getPicture().split("\\|") != null
							&& companyDeviceType.getPicture().split("\\|").length > 0) {
						picture = companyDeviceType.getPicture().split("\\|")[0];
					} else {
						picture = companyDeviceType.getPicture();
					}

				}
				device.setPicture(picture);
				device.setQuantity(quantity);
				device.setInqueryOrder(inqueryOrder);
				device.setStartTime(params.get("startTime").toString());

				String endTime = CommonUtils.getEndDate(params.get("startTime")
						.toString(), billingType, Integer.parseInt(leaseTerm));
				device.setEndTime(endTime);
				device.setCreator(params.get("creator").toString());
				dto = baseDao
						.insertSelective(InqueryDeviceMapper.class, device);
				if (!FrameworkUtils.isSuccess(dto)) {
					return JSON.toJSONString(dto);
				}
				List<InqueryDevice> deviceList = new ArrayList<InqueryDevice>();
				deviceList.add(device);
				if (inqueryOrder.getInqueryDevices() == null) {
					inqueryOrder.setInqueryDevices(deviceList);
				} else {
					inqueryOrder.getInqueryDevices().clear();
					inqueryOrder.setInqueryDevices(deviceList);
				}

				jsonStr = addCreateRentOrderUseInqueryOrder(inqueryOrder);

			} catch (Exception e) {
				e.printStackTrace();
				log.error("addFastInqueryOrderAndDevice exception!");
				throw new RuntimeException(
						"addFastInqueryOrderAndDevice Exception!");
			}
		} else {
			dto.setRinfo("The data is null ");
			jsonStr = JSON.toJSONString(dto);
			log.error("---addFastInqueryOrderAndDevice -------- data is null ==== ");
		}

		return jsonStr;
	}

	/**
	 * 询价车详细中立即下单
	 * 
	 * @author liukh
	 * @date 2017-3-22 下午4:11:38
	 * @param inqueryOrderId
	 * @param data
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseTMService#addFastInqueryOrderToRentOrder(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public String addFastInqueryOrderToRentOrder(String inqueryOrderId,
			String data) {
		String jsonStr = "";
		BaseDto dto = new BaseDto();
		if (StringUtils.isNotBlank(data)
				&& StringUtils.isNotBlank(inqueryOrderId)) {
			try {
				InqueryOrder updateInqueryOrder = JSON.parseObject(data,
						InqueryOrder.class);
				if (updateInqueryOrder == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data parse InqueryOrder is null !");
					return JSON.toJSONString(dto);
				} else if (updateInqueryOrder.getAddress() == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's address is null !");
					return JSON.toJSONString(dto);
				} else if (updateInqueryOrder.getIsIncludeInvoice() == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's isIncludeInvoice is null !");
					return JSON.toJSONString(dto);
				} else if (updateInqueryOrder.getIsIncludeShippingFee() == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's isIncludeShippingFee is null !");
					return JSON.toJSONString(dto);
				} else if (updateInqueryOrder.getIsInlcudeJiShou() == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's isInlcudeJiShou is null !");
					return JSON.toJSONString(dto);
				} else if (updateInqueryOrder.getIsInlcudeFuel() == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's isInlcudeFuel is null !");
					return JSON.toJSONString(dto);
				} else if (updateInqueryOrder.getIsInlcudeInsurance() == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's isInlcudeInsurance is null !");
					return JSON.toJSONString(dto);
				} else if (updateInqueryOrder.getPayMethod() == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's payMethod is null !");
					return JSON.toJSONString(dto);
				} else if (updateInqueryOrder.getCity() == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's city is null !");
					return JSON.toJSONString(dto);
				}

				BaseObjDto<InqueryOrder> inqueryOrderDto = baseDao
						.selectByPrimaryKey(InqueryOrderMapper.class,
								StringUtils.trim(inqueryOrderId));
				if (!FrameworkUtils.isSuccess(inqueryOrderDto)) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The inqueryOrder  is not exit !");
					return JSON.toJSONString(dto);
				}

				InqueryOrder inqueryOrder = inqueryOrderDto.getData();
				if (inqueryOrder.getStatus() != null
						&& inqueryOrder.getStatus() >= Constant.INQUERYORDER_STATUS_MAKEORDER) {
					dto.setRcode(21);
					dto.setRinfo("The inqueryOrder of "
							+ inqueryOrder.getId()
							+ " is has been created rentOrder,Please do not submit a duplicate !");
					return JSON.toJSONString(dto);
				}
				if (inqueryOrder.getInqueryDevices() == null
						|| (inqueryOrder.getInqueryDevices() != null && inqueryOrder
								.getInqueryDevices().size() < 1)) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The inqueryOrder's inqueryDevice is null !");
					return JSON.toJSONString(dto);
				}
				updateInqueryOrder
						.setStatus(Constant.INQUERYORDER_STATUS_MAKEORDER);
				updateInqueryOrder.setId(StringUtils.trim(inqueryOrderId));
				dto = baseDao.updateByPrimaryKeySelective(
						InqueryOrderMapper.class, updateInqueryOrder);
				if (!FrameworkUtils.isSuccess(dto)) {
					log.info("The addFastInqueryOrderToRentOrder error when do updateInqueryOrder:"
							+ JSON.toJSONString(dto));
					return JSON.toJSONString(dto);
				}

				inqueryOrder.setAddress(updateInqueryOrder.getAddress());
				inqueryOrder.setIsIncludeInvoice(updateInqueryOrder
						.getIsIncludeInvoice());
				inqueryOrder.setIsIncludeShippingFee(updateInqueryOrder
						.getIsIncludeShippingFee());
				inqueryOrder.setIsInlcudeFuel(updateInqueryOrder
						.getIsInlcudeFuel());
				inqueryOrder.setIsInlcudeJiShou(updateInqueryOrder
						.getIsInlcudeJiShou());
				inqueryOrder.setIsInlcudeInsurance(updateInqueryOrder
						.getIsInlcudeInsurance());
				inqueryOrder.setPayMethod(updateInqueryOrder.getPayMethod());
				inqueryOrder.setCity(updateInqueryOrder.getCity());
				inqueryOrder.setAdditionalRequirement(updateInqueryOrder
						.getAdditionalRequirement());

				jsonStr = addCreateRentOrderUseInqueryOrder(inqueryOrder);

			} catch (Exception e) {
				e.printStackTrace();
				log.error("addFastInqueryOrderToRentOrder exception!");
				throw new RuntimeException(
						"addFastInqueryOrderToRentOrder Exception!");
			}
		} else {
			dto.setRinfo("The data is null ");
			jsonStr = JSON.toJSONString(dto);
			log.error("---addFastInqueryOrderToRentOrder -------- data is null ==== ");
		}

		return jsonStr;
	}

	/**
	 * 提交询价 0：草稿 1：待回复 2：待确认 3：确认订单（结束）---- 承租方确认下单 4：关闭询价（结束）---- 承租方结束询价
	 * 
	 * @author liukh
	 * @date 2017-3-22 下午5:13:01
	 * @param inqueryOrderId
	 * @param data
	 * @return
	 */
	@Override
	public String updateCommitInqueryOrder(String inqueryOrderId, String data) {
		String jsonStr = "";
		BaseDto dto = new BaseDto();
		if (StringUtils.isNotBlank(inqueryOrderId)
				&& StringUtils.isNotBlank(data)) {
			try {
				InqueryOrder inqueryOrder = JSON.parseObject(data,
						InqueryOrder.class);
				if (inqueryOrder == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data parseObject to InqueryOrder is null !");
					return JSON.toJSONString(dto);
				} else if (inqueryOrder.getStatus() == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's status is null !");
					return JSON.toJSONString(dto);
				}
				// 结束询价的时候不做提交询价的过多条件判断交
				if (inqueryOrder.getStatus() != Constant.INQUERYORDER_STATUS_OVER) {
					if (inqueryOrder.getAddress() == null) {
						dto.setRcode(BaseDto.ERROR_RCODE);
						dto.setRinfo("The data's address is null !");
						return JSON.toJSONString(dto);
					} else if (inqueryOrder.getPayMethod() == null) {
						dto.setRcode(BaseDto.ERROR_RCODE);
						dto.setRinfo("The data's payMethod is null !");
						return JSON.toJSONString(dto);
					} else if (inqueryOrder.getCity() == null) {
						dto.setRcode(BaseDto.ERROR_RCODE);
						dto.setRinfo("The data's city is null !");
						return JSON.toJSONString(dto);
					}
				}

				BaseObjDto<InqueryOrder> queryDto = baseDao.selectByPrimaryKey(
						InqueryOrderMapper.class,
						StringUtils.trim(inqueryOrderId));
				if (!FrameworkUtils.isSuccess(queryDto)
						|| queryDto.getData() == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The  inqueryOrderId is not exit !");
					return JSON.toJSONString(dto);
				}
				InqueryOrder queryInqueryOrder = queryDto.getData();
				if (inqueryOrder != null
						&& inqueryOrder.getStatus() != null
						&& inqueryOrder.getStatus() == Constant.INQUERYORDER_STATUS_REPLY) {
					// 当前处理人
					// 承租方提交询价 --------租赁方-------租赁方待报价
					UserDto user = baseOtherService
							.getUserDtoByComapnyId(queryInqueryOrder
									.getLeasingSideId());
					if (user != null) {
						inqueryOrder.setCurrentHandler(user.getId());
					}
				}

				inqueryOrder.setId(StringUtils.trim(inqueryOrderId));

				dto = baseDao.updateByPrimaryKeySelective(
						InqueryOrderMapper.class, inqueryOrder);
				if (FrameworkUtils.isSuccess(dto)) {
					log.info("updateCommitInqueryOrder success!");
				} else {
					log.error("updateCommitInqueryOrder failure!");
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("updateCommitInqueryOrder exception!");
				throw new RuntimeException(
						"updateCommitInqueryOrder Exception!");
			}

		} else {
			log.error("---updateCommitInqueryOrder -------- data or inqueryOrderId is null ==== ");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 修改报价
	 * 
	 * @author liukh
	 * @date 2017-3-22 下午4:57:06
	 * @param inqueryOrderId
	 * @param data
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseTMService#updateEditQuotoInqueryOrder(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public String updateInqueryOrderQuote(String inqueryOrderId, String data) {
		String jsonStr = "";
		BaseDto dto = new BaseDto();
		if (StringUtils.isNotBlank(inqueryOrderId)
				&& StringUtils.isNotBlank(data)) {
			try {
				InqueryOrder updateInqueryOrder = JSON.parseObject(data,
						InqueryOrder.class);
				if (updateInqueryOrder == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data parseObject to InqueryOrder is null !");
					return JSON.toJSONString(dto);
				}
				BaseObjDto<InqueryOrder> queryDto = baseDao.selectByPrimaryKey(
						InqueryOrderMapper.class,
						StringUtils.trim(inqueryOrderId));
				if (!FrameworkUtils.isSuccess(queryDto)
						|| queryDto.getData() == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The  inqueryOrderId is not exit !");
					return JSON.toJSONString(dto);
				}
				InqueryOrder queryInqueryOrder = queryDto.getData();
				if (queryInqueryOrder != null) {
					BigDecimal totalPrice = BigDecimal.ZERO;
					BigDecimal otherExpesne = queryInqueryOrder
							.getOtherExpense() != null ? queryInqueryOrder
							.getOtherExpense() : BigDecimal.ZERO;
					// 把处理人设为承租方
					// 当前处理人
					// 租赁方修改报价提交--------承租方-------待下单（询价反馈）
					UserDto user = baseOtherService
							.getUserDtoByComapnyId(queryInqueryOrder
									.getLesseeSideId());
					if (user != null) {
						updateInqueryOrder.setCurrentHandler(user.getId());
					}
					if (queryInqueryOrder.getInqueryDevices() != null
							&& queryInqueryOrder.getInqueryDevices().size() > 0) {
						List<InqueryDevice> deviceList = queryInqueryOrder
								.getInqueryDevices();
						for (int index = 0; index < deviceList.size(); index++) {
							InqueryDevice inqueryDevice = deviceList.get(index);
							totalPrice = totalPrice.add(inqueryDevice
									.getTotalPrice() != null ? inqueryDevice
									.getTotalPrice() : BigDecimal.ZERO);
						}
					}
					if (updateInqueryOrder.getOtherExpense() != null) {
						totalPrice = totalPrice.add(updateInqueryOrder
								.getOtherExpense());
					} else {
						totalPrice = totalPrice.add(otherExpesne);
					}
					updateInqueryOrder.setTotalOffer(totalPrice);
				}

				updateInqueryOrder.setId(StringUtils.trim(inqueryOrderId));
				dto = baseDao.updateByPrimaryKeySelective(
						InqueryOrderMapper.class, updateInqueryOrder);
				if (FrameworkUtils.isSuccess(dto)) {
					log.info("updateInqueryOrderQuote success!");
				} else {
					log.error("updateInqueryOrderQuote failure!");
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("updateInqueryOrderQuote exception!");
				throw new RuntimeException("updateInqueryOrderQuote Exception!");
			}

		} else {
			log.error("---updateInqueryOrderQuote -------- data or inqueryOrderId is null ==== ");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 保存询价单的报价
	 * 
	 * @author liukh
	 * @date 2017-3-22 下午5:39:45
	 * @param inqueryOrderId
	 * @param data
	 *            { "otherExpense":"", "otherExpenseComment":"",
	 *            "isIncludeShippingFee":"", "isIncludeInvoice":"",
	 *            "isInlcudeJiShou":"", "isInlcudeFuel":"",
	 *            "isInlcudeInsurance":"", "status": 0, "payMethod": "1",
	 *            "data":[ {"inqueryDeviceId":"","unitPrice":""},
	 *            {"inqueryDeviceId":"","unitPrice":""},
	 *            {"inqueryDeviceId":"","unitPrice":""} ]
	 * 
	 *            }
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseTMService#updateSaveQuotoInqueryOrder(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public String updateSaveQuotoInqueryOrder(String inqueryOrderId, String data) {
		String jsonStr = "";
		BaseDto dto = new BaseDto();
		if (StringUtils.isNotBlank(inqueryOrderId)
				&& StringUtils.isNotBlank(data)) {
			try {
				InqueryDeviceUpdatePreview inqueryDeviceUpdatePreview = JSON
						.parseObject(data, InqueryDeviceUpdatePreview.class);
				if (inqueryDeviceUpdatePreview == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data parseObject to InqueryDeviceUpdatePreview is null !");
					return JSON.toJSONString(dto);
				} else if (inqueryDeviceUpdatePreview.getIsIncludeShippingFee() == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's  isIncludeShippingFee is null !");
					return JSON.toJSONString(dto);
				} else if (inqueryDeviceUpdatePreview.getIsIncludeInvoice() == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's  isIncludeIndtoice is null !");
					return JSON.toJSONString(dto);
				} else if (inqueryDeviceUpdatePreview.getIsInlcudeJiShou() == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's  isInlcudeJiShou is null !");
					return JSON.toJSONString(dto);
				} else if (inqueryDeviceUpdatePreview.getIsInlcudeFuel() == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's  isInlcudeFuel is null !");
					return JSON.toJSONString(dto);
				} else if (inqueryDeviceUpdatePreview.getIsInlcudeInsurance() == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's  isInlcudeInsurance is null !");
					return JSON.toJSONString(dto);
				} else if (inqueryDeviceUpdatePreview.getPayMethod() == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's  payMethod is null !");
					return JSON.toJSONString(dto);
				} else if (inqueryDeviceUpdatePreview.getStatus() == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's  status is null !");
					return JSON.toJSONString(dto);
				}
				if (inqueryDeviceUpdatePreview.getData() == null
						|| (inqueryDeviceUpdatePreview.getData() != null && inqueryDeviceUpdatePreview
								.getData().size() == 0)) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's  inqueryDevice is null !");
					return JSON.toJSONString(dto);
				}

				BaseObjDto<InqueryOrder> queryDto = baseDao.selectByPrimaryKey(
						InqueryOrderMapper.class,
						StringUtils.trim(inqueryOrderId));
				if (!FrameworkUtils.isSuccess(queryDto)
						|| queryDto.getData() == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The  inqueryOrderId is not exit !");
					return JSON.toJSONString(dto);
				}
				InqueryOrder queryInqueryOrder = queryDto.getData();
				if (queryInqueryOrder != null) {
					Map<String, String> checkDeviceMap = new HashMap<String, String>();
					if (queryInqueryOrder.getInqueryDevices() != null
							&& queryInqueryOrder.getInqueryDevices().size() > 0) {
						List<InqueryDevice> deviceList = queryInqueryOrder
								.getInqueryDevices();
						for (int index = 0; index < deviceList.size(); index++) {
							InqueryDevice inqueryDevice = deviceList.get(index);
							checkDeviceMap.put(inqueryDevice.getId(),
									inqueryDevice.getId());
						}
					}

					// ///////////////////////////////////////////////////////////
					BigDecimal toatalInqueryOrderPrice = BigDecimal.ZERO;
					for (int index = 0; index < inqueryDeviceUpdatePreview
							.getData().size(); index++) {
						InqueryDeviceUpdatePreview.DeviceUpdatePrice deviceUpdatePrice = inqueryDeviceUpdatePreview
								.getData().get(index);
						if (StringUtils.isBlank(deviceUpdatePrice
								.getInqueryDeviceId())) {
							dto.setRcode(BaseDto.ERROR_RCODE);
							dto.setRinfo("The data's  inqueryDevice's  inqueryDeviceId is null !");
							return JSON.toJSONString(dto);
						} else if (deviceUpdatePrice.getUnitPrice() == null) {
							dto.setRcode(BaseDto.ERROR_RCODE);
							dto.setRinfo("The data's  inqueryDevice's  unitPrice is null !");
							return JSON.toJSONString(dto);
						} else if (!checkDeviceMap
								.containsKey(deviceUpdatePrice
										.getInqueryDeviceId())) {
							dto.setRcode(BaseDto.ERROR_RCODE);
							dto.setRinfo("The data's  inqueryDevice's  inqueryDeviceId not in InqueryDeviceList !");
							return JSON.toJSONString(dto);
						}

					}

					for (int index = 0; index < inqueryDeviceUpdatePreview
							.getData().size(); index++) {
						InqueryDeviceUpdatePreview.DeviceUpdatePrice deviceUpdatePrice = inqueryDeviceUpdatePreview
								.getData().get(index);
						BigDecimal deviceTotalprice = calculateDeviceTotalPriceByChangeUnitPrice(
								deviceUpdatePrice.getInqueryDeviceId(),
								deviceUpdatePrice.getUnitPrice());
						toatalInqueryOrderPrice = toatalInqueryOrderPrice
								.add(deviceTotalprice);
						InqueryDevice updateInDevice = new InqueryDevice();
						updateInDevice.setUnitPrice(deviceUpdatePrice
								.getUnitPrice());
						updateInDevice.setTotalPrice(deviceTotalprice);
						updateInDevice.setId(deviceUpdatePrice
								.getInqueryDeviceId());
						dto = baseDao.updateByPrimaryKeySelective(
								InqueryDeviceMapper.class, updateInDevice);

						if (!FrameworkUtils.isSuccess(dto)) {
							return JSON.toJSONString(dto);
						}
					}
					InqueryOrder updateInqueryOrder = new InqueryOrder();
					updateInqueryOrder
							.setIsIncludeShippingFee(inqueryDeviceUpdatePreview
									.getIsIncludeShippingFee());
					updateInqueryOrder
							.setIsIncludeInvoice(inqueryDeviceUpdatePreview
									.getIsIncludeInvoice());
					updateInqueryOrder
							.setIsInlcudeFuel(inqueryDeviceUpdatePreview
									.getIsInlcudeFuel());
					updateInqueryOrder
							.setIsInlcudeInsurance(inqueryDeviceUpdatePreview
									.getIsInlcudeInsurance());
					updateInqueryOrder
							.setIsInlcudeJiShou(inqueryDeviceUpdatePreview
									.getIsInlcudeJiShou());

					if (inqueryDeviceUpdatePreview.getOtherExpense() != null) {
						toatalInqueryOrderPrice = toatalInqueryOrderPrice
								.add(inqueryDeviceUpdatePreview
										.getOtherExpense());
						updateInqueryOrder
								.setOtherExpense(inqueryDeviceUpdatePreview
										.getOtherExpense());

					} else {
						updateInqueryOrder.setOtherExpense(BigDecimal.ZERO);
					}
					if (inqueryDeviceUpdatePreview.getOtherExpenseComment() != null) {
						updateInqueryOrder
								.setOtherExpenseComment(inqueryDeviceUpdatePreview
										.getOtherExpenseComment());
					}

					// 把处理人设为承租方
					// 当前处理人
					// 租赁方修改报价提交--------承租方-------待下单（询价反馈）
					UserDto user = baseOtherService
							.getUserDtoByComapnyId(queryInqueryOrder
									.getLesseeSideId());
					if (user != null) {
						updateInqueryOrder.setCurrentHandler(user.getId());
					}
					updateInqueryOrder.setStatus(inqueryDeviceUpdatePreview
							.getStatus());
					updateInqueryOrder.setTotalOffer(toatalInqueryOrderPrice);
					updateInqueryOrder.setId(StringUtils.trim(inqueryOrderId));
					dto = baseDao.updateByPrimaryKeySelective(
							InqueryOrderMapper.class, updateInqueryOrder);
				}

				if (FrameworkUtils.isSuccess(dto)) {
					log.info("updateSaveQuotoInqueryOrder success!");
				} else {
					log.error("updateSaveQuotoInqueryOrder failure!");
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("updateSaveQuotoInqueryOrder exception!");
				throw new RuntimeException(
						"updateSaveQuotoInqueryOrder Exception!");
			}

		} else {
			log.error("---updateSaveQuotoInqueryOrder -------- data or inqueryOrderId is null ==== ");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	@Override
	public String getInqueryOrderAndDeviceContainDateList(String inqueryOrderId) {
		String jsonStr = "";
		BaseObjDto<InqueryOrderDto> dto = new BaseObjDto<InqueryOrderDto>();
		try {
			if (StringUtils.isBlank(inqueryOrderId)) {
				dto.setRinfo("inqueryOrderId is null");
				return JSON.toJSONString(dto);
			}
			BaseObjDto<InqueryOrder> inqueryOrderDto = baseDao
					.selectByPrimaryKey(InqueryOrderMapper.class,
							StringUtils.trim(inqueryOrderId));
			if (FrameworkUtils.isSuccess(inqueryOrderDto)) {
				List<InqueryDeviceListDto> inqueryDeviceListDtoList = new ArrayList<InqueryDeviceListDto>();
				InqueryOrder queryInqueryOrder = inqueryOrderDto.getData();
				InqueryOrderDto inqueryOrder = new InqueryOrderDto();
				inqueryOrder.copyFrom(queryInqueryOrder);

				InqueryDeviceQueryForm form = new InqueryDeviceQueryForm();
				form.setInqueryOrderId(StringUtils.trim(inqueryOrderId));
				BaseObjDto<List<InqueryDeviceListDto>> deviceDto = baseDao
						.getList(InqueryDeviceMapper.class,
								InqueryDeviceListDto.class,
								"getInqueryDeviceMoreInforList", form);
				if (FrameworkUtils.isSuccess(deviceDto)) {
					List<InqueryDeviceListDto> deviceDtoList = deviceDto
							.getData();
					for (int index = 0; index < deviceDtoList.size(); index++) {
						InqueryDeviceListDto inqueryDeviceListDto = deviceDtoList
								.get(index);
						DateTypeDto dateType = new DateTypeDto();
						if (inqueryDeviceListDto.getBillingType() != null
								&& inqueryDeviceListDto.getLeaseTerm() != null) {
							dateType.setType(inqueryDeviceListDto
									.getBillingType().toString());
							if (inqueryDeviceListDto.getBillingType() == Constant.BILLINGTYPE_MONTH) {
								dateType.setMonth(new BigDecimal(
										inqueryDeviceListDto.getLeaseTerm()));
								dateType.setDay(BigDecimal.ZERO);
							} else if (inqueryDeviceListDto.getBillingType() == Constant.BILLINGTYPE_DAY) {
								dateType.setDay(new BigDecimal(
										inqueryDeviceListDto.getLeaseTerm()));
								dateType.setMonth(BigDecimal.ZERO);

							}
							dateType.setDateAmount(BigDecimal.ZERO);

						} else {
							dateType.setType(Constant.BILLINGTYPE_MONTH
									.toString());
							dateType.setMonth(BigDecimal.ZERO);
							dateType.setDay(BigDecimal.ZERO);
							dateType.setDateAmount(BigDecimal.ZERO);
						}
						inqueryDeviceListDto.setDateType(dateType);
						inqueryDeviceListDtoList.add(inqueryDeviceListDto);

					}
				}
				if (inqueryDeviceListDtoList != null
						&& inqueryDeviceListDtoList.size() > 0) {
					inqueryOrder
							.setInqueryDevicesProcess(inqueryDeviceListDtoList);
				}

				dto.setData(inqueryOrder);
				dto.setRcode(BaseDto.SUCCESS_RCODE);
				dto.setRinfo(BaseDto.DEFAULT_SUCCESS_INFO);
				log.info("getInqueryOrder success!");
			} else {
				FrameworkUtils.setNoData(dto);
				log.error("getInqueryOrder failure");
			}
		} catch (Exception e) {
			log.error("getInqueryOrder exception!");
			e.printStackTrace();
			throw new RuntimeException("getInqueryOrder Exception!");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 询价设备加入询价车的时候，如果询价车中已经有此类型的设备，则更改询价设备的数量和金额，如果不存在，则新增
	 * 
	 * @author liukh
	 * @date 2017-3-23 上午9:56:07
	 * @param inqueryOrderId
	 * @param data
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseTMService#addOrUpdateWhenHaveInqueryDevice(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public String addOrUpdateWhenHaveInqueryDevice(String leasingSideId,
			String data) {
		String jsonStr = "";
		BaseDto dto = new BaseDto();
		if (StringUtils.isNotBlank(data)
				&& StringUtils.isNotBlank(leasingSideId)) {
			try {

				InqueryDevice updateDevice = JSON.parseObject(data,
						InqueryDevice.class);
				if (updateDevice == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data parseObject to InqueryDevice is null !");
					return JSON.toJSONString(dto);
				} else if (updateDevice.getStartTime() == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's startTime  is null");
					return JSON.toJSONString(dto);
				} else if (updateDevice.getLeaseTerm() == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's leaseTerm is null !");
					return JSON.toJSONString(dto);
				} else if (updateDevice.getBillingType() == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's billingType is null !");
					return JSON.toJSONString(dto);
				} else if (updateDevice.getDeviceModelId() == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's deviceModelId is null !");
					return JSON.toJSONString(dto);
				} else if (updateDevice.getQuantity() == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's quantity is null !");
					return JSON.toJSONString(dto);
				}

				JSONObject jp = JSON.parseObject(data);
				if (jp == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data parseObject to JSONObject is null !");
					return JSON.toJSONString(dto);
				}

				String inqueryOrderId = jp.getString("inqueryOrderId");
				if (StringUtils.isBlank(inqueryOrderId)) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's inqueryOrderId is null !");
					return JSON.toJSONString(dto);
				}
				BaseObjDto<InqueryOrder> inqueryOrderDto = baseDao
						.selectByPrimaryKey(InqueryOrderMapper.class,
								StringUtils.trim(inqueryOrderId));
				if (!FrameworkUtils.isSuccess(inqueryOrderDto)) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's inqueryOrderId is not exit !");
					return JSON.toJSONString(dto);
				}

				BaseObjDto<Company> companyDto = baseDao.selectByPrimaryKey(
						CompanyMapper.class, StringUtils.trim(leasingSideId));
				if (!FrameworkUtils.isSuccess(companyDto)) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's leasingSideId is not exit !");
					return JSON.toJSONString(dto);
				}

				InqueryOrder inqueryOrder = inqueryOrderDto.getData();

				boolean isUpdateSucces = false;
				BigDecimal orderTotalPrice = BigDecimal.ZERO;
				if (inqueryOrder.getInqueryDevices() != null
						&& inqueryOrder.getInqueryDevices().size() > 0) {
					List<InqueryDevice> queryDeviceList = inqueryOrder
							.getInqueryDevices();
					InqueryDevice queryDevice = null;
					for (int index = 0; index < queryDeviceList.size(); index++) {
						queryDevice = queryDeviceList.get(index);
						if (!isUpdateSucces
								&& queryDevice != null
								&& queryDevice.getId() != null
								&& queryDevice.getDeviceModelId().equals(
										updateDevice.getDeviceModelId())
								&& queryDevice.getStartTime().equals(
										updateDevice.getStartTime())
								&& queryDevice.getBillingType() == updateDevice
										.getBillingType()
								&& queryDevice.getLeaseTerm().equals(
										updateDevice.getLeaseTerm())) {
							Integer quantity = queryDevice.getQuantity()
									+ updateDevice.getQuantity();
							BigDecimal quantityBigDecimal = new BigDecimal(
									quantity);
							BigDecimal totalPrice = NumberUtils
									.getDeviceTotalPrice(queryDevice
											.getLeaseTerm(), queryDevice
											.getUnitPrice(), Integer
											.parseInt(quantityBigDecimal
													.toString()));
							updateDevice.setQuantity(quantity);
							updateDevice.setTotalPrice(totalPrice);
							updateDevice.setId(queryDevice.getId());
							isUpdateSucces = true;
							orderTotalPrice = orderTotalPrice.add(totalPrice);
							dto = baseDao.updateByPrimaryKeySelective(
									InqueryDeviceMapper.class, updateDevice);
							if (!FrameworkUtils.isSuccess(dto)) {
								log.info("addOrUpdateWhenHaveInqueryDevice when update  InqueryDevice error !");
								return JSON.toJSONString(dto);
							}
						} else {
							orderTotalPrice = orderTotalPrice.add(queryDevice
									.getTotalPrice() != null ? queryDevice
									.getTotalPrice() : BigDecimal.ZERO);
						}

					}
				}
				if (!isUpdateSucces) {
					// 新增询价设备
					CompanyDeviceType companyDeviceType = baseOtherService
							.getCompanyDeviceTypeByComapnyIdAndModelId(
									leasingSideId,
									updateDevice.getDeviceModelId());
					if (companyDeviceType == null) {
						dto.setRcode(BaseDto.ERROR_RCODE);
						dto.setRinfo("get CompanyDeviceType by deviceModelId and companyId is null !");
						return JSON.toJSONString(dto);
					}
					int quantity = updateDevice.getQuantity() != null ? updateDevice
							.getQuantity() : 0;

					String companyDeviceTypeModelQuote = companyDeviceType
							.getModelQuote();
					BigDecimal unitPrice = NumberUtils.getMonthOrDayModelQuote(
							updateDevice.getBillingType(),
							companyDeviceTypeModelQuote);
					BigDecimal totalPrice = NumberUtils.getDeviceTotalPrice(
							updateDevice.getLeaseTerm(), unitPrice, quantity);
					updateDevice.setUnitPrice(unitPrice);
					updateDevice.setDeviceModelName(companyDeviceType
							.getDeviceModelName());
					updateDevice.setTotalPrice(totalPrice);
					orderTotalPrice = orderTotalPrice.add(totalPrice);
					String leaseTerm = updateDevice.getLeaseTerm();
					String endTime = CommonUtils.getEndDate(
							updateDevice.getStartTime(),
							updateDevice.getBillingType(),
							Integer.parseInt(leaseTerm));
					updateDevice.setEndTime(endTime);
					String picture = Constant.REQUIRED_PICTURE_TEMP;
					if (companyDeviceType.getPicture() != null) {
						if (companyDeviceType.getPicture().indexOf("|") > -1
								&& companyDeviceType.getPicture().split("\\|") != null
								&& companyDeviceType.getPicture().split("\\|").length > 0) {
							picture = companyDeviceType.getPicture().split(
									"\\|")[0];
						} else {
							picture = companyDeviceType.getPicture();
						}

					}
					updateDevice.setPicture(picture);
					updateDevice.setCreateTime(new Date());
					InqueryOrder assInqueryOrder = new InqueryOrder();
					assInqueryOrder.setId(StringUtils.trim(inqueryOrderId));
					updateDevice.setInqueryOrder(assInqueryOrder);
					dto = baseDao.insertSelective(InqueryDeviceMapper.class,
							updateDevice);
					if (!FrameworkUtils.isSuccess(dto)) {
						log.info("addOrUpdateWhenHaveInqueryDevice when add  InqueryDevice error !");
						return JSON.toJSONString(dto);
					}
				}

				InqueryOrder updateOrder = new InqueryOrder();
				updateOrder.setId(StringUtils.trim(inqueryOrderId));
				updateOrder.setTotalOffer(orderTotalPrice);
				dto = baseDao.updateByPrimaryKeySelective(
						InqueryOrderMapper.class, updateOrder);
				if (FrameworkUtils.isSuccess(dto)) {
					log.info("addInqueryDevice success!");
				} else {
					log.error("addInqueryDevice failure!");
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("addInqueryDevice exception!");
				throw new RuntimeException("addInqueryDevice Exception!");
			}
		} else {
			log.error("---addInqueryDevice -------- data or leasingSideId is null ==== ");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 修改询价设备数量
	 * 
	 * @author liukh
	 * @date 2017-3-23 下午2:25:15
	 * @param inqueryDeviceId
	 * @param data
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseTMService#updateInqueryDevice4Quantity(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public String updateInqueryDevice4Quantity(String inqueryDeviceId,
			String data) {
		String jsonStr = "";
		BaseDto dto = new BaseDto();
		if (StringUtils.isNotBlank(inqueryDeviceId)
				&& StringUtils.isNotBlank(data)) {
			try {
				InqueryDevice inqueryDevice = JSON.parseObject(data,
						InqueryDevice.class);
				if (inqueryDevice == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data parseObject to InqueryDevice is null !");
					return JSON.toJSONString(dto);
				} else if (inqueryDevice.getQuantity() == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's quantity  is null !");
					return JSON.toJSONString(dto);
				}
				Map<String, Object> paramsMap = new HashMap<String, Object>();
				paramsMap.put("id", StringUtils.trim(inqueryDeviceId));
				BaseObjDto<InqueryDeviceListDto> deviceDto = baseDao
						.getObjProperty(InqueryDeviceMapper.class,
								"selectInquerDevieDeatilinfo", paramsMap);
				if (!FrameworkUtils.isSuccess(deviceDto)) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's  inqueryDeviceId is not exits !");
					return JSON.toJSONString(dto);
				}
				InqueryDeviceListDto queryDevice = deviceDto.getData();
				if (queryDevice.getInqueryOrderId() != null) {
					Integer quantity = inqueryDevice.getQuantity();
					BigDecimal quantityBigDecimal = new BigDecimal(quantity);
					BigDecimal totalPrice = NumberUtils.getDeviceTotalPrice(
							queryDevice.getLeaseTerm(),
							queryDevice.getUnitPrice(),
							Integer.parseInt(quantityBigDecimal.toString()));
					BaseObjDto<InqueryOrder> queryOrderDto = baseDao
							.selectByPrimaryKey(InqueryOrderMapper.class,
									queryDevice.getInqueryOrderId());
					if (!FrameworkUtils.isSuccess(queryOrderDto)) {
						dto.setRcode(BaseDto.ERROR_RCODE);
						dto.setRinfo("The data's  inqueryDevice's inqueryOrderId is not exits !");
						return JSON.toJSONString(dto);
					}
					InqueryOrder queryOrder = queryOrderDto.getData();
					BigDecimal orderTotalPrice = queryOrder.getTotalOffer();
					orderTotalPrice = orderTotalPrice.subtract(queryDevice
							.getTotalPrice());
					orderTotalPrice = orderTotalPrice.add(totalPrice);
					InqueryDevice updateDevice = new InqueryDevice();
					updateDevice.setId(StringUtils.trim(inqueryDeviceId));
					updateDevice.setQuantity(inqueryDevice.getQuantity());
					updateDevice.setTotalPrice(totalPrice);
					dto = baseDao.updateByPrimaryKeySelective(
							InqueryDeviceMapper.class, updateDevice);
					if (FrameworkUtils.isSuccess(dto)) {
						InqueryOrder updateOrder = new InqueryOrder();
						updateOrder.setId(queryOrder.getId());
						updateOrder.setTotalOffer(orderTotalPrice);
						dto = baseDao.updateByPrimaryKeySelective(
								InqueryOrderMapper.class, updateOrder);
					}

				}
				if (FrameworkUtils.isSuccess(dto)) {
					log.info("updateInqueryDevice4Quantity success!");
				} else {
					log.error("updateInqueryDevice4Quantity failure!");
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("updateInqueryDevice4Quantity exception!");
				throw new RuntimeException(
						"updateInqueryDevice4Quantity Exception!");
			}

		} else {
			log.error("---updateInqueryDevice4Quantity -------- data or inqueryDeviceId is null ==== ");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 修改询价车设备单价
	 * 
	 * @author liukh
	 * @date 2017-3-23 下午2:26:18
	 * @param inqueryDeviceId
	 * @param data
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseTMService#updateInqueryDevice4UnitPrice(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public String updateInqueryDevice4UnitPrice(String inqueryDeviceId,
			String data) {
		String jsonStr = "";
		BaseDto dto = new BaseDto();
		if (StringUtils.isNotBlank(inqueryDeviceId)
				&& StringUtils.isNotBlank(data)) {
			try {
				InqueryDevice inqueryDevice = JSON.parseObject(data,
						InqueryDevice.class);
				if (inqueryDevice == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data parseObject to InqueryDevice is null !");
					return JSON.toJSONString(dto);
				} else if (inqueryDevice.getUnitPrice() == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's unitPrice  is null !");
					return JSON.toJSONString(dto);
				}
				Map<String, Object> paramsMap = new HashMap<String, Object>();
				paramsMap.put("id", StringUtils.trim(inqueryDeviceId));
				BaseObjDto<InqueryDeviceListDto> deviceDto = baseDao
						.getObjProperty(InqueryDeviceMapper.class,
								"selectInquerDevieDeatilinfo", paramsMap);
				if (!FrameworkUtils.isSuccess(deviceDto)) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's  inqueryDeviceId is not exits !");
					return JSON.toJSONString(dto);
				}
				InqueryDeviceListDto queryDevice = deviceDto.getData();
				if (queryDevice.getInqueryOrderId() != null) {
					BigDecimal unitPrice = inqueryDevice.getUnitPrice();
					BigDecimal totalPrice = NumberUtils.getDeviceTotalPrice(
							queryDevice.getLeaseTerm(), unitPrice,
							queryDevice.getQuantity());
					BaseObjDto<InqueryOrder> queryOrderDto = baseDao
							.selectByPrimaryKey(InqueryOrderMapper.class,
									queryDevice.getInqueryOrderId());
					if (!FrameworkUtils.isSuccess(queryOrderDto)) {
						dto.setRcode(BaseDto.ERROR_RCODE);
						dto.setRinfo("The data's  inqueryDevice's inqueryOrderId is not exits !");
						return JSON.toJSONString(dto);
					}
					InqueryOrder queryOrder = queryOrderDto.getData();
					BigDecimal orderTotalPrice = queryOrder.getTotalOffer();
					orderTotalPrice = orderTotalPrice.subtract(queryDevice
							.getTotalPrice());
					orderTotalPrice = orderTotalPrice.add(totalPrice);

					InqueryDevice updateDevice = new InqueryDevice();
					updateDevice.setId(StringUtils.trim(inqueryDeviceId));
					updateDevice.setUnitPrice(inqueryDevice.getUnitPrice());
					updateDevice.setTotalPrice(totalPrice);
					dto = baseDao.updateByPrimaryKeySelective(
							InqueryDeviceMapper.class, inqueryDevice);
					if (FrameworkUtils.isSuccess(dto)) {
						InqueryOrder updateOrder = new InqueryOrder();
						updateOrder.setId(queryOrder.getId());
						updateOrder.setTotalOffer(orderTotalPrice);
						dto = baseDao.updateByPrimaryKeySelective(
								InqueryOrderMapper.class, updateOrder);
					}

					if (FrameworkUtils.isSuccess(dto)) {
						log.info("updateInqueryDevice4UnitPrice success!");
						JSONObject jsobRecord = new JSONObject();
						jsobRecord.put("deviceTotalPrice", totalPrice);
						BigDecimal previousOtherExpense = BigDecimal.ZERO;
						previousOtherExpense = previousOtherExpense
								.add(queryOrder.getOtherExpense() != null ? queryOrder
										.getOtherExpense() : BigDecimal.ZERO);
						jsobRecord.put("previousOtherExpense",
								previousOtherExpense);
						jsobRecord.put("totalPriceNoOtherExpense",
								orderTotalPrice.subtract(previousOtherExpense));
						jsobRecord.put("totalPrice", orderTotalPrice);
						BaseObjDto<JSONObject> returnTotalDto = new BaseObjDto<JSONObject>();
						returnTotalDto.setRcode(BaseDto.SUCCESS_RCODE);
						returnTotalDto.setRinfo(BaseDto.DEFAULT_SUCCESS_INFO);
						returnTotalDto.setData(jsobRecord);
						dto = returnTotalDto;

					} else {
						log.error("updateInqueryDevice4UnitPrice failure!");
						return JSON.toJSONString(dto);
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
				log.error("updateInqueryDevice4UnitPrice exception!");
				throw new RuntimeException(
						"updateInqueryDevice4UnitPrice Exception!");
			}

		} else {
			dto.setRcode(BaseDto.ERROR_RCODE);
			dto.setRinfo("The data or inqueryDeviceId is null!");
			log.error("---updateInqueryDevice4UnitPrice -------- data or inqueryDeviceId is null ==== ");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 修改询价车设备单价预览
	 * 
	 * @author liukh
	 * @date 2017-3-23 下午2:37:00
	 * @param data
	 *            ：{ "data":[ {"inqueryDeviceId":"XXXXXX","unitPrice":"1000"},
	 *            {"inqueryDeviceId":"XXXXXX","unitPrice":"1000"},
	 *            {"inqueryDeviceId":"XXXXXX","unitPrice":"1000"} ] }
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseTMService#updateInqueryDevice4UnitPricePreview(java.lang.String)
	 */
	@Override
	public String updateInqueryDevice4UnitPricePreview(String data) {
		String jsonStr = null;
		BaseObjDto<Object> dto = new BaseObjDto<Object>();

		try {
			if (StringUtils.isBlank(data)) {
				dto.setRcode(BaseDto.ERROR_RCODE);
				dto.setRinfo("The data is null !");
				return JSON.toJSONString(dto);
			}
			JSONObject jsob = JSON.parseObject(data);
			if (jsob == null) {
				dto.setRcode(BaseDto.ERROR_RCODE);
				dto.setRinfo("The data parse Object is error !");
				return JSON.toJSONString(dto);
			} else if (jsob.get("data") == null) {
				dto.setRcode(BaseDto.ERROR_RCODE);
				dto.setRinfo("The dataValue get data is null !");
				return JSON.toJSONString(dto);
			}

			List<InqueryDeviceUpdatePreview.DeviceUpdatePrice> list = JSON
					.parseArray(JSON.toJSONString(jsob.get("data")),
							InqueryDeviceUpdatePreview.DeviceUpdatePrice.class);
			for (int index = 0; index < list.size(); index++) {
				InqueryDeviceUpdatePreview.DeviceUpdatePrice deviceUpdatePrice = list
						.get(index);
				if (StringUtils.isBlank(deviceUpdatePrice.getInqueryDeviceId())) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The deviceUpdatePrice's inqueryDeviceId  is null !");
					return JSON.toJSONString(dto);
				} else if (deviceUpdatePrice.getUnitPrice() == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The deviceUpdatePrice's unitPrice  is null !");
					return JSON.toJSONString(dto);
				}

			}

			BigDecimal totalPriceNoOtherExpense = BigDecimal.ZERO;
			for (int index = 0; index < list.size(); index++) {
				InqueryDeviceUpdatePreview.DeviceUpdatePrice deviceUpdatePrice = list
						.get(index);
				if (deviceUpdatePrice.getInqueryDeviceId() != null
						&& deviceUpdatePrice.getUnitPrice() != null) {
					totalPriceNoOtherExpense = totalPriceNoOtherExpense
							.add(calculateDeviceTotalPriceByChangeUnitPrice(
									deviceUpdatePrice.getInqueryDeviceId(),
									deviceUpdatePrice.getUnitPrice()));
				}
				JSONObject returnJsb = new JSONObject();
				returnJsb.put("totalPriceNoOtherExpense",
						totalPriceNoOtherExpense);
				dto.setRcode(0);
				dto.setRinfo("请求成功！");
				dto.setData(returnJsb);
				jsonStr = JSON.toJSONString(dto);
			}

		} catch (Exception e) {
			log.error("updateInqueryDevice4UnitPricePreview  ---- 异常,message = "
					+ e.getMessage());
			throw new RuntimeException(
					"updateInqueryDevice4UnitPricePreview Exception!");
		}
		log.info("updateInqueryDevice4UnitPricePreview ---- jsonStr ========= "
				+ jsonStr);
		return jsonStr;
	}

	/**
	 * 根据公司设备型号，数量，单价，工期，工期类型计算设备价格
	 * 
	 * @author liukh
	 * @date 2017-3-23 下午2:55:38
	 * @param data
	 *            {companyId,deviceModelId,quantity,billingType,leaseTerm}
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseTMService#calculatePriceUseDeviceModel(java.lang.String)
	 */
	@Override
	public String calculatePriceUseDeviceModel(String data) {
		String jsonStr = null;
		BaseObjDto<Object> dto = new BaseObjDto<Object>();

		try {
			if (StringUtils.isBlank(data)) {
				dto.setRcode(BaseDto.ERROR_RCODE);
				dto.setRinfo("The data is null !");
				return JSON.toJSONString(dto);
			}
			Map<String, Object> dataParams = (Map<String, Object>) JSON
					.parseObject(data);
			if (dataParams == null) {
				dto.setRcode(BaseDto.ERROR_RCODE);
				dto.setRinfo("The data parse to Map  is null !");
				return JSON.toJSONString(dto);
			} else if (dataParams.get("companyId") == null) {
				dto.setRcode(BaseDto.ERROR_RCODE);
				dto.setRinfo("The data's companyId is null !");
				return JSON.toJSONString(dto);
			} else if (dataParams.get("deviceModelId") == null) {
				dto.setRcode(BaseDto.ERROR_RCODE);
				dto.setRinfo("The data's deviceModelId is null !");
				return JSON.toJSONString(dto);
			} else if (dataParams.get("quantity") == null) {
				dto.setRcode(BaseDto.ERROR_RCODE);
				dto.setRinfo("The data's quantity is null !");
				return JSON.toJSONString(dto);
			} else if (dataParams.get("billingType") == null) {
				dto.setRcode(BaseDto.ERROR_RCODE);
				dto.setRinfo("The data's billingType is null !");
				return JSON.toJSONString(dto);
			} else if (dataParams.get("leaseTerm") == null) {
				dto.setRcode(BaseDto.ERROR_RCODE);
				dto.setRinfo("The data's leaseTerm is null !");
				return JSON.toJSONString(dto);
			}

			CompanyDeviceType companyDevieType = baseOtherService
					.getCompanyDeviceTypeByComapnyIdAndModelId(
							dataParams.get("companyId").toString(), dataParams
									.get("deviceModelId").toString());
			if (companyDevieType == null) {
				dto.setRcode(BaseDto.ERROR_RCODE);
				dto.setRinfo("The Query CompanyDeviceType by  companyId and deviceModelId  is not exits !");
				return JSON.toJSONString(dto);
			}
			BigDecimal totalPrice = NumberUtils.getModelQuotetotalPrice(
					Integer.parseInt(dataParams.get("billingType").toString()),
					dataParams.get("leaseTerm").toString(),
					companyDevieType.getModelQuote(),
					Integer.parseInt(dataParams.get("quantity").toString()));

			JSONObject jsObreturn = new JSONObject();
			jsObreturn.put("totalPrice", totalPrice);
			dto.setRcode(BaseDto.SUCCESS_RCODE);
			dto.setRinfo("请求成功!");
			dto.setData(jsObreturn);
			jsonStr = JSON.toJSONString(dto);
		} catch (Exception e) {
			log.error("calculatePriceUseDeviceModel  ---- 异常,message = "
					+ e.getMessage());
			throw new RuntimeException(
					"calculatePriceUseDeviceModel Exception!");
		}
		log.info("calculatePriceUseDeviceModel ---- jsonStr ========= "
				+ jsonStr);
		return jsonStr;
	}

	/**
	 * 获取询价车中草稿状态下的数量
	 * 
	 * @author liukh
	 * @date 2017-3-23 下午3:51:29
	 * @param form
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseTMService#getInqueryOrderCarCount(com.zj.entity.tm.form.InqueryOrderQueryForm)
	 */
	@Override
	public String getInqueryOrderCarCount(InqueryOrderQueryForm form) {
		BaseObjDto<Object> dto = new BaseObjDto<Object>();
		try {
			if (form == null) {
				dto.setRcode(BaseDto.ERROR_RCODE);
				dto.setRinfo("The request params  is null !");
				return JSON.toJSONString(dto);
			} else if (form.getLesseeSideId() == null) {
				dto.setRcode(BaseDto.ERROR_RCODE);
				dto.setRinfo("The request params's lesseeSideId  is null !");
				return JSON.toJSONString(dto);
			}
			InqueryOrderQueryForm queryForm = new InqueryOrderQueryForm();
			queryForm.setLesseeSideId(form.getLesseeSideId());
			queryForm.setStatus(Constant.INQUERYORDER_STATUS_CREATE.toString());
			Long count = baseDao.getCount(InqueryOrderMapper.class,
					"getInqueryOrderCount", queryForm);
			JSONObject jsObreturn = new JSONObject();
			jsObreturn.put("count", count);
			dto.setRcode(BaseDto.SUCCESS_RCODE);
			dto.setRinfo("请求成功!");
			dto.setData(jsObreturn);
		} catch (Exception e) {
			log.error("getInqueryOrderCarCount  ---- 异常,message = "
					+ e.getMessage());
			throw new RuntimeException("getInqueryOrderCarCount Exception!");
		}
		return JSON.toJSONString(dto);
	}

	/**
	 * 删除询价设备及更新询价车总价
	 * 
	 * @author liukh
	 * @date 2017-3-23 下午2:52:11
	 * @param inqueryDeviceId
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseTMService#deleteInqueryDeviceAndUpdateInqueryOrderTotalPrice(java.lang.String)
	 */
	@Override
	public String deleteInqueryDeviceAndUpdateInqueryOrderTotalPrice(
			String inqueryDeviceId) {
		BaseDto dto = new BaseDto();

		try {
			if (StringUtils.isBlank(inqueryDeviceId)) {
				dto.setRcode(BaseDto.ERROR_RCODE);
				dto.setRinfo("The inquryDeviceId is null !");
				return JSON.toJSONString(dto);
			}
			boolean isDeleteInquerOrder = false;
			Map<String, Object> paramsMap = new HashMap<String, Object>();
			paramsMap.put("id", StringUtils.trim(inqueryDeviceId));
			BaseObjDto<InqueryDeviceListDto> deviceDto = baseDao
					.getObjProperty(InqueryDeviceMapper.class,
							"selectInquerDevieDeatilinfo", paramsMap);
			if (!FrameworkUtils.isSuccess(deviceDto)) {
				dto.setRcode(BaseDto.ERROR_RCODE);
				dto.setRinfo("The inqueryDeviceId is not exits !");
				return JSON.toJSONString(dto);
			}
			InqueryDeviceListDto queryDevice = deviceDto.getData();
			if (queryDevice.getInqueryOrderId() != null) {
				BigDecimal devieTotalPrice = queryDevice.getTotalPrice();
				BaseObjDto<InqueryOrder> queryOrderDto = baseDao
						.selectByPrimaryKey(InqueryOrderMapper.class,
								queryDevice.getInqueryOrderId());
				if (!FrameworkUtils.isSuccess(queryOrderDto)) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's  inqueryDevice's inqueryOrderId is not exits !");
					return JSON.toJSONString(dto);
				}
				InqueryOrder queryOrder = queryOrderDto.getData();
				if (queryOrder.getInqueryDevices() != null
						&& queryOrder.getInqueryDevices().size() == 1) {
					InqueryDevice inqueryDevice = queryOrder
							.getInqueryDevices().get(0);
					if (inqueryDevice.getId().equals(
							StringUtils.trim(inqueryDeviceId))) {
						isDeleteInquerOrder = true;
					}
				}
				dto = baseDao.deleteByPrimaryKey(InqueryDeviceMapper.class,
						StringUtils.trim(inqueryDeviceId));
				if (FrameworkUtils.isSuccess(dto)) {
					if (isDeleteInquerOrder) {
						dto = baseDao.deleteByPrimaryKey(
								InqueryOrderMapper.class,
								queryDevice.getInqueryOrderId());
					} else {
						BigDecimal orderTotalPrice = queryOrder.getTotalOffer();
						orderTotalPrice = orderTotalPrice
								.subtract(devieTotalPrice);
						InqueryOrder updateOrder = new InqueryOrder();
						updateOrder.setId(queryOrder.getId());
						updateOrder.setTotalOffer(orderTotalPrice);
						dto = baseDao.updateByPrimaryKeySelective(
								InqueryOrderMapper.class, updateOrder);
					}
				}

			}
		} catch (Exception e) {

			log.error("deleteInqueryDeviceAndUpdateInqueryOrderTotalPrice ---- 异常,message = "
					+ e.getMessage());
			e.printStackTrace();
		}
		return JSON.toJSONString(dto);
	}

	/**
	 * 计算询价设备的总价
	 * 
	 * @author liukh
	 * @date 2017-3-22 下午6:03:07
	 * @param inqueryDeviceId
	 * @param unitPrice
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseTMService#calculateDeviceTotalPriceByChangeUnitPrice(java.lang.String,
	 *      java.math.BigDecimal)
	 */
	@Override
	public BigDecimal calculateDeviceTotalPriceByChangeUnitPrice(
			String inqueryDeviceId, BigDecimal unitPrice) {
		BigDecimal deviceTotalprice = BigDecimal.ZERO;

		try {
			Map<String, Object> queryInqueryDeviceParams = new HashMap<String, Object>();
			queryInqueryDeviceParams.put("inqueryDeviceId", inqueryDeviceId);

			BaseObjDto<InqueryDevice> dto = baseDao.selectByPrimaryKey(
					InqueryDeviceMapper.class,
					StringUtils.trim(inqueryDeviceId));
			if (!FrameworkUtils.isSuccess(dto)) {
				return deviceTotalprice;
			}
			InqueryDevice inqueryDevice = dto.getData();

			if (inqueryDevice != null && inqueryDevice.getQuantity() == null) {
				return deviceTotalprice;
			}
			deviceTotalprice = NumberUtils.getDeviceTotalPrice(
					inqueryDevice.getLeaseTerm(), unitPrice,
					inqueryDevice.getQuantity());
		} catch (Exception e) {
			log.error("calculateDeviceTotalPriceByChangeUnitPrice ---- 异常,message = "
					+ e.getMessage());
			e.printStackTrace();
		}
		return deviceTotalprice;
	}

	/**
	 * 询价单生成订单
	 * 
	 * @author liukh
	 * @date 2017-3-23 下午5:19:13
	 * @param inqueryOrder
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseTMService#addCreateRentOrderUseInqueryOrder(com.zj.entity.base.po.InqueryOrder)
	 */
	@Override
	public String addCreateRentOrderUseInqueryOrder(InqueryOrder inqueryOrder) {

		String jsonStr = "";
		BaseObjDto<Object> dto = new BaseObjDto<Object>();

		try {

			if (inqueryOrder == null) {
				dto.setRcode(BaseDto.ERROR_RCODE);
				dto.setRinfo("Query InqueryOrder by inqueryOrderId is null !");
				return JSON.toJSONString(dto);
			}
			if (inqueryOrder.getLesseeSideId() == null) {
				dto.setRcode(BaseDto.ERROR_RCODE);
				dto.setRinfo("The inqueryOrdder's lesseeSideId is null !");
				return JSON.toJSONString(dto);
			}

			if (inqueryOrder.getStatus() != null
					&& inqueryOrder.getStatus() >= Constant.INQUERYORDER_STATUS_MAKEORDER) {
				dto.setRcode(BaseDto.ERROR_RCODE);
				dto.setRinfo("The inqueryOrder has been created to  rentOrder,Please do not submit a duplicate !");
				return JSON.toJSONString(dto);
			}
			if (inqueryOrder.getInqueryDevices() == null
					|| (inqueryOrder.getInqueryDevices() != null && inqueryOrder
							.getInqueryDevices().size() < 1)) {
				dto.setRcode(BaseDto.ERROR_RCODE);
				dto.setRinfo("The inqueryOrder's inqueryDevice is null !");
				return JSON.toJSONString(dto);
			}

			// 更改询价单状态
			InqueryOrder updateOrder = new InqueryOrder();
			updateOrder.setId(inqueryOrder.getId());
			updateOrder.setStatus(Constant.INQUERYORDER_STATUS_MAKEORDER);
			BaseDto updateDto = baseDao.updateByPrimaryKeySelective(
					InqueryOrderMapper.class, updateOrder);
			if (!FrameworkUtils.isSuccess(updateDto)) {
				return JSON.toJSONString(updateDto);
			}

			boolean isUpdateCapitalPool = false;
			BigDecimal lockedBigDecimal = BigDecimal.ZERO;
			boolean isPayMethodOnline = false;
			if (Constant.PAYMETHOD_ONLINE.equals(inqueryOrder.getPayMethod())) {
				isPayMethodOnline = true;
			}

			if (isPayMethodOnline && inqueryOrder.getInqueryDevices() != null
					&& inqueryOrder.getInqueryDevices().size() > 0) {
				// 循环计算锁定金额
				List<InqueryDevice> inqueryDeviceList = inqueryOrder
						.getInqueryDevices();
				for (int index = 0; index < inqueryDeviceList.size(); index++) {
					InqueryDevice inqueryDevice = inqueryDeviceList.get(index);
					BigDecimal unitLockedPrice = NumberUtils.getLockedPrice(
							inqueryDevice.getBillingType(),
							inqueryDevice.getLeaseTerm(),
							inqueryDevice.getTotalPrice());
					lockedBigDecimal = lockedBigDecimal.add(unitLockedPrice);
				}

				dto = baseDao.selectByPrimaryKey(CompanyMapper.class,
						inqueryOrder.getLesseeSideId());
				Company lesseeCompany = (Company) dto.getData();
				// 判断资金是否充足
				if (NumberUtils.checkCompanyMoneyIsEnough(
						lesseeCompany.getDisposableAmount(), lockedBigDecimal)) {
					isUpdateCapitalPool = true;

				} else {
					dto.setRcode(55);
					dto.setRinfo("预付款，余额不足,将锁定：" + lockedBigDecimal + ",请充值！");
					return JSON.toJSONString(dto);
				}

			}

			RentOrder rentOrder = new RentOrder();
			rentOrder.copyFromInqueryOrder(inqueryOrder);
			rentOrder.setStatus(Constant.RENTORDER_STATUS_UNGO);
			rentOrder.setCreateTime(new Date());
			BaseDto baseDto = baseDao.insertSelective(RentOrderMapper.class,
					rentOrder);
			if (!FrameworkUtils.isSuccess(baseDto)) {
				return JSON.toJSONString(baseDto);
			}
			// 订单进度
			OrderProgressTrace orderProgressTrace = new OrderProgressTrace();
			orderProgressTrace.setOrderId(rentOrder.getId());
			orderProgressTrace.setCreateTime(new Date());
			orderProgressTrace.setStatus(Constant.IRENTORDER_STATUS_CREATE);
			orderProgressTrace
					.setHandlerId(inqueryOrder.getCurrentHandler() != null ? inqueryOrder
							.getCurrentHandler() : null);
			BaseDto baseProTraceDto = baseDao.insertSelective(
					OrderProgressTraceMapper.class, orderProgressTrace);
			if (!FrameworkUtils.isSuccess(baseProTraceDto)) {
				return JSON.toJSONString(baseProTraceDto);
			}
			// 新增资金池
			if (isUpdateCapitalPool && isPayMethodOnline) {
				// 新增订单资金池的时候，锁定公司的金额，已经做了处理
				OrderCapitalPool orderCapitalPool = new OrderCapitalPool();
				orderCapitalPool.copyFromRentOrder(rentOrder,
						inqueryOrder.getLesseeSideId(),
						Constant.ORDERCAPITALPOOLPOJO_LOCKED);
				orderCapitalPool.setAmount(lockedBigDecimal);
				jsonStr = addOrderCapitalPool(JSON
						.toJSONString(orderCapitalPool));
				if (!FrameworkUtils.isSuccess(jsonStr)) {
					log.info("addCreateRentOrder4InqueryOrder error when  do addOrderCapitalPool :"
							+ jsonStr);
					return jsonStr;
				}

			}
			// 从询价单设备明细中copy到租赁订单设备明细
			if (inqueryOrder.getInqueryDevices() != null
					&& inqueryOrder.getInqueryDevices().size() > 0) {

				List<InqueryDevice> inqueryDeviceList = inqueryOrder
						.getInqueryDevices();
				BaseDto deviceDto = null;
				for (int index = 0; index < inqueryDeviceList.size(); index++) {
					InqueryDevice inqueryDevice = inqueryDeviceList.get(index);
					RentOrderDevice rentOrderDevice = new RentOrderDevice();
					rentOrderDevice.setRentOrder(rentOrder);
					rentOrderDevice.copyFromInqueryDevice(inqueryDevice);
					rentOrderDevice.setCreateTime(new Date());
					deviceDto = baseDao.insertSelective(
							RentOrderDeviceMapper.class, rentOrderDevice);
					if (!FrameworkUtils.isSuccess(deviceDto)) {
						log.info("addCreateRentOrder4InqueryOrder error when do addRentOrderDevice :"
								+ JSON.toJSONString(deviceDto));
						return JSON.toJSONString(deviceDto);
					}

				}
			}
			OrderProgressTrace orderProgressTraceAgain = new OrderProgressTrace();
			orderProgressTraceAgain.setOrderId(rentOrder.getId());
			orderProgressTraceAgain.setCreateTime(new Date());
			orderProgressTraceAgain.setStatus(Constant.RENTORDER_STATUS_UNGO);
			orderProgressTraceAgain.setHandlerId(inqueryOrder
					.getCurrentHandler() != null ? inqueryOrder
					.getCurrentHandler() : null);
			BaseDto baseProTraceAgainDto = baseDao.insertSelective(
					OrderProgressTraceMapper.class, orderProgressTraceAgain);
			if (!FrameworkUtils.isSuccess(baseProTraceAgainDto)) {
				log.info("addCreateRentOrder4InqueryOrder error when do addOrderProgressTrace :"
						+ JSON.toJSONString(baseProTraceAgainDto));
				return JSON.toJSONString(baseProTraceAgainDto);
			}
			// 发送短信通知及内部消息
			UserDto leasidUser = baseOtherService
					.getUserDtoByComapnyId(inqueryOrder.getLeasingSideId());
			UserDto lessidUser = baseOtherService
					.getUserDtoByComapnyId(inqueryOrder.getLesseeSideId());
			if (leasidUser != null && leasidUser.getCellPhone() != null
					&& lessidUser != null && lessidUser.getCellPhone() != null) {

				StringBuffer leasidSbf = new StringBuffer();
				leasidSbf.append(inqueryOrder.getLeasingSideName());
				leasidSbf.append(":");
				leasidSbf.append(leasidUser.getCellPhone());

				StringBuffer lessidSbf = new StringBuffer();
				lessidSbf.append(inqueryOrder.getLesseeSideName());
				lessidSbf.append(":");
				lessidSbf.append(lessidUser.getCellPhone());

				StringBuffer lockedlessidSbf = new StringBuffer();
				lockedlessidSbf.append(lockedBigDecimal);
				/*
				 * if (isPayMethodOnline) {
				 * baseSMService.sendOrderMessageOnlineLeasingSideSms(
				 * leasidUser.getCellPhone(), lessidSbf.toString(),
				 * lockedlessidSbf.toString());
				 * baseSMService.sendOrderMessageOnlineLesseeSideSms(
				 * lessidUser.getCellPhone(), leasidSbf.toString()); } else {
				 * baseSMService.sendOrderMessageOfflineSms(
				 * lessidUser.getCellPhone(), leasidSbf.toString());
				 * baseSMService.sendOrderMessageOfflineSms(
				 * leasidUser.getCellPhone(), lessidSbf.toString()); }
				 */
				// 内部消息
				String leasidSmsTemplateInfo = "";
				String lessidSmsTemplateInfo = "";
				if (isPayMethodOnline) {
					leasidSmsTemplateInfo = SmsTemplateUtils
							.getOrderMessageOnlineLeasingSideTemplateInfo();
					if (StringUtils.isNotBlank(leasidSmsTemplateInfo)) {
						leasidSmsTemplateInfo = leasidSmsTemplateInfo.replace(
								"{1}", lessidSbf.toString());
						leasidSmsTemplateInfo = leasidSmsTemplateInfo.replace(
								"{2}", lockedlessidSbf.toString());
					}
					lessidSmsTemplateInfo = SmsTemplateUtils
							.getOrderMessageOnlineLesseeSideTemplateInfo();
					if (StringUtils.isNotBlank(lessidSmsTemplateInfo)) {
						lessidSmsTemplateInfo = lessidSmsTemplateInfo.replace(
								"{1}", leasidSbf.toString());
					}
				} else {
					leasidSmsTemplateInfo = SmsTemplateUtils
							.getOrderMessageOfflineTemplateInfo();
					if (StringUtils.isNotBlank(leasidSmsTemplateInfo)) {
						leasidSmsTemplateInfo = leasidSmsTemplateInfo.replace(
								"{1}", lessidSbf.toString());
					}
					lessidSmsTemplateInfo = SmsTemplateUtils
							.getOrderMessageOfflineTemplateInfo();
					if (StringUtils.isNotBlank(lessidSmsTemplateInfo)) {
						lessidSmsTemplateInfo = lessidSmsTemplateInfo.replace(
								"{1}", leasidSbf.toString());
					}

				}

				JSONObject jsobleasidAdd = new JSONObject();
				jsobleasidAdd.put("type",
						Constant.MESSAGE_TYPE_RENTORDER_INQUERYORDER);
				jsobleasidAdd.put("userId", leasidUser.getId());
				jsobleasidAdd.put("externalRelatedId", rentOrder.getId());
				jsobleasidAdd.put("operatorId", lessidUser.getId());
				jsobleasidAdd.put("content", leasidSmsTemplateInfo);
				jsobleasidAdd.put("status", Constant.MESSAGE_STATUS_UNREAD);
				jsonStr = baseSMService.addMessage(JSON
						.toJSONString(jsobleasidAdd));
				if (!FrameworkUtils.isSuccess(jsonStr)) {
					log.info("addCreateRentOrder4InqueryOrder error when  do addMessage :"
							+ jsonStr);
					return jsonStr;
				}

				JSONObject jsoblessidAdd = new JSONObject();
				jsoblessidAdd.put("type",
						Constant.MESSAGE_TYPE_RENTORDER_INQUERYORDER);
				jsoblessidAdd.put("userId", lessidUser.getId());
				jsoblessidAdd.put("externalRelatedId", rentOrder.getId());
				jsoblessidAdd.put("operatorId", leasidUser.getId());
				jsoblessidAdd.put("content", lessidSmsTemplateInfo);
				jsoblessidAdd.put("status", Constant.MESSAGE_STATUS_UNREAD);
				jsonStr = baseSMService.addMessage(JSON
						.toJSONString(jsoblessidAdd));
				if (!FrameworkUtils.isSuccess(jsonStr)) {
					log.info("addCreateRentOrder4InqueryOrder error when  do addMessage :"
							+ jsonStr);
					return jsonStr;
				}

			}

			FrameworkUtils.setSuccess(dto);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("addCreateRentOrder4InqueryOrder exception!");
			throw new RuntimeException(
					"addCreateRentOrder4InqueryOrder Exception!");
		}

		jsonStr = JSON.toJSONString(dto);
		return jsonStr;

	}

	/**
	 * 询价单马上下单
	 * 
	 * @author liukh
	 * @date 2017-3-22 上午10:26:52
	 * @param data
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseTMService#addCreateRentOrder4InqueryOrder(java.lang.String)
	 */
	@Override
	public String addCreateRentOrder4InqueryOrder(String data) {
		String jsonStr = "";
		BaseObjDto<Object> dto = new BaseObjDto<Object>();
		if (StringUtils.isNotBlank(data)) {
			try {
				Map<String, Object> inqueryOrderIdParams = (Map<String, Object>) JSON
						.parseObject(data);
				if (inqueryOrderIdParams == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data parse to Map  is null !");
					return JSON.toJSONString(dto);
				} else if (inqueryOrderIdParams.get("inqueryOrderId") == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's  inqueryOrderId is null !");
					return JSON.toJSONString(dto);
				}

				dto = baseDao.selectByPrimaryKey(
						InqueryOrderMapper.class,
						StringUtils.trim(inqueryOrderIdParams.get(
								"inqueryOrderId").toString()));

				if (!FrameworkUtils.isSuccess(dto)) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's  inqueryOrderId  not exits !");
					return JSON.toJSONString(dto);
				}

				InqueryOrder inqueryOrder = (InqueryOrder) dto.getData();

				if (inqueryOrder == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("Query InqueryOrder by inqueryOrderId is null !");
					return JSON.toJSONString(dto);
				}
				if (inqueryOrder.getLesseeSideId() == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The inqueryOrdder's lesseeSideId is null !");
					return JSON.toJSONString(dto);
				}

				if (inqueryOrder.getStatus() != null
						&& inqueryOrder.getStatus() >= Constant.INQUERYORDER_STATUS_MAKEORDER) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The inqueryOrder has been created to  rentOrder,Please do not submit a duplicate !");
					return JSON.toJSONString(dto);
				}
				return addCreateRentOrderUseInqueryOrder(inqueryOrder);

			} catch (Exception e) {
				e.printStackTrace();
				log.error("addCreateRentOrder4InqueryOrder exception!");
				throw new RuntimeException(
						"addCreateRentOrder4InqueryOrder Exception!");
			}
		} else {
			dto.setRcode(BaseDto.ERROR_RCODE);
			dto.setRinfo("The data  is null !");
			log.error("---addCreateRentOrder4InqueryOrder -------- data is null ==== ");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/*
	 * public String addCreateRentOrder4InqueryOrder(String data) { String
	 * jsonStr = ""; BaseObjDto<Object> dto = new BaseObjDto<Object>(); if
	 * (StringUtils.isNotBlank(data)) { try { Map<String, Object>
	 * inqueryOrderIdParams = (Map<String, Object>) JSON .parseObject(data); if
	 * (inqueryOrderIdParams == null) { dto.setRcode(BaseDto.ERROR_RCODE);
	 * dto.setRinfo("The data parse to Map  is null !"); return
	 * JSON.toJSONString(dto); } else if
	 * (inqueryOrderIdParams.get("inqueryOrderId") == null) {
	 * dto.setRcode(BaseDto.ERROR_RCODE);
	 * dto.setRinfo("The data's  inqueryOrderId is null !"); return
	 * JSON.toJSONString(dto); }
	 * 
	 * dto = baseDao.selectByPrimaryKey( InqueryOrderMapper.class,
	 * StringUtils.trim(inqueryOrderIdParams.get(
	 * "inqueryOrderId").toString()));
	 * 
	 * if (!FrameworkUtils.isSuccess(dto)) { dto.setRcode(BaseDto.ERROR_RCODE);
	 * dto.setRinfo("The data's  inqueryOrderId  not exits !"); return
	 * JSON.toJSONString(dto); }
	 * 
	 * InqueryOrder inqueryOrder = (InqueryOrder) dto.getData();
	 * 
	 * if (inqueryOrder == null) { dto.setRcode(BaseDto.ERROR_RCODE);
	 * dto.setRinfo("Query InqueryOrder by inqueryOrderId is null !"); return
	 * JSON.toJSONString(dto); } if (inqueryOrder.getLesseeSideId() == null) {
	 * dto.setRcode(BaseDto.ERROR_RCODE);
	 * dto.setRinfo("The inqueryOrdder's lesseeSideId is null !"); return
	 * JSON.toJSONString(dto); }
	 * 
	 * if (inqueryOrder.getStatus() != null && inqueryOrder.getStatus() >=
	 * Constant.INQUERYORDER_STATUS_MAKEORDER) {
	 * dto.setRcode(BaseDto.ERROR_RCODE); dto.setRinfo(
	 * "The inqueryOrder has been created to  rentOrder,Please do not submit a duplicate !"
	 * ); return JSON.toJSONString(dto); }
	 * 
	 * boolean isUpdateCapitalPool = false; BigDecimal lockedBigDecimal =
	 * BigDecimal.ZERO; boolean isPayMethodOnline = false; if
	 * (Constant.PAYMETHOD_ONLINE.equals(inqueryOrder .getPayMethod())) {
	 * isPayMethodOnline = true; }
	 * 
	 * if (isPayMethodOnline && inqueryOrder.getInqueryDevices() != null &&
	 * inqueryOrder.getInqueryDevices().size() > 0) { // 循环计算锁定金额
	 * List<InqueryDevice> inqueryDeviceList = inqueryOrder
	 * .getInqueryDevices(); for (int index = 0; index <
	 * inqueryDeviceList.size(); index++) { InqueryDevice inqueryDevice =
	 * inqueryDeviceList .get(index); BigDecimal unitLockedPrice = NumberUtils
	 * .getLockedPrice(inqueryDevice.getBillingType(),
	 * inqueryDevice.getLeaseTerm(), inqueryDevice.getTotalPrice());
	 * lockedBigDecimal = lockedBigDecimal .add(unitLockedPrice); }
	 * 
	 * dto = baseDao.selectByPrimaryKey(CompanyMapper.class,
	 * inqueryOrder.getLesseeSideId()); Company lesseeCompany = (Company)
	 * dto.getData(); // 判断资金是否充足 if (NumberUtils.checkCompanyMoneyIsEnough(
	 * lesseeCompany.getDisposableAmount(), lockedBigDecimal)) {
	 * isUpdateCapitalPool = true;
	 * 
	 * } else { dto.setRcode(55); dto.setRinfo("预付款，余额不足,将锁定：" +
	 * lockedBigDecimal + ",请充值！"); return JSON.toJSONString(dto); }
	 * 
	 * }
	 * 
	 * RentOrder rentOrder = new RentOrder();
	 * rentOrder.copyFromInqueryOrder(inqueryOrder);
	 * rentOrder.setStatus(Constant.RENTORDER_STATUS_UNGO);
	 * rentOrder.setCreateTime(new Date()); BaseDto baseDto =
	 * baseDao.insertSelective( RentOrderMapper.class, rentOrder); if
	 * (!FrameworkUtils.isSuccess(baseDto)) { return JSON.toJSONString(baseDto);
	 * } // 订单进度 OrderProgressTrace orderProgressTrace = new
	 * OrderProgressTrace(); orderProgressTrace.setCreateTime(new Date());
	 * orderProgressTrace.setStatus(Constant.IRENTORDER_STATUS_CREATE);
	 * orderProgressTrace.setHandlerId(inqueryOrder .getCurrentHandler() != null
	 * ? inqueryOrder .getCurrentHandler() : null); BaseDto baseProTraceDto =
	 * baseDao.insertSelective( OrderProgressTraceMapper.class,
	 * orderProgressTrace); if (!FrameworkUtils.isSuccess(baseProTraceDto)) {
	 * return JSON.toJSONString(baseProTraceDto); } // 新增资金池 if
	 * (isUpdateCapitalPool && isPayMethodOnline) { // 新增订单资金池的时候，锁定公司的金额，已经做了处理
	 * OrderCapitalPool orderCapitalPool = new OrderCapitalPool();
	 * orderCapitalPool.copyFromRentOrder(rentOrder,
	 * inqueryOrder.getLesseeSideId(), Constant.ORDERCAPITALPOOLPOJO_LOCKED);
	 * orderCapitalPool.setAmount(lockedBigDecimal); jsonStr =
	 * addOrderCapitalPool(JSON .toJSONString(orderCapitalPool)); if
	 * (!FrameworkUtils.isSuccess(jsonStr)) { log.info(
	 * "addCreateRentOrder4InqueryOrder error when  do addOrderCapitalPool :" +
	 * jsonStr); return jsonStr; }
	 * 
	 * } // 从询价单设备明细中copy到租赁订单设备明细 if (inqueryOrder.getInqueryDevices() != null
	 * && inqueryOrder.getInqueryDevices().size() > 0) {
	 * 
	 * List<InqueryDevice> inqueryDeviceList = inqueryOrder
	 * .getInqueryDevices(); BaseDto deviceDto = null; for (int index = 0; index
	 * < inqueryDeviceList.size(); index++) { InqueryDevice inqueryDevice =
	 * inqueryDeviceList .get(index); RentOrderDevice rentOrderDevice = new
	 * RentOrderDevice(); rentOrderDevice.copyFromInqueryDevice(inqueryDevice);
	 * rentOrderDevice.setCreateTime(new Date()); deviceDto =
	 * baseDao.insertSelective( RentOrderDeviceMapper.class, rentOrderDevice);
	 * if (!FrameworkUtils.isSuccess(deviceDto)) {
	 * log.info("addCreateRentOrder4InqueryOrder error when do addRentOrderDevice :"
	 * + JSON.toJSONString(deviceDto)); return JSON.toJSONString(dto); }
	 * 
	 * } } OrderProgressTrace orderProgressTraceAgain = new
	 * OrderProgressTrace(); orderProgressTraceAgain.setCreateTime(new Date());
	 * orderProgressTraceAgain .setStatus(Constant.IRENTORDER_STATUS_CREATE);
	 * orderProgressTraceAgain.setHandlerId(inqueryOrder .getCurrentHandler() !=
	 * null ? inqueryOrder .getCurrentHandler() : null); BaseDto
	 * baseProTraceAgainDto = baseDao
	 * .insertSelective(OrderProgressTraceMapper.class,
	 * orderProgressTraceAgain); if
	 * (!FrameworkUtils.isSuccess(baseProTraceAgainDto)) { log.info(
	 * "addCreateRentOrder4InqueryOrder error when do addOrderProgressTrace :" +
	 * JSON.toJSONString(baseProTraceAgainDto)); return
	 * JSON.toJSONString(baseProTraceAgainDto); } // 发送短信通知及内部消息 UserDto
	 * leasidUser = baseOtherService
	 * .getUserDtoByComapnyId(inqueryOrder.getLeasingSideId()); UserDto
	 * lessidUser = baseOtherService
	 * .getUserDtoByComapnyId(inqueryOrder.getLesseeSideId()); if (leasidUser !=
	 * null && leasidUser.getCellPhone() != null && lessidUser != null &&
	 * lessidUser.getCellPhone() != null) {
	 * 
	 * StringBuffer leasidSbf = new StringBuffer();
	 * leasidSbf.append(inqueryOrder.getLeasingSideName());
	 * leasidSbf.append(":"); leasidSbf.append(leasidUser.getCellPhone());
	 * 
	 * StringBuffer lessidSbf = new StringBuffer();
	 * lessidSbf.append(inqueryOrder.getLesseeSideName());
	 * lessidSbf.append(":"); lessidSbf.append(lessidUser.getCellPhone());
	 * 
	 * StringBuffer lockedlessidSbf = new StringBuffer();
	 * lockedlessidSbf.append(lockedBigDecimal);
	 * 
	 * if (isPayMethodOnline) {
	 * baseSMService.sendOrderMessageOnlineLeasingSideSms
	 * (leasidUser.getCellPhone
	 * (),lessidSbf.toString(),lockedlessidSbf.toString());
	 * baseSMService.sendOrderMessageOnlineLesseeSideSms
	 * (lessidUser.getCellPhone(),leasidSbf.toString()); } else {
	 * baseSMService.sendOrderMessageOfflineSms
	 * (lessidUser.getCellPhone(),leasidSbf.toString());
	 * baseSMService.sendOrderMessageOfflineSms
	 * (leasidUser.getCellPhone(),lessidSbf.toString()); }
	 * 
	 * // 内部消息 String leasidSmsTemplateInfo = ""; String lessidSmsTemplateInfo =
	 * ""; if (isPayMethodOnline) { leasidSmsTemplateInfo = SmsTemplateUtils
	 * .getOrderMessageOnlineLeasingSideTemplateInfo(); if
	 * (StringUtils.isNotBlank(leasidSmsTemplateInfo)) { leasidSmsTemplateInfo =
	 * leasidSmsTemplateInfo .replace("{1}", lessidSbf.toString());
	 * leasidSmsTemplateInfo = leasidSmsTemplateInfo .replace("{2}",
	 * lockedlessidSbf.toString()); } lessidSmsTemplateInfo = SmsTemplateUtils
	 * .getOrderMessageOnlineLesseeSideTemplateInfo(); if
	 * (StringUtils.isNotBlank(lessidSmsTemplateInfo)) { lessidSmsTemplateInfo =
	 * lessidSmsTemplateInfo .replace("{1}", leasidSbf.toString()); } } else {
	 * leasidSmsTemplateInfo = SmsTemplateUtils
	 * .getOrderMessageOfflineTemplateInfo(); if
	 * (StringUtils.isNotBlank(leasidSmsTemplateInfo)) { leasidSmsTemplateInfo =
	 * leasidSmsTemplateInfo .replace("{1}", lessidSbf.toString()); }
	 * lessidSmsTemplateInfo = SmsTemplateUtils
	 * .getOrderMessageOfflineTemplateInfo(); if
	 * (StringUtils.isNotBlank(lessidSmsTemplateInfo)) { lessidSmsTemplateInfo =
	 * lessidSmsTemplateInfo .replace("{1}", leasidSbf.toString()); }
	 * 
	 * }
	 * 
	 * JSONObject jsobleasidAdd = new JSONObject(); jsobleasidAdd.put("type",
	 * Constant.MESSAGE_TYPE_RENTORDER_INQUERYORDER);
	 * jsobleasidAdd.put("userId", leasidUser.getId());
	 * jsobleasidAdd.put("externalRelatedId", rentOrder.getId());
	 * jsobleasidAdd.put("operatorId", lessidUser.getId());
	 * jsobleasidAdd.put("content", leasidSmsTemplateInfo);
	 * jsobleasidAdd.put("status", Constant.MESSAGE_STATUS_UNREAD);
	 * baseSMService.addMessage(JSON.toJSONString(jsobleasidAdd));
	 * 
	 * JSONObject jsoblessidAdd = new JSONObject(); jsoblessidAdd.put("type",
	 * Constant.MESSAGE_TYPE_RENTORDER_INQUERYORDER);
	 * jsoblessidAdd.put("userId", lessidUser.getId());
	 * jsoblessidAdd.put("externalRelatedId", rentOrder.getId());
	 * jsoblessidAdd.put("operatorId", leasidUser.getId());
	 * jsoblessidAdd.put("content", lessidSmsTemplateInfo);
	 * jsoblessidAdd.put("status", Constant.MESSAGE_STATUS_UNREAD);
	 * baseSMService.addMessage(JSON.toJSONString(jsoblessidAdd));
	 * 
	 * }
	 * 
	 * if (FrameworkUtils.isSuccess(dto)) {
	 * log.info("addCreateRentOrder4InqueryOrder success!"); } else {
	 * log.error("addCreateRentOrder4InqueryOrder failure!"); } } catch
	 * (Exception e) { e.printStackTrace();
	 * log.error("addCreateRentOrder4InqueryOrder exception!"); throw new
	 * RuntimeException( "addCreateRentOrder4InqueryOrder Exception!"); } } else
	 * { dto.setRcode(BaseDto.ERROR_RCODE); dto.setRinfo("The data  is null !");
	 * log
	 * .error("---addCreateRentOrder4InqueryOrder -------- data is null ==== ");
	 * } jsonStr = JSON.toJSONString(dto); return jsonStr; }
	 */

	// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<求租需求单>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	/**
	 * 增加求租需求单
	 * 
	 * @author liukh
	 * @date 2017-2-24 上午10:44:19
	 * @param data
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseTMService#addInqueryRent(java.lang.String)
	 */
	@Override
	public String addInqueryRent(String data) {
		String jsonStr = "";
		BaseDto dto = new BaseDto();
		if (StringUtils.isNotBlank(data)) {
			try {
				InqueryRent inqueryRent = JSON.parseObject(data,
						InqueryRent.class);
				if (inqueryRent == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data parseObject to InqueryRent is null !");
					return JSON.toJSONString(dto);
				} else if (inqueryRent.getLesseeSideId() == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's lesseeSideId is null !");
					return JSON.toJSONString(dto);
				} else if (inqueryRent.getDeviceTypeId() == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's deviceTypeId is null !");
					return JSON.toJSONString(dto);
				} else if (inqueryRent.getDeviceTypeSpecDataId() == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's deviceTypeSpecDataId is null !");
					return JSON.toJSONString(dto);
				} else if (inqueryRent.getStartTime() == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's startTime is null !");
					return JSON.toJSONString(dto);
				} else if (inqueryRent.getPayMethod() == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's payMethod is null !");
					return JSON.toJSONString(dto);
				} else if (inqueryRent.getAddress() == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's address is null !");
					return JSON.toJSONString(dto);
				} else if (inqueryRent.getCity() == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's city is null !");
					return JSON.toJSONString(dto);
				} else if (inqueryRent.getQuantity() == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's quantity is null !");
					return JSON.toJSONString(dto);
				} else if (inqueryRent.getLeaseTerm() == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's leaseTerm is null !");
					return JSON.toJSONString(dto);
				} else if (inqueryRent.getBillingType() == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's billingType is null !");
					return JSON.toJSONString(dto);
				}

				BaseObjDto<Company> comanydto = baseDao.selectByPrimaryKey(
						CompanyMapper.class,
						StringUtils.trim(inqueryRent.getLesseeSideId()));
				if (FrameworkUtils.isSuccess(comanydto)) {
					Company company = comanydto.getData();
					if (inqueryRent.getLesseeSideName() == null) {
						inqueryRent.setLesseeSideName(company.getCompanyName());
					}
				} else {
					comanydto
							.setRinfo("The data's  lesseeSideId is not exits !");
					return JSON.toJSONString(comanydto);
				}

				BaseObjDto<DeviceType> typedto = baseDao.selectByPrimaryKey(
						DeviceTypeMapper.class,
						StringUtils.trim(inqueryRent.getDeviceTypeId()));
				if (FrameworkUtils.isSuccess(typedto)) {
					DeviceType deviceType = typedto.getData();
					if (inqueryRent.getDeviceTypeName() == null) {
						inqueryRent.setDeviceTypeName(deviceType.getTypeName());
					}
				} else {
					typedto.setRinfo("The data's  deviceTypeId is not exits !");
					return JSON.toJSONString(comanydto);
				}

				BaseObjDto<DeviceTypeSpecDataDto> datadto = baseDao
						.selectByPrimaryKey(DeviceTypeSpecDataMapper.class,
								StringUtils.trim(inqueryRent
										.getDeviceTypeSpecDataId()));
				if (!FrameworkUtils.isSuccess(datadto)) {
					datadto.setRinfo("The data's  deviceTypeSpecDataId is not exits !");
					return JSON.toJSONString(comanydto);
				}
				String endTime = CommonUtils.getEndDate(
						inqueryRent.getStartTime(),
						inqueryRent.getBillingType(),
						Integer.parseInt(inqueryRent.getLeaseTerm()));
				inqueryRent.setEndTime(endTime);
				Date createDate = new Date();
				inqueryRent.setCreateTime(createDate);
				dto = baseDao.insertSelective(InqueryRentMapper.class,
						inqueryRent);
				if (FrameworkUtils.isSuccess(dto)) {
					log.info("addInqueryRent success!");
				} else {
					log.error("addInqueryRent failure!");
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("addInqueryRent exception!");
				throw new RuntimeException("addInqueryRent Exception!");
			}
		} else {
			log.error("---addInqueryRent -------- data is null ==== ");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 获取求租需求单
	 * 
	 * @author liukh
	 * @date 2017-2-24 上午10:48:21
	 * @param inqueryRentId
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseTMService#getInqueryRent(java.lang.String)
	 */
	@Override
	public String getInqueryRent(String inqueryRentId) {
		String jsonStr = "";
		BaseObjDto<InqueryRent> dto = new BaseObjDto<InqueryRent>();
		try {
			if (StringUtils.isBlank(inqueryRentId)) {
				dto.setRinfo("inqueryRentId is null");
				return JSON.toJSONString(dto);
			}
			BaseObjDto<InqueryRent> inqueryRentDto = baseDao
					.selectByPrimaryKey(InqueryRentMapper.class,
							StringUtils.trim(inqueryRentId));
			if (FrameworkUtils.isSuccess(inqueryRentDto)) {
				InqueryRent inqueryRent = inqueryRentDto.getData();
				dto.setData(inqueryRent);
				FrameworkUtils.setSuccess(dto);
				log.info("getInqueryRent success!");
			} else {
				FrameworkUtils.setNoData(dto);
				log.error("getInqueryRent failure");
			}
		} catch (Exception e) {
			log.error("getInqueryRent exception!");
			e.printStackTrace();
			throw new RuntimeException("getInqueryRent Exception!");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 查询求租需求单明细的时候包含关键参数属性,及时间参数
	 * 
	 * @author liukh
	 * @date 2017-3-24 上午11:50:34
	 * @param inqueryRentId
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseTMService#getInqueryRentContainSpaceDataAndDateType(java.lang.String)
	 */
	@Override
	public String getInqueryRentContainSpaceDataAndDateType(String inqueryRentId) {
		String jsonStr = "";
		BaseObjDto<InqueryRentDetailDto> dto = new BaseObjDto<InqueryRentDetailDto>();
		try {
			if (StringUtils.isBlank(inqueryRentId)) {
				dto.setRinfo("inqueryRentId is null");
				return JSON.toJSONString(dto);
			}
			Map<String, Object> paramsMap = new HashMap<String, Object>();
			paramsMap.put("id", StringUtils.trim(inqueryRentId));

			BaseObjDto<InqueryRentDetailDto> inqueryRentDto = baseDao
					.getObjProperty(InqueryRentMapper.class,
							"getInqueryRentDetail", paramsMap);
			if (FrameworkUtils.isSuccess(inqueryRentDto)) {
				InqueryRentDetailDto inqueryRent = inqueryRentDto.getData();
				DateTypeDto dateType = new DateTypeDto();
				if (inqueryRent.getBillingType() != null
						&& inqueryRent.getLeaseTerm() != null) {
					dateType.setType(inqueryRent.getBillingType().toString());
					if (inqueryRent.getBillingType() == Constant.BILLINGTYPE_MONTH) {
						dateType.setMonth(new BigDecimal(inqueryRent
								.getLeaseTerm()));
						dateType.setDay(BigDecimal.ZERO);
					} else if (inqueryRent.getBillingType() == Constant.BILLINGTYPE_DAY) {
						dateType.setDay(new BigDecimal(inqueryRent
								.getLeaseTerm()));
						dateType.setMonth(BigDecimal.ZERO);
					}
					dateType.setDateAmount(BigDecimal.ZERO);

				} else {
					dateType.setType(Constant.BILLINGTYPE_MONTH.toString());
					dateType.setMonth(BigDecimal.ZERO);
					dateType.setDay(BigDecimal.ZERO);
					dateType.setDateAmount(BigDecimal.ZERO);
				}
				inqueryRent.setDateType(dateType);
				dto.setData(inqueryRent);
				dto.setRcode(BaseDto.SUCCESS_RCODE);
				dto.setRinfo(BaseDto.DEFAULT_SUCCESS_INFO);
				log.error("getInqueryRentContainSpaceDataAndDateType success");
			} else {
				FrameworkUtils.setNoData(dto);
				log.error("getInqueryRentContainSpaceDataAndDateType failure");
			}
		} catch (Exception e) {
			log.error("getInqueryRentContainSpaceDataAndDateType exception!");
			e.printStackTrace();
			throw new RuntimeException(
					"getInqueryRentContainSpaceDataAndDateType Exception!");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 获取求租需求单及某个租赁方对应的报价的详细信息
	 * 
	 * @author liukh
	 * @date 2017-3-24 下午2:20:13
	 * @param params
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseTMService#getInqueryRentAndInqueryRentQuotoDetaliInfor(java.util.Map)
	 */
	@Override
	public String getInqueryRentAndInqueryRentQuotoDetaliInfor(
			Map<String, Object> params) {
		String jsonStr = "";
		BaseObjDto<InqueryRentDetailDto> dto = new BaseObjDto<InqueryRentDetailDto>();
		try {
			if (params == null) {
				dto.setRcode(BaseDto.ERROR_RCODE);
				dto.setRinfo("The request param is null");
				return JSON.toJSONString(dto);
			} else if (params.get("inqueryRentId") == null) {
				dto.setRcode(BaseDto.ERROR_RCODE);
				dto.setRinfo("The request param's inqueryRentId is null");
				return JSON.toJSONString(dto);
			} else if (params.get("leasingSideId") == null) {
				dto.setRcode(BaseDto.ERROR_RCODE);
				dto.setRinfo("The request param's leasingSideId is null");
				return JSON.toJSONString(dto);
			}

			BaseObjDto<Company> companyDto = baseDao.selectByPrimaryKey(
					CompanyMapper.class,
					StringUtils.trim(params.get("leasingSideId").toString()));
			if (!FrameworkUtils.isSuccess(companyDto)) {
				dto.setRcode(BaseDto.ERROR_RCODE);
				dto.setRinfo("The request param's leasingSideId is not exits ");
				return JSON.toJSONString(dto);
			}

			Map<String, Object> paramsMap = new HashMap<String, Object>();
			paramsMap.put("id",
					StringUtils.trim(params.get("inqueryRentId").toString()));

			BaseObjDto<InqueryRentDetailDto> inqueryRentDto = baseDao
					.getObjProperty(InqueryRentMapper.class,
							"getInqueryRentDetail", paramsMap);
			if (FrameworkUtils.isSuccess(inqueryRentDto)) {
				InqueryRentDetailDto inqueryRent = inqueryRentDto.getData();
				DateTypeDto dateType = new DateTypeDto();
				if (inqueryRent.getBillingType() != null
						&& inqueryRent.getLeaseTerm() != null) {
					dateType.setType(inqueryRent.getBillingType().toString());
					if (inqueryRent.getBillingType() == Constant.BILLINGTYPE_MONTH) {
						dateType.setMonth(new BigDecimal(inqueryRent
								.getLeaseTerm()));
						dateType.setDay(BigDecimal.ZERO);
					} else if (inqueryRent.getBillingType() == Constant.BILLINGTYPE_DAY) {
						dateType.setDay(new BigDecimal(inqueryRent
								.getLeaseTerm()));
						dateType.setMonth(BigDecimal.ZERO);
					}
					dateType.setDateAmount(BigDecimal.ZERO);

				} else {
					dateType.setType(Constant.BILLINGTYPE_MONTH.toString());
					dateType.setMonth(BigDecimal.ZERO);
					dateType.setDay(BigDecimal.ZERO);
					dateType.setDateAmount(BigDecimal.ZERO);
				}
				inqueryRent.setDateType(dateType);

				InqueryRentQuoteQueryForm form = new InqueryRentQuoteQueryForm();
				form.setInqueryRentId(StringUtils.trim(params.get(
						"inqueryRentId").toString()));
				form.setLeasingSideId(StringUtils.trim(params.get(
						"leasingSideId").toString()));
				BaseObjDto<ItemPage<InqueryRentQuote>> pagesDto = baseDao
						.getPageList(InqueryRentQuoteMapper.class,
								InqueryRentQuote.class, form,
								"getInqueryRentQuotePageList");
				if (FrameworkUtils.isSuccess(pagesDto)
						&& pagesDto.getData() != null
						&& pagesDto.getData().getItems() != null
						&& pagesDto.getData().getItems().size() > 0) {
					InqueryRentQuote queryRentQuote = pagesDto.getData()
							.getItems().get(0);
					queryRentQuote.setInqueryRent(null);
					inqueryRent.setData(queryRentQuote);
				}

				dto.setData(inqueryRent);
				dto.setRcode(BaseDto.SUCCESS_RCODE);
				dto.setRinfo(BaseDto.DEFAULT_SUCCESS_INFO);
				log.error("getInqueryRentContainSpaceDataAndDateType success");
			} else {
				FrameworkUtils.setNoData(dto);
				log.error("getInqueryRentContainSpaceDataAndDateType failure");
			}
		} catch (Exception e) {
			log.error("getInqueryRentContainSpaceDataAndDateType exception!");
			e.printStackTrace();
			throw new RuntimeException(
					"getInqueryRentContainSpaceDataAndDateType Exception!");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 修改求租需求单
	 * 
	 * @author liukh
	 * @date 2017-2-24 上午10:49:17
	 * @param inqueryRentId
	 * @param data
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseTMService#updateInqueryRent(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public String updateInqueryRent(String inqueryRentId, String data) {
		String jsonStr = "";
		BaseDto dto = new BaseDto();
		if (StringUtils.isNotBlank(inqueryRentId)
				&& StringUtils.isNotBlank(data)) {
			try {
				InqueryRent inqueryRent = JSON.parseObject(data,
						InqueryRent.class);
				if (inqueryRent == null) {
					dto.setRcode(1);
					dto.setRinfo("The data parseObject to InqueryRent is null !");
					return JSON.toJSONString(dto);
				}
				inqueryRent.setId(inqueryRentId);
				dto = baseDao.updateByPrimaryKeySelective(
						InqueryRentMapper.class, inqueryRent);
				if (FrameworkUtils.isSuccess(dto)) {
					log.info("updateInqueryRent success!");
				} else {
					log.error("updateInqueryRent failure!");
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("updateInqueryRent exception!");
				throw new RuntimeException("updateInqueryRent Exception!");
			}

		} else {
			log.error("---updateInqueryRent -------- data or inqueryRentId is null ==== ");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 获取求租需求单列表
	 * 
	 * @author liukh
	 * @date 2017-2-24 上午10:52:03
	 * @param form
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseTMService#getInqueryRentList(com.zj.entity.tm.form.InqueryRentQueryForm)
	 */
	@Override
	public String getInqueryRentList(InqueryRentQueryForm form) {
		String jsonStr = "";
		BaseObjDto<ItemPage<InqueryRent>> dto = new BaseObjDto<ItemPage<InqueryRent>>();
		try {
			BaseObjDto<ItemPage<InqueryRent>> pagesDto = baseDao.getPageList(
					InqueryRentMapper.class, InqueryRent.class, form,
					"getInqueryRentPageList");
			if (FrameworkUtils.isSuccess(pagesDto)) {
				dto = pagesDto;
				log.info("getInqueryRentList success");
			} else {
				FrameworkUtils.setNoData(dto);
				log.error("getInqueryRentList failure");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("getInqueryRentList Exception!");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 查询求租需求单列表的时候包含关键参数属性,并且是可以报价的(状态为待报价和已报价，且不包含自己公司已经报价的)
	 * 
	 * @author liukh
	 * @date 2017-3-24 下午3:33:35
	 * @param form
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseTMService#getCanQuotoInqueryRentContainSpaceDataList(com.zj.entity.tm.form.InqueryRentQueryForm)
	 */
	@Override
	public String getCanQuotoInqueryRentContainSpaceDataList(
			InqueryRentQueryForm form) {
		String jsonStr = "";
		BaseObjDto<ItemPage<InqueryRentDetailListDto>> dto = new BaseObjDto<ItemPage<InqueryRentDetailListDto>>();
		try {
			if (form == null) {
				dto.setRcode(BaseDto.ERROR_RCODE);
				dto.setRinfo("The request params is null !");
				return JSON.toJSONString(dto);
			} else if (form.getStatus() == null) {
				dto.setRcode(BaseDto.ERROR_RCODE);
				dto.setRinfo("The request params's status is null !");
				return JSON.toJSONString(dto);
			} else if (form.getRemoveQuotedCompanyId() == null) {
				dto.setRcode(BaseDto.ERROR_RCODE);
				dto.setRinfo("The request params's removeQuotedCompanyId is null !");
				return JSON.toJSONString(dto);
			}
			// 游客
			if (form.getRemoveQuotedCompanyId().equals("0")) {
				form.setRemoveQuotedCompanyId(null);
			}
			BaseObjDto<ItemPage<InqueryRentDetailListDto>> pagesDto = baseDao
					.getPageList(InqueryRentMapper.class,
							InqueryRentDetailListDto.class, form,
							"getCanQuotoInqueryRentPageList");
			if (FrameworkUtils.isSuccess(pagesDto)
					&& pagesDto.getData() != null
					&& pagesDto.getData().getItems() != null
					&& pagesDto.getData().getItems().size() > 0) {
				if (form.getLatitude() != null
						&& StringUtils.isNotBlank(form.getLatitude())
						&& form.getLongitude() != null
						&& StringUtils.isNotBlank(form.getLongitude())) {
					List<InqueryRentDetailListDto> list = pagesDto.getData()
							.getItems();
					List<InqueryRentDetailListDto> doneList = new ArrayList<InqueryRentDetailListDto>();
					double distance = 0;
					for (int index = 0; index < list.size(); index++) {
						InqueryRentDetailListDto inqueryRentDto = list
								.get(index);
						if (inqueryRentDto.getLatitude() != null
								&& inqueryRentDto.getLongitude() != null) {
							distance = MapToolUtils.distance(Double
									.parseDouble(form.getLongitude()), Double
									.parseDouble(form.getLatitude()),
									Double.parseDouble(inqueryRentDto
											.getLongitude()), Double
											.parseDouble(inqueryRentDto
													.getLatitude()));
							StringBuffer sbf = new StringBuffer();
							if (distance > 1000) {
								distance = distance / 1000;
								distance = CommonUtils.round(distance, 1);
								sbf.append(distance);
								sbf.append("km");

							} else {
								distance = CommonUtils.round(distance, 1);
								sbf.append(distance);
								sbf.append("m");
							}
							inqueryRentDto.setDistance(sbf.toString());
						}

						doneList.add(inqueryRentDto);

					}
					pagesDto.getData().getItems().clear();
					pagesDto.getData().setItems(doneList);
				}

				dto = pagesDto;
				log.info("getCanQuotoInqueryRentContainSpaceDataList success");
			} else {
				FrameworkUtils.setNoData(dto);
				log.error("getCanQuotoInqueryRentContainSpaceDataList failure");

			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(
					"getCanQuotoInqueryRentContainSpaceDataList Exception!");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;

	}

	@Override
	public String getInqueryRentListAndInqueryRentQuoteList(
			InqueryRentQueryForm form) {
		String jsonStr = "";
		BaseObjDto<ItemPage<InqueryRentDetailListDto>> dto = new BaseObjDto<ItemPage<InqueryRentDetailListDto>>();
		try {

			BaseObjDto<ItemPage<InqueryRentDetailListDto>> pagesDto = baseDao
					.getPageList(InqueryRentMapper.class,
							InqueryRentDetailListDto.class, form,
							"getInqueryRentDetailPageList");
			if (FrameworkUtils.isSuccess(pagesDto)
					&& pagesDto.getData() != null
					&& pagesDto.getData().getItems() != null
					&& pagesDto.getData().getItems().size() > 0) {
				List<InqueryRentDetailListDto> list = pagesDto.getData()
						.getItems();
				List<InqueryRentDetailListDto> doneList = new ArrayList<InqueryRentDetailListDto>();
				for (int index = 0; index < list.size(); index++) {
					List<InqueryRentQuote> doneQuoteList1 = new ArrayList<InqueryRentQuote>();
					List<InqueryRentQuote> doneQuoteList2 = new ArrayList<InqueryRentQuote>();
					InqueryRentDetailListDto inqueryRentDto = list.get(index);
					BaseObjDto<InqueryRent> queryInqueryRentDto = baseDao
							.selectByPrimaryKey(InqueryRentMapper.class,
									inqueryRentDto.getId());
					if (FrameworkUtils.isSuccess(queryInqueryRentDto)
							&& queryInqueryRentDto.getData() != null
							&& queryInqueryRentDto.getData()
									.getInqueryRentQuotes() != null) {
						if (queryInqueryRentDto.getData()
								.getInqueryRentQuotes().size() > 0) {
							List<InqueryRentQuote> quoteList = queryInqueryRentDto
									.getData().getInqueryRentQuotes();
							for (int indexx = 0; indexx < quoteList.size(); indexx++) {
								InqueryRentQuote inqueryRentQuote = quoteList
										.get(indexx);
								//quoteStatus=0代表求租报价反馈
								if (form != null
										&& form.getQuoteStatus() != null
										&& inqueryRentQuote.getStatus() != null
										&& form.getQuoteStatus() == inqueryRentQuote
												.getStatus()) {
									doneQuoteList1.add(inqueryRentQuote);
								}
								doneQuoteList2.add(inqueryRentQuote);

							}
							if (form != null && form.getQuoteStatus() != null) {
								inqueryRentDto.setInqueryRentQuotes(doneQuoteList1);
							}else{
								inqueryRentDto.setInqueryRentQuotes(doneQuoteList2);
							}
						} else {
							inqueryRentDto.setInqueryRentQuotes(null);
						}

					}
					doneList.add(inqueryRentDto);

				}
				pagesDto.getData().getItems().clear();
				pagesDto.getData().setItems(doneList);
				dto = pagesDto;
				log.info("getInqueryRentListAndInqueryRentQuoteList success");
			} else {
				FrameworkUtils.setNoData(dto);
				log.error("getInqueryRentListAndInqueryRentQuoteList failure");

			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(
					"getInqueryRentListAndInqueryRentQuoteList Exception!");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 增加求租需求单报价
	 * 
	 * @author liukh
	 * @date 2017-2-24 上午11:09:55
	 * @param data
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseTMService#addInqueryRentQuote(java.lang.String)
	 */
	@Override
	public String addInqueryRentQuote(String data) {
		String jsonStr = "";
		BaseDto dto = new BaseDto();
		if (StringUtils.isNotBlank(data)) {
			try {
				InqueryRentQuote inqueryRentQuote = JSON.parseObject(data,
						InqueryRentQuote.class);
				if (inqueryRentQuote == null) {
					dto.setRcode(1);
					dto.setRinfo("The data parseObject to InqueryRentQuote is null !");
					return JSON.toJSONString(dto);
				}
				JSONObject jp = JSON.parseObject(data);
				if (jp == null) {
					dto.setRcode(1);
					dto.setRinfo("The data parseObject to JSONObject is null !");
					return JSON.toJSONString(dto);
				}

				String inqueryRentId = jp.getString("inqueryRentId");
				if (StringUtils.isBlank(inqueryRentId)) {
					dto.setRcode(1);
					dto.setRinfo("The data's inqueryRentId is null !");
					return JSON.toJSONString(dto);
				}
				InqueryRent inqueryRent = new InqueryRent();
				inqueryRent.setId(inqueryRentId);
				inqueryRentQuote.setInqueryRent(inqueryRent);
				Date createDate = new Date();
				inqueryRentQuote.setCreateTime(createDate);
				dto = baseDao.insertSelective(InqueryRentQuoteMapper.class,
						inqueryRentQuote);
				if (FrameworkUtils.isSuccess(dto)) {
					log.info("addInqueryRentQuote success!");
				} else {
					log.error("addInqueryRentQuote failure!");
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("addInqueryRentQuote exception!");
				throw new RuntimeException("addInqueryRentQuote Exception!");
			}
		} else {
			log.error("---addInqueryRentQuote -------- data is null ==== ");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 新增求租需求单报价，并校验价格
	 * 
	 * @author liukh
	 * @date 2017-3-24 下午5:26:13
	 * @param data
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseTMService#addInqueryRentQuoteComapreTotalPrice(java.lang.String)
	 */
	@Override
	public String addInqueryRentQuoteComapreTotalPrice(String data) {
		String jsonStr = "";
		BaseDto dto = new BaseDto();
		if (StringUtils.isNotBlank(data)) {
			try {

				Map<String, Object> paramsMap = JSON.parseObject(data);
				if (paramsMap == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The dataParamsMap is null !");
					return JSON.toJSONString(dto);
				} else if (paramsMap.get("leasingSideId") == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's leasingSideId is null !");
					return JSON.toJSONString(dto);
				} else if (paramsMap.get("quantity") == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's quantity is null !");
					return JSON.toJSONString(dto);
				} else if (paramsMap.get("price") == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's price is null !");
					return JSON.toJSONString(dto);
				} else if (paramsMap.get("totalPrice") == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's totalPrice is null !");
					return JSON.toJSONString(dto);
				} else if (paramsMap.get("leaseTerm") == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's leaseTerm is null !");
					return JSON.toJSONString(dto);
				} else if (paramsMap.get("inqueryRentId") == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's inqueryRentId is null !");
					return JSON.toJSONString(dto);
				} else if (paramsMap.get("status") == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's status is null !");
					return JSON.toJSONString(dto);
				} else if (paramsMap.get("isInlcudeInsurance") == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's isInlcudeInsurance is null !");
					return JSON.toJSONString(dto);
				} else if (paramsMap.get("isInlcudeJiShou") == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's isInlcudeJiShou is null !");
					return JSON.toJSONString(dto);
				} else if (paramsMap.get("isInlcudeFuel") == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's isInlcudeFuel is null !");
					return JSON.toJSONString(dto);
				} else if (paramsMap.get("isIncludeInvoice") == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's isIncludeInvoice is null !");
					return JSON.toJSONString(dto);
				} else if (paramsMap.get("isIncludeShippingFee") == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's isIncludeShippingFee is null !");
					return JSON.toJSONString(dto);
				} else if (paramsMap.get("payMethod") == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's payMethod is null !");
					return JSON.toJSONString(dto);
				} else if (paramsMap.get("creator") == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's creator is null !");
					return JSON.toJSONString(dto);
				} else if (paramsMap.get("picture") == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's picture is null !");
					return JSON.toJSONString(dto);
				} else if (paramsMap.get("deviceModelId") == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's deviceModelId is null !");
					return JSON.toJSONString(dto);
				}

				InqueryRentQuoteQueryForm queryQuoteForm = new InqueryRentQuoteQueryForm();
				queryQuoteForm.setInqueryRentId(StringUtils.trim(paramsMap.get(
						"inqueryRentId").toString()));
				queryQuoteForm.setLeasingSideId(StringUtils.trim(paramsMap.get(
						"leasingSideId").toString()));
				BaseObjDto<ItemPage<InqueryRentQuote>> rentDto = baseDao
						.getPageList(InqueryRentQuoteMapper.class,
								InqueryRentQuote.class, queryQuoteForm,
								"getInqueryRentQuotePageList");
				if (FrameworkUtils.isSuccess(rentDto)) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("该需求单您已经报价，请勿重复报价!");
					return JSON.toJSONString(dto);
				}

				InqueryRentQuote addQuote = new InqueryRentQuote();
				BaseObjDto<DeviceModel> modeDto = baseDao.selectByPrimaryKey(
						DeviceModelMapper.class, StringUtils.trim(paramsMap
								.get("deviceModelId").toString()));
				if (FrameworkUtils.isSuccess(modeDto)) {
					DeviceModel deviceModel = modeDto.getData();
					System.out.println("-----------:" + deviceModel);
					addQuote.setDeviceModelName(deviceModel.getModelName());

				} else {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's deviceModelId is not exits !");
					return JSON.toJSONString(dto);
				}
				BaseObjDto<Company> leaCompanyDto = baseDao.selectByPrimaryKey(
						CompanyMapper.class, StringUtils.trim(paramsMap.get(
								"leasingSideId").toString()));
				if (FrameworkUtils.isSuccess(leaCompanyDto)) {
					Company leaComapny = leaCompanyDto.getData();
					addQuote.setLeasingSideName(leaComapny.getCompanyName());
				} else {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's leasingSideId is not exits !");
					return JSON.toJSONString(dto);
				}

				BaseObjDto<InqueryRent> queryRentDto = baseDao
						.selectByPrimaryKey(InqueryRentMapper.class,
								StringUtils.trim(paramsMap.get("inqueryRentId")
										.toString()));
				if (!FrameworkUtils.isSuccess(queryRentDto)) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's inqueryRentId is not exits !");
					return JSON.toJSONString(dto);
				}
				InqueryRent queryInqueryRent = queryRentDto.getData();
				if (queryInqueryRent != null
						&& queryInqueryRent.getLesseeSideId() != null) {
					UserDto user = baseOtherService
							.getUserDtoByComapnyId(queryInqueryRent
									.getLesseeSideId());
					if (user != null) {
						addQuote.setCurrentHandler(user.getId());
					}
				}

				if (paramsMap.get("otherExpense") == null) {
					addQuote.setOtherExpense(BigDecimal.ZERO);
				} else {
					addQuote.setOtherExpense(new BigDecimal(paramsMap.get(
							"otherExpense").toString()));
				}
				addQuote.setCreateTime(new Date());
				addQuote.setCreator(paramsMap.get("creator").toString());
				addQuote.setDeviceModelId(StringUtils.trim(paramsMap.get(
						"deviceModelId").toString()));
				InqueryRent assRent = new InqueryRent();
				assRent.setId(StringUtils.trim(paramsMap.get("inqueryRentId")
						.toString()));
				addQuote.setInqueryRent(assRent);
				addQuote.setIsIncludeInvoice(Boolean.valueOf(paramsMap.get(
						"isIncludeInvoice").toString()));
				addQuote.setIsIncludeShippingFee(Boolean.valueOf(paramsMap.get(
						"isIncludeShippingFee").toString()));
				addQuote.setIsInlcudeFuel(Boolean.valueOf(paramsMap.get(
						"isInlcudeFuel").toString()));
				addQuote.setIsInlcudeInsurance(Boolean.valueOf(paramsMap.get(
						"isInlcudeInsurance").toString()));
				addQuote.setIsInlcudeJiShou(Boolean.valueOf(paramsMap.get(
						"isInlcudeJiShou").toString()));
				addQuote.setLeasingSideId(StringUtils.trim(paramsMap.get(
						"leasingSideId").toString()));
				addQuote.setOtherExpenseComment(paramsMap.get(
						"otherExpenseComment").toString());
				addQuote.setPayMethod(paramsMap.get("payMethod").toString());
				addQuote.setPicture(paramsMap.get("picture").toString());
				addQuote.setPrice(new BigDecimal(paramsMap.get("price")
						.toString()));
				addQuote.setTotalPrice(new BigDecimal(paramsMap.get(
						"totalPrice").toString()));
				addQuote.setStatus(Constant.INQUERYRENTQUOTE_STATUS_CREATE);

				String leaseTerm = paramsMap.get("leaseTerm").toString();
				BigDecimal unitPrice = new BigDecimal(paramsMap.get("price")
						.toString());
				int amount = Integer.parseInt(paramsMap.get("quantity")
						.toString());
				addQuote.setQuantity(amount);
				BigDecimal otherPrice =BigDecimal.ZERO;
				if(paramsMap.get("otherExpense")!= null){
					otherPrice = new BigDecimal(paramsMap.get(
							"otherExpense").toString());
				}
				 
				BigDecimal totalPrice = new BigDecimal(paramsMap.get(
						"totalPrice").toString());
				if (NumberUtils.checkCalculateTotalPriceEqualsTotalPrice(
						leaseTerm, unitPrice, amount, otherPrice, totalPrice)) {
					dto = baseDao.insertSelective(InqueryRentQuoteMapper.class,
							addQuote);
					if (FrameworkUtils.isSuccess(dto)) {
						InqueryRent updateRent = new InqueryRent();
						updateRent.setId(StringUtils.trim(paramsMap.get(
								"inqueryRentId").toString()));
						updateRent
								.setStatus(Constant.INQUERYRENT_STATUS_MAKESURE);
						dto = baseDao.updateByPrimaryKeySelective(
								InqueryRentMapper.class, updateRent);
					}

				} else {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("数据异常,传递的总价和计算的总价不符合!");
					return JSON.toJSONString(dto);
				}

				if (FrameworkUtils.isSuccess(dto)) {
					log.info("addInqueryRentQuoteComapreTotalPrice success!");
				} else {
					log.error("addInqueryRentQuoteComapreTotalPrice failure!");
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("addInqueryRentQuoteComapreTotalPrice exception!");
				throw new RuntimeException(
						"addInqueryRentQuoteComapreTotalPrice Exception!");
			}
		} else {
			log.error("---addInqueryRentQuoteComapreTotalPrice -------- data is null ==== ");
			dto.setRcode(BaseDto.ERROR_RCODE);
			dto.setRinfo("The data  is null !");
			return JSON.toJSONString(dto);
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 根据求租需求单的报价计算出总的报价
	 * 
	 * @author liukh
	 * @date 2017-3-24 下午5:25:33
	 * @param data
	 *            {"leaseTerm"，"quantity"，"otherExpense"，"unitPrice"}
	 * @return
	 */
	@Override
	public String getOrderCalculatePrice(String data) {
		String jsonStr = "";
		BaseObjDto<OrderCalculatePriceDto> dto = new BaseObjDto<OrderCalculatePriceDto>();
		try {
			if (StringUtils.isBlank(data)) {
				dto.setRcode(BaseDto.ERROR_RCODE);
				dto.setRinfo("The data is null");
				return JSON.toJSONString(dto);
			}
			Map<String, Object> paramsMap = JSON.parseObject(data);
			if (paramsMap == null) {
				dto.setRcode(BaseDto.ERROR_RCODE);
				dto.setRinfo("The data prase  paramsMap null !");
				return JSON.toJSONString(dto);
			} else if (paramsMap.get("leaseTerm") == null) {
				dto.setRcode(BaseDto.ERROR_RCODE);
				dto.setRinfo("The   data's leaseTerm null !");
				return JSON.toJSONString(dto);
			} else if (paramsMap.get("quantity") == null) {
				dto.setRcode(BaseDto.ERROR_RCODE);
				dto.setRinfo("The   data's quantity null !");
				return JSON.toJSONString(dto);
			} else if (paramsMap.get("unitPrice") == null) {
				dto.setRcode(BaseDto.ERROR_RCODE);
				dto.setRinfo("The   data's unitPrice null !");
				return JSON.toJSONString(dto);
			}
			if (paramsMap.get("otherExpense") == null) {
				paramsMap.put("otherExpense", "0");
			}

			BigDecimal unitPrice = new BigDecimal(paramsMap.get("unitPrice")
					.toString());
			int quantity = Integer.parseInt(paramsMap.get("quantity")
					.toString());
			BigDecimal otherPrice = new BigDecimal(paramsMap
					.get("otherExpense").toString());

			BigDecimal totalPrice = NumberUtils.calculateTotalPrice(
					new BigDecimal(paramsMap.get("leaseTerm").toString()),
					unitPrice, quantity, otherPrice);

			OrderCalculatePriceDto orderCalculatePrice = new OrderCalculatePriceDto();
			orderCalculatePrice.setOtherExpense(otherPrice);
			orderCalculatePrice.setTimeTotal(new BigDecimal(paramsMap.get(
					"leaseTerm").toString()));
			orderCalculatePrice.setTotalPrice(totalPrice);
			orderCalculatePrice.setUnitPrice(unitPrice);
			orderCalculatePrice.setQuantity(quantity);
			dto.setData(orderCalculatePrice);
			dto.setRcode(BaseDto.SUCCESS_RCODE);
			dto.setRinfo(BaseDto.DEFAULT_SUCCESS_INFO);
		} catch (Exception e) {
			log.error("getOrderCalculatePrice exception!");
			e.printStackTrace();
			throw new RuntimeException("getOrderCalculatePrice Exception!");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 忽略求租需求单报价
	 * 
	 * @author liukh
	 * @date 2017-3-25 上午10:06:49
	 * @param inqueryRentQuoteId
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseTMService#updateIgnoreInqueryRentQuoto(java.lang.String)
	 */

	@Override
	public String updateIgnoreInqueryRentQuoto(String inqueryRentQuoteId) {
		String jsonStr = "";
		BaseDto dto = new BaseDto();
		if (StringUtils.isNotBlank(inqueryRentQuoteId)) {
			try {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("inqueryRentQuoteId",
						StringUtils.trim(inqueryRentQuoteId));
				BaseObjDto<InqueryRentQuotoAndInqueryRentDto> queryDto = baseDao
						.getObjProperty(InqueryRentQuoteMapper.class,
								"getInqueryRentAndQuoteDetail", params);

				if (!FrameworkUtils.isSuccess(queryDto)) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The inqueryRentQuoteId is not exits !");
				}
				InqueryRentQuotoAndInqueryRentDto inqueryRentQuotoAndInqueryRentDto = queryDto
						.getData();
				InqueryRentQuote updateRentQuote = new InqueryRentQuote();
				updateRentQuote.setId(StringUtils.trim(inqueryRentQuoteId));
				updateRentQuote
						.setStatus(Constant.INQUERYRENTQUOTE_STATUS_OVER);
				dto = baseDao.updateByPrimaryKeySelective(
						InqueryRentQuoteMapper.class, updateRentQuote);
				if (FrameworkUtils.isSuccess(dto)) {
					InqueryRentQuoteQueryForm queryForm = new InqueryRentQuoteQueryForm();
					queryForm
							.setInqueryRentId(inqueryRentQuotoAndInqueryRentDto
									.getInqueryRentId());
					queryForm.setStatus(Constant.INQUERYRENTQUOTE_STATUS_CREATE
							.toString());
					BaseObjDto<ItemPage<InqueryRentQuote>> rentQuoteListDto = baseDao
							.getPageList(InqueryRentQuoteMapper.class,
									InqueryRentQuote.class, queryForm,
									"getInqueryRentQuotePageList");
					if (!FrameworkUtils.isSuccess(rentQuoteListDto)) {
						InqueryRent updateRent = new InqueryRent();
						updateRent.setId(inqueryRentQuotoAndInqueryRentDto
								.getInqueryRentId());
						updateRent.setStatus(Constant.INQUERYRENT_STATUS_WAIT);
						dto = baseDao.updateByPrimaryKeySelective(
								InqueryRentMapper.class, updateRent);

					}

				}

				if (FrameworkUtils.isSuccess(dto)) {
					log.info("updateIgnoreInqueryRentQuoto success!");
				} else {
					log.error("updateIgnoreInqueryRentQuoto failure!");
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("updateIgnoreInqueryRentQuoto exception!");
				throw new RuntimeException(
						"updateIgnoreInqueryRentQuoto Exception!");
			}

		} else {
			log.error("---updateIgnoreInqueryRentQuoto --------  inqueryRentQuoteId is null ==== ");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 获取求租需求单报价
	 * 
	 * @author liukh
	 * @date 2017-2-24 上午11:15:17
	 * @param inqueryRentQuoteId
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseTMService#getInqueryRentQuote(java.lang.String)
	 */
	@Override
	public String getInqueryRentQuote(String inqueryRentQuoteId) {
		String jsonStr = "";
		BaseObjDto<InqueryRentQuote> dto = new BaseObjDto<InqueryRentQuote>();
		try {
			if (StringUtils.isBlank(inqueryRentQuoteId)) {
				dto.setRinfo("inqueryRentQuoteId is null");
				return JSON.toJSONString(dto);
			}
			BaseObjDto<InqueryRentQuote> inqueryRentQuoteDto = baseDao
					.selectByPrimaryKey(InqueryRentQuoteMapper.class,
							StringUtils.trim(inqueryRentQuoteId));
			if (FrameworkUtils.isSuccess(inqueryRentQuoteDto)) {
				InqueryRentQuote inqueryRentQuote = inqueryRentQuoteDto
						.getData();
				dto.setData(inqueryRentQuote);
				FrameworkUtils.setSuccess(dto);
				log.info("getInqueryRentQuote success!");
			} else {
				FrameworkUtils.setNoData(dto);
				log.error("getInqueryRentQuote failure");
			}
		} catch (Exception e) {
			log.error("getInqueryRentQuote exception!");
			e.printStackTrace();
			throw new RuntimeException("getInqueryRentQuote Exception!");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 修改求租需求单报价
	 * 
	 * @author liukh
	 * @date 2017-2-24 上午11:21:44
	 * @param inqueryRentQuoteId
	 * @param data
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseTMService#updateInqueryRentQuote(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public String updateInqueryRentQuote(String inqueryRentQuoteId, String data) {
		String jsonStr = "";
		BaseDto dto = new BaseDto();
		if (StringUtils.isNotBlank(inqueryRentQuoteId)
				&& StringUtils.isNotBlank(data)) {
			try {
				InqueryRentQuote inqueryRentQuote = JSON.parseObject(data,
						InqueryRentQuote.class);
				if (inqueryRentQuote == null) {
					dto.setRcode(1);
					dto.setRinfo("The data parseObject to InqueryRentQuote is null !");
					return JSON.toJSONString(dto);
				}
				inqueryRentQuote.setId(inqueryRentQuoteId);
				dto = baseDao.updateByPrimaryKeySelective(
						InqueryRentQuoteMapper.class, inqueryRentQuote);
				if (FrameworkUtils.isSuccess(dto)) {
					log.info("updateInqueryRentQuote success!");
				} else {
					log.error("updateInqueryRentQuote failure!");
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("updateInqueryRentQuote exception!");
				throw new RuntimeException("updateInqueryRentQuote Exception!");
			}

		} else {
			log.error("---updateInqueryRentQuote -------- data or inqueryRentQuoteId is null ==== ");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 获取求租需求单报价列表
	 * 
	 * @author liukh
	 * @date 2017-2-24 上午11:23:57
	 * @param form
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseTMService#getInqueryRentQuoteList(com.zj.entity.tm.form.InqueryRentQueryForm)
	 */
	@Override
	public String getInqueryRentQuoteList(InqueryRentQuoteQueryForm form) {
		String jsonStr = "";
		BaseObjDto<ItemPage<InqueryRentQuote>> dto = new BaseObjDto<ItemPage<InqueryRentQuote>>();
		try {
			BaseObjDto<ItemPage<InqueryRentQuote>> pagesDto = baseDao
					.getPageList(InqueryRentQuoteMapper.class,
							InqueryRentQuote.class, form,
							"getInqueryRentQuotePageList");
			if (FrameworkUtils.isSuccess(pagesDto)) {
				dto = pagesDto;
				log.info("getInqueryRentQuoteList success");
			} else {
				FrameworkUtils.setNoData(dto);
				log.error("getInqueryRentQuoteList failure");

			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("getInqueryRentQuoteList Exception!");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 
	 * @author liukh
	 * @date 2017-3-24 下午7:19:50
	 * @param form
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseTMService#getInqueryRentQuoteAndInqueryRentList(com.zj.entity.tm.form.InqueryRentQuoteQueryForm)
	 */
	@Override
	public String getInqueryRentQuoteAndInqueryRentList(
			InqueryRentQuoteQueryForm form) {
		String jsonStr = "";
		BaseObjDto<ItemPage<InqueryRentDetailListDto>> dto = new BaseObjDto<ItemPage<InqueryRentDetailListDto>>();
		try {
			BaseObjDto<ItemPage<InqueryRentQuote>> pagesDto = baseDao
					.getPageList(InqueryRentQuoteMapper.class,
							InqueryRentQuote.class, form,
							"getInqueryRentAndQuotePageList");
			List<InqueryRentDetailDto> doneList = new ArrayList<InqueryRentDetailDto>();
			if (FrameworkUtils.isSuccess(pagesDto)) {
				if (pagesDto.getData() != null
						&& pagesDto.getData().getItems() != null
						&& pagesDto.getData().getItems().size() > 0) {
					List<InqueryRentQuote> list = pagesDto.getData().getItems();
					for (int index = 0; index < list.size(); index++) {
						InqueryRentQuote queryQuote = list.get(index);
						if (queryQuote.getInqueryRent() != null
								&& queryQuote.getInqueryRent().getId() != null) {
							Map<String, Object> paramsMap = new HashMap<String, Object>();
							paramsMap.put("id", queryQuote.getInqueryRent()
									.getId());
							BaseObjDto<InqueryRentDetailDto> inqueryRentDto = baseDao
									.getObjProperty(InqueryRentMapper.class,
											"getInqueryRentDetail", paramsMap);
							if (FrameworkUtils.isSuccess(inqueryRentDto)) {
								InqueryRentDetailDto queryDetailInqueryRent = inqueryRentDto
										.getData();
								queryQuote.setInqueryRent(null);
								queryDetailInqueryRent.setData(queryQuote);
								doneList.add(queryDetailInqueryRent);

							}
						}
					}

				}

				if (pagesDto.getData() != null
						&& pagesDto.getData().getItems() != null) {
					BaseObjDto<ItemPage<InqueryRentDetailDto>> detaiDto = new BaseObjDto<ItemPage<InqueryRentDetailDto>>();
					detaiDto.setRcode(pagesDto.getRcode());
					detaiDto.setRinfo(pagesDto.getRinfo());
					ItemPage<InqueryRentQuote> itempaege = pagesDto.getData();
					ItemPage<InqueryRentDetailDto> newItemPage = new ItemPage<InqueryRentDetailDto>();
					newItemPage.setNextIndex(itempaege.getNextIndex());
					newItemPage.setPageIndex(itempaege.getPageIndex());
					newItemPage.setPagesCount(itempaege.getPagesCount());
					newItemPage.setPageSize(itempaege.getPageSize());
					newItemPage.setPreIndex(itempaege.getPreIndex());
					newItemPage.setRowsCount(itempaege.getRowsCount());
					newItemPage.setItems(doneList);
					detaiDto.setData(newItemPage);
					log.info("getInqueryRentQuoteAndInqueryRentList success");
					return JSON.toJSONString(detaiDto,
							SerializerFeature.DisableCircularReferenceDetect);

				}
			} else {
				FrameworkUtils.setNoData(dto);
				log.error("getInqueryRentQuoteAndInqueryRentList failure");

			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(
					"getInqueryRentQuoteAndInqueryRentList Exception!");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 获取求租需求单报价和求租需求单的复合信息
	 * 
	 * @author liukh
	 * @date 2017-3-24 下午8:14:51
	 * @param inqueryRentQuoteId
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseTMService#getInqueryRentQuotoAndInqueryRent(java.lang.String)
	 */
	@Override
	public String getInqueryRentQuotoAndInqueryRent(String inqueryRentQuoteId) {
		String jsonStr = "";
		BaseObjDto<InqueryRentQuotoAndInqueryRentDto> dto = new BaseObjDto<InqueryRentQuotoAndInqueryRentDto>();
		try {
			if (StringUtils.isBlank(inqueryRentQuoteId)) {
				dto.setRinfo("inqueryRentQuoteId is null");
				return JSON.toJSONString(dto);
			}
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("inqueryRentQuoteId",
					StringUtils.trim(inqueryRentQuoteId));
			BaseObjDto<InqueryRentQuotoAndInqueryRentDto> queryDto = baseDao
					.getObjProperty(InqueryRentQuoteMapper.class,
							"getInqueryRentAndQuoteDetail", params);

			if (FrameworkUtils.isSuccess(queryDto)) {
				InqueryRentQuotoAndInqueryRentDto inqueryRentQuotoAndInqueryRentDto = queryDto
						.getData();
				dto.setData(inqueryRentQuotoAndInqueryRentDto);
				FrameworkUtils.setSuccess(dto);
				log.info("getInqueryRentQuotoAndInqueryRent success!");
			} else {
				FrameworkUtils.setNoData(dto);
				log.error("getInqueryRentQuotoAndInqueryRent failure");
			}
		} catch (Exception e) {
			log.error("getInqueryRentQuotoAndInqueryRent exception!");
			e.printStackTrace();
			throw new RuntimeException(
					"getInqueryRentQuotoAndInqueryRent Exception!");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 求租需求单生成订单
	 * 
	 * @author liukh
	 * @date 2017-3-25 上午11:09:41
	 * @param data
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseTMService#addCreateRentOrder4InqueryRent(java.lang.String)
	 */

	@Override
	public String addCreateRentOrder4InqueryRent(String data) {
		String jsonStr = "";

		BaseDto dto = new BaseDto();
		try {
			if (StringUtils.isBlank(data)) {
				dto.setRcode(BaseDto.ERROR_RCODE);
				dto.setRinfo("The data is null !");
				return JSON.toJSONString(dto);
			}
			Map<String, Object> params = (Map<String, Object>) JSON
					.parseObject(data);
			if (params == null) {
				dto.setRcode(BaseDto.ERROR_RCODE);
				dto.setRinfo("The data parse to Map !");
				return JSON.toJSONString(dto);
			}
			if (params.get("inqueryRentQuoteId") == null) {
				dto.setRcode(BaseDto.ERROR_RCODE);
				dto.setRinfo("The data's inqueryRentQuoteId is null !");
				return JSON.toJSONString(dto);
			}

			Map<String, Object> mapParams = new HashMap<String, Object>();
			mapParams.put("inqueryRentQuoteId", StringUtils.trim(params.get(
					"inqueryRentQuoteId").toString()));
			BaseObjDto<InqueryRentQuotoAndInqueryRentDto> queryDto = baseDao
					.getObjProperty(InqueryRentQuoteMapper.class,
							"getInqueryRentAndQuoteDetail", mapParams);
			if (!FrameworkUtils.isSuccess(queryDto)) {
				dto.setRcode(BaseDto.ERROR_RCODE);
				dto.setRinfo("The data's inqueryRentQuoteId is not exit !");
				return JSON.toJSONString(dto);
			}
			InqueryRentQuotoAndInqueryRentDto inqueryRentQuotoAndInqueryRentDto = queryDto
					.getData();
			BaseObjDto<InqueryRent> queryRentDto = baseDao.selectByPrimaryKey(
					InqueryRentMapper.class,
					inqueryRentQuotoAndInqueryRentDto.getInqueryRentId());
			if (!FrameworkUtils.isSuccess(queryRentDto)) {
				dto.setRcode(BaseDto.ERROR_RCODE);
				dto.setRinfo("The data's inqueryRentId is not exits  use:"
						+ inqueryRentQuotoAndInqueryRentDto.getInqueryRentId());
				return JSON.toJSONString(dto);
			}
			InqueryRent queryInqueryRent = queryRentDto.getData();
			if (queryInqueryRent.getStatus() != null
					&& queryInqueryRent.getStatus() == Constant.INQUERYRENT_STATUS_OVER) {
				dto.setRcode(BaseDto.ERROR_RCODE);
				dto.setRinfo("The InqueryRent of "
						+ queryInqueryRent.getId()
						+ " has been  created been rentOrder,Please do not submit a duplicate !");
				return JSON.toJSONString(dto);
			}

			InqueryRentQuote queryInqueryRentQuote = null;
			if (queryInqueryRent != null
					&& queryInqueryRent.getInqueryRentQuotes() != null) {
				for (int index = 0; index < queryInqueryRent
						.getInqueryRentQuotes().size(); index++) {
					InqueryRentQuote queryCompareQuote = queryInqueryRent
							.getInqueryRentQuotes().get(index);
					if (queryCompareQuote.getId().equals(
							StringUtils.trim(params.get("inqueryRentQuoteId")
									.toString()))) {
						queryInqueryRentQuote = queryCompareQuote;
						break;
					}
				}
			}
			if (queryInqueryRentQuote == null) {
				dto.setRcode(BaseDto.ERROR_RCODE);
				dto.setRinfo("The InqueryRent's getInqueryRentQuotes  not match InqueryRentQuote");
				return JSON.toJSONString(dto);
			}
			boolean isUpdateCapitalPool = false;
			BigDecimal lockedBigDecimal = BigDecimal.ZERO;
			// 线上支付
			boolean isPayMethodOnline = false;
			if (Constant.PAYMETHOD_ONLINE.equals(queryInqueryRentQuote
					.getPayMethod())) {
				isPayMethodOnline = true;
			}
			if (isPayMethodOnline) {
				BigDecimal unitLockedPrice = NumberUtils.getLockedPrice(
						queryInqueryRent.getBillingType(),
						queryInqueryRent.getLeaseTerm(),
						queryInqueryRentQuote.getTotalPrice());

				lockedBigDecimal = lockedBigDecimal.add(unitLockedPrice);

				BaseObjDto<Company> companyDto = baseDao
						.selectByPrimaryKey(CompanyMapper.class,
								queryInqueryRent.getLesseeSideId());
				if (FrameworkUtils.isSuccess(companyDto)) {
					Company company = companyDto.getData();
					if (NumberUtils.checkCompanyMoneyIsEnough(
							company.getDisposableAmount(), lockedBigDecimal)) {
						isUpdateCapitalPool = true;

					} else {
						dto.setRcode(55);
						dto.setRinfo("预付款，余额不足,将锁定：" + lockedBigDecimal
								+ ",请充值！");
						return JSON.toJSONString(dto);
					}
				}
			}

			RentOrder addrentOrder = new RentOrder();
			addrentOrder.copyFromInqueryRentOrder(queryInqueryRent,
					queryInqueryRentQuote);
			addrentOrder.setStatus(Constant.RENTORDER_STATUS_UNGO);
			addrentOrder.setCreateTime(new Date());
			dto = baseDao.insertSelective(RentOrderMapper.class, addrentOrder);
			if (!FrameworkUtils.isSuccess(dto)) {
				return JSON.toJSONString(dto);
			}
			// 订单进度
			OrderProgressTrace orderProgressTrace = new OrderProgressTrace();
			orderProgressTrace.setOrderId(addrentOrder.getId());
			orderProgressTrace.setCreateTime(new Date());
			orderProgressTrace.setStatus(Constant.IRENTORDER_STATUS_CREATE);
			orderProgressTrace
					.setHandlerId(queryInqueryRent.getCreator() != null ? queryInqueryRent
							.getCreator() : null);
			BaseDto baseProTraceDto = baseDao.insertSelective(
					OrderProgressTraceMapper.class, orderProgressTrace);
			if (!FrameworkUtils.isSuccess(baseProTraceDto)) {
				return JSON.toJSONString(baseProTraceDto);
			}

			OrderProgressTrace orderProgressTraceUnGo = new OrderProgressTrace();
			orderProgressTraceUnGo.setOrderId(addrentOrder.getId());
			orderProgressTraceUnGo.setCreateTime(new Date());
			orderProgressTraceUnGo.setStatus(Constant.RENTORDER_STATUS_UNGO);
			orderProgressTraceUnGo
					.setHandlerId(queryInqueryRent.getCreator() != null ? queryInqueryRent
							.getCreator() : null);
			BaseDto baseProTraceUnGoDto = baseDao.insertSelective(
					OrderProgressTraceMapper.class, orderProgressTraceUnGo);
			if (!FrameworkUtils.isSuccess(baseProTraceUnGoDto)) {
				return JSON.toJSONString(baseProTraceUnGoDto);
			}
			// 新增资金池
			if (isUpdateCapitalPool && isPayMethodOnline) {

				// 新增订单资金池的时候，锁定公司的金额，已经做了处理
				OrderCapitalPool orderCapitalPool = new OrderCapitalPool();
				orderCapitalPool.copyFromRentOrder(addrentOrder,
						queryInqueryRent.getLesseeSideId(),
						Constant.ORDERCAPITALPOOLPOJO_LOCKED);
				orderCapitalPool.setAmount(lockedBigDecimal);
				jsonStr = addOrderCapitalPool(JSON
						.toJSONString(orderCapitalPool));
				if (!FrameworkUtils.isSuccess(jsonStr)) {
					log.info("addCreateRentOrder4InqueryRent error when  do addOrderCapitalPool :"
							+ jsonStr);
					return jsonStr;
				}

			}
			// 新增租赁订单设备明细
			RentOrderDevice addRentOrderDevice = new RentOrderDevice();
			addRentOrderDevice.copyFromInqueryRentAndInqueryRentQuote(
					queryInqueryRentQuote, queryInqueryRent);
			addRentOrderDevice.setCreateTime(new Date());
			addRentOrderDevice.setRentOrder(addrentOrder);
			dto = baseDao.insertSelective(RentOrderDeviceMapper.class,
					addRentOrderDevice);
			if (!FrameworkUtils.isSuccess(dto)) {
				return JSON.toJSONString(dto);
			}

			// 更改求租需求单的状态
			InqueryRent updateRents = new InqueryRent();
			updateRents.setId(inqueryRentQuotoAndInqueryRentDto
					.getInqueryRentId());
			updateRents.setStatus(Constant.INQUERYRENT_STATUS_OVER);
			dto = baseDao.updateByPrimaryKeySelective(InqueryRentMapper.class,
					updateRents);
			if (!FrameworkUtils.isSuccess(dto)) {
				return JSON.toJSONString(dto);
			}
			// 更改求租需求单报价单的状态
			if (queryInqueryRent != null
					&& queryInqueryRent.getInqueryRentQuotes() != null) {
				for (int indexs = 0; indexs < queryInqueryRent
						.getInqueryRentQuotes().size(); indexs++) {
					InqueryRentQuote querysQuote = queryInqueryRent
							.getInqueryRentQuotes().get(indexs);
					InqueryRentQuote updateQuote = new InqueryRentQuote();
					updateQuote.setId(querysQuote.getId());
					if (querysQuote.getId().equals(
							StringUtils.trim(params.get("inqueryRentQuoteId")
									.toString()))) {
						updateQuote
								.setStatus(Constant.INQUERYRENTQUOTE_STATUS_MAKESURE);
					} else {
						updateQuote
								.setStatus(Constant.INQUERYRENTQUOTE_STATUS_OVER);
					}
					dto = baseDao.updateByPrimaryKeySelective(
							InqueryRentQuoteMapper.class, updateQuote);
					if (!FrameworkUtils.isSuccess(dto)) {
						return JSON.toJSONString(dto);
					}
				}
			}

			// ////////////////////////////////////////////////////////////////////////////////////////
			// 发送短信通知

			UserDto leasidUser = baseOtherService
					.getUserDtoByComapnyId(inqueryRentQuotoAndInqueryRentDto
							.getLeasingSideId());

			UserDto lessidUser = baseOtherService
					.getUserDtoByComapnyId(inqueryRentQuotoAndInqueryRentDto
							.getLesseeSideId());

			if (leasidUser != null && leasidUser.getCellPhone() != null
					&& lessidUser != null && lessidUser.getCellPhone() != null) {
				// 发送短信通知
				StringBuffer leasidSbf = new StringBuffer();
				leasidSbf.append(leasidUser.getCompanyName());
				leasidSbf.append(":");
				leasidSbf.append(leasidUser.getCellPhone());

				StringBuffer lessidSbf = new StringBuffer();
				lessidSbf.append(lessidUser.getCompanyName());
				lessidSbf.append(":");
				lessidSbf.append(lessidUser.getCellPhone());

				StringBuffer lockedlessidSbf = new StringBuffer();
				lockedlessidSbf.append(lockedBigDecimal);

				if (isPayMethodOnline) {
					// baseSMService.sendOrderMessageOnlineLeasingSideSms(leasidUser.getCellPhone(),lessidSbf.toString(),lockedlessidSbf.toString());
					// baseSMService.sendOrderMessageOnlineLesseeSideSms(lessidUser.getCellPhone(),leasidSbf.toString());
				} else {
					// baseSMService.sendOrderMessageOfflineSms(lessidUser.getCellPhone(),leasidSbf.toString());
					// baseSMService.sendOrderMessageOfflineSms(leasidUser.getCellPhone(),lessidSbf.toString());
				}

				// 内部消息
				String leasidSmsTemplateInfo = "";
				String lessidSmsTemplateInfo = "";
				if (isPayMethodOnline) {
					leasidSmsTemplateInfo = SmsTemplateUtils
							.getOrderMessageOnlineLeasingSideTemplateInfo();
					if (StringUtils.isNotBlank(leasidSmsTemplateInfo)) {
						leasidSmsTemplateInfo = leasidSmsTemplateInfo.replace(
								"{1}", lessidSbf.toString());
						leasidSmsTemplateInfo = leasidSmsTemplateInfo.replace(
								"{2}", lockedlessidSbf.toString());
					}
					lessidSmsTemplateInfo = SmsTemplateUtils
							.getOrderMessageOnlineLesseeSideTemplateInfo();
					if (StringUtils.isNotBlank(lessidSmsTemplateInfo)) {
						lessidSmsTemplateInfo = lessidSmsTemplateInfo.replace(
								"{1}", leasidSbf.toString());
					}
				} else {
					leasidSmsTemplateInfo = SmsTemplateUtils
							.getOrderMessageOfflineTemplateInfo();
					if (StringUtils.isNotBlank(leasidSmsTemplateInfo)) {
						leasidSmsTemplateInfo = leasidSmsTemplateInfo.replace(
								"{1}", lessidSbf.toString());
					}
					lessidSmsTemplateInfo = SmsTemplateUtils
							.getOrderMessageOfflineTemplateInfo();
					if (StringUtils.isNotBlank(lessidSmsTemplateInfo)) {
						lessidSmsTemplateInfo = lessidSmsTemplateInfo.replace(
								"{1}", leasidSbf.toString());
					}

				}

				Message leasidMessage = new Message();
				leasidMessage
						.setType(Constant.MESSAGE_TYPE_RENTORDER_INQUERYORDER);
				leasidMessage.setUserId(leasidUser.getId());
				leasidMessage.setExternalRelatedId(addrentOrder.getId());
				leasidMessage.setOperatorId(lessidUser.getId());
				leasidMessage.setContent(leasidSmsTemplateInfo);
				leasidMessage.setStatus(Constant.MESSAGE_STATUS_UNREAD);
				leasidMessage.setCreateTime(new Date());
				dto = baseDao.insertSelective(MessageMapper.class,
						leasidMessage);
				if (!FrameworkUtils.isSuccess(dto)) {
					return JSON.toJSONString(dto);
				}

				Message lessidMessage = new Message();
				lessidMessage
						.setType(Constant.MESSAGE_TYPE_RENTORDER_INQUERYORDER);
				lessidMessage.setUserId(lessidUser.getId());
				lessidMessage.setExternalRelatedId(addrentOrder.getId());
				lessidMessage.setOperatorId(leasidUser.getId());
				lessidMessage.setContent(lessidSmsTemplateInfo);
				lessidMessage.setStatus(Constant.MESSAGE_STATUS_UNREAD);
				lessidMessage.setCreateTime(new Date());
				dto = baseDao.insertSelective(MessageMapper.class,
						lessidMessage);

			}

		} catch (Exception e) {
			log.error("getInqueryRentQuotoAndInqueryRent exception!");
			e.printStackTrace();
			throw new RuntimeException(
					"getInqueryRentQuotoAndInqueryRent Exception!");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<抛单>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	/**
	 * 增加抛单信息
	 * 
	 * @author liukh
	 * @date 2017-2-24 上午11:26:51
	 * @param data
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseTMService#addInqueryRentThrow(java.lang.String)
	 */
	@Override
	public String addInqueryRentThrow(String data) {
		String jsonStr = "";
		BaseDto dto = new BaseDto();
		if (StringUtils.isNotBlank(data)) {
			try {
				InqueryRentThrow inqueryRentThrow = JSON.parseObject(data,
						InqueryRentThrow.class);
				if (inqueryRentThrow == null) {
					dto.setRcode(1);
					dto.setRinfo("The data parseObject to InqueryRentThrow is null !");
					return JSON.toJSONString(dto);
				}
				Date createDate = new Date();
				inqueryRentThrow.setCreateTime(createDate);
				dto = baseDao.insertSelective(InqueryRentThrowMapper.class,
						inqueryRentThrow);
				if (FrameworkUtils.isSuccess(dto)) {
					log.info("addInqueryRentThrow success!");
				} else {
					log.error("addInqueryRentThrow failure!");
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("addInqueryRentThrow exception!");
				throw new RuntimeException("addInqueryRentThrow Exception!");
			}
		} else {
			log.error("---addInqueryRentThrow -------- data is null ==== ");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 获取抛单信息
	 * 
	 * @author liukh
	 * @date 2017-2-24 上午11:30:08
	 * @param inqueryRentThrowId
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseTMService#getInqueryRentThrow(java.lang.String)
	 */
	@Override
	public String getInqueryRentThrow(String inqueryRentThrowId) {
		String jsonStr = "";
		BaseObjDto<InqueryRentThrow> dto = new BaseObjDto<InqueryRentThrow>();
		try {
			if (StringUtils.isBlank(inqueryRentThrowId)) {
				dto.setRinfo("inqueryRentThrowId is null");
				return JSON.toJSONString(dto);
			}
			BaseObjDto<InqueryRentThrow> inqueryRentThrowDto = baseDao
					.selectByPrimaryKey(InqueryRentThrowMapper.class,
							StringUtils.trim(inqueryRentThrowId));
			if (FrameworkUtils.isSuccess(inqueryRentThrowDto)) {
				InqueryRentThrow inqueryRentThrow = inqueryRentThrowDto
						.getData();
				dto.setData(inqueryRentThrow);
				FrameworkUtils.setSuccess(dto);
				log.info("getInqueryRentThrow success!");
			} else {
				FrameworkUtils.setNoData(dto);
				log.error("getInqueryRentThrow failure");
			}
		} catch (Exception e) {
			log.error("getInqueryRentThrow exception!");
			e.printStackTrace();
			throw new RuntimeException("getInqueryRentThrow Exception!");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 修改抛单信息
	 * 
	 * @author liukh
	 * @date 2017-2-24 上午11:32:46
	 * @param inqueryRentThrowId
	 * @param data
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseTMService#updateInqueryRentThrow(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public String updateInqueryRentThrow(String inqueryRentThrowId, String data) {
		String jsonStr = "";
		BaseDto dto = new BaseDto();
		if (StringUtils.isNotBlank(inqueryRentThrowId)
				&& StringUtils.isNotBlank(data)) {
			try {
				InqueryRentThrow inqueryRentThrow = JSON.parseObject(data,
						InqueryRentThrow.class);
				if (inqueryRentThrow == null) {
					dto.setRcode(1);
					dto.setRinfo("The data parseObject to InqueryRentThrow is null !");
					return JSON.toJSONString(dto);
				}
				inqueryRentThrow.setId(inqueryRentThrowId);
				dto = baseDao.updateByPrimaryKeySelective(
						InqueryRentThrowMapper.class, inqueryRentThrow);
				if (FrameworkUtils.isSuccess(dto)) {
					log.info("updateInqueryRentThrow success!");
				} else {
					log.error("updateInqueryRentThrow failure!");
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("updateInqueryRentThrow exception!");
				throw new RuntimeException("updateInqueryRentThrow Exception!");
			}

		} else {
			log.error("---updateInqueryRentThrow -------- data or inqueryRentThrowId is null ==== ");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 获取抛单信息列表
	 * 
	 * @author liukh
	 * @date 2017-2-24 上午11:39:45
	 * @param form
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseTMService#getInqueryRentThrowList(com.zj.entity.tm.form.InqueryRentThrowQueryForm)
	 */
	@Override
	public String getInqueryRentThrowList(InqueryRentThrowQueryForm form) {
		String jsonStr = "";
		BaseObjDto<ItemPage<InqueryRentThrow>> dto = new BaseObjDto<ItemPage<InqueryRentThrow>>();
		try {
			BaseObjDto<ItemPage<InqueryRentThrow>> pagesDto = baseDao
					.getPageList(InqueryRentThrowMapper.class,
							InqueryRentThrow.class, form,
							"getInqueryRentThrowPageList");
			if (FrameworkUtils.isSuccess(pagesDto)) {
				dto = pagesDto;
				log.info("getInqueryRentThrowList success");
			} else {
				FrameworkUtils.setNoData(dto);
				log.error("getInqueryRentThrowList failure");

			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("getInqueryRentThrowList Exception!");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<租赁订单>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	/**
	 * 新增租赁订单
	 * 
	 * @author liukh
	 * @date 2017-2-24 上午11:43:21
	 * @param data
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseTMService#addRentOrder(java.lang.String)
	 */
	@Override
	public String addRentOrder(String data) {
		String jsonStr = "";
		BaseDto dto = new BaseDto();
		if (StringUtils.isNotBlank(data)) {
			try {
				RentOrder rentOrder = JSON.parseObject(data, RentOrder.class);
				if (rentOrder == null) {
					dto.setRcode(1);
					dto.setRinfo("The data parseObject to RentOrder is null !");
					return JSON.toJSONString(dto);
				}
				Date createDate = new Date();
				rentOrder.setCreateTime(createDate);
				dto = baseDao.insertSelective(RentOrderMapper.class, rentOrder);
				if (FrameworkUtils.isSuccess(dto)) {
					log.info("addRentOrder success!");
				} else {
					log.error("addRentOrder failure!");
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("addRentOrder exception!");
				throw new RuntimeException("addRentOrder Exception!");
			}
		} else {
			log.error("---addRentOrder -------- data is null ==== ");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 获取租赁订单
	 * 
	 * @author liukh
	 * @date 2017-2-24 上午11:51:09
	 * @param rentOrderId
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseTMService#getRentOrder(java.lang.String)
	 */
	@Override
	public String getRentOrder(String rentOrderId) {
		String jsonStr = "";
		BaseObjDto<RentOrderListDto> dto = new BaseObjDto<RentOrderListDto>();
		try {
			if (StringUtils.isBlank(rentOrderId)) {
				dto.setRinfo("rentOrderId is null");
				return JSON.toJSONString(dto);
			}
			BaseObjDto<RentOrderListDto> rentOrderDto = baseDao
					.selectByPrimaryKey(RentOrderMapper.class,
							StringUtils.trim(rentOrderId));
			if (FrameworkUtils.isSuccess(rentOrderDto)) {
				RentOrderListDto rentOrder = rentOrderDto.getData();
				dto.setData(rentOrder);
				FrameworkUtils.setSuccess(dto);
				log.info("getRentOrder success!");
			} else {
				FrameworkUtils.setNoData(dto);
				log.error("getRentOrder failure");
			}
		} catch (Exception e) {
			log.error("getRentOrder exception!");
			e.printStackTrace();
			throw new RuntimeException("getRentOrder Exception!");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 更新租赁订单
	 * 
	 * @author liukh
	 * @date 2017-2-24 上午11:57:46
	 * @param rentOrderId
	 * @param data
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseTMService#updateRentOrder(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public String updateRentOrder(String rentOrderId, String data) {
		String jsonStr = "";
		BaseDto dto = new BaseDto();
		if (StringUtils.isNotBlank(rentOrderId) && StringUtils.isNotBlank(data)) {
			try {
				RentOrder rentOrder = JSON.parseObject(data, RentOrder.class);
				if (rentOrder == null) {
					dto.setRcode(1);
					dto.setRinfo("The data parseObject to RentOrder is null !");
					return JSON.toJSONString(dto);
				}
				rentOrder.setId(rentOrderId);
				dto = baseDao.updateByPrimaryKeySelective(
						RentOrderMapper.class, rentOrder);
				if (FrameworkUtils.isSuccess(dto)) {
					log.info("updateRentOrder success!");
				} else {
					log.error("updateRentOrder failure!");
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("updateRentOrder exception!");
				throw new RuntimeException("updateRentOrder Exception!");
			}

		} else {
			log.error("---updateRentOrder -------- data or rentOrderId is null ==== ");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 获取租赁订单信息列表
	 * 
	 * @author liukh
	 * @date 2017-2-24 下午12:04:47
	 * @param form
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseTMService#getRentOrderList(com.zj.entity.tm.form.RentOrderQueryForm)
	 */
	@Override
	public String getRentOrderList(RentOrderQueryForm form) {
		String jsonStr = "";
		BaseObjDto<ItemPage<RentOrder>> dto = new BaseObjDto<ItemPage<RentOrder>>();
		try {
			BaseObjDto<ItemPage<RentOrder>> pagesDto = baseDao.getPageList(
					RentOrderMapper.class, RentOrder.class, form,
					"getRentOrderPageList");
			if (FrameworkUtils.isSuccess(pagesDto)) {
				dto = pagesDto;
				log.info("getRentOrderList success");
			} else {
				FrameworkUtils.setNoData(dto);
				log.error("getRentOrderList failure");

			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("getRentOrderList Exception!");
			throw new RuntimeException("getRentOrderList Exception!");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 获取租赁订单及设备列表
	 * 
	 * @author liukh
	 * @date 2017-3-27 上午10:23:33
	 * @param rentOrderId
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseTMService#getRentOrderAndRentOrderDeviceList(java.lang.String)
	 */
	@Override
	public String getRentOrderAndRentOrderDeviceList(String rentOrderId) {
		String jsonStr = "";
		BaseObjDto<RentOrderListDto> dto = new BaseObjDto<RentOrderListDto>();
		try {
			if (StringUtils.isBlank(rentOrderId)) {
				dto.setRinfo("rentOrderId is null");
				return JSON.toJSONString(dto);
			}
			BaseObjDto<RentOrderListDto> rentOrderDto = baseDao
					.selectByPrimaryKey(RentOrderMapper.class,
							StringUtils.trim(rentOrderId));
			if (FrameworkUtils.isSuccess(rentOrderDto)) {
				RentOrderListDto rentOrder = rentOrderDto.getData();
				List<RentOrderDeviceListDto> deviceList = rentOrder
						.getRentOrderDevices();
				List<RentOrderDeviceListDto> processDeviceList = new ArrayList<RentOrderDeviceListDto>();
				for (int index = 0; index < deviceList.size(); index++) {
					RentOrderDeviceListDto device = deviceList.get(index);
					DateTypeDto dateType = new DateTypeDto();
					if (device.getBillingType() != null
							&& device.getLeaseTerm() != null) {
						dateType.setType(device.getBillingType().toString());
						if (device.getBillingType() == Constant.BILLINGTYPE_MONTH) {
							dateType.setMonth(new BigDecimal(device
									.getLeaseTerm()));
							dateType.setDay(BigDecimal.ZERO);
						} else if (device.getBillingType() == Constant.BILLINGTYPE_DAY) {
							dateType.setDay(new BigDecimal(device
									.getLeaseTerm()));
							dateType.setMonth(BigDecimal.ZERO);

						}
						dateType.setDateAmount(BigDecimal.ZERO);

					} else {
						dateType.setType(Constant.BILLINGTYPE_MONTH.toString());
						dateType.setMonth(BigDecimal.ZERO);
						dateType.setDay(BigDecimal.ZERO);
						dateType.setDateAmount(BigDecimal.ZERO);
					}

					device.setDateType(dateType);
					processDeviceList.add(device);

				}

				rentOrderDto.getData().setRentOrderDevices(processDeviceList);
				dto = rentOrderDto;
				log.info("getRentOrder success!");
			} else {
				FrameworkUtils.setNoData(dto);
				log.error("getRentOrder failure");
			}
		} catch (Exception e) {
			log.error("getRentOrder exception!");
			e.printStackTrace();
			throw new RuntimeException("getRentOrder Exception!");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 获取租赁订单列表及设备列表
	 * 
	 * @author liukh
	 * @date 2017-3-27 上午10:24:04
	 * @param form
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseTMService#getRentOrderListAndRentOrderDeviceList(com.zj.entity.tm.form.RentOrderDeviceQueryForm)
	 */
	@Override
	public String getRentOrderListAndRentOrderDeviceList(RentOrderQueryForm form) {
		String jsonStr = "";
		BaseObjDto<ItemPage<RentOrderListDto>> dto = new BaseObjDto<ItemPage<RentOrderListDto>>();
		try {
			BaseObjDto<ItemPage<RentOrderListDto>> pagesDto = baseDao
					.getPageList(RentOrderMapper.class, RentOrderListDto.class,
							form, "getRentOrderPageList");
			if (FrameworkUtils.isSuccess(pagesDto)) {
				dto = pagesDto;
				log.info("getRentOrderListAndRentOrderDeviceList success");
			} else {
				FrameworkUtils.setNoData(dto);
				log.error("getRentOrderListAndRentOrderDeviceList failure");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("getRentOrderListAndRentOrderDeviceList Exception");
			throw new RuntimeException(
					"getRentOrderListAndRentOrderDeviceList Exception!");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 增加租赁订单设备信息
	 * 
	 * @author liukh
	 * @date 2017-2-24 下午1:40:05
	 * @param data
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseTMService#addRentOrderDevice(java.lang.String)
	 */
	@Override
	public String addRentOrderDevice(String data) {
		String jsonStr = "";
		BaseDto dto = new BaseDto();
		if (StringUtils.isNotBlank(data)) {
			try {
				RentOrderDevice rentOrderDevice = JSON.parseObject(data,
						RentOrderDevice.class);
				if (rentOrderDevice == null) {
					dto.setRcode(1);
					dto.setRinfo("The data parseObject to RentOrderDevice is null !");
					return JSON.toJSONString(dto);
				}
				JSONObject jp = JSON.parseObject(data);
				if (jp == null) {
					dto.setRcode(1);
					dto.setRinfo("The data parseObject to JSONObject is null !");
					return JSON.toJSONString(dto);
				}

				String rentOrderId = jp.getString("rentOrderId");
				if (StringUtils.isBlank(rentOrderId)) {
					dto.setRcode(1);
					dto.setRinfo("The data's rentOrderId is null !");
					return JSON.toJSONString(dto);
				}
				RentOrder rentOrder = new RentOrder();
				rentOrder.setId(rentOrderId);
				rentOrderDevice.setRentOrder(rentOrder);
				Date createDate = new Date();
				rentOrderDevice.setCreateTime(createDate);
				dto = baseDao.insertSelective(RentOrderDeviceMapper.class,
						rentOrderDevice);
				if (FrameworkUtils.isSuccess(dto)) {
					log.info("addRentOrderDevice success!");
				} else {
					log.error("addRentOrderDevice failure!");
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("addRentOrderDevice exception!");
				throw new RuntimeException("addRentOrderDevice Exception!");
			}
		} else {
			log.error("---addRentOrderDevice -------- data is null ==== ");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 获取租赁订单设备信息
	 * 
	 * @author liukh
	 * @date 2017-2-24 下午1:53:15
	 * @param rentOrderDeviceId
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseTMService#getRentOrderDevice(java.lang.String)
	 */
	@Override
	public String getRentOrderDevice(String rentOrderDeviceId) {
		String jsonStr = "";
		BaseObjDto<RentOrderDevice> dto = new BaseObjDto<RentOrderDevice>();
		try {
			if (StringUtils.isBlank(rentOrderDeviceId)) {
				dto.setRinfo("rentOrderDeviceId is null");
				return JSON.toJSONString(dto);
			}
			BaseObjDto<RentOrderDevice> rentOrderDeviceDto = baseDao
					.selectByPrimaryKey(RentOrderDeviceMapper.class,
							StringUtils.trim(rentOrderDeviceId));
			if (FrameworkUtils.isSuccess(rentOrderDeviceDto)) {
				RentOrderDevice rentOrderDevice = rentOrderDeviceDto.getData();
				dto.setData(rentOrderDevice);
				FrameworkUtils.setSuccess(dto);
				log.info("getRentOrderDevice success!");
			} else {
				FrameworkUtils.setNoData(dto);
				log.error("getRentOrderDevice failure");
			}
		} catch (Exception e) {
			log.error("getRentOrderDevice exception!");
			e.printStackTrace();
			throw new RuntimeException("getRentOrderDevice Exception!");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 获取租赁订单设备列表
	 * 
	 * @author liukh
	 * @date 2017-2-24 下午2:14:30
	 * @param form
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseTMService#getRentOrderDeviceList(com.zj.entity.tm.form.RentOrderDeviceQueryForm)
	 */
	@Override
	public String getRentOrderDeviceList(RentOrderDeviceQueryForm form) {
		String jsonStr = "";
		BaseObjDto<ItemPage<RentOrderDevice>> dto = new BaseObjDto<ItemPage<RentOrderDevice>>();
		try {
			BaseObjDto<ItemPage<RentOrderDevice>> pagesDto = baseDao
					.getPageList(RentOrderDeviceMapper.class,
							RentOrderDevice.class, form,
							"getRentOrderDevicePageList");
			if (FrameworkUtils.isSuccess(pagesDto)) {
				dto = pagesDto;
				log.info("getRentOrderDeviceList success");
			} else {
				FrameworkUtils.setNoData(dto);
				log.error("getRentOrderDeviceList failure");

			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("getRentOrderDeviceList Exception!");
			throw new RuntimeException("getRentOrderDeviceList Exception!");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 增加订单出库单信息
	 * 
	 * @author liukh
	 * @date 2017-2-24 下午1:41:01
	 * @param data
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseTMService#addDeliveryOrder(java.lang.String)
	 */
	@Override
	public String addDeliveryOrder(String data) {
		String jsonStr = "";
		BaseDto dto = new BaseDto();
		if (StringUtils.isNotBlank(data)) {
			try {
				DeliveryOrder deliveryOrder = JSON.parseObject(data,
						DeliveryOrder.class);
				if (deliveryOrder == null) {
					dto.setRcode(1);
					dto.setRinfo("The data parseObject to DeliveryOrder is null !");
					return JSON.toJSONString(dto);
				}
				Date createDate = new Date();
				deliveryOrder.setCreateTime(createDate);
				dto = baseDao.insertSelective(DeliveryOrderMapper.class,
						deliveryOrder);
				if (FrameworkUtils.isSuccess(dto)) {
					log.info("addDeliveryOrder success!");
				} else {
					log.error("addDeliveryOrder failure!");
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("addDeliveryOrder exception!");
				throw new RuntimeException("addDeliveryOrder Exception!");
			}
		} else {
			log.error("---addDeliveryOrder -------- data is null ==== ");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 
	 * @author liukh
	 * @date 2017-3-27 上午10:54:59
	 * @param data
	 *            : { data : [{ "rentOrderId" : "XXX", "rentOrderDeviceId" :
	 *            "XXX", "status" : YYY, "creator" : "YYYY", "data" : [{ "id" :
	 *            "XXXX", "deviceName" : "YYYY" }, { "id" : "XXXX", "deviceName"
	 *            : "YYYY" } ] } ] }
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseTMService#addDeliveryOrderAndUpdateRentOrderStatus(java.lang.String)
	 */

	@Override
	public String addDeliveryOrderAndUpdateRentOrderStatus(String data) {
		String jsonStr = "";
		BaseDto dto = new BaseDto();
		if (StringUtils.isNotBlank(data)) {
			try {
				JSONObject jsob = JSON.parseObject(data);
				boolean isPeerRentOrder = false;
				if (jsob != null && jsob.get("data") != null) {
					List<DeliveryOrderListDto.DeliveryOrderDto> list = JSON
							.parseArray(JSON.toJSONString(jsob.get("data")),
									DeliveryOrderListDto.DeliveryOrderDto.class);
					Map<String, String> cachDeviceMap = new HashMap<String, String>();
					for (int index = 0; index < list.size(); index++) {
						DeliveryOrderListDto.DeliveryOrderDto deliveryOrderDto = list
								.get(index);

						BaseObjDto<RentOrderListDto> rentOrderDto = baseDao
								.selectByPrimaryKey(RentOrderMapper.class,
										StringUtils.trim(deliveryOrderDto
												.getRentOrderId()));
						if (!FrameworkUtils.isSuccess(rentOrderDto)) {
							dto.setRcode(BaseDto.ERROR_RCODE);
							dto.setRinfo("参数中rentOrderId 不正确!");
							return JSON.toJSONString(dto);
						}
						RentOrderListDto queryRentOrder = rentOrderDto
								.getData();
						if (queryRentOrder.getStatus() != null
								&& queryRentOrder.getStatus() >= Constant.RENTORDER_STATUS_WORKING) {
							dto.setRcode(21);
							dto.setRinfo("该订单设备已出库，请勿重复出库!");
							return JSON.toJSONString(dto);

						}

						if (deliveryOrderDto.getData() != null) {
							List<DeliveryOrderListDto.DeliveryOrderDeviceDto> deviceList = deliveryOrderDto
									.getData();
							for (int iindex = 0; iindex < deviceList.size(); iindex++) {
								DeliveryOrderListDto.DeliveryOrderDeviceDto deliveryOrderDeviceDto = deviceList
										.get(iindex);
								if (cachDeviceMap
										.containsKey(deliveryOrderDeviceDto
												.getId())) {
									dto.setRcode(BaseDto.ERROR_RCODE);
									dto.setRinfo("Contain same id(deviceId) in  data  !");
									return JSON.toJSONString(dto);
								} else {
									cachDeviceMap.put(
											deliveryOrderDeviceDto.getId(),
											deliveryOrderDeviceDto.getId());
								}
								BaseObjDto<Device> deviceDto = baseDao
										.selectByPrimaryKey(DeviceMapper.class,
												deliveryOrderDeviceDto.getId());

								if (!FrameworkUtils.isSuccess(deviceDto)) {
									dto.setRcode(BaseDto.ERROR_RCODE);
									dto.setRinfo("The id(deviceId) is not exits !");
									return JSON.toJSONString(dto);
								}
							}
						}

					}
                 Map<String,String> cachRentOrderMap = new HashMap<String,String>();
					// /////////////////////////////////////////////////////////////////
					for (int index = 0; index < list.size(); index++) {
						DeliveryOrderListDto.DeliveryOrderDto deliveryOrderDto = list
								.get(index);

						BaseObjDto<RentOrderListDto> rentOrderDto = baseDao
								.selectByPrimaryKey(RentOrderMapper.class,
										StringUtils.trim(deliveryOrderDto
												.getRentOrderId()));
						RentOrderListDto queryRentOrder = rentOrderDto
								.getData();

						// 判断是否是同行找机的单子
						isPeerRentOrder = isPeerRentOrder(deliveryOrderDto
								.getRentOrderId());
						if (deliveryOrderDto.getData() != null) {
							List<DeliveryOrderListDto.DeliveryOrderDeviceDto> deviceList = deliveryOrderDto
									.getData();
							for (int iindex = 0; iindex < deviceList.size(); iindex++) {
								DeliveryOrderListDto.DeliveryOrderDeviceDto deliveryOrderDeviceDto = deviceList
										.get(iindex);
								BaseObjDto<Device> deviceDto = baseDao
										.selectByPrimaryKey(DeviceMapper.class,
												deliveryOrderDeviceDto.getId());

								Device queryDevice = deviceDto.getData();
								DeliveryOrder addDeliveryOrder = new DeliveryOrder();
								addDeliveryOrder
										.setRentOrderId(deliveryOrderDto
												.getRentOrderId());
								addDeliveryOrder
										.setRentOrderDeviceId(deliveryOrderDto
												.getRentOrderDeviceId());
								addDeliveryOrder.setCreateTime(new Date());
								addDeliveryOrder.setCreator(deliveryOrderDto
										.getCreator());
								addDeliveryOrder.setStatus(deliveryOrderDto
										.getStatus());
								addDeliveryOrder
										.setDeviceId(deliveryOrderDeviceDto
												.getId());
								addDeliveryOrder
										.setDeviceName(deliveryOrderDeviceDto
												.getDeviceName());
								// 新增出库单记录
								dto = baseDao.insertSelective(
										DeliveryOrderMapper.class,
										addDeliveryOrder);
								if (!FrameworkUtils.isSuccess(dto)) {
									return JSON.toJSONString(dto);
								}
								// 如果是同行找机的单子，则把设备转成对方的虚拟设备
								if (isPeerRentOrder) {
									CompanyDeviceType queryCompanyDeviceType = baseOtherService
											.getCompanyDeviceTypeByComapnyIdAndModelId(
													queryRentOrder
															.getLesseeSideId(),
													queryDevice
															.getDeviceModel());
									if (queryCompanyDeviceType != null) {
										Device deviceVirtual = new Device();
										deviceVirtual.copyFrom(queryDevice);
										deviceVirtual
												.setIsRealDevice(Constant.DEVICE_TRUTH_FALSE);
										deviceVirtual.setRealDeviceId(queryDevice.getId());
										deviceVirtual
												.setCreator(deliveryOrderDto
														.getCreator());
										deviceVirtual
												.setCompanyId(queryRentOrder
														.getLesseeSideId());
										deviceVirtual
												.setStatus(Constant.DEVICE_STATUS_WAIT);
										deviceVirtual.setCreateTime(new Date());
										deviceVirtual.setAddress(null);
										deviceVirtual.setLatitude(null);
										deviceVirtual.setLongitude(null);
										dto = baseDao.insertSelective(
												DeviceMapper.class,
												deviceVirtual);
										if (!FrameworkUtils.isSuccess(dto)) {
											return JSON.toJSONString(dto);
										}
										// 订单状态跟踪表
										DeviceStatusTrace addDeviceStatusTrace = new DeviceStatusTrace();
										addDeviceStatusTrace
												.setOrderId(deliveryOrderDto
														.getRentOrderId());
										addDeviceStatusTrace
												.setDeviceId(queryDevice
														.getId());
										addDeviceStatusTrace
												.setDeviceName(queryDevice
														.getDeviceName());
										addDeviceStatusTrace
												.setLeasingId(queryRentOrder
														.getLeasingSideId());
										addDeviceStatusTrace
												.setLesseeId(queryRentOrder
														.getLesseeSideId());
										addDeviceStatusTrace
												.setCreateTime(new Date());
										StringBuffer sbf = new StringBuffer();
										sbf.append("The Virtual device's id: ");
										sbf.append(deviceVirtual.getId());
										sbf.append(" be created ");
										sbf.append("from the real device's id: ");
										sbf.append(queryDevice.getId());
										sbf.append(" and companyId from ");	
										sbf.append(queryRentOrder
												.getLeasingSideId());
										sbf.append(" to ");
										sbf.append(queryRentOrder
												.getLesseeSideId());
										sbf.append(" in rentOrder's  ");
										sbf.append(queryRentOrder.getId());
										
										addDeviceStatusTrace.setComment(sbf
												.toString());
										dto = baseDao.insertSelective(
												DeviceStatusTraceMapper.class,
												addDeviceStatusTrace);
										if (!FrameworkUtils.isSuccess(dto)) {
											return JSON.toJSONString(dto);
										}

									} else {
										CompanyDeviceType queryLeaCompanyDeviceType = baseOtherService
												.getCompanyDeviceTypeByComapnyIdAndModelId(
														queryRentOrder
																.getLeasingSideId(),
														queryDevice
																.getDeviceModel());
										if (queryLeaCompanyDeviceType != null) {
											CompanyDeviceType addCompanyDeviceType = new CompanyDeviceType();
											addCompanyDeviceType
													.setCompanyId(queryRentOrder
															.getLesseeSideId());
											addCompanyDeviceType
													.setCreator(deliveryOrderDto
															.getCreator());
											addCompanyDeviceType
													.setDeviceModelId(queryLeaCompanyDeviceType
															.getDeviceModelId());
											addCompanyDeviceType
													.setDeviceModelName(queryLeaCompanyDeviceType
															.getDeviceModelName());
											addCompanyDeviceType
													.setModelQuote(queryLeaCompanyDeviceType
															.getModelQuote());
											addCompanyDeviceType
													.setPicture(queryLeaCompanyDeviceType
															.getPicture());
											dto = baseDao
													.insertSelective(
															CompanyDeviceTypeMapper.class,
															addCompanyDeviceType);
											if (!FrameworkUtils.isSuccess(dto)) {
												return JSON.toJSONString(dto);
											}

											Device deviceVirtual = new Device();
											deviceVirtual.copyFrom(queryDevice);
											deviceVirtual.setRealDeviceId(queryDevice.getId());
											deviceVirtual
													.setIsRealDevice(Constant.DEVICE_TRUTH_FALSE);
											deviceVirtual
													.setCreator(deliveryOrderDto
															.getCreator());
											deviceVirtual
													.setCompanyId(queryRentOrder
															.getLesseeSideId());
											deviceVirtual
													.setStatus(Constant.DEVICE_STATUS_WAIT);
											deviceVirtual
													.setCreateTime(new Date());
											deviceVirtual.setAddress(null);
											deviceVirtual.setLatitude(null);
											deviceVirtual.setLongitude(null);
											dto = baseDao.insertSelective(
													DeviceMapper.class,
													deviceVirtual);
											if (!FrameworkUtils.isSuccess(dto)) {
												return JSON.toJSONString(dto);
											}
											// 设备跟踪表
											DeviceStatusTrace addDeviceStatusTrace = new DeviceStatusTrace();
											addDeviceStatusTrace
													.setOrderId(deliveryOrderDto
															.getRentOrderId());
											addDeviceStatusTrace
													.setDeviceId(queryDevice
															.getId());
											addDeviceStatusTrace
													.setDeviceName(queryDevice
															.getDeviceName());
											addDeviceStatusTrace
													.setLeasingId(queryRentOrder
															.getLeasingSideId());
											addDeviceStatusTrace
													.setLesseeId(queryRentOrder
															.getLesseeSideId());
											addDeviceStatusTrace
													.setCreateTime(new Date());
											StringBuffer sbf = new StringBuffer();
											sbf.append("The Virtual device's id: ");
											sbf.append(deviceVirtual.getId());
											sbf.append(" be created ");
											sbf.append("from the real device's id: ");
											sbf.append(queryDevice.getId());
											sbf.append(" and companyId from ");	
											sbf.append(queryRentOrder
													.getLeasingSideId());
											sbf.append(" to ");
											sbf.append(queryRentOrder
													.getLesseeSideId());
											sbf.append(" in rentOrder's  ");
											sbf.append(queryRentOrder.getId());
											
											addDeviceStatusTrace.setComment(sbf
													.toString());
											dto = baseDao
													.insertSelective(
															DeviceStatusTraceMapper.class,
															addDeviceStatusTrace);
											if (!FrameworkUtils.isSuccess(dto)) {
												return JSON.toJSONString(dto);
											}
										}

									}

								}

								// 更改此设备的状态为工作中
								Device updateDevice = new Device();
								updateDevice.setId(queryDevice.getId());
								updateDevice
										.setStatus(Constant.DEVICE_STATUS_WORING);
								dto = baseDao.updateByPrimaryKeySelective(
										DeviceMapper.class, updateDevice);
								if (!FrameworkUtils.isSuccess(dto)) {
									return JSON.toJSONString(dto);
								}

							}
						}

						if(!cachRentOrderMap.containsKey(queryRentOrder.getId())){
							RentOrder updateRentOrder = new RentOrder();
							updateRentOrder.setId(queryRentOrder.getId());
							updateRentOrder
									.setStatus(Constant.RENTORDER_STATUS_WORKING);
							dto = baseDao.updateByPrimaryKeySelective(
									RentOrderMapper.class, updateRentOrder);
							if (!FrameworkUtils.isSuccess(dto)) {
								return JSON.toJSONString(dto);
							}

							OrderProgressTrace addOrderProgressTrace = new OrderProgressTrace();
							addOrderProgressTrace
									.setOrderId(queryRentOrder.getId());
							addOrderProgressTrace.setHandlerId(deliveryOrderDto
									.getCreator());
							addOrderProgressTrace
									.setStatus(Constant.RENTORDER_STATUS_GO);
							addOrderProgressTrace.setCreateTime(new Date());
							dto = baseDao.insertSelective(
									OrderProgressTraceMapper.class,
									addOrderProgressTrace);
							if (!FrameworkUtils.isSuccess(dto)) {
								return JSON.toJSONString(dto);
							}
							OrderProgressTrace addOrderProgressTraceAgain = new OrderProgressTrace();
							addOrderProgressTraceAgain.setOrderId(queryRentOrder
									.getId());
							addOrderProgressTraceAgain
									.setHandlerId(deliveryOrderDto.getCreator());
							addOrderProgressTraceAgain
									.setStatus(Constant.RENTORDER_STATUS_WORKING);
							addOrderProgressTraceAgain.setCreateTime(new Date());
							dto = baseDao.insertSelective(
									OrderProgressTraceMapper.class,
									addOrderProgressTraceAgain);
							cachRentOrderMap.put(queryRentOrder.getId(), queryRentOrder.getId());
						}
						
					}
				} else {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data parse to JSONObject is null !");
				}

			} catch (Exception e) {
				log.error("addDeliveryOrderAndUpdateRentOrderStatus ---- 异常,message = "
						+ e.getMessage());
				e.printStackTrace();
			}

		} else {
			log.error("---addDeliveryOrder -------- data is null ==== ");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 获取订单出库单信息
	 * 
	 * @author liukh
	 * @date 2017-2-24 下午1:53:47
	 * @param deliveryOrderId
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseTMService#getDeliveryOrder(java.lang.String)
	 */
	@Override
	public String getDeliveryOrder(String deliveryOrderId) {
		String jsonStr = "";
		BaseObjDto<DeliveryOrder> dto = new BaseObjDto<DeliveryOrder>();
		try {
			if (StringUtils.isBlank(deliveryOrderId)) {
				dto.setRinfo("deliveryOrderId is null");
				return JSON.toJSONString(dto);
			}
			BaseObjDto<DeliveryOrder> deliveryOrderDto = baseDao
					.selectByPrimaryKey(DeliveryOrderMapper.class,
							StringUtils.trim(deliveryOrderId));
			if (FrameworkUtils.isSuccess(deliveryOrderDto)) {
				DeliveryOrder deliveryOrder = deliveryOrderDto.getData();
				dto.setData(deliveryOrder);
				FrameworkUtils.setSuccess(dto);
				log.info("getDeliveryOrder success!");
			} else {
				FrameworkUtils.setNoData(dto);
				log.error("getDeliveryOrder failure");
			}
		} catch (Exception e) {
			log.error("getDeliveryOrder exception!");
			e.printStackTrace();
			throw new RuntimeException("getDeliveryOrder Exception!");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 修改订单出库单信息
	 * 
	 * @author liukh
	 * @date 2017-2-24 下午2:06:23
	 * @param deliveryOrderId
	 * @param data
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseTMService#updateDeliveryOrder(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public String updateDeliveryOrder(String deliveryOrderId, String data) {
		String jsonStr = "";
		BaseDto dto = new BaseDto();
		if (StringUtils.isNotBlank(deliveryOrderId)
				&& StringUtils.isNotBlank(data)) {
			try {
				DeliveryOrder deliveryOrder = JSON.parseObject(data,
						DeliveryOrder.class);
				if (deliveryOrder == null) {
					dto.setRcode(1);
					dto.setRinfo("The data parseObject to DeliveryOrder is null !");
					return JSON.toJSONString(dto);
				}
				deliveryOrder.setId(deliveryOrderId);
				dto = baseDao.updateByPrimaryKeySelective(
						DeliveryOrderMapper.class, deliveryOrder);
				if (FrameworkUtils.isSuccess(dto)) {
					log.info("updateDeliveryOrder success!");
				} else {
					log.error("updateDeliveryOrder failure!");
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("updateDeliveryOrder exception!");
				throw new RuntimeException("updateDeliveryOrder Exception!");
			}

		} else {
			log.error("---updateDeliveryOrder -------- data or deliveryOrderId is null ==== ");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 获取订单出库单信息列表信息
	 * 
	 * @author liukh
	 * @date 2017-2-24 下午2:16:03
	 * @param form
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseTMService#getDeliveryOrderList(com.zj.entity.tm.form.DeliveryOrderQueryForm)
	 */
	@Override
	public String getDeliveryOrderList(DeliveryOrderQueryForm form) {
		String jsonStr = "";
		BaseObjDto<ItemPage<DeliveryOrder>> dto = new BaseObjDto<ItemPage<DeliveryOrder>>();
		try {
			BaseObjDto<ItemPage<DeliveryOrder>> pagesDto = baseDao.getPageList(
					DeliveryOrderMapper.class, DeliveryOrder.class, form,
					"getDeliveryOrderPageList");
			if (FrameworkUtils.isSuccess(pagesDto)) {
				dto = pagesDto;
				log.info("getDeliveryOrderList success");
			} else {
				FrameworkUtils.setNoData(dto);
				log.error("getDeliveryOrderList failure");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("getDeliveryOrderList Exception");
			throw new RuntimeException("getDeliveryOrderList Exception!");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 增加订单资金池信息
	 * 
	 * @author liukh
	 * @date 2017-2-24 下午1:42:51
	 * @param data
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseTMService#addOrderCapitalPool(java.lang.String)
	 */
	@Override
	public String addOrderCapitalPool(String data) {
		String jsonStr = "";
		BaseDto dto = new BaseDto();
		if (StringUtils.isNotBlank(data)) {
			try {
				OrderCapitalPool orderCapitalPool = JSON.parseObject(data,
						OrderCapitalPool.class);
				if (orderCapitalPool == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data parseObject to OrderCapitalPool is null !");
					return JSON.toJSONString(dto);
				}
				if (orderCapitalPool.getRentOrderId() == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's  rentOrderId  is null !");
					return JSON.toJSONString(dto);
				}

				if (orderCapitalPool.getType() == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's  type  is null !");
					return JSON.toJSONString(dto);
				}

				if (orderCapitalPool.getCapitalSideId() == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's  capitalSideId  is null !");
					return JSON.toJSONString(dto);
				}

				if (orderCapitalPool.getAmount() == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's  amount  is null !");
					return JSON.toJSONString(dto);
				}
				if (orderCapitalPool.getStatus() == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's  status  is null !");
					return JSON.toJSONString(dto);
				}
				BaseObjDto<Company> companyDto = baseDao.selectByPrimaryKey(
						CompanyMapper.class,
						orderCapitalPool.getCapitalSideId());
				if (!FrameworkUtils.isSuccess(companyDto)) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's  capitalSideId not exit !");
					return JSON.toJSONString(dto);
				}

				Date createDate = new Date();
				orderCapitalPool.setCreateTime(createDate);
				dto = baseDao.insertSelective(OrderCapitalPoolMapper.class,
						orderCapitalPool);
				if (FrameworkUtils.isSuccess(dto)) {
					log.info("addOrderCapitalPool success!");
					if (orderCapitalPool.isLocked()) {
						Company company = companyDto.getData();
						Company updateCompany = new Company();
						if (company != null) {
							BigDecimal disposableAmount = company
									.getDisposableAmount();
							BigDecimal totalAmount = company.getTotalAmount();
							BigDecimal transactionAmount = orderCapitalPool
									.getAmount();
							transactionAmount = transactionAmount
									.multiply(new BigDecimal(-1));
							disposableAmount = disposableAmount
									.add(transactionAmount);
							totalAmount = totalAmount.add(transactionAmount);
							updateCompany.setDisposableAmount(disposableAmount);
							updateCompany.setTotalAmount(totalAmount);
							updateCompany.setId(company.getId());
							dto = baseDao.updateByPrimaryKeySelective(
									CompanyMapper.class, updateCompany);
						}

					}
				} else {
					log.error("addOrderCapitalPool failure!");
				}

			} catch (Exception e) {
				e.printStackTrace();
				log.error("addOrderCapitalPool exception!");
				throw new RuntimeException("addOrderCapitalPool Exception!");
			}
		} else {
			log.error("---addOrderCapitalPool -------- data is null ==== ");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 获取订单资金池信息
	 * 
	 * @author liukh
	 * @date 2017-2-24 下午1:55:52
	 * @param orderCapitalPoolId
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseTMService#getOrderCapitalPool(java.lang.String)
	 */
	@Override
	public String getOrderCapitalPool(String orderCapitalPoolId) {
		String jsonStr = "";
		BaseObjDto<OrderCapitalPool> dto = new BaseObjDto<OrderCapitalPool>();
		try {
			if (StringUtils.isBlank(orderCapitalPoolId)) {
				dto.setRinfo("deliveryOrderId is null");
				return JSON.toJSONString(dto);
			}
			BaseObjDto<OrderCapitalPool> deliveryOrderDto = baseDao
					.selectByPrimaryKey(OrderCapitalPoolMapper.class,
							StringUtils.trim(orderCapitalPoolId));
			if (FrameworkUtils.isSuccess(deliveryOrderDto)) {
				OrderCapitalPool orderCapitalPool = deliveryOrderDto.getData();
				dto.setData(orderCapitalPool);
				FrameworkUtils.setSuccess(dto);
				log.info("getOrderCapitalPool success!");
			} else {
				FrameworkUtils.setNoData(dto);
				log.error("getOrderCapitalPool failure");
			}
		} catch (Exception e) {
			log.error("getOrderCapitalPool exception!");
			e.printStackTrace();
			throw new RuntimeException("getOrderCapitalPool Exception!");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 修改订单资金池信息
	 * 
	 * @author liukh
	 * @date 2017-2-24 下午2:08:20
	 * @param orderCapitalPoolId
	 * @param data
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseTMService#updateOrderCapitalPool(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public String updateOrderCapitalPool(String orderCapitalPoolId, String data) {
		String jsonStr = "";
		BaseDto dto = new BaseDto();
		if (StringUtils.isNotBlank(orderCapitalPoolId)
				&& StringUtils.isNotBlank(data)) {
			try {
				OrderCapitalPool orderCapitalPool = JSON.parseObject(data,
						OrderCapitalPool.class);
				if (orderCapitalPool == null) {
					dto.setRcode(1);
					dto.setRinfo("The data parseObject to OrderCapitalPool is null !");
					return JSON.toJSONString(dto);
				}
				orderCapitalPool.setId(orderCapitalPoolId);
				dto = baseDao.updateByPrimaryKeySelective(
						OrderCapitalPoolMapper.class, orderCapitalPool);
				if (FrameworkUtils.isSuccess(dto)) {
					log.info("updateOrderCapitalPool success!");
				} else {
					log.error("updateOrderCapitalPool failure!");
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("updateOrderCapitalPool exception!");
				throw new RuntimeException("updateOrderCapitalPool Exception!");
			}

		} else {
			log.error("---updateOrderCapitalPool -------- data or orderCapitalPoolId is null ==== ");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 获取订单资金池信息 列表
	 * 
	 * @author liukh
	 * @date 2017-2-24 下午2:18:11
	 * @param form
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseTMService#getOrderCapitalPoolList(com.zj.entity.tm.form.OrderCapitalPoolQueryForm)
	 */
	@Override
	public String getOrderCapitalPoolList(OrderCapitalPoolQueryForm form) {
		String jsonStr = "";
		BaseObjDto<ItemPage<OrderCapitalPool>> dto = new BaseObjDto<ItemPage<OrderCapitalPool>>();
		try {
			BaseObjDto<ItemPage<OrderCapitalPool>> pagesDto = baseDao
					.getPageList(OrderCapitalPoolMapper.class,
							OrderCapitalPool.class, form,
							"getOrderCapitalPoolPageList");
			if (FrameworkUtils.isSuccess(pagesDto)) {
				dto = pagesDto;
				log.info("getOrderCapitalPoolList success");
			} else {
				FrameworkUtils.setNoData(dto);
				log.error("getOrderCapitalPoolList failure");

			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("getOrderCapitalPoolList Exception!");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 获取订单资金池及其相关订单的设备信息
	 * 
	 * @author liukh
	 * @date 2017-3-27 下午5:20:15
	 * @param form
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseTMService#getOrderCapitalPoolAndRentOrderDeviceList(com.zj.entity.tm.form.OrderCapitalPoolQueryForm)
	 */
	@Override
	public String getOrderCapitalPoolAndRentOrderDeviceList(
			OrderCapitalPoolQueryForm form) {
		String jsonStr = "";
		BaseObjDto<ItemPage<OrderCapitalPoolListDto>> dto = new BaseObjDto<ItemPage<OrderCapitalPoolListDto>>();
		try {
			BaseObjDto<ItemPage<OrderCapitalPool>> pagesDto = baseDao
					.getPageList(OrderCapitalPoolMapper.class,
							OrderCapitalPool.class, form,
							"getOrderCapitalPoolPageList");
			if (FrameworkUtils.isSuccess(pagesDto)) {
				if (pagesDto.getData() != null
						&& pagesDto.getData().getItems() != null
						&& pagesDto.getData().getItems().size() > 0) {
					List<OrderCapitalPoolListDto> processOrderPoolList = new ArrayList<OrderCapitalPoolListDto>();

					List<OrderCapitalPool> queryPoolList = pagesDto.getData()
							.getItems();
					for (int index = 0; index < queryPoolList.size(); index++) {
						OrderCapitalPoolListDto processCapitalPoolListDto = new OrderCapitalPoolListDto();
						OrderCapitalPool queryOrderCapitalPool = queryPoolList
								.get(index);
						processCapitalPoolListDto
								.copyFromOther(queryOrderCapitalPool);
						BaseObjDto<RentOrderListDto> rentOrderDto = baseDao
								.selectByPrimaryKey(RentOrderMapper.class,
										queryOrderCapitalPool.getRentOrderId());
						if (FrameworkUtils.isSuccess(rentOrderDto)) {
							RentOrderListDto queryRentOrder = rentOrderDto
									.getData();
							if (queryRentOrder != null) {
								if (queryRentOrder.getLeasingSideId() != null
										&& queryRentOrder
												.getLeasingSideId()
												.equals(processCapitalPoolListDto
														.getCapitalSideId())) {
									processCapitalPoolListDto
											.setCapitalSideName(queryRentOrder
													.getLeasingSideName());
									processCapitalPoolListDto
											.setCapitalOppositeSideName(queryRentOrder
													.getLesseeSideName());
								} else if (queryRentOrder.getLesseeSideId() != null
										&& queryRentOrder
												.getLesseeSideId()
												.equals(processCapitalPoolListDto
														.getCapitalSideId())) {
									processCapitalPoolListDto
											.setCapitalSideName(queryRentOrder
													.getLesseeSideName());
									processCapitalPoolListDto
											.setCapitalOppositeSideName(queryRentOrder
													.getLeasingSideName());
								}

							}

							if (queryRentOrder.getRentOrderDevices() != null
									&& queryRentOrder.getRentOrderDevices()
											.size() > 0) {
								List<OrderCapitalPoolListDto.RentOrderDeviceReleateOrderCapitalPool> releateOrderPoolList = new ArrayList<OrderCapitalPoolListDto.RentOrderDeviceReleateOrderCapitalPool>();
								List<RentOrderDeviceListDto> queryRentDeviceList = queryRentOrder
										.getRentOrderDevices();
								for (int iindex = 0; iindex < queryRentDeviceList
										.size(); iindex++) {
									OrderCapitalPoolListDto.RentOrderDeviceReleateOrderCapitalPool processDeviePool = new OrderCapitalPoolListDto.RentOrderDeviceReleateOrderCapitalPool();
									RentOrderDevice rentOrderDevice = queryRentDeviceList
											.get(iindex);
									processDeviePool
											.copyFromDevie(rentOrderDevice);
									releateOrderPoolList.add(processDeviePool);

								}
								processCapitalPoolListDto
										.setData(releateOrderPoolList);

							}
						}

						processOrderPoolList.add(processCapitalPoolListDto);
					}

					ItemPage<OrderCapitalPoolListDto> processItemPage = new ItemPage<OrderCapitalPoolListDto>();
					processItemPage.copyForm(pagesDto.getData());
					processItemPage.setItems(processOrderPoolList);
					dto.setData(processItemPage);
					dto.setRcode(BaseDto.SUCCESS_RCODE);
					dto.setRinfo(BaseDto.DEFAULT_SUCCESS_INFO);
				}

				log.info("getOrderCapitalPoolAndRentOrderDeviceList success");
			} else {
				FrameworkUtils.setNoData(dto);
				log.error("getOrderCapitalPoolAndRentOrderDeviceList failure");

			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(
					"getOrderCapitalPoolAndRentOrderDeviceList Exception!");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<结算单>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	/**
	 * 增加结算单信息
	 * 
	 * @author liukh
	 * @date 2017-2-24 下午1:44:10
	 * @param data
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseTMService#addOrderStatement(java.lang.String)
	 */
	@Override
	public String addOrderStatement(String data) {
		String jsonStr = "";
		BaseDto dto = new BaseDto();
		if (StringUtils.isNotBlank(data)) {
			try {
				OrderStatement orderStatement = JSON.parseObject(data,
						OrderStatement.class);
				if (orderStatement == null) {
					dto.setRcode(1);
					dto.setRinfo("The data parseObject to OrderStatement is null !");
					return JSON.toJSONString(dto);
				}
				Date createDate = new Date();
				orderStatement.setCreateTime(createDate);
				dto = baseDao.insertSelective(OrderStatementMapper.class,
						orderStatement);
				if (FrameworkUtils.isSuccess(dto)) {
					log.info("addOrderStatement success!");
				} else {
					log.error("addOrderStatement failure!");
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("addOrderStatement exception!");
				throw new RuntimeException("addOrderStatement Exception!");
			}
		} else {
			log.error("---addOrderStatement -------- data is null ==== ");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	@Override
	public String addOrderStatementProcess(String data) {
		String jsonStr = "";
		BaseDto dto = new BaseDto();
		if (StringUtils.isNotBlank(data)) {
			try {
				OrderStatement orderStatement = JSON.parseObject(data,
						OrderStatement.class);
				if (orderStatement == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data parseObject to OrderStatement is null !");
					return JSON.toJSONString(dto);
				} else if (orderStatement.getRentOrderId() == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's rentOrderId is null !");
					return JSON.toJSONString(dto);
				} else if (orderStatement.getStatementType() == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's statementType is null !");
					return JSON.toJSONString(dto);
				} else if ((orderStatement.getStatementType() != Constant.ORDERSTATEMENT_STATUS_SOMETIME)
						&& (orderStatement.getStatementType() != Constant.ORDERSTATEMENT_STATUS_ALLTIME)) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's statementType not right value !");
					return JSON.toJSONString(dto);
				} else if (orderStatement.getStartTime() == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's startTime is null!");
					return JSON.toJSONString(dto);
				} else if (orderStatement.getEndTime() == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's endTime is null!");
					return JSON.toJSONString(dto);
				} else if (orderStatement.getStatementAmount() == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's endTime is null!");
					return JSON.toJSONString(dto);
				} else if (orderStatement.getStatus() == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's status is null!");
					return JSON.toJSONString(dto);
				}

				BaseObjDto<RentOrderListDto> rentOrderDto = baseDao
						.selectByPrimaryKey(RentOrderMapper.class, StringUtils
								.trim(orderStatement.getRentOrderId()));
				if (!FrameworkUtils.isSuccess(rentOrderDto)) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's rentOrderId is not exits !");
					return JSON.toJSONString(dto);
				}
				RentOrderListDto queryRentOrder = rentOrderDto.getData();
				if (queryRentOrder.getStatus() != null
						&& ((queryRentOrder.getStatus() == Constant.RENTORDER_STATUS_OVER) || (queryRentOrder
								.getStatus() == Constant.RENTORDER_STATUS_BALANCEING))) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The rentOrder has been Settlement !");
					return JSON.toJSONString(dto);
				}
				if (queryRentOrder != null
						&& queryRentOrder.getLesseeSideId() != null) {
					UserDto userDto = baseOtherService
							.getUserDtoByComapnyId(queryRentOrder
									.getLesseeSideId());
					if (userDto != null) {
						orderStatement.setCurrentHandler(userDto.getId());
					}

				}
				orderStatement.setCreateTime(new Date());
				dto = baseDao.insertSelective(OrderStatementMapper.class,
						orderStatement);
				if (!FrameworkUtils.isSuccess(dto)) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The add  OrderStatement error !");
					return JSON.toJSONString(dto);
				}
				RentOrder updateRentOrder = new RentOrder();
				OrderProgressTrace addOrderProgressTrace = new OrderProgressTrace();
				updateRentOrder.setId(StringUtils.trim(orderStatement
						.getRentOrderId()));
				// 更改订单状态 ,如果是结算单状态为结项结算，订单状态改为为结算中。如果结算单状态为阶段性结算，订单状态改为部分结算
				if (orderStatement.getStatementType() == Constant.ORDERSTATEMENT_STATUS_SOMETIME) {
					updateRentOrder
							.setStatus(Constant.RENTORDER_STATUS_PARTBALANCE);
					addOrderProgressTrace
							.setStatus(Constant.RENTORDER_STATUS_PARTBALANCE);

				} else if (orderStatement.getStatementType() == Constant.ORDERSTATEMENT_STATUS_ALLTIME) {

					updateRentOrder
							.setStatus(Constant.RENTORDER_STATUS_BALANCEING);
					addOrderProgressTrace
							.setStatus(Constant.RENTORDER_STATUS_BALANCEING);
				}
				dto = baseDao.updateByPrimaryKeySelective(
						RentOrderMapper.class, updateRentOrder);
				if (!FrameworkUtils.isSuccess(dto)) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The update rentOrder error !");
					return JSON.toJSONString(dto);
				}
				addOrderProgressTrace.setOrderId(orderStatement
						.getRentOrderId());
				addOrderProgressTrace.setHandlerId(orderStatement.getCreator());
				addOrderProgressTrace.setCreateTime(new Date());
				dto = baseDao.insertSelective(OrderProgressTraceMapper.class,
						addOrderProgressTrace);

			} catch (Exception e) {
				e.printStackTrace();
				log.error("addOrderStatementProcess exception!");
				throw new RuntimeException(
						"addOrderStatementProcess Exception!");
			}
		} else {
			log.error("---addOrderStatementProcess -------- data is null ==== ");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 获取结算单信息
	 * 
	 * @author liukh
	 * @date 2017-2-24 下午1:57:36
	 * @param orderStatementId
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseTMService#getOrderStatement(java.lang.String)
	 */
	@Override
	public String getOrderStatement(String orderStatementId) {
		String jsonStr = "";
		BaseObjDto<OrderStatement> dto = new BaseObjDto<OrderStatement>();
		try {
			if (StringUtils.isBlank(orderStatementId)) {
				dto.setRinfo("orderStatementId is null");
				return JSON.toJSONString(dto);
			}
			BaseObjDto<OrderStatement> orderStatementDto = baseDao
					.selectByPrimaryKey(OrderStatementMapper.class,
							StringUtils.trim(orderStatementId));
			if (FrameworkUtils.isSuccess(orderStatementDto)) {
				OrderStatement orderStatement = orderStatementDto.getData();
				dto.setData(orderStatement);
				FrameworkUtils.setSuccess(dto);
				log.info("getOrderStatement success!");
			} else {
				FrameworkUtils.setNoData(dto);
				log.error("getOrderStatement failure");
			}
		} catch (Exception e) {
			log.error("getOrderStatement exception!");
			e.printStackTrace();
			throw new RuntimeException("getOrderStatement Exception!");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 修改结算单信息
	 * 
	 * @author liukh
	 * @date 2017-2-24 下午2:10:32
	 * @param orderStatementId
	 * @param data
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseTMService#updateOrderStatement(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public String updateOrderStatement(String orderStatementId, String data) {
		String jsonStr = "";
		BaseDto dto = new BaseDto();
		if (StringUtils.isNotBlank(orderStatementId)
				&& StringUtils.isNotBlank(data)) {
			try {
				OrderStatement orderStatement = JSON.parseObject(data,
						OrderStatement.class);
				if (orderStatement == null) {
					dto.setRcode(1);
					dto.setRinfo("The data parseObject to OrderCapitalPool is null !");
					return JSON.toJSONString(dto);
				}
				orderStatement.setId(orderStatementId);
				dto = baseDao.updateByPrimaryKeySelective(
						OrderStatementMapper.class, orderStatement);
				if (FrameworkUtils.isSuccess(dto)) {
					log.info("updateOrderStatement success!");
				} else {
					log.error("updateOrderStatement failure!");
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("updateOrderStatement exception!");
				throw new RuntimeException("updateOrderStatement Exception!");
			}

		} else {
			log.error("---updateOrderStatement -------- data or orderStatementId is null ==== ");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 获取结算单信息列表
	 * 
	 * @author liukh
	 * @date 2017-2-24 下午2:19:27
	 * @param form
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseTMService#getOrderStatementList(com.zj.entity.tm.form.OrderStatementQueryForm)
	 */
	@Override
	public String getOrderStatementList(OrderStatementQueryForm form) {
		String jsonStr = "";
		BaseObjDto<ItemPage<OrderStatement>> dto = new BaseObjDto<ItemPage<OrderStatement>>();
		try {
			BaseObjDto<ItemPage<OrderStatement>> pagesDto = baseDao
					.getPageList(OrderStatementMapper.class,
							OrderStatement.class, form,
							"getOrderStatementPageList");
			if (FrameworkUtils.isSuccess(pagesDto)) {
				dto = pagesDto;
				log.info("getOrderStatementList success");
			} else {
				FrameworkUtils.setNoData(dto);
				log.error("getOrderStatementList failure");

			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("getOrderStatementList Exception!");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 获取结算单的详细信息（包含租赁方名称，出租方名称）
	 * 
	 * @author liukh
	 * @date 2017-3-28 上午10:11:46
	 * @param form
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseTMService#getOrderStatementContainCompanyNameList(com.zj.entity.tm.form.OrderStatementQueryForm)
	 */
	@Override
	public String getOrderStatementContainCompanyNameList(
			OrderStatementQueryForm form) {
		String jsonStr = "";
		BaseObjDto<ItemPage<OrderStatementContainComanyNameDto>> dto = new BaseObjDto<ItemPage<OrderStatementContainComanyNameDto>>();
		try {
			BaseObjDto<ItemPage<OrderStatementContainComanyNameDto>> pagesDto = baseDao
					.getPageList(OrderStatementMapper.class,
							OrderStatementContainComanyNameDto.class, form,
							"getOrderStatementDetailPageList");
			if (FrameworkUtils.isSuccess(pagesDto)) {
				dto = pagesDto;
				log.info("getOrderStatementContainCompanyNameList success");
			} else {
				FrameworkUtils.setNoData(dto);
				log.error("getOrderStatementContainCompanyNameList failure");

			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("getOrderStatementContainCompanyNameList exception!");
			throw new RuntimeException(
					"getOrderStatementContainCompanyNameList Exception!");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 获取结算单的详细信息
	 * 
	 * @author liukh
	 * @date 2017-3-28 上午10:22:49
	 * @param orderStatementId
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseTMService#getOrderStatementDetailInfor(java.lang.String)
	 */
	@Override
	public String getOrderStatementDetailInfor(String orderStatementId) {
		String jsonStr = "";
		BaseObjDto<OrderStatementContainComanyNameDto> dto = new BaseObjDto<OrderStatementContainComanyNameDto>();
		try {
			if (StringUtils.isBlank(orderStatementId)) {
				dto.setRinfo("orderStatementId is null");
				return JSON.toJSONString(dto);
			}
			Map<String, Object> paramsMap = new HashMap<String, Object>();
			paramsMap.put("id", StringUtils.trim(orderStatementId));
			BaseObjDto<OrderStatementContainComanyNameDto> orderStatementDto = baseDao
					.getObjProperty(OrderStatementMapper.class,
							"getOrderStatementDetailInfor", paramsMap);

			if (FrameworkUtils.isSuccess(orderStatementDto)) {
				OrderStatementContainComanyNameDto orderStatement = orderStatementDto
						.getData();
				dto.setData(orderStatement);
				FrameworkUtils.setSuccess(dto);
				log.info("getOrderStatementDetailInfor success!");
			} else {
				FrameworkUtils.setNoData(dto);
				log.error("getOrderStatementDetailInfor failure");
			}
		} catch (Exception e) {
			log.error("getOrderStatementDetailInfor exception!");
			e.printStackTrace();
			throw new RuntimeException(
					"getOrderStatementDetailInfor Exception!");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;

	}

	/**
	 * 结算单确认对账
	 * 
	 * @author liukh
	 * @date 2017-3-28 上午10:24:38
	 * @param data
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseTMService#updateMakeSureOrderStatementInfor(java.lang.String)
	 */
	@Override
	public String updateMakeSureOrderStatementInfor(String data) {
		String jsonStr = "";
		BaseDto dto = new BaseDto();
		if (StringUtils.isNotBlank(data)) {
			try {
				Map<String, Object> dataParams = JSON.parseObject(data);
				if (dataParams == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data parse to Map  is null !");
					return JSON.toJSONString(dto);
				} else if (dataParams.get("orderStatementId") == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's  orderStatementId is null !");
					return JSON.toJSONString(dto);
				}

				BaseObjDto<OrderStatement> orderStatementDto = baseDao
						.selectByPrimaryKey(
								OrderStatementMapper.class,
								StringUtils.trim(dataParams.get(
										"orderStatementId").toString()));
				if (!FrameworkUtils.isSuccess(orderStatementDto)) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's  orderStatementId is not exits !");
					return JSON.toJSONString(dto);
				}
				OrderStatement queryOrderStatement = orderStatementDto
						.getData();

				BaseObjDto<RentOrderListDto> rentOrderDto = baseDao
						.selectByPrimaryKey(RentOrderMapper.class,
								queryOrderStatement.getRentOrderId());
				if (!FrameworkUtils.isSuccess(rentOrderDto)) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The query rentOrder by OrderStatement's  rentOrderId error !");
					return JSON.toJSONString(dto);
				}
				RentOrderListDto queryRentOrder = rentOrderDto.getData();
				if (queryOrderStatement.getStatus() != null
						&& queryOrderStatement.getStatus() == Constant.ORDERSTATEMENT_STATUS_CREATE) {
					OrderStatement updateOrderStatement = new OrderStatement();
					updateOrderStatement.setId(queryOrderStatement.getId());

					// 线上支付
					if (Constant.PAYMETHOD_ONLINE.equals(queryRentOrder
							.getPayMethod())) {
						updateOrderStatement
								.setStatus(Constant.ORDERSTATEMENT_STATUS_WAITPAY);
					} else {
						// 线下协商

						UserDto userDto = baseOtherService
								.getUserDtoByComapnyId(queryRentOrder
										.getLeasingSideId());

						if (userDto != null) {
							updateOrderStatement.setCurrentHandler(userDto
									.getId());
						}
						updateOrderStatement
								.setStatus(Constant.ORDERSTATEMENT_STATUS_WAITMAKESURE);
					}
					dto = baseDao.updateByPrimaryKeySelective(
							OrderStatementMapper.class, updateOrderStatement);
				} else if (queryOrderStatement.getStatus() > Constant.ORDERSTATEMENT_STATUS_CREATE) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("此结算单已经确认!");
					return JSON.toJSONString(dto);
				}

			} catch (Exception e) {
				e.printStackTrace();
				log.error("updateMakeSureOrderStatementInfor exception!");
				throw new RuntimeException(
						"updateMakeSureOrderStatementInfor Exception!");
			}

		} else {
			log.error("---updateMakeSureOrderStatementInfor -------- data  is null ==== ");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	@Override
	public String tipMakeSurePayMoenyTip4OrderStatement(String orderStatementId) {
		BaseObjDto<Object> dto = new BaseObjDto<Object>();
		try {
			if (StringUtils.isBlank(orderStatementId)) {
				dto.setRcode(BaseDto.ERROR_RCODE);
				dto.setRinfo("The orderStatementId is null !");
				return JSON.toJSONString(dto);
			}

			BaseObjDto<OrderStatement> orderStatatementDto = baseDao
					.selectByPrimaryKey(OrderStatementMapper.class,
							StringUtils.trim(orderStatementId));
			if (!FrameworkUtils.isSuccess(orderStatatementDto)) {
				dto.setRcode(BaseDto.ERROR_RCODE);
				dto.setRinfo("The orderStatementId is not exits  !");
				return JSON.toJSONString(dto);
			}
			OrderStatement queryOrderStatement = orderStatatementDto.getData();

			BaseObjDto<RentOrderListDto> rentOrderDto = baseDao
					.selectByPrimaryKey(RentOrderMapper.class, StringUtils
							.trim(queryOrderStatement.getRentOrderId()));
			if (!FrameworkUtils.isSuccess(rentOrderDto)) {
				dto.setRcode(BaseDto.ERROR_RCODE);
				dto.setRinfo("The RentOrder is null query by OrderStatement's  rentOrderId !");
				return JSON.toJSONString(dto);
			}
			RentOrderListDto queryRentOrder = rentOrderDto.getData();
			if (queryRentOrder != null
					&& queryRentOrder.getLesseeSideId() != null) {
				BaseObjDto<Company> companyDto = baseDao.selectByPrimaryKey(
						CompanyMapper.class, queryRentOrder.getLesseeSideId());
				Company queryCompany = companyDto.getData();
				LockedMoneyTigDto lockedDto = new LockedMoneyTigDto();
				lockedDto.setTip("可用金额不足，请充值!");
				if (queryCompany != null) {
					if (NumberUtils.checkCompanyMoneyIsEnough(
							queryCompany.getDisposableAmount(),
							queryOrderStatement.getStatementAmount())) {
						dto.setRcode(BaseDto.SUCCESS_RCODE);
						dto.setRinfo(BaseDto.DEFAULT_SUCCESS_INFO);
						StringBuffer sbf = new StringBuffer();
						sbf.append("支付");
						sbf.append(queryOrderStatement.getStatementAmount());
						lockedDto.setAmount(queryOrderStatement
								.getStatementAmount());
						lockedDto.setTip(sbf.toString());

					} else {
						dto.setRcode(55);
						dto.setRinfo("余额不足!");
						lockedDto.setAmount(queryOrderStatement
								.getStatementAmount());
						lockedDto.setTip("可用金额低于:"
								+ queryOrderStatement.getStatementAmount()
								+ "，请充值!");

					}
				}

				dto.setData(lockedDto);
				return JSON.toJSONString(dto);
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.error("tipMakeSurePayMoenyTip4OrderStatement exception!");
			throw new RuntimeException("tipMakeSurePayMoenyTip4OrderStatement Exception!");
		}

		return JSON.toJSONString(dto);
	}

	/**
	 * 结算单确认收款
	 * 
	 * @author liukh
	 * @date 2017-3-28 下午2:14:58
	 * @param orderStatementId
	 * @param data
	 *            {status,userId:XXX}
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseTMService#updateOrderStatementAndRentOrderAndReleaseLockMoney(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public String updateOrderStatementAndRentOrderAndReleaseLockMoney(
			String orderStatementId, String data) {
		BaseDto dto = new BaseDto();
		try {

			if (StringUtils.isNotBlank(orderStatementId)
					&& StringUtils.isNotBlank(data)) {
				Map<String, Object> paramsMap = (Map<String, Object>) JSON
						.parseObject(data);
				if (paramsMap == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data parse to Map is null !");
					return JSON.toJSONString(dto);
				} else if (paramsMap.get("status") == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's status  is null !");
					return JSON.toJSONString(dto);
				} else if (paramsMap.get("userId") == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's userId  is null !");
					return JSON.toJSONString(dto);
				}

				BaseObjDto<OrderStatement> orderStatatementDto = baseDao
						.selectByPrimaryKey(OrderStatementMapper.class,
								StringUtils.trim(orderStatementId));
				if (!FrameworkUtils.isSuccess(orderStatatementDto)) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The orderStatementId is not exits  !");
					return JSON.toJSONString(dto);
				}
				OrderStatement queryOrderStatement = orderStatatementDto
						.getData();

				BaseObjDto<RentOrderListDto> rentOrderDto = baseDao
						.selectByPrimaryKey(RentOrderMapper.class, StringUtils
								.trim(queryOrderStatement.getRentOrderId()));
				if (!FrameworkUtils.isSuccess(rentOrderDto)) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The RentOrder is null query by OrderStatement's  rentOrderId !");
					return JSON.toJSONString(dto);
				}
				RentOrderListDto queryRentOrder = rentOrderDto.getData();

				if (queryRentOrder == null
						|| (queryRentOrder != null && queryRentOrder
								.getPayMethod() == null)) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The RentOrder value(payMethod)  contain null  !");
					return JSON.toJSONString(dto);
				}
				OrderStatement updateOrderStatement = new OrderStatement();
				updateOrderStatement.setId(StringUtils.trim(orderStatementId));
				updateOrderStatement.setStatus(Integer.valueOf(paramsMap.get(
						"status").toString()));
				dto = baseDao.updateByPrimaryKeySelective(
						OrderStatementMapper.class, updateOrderStatement);
				if (!FrameworkUtils.isSuccess(dto)) {
					return JSON.toJSONString(dto);
				}
				// 是否是同行找机
				boolean isPeerOrder = false;
				if (queryOrderStatement.getRentOrderId() != null) {
					isPeerOrder = isPeerRentOrder(queryOrderStatement
							.getRentOrderId());

				}
				// 如果是结项结算
				if (queryOrderStatement.isAllTime()) {

					// 更改订单状态
					RentOrder updateRentOrder = new RentOrder();
					updateRentOrder.setStatus(Constant.RENTORDER_STATUS_OVER);
					updateRentOrder.setId(queryOrderStatement.getRentOrderId());
					dto = baseDao.updateByPrimaryKeySelective(
							RentOrderMapper.class, updateRentOrder);
					if (!FrameworkUtils.isSuccess(dto)) {
						return JSON.toJSONString(dto);
					}
					// 新增订单状态跟踪表
					OrderProgressTrace addOrderProgressTrace = new OrderProgressTrace();
					addOrderProgressTrace.setCreateTime(new Date());
					addOrderProgressTrace.setHandlerId(paramsMap.get("userId")
							.toString());
					addOrderProgressTrace.setOrderId(queryOrderStatement
							.getRentOrderId());
					addOrderProgressTrace
							.setStatus(Constant.RENTORDER_STATUS_OVER);
					dto = baseDao.insertSelective(
							OrderProgressTraceMapper.class,
							addOrderProgressTrace);
					if (!FrameworkUtils.isSuccess(dto)) {
						return JSON.toJSONString(dto);
					}

					DeliveryOrderQueryForm form = new DeliveryOrderQueryForm();
					form.setRentOrderId(queryOrderStatement.getRentOrderId());
					BaseObjDto<List<DeliveryOrder>> deliverOrderListDto = baseDao
							.getList(DeliveryOrderMapper.class,
									DeliveryOrder.class,
									"getDeliveryOrderAllList", form);
					if (FrameworkUtils.isSuccess(deliverOrderListDto)) {
						List<DeliveryOrder> deliveryOrderList = deliverOrderListDto
								.getData();
						for (int indexd = 0; indexd < deliveryOrderList.size(); indexd++) {
							DeliveryOrder deliveryOrder = deliveryOrderList
									.get(indexd);
							// 同行找机的单子，删除虚拟设备
							if (isPeerOrder) {
								DeviceQueryForm devicefrom = new DeviceQueryForm();
								devicefrom.setIsRealDevice(Constant.DEVICE_TRUTH_FALSE);
								devicefrom.setRealDeviceId(deliveryOrder
										.getDeviceId());
								BaseObjDto<ItemPage<DeviceListDto>> deviceListDto = baseDao
										.getPageList(DeviceMapper.class,
												DeviceListDto.class,
												devicefrom, "getDevicePageList");
								if (FrameworkUtils.isSuccess(deviceListDto)
										&& deviceListDto.getData() != null
										&& deviceListDto.getData().getItems() != null
										&& deviceListDto.getData().getItems()
												.size() > 0) {
									DeviceListDto device = deviceListDto
											.getData().getItems().get(0);
									dto = baseDao.deleteByPrimaryKey(
											DeviceMapper.class, device.getId());
									if (!FrameworkUtils.isSuccess(dto)) {
										return JSON.toJSONString(dto);
									}
								}
							}
							// 更改真实设备的状态
							Device updateDevice = new Device();
							updateDevice.setId(deliveryOrder.getDeviceId());
							updateDevice.setStatus(Constant.DEVICE_STATUS_WAIT);
							dto = baseDao.updateByPrimaryKeySelective(
									DeviceMapper.class, updateDevice);
							if (!FrameworkUtils.isSuccess(dto)) {
								return JSON.toJSONString(dto);
							}

						}

					}

					// 线上支付释放锁定金额
					if (queryRentOrder != null
							&& Constant.PAYMETHOD_ONLINE.equals(queryRentOrder
									.getPayMethod())) {
						OrderCapitalPoolQueryForm poolForm = new OrderCapitalPoolQueryForm();
						poolForm.setRentOrderId(queryRentOrder.getId());
						poolForm.setStatus(Constant.ORDERCAPITALPOOLPOJO_LOCKED);
						BaseObjDto<List<OrderCapitalPool>> orderPoolListDto = baseDao
								.getList(OrderCapitalPoolMapper.class,
										OrderCapitalPool.class,
										"getOrderCapitalPoolAllList", poolForm);
						if (FrameworkUtils.isSuccess(orderPoolListDto)
								&& orderPoolListDto.getData() != null
								&& orderPoolListDto.getData().size() > 0) {
							for (int indexx = 0; indexx < orderPoolListDto
									.getData().size(); indexx++) {
								OrderCapitalPool orderCapitalPool = orderPoolListDto
										.getData().get(indexx);

								// 把原来的订单资金池有锁定变为解锁状态
								OrderCapitalPool updateOrderCapitalPool = new OrderCapitalPool();
								updateOrderCapitalPool.setId(orderCapitalPool
										.getId());
								updateOrderCapitalPool
										.setStatus(Constant.ORDERCAPITALPOOLPOJO_UNLOCKED);
								dto = baseDao.updateByPrimaryKeySelective(
										OrderCapitalPoolMapper.class,
										updateOrderCapitalPool);
								if (!FrameworkUtils.isSuccess(dto)) {
									return JSON.toJSONString(dto);
								}

								BaseObjDto<Company> updateCompanyDto = baseDao
										.selectByPrimaryKey(
												CompanyMapper.class,
												orderCapitalPool
														.getCapitalSideId());
								if (!FrameworkUtils.isSuccess(updateCompanyDto)) {
									return JSON.toJSONString(updateCompanyDto);
								}
								Company queryCompany = updateCompanyDto
										.getData();
								if (queryCompany != null) {
									Company updateCompany = new Company();
									BigDecimal lockedAmount = queryCompany
											.getLockedAmount();
									lockedAmount = lockedAmount
											.subtract(orderCapitalPool
													.getAmount());
									updateCompany.setLockedAmount(lockedAmount);
									BigDecimal disposeableAmoun = queryCompany
											.getDisposableAmount();
									disposeableAmoun = disposeableAmoun
											.add(lockedAmount);
									updateCompany
											.setDisposableAmount(disposeableAmoun);
									dto = baseDao.updateByPrimaryKeySelective(
											CompanyMapper.class, updateCompany);
									if (FrameworkUtils.isSuccess(dto)) {
										return JSON.toJSONString(dto);
									}

								}

							}
						}

					}

				}
			} else {
				dto.setRcode(BaseDto.ERROR_RCODE);
				dto.setRinfo("The data or is orderStatementId null !");
				return JSON.toJSONString(dto);
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.error("updateOrderStatementAndRentOrderAndReleaseLockMoney exception!");
			throw new RuntimeException(
					"updateOrderStatementAndRentOrderAndReleaseLockMoney Exception!");
		}
		return JSON.toJSONString(dto);
	}

	/**
	 * 结算单支付 承租方账户金额减少，租赁方金额增加
	 * 
	 * @author liukh
	 * @date 2017-3-28 下午2:14:01
	 * @param data
	 *            {"orderStatementId":XXXX,"companyId":YYY}
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseTMService#updateOrderStatementPay(java.lang.String)
	 */
	@Override
	public String updateOrderStatementPay(String data) {
		BaseDto dto = new BaseDto();
		try {
			if (StringUtils.isBlank(data)) {
				dto.setRcode(BaseDto.ERROR_RCODE);
				dto.setRinfo("The data is null !");
				return JSON.toJSONString(dto);
			}
			Map<String, Object> paramsMap = (Map<String, Object>) JSON
					.parseObject(data);
			if (paramsMap == null) {
				dto.setRcode(BaseDto.ERROR_RCODE);
				dto.setRinfo("The data parse to Map is null !");
				return JSON.toJSONString(dto);
			} else if (paramsMap.get("orderStatementId") == null) {
				dto.setRcode(BaseDto.ERROR_RCODE);
				dto.setRinfo("The data's orderStatementId  is null !");
				return JSON.toJSONString(dto);
			} else if (paramsMap.get("companyId") == null) {
				dto.setRcode(BaseDto.ERROR_RCODE);
				dto.setRinfo("The data's companyId  is null !");
				return JSON.toJSONString(dto);
			}

			BaseObjDto<OrderStatement> orderStatatementDto = baseDao
					.selectByPrimaryKey(OrderStatementMapper.class, StringUtils
							.trim(paramsMap.get("orderStatementId").toString()));
			if (!FrameworkUtils.isSuccess(orderStatatementDto)) {
				dto.setRcode(BaseDto.ERROR_RCODE);
				dto.setRinfo("The orderStatementId is not exits  !");
				return JSON.toJSONString(dto);
			}
			OrderStatement queryOrderStatement = orderStatatementDto.getData();
			if (queryOrderStatement == null
					|| (queryOrderStatement != null && queryOrderStatement
							.getRentOrderId() == null)
					|| (queryOrderStatement != null && queryOrderStatement
							.getStatementAmount() == null)) {
				dto.setRcode(BaseDto.ERROR_RCODE);
				dto.setRinfo("The OrderStatement value(rentOrderId,statementAmount)  contain null  !");
				return JSON.toJSONString(dto);
			}

			BaseObjDto<RentOrderListDto> rentOrderDto = baseDao
					.selectByPrimaryKey(RentOrderMapper.class, StringUtils
							.trim(queryOrderStatement.getRentOrderId()));
			if (!FrameworkUtils.isSuccess(rentOrderDto)) {
				dto.setRcode(BaseDto.ERROR_RCODE);
				dto.setRinfo("The RentOrder is null query by OrderStatement's  rentOrderId !");
				return JSON.toJSONString(dto);
			}
			RentOrderListDto queryRentOrder = rentOrderDto.getData();

			if (queryRentOrder == null
					|| (queryRentOrder != null && queryRentOrder
							.getLeasingSideId() == null)
					|| (queryRentOrder != null && queryRentOrder
							.getLesseeSideId() == null)) {
				dto.setRcode(BaseDto.ERROR_RCODE);
				dto.setRinfo("The RentOrder value(leasingSideId,lesseeSideId)  contain null  !");
				return JSON.toJSONString(dto);
			}

			if (!(queryRentOrder.getLesseeSideId().equals(paramsMap.get(
					"companyId").toString()))) {
				dto.setRcode(BaseDto.ERROR_RCODE);
				dto.setRinfo("The data's companyId not match  rentOrderPo's lesseeSideId!");
				return JSON.toJSONString(dto);
			}
			BigDecimal transactionAmount = queryOrderStatement
					.getStatementAmount();
			AccountDetail addLesAccountDetail = new AccountDetail();
			addLesAccountDetail.setCompanyId(queryRentOrder.getLesseeSideId());
			addLesAccountDetail
					.setBusinessType(Constant.ACCOUNTDETAIL_BUSSINESS_PAYMENT);
			addLesAccountDetail
					.setExpenseType(Constant.ACCOUNTDETAIL_EXPENSETYPE_SUBTRACTION);
			addLesAccountDetail.setTransactionAmount(transactionAmount);
			addLesAccountDetail.setSequenceNum(queryOrderStatement.getId());
			addLesAccountDetail.setRecordTime(new Date());
			String memoStr1 = queryRentOrder.getLesseeSideId() + " pay Money:"
					+ transactionAmount + " to "
					+ queryRentOrder.getLeasingSideId();
			addLesAccountDetail.setMemo(memoStr1);
			BaseObjDto<Company> lesCompanyDto = baseDao.selectByPrimaryKey(
					CompanyMapper.class, queryRentOrder.getLesseeSideId());
			if (!FrameworkUtils.isSuccess(lesCompanyDto)) {
				return JSON.toJSONString(lesCompanyDto);
			}

			dto = baseDao.insertSelective(AccountDetailMapper.class,
					addLesAccountDetail);
			if (!FrameworkUtils.isSuccess(dto)) {
				return JSON.toJSONString(dto);
			}

			Company lesCompany = lesCompanyDto.getData();
			if (lesCompany != null) {
				Company lesUpdateCompany = new Company();
				lesUpdateCompany.setId(lesCompany.getId());
				BigDecimal disposableAmount = lesCompany.getDisposableAmount();
				BigDecimal totalAmount = lesCompany.getTotalAmount();
				BigDecimal lesTransactionAmount = transactionAmount;
				lesTransactionAmount = lesTransactionAmount
						.multiply(new BigDecimal(-1));
				disposableAmount = disposableAmount.add(lesTransactionAmount);
				totalAmount = totalAmount.add(lesTransactionAmount);
				lesUpdateCompany.setDisposableAmount(disposableAmount);
				lesUpdateCompany.setTotalAmount(totalAmount);
				dto = baseDao.updateByPrimaryKeySelective(CompanyMapper.class,
						lesUpdateCompany);
			}

			AccountDetail addLeaAccountDetail = new AccountDetail();
			addLeaAccountDetail.setCompanyId(queryRentOrder.getLeasingSideId());
			addLeaAccountDetail
					.setBusinessType(Constant.ACCOUNTDETAIL_BUSSINESS_GATHERING);
			addLeaAccountDetail
					.setExpenseType(Constant.ACCOUNTDETAIL_EXPENSETYPE_ADD);
			addLeaAccountDetail.setTransactionAmount(transactionAmount);
			addLeaAccountDetail.setSequenceNum(queryOrderStatement.getId());
			addLeaAccountDetail.setRecordTime(new Date());
			String memoStr2 = queryRentOrder.getLeasingSideId()
					+ " get Moeny : " + transactionAmount + " from "
					+ queryRentOrder.getLesseeSideId();

			addLeaAccountDetail.setMemo(memoStr2);
			BaseObjDto<Company> leaCompanyDto = baseDao.selectByPrimaryKey(
					CompanyMapper.class, queryRentOrder.getLeasingSideId());
			if (!FrameworkUtils.isSuccess(leaCompanyDto)) {
				return JSON.toJSONString(leaCompanyDto);
			}

			dto = baseDao.insertSelective(AccountDetailMapper.class,
					addLeaAccountDetail);
			if (!FrameworkUtils.isSuccess(dto)) {
				return JSON.toJSONString(dto);
			}

			Company leaCompany = leaCompanyDto.getData();
			if (leaCompany != null) {
				Company leaUpdateCompany = new Company();
				leaUpdateCompany.setId(leaCompany.getId());
				BigDecimal disposableAmount = leaCompany.getDisposableAmount();
				BigDecimal totalAmount = leaCompany.getTotalAmount();
				BigDecimal leaTransactionAmount = transactionAmount;
				leaTransactionAmount = leaTransactionAmount
						.multiply(new BigDecimal(-1));
				disposableAmount = disposableAmount.add(leaTransactionAmount);
				totalAmount = totalAmount.add(leaTransactionAmount);
				leaUpdateCompany.setDisposableAmount(disposableAmount);
				leaUpdateCompany.setTotalAmount(totalAmount);
				dto = baseDao.updateByPrimaryKeySelective(CompanyMapper.class,
						leaUpdateCompany);
			}

			// 更改结算单状态
			UserDto leaUser = baseOtherService
					.getUserDtoByComapnyId(queryRentOrder.getLeasingSideId());
			OrderStatement updateOrderStatement = new OrderStatement();
			updateOrderStatement.setId(queryOrderStatement.getId());
			updateOrderStatement
					.setStatus(Constant.ORDERSTATEMENT_STATUS_WAITMAKESURE);
			if (leaUser != null && leaUser.getId() != null) {
				updateOrderStatement.setCurrentHandler(leaUser.getId());
			}
			dto = baseDao.updateByPrimaryKeySelective(
					OrderStatementMapper.class, updateOrderStatement);

		} catch (Exception e) {
			e.printStackTrace();
			log.error("updateOrderStatementPay exception!");
			throw new RuntimeException("updateOrderStatementPay Exception!");
		}

		return JSON.toJSONString(dto);
	}

	// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	/**
	 * 增加订单互动内容跟踪信息
	 * 
	 * @author liukh
	 * @date 2017-2-24 下午1:45:26
	 * @param data
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseTMService#addOrderInteractiveTrace(java.lang.String)
	 */
	@Override
	public String addOrderInteractiveTrace(String data) {
		String jsonStr = "";
		BaseDto dto = new BaseDto();
		if (StringUtils.isNotBlank(data)) {
			try {
				OrderInteractiveTrace orderInteractiveTrace = JSON.parseObject(
						data, OrderInteractiveTrace.class);
				if (orderInteractiveTrace == null) {
					dto.setRcode(1);
					dto.setRinfo("The data parseObject to OrderInteractiveTrace is null !");
					return JSON.toJSONString(dto);
				}
				Date createDate = new Date();
				orderInteractiveTrace.setCreateTime(createDate);
				dto = baseDao.insertSelective(
						OrderInteractiveTraceMapper.class,
						orderInteractiveTrace);
				if (FrameworkUtils.isSuccess(dto)) {
					log.info("addOrderInteractiveTrace success!");
				} else {
					log.error("addOrderInteractiveTrace failure!");
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("addOrderInteractiveTrace exception!");
				throw new RuntimeException(
						"addOrderInteractiveTrace Exception!");
			}
		} else {
			log.error("---addOrderInteractiveTrace -------- data is null ==== ");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	@Override
	public String addOrderInteractiveTraceAndMessage(String data) {
		String jsonStr = "";
		BaseDto dto = new BaseDto();
		if (StringUtils.isNotBlank(data)) {
			try {
				OrderInteractiveTrace orderInteractiveTrace = JSON.parseObject(
						data, OrderInteractiveTrace.class);
				if (orderInteractiveTrace == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data parseObject to OrderInteractiveTrace is null !");
					return JSON.toJSONString(dto);
				}
				if (orderInteractiveTrace.getRelatedStatementId() == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's relatedStatementId is null !");
					return JSON.toJSONString(dto);
				}
				if (orderInteractiveTrace.getRelatedStatementType() == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's relatedStatementType is null !");
					return JSON.toJSONString(dto);
				}
				if (orderInteractiveTrace.getPublisherId() == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's publisherId is null !");
					return JSON.toJSONString(dto);
				}
				if (orderInteractiveTrace.getPosterCompanyId() == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's posterCompanyId is null !");
					return JSON.toJSONString(dto);
				}
				if (orderInteractiveTrace.getPublisherCompanyName() == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's publisherCompanyName is null !");
					return JSON.toJSONString(dto);
				}

				orderInteractiveTrace.setCreateTime(new Date());
				dto = baseDao.insertSelective(
						OrderInteractiveTraceMapper.class,
						orderInteractiveTrace);
				if (!FrameworkUtils.isSuccess(dto)) {
					return JSON.toJSONString(dto);
				}
				// 内部消息，如果存在此相关单的Id,则更改此相关单的内容及状态为未读，否则新增
				boolean isUpdate = false;
				Message addMessage = new Message();
				Message updateMessage = new Message();
				MessageQueryForm queryMessageForm = new MessageQueryForm();
				String companyId = orderInteractiveTrace.getPosterCompanyId();
				queryMessageForm.setExternalRelatedId(orderInteractiveTrace.getRelatedStatementId());
				if (OrderInteractiveTrace
						.isType4Inqueryorder(orderInteractiveTrace
								.getRelatedStatementType())) {
					// from inqueryOrder
					addMessage.setType(Constant.MESSAGE_TYPE_INQUERYORDER);
					queryMessageForm
							.setType(Constant.MESSAGE_TYPE_INQUERYORDER);

					BaseObjDto<InqueryOrder> inqueryOrderDto = baseDao
							.selectByPrimaryKey(InqueryOrderMapper.class,
									orderInteractiveTrace
											.getRelatedStatementId());
					if (FrameworkUtils.isSuccess(inqueryOrderDto)) {
						InqueryOrder queryInqueryOrder = inqueryOrderDto
								.getData();

						String publisherComapnyId = "";
						if (queryInqueryOrder != null
								&& queryInqueryOrder.getLeasingSideId() != null
								&& queryInqueryOrder.getLesseeSideId() != null) {
							// 取对方的用户公司Id
							if (queryInqueryOrder.getLeasingSideId().equals(
									companyId)) {
								publisherComapnyId = queryInqueryOrder
										.getLesseeSideId();
							} else {
								publisherComapnyId = queryInqueryOrder
										.getLeasingSideId();
							}
							UserDto userDto = baseOtherService
									.getUserDtoByComapnyId(publisherComapnyId);

							if (userDto != null) {
								addMessage.setUserId(userDto.getId());
								queryMessageForm.setUserId(userDto.getId());
							}

						}

						BaseObjDto<ItemPage<Message>> messageListDto = baseDao
								.getPageList(MessageMapper.class,
										Message.class, queryMessageForm,
										"getMessagePageList");
						if (FrameworkUtils.isSuccess(messageListDto)
								&& messageListDto.getData() != null
								&& messageListDto.getData().getItems() != null
								&& messageListDto.getData().getItems().size() > 0) {
							Message queryMessage = messageListDto.getData()
									.getItems().get(0);
							updateMessage.setId(queryMessage.getId());
							isUpdate = true;
						}

					}

				} else if (OrderInteractiveTrace
						.isType4Orderstatement(orderInteractiveTrace
								.getRelatedStatementType())) {
					// from orderStatement

					addMessage.setType(Constant.MESSAGE_TYPE_ORDERSTATEMENT);
					queryMessageForm
							.setType(Constant.MESSAGE_TYPE_ORDERSTATEMENT);

					BaseObjDto<OrderStatement> orderStatementDto = baseDao
							.selectByPrimaryKey(OrderStatementMapper.class,
									orderInteractiveTrace
											.getRelatedStatementId());
					if (FrameworkUtils.isSuccess(orderStatementDto)) {
						OrderStatement queryOrderStatement = orderStatementDto
								.getData();
						String publisherComapnyId = "";
						if (queryOrderStatement != null
								&& queryOrderStatement.getRentOrderId() != null) {

							BaseObjDto<RentOrderListDto> rentOrderDto = baseDao
									.selectByPrimaryKey(RentOrderMapper.class,
											queryOrderStatement
													.getRentOrderId());
							if (FrameworkUtils.isSuccess(rentOrderDto)) {
								RentOrderListDto queryRentOrderbyStatement = rentOrderDto
										.getData();
								if (queryRentOrderbyStatement != null
										&& queryRentOrderbyStatement
												.getLeasingSideId() != null
										&& queryRentOrderbyStatement
												.getLesseeSideId() != null) {
									// 取对方的用户公司Id
									if (queryRentOrderbyStatement
											.getLeasingSideId().equals(
													companyId)) {
										publisherComapnyId = queryRentOrderbyStatement
												.getLesseeSideId();
									} else {
										publisherComapnyId = queryRentOrderbyStatement
												.getLeasingSideId();
									}
									UserDto userByStatementDto = baseOtherService
											.getUserDtoByComapnyId(publisherComapnyId);

									if (userByStatementDto != null) {
										addMessage.setUserId(userByStatementDto
												.getId());
										queryMessageForm
												.setUserId(userByStatementDto
														.getId());
									}

								}
								BaseObjDto<ItemPage<Message>> messageListByStatemetDto = baseDao
										.getPageList(MessageMapper.class,
												Message.class,
												queryMessageForm,
												"getMessagePageList");
								if (FrameworkUtils
										.isSuccess(messageListByStatemetDto)
										&& messageListByStatemetDto.getData() != null
										&& messageListByStatemetDto.getData()
												.getItems() != null
										&& messageListByStatemetDto.getData()
												.getItems().size() > 0) {
									Message queryMessage = messageListByStatemetDto
											.getData().getItems().get(0);
									updateMessage.setId(queryMessage.getId());
									isUpdate = true;
								}

							}

						}
					}

				} else {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's relatedStatementType not  right values");
					return JSON.toJSONString(dto);
				}

				if (isUpdate) {
					updateMessage.setContent(orderInteractiveTrace
							.getPublishContent());
					updateMessage.setStatus(Constant.MESSAGE_STATUS_UNREAD);
					dto = baseDao.updateByPrimaryKeySelective(
							MessageMapper.class, updateMessage);
				} else {
					addMessage.setExternalRelatedId(orderInteractiveTrace
							.getRelatedStatementId());
					addMessage.setOperatorId(orderInteractiveTrace
							.getPublisherId());
					addMessage.setContent(orderInteractiveTrace
							.getPublishContent());
					addMessage.setStatus(Constant.MESSAGE_STATUS_UNREAD);
					addMessage.setCreateTime(new Date());
					dto = baseDao.insertSelective(MessageMapper.class,
							addMessage);
				}

			} catch (Exception e) {
				e.printStackTrace();
				log.error("addOrderInteractiveTraceAndMessage exception!");
				throw new RuntimeException(
						"addOrderInteractiveTraceAndMessage Exception!");
			}
		} else {
			log.error("---addOrderInteractiveTraceAndMessage -------- data is null ==== ");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 获取订单互动内容跟踪信息
	 * 
	 * @author liukh
	 * @date 2017-2-24 下午2:00:34
	 * @param orderInteractiveTraceId
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseTMService#getOrderInteractiveTrace(java.lang.String)
	 */
	@Override
	public String getOrderInteractiveTrace(String orderInteractiveTraceId) {
		String jsonStr = "";
		BaseObjDto<OrderInteractiveTrace> dto = new BaseObjDto<OrderInteractiveTrace>();
		try {
			if (StringUtils.isBlank(orderInteractiveTraceId)) {
				dto.setRinfo("orderInteractiveTraceId is null");
				return JSON.toJSONString(dto);
			}
			BaseObjDto<OrderInteractiveTrace> orderInteractiveTraceDto = baseDao
					.selectByPrimaryKey(OrderInteractiveTraceMapper.class,
							StringUtils.trim(orderInteractiveTraceId));
			if (FrameworkUtils.isSuccess(orderInteractiveTraceDto)) {
				OrderInteractiveTrace orderInteractiveTrace = orderInteractiveTraceDto
						.getData();
				dto.setData(orderInteractiveTrace);
				FrameworkUtils.setSuccess(dto);
				log.info("getOrderInteractiveTrace success!");
			} else {
				FrameworkUtils.setNoData(dto);
				log.error("getOrderInteractiveTrace failure");
			}
		} catch (Exception e) {
			log.error("getOrderInteractiveTrace exception!");
			e.printStackTrace();
			throw new RuntimeException("getOrderInteractiveTrace Exception!");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 获取订单互动内容跟踪信息列表
	 * 
	 * @author liukh
	 * @date 2017-2-24 下午2:21:05
	 * @param form
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseTMService#getOrderInteractiveTraceList(com.zj.entity.tm.form.OrderInteractiveTraceQueryForm)
	 */
	@Override
	public String getOrderInteractiveTraceList(
			OrderInteractiveTraceQueryForm form) {
		String jsonStr = "";
		BaseObjDto<ItemPage<OrderInteractiveTrace>> dto = new BaseObjDto<ItemPage<OrderInteractiveTrace>>();
		try {
			BaseObjDto<ItemPage<OrderInteractiveTrace>> pagesDto = baseDao
					.getPageList(OrderInteractiveTraceMapper.class,
							OrderInteractiveTrace.class, form,
							"getOrderInteractiveTracePageList");
			if (FrameworkUtils.isSuccess(pagesDto)) {
				dto = pagesDto;
				log.info("getOrderInteractiveTraceList success");
			} else {
				FrameworkUtils.setNoData(dto);
				log.error("getOrderInteractiveTraceList failure");

			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(
					"getOrderInteractiveTraceList Exception!");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 获取某公司的评价数量
	 * 
	 * @author liukh
	 * @date 2017-3-27 下午5:44:33
	 * @param form
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseTMService#getOrderCommentCount(com.zj.entity.tm.form.OrderCommentQueryForm)
	 */
	@Override
	public String getOrderCommentCount(OrderCommentQueryForm form) {
		String jsonStr = "";
		BaseObjDto<Object> dto = new BaseObjDto<Object>();
		try {
			if (form == null) {
				dto.setRcode(BaseDto.ERROR_RCODE);
				dto.setRinfo("The request params is null !");
				return JSON.toJSONString(dto);
			} else if (form.getBeCommentedCompanyId() == null) {
				dto.setRcode(BaseDto.ERROR_RCODE);
				dto.setRinfo("The request's beCommentedCompanyId is null !");
				return JSON.toJSONString(dto);
			}
			BaseObjDto<Company> companyDto = baseDao.selectByPrimaryKey(
					CompanyMapper.class, form.getBeCommentedCompanyId());
			if (!FrameworkUtils.isSuccess(companyDto)) {
				dto.setRcode(BaseDto.ERROR_RCODE);
				dto.setRinfo("The request's beCommentedCompanyId is not exits !");
				return JSON.toJSONString(dto);
			}
			JSONObject js = new JSONObject();
			Company company = companyDto.getData();
			if (company != null && company.getCompanyName() != null) {
				js.put("beCommentedCompanyName", company.getCompanyName());
			}
			Long commentCount = baseDao.getCount(OrderCommentMapper.class,
					"getOrderCommentCount", form);
			int processCommentCount = new Long(commentCount).intValue();
			js.put("count", processCommentCount);
			dto.setData(js);
			dto.setRcode(BaseDto.SUCCESS_RCODE);
			dto.setRinfo(BaseDto.DEFAULT_SUCCESS_INFO);

		} catch (Exception e) {
			e.printStackTrace();
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 获取某公司的综合评价 总评价相加/总评论数，取整
	 * 
	 * @author liukh
	 * @date 2017-3-27 下午5:44:49
	 * @param form
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseTMService#getComprehensiveOrderComment(com.zj.entity.tm.form.OrderCommentQueryForm)
	 */
	@Override
	public String getComprehensiveOrderComment(OrderCommentQueryForm form) {
		String jsonStr = "";
		BaseObjDto<Object> dto = new BaseObjDto<Object>();
		try {
			if (form == null) {
				dto.setRcode(BaseDto.ERROR_RCODE);
				dto.setRinfo("The request params is null !");
				return JSON.toJSONString(dto);
			} else if (form.getBeCommentedCompanyId() == null) {
				dto.setRcode(BaseDto.ERROR_RCODE);
				dto.setRinfo("The request's beCommentedCompanyId is null !");
				return JSON.toJSONString(dto);
			}
			BaseObjDto<Company> companyDto = baseDao.selectByPrimaryKey(
					CompanyMapper.class, form.getBeCommentedCompanyId());
			if (!FrameworkUtils.isSuccess(companyDto)) {
				dto.setRcode(BaseDto.ERROR_RCODE);
				dto.setRinfo("The request's beCommentedCompanyId is not exits !");
				return JSON.toJSONString(dto);
			}
			JSONObject js = new JSONObject();
			Company company = companyDto.getData();
			if (company != null && company.getCompanyName() != null) {
				js.put("beCommentedCompanyName", company.getCompanyName());
			}
			Long comprehensiveCount = baseDao.getCount(
					OrderCommentMapper.class, "getComprehensiveOrderComment",
					form);
			int processComprehensiveCount = new Long(comprehensiveCount)
					.intValue();
			js.put("star", processComprehensiveCount);
			dto.setData(js);
			dto.setRcode(BaseDto.SUCCESS_RCODE);
			dto.setRinfo(BaseDto.DEFAULT_SUCCESS_INFO);

		} catch (Exception e) {
			e.printStackTrace();
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 订单评论
	 * 
	 * @author liukh
	 * @date 2017-2-24 下午1:47:14
	 * @param data
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseTMService#addOrderComment(java.lang.String)
	 */
	@Override
	public String addOrderComment(String data) {
		String jsonStr = "";
		BaseDto dto = new BaseDto();
		if (StringUtils.isNotBlank(data)) {
			try {
				OrderComment orderComment = JSON.parseObject(data,
						OrderComment.class);
				if (orderComment == null) {
					dto.setRcode(1);
					dto.setRinfo("The data parseObject to OrderComment is null !");
					return JSON.toJSONString(dto);
				}
				Date createDate = new Date();
				orderComment.setCreateTime(createDate);
				dto = baseDao.insertSelective(OrderCommentMapper.class,
						orderComment);
				if (FrameworkUtils.isSuccess(dto)) {
					log.info("addOrderComment success!");
				} else {
					log.error("addOrderComment failure!");
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("addOrderComment exception!");
				throw new RuntimeException("addOrderComment Exception!");
			}
		} else {
			log.error("---addOrderComment -------- data is null ==== ");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 新增订单评论并检查订单的状态
	 * 
	 * @author liukh
	 * @date 2017-3-27 下午6:42:39
	 * @param data
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseTMService#addOrderCommentAndCheckOrderStatus(java.lang.String)
	 */

	@Override
	public String addOrderCommentAndCheckOrderStatus(String data) {
		String jsonStr = "";
		BaseDto dto = new BaseDto();
		if (StringUtils.isNotBlank(data)) {
			try {
				OrderComment orderComment = JSON.parseObject(data,
						OrderComment.class);
				if (orderComment == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data parseObject to OrderComment is null !");
					return JSON.toJSONString(dto);
				} else if (orderComment.getRentOrderId() == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's rentOrderId is null !");
					return JSON.toJSONString(dto);
				} else if (orderComment.getCommentSideId() == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's commentSideId is null !");
					return JSON.toJSONString(dto);
				} else if (orderComment.getStar() == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's star is null !");
					return JSON.toJSONString(dto);
				} else if (orderComment.getBeCommentedCompanyId() == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's beCommentedCompanyId is null !");
					return JSON.toJSONString(dto);
				} else if (orderComment.getCommentator() == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's commentator is null !");
					return JSON.toJSONString(dto);
				}
				orderComment.setStatus(Constant.ORDERCOMMENT_STATUS_CREATE);

				BaseObjDto<RentOrderListDto> rentOrderDto = baseDao
						.selectByPrimaryKey(RentOrderMapper.class,
								StringUtils.trim(orderComment.getRentOrderId()));
				if (!FrameworkUtils.isSuccess(rentOrderDto)) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's rentOrderId is exits !");
					return JSON.toJSONString(dto);
				}
				RentOrderListDto queryRentOrder = rentOrderDto.getData();
				if (queryRentOrder != null
						&& queryRentOrder.getStatus() != null
						&& queryRentOrder.getStatus() == Constant.RENTORDER_STATUS_COMMENT) {
					dto.setRcode(21);
					dto.setRinfo("The rentOrder:"
							+ StringUtils.trim(orderComment.getRentOrderId())
							+ " has been Commented  !");
					return JSON.toJSONString(dto);
				}

				orderComment.setCreateTime(new Date());
				dto = baseDao.insertSelective(OrderCommentMapper.class,
						orderComment);
				if (FrameworkUtils.isSuccess(dto)) {
					RentOrder updateRentOrder = new RentOrder();
					updateRentOrder.setId(StringUtils.trim(orderComment
							.getRentOrderId()));
					updateRentOrder
							.setStatus(Constant.RENTORDER_STATUS_COMMENT);
					dto = baseDao.updateByPrimaryKeySelective(
							RentOrderMapper.class, updateRentOrder);

				}

			} catch (Exception e) {
				e.printStackTrace();
				log.error("addOrderComment exception!");
				throw new RuntimeException("addOrderComment Exception!");
			}
		} else {
			log.error("---addOrderComment -------- data is null ==== ");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 获取订单评论
	 * 
	 * @author liukh
	 * @date 2017-2-24 下午2:02:36
	 * @param orderCommentId
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseTMService#getOrderComment(java.lang.String)
	 */
	@Override
	public String getOrderComment(String orderCommentId) {
		String jsonStr = "";
		BaseObjDto<OrderComment> dto = new BaseObjDto<OrderComment>();
		try {
			if (StringUtils.isBlank(orderCommentId)) {
				dto.setRinfo("orderCommentId is null");
				return JSON.toJSONString(dto);
			}
			BaseObjDto<OrderComment> orderCommentDto = baseDao
					.selectByPrimaryKey(OrderCommentMapper.class,
							StringUtils.trim(orderCommentId));
			if (FrameworkUtils.isSuccess(orderCommentDto)) {
				OrderComment orderComment = orderCommentDto.getData();
				dto.setData(orderComment);
				FrameworkUtils.setSuccess(dto);
				log.info("getOrderComment success!");
			} else {
				FrameworkUtils.setNoData(dto);
				log.error("getOrderComment failure");
			}
		} catch (Exception e) {
			log.error("getOrderComment exception!");
			e.printStackTrace();
			throw new RuntimeException("getOrderComment Exception!");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 修改订单评论
	 * 
	 * @author liukh
	 * @date 2017-2-24 下午2:12:31
	 * @param orderCommentId
	 * @param data
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseTMService#updateOrderComment(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public String updateOrderComment(String orderCommentId, String data) {
		String jsonStr = "";
		BaseDto dto = new BaseDto();
		if (StringUtils.isNotBlank(orderCommentId)
				&& StringUtils.isNotBlank(data)) {
			try {
				OrderComment orderComment = JSON.parseObject(data,
						OrderComment.class);
				if (orderComment == null) {
					dto.setRcode(1);
					dto.setRinfo("The data parseObject to OrderComment is null !");
					return JSON.toJSONString(dto);
				}
				orderComment.setId(orderCommentId);
				dto = baseDao.updateByPrimaryKeySelective(
						OrderCommentMapper.class, orderComment);
				if (FrameworkUtils.isSuccess(dto)) {
					log.info("updateOrderComment success!");
				} else {
					log.error("updateOrderComment failure!");
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("updateOrderComment exception!");
				throw new RuntimeException("updateOrderComment Exception!");
			}

		} else {
			log.error("---updateOrderComment -------- data or orderCommentId is null ==== ");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 获取订单评论信息列表
	 * 
	 * @author liukh
	 * @date 2017-2-24 下午2:22:28
	 * @param form
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseTMService#getOrderCommentList(com.zj.entity.tm.form.OrderCommentQueryForm)
	 */
	@Override
	public String getOrderCommentList(OrderCommentQueryForm form) {
		String jsonStr = "";
		BaseObjDto<ItemPage<OrderComment>> dto = new BaseObjDto<ItemPage<OrderComment>>();
		try {
			BaseObjDto<ItemPage<OrderComment>> pagesDto = baseDao.getPageList(
					OrderCommentMapper.class, OrderComment.class, form,
					"getOrderCommentPageList");
			if (FrameworkUtils.isSuccess(pagesDto)) {
				dto = pagesDto;
				log.info("getOrderCommentList success");
			} else {
				FrameworkUtils.setNoData(dto);
				log.error("getOrderCommentList failure");

			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("getOrderCommentList Exception!");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 增加订单进度跟踪信息
	 * 
	 * @author liukh
	 * @date 2017-2-24 下午1:48:51
	 * @param data
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseTMService#addOrderProgressTrace(java.lang.String)
	 */
	@Override
	public String addOrderProgressTrace(String data) {
		String jsonStr = "";
		BaseDto dto = new BaseDto();
		if (StringUtils.isNotBlank(data)) {
			try {
				OrderProgressTrace orderProgressTrace = JSON.parseObject(data,
						OrderProgressTrace.class);
				if (orderProgressTrace == null) {
					dto.setRcode(1);
					dto.setRinfo("The data parseObject to OrderProgressTrace is null !");
					return JSON.toJSONString(dto);
				}
				Date createDate = new Date();
				orderProgressTrace.setCreateTime(createDate);
				dto = baseDao.insertSelective(OrderProgressTraceMapper.class,
						orderProgressTrace);
				if (FrameworkUtils.isSuccess(dto)) {
					log.info("addOrderProgressTrace success!");
				} else {
					log.error("addOrderProgressTrace failure!");
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("addOrderProgressTrace exception!");
				throw new RuntimeException("addOrderProgressTrace Exception!");
			}
		} else {
			log.error("---addOrderProgressTrace -------- data is null ==== ");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 获取订单进度跟踪信息列表
	 * 
	 * @author liukh
	 * @date 2017-2-24 下午2:23:56
	 * @param form
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseTMService#getOrderProgressTraceList(com.zj.entity.tm.form.OrderProgressTraceQueryForm)
	 */
	@Override
	public String getOrderProgressTraceList(OrderProgressTraceQueryForm form) {
		String jsonStr = "";
		BaseObjDto<ItemPage<OrderProgressTrace>> dto = new BaseObjDto<ItemPage<OrderProgressTrace>>();
		try {
			BaseObjDto<ItemPage<OrderProgressTrace>> pagesDto = baseDao
					.getPageList(OrderProgressTraceMapper.class,
							OrderProgressTrace.class, form,
							"getOrderProgressTracePageList");
			if (FrameworkUtils.isSuccess(pagesDto)) {
				dto = pagesDto;
				log.info("getOrderProgressTraceList success");
			} else {
				FrameworkUtils.setNoData(dto);
				log.error("getOrderProgressTraceList failure");

			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("getOrderProgressTraceList exception!");
			throw new RuntimeException("getOrderProgressTraceList Exception!");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<扣款提示>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	@Override
	public String tipMakeSureLockedMoenyTip4PreventionWhenInqueryOrder2RentOrder(
			String data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String tipLockedMoneyWhenFastCreateOrder(String data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String tipMakeSureLockedMoenyTip4IqueryRentPrevention(String data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String tipMakeSureLockedMoenyTip4IqueryThrowPrevention(String data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String tipMakeSureLockedMoenyTip4Margin(String data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String tip4InqueryRentThrowDoHaveDeviceHaveMoneyMargin(
			String inqueryRentThrowId, String companyId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String tipLockedMoney(String companyId, String lockedType,
			BigDecimal lockedAmout) {
		// TODO Auto-generated method stub
		return null;
	}

	// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<其他>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	/**
	 * 判断是否是同行找机的订单
	 * 
	 * @author liukh
	 * @date 2017-3-27 上午11:47:52
	 * @param rentOrderId
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseTMService#isPeerRentOrder(java.lang.String)
	 */
	@Override
	public boolean isPeerRentOrder(String rentOrderId) {
		// TODO Auto-generated method stub
		if (StringUtils.isNoneBlank(rentOrderId)) {
			BaseObjDto<RentOrderListDto> rentOrderDto = baseDao
					.selectByPrimaryKey(RentOrderMapper.class,
							StringUtils.trim(rentOrderId));
			if (FrameworkUtils.isSuccess(rentOrderDto)) {
				RentOrderListDto queryRentOrder = rentOrderDto.getData();
				if (queryRentOrder != null
						&& queryRentOrder.getLesseeSideId() != null) {
					BaseObjDto<Company> companyDto = baseDao
							.selectByPrimaryKey(CompanyMapper.class,
									queryRentOrder.getLesseeSideId());
					if (FrameworkUtils.isSuccess(companyDto)) {
						Company company = companyDto.getData();
						if (company != null) {
							return company.isLeasingSide();
						}
					}
				}
			}
		}
		return false;
	}

	// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<待办>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	/**
	 * 租赁方待办
	 * 
	 * @author liukh
	 * @date 2017-3-29 上午10:21:35
	 * @param companyId
	 * @param userId
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseTMService#waitWorkingAmount4LeasingSide(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public String waitWorkingAmount4LeasingSide(String companyId, String userId) {
		// 待下单
		// 待出库
		// 待结算
		// 待结算 -- 确认结算
		// 待结算 -- 确认收款
		// 待支付

		BaseObjDto<Object> dto = new BaseObjDto<Object>();
		try {

			if (StringUtils.isNotBlank(companyId)
					&& StringUtils.isNotBlank(userId)) {
				// 待报价,作为租赁方，承租方向我询价的询价单（状态为，询价中）
				long inqueryOrderWaitQuoteAmount = 0;
				InqueryOrderQueryForm watitQuoteInqueryOrderForm = new InqueryOrderQueryForm();
				watitQuoteInqueryOrderForm.setCurrentHandler(StringUtils
						.trim(userId));
				watitQuoteInqueryOrderForm.setLeasingSideId(StringUtils
						.trim(companyId));
				watitQuoteInqueryOrderForm
						.setStatus(Constant.INQUERYORDER_STATUS_REPLY
								.toString());
				inqueryOrderWaitQuoteAmount = baseDao.getCount(
						InqueryOrderMapper.class, "getInqueryOrderCount",
						watitQuoteInqueryOrderForm);
				// 待下单 ，作为承租方，询价单状态为待确认
				long createInquerOrderAmount = 0;
				InqueryOrderQueryForm createOrderInqueryOrderForm = new InqueryOrderQueryForm();
				createOrderInqueryOrderForm.setLesseeSideId(StringUtils
						.trim(companyId));
				createOrderInqueryOrderForm.setCurrentHandler(StringUtils
						.trim(userId));
				createOrderInqueryOrderForm
						.setStatus(Constant.INQUERYORDER_STATUS_UNMAKESURE
								.toString());
				createInquerOrderAmount = baseDao.getCount(
						InqueryOrderMapper.class, "getInqueryOrderCount",
						createOrderInqueryOrderForm);

				// 待出库，作为租赁方，订单状态为待出库状态
				long waitGoRentOrderAmount = 0;
				RentOrderQueryForm waitGoRentOrderForm = new RentOrderQueryForm();
				waitGoRentOrderForm.setLeasingSideId(StringUtils
						.trim(companyId));
				waitGoRentOrderForm.setStatus(Constant.RENTORDER_STATUS_UNGO
						.toString());
				waitGoRentOrderAmount = baseDao.getCount(RentOrderMapper.class,
						"getRentOrderCount", waitGoRentOrderForm);

				// 待评价
				long watiEvaluateAmount = 0;
				RentOrderQueryForm watiEvaluateRentOrderForm = new RentOrderQueryForm();
				watiEvaluateRentOrderForm.setLesseeSideId(StringUtils
						.trim(companyId));
				watiEvaluateRentOrderForm
						.setStatus(Constant.RENTORDER_STATUS_OVER.toString());
				watiEvaluateAmount = baseDao.getCount(RentOrderMapper.class,
						"getRentOrderCount", watiEvaluateRentOrderForm);

				// 待结算-----确认结算,作为租赁方结算单状态为待确认
				// getOrderStatementCount
				long orderStatementMakeSureAmount = 0;
				OrderStatementQueryForm makeSureOrderStatementForm = new OrderStatementQueryForm();
				makeSureOrderStatementForm.setCurrentHandler(StringUtils
						.trim(userId));
				makeSureOrderStatementForm.setStatus(String
						.valueOf(Constant.ORDERSTATEMENT_STATUS_CREATE));
				orderStatementMakeSureAmount = baseDao.getCount(
						OrderStatementMapper.class, "getOrderStatementCount",
						makeSureOrderStatementForm);
				// 待结算-----确认收款,作为租赁方，结算单状态为待确认收款
				long orderStatementGetMoneyAmount = 0;
				OrderStatementQueryForm getMoneyOrderStatementForm = new OrderStatementQueryForm();
				getMoneyOrderStatementForm.setCurrentHandler(StringUtils
						.trim(userId));
				getMoneyOrderStatementForm.setStatus(String
						.valueOf(Constant.ORDERSTATEMENT_STATUS_WAITMAKESURE));
				orderStatementGetMoneyAmount = baseDao.getCount(
						OrderStatementMapper.class, "getOrderStatementCount",
						getMoneyOrderStatementForm);

				// 待支付
				long waitPayPAmount = 0;
				OrderStatementQueryForm waitPayOrderStatementForm = new OrderStatementQueryForm();
				waitPayOrderStatementForm.setCurrentHandler(StringUtils
						.trim(userId));
				waitPayOrderStatementForm.setStatus(String
						.valueOf(Constant.ORDERSTATEMENT_STATUS_WAITPAY));
				waitPayPAmount = baseDao.getCount(OrderStatementMapper.class,
						"getOrderStatementCount", waitPayOrderStatementForm);
				JSONArray jsonArray = new JSONArray();
				// 待报价
				WaitWorkStatisticsDto inqueryOrderWaitQuoteWaitWorkDto = new WaitWorkStatisticsDto();
				inqueryOrderWaitQuoteWaitWorkDto.setAmount(new Long(
						inqueryOrderWaitQuoteAmount).intValue());
				inqueryOrderWaitQuoteWaitWorkDto
						.setType(Constant.WAITWORK_TYPE_QUOTE);
				jsonArray.add(inqueryOrderWaitQuoteWaitWorkDto);
				// 待下单
				WaitWorkStatisticsDto inqueryOrderCreateOrderWaitWorkDto = new WaitWorkStatisticsDto();
				inqueryOrderCreateOrderWaitWorkDto.setAmount(new Long(
						createInquerOrderAmount).intValue());
				inqueryOrderCreateOrderWaitWorkDto
						.setType(Constant.WAITWORK_TYPE_CREATEORDER);
				jsonArray.add(inqueryOrderCreateOrderWaitWorkDto);
				// 待出库
				WaitWorkStatisticsDto waitGoRentOrderWaitWorkDto = new WaitWorkStatisticsDto();
				waitGoRentOrderWaitWorkDto.setAmount(new Long(
						waitGoRentOrderAmount).intValue());
				waitGoRentOrderWaitWorkDto
						.setType(Constant.WAITWORK_TYPE_WAITOUTBOUNDORDER);
				jsonArray.add(waitGoRentOrderWaitWorkDto);

				List<Object> orderStatementList = new ArrayList<Object>();
				int totalOrderStatementSum = new Long(
						orderStatementMakeSureAmount).intValue()
						+ new Long(orderStatementGetMoneyAmount).intValue();
				// 待结算 --确认结算,作为租赁方结算单状态为待确认
				WaitWorkStatisticsDto orderStatementMakeSureWaitWorkDto = new WaitWorkStatisticsDto();
				orderStatementMakeSureWaitWorkDto.setAmount(new Long(
						orderStatementMakeSureAmount).intValue());
				orderStatementMakeSureWaitWorkDto
						.setType(Constant.WAITWORK_TYPE_WAITSTATEMENTMAKESURE);
				orderStatementList.add(orderStatementMakeSureWaitWorkDto);
				// 待结算 --确认收款,作为租赁方，结算单状态为待确认收款
				WaitWorkStatisticsDto orderStatementGetMoneyWaitWorkDto = new WaitWorkStatisticsDto();
				orderStatementGetMoneyWaitWorkDto.setAmount(new Long(
						orderStatementGetMoneyAmount).intValue());
				orderStatementGetMoneyWaitWorkDto
						.setType(Constant.WAITWORK_TYPE_WAITSTATEMENTGETMONEY);
				orderStatementList.add(orderStatementGetMoneyWaitWorkDto);

				// 待结算
				WaitWorkStatisticsDto orderStatementWaitWaitWorkDto = new WaitWorkStatisticsDto();
				orderStatementWaitWaitWorkDto.setAmount(totalOrderStatementSum);
				orderStatementWaitWaitWorkDto
						.setType(Constant.WAITWORK_TYPE_WAITSTATEMENT);
				orderStatementWaitWaitWorkDto.setData(orderStatementList);
				jsonArray.add(orderStatementWaitWaitWorkDto);

				// 待支付
				WaitWorkStatisticsDto waitPaytWaitWorkDto = new WaitWorkStatisticsDto();
				waitPaytWaitWorkDto.setAmount(new Long(waitPayPAmount)
						.intValue());
				waitPaytWaitWorkDto.setType(Constant.WAITWORK_TYPE_WAITPAY);
				jsonArray.add(waitPaytWaitWorkDto);
				WaitWorkStatisticsDto waitEvaluateWorkDto = new WaitWorkStatisticsDto();
				waitEvaluateWorkDto.setAmount(new Long(watiEvaluateAmount)
						.intValue());
				waitEvaluateWorkDto
						.setType(Constant.WAITWORK_TYPE_WAITEVALUATE);
				jsonArray.add(waitEvaluateWorkDto);

				dto.setData(jsonArray);
				dto.setRcode(BaseDto.SUCCESS_RCODE);
				dto.setRinfo(BaseDto.DEFAULT_SUCCESS_INFO);

			} else {
				dto.setRcode(BaseDto.ERROR_RCODE);
				dto.setRinfo("The companyId or userId is null !");
				return JSON.toJSONString(dto);
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.error("waitWorkingAmount4LeasingSide exception!");
			throw new RuntimeException(
					"waitWorkingAmount4LeasingSide Exception!");
		}

		return JSON.toJSONString(dto);
	}

	/**
	 * 承租方待办
	 * 
	 * @author liukh
	 * @date 2017-3-29 上午10:22:16
	 * @param companyId
	 * @param userId
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseTMService#waitWorkingAmount4LesseeSide(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public String waitWorkingAmount4LesseeSide(String companyId, String userId) {
		BaseObjDto<Object> dto = new BaseObjDto<Object>();
		try {

			if (StringUtils.isNotBlank(companyId)
					&& StringUtils.isNotBlank(userId)) {
				// 待下单 --- 询价反馈
				long inquerOrderAmount = 0;
				InqueryOrderQueryForm quoteInqueryOrderForm = new InqueryOrderQueryForm();
				quoteInqueryOrderForm.setCurrentHandler(StringUtils
						.trim(userId));
				quoteInqueryOrderForm.setLesseeSideId(StringUtils
						.trim(companyId));

				quoteInqueryOrderForm
						.setStatus(Constant.INQUERYORDER_STATUS_UNMAKESURE
								.toString());
				inquerOrderAmount = baseDao.getCount(InqueryOrderMapper.class,
						"getInqueryOrderCount", quoteInqueryOrderForm);

				// 待下单 --- 求租反馈
				long iqueryRentOrderAmount = 0;
				InqueryRentQueryForm rentForm = new InqueryRentQueryForm();
				rentForm.setLesseeSideId(StringUtils.trim(companyId));
				rentForm.setStatus(Constant.INQUERYRENT_STATUS_MAKESURE
						.toString());
				iqueryRentOrderAmount = baseDao.getCount(
						InqueryRentMapper.class, "getInqueryRentCount",
						rentForm);

				// 待下单
				// 待结算,结算单状态为待确认和异议回复的
				long orderStatementAmount = 0;
				OrderStatementQueryForm orderStatementForm = new OrderStatementQueryForm();
				orderStatementForm.setCurrentHandler(StringUtils.trim(userId));
				orderStatementForm.setStatus(String
						.valueOf(Constant.ORDERSTATEMENT_STATUS_CREATE));
				orderStatementAmount = baseDao.getCount(
						OrderStatementMapper.class, "getOrderStatementCount",
						orderStatementForm);

				// 待支付
				long waitPayPAmount = 0;
				OrderStatementQueryForm waitPayOrderStatementForm = new OrderStatementQueryForm();
				waitPayOrderStatementForm.setCurrentHandler(StringUtils
						.trim(userId));
				waitPayOrderStatementForm.setStatus(String
						.valueOf(Constant.ORDERSTATEMENT_STATUS_WAITPAY));
				waitPayPAmount = baseDao.getCount(OrderStatementMapper.class,
						"getOrderStatementCount", waitPayOrderStatementForm);

				// 待评价
				long watiEvaluateAmount = 0;
				RentOrderQueryForm watiEvaluateRentOrderForm = new RentOrderQueryForm();
				watiEvaluateRentOrderForm.setLesseeSideId(StringUtils
						.trim(companyId));
				watiEvaluateRentOrderForm
						.setStatus(Constant.RENTORDER_STATUS_OVER.toString());
				watiEvaluateAmount = baseDao.getCount(RentOrderMapper.class,
						"getRentOrderCount", watiEvaluateRentOrderForm);

				int totalInquerOrderAndIqueryRentOrderAmount = new Long(
						inquerOrderAmount).intValue()
						+ new Long(iqueryRentOrderAmount).intValue();
				JSONArray jsonArray = new JSONArray();

				// 待下单
				WaitWorkStatisticsDto creatOrderWaitWorkDto = new WaitWorkStatisticsDto();
				creatOrderWaitWorkDto
						.setAmount(totalInquerOrderAndIqueryRentOrderAmount);
				creatOrderWaitWorkDto
						.setType(Constant.WAITWORK_TYPE_CREATEORDER);

				List<Object> createOrderList = new ArrayList<Object>();
				// 待下单 --- 询价反馈
				WaitWorkStatisticsDto inqueryOrderWaitWorkDto = new WaitWorkStatisticsDto();
				inqueryOrderWaitWorkDto.setAmount(new Long(inquerOrderAmount)
						.intValue());
				inqueryOrderWaitWorkDto
						.setType(Constant.WAITWORK_TYPE_CREATEORDER_INQUERYORDER);
				createOrderList.add(inqueryOrderWaitWorkDto);
				// 待下单 --- 求租反馈
				WaitWorkStatisticsDto inqueryRentWaitWorkDto = new WaitWorkStatisticsDto();
				inqueryRentWaitWorkDto
						.setAmount(new Long(iqueryRentOrderAmount).intValue());
				inqueryRentWaitWorkDto
						.setType(Constant.WAITWORK_TYPE_CREATEORDER_RENTORDER);
				createOrderList.add(inqueryRentWaitWorkDto);

				creatOrderWaitWorkDto.setData(createOrderList);

				jsonArray.add(creatOrderWaitWorkDto);
				// 待结算
				WaitWorkStatisticsDto waitOrderStatementWaitWorkDto = new WaitWorkStatisticsDto();
				waitOrderStatementWaitWorkDto.setAmount(new Long(
						orderStatementAmount).intValue());
				waitOrderStatementWaitWorkDto
						.setType(Constant.WAITWORK_TYPE_WAITSTATEMENT);

				jsonArray.add(waitOrderStatementWaitWorkDto);
				// 待支付
				WaitWorkStatisticsDto waitPaytWaitWorkDto = new WaitWorkStatisticsDto();
				waitPaytWaitWorkDto.setAmount(new Long(waitPayPAmount)
						.intValue());
				waitPaytWaitWorkDto.setType(Constant.WAITWORK_TYPE_WAITPAY);

				jsonArray.add(waitPaytWaitWorkDto);

				WaitWorkStatisticsDto waitEvaluateWorkDto = new WaitWorkStatisticsDto();
				waitEvaluateWorkDto.setAmount(new Long(watiEvaluateAmount)
						.intValue());
				waitEvaluateWorkDto
						.setType(Constant.WAITWORK_TYPE_WAITEVALUATE);

				dto.setData(jsonArray);
				dto.setRcode(BaseDto.SUCCESS_RCODE);
				dto.setRinfo(BaseDto.DEFAULT_SUCCESS_INFO);

			} else {
				dto.setRcode(BaseDto.ERROR_RCODE);
				dto.setRinfo("The companyId or userId is null !");
				return JSON.toJSONString(dto);
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.error("waitWorkingAmount4LeasingSide exception!");
			throw new RuntimeException(
					"waitWorkingAmount4LeasingSide Exception!");
		}

		return JSON.toJSONString(dto);
	}

	@Override
	public String queryInspectionDevice(String data) {
		String jsonStr = "";
		BaseObjDto<InspectionDevice>  dto = new BaseObjDto<InspectionDevice>();
		if (StringUtils.isNotBlank(data)) {
			try {
                  JSONObject jsob = JSON.parseObject(data);
                  
				if (jsob == null) {
					dto.setRcode(1);
					dto.setRinfo("The data parse to JSONObject is null !");
					return JSON.toJSONString(dto);
				}

				if (jsob.get("deviceId") == null) {
					dto.setRcode(1);
					dto.setRinfo("The data 's deviceId is null !");
					return JSON.toJSONString(dto);
				}
				if(jsob.get("projectId") !=null){
					Map<String, Object> queryParams = new HashMap<String, Object>();
					queryParams.put("deviceId", jsob.get("deviceId"));
					queryParams.put("projectId",jsob.get("projectId"));
					dto = baseDao.getObjProperty(InspectionDeviceMapper.class,"getInspectionDevice", queryParams);
					if(FrameworkUtils.isSuccess(dto)){
						return JSON.toJSONString(dto);
					}
				}
				
				InspectionDeviceQueryForm form = new InspectionDeviceQueryForm();
            	 form.setDeviceId(StringUtils.trim(jsob.get("deviceId").toString()));
            	 BaseObjDto<ItemPage<InspectionDevice>> listDto = baseDao.getPageList(InspectionDeviceMapper.class, InspectionDevice.class, form, "getInspectionDevicePageList");
            	 if(FrameworkUtils.isSuccess(listDto) && listDto.getData()!= null && listDto.getData().getItems()!= null && listDto.getData().getItems().size()>0 ){
            		 List<InspectionDevice> list = listDto.getData().getItems();
            		 InspectionDevice queryInspectionDevice = list.get(0);
            		 if(queryInspectionDevice.getInspectionStatus()== null){
            			 queryInspectionDevice.setInspectionStatus("0");
            		 }
            		 if(queryInspectionDevice.getProjectId()==null && jsob.get("projectId") !=null){
            			 queryInspectionDevice.setProjectId(jsob.get("projectId").toString());
            		 }
            		 dto.setData(queryInspectionDevice);
            		 FrameworkUtils.setSuccess(dto);
            		 
            	 }
            	 
	
				if (FrameworkUtils.isSuccess(dto)) {
					log.info("queryInspectionDevice success!");
				} else {
					log.error("queryInspectionDevice failure!");
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("queryInspectionDevice exception!");
				throw new RuntimeException("queryInspectionDevice Exception!");
			}
		} else {
			log.error("---queryInspectionDevice -------- data is null ==== ");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

}
