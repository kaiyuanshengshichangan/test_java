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
import com.ck.platform.business.system.dao.SystemMenuDAO;
import com.ck.platform.business.system.service.SystemMenuService;

@Service("systemMenuService")
@SuppressWarnings({ "rawtypes" })
@Transactional(rollbackFor = Exception.class)
public class SystemMenuServiceImpl implements SystemMenuService {

	@Autowired
	SystemMenuDAO systemMenuDAO;
	
	@Override
	public RepObject create(Map paramMap){
		RepObject responseObject = new RepObject();
		responseObject.setStatus(Boolean.FALSE);
		try{
			responseObject.setPk(systemMenuDAO.create(paramMap));
			responseObject.setStatus(Boolean.TRUE);

		} catch (Exception ex){
			responseObject.setCode(ResponseCode.GLOBAL_EXCEPTION);
			responseObject.setMsg("create system_menu error");
			ex.printStackTrace();
		}
		return responseObject;

	}

	@Override
	public RepObject update(Map paramMap) {
		RepObject responseObject = new RepObject();
		responseObject.setStatus(Boolean.FALSE);
		try{
			systemMenuDAO.update(paramMap);
			responseObject.setStatus(Boolean.TRUE);
		} catch (Exception ex){
			responseObject.setCode(ResponseCode.GLOBAL_EXCEPTION);
			responseObject.setMsg("update system_menu error");
			ex.printStackTrace();
			
		}
		return responseObject;
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public HashMap getById(String id) {
		return systemMenuDAO.getById(id);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Page getPageList(Map paramMap) {
		return systemMenuDAO.getPageList(paramMap);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<HashMap> getList(Map paramMap) {
		return systemMenuDAO.getList(paramMap);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Integer getCount(Map paramMap) {
		return systemMenuDAO.getCount(paramMap);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public HashMap getByParam(Map paramMap) {
		return systemMenuDAO.getByParam(paramMap);
	}

}
