package com.zj.access.service;

import java.util.Map;

import com.zj.entity.dm.form.CompanyAvailableDeviceQueryForm;
import com.zj.entity.dm.form.CompanyDeviceTypeQueryForm;
import com.zj.entity.dm.form.DeviceQueryForm;
import com.zj.entity.dm.form.DeviceStatusTraceQueryForm;

public interface BaseDMService {

	/**
	 * 新增设备信息
	 * 
	 * @author liukh
	 * @date 2017-2-21 上午10:37:15
	 * @param data
	 * @return
	 */
	public String addDevice(String data);

	/**
	 * 获取设备信息
	 * 
	 * @author liukh
	 * @date 2017-2-21 上午10:38:13
	 * @param deviceId
	 * @return
	 */
	public String getDevice(String deviceId);

	/**
	 * 更新设备信息
	 * 
	 * @author liukh
	 * @date 2017-2-21 上午10:38:44
	 * @param deviceId
	 * @param data
	 * @return
	 */
	public String updateDevice(String deviceId, String data);

	/**
	 * 删除设备信息
	 * 
	 * @author liukh
	 * @date 2017-2-21 上午10:42:21
	 * @param deviceId
	 * @return
	 */
	public String deleteDevice(String deviceId);

	/**
	 * 获取设备信息列表
	 * 
	 * @author liukh
	 * @date 2017-2-21 下午2:59:42
	 * @param companyId
	 * @param deviceBrand
	 * @param deviceId
	 * @param deviceModel
	 * @param deviceName
	 * @param deviceType
	 * @param startDate
	 * @param endDate
	 * @param isImported
	 * @param isRealDevice
	 * @param manufactureYear
	 * @param realDeviceId
	 * @param sequenceNum
	 * @param workingTime
	 * @param status
	 * @param numberOfItem
	 * @param page
	 * @return
	 */
	public String getDeviceList(DeviceQueryForm form);

	/**
	 * 新增设备跟踪记录信息
	 * 
	 * @author liukh
	 * @date 2017-2-21 上午10:37:15
	 * @param data
	 * @return
	 */
	public String addDeviceStatusTrace(String data);

	/**
	 * 删除设备跟踪记录信息
	 * 
	 * @author liukh
	 * @date 2017-2-21 上午10:42:21
	 * @param deviceStatusTraceId
	 * @return
	 */
	public String deleteDeviceStatusTrace(String deviceStatusTraceId);

	/**
	 * 获取设备跟踪记录信息列表
	 * 
	 * @author liukh
	 * @date 2017-2-21 下午3:05:01
	 * @param deviceId
	 * @param deviceName
	 * @param orderId
	 * @param numberOfItem
	 * @param page
	 * @return
	 */
	public String getDeviceStatusTraceList(DeviceStatusTraceQueryForm form);
	
	/**
	 * 新增公司设备信息
	 * @author liukh
	 * @date 2017-2-21 下午3:06:47
	 * @param data
	 * @return
	 */
	public String addCompanyDeviceType(String data);

	/**
	 * 获取公司设备信息列表
	 * @author liukh
	 * @date 2017-2-21 下午3:07:36
	 * @param companyDeviceTypeId
	 * @return
	 */
	public String getCompanyDeviceType(String companyDeviceTypeId);

/**
 * 更新公司设备信息列表
 * @author liukh
 * @date 2017-2-21 下午3:08:14
 * @param companyDeviceTypeId
 * @param data
 * @return
 */
	public String updateCompanyDeviceType(String companyDeviceTypeId, String data);

	/**
	 * 删除公司设备信息
	 * @author liukh
	 * @date 2017-2-21 下午3:08:56
	 * @param companyDeviceTypeId
	 * @return
	 */
	public String deleteCompanyDeviceType(String companyDeviceTypeId);
	
	/**
	 * 获取公司设备信息列表
	 * @author liukh
	 * @date 2017-2-21 下午5:53:33
	 * @param companyId
	 * @param deviceModelId
	 * @param numberOfItem
	 * @param page
	 * @return
	 */
	public String getCompanyDeviceTypeList(CompanyDeviceTypeQueryForm form);
	
