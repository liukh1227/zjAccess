package com.zj.access.control.sys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.zj.access.service.BaseSysService;
import com.zj.base.entity.base.dto.BaseDto;
import com.zj.base.entity.base.dto.BaseObjDto;
import com.zj.entity.base.po.SysUser;
import com.zj.entity.sys.bo.SysUserBo;
import com.zj.entity.sys.form.SysUserQueryForm;

@Controller
@Scope("prototype")
@RequestMapping(value = "/sys")
public class BaseSysController extends CommonController {

	private static final Logger log = Logger.getLogger(BaseSysController.class);
	@Autowired
	private BaseSysService baseSysService;
	
	
	
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
	@RequestMapping(value = "/sysUser", method = { RequestMethod.POST })
	@ResponseBody
	public String addSysUser(HttpServletRequest request,
			HttpServletResponse response, HttpSession session,
			@RequestParam(value = "data", required = true) String data) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			if (session.getAttribute("addSysUser") != null) {
				BaseDto dto = new BaseDto();
				dto.setRcode(21);
				dto.setRinfo("请求已提交，请耐心等待!");
				return JSON.toJSONString(dto);
			}
			session.setAttribute("addSysUser", "addSysUser");
			jsonStr = baseSysService.addSysUser(data);
		} catch (Exception e) {
			log.error("addSysUser ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		session.setAttribute("addSysUser", null);
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
	@RequestMapping(value = "/sysUser/logon", method = { RequestMethod.POST })
	@ResponseBody
	public String logon(
			@RequestParam(value = "data", required = true) String data,
			HttpServletRequest request, HttpServletResponse response) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		BaseObjDto<SysUser> dto = new BaseObjDto<SysUser>();
		try {
			
			SysUserBo userBo = baseSysService.getSysUserDetailInfo(data);
			if(userBo != null){
				jsonStr = userBo.getReturnJson();
				SysUser userDto = userBo.getSysUser();
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
	@RequestMapping(value = "/sysUser/{account}/passwordUpdate", method = { RequestMethod.POST })
	@ResponseBody
	public String updatePassword(
			@PathVariable(value = "account") String account,
			@RequestParam(value = "data", required = true) String data,
			HttpServletRequest request, HttpServletResponse response) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseSysService.updatePassword(account, data);
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
	@RequestMapping(value = "/sysUser/{account}/passwordReset", method = { RequestMethod.POST })
	@ResponseBody
	public String updatePasswordReset(
			@PathVariable(value = "account") String account,
			@RequestParam(value = "data", required = true) String data,
			HttpServletRequest request, HttpServletResponse response) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseSysService.updatePasswordReset(account, data);
		} catch (Exception e) {
			log.error("passwordReset ---- 异常,message = " + e.getMessage());
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
	@RequestMapping(value = "/sysUser/{userId}", method = { RequestMethod.GET })
	@ResponseBody
	public String getUser(HttpServletRequest request,
			HttpServletResponse response,
			HttpSession session,
			@PathVariable(value = "userId") String userId) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseSysService.getSysUser(userId);
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
	@RequestMapping(value = "/sysUser/{userId}", method = { RequestMethod.POST })
	@ResponseBody
	public String updateSysUser(@PathVariable(value = "userId") String userId,
			@RequestParam(value = "data", required = true) String data,
			HttpServletRequest request, HttpServletResponse response) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseSysService.updateSysUser(userId, data);
		} catch (Exception e) {
			log.error("updateSysUser ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;
	}

	

	/**
	 * 获取用户信息列表
	 * 
	 * @author liukh
	 * @date 2017-2-13 下午3:47:40
	 */
	@RequestMapping(value = "/sysUser/list", method = { RequestMethod.GET })
	@ResponseBody
	public String getSysUserList(HttpServletRequest request,
			HttpServletResponse response, SysUserQueryForm form) {

		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseSysService.getSysUserList(form);
		} catch (Exception e) {
			log.error("getSysUserList ---- 异常,message = " + e.getMessage());
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
	@RequestMapping(value = "/sysUser/{account}/isvalid", method = { RequestMethod.GET })
	@ResponseBody
	public String isValidUser(@PathVariable(value = "account") String account,
			HttpServletRequest request, HttpServletResponse response) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseSysService.isValidSysUser(account);
		} catch (Exception e) {
			log.error("isValidUser ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;
	}


}
