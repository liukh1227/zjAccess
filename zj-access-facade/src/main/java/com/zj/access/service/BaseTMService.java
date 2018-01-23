package com.zj.access.service;

import java.math.BigDecimal;
import java.util.Map;

import com.zj.entity.base.po.InqueryOrder;
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

public interface BaseTMService {

	/**
	 * 新增询价单
	 * 
	 * @author liukh
	 * @date 2017-2-23 下午2:45:19
	 * @param data
	 * @return
	 */
	public String addInqueryOrder(String data);

	/**
	 * 加入询价车时，如果存在草稿下的询价车，则返回此询价车，如果不存在，则新增一个询价单，并返回询价单Id
	 * 
	 * @author liukh
	 * @date 2017-3-21 下午4:52:08
	 * @param data
	 * @return
	 */
	public String addOrQueryInqueryOrder(String data);

	/**
	 * 获取询价单
	 * 
	 * @author liukh
	 * @date 2017-2-23 下午2:45:40
	 * @param inqueryOrderId
	 * @return
	 */
	public String getInqueryOrder(String inqueryOrderId);

	/**
	 * 更新询价单
	 * 
	 * @author liukh
	 * @date 2017-2-23 下午2:46:17
	 * @param inqueryOrderId
	 * @param data
	 * @return
	 */
	public String updateInqueryOrder(String inqueryOrderId, String data);

	/**
	 * 删除询价单
	 * 
	 * @author liukh
	 * @date 2017-2-23 下午2:46:54
	 * @param inqueryOrderId
	 * @return
	 */
	public String deleteInqueryOrder(String inqueryOrderId);

	/**
	 * 获取询价单列表
	 * 
	 * @author liukh
	 * @date 2017-2-23 下午2:55:05
	 * @param leasingSideId
	 * @param lesseeSideId
	 * @param city
	 * @param currentHandler
	 * @param payMethod
	 * @param projectId
	 * @param status
	 * @param startDate
	 * @param endDate
	 * @param numberOfItem
	 * @param page
	 * @return
	 */
	public String getInqueryOrderList(InqueryOrderQueryForm form);

	/**
	 * 获取询价单和询价单设备列表
	 * 
	 * @author liukh
	 * @date 2017-3-21 下午3:19:53
	 * @param form
	 * @return
	 */
	public String getInqueryOrderAndDeviceList(String inqueryOrderId);
	
	public String getInqueryOrderListAndDeviceList(InqueryOrderQueryForm form);

	/**
	 * 询价单立即下单(待租设备详细中)
	 * 
	 * @author liukh
	 * @date 2017-3-21 下午6:05:13
	 * @param data
	 * @return
	 */
	public String addFastInqueryOrderAndDevice(String data);

	/**
	 * 询价单立即下单
	 * 
	 * @author liukh
	 * @date 2017-3-21 下午6:04:51
	 * @param inqueryOrderId
	 * @param data
	 * @return
	 */
	public String addFastInqueryOrderToRentOrder(String inqueryOrderId,
			String data);

	/**
	 * 修改询价单报价
	 * 
	 * @author liukh
	 * @date 2017-3-21 下午6:03:37
	 * @param inqueryOrderId
	 * @param data
	 * @return
	 */
	public String updateInqueryOrderQuote(String inqueryOrderId, String data);

	/**
	 * 保存询价单报价
	 * 
	 * @author liukh
	 * @date 2017-3-21 下午6:03:06
	 * @param inqueryOrderId
	 * @param data
	 * @return
	 */
	public String updateSaveQuotoInqueryOrder(String inqueryOrderId, String data);

	/**
	 * 获取询价单设备的时候包含时间类型
	 * 
	 * @author liukh
	 * @date 2017-3-21 下午6:02:32
	 * @param inqueryOrderId
	 * @return
	 */
	public String getInqueryOrderAndDeviceContainDateList(String inqueryOrderId);

	/**
	 * 询价设备加入询价车的时候，判断如果询价车中已经有此设备了， 比较型号,工期类型，工期 则更改询价设备的数量和金额，否则新增询价设备
	 * 
	 * @author liukh
	 * @date 2017-3-21 下午6:01:11
	 * @param leasingSideId
	 * @param data
	 * @return
	 */
	public String addOrUpdateWhenHaveInqueryDevice(String leasingSideId,
			String data);

