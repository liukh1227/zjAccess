package com.zj.access.service;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.zj.access.base.dao.IBaseDao;
import com.zj.access.base.mapper.CompanyDeviceTypeMapper;
import com.zj.access.base.mapper.CompanyMapper;
import com.zj.access.base.mapper.UserMapper;
import com.zj.access.common.FrameworkUtils;
import com.zj.base.common.ItemPage;
import com.zj.base.entity.base.dto.BaseObjDto;
import com.zj.entity.base.po.Company;
import com.zj.entity.base.po.CompanyDeviceType;
import com.zj.entity.base.po.User;
import com.zj.entity.bm.dto.UserDto;
import com.zj.entity.bm.form.UserQueryForm;
import com.zj.entity.dm.form.CompanyDeviceTypeQueryForm;
@Service("baseOtherService")
@Scope("prototype")
public class BaseOtherServiceImpl implements  BaseOtherService{
	private static final Logger log = Logger.getLogger(BaseBMServiceImpl.class);
	@Autowired
	private IBaseDao baseDao;
	@Override
	public UserDto getUserDtoByComapnyId(String companyId) {
		UserDto userDto = null;
		try {
			UserQueryForm form = new UserQueryForm();
			form.setCompanyId(companyId);
			form.setPageSize(100);
			BaseObjDto<ItemPage<User>> pagesDto = baseDao.getPageList(
					UserMapper.class, User.class, form, "getUserPageList");
			if (FrameworkUtils.isSuccess(pagesDto) && pagesDto.getData()!= null && pagesDto.getData().getItems() != null &&  pagesDto.getData().getItems().size()>0) {
				User user = pagesDto.getData().getItems().get(0);
				userDto = new UserDto();
				userDto.copyFromUser(user);
				BaseObjDto<Company> companyDto = baseDao.selectByPrimaryKey(
						CompanyMapper.class, StringUtils.trim(companyId));
				if (FrameworkUtils.isSuccess(companyDto)) {
					Company company = companyDto.getData();
					userDto.setCompanyName(company.getCompanyName());
				}
				log.info("getUserDtoByComapnyId success");
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userDto;
	}
	@Override
	public CompanyDeviceType getCompanyDeviceTypeByComapnyIdAndModelId(
			String companyId, String modelId) {
		CompanyDeviceType companyDeviceType = null;
		try {
			if(StringUtils.isNotBlank(companyId) && StringUtils.isNoneBlank(modelId)){
				CompanyDeviceTypeQueryForm form = new CompanyDeviceTypeQueryForm();
				form.setCompanyId(companyId);
				form.setDeviceModelId(modelId);
				BaseObjDto<ItemPage<CompanyDeviceType>> pagesDto = baseDao.getPageList(CompanyDeviceTypeMapper.class,CompanyDeviceType.class, form,"getCompanyDeviceTypePageList");
				if (FrameworkUtils.isSuccess(pagesDto)) {
					if(pagesDto.getData()!= null && pagesDto.getData().getItems()!= null && pagesDto.getData().getItems().size()>0){
						companyDeviceType = pagesDto.getData().getItems().get(0);
						log.info("getCompanyDeviceTypeList success");
					}
					
				}
			}
		
	
		} catch (Exception e) {
			e.printStackTrace();
			log.error("getCompanyDeviceTypeList exception!");
			throw new RuntimeException("getCompanyDeviceTypeList Exception!");

		}
		return companyDeviceType;
	}
	
	

}
