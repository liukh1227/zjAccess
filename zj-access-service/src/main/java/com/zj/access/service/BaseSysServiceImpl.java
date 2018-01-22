package com.zj.access.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zj.access.base.dao.IBaseDao;
import com.zj.access.base.mapper.SysUserMapper;
import com.zj.access.common.FrameworkUtils;
import com.zj.base.common.Constant;
import com.zj.base.common.ItemPage;
import com.zj.base.common.utils.CommonUtils;
import com.zj.base.common.utils.PasswordUtils;
import com.zj.base.entity.base.dto.BaseDto;
import com.zj.base.entity.base.dto.BaseObjDto;
import com.zj.entity.base.po.SysUser;
import com.zj.entity.sys.bo.SysUserBo;
import com.zj.entity.sys.form.SysUserQueryForm;
@Service("baseSysService")
@Scope("prototype")
public class BaseSysServiceImpl implements BaseSysService {
	private static final Logger log = Logger.getLogger(BaseSysServiceImpl.class);
	@Autowired
	private IBaseDao baseDao;
	@Autowired
	private BaseBMService basebMService;

	
	@Override
	public String addSysUser(String data) {
		String jsonStr = "";
		BaseObjDto<SysUser> dto = new BaseObjDto<SysUser>();
		if (StringUtils.isNotBlank(data)) {
			try {

				SysUser sysUser = JSON.parseObject(data, SysUser.class);
				if (sysUser == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data parseObject to SysUser is null !");
					return JSON.toJSONString(dto);
				}
				if (sysUser.getAccount() == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's account is null !");
					return JSON.toJSONString(dto);
				}
				if (sysUser.getPassword() == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's password is null !");
					return JSON.toJSONString(dto);
				}
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("account", StringUtils.trim(sysUser.getAccount()));
				BaseObjDto<SysUser> userVailDto = baseDao.getObjProperty(SysUserMapper.class,
						"isValidSysUser", params);
				if (FrameworkUtils.isSuccess(userVailDto)) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("该账号已经注册，请勿重复注册!");
					return JSON.toJSONString(dto);
				}
				String passwordSalt = CommonUtils.getRandomString(40)
						.toLowerCase();
				sysUser.setPassword_salt(passwordSalt);
				
				log.info("----passwordSalt-----" + passwordSalt);
				String userPass = PasswordUtils.encyptPassword(
						sysUser.getAccount(), sysUser.getPassword(), passwordSalt);
				sysUser.setPassword(userPass);
				Date createDate = new Date();
				sysUser.setCreateTime(createDate);
				sysUser.setStatus(String.valueOf(Constant.USER_STATUS_TAKEEFFICACY));
				BaseDto addDto = baseDao.insertSelective(SysUserMapper.class, sysUser);

				if (FrameworkUtils.isSuccess(addDto)) {
					SysUser users = new SysUser();
					users.setId(sysUser.getId());
					users.setCreateTime(sysUser.getCreateTime());
					dto.setData(users);
					dto.setRcode(BaseDto.SUCCESS_RCODE);
					dto.setRinfo(BaseDto.DEFAULT_SUCCESS_INFO);
					log.info("addSysUser success!");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				log.error("addSysUser exception!");
				throw new RuntimeException("addSysUser Exception!");
			}
		} else {
			log.error("---addSysUser -------- data is null ==== ");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	@Override
	public String getSysUser(String userId) {
		String jsonStr = "";
		BaseObjDto<SysUser> dto = new BaseObjDto<SysUser>();
		try {
			if (StringUtils.isBlank(userId)) {
				dto.setRcode(BaseDto.ERROR_RCODE);
				dto.setRinfo("userId is null");
				return JSON.toJSONString(dto);
			}
			BaseObjDto<SysUser> userDto = baseDao.selectByPrimaryKey(
					SysUserMapper.class, StringUtils.trim(userId));
			if (FrameworkUtils.isSuccess(userDto)) {
				SysUser user = userDto.getData();
				dto.setData(user);
				FrameworkUtils.setSuccess(dto);
				log.info("getSysUser success!");
			} else {
				FrameworkUtils.setNoData(dto);
				log.error("getSysUser failure");
			}
		} catch (Exception e) {
			log.error("getSysUser exception!");
			e.printStackTrace();
			throw new RuntimeException("getSysUser Exception!");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	@Override
	public String getSysUserMoreInfo(String userId, String account) {
		String jsonStr = "";
		BaseObjDto<SysUser> dto = new BaseObjDto<SysUser>();
		try {
			if (StringUtils.isBlank(userId) && StringUtils.isBlank(account)) {
				dto.setRinfo("userId and account 不能都为空");
				return JSON.toJSONString(dto);
			}
			Map<String, Object> userParams = new HashMap<String, Object>();
			userParams.put("account", StringUtils.trim(account));
			userParams.put("id", StringUtils.trim(userId));
			BaseObjDto<SysUser> userInfoDto = baseDao.getObjProperty(
					SysUserMapper.class, "getSysUserByIdOrAccountId", userParams);
			if (FrameworkUtils.isSuccess(userInfoDto)) {
				dto = userInfoDto;
			} else {
				FrameworkUtils.setNoData(dto);
				log.error("getSysUserMoreInfo failure");
			}

		} catch (Exception e) {
			log.error("getSysUserMoreInfo exception!");
			e.printStackTrace();
			throw new RuntimeException("getSysUserMoreInfo Exception!");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	@Override
	public String updateSysUser(String userId, String data) {
		String jsonStr = "";
		BaseDto dto = new BaseDto();
		if (StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(data)) {
			try {
				SysUser sysUser = JSON.parseObject(data, SysUser.class);
				if (sysUser == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data parseObject to SysUser is null !");
					return JSON.toJSONString(dto);
				}
				sysUser.setId(userId);
				dto = baseDao.updateByPrimaryKeySelective(SysUserMapper.class,
						sysUser);
				if (FrameworkUtils.isSuccess(dto)) {
					log.info("updateSysUser success!");
				} else {
					log.error("updateSysUser failure!");
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("updateSysUser exception!");
				throw new RuntimeException("updateSysUser Exception!");
			}

		} else {
			log.error("---updateSysUser -------- data or userId is null ==== ");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	@Override
	public String getSysUserList(SysUserQueryForm form) {
		String jsonStr = "";
		BaseObjDto<ItemPage<SysUser>> dto = new BaseObjDto<ItemPage<SysUser>>();
		try {
			BaseObjDto<ItemPage<SysUser>> pagesDto = baseDao.getPageList(
					SysUserMapper.class, SysUser.class, form, "getSysUserPageList");
			if (FrameworkUtils.isSuccess(pagesDto)) {
				dto = pagesDto;
				log.info("getSysUserList success");
			} else {
				FrameworkUtils.setNoData(dto);
				log.error("getSysUserList failure");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("getSysUserList Exception");
			throw new RuntimeException("getSysUserList Exception!");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	@Override
	public String isValidSysUser(String account) {
		BaseDto dto = new BaseDto();
		if (StringUtils.isBlank(account)) {
			dto.setRcode(BaseDto.ERROR_RCODE);
			dto.setRinfo("The account is null !");
			return JSON.toJSONString(dto);
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("account", StringUtils.trim(account));
		BaseObjDto<SysUser> userDto = baseDao.getObjProperty(SysUserMapper.class,
				"isValidSysUser", params);
		if (FrameworkUtils.isSuccess(userDto)) {
			dto.setRcode(0);
			dto.setRinfo("The account is exist !");
			log.info("The account is exist !");
		} else {
			dto.setRcode(21);
			dto.setRinfo("The account is not exist !");
			log.info("The account is not  exist !");
		}
		return JSON.toJSONString(dto);
	}

	@Override
	public String updatePassword(String account, String data) {
		String jsonStr = "";
		BaseDto dto = new BaseDto();
		if (StringUtils.isNotBlank(account) && StringUtils.isNotBlank(data)) {
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
				params.put("account", StringUtils.trim(account));
				BaseObjDto<SysUser> userDto = baseDao.getObjProperty(
						SysUserMapper.class, "isValidSysUser", params);
				if (FrameworkUtils.isSuccess(userDto)) {
					SysUser user = userDto.getData();
					String passwordSalt = user.getPassword();
					password = PasswordUtils.encyptPassword(
							StringUtils.trim(account), password, passwordSalt);
					Map<String, Object> userParams = new HashMap<String, Object>();
					userParams.put("logonId", StringUtils.trim(account));
					userParams.put("password", StringUtils.trim(password));
					BaseObjDto<SysUser> userInfoDto = baseDao.getObjProperty(
							SysUserMapper.class, "getSysUserByAccount", userParams);
					if (FrameworkUtils.isSuccess(userInfoDto)) {
						user = userInfoDto.getData();
						String usernewPass = PasswordUtils.encyptPassword(
								user.getAccount(), newPassword, passwordSalt);
						SysUser updateUser = new SysUser();
						updateUser.setId(user.getId());
						updateUser.setPassword(usernewPass);
						dto = baseDao.updateByPrimaryKeySelective(
								SysUserMapper.class, updateUser);
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

	@Override
	public String updatePasswordReset(String account, String data) {
		String jsonStr = "";
		BaseObjDto<SysUser> dto = new BaseObjDto<SysUser>();
		if (StringUtils.isNotBlank(account) && StringUtils.isNotBlank(data)) {
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
				jsonStr = basebMService.checkValidCode(cellPhone, validCode);
				BaseDto basDto = JSON.parseObject(jsonStr, BaseDto.class);
				if (!FrameworkUtils.isSuccess(basDto)) {
					return jsonStr;
				}

				Map<String, Object> params = new HashMap<String, Object>();
				params.put("account", StringUtils.trim(account));
				BaseObjDto<SysUser> userDto = baseDao.getObjProperty(
						SysUserMapper.class, "isValidSysUser", params);
				if (FrameworkUtils.isSuccess(userDto)) {
					SysUser user = userDto.getData();
					String passwordSalt = user.getPassword_salt();
					String usernewPass = PasswordUtils.encyptPassword(
							StringUtils.trim(account), newPassword,
							passwordSalt);
					SysUser updateUser = new SysUser();
					updateUser.setId(user.getId());
					updateUser.setPassword(usernewPass);
					BaseDto baseDto = baseDao.updateByPrimaryKeySelective(
							SysUserMapper.class, updateUser);
					if (FrameworkUtils.isSuccess(baseDto)) {
						SysUser queryuser = new SysUser();
						queryuser.setId(updateUser.getId());
						dto.setRcode(BaseDto.SUCCESS_RCODE);
						dto.setRinfo(BaseDto.DEFAULT_SUCCESS_INFO);
						dto.setData(queryuser);
						log.info("passwordReset success!");
					} else {
						return JSON.toJSONString(baseDto);
					}
				} else {
					dto.setRcode(BaseDto.ERROR_RCODE);
					log.error("passwordReset failure!");
					dto.setRinfo("The account not exit !");
					return JSON.toJSONString(dto);
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("passwordReset exception!");
				throw new RuntimeException("passwordReset Exception!");
			}

		} else {
			log.error("---passwordReset -------- data or account is null ==== ");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	@Override
	public SysUserBo getSysUserDetailInfo(String data) {
		SysUserBo userBo = new SysUserBo();
		BaseObjDto<JSONObject> dto = new BaseObjDto<JSONObject>();
		String jsonStr = "";
		if (StringUtils.isNotBlank(data)) {
			try {
				JSONObject jp = JSON.parseObject(data);
				if (jp == null) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data parseObject to JSONObject is null !");
					userBo.setReturnJson(JSON.toJSONString(dto));
					return userBo;
				}
				String account = jp.getString("account");
				if (StringUtils.isBlank(account)) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's account is null !");
					userBo.setReturnJson(JSON.toJSONString(dto));
					return userBo;
				}
				String password = jp.getString("password");
				if (StringUtils.isBlank(password)) {
					dto.setRcode(BaseDto.ERROR_RCODE);
					dto.setRinfo("The data's password is null !");
					userBo.setReturnJson(JSON.toJSONString(dto));
					return userBo;
				}
				

				Map<String, Object> params = new HashMap<String, Object>();
				params.put("account", StringUtils.trim(account));
				BaseObjDto<SysUser> userlogonDto = baseDao.getObjProperty(
						SysUserMapper.class, "isValidSysUser", params);
				if (FrameworkUtils.isSuccess(userlogonDto)) {
					SysUser user = userlogonDto.getData();
					String passwordSalt = user.getPassword_salt();
					password = PasswordUtils.encyptPassword(
							StringUtils.trim(account), password, passwordSalt);
					Map<String, Object> userParams = new HashMap<String, Object>();
					userParams.put("account", StringUtils.trim(account));
					userParams.put("password", StringUtils.trim(password));
					BaseObjDto<SysUser> userInfoDto = baseDao.getObjProperty(
							SysUserMapper.class, "getSysUserByAccount", userParams);
					if (FrameworkUtils.isSuccess(userInfoDto)) {
						user = userInfoDto.getData();
			
						if (StringUtils.isBlank(user.getId())
								&& StringUtils.isBlank(account)) {
							dto.setRinfo("userId and account 不能都为空");
							userBo.setReturnJson(JSON.toJSONString(dto));
							return userBo;
						}
						Map<String, Object> user2Params = new HashMap<String, Object>();
						user2Params.put("account", StringUtils.trim(account));
						user2Params.put("id", StringUtils.trim(user.getId()));
						BaseObjDto<SysUser> userInfoDto2 = baseDao.getObjProperty(
								SysUserMapper.class, "getSysUserByIdOrAccountId",
								user2Params);
						if (FrameworkUtils.isSuccess(userInfoDto2)) {
							userBo.setSysUser(userInfoDto2.getData());
							JSONObject jsob = new JSONObject();
							jsob.put("id", userInfoDto2.getData().getId());
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
	
	

}