	/**
	 * 更改询价单设备（数量的加减）
	 * 
	 * @author liukh
	 * @date 2017-3-21 下午5:59:12
	 * @param inqueryDeviceId
	 * @param data
	 * @return
	 */
	public String updateInqueryDevice4Quantity(String inqueryDeviceId,
			String data);

	/**
	 * 修改询价设备单价
	 * 
	 * @author liukh
	 * @date 2017-3-21 下午5:58:02
	 * @param inqueryDeviceId
	 * @param data
	 * @return
	 */
	public String updateInqueryDevice4UnitPrice(String inqueryDeviceId,
			String data);

	/**
	 * 修改询价车设备价格预览
	 * 
	 * @author liukh
	 * @date 2017-3-21 下午5:55:21
	 * @param data
	 *            ：{ "data":[ {"inqueryDeviceId":"XXXXXX","unitPrice":"1000"},
	 *            {"inqueryDeviceId":"XXXXXX","unitPrice":"1000"},
	 *            {"inqueryDeviceId":"XXXXXX","unitPrice":"1000"} ] }
	 * @return
	 */
	public String updateInqueryDevice4UnitPricePreview(String data);

	/**
	 * 根据公司设备型号，数量，单价 计算设备的价格
	 * 
	 * @author liukh
	 * @date 2017-3-21 下午5:56:42
	 * @param data
	 * @return
	 */
	public String calculatePriceUseDeviceModel(String data);

	/**
	 * 获取询价车中处于草稿状态下的数量
	 * 
	 * @author liukh
	 * @date 2017-3-21 下午5:54:27
	 * @param params
	 * @return
	 */
	public String getInqueryOrderCarCount(InqueryOrderQueryForm form);

	/**
	 * 新增询价单设备
	 * 
	 * @author liukh
	 * @date 2017-2-23 下午2:45:19
	 * @param data
	 * @return
	 */
	public String addInqueryDevice(String data);

	/**
	 * 获取询价单设备
	 * 
	 * @author liukh
	 * @date 2017-2-23 下午2:45:40
	 * @param inqueryDeviceId
	 * @return
	 */
	public String getInqueryDevice(String inqueryDeviceId);

	/**
	 * 更新询价单设备
	 * 
	 * @author liukh
	 * @date 2017-2-23 下午2:46:17
	 * @param inqueryDeviceId
	 * @param data
	 * @return
	 */
	public String updateInqueryDevice(String inqueryDeviceId, String data);

	/**
	 * 删除询价单设备
	 * 
	 * @author liukh
	 * @date 2017-2-23 下午2:46:54
	 * @param inqueryDeviceId
	 * @return
	 */
	public String deleteInqueryDevice(String inqueryDeviceId);

	/**
	 * 删除询价单设备及更新询价单
	 * 
	 * @author liukh
	 * @date 2017-3-21 下午7:22:38
	 * @param inqueryDeviceId
	 * @return
	 */
	public String deleteInqueryDeviceAndUpdateInqueryOrderTotalPrice(
			String inqueryDeviceId);

	/**
	 * 获取询价单设备列表
	 * 
	 * @author liukh
	 * @date 2017-2-23 下午2:57:00
	 * @param inqueryOrderId
	 * @param deviceModelId
	 * @param numberOfItem
	 * @param page
	 * @return
	 */
	public String getInqueryDeviceList(InqueryDeviceQueryForm form);

	/**
	 * 询价单马上下单
	 * 
	 * @author liukh
	 * @date 2017-3-22 上午10:24:53
	 * @param data
	 * @return
	 */
	public String addCreateRentOrder4InqueryOrder(String data);

	/**
	 * 使用InqueryOrder 生产订单
	 * 
	 * @author liukh
	 * @date 2017-3-23 下午5:16:31
	 * @param inqueryOrder
	 * @return
	 */
	public String addCreateRentOrderUseInqueryOrder(InqueryOrder inqueryOrder);


