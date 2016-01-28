package com.ck.platform.business.system.resource;

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
import com.ck.platform.business.system.service.SystemMenuService;
/**
 * 系统菜单接口
 * @author lyq
 * @date 2015-8-1
 */
@Component("systemMenuResource")
@Path("/system/menu")
public class SystemMenuResource {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(SystemMenuResource.class);

	@Autowired
	SystemMenuService systemMenuservice;


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
		String siteId=StringTool.getMapString(paramMap, "siteId");
		if(StringUtils.isNotEmpty(siteId)){
			List<HashMap> list=systemMenuservice.getList(paramMap);
			Map respData=new HashMap();
			respData.put(RepDataPropety.DATA_LIST, list);
			RepObject.success(resp);
			resp.setData(respData);
		}else{
			resp.setCode(ResponseCode.GLOBAL_SYSTEM_PARAM_ERROR);
			resp.setMsg("参数不完整");
		}
		return resp;
	}

}