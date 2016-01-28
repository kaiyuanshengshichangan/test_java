package com.ck.platform.business.log.resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ck.platform.base.entity.RepObject;
import com.ck.platform.base.util.ConvertUtil;
import com.ck.platform.base.util.core.page.Page;
import com.ck.platform.business.log.service.ACopyTestService;
/**
 * 订单基本信息接口
 * @author lyq
 * @date 2015-8-1
 */
@Component("aCopyTestResource")
@Path("/test")
public class ACopyTestResource {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(ACopyTestResource.class);

	@Autowired
	ACopyTestService orderInfoservice;
	
	/**
	 * 接口说明：创建
	 * 
	 * @param orderNo
	 * @return
	 * @throws Exception
	 */

	@POST
	@Path("/create")
	@Produces({ MediaType.APPLICATION_JSON })
	public RepObject create(Map paramMap) {
		return orderInfoservice.create(paramMap);
	}

	/**
	 * 接口说明：创建
	 * 
	 * @param orderNo
	 * @return
	 * @throws Exception
	 */

	@POST
	@Path("/createOrder")
	@Produces({ MediaType.APPLICATION_JSON })
	public RepObject createOrder(Map paramMap) {
		return orderInfoservice.create(paramMap);
	}
	
	
	/**
	 * 接口说明：信息变更
	 * 
	 * @param Map
	 *            paramMap
	 * @return
	 */
	@PUT
	@Path("/update")
	@Produces({ MediaType.APPLICATION_JSON })
	public RepObject update(Map paramMap) {

		return orderInfoservice.update(paramMap);

	}

	/**
	 * 
	 * 接口说明：根据ID获取信息
	 * 
	 * @param id
	 * @return
	 */
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/getById/{id}")
	public HashMap getById(@PathParam("id") String id) {
		HashMap result=orderInfoservice.getById(id);
		return result;
	}

	/**
	 * 接口说明： 获取符合条件的数据信息列表(分页模式)
	 * 
	 * @param orderNo
	 * @return
	 * @throws Exception
	 */
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/getPageList")
	public Page getPageList(@QueryParam("queryJson") String queryJson) {

		return orderInfoservice.getPageList(ConvertUtil.jsonStringToHashMap(queryJson));
	}

	/**
	 * 
	 * @return List
	 * @throws Exception
	 */
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/getList")
	public List<HashMap> getList(@QueryParam("queryJson") String queryJson) {

		return orderInfoservice.getList(ConvertUtil.jsonStringToHashMap(queryJson));
	}

	/**
	 * 
	 * @return Integer
	 * @throws Exception
	 */
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/getCount")
	public Integer getCount(@QueryParam("queryJson") String queryJson) {
		return orderInfoservice.getCount(ConvertUtil.jsonStringToHashMap(queryJson));
	}

}