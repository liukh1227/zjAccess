package com.zj.access.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.zj.access.base.dao.IBaseDao;
import com.zj.access.base.mapper.AccountDetailMapper;
import com.zj.access.base.mapper.AuditMapper;
import com.zj.access.base.mapper.CompanyMapper;
import com.zj.access.base.mapper.DeviceBrandMapper;
import com.zj.access.base.mapper.DeviceModelMapper;
import com.zj.access.base.mapper.DeviceTypeMapper;
import com.zj.access.base.mapper.DeviceTypeSpecDataMapper;
import com.zj.access.base.mapper.DeviceTypeSpecDefinitionMapper;
import com.zj.access.base.mapper.UserMapper;
import com.zj.access.common.DataCache;
import com.zj.access.common.FrameworkUtils;
import com.zj.base.common.Constant;
import com.zj.base.common.ItemPage;
import com.zj.base.common.utils.CommonUtils;
import com.zj.base.common.utils.PasswordUtils;
import com.zj.base.common.utils.sms.SmsUtil;
import com.zj.base.entity.base.dto.BaseDto;
import com.zj.base.entity.base.dto.BaseObjDto;
import com.zj.common.utils.PingYinUtil;
import com.zj.entity.base.po.AccountDetail;
import com.zj.entity.base.po.Audit;
import com.zj.entity.base.po.Company;
import com.zj.entity.base.po.DeviceBrand;
import com.zj.entity.base.po.DeviceModel;
import com.zj.entity.base.po.DeviceType;
import com.zj.entity.base.po.DeviceTypeSpecData;
import com.zj.entity.base.po.DeviceTypeSpecDefinition;
import com.zj.entity.base.po.SmsDistributionLog;
import com.zj.entity.base.po.User;
import com.zj.entity.bm.bo.UserBo;
import com.zj.entity.bm.dto.BrandIndexListDto;
import com.zj.entity.bm.dto.BrandIndexListDto.BrandsDto;
import com.zj.entity.bm.dto.DeviceTypeSpecDataDto;
import com.zj.entity.bm.dto.DeviceTypeSpecDefinitionDto;
import com.zj.entity.bm.dto.UserDto;
import com.zj.entity.bm.form.AccountDetailQueryForm;
import com.zj.entity.bm.form.AuditQueryForm;
import com.zj.entity.bm.form.CompanyQueryForm;
import com.zj.entity.bm.form.DeviceBrandQueryForm;
import com.zj.entity.bm.form.DeviceModelQueryForm;
import com.zj.entity.bm.form.DeviceTypeQueryForm;
import com.zj.entity.bm.form.DeviceTypeSpecDataQueryForm;
import com.zj.entity.bm.form.DeviceTypeSpecDefinitionQueryForm;
import com.zj.entity.bm.form.UserQueryForm;

@Service("baseBMService")
@Scope("prototype")
public class BaseBMServiceImpl implements BaseBMService {
	private static final Logger log = Logger.getLogger(BaseBMServiceImpl.class);
	@Autowired
	private IBaseDao baseDao;
	@Autowired
	private BaseOtherService baseOtherService;
	@Autowired
	private BaseSMService baseSMService;

