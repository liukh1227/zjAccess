package com.zj.access.base.mapper;

import java.util.List;

import com.zj.entity.base.po.Audit;
import com.zj.entity.bm.form.AccountDetailQueryForm;

public interface AccountDetailMapper extends BaseMapper {
	public List<Audit> getAccountDetailPageList(AccountDetailQueryForm form);
}