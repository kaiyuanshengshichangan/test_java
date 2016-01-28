package com.ck.platform.business.customer.resource;

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

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ck.platform.base.constants.RepDataPropety;
import com.ck.platform.base.constants.ResponseCode;
import com.ck.platform.base.entity.RepObject;
import com.ck.platform.base.util.ConvertUtil;
import com.ck.platform.base.util.core.page.Page;
import com.ck.platform.base.util.object.StringTool;
import com.ck.platform.business.customer.service.CustomerReceiveAddressService;
/**
 * 会员联合登录接口
 * @author lyq
 * @date 2015-8-12
 */
@Component("customerReceiveAddressResource")
@Path("/customer/receive/address")
public class CustomerReceiveAddressResource {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(CustomerReceiveAddressResource.class);

	@Autowired
	CustomerReceiveAddressService customerReceiveAddressservice;
	
	/**
	 * 接口说明：创建收货地址
	 * @return
	 * @throws Exception
	 */
	@POST
	@Path("/create")
	@Produces({ MediaType.APPLICATION_JSON })
	public RepObject create(Map paramMap) {
		return customerReceiveAddressservice.create(paramMap);
	}

	
	
	/**
	 * 接口说明：修改收货地址
	 * 
	 * @param Map］ paramMap
	 * @return
	 */
	@PUT
	@Path("/update")
	@Produces({ MediaType.APPLICATION_JSON })
	public RepObject update(Map paramMap) {
		return customerReceiveAddressservice.update(paramMap);

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
	public RepObject getById(@PathParam("id") String id) {
		RepObject resp=new RepObject(false,"success","");
		if(StringUtils.isNotEmpty(id)){
			HashMap item= customerReceiveAddressservice.getById(id);
			resp.setData(item);
			RepObject.success(resp);
		}else{
			resp.setCode(ResponseCode.GLOBAL_SYSTEM_PARAM_ERROR);
			resp.setMsg("参数不完整");
		}
		return resp;
	}

	/**
	 * 接口说明： 获取符合条件的数据信息列表(分页模式)
	 * 
	 * @return
	 * @throws Exception
	 */
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/getPageList")
	public RepObject getPageList(@QueryParam("queryJson") String queryJson) {
		RepObject resp=new RepObject(true,"success","");
		Map paramMap=ConvertUtil.objToHashMap(queryJson);
		Page page=customerReceiveAddressservice.getPageList(paramMap);
		resp.setData(page);
		return resp;
		
	}

	/**
	 * 
	 * @return List
	 * @throws Exception
	 */
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/getList")
	public RepObject getList(@QueryParam("queryJson") String queryJson) {
		RepObject resp=new RepObject(false,"","");
		Map paramMap=ConvertUtil.objToHashMap(queryJson);
		List<HashMap> list=customerReceiveAddressservice.getList(paramMap);
		Map respData=new HashMap();
		respData.put(RepDataPropety.DATA_LIST, list);
		RepObject.success(resp);
		resp.setData(respData);
		return resp;
	}

	/**
	 * 
	 * @return Integer
	 * @throws Exception
	 */
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/getCount")
	public RepObject getCount(@QueryParam("queryJson") String queryJson) {
		RepObject resp=new RepObject(true,"success","");
		Map paramMap=ConvertUtil.objToHashMap(queryJson);
		int total=customerReceiveAddressservice.getCount(paramMap);
		resp.setData(total);
		return resp;
	}

}