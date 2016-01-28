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
import com.ck.platform.base.util.RandomUtil;
import com.ck.platform.base.util.core.page.Page;
import com.ck.platform.base.util.object.StringTool;
import com.ck.platform.business.customer.dao.CustomerInfoDAO;
import com.ck.platform.business.customer.dao.CustomerUniteDAO;
import com.ck.platform.business.customer.service.CustomerUniteService;

@Service("customerUniteService")
@SuppressWarnings({ "rawtypes" })
@Transactional(rollbackFor = Exception.class)
public class CustomerUniteServiceImpl implements CustomerUniteService {

	@Autowired
	CustomerUniteDAO customerUniteDAO;
	@Autowired
	CustomerInfoDAO customerInfoDAO;
	
	public synchronized RepObject createCustomer(Map paramMap)throws Exception{
		RepObject repObj = new RepObject(false,"","");
		String username=StringTool.getMapString(paramMap, "username");	
		String mobile =StringTool.getMapString(paramMap, "mobile");
		String email =StringTool.getMapString(paramMap, "email");
		String password =StringTool.getMapString(paramMap, "password");
		try{
			RepObject validObj=validAccount(paramMap);
			if(validObj.getStatus()){
				String customerId =RandomUtil.generateTimeMillNum(12);
				String passwdMd5=MD5PasswordEncoderUtil.encode(password, customerId);
				paramMap.put("passwd", passwdMd5);
				paramMap.put("initPasswd", password);
				paramMap.put("customerId",customerId);
				Long cId=customerUniteDAO.create(paramMap);
				if(cId!=null){
					repObj.setPk(Long.valueOf(customerId));
					HashMap data=customerUniteDAO.getById(customerId);
					System.out.println("-----:"+data);
					repObj.setCode(ResponseCode.GLOBAL_SUSSCESS);
					repObj.setStatus(Boolean.TRUE);
					repObj.setData(data);
				}
				//创建用户基本信息
				if(repObj.getStatus()){
					customerInfoDAO.create(paramMap);
				}
			}else{
				repObj=validObj;
			}
			
		} catch (Exception ex){
			repObj.setCode(ResponseCode.GLOBAL_EXCEPTION);
			repObj.setMsg("create customer_unite error");
			throw ex;
		}
		return repObj;

	}

	public RepObject validAccount(Map paramMap) {
		RepObject repObj = new RepObject(false,"","");
		String username=StringTool.getMapString(paramMap, "username");	
		String mobile =StringTool.getMapString(paramMap, "mobile");
		String email =StringTool.getMapString(paramMap, "email");
		String password =StringTool.getMapString(paramMap, "password");
		boolean valid=false;
		try{
			if((StringUtils.isEmpty(mobile) && StringUtils.isEmpty(email)) || StringUtils.isEmpty(password)){
				repObj.setCode(ResponseCode.GLOBAL_SYSTEM_PARAM_ERROR);
				repObj.setMsg("参数错误");
				valid=false;
			}else{
				valid=true;
			}
			if(valid){
				Map param=new HashMap();
				HashMap customerMap=null;
				String customerId=null;
				//验证用户名
				if(StringUtils.isNotEmpty(username)){
					param.put("username", username);
					customerMap=customerUniteDAO.getByParam(param);
					customerId=StringTool.getMapString(customerMap, "customer_id");
					if(customerMap!=null && StringUtils.isNotEmpty(customerId)){
						repObj.setCode(ResponseCode.GLOBAL_SYSTEM_EXIST);
						repObj.setMsg("用户名已经存在");
						valid=false;
					}else{
						valid=true;
					}
				}
				//验证手机
				if(valid && StringUtils.isNotEmpty(mobile)){
					param=new HashMap();
					param.put("mobile", mobile);
					customerMap=customerUniteDAO.getByParam(param);
					customerId=StringTool.getMapString(customerMap, "customer_id");
					if(!StringTool.isMobile(mobile)){
						repObj.setCode(ResponseCode.GLOBAL_SYSTEM_PARAM_ERROR);
						repObj.setMsg("手机格式错误");
						valid=false;
					}else if(customerMap!=null && StringUtils.isNotEmpty(customerId)){
						repObj.setCode(ResponseCode.GLOBAL_SYSTEM_EXIST);
						repObj.setMsg("手机已经存在");
						valid=false;
					}else{
						valid=true;
					}
				}
				//验证邮箱
				if(valid && StringUtils.isNotEmpty(email)){
					param=new HashMap();
					param.put("email", email);
					customerMap=customerUniteDAO.getByParam(param);
					customerId=StringTool.getMapString(customerMap, "customer_id");
					if(!StringTool.isEmail(email)){
						repObj.setCode(ResponseCode.GLOBAL_SYSTEM_PARAM_ERROR);
						repObj.setMsg("邮箱格式错误");
						valid=false;
					}else if(customerMap!=null && StringUtils.isNotEmpty(customerId)){
						repObj.setCode(ResponseCode.GLOBAL_SYSTEM_EXIST);
						repObj.setMsg("邮箱已经存在");
						valid=false;
					}else{
						valid=true;
					}
				}
				
			}
			if(valid){
				repObj.setStatus(valid);
				repObj.setCode(ResponseCode.GLOBAL_SUSSCESS);
			}
		} catch (Exception ex){
			repObj.setStatus(false);
			repObj.setCode(ResponseCode.GLOBAL_EXCEPTION);
			repObj.setMsg("validCustoemr customer_unite error");
			ex.printStackTrace();
			
		}
		return repObj;
	}
	
