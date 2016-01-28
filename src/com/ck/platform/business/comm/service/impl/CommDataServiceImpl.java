package com.ck.platform.business.comm.service.impl;

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
import com.ck.platform.business.comm.dao.CommDataDAO;
import com.ck.platform.business.comm.service.CommDataService;

@Service("commDataService")
@SuppressWarnings({ "rawtypes" })
@Transactional(rollbackFor = Exception.class)
public class CommDataServiceImpl implements CommDataService {

	@Autowired
	CommDataDAO commDataDAO;
	
	@Override
	public RepObject create(Map paramMap){
		RepObject responseObject = new RepObject();
		responseObject.setStatus(Boolean.FALSE);
		try{
			responseObject.setPk(commDataDAO.create(paramMap));
			responseObject.setStatus(Boolean.TRUE);
		} catch (Exception ex){
			responseObject.setCode(ResponseCode.GLOBAL_EXCEPTION);
			responseObject.setMsg("create comm_region  error");
			ex.printStackTrace();
		}
		return responseObject;
	}

	@Override
	public RepObject update(Map paramMap) {
		RepObject responseObject = new RepObject();
		responseObject.setStatus(Boolean.FALSE);
		try{
			commDataDAO.update(paramMap);
			responseObject.setStatus(Boolean.TRUE);
		} catch (Exception ex){
			responseObject.setCode(ResponseCode.GLOBAL_EXCEPTION);
			responseObject.setMsg("update comm_region error");
			ex.printStackTrace();
			
		}
		return responseObject;
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public HashMap getById(String id) {
		return commDataDAO.getById(id);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Page getPageList(Map paramMap) {
		return commDataDAO.getPageList(paramMap);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<HashMap> getList(Map paramMap) {
		try{
			return commDataDAO.getList(paramMap);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Integer getCount(Map paramMap) {
		return commDataDAO.getCount(paramMap);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public HashMap getByParam(Map paramMap) {
		return commDataDAO.getByParam(paramMap);
	}

}
