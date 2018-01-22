package com.zj.base.common;

import java.math.BigDecimal;

/**
 * 普通常量
 * @author tanjh
 * @date 2016-8-18 下午1:41:57
 */
public class Constant {

	/** 状态：正常 */
	public static final String STATUS_NORMAL = "0";
	/** 状态：禁用 */
	public static final String STATUS_DISABLE = "1";
	/** 状态：锁定 */
	public static final String STATUS_LOCKED = "2";
	/** 充值*/
	public static final String TYPE_RECHARGE = "0";
	/** 提现*/
	public static final String TYPE_WITHDRAW = "1";
	/**已下单*/
	public static final String WASHING_STATUS_READY = "0";
	/**使用中*/
	public static final String WASHING_STATUS_USE = "1";
	/**订单完成*/
	public static final String WASHING_STATUS_COMPLETE = "2";
	/**订单取消*/
	public static final String WASHING_STATUS_CANCEL = "3";
	/**订单关闭*/
	public static final String WASHING_STATUS_CLOSE = "4";
	/**距离最大相差*/
	public static final Double DISTANCE_DIFF = 3000.0;
	/**空闲 */
	public static final String WASHING_FREE = "1";
	/**工作 */
	public static final String WASHING_BUSY = "0";
	
	

	/**
	 * 有效
	 */
	public static final String STATUS_VALID = "0";
	/**
	 * 无效
	 */
	public static final String STATUS_INVALID = "1";
	
	/**
	 * 环境变量key
	 */
	public static final String ENV = "ENVIRONMENT";

	/**
	 * shiro start
	 */
	/** 加密类型*/
	public static final String ALGORITHMNAME = "MD5";
	/** 加密循环次数*/
	public static final int HASHITERATIONS = 2;
	/**
	 * shiro end
	 */
	/**
	 * 左方菜单名称
	 */
	public static final String MENU = "MENU";
	/**
	 * 分页
	 */
	public static final String ATTRIBUTE_ITEMPAGE = "ITEMPAGE";
	/**
	 * 用户
	 */
	public static final String SYSTEM_VISITOR = "SYSTEM_VISITOR";
	/**
	 * FTP BIZ
	 */
	public static final String FTP_BIZ = "http://api.chebaotec.com/biz";
	/**
	 * FTP APP
	 */
	public static String FTP_APP = "http://api.chebaotec.com/app";

	/**
	 * http 连接超时
	 */
	public static final int HTTP_CONNECT_TIMEOUT = 10000;
	/**
	 * http 请求超时
	 */
	public static final int HTTP_REQUEST_TIMEOUT = 10000;
	/**
	 * http socket链接超时
	 */
	public static final int HTTP_SOCKET_TIMEOUT = 10000;
	/**
	 * http 编码
	 */
	public static final String HTTP_ENCODING = "UTF-8";
	/**
	 * http content type
	 */
	public static final String HTTP_CONTENT_TYPE = "application/json";
	
	/**
	 * Redis
	 */
	public static final String WASH_REDIS_ROLE = "washRole";
	public static final String WASH_REDIS_ROLE_PRIVILGES = "washRolePrevilges";
	public static final String WASH_REDIS_MODULE = "washModule";
	public static final String WASH_REDIS_LEVEL = "washLevel";
	public static final String WASH_REDIS_TAGS = "washTags";
	public static final String WASH_REDIS_INDUSTRY_TAGS = "washIndustryTags";
	//读的redis
	public static final String REDIS_SLAVE_READ_KEY = "jedisShardInfoSlave";
	//写的redis
	public static final String REDIS_MASTER_WRITE_KEY = "jedisShardInfoMaster";
	//第几个库
	public static final Integer REDIS_DB = 7;
	

	/**
	 * app key
	 */
	public static final String KEY = "key:washing.com/android.apk";
	
	/**
	 * 系统用户信息以及系统处理feedback的配置
	 */
	public static final String SYSTEM_NAME = "系统";
	public static final String SYSTEM_ID = "0";
	public static final String SYSTEM_DISPOSE_STATUS = "1";
	/**辅助程序*/
	public static final String ASSIS_TYPE = "1";
	/**主程序*/
	public static final String MAIN_TYPE = "0";
	
