package com.ck.platform.business.product.service.impl;

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
import com.ck.platform.business.product.dao.ProductLogoDAO;
import com.ck.platform.business.product.service.ProductLogoService;

@Service("productLogoService")
@SuppressWarnings({ "rawtypes" })
@Transactional(rollbackFor = Exception.class)
public class ProductLogoServiceImpl implements ProductLogoService {

	@Autowired
	ProductLogoDAO productLogoDAO;
	
	@Override
	public RepObject create(Map paramMap){
		RepObject repObj = new RepObject(true,"","");
		repObj.setStatus(Boolean.FALSE);
		try{
			String productId =StringTool.getMapString(paramMap, "productId");
			if(StringUtils.isNotEmpty(productId)){
				repObj.setPk(productLogoDAO.create(paramMap));
				success(repObj);
			}else{
				repObj.setCode(ResponseCode.GLOBAL_SYSTEM_PARAM_ERROR);
				repObj.setMsg("参数错误");
			}
			
		} catch (Exception ex){
			repObj.setCode(ResponseCode.GLOBAL_EXCEPTION);
			repObj.setMsg("create product_info error");
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
				productLogoDAO.update(paramMap);
				success(repObj);
			}
			
			repObj.setStatus(Boolean.TRUE);
		} catch (Exception ex){
			repObj.setCode(ResponseCode.GLOBAL_EXCEPTION);
			repObj.setMsg("update product_info error");
			ex.printStackTrace();
		}
		return repObj;
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public HashMap getById(String id) {
		return productLogoDAO.getById(id);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Page getPageList(Map paramMap) {
		return productLogoDAO.getPageList(paramMap);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<HashMap> getList(Map paramMap) {
		return productLogoDAO.getList(paramMap);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Integer getCount(Map paramMap) {
		return productLogoDAO.getCount(paramMap);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public HashMap getByParam(Map paramMap) {
		return productLogoDAO.getByParam(paramMap);
	}

	
	public void success(RepObject resp){
		resp.setStatus(true);
		resp.setCode(ResponseCode.GLOBAL_SUSSCESS);
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
	
}
