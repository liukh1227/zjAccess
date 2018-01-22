package com.zj.access.service;

import com.zj.entity.bm.bo.UserBo;
import com.zj.entity.bm.form.AccountDetailQueryForm;
import com.zj.entity.bm.form.AuditQueryForm;
import com.zj.entity.bm.form.CompanyQueryForm;
import com.zj.entity.bm.form.DeviceBrandQueryForm;
import com.zj.entity.bm.form.DeviceModelQueryForm;
import com.zj.entity.bm.form.DeviceTypeQueryForm;
import com.zj.entity.bm.form.DeviceTypeSpecDataQueryForm;
import com.zj.entity.bm.form.DeviceTypeSpecDefinitionQueryForm;
import com.zj.entity.bm.form.UserQueryForm;

public interface BaseBMService {
	/**
	 * 新增公司
	 * 
	 * @author liukh
	 * @date 2017-2-7 下午3:12:11
	 * @param data
	 * @return
	 */
	public String addCompany(String data);
	
	/**
	 * 新增公司信息，并修改用户的信息的companyId
	 * @author liukh
	 * @date 2017-3-2 下午4:52:37
	 * @param userId
	 * @param data
	 * @return
	 */
	public String addCompanyAndUpdateUserAccountCompanyId(String userId,String data);

	/**
	 * 获取公司信息
	 * 
	 * @author liukh
	 * @date 2017-2-7 下午3:12:23
	 * @param companyId
	 * @return
	 */
	public String getCompany(String companyId);

	/**
	 * 更新公司信息
	 * 
	 * @author liukh
	 * @date 2017-2-7 下午3:12:34
	 * @param companyId
	 * @param data
	 * @return
	 */
	public String updateCompany(String companyId, String data);

	/**
	 * 获取公司信息列表
	 * 
	 * @author liukh
	 * @date 2017-2-7 下午4:24:52
	 * @param companyBusinessType
	 * @param companyName
	 * @param companyType
	 * @param numberOfItem
	 * @param page
	 * @param status
	 * @return
	 */
	public String getCompanyList(CompanyQueryForm form);
	/**
	 * 获取验证码
	 * @author liukh
	 * @date 2017-3-2 上午10:46:26
	 * @param phone
	 * @return
	 */
	
	public String addGetValidCode(String data);

	/**
	 * 校验手机验证码是否正确
	 * @author liukh
	 * @date 2017-3-2 上午11:37:07
	 * @param cellPhone
	 * @param validCode
	 * @return
	 */
	public String checkValidCode(String cellPhone,String validCode);
	/**
	 * 新增用户信息
	 * 
	 * @author liukh
	 * @date 2017-2-13 下午2:22:32
	 * @param data
	 * @return
	 */
	public String addUser(String data);

	/**
	 * 获取用户信息
	 * 
	 * @author liukh
	 * @date 2017-2-13 下午2:23:12
	 * @param userId
	 * @return
	 */

	public String getUser(String userId);
	
/**
 * 根据用户Id或账号获取用户信息
 * @author liukh
 * @date 2017-3-2 上午9:58:07
 * @param userId
 * @param logonId
 * @return
 */
	public String getUserMoreInfo(String userId,String logonId);
	
	/**
	 * 登录并获取用户的详细信息
	 * @author liukh
	 * @date 2017-3-15 下午3:28:35
	 * @param data
	 * @return
	 */
	public UserBo getUserDetailInfo(String data);
	
	/**
	 * web登录并返回用户的详细信息
	 * @author liukh
	 * @date 2017-3-2 下午5:30:09
	 * @param data
	 * @return
	 */
	public UserBo getUserMoreInfoWhenLogon4Web(String data);

	/**
	 * 更新用户信息
	 * 
	 * @author liukh
	 * @date 2017-2-13 下午2:23:25
	 * @param userId
	 * @param data
	 * @return
	 */
	public String updateUser(String userId, String data);

	/**
	 * 获取用户列表
	 * 
	 * @author liukh
	 * @date 2017-2-13 下午2:30:45
	 * @param cellPhone
	 * @param companyId
	 * @param logonId
	 * @param userId
	 * @param userName
	 * @param numberOfItem
	 * @param page
	 * @param status
	 * @return
	 */
	public String getUserList(UserQueryForm form);
	
