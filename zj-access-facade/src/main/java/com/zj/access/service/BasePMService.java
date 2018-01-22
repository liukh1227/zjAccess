package com.zj.access.service;

import com.zj.entity.pm.form.ProjectQueryForm;


public interface BasePMService {

	/**
	 * 新增项目
	 * 
	 * @author five
	 * @date 2017年2月22日17:28:58
	 */
	public String addProject(String data);
 
	/**
	 * 获取项目信息
	 * 
	 * @author five
	 * @date 2017年2月23日10:43:02
	 */
	public String getProject(String projectId);

	/**
	 * 更新项目信息
	 * 
	 * @author five
	 * @date 2017年2月23日10:43:02
	 */
	public String updateProject(String projectId, String data);

	/**
	 * 获取项目列表
	 * 
	 * @author five
	 * @date 2017年2月23日10:44:10
	 */
	public String getProjectList(ProjectQueryForm form) ;
	
	public String getProjectContainViewCountList(ProjectQueryForm form);

 
	
	
}