	/**
	 * 提交询价单
	 * 
	 * @author liukh
	 * @date 2017-3-22 下午5:15:07
	 * @param inqueryOrderId
	 * @param data
	 * @return
	 */
	public String updateCommitInqueryOrder(String inqueryOrderId, String data);

	/**
	 * 计算询价设备的总价
	 * 
	 * @author liukh
	 * @date 2017-3-22 下午5:58:09
	 * @param inqueryDeviceId
	 * @param unitPrice
	 * @return
	 */
	public BigDecimal calculateDeviceTotalPriceByChangeUnitPrice(
			String inqueryDeviceId, BigDecimal unitPrice);

	// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<求租需求单模块>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	/**
	 * 新增求租需求单
	 * 
	 * @author liukh
	 * @date 2017-2-23 下午2:58:15
	 * @param data
	 * @return
	 */

	public String addInqueryRent(String data);

	/**
	 * 获取求租需求单
	 * 
	 * @author liukh
	 * @date 2017-2-23 下午2:45:40
	 * @param inqueryRentId
	 * @return
	 */
	public String getInqueryRent(String inqueryRentId);

	/**
	 * 获取求租需求单的详细信息
	 * 
	 * @author liukh
	 * @date 2017-3-24 上午11:49:44
	 * @param inqueryRentId
	 * @return
	 */
	public String getInqueryRentContainSpaceDataAndDateType(String inqueryRentId);

	/**
	 * 获取求租需求单及某个租赁方对应的报价的详细信息
	 * 
	 * @author liukh
	 * @date 2017-3-24 下午2:17:03
	 * @param params
	 * @return
	 */
	public String getInqueryRentAndInqueryRentQuotoDetaliInfor(
			Map<String, Object> params);

	/**
	 * 查询求租需求单列表的时候包含关键参数属性,并且是可以报价的(状态为待报价和已报价，且不包含自己公司已经报价的)
	 * 
	 * @author liukh
	 * @date 2017-3-24 下午3:06:39
	 * @param params
	 * @return
	 */
	public String getCanQuotoInqueryRentContainSpaceDataList(
			InqueryRentQueryForm form);

	/**
	 * 获取求租需求单及关联的报价列表
	 * 
	 * @author liukh
	 * @date 2017-3-24 下午3:48:22
	 * @param form
	 * @return
	 */
	public String getInqueryRentListAndInqueryRentQuoteList(
			InqueryRentQueryForm form);

	/**
	 * 更新求租需求单
	 * 
	 * @author liukh
	 * @date 2017-2-23 下午2:46:17
	 * @param inqueryRentId
	 * @param data
	 * @return
	 */
	public String updateInqueryRent(String inqueryRentId, String data);

	/**
	 * 获取求租需求单列表
	 * 
	 * @author liukh
	 * @date 2017-2-23 下午3:04:00
	 * @param lesseeSideId
	 * @param removeQuotedCompanyId
	 * @param deviceTypeId
	 * @param parentDeviceTypeId
	 * @param startDate
	 * @param endDate
	 * @param payMethod
	 * @param city
	 * @param status
	 * @param projectId
	 * @param numberOfItem
	 * @param page
	 * @return
	 */
	public String getInqueryRentList(InqueryRentQueryForm form);

	/**
	 * 新增求租需求单报价
	 * 
	 * @author liukh
	 * @date 2017-2-23 下午3:05:29
	 * @param data
	 * @return
	 */

	public String addInqueryRentQuote(String data);

	/**
	 * 新增求租需求单报价，并对比总价
	 * 
	 * @author liukh
	 * @date 2017-3-24 下午4:13:29
	 * @param data
	 * @return
	 */
	public String addInqueryRentQuoteComapreTotalPrice(String data);

	/**
	 * 根据求租需求单的报价计算出总的报价
	 * 
	 * @author liukh
	 * @date 2017-3-24 下午5:25:33
	 * @param data
	 * @return
	 */
	public String getOrderCalculatePrice(String data);

	/**
	 * 获取求租需求单报价
	 * 
	 * @author liukh
	 * @date 2017-2-23 下午3:06:32
	 * @param inqueryRentQuoteId
	 * @return
	 */
	public String getInqueryRentQuote(String inqueryRentQuoteId);

