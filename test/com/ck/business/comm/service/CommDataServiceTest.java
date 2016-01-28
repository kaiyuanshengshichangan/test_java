package com.ck.business.comm.service;

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
import com.ck.platform.business.comm.service.CommDataService;

public class CommDataServiceTest extends BaseInterfaceTest {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(CommDataServiceTest.class);

	@Resource
	CommDataService service;

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCreate() {
		Map paramMap = new HashMap();
		paramMap.put("dataType", "1");
		paramMap.put("dataValue", 123);
		paramMap.put("dataName", "23ewe");
		RepObject resp=null;
		try {
			resp = service.create(paramMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("----------"+resp.getStatus());
	}
	
	@Test
	public void testGetList() {
		Map paramMap = new HashMap();
		paramMap.put("dataType", 1);
		paramMap.put("firstResult", 0);
		paramMap.put("maxResult", 10);
		List<HashMap> result= service.getList(paramMap);
		System.out.println("----------"+result);
	}
	@Test
	public void testUpdate() {
		Map paramMap = new HashMap();
		
		paramMap.put("regionName", "test2");
		paramMap.put("regionId", 123);
		paramMap.put("treeLevel", 3);
		RepObject result= service.update(paramMap);

		System.out.println("----------"+result.getStatus());
	}
	
	@Test
	public void testGetById() {
		Map paramMap = new HashMap();
		Map result= service.getById("1");

		System.out.println("----------"+result);
	}
	
	@Test
	public void testGetPageList() {
		Map paramMap = new HashMap();
		paramMap.put("regionId", 123);
		Page result= service.getPageList(paramMap);

		System.out.println("----------"+result.getItems());
	}

}