package com.zj.access.control.tm;

import java.math.BigDecimal;
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
import com.zj.access.service.BaseTMService;
import com.zj.base.common.Constant;
import com.zj.base.entity.base.dto.BaseDto;
import com.zj.entity.tm.form.DeliveryOrderQueryForm;
import com.zj.entity.tm.form.InqueryDeviceQueryForm;
import com.zj.entity.tm.form.InqueryOrderQueryForm;
import com.zj.entity.tm.form.InqueryRentQueryForm;
import com.zj.entity.tm.form.InqueryRentQuoteQueryForm;
import com.zj.entity.tm.form.InqueryRentThrowQueryForm;
import com.zj.entity.tm.form.OrderCapitalPoolQueryForm;
import com.zj.entity.tm.form.OrderCommentQueryForm;
import com.zj.entity.tm.form.OrderInteractiveTraceQueryForm;
import com.zj.entity.tm.form.OrderProgressTraceQueryForm;
import com.zj.entity.tm.form.OrderStatementQueryForm;
import com.zj.entity.tm.form.RentOrderDeviceQueryForm;
import com.zj.entity.tm.form.RentOrderQueryForm;

@Controller
@Scope("prototype")
@RequestMapping(value = "/tm")
public class BaseTMController extends CommonController {

	private static final Logger log = Logger.getLogger(BaseTMController.class);
	@Autowired
	private BaseTMService baseTMService;

	// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<询价单模块>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	/**
	 * 获取询价单列表
	 * 
	 * @author liukh
	 * @date 2017-2-24 下午3:49:08
	 * @param request
	 * @param response
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "/inqueryOrder/list", method = { RequestMethod.GET })
	@ResponseBody
	public String getInqueryOrderList(HttpServletRequest request,
			HttpServletResponse response, InqueryOrderQueryForm form) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseTMService.getInqueryOrderListAndDeviceList(form);
		} catch (Exception e) {
			log.error("getInqueryOrderList ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;

	}

	/**
	 * 新增询价单,加入询价车时，如果存在草稿下的询价车，则返回此询价车，如果不存在，则新增一个询价单，并返回询价单Id
	 * 
	 * @author liukh
	 * @date 2017-2-23 下午5:12:05
	 * @param request
	 * @param response
	 * @param data
	 *            {"leasingSideId":"2016092400242444050211778","lesseeSideId":
	 *            "201609240033311895930529"}
	 * @return
	 */
	@RequestMapping(value = "/inqueryOrder", method = { RequestMethod.POST })
	@ResponseBody
	public String addInqueryOrder(HttpServletRequest request,
			HttpServletResponse response, HttpSession session,
			@RequestParam(value = "data", required = true) String data) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			if (session.getAttribute("addInqueryOrder") != null) {
				BaseDto vo = new BaseDto();
				vo.setRcode(BaseDto.ERROR_RCODE);
				vo.setRinfo("请求已提交，请耐心等待!");
				return JSON.toJSONString(vo);
			}
			session.setAttribute("addInqueryOrder", "addInqueryOrder");
			jsonStr = baseTMService.addOrQueryInqueryOrder(data);
		} catch (Exception e) {
			log.error("addInqueryOrder ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		session.setAttribute("addInqueryOrder", null);
		return jsonStr;

	}

	/**
	 * 询价单立即下单(待租设备详细中)
	 * 
	 * @author liukh
	 * @date 2017-3-21 下午6:18:37
	 * @param request
	 * @param response
	 * @param session
	 * @param data
	 * @return
	 */

	@RequestMapping(value = "/fastAdd/inqueryOrder", method = { RequestMethod.POST })
	@ResponseBody
	public String fastAddInqueryOrderAndDevice(HttpServletRequest request,
			HttpServletResponse response, HttpSession session,
			@RequestParam(value = "data", required = true) String data) {
		String jsonStr = "";
		try {
			if (session.getAttribute("fastAddInqueryOrderAndDevice") != null) {
				BaseDto dto = new BaseDto();
				dto.setRcode(BaseDto.ERROR_RCODE);
				dto.setRinfo("请求已提交，请耐心等待!");
				return JSON.toJSONString(dto);
			}
			session.setAttribute("fastAddInqueryOrderAndDevice",
					"fastAddInqueryOrderAndDevice");
			jsonStr = baseTMService.addFastInqueryOrderAndDevice(data);
		} catch (Exception e) {
			log.error("fastAddInqueryOrderAndDevice ---- 异常,message = "
					+ e.getMessage());
			e.printStackTrace();
		}
		log.info("fastAddInqueryOrderAndDevice ---- jsonStr ========= "
				+ jsonStr);
		session.setAttribute("fastAddInqueryOrderAndDevice", null);
		return jsonStr;
	}

	/**
	 * 询价单立即下单
	 * 
	 * @author liukh
	 * @date 2017-3-21 下午6:19:16
	 * @param request
	 * @param response
	 * @param session
	 * @param inqueryOrderId
	 * @param data
	 * @return
	 */
	@RequestMapping(value = "/fastAdd/{inqueryOrderId}/inqueryOrdertoRentOrder", method = { RequestMethod.POST })
	@ResponseBody
	public String fastAddInqueryOrderToRentOrder(HttpServletRequest request,
			HttpServletResponse response, HttpSession session,
			@PathVariable(value = "inqueryOrderId") String inqueryOrderId,
			@RequestParam(value = "data", required = true) String data) {
		String jsonStr = "";
		try {
			if (session.getAttribute("fastAddInqueryOrderToRentOrder") != null) {
				BaseDto vo = new BaseDto();
				vo.setRcode(21);
				vo.setRinfo("请求已提交，请耐心等待!");
				return JSON.toJSONString(vo);
			}
			session.setAttribute("fastAddInqueryOrderToRentOrder",
					"fastAddInqueryOrderToRentOrder");

			jsonStr = baseTMService.addFastInqueryOrderToRentOrder(
					inqueryOrderId, data);

		} catch (Exception e) {
			log.error("fastAddInqueryOrderAndDevice ---- 异常,message = "
					+ e.getMessage());
			e.printStackTrace();
		}
		log.info("fastAddInqueryOrderAndDevice ---- jsonStr ========= "
				+ jsonStr);
		session.setAttribute("fastAddInqueryOrderToRentOrder", null);
		return jsonStr;
	}

	/**
	 * 修改报价
	 * 
	 * @author liukh
	 * @date 2017-3-21 下午6:23:02
	 * @param request
	 * @param response
	 * @param inqueryOrderId
	 * @param data
	 * @return
	 */
	@RequestMapping(value = "/inqueryOrder/editQuote/{inqueryOrderId}", method = { RequestMethod.POST })
	@ResponseBody
	public String updateInqueryOrderQuote(HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable(value = "inqueryOrderId") String inqueryOrderId,
			@RequestParam(value = "data", required = true) String data) {
		String jsonStr = "";
		try {

			jsonStr = baseTMService.updateInqueryOrderQuote(inqueryOrderId,
					data);
		} catch (Exception e) {
			log.error("updateInqueryOrderQuote ---- 异常,message = "
					+ e.getMessage());
			e.printStackTrace();
		}
		log.info("updateInqueryOrderQuote ---- jsonStr ========= " + jsonStr);
		return jsonStr;
	}

	/**
	 * 保存报价
	 * 
	 * @author liukh
	 * @date 2017-3-21 下午6:24:32
	 * @param request
	 * @param response
	 * @param inqueryOrderId
	 * @param data
	 * @return
	 */
	@RequestMapping(value = "/inqueryOrder/saveQuote/{inqueryOrderId}", method = { RequestMethod.POST })
	@ResponseBody
	public String saveInqueryOrderQuoto(HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable(value = "inqueryOrderId") String inqueryOrderId,
			@RequestParam(value = "data", required = true) String data) {
		String jsonStr = "";
		try {

			jsonStr = baseTMService.updateSaveQuotoInqueryOrder(inqueryOrderId,
					data);
		} catch (Exception e) {
			log.error("updateInqueryOrderQuote ---- 异常,message = "
					+ e.getMessage());
			e.printStackTrace();
		}
		log.info("updateInqueryOrderQuote ---- jsonStr ========= " + jsonStr);
		return jsonStr;
	}

	/**
	 * 获取询价单设备的时候包含时间类型
	 * 
	 * @author liukh
	 * @date 2017-3-21 下午6:27:25
	 * @param request
	 * @param response
	 * @param inqueryOrderId
	 * @return
	 */
	@RequestMapping(value = "/inqueryOrder/deviceContainDate/{inqueryOrderId}", method = { RequestMethod.GET })
	@ResponseBody
	public String getInqueryOrderAndDeviceContainDateTypeList(
			HttpServletRequest request, HttpServletResponse response,
			@PathVariable(value = "inqueryOrderId") String inqueryOrderId) {
		String jsonStr = "";
		try {

			jsonStr = baseTMService
					.getInqueryOrderAndDeviceContainDateList(inqueryOrderId);

		} catch (Exception e) {
			log.error("getInqueryOrder ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		log.info("getInqueryOrder ---- jsonStr ========= " + jsonStr);
		return jsonStr;
	}

	/**
	 * 获取询价单信息
	 * 
	 * @author liukh
	 * @date 2017-2-21 上午10:38:13
	 * @param deviceId
	 * @return
	 */

	@RequestMapping(value = "/inqueryOrder/{inqueryOrderId}", method = { RequestMethod.GET })
	@ResponseBody
	public String getInqueryOrder(HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable(value = "inqueryOrderId") String inqueryOrderId) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseTMService.getInqueryOrderAndDeviceContainDateList(inqueryOrderId);
					//.getInqueryOrderAndDeviceList(inqueryOrderId);
		} catch (Exception e) {
			log.error("getInqueryOrder ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;

	}

	/**
	 * 修改询价单信息
	 * 
	 * @author liukh
	 * @date 2017-2-24 下午3:55:23
	 * @param inqueryOrderId
	 * @param data
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/inqueryOrder/{inqueryOrderId}", method = { RequestMethod.POST })
	@ResponseBody
	public String updateInqueryOrder(
			@PathVariable(value = "inqueryOrderId") String inqueryOrderId,
			@RequestParam(value = "data", required = true) String data,
			HttpServletRequest request, HttpServletResponse response) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			// jsonStr = baseTMService.updateInqueryOrder(inqueryOrderId, data);
			jsonStr = baseTMService.updateCommitInqueryOrder(inqueryOrderId,
					data);

		} catch (Exception e) {
			log.error("updateInqueryOrder ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;

	}

	/**
	 * 删除询价单
	 * 
	 * @author liukh
	 * @date 2017-2-23 下午2:46:54
	 * @param inqueryOrderId
	 * @return
	 */
	@RequestMapping(value = "/delete/inqueryOrder/{inqueryOrderId}", method = { RequestMethod.GET })
	@ResponseBody
	public String deleteInqueryOrder(HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable(value = "inqueryOrderId") String inqueryOrderId) {

		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseTMService.deleteInqueryOrder(inqueryOrderId);
		} catch (Exception e) {
			log.error("deleteInqueryOrder ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;
	}

	/**
	 * 新增询价单设备信息
	 * 询价设备加入询价车的时候，判断如果询价车中已经有此设备了，比较型号，开始时间和结束时间如果匹配，则更改询价设备的数量和金额，否则新增询价设备
	 * 
	 * @author liukh
	 * @date 2017-2-23 下午5:12:05
	 * @param request
	 * @param response
	 * @param data
	 * @return
	 */
	@RequestMapping(value = "/{leasingSideId}/inqueryDevice", method = { RequestMethod.POST })
	@ResponseBody
	public String addInqueryDevice(HttpServletRequest request,
			HttpServletResponse response, HttpSession session,
			@PathVariable(value = "leasingSideId") String leasingSideId,
			@RequestParam(value = "data", required = true) String data) {

		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			if (session.getAttribute("addOrUpdateWhenHaveInqueryDevice") != null) {
				BaseDto vo = new BaseDto();
				vo.setRcode(21);
				vo.setRinfo("请求已提交，请耐心等待!");
				return JSON.toJSONString(vo);
			}
			session.setAttribute("addOrUpdateWhenHaveInqueryDevice",
					"addOrUpdateWhenHaveInqueryDevice");
			jsonStr = baseTMService.addOrUpdateWhenHaveInqueryDevice(
					leasingSideId, data);
		} catch (Exception e) {
			log.error("addInqueryDevice ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		session.setAttribute("addOrUpdateWhenHaveInqueryDevice", null);
		return jsonStr;
	}

	/**
	 * 更改询价单设备（数量的加减）
	 * 
	 * @author liukh
	 * @date 2017-3-29 下午3:24:10
	 * @param request
	 * @param response
	 * @param data
	 *            :{"quantity":5}
	 * @param inqueryDeviceId
	 * @return
	 */
	@RequestMapping(value = "/inqueryDevice/quantity/{inqueryDeviceId}", method = { RequestMethod.POST })
	@ResponseBody
	public String updateInqueryDevice4Quantity(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "data", required = true) String data,
			@PathVariable(value = "inqueryDeviceId") String inqueryDeviceId) {
		String jsonStr = "";
		try {

			jsonStr = baseTMService.updateInqueryDevice4Quantity(
					inqueryDeviceId, data);
		} catch (Exception e) {
			log.error("updateInqueryDevice4Quantity ---- 异常,message = "
					+ e.getMessage());
			e.printStackTrace();
		}
		log.info("updateInqueryDevice4Quantity ---- jsonStr ========= "
				+ jsonStr);
		return jsonStr;
	}

	/**
	 * 修改询价设备单价
	 * 
	 * @author liukh
	 * @date 2017-3-21 下午7:23:50
	 * @param request
	 * @param response
	 * @param data
	 *            :{unitprice}
	 * @param inqueryDeviceId
	 * @return
	 */
	@RequestMapping(value = "/inqueryDevice/unitPrice/{inqueryDeviceId}", method = { RequestMethod.POST })
	@ResponseBody
	public String updateInqueryDevice4UnitPrice(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "data", required = true) String data,
			@PathVariable(value = "inqueryDeviceId") String inqueryDeviceId) {
		String jsonStr = "";
		try {
			jsonStr = baseTMService.updateInqueryDevice4UnitPrice(
					inqueryDeviceId, data);
		} catch (Exception e) {
			log.error("updateInqueryDevice4UnitPrice ---- 异常,message = "
					+ e.getMessage());
			e.printStackTrace();
		}
		log.info("updateInqueryDevice4UnitPrice ---- jsonStr ========= "
				+ jsonStr);
		return jsonStr;
	}

	/**
	 * 询价设备预览
	 * 
	 * @author liukh
	 * @date 2017-3-21 下午7:25:39
	 * @param request
	 * @param response
	 * @param data
	 * @return
	 */
	@RequestMapping(value = "/inqueryDevice/preview/totalPrice", method = { RequestMethod.POST })
	@ResponseBody
	public String updateInqueryDevice4UnitPricePreview(
			HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "data", required = true) String data) {
		String jsonStr = "";
		try {
			jsonStr = baseTMService.updateInqueryDevice4UnitPricePreview(data);
		} catch (Exception e) {
			log.error("updateInqueryDevice4UnitPricePreview ---- 异常,message = "
					+ e.getMessage());
			e.printStackTrace();
		}
		log.info("updateInqueryDevice4UnitPricePreview ---- jsonStr ========= "
				+ jsonStr);
		return jsonStr;
	}

	/**
	 * 根据公司设备型号，数量，单价，工期，工期类型，计算设备价格
	 * 
	 * @author liukh
	 * @date 2017-3-21 下午7:29:39
	 * @param request
	 * @param response
	 * @param data
	 *            : {companyId,deviceModelId,quantity,billingType,leaseTerm}
	 * @return
	 */
	@RequestMapping(value = "/calculate/deviceTotalPrice", method = { RequestMethod.POST })
	@ResponseBody
	public String calculatePriceUseDeviceModel(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "data", required = true) String data) {
		String jsonStr = "";
		try {

			jsonStr = baseTMService.calculatePriceUseDeviceModel(data);
		} catch (Exception e) {
			log.error("calculatePriceUseDeviceModel ---- 异常,message = "
					+ e.getMessage());
			e.printStackTrace();
		}
		log.info("calculatePriceUseDeviceModel ---- jsonStr ========= "
				+ jsonStr);
		return jsonStr;
	}

	/**
	 * 获取询价车草稿状态下的数量
	 * 
	 * @author liukh
	 * @date 2017-3-21 下午7:30:38
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/inqueryOrder/carCount", method = { RequestMethod.GET })
	@ResponseBody
	public String getInqueryOrderCarCount(HttpServletRequest request,
			HttpServletResponse response, InqueryOrderQueryForm form) {
		String jsonStr = "";
		try {
			jsonStr = baseTMService.getInqueryOrderCarCount(form);
		} catch (Exception e) {
			log.error("getInqueryOrderCarCount ---- 异常,message = "
					+ e.getMessage());
			e.printStackTrace();
		}
		log.info("getInqueryOrderCarCount ---- jsonStr ========= " + jsonStr);
		return jsonStr;
	}

	/**
	 * 获取询价单设备信息
	 * 
	 * @author liukh
	 * @date 2017-2-21 上午10:38:13
	 * @param deviceId
	 * @return
	 */

	@RequestMapping(value = "/inqueryDevice/{inqueryDeviceId}", method = { RequestMethod.GET })
	@ResponseBody
	public String getInqueryDevice(HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable(value = "inqueryDeviceId") String inqueryDeviceId) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseTMService.getInqueryDevice(inqueryDeviceId);
		} catch (Exception e) {
			log.error("getInqueryDevice ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;

	}

	/**
	 * 修改询价单设备信息
	 * 
	 * @author liukh
	 * @date 2017-2-24 下午3:55:23
	 * @param inqueryOrderId
	 * @param data
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/inqueryDevice/{inqueryDeviceId}", method = { RequestMethod.POST })
	@ResponseBody
	public String updateInqueryDevice(
			@PathVariable(value = "inqueryDeviceId") String inqueryDeviceId,
			@RequestParam(value = "data", required = true) String data,
			HttpServletRequest request, HttpServletResponse response) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseTMService.updateInqueryDevice(inqueryDeviceId, data);
		} catch (Exception e) {
			log.error("updateInqueryDevice ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;

	}

	/**
	 * 删除询价单设备
	 * 
	 * @author liukh
	 * @date 2017-2-23 下午2:46:54
	 * @param inqueryDeviceId
	 * @return
	 */
	@RequestMapping(value = "/inqueryDevice/delete/{inqueryDeviceId}", method = { RequestMethod.GET })
	@ResponseBody
	public String deleteInqueryDevice(HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable(value = "inqueryDeviceId") String inqueryDeviceId) {

		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseTMService
					.deleteInqueryDeviceAndUpdateInqueryOrderTotalPrice(inqueryDeviceId);
		} catch (Exception e) {
			log.error("deleteInqueryDevice ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;
	}

	/**
	 * 获取询价单设备列表
	 * 
	 * @author liukh
	 * @date 2017-2-24 下午3:49:08
	 * @param request
	 * @param response
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "/inqueryDevice/list", method = { RequestMethod.GET })
	@ResponseBody
	public String getInqueryDeviceList(HttpServletRequest request,
			HttpServletResponse response, InqueryDeviceQueryForm form) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseTMService.getInqueryDeviceList(form);
		} catch (Exception e) {
			log.error("getInqueryDeviceList ---- 异常,message = "
					+ e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;

	}
	/**
	 * 询价单确认下单
	 * @author liukh
	 * @date 2017-4-11 下午4:24:39
	 * @param request
	 * @param response
	 * @param session
	 * @param data {"data":{"inqueryOrderId";"XXXX"}}
	 * @return
	 */
	@RequestMapping(value = "/rendOrder/inqueryOrder", method = { RequestMethod.POST })
	@ResponseBody
	public String createRentOrder4InqueryOrder(HttpServletRequest request,
			HttpServletResponse response,HttpSession session,
			@RequestParam(value = "data", required = true) String data) {
		String jsonStr = "";
		try {
			if(session.getAttribute("createRentOrder4InqueryOrder")!= null){
				BaseDto dto = new BaseDto();
				dto.setRcode(BaseDto.ERROR_RCODE);
				dto.setRinfo("请求已提交，请耐心等待!");
				return JSON.toJSONString(dto);
			}
			session.setAttribute("createRentOrder4InqueryOrder", "createRentOrder4InqueryOrder");
			jsonStr = baseTMService.addCreateRentOrder4InqueryOrder(data);
			session.setAttribute("createRentOrder4InqueryOrder", null);
		} catch (Exception e) {
			log.error("createRentOrder4InqueryOrder ---- 异常,message = "
					+ e.getMessage());
			e.printStackTrace();
		}
		log.info("createRentOrder4InqueryOrder ---- jsonStr ========= "
				+ jsonStr);
		return jsonStr;

	}

	// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<求租需求单>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	/**
	 * 新增求租需求单
	 * 
	 * @author liukh
	 * @date 2017-2-23 下午5:12:05
	 * @param request
	 * @param response
	 * @param data
	 * @return
	 */
	@RequestMapping(value = "/inqueryRent", method = { RequestMethod.POST })
	@ResponseBody
	public String addInqueryRent(HttpServletRequest request,
			HttpServletResponse response, HttpSession session,
			@RequestParam(value = "data", required = true) String data) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			if (session.getAttribute("addInqueryRent") != null) {
				BaseDto dto = new BaseDto();
				dto.setRcode(BaseDto.ERROR_RCODE);
				dto.setRinfo("请求已提交，请耐心等待!");
				return JSON.toJSONString(dto);
			}
			session.setAttribute("addInqueryRent", "addInqueryRent");
			jsonStr = baseTMService.addInqueryRent(data);
		} catch (Exception e) {
			log.error("addInqueryRent ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		session.setAttribute("addInqueryRent", null);
		return jsonStr;
	}

	/**
	 * 获取求租需求单信息(关键参数属性，时间参数)
	 * 
	 * @author liukh
	 * @date 2017-2-21 上午10:38:13
	 * @param inqueryRentId
	 * @return
	 */

	@RequestMapping(value = "/inqueryRent/{inqueryRentId}", method = { RequestMethod.GET })
	@ResponseBody
	public String getInqueryRent(HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable(value = "inqueryRentId") String inqueryRentId) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseTMService
					.getInqueryRentContainSpaceDataAndDateType(inqueryRentId);
		} catch (Exception e) {
			log.error("getInqueryRent ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;

	}

	/**
	 * 获取求租需求单及报价详细信息
	 * 
	 * @author liukh
	 * @date 2017-3-24 下午2:12:25
	 * @param request
	 *            {inqueryRentId,leasingSideId}
	 * @param response
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/inqueryRentAndInqueryRentQuoto", method = { RequestMethod.GET })
	@ResponseBody
	public String getInqueryRentAndInqueryRentQuotoDetaliInfor(
			HttpServletRequest request, HttpServletResponse response) {
		String jsonStr = "";
		try {
			Map params = ToolUtils.getParams(request);
			jsonStr = baseTMService
					.getInqueryRentAndInqueryRentQuotoDetaliInfor(params);
		} catch (Exception e) {
			log.error("getInqueryRentAndInqueryRentQuotoDetaliInfor ---- 异常,message = "
					+ e.getMessage());
			e.printStackTrace();
		}
		log.info("getInqueryRentAndInqueryRentQuotoDetaliInfor ---- jsonStr ========= "
				+ jsonStr);
		return jsonStr;
	}

	/**
	 * 修改求租需求单信息
	 * 
	 * @author liukh
	 * @date 2017-2-24 下午3:55:23
	 * @param inqueryRentId
	 * @param data
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/inqueryRent/{inqueryRentId}", method = { RequestMethod.POST })
	@ResponseBody
	public String updateInqueryRent(
			@PathVariable(value = "inqueryRentId") String inqueryRentId,
			@RequestParam(value = "data", required = true) String data,
			HttpServletRequest request, HttpServletResponse response) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseTMService.updateInqueryRent(inqueryRentId, data);
		} catch (Exception e) {
			log.error("updateInqueryRent ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;

	}

	/**
	 * 获取求租需求单列表
	 * 
	 * @author liukh
	 * @date 2017-2-24 下午3:49:08
	 * @param request
	 * @param response
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "/inqueryRent/list", method = { RequestMethod.GET })
	@ResponseBody
	public String getInqueryRentList(HttpServletRequest request,
			HttpServletResponse response, InqueryRentQueryForm form) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			if(form != null){
				if(StringUtils.isBlank(form.getAddress())){
					form.setAddress(null);
				}else{
					form.setAddress(URLDecoder.decode(form.getAddress(),"utf-8"));
				}
				
			}
			jsonStr = baseTMService.getInqueryRentList(form);
		} catch (Exception e) {
			log.error("getInqueryRentList ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;

	}

	/**
	 * 查询求租需求单列表的时候包含关键参数属性,并且是可以报价的(状态为待报价和已报价，且不包含自己公司已经报价的)
	 * 
	 * @author liukh
	 * @date 2017-3-24 下午3:35:05
	 * @param request
	 * @param response
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "/inqueryRent/canQuoto/list", method = { RequestMethod.GET })
	@ResponseBody
	public String getCanQuotoInqueryRentContainSpaceDataList(
			HttpServletRequest request, HttpServletResponse response,
			InqueryRentQueryForm form) {
		String jsonStr = "";
		try {
			if(form != null){
				if(StringUtils.isBlank(form.getAddress())){
					form.setAddress(null);
				}else{
					form.setAddress(URLDecoder.decode(form.getAddress(),"utf-8"));
				}
				
			}
			jsonStr = baseTMService
					.getCanQuotoInqueryRentContainSpaceDataList(form);
		} catch (Exception e) {
			log.error("getInqueryRentContainSpaceDataList ---- 异常,message = "
					+ e.getMessage());
			e.printStackTrace();
		}
		log.info("getInqueryRentContainSpaceDataList ---- jsonStr ========= "
				+ jsonStr);
		return jsonStr;
	}

	/**
	 * 获取求租需求单列表及其关联的求租需求单报价列表
	 */
	@RequestMapping(value = "/inqueryRentandQuote/list", method = { RequestMethod.GET })
	@ResponseBody
	public String getInqueryRentListAndInqueryRentQuoteList(
			HttpServletRequest request, HttpServletResponse response,
			InqueryRentQueryForm form) {
		String jsonStr = "";
		try {
			if(form != null){
				if(StringUtils.isBlank(form.getAddress())){
					form.setAddress(null);
				}else{
					form.setAddress(URLDecoder.decode(form.getAddress(),"utf-8"));
				}
				
			}

			jsonStr = baseTMService
					.getInqueryRentListAndInqueryRentQuoteList(form);
		} catch (Exception e) {
			log.error("getInqueryRentListAndInqueryRentQuoteList ---- 异常,message = "
					+ e.getMessage());
			e.printStackTrace();
		}
		log.info("getInqueryRentListAndInqueryRentQuoteList ---- jsonStr ========= "
				+ jsonStr);
		return jsonStr;
	}

	/**
	 * 新增求租需求单报价 根据传递过来的单价，其他费用，来计算应该的总价，然后对比传递过来的总价看是否对应
	 * 
	 * @author liukh
	 * @date 2017-2-24 下午5:01:25
	 * @param request
	 * @param response
	 * @param data
	 * @return
	 */
	@RequestMapping(value = "/inqueryRentQuote", method = { RequestMethod.POST })
	@ResponseBody
	public String addInqueryRentQuote(HttpServletRequest request,
			HttpServletResponse response, HttpSession session,
			@RequestParam(value = "data", required = true) String data) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			if (session.getAttribute("addInqueryRentQuote") != null) {
				BaseDto dto = new BaseDto();
				dto.setRcode(BaseDto.ERROR_RCODE);
				dto.setRinfo("请求已提交，请耐心等待!");
				return JSON.toJSONString(dto);
			}
			session.setAttribute("addInqueryRentQuote", "addInqueryRentQuote");
			jsonStr = baseTMService.addInqueryRentQuoteComapreTotalPrice(data);
		} catch (Exception e) {
			log.error("addInqueryRentQuote ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		session.setAttribute("addInqueryRentQuote", null);
		return jsonStr;
	}

	/**
	 * 求租需求单报价获取计算出的报价总的报价
	 * 
	 * @author liukh
	 * @date 2017-3-24 下午5:23:40
	 * @param request
	 * @param response
	 * @param data
	 *            {"leaseTerm"，"quantity"，"otherExpense"，"unitPrice"}
	 * @return
	 */
	@RequestMapping(value = "/orderCalculatePrice", method = { RequestMethod.POST })
	@ResponseBody
	public String getOrderCalculatePrice(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "data", required = true) String data) {
		String jsonStr = "";
		try {
			jsonStr = baseTMService.getOrderCalculatePrice(data);
		} catch (Exception e) {
			log.error("getOrderCalculatePrice ---- 异常,message = "
					+ e.getMessage());
			e.printStackTrace();
		}
		log.info("getOrderCalculatePrice ---- jsonStr ========= " + jsonStr);
		return jsonStr;

	}

	/**
	 * 获取求租需求单和对应报价列表（一对一，适用于租赁方我的求租需求单报价）
	 * 
	 * @author liukh
	 * @date 2017-3-24 下午7:22:44
	 * @param request
	 * @param response
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "/quoteAndInqueryRent/list", method = { RequestMethod.GET })
	@ResponseBody
	public String getQuoteAndInqueryRentList(HttpServletRequest request,
			HttpServletResponse response, InqueryRentQuoteQueryForm form) {
		String jsonStr = "";
		try {
			jsonStr = baseTMService.getInqueryRentQuoteAndInqueryRentList(form);
		} catch (Exception e) {
			log.error("getQuoteAndInqueryRentList ---- 异常,message = "
					+ e.getMessage());
			e.printStackTrace();
		}
		log.info("getQuoteAndInqueryRentList ---- jsonStr ========= " + jsonStr);
		return jsonStr;
	}

	/**
	 * 获取求租需求单报价和求租需求单
	 * 
	 * @author liukh
	 * @date 2017-3-24 下午7:25:13
	 * @param request
	 * @param response
	 * @param inqueryRentQuoteId
	 * @return
	 */
	@RequestMapping(value = "/quoteAndInqueryRent/{inqueryRentQuoteId}", method = { RequestMethod.GET })
	@ResponseBody
	public String getInqueryRentQuotoAndInqueryRent(
			HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable(value = "inqueryRentQuoteId") String inqueryRentQuoteId) {
		String jsonStr = "";
		try {
			jsonStr = baseTMService
					.getInqueryRentQuotoAndInqueryRent(inqueryRentQuoteId);
		} catch (Exception e) {
			log.error("getInqueryRentQuotoAndInqueryRent ---- 异常,message = "
					+ e.getMessage());
			e.printStackTrace();
		}
		log.info("getInqueryRentQuotoAndInqueryRent ---- jsonStr ========= "
				+ jsonStr);
		return jsonStr;
	}

	/**
	 * 忽略求租需求单报价
	 * 
	 * @author liukh
	 * @date 2017-3-25 上午10:03:32
	 * @param request
	 * @param response
	 * @param inqueryRentQuoteId
	 * @return
	 */
	@RequestMapping(value = "/ignoreInqueryRentQuoto/{inqueryRentQuoteId}", method = { RequestMethod.GET })
	@ResponseBody
	public String ignoreInqueryRentQuoto(
			HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable(value = "inqueryRentQuoteId") String inqueryRentQuoteId) {
		String jsonStr = "";
		try {

			jsonStr = baseTMService
					.updateIgnoreInqueryRentQuoto(inqueryRentQuoteId);
		} catch (Exception e) {
			log.error("ignoreInqueryRentQuoto ---- 异常,message = "
					+ e.getMessage());
			e.printStackTrace();
		}
		log.info("ignoreInqueryRentQuoto ---- jsonStr ========= " + jsonStr);
		return jsonStr;

	}

	/**
	 * 获取求租需求单报价信息
	 * 
	 * @author liukh
	 * @date 2017-2-21 上午10:38:13
	 * @param inqueryRentId
	 * @return
	 */

	@RequestMapping(value = "/inqueryRentQuote/{inqueryRentQuoteId}", method = { RequestMethod.GET })
	@ResponseBody
	public String getInqueryRentQuote(
			HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable(value = "inqueryRentQuoteId") String inqueryRentQuoteId) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseTMService.getInqueryRentQuote(inqueryRentQuoteId);
		} catch (Exception e) {
			log.error("getInqueryRentQuote ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;

	}

	/**
	 * 修改求租需求单报价信息
	 * 
	 * @author liukh
	 * @date 2017-2-24 下午3:55:23
	 * @param inqueryRentQuoteId
	 * @param data
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/inqueryRentQuote/{inqueryRentQuoteId}", method = { RequestMethod.POST })
	@ResponseBody
	public String updateInqueryRentQuote(
			@PathVariable(value = "inqueryRentQuoteId") String inqueryRentQuoteId,
			@RequestParam(value = "data", required = true) String data,
			HttpServletRequest request, HttpServletResponse response) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseTMService.updateInqueryRentQuote(inqueryRentQuoteId,
					data);
		} catch (Exception e) {
			log.error("updateInqueryRentQuote ---- 异常,message = "
					+ e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;

	}

	/**
	 * 获取求租需求单报价列表
	 * 
	 * @author liukh
	 * @date 2017-2-24 下午3:49:08
	 * @param request
	 * @param response
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "/inqueryRentQuote/list", method = { RequestMethod.GET })
	@ResponseBody
	public String getInqueryRentQuoteList(HttpServletRequest request,
			HttpServletResponse response, InqueryRentQuoteQueryForm form) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseTMService.getInqueryRentQuoteList(form);
		} catch (Exception e) {
			log.error("getInqueryRentQuoteList ---- 异常,message = "
					+ e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;

	}

	@RequestMapping(value = "/rendOrder/inqueryRent", method = { RequestMethod.POST })
	@ResponseBody
	public String createRentOrder4InqueryRent(HttpServletRequest request,
			HttpServletResponse response, HttpSession session,
			@RequestParam(value = "data", required = true) String data) {
		String jsonStr = "";
		try {
			if (session.getAttribute("createRentOrder4InqueryRent") != null) {
				BaseDto dto = new BaseDto();
				dto.setRcode(21);
				dto.setRinfo("请求已提交，请耐心等待!");
				return JSON.toJSONString(dto);
			}
			session.setAttribute("createRentOrder4InqueryRent",
					"createRentOrder4InqueryRent");
			jsonStr = baseTMService.addCreateRentOrder4InqueryRent(data);

		} catch (Exception e) {
			log.error("createRentOrder4InqueryRent ---- 异常,message = "
					+ e.getMessage());
			e.printStackTrace();
		}
		log.info("createRentOrder4InqueryRent ---- jsonStr ========= "
				+ jsonStr);
		session.setAttribute("createRentOrder4InqueryRent", null);
		return jsonStr;
	}

	// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<扣款提示>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	/**
	 * 求租需求单马上下单，提示锁定金额
	 * 
	 * @author liukh
	 * @date 2016-11-5 上午10:06:08
	 * @param request
	 * @param response
	 * @param data
	 *            :{"inqueryOrderId":XX}
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/lockedMoney/inqueryOrder/prevention", method = { RequestMethod.POST })
	@ResponseBody
	public String tipMakeSureLockedMoenyTip4PreventionWhenInqueryOrder2RentOrder(
			HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "data", required = true) String data) {
		String jsonStr = "";
		try {

			jsonStr = baseTMService
					.tipMakeSureLockedMoenyTip4PreventionWhenInqueryOrder2RentOrder(data);

		} catch (Exception e) {
			log.error("tipMakeSureLockedMoenyTip4PreventionWhenInqueryOrder2RentOrder ---- 异常,message = "
					+ e.getMessage());
			e.printStackTrace();
		}
		log.info("tipMakeSureLockedMoenyTip4PreventionWhenInqueryOrder2RentOrder ---- jsonStr ========= "
				+ jsonStr);
		return jsonStr;
	}

	/**
	 * 适用于询价单 立即下单提示锁定金额
	 * 
	 * @author liukh
	 * @date 2016-11-18 下午3:59:44
	 * @param request
	 * @param response
	 * @param ddata
	 *            {lesseeSideId,leasingSideId,deviceModelId,payMethod,quantity,
	 *            leaseTerm,billingType，userId}
	 * @return
	 */
	@RequestMapping(value = "/fastCreateOrder/lockedMoney/prevention", method = { RequestMethod.POST })
	@ResponseBody
	public String tipLockedMoneyWhenFastCreateOrder(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "data", required = true) String data) {
		String jsonStr = "";
		try {

			jsonStr = baseTMService.tipLockedMoneyWhenFastCreateOrder(data);

		} catch (Exception e) {
			log.error("tipLockedMoneyWhenFastCreateOrder ---- 异常,message = "
					+ e.getMessage());
			e.printStackTrace();
		}
		log.info("tipLockedMoneyWhenFastCreateOrder ---- jsonStr ========= "
				+ jsonStr);
		return jsonStr;
	}

	/**
	 * 使用于求租需求单 提示扣预付款信息
	 * 
	 * @author liukh
	 * @date 2016-10-31 下午1:55:26
	 * @param request
	 * @param response
	 * @param data
	 *            {"lesseeSideId"，"leaseTerm"，"billingType"，"rentFee"}
	 * @return
	 */

	@RequestMapping(value = "/lockedMoney/inqueryRent/prevention", method = { RequestMethod.POST })
	@ResponseBody
	public String tipMakeSureLockedMoenyTip4IqueryRentPrevention(
			HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "data", required = true) String data) {
		String jsonStr = "";
		try {

			jsonStr = baseTMService
					.tipMakeSureLockedMoenyTip4IqueryRentPrevention(data);

		} catch (Exception e) {
			log.error("tipMakeSureLockedMoenyTip4Prevention ---- 异常,message = "
					+ e.getMessage());
			e.printStackTrace();
		}
		log.info("tipMakeSureLockedMoenyTip4Prevention ---- jsonStr ========= "
				+ jsonStr);
		return jsonStr;
	}

	/**
	 * 适用于抛单 提示扣预付款信息
	 * 
	 * @author liukh
	 * @date 2016-11-23 上午10:51:20
	 * @param request
	 * @param response
	 * @param data
	 *            {"lesseeSideId"，"leaseTerm"，"billingType"，"rentFee"}
	 * @return
	 */
	@RequestMapping(value = "/lockedMoney/inqueryThrow/prevention", method = { RequestMethod.POST })
	@ResponseBody
	public String tipMakeSureLockedMoenyTip4IqueryThrowPrevention(
			HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "data", required = true) String data) {
		String jsonStr = "";
		try {

			jsonStr = baseTMService
					.tipMakeSureLockedMoenyTip4IqueryThrowPrevention(data);

		} catch (Exception e) {
			log.error("tipMakeSureLockedMoenyTip4IqueryThrowPrevention ---- 异常,message = "
					+ e.getMessage());
			e.printStackTrace();
		}
		log.info("tipMakeSureLockedMoenyTip4IqueryThrowPrevention ---- jsonStr ========= "
				+ jsonStr);
		return jsonStr;
	}

	/**
	 * 
	 * 提示扣保证金信息
	 * 
	 * @author liukh
	 * @date 2016-10-31 下午1:55:26
	 * @param request
	 * @param response
	 * @param data
	 *            {"lesseeSideId"}
	 * @return
	 */

	@RequestMapping(value = "/lockedMoney/margin", method = { RequestMethod.POST })
	@ResponseBody
	public String tipMakeSureLockedMoenyTip4Margin(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "data", required = true) String data) {
		String jsonStr = "";
		try {

			jsonStr = baseTMService.tipMakeSureLockedMoenyTip4Margin(data);
		} catch (Exception e) {
			log.error("tipMakeSureLockedMoenyTip4Prevention ---- 异常,message = "
					+ e.getMessage());
			e.printStackTrace();
		}
		log.info("tipMakeSureLockedMoenyTip4Prevention ---- jsonStr ========= "
				+ jsonStr);
		return jsonStr;
	}

	/**
	 * 抢单前提示抢单公司是否有抛单需要的设备或者充足的保证金
	 * 
	 * @author liukh
	 * @date 2016-11-30 上午11:00:03
	 * @param request
	 *            {inqueryRentThrowId,companyId}
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/canGetInqueryRentThrow", method = { RequestMethod.GET })
	@ResponseBody
	public String tip4InqueryRentThrowDoHaveDeviceHaveMoneyMargin(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "inqueryRentThrowId", required = true) String inqueryRentThrowId,
			@RequestParam(value = "companyId", required = true) String companyId) {

		String jsonStr = "";
		try {
			jsonStr = baseTMService
					.tip4InqueryRentThrowDoHaveDeviceHaveMoneyMargin(
							inqueryRentThrowId, companyId);
		} catch (Exception e) {
			log.error("tipMakeSureLockedMoenyTip4Prevention ---- 异常,message = "
					+ e.getMessage());
			e.printStackTrace();
		}
		log.info("tipMakeSureLockedMoenyTip4Prevention ---- jsonStr ========= "
				+ jsonStr);
		return jsonStr;
	}

	/**
	 * 使用于抛单抢单 抢单前先判断抢单人是否有该设备，然后在提示保证金是否充足 租赁方抢单的时候比较可用金额，及要提示扣除多少钱
	 * 租赁方扣保证金默认扣除的金额为2000
	 * 
	 * @author liukh
	 * @date 2016-10-27 下午5:54:20
	 * @param tipMakeSureLockedMoenyTip4InqueryRentThrowWhenMargin
	 * @return
	 */

	@RequestMapping(value = "/lockedMoney/margin/{companyId}", method = { RequestMethod.GET })
	@ResponseBody
	public String tipMakeSureLockedMoenyTip4InqueryRentThrowWhenMargin(
			HttpServletRequest request, HttpServletResponse response,
			@PathVariable(value = "companyId") String companyId) {
		String jsonStr = "";
		try {

			String lockedType = Constant.ORDERCAPITALPOOLPOJO_MARGIN.toString();
			BigDecimal lockedAmout = Constant.MARGIN_AMOUNT;
			jsonStr = baseTMService.tipLockedMoney(companyId, lockedType,
					lockedAmout);

		} catch (Exception e) {
			log.error("tipMakeSureLockedMoenyTip4InqueryRentThrowWhenMargin ---- 异常,message = "
					+ e.getMessage());
			e.printStackTrace();
		}
		log.info("tipMakeSureLockedMoenyTip4InqueryRentThrowWhenMargin ---- jsonStr ========= "
				+ jsonStr);
		return jsonStr;

	}

	// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<求租抛单>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	/**
	 * 新增求租抛单
	 * 
	 * @author liukh
	 * @date 2017-2-24 下午5:20:33
	 * @param request
	 * @param response
	 * @param data
	 * @return
	 */
	@RequestMapping(value = "/inqueryRentThrow", method = { RequestMethod.POST })
	@ResponseBody
	public String addInqueryRentThrow(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "data", required = true) String data) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseTMService.addInqueryRentThrow(data);
		} catch (Exception e) {
			log.error("addInqueryRentThrow ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;
	}

	/**
	 * 获取求租抛单信息
	 * 
	 * @author liukh
	 * @date 2017-2-21 上午10:38:13
	 * @param inqueryRentId
	 * @return
	 */

	@RequestMapping(value = "/inqueryRentThrow/{inqueryRentThrowId}", method = { RequestMethod.GET })
	@ResponseBody
	public String getInqueryRentThrow(
			HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable(value = "inqueryRentThrowId") String inqueryRentThrowId) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseTMService.getInqueryRentThrow(inqueryRentThrowId);
		} catch (Exception e) {
			log.error("getInqueryRentThrow ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;

	}

	/**
	 * 修改求租抛单信息
	 * 
	 * @author liukh
	 * @date 2017-2-24 下午3:55:23
	 * @param inqueryRentThrowId
	 * @param data
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/inqueryRentThrow/{inqueryRentThrowId}", method = { RequestMethod.POST })
	@ResponseBody
	public String updateInqueryRentThrow(
			@PathVariable(value = "inqueryRentThrowId") String inqueryRentThrowId,
			@RequestParam(value = "data", required = true) String data,
			HttpServletRequest request, HttpServletResponse response) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseTMService.updateInqueryRentThrow(inqueryRentThrowId,
					data);
		} catch (Exception e) {
			log.error("updateInqueryRentThrow ---- 异常,message = "
					+ e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;

	}

	/**
	 * 获取求租抛单列表
	 * 
	 * @author liukh
	 * @date 2017-2-24 下午3:49:08
	 * @param request
	 * @param response
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "/inqueryRentThrow/list", method = { RequestMethod.GET })
	@ResponseBody
	public String getInqueryRentThrowList(HttpServletRequest request,
			HttpServletResponse response, InqueryRentThrowQueryForm form) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseTMService.getInqueryRentThrowList(form);
		} catch (Exception e) {
			log.error("getInqueryRentThrowList ---- 异常,message = "
					+ e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;

	}

	// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<租赁订单>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	/**
	 * 增加租赁订单信息
	 * 
	 * @author liukh
	 * @date 2017-2-27 下午4:23:44
	 * @param request
	 * @param response
	 * @param data
	 * @return
	 */
	@RequestMapping(value = "/rentOrder", method = { RequestMethod.POST })
	@ResponseBody
	public String addRentOrder(HttpServletRequest request,
			HttpServletResponse response, HttpSession session,
			@RequestParam(value = "data", required = true) String data) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			if (session.getAttribute("addRentOrder") != null) {
				BaseDto dto = new BaseDto();
				dto.setRcode(BaseDto.ERROR_RCODE);
				dto.setRinfo("请求已提交，请耐心等待!");
				return JSON.toJSONString(dto);
			}
			jsonStr = baseTMService.addRentOrder(data);
		} catch (Exception e) {
			log.error("addRentOrder ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		session.setAttribute("addRentOrder", null);
		return jsonStr;
	}

	/**
	 * 获取租赁订单信息
	 * 
	 * @author liukh
	 * @date 2017-2-27 下午4:25:26
	 * @param request
	 * @param response
	 * @param rentOrderId
	 * @return
	 */
	@RequestMapping(value = "/rentOrder/{rentOrderId}", method = { RequestMethod.GET })
	@ResponseBody
	public String getRentOrder(HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable(value = "rentOrderId") String rentOrderId) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseTMService
					.getRentOrderAndRentOrderDeviceList(rentOrderId);
		} catch (Exception e) {
			log.error("getRentOrder ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;

	}

	/**
	 * 修改租赁订单信息
	 * 
	 * @author liukh
	 * @date 2017-2-27 下午4:26:54
	 * @param rentOrderId
	 * @param data
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/rentOrder/{rentOrderId}", method = { RequestMethod.POST })
	@ResponseBody
	public String updateRentOrder(
			@PathVariable(value = "rentOrderId") String rentOrderId,
			@RequestParam(value = "data", required = true) String data,
			HttpServletRequest request, HttpServletResponse response) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseTMService.updateRentOrder(rentOrderId, data);
		} catch (Exception e) {
			log.error("updateRentOrder ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;

	}

	/**
	 * 获取租赁订单信息列表
	 * 
	 * @author liukh
	 * @date 2017-2-27 下午4:28:28
	 * @param request
	 * @param response
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "/rentOrder/list", method = { RequestMethod.GET })
	@ResponseBody
	public String getRentOrderList(HttpServletRequest request,
			HttpServletResponse response, RentOrderQueryForm form) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseTMService
					.getRentOrderListAndRentOrderDeviceList(form);
		} catch (Exception e) {
			log.error("getRentOrderList ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;

	}

	/**
	 * 新增租赁订单设备信息
	 * 
	 * @author liukh
	 * @date 2017-2-27 下午4:29:42
	 * @param request
	 * @param response
	 * @param data
	 * @return
	 */
	@RequestMapping(value = "/rentOrderDevice", method = { RequestMethod.POST })
	@ResponseBody
	public String addRentOrderDevice(HttpServletRequest request,
			HttpServletResponse response, HttpSession session,
			@RequestParam(value = "data", required = true) String data) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			if (session.getAttribute("addRentOrderDevice") != null) {
				BaseDto dto = new BaseDto();
				dto.setRcode(BaseDto.ERROR_RCODE);
				dto.setRinfo("请求已提交，请耐心等待!");
				return JSON.toJSONString(dto);
			}
			session.setAttribute("addRentOrderDevice", "addRentOrderDevice");
			jsonStr = baseTMService.addRentOrderDevice(data);
		} catch (Exception e) {
			log.error("addRentOrderDevice ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		session.setAttribute("addRentOrderDevice", null);
		return jsonStr;
	}

	/**
	 * 获取租赁订单设备信息
	 * 
	 * @author liukh
	 * @date 2017-2-27 下午4:31:00
	 * @param request
	 * @param response
	 * @param rentOrderDeviceId
	 * @return
	 */
	@RequestMapping(value = "/rentOrderDevice/{rentOrderDeviceId}", method = { RequestMethod.GET })
	@ResponseBody
	public String getRentOrderDevice(HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable(value = "rentOrderDeviceId") String rentOrderDeviceId) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseTMService.getRentOrderDevice(rentOrderDeviceId);
		} catch (Exception e) {
			log.error("getRentOrder ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;

	}

	/**
	 * 获取租赁订单设备信息列表
	 * 
	 * @author liukh
	 * @date 2017-2-27 下午4:32:08
	 * @param request
	 * @param response
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "/rentOrderDevice/list", method = { RequestMethod.GET })
	@ResponseBody
	public String getRentOrderDeviceList(HttpServletRequest request,
			HttpServletResponse response, RentOrderDeviceQueryForm form) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseTMService.getRentOrderDeviceList(form);
		} catch (Exception e) {
			log.error("getRentOrderDeviceList ---- 异常,message = "
					+ e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;

	}

	/**
	 * 新增订单出库单
	 * 
	 * @author liukh
	 * @date 2017-2-28 上午10:15:10
	 * @param request
	 * @param response
	 * @param data
	 * @return
	 */
	/*
	 * @RequestMapping(value = "/deliveryOrder", method = { RequestMethod.POST
	 * })
	 * 
	 * @ResponseBody public String addDeliveryOrder(HttpServletRequest request,
	 * HttpServletResponse response,
	 * 
	 * @RequestParam(value = "data", required = true) String data) { String
	 * jsonStr = JSON.toJSONString(new BaseDto()); try { jsonStr =
	 * baseTMService.addDeliveryOrder(data); } catch (Exception e) {
	 * log.error("addDeliveryOrder ---- 异常,message = " + e.getMessage());
	 * e.printStackTrace(); } return jsonStr; }
	 */
	/**
	 * 新增订单出库单及改变租赁订单的状态
	 * 
	 * @author liukh
	 * @date 2017-3-27 上午10:48:47
	 * @param request
	 * @param response
	 * @param session
	 * @param data
	 *            :{ * data:[
	 * 
	 *            { "rentOrderId":"XXX", "rentOrderDeviceId":"XXX",
	 *            "status":YYY, "creator":"YYYY", "data":[
	 *            {"id":"XXXX","deviceName":"YYYY"},
	 *            {"id":"XXXX","deviceName":"YYYY"}
	 * 
	 *            ]
	 * 
	 *            } ] }
	 * @return
	 */
	@RequestMapping(value = "/deliveryOrder", method = { RequestMethod.POST })
	@ResponseBody
	public String addDeliveryOrderAndUpdateRentOrderStatus(
			HttpServletRequest request, HttpServletResponse response,
			HttpSession session,
			@RequestParam(value = "data", required = true) String data) {
		String jsonStr = "";
		try {
			if (session
					.getAttribute("addDeliveryOrderAndUpdateRentOrderStatus") != null) {
				BaseDto dto = new BaseDto();
				dto.setRcode(BaseDto.ERROR_RCODE);
				dto.setRinfo("请求已提交，请耐心等待!");
				return JSON.toJSONString(dto);
			}
			session.setAttribute("addDeliveryOrderAndUpdateRentOrderStatus",
					"addDeliveryOrderAndUpdateRentOrderStatus");
			jsonStr = baseTMService
					.addDeliveryOrderAndUpdateRentOrderStatus(data);

		} catch (Exception e) {
			log.error("addDeliveryOrderAndUpdateRentOrderStatus ---- 异常,message = "
					+ e.getMessage());
			e.printStackTrace();
		}
		log.info("addDeliveryOrderAndUpdateRentOrderStatus ---- jsonStr ========= "
				+ jsonStr);
		session.setAttribute("addDeliveryOrderAndUpdateRentOrderStatus", null);
		return jsonStr;
	}

	/**
	 * 获取订单出库单
	 * 
	 * @author liukh
	 * @date 2017-2-28 上午10:16:51
	 * @param request
	 * @param response
	 * @param deliveryOrderId
	 * @return
	 */
	@RequestMapping(value = "/deliveryOrder/{deliveryOrderId}", method = { RequestMethod.GET })
	@ResponseBody
	public String getDeliveryOrder(HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable(value = "deliveryOrderId") String deliveryOrderId) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseTMService.getDeliveryOrder(deliveryOrderId);
		} catch (Exception e) {
			log.error("getDeliveryOrder ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;

	}

	/**
	 * 修改订单出库单
	 * 
	 * @author liukh
	 * @date 2017-2-28 上午10:19:31
	 * @param deliveryOrderId
	 * @param data
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/deliveryOrder/{deliveryOrderId}", method = { RequestMethod.POST })
	@ResponseBody
	public String updateDeliveryOrder(
			@PathVariable(value = "deliveryOrderId") String deliveryOrderId,
			@RequestParam(value = "data", required = true) String data,
			HttpServletRequest request, HttpServletResponse response) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseTMService.updateDeliveryOrder(deliveryOrderId, data);
		} catch (Exception e) {
			log.error("updateDeliveryOrder ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;

	}

	/**
	 * 获取订单出库单列表
	 * 
	 * @author liukh
	 * @date 2017-2-28 上午10:20:35
	 * @param request
	 * @param response
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "/deliveryOrder/list", method = { RequestMethod.GET })
	@ResponseBody
	public String getDeliveryOrderList(HttpServletRequest request,
			HttpServletResponse response, DeliveryOrderQueryForm form) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseTMService.getDeliveryOrderList(form);
		} catch (Exception e) {
			log.error("getDeliveryOrderList ---- 异常,message = "
					+ e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;

	}

	/**
	 * 新增订单资金池
	 * 
	 * @author liukh
	 * @date 2017-2-28 上午10:23:32
	 * @param request
	 * @param response
	 * @param data
	 * @return
	 */
	@RequestMapping(value = "/orderCapitalPool", method = { RequestMethod.POST })
	@ResponseBody
	public String addOrderCapitalPool(HttpServletRequest request,
			HttpServletResponse response, HttpSession session,
			@RequestParam(value = "data", required = true) String data) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			if (session.getAttribute("addOrderCapitalPool") != null) {
				BaseDto dto = new BaseDto();
				dto.setRcode(BaseDto.ERROR_RCODE);
				dto.setRinfo("请求已提交，请耐心等待!");
				return JSON.toJSONString(dto);
			}
			session.setAttribute("addOrderCapitalPool", "addOrderCapitalPool");
			jsonStr = baseTMService.addOrderCapitalPool(data);
		} catch (Exception e) {
			log.error("addOrderCapitalPool ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		session.setAttribute("addOrderCapitalPool", null);
		return jsonStr;
	}

	/**
	 * 获取订单资金池
	 * 
	 * @author liukh
	 * @date 2017-2-28 上午10:25:45
	 * @param request
	 * @param response
	 * @param orderCapitalPoolId
	 * @return
	 */
	@RequestMapping(value = "/orderCapitalPool/{orderCapitalPoolId}", method = { RequestMethod.GET })
	@ResponseBody
	public String getOrderCapitalPool(
			HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable(value = "orderCapitalPoolId") String orderCapitalPoolId) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseTMService.getOrderCapitalPool(orderCapitalPoolId);
		} catch (Exception e) {
			log.error("getDeliveryOrder ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;

	}

	/**
	 * 修改订单出库单
	 * 
	 * @author liukh
	 * @date 2017-2-28 上午10:19:31
	 * @param deliveryOrderId
	 * @param data
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/orderCapitalPool/{orderCapitalPoolId}", method = { RequestMethod.POST })
	@ResponseBody
	public String updateOrderCapitalPool(
			@PathVariable(value = "orderCapitalPoolId") String orderCapitalPoolId,
			@RequestParam(value = "data", required = true) String data,
			HttpServletRequest request, HttpServletResponse response) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseTMService.updateOrderCapitalPool(orderCapitalPoolId,
					data);
		} catch (Exception e) {
			log.error("updateOrderCapitalPool ---- 异常,message = "
					+ e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;

	}

	/**
	 * 获取订单资金池及其相关订单的设备信息
	 * 
	 * @author liukh
	 * @date 2017-2-28 上午10:33:43
	 * @param request
	 * @param response
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "/orderCapitalPool/list", method = { RequestMethod.GET })
	@ResponseBody
	public String getOrderCapitalPoolList(HttpServletRequest request,
			HttpServletResponse response, OrderCapitalPoolQueryForm form) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			// jsonStr = baseTMService.getOrderCapitalPoolList(form);
			jsonStr = baseTMService
					.getOrderCapitalPoolAndRentOrderDeviceList(form);
		} catch (Exception e) {
			log.error("getOrderCapitalPoolList ---- 异常,message = "
					+ e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;

	}

	// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<结算单>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	/**
	 * 新增结算单信息
	 * 
	 * @author liukh
	 * @date 2017-2-28 下午3:09:03
	 * @param request
	 * @param response
	 * @param data
	 * @return
	 */
	@RequestMapping(value = "/orderStatement", method = { RequestMethod.POST })
	@ResponseBody
	public String addOrderStatement(HttpServletRequest request,
			HttpServletResponse response, HttpSession session,
			@RequestParam(value = "data", required = true) String data) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			if (session.getAttribute("addOrderStatementProcess") != null) {
				BaseDto dto = new BaseDto();
				dto.setRcode(BaseDto.ERROR_RCODE);
				dto.setRinfo("请求已提交，请耐心等待!");
				return JSON.toJSONString(dto);
			}
			session.setAttribute("addOrderStatementProcess",
					"addOrderStatementProcess");
			jsonStr = baseTMService.addOrderStatementProcess(data);
		} catch (Exception e) {
			log.error("addOrderStatement ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		session.setAttribute("addOrderStatementProcess", null);
		return jsonStr;
	}

	/**
	 * 获取结算单信息
	 * 
	 * @author liukh
	 * @date 2017-2-28 下午3:09:23
	 * @param request
	 * @param response
	 * @param orderStatementId
	 * @return
	 */
	@RequestMapping(value = "/orderStatement/{orderStatementId}", method = { RequestMethod.GET })
	@ResponseBody
	public String getOrderStatement(HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable(value = "orderStatementId") String orderStatementId) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseTMService
					.getOrderStatementDetailInfor(orderStatementId);
		} catch (Exception e) {
			log.error("getOrderStatement ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;

	}

	/**
	 * 结算单确认对账
	 * 
	 * @author liukh
	 * @date 2016-12-29 下午2:29:03
	 * @param request
	 * @param response
	 * @param orderStatementId
	 * @return
	 */
	@RequestMapping(value = "/makeSureOrderStatemetInfo/orderStatement", method = { RequestMethod.POST })
	@ResponseBody
	public String makeSureOrderStatementInfor(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "data", required = true) String data) {
		String jsonStr = "";
		try {

			jsonStr = baseTMService.updateMakeSureOrderStatementInfor(data);

		} catch (Exception e) {
			log.error("makeSureOrderStatementInfor ---- 异常,message = "
					+ e.getMessage());
			e.printStackTrace();
		}
		log.info("makeSureOrderStatementInfor ---- jsonStr ========= "
				+ jsonStr);
		return jsonStr;
	}

	/**
	 * 结算中的支付提示金额是否足够支付
	 * 
	 * @author liukh
	 * @date 2017-3-28 上午10:52:36
	 * @param request
	 * @param response
	 * @param orderStatementId
	 * @return
	 */
	@RequestMapping(value = "/payMoney/{orderStatementId}", method = { RequestMethod.GET })
	@ResponseBody
	public String tipMakeSurePayMoenyTip4OrderStatement(
			HttpServletRequest request, HttpServletResponse response,
			@PathVariable(value = "orderStatementId") String orderStatementId) {
		String jsonStr = "";
		try {
			jsonStr = baseTMService
					.tipMakeSurePayMoenyTip4OrderStatement(orderStatementId);

		} catch (Exception e) {
			log.error("tipMakeSurePayMoenyTip4OrderStatement ---- 异常,message = "
					+ e.getMessage());
			e.printStackTrace();
		}
		log.info("tipMakeSurePayMoenyTip4OrderStatement ---- jsonStr ========= "
				+ jsonStr);
		return jsonStr;

	}

	/**
	 * 修改结算单信息
	 * 
	 * @author liukh
	 * @date 2017-2-28 下午3:09:36
	 * @param orderStatementId
	 * @param data
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/orderStatement/{orderStatementId}", method = { RequestMethod.POST })
	@ResponseBody
	public String updateOrderStatement(
			@PathVariable(value = "orderStatementId") String orderStatementId,
			@RequestParam(value = "data", required = true) String data,
			HttpServletRequest request, HttpServletResponse response) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseTMService
					.updateOrderStatement(orderStatementId, data);
		} catch (Exception e) {
			log.error("updateOrderStatement ---- 异常,message = "
					+ e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;

	}

	/**
	 * 获取结算单信息列表
	 * 
	 * @author liukh
	 * @date 2017-2-28 下午3:09:47
	 * @param request
	 * @param response
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "/orderStatement/list", method = { RequestMethod.GET })
	@ResponseBody
	public String getOrderStatementList(HttpServletRequest request,
			HttpServletResponse response, OrderStatementQueryForm form) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseTMService
					.getOrderStatementContainCompanyNameList(form);
		} catch (Exception e) {
			log.error("getOrderStatementList ---- 异常,message = "
					+ e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;

	}

	/**
	 * 结算单确认收款
	 * 
	 * @author liukh
	 * @date 2017-3-28 下午6:02:51
	 * @param request
	 * @param response
	 * @param session
	 * @param data
	 *            {status,userId:XXX:XXX}
	 * @param orderStatementId
	 * @return
	 */
	@RequestMapping(value = "/makeSureGetMoeny/orderStatement/{orderStatementId}", method = { RequestMethod.POST })
	@ResponseBody
	public String updateOrderStatementAndRentOrderAndReleaseLockMoney(
			HttpServletRequest request, HttpServletResponse response,
			HttpSession session,
			@RequestParam(value = "data", required = true) String data,
			@PathVariable(value = "orderStatementId") String orderStatementId) {
		String jsonStr = "";
		try {
			if (session
					.getAttribute("updateOrderStatementAndRentOrderAndReleaseLockMoney") != null) {
				BaseDto dto = new BaseDto();
				dto.setRcode(BaseDto.ERROR_RCODE);
				dto.setRinfo("请求已提交，请勿重复提交!");
				return JSON.toJSONString(dto);
			}
			session.setAttribute(
					"updateOrderStatementAndRentOrderAndReleaseLockMoney",
					"updateOrderStatementAndRentOrderAndReleaseLockMoney");
			jsonStr = baseTMService
					.updateOrderStatementAndRentOrderAndReleaseLockMoney(
							orderStatementId, data);

		} catch (Exception e) {
			log.error("updateOrderStatementAndRentOrderAndReleaseLockMoney ---- 异常,message = "
					+ e.getMessage());
			e.printStackTrace();
		}
		log.info("updateOrderStatementAndRentOrderAndReleaseLockMoney ---- jsonStr ========= "
				+ jsonStr);
		session.setAttribute(
				"updateOrderStatementAndRentOrderAndReleaseLockMoney", null);
		return jsonStr;
	}

	/**
	 * 结算单支付
	 * 
	 * @author liukh
	 * @date 2017-3-28 下午6:06:31
	 * @param request
	 * @param response
	 * @param session
	 * @param data
	 *            {"orderStatementId":XXXX,"companyId":YYY}
	 * @return
	 */
	@RequestMapping(value = "/orderStatement/pay", method = { RequestMethod.POST })
	@ResponseBody
	public String orderStatementPay(HttpServletRequest request,
			HttpServletResponse response, HttpSession session,
			@RequestParam(value = "data", required = true) String data) {
		String jsonStr = "";
		try {
			if (session.getAttribute("orderStatementPay") != null) {
				BaseDto dto = new BaseDto();
				dto.setRcode(BaseDto.ERROR_RCODE);
				dto.setRinfo("请求已提交，请耐心等待!");
				return JSON.toJSONString(dto);
			}
			session.setAttribute("orderStatementPay", "orderStatementPay");
			jsonStr = baseTMService.updateOrderStatementPay(data);

		} catch (Exception e) {
			log.error("orderStatementPay ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		log.info("orderStatementPay ---- jsonStr ========= " + jsonStr);
		session.setAttribute("orderStatementPay", null);
		return jsonStr;

	}

	// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<订单互动内容模块>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	/**
	 * 新增订单互动内容跟踪信息
	 * 
	 * @author liukh
	 * @date 2017-2-28 下午3:10:05
	 * @param request
	 * @param response
	 * @param data
	 * @return
	 */
	@RequestMapping(value = "/orderInteractiveTrace", method = { RequestMethod.POST })
	@ResponseBody
	public String addOrderInteractiveTrace(HttpServletRequest request,
			HttpServletResponse response, HttpSession session,
			@RequestParam(value = "data", required = true) String data) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {

			if (session.getAttribute("addOrderInteractiveTraceAndMessage") != null) {
				BaseDto dto = new BaseDto();
				dto.setRcode(BaseDto.ERROR_RCODE);
				dto.setRinfo("请求已提交，请耐心等待!");
				return JSON.toJSONString(dto);
			}
			session.setAttribute("addOrderInteractiveTraceAndMessage",
					"addOrderInteractiveTraceAndMessage");
			jsonStr = baseTMService.addOrderInteractiveTraceAndMessage(data);
		} catch (Exception e) {
			log.error("addOrderInteractiveTrace ---- 异常,message = "
					+ e.getMessage());
			e.printStackTrace();
		}
		session.setAttribute("addOrderInteractiveTraceAndMessage", null);
		return jsonStr;
	}

	/**
	 * 获取订单互动内容跟踪信息
	 * 
	 * @author liukh
	 * @date 2017-2-28 下午3:10:34
	 * @param request
	 * @param response
	 * @param orderInteractiveTraceId
	 * @return
	 */
	@RequestMapping(value = "/orderInteractiveTrace/{orderInteractiveTraceId}", method = { RequestMethod.GET })
	@ResponseBody
	public String getOrderInteractiveTrace(
			HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable(value = "orderInteractiveTraceId") String orderInteractiveTraceId) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseTMService
					.getOrderInteractiveTrace(orderInteractiveTraceId);
		} catch (Exception e) {
			log.error("getOrderInteractiveTrace ---- 异常,message = "
					+ e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;

	}

	/**
	 * 订单互动内容跟踪信息列表
	 * 
	 * @author liukh
	 * @date 2017-2-28 下午3:10:50
	 * @param request
	 * @param response
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "/orderInteractiveTrace/list", method = { RequestMethod.GET })
	@ResponseBody
	public String getOrderInteractiveTraceList(HttpServletRequest request,
			HttpServletResponse response, OrderInteractiveTraceQueryForm form) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseTMService.getOrderInteractiveTraceList(form);
		} catch (Exception e) {
			log.error("getOrderInteractiveTraceList ---- 异常,message = "
					+ e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;

	}

	/**
	 * 新增订单评论
	 * 
	 * @author liukh
	 * @date 2017-2-28 上午10:35:51
	 * @param request
	 * @param response
	 * @param data
	 * @return
	 */
	@RequestMapping(value = "/orderComment", method = { RequestMethod.POST })
	@ResponseBody
	public String addOrderComment(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "data", required = true) String data) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseTMService.addOrderCommentAndCheckOrderStatus(data);
		} catch (Exception e) {
			log.error("addOrderComment ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;
	}

	/**
	 * 获取订单评论
	 * 
	 * @author liukh
	 * @date 2017-2-28 上午10:37:25
	 * @param request
	 * @param response
	 * @param orderCommentId
	 * @return
	 */
	@RequestMapping(value = "/orderComment/{orderCommentId}", method = { RequestMethod.GET })
	@ResponseBody
	public String getOrderComment(HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable(value = "orderCommentId") String orderCommentId) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseTMService.getOrderComment(orderCommentId);
		} catch (Exception e) {
			log.error("getOrderComment ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;

	}

	/**
	 * 修改订单评论
	 * 
	 * @author liukh
	 * @date 2017-2-28 上午10:39:10
	 * @param orderCommentId
	 * @param data
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/orderComment/{orderCommentId}", method = { RequestMethod.POST })
	@ResponseBody
	public String updateOrderComment(
			@PathVariable(value = "orderCommentId") String orderCommentId,
			@RequestParam(value = "data", required = true) String data,
			HttpServletRequest request, HttpServletResponse response) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseTMService.updateOrderComment(orderCommentId, data);
		} catch (Exception e) {
			log.error("updateOrderComment ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;

	}

	/**
	 * 获取订单评论列表
	 * 
	 * @author liukh
	 * @date 2017-2-28 上午10:40:44
	 * @param request
	 * @param response
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "/orderComment/list", method = { RequestMethod.GET })
	@ResponseBody
	public String getOrderCommentList(HttpServletRequest request,
			HttpServletResponse response, OrderCommentQueryForm form) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseTMService.getOrderCommentList(form);
		} catch (Exception e) {
			log.error("getOrderCommentList ---- 异常,message = " + e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;

	}

	/**
	 * 获取某个公司的评价数量
	 * 
	 * @author liukh
	 * @date 2017-1-9 上午11:22:05
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/orderComment/count", method = { RequestMethod.GET })
	@ResponseBody
	public String getOrderCommentCount(HttpServletRequest request,
			HttpServletResponse response, OrderCommentQueryForm form) {
		String jsonStr = "";
		try {
			jsonStr = baseTMService.getOrderCommentCount(form);
		} catch (Exception e) {
			log.error("getOrderCommentCount ---- 异常,message = "
					+ e.getMessage());
			e.printStackTrace();
		}
		log.info("getOrderCommentCount ---- jsonStr ========= " + jsonStr);
		return jsonStr;
	}

	/**
	 * 获取公司的综合评价 总评价相加/总评论数，取整
	 * 
	 * @author liukh
	 * @date 2017-3-27 下午5:39:53
	 * @param request
	 * @param response
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "/comprehensive/orderComment", method = { RequestMethod.GET })
	@ResponseBody
	public String getComprehensiveOrderComment(HttpServletRequest request,
			HttpServletResponse response, OrderCommentQueryForm form) {
		String jsonStr = "";
		try {
			jsonStr = baseTMService.getComprehensiveOrderComment(form);
		} catch (Exception e) {
			log.error("getComprehensiveOrderComment ---- 异常,message = "
					+ e.getMessage());
			e.printStackTrace();
		}
		log.info("getComprehensiveOrderComment ---- jsonStr ========= "
				+ jsonStr);
		return jsonStr;
	}

	/**
	 * 新增订单进度跟踪
	 * 
	 * @author liukh
	 * @date 2017-2-28 上午10:42:16
	 * @param request
	 * @param response
	 * @param data
	 * @return
	 */
	@RequestMapping(value = "/orderProgressTrace", method = { RequestMethod.POST })
	@ResponseBody
	public String addOrderProgressTrace(HttpServletRequest request,
			HttpServletResponse response, HttpSession session,
			@RequestParam(value = "data", required = true) String data) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			if (session.getAttribute("addOrderProgressTrace") != null) {
				BaseDto dto = new BaseDto();
				dto.setRcode(BaseDto.ERROR_RCODE);
				dto.setRinfo("请求已提交，请耐心等待!");
				return JSON.toJSONString(dto);
			}
			session.setAttribute("addOrderProgressTrace",
					"addOrderProgressTrace");
			jsonStr = baseTMService.addOrderProgressTrace(data);
		} catch (Exception e) {
			log.error("addOrderProgressTrace ---- 异常,message = "
					+ e.getMessage());
			e.printStackTrace();
		}
		session.setAttribute("addOrderProgressTrace", null);
		return jsonStr;
	}

	/**
	 * 获取订单进度跟踪列表
	 * 
	 * @author liukh
	 * @date 2017-2-28 上午10:43:21
	 * @param request
	 * @param response
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "/orderProgressTrace/list", method = { RequestMethod.GET })
	@ResponseBody
	public String getOrderProgressTraceList(HttpServletRequest request,
			HttpServletResponse response, OrderProgressTraceQueryForm form) {
		String jsonStr = JSON.toJSONString(new BaseDto());
		try {
			jsonStr = baseTMService.getOrderProgressTraceList(form);
		} catch (Exception e) {
			log.error("getOrderProgressTraceList ---- 异常,message = "
					+ e.getMessage());
			e.printStackTrace();
		}
		return jsonStr;

	}

	// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<待办统计>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	/**
	 * 租赁方待办统计
	 * 
	 * @author liukh
	 * @date 2017-3-29 下午4:02:05
	 * @param request
	 * @param response
	 * @param companyId
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/waitWork/leasingSide", method = { RequestMethod.GET })
	@ResponseBody
	public String waitWorkingAmount4LeasingSide(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "companyId", required = true) String companyId,
			@RequestParam(value = "userId", required = true) String userId) {
		String jsonStr = "";
		try {
			jsonStr = baseTMService.waitWorkingAmount4LeasingSide(companyId,
					userId);

		} catch (Exception e) {
			log.error("waitWorkingAmount4LeasingSide ---- 异常,message = "
					+ e.getMessage());
			e.printStackTrace();
		}
		log.info("waitWorkingAmount4LeasingSide ---- jsonStr ========= "
				+ jsonStr);
		return jsonStr;

	}

	/**
	 * 承租方待办统计
	 * 
	 * @author liukh
	 * @date 2017-3-29 下午4:02:38
	 * @param request
	 * @param response
	 * @param companyId
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/waitWork/lesseeSide", method = { RequestMethod.GET })
	@ResponseBody
	public String waitWorkingAmount4LesseeSide(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "companyId", required = true) String companyId,
			@RequestParam(value = "userId", required = true) String userId) {
		String jsonStr = "";
		try {
			jsonStr = baseTMService.waitWorkingAmount4LesseeSide(companyId,
					userId);

		} catch (Exception e) {
			log.error("waitWorkingAmount4LesseeSide ---- 异常,message = "
					+ e.getMessage());
			e.printStackTrace();
		}
		log.info("waitWorkingAmount4LesseeSide ---- jsonStr ========= "
				+ jsonStr);
		return jsonStr;

	}

}
