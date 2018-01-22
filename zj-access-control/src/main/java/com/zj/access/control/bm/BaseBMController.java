package com.zj.access.control.bm;

import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.zj.access.control.base.CommonController;
import com.zj.access.service.BaseBMService;
import com.zj.base.entity.base.dto.BaseDto;
import com.zj.base.entity.base.dto.BaseObjDto;
import com.zj.entity.bm.bo.UserBo;
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

@Controller
@Scope("prototype")
@RequestMapping(value = "/bm")
public class BaseBMController extends CommonController {

	private static final Logger log = Logger.getLogger(BaseBMController.class);
	@Autowired
	private BaseBMService baseBMService;
	
	/**
	 * 获取手机验证
	 * 
	 * @author liukh
	 * @date 2017-3-2 上午11:05:50
	 * @param request
	 * @param response
	 * @param data
	 * @return
	 */
	@RequestMapping(value = "/user/validCode", method = { RequestMethod.POST })
	@ResponseBody
	public String getValidCode(HttpServletRequest request,
			HttpServletResponse response,HttpSession session,
			@RequestParam(value = "data", required = true) String data) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			if (session.getAttribute("getValidCode") != null) {
				BaseDto dto = new BaseDto();
				dto.setRcode(21);
				dto.setRinfo("请求已提交，请耐心等待!");
				return JSON.toJSONString(dto);
			}
			session.setAttribute("getValidCode", "getValidCode");
			jsonStr = baseBMService.addGetValidCode(data);
		} catch (Exception e) {
			log.error("getValidCode ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		log.info("getValidCode ---- jsonStr ========= " + jsonStr);
		session.setAttribute("getValidCode", null);
		return jsonStr;
	}
	
	/**
	 * 用户注册
	 * 
	 * @author liukh
	 * @date 2017-2-13 下午3:22:19
	 * @param request
	 * @param response
	 * @param data
	 * @return
	 */
	@RequestMapping(value = "/user", method = { RequestMethod.POST })
	@ResponseBody
	public String addUser(HttpServletRequest request,
			HttpServletResponse response, HttpSession session,
			@RequestParam(value = "data", required = true) String data) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			if (session.getAttribute("addUser") != null) {
				BaseDto dto = new BaseDto();
				dto.setRcode(21);
				dto.setRinfo("请求已提交，请耐心等待!");
				return JSON.toJSONString(dto);
			}
			session.setAttribute("addUser", "addUser");
			jsonStr = baseBMService.addUser(data);
		} catch (Exception e) {
			log.error("addUser ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		session.setAttribute("addUser", null);
		return jsonStr;
	}
	
	/**
	 * 登录并获取用户的详细信息
	 * 
	 * @author liukh
	 * @date 2017-3-1 下午4:10:09
	 * @param data
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/user/logon", method = { RequestMethod.POST })
	@ResponseBody
	public String logon(
			@RequestParam(value = "data", required = true) String data,
			HttpServletRequest request, HttpServletResponse response) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		BaseObjDto<UserDto> dto = new BaseObjDto<UserDto>();
		try {
			
			UserBo userBo = baseBMService.getUserDetailInfo(data);
			if(userBo != null){
				jsonStr = userBo.getReturnJson();
				UserDto userDto = userBo.getUserDto();
				if(userDto != null){
					this.setVisitor(request, userDto);
					dto.setData(userDto);
					dto.setRinfo("请求成功!");
					dto.setRcode(BaseDto.SUCCESS_RCODE);
					jsonStr = JSON.toJSONString(dto);
				}
				
			}			
		} catch (Exception e) {
			log.error("logon ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;
	}
	/**
	 * web端登录
	 * @author liukh
	 * @date 2017-3-16 上午10:43:25
	 * @param request
	 * @param response
	 * @param data
	 * @return
	 */
	
	@RequestMapping(value = "/web/user/logon", method = { RequestMethod.POST })
	@ResponseBody
	public String getUserInfoWhenLogonForPost4Web(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "data", required = true) String data) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		BaseObjDto<UserDto> dto = new BaseObjDto<UserDto>();
		
		try {
			UserBo userBo =baseBMService.getUserMoreInfoWhenLogon4Web(data);
			if(userBo != null){
				jsonStr = userBo.getReturnJson();
				UserDto userDto = userBo.getUserDto();
				if(userDto != null){
					this.setVisitor(request, userDto);
					dto.setData(userDto);
					dto.setRinfo("请求成功!");
					dto.setRcode(BaseDto.SUCCESS_RCODE);
					jsonStr = JSON.toJSONString(dto);
				}
				
				}
				
				
		} catch (Exception e) {
			log.error("getUser ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;
	}
	
	
	/**
	 * 获取详细用户信息
	 * 
	 * @author liukh
	 * @date 2017-3-2 上午10:43:03
	 * @param request
	 * @param response
	 * @param id
	 * @param logonId
	 * @return
	 */
	@RequestMapping(value = "/user", method = { RequestMethod.GET })
	@ResponseBody
	public String getUserMoreInfo(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "id", required = false) String id,
			@RequestParam(value = "logonId", required = false) String logonId) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseBMService.getUserMoreInfo(id, logonId);
		} catch (Exception e) {
			log.error("getUser ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;
	}


	/**
	 * 新增公司信息
	 * 
	 * @author liukh
	 * @date 2017-2-13 下午3:12:13
	 * @param request
	 * @param response
	 * @param data
	 * @return
	 */
	@RequestMapping(value = "/{userId}/company", method = { RequestMethod.POST })
	@ResponseBody
	public String addCompany(HttpServletRequest request,
			HttpServletResponse response,HttpSession session,
			@PathVariable(value = "userId") String userId,
			@RequestParam(value = "data", required = true) String data) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			if (session.getAttribute("addCompany") != null) {
				BaseDto dto = new BaseDto();
				dto.setRcode(21);
				dto.setRinfo("请求已提交，请耐心等待!");
				return JSON.toJSONString(dto);
			}
			session.setAttribute("addCompany", "addCompany");
			jsonStr = baseBMService.addCompanyAndUpdateUserAccountCompanyId(
					userId, data);
			
		} catch (Exception e) {
			
			log.error("addCompany ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		session.setAttribute("addCompany", null);
		return jsonStr;
	}
	
	/**
	 * 修改用户密码
	 * 
	 * @author liukh
	 * @date 2017-3-2 下午2:25:41
	 * @param logonId
	 * @param data
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/user/{logonId}/passwordUpdate", method = { RequestMethod.POST })
	@ResponseBody
	public String updatePassword(
			@PathVariable(value = "logonId") String logonId,
			@RequestParam(value = "data", required = true) String data,
			HttpServletRequest request, HttpServletResponse response) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseBMService.updatePassword(logonId, data);
		} catch (Exception e) {
			log.error("updatePassword ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;
	}

	/**
	 * 重置用户密码
	 * 
	 * @author liukh
	 * @date 2017-3-2 下午2:25:56
	 * @param logonId
	 * @param data
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/user/{logonId}/passwordReset", method = { RequestMethod.POST })
	@ResponseBody
	public String updatePasswordReset(
			@PathVariable(value = "logonId") String logonId,
			@RequestParam(value = "data", required = true) String data,
			HttpServletRequest request, HttpServletResponse response) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseBMService.updatePasswordReset(logonId, data);
		} catch (Exception e) {
			log.error("passwordReset ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;
	}

	/**
	 * 根据设备类型查询关键参数值列表
	 * @author liukh
	 * @date 2017-3-16 下午3:15:42
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/keyAttribute/deviceTypeSpecData", method = { RequestMethod.GET })
	@ResponseBody
	public String getKeAttributeDeviceTypeSpecData(HttpServletRequest request,
			HttpServletResponse response,@RequestParam(value = "deviceTypeId", required = true) String deviceTypeId) {
		String jsonStr = "";
		try {
			jsonStr = baseBMService.getKeAttributeDeviceTypeSpecData(deviceTypeId);
		} catch (Exception e) {
			log.error("getKeAttributeDeviceTypeSpecData ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		log.info("getKeAttributeDeviceTypeSpecData ---- jsonStr ========= " + jsonStr);
		return jsonStr;

	}
	

	/**
	 * 查询公司信息
	 * 
	 * @author liukh
	 * @date 2017-2-7 下午4:36:11
	 * @param request
	 * @param response
	 * @param companyId
	 * @return
	 */

	@RequestMapping(value = "/company/{companyId}", method = { RequestMethod.GET })
	@ResponseBody
	public String getCompany(HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable(value = "companyId") String companyId) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseBMService.getCompany(companyId);
		} catch (Exception e) {
			log.error("getCompany ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;
	}

	/**
	 * 更新公司信息
	 * 
	 * @author liukh
	 * @date 2017-2-7 下午4:42:08
	 * @param companyId
	 * @param data
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/company/{companyId}", method = { RequestMethod.POST })
	@ResponseBody
	public String updateCompany(
			@PathVariable(value = "companyId") String companyId,
			@RequestParam(value = "data", required = true) String data,
			HttpServletRequest request, HttpServletResponse response) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseBMService.updateCompany(companyId, data);
		} catch (Exception e) {
			log.error("updateCompany ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;
	}

	/**
	 * 获取公司信息列表
	 * 
	 * @author liukh
	 * @date 2017-2-7 下午4:42:19
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/company/list", method = { RequestMethod.GET })
	@ResponseBody
	public String getCompanyList(HttpServletRequest request,
			HttpServletResponse response, CompanyQueryForm form) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			if(form != null){
				if(StringUtils.isBlank(form.getCompanyName())){
					form.setCompanyName(null);
				}else{
					form.setCompanyName(URLDecoder.decode(form.getCompanyName(),"utf-8"));
				}
				
			}
			jsonStr = baseBMService.getCompanyList(form);
		} catch (Exception e) {
			log.error("getCompanyList ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;

	}



	

	/**
	 * 获取用户信息
	 * 
	 * @author liukh
	 * @date 2017-2-13 下午3:47:11
	 * @param request
	 * @param response
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/user/{userId}", method = { RequestMethod.GET })
	@ResponseBody
	public String getUser(HttpServletRequest request,
			HttpServletResponse response,
			HttpSession session,
			@PathVariable(value = "userId") String userId) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseBMService.getUser(userId);
		} catch (Exception e) {
			log.error("getUser ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;
	}




	/**
	 * 更新用户信息
	 * 
	 * @author liukh
	 * @date 2017-2-13 下午3:47:29
	 * @param userId
	 * @param data
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/user/{userId}", method = { RequestMethod.POST })
	@ResponseBody
	public String updateUser(@PathVariable(value = "userId") String userId,
			@RequestParam(value = "data", required = true) String data,
			HttpServletRequest request, HttpServletResponse response) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseBMService.updateUser(userId, data);
		} catch (Exception e) {
			log.error("getUserList ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;
	}

	

	/**
	 * 获取用户信息列表
	 * 
	 * @author liukh
	 * @date 2017-2-13 下午3:47:40
	 * @param request
	 * @param cellPhone
	 * @param companyId
	 * @param logonId
	 * @param userId
	 * @param userName
	 * @param numberOfItem
	 * @param page
	 * @param status
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/user/list", method = { RequestMethod.GET })
	@ResponseBody
	public String getUserList(HttpServletRequest request,
			HttpServletResponse response, UserQueryForm form) {

		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseBMService.getUserList(form);
		} catch (Exception e) {
			log.error("getUserList ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;
	}

	/**
	 * 验证登录账号是否已存在
	 * 
	 * @author liukh
	 * @date 2017-3-1 下午4:06:31
	 * @param logonId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/user/{logonId}/isvalid", method = { RequestMethod.GET })
	@ResponseBody
	public String isValidUser(@PathVariable(value = "logonId") String logonId,
			HttpServletRequest request, HttpServletResponse response) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseBMService.isValidUser(logonId);
		} catch (Exception e) {
			log.error("isValidUser ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;
	}



	/**
	 * 新增品牌信息
	 * 
	 * @author liukh
	 * @date 2017-2-15 下午2:11:01
	 * @param request
	 * @param response
	 * @param data
	 * @return
	 */
	@RequestMapping(value = "/deviceBrand", method = { RequestMethod.POST })
	@ResponseBody
	public String addDeviceBrand(HttpServletRequest request,
			HttpServletResponse response,HttpSession session,
			@RequestParam(value = "data", required = true) String data) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			if (session.getAttribute("addDeviceBrand") != null) {
				BaseDto dto = new BaseDto();
				dto.setRcode(21);
				dto.setRinfo("请求已提交，请耐心等待!");
				return JSON.toJSONString(dto);
			}
			session.setAttribute("addDeviceBrand", "addDeviceBrand");
			jsonStr = baseBMService.addDeviceBrand(data);
		} catch (Exception e) {
			log.error("addDeviceType ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		session.setAttribute("addDeviceBrand", null);
		return jsonStr;
	}

	/**
	 * 获取品牌信息
	 * 
	 * @author liukh
	 * @date 2017-2-15 下午2:15:33
	 * @param request
	 * @param response
	 * @param deviceBrandId
	 * @return
	 */
	@RequestMapping(value = "/deviceBrand/{deviceBrandId}", method = { RequestMethod.GET })
	@ResponseBody
	public String getDeviceBrand(HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable(value = "deviceBrandId") String deviceBrandId) {
		String jsonStr = baseBMService.getDeviceBrand(deviceBrandId);
		return jsonStr;
	}

	/**
	 * 更新品牌信息
	 * 
	 * @author liukh
	 * @date 2017-2-15 下午2:15:16
	 * @param deviceTypeId
	 * @param data
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/deviceBrand/{deviceBrandId}", method = { RequestMethod.POST })
	@ResponseBody
	public String updateDeviceBrand(
			@PathVariable(value = "deviceBrandId") String deviceBrandId,
			@RequestParam(value = "data", required = true) String data,
			HttpServletRequest request, HttpServletResponse response) {
		String jsonStr = baseBMService.updateDeviceBrand(deviceBrandId, data);
		return jsonStr;
	}

	/**
	 * 获取品牌信息列表
	 * 
	 * @author liukh
	 * @date 2017-2-15 下午2:20:38
	 * @param request
	 * @param brandName
	 * @param isDisplay
	 * @param numberOfItem
	 * @param page
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/deviceBrand/list", method = { RequestMethod.GET })
	@ResponseBody
	public String getDeviceBrandList(HttpServletRequest request,
			HttpServletResponse response, DeviceBrandQueryForm form) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseBMService.getDeviceBrandList(form);
		} catch (Exception e) {
			log.error("getDeviceBrandList ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;

	}

	/**
	 * 获取按顺序排列的品牌信息列表
	 * 
	 * @author liukh
	 * @date 2017-3-3 上午10:23:51
	 * @param request
	 * @param response
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "/deviceBrand/list/index", method = { RequestMethod.GET })
	@ResponseBody
	public String getDeviceBrandIndexList(HttpServletRequest request,
			HttpServletResponse response, DeviceBrandQueryForm form) {

		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseBMService.getDeviceBrandIndexList(form);
		} catch (Exception e) {
			log.error("getDeviceBrandList ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;

	}
	/**
	 * 获取按顺序排列的品牌信息列表
	 * 
	 * @author liukh
	 * @date 2017-3-3 上午10:23:51
	 * @param request
	 * @param response
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "/deviceBrand/list/exist/index", method = { RequestMethod.GET })
	@ResponseBody
	public String getExistDeviceBrandIndexList(HttpServletRequest request,
			HttpServletResponse response, DeviceBrandQueryForm form) {
		
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseBMService.getExistDeviceBrandIndexList(form);
		} catch (Exception e) {
			log.error("getExistDeviceBrandIndexList ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;
		
	}

	/**
	 * 新增设备类型
	 * 
	 * @author liukh
	 * @date 2017-2-15 下午3:30:57
	 * @param request
	 * @param response
	 * @param data
	 * @return
	 */
	@RequestMapping(value = "/deviceType", method = { RequestMethod.POST })
	@ResponseBody
	public String addDeviceType(HttpServletRequest request,
			HttpServletResponse response,HttpSession session,
			@RequestParam(value = "data", required = true) String data) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			if (session.getAttribute("addDeviceType") != null) {
				BaseDto dto = new BaseDto();
				dto.setRcode(21);
				dto.setRinfo("请求已提交，请耐心等待!");
				return JSON.toJSONString(dto);
			}
			session.setAttribute("addDeviceType", "addDeviceType");
			jsonStr = baseBMService.addDeviceType(data);
		} catch (Exception e) {
			log.error("addDeviceType ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		session.setAttribute("addDeviceType", null);
		return jsonStr;
	}

	/**
	 * 获取设备类型
	 * 
	 * @author liukh
	 * @date 2017-2-15 下午3:33:18
	 * @param request
	 * @param response
	 * @param deviceTypeId
	 * @return
	 */
	@RequestMapping(value = "/deviceType/{deviceTypeId}", method = { RequestMethod.GET })
	@ResponseBody
	public String getDeviceType(HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable(value = "deviceTypeId") String deviceTypeId) {
		String jsonStr = baseBMService.getDeviceType(deviceTypeId);
		return jsonStr;
	}

	/**
	 * 更新设备类型
	 * 
	 * @author liukh
	 * @date 2017-2-15 下午3:34:32
	 * @param deviceTypeId
	 * @param data
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/deviceType/{deviceTypeId}", method = { RequestMethod.POST })
	@ResponseBody
	public String updateDeviceType(
			@PathVariable(value = "deviceTypeId") String deviceTypeId,
			@RequestParam(value = "data", required = true) String data,
			HttpServletRequest request, HttpServletResponse response) {
		String jsonStr = baseBMService.updateDeviceType(deviceTypeId, data);
		return jsonStr;
	}

	/**
	 * 获取设备类型列表
	 * 
	 * @author liukh
	 * @date 2017-2-15 下午3:37:58
	 * @param request
	 * @param typeName
	 * @param parentId
	 * @param isDisplay
	 * @param numberOfItem
	 * @param page
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/deviceType/list", method = { RequestMethod.GET })
	@ResponseBody
	public String getDeviceTypeList(HttpServletRequest request,
			HttpServletResponse response, DeviceTypeQueryForm form) {

		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseBMService.getDeviceTypeList(form);
		} catch (Exception e) {
			log.error("getDeviceTypeList ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;

	}

	/**
	 * 新增设备型号
	 * 
	 * @author liukh
	 * @date 2017-2-16 下午2:40:06
	 * @param request
	 * @param response
	 * @param data
	 * @return
	 */
	@RequestMapping(value = "/deviceModel", method = { RequestMethod.POST })
	@ResponseBody
	public String addDeviceModel(HttpServletRequest request,
			HttpServletResponse response,HttpSession session,
			@RequestParam(value = "data", required = true) String data) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			if (session.getAttribute("addDeviceModel") != null) {
				BaseDto dto = new BaseDto();
				dto.setRcode(21);
				dto.setRinfo("请求已提交，请耐心等待!");
				return JSON.toJSONString(dto);
			}
			session.setAttribute("addDeviceModel", "addDeviceModel");
			jsonStr = baseBMService.addDeviceModel(data);
		} catch (Exception e) {
			log.error("addDeviceModel ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		session.setAttribute("addDeviceModel", null);
		return jsonStr;
	}

	/**
	 * 获取设备型号
	 * 
	 * @author liukh
	 * @date 2017-2-16 下午2:41:41
	 * @param request
	 * @param response
	 * @param deviceModelId
	 * @return
	 */
	@RequestMapping(value = "/deviceModel/{deviceModelId}", method = { RequestMethod.GET })
	@ResponseBody
	public String getDeviceModel(HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable(value = "deviceModelId") String deviceModelId) {
		String jsonStr = baseBMService.getDeviceModel(deviceModelId);
		return jsonStr;
	}

	/**
	 * 更新设备型号
	 * 
	 * @author liukh
	 * @date 2017-2-16 下午2:43:39
	 * @param deviceModelId
	 * @param data
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/deviceModel/{deviceModelId}", method = { RequestMethod.POST })
	@ResponseBody
	public String updateDeviceModel(
			@PathVariable(value = "deviceModelId") String deviceModelId,
			@RequestParam(value = "data", required = true) String data,
			HttpServletRequest request, HttpServletResponse response) {
		String jsonStr = baseBMService.updateDeviceModel(deviceModelId, data);
		return jsonStr;
	}

	/**
	 * 获取设备型号列表
	 * 
	 * @author liukh
	 * @date 2017-2-16 下午2:47:11
	 * @param request
	 * @param deviceBrandId
	 * @param deviceTypeId
	 * @param modelName
	 * @param numberOfItem
	 * @param page
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/deviceModel/list", method = { RequestMethod.GET })
	@ResponseBody
	public String getDeviceModelList(HttpServletRequest request,
			HttpServletResponse response, DeviceModelQueryForm form) {

		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseBMService.getDeviceModelList(form);
		} catch (Exception e) {
			log.error("getDeviceModelList ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;
	}

	/**
	 * 新增规格属性定义
	 * 
	 * @author liukh
	 * @date 2017-2-20 上午11:57:58
	 * @param request
	 * @param response
	 * @param data
	 * @return
	 */
	@RequestMapping(value = "/deviceTypeSpecDefinition", method = { RequestMethod.POST })
	@ResponseBody
	public String addDeviceTypeSpecDefinition(HttpServletRequest request,
			HttpServletResponse response,HttpSession session,
			@RequestParam(value = "data", required = true) String data) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			if (session.getAttribute("addDeviceTypeSpecDefinition") != null) {
				BaseDto dto = new BaseDto();
				dto.setRcode(21);
				dto.setRinfo("请求已提交，请耐心等待!");
				return JSON.toJSONString(dto);
			}
			session.setAttribute("addDeviceTypeSpecDefinition", "addDeviceTypeSpecDefinition");
			jsonStr = baseBMService.addDeviceTypeSpecDefinition(data);
		} catch (Exception e) {
			log.error("addDeviceTypeSpecDefinition ---- 异常,message = "
					+ e.getMessage());
			e.printStackTrace();
		}
		session.setAttribute("addDeviceTypeSpecDefinition", null);
		return jsonStr;
	}

	/**
	 * 获取规格属性定义
	 * 
	 * @author liukh
	 * @date 2017-2-20 下午12:00:05
	 * @param request
	 * @param response
	 * @param deviceTypeSpecDefinitionId
	 * @return
	 */
	@RequestMapping(value = "/deviceTypeSpecDefinition/{deviceTypeSpecDefinitionId}", method = { RequestMethod.GET })
	@ResponseBody
	public String getDeviceTypeSpecDefinition(
			HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable(value = "deviceTypeSpecDefinitionId") String deviceTypeSpecDefinitionId) {
		String jsonStr = baseBMService
				.getDeviceTypeSpecDefinition(deviceTypeSpecDefinitionId);
		return jsonStr;
	}

	/**
	 * 更新规格属性定义
	 * 
	 * @author liukh
	 * @date 2017-2-20 下午12:00:56
	 * @param deviceModelId
	 * @param data
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/deviceTypeSpecDefinition/{deviceTypeSpecDefinitionId}", method = { RequestMethod.POST })
	@ResponseBody
	public String updateDeviceTypeSpecDefinition(
			@PathVariable(value = "deviceTypeSpecDefinitionId") String deviceTypeSpecDefinitionId,
			@RequestParam(value = "data", required = true) String data,
			HttpServletRequest request, HttpServletResponse response) {
		String jsonStr = baseBMService.updateDeviceTypeSpecDefinition(
				deviceTypeSpecDefinitionId, data);
		return jsonStr;
	}

	/**
	 * 获取设备型号列表
	 * 
	 * @author liukh
	 * @date 2017-2-16 下午2:47:11
	 * @param request
	 * @param deviceBrandId
	 * @param deviceTypeId
	 * @param modelName
	 * @param numberOfItem
	 * @param page
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/deviceTypeSpecDefinition/list", method = { RequestMethod.GET })
	@ResponseBody
	public String getDeviceTypeSpecDefinitionList(HttpServletRequest request,
			HttpServletResponse response, DeviceTypeSpecDefinitionQueryForm form) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseBMService.getDeviceTypeSpecDefinitionList(form);
		} catch (Exception e) {
			log.error("getDeviceModelList ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;
	}

	/**
	 * 新增规格属性数据
	 * 
	 * @author liukh
	 * @date 2017-2-20 下午3:09:42
	 * @param request
	 * @param response
	 * @param data
	 * @return
	 */
	@RequestMapping(value = "/deviceTypeSpecData", method = { RequestMethod.POST })
	@ResponseBody
	public String addDeviceTypeSpecData(HttpServletRequest request,
			HttpServletResponse response,HttpSession session,
			@RequestParam(value = "data", required = true) String data) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			if (session.getAttribute("addDeviceTypeSpecData") != null) {
				BaseDto dto = new BaseDto();
				dto.setRcode(21);
				dto.setRinfo("请求已提交，请耐心等待!");
				return JSON.toJSONString(dto);
			}
			session.setAttribute("addDeviceTypeSpecData", "addDeviceTypeSpecData");
			jsonStr = baseBMService.addDeviceTypeSpecData(data);
		} catch (Exception e) {
			log.error("addDeviceTypeSpecData ---- 异常,message = "
					+ e.getMessage());
			e.printStackTrace();
		}
		session.setAttribute("addDeviceTypeSpecData", null);
		return jsonStr;
	}

	/**
	 * 获取规格属性数据
	 * 
	 * @author liukh
	 * @date 2017-2-20 下午3:11:08
	 * @param request
	 * @param response
	 * @param deviceTypeSpecDataId
	 * @return
	 */
	@RequestMapping(value = "/deviceTypeSpecData/{deviceTypeSpecDataId}", method = { RequestMethod.GET })
	@ResponseBody
	public String getDeviceTypeSpecData(
			HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable(value = "deviceTypeSpecDataId") String deviceTypeSpecDataId) {
		String jsonStr = baseBMService
				.getDeviceTypeSpecData(deviceTypeSpecDataId);
		return jsonStr;
	}

	/**
	 * 更新规格属性数据
	 * 
	 * @author liukh
	 * @date 2017-2-20 下午3:11:56
	 * @param deviceTypeSpecDefinitionId
	 * @param data
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/deviceTypeSpecData/{deviceTypeSpecDataId}", method = { RequestMethod.POST })
	@ResponseBody
	public String updateDeviceTypeSpecData(
			@PathVariable(value = "deviceTypeSpecDataId") String deviceTypeSpecDataId,
			@RequestParam(value = "data", required = true) String data,
			HttpServletRequest request, HttpServletResponse response) {
		String jsonStr = baseBMService.updateDeviceTypeSpecData(
				deviceTypeSpecDataId, data);
		return jsonStr;
	}

	/**
	 * 获取设备型号列表
	 * 
	 * @author liukh
	 * @date 2017-2-16 下午2:47:11
	 * @param request
	 * @param deviceBrandId
	 * @param deviceTypeId
	 * @param modelName
	 * @param numberOfItem
	 * @param page
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/deviceTypeSpecData/list", method = { RequestMethod.GET })
	@ResponseBody
	public String getDeviceTypeSpecDataList(HttpServletRequest request,
			HttpServletResponse response, DeviceTypeSpecDataQueryForm form) {

		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseBMService.getDeviceTypeSpecDataList(form);
		} catch (Exception e) {
			log.error("getDeviceTypeSpecDataList ---- 异常,message = "
					+ e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;
	}

	/**
	 * 新增审核信息
	 * 
	 * @author liukh
	 * @date 2017-2-21 上午10:04:41
	 * @param request
	 * @param response
	 * @param data
	 * @return
	 */
	@RequestMapping(value = "/audit", method = { RequestMethod.POST })
	@ResponseBody
	public String addAudit(HttpServletRequest request,
			HttpServletResponse response,HttpSession session,
			@RequestParam(value = "data", required = true) String data) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			if (session.getAttribute("addAudit") != null) {
				BaseDto dto = new BaseDto();
				dto.setRcode(21);
				dto.setRinfo("请求已提交，请耐心等待!");
				return JSON.toJSONString(dto);
			}
			session.setAttribute("addAudit", "addAudit");
			jsonStr = baseBMService.addAudit(data);
		} catch (Exception e) {
			log.error("addDeviceTypeSpecData ---- 异常,message = "
					+ e.getMessage());
			e.printStackTrace();
		}
		session.setAttribute("addAudit", null);
		return jsonStr;
	}

	/**
	 * 获取审核信息列表
	 * 
	 * @author liukh
	 * @date 2017-2-21 上午10:09:56
	 * @param request
	 * @param informationType
	 * @param relatedId
	 * @param numberOfItem
	 * @param page
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/audit/list", method = { RequestMethod.GET })
	@ResponseBody
	public String getAuditList(HttpServletRequest request,
			HttpServletResponse response, AuditQueryForm form) {

		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseBMService.getAuditList(form);
		} catch (Exception e) {
			log.error("getDeviceTypeSpecDataList ---- 异常,message = "
					+ e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;
	}

	/**
	 * 新增账户明细
	 * 
	 * @author liukh
	 * @date 2017-2-21 上午10:06:25
	 * @param request
	 * @param response
	 * @param data
	 * @return
	 */
	@RequestMapping(value = "/accountDetail", method = { RequestMethod.POST })
	@ResponseBody
	public String addAccountDetail(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "data", required = true) String data) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseBMService.addAccountDetail(data);
		} catch (Exception e) {
			log.error("addAccountDetail ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;
	}

	/**
	 * 获取账户明细
	 * 
	 * @author liukh
	 * @date 2017-2-21 上午10:13:18
	 * @param request
	 * @param businessType
	 * @param companyId
	 * @param expenseType
	 * @param numberOfItem
	 * @param page
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/accountDetail/list", method = { RequestMethod.GET })
	@ResponseBody
	public String getAccountDetailList(HttpServletRequest request,
			HttpServletResponse response, AccountDetailQueryForm form) {

		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseBMService.getAccountDetailList(form);
		} catch (Exception e) {
			log.error("getDeviceTypeSpecDataList ---- 异常,message = "
					+ e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;
	}
	
	/**
	 * 审核公司信息
	 * @author liukh
	 * @date 2017-3-16 下午5:19:09
	 * @param data
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/check/company", method = { RequestMethod.POST })
	@ResponseBody
	public String checkCompany(
			@RequestParam(value = "data", required = true) String data,
			HttpServletRequest request, HttpServletResponse response) {
		String jsonStr = "";
		try {
			jsonStr = baseBMService.updateCheckCompany(data);
		} catch (Exception e) {
			log.error("checkCompany ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		log.info("checkCompany ---- jsonStr ========= " + jsonStr);
		return jsonStr;

	}

}
