package com.ck.platform.base.constants;
/**  
 * @desc: 系统输出编码
 * @author:Administrator 
 * @date：2015-3-31   
 */
public class ResponseCode {
	   /** 全局系统输出变量*/
	   public static final String GLOBAL_SUSSCESS="success";
	   public static final String GLOBAL_FAILTURE="fail";
	   public static final String GLOBAL_EXCEPTION="exception";
	   public static final boolean GLOBAL_YES=true;
	   public static final boolean GLOBAL_NO=false;
	   public static final String GLOBAL_INIT="init";
	   public static final String GLOBAL_SYSTEM_REPEAT="9X0001";  //数据重复
	   public static final String GLOBAL_SYSTEM_OUTDATE="9X0002"; //已过期
	   public static final String GLOBAL_SYSTEM_INCOMPLEMENT="9X0003"; //数据缺失
	   public static final String GLOBAL_SYSTEM_NONEXIST="9X0004"; //不存在
	   public static final String GLOBAL_SYSTEM_EXIST="9X0005"; //已存在
	   public static final String GLOBAL_SYSTEM_PARAM_ERROR="9X0006"; //参数错误
	   public static final String GLOBAL_SYSTEM_NOLOGIN="9X0007"; //未登录
	   public static final String GLOBAL_SYSTEM_AUTH_ERR="9X0008"; //权限不足
	   public static final String GLOBAL_SYSTEM_NET_ERR="9X0009"; //网络错误
	   public static final String GLOBAL_SYSTEM_NODATA="9X0010"; //无数据
	   
	   
	   /** 系统登录*/
	   public static final String RESPONSE_LOGIN_0A0001="0A0001";  //数据不完整
	   public static final String RESPONSE_LOGIN_0A0002="0A0002";  //格式验证失败
	   public static final String RESPONSE_LOGIN_0A0003="0A0003";  //用户名或密码错误
	   public static final String RESPONSE_LOGIN_0A0004="0A0004";  //登录成功，跳转到访问页面
	   public static final String RESPONSE_LOGIN_0A0005="0A0005";  //密码错误
	   
}

  
