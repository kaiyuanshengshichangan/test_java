package com.ck.platform.business.customer.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ck.platform.base.constants.ResponseCode;
import com.ck.platform.base.entity.RepObject;
import com.ck.platform.base.util.MD5PasswordEncoderUtil;
import com.ck.platform.base.util.core.page.Page;
import com.ck.platform.base.util.object.StringTool;
import com.ck.platform.business.customer.dao.CustomerInfoDAO;
import com.ck.platform.business.customer.service.CustomerInfoService;

@Service("customerInfoService")
@SuppressWarnings({ "rawtypes" })
@Transactional(rollbackFor = Exception.class)
public class CustomerInfoServiceImpl implements CustomerInfoService {

	@Autowired
	CustomerInfoDAO customerInfoDAO;
	
	@Override
	public RepObject create(Map paramMap){
		RepObject responseObject = new RepObject();
		responseObject.setStatus(Boolean.FALSE);
		try{
			responseObject.setPk(customerInfoDAO.create(paramMap));
			responseObject.setStatus(Boolean.TRUE);

		} catch (Exception ex){
			responseObject.setCode(ResponseCode.GLOBAL_EXCEPTION);
			responseObject.setMsg("create customer_unite error");
			ex.printStackTrace();
		}
		return responseObject;

	}

	@Override
	public RepObject updateInfo(Map paramMap) {
		RepObject repObj = new RepObject(false,"","");
		String customerId=StringTool.getMapString(paramMap, "customerId");	
		try{
			if(StringUtils.isEmpty(customerId)){
				repObj.setCode(ResponseCode.GLOBAL_SYSTEM_PARAM_ERROR);
				repObj.setMsg("参数错误");
			}else{
				customerInfoDAO.update(paramMap);
				repObj.setCode(ResponseCode.GLOBAL_SUSSCESS);
				repObj.setStatus(true);
			}
		} catch (Exception ex){
			repObj.setCode(ResponseCode.GLOBAL_EXCEPTION);
			repObj.setMsg("update customer_unite error");
			ex.printStackTrace();
		}
		return repObj;
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public HashMap getById(String id) {
		return customerInfoDAO.getById(id);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Page getPageList(Map paramMap) {
		return customerInfoDAO.getPageList(paramMap);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<HashMap> getList(Map paramMap) {
		return customerInfoDAO.getList(paramMap);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Integer getCount(Map paramMap) {
		return customerInfoDAO.getCount(paramMap);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public HashMap getByParam(Map paramMap) {
		return customerInfoDAO.getByParam(paramMap);
	}

}
