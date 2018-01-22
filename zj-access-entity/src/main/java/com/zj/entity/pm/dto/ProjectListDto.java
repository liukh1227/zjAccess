package com.zj.entity.pm.dto;

import com.zj.entity.base.po.Project;

public class ProjectListDto extends Project {
	
	private static final long serialVersionUID = -6505016577597011986L;
	private Long viewCount;

	public Long getViewCount() {
		return viewCount;
	}

	public void setViewCount(Long viewCount) {
		this.viewCount = viewCount;
	}
	

}
;