	/**
	 * 更新求租需求单报价
	 * 
	 * @author liukh
	 * @date 2017-2-23 下午3:07:11
	 * @param inqueryRentQuoteId
	 * @param data
	 * @return
	 */
	public String updateInqueryRentQuote(String inqueryRentQuoteId, String data);

	/**
	 * 获取求租需求单报价列表
	 * 
	 * @author liukh
	 * @date 2017-2-23 下午3:09:18
	 * @param inqueryRentId
	 * @param deviceModelId
	 * @param payMethod
	 * @param leasingSideId
	 * @param status
	 * @param currentHandler
	 * @param numberOfItem
	 * @param page
	 * @return
	 */
	public String getInqueryRentQuoteList(InqueryRentQuoteQueryForm form);

	/**
	 * 获取求租需求单和对应报价列表（一对一，适用于租赁方我的求租需求单报价）
	 * 
	 * @author liukh
	 * @date 2017-3-24 下午6:27:10
	 * @param form
	 * @return
	 */
	public String getInqueryRentQuoteAndInqueryRentList(
			InqueryRentQuoteQueryForm form);

	/**
	 * 获取求租需求单和对应的报价单复合信息
	 * 
	 * @author liukh
	 * @date 2017-3-25 上午10:04:20
	 * @param inqueryRentQuoteId
	 * @return
	 */
	public String getInqueryRentQuotoAndInqueryRent(String inqueryRentQuoteId);

	/**
	 * 忽略求租需求单报价
	 * 
	 * @author liukh
	 * @date 2017-3-25 上午10:05:18
	 * @param inqueryRentQuoteId
	 * @return
	 */
	public String updateIgnoreInqueryRentQuoto(String inqueryRentQuoteId);

	/**
	 * 求租需求单生成订单
	 * 
	 * @author liukh
	 * @date 2017-3-25 上午11:07:19
	 * @param data
	 * @return
	 */
	public String addCreateRentOrder4InqueryRent(String data);

	// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<抛单>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	/**
	 * 新增求租抛单
	 * 
	 * @author liukh
	 * @date 2017-2-23 下午3:10:57
	 * @param data
	 * @return
	 */
	public String addInqueryRentThrow(String data);

	/**
	 * 获取求租抛单
	 * 
	 * @author liukh
	 * @date 2017-2-23 下午3:11:42
	 * @param inqueryRentThrowId
	 * @return
	 */
	public String getInqueryRentThrow(String inqueryRentThrowId);

	/**
	 * 更新求租抛单
	 * 
	 * @author liukh
	 * @date 2017-2-23 下午3:13:09
	 * @param inqueryRentThrowId
	 * @param data
	 * @return
	 */
	public String updateInqueryRentThrow(String inqueryRentThrowId, String data);

	/**
	 * 获取求租抛单列表
	 * 
	 * @author liukh
	 * @date 2017-2-23 下午3:15:09
	 * @param lesseeSideId
	 * @param deviceTypeId
	 * @param parentDeviceTypeId
	 * @param startDate
	 * @param endDate
	 * @param city
	 * @param status
	 * @param responseLeasingSideId
	 * @param removeCompanyId
	 * @param projectId
	 * @param numberOfItem
	 * @param page
	 * @return
	 */
	public String getInqueryRentThrowList(InqueryRentThrowQueryForm form);

	/**
	 * 新增租赁订单
	 * 
	 * @author liukh
	 * @date 2017-2-23 下午3:29:28
	 * @param data
	 * @return
	 */
	public String addRentOrder(String data);

	/**
	 * 获取租赁订单
	 * 
	 * @author liukh
	 * @date 2017-2-23 下午3:30:03
	 * @param rentOrderId
	 * @return
	 */
	public String getRentOrder(String rentOrderId);

	/**
	 * 获取租赁订单及设备列表信息
	 * 
	 * @author liukh
	 * @date 2017-3-27 上午10:21:31
	 * @param rentOrderId
	 * @return
	 */
	public String getRentOrderAndRentOrderDeviceList(String rentOrderId);

	/**
	 * 更新租赁订单
	 * 
	 * @author liukh
	 * @date 2017-2-23 下午3:30:38
	 * @param rentOrderId
	 * @param data
	 * @return
	 */
	public String updateRentOrder(String rentOrderId, String data);

