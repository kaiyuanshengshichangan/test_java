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
import com.ck.platform.business.customer.service.CustomerUniteService;
/**
 * 会员联合登录接口
 * @author lyq
 * @date 2015-8-12
 */
@Component("customerUniteResource")
@Path("/customer/unite")
public class CustomerUniteResource {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(CustomerUniteResource.class);

	@Autowired
	CustomerUniteService customerUniteservice;
	
	/**
	 * 接口说明：创建会员
	 * @return
	 * @throws Exception
	 */

	@POST
	@Path("/regist")
	@Produces({ MediaType.APPLICATION_JSON })
	public RepObject create(Map paramMap)throws Exception {
		RepObject repObj = new RepObject(false,"","");
		String username=StringTool.getMapString(paramMap, "username");	
		String mobile =StringTool.getMapString(paramMap, "mobile");
		String email =StringTool.getMapString(paramMap, "email");
		String password =StringTool.getMapString(paramMap, "password");
		if((StringUtils.isEmpty(mobile) && StringUtils.isEmpty(email)) || StringUtils.isEmpty(password)){
			repObj.setCode(ResponseCode.GLOBAL_SYSTEM_PARAM_ERROR);
			repObj.setMsg("参数错误");
			repObj.setStatus(false);
		}else{
			repObj=customerUniteservice.createCustomer(paramMap);
		}
		return repObj;
	}

	/**
	 * 接口说明：会员登录
	 * @return
	 * @throws Exception
	 */

	@POST
	@Path("/login")
	@Produces({ MediaType.APPLICATION_JSON })
	public RepObject login(Map paramMap) {
		RepObject repObj = new RepObject(false,"","");
		String account=StringTool.getMapString(paramMap, "account");	
		String password =StringTool.getMapString(paramMap, "password");
		if(StringUtils.isEmpty(account) || StringUtils.isEmpty(password)){
			repObj.setCode(ResponseCode.GLOBAL_SYSTEM_PARAM_ERROR);
			repObj.setMsg("参数错误");
			repObj.setStatus(false);
		}else{
			repObj=customerUniteservice.executeLogin(paramMap);
		}
		return repObj;
	}
	
	/**
	 * 接口说明：验证会员
	 * @return
	 * @throws Exception
	 */

	@POST
	@Path("/validAccount")
	@Produces({ MediaType.APPLICATION_JSON })
	public RepObject validAccount(Map paramMap) {
		return customerUniteservice.validAccount(paramMap);
	}
	
	
	/**
	 * 接口说明：信息变更
	 * 
	 * @param Map
	 *            paramMap
	 * @return
	 */
	@PUT
	@Path("/updateCustomer")
	@Produces({ MediaType.APPLICATION_JSON })
	public RepObject update(Map paramMap) {
		return customerUniteservice.updateCustomer(paramMap);

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
			HashMap item= customerUniteservice.getById(id);
			resp.setData(item);
			RepObject.success(resp);
		}else{
			resp.setCode(ResponseCode.GLOBAL_SYSTEM_PARAM_ERROR);
			resp.setMsg("参数不完整");
		}
		return resp;
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/getByParam")
	public RepObject getByParam(@QueryParam("queryJson") String queryJson) {
		RepObject resp=new RepObject(false,"success","");
		Map paramMap=ConvertUtil.objToHashMap(queryJson);
		HashMap item= customerUniteservice.getByParam(paramMap);
		resp.setData(item);
		RepObject.success(resp);
		return resp;
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
	public RepObject getPageList(@QueryParam("queryJson") String queryJson) {
		RepObject resp=new RepObject(true,"success","");
		Map paramMap=ConvertUtil.objToHashMap(queryJson);
		Page page=customerUniteservice.getPageList(paramMap);
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
		List<HashMap> list=customerUniteservice.getList(paramMap);
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
		int total=customerUniteservice.getCount(paramMap);
		resp.setData(total);
		return resp;
	}

}