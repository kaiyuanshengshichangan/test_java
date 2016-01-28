package com.ck.platform.common.sms;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.http.HttpStatus;

public class YiTongSmsUtil {

  private static String mtUrl = "http://esms.etonenet.com/sms/mt";
                               
  //private static String mtUrl = "http://esms2.10690007.net/sms/mt";
  private static final String spid = "3307";
  private static final String sppassword = "bjoy123";

  
  /**
   * 发送http GET请求，并返回http响应字符串
   * 
   * @param urlstr 完整的请求url字符串
   * @return
   */
  public static String doGetRequest(String urlstr) {
      String res = null;
      HttpClient client = new HttpClient(new MultiThreadedHttpConnectionManager());
      client.getParams().setIntParameter("http.socket.timeout", 10000);
      client.getParams().setIntParameter("http.connection.timeout", 5000);

      HttpMethod httpmethod = new GetMethod(urlstr);
      try {
          int statusCode = client.executeMethod(httpmethod);
          if (statusCode == HttpStatus.SC_OK) {
              res = httpmethod.getResponseBodyAsString();
          }
      } catch (Exception e) {
          e.printStackTrace();
      } finally {
          httpmethod.releaseConnection();
      }
      return res;
  }
  
  /**
   * 单条下行实例
   * @param cellPhone	手机号
   * @param smsContent 短信内容
   * @return
   * @throws Exception
   */
  public static String singleSms(String cellPhone,String smsContent){
  	
      //操作命令、SP编号、SP密码，必填参数
      String command = "MT_REQUEST";
      //sp服务代码，可选参数，默认为 00
      String spsc = "00";
      //源号码，可选参数
      String sa = "10657109053657";
      //目标号码，必填参数
      String da = "86"+cellPhone;
      //下行内容以及编码格式，必填参数
      int dc = 15;
      String ecodeform = "GBK";
      String sm = "";
		try {
			sm = new String(Hex.encodeHex(smsContent.getBytes(ecodeform)));
		} catch (UnsupportedEncodingException e) {
			sm = "";
		}
      //组成url字符串
      StringBuilder smsUrl = new StringBuilder();
      smsUrl.append(mtUrl);
      smsUrl.append("?command=" + command);
      smsUrl.append("&spid=" + spid);
      smsUrl.append("&sppassword=" + sppassword);
      smsUrl.append("&spsc=" + spsc);
      smsUrl.append("&sa=" + sa);
      smsUrl.append("&da=" + da);
      smsUrl.append("&sm=" + sm);
      smsUrl.append("&dc=" + dc);

      //发送http请求，并接收移通短信网关响应
      return doGetRequest(smsUrl.toString());
  }
  
  /**
   * 将普通字符串转换成Hex编码字符串
   * @param dataCoding 编码格式，15表示GBK编码，8表示UnicodeBigUnmarked编码，0表示ISO8859-1编码
   * @param realStr 普通字符串
   * @return Hex编码字符串
   * @throws UnsupportedEncodingException 
   */
  public static String encodeHexStr(int dataCoding, String realStr) {
      String hexStr = null;
      if (realStr != null) {
          try {
              if (dataCoding == 15) {
                  hexStr = new String(Hex.encodeHex(realStr.getBytes("GBK")));
              } else if ((dataCoding & 0x0C) == 0x08) {
                  hexStr = new String(Hex.encodeHex(realStr.getBytes("UnicodeBigUnmarked")));
              } else {
                  hexStr = new String(Hex.encodeHex(realStr.getBytes("ISO8859-1")));
              }
          } catch (UnsupportedEncodingException e) {
              System.out.println(e.toString());
          }
      }
      return hexStr;
  }
  
  /**
   * 将Hex编码字符串转换成普通字符串
   * 
   * @param dataCoding 反编码格式，15表示GBK编码，8表示UnicodeBigUnmarked编码，0表示ISO8859-1编码
   * @param hexStr Hex编码字符串
   * @return 普通字符串
   */
  public static String decodeHexStr(int dataCoding, String hexStr) {
      String realStr = null;
      try {
          if (hexStr != null) {
              if (dataCoding == 15) {
                  realStr = new String(Hex.decodeHex(hexStr.toCharArray()), "GBK");
              } else if ((dataCoding & 0x0C) == 0x08) {
                  realStr = new String(Hex.decodeHex(hexStr.toCharArray()), "UnicodeBigUnmarked");
              } else {
                  realStr = new String(Hex.decodeHex(hexStr.toCharArray()), "ISO8859-1");
              }
          }
      } catch (Exception e) {
          System.out.println(e.toString());
      }
      
      return realStr;
  }

  /**
   * 发送http GET请求，并返回http响应字符串
   * 
   * @param urlstr 完整的请求url字符串
   * @return
   */
  
  /*
  @SuppressWarnings("deprecation")
	public static String sendSmsRequest(String urlstr) {
      HttpClient client = new DefaultHttpClient();
      client.getParams().setIntParameter("http.socket.timeout", 10000);
      client.getParams().setIntParameter("http.connection.timeout", 5000);
      
      HttpEntity entity = null;
      String entityContent = null;
      try {
          HttpGet httpGet = new HttpGet(urlstr.toString());

          HttpResponse httpResponse = client.execute(httpGet);
          entityContent = EntityUtils.toString(httpResponse.getEntity());
          
      } catch (Exception e) {
          e.printStackTrace();
      } finally {
          if (entity != null) {
              try {
                  entity.consumeContent();
              } catch (Exception e) {
              	
              }
          }
      }
      return entityContent;
  }
  
  */
  /**
   * 将 短信下行 请求响应字符串解析到一个HashMap中
   * @param resStr
   * @return
   */
  public static HashMap<String,String> parseResStr(String resStr) {
      HashMap<String,String> pp = new HashMap<String,String>();
      try {
          String[] ps = resStr.split("&");
          for(int i=0;i<ps.length;i++){
              int ix = ps[i].indexOf("=");
              if(ix!=-1){
                 pp.put(ps[i].substring(0,ix),ps[i].substring(ix+1)); 
              }
          }
      } catch (Exception e) {
          System.out.println(e.toString());
      }
      return pp;
  }
  
  public static void main(String[] args){
	  YiTongSmsUtil.singleSms("18600565328", "yitong中文");
  }
  
}
