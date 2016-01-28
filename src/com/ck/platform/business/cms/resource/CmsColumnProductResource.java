package com.ck.platform.business.cms.resource;

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

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ck.platform.base.constants.RepDataPropety;
import com.ck.platform.base.constants.ResponseCode;
import com.ck.platform.base.entity.RepObject;
import com.ck.platform.base.util.ConvertUtil;
import com.ck.platform.base.util.core.page.Page;
import com.ck.platform.base.util.object.StringTool;
import com.ck.platform.business.cms.service.CmsColumnProductService;
/**
 * 栏目下的商品 接口
 * @author lyq
 * @date 2015-8-3
 */
@Component("cmsColumnProductResource")
@Path("/cms/column/product")
public class CmsColumnProductResource extends RepObject {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(CmsColumnProductResource.class);

	@Autowired
	CmsColumnProductService cmsColumnProductservice;

	/**
	 * 接口说明：栏目下添加商品
	 * @param productNo
	 */
	@POST
	@Path("/createColumnProduct")
	@Produces({ MediaType.APPLICATION_JSON })
	public RepObject createProduct(Map paramMap) {
		RepObject resp=new RepObject(false,"","");
		String columnId=StringTool.getMapString(paramMap, "columnId");
		String productId=StringTool.getMapString(paramMap, "productId");
		if(StringUtils.isNotEmpty(columnId) || StringUtils.isNotEmpty(productId)){
			resp=cmsColumnProductservice.create(paramMap);
		}else{
			resp.setCode(ResponseCode.GLOBAL_SYSTEM_PARAM_ERROR);
			resp.setMsg("参数不完整");
		}
		return resp;
	}
	
	@PUT
	@Path("/update")
	@Produces({ MediaType.APPLICATION_JSON })
	public RepObject update(Map paramMap) {
		return cmsColumnProductservice.update(paramMap);
	}
	

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/getById/{id}")
	public RepObject getById(@PathParam("id") String id) {
		RepObject resp=new RepObject(false,"success","");
		if(StringUtils.isNotEmpty(id)){
			HashMap item= cmsColumnProductservice.getById(id);
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
	@Path("/delete")
	public RepObject delete(Map paramMap) {
		return cmsColumnProductservice.delete(paramMap);
	}
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/getPageList")
	public RepObject getPageList(@QueryParam("queryJson") String queryJson) {
		RepObject resp=new RepObject(true,"success","");
		Map paramMap=ConvertUtil.objToHashMap(queryJson);
		Page page=cmsColumnProductservice.getPageList(paramMap);
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
		RepObject resp=new RepObject(true,"success","");
		Map paramMap=ConvertUtil.objToHashMap(queryJson);
		List<HashMap> list = cmsColumnProductservice.getList(paramMap);
		Map data=new HashMap();
		data.put(RepDataPropety.DATA_LIST, list);
		resp.setData(data);
		return resp;
	}
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/getCount")
	public RepObject getCount(@QueryParam("queryJson") String queryJson) {
		RepObject resp=new RepObject(true,"success","");
		Map paramMap=ConvertUtil.objToHashMap(queryJson);
		int total=cmsColumnProductservice.getCount(paramMap);
		resp.setData(total);
		return resp;
	}
	

}