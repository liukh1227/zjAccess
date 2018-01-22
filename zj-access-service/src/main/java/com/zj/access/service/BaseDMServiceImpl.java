package com.zj.access.service;

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
import com.alibaba.fastjson.JSONObject;
import com.zj.access.base.dao.IBaseDao;
import com.zj.access.base.mapper.CompanyAvailableDeviceMapper;
import com.zj.access.base.mapper.CompanyDeviceTypeMapper;
import com.zj.access.base.mapper.CompanyMapper;
import com.zj.access.base.mapper.DeviceBrandMapper;
import com.zj.access.base.mapper.DeviceMapper;
import com.zj.access.base.mapper.DeviceModelMapper;
import com.zj.access.base.mapper.DeviceStatusTraceMapper;
import com.zj.access.base.mapper.DeviceTypeMapper;
import com.zj.access.base.mapper.MessageMapper;
import com.zj.access.common.FrameworkUtils;
import com.zj.base.common.Constant;
import com.zj.base.common.ItemPage;
import com.zj.base.common.utils.CommonUtils;
import com.zj.base.common.utils.sms.MessageTemplateUtils;
import com.zj.base.entity.base.dto.BaseDto;
import com.zj.base.entity.base.dto.BaseObjDto;
import com.zj.common.utils.MapToolUtils;
import com.zj.entity.base.po.Audit;
import com.zj.entity.base.po.Company;
import com.zj.entity.base.po.CompanyDeviceType;
import com.zj.entity.base.po.Device;
import com.zj.entity.base.po.DeviceBrand;
import com.zj.entity.base.po.DeviceModel;
import com.zj.entity.base.po.DeviceStatusTrace;
import com.zj.entity.base.po.DeviceType;
import com.zj.entity.base.po.Message;
import com.zj.entity.bm.dto.UserDto;
import com.zj.entity.dm.dto.CompanyAvailableDevice;
import com.zj.entity.dm.dto.CompanyAvailableDeviceInMapDto;
import com.zj.entity.dm.dto.DeviceDetailInforListDto;
import com.zj.entity.dm.dto.DeviceListDto;
import com.zj.entity.dm.dto.DeviceStatisticsDto;
import com.zj.entity.dm.form.CompanyAvailableDeviceQueryForm;
import com.zj.entity.dm.form.CompanyDeviceTypeQueryForm;
import com.zj.entity.dm.form.DeviceQueryForm;
import com.zj.entity.dm.form.DeviceStatusTraceQueryForm;