	/**数据字典*/
    public static final String ROOT_OF_DATAMAPPING = "ROOT";

    public static final String DATAMAPPING_ROOT_SYS_LEVEL = "SYS_LEVEL";
    public static final String DATAMAPPING_ROOT_TORF = "TORF";
    public static final String DATAMAPPING_ROOT_ENABLED = "ENABLED";
    public static final String DATAMAPPING_ROOT_GENDER = "GENDER";
    public static final String DATAMAPPING_ROOT_FORMULA_CLASS = "FORMULACLS";
    public static final String DATAMAPPING_ROOT_TRADETYPE = "TRADETYPE";
    public static final String DATAMAPPING_ROOT_PRODUCT_EXCEL_HEADER = "PRODUCTXLS";
    public static final String DATAMAPPING_ROOT_COST_CONFIRM_DAY = "COSTCONFIRMDAY";
    public static final String DATAMAPPING_ROOT_BIZ_TYPE = "BIZTYPE";
    public static final String DATAMAPPING_ROOT_CURRENCY = "CURRENCY";
    public static final String DATAMAPPING_ROOT_CURRENCY_SYMBOL = "CURSYMBOL";
    public static final String DATAMAPPING_ROOT_COUNTRY = "SHIPCOUNTRY";
	
	
	


	/*
	 * 加密的key
	 */
	public static final String JSON_RESPONSE_KEY = "key:zhaojinet.com/android.apk";

	/**
	 * 抛单抢单时监视keys
	 */
	public static final String WATCHKEYS = "watchkeys";
	public static final String WATCHKEYS_RENTTHROW = "watchkeys4RentThrow";
	
	/**
	 * 设备上传token
	 */
	public static final String APP_DEVICEUPLOAD_TOKEN = "DEVICEUPLOAD";
	/**
	 * 设备上传token
	 */
	public static final String APP_USERUPLOAD_TOKEN = "USERUPLOAD";

	/*
	 * 请求成功及描述
	 */
	public static final Integer JSON_RESPONSE_STATUS_SUCCESS = 0;

	public static final Integer USER_LOGON_ERROR_USERNOTEXIST = 61;
	public static final String USER_LOGON_ERROR_USERNOTEXIST_INFOR = "您输入的账号不存在,请重新输入!";

	public static final Integer USER_LOGON_ERROR_PASSWORDNOTMATCH = 62;
	public static final String USER_LOGON_ERROR_PASSWORDNOTMATCH_INFOR = "您输入的密码和账户名不匹配,请重新输入!";

	public static final String JSON_RESPONSE_STATUS_SUCCESS_INFOR = "request sucess";
	/*
	 * 请求不成功及描述
	 */
	public static final Integer JSON_RESPONSE_STATUS_ERROR = 21;

	public static final String JSON_RESPONSE_STATUS_ERROR_INFOR = "request error or request data is null";

	/**
	 * 没有符合要求的数据
	 */
	public static final int NO_DATA_RCODE = 55;
	/**
	 * 所用的账号在客户端登录的时候匹配
	 */
	public static final int NOT_MATCH_COMPANYBUSINESSTYPE = 22;

	public static final String LOGON_LEASINGSIDE = "你所用的账号属于机主端账号，请在找机网-机主端登录 !";
	public static final String LOGON_LESSEESIDE = "你所用的账号属于找机网账号，请在找机网客户端登录 !";
	/**
	 * 抢单时，没有匹配的设备
	 */
	public static final int NOT_HAVE_MATCH_DEVICE_RCODE = 25;
	public static final String NOT_HAVE_MATCH_DEVICE_RINF = "您没有充足的设备满足此订单！";

