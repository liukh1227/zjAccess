package com.zj.access.control.pm;

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
import com.zj.access.control.base.CommonController;
import com.zj.access.service.BasePMService;
import com.zj.base.entity.base.dto.BaseDto;
import com.zj.entity.pm.form.ProjectQueryForm;

@Controller
@Scope("prototype")
@RequestMapping(value = "/pm")
public class BasePMController extends CommonController {

	private static final Logger log = Logger.getLogger(BasePMController.class);

	@Resource
	private BasePMService basePMService;

	/**
	 * 增加项目信息
	 * 
	 * @author liukh
	 * @date 2017-3-8 下午3:16:42
	 * @param request
	 * @param response
	 * @param data
	 * @return
	 */
	@RequestMapping(value = "/project", method = { RequestMethod.POST })
	@ResponseBody
	public String addProject(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "data", required = true) String data) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = basePMService.addProject(data);
		} catch (Exception e) {
			log.error("addProject ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;
	}

	/**
	 * 查询项目信息
	 * 
	 * @author liukh
	 * @date 2017-3-8 下午3:15:54
	 * @param request
	 * @param response
	 * @param projectId
	 * @return
	 */
	@RequestMapping(value = "/project/{projectId}", method = { RequestMethod.GET })
	@ResponseBody
	public String getProject(HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable(value = "projectId") String projectId) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = basePMService.getProject(projectId);
		} catch (Exception e) {
			log.error("getProject ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;
	}

	/**
	 * 更新项目信息
	 * 
	 * @author liukh
	 * @date 2017-3-8 下午3:14:29
	 * @param request
	 * @param response
	 * @param projectId
	 * @param data
	 * @return
	 */
	@RequestMapping(value = "/project/{projectId}", method = { RequestMethod.POST })
	@ResponseBody
	public String updateProject(HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable(value = "projectId") String projectId,
			@RequestParam(value = "data", required = true) String data) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = basePMService.updateProject(projectId, data);
		} catch (Exception e) {
			log.error("updateProject ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;
	}

	/**
	 * 获取项目列表
	 * 
	 * @author liukh
	 * @date 2017-3-8 下午3:12:28
	 * @param request
	 * @param response
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "/project/list", method = { RequestMethod.GET })
	@ResponseBody
	public String getProjectList(HttpServletRequest request,
			HttpServletResponse response, ProjectQueryForm form) {

		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = basePMService.getProjectContainViewCountList(form);
		} catch (Exception e) {
			log.error("getProjectList ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;
	}

}
