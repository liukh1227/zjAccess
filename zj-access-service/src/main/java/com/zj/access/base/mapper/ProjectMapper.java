package com.zj.access.base.mapper;

import java.util.List;

import com.zj.entity.base.po.Project;
import com.zj.entity.pm.dto.ProjectListDto;
import com.zj.entity.pm.form.ProjectQueryForm;


public interface ProjectMapper  extends BaseMapper {
	public List<Project> getProjectPageList(ProjectQueryForm form);
	
	public List<ProjectListDto> getProjectDeatilPageList(ProjectQueryForm form);
	
    
}