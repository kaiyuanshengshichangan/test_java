package com.ck.platform.business.customer.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ck.platform.base.constants.ResponseCode;
import com.ck.platform.base.entity.RepObject;
import com.ck.platform.base.util.MD5PasswordEncoderUtil;
import com.ck.platform.base.util.core.page.Page;
import com.ck.platform.base.util.object.DateUtil;
import com.ck.platform.base.util.object.StringTool;
import com.ck.platform.business.comm.dao.CommWechatTokenDAO;
import com.ck.platform.business.customer.dao.CustomerUniteDAO;
import com.ck.platform.business.customer.dao.CustomerUniteSocialDAO;
import com.ck.platform.business.customer.service.CustomerUniteSocialService;
import com.ck.platform.business.weixin.dao.WeixinInteractDAO;

@Service("customerUniteSocialService")
@SuppressWarnings({ "rawtypes" })
@Transactional(rollbackFor = Exception.class)
public class CustomerUniteSocialServiceImpl implements CustomerUniteSocialService {

	@Autowired
	CustomerUniteSocialDAO customerUniteSocialDAO;
	@Autowired
	CommWechatTokenDAO commWechatTokenDAO;
	@Autowired
	WeixinInteractDAO weixinInteractDAO;
	@Autowired
	CustomerUniteDAO customerUniteDAO;
	
	@Value(value = "${weixin.service.appid}")
	private String weixin_service_appid = "";
	@Value(value = "${weixin.service.appsecret}")
	private String weixin_service_appsecret = "";
	
	@Override
	public RepObject create(Map paramMap){
		RepObject repObj = new RepObject();
		repObj.setStatus(Boolean.FALSE);
		try{
			//更改渠道信息
			long id = 0L;
			String uniteOpenid=StringTool.getMapString(paramMap, "uniteOpenid");
			String channelId=StringTool.getMapString(paramMap, "channelId");
			String customerId=StringTool.getMapString(paramMap, "customerId");
			System.out.println("------------uniteOpenid"+uniteOpenid);
			if(StringUtils.isEmpty(customerId) || StringUtils.isEmpty(channelId) || StringUtils.isEmpty(uniteOpenid)){
				repObj.setCode(ResponseCode.GLOBAL_SYSTEM_PARAM_ERROR);
				repObj.setMsg("参数错误");
			}else {
				Map pMap=new HashMap();
				pMap.put("channelId", channelId);
				pMap.put("uniteOpenid", uniteOpenid);
				HashMap info1=this.getByParam(pMap);
				if(info1!=null){ // 判断uniteOpenid是否已被注册过
				    paramMap.put("id",StringTool.getMapString(info1, "id"));
				    paramMap.put("isValid", 1);	
				    customerUniteSocialDAO.update(paramMap);
				}else{
					id=customerUniteSocialDAO.create(paramMap);
				}
			}
			
			repObj.setPk(id);
			repObj.setStatus(Boolean.TRUE);
		} catch (Exception ex){
			repObj.setCode(ResponseCode.GLOBAL_EXCEPTION);
			repObj.setMsg("create customer_unite_social error");
			ex.printStackTrace();
		}
		return repObj;

	}

