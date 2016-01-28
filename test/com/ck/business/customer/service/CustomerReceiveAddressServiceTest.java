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
import com.ck.platform.business.customer.service.CustomerReceiveAddressService;

public class CustomerReceiveAddressServiceTest extends BaseInterfaceTest {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(CustomerReceiveAddressServiceTest.class);


	@Resource
	CustomerReceiveAddressService service;

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
   		paramMap.put("receiveName", "大大");
   		paramMap.put("receiveMobile", "18203312121");
   		paramMap.put("provinceId", "112");
   		paramMap.put("isDefault", "1");
   		paramMap.put("customerId","439645262386");
   		RepObject br = service.create(paramMap);
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
		paramMap.put("id", 3);
		paramMap.put("isDefault", 1);
		List<HashMap> result= service.getList(paramMap);
		System.out.println("----------"+result);
	}
	
	@Test
	public void testUpdate() {
		Map paramMap = new HashMap();
		paramMap.put("id", 3);
		paramMap.put("isDefault", 1);
		RepObject br= service.update(paramMap);

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
	

}