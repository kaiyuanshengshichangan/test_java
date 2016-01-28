package com.ck.platform.business.weixin.resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ck.platform.base.util.object.JsonUtil;
import com.ck.platform.base.util.object.StringTool;
import com.ck.platform.business.weixin.constant.WeixinConfig;
import com.ck.platform.business.weixin.entity.TemplateData;
import com.ck.platform.business.weixin.service.WeixinInteractService;

/**
 * 微信相关接口
 */
@Component("weixinInteractResource")
@Path("/weixin/interact")
public class WeixinInteractResource {

	private static final Logger logger = Logger.getLogger(WeixinInteractResource.class);

	@Autowired
	private WeixinInteractService weixinInteractService;

	@Value(value = "${weixin.service.appid}")
	private String weixin_service_appid = "";
	@Value(value = "${weixin.service.appsecret}")
	private String weixin_service_appsecret = "";
	
	/**
	 * 调用客服接口给微信会员发送信息 
	 * @param paramMap （conent/openid/appChannel）
	 * 如果多个openid用“,”分割 例“w3w4w22,2343sdf4”
	 * appChannel:公众号 1-唯医服务号 2-唯医订阅号 3－CAOS服务号 4-CAOS订阅号  
	 * @return
	 */
	@POST
	@Path("/sendMessage")
	@Produces({ MediaType.APPLICATION_JSON })
	public Map sendMessage(Map paramMap) {
		System.out.println("-----------wx_sendMessage:");
		String appChannel=StringTool.getMapString(paramMap, "appChannel"); //预留,多个公众号时区分
		String appId=weixin_service_appid;
		String appSecret=weixin_service_appsecret;
		if("2".equals(appChannel)){}
		HashMap accessTokenMap=weixinInteractService.updateGetAccessToken(appId,appSecret);
		String content=StringTool.getMapString(paramMap, "content");
		String openid=StringTool.getMapString(paramMap, "openid");  // 微信唯一标识，对应unite_name_weixin
		String accessToken=StringTool.getMapString(accessTokenMap, "access_token");
		if(StringUtils.isNotEmpty(openid) && openid.indexOf(",")>-1){
			List<String> openidsList=getOpenIdList(openid);
			return weixinInteractService.sendMassText(accessToken, openidsList, content);
		}else{
			return weixinInteractService.sendText(accessToken, openid, content);
		}
		
	}
	
	/**
	 * 调用客服接口给微信会员发送信息 
	 * @param paramMap （conent/openid/appChannel）
	 * 如果多个openid用“,”分割 例“w3w4w22,2343sdf4”
	 * appChannel:公众号 1-唯医服务号 2-唯医订阅号 3－CAOS服务号 4-CAOS订阅号  
	 * @return
	 */
	@POST
	@Path("/sendMassMessage")
	@Produces({ MediaType.APPLICATION_JSON })
	public Map sendMassMessage(Map paramMap) {
		System.out.println("-----------wx_sendMassMessage:");
		String appChannel=StringTool.getMapString(paramMap, "appChannel"); //预留,多个公众号时区分
		String appId=weixin_service_appid;
		String appSecret=weixin_service_appsecret;
		if("2".equals(appChannel)){}
		HashMap accessTokenMap=weixinInteractService.updateGetAccessToken(appId,appSecret);
		String content=StringTool.getMapString(paramMap, "content");
		String openid=StringTool.getMapString(paramMap, "openid");  // 微信唯一标识，对应unite_name_weixin
		String accessToken=StringTool.getMapString(accessTokenMap, "access_token");
		List<String> openidsList=getOpenIdList(openid);
		return weixinInteractService.sendMassText(accessToken, openidsList, content);
		
	}
	