	@Override
	public RepObject update(Map paramMap) {
		RepObject repObj = new RepObject();
		repObj.setStatus(Boolean.FALSE);
		try{
			customerUniteSocialDAO.update(paramMap);
			repObj.setStatus(Boolean.TRUE);
		} catch (Exception ex){
			repObj.setCode(ResponseCode.GLOBAL_EXCEPTION);
			repObj.setMsg("update customer_unite_social error");
			ex.printStackTrace();
			
		}
		return repObj;
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public HashMap getById(String id) {
		return customerUniteSocialDAO.getById(id);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Page getPageList(Map paramMap) {
		return customerUniteSocialDAO.getPageList(paramMap);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<HashMap> getList(Map paramMap) {
		return customerUniteSocialDAO.getList(paramMap);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Integer getCount(Map paramMap) {
		return customerUniteSocialDAO.getCount(paramMap);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public HashMap getByParam(Map paramMap) {
		return customerUniteSocialDAO.getByParam(paramMap);
	}
	
	
	/**
	 * 绑定公众号
	 * 1.判断用户名、密码
	 * 2.判断是否可以更新 isUpdate 0-否 1-是
	 */
	public RepObject createWeixinUniteBind(Map paramMap) {
		RepObject repObj = new RepObject(false, "","");
		try {
			if (paramMap != null) {
				String openId = StringTool.getMapString(paramMap,"openId");
				String account = StringTool.getMapString(paramMap, "account");
				String password = StringTool.getMapString(paramMap, "password");
				String channelId = StringTool.getMapString(paramMap, "channelId");
				String customerId = StringTool.getMapString(paramMap, "customerId");
				// 验证参数完整性
				if (StringUtils.isEmpty(openId) ||StringUtils.isEmpty(customerId)
						|| StringUtils.isEmpty(account)
						|| StringUtils.isEmpty(password) || StringUtils.isEmpty(channelId)) {
					repObj.setCode(ResponseCode.GLOBAL_SYSTEM_INCOMPLEMENT);
					repObj.setMsg("参数不完整");
					repObj.setStatus(false);
				} else {
					repObj.setCode(ResponseCode.GLOBAL_SUSSCESS);
				}
				if(isUniteByOpenId(openId,channelId)){ //已绑定过
					repObj.setStatus(false);
					repObj.setCode(ResponseCode.GLOBAL_SYSTEM_EXIST);
				}
				// 验证参数一致性
				if (ResponseCode.GLOBAL_SUSSCESS.equals(repObj.getCode())) { 
					Map pMap = new HashMap();
					pMap.put("account", account);
					HashMap uniteMap = customerUniteDAO.getByParam(pMap);
					String cid=StringTool.getMapString(uniteMap, "customerId");
					if (uniteMap != null && cid.equals(customerId)) {
						String passwd = StringTool.getMapString(uniteMap, "passwd");
						boolean b = MD5PasswordEncoderUtil.valid(passwd,password, customerId);
						if (!b && !password.equals(passwd)) { //密码不正确
							repObj.setStatus(false);
							repObj.setCode(ResponseCode.GLOBAL_FAILTURE);
							repObj.setMsg("密码错误！");
						} else {
							updateBindWeixinUnite(customerId, openId);
							repObj.setStatus(true);
						}
					} else {
						repObj.setStatus(false);
						repObj.setCode(ResponseCode.GLOBAL_SYSTEM_NONEXIST);
						repObj.setMsg("用户不存在！");
					}

				}
			}

		} catch (Exception e) {
			repObj.setCode(ResponseCode.GLOBAL_EXCEPTION);
			repObj.setStatus(false);
			e.printStackTrace();

		}
		return repObj;

	}
	
	/**
	 * 取消微信绑定
	 * @param customerId
	 * @return
	 */
	public RepObject updateCancelWxBind(String customerId) {
		RepObject resp=new RepObject(false,"","");
		if(StringUtils.isNotEmpty(customerId)){
			Map infoParam=new HashMap();
			infoParam.put("customerId", customerId);
			infoParam.put("channelId", "1"); //公众号与开放平台属于两种不同的渠道
			infoParam.put("isValid", 0);
			customerUniteSocialDAO.update(infoParam);
			resp.setStatus(true);
			System.out.println("-----------取消微信公众号成功！");
		}
		return resp;
	}

	/**
	 * 判断第三方账号是否绑定过
	 * @param openId
	 * @return
	 */
	private Boolean isUniteByOpenId(String openId,String channelId) {
		Boolean b=false;
		Map infoParam=new HashMap();
		infoParam.put("uniteOpenid",openId);
		infoParam.put("channelId",channelId);
		infoParam.put("isValid",1);
		HashMap info=this.getByParam(infoParam);
		if(channelId!=null){
			b=true;
		}
		return b;
	}
	
	/**
	 *  绑定
	 * @param customerId
	 * @param uniteNameWeixin
	 */
	private void updateBindWeixinUnite(String customerId, String openid) {
		try {
			if (StringUtils.isNotEmpty(customerId)) {
				//this.updateClearWxByOpenId(openid);
				//记录微信用户信息
				Map tokenMap=this.updateGetAccessToken(weixin_service_appid,weixin_service_appsecret);
				String token=StringTool.getMapString(tokenMap, "access_token");
				Map weixinUserMap=weixinInteractDAO.userInfo(token, openid);
				Map<String,Object>  uniteInfo=new HashMap<String,Object>();
				if(weixinUserMap!=null && (weixinUserMap.get("openid")!=null ||weixinUserMap.get("unionid")!=null)){
					 uniteInfo.put("customerId", customerId);//会员id
					 uniteInfo.put("channelId", 1);//渠道id
					 uniteInfo.put("uniteInfo", weixin_service_appid);//渠道信息
					 uniteInfo.put("uniteFollowState",StringTool.getMapString(weixinUserMap, "subscribe"));//是否关注渠道信息
					 uniteInfo.put("uniteFollowTime", DateUtil.getCurrentTime());//关注时间
					 uniteInfo.put("uniteOpenid", openid);//用户openid
					 uniteInfo.put("uniteNickname", StringTool.getMapString(weixinUserMap, "nickname"));//昵称
					 uniteInfo.put("uniteSex", StringTool.getMapString(weixinUserMap, "sex"));//性别
					 uniteInfo.put("uniteLanguage", StringTool.getMapString(weixinUserMap, "language"));//语言
					 uniteInfo.put("country", StringTool.getMapString(weixinUserMap, "country"));//国家
					 uniteInfo.put("province", StringTool.getMapString(weixinUserMap, "province"));//省份
					 uniteInfo.put("city", StringTool.getMapString(weixinUserMap, "city"));//城市
					 uniteInfo.put("uniteHeadLogo", StringTool.getMapString(weixinUserMap, "headimgurl"));//头像链接
					 uniteInfo.put("uniteRemark", StringTool.getMapString(weixinUserMap, "remark"));//备注
					 uniteInfo.put("unionId",StringTool.getMapString(weixinUserMap, "unionid"));//联合id
					 this.create(uniteInfo);
				}
				 System.out.println("-----------绑定微信公众号成功！");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private HashMap updateGetAccessToken(String appId,String appSecret) {
		HashMap result=new HashMap();
		Map paramMap=new HashMap();
		paramMap.put("appId",appId);
		HashMap tokenMap=commWechatTokenDAO.getByParam(paramMap);
		Date dateBefore30=DateUtil.getMinBefore(new Date(), 30);
		Date updateTime = DateUtil.parse(StringTool.getMapString(tokenMap, "update_time"));
		String token=StringTool.getMapString(tokenMap, "token");
		if(tokenMap!=null && dateBefore30.before(updateTime) && StringUtils.isNotEmpty(token)){
			System.out.println("-----------getAccessToken:"+"数据库－获取口令");
			result.put("access_token", token);
		}else{
			System.out.println("-----------getAccessToken:"+"更新数据库－获取口令");
			result=weixinInteractDAO.getAccessToken(appId, appSecret);
			String accessToken=StringTool.getMapString(result, "access_token");
			if(StringUtils.isNotEmpty(accessToken)){
				Map updateMap=new HashMap();
				updateMap.put("appId",appId);
				updateMap.put("token", accessToken) ;
				updateMap.put("updateTime", DateUtil.getCurrentTime());
				commWechatTokenDAO.update(updateMap);
			}
		}
		
		return result;
	}
}
