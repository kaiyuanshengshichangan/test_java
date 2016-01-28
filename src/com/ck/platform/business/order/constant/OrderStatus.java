package com.ck.platform.business.order.constant;


public class OrderStatus {
	public static final String WAITING_PAYMENT = "1"; // 订单提交、待付款
	public static final String WAITING_SEND = "2"; // 已付款、待发货
	public static final String WAITING_RECEIVE = "3"; // 待收货
	public static final String RECEIVE= "4"; // 已收货
	public static final String CANCEL = "5"; // 已取消
	public static final String COMPLETE= "99"; // 已完成
	
}