	public  RepObject executeLogin(Map paramMap){
		RepObject repObj = new RepObject(false,"","");
		String account=StringTool.getMapString(paramMap, "account");	
		String password =StringTool.getMapString(paramMap, "password");
		boolean valid=false;
		try{
			if(StringUtils.isEmpty(account) || StringUtils.isEmpty(password)){
				repObj.setCode(ResponseCode.GLOBAL_SYSTEM_PARAM_ERROR);
				repObj.setMsg("参数错误");
				valid=false;
			}else{
				valid=true;
			}
			if(valid){
				Map loginMap=new HashMap();
				loginMap.put("isValid", 1);
				loginMap.put("status", 1);
				loginMap.put("account", account);
				HashMap customerMap= customerUniteDAO.getByParam(paramMap);
				if(customerMap!=null && customerMap.get("customer_id")!=null){
					String customerId=StringTool.getMapString(customerMap, "customer_id");
					String passwd=StringTool.getMapString(customerMap, "passwd");
					String initPasswd=StringTool.getMapString(customerMap, "init_passwd");
					String isValid=StringTool.getMapString(customerMap, "is_valid");
					String status=StringTool.getMapString(customerMap, "status");
					String passwdMd5=MD5PasswordEncoderUtil.encode(password, customerId);
					if(passwdMd5.equals(passwd) && "1".equals(status) && "1".equals(isValid)){
						repObj.setCode(ResponseCode.GLOBAL_SUSSCESS);
						repObj.setStatus(Boolean.TRUE);
						repObj.setData(customerMap);
					}else{
						repObj.setCode(ResponseCode.RESPONSE_LOGIN_0A0005);
						repObj.setStatus(Boolean.FALSE);
					}
					
				}else{
					repObj.setCode(ResponseCode.GLOBAL_SYSTEM_NONEXIST);
					repObj.setStatus(Boolean.FALSE);
				}
				
			}
		} catch (Exception ex){
			repObj.setCode(ResponseCode.GLOBAL_EXCEPTION);
			repObj.setMsg("create customer_unite error");
			ex.printStackTrace();
		}
		return repObj;

	}
	
	@Override
	public RepObject updateCustomer(Map paramMap) {
		RepObject repObj = new RepObject(false,"","");
		String customerId=StringTool.getMapString(paramMap, "customerId");	
		String password =StringTool.getMapString(paramMap, "password");
		String oldPassword =StringTool.getMapString(paramMap, "oldPassword");
		try{
			if(StringUtils.isEmpty(customerId)){
				repObj.setCode(ResponseCode.GLOBAL_SYSTEM_PARAM_ERROR);
				repObj.setMsg("参数错误");
			}else{
				if(StringUtils.isNotEmpty(password) && StringUtils.isNotEmpty(oldPassword)){
					//修改密码
					Map loginMap=new HashMap();
					loginMap.put("isValid", 1);
					loginMap.put("status", 1);
					loginMap.put("customerId", customerId);
					HashMap customerMap= customerUniteDAO.getByParam(loginMap);
					if(customerMap!=null && customerMap.get("customer_id")!=null){
						String passwd=StringTool.getMapString(customerMap, "passwd");
						String oldPasswdMd5=MD5PasswordEncoderUtil.encode(oldPassword, customerId);
						if(oldPasswdMd5.equals(passwd)){
							String newPasswdMd5=MD5PasswordEncoderUtil.encode(password, customerId);
							paramMap.put("passwd", newPasswdMd5);
							paramMap.put("initPasswd", password);
							customerUniteDAO.update(paramMap);
							repObj.setCode(ResponseCode.GLOBAL_SUSSCESS);
							repObj.setStatus(true);
						}else{
							repObj.setCode(ResponseCode.GLOBAL_SYSTEM_PARAM_ERROR);
							repObj.setMsg("原密码错误,密码修改失败");
						}
					}else{
						repObj.setCode(ResponseCode.GLOBAL_SYSTEM_PARAM_ERROR);
						repObj.setMsg("用户不存在,密码修改失败");
					}
					
				}else{
					//普通修改
					customerUniteDAO.update(paramMap);
					repObj.setCode(ResponseCode.GLOBAL_SUSSCESS);
					repObj.setStatus(true);
				}
				
					
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
		return customerUniteDAO.getById(id);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Page getPageList(Map paramMap) {
		return customerUniteDAO.getPageList(paramMap);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<HashMap> getList(Map paramMap) {
		return customerUniteDAO.getList(paramMap);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Integer getCount(Map paramMap) {
		return customerUniteDAO.getCount(paramMap);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public HashMap getByParam(Map paramMap) {
		return customerUniteDAO.getByParam(paramMap);
	}

}