	/**
	 * 默认没有图片的空路径
	 */
	public static final String REQUIRED_PICTURE_TEMP = "picture temp path";
	/**
	 * 身份证或者营业执照暂未添加标识
	 */
	public static final String COMPANY_INFOR_TENP = "0";



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
	 * 询价单的状态 0：草稿 1：询价中 2：待确认 3：确认订单（结束）---- 承租方确认下单 4：关闭询价（结束）---- 承租方结束询价
	 * 询价单的状态 0：询价车 1：询价中 2：已报价 3：确认订单（结束）---- 承租方确认下单 4：关闭询价（结束）---- 承租方结束询价
	 */
	public static final Integer INQUERYORDER_STATUS_CREATE = 0;
	public static final Integer INQUERYORDER_STATUS_REPLY = 1;
	public static final Integer INQUERYORDER_STATUS_UNMAKESURE = 2;
	public static final Integer INQUERYORDER_STATUS_MAKEORDER = 3;
	public static final Integer INQUERYORDER_STATUS_OVER = 4;

	/**
	 * 求租需求单的状态 0：待报价 1：已报价 2：已结束 3、待新的报价 求租需求单的状态 0：待报价 1：已报价 2：已结束 3、待新的报价
	 */
	public static final Integer INQUERYRENT_STATUS_CREATE = 0;
	public static final Integer INQUERYRENT_STATUS_MAKESURE = 1;
	public static final Integer INQUERYRENT_STATUS_OVER = 2;
	public static final Integer INQUERYRENT_STATUS_WAIT = 3;

	/**
	 * 求租需求单报价单的状态 0：待确认 1：已确认（选中） 2：为未选中 求租需求单报价单的状态 0：竞标中 1：已中标 2：未中标
	 */
	public static final Integer INQUERYRENTQUOTE_STATUS_CREATE = 0;
	public static final Integer INQUERYRENTQUOTE_STATUS_MAKESURE = 1;
	public static final Integer INQUERYRENTQUOTE_STATUS_OVER = 2;

	/**
	 * 抛单状态 0 待抢单 1 已接单 2 关闭 抛单状态 0 待抢单 1 已接单 2 关闭
	 */
	public static final Integer INQUERYRENTTHROW_STATUS_CREATE = 0;
	public static final Integer INQUERYRENTTHROW_STATUS_MAKESURE = 1;
	public static final Integer INQUERYRENTTHROW_STATUS_CLOSE = 2;

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
	 * 租赁方保证金扣除的金额
	 */
	public static final BigDecimal MARGIN_AMOUNT = new BigDecimal(2000);

	// 时间工具类参数
	public static final int MONTH_DAYS = 30;
	public static final String DATETYPE_DAY = "1";
	public static final String DATETYPE_MONTH = "0";
	public static final String DATEFORMATTYPE = "yyyy-MM-dd";
	public static final String DATE_DAY = "day";
	public static final String DATE_MONTH = "month";
	public static final String DATE_YEAR = "year";

	public static final Integer BILLINGTYPE_MONTH = 0;
	public static final Integer BILLINGTYPE_DAY = 1;

	/**
	 * A:统计设备总数 B:真实设备总数 C:虚拟设备总数
	 */
	public static final String DEVICE_STATUS_TOTLA = "A";
	public static final String DEVICE_STATUS_TOTLAISREAL = "B";
	public static final String DEVICE_STATUS_TOTLANOTISREAL = "C";

	/**
	 * 自有设备各种状态数量 B0：自有设备待审核 B1：自有设备审核已通过 B2：自有设备审核未通过 B3：自有设备已租 B4：自有设备审核待租
	 * B5：自有设备下架
	 */
	public static final String DEVICE_STATUS_TOTLAISREAL_CREAT = "B0";
	public static final String DEVICE_STATUS_TOTLAISREAL_CHECKPASS = "B1";
	public static final String DEVICE_STATUS_TOTLAISREAL_CHECKNOPASS = "B2";
	public static final String DEVICE_STATUS_TOTLAISREAL_WORING = "B3";
	public static final String DEVICE_STATUS_TOTLAISREAL_WAIT = "B4";
	public static final String DEVICE_STATUS_TOTLAISREAL_BELOW_WORING = "B5";
	/**
	 * 外借设备各种状态数量 B3：外借设备已租 B4：外借设备审核待租 B5：外借设备下架
	 */
	public static final String DEVICE_STATUS_TOTLANOTISREAL_WORING = "C3";
	public static final String DEVICE_STATUS_TOTLANOTISREAL_WAIT = "C4";
	public static final String DEVICE_STATUS_TOTLANOTISREAL_BELOW_WORING = "C5";

