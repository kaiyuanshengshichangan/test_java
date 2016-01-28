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
import com.ck.platform.business.log.dao.ACopyTestDAO;
import com.ck.platform.business.log.service.ACopyTestService;

@Service("aCopyTestService")
@SuppressWarnings({ "rawtypes" })
@Transactional(rollbackFor = Exception.class)
public class ACopyTestServiceImpl implements ACopyTestService {

	@Autowired
	ACopyTestDAO orderInfoDAO;
	
	@Override
	public RepObject create(Map paramMap){
		RepObject responseObject = new RepObject();
		responseObject.setStatus(Boolean.FALSE);
		try{
			responseObject.setPk(orderInfoDAO.create(paramMap));
			responseObject.setStatus(Boolean.TRUE);

		} catch (Exception ex){
			responseObject.setCode(ResponseCode.GLOBAL_EXCEPTION);
			responseObject.setMsg("create order_info error");
			ex.printStackTrace();
		}
		return responseObject;

	}

	@Override
	public RepObject update(Map paramMap) {
		RepObject responseObject = new RepObject();
		responseObject.setStatus(Boolean.FALSE);
		try{
			orderInfoDAO.update(paramMap);
			responseObject.setStatus(Boolean.TRUE);
		} catch (Exception ex){
			responseObject.setCode(ResponseCode.GLOBAL_EXCEPTION);
			responseObject.setMsg("update order_info error");
			ex.printStackTrace();
		}
		return responseObject;
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public HashMap getById(String id) {
		return orderInfoDAO.getById(id);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Page getPageList(Map paramMap) {
		return orderInfoDAO.getPageList(paramMap);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<HashMap> getList(Map paramMap) {
		return orderInfoDAO.getList(paramMap);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Integer getCount(Map paramMap) {
		return orderInfoDAO.getCount(paramMap);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public HashMap getByParam(Map paramMap) {
		return orderInfoDAO.getByParam(paramMap);
	}

}
