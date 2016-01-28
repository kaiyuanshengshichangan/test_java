package com.ck.platform.business.weixin.constant;
/**
 * 微信API接口 配置
 * @author lyq
 *
 */
public class WeixinAPI {
	//GET 获取access_token ?grant_type=client_credential&appid=&secret= 每日2000次
	public final static String URL_ACCESS_TOKEN="https://api.weixin.qq.com/cgi-bin/token";
	//POST 创建菜单?access_token=
	public final static String URL_MENU_CREATE="https://api.weixin.qq.com/cgi-bin/menu/create";   
	//GET 获取菜单?access_token=
	public final static String URL_MENU_GET="https://api.weixin.qq.com/cgi-bin/menu/get";   
	//GET 删除菜单?access_token=
	public final static String URL_MENU_DELETE="https://api.weixin.qq.com/cgi-bin/menu/delete"; 
	//POST 生成二维码票据?access_token='  {"action_name": "QR_LIMIT_STR_SCENE[QR_SCENE/QR_LIMIT_SCENE]", "action_info": {"scene": {"scene_str": "123"}}}
	public final static String URL_QRCODE_CREATE="https://api.weixin.qq.com/cgi-bin/qrcode/create";
	//GET 换取二维码?TICKET=
	public final static String URL_QRCODE_SHOW="https://mp.weixin.qq.com/cgi-bin/showqrcode"; 
	//用户授权 ?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect    (snsapi_base （不弹出授权页面，直接跳转，只能获取用户openid），snsapi_userinfo )
	public final static String URL_OAUTH2_AUTHORIZE="https://open.weixin.qq.com/connect/oauth2/authorize";
	//获取授权口令（或openID） ?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code
	public final static String URL_OAUTH2_ACCESS_TOKEN ="https://api.weixin.qq.com/sns/oauth2/access_token";
	//获取用户基本信息?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN
	public final static String URL_USER_INFO="https://api.weixin.qq.com/cgi-bin/user/info";
	//获取用户关注人 ?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID （next_openid 第一个拉取的人，不填写默认从头）
	public final static String URL_USER_GET="https://api.weixin.qq.com/cgi-bin/user/get";
	//GET 下载多媒体?access_token=ACCESS_TOKEN&media_id=MEDIA_ID
	public final static String URL_MEDIA_GET="http://file.api.weixin.qq.com/cgi-bin/media/get";
	//POST 上传多媒体?access_token=ACCESS_TOKEN&type=TYPE（image\video\voice\thumb） post(media)
	public final static String URL_MEDIA_UPLOAD="http://file.api.weixin.qq.com/cgi-bin/media/upload";
	//POST 上传图文信息 ?access_token=ACCESS_TOKEN
	public final static String URL_MEDIA_UPLOADNEWS="https://api.weixin.qq.com/cgi-bin/media/uploadnews";
	//POST 群发 ?access_token=ACCESS_TOKEN (全部或根据分组)
	public final static String URL_MESSAGE_SENDALL="https://api.weixin.qq.com/cgi-bin/message/mass/sendall";
	//POST  根据openid列表进行消息的群发
	public final static String URL_QUN_MESSAGE_SEND="https://api.weixin.qq.com/cgi-bin/message/mass/send";
	//POST 发送消息 ?access_token=ACCESS_TOKEN (订阅号不可用,服务号认证后可用)客户接口,调用次数没限制
	public final static String URL_MESSAGE_SEND="https://api.weixin.qq.com/cgi-bin/message/custom/send";
	//get 查询用户所在分组
	public  final  static  String  URL_QUERY_GROUPID="https://api.weixin.qq.com/cgi-bin/groups/getid";
	//模版消息?access_token=ACCESS_TOKEN
	public  final  static  String  URL_TEMPLATE_MESSAGE_SEND="https://api.weixin.qq.com/cgi-bin/message/template/send";

	//开放平台网站二维码，获取用户信息
	public final static String OPEN_SITE_USER_INFO="https://api.weixin.qq.com/sns/userinfo";
	
	
}
