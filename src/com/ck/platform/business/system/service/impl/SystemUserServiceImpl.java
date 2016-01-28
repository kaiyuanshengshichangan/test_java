package com.ck.platform.business.system.service.impl;

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
import com.ck.platform.business.system.dao.SystemUserDAO;
import com.ck.platform.business.system.service.SystemUserService;

@Service("systemUserService")
@SuppressWarnings({ "rawtypes" })
@Transactional(rollbackFor = Exception.class)
public class SystemUserServiceImpl implements SystemUserService {

	@Autowired
	SystemUserDAO systemUserDAO;
	
	@Override
	public RepObject create(Map paramMap){
		RepObject responseObject = new RepObject();
		responseObject.setStatus(Boolean.FALSE);
		try{
			responseObject.setPk(systemUserDAO.create(paramMap));
			responseObject.setStatus(Boolean.TRUE);

		} catch (Exception ex){
			responseObject.setCode(ResponseCode.GLOBAL_EXCEPTION);
			responseObject.setMsg("create system_user error");
			ex.printStackTrace();
		}
		return responseObject;

	}

	@Override
	public RepObject update(Map paramMap) {
		RepObject responseObject = new RepObject();
		responseObject.setStatus(Boolean.FALSE);
		try{
			systemUserDAO.update(paramMap);
			responseObject.setStatus(Boolean.TRUE);
		} catch (Exception ex){
			responseObject.setCode(ResponseCode.GLOBAL_EXCEPTION);
			responseObject.setMsg("update system_user error");
			ex.printStackTrace();
			
		}
		return responseObject;
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public HashMap getById(String id) {
		return systemUserDAO.getById(id);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Page getPageList(Map paramMap) {
		return systemUserDAO.getPageList(paramMap);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<HashMap> getList(Map paramMap) {
		return systemUserDAO.getList(paramMap);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Integer getCount(Map paramMap) {
		return systemUserDAO.getCount(paramMap);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public HashMap getByParam(Map paramMap) {
		return systemUserDAO.getByParam(paramMap);
	}

}
