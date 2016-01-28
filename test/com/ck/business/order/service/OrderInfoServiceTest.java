package com.ck.business.order.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.ck.business.BaseInterfaceTest;
import com.ck.platform.base.entity.RepObject;
import com.ck.platform.base.util.core.page.Page;
import com.ck.platform.business.order.service.OrderInfoService;

public class OrderInfoServiceTest extends BaseInterfaceTest {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(OrderInfoServiceTest.class);


	@Resource
	OrderInfoService service;

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCreate() {
		Map paramMap = new HashMap();
		paramMap.put("customerId", 12345);
		paramMap.put("orderId", "123123123123");
		RepObject resp=null;
		try {
			resp = service.createOrder(paramMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("----------"+resp);
	}
	
	@Test
	public void testGetList() {
		Map paramMap = new HashMap();
		paramMap.put("customerId", 123);

		List<HashMap> result= service.getList(paramMap);

		System.out.println("----------"+result);
	}
	
	@Test
	public void testUpdate() {
		Map paramMap = new HashMap();
		paramMap.put("customerId", 123);
		paramMap.put("isValid", 0);
		RepObject result= service.update(paramMap);

		System.out.println("----------"+result);
	}
	
	@Test
	public void testGetById() {
		Map paramMap = new HashMap();
		Map result= service.getById("2");

		System.out.println("----------"+result);
	}
	
	@Test
	public void testGetPageList() {
		Map paramMap = new HashMap();
		paramMap.put("customerId", 123);
		paramMap.put("firstResult", 1);
		paramMap.put("maxResult", 2);
		Page result= service.getPageList(paramMap);

		System.out.println("----------"+result.getItems());
	}
	
	@Test
	public void testCreateOrder() {
		try{
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("productIds", "123123123-1&999999999-2");
		paramMap.put("order.discountMoney", "2");
		paramMap.put("order.customerId", "1231234567");
		paramMap.put("order.email", "aa@a.com");
		paramMap.put("order.mobile", "1892121212");
		paramMap.put("order.orderName", "test order");
		paramMap.put("order.payMode", "1");
		paramMap.put("order.receiveName", "wang kai");
		paramMap.put("order.receiveMobile", "18209990202");
		paramMap.put("order.zipCode", "123123");
		paramMap.put("order.provinceId", "100");
		paramMap.put("order.province", "100==");
		paramMap.put("order.cityId", "120");
		paramMap.put("order.city", "120==");
		paramMap.put("order.districtId", "111");
		paramMap.put("order.district", "111==");
		paramMap.put("order.receiveAddress", "asfawerqwefwqefsdfawsfawefawefasf");
		paramMap.put("order.siteId", "5");
		paramMap.put("order.isInvoice", "0");
		paramMap.put("order.freight", "10");
		paramMap.put("inv.receiveName", "wang kai3");
		paramMap.put("inv.receiveMobile", "182099902023");
		paramMap.put("inv.zipCode", "1231233");
		paramMap.put("inv.provinceId", "1003");
		paramMap.put("inv.province", "100==3");
		paramMap.put("inv.cityId", "1203");
		paramMap.put("inv.city", "120==3");
		paramMap.put("inv.districtId", "1113");
		paramMap.put("inv.district", "111==3");
		paramMap.put("inv.receiveAddress", "asfawerqwefwqefsdfawsfawefawefasf3&23");
		paramMap.put("inv.invTitle", "asfaawefasf3");
		paramMap.put("inv.invContent", "asfsfawefawefasf3");
		paramMap.put("inv.invType", "1");
		// 创建新记录
		RepObject br = service.createOrder(paramMap);
		System.out.println(br.getStatus());
		System.out.println(br.getCode());
		System.out.println(br.getMsg());
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}