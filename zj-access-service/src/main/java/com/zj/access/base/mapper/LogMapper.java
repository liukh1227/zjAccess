package com.zj.access.base.mapper;

import java.util.List;

import com.zj.entity.base.po.Log;
import com.zj.entity.sm.form.LogQueryForm;

public interface LogMapper extends BaseMapper {
	public  List<Log> getLogPageList(LogQueryForm form);
}