package com.ck.platform.business.weixin.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ck.platform.base.util.object.DateUtil;
import com.ck.platform.base.util.object.StringTool;
import com.ck.platform.business.comm.dao.CommWechatTokenDAO;
import com.ck.platform.business.weixin.dao.WeixinInteractDAO;
import com.ck.platform.business.weixin.service.WeixinInteractService;

@Service("weixinInteractService")
@SuppressWarnings({ "rawtypes" })
public class WeixinInteractServiceImpl implements WeixinInteractService {

	private static final Logger logger = Logger
			.getLogger(WeixinInteractServiceImpl.class);
	@Autowired
	WeixinInteractDAO weixinInteractDAO;
	@Autowired
	CommWechatTokenDAO commWechatTokenDAO;
	@Override
	public HashMap updateGetAccessToken(String appId,String appSecret) {
		HashMap result=new HashMap();
		Map paramMap=new HashMap();
		paramMap.put("appId",appId);
		HashMap tokenMap=commWechatTokenDAO.getByParam(paramMap);
		Date dateBefore30=DateUtil.getMinBefore(new Date(), 30);
		Date updateTime = DateUtil.parse(tokenMap.get("updateTime").toString(),"yyyy-MM-dd HH:mm:ss");
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
	
	public HashMap getOAuth2Authorize(String appId,String url) {
		HashMap result=weixinInteractDAO.getOAuth2Authorize(appId, url);
		return result;
		
	}
	public HashMap getOAuth2AccessToken(String appId,String appSecret,String code) {
		HashMap result=weixinInteractDAO.getOAuth2AccessToken(appId, appSecret, code);
		
		return result;
	}

	@Override
	public HashMap clearMenu(String accessToken) {
		HashMap result=weixinInteractDAO.clearMenu(accessToken);
		return result;
	}

	@Override
	public HashMap createMenu(String accessToken,Object jsonButton) {
		HashMap result=weixinInteractDAO.createMenu(accessToken, jsonButton);
		return result;
	}

	@Override
	public HashMap queryMenu(String accessToken) {
		HashMap result=weixinInteractDAO.queryMenu(accessToken);
		return result;
	}

	@Override
	public HashMap userInfo(String accessToken,String openid) {
		HashMap result=weixinInteractDAO.userInfo(accessToken, openid);
		return result;
	}
	
	@Override
	public HashMap openUserInfo(String accessToken,String openid) {
		HashMap result=weixinInteractDAO.getOpenUserInfo(accessToken, openid);
		return result;
	}
	
    /**
     * 根据用户的openid，调客服接口发送文本消息
     */
	@Override
	public HashMap sendText(String accessToken, String openid,String content) {
		HashMap result=weixinInteractDAO.sendText(accessToken, openid, content);
		return result;
	}

	@Override
	public HashMap sendMassText(String accessToken, Object openids, String content) {
		HashMap result=weixinInteractDAO.sendMassText(accessToken, openids, content);
		return result;
	}
	
	
	@Override
	public HashMap sendNews(String accessToken, Object openid,List<Map> articles) {
		HashMap result=weixinInteractDAO.sendNews(accessToken, openid, articles);
		return result;
	}
	

	@Override
	public HashMap sendMassMedia(String accessToken, List<String> openids,
			String msgType, String mediaId) {
		HashMap result=weixinInteractDAO.sendMassMedia(accessToken, openids, msgType, mediaId);
		return result;
	}
	
	@Override
	public HashMap uploadNews(String accessToken, List<Map> list) {
		HashMap result=weixinInteractDAO.uploadNews(accessToken, list);
		return result;
	}

	public HashMap createQrCodeTicket(String accessToken,Map paramMap) {
		HashMap result=weixinInteractDAO.createQrCodeTicket(accessToken, paramMap);
		return result;
	}
	
	public HashMap getQrCode(String ticket) {
		HashMap result=weixinInteractDAO.getQrCode(ticket);
		return result;
	}

	@Override
	public HashMap sendTmpMsg(String accessToken, Map messageProperty, Map data) {
		HashMap result=weixinInteractDAO.sendTmpMsg(accessToken, messageProperty, data);
		return result;
	}

}
