package com.zj.access.run;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 * @desc 
 * @author liukh
 * @date 2016-12-2 下午5:28:01
 */
public class ServiceProvider {
	

	/**
	 * @desc 
	 * @author liukh
	 * @date 2016-12-2 下午5:28:01
	 * @param args
	 */
	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "classpath:spring/spring-*.xml"});  
        context.start();
        run(context);
	}
	
	public static InetAddress getInetAddress() {
		try {
			return InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			System.out.println("unknown host!");
		}
		return null;

	}

	public static String getHostName(InetAddress netAddress) {
		if (null == netAddress) {
			return null;
		}
		String hostName = netAddress.getHostName(); // get the ip address
		return hostName;
	}
	
	public static void run(ClassPathXmlApplicationContext context) {

		try {
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					try {
					System.out.println("The ServiceProvider staring .........");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			}, 1000 * 10, 1000 * 10);

		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	/*
	public static void run(ClassPathXmlApplicationContext context) {
		final RedisSessionManager redisSessionManager = (RedisSessionManager) context.getBean("redisSessionManager");
		final IWashService washService = (IWashService) context.getBean("washService");
		redisSessionManager.putString("isDeling", String.valueOf(false));
		try {
			// 30分钟超时，取消订单
			new Timer().schedule(new TimerTask() {
				@SuppressWarnings("unchecked")
				@Override
				public void run() {
					try {
						if(Constant.HOSTNAME.indexOf(getHostName(getInetAddress())) != -1) {
							List<OrderBo> orderList = redisSessionManager.getListObjAll("orderIds");

							// 移除为0的orderId
							List<OrderBo> tmpList = new ArrayList<OrderBo>();
							if (orderList != null && orderList.size() > 0) {
								for (OrderBo bo : orderList) {
									if (!StringUtils.equals(bo.getOrderId(), "0")) {
										tmpList.add(bo);
									}
								}
								redisSessionManager.putList("orderIds", tmpList);
							}
							// 从新增订单的队列中，取出新增的orderId，增加到orderIds队列中
							if(redisSessionManager.getString("isDeling") != null && !Boolean.valueOf(redisSessionManager.getString("isDeling"))) {
								redisSessionManager.putString("isDeling", String.valueOf(true));
								List<OrderBo> newOrderList = redisSessionManager.getListObjAll("newOrderIds");
								if (newOrderList != null && newOrderList.size() > 0) {
									if (tmpList != null && tmpList.size() > 0) {
										newOrderList.addAll(tmpList);
									}
									redisSessionManager.putList("orderIds", newOrderList);
									redisSessionManager.putList("newOrderIds", new ArrayList<OrderBo>());
								}
								redisSessionManager.putString("isDeling", String.valueOf(false));
							}
							
							// 超时取消订单
							orderList = redisSessionManager.getListObjAll("orderIds");
							boolean exist = true;
							List<OrderBo> list = new ArrayList<OrderBo>();
							String result = null;
							JSONObject resultObj = null;
							if (orderList != null && orderList.size() > 0) {
								for (OrderBo bo : orderList) {
									if (!StringUtils.equals(bo.getOrderId(), "0")) {
										exist = redisSessionManager.exists(bo.getOrderId());
										if (exist) {// 未超时
											list.add(bo);
										} else {
											JSONObject jp = new JSONObject();
											jp.put("orderId", StringUtils.trim(bo.getOrderId()));
											String data = jp.toJSONString();
											data = EncryptUtils.encrypt(data);
											result = washService.updateOrder(data);// 取消订单可用存储过程处理，当前量不大，故省略
											resultObj = JSONObject.parseObject(result);
											if (resultObj.getInteger("rcode") == 0) {
												log.info("订单【" + bo.getOrderId() + "】已取消");
											}
										}
									}
								}
								redisSessionManager.putList("orderIds", list);
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			}, 1000 * 10, 1000 * 10);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			redisSessionManager.putString("isDeling", String.valueOf(false));
		}
	}
	*/

}