	/**
	 * 验证登录账号是否已存在
	 * @author liukh
	 * @date 2017-3-1 下午3:56:08
	 * @param logonId
	 * @return
	 */
	public String isValidUser(String logonId);
	
	/**
	 * 修改密码
	 * @author liukh
	 * @date 2017-3-2 上午11:47:37
	 * @param logonId
	 * @param data 
	 * @param data：{oldPassword,newPassword,newAgainPassword}
	 * @return
	 */
	public String updatePassword(String logonId,String data);
	
	/**
	 * 重置密码
	 * @author liukh
	 * @date 2017-3-2 下午2:06:32
	 * @param data
	 * @param logonId
	 * @return
	 */
	public String updatePasswordReset(String logonId,String data);
	
	
	/**
	 * 新增品牌信息
	 * 
	 * @author liukh
	 * @date 2017-2-14 下午3:56:16
	 * @param data
	 * @return
	 */
	public String addDeviceBrand(String data);

	/**
	 * 修改品牌信息
	 * 
	 * @author liukh
	 * @date 2017-2-14 下午3:56:38
	 * @param deviceBrandId
	 * @param data
	 * @return
	 */
	public String updateDeviceBrand(String deviceBrandId, String data);

	/**
	 * 获取品牌信息
	 * 
	 * @author liukh
	 * @date 2017-2-14 下午3:56:49
	 * @param deviceBrandId
	 * @return
	 */
	public String getDeviceBrand(String deviceBrandId);

	/**
	 * 获取品牌信息列表
	 * 
	 * @author liukh
	 * @date 2017-2-14 下午3:56:58
	 * @param numberOfItem
	 * @param page
	 * @param brandName
	 * @param isDisplay
	 * @return
	 */
	public String getDeviceBrandList(DeviceBrandQueryForm form);
	
	/**
	 * 获取按顺序排序的品牌信息列表
	 * @author liukh
	 * @date 2017-3-3 上午10:25:12
	 * @param form
	 * @return
	 */
	public String getDeviceBrandIndexList(DeviceBrandQueryForm form);
	
	/**
	 * 获取顺序排序的品牌信息列表且产品中存在的品牌
	 * @author liukh
	 * @date 2017-5-5 下午6:44:05
	 * @param form
	 * @return
	 */
	public String getExistDeviceBrandIndexList(DeviceBrandQueryForm form);

	/**
	 * 新增设备类型
	 * 
	 * @author liukh
	 * @date 2017-2-15 下午2:49:28
	 * @param data
	 * @return
	 */
	public String addDeviceType(String data);

	/**
	 * 修改设备类型
	 * 
	 * @author liukh
	 * @date 2017-2-15 下午2:51:17
	 * @param deviceTypeId
	 * @param data
	 * @return
	 */
	public String updateDeviceType(String deviceTypeId, String data);

	/**
	 * 获取设备类型
	 * 
	 * @author liukh
	 * @date 2017-2-15 下午2:52:02
	 * @param deviceTypeId
	 * @return
	 */
	public String getDeviceType(String deviceTypeId);

	/**
	 * 获取设备类型列表
	 * 
	 * @author liukh
	 * @date 2017-2-15 下午2:55:19
	 * @param numberOfItem
	 * @param page
	 * @param typeName
	 * @param parentId
	 * @param isDisplay
	 * @return
	 */
	public String getDeviceTypeList(DeviceTypeQueryForm form);

	/**
	 * 新增设备类型规格属性
	 * 
	 * @author liukh
	 * @date 2017-2-17 下午4:33:23
	 * @param data
	 * @return
	 */
	public String addDeviceTypeSpecDefinition(String data);

	/**
	 * 修改设备类型规格属性
	 * 
	 * @author liukh
	 * @date 2017-2-17 下午4:34:10
	 * @param deviceTypeId
	 * @param data
	 * @return
	 */
	public String updateDeviceTypeSpecDefinition(
			String deviceTypeSpecDefinitionId, String data);

	/**
	 * 获取设备类型规格属性
	 * 
	 * @author liukh
	 * @date 2017-2-17 下午4:34:29
	 * @param deviceTypeId
	 * @return
	 */
	public String getDeviceTypeSpecDefinition(String deviceTypeSpecDefinitionId);

