package com.zj.access.base.mapper;

import java.util.List;

import com.zj.entity.base.po.Company;
import com.zj.entity.bm.form.CompanyQueryForm;

public interface CompanyMapper extends BaseMapper{
	
	public  List<Company> getCompanyPageList(CompanyQueryForm form);
	
}