	/**
	 * 获取租赁订单列表
	 * 
	 * @author liukh
	 * @date 2017-2-23 下午3:48:27
	 * @param leasingSideId
	 * @param lesseeSideId
	 * @param city
	 * @param currentHandler
	 * @param payMethod
	 * @param projectId
	 * @param status
	 * @param startDate
	 * @param endDate
	 * @param numberOfItem
	 * @param page
	 * @return
	 */
	public String getRentOrderList(RentOrderQueryForm form);

	/**
	 * 获取租赁订单列表及设备列表
	 * 
	 * @author liukh
	 * @date 2017-3-27 上午10:20:13
	 * @param form
	 * @return
	 */
	public String getRentOrderListAndRentOrderDeviceList(
			RentOrderQueryForm form);

	/**
	 * 新增租赁订单设备
	 * 
	 * @author liukh
	 * @date 2017-2-23 下午3:50:26
	 * @param data
	 * @return
	 */
	public String addRentOrderDevice(String data);

	/**
	 * 获取租赁订单设备
	 * 
	 * @author liukh
	 * @date 2017-2-23 下午3:50:26
	 * @param rentOrderDeviceId
	 * @return
	 */
	public String getRentOrderDevice(String rentOrderDeviceId);

	/**
	 * 获取租赁订单设备列表
	 * 
	 * @author liukh
	 * @date 2017-2-23 下午3:53:30
	 * @param rentOrderId
	 * @return
	 */
	public String getRentOrderDeviceList(RentOrderDeviceQueryForm form);

	/**
	 * 新增订单出库单
	 * 
	 * @author liukh
	 * @date 2017-2-23 下午3:55:34
	 * @param data
	 * @return
	 */
	public String addDeliveryOrder(String data);

	/**
	 * 新增订单出库单及改变租赁订单的状态
	 * 
	 * @author liukh
	 * @date 2017-3-27 上午10:51:29
	 * @param data
	 *            : { data : [{ "rentOrderId" : "XXX", "rentOrderDeviceId" :
	 *            "XXX", "status" : YYY, "creator" : "YYYY", "data" : [{ "id" :
	 *            "XXXX", "deviceName" : "YYYY" }, { "id" : "XXXX", "deviceName"
	 *            : "YYYY" } ] } ] }
	 * @return
	 */
	public String addDeliveryOrderAndUpdateRentOrderStatus(String data);

	/**
	 * 获取订单出库单
	 * 
	 * @author liukh
	 * @date 2017-2-23 下午3:50:26
	 * @param deliveryOrderId
	 * @return
	 */
	public String getDeliveryOrder(String deliveryOrderId);

	/**
	 * 修改订单出库单
	 * 
	 * @author liukh
	 * @date 2017-2-23 下午4:13:31
	 * @param deliveryOrderId
	 * @param data
	 * @return
	 */
	public String updateDeliveryOrder(String deliveryOrderId, String data);

	/**
	 * 获取订单出库单列表
	 * 
	 * @author liukh
	 * @date 2017-2-23 下午4:11:48
	 * @param rentOrderDeviceId
	 * @param rentOrderId
	 * @param status
	 * @param numberOfItem
	 * @param page
	 * @return
	 */
	public String getDeliveryOrderList(DeliveryOrderQueryForm form);

	/**
	 * 新增订单资金池
	 * 
	 * @author liukh
	 * @date 2017-2-23 下午4:16:15
	 * @param data
	 * @return
	 */
	public String addOrderCapitalPool(String data);

	/**
	 * 获取订单资金池
	 * 
	 * @author liukh
	 * @date 2017-2-23 下午4:16:59
	 * @param orderCapitalPoolId
	 * @return
	 */
	public String getOrderCapitalPool(String orderCapitalPoolId);

	/**
	 * 修改订单资金池
	 * 
	 * @author liukh
	 * @date 2017-2-23 下午4:13:31
	 * @param deliveryOrderId
	 * @param data
	 * @return
	 */
	public String updateOrderCapitalPool(String orderCapitalPoolId, String data);

