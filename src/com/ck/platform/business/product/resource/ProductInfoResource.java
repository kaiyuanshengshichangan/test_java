package com.ck.platform.business.product.resource;

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
import com.ck.platform.business.product.service.ProductInfoService;
/**
 * 商品基本信息接口
 * @author lyq
 * @date 2015-8-1
 */
@Component("productInfoResource")
@Path("/product/info")
public class ProductInfoResource {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ProductInfoResource.class);

	@Autowired
	ProductInfoService productInfoservice;

	/**
	 * 接口说明：
	 * @param productNo
	 */
	@POST
	@Path("/createProduct")
	@Produces({ MediaType.APPLICATION_JSON })
	public RepObject createProduct(Map paramMap) {
		RepObject resp=new RepObject(false,"","");
		String productName=StringTool.getMapString(paramMap, "productName");
		String productPrice=StringTool.getMapString(paramMap, "productPrice");
		if(StringUtils.isNotEmpty(productName) && StringUtils.isNotEmpty(productPrice)){
			resp=productInfoservice.create(paramMap);
		}else{
			resp.setCode(ResponseCode.GLOBAL_SYSTEM_PARAM_ERROR);
			resp.setMsg("参数不完整");
		}
		return resp;
	}
	
	@PUT
	@Path("/updateProduct")
	@Produces({ MediaType.APPLICATION_JSON })
	public RepObject update(Map paramMap) {
		return productInfoservice.update(paramMap);
	}
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/getById/{id}")
	public RepObject getById(@PathParam("id") String id) {
		RepObject resp=new RepObject(false,"success","");
		if(StringUtils.isNotEmpty(id)){
			HashMap item= productInfoservice.getById(id);
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
	@Path("/getPageList")
	public RepObject getPageList(@QueryParam("queryJson") String queryJson) {
		RepObject resp=new RepObject(true,"success","");
		Map paramMap=ConvertUtil.objToHashMap(queryJson);
		Page page=productInfoservice.getPageList(paramMap);
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
		List<HashMap> list = productInfoservice.getList(paramMap);
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
		int total=productInfoservice.getCount(paramMap);
		resp.setData(total);
		return resp;
	}
	

}