@Service("baseDMService")
@Scope("prototype")
public class BaseDMServiceImpl implements BaseDMService {
	private static final Logger log = Logger.getLogger(BaseDMServiceImpl.class);
	@Autowired
	private IBaseDao baseDao;
	@Autowired
	private BaseOtherService baseOtherService;
	@Autowired
	private BaseBMService baseBMService;
	/**
	 * 新增设备信息
	 * 
	 * @author liukh
	 * @date 2017-2-21 下午3:25:27
	 * @param data
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseDMService#addDevice(java.lang.String)
	 */
	@Override
	public String addDevice(String data) {
		String jsonStr = "";
		BaseDto dto = new BaseDto();
		if (StringUtils.isNotBlank(data)) {
			try {
				Device device = JSON.parseObject(data, Device.class);
				if (device == null) {
					dto.setRcode(1);
					dto.setRinfo("The data parseObject to Device is null !");
					return JSON.toJSONString(dto);
				}

				if (StringUtils.isBlank(device.getDeviceName())) {
					dto.setRcode(1);
					dto.setRinfo("The data's deviceName is null !");
					return JSON.toJSONString(dto);
				}
				if (device.getIsRealDevice() == null) {
					dto.setRcode(1);
					dto.setRinfo("The data's isRealDevice is null !");
					return JSON.toJSONString(dto);
				}
				/**
				 * 
				 if(StringUtils.isBlank(device.getDeviceBrand())){
				 * dto.setRcode(1);
				 * dto.setRinfo("The data's deviceBrand is not exist !"); return
				 * JSON.toJSONString(dto); }
				 * if(StringUtils.isNotBlank(device.getDeviceBrand())){
				 * BaseObjDto<DeviceBrand> deviceBrandDto =
				 * baseDao.selectByPrimaryKey(DeviceBrandMapper.class,
				 * StringUtils.trim(device.getDeviceBrand())); if
				 * (!FrameworkUtils.isSuccess(deviceBrandDto)) {
				 * dto.setRcode(1);
				 * dto.setRinfo("The deviceBrand is not exist !"); return
				 * JSON.toJSONString(dto); } }
				 * if(StringUtils.isBlank(device.getDeviceType())){
				 * dto.setRcode(1);
				 * dto.setRinfo("The data's deviceBrand is not exist !"); return
				 * JSON.toJSONString(dto); }
				 * if(StringUtils.isNotBlank(device.getDeviceType())){
				 * BaseObjDto<DeviceType> deviceTypeDto =
				 * baseDao.selectByPrimaryKey( DeviceTypeMapper.class,
				 * StringUtils.trim(device.getDeviceType())); if
				 * (!FrameworkUtils.isSuccess(deviceTypeDto)) { dto.setRcode(1);
				 * dto.setRinfo("The deviceType is not exist !"); return
				 * JSON.toJSONString(dto); } }
				 */
				if (StringUtils.isBlank(device.getDeviceModel())) {
					dto.setRcode(1);
					dto.setRinfo("The data's deviceModel is not exist !");
					return JSON.toJSONString(dto);
				}
				if (StringUtils.isNotBlank(device.getDeviceModel())) {
					BaseObjDto<DeviceModel> deviceModelDto = baseDao
							.selectByPrimaryKey(DeviceModelMapper.class,
									StringUtils.trim(device.getDeviceModel()));
					if (!FrameworkUtils.isSuccess(deviceModelDto)) {
						dto.setRcode(1);
						dto.setRinfo("The deviceModel is not exist !");
						return JSON.toJSONString(dto);
					}
					DeviceModel deviceModel = deviceModelDto.getData();
					if (deviceModel != null) {
						if (deviceModel.getDeviceBrand() != null) {
							device.setDeviceBrand(deviceModel.getDeviceBrand()
									.getId());
						}
						if (deviceModel.getDeviceType() != null) {
							device.setDeviceType(deviceModel.getDeviceType()
									.getId());
						}

					}
				}

				if (StringUtils.isBlank(device.getCompanyId())) {
					dto.setRcode(1);
					dto.setRinfo("The data's companyId is not exist !");
					return JSON.toJSONString(dto);
				}
				if (StringUtils.isNotBlank(device.getCompanyId())) {
					BaseObjDto<Company> companyDto = baseDao
							.selectByPrimaryKey(CompanyMapper.class,
									StringUtils.trim(device.getCompanyId()));
					if (!FrameworkUtils.isSuccess(companyDto)) {
						dto.setRcode(1);
						dto.setRinfo("The companyId is not exist !");
						return JSON.toJSONString(dto);
					}
				}

				device.setCreateTime(new Date());
				dto = baseDao.insertSelective(DeviceMapper.class, device);

				if (FrameworkUtils.isSuccess(dto)) {
					log.info("addDevice success!");
				} else {
					log.error("addDevice failure!");
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("addDevice exception!");
				throw new RuntimeException("addDevice Exception!");
			}
		} else {
			log.error("---addDevice -------- data is null ==== ");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 获取设备
	 * 
	 * @author liukh
	 * @date 2017-2-21 下午4:49:45
	 * @param deviceId
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseDMService#getDevice(java.lang.String)
	 */
	@Override
	public String getDevice(String deviceId) {
		String jsonStr = "";
		BaseObjDto<DeviceDetailInforListDto> dto = new BaseObjDto<DeviceDetailInforListDto>();
		try {
			if (StringUtils.isBlank(deviceId)) {
				dto.setRinfo("deviceId is null");
				return JSON.toJSONString(dto);
			}
			BaseObjDto<DeviceDetailInforListDto> deviceDto = baseDao.selectByPrimaryKey(
					DeviceMapper.class, StringUtils.trim(deviceId));
			if (FrameworkUtils.isSuccess(deviceDto)) {
				DeviceDetailInforListDto device = deviceDto.getData();
				dto.setData(device);
				FrameworkUtils.setSuccess(dto);
				log.info("getDevice success!");
			} else {
				FrameworkUtils.setNoData(dto);
				log.error("getDevice failure");
			}
		} catch (Exception e) {
			log.error("getDevice exception!");
			e.printStackTrace();
			throw new RuntimeException("getDevice Exception!");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 修改设备公司信息
	 * 
	 * @author liukh
	 * @date 2017-2-21 下午5:03:49
	 * @param deviceId
	 * @param data
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseDMService#updateDevice(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public String updateDevice(String deviceId, String data) {
		String jsonStr = "";
		BaseDto dto = new BaseDto();
		if (StringUtils.isNotBlank(deviceId) && StringUtils.isNotBlank(data)) {
			try {
				Device device = JSON.parseObject(data, Device.class);
				if (device == null) {
					dto.setRcode(1);
					dto.setRinfo("The data parseObject to Device is null !");
					return JSON.toJSONString(dto);
				}
				device.setId(deviceId);
				if (StringUtils.isNotBlank(device.getDeviceType())) {
					BaseObjDto<DeviceType> deviceTypeDto = baseDao
							.selectByPrimaryKey(DeviceTypeMapper.class,
									StringUtils.trim(device.getDeviceType()));
					if (!FrameworkUtils.isSuccess(deviceTypeDto)) {
						dto.setRcode(1);
						dto.setRinfo("The deviceType is not exist !");
						return JSON.toJSONString(dto);
					}
				}

				if (StringUtils.isNotBlank(device.getDeviceBrand())) {
					BaseObjDto<DeviceBrand> deviceBrandDto = baseDao
							.selectByPrimaryKey(DeviceBrandMapper.class,
									StringUtils.trim(device.getDeviceBrand()));
					if (!FrameworkUtils.isSuccess(deviceBrandDto)) {
						dto.setRcode(1);
						dto.setRinfo("The deviceBrand is not exist !");
						return JSON.toJSONString(dto);
					}
				}

				if (StringUtils.isNotBlank(device.getDeviceModel())) {
					BaseObjDto<DeviceModel> deviceModelDto = baseDao
							.selectByPrimaryKey(DeviceModelMapper.class,
									StringUtils.trim(device.getDeviceModel()));
					if (!FrameworkUtils.isSuccess(deviceModelDto)) {
						dto.setRcode(1);
						dto.setRinfo("The deviceModel is not exist !");
						return JSON.toJSONString(dto);
					}
				}

				if (StringUtils.isNotBlank(device.getCompanyId())) {
					BaseObjDto<Company> companyDto = baseDao
							.selectByPrimaryKey(CompanyMapper.class,
									StringUtils.trim(device.getCompanyId()));
					if (!FrameworkUtils.isSuccess(companyDto)) {
						dto.setRcode(1);
						dto.setRinfo("The companyId is not exist !");
						return JSON.toJSONString(dto);
					}
				}
				dto = baseDao.updateByPrimaryKeySelective(DeviceMapper.class,
						device);

				if (FrameworkUtils.isSuccess(dto)) {
					log.info("updateDevice success!");
				} else {
					log.error("updateDevice failure!");
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("updateDevice exception!");
				throw new RuntimeException("updateDevice Exception!");
			}

		} else {
			log.error("---updateDevice -------- data or deviceTypeSpecDataId is null ==== ");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 删除设备信息
	 * 
	 * @author liukh
	 * @date 2017-2-21 下午5:22:12
	 * @param deviceId
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseDMService#deleteDevice(java.lang.String)
	 */
	@Override
	public String deleteDevice(String deviceId) {
		String jsonStr = "";
		BaseDto dto = new BaseDto();
		if (StringUtils.isNotBlank(deviceId)) {
			// 删除
			dto = baseDao.deleteByPrimaryKey(DeviceMapper.class,
					StringUtils.trim(deviceId));
			if (FrameworkUtils.isSuccess(dto)) {
				log.info("deleteDevice success!");
			}
		} else {
			log.error("---deleteDevice -------- deviceId is null ==== ");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 获取设备信息列表
	 * 
	 * @author liukh
	 * @date 2017-2-21 下午5:50:05
	 * @param companyId
	 * @param deviceBrand
	 * @param deviceId
	 * @param deviceModel
	 * @param deviceName
	 * @param deviceType
	 * @param startDate
	 * @param endDate
	 * @param isImported
	 * @param isRealDevice
	 * @param manufactureYear
	 * @param realDeviceId
	 * @param sequenceNum
	 * @param workingTime
	 * @param status
	 * @param numberOfItem
	 * @param page
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseDMService#getDeviceList(java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String,
	 *      java.lang.String, java.lang.String, java.util.Date, java.util.Date,
	 *      java.lang.Boolean, java.lang.Integer, java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.Integer,
	 *      java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public String getDeviceList(DeviceQueryForm form) {
		String jsonStr = "";
		BaseObjDto<ItemPage<DeviceListDto>> dto = new BaseObjDto<ItemPage<DeviceListDto>>();
		try {
			BaseObjDto<ItemPage<DeviceListDto>> pagesDto = baseDao.getPageList(
					DeviceMapper.class, DeviceListDto.class, form,
					"getDevicePageList");
			if (FrameworkUtils.isSuccess(pagesDto)) {
				dto = pagesDto;
				log.info("getDeviceList success");
			} else {
				FrameworkUtils.setNoData(dto);
				log.error("getDeviceList failure");

			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("getDeviceList Exception!");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 新增设备跟踪信息
	 * 
	 * @author liukh
	 * @date 2017-2-21 下午3:46:28
	 * @param data
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseDMService#addDeviceStatusTrace(java.lang.String)
	 */
	@Override
	public String addDeviceStatusTrace(String data) {
		String jsonStr = "";
		BaseDto dto = new BaseDto();
		if (StringUtils.isNotBlank(data)) {
			try {

				DeviceStatusTrace deviceStatusTrace = JSON.parseObject(data,
						DeviceStatusTrace.class);
				if (deviceStatusTrace == null) {
					dto.setRcode(1);
					dto.setRinfo("The data parseObject to DeviceStatusTrace is null !");
					return JSON.toJSONString(dto);
				}
				if (deviceStatusTrace.getDeviceId() == null) {
					dto.setRcode(1);
					dto.setRinfo("The data's deviceId is null !");
					return JSON.toJSONString(dto);
				}
				if (deviceStatusTrace.getLeasingId() == null) {
					dto.setRcode(1);
					dto.setRinfo("The data's leasingId is null !");
					return JSON.toJSONString(dto);
				}
				if (deviceStatusTrace.getLesseeId() == null) {
					dto.setRcode(1);
					dto.setRinfo("The data's lesseeId is null !");
					return JSON.toJSONString(dto);
				}

				if (StringUtils.isNotBlank(deviceStatusTrace.getDeviceId())) {
					BaseObjDto<Device> deviceDto = baseDao.selectByPrimaryKey(
							DeviceMapper.class,
							StringUtils.trim(deviceStatusTrace.getDeviceId()));
					if (!FrameworkUtils.isSuccess(deviceDto)) {
						dto.setRcode(1);
						dto.setRinfo("The deviceId is not exist !");
						return JSON.toJSONString(dto);
					}
				}
				if (StringUtils.isNotBlank(deviceStatusTrace.getLeasingId())) {
					BaseObjDto<Company> companyDto = baseDao
							.selectByPrimaryKey(CompanyMapper.class,
									StringUtils.trim(deviceStatusTrace
											.getLeasingId()));
					if (!FrameworkUtils.isSuccess(companyDto)) {
						dto.setRcode(1);
						dto.setRinfo("The leasingId is not exist !");
						return JSON.toJSONString(dto);
					}
				}
				if (StringUtils.isNotBlank(deviceStatusTrace.getLesseeId())) {
					BaseObjDto<Company> companyDto = baseDao
							.selectByPrimaryKey(CompanyMapper.class,
									StringUtils.trim(deviceStatusTrace
											.getLesseeId()));
					if (!FrameworkUtils.isSuccess(companyDto)) {
						dto.setRcode(1);
						dto.setRinfo("The lesseeId is not exist !");
						return JSON.toJSONString(dto);
					}
				}

				deviceStatusTrace.setCreateTime(new Date());
				dto = baseDao.insert(DeviceStatusTraceMapper.class,
						deviceStatusTrace);
				if (FrameworkUtils.isSuccess(dto)) {
					log.info("addDeviceStastics success!");
				} else {
					log.error("addDeviceStastics failure!");
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("addDeviceStastics exception!");
				throw new RuntimeException("addDeviceStastics Exception!");
			}
		} else {
			log.error("---addDeviceStastics -------- data is null ==== ");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 删除设备跟踪信息
	 * 
	 * @author liukh
	 * @date 2017-2-21 下午5:25:47
	 * @param deviceStatusTraceId
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseDMService#deleteDeviceStatusTrace(java.lang.String)
	 */
	@Override
	public String deleteDeviceStatusTrace(String deviceStatusTraceId) {
		String jsonStr = "";
		BaseDto dto = new BaseDto();
		if (StringUtils.isNotBlank(deviceStatusTraceId)) {
			// 删除
			dto = baseDao.deleteByPrimaryKey(DeviceStatusTraceMapper.class,
					StringUtils.trim(deviceStatusTraceId));
			if (FrameworkUtils.isSuccess(dto)) {
				log.info("deleteDeviceStastics success!");
			}
		} else {
			log.error("---deleteDeviceStastics -------- deviceStatusTraceId is null ==== ");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 获取设备跟踪信息列表
	 * 
	 * @author liukh
	 * @date 2017-2-21 下午5:37:25
	 * @param deviceId
	 * @param deviceName
	 * @param orderId
	 * @param numberOfItem
	 * @param page
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseDMService#getDeviceStatusTraceList(java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.Integer,
	 *      java.lang.Integer)
	 */
	@Override
	public String getDeviceStatusTraceList(DeviceStatusTraceQueryForm form) {
		String jsonStr = "";
		BaseObjDto<ItemPage<DeviceStatusTrace>> dto = new BaseObjDto<ItemPage<DeviceStatusTrace>>();
		try {
			BaseObjDto<ItemPage<DeviceStatusTrace>> pagesDto = baseDao
					.getPageList(DeviceStatusTraceMapper.class,
							DeviceStatusTrace.class, form,
							"getDeviceStatusTracePageList");
			if (FrameworkUtils.isSuccess(pagesDto)) {
				dto = pagesDto;
				log.info("getDeviceStatusTraceList success");
			} else {
				FrameworkUtils.setNoData(dto);
				log.error("getDeviceStatusTraceList failure");

			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("getDeviceStatusTraceList Exception!");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 新增公司设备信息
	 * 
	 * @author liukh
	 * @date 2017-2-21 下午4:07:26
	 * @param data
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseDMService#addCompanyDeviceType(java.lang.String)
	 */
	@Override
	public String addCompanyDeviceType(String data) {
		String jsonStr = "";
		BaseDto dto = new BaseDto();
		if (StringUtils.isNotBlank(data)) {
			try {

				CompanyDeviceType companyDeviceType = JSON.parseObject(data,
						CompanyDeviceType.class);
				if (companyDeviceType == null) {
					dto.setRcode(1);
					dto.setRinfo("The data parseObject to CompanyDeviceType is null !");
					return JSON.toJSONString(dto);
				}

				if (companyDeviceType.getDeviceModelId() == null) {
					dto.setRcode(1);
					dto.setRinfo("The data 's deviceModelId is null !");
					return JSON.toJSONString(dto);
				}
				if (companyDeviceType.getCompanyId() == null) {
					dto.setRcode(1);
					dto.setRinfo("The data 's companyId is null !");
					return JSON.toJSONString(dto);
				}

				if (companyDeviceType.getModelQuote() == null) {
					dto.setRcode(1);
					dto.setRinfo("The data 's modelQuote is null !");
					return JSON.toJSONString(dto);
				}

				if (StringUtils
						.isNotBlank(companyDeviceType.getDeviceModelId())) {
					BaseObjDto<DeviceModel> deviceModelDto = baseDao
							.selectByPrimaryKey(DeviceModelMapper.class,
									StringUtils.trim(companyDeviceType
											.getDeviceModelId()));
					if (FrameworkUtils.isSuccess(deviceModelDto)) {
						companyDeviceType.setDeviceModelId(companyDeviceType
								.getDeviceModelId());
					} else {
						dto.setRcode(1);
						dto.setRinfo("The deviceModelId is not exist !");
						return JSON.toJSONString(dto);
					}
				}
				if (StringUtils.isNotBlank(companyDeviceType.getCompanyId())) {
					BaseObjDto<Company> companyDto = baseDao
							.selectByPrimaryKey(CompanyMapper.class,
									StringUtils.trim(companyDeviceType
											.getCompanyId()));
					if (!FrameworkUtils.isSuccess(companyDto)) {
						dto.setRcode(1);
						dto.setRinfo("The companyId is not exist !");
						return JSON.toJSONString(dto);
					} else {
						companyDeviceType.setCompanyId(companyDeviceType
								.getCompanyId());
					}
				}
				companyDeviceType.setCreateTime(new Date());
				dto = baseDao.insert(CompanyDeviceTypeMapper.class,
						companyDeviceType);

				if (FrameworkUtils.isSuccess(dto)) {
					log.info("addCompanyDeviceType success!");
				} else {
					log.error("addCompanyDeviceType failure!");
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("addCompanyDeviceType exception!");
				throw new RuntimeException("addCompanyDeviceType Exception!");
			}
		} else {
			log.error("---addCompanyDeviceType -------- data is null ==== ");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 获取公司设备信息
	 * 
	 * @author liukh
	 * @date 2017-2-21 下午4:57:06
	 * @param companyDeviceTypeId
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseDMService#getCompanyDeviceType(java.lang.String)
	 */
	@Override
	public String getCompanyDeviceType(String companyDeviceTypeId) {
		String jsonStr = "";
		BaseObjDto<CompanyDeviceType> dto = new BaseObjDto<CompanyDeviceType>();
		try {
			if (StringUtils.isBlank(companyDeviceTypeId)) {
				dto.setRinfo("companyDeviceTypeId is null");
				return JSON.toJSONString(dto);
			}
			BaseObjDto<CompanyDeviceType> companyDeviceTypeDto = baseDao
					.selectByPrimaryKey(CompanyDeviceTypeMapper.class,
							StringUtils.trim(companyDeviceTypeId));
			if (FrameworkUtils.isSuccess(companyDeviceTypeDto)) {
				CompanyDeviceType companyDeviceType = companyDeviceTypeDto
						.getData();
				dto.setData(companyDeviceType);
				FrameworkUtils.setSuccess(dto);
				log.info("getCompanyDeviceType success!");
			} else {
				FrameworkUtils.setNoData(dto);
				log.error("getCompanyDeviceType failure");
			}
		} catch (Exception e) {

			e.printStackTrace();
			log.error("getCompanyDeviceType exception!");
			throw new RuntimeException("getCompanyDeviceType Exception!");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 修改公司设备信息
	 * 
	 * @author liukh
	 * @date 2017-2-21 下午5:02:43
	 * @param companyDeviceTypeId
	 * @param data
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseDMService#updateCompanyDeviceType(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public String updateCompanyDeviceType(String companyDeviceTypeId,
			String data) {
		String jsonStr = "";
		BaseDto dto = new BaseDto();
		if (StringUtils.isNotBlank(companyDeviceTypeId)
				&& StringUtils.isNotBlank(data)) {
			try {
				CompanyDeviceType companyDeviceType = JSON.parseObject(data,
						CompanyDeviceType.class);
				if (companyDeviceType == null) {
					dto.setRcode(1);
					dto.setRinfo("The data parseObject to CompanyDeviceType is null !");
					return JSON.toJSONString(dto);
				}
				companyDeviceType.setId(companyDeviceTypeId);
				if (companyDeviceType.getModelQuote() == null
						&& companyDeviceType.getPicture() == null) {
					dto.setRcode(1);
					dto.setRinfo("The data's modelQuote and picture is both null !");
					return JSON.toJSONString(dto);
				}

				dto = baseDao.updateByPrimaryKeySelective(
						CompanyDeviceTypeMapper.class, companyDeviceType);

				if (FrameworkUtils.isSuccess(dto)) {
					log.info("updateCompanyDeviceType success!");
				} else {
					log.error("updateCompanyDeviceType failure!");
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("updateCompanyDeviceType exception!");
				throw new RuntimeException("updateCompanyDeviceType Exception!");
			}

		} else {
			log.error("---updateCompanyDeviceType -------- data or deviceTypeSpecDataId is null ==== ");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 删除公司设备信息
	 * 
	 * @author liukh
	 * @date 2017-2-21 下午5:51:30
	 * @param companyDeviceTypeId
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseDMService#deleteCompanyDeviceType(java.lang.String)
	 */
	@Override
	public String deleteCompanyDeviceType(String companyDeviceTypeId) {
		String jsonStr = "";
		BaseDto dto = new BaseDto();
		if (StringUtils.isNotBlank(companyDeviceTypeId)) {
			// 删除
			dto = baseDao.deleteByPrimaryKey(CompanyDeviceTypeMapper.class,
					StringUtils.trim(companyDeviceTypeId));
			if (FrameworkUtils.isSuccess(dto)) {
				log.info("deleteCompanyDeviceType success!");
			}
		} else {
			log.error("---deleteCompanyDeviceType -------- companyDeviceTypeId is null ==== ");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 获取公司设备信息列表
	 * 
	 * @author liukh
	 * @date 2017-2-21 下午6:09:48
	 * @param companyId
	 * @param deviceModelId
	 * @param numberOfItem
	 * @param page
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseDMService#getCompanyDeviceTypeList(java.lang.String,
	 *      java.lang.String, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public String getCompanyDeviceTypeList(CompanyDeviceTypeQueryForm form) {
		String jsonStr = "";
		BaseObjDto<ItemPage<CompanyDeviceType>> dto = new BaseObjDto<ItemPage<CompanyDeviceType>>();
		try {
			BaseObjDto<ItemPage<CompanyDeviceType>> pagesDto = baseDao
					.getPageList(CompanyDeviceTypeMapper.class,
							CompanyDeviceType.class, form,
							"getCompanyDeviceTypePageList");
			if (FrameworkUtils.isSuccess(pagesDto)) {
				dto = pagesDto;
				log.info("getCompanyDeviceTypeList success");
			} else {
				FrameworkUtils.setNoData(dto);
				log.error("getCompanyDeviceTypeList failure");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("getCompanyDeviceTypeList exception!");
			throw new RuntimeException("getCompanyDeviceTypeList Exception!");

		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 获取可租设备信息列表
	 * 
	 * @author liukh
	 * @date 2017-3-7 下午10:08:16
	 * @param form
	 * @return
	 */
	@Override
	public String getCompanyAvailableDeviceList(
			CompanyAvailableDeviceQueryForm form) {
		String jsonStr = "";
		BaseObjDto<ItemPage<CompanyAvailableDevice>> dto = new BaseObjDto<ItemPage<CompanyAvailableDevice>>();
		try {
			BaseObjDto<ItemPage<CompanyAvailableDevice>> pagesDto = baseDao
					.getPageList(CompanyAvailableDeviceMapper.class,
							CompanyAvailableDevice.class, form,
							"getCompanyAvailableDevicePageList");
			if (FrameworkUtils.isSuccess(pagesDto)) {
				dto = pagesDto;
				if(form!= null && form.getLatitude()!=null && form.getLongitude()!= null){
					double distance = 0;
					List<CompanyAvailableDevice> processList = new ArrayList<CompanyAvailableDevice>();
					for(CompanyAvailableDevice companyAvailableDevice:pagesDto.getData().getItems()){
						if(companyAvailableDevice.getLatitude()!= null && companyAvailableDevice.getLongitude()!= null){
							distance = MapToolUtils.distance(
									Double.valueOf(form.getLongitude()),
									Double.valueOf(form.getLatitude()),
									Double.valueOf(companyAvailableDevice.getLongitude()), Double.valueOf(companyAvailableDevice.getLatitude()));
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
							companyAvailableDevice.setDistance(sbf.toString());
						}
						processList.add(companyAvailableDevice);
						
						
					}
					dto.getData().setItems(null);
					dto.getData().setItems(processList);
					
				}
				log.info("getCompanyAvailableDeviceList success");
			} else {
				FrameworkUtils.setNoData(dto);
			}
		} catch (Exception e) {

			e.printStackTrace();
			log.error("getCompanyAvailableDeviceList exception");
			throw new RuntimeException(
					"getCompanyAvailableDeviceList Exception!");

		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 获取可租信息
	 * 
	 * @author liukh
	 * @date 2017-3-7 下午10:08:39
	 * @param companyId
	 * @param deviceModelId
	 * @return
	 */
	@Override
	public String getCompanyAvailableDeviceInfor(String companyId,
			String deviceModelId) {
		String jsonStr = "";
		BaseObjDto<CompanyAvailableDevice> dto = new BaseObjDto<CompanyAvailableDevice>();
		try {
			if (StringUtils.isBlank(companyId)
					&& StringUtils.isBlank(deviceModelId)) {
				dto.setRinfo("companyId and deviceModelId 不能都为空");
				return JSON.toJSONString(dto);
			}
			Map<String, Object> companyAvaParams = new HashMap<String, Object>();
			companyAvaParams.put("companyId", StringUtils.trim(companyId));
			companyAvaParams.put("deviceModelId",
					StringUtils.trim(deviceModelId));
			dto = baseDao.getObjProperty(CompanyAvailableDeviceMapper.class,
					"getCompanyAvailableDevice", companyAvaParams);
			if (!FrameworkUtils.isSuccess(dto))  {
				dto = baseDao.getObjProperty(CompanyAvailableDeviceMapper.class,
						"getCompanyNotAvailableDevice", companyAvaParams);
				if(!FrameworkUtils.isSuccess(dto)){
					FrameworkUtils.setNoData(dto);
				}
				log.error("getCompanyAvailableDeviceInfor failure");
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.error("getCompanyAvailableDeviceInfor exception");
			throw new RuntimeException(
					"getCompanyAvailableDeviceInfor Exception!");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 以半径获取待租设备
	 * 
	 * @author liukh
	 * @date 2017-3-17 上午11:16:07
	 * @param replaceParams
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseDMService#getNearCompanyAvailableDeviceListInRadius(java.util.Map)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public String getNearCompanyAvailableDeviceListInRadius(Map replaceParams) {
		String jsonStr = null;
		BaseObjDto<ItemPage<CompanyAvailableDeviceInMapDto>> dto = new BaseObjDto<ItemPage<CompanyAvailableDeviceInMapDto>>();
		jsonStr = JSON.toJSONString(dto);
		try {
			if (replaceParams == null) {
				dto.setRinfo("The param is null !");
				dto.setRcode(21);
				return JSON.toJSONString(dto);
			} else if (replaceParams.get("myLongitude") == null) {
				dto.setRinfo("The param's  myLongitude is null !");
				dto.setRcode(21);
				return JSON.toJSONString(dto);
			} else if (replaceParams.get("myLatitude") == null) {
				dto.setRinfo("The param's  myLatitude is null !");
				dto.setRcode(21);
				return JSON.toJSONString(dto);
			}
			double distanceCustom = replaceParams.get("distanceCustom") == null ? Constant.DISTANCE_DIFF
					: Double.parseDouble(String.valueOf(replaceParams
							.get("distanceCustom")));
			CompanyAvailableDeviceQueryForm form = new CompanyAvailableDeviceQueryForm();
			if (replaceParams.get("deviceTypeId") != null) {
				form.setDeviceTypeId(replaceParams.get("deviceTypeId")
						.toString());

			}
			if (replaceParams.get("parentDeviceTypeId") != null) {
				form.setParentDeviceTypeId(replaceParams.get(
						"parentDeviceTypeId").toString());

			}

			BaseObjDto<List<CompanyAvailableDevice>> listDto = baseDao.getList(
					CompanyAvailableDeviceMapper.class,
					CompanyAvailableDevice.class,
					"getCompanyAvailableDeviceList", form);
			String areaLongitudeStr = null;
			String areaLatitudeStr = null;
			Double areaLongitude = null;
			Double areaLatitude = null;
			double distance = 0;

			if (FrameworkUtils.isSuccess(listDto)) {
				List<CompanyAvailableDevice> listCompanyAvailableDevice = listDto
						.getData();
				Map<String, Integer> cachComapnyTotal = new HashMap<String, Integer>();
				Map<String, CompanyAvailableDeviceInMapDto> cachComapny = new HashMap<String, CompanyAvailableDeviceInMapDto>();
				for (int index = 0; index < listCompanyAvailableDevice.size(); index++) {
					CompanyAvailableDevice companyAvailableDevice = listCompanyAvailableDevice
							.get(index);
					if (companyAvailableDevice != null
							&& companyAvailableDevice.getLatitude() != null
							&& companyAvailableDevice.getLongitude() != null) {
						areaLongitudeStr = companyAvailableDevice
								.getLongitude();
						areaLatitudeStr = companyAvailableDevice.getLatitude();
						if (StringUtils.isNotBlank(areaLongitudeStr)
								&& StringUtils.isNotBlank(areaLatitudeStr)) {
							areaLongitude = Double.valueOf(areaLongitudeStr);
							areaLatitude = Double.valueOf(areaLatitudeStr);
							distance = MapToolUtils.distance(
									Double.valueOf(replaceParams.get(
											"myLongitude").toString()),
									Double.valueOf(replaceParams.get(
											"myLatitude").toString()),
									areaLongitude, areaLatitude);
							if (distance <= distanceCustom) {
								if (cachComapny != null
										&& !cachComapny
												.containsKey(companyAvailableDevice
														.getCompanyId())) {
									CompanyAvailableDeviceInMapDto companyAvailableDeviceInMapDto = new CompanyAvailableDeviceInMapDto();
									companyAvailableDeviceInMapDto
											.setCompanyId(companyAvailableDevice
													.getCompanyId());
									companyAvailableDeviceInMapDto
											.setCompanyName(companyAvailableDevice
													.getCompanyName());
									companyAvailableDeviceInMapDto
											.setTotal(companyAvailableDevice
													.getTotal());
									companyAvailableDeviceInMapDto
											.setLongitude(companyAvailableDevice
													.getLongitude());
									companyAvailableDeviceInMapDto
											.setLatitude(companyAvailableDevice
													.getLatitude());
									companyAvailableDeviceInMapDto
											.setDistance(distance);
									companyAvailableDeviceInMapDto
											.setAddress(companyAvailableDevice
													.getAddress());
									cachComapny.put(companyAvailableDevice
											.getCompanyId(),
											companyAvailableDeviceInMapDto);
									cachComapnyTotal.put(companyAvailableDevice
											.getCompanyId(),
											companyAvailableDeviceInMapDto
													.getTotal());
								} else {
									Integer lasetTotal = cachComapnyTotal
											.get(companyAvailableDevice
													.getCompanyId());
									if (lasetTotal != null) {
										lasetTotal += companyAvailableDevice
												.getTotal();
										cachComapnyTotal.put(
												companyAvailableDevice
														.getCompanyId(),
												lasetTotal);
									}
								}

							}
						}

					}
				}
				BaseObjDto<ItemPage<CompanyAvailableDeviceInMapDto>> returnBaseListDto = new BaseObjDto<ItemPage<CompanyAvailableDeviceInMapDto>>();
				if (cachComapny != null && cachComapny.size() > 0) {
					List<CompanyAvailableDeviceInMapDto> qualifiedList = new ArrayList<CompanyAvailableDeviceInMapDto>();
					for (String key : cachComapny.keySet()) {
						CompanyAvailableDeviceInMapDto companyAvailableDeviceInMapDto = cachComapny
								.get(key);
						companyAvailableDeviceInMapDto
								.setTotal(cachComapnyTotal.get(key));
						qualifiedList.add(companyAvailableDeviceInMapDto);
					}
					if (qualifiedList.size() > 0) {
						ItemPage<CompanyAvailableDeviceInMapDto> page = new ItemPage<CompanyAvailableDeviceInMapDto>();
						page.setItems(qualifiedList);
						page.setRowsCount(qualifiedList.size());
						page.setPreIndex(0);
						page.setPageIndex(1);
						page.setNextIndex(1);
						page.setPageSize(qualifiedList.size());
						page.setPagesCount(1);
						returnBaseListDto.setData(page);
						dto = returnBaseListDto;
						log.info("getNearCompanyAvailableDeviceListInRadius success");
					} else {
						FrameworkUtils.setNoData(dto);
						log.info("getNearCompanyAvailableDeviceListInRadius no data");
					}

				} else {
					FrameworkUtils.setNoData(dto);
					log.info("getNearCompanyAvailableDeviceListInRadius not data");
				}

			} else {
				FrameworkUtils.setNoData(dto);
				log.info("getNearCompanyAvailableDeviceListInRadius not data");
			}

		} catch (Exception e) {
			log.error("查询附近设备异常");
			dto.setRinfo("数据异常!");

			log.error("getNearCompanyAvailableDeviceListInRadius ---- 异常,message = "
					+ e.getMessage());
			e.printStackTrace();
		}
		jsonStr = JSON.toJSONString(dto);
		log.info("getNearCompanyAvailableDeviceListInRadius ---- jsonStr ========= "
				+ jsonStr);
		return jsonStr;
	}

	/**
	 * 以手机屏幕查询附近可租机械设备
	 * 
	 * @author liukh
	 * @date 2017-3-17 上午11:29:03
	 * @param replaceParams
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseDMService#getNearCompanyAvailableDeviceListInScreen(java.util.Map)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public String getNearCompanyAvailableDeviceListInScreen(Map replaceParams) {
		String jsonStr = null;
		BaseObjDto<ItemPage<CompanyAvailableDeviceInMapDto>> dto = new BaseObjDto<ItemPage<CompanyAvailableDeviceInMapDto>>();
		jsonStr = JSON.toJSONString(dto);
		try {
			if (replaceParams == null) {
				dto.setRinfo("The param is null !");
				dto.setRcode(21);
				return JSON.toJSONString(dto);
			} else if (replaceParams.get("myLongitude") == null) {
				dto.setRinfo("The param's  myLongitude is null !");
				dto.setRcode(21);
				return JSON.toJSONString(dto);
			} else if (replaceParams.get("myLatitude") == null) {
				dto.setRinfo("The param's  myLatitude is null !");
				dto.setRcode(21);
				return JSON.toJSONString(dto);
			} else if (replaceParams.get("leftLongitude") == null) {
				dto.setRinfo("The param's  leftLongitude is null !");
				dto.setRcode(21);
				return JSON.toJSONString(dto);
			} else if (replaceParams.get("leftLatitude") == null) {
				dto.setRinfo("The param's  leftLatitude is null !");
				dto.setRcode(21);
				return JSON.toJSONString(dto);
			} else if (replaceParams.get("rightLongitude") == null) {
				dto.setRinfo("The param's  rightLongitude is null !");
				dto.setRcode(21);
				return JSON.toJSONString(dto);
			} else if (replaceParams.get("rightLatitude") == null) {
				dto.setRinfo("The param's  rightLatitude is null !");
				dto.setRcode(21);
				return JSON.toJSONString(dto);
			}

			CompanyAvailableDeviceQueryForm form = new CompanyAvailableDeviceQueryForm();
			if (replaceParams.get("deviceTypeId") != null) {
				form.setDeviceTypeId(replaceParams.get("deviceTypeId")
						.toString());

			}
			if (replaceParams.get("parentDeviceTypeId") != null) {
				form.setParentDeviceTypeId(replaceParams.get(
						"parentDeviceTypeId").toString());

			}
			if(form!= null){
				form.setNumberOfItem(0);
			}

			
			BaseObjDto<List<CompanyAvailableDevice>> listDto = baseDao.getList(CompanyAvailableDeviceMapper.class,CompanyAvailableDevice.class,"getCompanyAvailableDeviceList", form);
			String areaLongitudeStr = null;
			String areaLatitudeStr = null;
			Double areaLongitude = null;
			Double areaLatitude = null;
			Double areaLatitude1 = null;
			Double areaLatitude2 = null;
			Double areaLongitude1 = null;
			Double areaLongitude2 = null;
			double distance = 0;

			if (FrameworkUtils.isSuccess(listDto)) {
				List<CompanyAvailableDevice> listCompanyAvailableDevice = listDto
						.getData();
				Map<String, Integer> cachComapnyTotal = new HashMap<String, Integer>();
				Map<String, CompanyAvailableDeviceInMapDto> cachComapny = new HashMap<String, CompanyAvailableDeviceInMapDto>();
				for (int index = 0; index < listCompanyAvailableDevice.size(); index++) {
					CompanyAvailableDevice companyAvailableDevice = listCompanyAvailableDevice
							.get(index);
					if (companyAvailableDevice != null
							&& companyAvailableDevice.getLatitude() != null
							&& companyAvailableDevice.getLongitude() != null) {
						areaLongitudeStr = companyAvailableDevice
								.getLongitude();
						areaLatitudeStr = companyAvailableDevice.getLatitude();
						if (StringUtils.isNotBlank(areaLongitudeStr)
								&& StringUtils.isNotBlank(areaLatitudeStr)
								&& StringUtils.isNotBlank(replaceParams.get(
										"myLongitude").toString())
								&& StringUtils.isNotBlank(replaceParams.get(
										"myLatitude").toString())
								&& StringUtils.isNotBlank(replaceParams.get(
										"leftLongitude").toString())
								&& StringUtils.isNotBlank(replaceParams.get(
										"leftLatitude").toString())
								&& StringUtils.isNotBlank(replaceParams.get(
										"rightLongitude").toString())
								&& StringUtils.isNotBlank(replaceParams.get(
										"rightLatitude").toString())) {

							areaLongitude = Double.valueOf(areaLongitudeStr);
							areaLatitude = Double.valueOf(areaLatitudeStr);
							areaLatitude1 = Double.valueOf(replaceParams.get(
									"leftLatitude").toString());
							areaLatitude2 = Double.valueOf(replaceParams.get(
									"rightLatitude").toString());
							areaLongitude1 = Double.valueOf(replaceParams.get(
									"leftLongitude").toString());
							areaLongitude2 = Double.valueOf(replaceParams.get(
									"rightLongitude").toString());
							if (MapToolUtils.isInArea(areaLatitude,
									areaLongitude, areaLatitude1,
									areaLatitude2, areaLongitude1,
									areaLongitude2)) {
								distance = MapToolUtils.distance(
										Double.valueOf(replaceParams.get(
												"myLongitude").toString()),
										Double.valueOf(replaceParams.get(
												"myLatitude").toString()),
										areaLongitude, areaLatitude);
								if (cachComapny != null
										&& !cachComapny
												.containsKey(companyAvailableDevice
														.getCompanyId())) {
									CompanyAvailableDeviceInMapDto companyAvailableDeviceInMapDto = new CompanyAvailableDeviceInMapDto();
									companyAvailableDeviceInMapDto
											.setCompanyId(companyAvailableDevice
													.getCompanyId());
									companyAvailableDeviceInMapDto
											.setCompanyName(companyAvailableDevice
													.getCompanyName());
									companyAvailableDeviceInMapDto
											.setTotal(companyAvailableDevice
													.getTotal());
									companyAvailableDeviceInMapDto
											.setLongitude(companyAvailableDevice
													.getLongitude());
									companyAvailableDeviceInMapDto
											.setLatitude(companyAvailableDevice
													.getLatitude());
									companyAvailableDeviceInMapDto
											.setDistance(distance);
									companyAvailableDeviceInMapDto
											.setAddress(companyAvailableDevice
													.getAddress());
									cachComapny.put(companyAvailableDevice
											.getCompanyId(),
											companyAvailableDeviceInMapDto);
									cachComapnyTotal.put(companyAvailableDevice
											.getCompanyId(),
											companyAvailableDeviceInMapDto
													.getTotal());
								} else {
									Integer lasetTotal = cachComapnyTotal
											.get(companyAvailableDevice
													.getCompanyId());
									if (lasetTotal != null) {
										lasetTotal += companyAvailableDevice
												.getTotal();
										cachComapnyTotal.put(
												companyAvailableDevice
														.getCompanyId(),
												lasetTotal);
									}

								}

							}

						}
					}
				}
				BaseObjDto<ItemPage<CompanyAvailableDeviceInMapDto>> returnBaseListDto = new BaseObjDto<ItemPage<CompanyAvailableDeviceInMapDto>>();
				if (cachComapny != null && cachComapny.size() > 0) {
					List<CompanyAvailableDeviceInMapDto> qualifiedList = new ArrayList<CompanyAvailableDeviceInMapDto>();
					for (String key : cachComapny.keySet()) {
						CompanyAvailableDeviceInMapDto companyAvailableDeviceInMapDto = cachComapny
								.get(key);
						companyAvailableDeviceInMapDto
								.setTotal(cachComapnyTotal.get(key));
						qualifiedList.add(companyAvailableDeviceInMapDto);
					}
					if (qualifiedList.size() > 0) {
						returnBaseListDto.setRcode(BaseDto.SUCCESS_RCODE);
						returnBaseListDto.setRinfo(BaseDto.DEFAULT_SUCCESS_INFO);
						ItemPage<CompanyAvailableDeviceInMapDto> page = new ItemPage<CompanyAvailableDeviceInMapDto>();
						page.setItems(qualifiedList);
						page.setRowsCount(qualifiedList.size());
						page.setPreIndex(0);
						page.setPageIndex(1);
						page.setNextIndex(1);
						page.setPageSize(qualifiedList.size());
						page.setPagesCount(1);
						returnBaseListDto.setData(page);
						
						dto = returnBaseListDto;
						log.info("getNearCompanyAvailableDeviceListInScreen success");
					} else {
						FrameworkUtils.setNoData(dto);
						log.info("getNearCompanyAvailableDeviceListInScreen no data");
					}

				} else {
					FrameworkUtils.setNoData(dto);
					log.info("getNearCompanyAvailableDeviceListInRadius not data");
				}

			} else {
				FrameworkUtils.setNoData(dto);
				log.info("getNearCompanyAvailableDeviceListInScreen not data");
			}

		} catch (Exception e) {
			log.error("查询附近设备异常");
			dto.setRinfo("数据异常!");

			log.error("getNearCompanyAvailableDeviceListInScreen ---- 异常,message = "
					+ e.getMessage());
			e.printStackTrace();
		}
		jsonStr = JSON.toJSONString(dto);
		log.info("getNearCompanyAvailableDeviceListInScreen ---- jsonStr ========= "
				+ jsonStr);
		return jsonStr;
	}
/**
 * 增加或者更新设备型号
 * @author liukh
 * @date 2017-3-17 下午2:50:25 
 * @param data
 * @return
 * (non-Javadoc)
 * @see com.zj.access.service.BaseDMService#addCompanyDeviceTypeOrUpdaeWhenExist(java.lang.String)
 */
	@Override
	public String addCompanyDeviceTypeOrUpdaeWhenExist(String data) {
		String jsonStr = "";
		BaseDto dto = new BaseDto();
		if (StringUtils.isNotBlank(data)) {
			try {

				CompanyDeviceType companyDeviceType = JSON.parseObject(data,CompanyDeviceType.class);
				if (companyDeviceType == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data parseObject to CompanyDeviceType is null !");
					return JSON.toJSONString(dto);
				}

				if (companyDeviceType.getDeviceModelId() == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data 's deviceModelId is null !");
					return JSON.toJSONString(dto);
				}
				if (companyDeviceType.getCompanyId() == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data 's companyId is null !");
					return JSON.toJSONString(dto);
				}

				if (companyDeviceType.getModelQuote() == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data 's modelQuote is null !");
					return JSON.toJSONString(dto);
				}

				if (StringUtils
						.isNotBlank(companyDeviceType.getDeviceModelId())) {
					BaseObjDto<DeviceModel> deviceModelDto = baseDao
							.selectByPrimaryKey(DeviceModelMapper.class,
									StringUtils.trim(companyDeviceType
											.getDeviceModelId()));
					if (FrameworkUtils.isSuccess(deviceModelDto)) {
						companyDeviceType.setDeviceModelId(companyDeviceType
								.getDeviceModelId());
						if(deviceModelDto.getData() != null){
							companyDeviceType.setDeviceModelName(deviceModelDto.getData().getModelName());
						}
						
					} else {
						dto.setRcode(BaseDto.ERROR_RCODE);
						dto.setRinfo("The deviceModelId is not exist !");
						return JSON.toJSONString(dto);
					}
				}
				if (StringUtils.isNotBlank(companyDeviceType.getCompanyId())) {
					BaseObjDto<Company> companyDto = baseDao
							.selectByPrimaryKey(CompanyMapper.class,
									StringUtils.trim(companyDeviceType
											.getCompanyId()));
					if (!FrameworkUtils.isSuccess(companyDto)) {
						dto.setRcode(BaseDto.ERROR_RCODE);
						dto.setRinfo("The companyId is not exist !");
						return JSON.toJSONString(dto);
					} else {
						companyDeviceType.setCompanyId(companyDeviceType
								.getCompanyId());
					}
				}
				if(companyDeviceType.getPicture() == null){
					companyDeviceType.setPicture(Constant.REQUIRED_PICTURE_TEMP);
				}
				companyDeviceType.setCreateTime(new Date());
				CompanyDeviceType queryCompanyDeviceType = baseOtherService.getCompanyDeviceTypeByComapnyIdAndModelId(companyDeviceType.getCompanyId(), companyDeviceType.getDeviceModelId());
				if(queryCompanyDeviceType == null){
					dto = baseDao.insert(CompanyDeviceTypeMapper.class,companyDeviceType);
					if(FrameworkUtils.isSuccess(dto)){
						BaseObjDto<JSONObject> returnDto = new BaseObjDto<JSONObject>();
						returnDto.setRcode(BaseDto.SUCCESS_RCODE);
						returnDto.setRinfo(BaseDto.DEFAULT_SUCCESS_INFO);
						JSONObject js = new JSONObject();
						js.put("id", companyDeviceType.getId());
						js.put("createTime", companyDeviceType.getCreateTime());
						returnDto.setData(js);
						dto = returnDto;
					}
					
				}else{
					CompanyDeviceType updateCompanyDeviceType = new CompanyDeviceType();
					updateCompanyDeviceType.setId(queryCompanyDeviceType.getId());
					updateCompanyDeviceType.setModelQuote(companyDeviceType.getModelQuote());
					dto = baseDao.updateByPrimaryKeySelective(CompanyDeviceTypeMapper.class, updateCompanyDeviceType);
					if(FrameworkUtils.isSuccess(dto)){
						BaseObjDto<JSONObject> returnDto = new BaseObjDto<JSONObject>();
						returnDto.setRcode(BaseDto.SUCCESS_RCODE);
						returnDto.setRinfo(BaseDto.DEFAULT_SUCCESS_INFO);
						JSONObject js = new JSONObject();
						js.put("id", queryCompanyDeviceType.getId());
						returnDto.setData(js);
						dto = returnDto;
					}
				}
			
			} catch (Exception e) {
				e.printStackTrace();
				log.error("addCompanyDeviceTypeOrUpdaeWhenExist exception!");
				throw new RuntimeException("addCompanyDeviceTypeOrUpdaeWhenExist Exception!");
			}
		} else {
			log.error("---addCompanyDeviceTypeOrUpdaeWhenExist -------- data is null ==== ");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}
/**
 * 修改公司设备类型的图片或报价
 * @author liukh
 * @date 2017-3-17 下午3:24:02 
 * @param data
 * @return
 * (non-Javadoc)
 * @see com.zj.access.service.BaseDMService#updateCompanyDeviceTypebyCompanyIdAndModelId(java.lang.String)
 */
@Override
public String updateCompanyDeviceTypebyCompanyIdAndModelId(String data) {
	String jsonStr = "";
	BaseDto dto = new BaseDto();
	if (StringUtils.isNotBlank(data)) {
		try {

			CompanyDeviceType companyDeviceType = JSON.parseObject(data,CompanyDeviceType.class);
			if (companyDeviceType == null) {
				dto.setRcode(BaseDto.ERROR_RCODE);
				dto.setRinfo("The data parseObject to CompanyDeviceType is null !");
				return JSON.toJSONString(dto);
			}

			if (companyDeviceType.getDeviceModelId() == null) {
				dto.setRcode(BaseDto.ERROR_RCODE);
				dto.setRinfo("The data 's deviceModelId is null !");
				return JSON.toJSONString(dto);
			}
			if (companyDeviceType.getCompanyId() == null) {
				dto.setRcode(BaseDto.ERROR_RCODE);
				dto.setRinfo("The data 's companyId is null !");
				return JSON.toJSONString(dto);
			}

			if (companyDeviceType.getModelQuote() == null && companyDeviceType.getPicture() == null) {
				dto.setRcode(BaseDto.ERROR_RCODE);
				dto.setRinfo("The data 's modelQuote and picture both is  null !");
				return JSON.toJSONString(dto);
			}

			if (StringUtils
					.isNotBlank(companyDeviceType.getDeviceModelId())) {
				BaseObjDto<DeviceModel> deviceModelDto = baseDao
						.selectByPrimaryKey(DeviceModelMapper.class,
								StringUtils.trim(companyDeviceType
										.getDeviceModelId()));
				if (FrameworkUtils.isSuccess(deviceModelDto)) {
					companyDeviceType.setDeviceModelId(companyDeviceType
							.getDeviceModelId());
					if(deviceModelDto.getData() != null){
						companyDeviceType.setDeviceModelName(deviceModelDto.getData().getModelName());
					}
					
				} else {
					dto.setRcode(BaseDto.NO_DATA_RCODE);
					dto.setRinfo("The deviceModelId is not exist !");
					return JSON.toJSONString(dto);
				}
			}
			if (StringUtils.isNotBlank(companyDeviceType.getCompanyId())) {
				BaseObjDto<Company> companyDto = baseDao
						.selectByPrimaryKey(CompanyMapper.class,
								StringUtils.trim(companyDeviceType
										.getCompanyId()));
				if (!FrameworkUtils.isSuccess(companyDto)) {
					dto.setRcode(BaseDto.NO_DATA_RCODE);
					dto.setRinfo("The companyId is not exist !");
					return JSON.toJSONString(dto);
				} else {
					companyDeviceType.setCompanyId(companyDeviceType
							.getCompanyId());
				}
			}
		
			companyDeviceType.setCreateTime(new Date());
			CompanyDeviceType queryCompanyDeviceType = baseOtherService.getCompanyDeviceTypeByComapnyIdAndModelId(companyDeviceType.getCompanyId(), companyDeviceType.getDeviceModelId());
			if(queryCompanyDeviceType == null){
				dto.setRcode(BaseDto.NO_DATA_RCODE);
				dto.setRinfo("The query companyDevice by comapnyId and deviceModelId is not exits !");
				return JSON.toJSONString(dto);
				
			}else{
				CompanyDeviceType updateCompanyDeviceType = new CompanyDeviceType();
				updateCompanyDeviceType.setId(queryCompanyDeviceType.getId());
				if(companyDeviceType.getModelQuote()!= null){
					updateCompanyDeviceType.setModelQuote(companyDeviceType.getModelQuote());
				}
				if(companyDeviceType.getPicture()!= null){
					updateCompanyDeviceType.setPicture(companyDeviceType.getPicture());
				}
			
				dto = baseDao.updateByPrimaryKeySelective(CompanyDeviceTypeMapper.class, updateCompanyDeviceType);
			}
		
		} catch (Exception e) {
			e.printStackTrace();
			log.error("updateCompanyDeviceTypebyCompanyIdAndModelId exception!");
			throw new RuntimeException("updateCompanyDeviceTypebyCompanyIdAndModelId Exception!");
		}
	} else {
		log.error("---updateCompanyDeviceTypebyCompanyIdAndModelId -------- data is null ==== ");
	}
	jsonStr = JSON.toJSONString(dto);
	return jsonStr;
}
/**
 * 增加设备的时候，更新设备公司型号的图片
 * @author liukh
 * @date 2017-3-17 下午3:50:25 
 * @param companyDeviceTypeId
 * @param data
 * @return
 * (non-Javadoc)
 * @see com.zj.access.service.BaseDMService#addDeviceAndInserPictureToCompanyDevice(java.lang.String, java.lang.String)
 */
@Override
public String addDeviceAndInserPictureToCompanyDevice(
		String companyDeviceTypeId, String data) {
	String jsonStr = "";
	BaseDto dto = new BaseDto();
	if (StringUtils.isNotBlank(data) && StringUtils.isNotBlank(companyDeviceTypeId) ) {
		try {
			Device device = JSON.parseObject(data, Device.class);
			if (device == null) {
				dto.setRcode(BaseDto.ERROR_RCODE);
				dto.setRinfo("The data parseObject to Device is null !");
				return JSON.toJSONString(dto);
			}
			if (StringUtils.isBlank(device.getDeviceName())) {
				dto.setRcode(BaseDto.ERROR_RCODE);
				dto.setRinfo("The data's deviceName is null !");
				return JSON.toJSONString(dto);
			}
			if (StringUtils.isBlank(device.getPicture())) {
				dto.setRcode(BaseDto.ERROR_RCODE);
				dto.setRinfo("The data's picture is null !");
				return JSON.toJSONString(dto);
			}
			if (device.getIsRealDevice() == null) {
				dto.setRcode(BaseDto.ERROR_RCODE);
				dto.setRinfo("The data's isRealDevice is null !");
				return JSON.toJSONString(dto);
			}
		
			BaseObjDto<CompanyDeviceType> companyDeviceTypeDto = baseDao
					.selectByPrimaryKey(CompanyDeviceTypeMapper.class,
							StringUtils.trim(companyDeviceTypeId));
			if (!FrameworkUtils.isSuccess(companyDeviceTypeDto)) {
				dto.setRcode(BaseDto.NO_DATA_RCODE);
				dto.setRinfo("The companyDeviceTypeId is not exist  !");
				return JSON.toJSONString(dto);
					
			}
			CompanyDeviceType companyDeviceType = companyDeviceTypeDto.getData();
			if(companyDeviceType != null && companyDeviceType.getDeviceModelId()!= null && companyDeviceType.getCompanyId() != null){
				device.setDeviceModel(companyDeviceType.getDeviceModelId());
				device.setCompanyId(companyDeviceType.getCompanyId());
			}
			if (StringUtils.isBlank(device.getDeviceModel())) {
				dto.setRcode(BaseDto.ERROR_RCODE);
				dto.setRinfo("The data's deviceModel is not exist !");
				return JSON.toJSONString(dto);
			}
			if (StringUtils.isNotBlank(device.getDeviceModel())) {
				BaseObjDto<DeviceModel> deviceModelDto = baseDao
						.selectByPrimaryKey(DeviceModelMapper.class,
								StringUtils.trim(device.getDeviceModel()));
				if (!FrameworkUtils.isSuccess(deviceModelDto)) {
					dto.setRcode(BaseDto.NO_DATA_RCODE);
					dto.setRinfo("The deviceModel is not exist !");
					return JSON.toJSONString(dto);
				}
				DeviceModel deviceModel = deviceModelDto.getData();
				if (deviceModel != null) {
					if (deviceModel.getDeviceBrand() != null) {
						device.setDeviceBrand(deviceModel.getDeviceBrand()
								.getId());
					}
					if (deviceModel.getDeviceType() != null) {
						device.setDeviceType(deviceModel.getDeviceType()
								.getId());
					}

				}
			}

			if (StringUtils.isBlank(device.getCompanyId())) {
				dto.setRcode(BaseDto.ERROR_RCODE);
				dto.setRinfo("The data's companyId is null !");
				return JSON.toJSONString(dto);
			}
			Company company = null;
			if (StringUtils.isNotBlank(device.getCompanyId())) {
				BaseObjDto<Company> companyDto = baseDao
						.selectByPrimaryKey(CompanyMapper.class,
								StringUtils.trim(device.getCompanyId()));
				if (!FrameworkUtils.isSuccess(companyDto)) {
					dto.setRcode(BaseDto.NO_DATA_RCODE);
					dto.setRinfo("The companyId is not exist !");
					return JSON.toJSONString(dto);
				}
				company = companyDto.getData();
				
			}
             if(company != null){
            	 if(device.getAddress() == null && company.getAddress() != null){
            		 device.setAddress(company.getAddress());
            	 }
            	 if(device.getLatitude() == null && company.getLatitude() != null){
            		 device.setLatitude(company.getLatitude());
            	 }
            	 if(device.getLongitude() == null && company.getLongitude() != null){
            		 device.setLongitude(company.getLongitude());
            	 }
            	 
             }
             if(device.getStatus() == null){
            	 device.setStatus(Constant.DEVICE_STATUS_CREAT);
             }
			device.setCreateTime(new Date());
			dto = baseDao.insertSelective(DeviceMapper.class, device);

			if (FrameworkUtils.isSuccess(dto)) {
				CompanyDeviceType updateCompanyDevie = new CompanyDeviceType();
				updateCompanyDevie.setId(StringUtils.trim(companyDeviceTypeId));
				updateCompanyDevie.setPicture(device.getPicture());
				dto = baseDao.updateByPrimaryKeySelective(CompanyDeviceTypeMapper.class, updateCompanyDevie);
			} else {
				log.error("addDevice failure!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("addDevice exception!");
			throw new RuntimeException("addDevice Exception!");
		}
	} else {
		log.error("---addDevice -------- data is null ==== ");
	}
	jsonStr = JSON.toJSONString(dto);
	return jsonStr;
}
/**
 * 筛序公司可租设备信息
 * @author liukh
 * @date 2017-3-17 下午4:40:34 
 * @param form
 * @return
 * (non-Javadoc)
 * @see com.zj.access.service.BaseDMService#getCompanyAvailableDeviceListFiltrate(com.zj.entity.dm.form.CompanyAvailableDeviceQueryForm)
 */
@Override
public String getCompanyAvailableDeviceListFiltrate(
		CompanyAvailableDeviceQueryForm form) {
	String jsonStr = "";
	BaseObjDto<ItemPage<CompanyAvailableDevice>> dto = new BaseObjDto<ItemPage<CompanyAvailableDevice>>();
	try {
		if(form == null){
			dto.setRcode(BaseDto.ERROR_RCODE);
			dto.setRinfo("请求参数为空 !");
			return JSON.toJSONString(dto);
		}
		if(form.getCompanyId() == null){
			dto.setRcode(BaseDto.ERROR_RCODE);
			dto.setRinfo("请求参数的companyId为空 !");
			return JSON.toJSONString(dto);
		}
		if(form.getDeviceTypeId() == null){
			dto.setRcode(BaseDto.ERROR_RCODE);
			dto.setRinfo("请求参数的deviceTypeId为空 !");
			return JSON.toJSONString(dto);
		}
	
		if(form.getKeyAttributeValueId() == null){
			dto.setRcode(BaseDto.ERROR_RCODE);
			dto.setRinfo("请求参数的keyAttributeValueId为空 !");
			return JSON.toJSONString(dto);
		}
		if(form.getRequreAmount() == null){
			dto.setRcode(BaseDto.ERROR_RCODE);
			dto.setRinfo("请求参数的requreAmount为空 !");
			return JSON.toJSONString(dto);
		}
		
		BaseObjDto<ItemPage<CompanyAvailableDevice>> listDto = baseDao
				.getPageList(CompanyAvailableDeviceMapper.class,
						CompanyAvailableDevice.class, form,
						"getCompanyAvailableDevicePageList");
		//BaseObjDto<List<CompanyAvailableDevice>> listDto = baseDao.getList(CompanyAvailableDeviceMapper.class, CompanyAvailableDevice.class, "getCompanyAvailableDeviceList", form);
		if (FrameworkUtils.isSuccess(listDto)) {
			dto = listDto;
			log.info("getCompanyAvailableDeviceListFiltrate success");
		} else {
			FrameworkUtils.setNoData(dto);
		}
	} catch (Exception e) {

		e.printStackTrace();
		log.error("getCompanyAvailableDeviceListFiltrate exception");
		throw new RuntimeException(
				"getCompanyAvailableDeviceListFiltrate Exception!");

	}
	jsonStr = JSON.toJSONString(dto);
	return jsonStr;
}

/**
 * 获取状态为待租状态的设备
 * @author liukh
 * @date 2017-3-17 下午5:00:42 
 * @param form  {"companyId":XXX,"deviceModel":XXX,status:4}
 * @return
 * (non-Javadoc)
 * @see com.zj.access.service.BaseDMService#getWaitWorkingDeviceList(com.zj.entity.dm.form.DeviceQueryForm)
 */
@Override
public String getWaitWorkingDeviceList(DeviceQueryForm form) {
	String jsonStr = "";
	
	BaseObjDto<ItemPage<DeviceListDto>> dto = new BaseObjDto<ItemPage<DeviceListDto>>();
	try {
		if(form == null){
			dto.setRcode(BaseDto.ERROR_RCODE);
			dto.setRinfo("请求参数为空 !");
			return JSON.toJSONString(dto);
		}
		if(form.getCompanyId() == null){
			dto.setRcode(BaseDto.ERROR_RCODE);
			dto.setRinfo("请求参数的companyId为空 !");
			return JSON.toJSONString(dto);
		}
		if(form.getDeviceModel() == null){
			dto.setRcode(BaseDto.ERROR_RCODE);
			dto.setRinfo("请求参数的deviceModel为空 !");
			return JSON.toJSONString(dto);
		}
		if(form.getStatus() == null){
			dto.setRcode(BaseDto.ERROR_RCODE);
			dto.setRinfo("请求参数的status为空 !");
			return JSON.toJSONString(dto);
		}
		if(form.getStatus() != null && form.getStatus() != Constant.DEVICE_STATUS_WAIT){
			dto.setRcode(BaseDto.ERROR_RCODE);
			dto.setRinfo("请求参数的status的值不正确 !");
			return JSON.toJSONString(dto);
		}
		form.setPageIndex(1);
		form.setPageSize(1000);
		BaseObjDto<ItemPage<DeviceListDto>> pagesDto = baseDao.getPageList(DeviceMapper.class, DeviceListDto.class, form,	"getDevicePageList");
		if (FrameworkUtils.isSuccess(pagesDto)) {
			dto = pagesDto;
			log.info("getWaitWorkingDeviceList success");
		} else {
			FrameworkUtils.setNoData(dto);
			log.error("getWaitWorkingDeviceList failure");

		}
	} catch (Exception e) {
		e.printStackTrace();
		throw new RuntimeException("getWaitWorkingDeviceList Exception!");
	}
	jsonStr = JSON.toJSONString(dto);
	return jsonStr;
}
/**
 * 获取设备的详细信息列表
 * @author liukh
 * @date 2017-3-17 下午5:56:09 
 * @param form
 * @return
 * (non-Javadoc)
 * @see com.zj.access.service.BaseDMService#getDeviceContainPriceAndAuditList(com.zj.entity.dm.form.DeviceQueryForm)
 */
@Override
public String getDeviceContainPriceAndAuditList(DeviceQueryForm form) {
	String jsonStr = "";
	
	BaseObjDto<ItemPage<DeviceDetailInforListDto>> dto = new BaseObjDto<ItemPage<DeviceDetailInforListDto>>();
	try {
		
		BaseObjDto<ItemPage<DeviceDetailInforListDto>> pagesDto = baseDao.getPageList(DeviceMapper.class, DeviceDetailInforListDto.class, form,	"getDeviceDetailPageList");
		if (FrameworkUtils.isSuccess(pagesDto)) {
			dto = pagesDto;
			log.info("getDeviceContainPriceAndAuditList success");
		} else {
			FrameworkUtils.setNoData(dto);
			log.error("getDeviceContainPriceAndAuditList failure");

		}
	} catch (Exception e) {
		e.printStackTrace();
		throw new RuntimeException("getDeviceContainPriceAndAuditList Exception!");
	}
	jsonStr = JSON.toJSONString(dto);
	return jsonStr;
}

@Override
public String getModelQuoteByCompanyIdAndModelIdInCompanyDeviceType(
		String companyId, String deviceModelId) {
	String jsonStr = "";
	BaseObjDto<CompanyDeviceType> dto = new BaseObjDto<CompanyDeviceType>();
	try {
		if(StringUtils.isBlank(companyId)){
			dto.setRcode(BaseDto.ERROR_RCODE);
			dto.setRinfo("The companyId is null !");
			return JSON.toJSONString(dto);
		}
		if(StringUtils.isBlank(deviceModelId)){
			dto.setRcode(BaseDto.ERROR_RCODE);
			dto.setRinfo("The deviceModelId is null !");
			return JSON.toJSONString(dto);
		}
		CompanyDeviceType companyDeviceType = baseOtherService.getCompanyDeviceTypeByComapnyIdAndModelId(companyId, deviceModelId);
		if(companyDeviceType!= null){
			CompanyDeviceType queryCompanyDeviceType = new CompanyDeviceType();
			queryCompanyDeviceType.setModelQuote(companyDeviceType.getModelQuote());
			dto.setData(queryCompanyDeviceType);
			dto.setRcode(BaseDto.SUCCESS_RCODE);
			dto.setRinfo(BaseDto.DEFAULT_SUCCESS_INFO);
			log.error("getModelQuoteByCompanyIdAndModelIdInCompanyDeviceType success");
		}else{
			FrameworkUtils.setNoData(dto);
			log.error("getModelQuoteByCompanyIdAndModelIdInCompanyDeviceType failure");
		}
	} catch (Exception e) {
		e.printStackTrace();
		throw new RuntimeException("getModelQuoteByCompanyIdAndModelIdInCompanyDeviceType Exception!");
	}
	jsonStr = JSON.toJSONString(dto);
	return jsonStr;
}

@Override
public String getDeviceStatisticsCount(String companyId) {
	String jsonStr = "";
	
	BaseObjDto<List<DeviceStatisticsDto>> dto = new BaseObjDto<List<DeviceStatisticsDto>>();
	try {
		if(StringUtils.isNotBlank(companyId)){
			DeviceQueryForm form = new DeviceQueryForm();
			form.setCompanyId(companyId);

			BaseObjDto<List<DeviceStatisticsDto>> listDto = baseDao.getList(DeviceMapper.class, DeviceStatisticsDto.class, "getDeviceStatisticsList", form);
			if (FrameworkUtils.isSuccess(listDto) && listDto.getData() != null && listDto.getData().size()>0) {
				List<DeviceStatisticsDto> deviceStatusList = new ArrayList<DeviceStatisticsDto>();
				Integer deviceTotalCount = 0;
				Integer deviceRealTotalCount = 0;
				Integer deviceNotRealTotalCount = 0;
				//自有设备待审核
				Integer createStatusRealCount = 0;
				//自有设备审核通过
				Integer checkPassStatusRealCount = 0;
				//自有设备审核不通过
				Integer checkNoPassStatusRealCount = 0;
				//自有设备已租
				Integer workStatusRealCount = 0;
				// 自有设备 待租
				Integer waitStatusRealCount = 0;
				//自有设备线下出租
				Integer belowStatusRealCount = 0;
				//外借设备已租
				Integer workStatusNotRealCount = 0;
				//外借设备待租
				Integer waitStatusNotRealCount = 0;
				//外借设备线下出租
				Integer belowStatusNotRealCount = 0;
				
			for(DeviceStatisticsDto deviceStatisticsDto:listDto.getData()){
				deviceTotalCount += deviceStatisticsDto.getAmount();
				 if(deviceStatisticsDto.getIsRealDevice()== Constant.DEVICE_TRUTH_TRUE){
					 deviceRealTotalCount += deviceStatisticsDto.getAmount();
					 if(deviceStatisticsDto.getStatus() == Constant.DEVICE_STATUS_CREAT){
						 createStatusRealCount += deviceStatisticsDto.getAmount();
					 }else if(deviceStatisticsDto.getStatus() == Constant.DEVICE_STATUS_CHECKPASS){
						 checkPassStatusRealCount += deviceStatisticsDto.getAmount();
					 }else if(deviceStatisticsDto.getStatus() == Constant.DEVICE_STATUS_CHECKNOPASS){
						 checkNoPassStatusRealCount += deviceStatisticsDto.getAmount();
					 }else if(deviceStatisticsDto.getStatus() == Constant.DEVICE_STATUS_WORING){
						 workStatusRealCount += deviceStatisticsDto.getAmount();
					 }else if(deviceStatisticsDto.getStatus() == Constant.DEVICE_STATUS_WAIT){
						 waitStatusRealCount += deviceStatisticsDto.getAmount();
					 }else if(deviceStatisticsDto.getStatus() == Constant.DEVICE_STATUS_BELOW_WORING){
						 belowStatusRealCount += deviceStatisticsDto.getAmount();
					 }
					
				 }else{
					 deviceNotRealTotalCount += deviceStatisticsDto.getAmount();
					 if(deviceStatisticsDto.getStatus() == Constant.DEVICE_STATUS_WORING){
						 workStatusNotRealCount += deviceStatisticsDto.getAmount();
					 }else if(deviceStatisticsDto.getStatus() == Constant.DEVICE_STATUS_WAIT){
						 waitStatusNotRealCount += deviceStatisticsDto.getAmount();
					 }else if(deviceStatisticsDto.getStatus() == Constant.DEVICE_STATUS_BELOW_WORING){
						 belowStatusNotRealCount += deviceStatisticsDto.getAmount();
					 }
				 }
			}
			// A：总数量
			DeviceStatisticsDto totalDevice = new DeviceStatisticsDto();
			totalDevice.setAmount(deviceTotalCount);
			totalDevice.setType(Constant.DEVICE_STATUS_TOTLA);
			deviceStatusList.add(totalDevice);
			// B:真实设备数量
			DeviceStatisticsDto realDevice = new DeviceStatisticsDto();
			realDevice.setAmount(deviceRealTotalCount);
			realDevice.setType(Constant.DEVICE_STATUS_TOTLAISREAL);
			deviceStatusList.add(realDevice);
			// C:虚拟设备数量
			DeviceStatisticsDto realNotDevice = new DeviceStatisticsDto();
			realNotDevice.setAmount(deviceNotRealTotalCount);
			realNotDevice.setType(Constant.DEVICE_STATUS_TOTLANOTISREAL);
			deviceStatusList.add(realNotDevice);
			// 自有设备待审核
			DeviceStatisticsDto createStatusDevice = new DeviceStatisticsDto();
			createStatusDevice.setAmount(createStatusRealCount);
			createStatusDevice.setType(Constant.DEVICE_STATUS_TOTLAISREAL_CREAT);
			deviceStatusList.add(createStatusDevice);
			//自有设备审核通过
			DeviceStatisticsDto checkPassStatusDevice = new DeviceStatisticsDto();
			checkPassStatusDevice.setAmount(checkPassStatusRealCount);
			checkPassStatusDevice.setType(Constant.DEVICE_STATUS_TOTLAISREAL_CHECKPASS);
			deviceStatusList.add(checkPassStatusDevice);
			//自有设备审核不通过
			DeviceStatisticsDto checkNoPassStatusDevice = new DeviceStatisticsDto();
			checkNoPassStatusDevice.setAmount(checkNoPassStatusRealCount);
			checkNoPassStatusDevice.setType(Constant.DEVICE_STATUS_TOTLAISREAL_CHECKNOPASS);
			deviceStatusList.add(checkNoPassStatusDevice);
			// 自有设备已租
			DeviceStatisticsDto workStatusDevice = new DeviceStatisticsDto();
			workStatusDevice.setAmount(workStatusRealCount);
			workStatusDevice.setType(Constant.DEVICE_STATUS_TOTLAISREAL_WORING);
			deviceStatusList.add(workStatusDevice);
            //自有设备待租
			DeviceStatisticsDto waitStatusDevice = new DeviceStatisticsDto();
			waitStatusDevice.setAmount(waitStatusRealCount);
			waitStatusDevice.setType(Constant.DEVICE_STATUS_TOTLAISREAL_WAIT);
			deviceStatusList.add(waitStatusDevice);
			//自有设备线下出租
			DeviceStatisticsDto belowStatusDevice = new DeviceStatisticsDto();
			belowStatusDevice.setAmount(belowStatusRealCount);
			belowStatusDevice.setType(Constant.DEVICE_STATUS_TOTLAISREAL_BELOW_WORING);
			deviceStatusList.add(belowStatusDevice);
			//外借设备已租
			DeviceStatisticsDto workStatusNotRealDevice = new DeviceStatisticsDto();
			workStatusNotRealDevice.setAmount(workStatusNotRealCount);
			workStatusNotRealDevice.setType(Constant.DEVICE_STATUS_TOTLANOTISREAL_WORING);
			deviceStatusList.add(workStatusNotRealDevice);
            //外借设备待租
			DeviceStatisticsDto waitStatusNotRealDevice = new DeviceStatisticsDto();
			waitStatusNotRealDevice.setAmount(waitStatusNotRealCount);
			waitStatusNotRealDevice.setType(Constant.DEVICE_STATUS_TOTLANOTISREAL_WAIT);
			deviceStatusList.add(waitStatusNotRealDevice);
			//外借设备线下出租
			DeviceStatisticsDto belowStatusNotRealDevice = new DeviceStatisticsDto();
			belowStatusNotRealDevice.setAmount(belowStatusNotRealCount);
			belowStatusNotRealDevice.setType(Constant.DEVICE_STATUS_TOTLANOTISREAL_BELOW_WORING);
			deviceStatusList.add(belowStatusNotRealDevice);
			dto.setData(deviceStatusList);
			dto.setRcode(BaseDto.SUCCESS_RCODE);
			dto.setRinfo(BaseDto.DEFAULT_SUCCESS_INFO);
			
				log.info("getDeviceStatisticsCount success");
			} else {
				FrameworkUtils.setNoData(dto);
				log.error("getDeviceStatisticsCount failure");

			}
		}else{
			dto.setRcode(BaseDto.ERROR_RCODE);
			dto.setRinfo("The companyId is null ");
		}

	} catch (Exception e) {
		e.printStackTrace();
		throw new RuntimeException("getDeviceStatisticsCount Exception!");
	}
	jsonStr = JSON.toJSONString(dto);
	return jsonStr;
}

/**
 * 审核设备
 * @author liukh
 * @date 2017-3-20 上午11:59:40 
 * @param data
 * @return
 * (non-Javadoc)
 * @see com.zj.access.service.BaseDMService#checkDevice(java.lang.String)
 */
@SuppressWarnings("unchecked")
@Override
public String updateCheckDevice(String data) {
	String jsonStr = "";
	BaseDto dto = new BaseDto();

		try {	
			if (StringUtils.isBlank(data)) {
				dto.setRinfo("The data is null ");
				dto.setRcode(BaseDto.ERROR_RCODE);
				return JSON.toJSONString(dto);
			}
			Map<String, Object> params = (Map<String, Object>) JSON.parse(data);
			if (params == null) {
				dto.setRinfo("The params is null ");
				dto.setRcode(BaseDto.ERROR_RCODE);
				return JSON.toJSONString(dto);
			} else if (params.get("status") == null) {
				dto.setRinfo("The params's status is null ");
				dto.setRcode(BaseDto.ERROR_RCODE);
				return JSON.toJSONString(dto);
			}else if (params.get("deviceId") == null) {
				dto.setRinfo("The params's deviceId is null ");
				dto.setRcode(BaseDto.ERROR_RCODE);
				return JSON.toJSONString(dto);
			}else if (params.get("logonUserId") == null) {
				dto.setRinfo("The params's logonUserId is null ");
				dto.setRcode(BaseDto.ERROR_RCODE);
				return JSON.toJSONString(dto);
			}else if (params.get("content") == null) {
				dto.setRinfo("The params's content is null ");
				dto.setRcode(BaseDto.ERROR_RCODE);
				return JSON.toJSONString(dto);
			}
			
		BaseObjDto<DeviceDetailInforListDto> deviceDto = baseDao.selectByPrimaryKey(DeviceMapper.class, StringUtils.trim(params.get("deviceId").toString()));
		if (!FrameworkUtils.isSuccess(deviceDto)) {
			dto.setRinfo("The data's deviceId not exits !");
			dto.setRcode(BaseDto.ERROR_RCODE);
			return JSON.toJSONString(dto);
		}

		Device updateDevice = new Device();
		updateDevice.setId(StringUtils.trim((params.get("deviceId").toString())));
		updateDevice.setStatus(Integer.valueOf(params.get("status").toString()));
		dto = baseDao.updateByPrimaryKeySelective(DeviceMapper.class, updateDevice);
		Audit updateAudit = new Audit();
		if(FrameworkUtils.isSuccess(dto)){
			DeviceDetailInforListDto device = deviceDto.getData();
			if(StringUtils.isNotBlank(device.getCompanyId())){
				UserDto user = baseOtherService.getUserDtoByComapnyId(device.getCompanyId());
				if(user != null){
					String messageTemplateInfo = "";
					if(user!= null && user.getId()!= null){
						//审核不通过
						if(String.valueOf(Constant.DEVICE_STATUS_CHECKNOPASS).equals(params.get("status").toString())){
							messageTemplateInfo = MessageTemplateUtils.getDeviceUnPassedMessageTemplateInfo();
							updateAudit.setIsPassed(Boolean.FALSE);
						}else{
							updateAudit.setIsPassed(Boolean.TRUE);
							messageTemplateInfo = MessageTemplateUtils.getDevicePassedMessageTemplateInfo();
						}
						if (StringUtils.isNotBlank(messageTemplateInfo)) {
							messageTemplateInfo = messageTemplateInfo.replace("{1}",device.getDeviceName()!= null ? device.getDeviceName():"");
						}
						Message message = new Message();
						message.setType(Constant.MESSAGE_TYPE_SYSTEM);
						message.setUserId(user.getId());
						message.setExternalRelatedId(user.getId());
						message.setOperatorId(params.get("logonUserId").toString());
						message.setContent(messageTemplateInfo);
						message.setStatus(Constant.MESSAGE_STATUS_UNREAD);
						dto = baseDao.insertSelective(MessageMapper.class, message);
						if(FrameworkUtils.isSuccess(dto)){
							updateAudit.setRelatedId(StringUtils.trim((params.get("deviceId").toString())));
							updateAudit.setInformationType(Constant.AUDIT_TYPE_DEVICE);
							updateAudit.setCreateTime(new Date());
							updateAudit.setAuditor(params.get("logonUserId").toString());
							updateAudit.setContent(params.get("content").toString());
							return baseBMService.addOrUpdateAudit(JSON.toJSONString(updateAudit));
						}
					}
				}
				
			}
			
		}

		} catch (Exception e) {
			e.printStackTrace();
			log.error("addDevice exception!");
			throw new RuntimeException("addDevice Exception!");
		}
	 
	jsonStr = JSON.toJSONString(dto);
	return jsonStr;
}





}