	/**
	 * 获取订单资金池列表
	 * 
	 * @author liukh
	 * @date 2017-2-23 下午4:19:34
	 * @param capitalSideId
	 * @param rentOrderId
	 * @param status
	 * @param numberOfItem
	 * @param page
	 * @return
	 */
	public String getOrderCapitalPoolList(OrderCapitalPoolQueryForm form);

	/**
	 * 获取订单资金池及其相关的订单的设备信息
	 * 
	 * @author liukh
	 * @date 2017-3-27 下午4:24:53
	 * @param form
	 * @return
	 */
	public String getOrderCapitalPoolAndRentOrderDeviceList(
			OrderCapitalPoolQueryForm form);

	// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<结算单>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	/**
	 * 新增结算单
	 * 
	 * @author liukh
	 * @date 2017-2-23 下午4:21:17
	 * @param data
	 * @return
	 */
	public String addOrderStatement(String data);

	public String addOrderStatementProcess(String data);

	/**
	 * 获取新增结算单
	 * 
	 * @author liukh
	 * @date 2017-2-23 下午4:16:59
	 * @param orderStatementId
	 * @return
	 */
	public String getOrderStatement(String orderStatementId);

	/**
	 * 获取结算单的详情
	 * 
	 * @author liukh
	 * @date 2017-3-27 下午6:55:54
	 * @param orderStatementId
	 * @return
	 */
	public String getOrderStatementDetailInfor(String orderStatementId);

	/**
	 * 修改结算单
	 * 
	 * @author liukh
	 * @date 2017-2-23 下午4:13:31
	 * @param orderStatementId
	 * @param data
	 * @return
	 */
	public String updateOrderStatement(String orderStatementId, String data);

	/**
	 * 结算单确认对账
	 * 
	 * @author liukh
	 * @date 2017-3-27 下午6:50:13
	 * @param data
	 * @return
	 */
	public String updateMakeSureOrderStatementInfor(String data);

	/**
	 * 结算中的支付提示金额是否足够支付
	 * 
	 * @author liukh
	 * @date 2017-3-27 下午6:52:10
	 * @param orderStatementId
	 * @return
	 */
	public String tipMakeSurePayMoenyTip4OrderStatement(String orderStatementId);

	/**
	 * 结算单确认收款
	 * 
	 * @author liukh
	 * @date 2017-3-27 下午6:54:03
	 * @param orderStatementId
	 * @param data
	 *            {status,loginId:XXX}
	 * @return
	 */
	public String updateOrderStatementAndRentOrderAndReleaseLockMoney(
			String orderStatementId, String data);

	/**
	 * 结算单支付
	 * 
	 * @author liukh
	 * @date 2017-3-27 下午6:55:14
	 * @param data
	 * @return
	 */
	public String updateOrderStatementPay(String data);

	/**
	 * 获取结算单列表
	 * 
	 * @author liukh
	 * @date 2017-2-23 下午4:23:16
	 * @param rentOrderId
	 * @param statementType
	 * @param status
	 * @param currentHandler
	 * @param numberOfItem
	 * @param page
	 * @return
	 */
	public String getOrderStatementList(OrderStatementQueryForm form);

	public String getOrderStatementContainCompanyNameList(
			OrderStatementQueryForm form);

	// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	/**
	 * 新增订单互动内容跟踪
	 * 
	 * @author liukh
	 * @date 2017-2-23 下午4:25:49
	 * @param data
	 * @return
	 */
	public String addOrderInteractiveTrace(String data);
	/**
	 * 新增订单互动内容跟踪,并增加内部消息
	 * @author liukh
	 * @date 2017-3-28 下午6:47:13
	 * @param data
	 * @return
	 */
	public String addOrderInteractiveTraceAndMessage(String data);

	/**
	 * 获取订单互动内容跟踪
	 * 
	 * @author liukh
	 * @date 2017-2-23 下午4:16:59
	 * @param orderInteractiveTraceId
	 * @return
	 */
	public String getOrderInteractiveTrace(String orderInteractiveTraceId);

	/**
	 * 获取订单互动内容跟踪列表
	 * 
	 * @author liukh
	 * @date 2017-2-23 下午4:28:43
	 * @param relatedStatementId
	 * @param numberOfItem
	 * @param page
	 * @return
	 */
	public String getOrderInteractiveTraceList(
			OrderInteractiveTraceQueryForm form);

