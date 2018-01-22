package com.zj.common;

public class Constant {
	/**
	 * 0：充值 1：结算收款 2：结算付款 3、提现
	 */
	public static final int ACCOUNTDETAIL_BUSSINESS_TOPUP = 0;
	public static final int ACCOUNTDETAIL_BUSSINESS_GATHERING = 1;
	public static final int ACCOUNTDETAIL_BUSSINESS_PAYMENT = 2;
	public static final int ACCOUNTDETAIL_BUSSINESS_WITHDRAW = 3;
	

	/**
	 * 结算单业务（增加，减少）
	 */
	public static final String ACCOUNTDETAIL_EXPENSETYPE_ADD = "0";
	public static final String ACCOUNTDETAIL_EXPENSETYPE_SUBTRACTION = "1";
	
	/**
	 * 租赁订单来源 0:询价单转化 1：求租需求单转化 2：抛单转化
	 */
	public static final Integer ORDERSOURCETYPE_INQUERYORDER = 0;

	public static final Integer ORDERSOURCETYPE_INQUERYRENT = 1;

	public static final Integer ORDERSOURCETYPE_INQUERYRENTTHROW = 2;
	
	/**
	 * 订单状态 0：待确认 ---> 已下单 1：待支付 2：待出库 3：出库 4：服务中 5:待结算 6：部分结算 7：结束 8:结算中 9:已评论
	 */

	public static final Integer IRENTORDER_STATUS_CREATE = 0;
	public static final Integer RENTORDER_STATUS_UNPAY = 1;
	public static final Integer RENTORDER_STATUS_UNGO = 2;
	public static final Integer RENTORDER_STATUS_GO = 3;
	public static final Integer RENTORDER_STATUS_WORKING = 4;
	public static final Integer RENTORDER_STATUS_UNBALANCE = 5;
	public static final Integer RENTORDER_STATUS_PARTBALANCE = 6;
	public static final Integer RENTORDER_STATUS_OVER = 7;
	public static final Integer RENTORDER_STATUS_BALANCEING = 8;
	public static final Integer RENTORDER_STATUS_COMMENT = 9;
	
	/**
	 * 支付方式 0：协商付款 1：线上支付
	 * 
	 */

	public static final String PAYMETHOD_CONSULTWITH = "0";
	public static final String PAYMETHOD_ONLINE = "1";
	
	/**
	 * 资金池状态 0:锁定 1：解锁
	 */
	public static final Integer ORDERCAPITALPOOLPOJO_LOCKED = 0;
	public static final Integer ORDERCAPITALPOOLPOJO_UNLOCKED = 1;
	
	/**
	 * 资金池类型 0:预付款 1：保证金
	 */
	public static final Integer ORDERCAPITALPOOLPOJO_PREVENTION = 0;
	public static final Integer ORDERCAPITALPOOLPOJO_MARGIN = 1;
	public static final String ORDERCAPITALPOOLPOJO_PREVENTION_TIP = "预付款";
	public static final String ORDERCAPITALPOOLPOJO_MARGIN_TIP = "保证金";
	
	
	/**
	 * 公司性质 0：租赁方 1：承租方 公司性质 0：出租方 1：承租方
	 * 
	 */

	public static final String COMPANY_BUSSINESS_LEASINGSID = "0";
	public static final String COMPANY_BUSSINESS_LESSEESIDE = "1";
	
	/*
	 * 结算单类型 0:阶段性结算 1：结项结算
	 */
	public static final int ORDERSTATEMENT_STATUS_SOMETIME = 0;
	public static final int ORDERSTATEMENT_STATUS_ALLTIME = 1;
	
	/**
	 * 订单互动类型 1、询价单 2、结算单
	 */
	public static final String RELATEDSTATEMENTTYPE_INQUERYORDER = "1";
	public static final String RELATEDSTATEMENTTYPE_ORDERSTATEMENT = "2";

}
