package com.ck.business.system.service;

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
import com.ck.platform.business.system.service.SystemUserService;

public class SystemUserServiceTest extends BaseInterfaceTest {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(SystemUserServiceTest.class);


	@Resource
	SystemUserService service;

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCreate() {
		Map paramMap = new HashMap();
		paramMap.put("username", "123123dd");
		paramMap.put("nickname", "123233");
		paramMap.put("age", 12);
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
		paramMap.put("trueName", "王");
		List<HashMap> result= service.getList(paramMap);

		System.out.println("----------"+result);
	}
	
	@Test
	public void testUpdate() {
		Map paramMap = new HashMap();
		paramMap.put("id", 3);
		paramMap.put("trueName", "王凯");
		paramMap.put("age", 31);
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

}