	/**
	 * 设备状态 0：待审核 1：审核通过 2：审核不通过 3、已租 4、待租 5、线下出租 设备状态 
	 * 0：待审核 1：审核通过 2：审核不通过 3、已租 4、待租 5、下架
	 * 
	 */

	public static final int DEVICE_STATUS_CREAT = 0;
	public static final int DEVICE_STATUS_CHECKPASS = 1;
	public static final int DEVICE_STATUS_CHECKNOPASS = 2;
	public static final int DEVICE_STATUS_WORING = 3;
	public static final int DEVICE_STATUS_WAIT = 4;
	public static final int DEVICE_STATUS_BELOW_WORING = 5;

	/**
	 * 设备的真实性 0:真实设备 1：虚拟设备
	 */

	public static final int DEVICE_TRUTH_TRUE = 0;
	public static final int DEVICE_TRUTH_FALSE = 1;

	/**
	 * 用户的状态 0:有效 1：黑名单
	 */

	public static final int USER_STATUS_TAKEEFFICACY = 0;
	public static final int USER_STATUS_LOSEEFFICACY = 1;

	/**
	 * 用户所属公司的状态 0:待审核 1：审核通过 2：审核不通过
	 */

	public static final int COMAPNY_STATUS_CREATE = 0;
	public static final int COMAPNY_STATUS_TAKEEFFICACY = 1;
	public static final int COMAPNY_STATUS_NOPASS = 2;

	/**
	 * 公司所属类型 0：公司 1：个人
	 */
	public static final int COMPANY_TYPE_COMPANY = 0;
	public static final int COMPANY_TYPE_PERSON = 1;

	/**
	 * 公司性质 0：租赁方 1：承租方 公司性质 0：出租方 1：承租方
	 * 
	 */

	public static final String COMPANY_BUSSINESS_LEASINGSID = "0";
	public static final String COMPANY_BUSSINESS_LESSEESIDE = "1";

	/**
	 * 结算单状态 0：待确认 1：异议回复 2、待支付（确认结算） 3、待确认收款 4、结束
	 * 
	 */
	public static final int ORDERSTATEMENT_STATUS_CREATE = 0;
	public static final int ORDERSTATEMENT_STATUS_QUESTION = 1;
	public static final int ORDERSTATEMENT_STATUS_WAITPAY = 2;
	public static final int ORDERSTATEMENT_STATUS_WAITMAKESURE = 3;
	public static final int ORDERSTATEMENT_STATUS_OVER = 4;

	/**
	 * 结算单类型 0:阶段性结算 1：结项结算
	 */
	public static final int ORDERSTATEMENT_STATUS_SOMETIME = 0;
	public static final int ORDERSTATEMENT_STATUS_ALLTIME = 1;

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
	 * 内部消息类型 0、系统消息 1、询价单 2、求租需求单 3、求租需求单报价 4、抛单 5、租赁订单-询价单 6、租赁订单-求租需求单
	 * 7、租赁订单-求租需求单报价 8、租赁订单-抛单 9、出库单 10、结算单 11、订单评论
	 */
	public static final String MESSAGE_TYPE_SYSTEM = "0";
	public static final String MESSAGE_TYPE_INQUERYORDER = "1";
	public static final String MESSAGE_TYPE_INQUERYRENT = "2";
	public static final String MESSAGE_TYPE_INQUERYRENTQUOTE = "3";
	public static final String MESSAGE_TYPE_INQUERYRENTTHROW = "4";
	public static final String MESSAGE_TYPE_RENTORDER_INQUERYORDER = "5";
	public static final String MESSAGE_TYPE_RENTORDER_INQUERYRENT = "6";
	public static final String MESSAGE_TYPE_RENTORDER_INQUERYRENTQUOTE = "7";
	public static final String MESSAGE_TYPE_RENTORDER_INQUERYRENTTHROW = "8";
	public static final String MESSAGE_TYPE_DELIVERYORDER = "9";
	public static final String MESSAGE_TYPE_ORDERSTATEMENT = "10";
	public static final String MESSAGE_TYPE_ORDERCOMMENT = "11";
	/**
	 * 订单互动类型 1、询价单 2、结算单
	 */
	public static final String RELATEDSTATEMENTTYPE_INQUERYORDER = "1";
	public static final String RELATEDSTATEMENTTYPE_ORDERSTATEMENT = "2";
	/**
	 * 内部消息类型 0：未读 1：已读
	 */
	public static final Integer MESSAGE_STATUS_UNREAD = 0;
	public static final Integer MESSAGE_STATUS_READED = 1;

