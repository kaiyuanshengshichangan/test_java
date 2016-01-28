package com.ck.platform.business.weixin.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.restlet.data.Method;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.ck.platform.base.util.MapUtil;
import com.ck.platform.base.util.core.rest.RestInvokeUtil;
import com.ck.platform.base.util.object.JsonUtil;
import com.ck.platform.business.weixin.constant.MsgType;
import com.ck.platform.business.weixin.constant.WeixinAPI;
import com.ck.platform.business.weixin.dao.WeixinInteractDAO;
@Repository("weixinInteractDAO")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class WeixinInteractDAOImpl implements WeixinInteractDAO {

	private static final Logger logger = Logger
			.getLogger(WeixinInteractDAOImpl.class);
	@Value(value = "${weixin.service.appid}")
	private String weixin_service_appid = "";
	@Value(value = "${weixin.service.appsecret}")
	private String weixin_service_appsecret = "";
	
	@Override
	public HashMap getAccessToken(String appId,String appSecret) {
		HashMap result=null;
		if(StringUtils.isEmpty(appId)){
			appId=weixin_service_appid;
		}
		if(StringUtils.isEmpty(appSecret)){
			appSecret=weixin_service_appsecret;
		}
		String invoke_url=WeixinAPI.URL_ACCESS_TOKEN+"?grant_type=client_credential&appid="+appId+"&secret="+appSecret;
		try {
			String text=RestInvokeUtil.invokeURL(invoke_url,Method.GET, null);
			if(StringUtils.isNotEmpty(text)){
				result = (HashMap) JsonUtil.getObject4JsonString(text, HashMap.class);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public HashMap getOAuth2Authorize(String appId,String url) {
		HashMap result=null;
		if(StringUtils.isEmpty(appId)){
			appId=weixin_service_appid;
		}
		String invoke_url=WeixinAPI.URL_OAUTH2_AUTHORIZE+"?appid="+appId+"&redirect_uri="+url+"&response_type=code&state=1#wechat_redirect";
		try {
			String text=RestInvokeUtil.invokeURL(invoke_url,Method.GET, null);
			if(StringUtils.isNotEmpty(text)){
				result = (HashMap) JsonUtil.getObject4JsonString(text, HashMap.class);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
		
	}

	public HashMap getOAuth2AccessToken(String appId,String appSecret,String code) {
		HashMap result=null;
		if(StringUtils.isEmpty(appId)){
			appId=weixin_service_appid;
		}
		if(StringUtils.isEmpty(appSecret)){
			appSecret=weixin_service_appsecret;
		}
		String invoke_url=WeixinAPI.URL_OAUTH2_ACCESS_TOKEN+"?appid="+appId+"&secret="+appSecret+"&code="+code+"&&grant_type=authorization_code";
		logger.info("--------------=invoke_url:"+invoke_url);
		try {
			String text=RestInvokeUtil.invokeURL(invoke_url,Method.GET, null);
			if(StringUtils.isNotEmpty(text)){
				result = (HashMap) JsonUtil.getObject4JsonString(text, HashMap.class);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public HashMap clearMenu(String accessToken) {
		HashMap result=null;
		String invoke_url=WeixinAPI.URL_MENU_DELETE+"?access_token="+accessToken;
		try {
			String text=RestInvokeUtil.invokeURL(invoke_url,Method.GET, null);
			if(StringUtils.isNotEmpty(text)){
				result = (HashMap) JsonUtil.getObject4JsonString(text, HashMap.class);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public HashMap createMenu(String accessToken,Object jsonButton) {
		HashMap result=null;
		String invoke_url=WeixinAPI.URL_MENU_CREATE+"?access_token="+accessToken;
		try {
			
			String text=RestInvokeUtil.invokeURL(invoke_url,Method.POST, jsonButton);
			if(StringUtils.isNotEmpty(text)){
				result = (HashMap) JsonUtil.getObject4JsonString(text, HashMap.class);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public HashMap queryMenu(String accessToken) {
		HashMap result=null;
		String invoke_url=WeixinAPI.URL_MENU_DELETE+"?access_token="+accessToken;
		try {
			String text=RestInvokeUtil.invokeURL(invoke_url,Method.GET, null);
			if(StringUtils.isNotEmpty(text)){
				result = (HashMap) JsonUtil.getObject4JsonString(text, HashMap.class);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public HashMap userInfo(String accessToken,String openid) {
		HashMap result=null;
		String invoke_url=WeixinAPI.URL_USER_INFO+"?access_token="+accessToken+"&openid="+openid+"&lang=zh_CN";
		try {
			String text=RestInvokeUtil.invokeURL(invoke_url,Method.GET, null);
			if(StringUtils.isNotEmpty(text)){
				result = (HashMap) JsonUtil.getObject4JsonString(text, HashMap.class);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
    /**
     * 根据用户的openid，调客服接口发送文本消息
     */
	@Override
	public HashMap sendText(String accessToken, String openid,String content) {
		HashMap result=null;
		String invoke_url=WeixinAPI.URL_MESSAGE_SEND+"?access_token="+accessToken;
		try {
			Map paramMap=new HashMap();
			paramMap.put("touser", openid);
			paramMap.put("msgtype", "text")	;
			Map msgText=new HashMap();
			msgText.put("content", content);
			paramMap.put("text", msgText);
			String text=RestInvokeUtil.invokeURL(invoke_url,Method.POST, paramMap);
			if(StringUtils.isNotEmpty(text)){
				result = (HashMap) JsonUtil.getObject4JsonString(text, HashMap.class);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public HashMap sendMassText(String accessToken, Object openids, String content) {
		HashMap result=null;
		String invoke_url=WeixinAPI.URL_QUN_MESSAGE_SEND+"?access_token="+accessToken;
		try {
			Map paramMap=new HashMap();
			paramMap.put("touser", openids);
			paramMap.put("msgtype", "text")	;
			Map msgText=new HashMap();
			msgText.put("content", content);
			paramMap.put("text", msgText);
			String text=RestInvokeUtil.invokeURL(invoke_url,Method.POST, paramMap);
			if(StringUtils.isNotEmpty(text)){
				result = (HashMap) JsonUtil.getObject4JsonString(text, HashMap.class);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	
	@Override
	public HashMap sendNews(String accessToken, Object openid,List<Map> articles) {
		HashMap result=null;
		String invoke_url=WeixinAPI.URL_MESSAGE_SEND+"?access_token="+accessToken;
		try {
			Map paramMap=new HashMap();
			paramMap.put("touser", openid);
			paramMap.put("msgtype", "news")	;
			Map msgText=new HashMap();
			msgText.put("articles", articles);
			paramMap.put("news", msgText);
			String text=RestInvokeUtil.invokeURL(invoke_url,Method.POST, paramMap);
			if(StringUtils.isNotEmpty(text)){
				result = (HashMap) JsonUtil.getObject4JsonString(text, HashMap.class);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	

	@Override
	public HashMap sendMassMedia(String accessToken, List<String> openids,
			String msgType, String mediaId) {
		HashMap result=null;
		String invoke_url=WeixinAPI.URL_MESSAGE_SEND+"?access_token="+accessToken;
		try {
			Map paramMap=new HashMap();
			paramMap.put("touser", openids);
			Map msgText=new HashMap();
			msgText.put("media_id", mediaId);
			if(MsgType.NEWS.equals(msgType)){
				paramMap.put("msgtype", "mpnews");
				paramMap.put("mpnews", msgText);
			}else if(MsgType.IMAGE.equals(msgType)){
				paramMap.put("msgtype", "image");
				paramMap.put("image", msgText);
			}else if(MsgType.VOICE.equals(msgType)){
				paramMap.put("msgtype", "voice");
				paramMap.put("voice", msgText);
			}
		//	String text=RestInvokeUtil.invokeURL(invoke_url,Method.POST, paramMap);
//			if(StringUtils.isNotEmpty(text)){
//				result = (HashMap) JsonUtil.getObject4JsonString(text, HashMap.class);
//			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public HashMap uploadNews(String accessToken, List<Map> list) {
		HashMap result=null;
		String invoke_url=WeixinAPI.URL_MEDIA_UPLOADNEWS+"?access_token="+accessToken;
		try {
			Map paramMap=new HashMap();
			paramMap.put("articles", list);			
			String text=RestInvokeUtil.invokeURL(invoke_url,Method.POST, paramMap);
			if(StringUtils.isNotEmpty(text)){
				result = (HashMap) JsonUtil.getObject4JsonString(text, HashMap.class);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public HashMap createQrCodeTicket(String accessToken,Map paramMap) {
		HashMap result=null;
		String invoke_url=WeixinAPI.URL_QRCODE_CREATE+"?access_token="+accessToken;
		try {
			String text=RestInvokeUtil.invokeURL(invoke_url,Method.POST, paramMap);
			if(StringUtils.isNotEmpty(text)){
				result = (HashMap) JsonUtil.getObject4JsonString(text, HashMap.class);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public HashMap getQrCode(String ticket) {
		HashMap result=null;
		String invoke_url=WeixinAPI.URL_QRCODE_SHOW+"?ticket="+ticket;
		try {
			String text=RestInvokeUtil.invokeURL(invoke_url,Method.GET,null);
			if(StringUtils.isNotEmpty(text)){
				result = (HashMap) JsonUtil.getObject4JsonString(text, HashMap.class);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public HashMap sendTmpMsg(String accessToken, Map messageProperty, Map data) {
		HashMap result=null;
		String invoke_url=WeixinAPI.URL_TEMPLATE_MESSAGE_SEND+"?access_token="+accessToken;
		try {
			Map paramMap=new HashMap();
			paramMap.putAll(messageProperty);	
			paramMap.put("data", data);
			String text=RestInvokeUtil.invokeURL(invoke_url,Method.POST, paramMap);
			if(StringUtils.isNotEmpty(text)){
				result = (HashMap) JsonUtil.getObject4JsonString(text, HashMap.class);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public HashMap getOpenUserInfo(String accessToken, String openId) {
		HashMap userInfoResult=null;
		 String invoke_userInfo_url=WeixinAPI.OPEN_SITE_USER_INFO+"?access_token="+accessToken+"&openid="+openId+"&lang=zh_CN";
		 try{
             String userInfo=RestInvokeUtil.invokeURL(invoke_userInfo_url,Method.GET,null);	
			 if(StringUtils.isNotEmpty(userInfo)){
				 //userInfoResult=(HashMap) JsonUtil.getObject4JsonString(userInfo, HashMap.class);;//有问题
				 userInfoResult=(HashMap)MapUtil.parserToMap(userInfo);
			 }
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		return userInfoResult;
	}

}
