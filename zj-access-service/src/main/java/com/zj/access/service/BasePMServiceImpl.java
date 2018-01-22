package com.zj.access.service;

import java.util.Date;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSON;
import com.zj.access.base.dao.IBaseDao;
import com.zj.access.base.mapper.ProjectMapper;
import com.zj.access.common.FrameworkUtils;
import com.zj.base.common.ItemPage;
import com.zj.base.entity.base.dto.BaseDto;
import com.zj.base.entity.base.dto.BaseObjDto;
import com.zj.entity.base.po.Project;
import com.zj.entity.pm.dto.ProjectListDto;
import com.zj.entity.pm.form.ProjectQueryForm;

@Service("basePMService")
@Scope("prototype")
public class BasePMServiceImpl implements BasePMService {
	private static final Logger log = Logger.getLogger(BasePMServiceImpl.class);
	@Resource
	private IBaseDao baseDao;

	/**
	 * 新增项目
	 * 
	 * @author five
	 * @date 2017年2月22日17:31:17
	 */
	@Override
	public String addProject(String data) {
		String jsonStr = "";
		BaseDto dto = new BaseDto();
		if (StringUtils.isNotBlank(data)) {
			try {
				Project project = JSON.parseObject(data, Project.class);
				if (project == null) {
					dto.setRcode(1);
					dto.setRinfo("The data parseObject to Project is null !");
					return JSON.toJSONString(dto);
				}
				if (project.getTheme() == null) {
					dto.setRcode(1);
					dto.setRinfo("The data's  theme is null !");
					return JSON.toJSONString(dto);
				}
				if (project.getContent() == null) {
					dto.setRcode(1);
					dto.setRinfo("The data's  content is null !");
					return JSON.toJSONString(dto);
				}
				Date createTime = new Date();
				project.setCreateTime(createTime);
				dto = baseDao.insertSelective(ProjectMapper.class, project);
				if (FrameworkUtils.isSuccess(dto)) {
					log.info("addProject success!");
				} else {
					log.error("addProject failure!");
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("addProject exception!");
				throw new RuntimeException("addProject Exception!");
			}
		} else {
			log.error("---addProject -------- data is null ==== ");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 获取项目信息
	 * 
	 * @author liukh
	 * @date 2017-3-8 下午2:20:45
	 * @param projectId
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BasePMService#getProject(java.lang.String)
	 */
	@Override
	public String getProject(String projectId) {
		String jsonStr = "";
		BaseObjDto<Project> dto = new BaseObjDto<Project>();
		try {
			if (StringUtils.isBlank(projectId)) {
				dto.setRinfo("projectId is null");
				return JSON.toJSONString(dto);
			}
			BaseObjDto<Project> projectDto = baseDao.selectByPrimaryKey(
					ProjectMapper.class, StringUtils.trim(projectId));
			if (FrameworkUtils.isSuccess(projectDto)) {
				Project project = projectDto.getData();
				dto.setData(project);
				FrameworkUtils.setSuccess(dto);
				log.info("getProject success!");
			} else {
				FrameworkUtils.setNoData(dto);
				log.error("getProject failure");
			}
		} catch (Exception e) {
			log.error("getProject exception!");
			e.printStackTrace();
			throw new RuntimeException("getProject Exception!");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 更新项目信息
	 * 
	 * @author liukh
	 * @date 2017-3-8 下午2:15:29
	 * @param projectId
	 * @param data
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BasePMService#updateProject(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public String updateProject(String projectId, String data) {
		String jsonStr = "";
		BaseDto dto = new BaseDto();
		if (StringUtils.isNotBlank(projectId) && StringUtils.isNotBlank(data)) {
			try {
				Project project = JSON.parseObject(data, Project.class);
				if (project == null) {
					dto.setRcode(1);
					dto.setRinfo("The data parseObject to Project is null !");
					return JSON.toJSONString(dto);
				}

				project.setId(projectId);
				dto = baseDao.updateByPrimaryKeySelective(ProjectMapper.class,
						project);
				if (FrameworkUtils.isSuccess(dto)) {
					log.info("updateProject success!");
				} else {
					log.error("updateProject failure!");
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("updateProject exception!");
				throw new RuntimeException("updateProject Exception!");
			}

		} else {
			log.error("---updateProject -------- data or projectId is null ==== ");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	/**
	 * 获取项目信息列表
	 * 
	 * @author liukh
	 * @date 2017-3-8 下午2:11:32
	 * @param form
	 * @return (non-Javadoc)
	 * @see com.zj.access.service.BasePMService#getProjectList(com.zj.entity.pm.form.ProjectQueryForm)
	 */
	@Override
	public String getProjectList(ProjectQueryForm form) {
		String jsonStr = "";
		BaseObjDto<ItemPage<Project>> dto = new BaseObjDto<ItemPage<Project>>();
		try {
			BaseObjDto<ItemPage<Project>> pagesDto = baseDao.getPageList(
					ProjectMapper.class, Project.class, form,
					"getProjectPageList");
			if (FrameworkUtils.isSuccess(pagesDto)) {
				dto = pagesDto;
				log.info("getProjectList success");
			} else {
				FrameworkUtils.setNoData(dto);
				log.error("getProjectList failure");
				throw new RuntimeException("getProjectList Exception!");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("getProjectList exception!");
			throw new RuntimeException("getProjectList Exception!");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}
/**
 * 获取详细信息的项目信息列表
 * @author liukh
 * @date 2017-3-29 下午2:37:46 
 * @param form
 * @return
 * (non-Javadoc)
 * @see com.zj.access.service.BasePMService#getProjectContainViewCountList(com.zj.entity.pm.form.ProjectQueryForm)
 */
	@Override
	public String getProjectContainViewCountList(ProjectQueryForm form) {
		String jsonStr = "";
		BaseObjDto<ItemPage<ProjectListDto>> dto = new BaseObjDto<ItemPage<ProjectListDto>>();
		try {
			BaseObjDto<ItemPage<ProjectListDto>> pagesDto = baseDao.getPageList(
					ProjectMapper.class, ProjectListDto.class, form,"getProjectDeatilPageList");
			if (FrameworkUtils.isSuccess(pagesDto)) {
				dto = pagesDto;
				log.info("getProjectContainViewCountList success");
			} else {
				FrameworkUtils.setNoData(dto);
				log.error("getProjectContainViewCountList failure");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("getProjectContainViewCountList exception!");
			throw new RuntimeException("getProjectContainViewCountList Exception!");
		}
		jsonStr = JSON.toJSONString(dto);
		return jsonStr;
	}

	
	
}
