package com.zj.access.control.dm;

import java.net.URLDecoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.zj.access.common.ToolUtils;
import com.zj.access.control.base.CommonController;
import com.zj.access.service.BaseDMService;
import com.zj.base.entity.base.dto.BaseDto;
import com.zj.entity.dm.form.CompanyAvailableDeviceQueryForm;
import com.zj.entity.dm.form.DeviceQueryForm;
import com.zj.entity.dm.form.DeviceStatusTraceQueryForm;

@Controller
@Scope("prototype")
@RequestMapping(value = "/dm")
public class BaseDMController extends CommonController {

	private static final Logger log = Logger.getLogger(BaseDMController.class);
	@Autowired
	private BaseDMService baseDMService;
	/**
	 * 可租设备信息列表
	 * @author liukh
	 * @date 2017-3-7 下午10:41:25
	 * @param request
	 * @param response
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "/companyAvailableDeviceList", method = { RequestMethod.GET })
	@ResponseBody
	public String getCompanyAvailableDeviceList(HttpServletRequest request,HttpServletResponse response,CompanyAvailableDeviceQueryForm form) {
	
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			if(form != null){
				if(StringUtils.isBlank(form.getAddress())){
					form.setAddress(null);
				}else{
					form.setAddress(URLDecoder.decode(form.getAddress(),"utf-8"));
				}
				if(StringUtils.isBlank(form.getDeviceModelName())){
					form.setDeviceModelName(null);
				}else{
					form.setDeviceModelName(URLDecoder.decode(form.getDeviceModelName(),"utf-8"));
				}
				
			}
			jsonStr = baseDMService.getCompanyAvailableDeviceList(form);
		} catch (Exception e) {
			log.error("getCompanyAvailableDeviceList ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;

	}
	/**
	 * 可租信息详细信息
	 * @author liukh
	 * @date 2017-3-7 下午10:45:06
	 * @param request
	 * @param response
	 * @param companyId
	 * @param deviceModelId
	 * @return
	 */
	@RequestMapping(value = "/companyAvailableDeviceInfo", method = { RequestMethod.GET })
	@ResponseBody
	public String getCompanyAvailableDeviceInfor(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "companyId", required = true) String companyId,
			@RequestParam(value = "deviceModelId", required = true) String deviceModelId) {
	
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseDMService.getCompanyAvailableDeviceInfor(companyId, deviceModelId);
		} catch (Exception e) {
			log.error("getCompanyAvailableDeviceInfor ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;

	}
	
	/**
	 * 获取半径范围内的可租设备
	 * @author liukh
	 * @date 2016-11-25 下午5:05:13
	 * @param request
	 * @param response
	 * @param myLongitude
	 * @param myLatitude
	 * @param distanceCustom
	 * @param deviceTypeId or parentDeviceTypeId
	 * @return
	 */
	@SuppressWarnings({ "rawtypes"})
	@RequestMapping(value = "/near/radius/companyAvailableDeviceList", method = {RequestMethod.GET})
	@ResponseBody
	public String getNearCompanyAvailableDeviceListInRadius(HttpServletRequest request,HttpServletResponse response) {
		String jsonStr = "";
		try {
			Map replaceMap = ToolUtils.getParams(request);
			jsonStr = baseDMService.getNearCompanyAvailableDeviceListInRadius(replaceMap);
		} catch (Exception e) {
			log.error("nearWashAreaList ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		log.info("nearWashAreaList ---- jsonStr ========= " + jsonStr);
		return jsonStr;		
	}
	/**
	 * 获取屏幕范围内的可租设备
	 * @author liukh
	 * @date 2016-12-1 上午10:57:11
	 * @param request
	 * @param myLongitude
	 * @param myLatitude
	 * @param leftLongitude
	 * @param leftLatitude
	 * @param rightLongitude
	 * @param rightLatitude
	 * @param deviceTypeId or parentDeviceTypeId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/near/screen/companyAvailableDeviceList", method = {RequestMethod.GET})
	@ResponseBody
	public String getNearCompanyAvailableDeviceListInScreen(HttpServletRequest request,HttpServletResponse response) {
		String jsonStr = "";
		try {
			Map replaceMap = ToolUtils.getParams(request);
			jsonStr = baseDMService.getNearCompanyAvailableDeviceListInScreen(replaceMap);
		} catch (Exception e) {
			log.error("nearWashAreaList ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		log.info("nearWashAreaList ---- jsonStr ========= " + jsonStr);
		return jsonStr;		
	}

	/**
	 * 增加或者更新公司设备信息
	 * 
	 * @author liukh
	 * @date 2017-2-22 下午2:23:50
	 * @param request
	 * @param response
	 * @param data
	 * @return
	 */
	@RequestMapping(value = "/companyDeviceType", method = { RequestMethod.POST })
	@ResponseBody
	public String addCompanyDeviceType(HttpServletRequest request,
			HttpServletResponse response,HttpSession session,
			@RequestParam(value = "data", required = true) String data) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			if(session.getAttribute("addCompanyDeviceType")!= null){
				BaseDto vo = new BaseDto();
				vo.setRcode(21);
				vo.setRinfo("请求已提交，请耐心等待!");
				return JSON.toJSONString(vo);
			}
			session.setAttribute("addCompanyDeviceType", "addCompanyDeviceType");
			jsonStr = baseDMService.addCompanyDeviceTypeOrUpdaeWhenExist(data);
		} catch (Exception e) {
			log.error("addCompanyDeviceType ---- 异常,message = "
					+ e.getMessage());
			e.printStackTrace();
		}
		session.setAttribute("addCompanyDeviceType", null);
		return jsonStr;
	}
	

	/**
	 * 修改公司设备类型价格图片
	 * @author liukh
	 * @date 2017-3-17 下午3:20:39
	 * @param response
	 * @param session
	 * @param repacleParams{companyId,deviceModelId}
	 * @param data{modelQuote,picture}
	 * @return
	 */
	@RequestMapping(value = "/update/companyDeviceType", method = { RequestMethod.POST })
	@ResponseBody
	public String updateCompanyDeviceTypebyCompanyIdAndModelId(HttpServletResponse response,HttpSession session,
			@RequestParam(value = "data", required = true) String data){
		String jsonStr = "";
		try {
			if(session.getAttribute("updateCompanyDeviceTypebyCompanyIdAndModelId")!= null){
				BaseDto vo = new BaseDto();
				vo.setRcode(21);
				vo.setRinfo("请求已提交，请耐心等待!");
				return JSON.toJSONString(vo);
			}
			session.setAttribute("updateCompanyDeviceTypebyCompanyIdAndModelId", "updateCompanyDeviceTypebyCompanyIdAndModelId");
			jsonStr = baseDMService.updateCompanyDeviceTypebyCompanyIdAndModelId(data);
		} catch (Exception e) {
			log.error("updateCompanyDeviceTypebyCompanyIdAndModelId ---- 异常,message = "
					+ e.getMessage());
			e.printStackTrace();
		}
		log.info("updateCompanyDeviceTypebyCompanyIdAndModelId ---- jsonStr ========= " + jsonStr);
		session.setAttribute("updateCompanyDeviceTypebyCompanyIdAndModelId", null);
		return jsonStr;
		
	}
	

	/**
	 * 新增设备信息,并更新公司设备信息的图片
	 * 
	 * @author liukh
	 * @date 2017-2-21 上午10:37:15
	 * @param data
	 * {"deviceName":"剪刀车-A","picture":"http://api.chebaotec.com/app/device/2016110910141535669470988/20170213/3497e61811174110563d5a072817b914.jpg","isRealDevice":0,"sequenceNum":"0912","manufactureYear":"2016","workingTime":"100","plateNumber":"010"}
	 * @return
	 */
	@RequestMapping(value = "/{companyDeviceTypeId}/device", method = { RequestMethod.POST })
	@ResponseBody
	public String addDevice(HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable(value = "companyDeviceTypeId") String companyDeviceTypeId,
			@RequestParam(value = "data", required = true) String data) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseDMService.addDeviceAndInserPictureToCompanyDevice(companyDeviceTypeId,data);
		} catch (Exception e) {
			log.error("addDevice ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;
	}
	/**
	 * 求租需求单选择设备型号时候，筛选数量，关键参数值 给出公司可租设备类型列表
	 * @author liukh
	 * @date 2017-3-17 下午4:53:50
	 * @param request?companyId=XX&deviceTypeId=XX&keyAttributeValueId=XX&requreAmount=1
	 * @param response
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "companyAvailableDeviceList/filtrate", method = { RequestMethod.GET })
	@ResponseBody
	public String getCompanyAvailableDeviceListFiltrate(HttpServletRequest request,
			HttpServletResponse response,CompanyAvailableDeviceQueryForm form) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseDMService.getCompanyAvailableDeviceListFiltrate(form);
		} catch (Exception e) {
			log.error("getCompanyAvailableDeviceListFiltrate ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;
		
	}

	/**
	 * 获取要出库设备
	 * @author liukh
	 * @date 2017-3-17 下午5:08:22
	 * @param request
	 * @param response
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "/device/waitWork", method = { RequestMethod.GET })
	@ResponseBody
	public String getWaitWorkingDeviceList(HttpServletRequest request,
			HttpServletResponse response,DeviceQueryForm form) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseDMService.getWaitWorkingDeviceList(form);
		} catch (Exception e) {
			log.error("getWaitWorkingDeviceList ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;
		
	}
	
	
	/**
	 * 查询设备管理列表
	 * 
	 * @author five
	 * @date 2016-10-20 上午11:06:45
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/device/allInfor/list", method = { RequestMethod.GET })
	@ResponseBody
	public String getDeviceContainPriceAndAuditList(HttpServletRequest request,
			HttpServletResponse response,DeviceQueryForm form) {

		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseDMService.getDeviceContainPriceAndAuditList(form);
		} catch (Exception e) {
			log.error("getDeviceContainPriceAndAuditList ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;

	}
	
	/**
	 * 查询设备详情
	 * @author liukh
	 * @date 2016-12-13 下午3:13:04
	 * @param deviceId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/device/{deviceId}", method = { RequestMethod.GET })
	@ResponseBody
	public String getDeviceAndFillPriceAndAudit(@PathVariable(value = "deviceId") String deviceId,
			HttpServletRequest request, HttpServletResponse response) {
	
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseDMService.getDevice(deviceId);
		} catch (Exception e) {
			log.error("getDeviceContainPriceAndAuditList ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;

	}
	

	/**
	 * 根据公司Id和设备型号Id，获取公司设备型号中的报价
	 * @author liukh
	 * @date 2016-11-23 下午4:41:21
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/companyDeviceType/modelQuote", method = { RequestMethod.GET })
	@ResponseBody
	public String getModelQuoteByCompanyIdAndModelIdInCompanyDeviceType(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "companyId", required = true) String companyId,
			@RequestParam(value = "deviceModelId", required = true) String deviceModelId){
		
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseDMService.getModelQuoteByCompanyIdAndModelIdInCompanyDeviceType(companyId,deviceModelId);
		} catch (Exception e) {
			log.error("getModelQuoteByCompanyIdAndModelIdInCompanyDeviceType ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;
		
	}
	
/**
 * 统计不同设备不同状态下的数量
 * @author liukh
 * @date 2017-3-20 上午9:55:17
 * @param request
 * @param response
 * @return
 */
	@RequestMapping(value = "/device/count", method = { RequestMethod.GET })
	@ResponseBody
	public String getDeviceStatisticsCount(HttpServletRequest request,HttpServletResponse response,@RequestParam(value = "companyId", required = true) String companyId){
		
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseDMService.getDeviceStatisticsCount(companyId);
		} catch (Exception e) {
			log.error("getDeviceStatisticsCount ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;
		
	}
	
	/**
	 * 审核设备信息
	 * @author liukh
	 * @date 2017-3-20 上午11:52:26
	 * @param request
	 * @param response
	 * @param data
	 * @return
	 */
	@RequestMapping(value = "/check/device", method = { RequestMethod.POST })
	@ResponseBody
	public String checkDevice(
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "data", required = true) String data) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseDMService.updateCheckDevice(data);
		} catch (Exception e) {
			log.error("checkDevice ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		log.info("checkDevice ---- jsonStr ========= " + jsonStr);
		return jsonStr;

	}
	
	/**
	 * 获取设备信息
	 * 
	 * @author liukh
	 * @date 2017-2-21 上午10:38:13
	 * @param deviceId
	 * @return
	 */

/*	@RequestMapping(value = "/device/{deviceId}", method = { RequestMethod.GET })
	@ResponseBody
	public String getDevice(HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable(value = "deviceId") String deviceId) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseDMService.getDevice(deviceId);
		} catch (Exception e) {
			log.error("addDevice ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;
	}
*/
	/**
	 * 更新设备信息
	 * 
	 * @author liukh
	 * @date 2017-2-21 上午10:38:44
	 * @param deviceId
	 * @param data
	 * @return
	 */
	@RequestMapping(value = "/device/{deviceId}", method = { RequestMethod.POST })
	@ResponseBody
	public String updateDevice(
			@PathVariable(value = "deviceId") String deviceId,
			@RequestParam(value = "data", required = true) String data,
			HttpServletRequest request, HttpServletResponse response) {
		
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseDMService.updateDevice(deviceId, data);
		} catch (Exception e) {
			log.error("addDevice ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;
	}

	/**
	 * 删除设备信息
	 * 
	 * @author liukh
	 * @date 2017-2-21 上午10:42:21
	 * @param deviceId
	 * @return
	 */
	@RequestMapping(value = "/delete/device/{deviceId}", method = { RequestMethod.GET })
	@ResponseBody
	public String deleteDevice(HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable(value = "deviceId") String deviceId) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseDMService.deleteDevice(deviceId);
		} catch (Exception e) {
			log.error("deleteDevice ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;
		
	}

	/**
	 * 获取设备信息列表
	 * 
	 * @author liukh
	 * @date 2017-2-22 上午11:00:30
	 * @param request
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
	 * @param companyType
	 * @param numberOfItem
	 * @param page
	 * @param status
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/device/list", method = { RequestMethod.GET })
	@ResponseBody
	public String getDeviceList(HttpServletRequest request,HttpServletResponse response,DeviceQueryForm form) {
		
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseDMService.getDeviceList(form);
		} catch (Exception e) {
			log.error("getDeviceList ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;
		

	}

	/**
	 * 增加设备状态跟踪信息
	 * 
	 * @author liukh
	 * @date 2017-2-22 下午2:01:24
	 * @param request
	 * @param response
	 * @param data
	 * @return
	 */
	@RequestMapping(value = "/deviceStatusTrace", method = { RequestMethod.POST })
	@ResponseBody
	public String addDeviceStatusTrace(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "data", required = true) String data) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseDMService.addDeviceStatusTrace(data);
		} catch (Exception e) {
			log.error("addDeviceStatusTrace ---- 异常,message = "
					+ e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;
	}

	/**
	 * 删除设备状态跟踪信息
	 * 
	 * @author liukh
	 * @date 2017-2-22 下午2:17:09
	 * @param request
	 * @param response
	 * @param deviceStatusTraceId
	 * @return
	 */
	@RequestMapping(value = "/delete/deviceStatusTrace/{deviceStatusTraceId}", method = { RequestMethod.GET })
	@ResponseBody
	public String deleteDeviceStatusTrace(
			HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable(value = "deviceStatusTraceId") String deviceStatusTraceId) {
		
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseDMService.deleteDeviceStatusTrace(deviceStatusTraceId);
		} catch (Exception e) {
			log.error("deleteDeviceStatusTrace ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;
	}

	/**
	 * 获取设备跟踪总体记录列表
	 * 
	 * @author liukh
	 * @date 2017-2-22 下午2:22:07
	 * @param request
	 * @param deviceId
	 * @param deviceName
	 * @param orderId
	 * @param numberOfItem
	 * @param page
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/deviceStatusTrace/list", method = { RequestMethod.GET })
	@ResponseBody
	public String getDeviceStatusTraceList(HttpServletRequest request,HttpServletResponse response,DeviceStatusTraceQueryForm form) {
	
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseDMService.getDeviceStatusTraceList(form);
		} catch (Exception e) {
			log.error("getDeviceStatusTraceList ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;

	}



	/**
	 * 获取公司设备信息
	 * 
	 * @author liukh
	 * @date 2017-2-22 下午2:25:26
	 * @param request
	 * @param response
	 * @param companyDeviceTypeId
	 * @return
	 */

	@RequestMapping(value = "/companyDeviceType/{companyDeviceTypeId}", method = { RequestMethod.GET })
	@ResponseBody
	public String getCompanyDeviceType(
			HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable(value = "companyDeviceTypeId") String companyDeviceTypeId) {		
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseDMService.getCompanyDeviceType(companyDeviceTypeId);
		} catch (Exception e) {
			log.error("addCompanyDeviceType ---- 异常,message = "
					+ e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;
		
	}

	/**
	 * 更新设备信息
	 * 
	 * @author liukh
	 * @date 2017-2-22 下午2:26:42
	 * @param companyDeviceTypeId
	 * @param data
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/companyDeviceType/{companyDeviceTypeId}", method = { RequestMethod.POST })
	@ResponseBody
	public String updateCompanyDeviceType(
			@PathVariable(value = "companyDeviceTypeId") String companyDeviceTypeId,
			@RequestParam(value = "data", required = true) String data,
			HttpServletRequest request, HttpServletResponse response) {
		
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseDMService.updateCompanyDeviceType(companyDeviceTypeId, data);
		} catch (Exception e) {
			log.error("updateCompanyDeviceType ---- 异常,message = "
					+ e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;
		
	}

	/**
	 * 删除公司设备信息
	 * 
	 * @author liukh
	 * @date 2017-2-22 下午2:27:24
	 * @param request
	 * @param response
	 * @param deviceId
	 * @return
	 */
	@RequestMapping(value = "/delete/companyDeviceType/{companyDeviceTypeId}", method = { RequestMethod.GET })
	@ResponseBody
	public String deleteCompanyDeviceType(
			HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable(value = "companyDeviceTypeId") String companyDeviceTypeId) {
		
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseDMService.deleteCompanyDeviceType(companyDeviceTypeId);
		} catch (Exception e) {
			log.error("deleteCompanyDeviceType ---- 异常,message = "
					+ e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;
	}
	
	/**
	 * 新增设备通行信息
	 * @author liukh
	 * @date 2018-1-23 下午2:25:29
	 * @param request
	 * @param response
	 * @param session
	 * @param data
	 * @return
	 */
	@RequestMapping(value = "/deviceInspection", method = { RequestMethod.POST })
	@ResponseBody
	public String addDeviceInspection(HttpServletRequest request,
			HttpServletResponse response,HttpSession session,
			@RequestParam(value = "data", required = true) String data) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			if(session.getAttribute("addDeviceInspection")!= null){
				BaseDto vo = new BaseDto();
				vo.setRcode(21);
				vo.setRinfo("请求已提交，请耐心等待!");
				return JSON.toJSONString(vo);
			}
			session.setAttribute("addDeviceInspection", "addDeviceInspection");
			jsonStr = baseDMService.addDeviceInspection(data);
		} catch (Exception e) {
			log.error("addDevice ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
			session.setAttribute("addDeviceInspection", null);
		}
		session.setAttribute("addDeviceInspection", null);
		return jsonStr;
	}
	
	/**
	 * 修改通行信息
	 * @author liukh
	 * @date 2018-1-23 下午2:28:57
	 * @param request
	 * @param response
	 * @param inspectionId
	 * @param data
	 * @return
	 */
	@RequestMapping(value = "/{inspectionId}/deviceInspection", method = { RequestMethod.POST })
	@ResponseBody
	public String updateDeviceInspection(HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable(value = "inspectionId") String inspectionId,
			@RequestParam(value = "data", required = true) String data) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseDMService.updateDeviceInspection(inspectionId, data);
		} catch (Exception e) {
			log.error("updateDeviceInspection ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;
	}


}