	/**
	 * 发送图文信息
	 * @param paramMap
	 * appChannel:公众号 1-唯医服务号 2-唯医订阅号 3－CAOS服务号 4-CAOS订阅号
	 * @return
	 */
	@POST
	@Path("/sendNews")
	@Produces({ MediaType.APPLICATION_JSON })
	public Map sendNews(Map paramMap) {
		System.out.println("-----------wx_sendNews:");
		String appChannel=StringTool.getMapString(paramMap, "appChannel"); //预留,多个公众号时区分
		String appId=weixin_service_appid;
		String appSecret=weixin_service_appsecret;
		if("2".equals(appChannel)){}
		HashMap accessTokenMap=weixinInteractService.updateGetAccessToken(appId,appSecret);
		String content=StringTool.getMapString(paramMap, "content");
		String openid=StringTool.getMapString(paramMap, "openid");
		String accessToken=StringTool.getMapString(accessTokenMap, "access_token");
		String articles=StringTool.getMapString(paramMap, "articles");//数组字符串
		List<Map> articlesList=JsonUtil.getList4Json(articles, HashMap.class);
		Object openids=openid;
		if(StringUtils.isNotEmpty(openid) && openid.indexOf(",")>-1){
			openids=getOpenIdList(openid);
		}
		return weixinInteractService.sendNews(accessToken, openids, articlesList);
	}
	
	/**
	 * 发送模板信息
	 * @param paramMap （touser、template_id、url、first、keyword1、keyword2、keyword4、keyword5、remark）
	 * openid：用户的openid
	 * template_id：模版id
	 * url ：消息地址
	 * first：标题
	 * keywordN：第N个关键词
	 * remark:备注
	 * appChannel:公众号 1-唯医服务号 2-唯医订阅号 3－CAOS服务号 4-CAOS订阅号
	 * @return
	 */
	@POST
	@Path("/sendTemplateMsg")
	@Produces({ MediaType.APPLICATION_JSON })
	public Map sendTemplateMsg(Map paramMap){
		System.out.println("-----------wx_sendTemplateMsg:");
		String appChannel=StringTool.getMapString(paramMap, "appChannel"); //预留,多个公众号时区分
		String appId=weixin_service_appid;
		String appSecret=weixin_service_appsecret;
		HashMap accessTokenMap=weixinInteractService.updateGetAccessToken(appId,appSecret);
		String accessToken=StringTool.getMapString(accessTokenMap, "access_token");
		
		Map tmpProperty=new HashMap();
		tmpProperty.put("touser", StringTool.getMapString(paramMap, "openid"));
		tmpProperty.put("template_id",StringTool.getMapString(paramMap, "template_id"));
		tmpProperty.put("url",StringTool.getMapString(paramMap, "url"));
		tmpProperty.put("topcolor",StringTool.getMapString(paramMap, "topcolor"));
		Map data=new HashMap();
		Map<String,Object> first=new HashMap<String,Object>();
		data.put("first",new TemplateData(StringTool.getMapString(paramMap, "first"),""));
		data.put("keyword1",new TemplateData(StringTool.getMapString(paramMap, "keyword1"),""));
	    data.put("keyword2",new TemplateData(StringTool.getMapString(paramMap, "keyword2"),""));
	    data.put("keyword3",new TemplateData(StringTool.getMapString(paramMap, "keyword3"),""));
	    data.put("keyword4",new TemplateData(StringTool.getMapString(paramMap, "keyword4"),""));
	    data.put("keyword5",new TemplateData(StringTool.getMapString(paramMap, "keyword5"),""));
		data.put("remark",new TemplateData(StringTool.getMapString(paramMap, "remark"),""));
		return weixinInteractService.sendTmpMsg(accessToken, tmpProperty, data);
	}
	
	/**
	 * 获取口令
	 * @param paramMap
	 * @return
	 */
	@POST
	@Path("/accessToken")
	@Produces({ MediaType.APPLICATION_JSON })
	public HashMap accessToken(Map paramMap) {
		String appChannel=StringTool.getMapString(paramMap, "appChannel"); //预留,多个公众号时区分
		String appId=weixin_service_appid;
		String appSecret=weixin_service_appsecret;;
		HashMap accessTokenMap=weixinInteractService.updateGetAccessToken(appId,appSecret);
		return accessTokenMap;
	}
	