	/**
	 * 获取设备类型规格属性列表
	 * 
	 * @author liukh
	 * @date 2017-2-17 下午4:37:55
	 * @param numberOfItem
	 * @param page
	 * @param attributeName
	 * @param deviceTypeId
	 * @param isDisplay
	 * @param isKeyAttribute
	 * @return
	 */
	public String getDeviceTypeSpecDefinitionList(DeviceTypeSpecDefinitionQueryForm form);

	/**
	 * 新增设备类型规格属性数据
	 * 
	 * @author liukh
	 * @date 2017-2-20 下午2:09:15
	 * @param data
	 * @return
	 */
	public String addDeviceTypeSpecData(String data);

	/**
	 * 修改设备类型规格属性数据
	 * 
	 * @author liukh
	 * @date 2017-2-20 下午2:09:52
	 * @param deviceTypeSpecDefinitionId
	 * @param data
	 * @return
	 */
	public String updateDeviceTypeSpecData(String deviceTypeSpecDataId,
			String data);

	/**
	 * 获取设备类型规格属性数据
	 * 
	 * @author liukh
	 * @date 2017-2-20 下午2:10:48
	 * @param deviceTypeSpecDataId
	 * @return
	 */
	public String getDeviceTypeSpecData(String deviceTypeSpecDataId);

	/**
	 * 获取设备类型规格属性数据列表
	 * 
	 * @author liukh
	 * @date 2017-2-20 下午2:16:51
	 * @param numberOfItem
	 * @param page
	 * @param definitionId
	 * @param deviceTypeId
	 * @return
	 */
	public String getDeviceTypeSpecDataList(DeviceTypeSpecDataQueryForm form);

	/**
	 * 新增设备型号
	 * 
	 * @author liukh
	 * @date 2017-2-16 上午11:52:59
	 * @param data
	 * @return
	 */
	public String addDeviceModel(String data);

	/**
	 * 修改设备型号
	 * 
	 * @author liukh
	 * @date 2017-2-16 上午11:53:30
	 * @param deviceModelId
	 * @param data
	 * @return
	 */
	public String updateDeviceModel(String deviceModelId, String data);

	/**
	 * 获取设备型号
	 * 
	 * @author liukh
	 * @date 2017-2-16 上午11:54:16
	 * @param deviceModelId
	 * @return
	 */
	public String getDeviceModel(String deviceModelId);

	/**
	 * 获取设备型号列表
	 * 
	 * @author liukh
	 * @date 2017-2-16 上午11:55:33
	 * @param numberOfItem
	 * @param page
	 * @param deviceBrandId
	 * @param deviceTypeId
	 * @param modelName
	 * @return
	 */
	public String getDeviceModelList(DeviceModelQueryForm form);

	/**
	 * 新增审核信息
	 * 
	 * @author liukh
	 * @date 2017-2-20 下午4:54:08
	 * @param data
	 * @return
	 */
	public String addAudit(String data);
	
	/**
	 * 
	 * @author liukh
	 * @date 2017-5-8 下午3:42:23
	 * @param data
	 * @return
	 */
	public String addOrUpdateAudit(String data);

	/**
	 * 获取审核信息列表
	 * 
	 * @author liukh
	 * @date 2017-2-20 下午4:57:27
	 * @param numberOfItem
	 * @param page
	 * @param informationType
	 * @param relatedId
	 * @return
	 */
	public String getAuditList(AuditQueryForm form);

	/**
	 * 新增账户明细
	 * 
	 * @author liukh
	 * @date 2017-2-20 下午5:41:11
	 * @param data
	 * @return
	 */
	public String addAccountDetail(String data);

	/**
	 * 获取账户明细列表
	 * 
	 * @author liukh
	 * @date 2017-2-20 下午5:42:40
	 * @param numberOfItem
	 * @param page
	 * @param businessType
	 * @param companyId
	 * @param expenseType
	 * @return
	 */
	public String getAccountDetailList(AccountDetailQueryForm form);
	
	/**
	 * 根据设备类型查询关键参数值列表
	 * @author liukh
	 * @date 2017-3-16 下午2:44:01
	 * @param deviceTypeId
	 * @return
	 */
	public String getKeAttributeDeviceTypeSpecData(String deviceTypeId);
	
	/**
	 * 审核公司信息
	 * @author liukh
	 * @date 2017-3-16 下午3:39:16
	 * @param data
	 * @return
	 */
	public String updateCheckCompany(String data);

}
