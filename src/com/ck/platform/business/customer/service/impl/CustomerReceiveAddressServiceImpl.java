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
import com.ck.platform.base.util.core.page.Page;
import com.ck.platform.base.util.object.StringTool;
import com.ck.platform.business.customer.dao.CustomerReceiveAddressDAO;
import com.ck.platform.business.customer.service.CustomerReceiveAddressService;

@Service("customerReceiveAddressService")
@SuppressWarnings({ "rawtypes" })
@Transactional(rollbackFor = Exception.class)
public class CustomerReceiveAddressServiceImpl implements CustomerReceiveAddressService {

	@Autowired
	CustomerReceiveAddressDAO customerReceiveAddressDAO;
	
	@Override
	public RepObject create(Map paramMap){
		RepObject repObj = new RepObject(false,"","");
		try{
			String customerId=StringTool.getMapString(paramMap, "customerId");	
			String receiveName=StringTool.getMapString(paramMap, "receiveName");
			String receiveMobile=StringTool.getMapString(paramMap, "receiveMobile");
			String isDefault=StringTool.getMapString(paramMap, "isDefault");
			if(StringUtils.isEmpty(customerId) || StringUtils.isEmpty(receiveName)  || StringUtils.isEmpty(receiveMobile)){
				repObj.setCode(ResponseCode.GLOBAL_SYSTEM_PARAM_ERROR);
				repObj.setMsg("参数错误");
				repObj.setStatus(false);
			}else{
				if("1".equals(isDefault)){
					Map pMap=new HashMap();
					pMap.put("customerId", customerId);
					pMap.put("isDefault", 0);
					customerReceiveAddressDAO.update(pMap);
				}
				repObj.setPk(customerReceiveAddressDAO.create(paramMap));
				repObj.setStatus(Boolean.TRUE);
				repObj.setCode(ResponseCode.GLOBAL_SUSSCESS);
			}
			
		} catch (Exception ex){
			repObj.setCode(ResponseCode.GLOBAL_EXCEPTION);
			repObj.setMsg("create customer_receive_address error");
			ex.printStackTrace();
		}
		return repObj;

	}

	@Override
	public RepObject update(Map paramMap) {
		RepObject repObj = new RepObject(false,"","");
		String id=StringTool.getMapString(paramMap, "id");
		String isDefault=StringTool.getMapString(paramMap, "isDefault");	
		try{
			if(StringUtils.isEmpty(id)){
				repObj.setCode(ResponseCode.GLOBAL_SYSTEM_PARAM_ERROR);
				repObj.setMsg("参数错误");
			}else{
				//需要先将其他地址改称非默认地址
				if("1".equals(isDefault)){
					HashMap defaultMap=customerReceiveAddressDAO.getById(id);
					String isDefaultDB=StringTool.getMapString(defaultMap, "is_default");
					String customerId=StringTool.getMapString(defaultMap, "customer_id");
					if("0".equals(isDefaultDB)){
						Map pMap=new HashMap();
						pMap.put("customerId", customerId);
						pMap.put("isDefault", 0);
						customerReceiveAddressDAO.update(pMap);
					}
					
				}
				customerReceiveAddressDAO.update(paramMap);
				repObj.setCode(ResponseCode.GLOBAL_SUSSCESS);
				repObj.setStatus(true);
			}
		} catch (Exception ex){
			repObj.setCode(ResponseCode.GLOBAL_EXCEPTION);
			repObj.setMsg("update customer_receive_address error");
			ex.printStackTrace();
		}
		return repObj;
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public HashMap getById(String id) {
		return customerReceiveAddressDAO.getById(id);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Page getPageList(Map paramMap) {
		return customerReceiveAddressDAO.getPageList(paramMap);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<HashMap> getList(Map paramMap) {
		return customerReceiveAddressDAO.getList(paramMap);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Integer getCount(Map paramMap) {
		return customerReceiveAddressDAO.getCount(paramMap);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public HashMap getByParam(Map paramMap) {
		return customerReceiveAddressDAO.getByParam(paramMap);
	}

}