	/**
	 * 获取微信授权链接
	 * @param paramMap
	 * @return
	 */
	@POST
	@Path("/oAuth2Authorize")
	@Produces({ MediaType.APPLICATION_JSON })
	public HashMap oAuth2Authorize(Map paramMap) {
		String appChannel=StringTool.getMapString(paramMap, "appChannel"); //预留,多个公众号时区分
		String url=StringTool.getMapString(paramMap, "url");
		String appId=weixin_service_appid;
		HashMap result=weixinInteractService.getOAuth2Authorize(appId, url);
		return result;
	}

	/**
	 * 获取微信授权链接口令
	 * @param paramMap
	 * @return
	 */
	@POST
	@Path("/oAuth2AccessToken")
	@Produces({ MediaType.APPLICATION_JSON })
	public HashMap oAuth2AccessToken(Map paramMap) {
		String appChannel=StringTool.getMapString(paramMap, "appChannel"); //预留,多个公众号时区分
		String code=StringTool.getMapString(paramMap, "code");
		String appId=weixin_service_appid;
		String appSecret=weixin_service_appsecret;
		if("2".equals(appChannel)){
			appId=WeixinConfig.OPEN_SITE_APP_ID;
			appSecret=WeixinConfig.OPEN_SITE_APP_SECRET;	
		}
		HashMap result=weixinInteractService.getOAuth2AccessToken(appId, appSecret, code);
		return result;
	}
	
	@POST
	@Path("/clearMenu")
	@Produces({ MediaType.APPLICATION_JSON })
	public HashMap clearMenu(Map paramMap) {
		String appChannel=StringTool.getMapString(paramMap, "appChannel"); //预留,多个公众号时区分
		String appId=weixin_service_appid;
		String appSecret=weixin_service_appsecret;
		HashMap accessTokenMap=weixinInteractService.updateGetAccessToken(appId,appSecret);
		String accessToken=StringTool.getMapString(accessTokenMap, "access_token");
		HashMap result=weixinInteractService.clearMenu(accessToken);
		return result;
	}

	@POST
	@Path("/createMenu")
	@Produces({ MediaType.APPLICATION_JSON })
	public HashMap createMenu(Map paramMap) {
		String appChannel=StringTool.getMapString(paramMap, "appChannel"); //预留,多个公众号时区分
		String jsonButton=StringTool.getMapString(paramMap, "jsonButton");
		String appId=weixin_service_appid;
		String appSecret=weixin_service_appsecret;
		HashMap accessTokenMap=weixinInteractService.updateGetAccessToken(appId,appSecret);
		String accessToken=StringTool.getMapString(accessTokenMap, "access_token");
		HashMap result=weixinInteractService.createMenu(accessToken, jsonButton);
		return result;
	}

	@POST
	@Path("/queryMenu")
	@Produces({ MediaType.APPLICATION_JSON })
	public HashMap queryMenu(Map paramMap) {
		String appChannel=StringTool.getMapString(paramMap, "appChannel"); //预留,多个公众号时区分
		String appId=weixin_service_appid;
		String appSecret=weixin_service_appsecret;
		HashMap accessTokenMap=weixinInteractService.updateGetAccessToken(appId,appSecret);
		String accessToken=StringTool.getMapString(accessTokenMap, "access_token");
		HashMap result=weixinInteractService.queryMenu(accessToken);
		return result;
	}
	