	/**
	 * 获取可租设备信息列表
	 * @author liukh
	 * @date 2017-3-7 下午10:08:16
	 * @param form
	 * @return
	 */
	public String getCompanyAvailableDeviceList(CompanyAvailableDeviceQueryForm form);
	/**
	 * 获取可租信息
	 * @author liukh
	 * @date 2017-3-7 下午10:08:39
	 * @param companyId
	 * @param deviceModelId
	 * @return
	 */
	public String getCompanyAvailableDeviceInfor(String companyId,String deviceModelId);
	/**
	 * 以半径查询附近可租的机械设备
	 * @author liukh
	 * @date 2017-3-16 下午6:45:18
	 * @param replaceParams
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public String getNearCompanyAvailableDeviceListInRadius(Map replaceParams);
	/**
	 * 以手机屏幕查询附近可租机械设备
	 * @author liukh
	 * @date 2017-3-17 上午11:17:56
	 * @param replaceParams
	 * @return
	 */
	
	@SuppressWarnings("rawtypes")
	public String getNearCompanyAvailableDeviceListInScreen(Map replaceParams);;
	
	/**
	 * 增加或者更新公司设备型号
	 * @author liukh
	 * @date 2017-3-17 下午2:47:53
	 * @param data
	 * @return
	 */
	public String addCompanyDeviceTypeOrUpdaeWhenExist(String data);
	/**
	 * 修改公司设备的价格或图片
	 * @author liukh
	 * @date 2017-3-17 下午3:23:17
	 * @param data
	 * @return
	 */
	public String updateCompanyDeviceTypebyCompanyIdAndModelId(String data);
	/**
	 * 增加设备的时候，更新设备公司型号的图片
	 * @author liukh
	 * @date 2017-3-17 下午3:49:35
	 * @param companyDeviceTypeId
	 * @param data
	 * @return
	 */
	public String addDeviceAndInserPictureToCompanyDevice(String companyDeviceTypeId,String data);
	
	/**
	 * 筛选公司可租设备信息
	 * @author liukh
	 * @date 2017-3-17 下午4:39:57
	 * @param form
	 * @return
	 */
	public String getCompanyAvailableDeviceListFiltrate(CompanyAvailableDeviceQueryForm form);
	
	/**
	 * 获取状态为待租状态的设备
	 * @author liukh
	 * @date 2017-3-17 下午4:59:55
	 * @param form
	 * @return
	 */
	public String getWaitWorkingDeviceList(DeviceQueryForm form);
	/**
	 * 获取设备的详细信息（价格，审核信息）
	 * @author liukh
	 * @date 2017-3-17 下午5:54:06
	 * @param form
	 * @return
	 */
	public String getDeviceContainPriceAndAuditList(DeviceQueryForm form);
	
	/**
	 * 获取报价
	 * @author liukh
	 * @date 2017-3-17 下午6:02:41
	 * @param companyId
	 * @param deviceModelId
	 * @return
	 */
	public String getModelQuoteByCompanyIdAndModelIdInCompanyDeviceType(String companyId,String  deviceModelId);
	/**
	 * 获取公司设备不同状态的统计信息
	 * @author liukh
	 * @date 2017-3-20 上午9:54:05
	 * @param companyId
	 * @return
	 */
	public String getDeviceStatisticsCount(String companyId);
	/**
	 * 审核设备
	 * @author liukh
	 * @date 2017-3-20 上午11:59:15
	 * @param data
	 * @return
	 */
	public String updateCheckDevice(String data);
	
	/**
	 * 增加机器校验信息
	 * @author liukh
	 * @date 2018-1-23 上午11:22:27
	 * @param data
	 * @return
	 */
	public String addDeviceInspection(String data);
	/**
	 * 修改机器校验信息
	 * @author liukh
	 * @date 2018-1-23 上午11:24:09
	 * @param inspectionId
	 * @param data
	 * @return
	 */
	public String updateDeviceInspection(String inspectionId,String data);
}
