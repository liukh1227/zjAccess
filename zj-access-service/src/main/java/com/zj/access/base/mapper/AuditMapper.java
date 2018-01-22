package com.zj.access.base.mapper;

import java.util.List;

import com.zj.entity.base.po.Audit;
import com.zj.entity.bm.form.AuditQueryForm;

public interface AuditMapper extends BaseMapper {
	public List<Audit> getAuditPageList(AuditQueryForm form);
}