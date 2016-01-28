package com.ck.platform.business.weixin.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @desc: 微信业务接口Service
 * @author:lyq
 * @date：2014-6-5
 */
@SuppressWarnings("rawtypes")
public interface WeixinInteractService {
	/**
	 * 获取口令
	 * @return
	 */
	public HashMap updateGetAccessToken(String appId,String appSecret) ;
	/**
	 * 授权 请求
	 * @param url
	 * @return
	 */
	public HashMap getOAuth2Authorize(String appId,String url);
    
	/**
	 * 根据口令码获取 会员openid 及授权口令
	 * @param code
	 * @return
	 */
	public HashMap getOAuth2AccessToken(String appId,String appSecret,String code);
	
	/**
	 * 清除菜单结构
	 * @param accessToken
	 * @return
	 */
	public HashMap clearMenu(String accessToken);
	
	/**
	 * 创建菜单结构
	 * @param accessToken
	 * @param jsonButton
	 * @return
	 */
	public HashMap createMenu(String accessToken,Object jsonButton);
	
	/**
	 * 查询菜单结构
	 * @param accessToken
	 * @return
	 */
	public HashMap queryMenu(String accessToken);
	/**
	 * 会员信息
	 * @param accessToken
	 * @param openid
	 * @return
	 */
	public HashMap userInfo(String accessToken,String openid);
	
	/**
	 * 开放平台获取会员信息
	 * @param accessToken
	 * @param openid 开放平台－网站openId
	 * @return
	 */
	public HashMap openUserInfo(String accessToken,String openid);
	
	/**
	 * 发送单文本信息
	 * @param accessToken
	 * @param openid
	 * @return
	 */
	public HashMap sendText(String accessToken,String openid,String content);
	
	/**
	 * 发送模版消息
	 * @param accessToken
	 * @param messageProperty: toUser／templateId/url/topcolor
	 * @param data:
	 * @return
	 */
	public HashMap sendTmpMsg(String accessToken,Map messageProperty,Map data);
	
	/**
	 * 群发文本
	 * @param accessToken
	 * @param openids 微信openid （数组或字符串）
	 * @param content
	 * @return
	 */
	public HashMap sendMassText(String accessToken, Object openids, String content) ;
	
	/**
	 * 根据用户列表群发送单文本信息
	         "title":"Happy Day",
             "description":"Is Really A Happy Day",
             "url":"URL",
             "picurl":"PIC_URL"
	 * @param accessToken
	 * @param openid String或数组
	 * @return
	 */
	public HashMap sendNews(String accessToken, Object openid,List<Map> articles);
	
	
	/**
	 * 根据用户列表群发媒体信息
	 * @param accessToken
	 * @param openid
	 * @return
	 */
	public HashMap sendMassMedia(String accessToken,List<String> openids,String msgType,String mediaId);
	
	/**
	 * 上传图文信息
	 * @param accessToken
	 * @param List<Map> 
	 *  "thumb_media_id":"qI6_Ze_6PtV7svjolgs-rN6stStuHIjs9_DidOHaj0Q-mwvBelOXCFZiq2OsIU-p",  缩略图mediaId
         "author":"xxx",
		 "title":"Happy Day",
		 "content_source_url":"www.qq.com",
		 "content":"content",
		 "digest":"digest",
         "show_cover_pic":"1"
	 * @return
	 */
	public HashMap uploadNews(String accessToken,List<Map> list);
	
	/**
	 * 获取二维码生成票据
	 * @param accessToken
	 * @param paramMap （expire_seconds，action_name，action_info，scene_id（scene_str））
	 * @return
	 */
	public HashMap createQrCodeTicket(String accessToken,Map paramMap);
	
	/**
	 * 换取二维码
	 * @param ticket
	 * @return
	 */
	public HashMap getQrCode(String ticket);
	
}