	/**
	 * 新增公司信息
	 * 
	 * @author liukh
	 * @date 2017-2-7 下午2:53:40
	 * @param data
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseBMService#addCompany(java.lang.String)
	 */
	@Override
	public String addCompany(String data) {
		String jsonStr = "";
		BaseDto dto = new BaseDto();
		if (StringUtils.isNotBlank(data)) {
			try {
				Company company = JSON.parseObject(data, Company.class);
				if (company == null) {
					dto.setRcode(1);
					dto.setRinfo("The data parseObject to Company is null !");
					return JSON.toJSONString(dto);
				}
				Date createDate = new Date();
				company.setCreateTime(createDate);
				if (company.getCompanyType() == null) {
					dto.setRcode(1);
					dto.setRinfo("The data's companyType is null !");
					return JSON.toJSONString(dto);
				}
				if (company.getCompanyName() == null) {
					dto.setRcode(1);
					dto.setRinfo("The data's companyName is null !");
					return JSON.toJSONString(dto);
				}
				if (company.getCompanyBusinessType() == null) {
					dto.setRcode(1);
					dto.setRinfo("The data's companyBusinessType is null !");
					return JSON.toJSONString(dto);
				}
				company.setStatus(Constant.COMAPNY_STATUS_CREATE);
				company.setCreateTime(createDate);
				company.setTotalAmount(BigDecimal.ZERO);
				company.setDisposableAmount(BigDecimal.ZERO);
				company.setLockedAmount(BigDecimal.ZERO);
				dto = baseDao.insert(CompanyMapper.class, company);
				if (FrameworkUtils.isSuccess(dto)) {
					log.info("addCompany success!");
				} else {
					log.error("addCompany failure!");
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("addCompany exception!");
				throw new RuntimeException("addCompany Exception!");
			}
		} else {
			log.error("---addCompany -------- data is null ==== ");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 增加公司信息并更新用户信息
	 * 
	 * @author liukh
	 * @date 2017-3-2 下午5:08:51
	 * @param userId
	 * @param data
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseBMService#addCompanyAndUpdateUserAccountCompanyId(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public String addCompanyAndUpdateUserAccountCompanyId(String userId,
			String data) {
		String jsonStr = "";
		BaseDto dto = new BaseDto();
		if (StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(data)) {
			try {
				Company company = JSON.parseObject(data, Company.class);
				if (company == null) {
					dto.setRcode(1);
					dto.setRinfo("The data parseObject to Company is null !");
					return JSON.toJSONString(dto);
				}
				Date createDate = new Date();
				company.setCreateTime(createDate);
				if (company.getCompanyType() == null) {
					dto.setRcode(1);
					dto.setRinfo("The data's companyType is null !");
					return JSON.toJSONString(dto);
				}
				if (company.getCompanyName() == null) {
					dto.setRcode(1);
					dto.setRinfo("The data's companyName is null !");
					return JSON.toJSONString(dto);
				}
				if (company.getCompanyBusinessType() == null) {
					dto.setRcode(1);
					dto.setRinfo("The data's companyBusinessType is null !");
					return JSON.toJSONString(dto);
				}
				company.setStatus(Constant.COMAPNY_STATUS_CREATE);
				company.setCreateTime(createDate);
				company.setTotalAmount(BigDecimal.ZERO);
				company.setDisposableAmount(BigDecimal.ZERO);
				company.setLockedAmount(BigDecimal.ZERO);
				dto = baseDao.insert(CompanyMapper.class, company);
				// dto = baseDao.insertSelective(CompanyMapper.class, company);
				if (FrameworkUtils.isSuccess(dto)) {
					User user = new User();
					user.setId(userId);
					user.setCompany(company);
					dto = baseDao.updateByPrimaryKeySelective(UserMapper.class,
							user);
					if (FrameworkUtils.isSuccess(dto)) {
						log.info("addCompany success!");
					} else {
						log.error("addCompany failure!");
					}

				} else {
					log.error("addCompany failure!");
				}

			} catch (Exception e) {
				e.printStackTrace();
				log.error("addCompany exception!");
				throw new RuntimeException("addCompany Exception!");
			}
		} else {
			log.error("---addCompany -------- data or userId is null ==== ");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 获取公司信息
	 * 
	 * @author liukh
	 * @date 2017-2-7 下午2:51:10
	 * @param companyId
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseBMService#getCompany(java.lang.String)
	 */
	@Override
	public String getCompany(String companyId) {
		String jsonStr = "";
		BaseObjDto<Company> dto = new BaseObjDto<Company>();
		try {
			if (StringUtils.isBlank(companyId)) {
				dto.setRinfo("companyId is null");
				return JSON.toJSONString(dto);
			}
			BaseObjDto<Company> companyDto = baseDao.selectByPrimaryKey(
					CompanyMapper.class, StringUtils.trim(companyId));
			if (FrameworkUtils.isSuccess(companyDto)) {
				Company company = companyDto.getData();
				dto.setData(company);
				FrameworkUtils.setSuccess(dto);
				log.info("getCompany success!");
			} else {
				FrameworkUtils.setNoData(dto);
				log.error("getCompany failure");
			}
		} catch (Exception e) {
			log.error("getCompany exception!");
			e.printStackTrace();
			throw new RuntimeException("getCompany Exception!");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 更新公司信息
	 * 
	 * @author liukh
	 * @date 2017-2-7 下午3:26:19
	 * @param companyId
	 * @param data
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseBMService#updateCompany(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public String updateCompany(String companyId, String data) {
		String jsonStr = "";
		BaseDto dto = new BaseDto();
		if (StringUtils.isNotBlank(companyId) && StringUtils.isNotBlank(data)) {
			try {
				Company company = JSON.parseObject(data, Company.class);
				if (company == null) {
					dto.setRcode(1);
					dto.setRinfo("The data parseObject to Company is null !");
					return JSON.toJSONString(dto);
				}
				company.setId(companyId);
				dto = baseDao.updateByPrimaryKeySelective(CompanyMapper.class,
						company);
				if (FrameworkUtils.isSuccess(dto)) {
					log.info("updateCompany success!");
				} else {
					log.error("updateCompany failure!");
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("updateCompany exception!");
				throw new RuntimeException("updateCompany Exception!");
			}

		} else {
			log.error("---updateCompany -------- data or companyId is null ==== ");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 
	 * @author liukh
	 * @date 2017-2-7 下午4:25:20
	 * @param companyBusinessType
	 * @param companyName
	 * @param companyType
	 * @param numberOfItem
	 * @param page
	 * @param status
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseBMService#getCompanyList(java.lang.String,
	 *      java.lang.String, int, int, int, int)
	 */
	@Override
	public String getCompanyList(CompanyQueryForm form) {
		String jsonStr = "";
		BaseObjDto<ItemPage<Company>> dto = new BaseObjDto<ItemPage<Company>>();
		try {

			BaseObjDto<ItemPage<Company>> pagesDto = baseDao.getPageList(
					CompanyMapper.class, Company.class, form,
					"getCompanyPageList");
			if (FrameworkUtils.isSuccess(pagesDto)) {
				dto = pagesDto;
				log.info("getCompanyList success");
			} else {
				FrameworkUtils.setNoData(dto);
				log.error("getCompanyList failure");

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("getCompanyList Exception ");
			e.printStackTrace();
			throw new RuntimeException("getCompanyList Exception!");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 获取手机验证码
	 * 
	 * @author liukh
	 * @date 2017-3-2 上午10:54:30
	 * @param data
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseBMService#addGetValidCode(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String addGetValidCode(String data) {
		String jsonStr = "";
		BaseDto dto = new BaseDto();
		if (StringUtils.isNotBlank(data)) {
			Map<String, Object> params = (Map<String, Object>) JSON.parse(data);
			if (params == null) {
				dto.setRinfo("The data parse Map is  null");
				return JSON.toJSONString(dto);
			} else if (params != null && params.get("cellPhone") == null) {
				dto.setRinfo("The params cellPhone is null");
				return JSON.toJSONString(dto);
			}
			String cellPhone = params.get("cellPhone").toString();
			String smsCode = null;
			try {
				 smsCode = CommonUtils.getRandomNumberString(4);
				//smsCode = "0000";
				String nowStr = CommonUtils.getDateString(new Date(),
						"yyyy-MM-dd HH:mm:ss");
				DataCache cache = DataCache.getInstance();
				cache.setSmsCode(cellPhone, smsCode + "|" + nowStr);
				 jsonStr = SmsUtil.sendSms(cellPhone, smsCode);
				log.info("getValidCode ---- smsCode ========= " + smsCode);
				log.info("getValidCode ---- jsonStr ========= " + jsonStr);
				SmsDistributionLog addSmsDistributionLog = new SmsDistributionLog();
				addSmsDistributionLog.setCellphone(cellPhone);
				addSmsDistributionLog.setContent(smsCode);
				addSmsDistributionLog.setStatus(Constant.SENDCODE_SENDED);
				addSmsDistributionLog.setTotalDistributionCount(1);
				jsonStr = baseSMService.addSmsDistributionLog(JSON.toJSONString(addSmsDistributionLog));
				//jsonStr = "{\"rcode\":0,\"rinfo\":\"短信发送成功\"}";

				return jsonStr;
			} catch (Exception e) {
				log.error("---getValidCode ---- data parse error ====== ");
				e.printStackTrace();
				throw new RuntimeException("getValidCode Exception!");
			}
		} else {
			log.error("---getValidCode ---- data is null ====== ");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 校验手机验证码
	 * 
	 * @author liukh
	 * @date 2017-3-2 上午11:38:33
	 * @param cellPhone
	 * @param validCode
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseBMService#checkValidCode(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public String checkValidCode(String cellPhone, String validCode) {
		String jsonStr = "";
		BaseDto dto = new BaseDto();
		try {
			if (StringUtils.isBlank(cellPhone)) {
				dto.setRcode(1);
				dto.setRinfo("cellPhone is null !");
				return JSON.toJSONString(dto);
			}
			if (StringUtils.isBlank(validCode)) {
				dto.setRcode(1);
				dto.setRinfo("validCode is null !");
				return JSON.toJSONString(dto);
			}
			DataCache cache = DataCache.getInstance();
			String smsStr = cache.getSmsCode(cellPhone);
			if (StringUtils.isNotBlank(smsStr)) {
				String[] sms = smsStr.split("\\|");
				if (StringUtils.isNotBlank(sms[0])) {
					String smsCode = sms[0];
					String createTime = sms[1];
					if (StringUtils.isBlank(smsCode)) {
						dto.setRcode(1);
						dto.setRinfo("data is error");
						return JSON.toJSONString(dto);
					} else if (StringUtils.isEmpty(createTime)) {
						dto.setRcode(1);
						dto.setRinfo("data is error");
						return JSON.toJSONString(dto);
					}
					if (!StringUtils.equals(validCode, smsCode)) {
						dto.setRcode(1);
						dto.setRinfo("validCode is error");
						return JSON.toJSONString(dto);
					}
					Calendar now = Calendar.getInstance();
					Calendar time = Calendar.getInstance();
					time.setTime(CommonUtils.parseDate(createTime,
							"yyyy-MM-dd HH:mm:ss"));
					long nowTime = now.getTimeInMillis();
					long smsTime = time.getTimeInMillis();
					long minute = (nowTime - smsTime) / (1000 * 60);// 转化minute
					if (minute >= 3) {// 如果超过3分钟,就移除
						dto.setRcode(1);
						dto.setRinfo("validCode is timeout");
						return JSON.toJSONString(dto);
					}
					if (!StringUtils.equals(validCode, smsCode)) {
						dto.setRcode(1);
						dto.setRinfo("validCode is not equal smsCode");
						return JSON.toJSONString(dto);
					}
					dto.setRcode(0);
					dto.setRinfo("success");
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("checkValidCode exception!");
			throw new RuntimeException("checkValidCode Exception!");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 新增用户信息
	 * 
	 * @author liukh
	 * @date 2017-2-13 下午2:57:51
	 * @param data
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseBMService#addUser(java.lang.String)
	 */
	@Override
	public String addUser(String data) {
		String jsonStr = "";
		BaseObjDto<User> dto = new BaseObjDto<User>();
		if (StringUtils.isNotBlank(data)) {
			try {

				User user = JSON.parseObject(data, User.class);
				if (user == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data parseObject to User is null !");
					return JSON.toJSONString(dto);
				}
				if (user.getLogonId() == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's logonId is null !");
					return JSON.toJSONString(dto);
				}
				if (user.getPassword() == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's password is null !");
					return JSON.toJSONString(dto);
				}
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("logonId", StringUtils.trim(user.getLogonId()));
				BaseObjDto<User> userVailDto = baseDao.getObjProperty(UserMapper.class,
						"isValidUser", params);
				if (FrameworkUtils.isSuccess(userVailDto)) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("该手机后已经注册，请勿重复注册!");
					return JSON.toJSONString(dto);
				}
				
				JSONObject jp = JSON.parseObject(data);
				String validCode = jp.getString("validCode");
				if (StringUtils.isBlank(validCode)) {
					dto.setRcode(1);
					dto.setRinfo("The data's validCode is null !");
					return JSON.toJSONString(dto);
				}

				if (user.getCellPhone() == null) {
					user.setCellPhone(user.getLogonId());
				}
				// 校验手机验证码是否正确和过期
				jsonStr = this.checkValidCode(user.getCellPhone(), validCode);
				BaseDto basDto = JSON.parseObject(jsonStr, BaseDto.class);
				if (!FrameworkUtils.isSuccess(basDto)) {
					return jsonStr;
				}

				String companyId = jp.getString("companyId");
				if (StringUtils.isNotBlank(companyId)) {
					Company company = new Company();
					company.setId(companyId);
					user.setCompany(company);
				}
				String passwordSalt = CommonUtils.getRandomString(40)
						.toLowerCase();
				user.setPasswordSalt(passwordSalt);
				log.info("----passwordSalt-----" + passwordSalt);
				String userPass = PasswordUtils.encyptPassword(
						user.getLogonId(), user.getPassword(), passwordSalt);
				user.setPassword(userPass);
				Date createDate = new Date();
				user.setCreateTime(createDate);
				user.setStatus(Constant.USER_STATUS_TAKEEFFICACY);
				BaseDto addDto = baseDao
						.insertSelective(UserMapper.class, user);

				if (FrameworkUtils.isSuccess(addDto)) {
					User users = new User();
					users.setId(user.getId());
					users.setCreateTime(user.getCreateTime());
					dto.setData(users);
					dto.setRcode(BaseDto.SUCCESS_RCODE);
					dto.setRinfo(BaseDto.DEFAULT_SUCCESS_INFO);
					log.info("addUser success!");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				log.error("addUser exception!");
				throw new RuntimeException("addUser Exception!");
			}
		} else {
			log.error("---addUser -------- data is null ==== ");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 获取用户信息
	 * 
	 * @author liukh
	 * @date 2017-2-13 下午2:59:14
	 * @param userId
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseBMService#getUser(java.lang.String)
	 */
	@Override
	public String getUser(String userId) {
		String jsonStr = "";
		BaseObjDto<User> dto = new BaseObjDto<User>();
		try {
			if (StringUtils.isBlank(userId)) {
				dto.setRcode(1);
				dto.setRinfo("userId is null");
				return JSON.toJSONString(dto);
			}
			BaseObjDto<User> userDto = baseDao.selectByPrimaryKey(
					UserMapper.class, StringUtils.trim(userId));
			if (FrameworkUtils.isSuccess(userDto)) {
				User user = userDto.getData();
				dto.setData(user);
				FrameworkUtils.setSuccess(dto);
				log.info("getUser success!");
			} else {
				FrameworkUtils.setNoData(dto);
				log.error("getUser failure");
			}
		} catch (Exception e) {
			log.error("getUser exception!");
			e.printStackTrace();
			throw new RuntimeException("getUser Exception!");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 获取用户的详细信息
	 * 
	 * @author liukh
	 * @date 2017-3-2 上午10:38:12
	 * @param userId
	 * @param logonId
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseBMService#getUserMoreInfo(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public String getUserMoreInfo(String id, String logonId) {
		String jsonStr = "";
		BaseObjDto<UserDto> dto = new BaseObjDto<UserDto>();
		try {
			if (StringUtils.isBlank(id) && StringUtils.isBlank(logonId)) {
				dto.setRinfo("userId and logonId 不能都为空");
				return JSON.toJSONString(dto);
			}
			Map<String, Object> userParams = new HashMap<String, Object>();
			userParams.put("logonId", StringUtils.trim(logonId));
			userParams.put("id", StringUtils.trim(id));
			BaseObjDto<User> userInfoDto = baseDao.getObjProperty(
					UserMapper.class, "getUserByIdOrAccountId", userParams);
			if (FrameworkUtils.isSuccess(userInfoDto)) {
				User user = userInfoDto.getData();
				UserDto userDto = new UserDto();
				userDto.setId(user.getId());
				userDto.setUserName(user.getUserName());
				userDto.setLogonId(user.getLogonId());
				userDto.setCellPhone(user.getCellPhone());
				userDto.setStatus(user.getStatus());
				userDto.setHeadPortrait(user.getHeadPortrait());

				if (user.getCompany() != null) {
					userDto.setComapnyStatus(user.getCompany().getStatus());
					userDto.setCompanyId(user.getCompany().getId());
					userDto.setCompanyType(user.getCompany().getCompanyType());
					userDto.setCompanyName(user.getCompany().getCompanyName());
					userDto.setLicense(user.getCompany().getLicense());
					userDto.setRepresentative(user.getCompany()
							.getRepresentative());
					userDto.setRepresentativeTelephone(user.getCompany()
							.getRepresentativeTelephone());
					userDto.setAddress(user.getCompany().getAddress());
					userDto.setLatitude(user.getCompany().getLatitude());
					userDto.setLongitude(user.getCompany().getLongitude());
					userDto.setCompanyBusinessType(user.getCompany()
							.getCompanyBusinessType());
					userDto.setInvoiceTitle(user.getCompany().getInvoiceTitle());
					userDto.setTotalAmount(user.getCompany().getTotalAmount());
					userDto.setDisposableAmount(user.getCompany()
							.getDisposableAmount());
					userDto.setLockedAmount(user.getCompany().getLockedAmount());

				}
				dto.setData(userDto);
				FrameworkUtils.setSuccess(dto);

			} else {
				FrameworkUtils.setNoData(dto);
				log.error("getUserMoreInfo failure");
			}

		} catch (Exception e) {
			log.error("getUserMoreInfo exception!");
			e.printStackTrace();
			throw new RuntimeException("getUserMoreInfo Exception!");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * web登录并获取用户的详细信息
	 * 
	 * @author liukh
	 * @date 2017-3-2 下午5:31:26
	 * @param data
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseBMService#getUserMoreInfoWhenLogon4Web(java.lang.String)
	 */
	@Override
	public UserBo getUserMoreInfoWhenLogon4Web(String data) {
		String jsonStr = "";
		UserBo userBo = new UserBo();
		BaseObjDto<JSONObject> dto = new BaseObjDto<JSONObject>();
		if (StringUtils.isNotBlank(data)) {
			try {
				JSONObject jp = JSON.parseObject(data);
				if (jp == null) {
					dto.setRcode(1);
					dto.setRinfo("The data parseObject to JSONObject is null !");
					userBo.setReturnJson(JSON.toJSONString(dto));
					return userBo;
				}
				String logonId = jp.getString("logonId");
				if (StringUtils.isBlank(logonId)) {
					dto.setRcode(1);
					dto.setRinfo("The data's logonId is null !");
					userBo.setReturnJson(JSON.toJSONString(dto));
					return userBo;
				}
				String password = jp.getString("password");
				if (StringUtils.isBlank(password)) {
					dto.setRcode(1);
					dto.setRinfo("The data's password is null !");
					userBo.setReturnJson(JSON.toJSONString(dto));
					return userBo;
				}
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("logonId", StringUtils.trim(logonId));
				BaseObjDto<User> userLogonDto = baseDao.getObjProperty(
						UserMapper.class, "isValidUser", params);
				if (FrameworkUtils.isSuccess(userLogonDto)) {
					User user = userLogonDto.getData();
					String passwordSalt = user.getPasswordSalt();
					password = PasswordUtils.encyptPassword(
							StringUtils.trim(logonId), password, passwordSalt);
					Map<String, Object> userParams = new HashMap<String, Object>();
					userParams.put("logonId", StringUtils.trim(logonId));
					userParams.put("password", StringUtils.trim(password));
					BaseObjDto<User> userInfoDto = baseDao.getObjProperty(
							UserMapper.class, "getUserByAccount", userParams);
					if (FrameworkUtils.isSuccess(userInfoDto)) {
						if (StringUtils.isBlank(user.getId())
								&& StringUtils.isBlank(logonId)) {
							dto.setRinfo("userId and logonId 不能都为空");
							userBo.setReturnJson(JSON.toJSONString(dto));
							return userBo;
						}
						Map<String, Object> user2Params = new HashMap<String, Object>();
						user2Params.put("logonId", StringUtils.trim(logonId));
						user2Params.put("id", StringUtils.trim(user.getId()));
						BaseObjDto<User> userInfoDto2 = baseDao.getObjProperty(
								UserMapper.class, "getUserByIdOrAccountId",
								user2Params);
						UserDto userDto = null;
						if (FrameworkUtils.isSuccess(userInfoDto2)) {
							User user2 = userInfoDto.getData();
							userDto = new UserDto();
							userDto.setId(user2.getId());
							userDto.setUserName(user2.getUserName());
							userDto.setLogonId(user2.getLogonId());
							userDto.setCellPhone(user2.getCellPhone());
							userDto.setStatus(user2.getStatus());
							userDto.setHeadPortrait(user2.getHeadPortrait());

							if (user2.getCompany() != null) {
								userDto.setComapnyStatus(user2.getCompany()
										.getStatus());
								userDto.setCompanyId(user2.getCompany().getId());
								userDto.setCompanyType(user2.getCompany()
										.getCompanyType());
								userDto.setCompanyName(user2.getCompany()
										.getCompanyName());
								userDto.setLicense(user2.getCompany()
										.getLicense());
								userDto.setRepresentative(user2.getCompany()
										.getRepresentative());
								userDto.setRepresentativeTelephone(user2
										.getCompany()
										.getRepresentativeTelephone());
								userDto.setAddress(user2.getCompany()
										.getAddress());
								userDto.setLatitude(user2.getCompany()
										.getLatitude());
								userDto.setLongitude(user2.getCompany()
										.getLongitude());
								userDto.setCompanyBusinessType(user2
										.getCompany().getCompanyBusinessType());
								userDto.setInvoiceTitle(user2.getCompany()
										.getInvoiceTitle());
								userDto.setTotalAmount(user2.getCompany()
										.getTotalAmount());
								userDto.setDisposableAmount(user2.getCompany()
										.getDisposableAmount());
								userDto.setLockedAmount(user2.getCompany()
										.getLockedAmount());

							}

							userBo.setUserDto(userDto);
							JSONObject jsob = new JSONObject();
							jsob.put("id", user2.getId());
							dto.setData(jsob);
							FrameworkUtils.setSuccess(dto);

						} else {
							FrameworkUtils.setNoData(dto);
							log.error("getUserDetailInfo failure");
						}
					} else {
						log.info("logon失败 密码错误!");
						dto.setRcode(62);
						dto.setRinfo("密码错误 ！");
						userBo.setReturnJson(JSON.toJSONString(dto));
						return userBo;
					}
				} else {
					dto.setRcode(61);
					dto.setRinfo("用户账号不存在！");
					log.info("logon失败 用户账号不存在!");
					userBo.setReturnJson(JSON.toJSONString(dto));
					return userBo;
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("logon4Web exception!");
				throw new RuntimeException("logon4Web Exception!");
			}
		} else {
			log.error("---logon4Web -------- data is null ==== ");
		}
		jsonStr = JSON.toJSONString(dto);
		userBo.setReturnJson(jsonStr);
		return userBo;
	}

	/**
	 * 修改用户信息
	 * 
	 * @author liukh
	 * @date 2017-2-13 下午2:58:54
	 * @param userId
	 * @param data
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseBMService#updateUser(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public String updateUser(String userId, String data) {
		String jsonStr = "";
		BaseDto dto = new BaseDto();
		if (StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(data)) {
			try {
				JSONObject jp = JSON.parseObject(data);
				String userName = jp.getString("userName");
				String cellPhone = jp.getString("cellPhone");
				String headPortrait = jp.getString("headPortrait");
				String companyId = jp.getString("companyId");
				int status = jp.getIntValue("status");
				User user = new User();
				user.setId(userId);
				user.setUserName(userName);
				user.setCellPhone(cellPhone);
				user.setHeadPortrait(headPortrait);
				if (StringUtils.isNotBlank(companyId)) {
					Company company = new Company();
					company.setId(companyId);
					user.setCompany(company);
				}
				user.setStatus(status);
				dto = baseDao.updateByPrimaryKeySelective(UserMapper.class,
						user);
				if (FrameworkUtils.isSuccess(dto)) {
					log.info("updateUser success!");
				} else {
					log.error("updateUser failure!");
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("updateUser exception!");
				throw new RuntimeException("updateUser Exception!");
			}

		} else {
			log.error("---updateUser -------- data or userId is null ==== ");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 获取用户列表
	 * 
	 * @author liukh
	 * @date 2017-2-13 下午3:11:21
	 * @param cellPhone
	 * @param companyId
	 * @param logonId
	 * @param userId
	 * @param userName
	 * @param numberOfItem
	 * @param page
	 * @param status
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseBMService#getUserList(java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String,
	 *      java.lang.String, java.lang.Integer, java.lang.Integer,
	 *      java.lang.Integer)
	 */
	@Override
	public String getUserList(UserQueryForm form) {
		String jsonStr = "";
		BaseObjDto<ItemPage<User>> dto = new BaseObjDto<ItemPage<User>>();
		try {
			BaseObjDto<ItemPage<User>> pagesDto = baseDao.getPageList(
					UserMapper.class, User.class, form, "getUserPageList");
			if (FrameworkUtils.isSuccess(pagesDto)) {
				dto = pagesDto;
				log.info("getUserList success");
			} else {
				FrameworkUtils.setNoData(dto);
				log.error("getUserList failure");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("getUserList Exception");
			throw new RuntimeException("getUserList Exception!");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 修改用户密码
	 * 
	 * @author liukh
	 * @date 2017-3-2 上午11:48:37
	 * @param logonId
	 * @param data
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseBMService#updatePassword(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public String updatePassword(String logonId, String data) {
		String jsonStr = "";
		BaseDto dto = new BaseDto();
		if (StringUtils.isNotBlank(logonId) && StringUtils.isNotBlank(data)) {
			try {
				Map<String, Object> dataParamsMap = (Map<String, Object>) JSON
						.parseObject(data);
				if (dataParamsMap == null) {
					dto.setRinfo("The  data Parse to Map is null");
					return JSON.toJSONString(dto);
				} else if (dataParamsMap.get("oldPassword") == null) {
					dto.setRinfo("The params oldPassword is null");
					return JSON.toJSONString(dto);
				} else if (dataParamsMap.get("newPassword") == null) {
					dto.setRinfo("The params newPassword is null");
					return JSON.toJSONString(dto);
				} else if (dataParamsMap.get("newAgainPassword") == null) {
					dto.setRinfo("The params newAgainPassword is null");
					return JSON.toJSONString(dto);
				} else if (!(dataParamsMap.get("newPassword").toString()
						.equals(dataParamsMap.get("newAgainPassword")
								.toString()))) {
					dto.setRinfo("The newPassword not equals newAgainPassword");
					return JSON.toJSONString(dto);
				}
				String password = dataParamsMap.get("oldPassword").toString();
				String newPassword = dataParamsMap.get("newPassword")
						.toString();

				Map<String, Object> params = new HashMap<String, Object>();
				params.put("logonId", StringUtils.trim(logonId));
				BaseObjDto<User> userDto = baseDao.getObjProperty(
						UserMapper.class, "isValidUser", params);
				if (FrameworkUtils.isSuccess(userDto)) {
					User user = userDto.getData();
					String passwordSalt = user.getPasswordSalt();
					password = PasswordUtils.encyptPassword(
							StringUtils.trim(logonId), password, passwordSalt);
					Map<String, Object> userParams = new HashMap<String, Object>();
					userParams.put("logonId", StringUtils.trim(logonId));
					userParams.put("password", StringUtils.trim(password));
					BaseObjDto<User> userInfoDto = baseDao.getObjProperty(
							UserMapper.class, "getUserByAccount", userParams);
					if (FrameworkUtils.isSuccess(userInfoDto)) {
						user = userInfoDto.getData();
						String usernewPass = PasswordUtils.encyptPassword(
								user.getLogonId(), newPassword, passwordSalt);
						User updateUser = new User();
						updateUser.setId(user.getId());
						updateUser.setPassword(usernewPass);
						dto = baseDao.updateByPrimaryKeySelective(
								UserMapper.class, updateUser);
						if (FrameworkUtils.isSuccess(dto)) {
							log.info("updatepassword success!");
						} else {
							log.error("updatePassword failure!");
						}
					} else {
						dto.setRinfo("原密码不正确!");
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
				log.error("updatePassword exception!");
				throw new RuntimeException("updatePassword Exception!");
			}

		} else {
			log.error("---updatePassword -------- data or userId is null ==== ");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 重置密码
	 * 
	 * @author liukh
	 * @date 2017-3-2 下午2:07:10
	 * @param data
	 * @param logonId
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseBMService#passwordReset(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public String updatePasswordReset(String logonId, String data) {
		String jsonStr = "";
		BaseObjDto<User> dto = new BaseObjDto<User>();
		if (StringUtils.isNotBlank(logonId) && StringUtils.isNotBlank(data)) {
			try {
				Map<String, Object> dataParamsMap = (Map<String, Object>) JSON
						.parseObject(data);
				if (dataParamsMap == null) {
					dto.setRinfo("The  data Parse to Map is null");
					return JSON.toJSONString(dto);
				} else if (dataParamsMap.get("cellPhone") == null) {
					dto.setRinfo("The params cellPhone is null");
					return JSON.toJSONString(dto);
				} else if (dataParamsMap.get("newPassword") == null) {
					dto.setRinfo("The params newPassword is null");
					return JSON.toJSONString(dto);
				} else if (dataParamsMap.get("newAgainPassword") == null) {
					dto.setRinfo("The params newAgainPassword is null");
					return JSON.toJSONString(dto);
				} else if (!(dataParamsMap.get("newPassword").toString()
						.equals(dataParamsMap.get("newAgainPassword")
								.toString()))) {
					dto.setRinfo("The newPassword not equals newAgainPassword");
					return JSON.toJSONString(dto);
				} else if (dataParamsMap.get("validCode") == null) {
					dto.setRinfo("The params validCode is null");
					return JSON.toJSONString(dto);
				}

				String cellPhone = dataParamsMap.get("cellPhone").toString();
				String validCode = dataParamsMap.get("validCode").toString();
				String newPassword = dataParamsMap.get("newPassword")
						.toString();

				// 校验手机验证码是否正确和过期
				jsonStr = this.checkValidCode(cellPhone, validCode);
				BaseDto basDto = JSON.parseObject(jsonStr, BaseDto.class);
				if (!FrameworkUtils.isSuccess(basDto)) {
					return jsonStr;
				}

				Map<String, Object> params = new HashMap<String, Object>();
				params.put("logonId", StringUtils.trim(logonId));
				BaseObjDto<User> userDto = baseDao.getObjProperty(
						UserMapper.class, "isValidUser", params);
				if (FrameworkUtils.isSuccess(userDto)) {
					User user = userDto.getData();
					String passwordSalt = user.getPasswordSalt();
					String usernewPass = PasswordUtils.encyptPassword(
							StringUtils.trim(logonId), newPassword,
							passwordSalt);
					User updateUser = new User();
					updateUser.setId(user.getId());
					updateUser.setPassword(usernewPass);
					BaseDto baseDto = baseDao.updateByPrimaryKeySelective(
							UserMapper.class, updateUser);
					if (FrameworkUtils.isSuccess(baseDto)) {
						User queryuser = new User();
						queryuser.setId(user.getId());
						dto.setRcode(BaseDto.SUCCESS_RCODE);
						dto.setRinfo(BaseDto.DEFAULT_SUCCESS_INFO);
						dto.setData(queryuser);
						log.info("passwordReset success!");
					} else {
						return JSON.toJSONString(baseDto);
					}
				} else {
					dto.setRcode(1);
					log.error("passwordReset failure!");
					dto.setRinfo("The logonId not exit !");
					return JSON.toJSONString(dto);
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("passwordReset exception!");
				throw new RuntimeException("passwordReset Exception!");
			}

		} else {
			log.error("---passwordReset -------- data or userId is null ==== ");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 登录
	 * 
	 * @author liukh
	 * @date 2017-3-1 下午3:54:45
	 * @param data
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseBMService#logon(java.lang.String)
	 */
	@Override
	public UserBo getUserDetailInfo(String data) {
		UserBo userBo = new UserBo();
		BaseObjDto<JSONObject> dto = new BaseObjDto<JSONObject>();
		String jsonStr = "";
		if (StringUtils.isNotBlank(data)) {
			try {
				JSONObject jp = JSON.parseObject(data);
				if (jp == null) {
					dto.setRcode(1);
					dto.setRinfo("The data parseObject to JSONObject is null !");
					userBo.setReturnJson(JSON.toJSONString(dto));
					return userBo;
				}
				String logonId = jp.getString("logonId");
				if (StringUtils.isBlank(logonId)) {
					dto.setRcode(1);
					dto.setRinfo("The data's logonId is null !");
					userBo.setReturnJson(JSON.toJSONString(dto));
					return userBo;
				}
				String password = jp.getString("password");
				if (StringUtils.isBlank(password)) {
					dto.setRcode(1);
					dto.setRinfo("The data's password is null !");
					userBo.setReturnJson(JSON.toJSONString(dto));
					return userBo;
				}
				String companyBusinessType = jp
						.getString("companyBusinessType");
				if (StringUtils.isBlank(companyBusinessType)) {
					dto.setRcode(1);
					dto.setRinfo("The data's companyBusinessType is null !");
					userBo.setReturnJson(JSON.toJSONString(dto));
					return userBo;
				}

				Map<String, Object> params = new HashMap<String, Object>();
				params.put("logonId", StringUtils.trim(logonId));
				BaseObjDto<User> userlogonDto = baseDao.getObjProperty(
						UserMapper.class, "isValidUser", params);
				if (FrameworkUtils.isSuccess(userlogonDto)) {
					User user = userlogonDto.getData();
					String passwordSalt = user.getPasswordSalt();
					password = PasswordUtils.encyptPassword(
							StringUtils.trim(logonId), password, passwordSalt);
					Map<String, Object> userParams = new HashMap<String, Object>();
					userParams.put("logonId", StringUtils.trim(logonId));
					userParams.put("password", StringUtils.trim(password));
					BaseObjDto<User> userInfoDto = baseDao.getObjProperty(
							UserMapper.class, "getUserByAccount", userParams);
					if (FrameworkUtils.isSuccess(userInfoDto)) {
						user = userInfoDto.getData();
						if (user.getCompany() != null
								&& user.getCompany().getCompanyBusinessType() != null) {
							if (!companyBusinessType.equals(user.getCompany()
									.getCompanyBusinessType())) {
								dto.setRcode(Constant.NOT_MATCH_COMPANYBUSINESSTYPE);

								if (companyBusinessType
										.equals(Constant.COMPANY_BUSSINESS_LEASINGSID)) {
									dto.setRinfo(Constant.LOGON_LESSEESIDE);
								} else if (companyBusinessType
										.equals(Constant.COMPANY_BUSSINESS_LESSEESIDE)) {
									dto.setRinfo(Constant.LOGON_LEASINGSIDE);
								}
								userBo.setReturnJson(JSON.toJSONString(dto));
								return userBo;
							}
						}
						if (StringUtils.isBlank(user.getId())
								&& StringUtils.isBlank(logonId)) {
							dto.setRinfo("userId and logonId 不能都为空");
							userBo.setReturnJson(JSON.toJSONString(dto));
							return userBo;
						}
						Map<String, Object> user2Params = new HashMap<String, Object>();
						user2Params.put("logonId", StringUtils.trim(logonId));
						user2Params.put("id", StringUtils.trim(user.getId()));
						BaseObjDto<User> userInfoDto2 = baseDao.getObjProperty(
								UserMapper.class, "getUserByIdOrAccountId",
								user2Params);
						UserDto userDto = null;
						if (FrameworkUtils.isSuccess(userInfoDto2)) {
							User user2 = userInfoDto.getData();
							userDto = new UserDto();
							userDto.setId(user2.getId());
							userDto.setUserName(user2.getUserName());
							userDto.setLogonId(user2.getLogonId());
							userDto.setCellPhone(user2.getCellPhone());
							userDto.setStatus(user2.getStatus());
							userDto.setHeadPortrait(user2.getHeadPortrait());

							if (user2.getCompany() != null) {
								userDto.setComapnyStatus(user2.getCompany()
										.getStatus());
								userDto.setCompanyId(user2.getCompany().getId());
								userDto.setCompanyType(user2.getCompany()
										.getCompanyType());
								userDto.setCompanyName(user2.getCompany()
										.getCompanyName());
								userDto.setLicense(user2.getCompany()
										.getLicense());
								userDto.setRepresentative(user2.getCompany()
										.getRepresentative());
								userDto.setRepresentativeTelephone(user2
										.getCompany()
										.getRepresentativeTelephone());
								userDto.setAddress(user2.getCompany()
										.getAddress());
								userDto.setLatitude(user2.getCompany()
										.getLatitude());
								userDto.setLongitude(user2.getCompany()
										.getLongitude());
								userDto.setCompanyBusinessType(user2
										.getCompany().getCompanyBusinessType());
								userDto.setInvoiceTitle(user2.getCompany()
										.getInvoiceTitle());
								userDto.setTotalAmount(user2.getCompany()
										.getTotalAmount());
								userDto.setDisposableAmount(user2.getCompany()
										.getDisposableAmount());
								userDto.setLockedAmount(user2.getCompany()
										.getLockedAmount());

							}

							userBo.setUserDto(userDto);
							JSONObject jsob = new JSONObject();
							jsob.put("id", user2.getId());
							dto.setData(jsob);
							FrameworkUtils.setSuccess(dto);

						} else {
							FrameworkUtils.setNoData(dto);
							log.error("getUserDetailInfo failure");
						}

					} else {
						log.info("logon失败 密码错误!");
						dto.setRcode(62);
						dto.setRinfo("密码错误 ！");
						userBo.setReturnJson(JSON.toJSONString(dto));
						return userBo;
					}
				} else {
					dto.setRcode(61);
					dto.setRinfo("用户账号不存在！");
					log.info("logon失败 用户账号不存在!");
					userBo.setReturnJson(JSON.toJSONString(dto));
					return userBo;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				log.error("logon exception!");
				throw new RuntimeException("logon Exception!");
			}
		} else {
			log.error("---logon -------- data is null ==== ");
		}
		jsonStr = JSON.toJSONString(dto);
		userBo.setReturnJson(jsonStr);
		return userBo;
	}

	/**
	 * 验证账户是否存在
	 * 
	 * @author liukh
	 * @date 2017-3-1 下午4:02:06
	 * @param logonId
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseBMService#isValidUser(java.lang.String)
	 */
	@Override
	public String isValidUser(String logonId) {
		BaseDto dto = new BaseDto();
		if (StringUtils.isBlank(logonId)) {
			dto.setRcode(1);
			dto.setRinfo("The logonId is null !");
			return JSON.toJSONString(dto);
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("logonId", StringUtils.trim(logonId));
		BaseObjDto<User> userDto = baseDao.getObjProperty(UserMapper.class,
				"isValidUser", params);
		if (FrameworkUtils.isSuccess(userDto)) {
			dto.setRcode(0);
			dto.setRinfo("The logonId is exist !");
			log.info("The logonId is exist !");
		} else {
			dto.setRcode(21);
			dto.setRinfo("The logonId is not exist !");
			log.info("The logonId is not  exist !");
		}
		return JSON.toJSONString(dto);
	}

	/**
	 * 新增设备品牌信息
	 * 
	 * @author liukh
	 * @date 2017-2-14 下午4:03:08
	 * @param data
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseBMService#addDeviceBrand(java.lang.String)
	 */
	@Override
	public String addDeviceBrand(String data) {
		String jsonStr = "";
		BaseDto dto = new BaseDto();
		if (StringUtils.isNotBlank(data)) {
			try {
				DeviceBrand deviceBrand = JSON.parseObject(data,
						DeviceBrand.class);
				if (deviceBrand == null) {
					dto.setRcode(1);
					dto.setRinfo("The data parseObject to DeviceBrand is null !");
					return JSON.toJSONString(dto);
				}
				if (deviceBrand.getBrandName() == null) {
					dto.setRcode(1);
					dto.setRinfo("The data's brandName is null !");
					return JSON.toJSONString(dto);
				}
				Date createDate = new Date();
				deviceBrand.setCreateTime(createDate);
				dto = baseDao.insertSelective(DeviceBrandMapper.class,
						deviceBrand);

				if (FrameworkUtils.isSuccess(dto)) {
					log.info("addDeviceBrand success!");
				} else {
					log.error("addDeviceBrand failure!");
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("addDeviceBrand exception!");
				throw new RuntimeException("addDeviceBrand Exception!");
			}
		} else {
			log.error("---addDeviceBrand -------- data is null ==== ");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 更新品牌信息
	 * 
	 * @author liukh
	 * @date 2017-2-14 下午4:49:39
	 * @param deviceBrandId
	 * @param data
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseBMService#updateDeviceBrand(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public String updateDeviceBrand(String deviceBrandId, String data) {
		String jsonStr = "";
		BaseDto dto = new BaseDto();
		if (StringUtils.isNotBlank(deviceBrandId)
				&& StringUtils.isNotBlank(data)) {
			try {
				DeviceBrand deviceBrand = JSON.parseObject(data,
						DeviceBrand.class);
				if (deviceBrand == null) {
					dto.setRcode(1);
					dto.setRinfo("The data parseObject to DeviceBrand is null !");
					return JSON.toJSONString(dto);
				}
				deviceBrand.setId(deviceBrandId);
				dto = baseDao.updateByPrimaryKeySelective(
						DeviceBrandMapper.class, deviceBrand);
				if (FrameworkUtils.isSuccess(dto)) {
					log.info("updateDeviceBrand success!");
				} else {
					log.error("updateDeviceBrand failure!");
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("updateDeviceBrand exception!");
				throw new RuntimeException("updateDeviceBrand Exception!");
			}

		} else {
			log.error("---updateDeviceBrand -------- data or deviceBrandId is null ==== ");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 获取品牌信息
	 * 
	 * @author liukh
	 * @date 2017-2-14 下午4:20:50
	 * @param deviceBrandId
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseBMService#getDeviceBrand(java.lang.String)
	 */
	@Override
	public String getDeviceBrand(String deviceBrandId) {
		String jsonStr = "";
		BaseObjDto<DeviceBrand> dto = new BaseObjDto<DeviceBrand>();
		try {
			if (StringUtils.isBlank(deviceBrandId)) {
				dto.setRcode(1);
				dto.setRinfo("deviceBrandId is null");
				return JSON.toJSONString(dto);
			}
			BaseObjDto<DeviceBrand> deviceBrandDto = baseDao
					.selectByPrimaryKey(DeviceBrandMapper.class,
							StringUtils.trim(deviceBrandId));
			if (FrameworkUtils.isSuccess(deviceBrandDto)) {
				DeviceBrand deviceBrand = deviceBrandDto.getData();
				dto.setData(deviceBrand);
				FrameworkUtils.setSuccess(dto);
				log.info("getDeviceBrand success!");
			} else {
				FrameworkUtils.setNoData(dto);
				log.error("getDeviceBrand failure");
			}
		} catch (Exception e) {
			log.error("getDeviceBrand exception!");
			e.printStackTrace();
			throw new RuntimeException("getDeviceBrand Exception!");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 获取品牌信息
	 * 
	 * @author liukh
	 * @date 2017-2-14 下午4:55:14
	 * @param numberOfItem
	 * @param page
	 * @param brandName
	 * @param isDisplay
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseBMService#getDeviceBrandList(java.lang.Integer,
	 *      java.lang.Integer, java.lang.String, java.lang.Integer)
	 */
	@Override
	public String getDeviceBrandList(DeviceBrandQueryForm form) {
		String jsonStr = "";
		BaseObjDto<ItemPage<DeviceBrand>> dto = new BaseObjDto<ItemPage<DeviceBrand>>();
		try {

			BaseObjDto<ItemPage<DeviceBrand>> pagesDto = baseDao.getPageList(
					DeviceBrandMapper.class, DeviceBrand.class, form,
					"getDeviceBrandPageList");
			if (FrameworkUtils.isSuccess(pagesDto)) {
				dto = pagesDto;
				log.info("getDeviceBrandList success");
			} else {
				FrameworkUtils.setNoData(dto);
				log.error("getDeviceBrandList failure");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("getDeviceBrandList Exception !");
			throw new RuntimeException("getDeviceBrandList Exception!");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 获取按顺序排列的品牌信息列表
	 * 
	 * @author liukh
	 * @date 2017-3-3 上午11:01:27
	 * @param form
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseBMService#getDeviceBrandIndexList(com.zj.entity.bm.form.DeviceBrandQueryForm)
	 */
	@Override
	public String getDeviceBrandIndexList(DeviceBrandQueryForm form) {
		String jsonStr = "";
		BaseObjDto<BrandIndexListDto> dto = new BaseObjDto<BrandIndexListDto>();
		try {
			BaseObjDto<List<DeviceBrand>> pagesDto = baseDao.getList(
					DeviceBrandMapper.class, DeviceBrand.class,
					"getDeviceBrandAllList", form);

			if (FrameworkUtils.isSuccess(pagesDto)
					&& pagesDto.getData() != null) {

				Map<String, ArrayList<DeviceBrand>> cachMap = new TreeMap<String, ArrayList<DeviceBrand>>(
						new Comparator<String>() {
							public int compare(String obj1, String obj2) {
								// 升序排序
								return obj1.compareTo(obj2);
							}
						});
				List<DeviceBrand> deviceBrandList = pagesDto.getData();
				BrandIndexListDto brandIndexListDto = new BrandIndexListDto();
				BrandIndexListDto.BrandsDto hotBrands = new BrandIndexListDto.BrandsDto(); 
				ArrayList<BrandIndexListDto.BrandsDto> groupBrands = new ArrayList<BrandIndexListDto.BrandsDto>();
				ArrayList<DeviceBrand> hotList = new ArrayList<DeviceBrand>();
				if (deviceBrandList != null && deviceBrandList.size() > 0) {
					
					for (int index = 0; index < deviceBrandList.size(); index++) {
						DeviceBrand deviceBrand = deviceBrandList.get(index);
						//热门品牌
						if(deviceBrand.getSequenceOrder()!= null && deviceBrand.getSequenceOrder()<0){
							hotList.add(deviceBrand);
						}
						String fistSpellStr = "";
						if (deviceBrand.getBrandName() != null) {
							fistSpellStr = PingYinUtil
									.getFirstChineseFirstSpell(deviceBrand
											.getBrandName());
							deviceBrand.setLetter(fistSpellStr);
							if (!cachMap.containsKey(fistSpellStr)) {
								ArrayList<DeviceBrand> processArrayList = new ArrayList<DeviceBrand>();
								cachMap.put(fistSpellStr, processArrayList);
							}
							cachMap.get(fistSpellStr).add(deviceBrand);
						}
					}

				}
				for (Map.Entry<String, ArrayList<DeviceBrand>> entry : cachMap
						.entrySet()) {
					if (entry.getKey() != null && entry.getValue() != null) {
						BrandsDto brandsDto = new BrandsDto();
						brandsDto.setData(entry.getValue());
						brandsDto.setIndex(entry.getKey());
						brandsDto.setRowNum(entry.getValue().size());
						groupBrands.add(brandsDto);
					}

				}
				hotBrands.setData(hotList);
				hotBrands.setIndex("-1");
				hotBrands.setRowNum(hotList.size());
				brandIndexListDto.setHotBrands(hotBrands);
				brandIndexListDto.setGroupBrands(groupBrands);
				dto.setRcode(BaseDto.SUCCESS_RCODE);
				dto.setData(brandIndexListDto);
				dto.setRinfo(BaseDto.DEFAULT_SUCCESS_INFO);

				log.info("getDeviceBrandIndexList success");
			} else {
				FrameworkUtils.setNoData(dto);
				log.error("getDeviceBrandIndexList failure");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("getDeviceBrandIndexList Exception");
			throw new RuntimeException("getDeviceBrandIndexList Exception!");
		}
		jsonStr = JSON.toJSONString(dto,SerializerFeature.DisableCircularReferenceDetect);
		return jsonStr;
	}

	/**
	 * 获取按顺序排列的品牌信息列表且显示已经待租设备中可用的品牌
	 * @author liukh
	 * @date 2017-5-5 下午7:02:52 
	 * @param form
	 * @return
	 * (non-Javadoc)
	 * @see com.zj.access.service.BaseBMService#getExistDeviceBrandIndexList(com.zj.entity.bm.form.DeviceBrandQueryForm)
	 */
	
	@Override
	public String getExistDeviceBrandIndexList(DeviceBrandQueryForm form) {
		String jsonStr = "";
		BaseObjDto<BrandIndexListDto> dto = new BaseObjDto<BrandIndexListDto>();
		try {
			BaseObjDto<List<DeviceBrand>> pagesDto = baseDao.getList(
					DeviceBrandMapper.class, DeviceBrand.class,
					"getExitsDeviceBrandAllList", form);

			if (FrameworkUtils.isSuccess(pagesDto)
					&& pagesDto.getData() != null) {

				Map<String, ArrayList<DeviceBrand>> cachMap = new TreeMap<String, ArrayList<DeviceBrand>>(
						new Comparator<String>() {
							public int compare(String obj1, String obj2) {
								// 升序排序
								return obj1.compareTo(obj2);
							}
						});
				List<DeviceBrand> deviceBrandList = pagesDto.getData();
				BrandIndexListDto brandIndexListDto = new BrandIndexListDto();
				BrandIndexListDto.BrandsDto hotBrands = new BrandIndexListDto.BrandsDto(); 
				ArrayList<BrandIndexListDto.BrandsDto> groupBrands = new ArrayList<BrandIndexListDto.BrandsDto>();
				ArrayList<DeviceBrand> hotList = new ArrayList<DeviceBrand>();
				if (deviceBrandList != null && deviceBrandList.size() > 0) {
					
					for (int index = 0; index < deviceBrandList.size(); index++) {
						DeviceBrand deviceBrand = deviceBrandList.get(index);
						//热门品牌
						if(deviceBrand.getSequenceOrder()!= null && deviceBrand.getSequenceOrder()<0){
							hotList.add(deviceBrand);
						}
						String fistSpellStr = "";
						if (deviceBrand.getBrandName() != null) {
							fistSpellStr = PingYinUtil
									.getFirstChineseFirstSpell(deviceBrand
											.getBrandName());
							deviceBrand.setLetter(fistSpellStr);
							if (!cachMap.containsKey(fistSpellStr)) {
								ArrayList<DeviceBrand> processArrayList = new ArrayList<DeviceBrand>();
								cachMap.put(fistSpellStr, processArrayList);
							}
							cachMap.get(fistSpellStr).add(deviceBrand);
						}
					}

				}
				for (Map.Entry<String, ArrayList<DeviceBrand>> entry : cachMap
						.entrySet()) {
					if (entry.getKey() != null && entry.getValue() != null) {
						BrandsDto brandsDto = new BrandsDto();
						brandsDto.setData(entry.getValue());
						brandsDto.setIndex(entry.getKey());
						brandsDto.setRowNum(entry.getValue().size());
						groupBrands.add(brandsDto);
					}

				}
				hotBrands.setData(hotList);
				hotBrands.setIndex("-1");
				hotBrands.setRowNum(hotList.size());
				brandIndexListDto.setHotBrands(hotBrands);
				brandIndexListDto.setGroupBrands(groupBrands);
				dto.setRcode(BaseDto.SUCCESS_RCODE);
				dto.setData(brandIndexListDto);
				dto.setRinfo(BaseDto.DEFAULT_SUCCESS_INFO);

				log.info("getDeviceBrandIndexList success");
			} else {
				FrameworkUtils.setNoData(dto);
				log.error("getDeviceBrandIndexList failure");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("getDeviceBrandIndexList Exception");
			throw new RuntimeException("getDeviceBrandIndexList Exception!");
		}
		jsonStr = JSON.toJSONString(dto,SerializerFeature.DisableCircularReferenceDetect);
		return jsonStr;
	}

	/**
	 * 新增设备类型
	 * 
	 * @author liukh
	 * @date 2017-2-15 下午2:59:33
	 * @param data
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseBMService#addDeviceType(java.lang.String)
	 */
	@Override
	public String addDeviceType(String data) {
		String jsonStr = "";
		BaseDto dto = new BaseDto();
		if (StringUtils.isNotBlank(data)) {
			try {

				DeviceType deviceType = JSON
						.parseObject(data, DeviceType.class);
				if (deviceType == null) {
					dto.setRcode(1);
					dto.setRinfo("The data parseObject to DeviceType is null !");
					return JSON.toJSONString(dto);
				}
				if (deviceType.getTypeName() == null) {
					dto.setRcode(1);
					dto.setRinfo("The data's  typeName is null !");
					return JSON.toJSONString(dto);
				}
				if (StringUtils.isNotBlank(deviceType.getParentId())) {
					BaseObjDto<DeviceType> deviceTypeDto = baseDao
							.selectByPrimaryKey(DeviceTypeMapper.class,
									StringUtils.trim(deviceType.getParentId()));
					if (!FrameworkUtils.isSuccess(deviceTypeDto)) {
						dto.setRcode(1);
						dto.setRinfo("The data's  parentId is  not exit !");
						return JSON.toJSONString(dto);
					}
					if (deviceType.getIsChild() == null) {
						deviceType.setIsChild(Boolean.TRUE);
					}

				} else {
					if (deviceType.getIsChild() == null) {
						deviceType.setIsChild(Boolean.FALSE);
					}
				}

				Date createDate = new Date();
				deviceType.setCreateTime(createDate);
				dto = baseDao.insertSelective(DeviceTypeMapper.class,
						deviceType);

				if (FrameworkUtils.isSuccess(dto)) {
					log.info("addDeviceType success!");
				} else {
					log.error("addDeviceType failure!");
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("addDeviceType exception!");
				throw new RuntimeException("addDeviceType Exception!");
			}
		} else {
			log.error("---addDeviceType -------- data is null ==== ");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 更新设备类型
	 * 
	 * @author liukh
	 * @date 2017-2-15 下午3:38:53
	 * @param deviceTypeId
	 * @param data
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseBMService#updateDeviceType(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public String updateDeviceType(String deviceTypeId, String data) {
		String jsonStr = "";
		BaseDto dto = new BaseDto();
		if (StringUtils.isNotBlank(deviceTypeId)
				&& StringUtils.isNotBlank(data)) {
			try {
				DeviceType deviceType = JSON
						.parseObject(data, DeviceType.class);
				if (deviceType == null) {
					dto.setRcode(1);
					dto.setRinfo("The data parseObject to DeviceType is null !");
					return JSON.toJSONString(dto);
				}
				deviceType.setId(deviceTypeId);
				if (StringUtils.isNotBlank(deviceType.getParentId())) {
					BaseObjDto<DeviceType> deviceTypeDto = baseDao
							.selectByPrimaryKey(DeviceTypeMapper.class,
									StringUtils.trim(deviceType.getParentId()));
					if (!FrameworkUtils.isSuccess(deviceTypeDto)) {
						dto.setRcode(1);
						dto.setRinfo("The data's  parentId is  not exit !");
						return JSON.toJSONString(dto);
					}
					if (deviceType.getIsChild() == null) {
						deviceType.setIsChild(Boolean.TRUE);
					}

				} else {
					if (deviceType.getIsChild() == null) {
						deviceType.setIsChild(Boolean.FALSE);
					}
				}

				dto = baseDao.updateByPrimaryKeySelective(
						DeviceTypeMapper.class, deviceType);

				if (FrameworkUtils.isSuccess(dto)) {
					log.info("updateDeviceType success!");
				} else {
					log.error("updateDeviceType failure!");
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("updateDeviceType exception!");
				throw new RuntimeException("updateDeviceType Exception!");
			}

		} else {
			log.error("---updateDeviceType -------- data or deviceTypeId is null ==== ");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 获取设备类型
	 * 
	 * @author liukh
	 * @date 2017-2-15 下午3:39:06
	 * @param deviceTypeId
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseBMService#getDeviceType(java.lang.String)
	 */
	@Override
	public String getDeviceType(String deviceTypeId) {
		String jsonStr = "";
		BaseObjDto<DeviceType> dto = new BaseObjDto<DeviceType>();
		try {
			if (StringUtils.isBlank(deviceTypeId)) {
				dto.setRinfo("deviceTypeId is null");
				return JSON.toJSONString(dto);
			}
			BaseObjDto<DeviceType> deviceTypeDto = baseDao.selectByPrimaryKey(
					DeviceTypeMapper.class, StringUtils.trim(deviceTypeId));
			if (FrameworkUtils.isSuccess(deviceTypeDto)) {
				DeviceType deviceType = deviceTypeDto.getData();
				dto.setData(deviceType);
				FrameworkUtils.setSuccess(dto);
				log.info("getDeviceType success!");
			} else {
				FrameworkUtils.setNoData(dto);
				log.error("getDeviceType failure");
			}
		} catch (Exception e) {
			log.error("getDeviceType exception!");
			e.printStackTrace();
			throw new RuntimeException("getDeviceType Exception!");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 获取设备类型列表
	 * 
	 * @author liukh
	 * @date 2017-2-15 下午3:39:19
	 * @param numberOfItem
	 * @param page
	 * @param typeName
	 * @param parentId
	 * @param isDisplay
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseBMService#getDeviceTypeList(java.lang.Integer,
	 *      java.lang.Integer, java.lang.String, java.lang.String,
	 *      java.lang.Integer)
	 */

	@Override
	public String getDeviceTypeList(DeviceTypeQueryForm form) {
		String jsonStr = "";
		BaseObjDto<ItemPage<DeviceType>> dto = new BaseObjDto<ItemPage<DeviceType>>();
		try {

			BaseObjDto<ItemPage<DeviceType>> pagesDto = baseDao.getPageList(
					DeviceTypeMapper.class, DeviceType.class, form,
					"getDeviceTypePageList");
			if (FrameworkUtils.isSuccess(pagesDto)) {
				dto = pagesDto;
				log.info("getDeviceTypeList success");
			} else {
				FrameworkUtils.setNoData(dto);
				log.error("getDeviceTypeList failure");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("getDeviceTypeList Exception ");
			throw new RuntimeException("getDeviceTypeList Exception!");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 新增设备类型规格属性
	 * 
	 * @author liukh
	 * @date 2017-2-17 下午4:39:03
	 * @param data
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseBMService#addDeviceTypeSpecDefinition(java.lang.String)
	 */

	@Override
	public String addDeviceTypeSpecDefinition(String data) {
		String jsonStr = "";
		BaseDto dto = new BaseDto();
		if (StringUtils.isNotBlank(data)) {
			try {
				DeviceTypeSpecDefinition deviceTypeSpecDefinition = JSON
						.parseObject(data, DeviceTypeSpecDefinition.class);
				if (deviceTypeSpecDefinition == null) {
					dto.setRcode(1);
					dto.setRinfo("The data parseObject to deviceTypeSpecDefinition is null !");
					return JSON.toJSONString(dto);
				}

				if (StringUtils.isNotBlank(deviceTypeSpecDefinition
						.getDeviceTypeId())) {
					BaseObjDto<DeviceType> deviceTypeDto = baseDao
							.selectByPrimaryKey(DeviceTypeMapper.class,
									StringUtils.trim(deviceTypeSpecDefinition
											.getDeviceTypeId()));
					if (!FrameworkUtils.isSuccess(deviceTypeDto)) {
						dto.setRcode(1);
						dto.setRinfo("The deviceTypeId is not exist !");
						return JSON.toJSONString(dto);
					}
				}

				Date createDate = new Date();
				deviceTypeSpecDefinition.setCreateTime(createDate);
				dto = baseDao.insertSelective(
						DeviceTypeSpecDefinitionMapper.class,
						deviceTypeSpecDefinition);

				if (FrameworkUtils.isSuccess(dto)) {
					log.info("addDeviceTypeSpecDefinition success!");
				} else {
					log.error("addDeviceTypeSpecDefinition failure!");
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("addDeviceTypeSpecDefinition exception!");
				throw new RuntimeException(
						"addDeviceTypeSpecDefinition Exception!");
			}
		} else {
			log.error("---addDeviceTypeSpecDefinition -------- data is null ==== ");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 更新规格属性定义
	 * 
	 * @author liukh
	 * @date 2017-2-20 上午11:29:16
	 * @param deviceTypeSpecDefinitionId
	 * @param data
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseBMService#updateDeviceTypeSpecDefinition(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public String updateDeviceTypeSpecDefinition(
			String deviceTypeSpecDefinitionId, String data) {
		String jsonStr = "";
		BaseDto dto = new BaseDto();
		if (StringUtils.isNotBlank(deviceTypeSpecDefinitionId)
				&& StringUtils.isNotBlank(data)) {
			try {

				DeviceTypeSpecDefinition deviceTypeSpecDefinition = JSON
						.parseObject(data, DeviceTypeSpecDefinition.class);
				if (deviceTypeSpecDefinition == null) {
					dto.setRcode(1);
					dto.setRinfo("The data parseObject to deviceTypeSpecDefinition is null !");
					return JSON.toJSONString(dto);
				}
				deviceTypeSpecDefinition.setId(deviceTypeSpecDefinitionId);
				dto = baseDao.updateByPrimaryKeySelective(
						DeviceTypeSpecDefinitionMapper.class,
						deviceTypeSpecDefinition);

				if (FrameworkUtils.isSuccess(dto)) {
					log.info("updateDeviceTypeSpecDefinition success!");
				} else {
					log.error("updateDeviceTypeSpecDefinition failure!");
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("updateDeviceTypeSpecDefinition exception!");
				throw new RuntimeException(
						"updateDeviceTypeSpecDefinition Exception!");
			}

		} else {
			log.error("---updateDeviceTypeSpecDefinition -------- data or deviceTypeSpecDefinitionId is null ==== ");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 根据设备类型查询关键参数值列表
	 * 
	 * @author liukh
	 * @date 2017-3-16 下午3:13:33
	 * @param deviceTypeId
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseBMService#getKeAttributeDeviceTypeSpecData(java.lang.String)
	 */
	@Override
	public String getKeAttributeDeviceTypeSpecData(String deviceTypeId) {
		String jsonStr = "";
		BaseObjDto<List<DeviceTypeSpecDataDto>> dto = new BaseObjDto<List<DeviceTypeSpecDataDto>>();
		try {
			if (StringUtils.isNotBlank(deviceTypeId)) {
				Map<String, Object> paramsMap = new HashMap<String, Object>();
				paramsMap.put("deviceTypeId", StringUtils.trim(deviceTypeId));
				BaseObjDto<List<DeviceTypeSpecDataDto>> listDto = baseDao
						.getList(DeviceTypeSpecDataMapper.class,
								DeviceTypeSpecDataDto.class, paramsMap,
								"getKeyAttributeDeviceTypeSpecDataList");
				if (FrameworkUtils.isSuccess(listDto)) {
					dto = listDto;
					log.info("getKeAttributeDeviceTypeSpecData success");
				} else {
					FrameworkUtils.setNoData(dto);
					log.error("getKeAttributeDeviceTypeSpecData failure");

				}
			} else {
				dto.setRcode(1);
				dto.setRinfo("The deviceType is null !");
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.error("getKeAttributeDeviceTypeSpecData Exception ");
			throw new RuntimeException(
					"getKeAttributeDeviceTypeSpecData Exception!");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 获取规格属性定义
	 * 
	 * @author liukh
	 * @date 2017-2-20 上午11:28:52
	 * @param deviceTypeSpecDefinitionId
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseBMService#getDeviceTypeSpecDefinition(java.lang.String)
	 */
	@Override
	public String getDeviceTypeSpecDefinition(String deviceTypeSpecDefinitionId) {
		String jsonStr = "";
		BaseObjDto<DeviceTypeSpecDefinition> dto = new BaseObjDto<DeviceTypeSpecDefinition>();
		try {
			if (StringUtils.isBlank(deviceTypeSpecDefinitionId)) {
				dto.setRinfo("deviceTypeSpecDefinitionId is null");
				return JSON.toJSONString(dto);
			}
			BaseObjDto<DeviceTypeSpecDefinition> deviceTypeSpecDefinitionDto = baseDao
					.selectByPrimaryKey(DeviceTypeSpecDefinitionMapper.class,
							StringUtils.trim(deviceTypeSpecDefinitionId));
			if (FrameworkUtils.isSuccess(deviceTypeSpecDefinitionDto)) {
				DeviceTypeSpecDefinition deviceTypeSpecDefinition = deviceTypeSpecDefinitionDto
						.getData();
				dto.setData(deviceTypeSpecDefinition);
				FrameworkUtils.setSuccess(dto);
				log.info("getDeviceTypeSpecDefinition success!");
			} else {
				FrameworkUtils.setNoData(dto);
				log.error("getDeviceTypeSpecDefinition failure");
			}
		} catch (Exception e) {
			log.error("getDeviceTypeSpecDefinition exception!");
			e.printStackTrace();
			throw new RuntimeException("getDeviceTypeSpecDefinition Exception!");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 获取规格属性定义
	 * 
	 * @author liukh
	 * @date 2017-2-20 上午11:33:15
	 * @param numberOfItem
	 * @param page
	 * @param attributeName
	 * @param deviceTypeId
	 * @param isDisplay
	 * @param isKeyAttribute
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseBMService#getDeviceTypeSpecDefinitionList(java.lang.Integer,
	 *      java.lang.Integer, java.lang.String, java.lang.String,
	 *      java.lang.Boolean, java.lang.Boolean)
	 */
	@Override
	public String getDeviceTypeSpecDefinitionList(
			DeviceTypeSpecDefinitionQueryForm form) {
		String jsonStr = "";
		BaseObjDto<ItemPage<DeviceTypeSpecDefinitionDto>> dto = new BaseObjDto<ItemPage<DeviceTypeSpecDefinitionDto>>();
		try {

			BaseObjDto<ItemPage<DeviceTypeSpecDefinitionDto>> pagesDto = baseDao
					.getPageList(DeviceTypeSpecDefinitionMapper.class,
							DeviceTypeSpecDefinitionDto.class, form,
							"getDeviceTypeSpecDefinitionPageList");
			if (FrameworkUtils.isSuccess(pagesDto)) {
				dto = pagesDto;
				log.info("getDeviceTypeSpecDefinitionList success");
			} else {
				FrameworkUtils.setNoData(dto);
				log.error("getDeviceTypeSpecDefinitionList failure");

			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("getDeviceTypeSpecDefinitionList Exception");
			throw new RuntimeException(
					"getDeviceTypeSpecDefinitionList Exception!");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 新增设备型号
	 * 
	 * @author liukh
	 * @date 2017-2-16 下午1:44:07
	 * @param data
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseBMService#addDeviceModel(java.lang.String)
	 */
	@Override
	public String addDeviceModel(String data) {
		String jsonStr = "";
		BaseDto dto = new BaseDto();
		if (StringUtils.isNotBlank(data)) {
			try {
				JSONObject jp = JSON.parseObject(data);

				String modelName = jp.getString("modelName");
				String deviceTypeId = jp.getString("deviceTypeId");
				String deviceBrandId = jp.getString("deviceBrandId");
				String keyAttributeValueId = jp
						.getString("keyAttributeValueId");
				String creator = jp.getString("creator");

				Date createDate = new Date();
				DeviceModel deviceModel = new DeviceModel();
				deviceModel.setModelName(modelName);
				if (StringUtils.isNotBlank(deviceTypeId)) {
					BaseObjDto<DeviceType> deviceTypeDto = baseDao
							.selectByPrimaryKey(DeviceTypeMapper.class,
									StringUtils.trim(deviceTypeId));
					if (!FrameworkUtils.isSuccess(deviceTypeDto)) {
						dto.setRcode(1);
						dto.setRinfo("The deviceTypeId is not exist !");
						return JSON.toJSONString(dto);

					}
					DeviceType deviceType = new DeviceType();
					deviceType.setId(deviceTypeId);
					deviceModel.setDeviceType(deviceType);
				} else {
					dto.setRcode(1);
					dto.setRinfo("The deviceTypeId is null !");
					return JSON.toJSONString(dto);
				}
				if (StringUtils.isNotBlank(deviceBrandId)) {
					BaseObjDto<DeviceBrand> deviceBrandDto = baseDao
							.selectByPrimaryKey(DeviceBrandMapper.class,
									StringUtils.trim(deviceBrandId));
					if (!FrameworkUtils.isSuccess(deviceBrandDto)) {

						dto.setRcode(1);
						dto.setRinfo("The deviceBrandId is not exist !");
						return JSON.toJSONString(dto);
					}
					DeviceBrand deviceBrand = new DeviceBrand();
					deviceBrand.setId(deviceBrandId);
					deviceModel.setDeviceBrand(deviceBrand);
				} else {
					dto.setRcode(1);
					dto.setRinfo("The deviceBrandId is null !");
					return JSON.toJSONString(dto);
				}
				if (StringUtils.isNotBlank(keyAttributeValueId)) {
					BaseObjDto<DeviceTypeSpecDataDto> deviceTypeSpecDataDto = baseDao
							.selectByPrimaryKey(DeviceTypeSpecDataMapper.class,
									StringUtils.trim(keyAttributeValueId));
					if (!FrameworkUtils.isSuccess(deviceTypeSpecDataDto)) {
						dto.setRcode(1);
						dto.setRinfo("The keyAttributeValueId is not exist !");
						return JSON.toJSONString(dto);
					}
					DeviceTypeSpecData deviceTypeSpecData = new DeviceTypeSpecData();
					deviceTypeSpecData.setId(keyAttributeValueId);
					deviceModel.setDeviceTypeSpecData(deviceTypeSpecData);
				}

				deviceModel.setModelName(modelName);
				deviceModel.setCreateTime(createDate);
				deviceModel.setCreator(creator);
				dto = baseDao.insertSelective(DeviceModelMapper.class,
						deviceModel);

				if (FrameworkUtils.isSuccess(dto)) {
					log.info("addDeviceModel success!");
				} else {
					log.error("addDeviceModel failure!");
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("addDeviceModel exception!");
				throw new RuntimeException("addDeviceModel Exception!");
			}
		} else {
			log.error("---addDeviceModel -------- data is null ==== ");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 更新设备型号
	 * 
	 * @author liukh
	 * @date 2017-2-16 下午2:11:47
	 * @param deviceModelId
	 * @param data
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseBMService#updateDeviceModel(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public String updateDeviceModel(String deviceModelId, String data) {
		String jsonStr = "";
		BaseDto dto = new BaseDto();
		if (StringUtils.isNotBlank(deviceModelId)
				&& StringUtils.isNotBlank(data)) {
			try {
				JSONObject jp = JSON.parseObject(data);
				String modelName = jp.getString("modelName");
				String deviceTypeId = jp.getString("deviceTypeId");
				String deviceBrandId = jp.getString("deviceBrandId");
				String keyAttributeValueId = jp
						.getString("keyAttributeValueId");

				DeviceModel deviceModel = new DeviceModel();
				deviceModel.setId(deviceModelId);
				deviceModel.setModelName(modelName);
				if (StringUtils.isNotBlank(deviceTypeId)) {
					BaseObjDto<DeviceType> deviceTypeDto = baseDao
							.selectByPrimaryKey(DeviceTypeMapper.class,
									StringUtils.trim(deviceTypeId));
					if (FrameworkUtils.isSuccess(deviceTypeDto)) {
						DeviceType deviceType = deviceTypeDto.getData();
						deviceModel.setDeviceType(deviceType);
					} else {
						dto.setRcode(1);
						dto.setRinfo("The deviceTypeId is not exist !");
						return JSON.toJSONString(dto);
					}
				}
				if (StringUtils.isNotBlank(deviceBrandId)) {
					BaseObjDto<DeviceBrand> deviceBrandDto = baseDao
							.selectByPrimaryKey(DeviceBrandMapper.class,
									StringUtils.trim(deviceBrandId));
					if (FrameworkUtils.isSuccess(deviceBrandDto)) {
						DeviceBrand deviceBrand = deviceBrandDto.getData();
						deviceModel.setDeviceBrand(deviceBrand);
					} else {
						dto.setRcode(1);
						dto.setRinfo("The deviceBrandId is not exist !");
						return JSON.toJSONString(dto);
					}
				}
				if (StringUtils.isNotBlank(keyAttributeValueId)) {
					BaseObjDto<DeviceTypeSpecDataDto> deviceTypeSpecDataDto = baseDao
							.selectByPrimaryKey(DeviceTypeSpecDataMapper.class,
									StringUtils.trim(keyAttributeValueId));
					if (FrameworkUtils.isSuccess(deviceTypeSpecDataDto)) {
						DeviceTypeSpecDataDto deviceTypeSpecDatas = deviceTypeSpecDataDto
								.getData();
						DeviceTypeSpecData deviceTypeSpecData = new DeviceTypeSpecData();
						deviceTypeSpecData.setId(deviceTypeSpecDatas.getId());
						deviceModel.setDeviceTypeSpecData(deviceTypeSpecData);
					} else {
						dto.setRcode(1);
						dto.setRinfo("The keyAttributeValueId is not exist !");
						return JSON.toJSONString(dto);
					}
				}
				deviceModel.setModelName(modelName);
				dto = baseDao.updateByPrimaryKeySelective(
						DeviceModelMapper.class, deviceModel);

				if (FrameworkUtils.isSuccess(dto)) {
					log.info("updateDeviceModel success!");
				} else {
					log.error("updateDeviceModel failure!");
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("updateDeviceModel exception!");
				throw new RuntimeException("updateDeviceModel Exception!");
			}

		} else {
			log.error("---updateDeviceModel -------- data or deviceModelId is null ==== ");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 获取设备型号
	 * 
	 * @author liukh
	 * @date 2017-2-16 下午2:11:30
	 * @param deviceModelId
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseBMService#getDeviceModel(java.lang.String)
	 */
	@Override
	public String getDeviceModel(String deviceModelId) {
		String jsonStr = "";
		BaseObjDto<JSONObject> dto = new BaseObjDto<JSONObject>();
		JSONObject js = new JSONObject();
		try {
			if (StringUtils.isBlank(deviceModelId)) {
				dto.setRinfo("deviceModelId is null");
				return JSON.toJSONString(dto);
			}
			BaseObjDto<DeviceModel> deviceModelDto = baseDao
					.selectByPrimaryKey(DeviceModelMapper.class,
							StringUtils.trim(deviceModelId));

			if (FrameworkUtils.isSuccess(deviceModelDto)) {
				DeviceModel deviceModel = deviceModelDto.getData();
				js.put("id", deviceModel.getId());
				js.put("modelName", deviceModel.getModelName());
				js.put("creator", deviceModel.getCreator());
				js.put("createTime", deviceModel.getCreateTime());

				if (deviceModel != null) {
					if (deviceModel.getDeviceType() != null) {
						js.put("deviceTypeId", deviceModel.getDeviceType()
								.getId());
						js.put("deviceTypeName", deviceModel.getDeviceType()
								.getTypeName());
					}
					if (deviceModel.getDeviceBrand() != null) {
						js.put("deviceBrandId", deviceModel.getDeviceBrand()
								.getId());
						js.put("deviceBrandName", deviceModel.getDeviceBrand()
								.getBrandName());
					}
					if (deviceModel.getDeviceTypeSpecData() != null) {
						js.put("keyAttributeValueId", deviceModel
								.getDeviceTypeSpecData().getId());
					}

				}
				dto.setData(js);
				FrameworkUtils.setSuccess(dto);
				log.info("getDeviceModel success!");
			} else {
				FrameworkUtils.setNoData(dto);
				log.error("getDeviceModel failure");
			}
		} catch (Exception e) {
			log.error("getDeviceModel exception!");
			e.printStackTrace();
			throw new RuntimeException("getDeviceModel Exception!");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 获取设备型号列表
	 * 
	 * @author liukh
	 * @date 2017-2-16 下午2:24:53
	 * @param numberOfItem
	 * @param page
	 * @param deviceBrandId
	 * @param deviceTypeId
	 * @param modelName
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseBMService#getDeviceModelList(java.lang.Integer,
	 *      java.lang.Integer, java.lang.String, java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public String getDeviceModelList(DeviceModelQueryForm form) {

		String jsonStr = "";
		BaseObjDto<ItemPage<JSONObject>> dto = new BaseObjDto<ItemPage<JSONObject>>();
		try {

			BaseObjDto<ItemPage<DeviceModel>> pagesDto = baseDao.getPageList(
					DeviceModelMapper.class, DeviceModel.class, form,
					"getDeviceModelPageList");
			if (FrameworkUtils.isSuccess(pagesDto)) {
				ItemPage<JSONObject> jpItempage = new ItemPage<JSONObject>();
				jpItempage.setNextIndex(pagesDto.getData().getNextIndex());
				jpItempage.setPageIndex(pagesDto.getData().getPageIndex());
				jpItempage.setPagesCount(pagesDto.getData().getPagesCount());
				jpItempage.setPageSize(pagesDto.getData().getPageSize());
				jpItempage.setPreIndex(pagesDto.getData().getPreIndex());
				jpItempage.setRowsCount(pagesDto.getData().getRowsCount());
				List<JSONObject> jsList = new ArrayList<JSONObject>();

				for (DeviceModel deviceModel : pagesDto.getData().getItems()) {
					JSONObject js = new JSONObject();
					js.put("id", deviceModel.getId());
					js.put("modelName", deviceModel.getModelName());
					js.put("creator", deviceModel.getCreator());
					js.put("createTime", deviceModel.getCreateTime());

					if (deviceModel != null) {
						if (deviceModel.getDeviceType() != null) {
							js.put("deviceTypeId", deviceModel.getDeviceType()
									.getId());
							js.put("deviceTypeName", deviceModel
									.getDeviceType().getTypeName());
						}
						if (deviceModel.getDeviceBrand() != null) {
							js.put("deviceBrandId", deviceModel
									.getDeviceBrand().getId());
							js.put("deviceBrandName", deviceModel
									.getDeviceBrand().getBrandName());
						}
						if (deviceModel.getDeviceTypeSpecData() != null) {
							js.put("keyAttributeValueId", deviceModel
									.getDeviceTypeSpecData().getId());
						}

					}
					jsList.add(js);

				}
				jpItempage.setItems(jsList);
				dto.setData(jpItempage);
				FrameworkUtils.setSuccess(dto);

				log.info("getDeviceModelList success");
			} else {
				FrameworkUtils.setNoData(dto);
				log.error("getDeviceModelList failure");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("getDeviceModelList Exception");
			throw new RuntimeException("getDeviceModelList Exception!");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 增加属性定义数据
	 * 
	 * @author liukh
	 * @date 2017-2-20 下午2:44:23
	 * @param data
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseBMService#addDeviceTypeSpecData(java.lang.String)
	 */
	@Override
	public String addDeviceTypeSpecData(String data) {
		String jsonStr = "";
		BaseDto dto = new BaseDto();
		if (StringUtils.isNotBlank(data)) {
			try {
				JSONObject jp = JSON.parseObject(data);

				String definitionId = jp.getString("definitionId");
				String deviceTypeId = jp.getString("deviceTypeId");
				String dataValue = jp.getString("data");
				String creator = jp.getString("creator");
				if (StringUtils.isNotBlank(definitionId)) {
					BaseObjDto<DeviceTypeSpecDefinition> deviceTypeSpecDefinitionDto = baseDao
							.selectByPrimaryKey(
									DeviceTypeSpecDefinitionMapper.class,
									StringUtils.trim(definitionId));
					if (!FrameworkUtils.isSuccess(deviceTypeSpecDefinitionDto)) {
						dto.setRcode(1);
						dto.setRinfo("The definitionId is not exist !");
						return JSON.toJSONString(dto);
					}
				}

				Date createDate = new Date();
				DeviceTypeSpecData deviceTypeSpecData = new DeviceTypeSpecData();
				deviceTypeSpecData.setDefinitionId(definitionId);
				deviceTypeSpecData.setDeviceTypeId(deviceTypeId);
				deviceTypeSpecData.setData(dataValue);
				deviceTypeSpecData.setCreator(creator);
				deviceTypeSpecData.setCreateTime(createDate);
				dto = baseDao.insertSelective(DeviceTypeSpecDataMapper.class,
						deviceTypeSpecData);

				if (FrameworkUtils.isSuccess(dto)) {
					log.info("addDeviceTypeSpecData success!");
				} else {
					log.error("addDeviceTypeSpecData failure!");
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("addDeviceTypeSpecData exception!");
				throw new RuntimeException("addDeviceTypeSpecData Exception!");
			}
		} else {
			log.error("---addDeviceTypeSpecData -------- data is null ==== ");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	@Override
	public String updateDeviceTypeSpecData(String deviceTypeSpecDataId,
			String data) {
		String jsonStr = "";
		BaseDto dto = new BaseDto();
		if (StringUtils.isNotBlank(deviceTypeSpecDataId)
				&& StringUtils.isNotBlank(data)) {
			try {
				JSONObject jp = JSON.parseObject(data);

				String dataValue = jp.getString("data");
				DeviceTypeSpecData deviceTypeSpecData = new DeviceTypeSpecData();
				deviceTypeSpecData.setId(deviceTypeSpecDataId);
				deviceTypeSpecData.setData(dataValue);

				dto = baseDao.updateByPrimaryKeySelective(
						DeviceTypeSpecDataMapper.class, deviceTypeSpecData);

				if (FrameworkUtils.isSuccess(dto)) {
					log.info("updateDeviceTypeSpecData success!");
				} else {
					log.error("updateDeviceTypeSpecData failure!");
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("updateDeviceTypeSpecData exception!");
				throw new RuntimeException(
						"updateDeviceTypeSpecData Exception!");
			}

		} else {
			log.error("---updateDeviceTypeSpecData -------- data or deviceTypeSpecDataId is null ==== ");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 获取属性定义数据
	 * 
	 * @author liukh
	 * @date 2017-2-20 下午2:51:49
	 * @param deviceTypeSpecDataId
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseBMService#getDeviceTypeSpecData(java.lang.String)
	 */
	@Override
	public String getDeviceTypeSpecData(String deviceTypeSpecDataId) {
		String jsonStr = "";
		BaseObjDto<DeviceTypeSpecDataDto> dto = new BaseObjDto<DeviceTypeSpecDataDto>();
		try {
			if (StringUtils.isBlank(deviceTypeSpecDataId)) {
				dto.setRinfo("deviceTypeSpecDataId is null");
				return JSON.toJSONString(dto);
			}
			BaseObjDto<DeviceTypeSpecDataDto> deviceTypeSpecDataDto = baseDao
					.selectByPrimaryKey(DeviceTypeSpecDataMapper.class,
							StringUtils.trim(deviceTypeSpecDataId));
			if (FrameworkUtils.isSuccess(deviceTypeSpecDataDto)) {
				DeviceTypeSpecDataDto deviceTypeSpecData = deviceTypeSpecDataDto
						.getData();
				dto.setData(deviceTypeSpecData);
				FrameworkUtils.setSuccess(dto);
				log.info("getDeviceTypeSpecData success!");
			} else {
				FrameworkUtils.setNoData(dto);
				log.error("getDeviceTypeSpecData failure");
			}
		} catch (Exception e) {
			log.error("getDeviceTypeSpecData exception!");
			e.printStackTrace();
			throw new RuntimeException("getDeviceTypeSpecData Exception!");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 获取规格属性数据列表
	 * 
	 * @author liukh
	 * @date 2017-2-20 下午2:52:38
	 * @param numberOfItem
	 * @param page
	 * @param definitionId
	 * @param deviceTypeId
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseBMService#getDeviceTypeSpecDataList(java.lang.Integer,
	 *      java.lang.Integer, java.lang.String, java.lang.String)
	 */
	@Override
	public String getDeviceTypeSpecDataList(DeviceTypeSpecDataQueryForm form) {
		String jsonStr = "";
		BaseObjDto<ItemPage<DeviceTypeSpecDataDto>> dto = new BaseObjDto<ItemPage<DeviceTypeSpecDataDto>>();
		try {

			BaseObjDto<ItemPage<DeviceTypeSpecDataDto>> pagesDto = baseDao
					.getPageList(DeviceTypeSpecDataMapper.class,
							DeviceTypeSpecDataDto.class, form,
							"getDeviceTypeSpecDataPageList");
			if (FrameworkUtils.isSuccess(pagesDto)) {
				dto = pagesDto;
				log.info("getDeviceTypeSpecDataList success");
			} else {
				FrameworkUtils.setNoData(dto);
				log.error("getDeviceTypeSpecDataList failure");

			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("getDeviceTypeSpecDataList Exception");
			throw new RuntimeException("getDeviceTypeSpecDataList Exception!");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 新增审核信息
	 * 
	 * @author liukh
	 * @date 2017-2-20 下午5:15:06
	 * @param data
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseBMService#addAudit(java.lang.String)
	 */
	@Override
	public String addAudit(String data) {
		String jsonStr = "";
		BaseDto dto = new BaseDto();
		if (StringUtils.isNotBlank(data)) {
			try {

				Audit audit = JSON.parseObject(data, Audit.class);
				if (audit == null) {
					dto.setRcode(1);
					dto.setRinfo("The data parseObject to Audit is null !");
					return JSON.toJSONString(dto);
				}
				Date createDate = new Date();
				audit.setCreateTime(createDate);
				dto = baseDao.insertSelective(AuditMapper.class, audit);

				if (FrameworkUtils.isSuccess(dto)) {
					log.info("addAudit success!");
				} else {
					log.error("addAudit failure!");
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("addAudit exception!");
				throw new RuntimeException("addAudit Exception!");
			}
		} else {
			log.error("---addAudit -------- data is null ==== ");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}
	
	
/**
 * 新增或更改审核信息
 * @author liukh
 * @date 2017-5-8 下午4:33:56 
 * @param data
 * @return
 * (non-Javadoc)
 * @see com.zj.access.service.BaseBMService#addOrUpdateAudit(java.lang.String)
 */
	@Override
	public String addOrUpdateAudit(String data) {
		String jsonStr = "";
		BaseDto dto = new BaseDto();
		if (StringUtils.isNotBlank(data)) {
			try {

				Audit audit = JSON.parseObject(data, Audit.class);
				if (audit == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data parseObject to Audit is null !");
					return JSON.toJSONString(dto);
				}
				if(audit.getRelatedId()== null){
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's  relatedId is null !");
					return JSON.toJSONString(dto);
				}
				if(audit.getInformationType() == null){
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's  informationType is null !");
					return JSON.toJSONString(dto);
				}
				if(audit.getContent() == null){
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's  content is null !");
					return JSON.toJSONString(dto);
				}
				Date createDate = new Date();
				audit.setCreateTime(createDate);
				
				AuditQueryForm form = new AuditQueryForm();
				form.setInformationType(audit.getInformationType());
				form.setRelatedId(audit.getRelatedId());
				BaseObjDto<ItemPage<Audit>> pagesDto = baseDao.getPageList(
						AuditMapper.class, Audit.class, form, "getAuditPageList");
				if (FrameworkUtils.isSuccess(pagesDto)) {
					if(pagesDto.getData()!= null && pagesDto.getData().getItems()!= null&& pagesDto.getData().getItems().size()>0){
						Audit updateAudit = pagesDto.getData().getItems().get(0);
						audit.setId(updateAudit.getId());
						dto = baseDao.updateByPrimaryKeySelective(AuditMapper.class, audit);
					}
					
				}else{
					dto = baseDao.insertSelective(AuditMapper.class, audit);

				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("addAudit exception!");
				throw new RuntimeException("addAudit Exception!");
			}
		} else {
			log.error("---addAudit -------- data is null ==== ");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 审核信息列表
	 * 
	 * @author liukh
	 * @date 2017-2-20 下午5:24:48
	 * @param numberOfItem
	 * @param page
	 * @param informationType
	 * @param relatedId
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseBMService#getAuditList(java.lang.Integer,
	 *      java.lang.Integer, java.lang.Integer, java.lang.String)
	 */
	@Override
	public String getAuditList(AuditQueryForm form) {
		String jsonStr = "";
		BaseObjDto<ItemPage<Audit>> dto = new BaseObjDto<ItemPage<Audit>>();
		try {

			BaseObjDto<ItemPage<Audit>> pagesDto = baseDao.getPageList(
					AuditMapper.class, Audit.class, form, "getAuditPageList");
			if (FrameworkUtils.isSuccess(pagesDto)) {
				dto = pagesDto;
				log.info("getAuditList success");
			} else {
				FrameworkUtils.setNoData(dto);
				log.error("getAuditList failure");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("getAuditList Exception");
			throw new RuntimeException("getAuditList Exception!");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 新增账户明细
	 * 
	 * @author liukh
	 * @date 2017-2-20 下午5:43:46
	 * @param data
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseBMService#addAccountDetail(java.lang.String)
	 */
	@Override
	public String addAccountDetail(String data) {
		String jsonStr = "";
		BaseDto dto = new BaseDto();
		if (StringUtils.isNotBlank(data)) {
			try {
				AccountDetail accountDetail = JSON.parseObject(data,
						AccountDetail.class);
				if (accountDetail == null) {
					dto.setRcode(1);
					dto.setRinfo("The data parseObject to AccountDetail is null !");
					return JSON.toJSONString(dto);
				}

				if (accountDetail.getBusinessType() == null) {
					dto.setRcode(1);
					dto.setRinfo("The data's businessType is null !");
					return JSON.toJSONString(dto);
				}
				if (accountDetail.getTransactionAmount() == null) {
					dto.setRcode(1);
					dto.setRinfo("The data's transactionAmount is null !");
					return JSON.toJSONString(dto);
				}
				if (accountDetail.getBusinessType() > Constant.ACCOUNTDETAIL_BUSSINESS_WITHDRAW
						|| accountDetail.getBusinessType() < Constant.ACCOUNTDETAIL_BUSSINESS_TOPUP) {
					dto.setRcode(1);
					dto.setRinfo("The data's businessType value is error !");
					return JSON.toJSONString(dto);
				}
				if (StringUtils.isBlank(accountDetail.getExpenseType())) {
					dto.setRcode(1);
					dto.setRinfo("The data's expenseType is null !");
					return JSON.toJSONString(dto);
				}
				if (!(accountDetail.getExpenseType().equals(
						Constant.ACCOUNTDETAIL_EXPENSETYPE_ADD) || accountDetail
						.getExpenseType().equals(
								Constant.ACCOUNTDETAIL_EXPENSETYPE_SUBTRACTION))) {
					dto.setRcode(1);
					dto.setRinfo("The data's expenseType value is error !");
					return JSON.toJSONString(dto);
				}
				BaseObjDto<Company> companyDto = null;
				if (StringUtils.isNotBlank(accountDetail.getCompanyId())) {
					companyDto = baseDao.selectByPrimaryKey(
							CompanyMapper.class,
							StringUtils.trim(accountDetail.getCompanyId()));
					if (!FrameworkUtils.isSuccess(companyDto)) {
						dto.setRcode(1);
						dto.setRinfo("The companyId is not exist !");
						return JSON.toJSONString(dto);
					}
				}

				Date createDate = new Date();
				accountDetail.setRecordTime(createDate);

				dto = baseDao.insertSelective(AccountDetailMapper.class,
						accountDetail);

				if (FrameworkUtils.isSuccess(dto)) {
					Company company = companyDto.getData();
					Company updateCompany = new Company();
					if (company != null) {
						BigDecimal disposableAmount = company
								.getDisposableAmount();
						BigDecimal totalAmount = company.getTotalAmount();
						BigDecimal transactionAmount = accountDetail
								.getTransactionAmount();
						if (!accountDetail.isAdd()) {
							transactionAmount = transactionAmount
									.multiply(new BigDecimal(-1));
						}
						disposableAmount = disposableAmount
								.add(transactionAmount);
						totalAmount = totalAmount.add(transactionAmount);
						updateCompany.setDisposableAmount(disposableAmount);
						updateCompany.setTotalAmount(totalAmount);
						updateCompany.setId(company.getId());
						dto = baseDao.updateByPrimaryKeySelective(
								CompanyMapper.class, updateCompany);
						if (FrameworkUtils.isSuccess(dto)) {
							log.info("addAccountDetail success!");
						} else {
							log.error("addAccountDetail failure!");
						}
					}
				} else {
					log.error("addAccountDetail failure!");
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("addAccountDetail exception!");
				throw new RuntimeException("addAccountDetail Exception!");
			}
		} else {
			log.error("---addAccountDetail -------- data is null ==== ");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 获取账户明细列表
	 * 
	 * @author liukh
	 * @date 2017-2-21 上午9:55:02
	 * @param numberOfItem
	 * @param page
	 * @param businessType
	 * @param companyId
	 * @param expenseType
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseBMService#getAccountDetailList(java.lang.Integer,
	 *      java.lang.Integer, java.lang.Integer, java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public String getAccountDetailList(AccountDetailQueryForm form) {
		String jsonStr = "";
		BaseObjDto<ItemPage<AccountDetail>> dto = new BaseObjDto<ItemPage<AccountDetail>>();
		try {
			BaseObjDto<ItemPage<AccountDetail>> pagesDto = baseDao.getPageList(
					AccountDetailMapper.class, AccountDetail.class, form,
					"getAccountDetailPageList");
			if (FrameworkUtils.isSuccess(pagesDto)) {
				dto = pagesDto;
				log.info("getAccountDetailList success");
			} else {
				FrameworkUtils.setNoData(dto);
				log.error("getAccountDetailList failure");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("getAccountDetailList Exception");
			throw new RuntimeException("getAccountDetailList Exception!");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 审核公司
	 * 
	 * @author liukh
	 * @date 2017-3-16 下午4:36:15
	 * @param data
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BaseBMService#updateCheckCompany(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String updateCheckCompany(String data) {
		String jsonStr = "";
		BaseDto dto = new BaseDto();
		if (StringUtils.isBlank(data)) {
			dto.setRinfo("The data is null ");
			dto.setRcode(1);
			return JSON.toJSONString(dto);
		}
		try {
			Map<String, Object> params = (Map<String, Object>) JSON.parse(data);
			if (params == null) {
				dto.setRinfo("The params is null ");
				dto.setRcode(1);
				return JSON.toJSONString(dto);
			} else if (params.get("status") == null) {
				dto.setRinfo("The data's status is null ");
				dto.setRcode(1);
				return JSON.toJSONString(dto);
			} else if (params.get("companyId") == null) {
				dto.setRinfo("The data's companyId is null ");
				dto.setRcode(1);
				return JSON.toJSONString(dto);
			}
			Company company = new Company();
			company.setStatus(Integer.valueOf(params.get("status").toString()));
			company.setId(StringUtils.trim(params.get("companyId").toString()));
			dto = baseDao.updateByPrimaryKeySelective(CompanyMapper.class,
					company);
			Audit updateAudit = new Audit();
			if (FrameworkUtils.isSuccess(dto)) {
				log.info("checkCompany success!");
				UserDto userDto = baseOtherService
						.getUserDtoByComapnyId(StringUtils.trim(params.get(
								"companyId").toString()));
				if (userDto != null && userDto.getCellPhone() != null) {
					// 审核不通过
					if (String.valueOf(Constant.COMAPNY_STATUS_NOPASS).equals(
							params.get("status").toString())) {
						SmsUtil.sendCompanyCheckNOPassedInfoSms(
								userDto.getCellPhone(),
								userDto.getCompanyName());
						updateAudit.setIsPassed(Boolean.FALSE);
					} else {
						SmsUtil.sendCompanyCheckPassedInfoSms(
								userDto.getCellPhone(),
								userDto.getCompanyName());
						updateAudit.setIsPassed(Boolean.TRUE);
					}
				}
				if(FrameworkUtils.isSuccess(dto)){
					updateAudit.setRelatedId(StringUtils.trim(params.get("companyId").toString()));
					updateAudit.setInformationType(Constant.AUDIT_TYPE_COMPANY);
					updateAudit.setCreateTime(new Date());
					updateAudit.setAuditor(params.get("logonUserId").toString());
					updateAudit.setContent(params.get("content").toString());
					return addOrUpdateAudit(JSON.toJSONString(updateAudit));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

}
