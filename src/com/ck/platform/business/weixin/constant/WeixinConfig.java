package com.ck.platform.business.weixin.constant;
/**
 * 微信开发 配置
 * appChannel:1-微信服务号   2-开放平台对应的PC网站
 * @author lyq
 */
public class WeixinConfig {
	//公共配置
	public final static String PROPERTY_OPEN_ID = "weixin_open_id"; //微信用户openId session 名称 
	
	//唯医服务号配置
	public final static String TOKEN = "allin123";
//	public final static String APP_ID="wx8d4a2df6fdc13658"; //改为配置文件
//	public final static String APP_SECRET="bde0b53710661df660a49d120a11d5e5";
	public static final String OPEN_SITE_APP_ID = "wx9bf385b65d0ae649"; //开放平台对应的PC网站的app_id
	public static final String OPEN_SITE_APP_SECRET="980e4fc248f5d21f2a82d8cfd88b1444";//开放平台对应的PC网站的app_secrect
//	public final static String APP_ID="wx9a9374db14cd27e7";  //  测试的 
//	public final static String APP_SECRET="202ed7820b566487f21f99a65016a1ce";//测试的
	public final static String ENCODING_AES_KEY="";
	public final static String UNITE_LOGIN_ALLIN = "http://m.allinmd.cn/mcall/wx/customer/isWeixinBind/"; //唯医用户登录
	
	
	
}
