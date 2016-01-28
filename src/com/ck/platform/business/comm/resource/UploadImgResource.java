package com.ck.platform.business.comm.resource;

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
import com.ck.platform.business.comm.service.UploadImgService;
/**
 * 上传图片接口
 * @author lyq
 * @date 2015-8-1
 */
@Component("uploadImgResource")
@Path("/comm/img")
public class UploadImgResource {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(UploadImgResource.class);

	@Autowired
	UploadImgService uploadImgService;

	/**
	 * 上传图片
	 * @param paramMap 
	 * imageType 1-商品图   2-头像图
	 * @return
	 */
	@POST
	@Path("/upload")
	@Produces({ MediaType.APPLICATION_JSON })
	public RepObject createProduct(Map paramMap) {
		RepObject resp=new RepObject(false,"","");
		String imageType=StringTool.getMapString(paramMap, "imageType");
		if(StringUtils.isNotEmpty(imageType)){
			if("1".equals(imageType)){
				resp=uploadImgService.uploadProductImg(paramMap);
			}else if("2".equals(imageType)){
				resp=uploadImgService.uploadCustomerPortraitImg(paramMap);
			}
		}else{
			resp.setCode(ResponseCode.GLOBAL_SYSTEM_PARAM_ERROR);
			resp.setMsg("参数不完整");
		}
		return resp;
	}
	

}