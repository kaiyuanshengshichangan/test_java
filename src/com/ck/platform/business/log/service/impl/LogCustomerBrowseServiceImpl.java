package com.ck.platform.business.log.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ck.platform.base.constants.ResponseCode;
import com.ck.platform.base.entity.RepObject;
import com.ck.platform.base.util.core.page.Page;
import com.ck.platform.business.log.dao.LogCustomerBrowseDAO;
import com.ck.platform.business.log.service.LogCustomerBrowseService;

@Service("logCustomerBrowseService")
@SuppressWarnings({ "rawtypes" })
@Transactional(rollbackFor = Exception.class)
public class LogCustomerBrowseServiceImpl implements LogCustomerBrowseService {

	@Autowired
	LogCustomerBrowseDAO logCustomerBrowseDAO;
	
	@Override
	public RepObject create(Map paramMap){
		RepObject responseObject = new RepObject();
		responseObject.setStatus(Boolean.FALSE);
		try{
			responseObject.setPk(logCustomerBrowseDAO.create(paramMap));
			responseObject.setStatus(Boolean.TRUE);

		} catch (Exception ex){
			responseObject.setCode(ResponseCode.GLOBAL_EXCEPTION);
			responseObject.setMsg("create log_customer_browse error");
			ex.printStackTrace();
		}
		return responseObject;

	}

	@Override
	public RepObject update(Map paramMap) {
		RepObject responseObject = new RepObject();
		responseObject.setStatus(Boolean.FALSE);
		try{
			logCustomerBrowseDAO.update(paramMap);
			responseObject.setStatus(Boolean.TRUE);
		} catch (Exception ex){
			responseObject.setCode(ResponseCode.GLOBAL_EXCEPTION);
			responseObject.setMsg("update log_customer_browse error");
			ex.printStackTrace();
		}
		return responseObject;
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public HashMap getById(String id) {
		return logCustomerBrowseDAO.getById(id);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Page getPageList(Map paramMap) {
		return logCustomerBrowseDAO.getPageList(paramMap);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<HashMap> getList(Map paramMap) {
		return logCustomerBrowseDAO.getList(paramMap);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Integer getCount(Map paramMap) {
		return logCustomerBrowseDAO.getCount(paramMap);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public HashMap getByParam(Map paramMap) {
		return logCustomerBrowseDAO.getByParam(paramMap);
	}

}
