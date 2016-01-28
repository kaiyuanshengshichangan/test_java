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
import com.ck.platform.business.customer.service.CustomerInfoService;

public class CustomerInfoServiceTest extends BaseInterfaceTest {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(CustomerInfoServiceTest.class);


	@Resource
	CustomerInfoService service;

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCreate() {
		Map paramMap = new HashMap();
		paramMap.put("customerId", 123456);
		paramMap.put("age", 12);
		paramMap.put("sex", 1);
		RepObject resp=null;
		try {
			resp = service.create(paramMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("----------"+resp);
	}
	
	@Test
	public void testGetList() {
		Map paramMap = new HashMap();
		paramMap.put("customerId", 123456);

		List<HashMap> result= service.getList(paramMap);

		System.out.println("----------"+result);
	}
	
	@Test
	public void testUpdate() {
		Map paramMap = new HashMap();
		paramMap.put("customerId", 123456);
		paramMap.put("province","234234");
		RepObject result= service.updateInfo(paramMap);

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

}