	/**
	 * 待办类型 0 待报价 1：待下单 2：待下单----询价反馈 3：待下单 ----求租反馈 4：待出库 5：待结算 6：待结算 -- 确认结算
	 * 7：待结算 -- 确认收款 8：待支付
	 */
	public static final String WAITWORK_TYPE_QUOTE = "0";
	public static final String WAITWORK_TYPE_CREATEORDER = "1";
	public static final String WAITWORK_TYPE_CREATEORDER_INQUERYORDER = "2";
	public static final String WAITWORK_TYPE_CREATEORDER_RENTORDER = "3";
	public static final String WAITWORK_TYPE_WAITOUTBOUNDORDER = "4";
	public static final String WAITWORK_TYPE_WAITSTATEMENT = "5";
	public static final String WAITWORK_TYPE_WAITSTATEMENTMAKESURE = "6";
	public static final String WAITWORK_TYPE_WAITSTATEMENTGETMONEY = "7";
	public static final String WAITWORK_TYPE_WAITPAY = "8";
	public static final String WAITWORK_TYPE_WAITEVALUATE = "9";

	/**
	 * 短信下发历史状态 0:未发送 1：已发送
	 */
	public static final Integer SENDCODE_USEND = 0;
	public static final Integer SENDCODE_SENDED = 1;
	/**
	 * 单价标识
	 */
	public static final String PRICETAG_MONTH = " 元/月";
	public static final String PRICETAG_DAY = " 元/天";

	/**
	 * log记录
	 */
	public static final String LOG_LOCAL_FILE_PATH = "E:\\log";
	/**
	 * 0:登录 1:找机设备 2:求租需求单 3：抛单 4：项目信息
	 */
	public static final String LOG_BUSINESSNAME_LOGON = "0";
	public static final String LOG_BUSINESSNAME_COMPANYDEVICE = "1";
	public static final String LOG_BUSINESSNAME_RENTORDER = "2";
	public static final String LOG_BUSINESSNAME_RENTTHROWORDER = "3";
	public static final String LOG_BUSINESSNAME_PROJECT = "4";

	/**
	 * 操作类型 0：登录 1：查看
	 */
	public static final String LOG_PROCESSTYPENAME_LOGON = "0";
	public static final String LOG_PROCESSTYPENAME_VIEW = "1";
	/**
	 * 操作端类型 0：出租方 1：承租方
	 * 
	 */
	public static final String LOG_CLIENTSIDE_LEASING = "0";
	public static final String LOG_CLIENTSIDE_LESSEE = "1";
	/**
	 * 日志来源 0：android 1:ios 2:web
	 */
	public static final String LOG_CLIENTSIDETYPE_ANDROID = "0";
	public static final String LOG_CLIENTSIDETYPE_IOS = "1";
	public static final String LOG_CLIENTSIDETYPE_WEB = "2";
	/**
	 * 日志操作者 0：游客
	 */
	public static final String LOG_OPERATOR_VISITOR = "0";

	public static final String dataFormat1 = "yyyy-MM-dd";
	public static final String dataFormat4 = "yyyy-MM-dd HH:mm:ss";
	
	public static final Integer DEFAULTPAGEINDEX = 1;
	public static final Integer DEFAULTPAGESIZE = 10;
	/**
	 * 订单评论的状态
	 */
	public static final Integer ORDERCOMMENT_STATUS_CREATE = 0;
	/**
	 * 审核信息类型 0 公司 1设备
	 */
	public static final Integer AUDIT_TYPE_COMPANY= 0;
	public static final Integer AUDIT_TYPE_DEVICE= 1;

}
