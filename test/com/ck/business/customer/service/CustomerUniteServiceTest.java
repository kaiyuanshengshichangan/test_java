package com.ck.business.customer.service;

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
import com.ck.platform.business.customer.service.CustomerUniteService;

public class CustomerUniteServiceTest extends BaseInterfaceTest {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(CustomerUniteServiceTest.class);


	@Resource
	CustomerUniteService service;

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCreate() {
   		try {
		Map paramMap=new HashMap();
   		paramMap.put("email", "aa@a.com");
   		paramMap.put("mobile", "18203312121");
   		paramMap.put("password", "123123");
   		paramMap.put("nickname", "1");
   		paramMap.put("registIp", 2);
   		paramMap.put("registSource", "3");
   		RepObject br = service.createCustomer(paramMap);
   		System.out.println("---------"+br.getStatus());
   		System.out.println("---------"+br.getCode());
   		System.out.println("---------"+br.getMsg());
   		}catch(Exception e){
   			e.printStackTrace();
   		}
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
		paramMap.put("customerId", "439645262386");
		paramMap.put("nickname", "123123");
		paramMap.put("password", "123456");
		paramMap.put("oldPassword", "123123");
		RepObject br= service.updateCustomer(paramMap);

		System.out.println("---------"+br.getStatus());
   		System.out.println("---------"+br.getCode());
   		System.out.println("---------"+br.getMsg());
   		System.out.println("---------"+br.getData());
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
	public void testLogin() {
		Map paramMap = new HashMap();
		paramMap.put("account", "aa@a.com");
		paramMap.put("password", 123453);
		RepObject br= service.executeLogin(paramMap);

		System.out.println("---------"+br.getStatus());
   		System.out.println("---------"+br.getCode());
   		System.out.println("---------"+br.getMsg());
   		System.out.println("---------"+br.getData());
	}

}