package com.ck.platform.business.cms.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ck.platform.base.constants.ResponseCode;
import com.ck.platform.base.entity.RepObject;
import com.ck.platform.base.util.RandomUtil;
import com.ck.platform.base.util.core.page.Page;
import com.ck.platform.base.util.object.StringTool;
import com.ck.platform.business.cms.dao.CmsColumnProductDAO;
import com.ck.platform.business.cms.service.CmsColumnProductService;

@Service("cmsColumnProductService")
@SuppressWarnings({ "rawtypes" })
@Transactional(rollbackFor = Exception.class)
public class CmsColumnProductServiceImpl implements CmsColumnProductService {

	@Autowired
	CmsColumnProductDAO cmsColumnProductDAO;
	
	@Override
	public RepObject create(Map paramMap){
		RepObject repObj = new RepObject(true,"","");
		repObj.setStatus(Boolean.FALSE);
		try{
			String productId =StringTool.getMapString(paramMap, "productId");
			String columnId =StringTool.getMapString(paramMap, "columnId");
			if(StringUtils.isNotEmpty(productId) || StringUtils.isNotEmpty(columnId)){
				if(productId.indexOf(",")>0){
					String [] productIdArr=productId.split(",");
					for(String pid:productIdArr){
						Map pMap=new HashMap();
						pMap.put("columnId",columnId);
						pMap.put("productId",pid);
						cmsColumnProductDAO.create(pMap);
					}
				}else{
					cmsColumnProductDAO.create(paramMap);
				}
				success(repObj);
			}else{
				repObj.setCode(ResponseCode.GLOBAL_SYSTEM_PARAM_ERROR);
				repObj.setMsg("参数错误");
			}
			
		} catch (Exception ex){
			repObj.setCode(ResponseCode.GLOBAL_EXCEPTION);
			repObj.setMsg("create cms_column_product error");
			ex.printStackTrace();
		}
		return repObj;

	}

	@Override
	public RepObject update(Map paramMap) {
		RepObject repObj = new RepObject(false,"","");
		repObj.setStatus(Boolean.FALSE);
		try{
			if(!paramMap.containsKey("id")){
				repObj.setCode(ResponseCode.GLOBAL_SYSTEM_PARAM_ERROR);
				repObj.setMsg("参数错误");
			}else{
				cmsColumnProductDAO.update(paramMap);
				success(repObj);
			}
			repObj.setStatus(Boolean.TRUE);
		} catch (Exception ex){
			repObj.setCode(ResponseCode.GLOBAL_EXCEPTION);
			repObj.setMsg("update cms_column_product error");
			ex.printStackTrace();
		}
		return repObj;
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public HashMap getById(String id) {
		return cmsColumnProductDAO.getById(id);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Page getPageList(Map paramMap) {
		return cmsColumnProductDAO.getPageList(paramMap);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<HashMap> getList(Map paramMap) {
		List result=null;
		try{
			result= cmsColumnProductDAO.getList(paramMap);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Integer getCount(Map paramMap) {
		return cmsColumnProductDAO.getCount(paramMap);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public HashMap getByParam(Map paramMap) {
		return cmsColumnProductDAO.getByParam(paramMap);
	}

	
	public static void main(String args []){
		System.out.println("----"+RandomUtil.generateTimeMillNum(9));
		String genNum="123123220";
		if(genNum.startsWith("0")){
			genNum="1"+genNum.substring(1,9);
		}
		if(genNum.endsWith("0")){
			genNum=genNum.substring(0,9-1)+"1";
		}
		System.out.println("----"+genNum);
	}
	
	public void success(RepObject resp){
		resp.setStatus(true);
		resp.setCode(ResponseCode.GLOBAL_SUSSCESS);
	}

	@Override
	public RepObject delete(Map paramMap) {
		RepObject repObj = new RepObject(true,"","");
		repObj.setStatus(Boolean.FALSE);
		try{
			String productIds =StringTool.getMapString(paramMap, "productIds");
			String columnId =StringTool.getMapString(paramMap, "columnId");
			if(StringUtils.isNotEmpty(productIds) || StringUtils.isNotEmpty(columnId)){
				cmsColumnProductDAO.delete(paramMap);
				success(repObj);
			}else{
				repObj.setCode(ResponseCode.GLOBAL_SYSTEM_PARAM_ERROR);
				repObj.setMsg("参数错误");
			}
			
		} catch (Exception ex){
			repObj.setCode(ResponseCode.GLOBAL_EXCEPTION);
			repObj.setMsg("create cms_column_product error");
			ex.printStackTrace();
		}
		return repObj;
	}
}