	/**
	 * 用户基本信息
	 * @param paramMap
	 * @return
	 */
	@POST
	@Path("/userInfo")
	@Produces({ MediaType.APPLICATION_JSON })
	public HashMap userInfo(Map paramMap) {
		String appChannel=StringTool.getMapString(paramMap, "appChannel"); //预留,多个公众号时区分
		String openid=StringTool.getMapString(paramMap, "openid");
		String code=StringTool.getMapString(paramMap, "code"); //通过开放平台获取用户信息时用到
		String appId=weixin_service_appid;
		String appSecret=weixin_service_appsecret;
		HashMap result=null;
		if("2".equals(appChannel)){
			appId=WeixinConfig.OPEN_SITE_APP_ID;
			appSecret=WeixinConfig.OPEN_SITE_APP_SECRET;
			HashMap accessTokenMap=weixinInteractService.getOAuth2AccessToken(appId, appSecret, code);
			String accessToken=StringTool.getMapString(accessTokenMap, "access_token");
			result=weixinInteractService.openUserInfo(accessToken, openid);
		}else{
			appId=weixin_service_appid;
			appSecret=weixin_service_appsecret;
			HashMap accessTokenMap=weixinInteractService.updateGetAccessToken(appId,appSecret);
			String accessToken=StringTool.getMapString(accessTokenMap, "access_token");
			result=weixinInteractService.userInfo(accessToken, openid);
		}
		return result;
	}
    
	/**
	 * 批量发送媒体文件
	 * @param paramMap
	 * @return
	 */
	@POST
	@Path("/sendMassMedia")
	@Produces({ MediaType.APPLICATION_JSON })
	public HashMap sendMassMedia(Map paramMap) {
		String appChannel=StringTool.getMapString(paramMap, "appChannel"); //预留,多个公众号时区分
		String mediaId=StringTool.getMapString(paramMap, "mediaId");
		String msgType=StringTool.getMapString(paramMap, "msgType");
		String openidsStr=StringTool.getMapString(paramMap, "openids");
		String appId=weixin_service_appid;
		String appSecret=weixin_service_appsecret;
		HashMap accessTokenMap=weixinInteractService.updateGetAccessToken(appId,appSecret);
		String accessToken=StringTool.getMapString(accessTokenMap, "access_token");
		String openidsArr []=openidsStr.split(",");
		List openidList=new ArrayList();
		openidList.addAll(openidList);
		HashMap result=weixinInteractService.sendMassMedia(accessToken,openidList , msgType, mediaId);
		return result;
	}
	
	/**
	 * 创建二维码票据
	 * @param paramMap
	 * @return
	 */
	@POST
	@Path("/createQrCodeTicket")
	@Produces({ MediaType.APPLICATION_JSON })
	public HashMap createQrCodeTicket(Map paramMap) {
		String appChannel=StringTool.getMapString(paramMap, "appChannel"); //预留,多个公众号时区分
		String appId=weixin_service_appid;
		String appSecret=weixin_service_appsecret;
		HashMap accessTokenMap=weixinInteractService.updateGetAccessToken(appId,appSecret);
		String accessToken=StringTool.getMapString(accessTokenMap, "access_token");
		HashMap result=weixinInteractService.createQrCodeTicket(accessToken, paramMap);
		return result;
	}
	
	/**
	 * 获取二维码
	 * @param ticket
	 * @return
	 */
	@POST
	@Path("/qrCodeInfo")
	@Produces({ MediaType.APPLICATION_JSON })
	public HashMap qrCodeInfo(String ticket) {
		HashMap result=weixinInteractService.getQrCode(ticket);
		return result;
	}


	/**
	 * 数组转换，将以逗号分割的字符串转换为数组
	 * @param openids
	 * @return
	 */
	private List getOpenIdList(String openids){
		List<String> openidsList=new ArrayList();
		if(StringUtils.isNotEmpty(openids)){
			if(openids.indexOf(",")>-1){
				String [] openidArr=openids.split(",");
				for(String openid:openidArr){
					openidsList.add(openid.trim());
				}
			}else{
				openidsList.add(openids.trim());
			}
		}
		return openidsList;
	}
}