package com.ck.platform.business.order.resource;

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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ck.platform.base.constants.RepDataPropety;
import com.ck.platform.base.constants.ResponseCode;
import com.ck.platform.base.entity.RepObject;
import com.ck.platform.base.util.ConvertUtil;
import com.ck.platform.base.util.core.page.Page;
import com.ck.platform.business.order.service.OrderInfoService;
/**
 * 订单基本信息接口
 * @author lyq
 * @date 2015-8-1
 */
@Component("orderInfoResource")
@Path("/order/info")
public class OrderInfoResource{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(OrderInfoResource.class);

	@Autowired
	OrderInfoService orderInfoService;
	
	/**
	 * 接口说明：创建订单
	 * @return
	 * @throws Exception
	 */
	@POST
	@Path("/creatOrder")
	@Produces({ MediaType.APPLICATION_JSON })
	public RepObject create(Map paramMap)throws Exception {
		return orderInfoService.createOrder(paramMap);
	}

	
	
	/**
	 * 接口说明：修改订单
	 * @param Map］ paramMap
	 * @return
	 */
	@PUT
	@Path("/update")
	@Produces({ MediaType.APPLICATION_JSON })
	public RepObject update(Map paramMap) {
		return orderInfoService.update(paramMap);

	}

	/**
	 * 接口说明：取消订单
	 * @param Map］ paramMap
	 * @return
	 */
	@PUT
	@Path("/cancel")
	@Produces({ MediaType.APPLICATION_JSON })
	public RepObject cancel(Map paramMap) {
		return orderInfoService.executeCancel(paramMap);

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
			HashMap item= orderInfoService.getById(id);
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
	 * @param orderId
	 * @return
	 * @throws Exception
	 */
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/getPageList")
	public RepObject getPageList(@QueryParam("queryJson") String queryJson) {
		RepObject resp=new RepObject(true,"success","");
		Map paramMap=ConvertUtil.objToHashMap(queryJson);
		Page page=orderInfoService.getPageList(paramMap);
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
		List<HashMap> list = orderInfoService.getList(paramMap);
		Map data=new HashMap();
		data.put(RepDataPropety.DATA_LIST, list);
		resp.setData(data);
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
		int total=orderInfoService.getCount(paramMap);
		resp.setData(total);
		return resp;
	}

}