	/**
	 * 获取某公司的评价数量
	 * 
	 * @author liukh
	 * @date 2017-3-27 下午5:42:48
	 * @param form
	 * @return
	 */
	public String getOrderCommentCount(OrderCommentQueryForm form);

	/**
	 * 获取某公司的综合评价
	 * 
	 * @author liukh
	 * @date 2017-3-27 下午5:43:22
	 * @param form
	 * @return
	 */
	public String getComprehensiveOrderComment(OrderCommentQueryForm form);

	/**
	 * 新增订单评论
	 * 
	 * @author liukh
	 * @date 2017-2-23 下午4:29:55
	 * @param data
	 * @return
	 */
	public String addOrderComment(String data);

	/**
	 * 新增订单评论并加成订单是否评论过
	 * 
	 * @author liukh
	 * @date 2017-3-27 下午6:24:56
	 * @param data
	 * @return
	 */
	public String addOrderCommentAndCheckOrderStatus(String data);

	/**
	 * 获取订单评论
	 * 
	 * @author liukh
	 * @date 2017-2-23 下午4:16:59
	 * @param orderCommentId
	 * @return
	 */
	public String getOrderComment(String orderCommentId);

	/**
	 * 修改订单评论
	 * 
	 * @author liukh
	 * @date 2017-2-23 下午4:13:31
	 * @param orderCommentId
	 * @param data
	 * @return
	 */
	public String updateOrderComment(String orderCommentId, String data);

	/**
	 * 获取订单评论列表
	 * 
	 * @author liukh
	 * @date 2017-2-23 下午4:32:46
	 * @param beCommentedCompanyId
	 * @param commentSideId
	 * @param rentOrderId
	 * @param star
	 * @param status
	 * @return
	 */
	public String getOrderCommentList(OrderCommentQueryForm form);

	/**
	 * 新增订单进度跟踪
	 * 
	 * @author liukh
	 * @date 2017-2-23 下午4:36:06
	 * @param data
	 * @return
	 */
	public String addOrderProgressTrace(String data);

	/**
	 * 获取订单进度跟踪
	 * 
	 * @author liukh
	 * @date 2017-2-23 下午4:39:33
	 * @param handlerId
	 * @param orderId
	 * @param status
	 * @param numberOfItem
	 * @param page
	 * @return
	 */
	public String getOrderProgressTraceList(OrderProgressTraceQueryForm form);
	//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<待办统计>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	/**
	 * 租赁方待办统计
	 * @author liukh
	 * @date 2017-3-29 上午9:12:57
	 * @param companyId
	 * @param userId
	 * @return
	 */
	public String waitWorkingAmount4LeasingSide(String companyId,String userId);
	/**
	 * 承租方待办统计
	 * @author liukh
	 * @date 2017-3-29 上午9:12:25
	 * @param companyId
	 * @param userId
	 * @return
	 */
	public String waitWorkingAmount4LesseeSide(String companyId,String userId);

	// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<扣款提示>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	/**
	 * 
	 * @author liukh
	 * @date 2017-3-25 上午10:48:57
	 * @param data
	 * @return
	 */
	public String tipMakeSureLockedMoenyTip4PreventionWhenInqueryOrder2RentOrder(
			String data);

	public String tipLockedMoneyWhenFastCreateOrder(String data);

	public String tipMakeSureLockedMoenyTip4IqueryRentPrevention(String data);

	public String tipMakeSureLockedMoenyTip4IqueryThrowPrevention(String data);

	public String tipMakeSureLockedMoenyTip4Margin(String data);

	public String tip4InqueryRentThrowDoHaveDeviceHaveMoneyMargin(
			String inqueryRentThrowId, String companyId);

	public String tipLockedMoney(String companyId, String lockedType,
			BigDecimal lockedAmout);

	// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<其他>>>>>>>>>>>>>>>>>>>>>>>

	public boolean isPeerRentOrder(String rentOrderId);
	
	// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<校验机器>>>>>>>>>>>>>>>>>>>>>>>
	public String queryInspectionDevice(String data);

}
