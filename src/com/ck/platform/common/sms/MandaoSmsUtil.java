package com.ck.platform.common.sms;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.codec.binary.Hex;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class MandaoSmsUtil {

	// private String mtUrl = "http://esms.etonenet.com/sms/mt";
	private static String mtUrl = "http://sdk2.entinfo.cn:8061/mdsmssend.ashx";
	// private static final String action = "send";
	// private static final String userid = "580";
	private static final String sn = "SDK-BBX-010-20738";
	private static final String pwd = "158e-[5[";

	// private static final String checkcontent = "1";

	/**
	 * 单条下行实例
	 * 
	 * @param mobile
	 *            手机号
	 * @param content
	 *            短信内容
	 * @return
	 * @throws Exception
	 */
	public static Map singleSms(String mobile, String content) {
		// 组成url字符串
		StringBuffer smsUrl = new StringBuffer("");
		try {

			// 下行内容以及编码格式，必填参数
			int dc = 15;
			String ecodeform = "GBK";
			String sm = "";
			try {
				sm = new String(Hex.encodeHex(content.getBytes(ecodeform)));
			} catch (UnsupportedEncodingException e) {
				sm = "";
			}

			smsUrl.append(mtUrl);
			smsUrl.append("?sn=" + sn);
			smsUrl.append("&pwd=" + getMD5(sn + pwd).toUpperCase());
			smsUrl.append("&mobile=" + mobile);
			smsUrl.append("&content=" + content);
			smsUrl.append("&ext=");
			smsUrl.append("&stime=");
			smsUrl.append("&rrid=");
			smsUrl.append("&msgfmt=");

			// smsUrl.append("&sendTime=");

			// smsUrl.append("&checkcontent=" + checkcontent);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		// 发送http请求，并接收 短信网关响应
		if (smsUrl.length() > 0) {

			System.out.println("smsUrl=" + smsUrl.toString());

			return send(smsUrl.toString());
		} else {
			return null;
		}

	}

	public static Map send(String url) {
		Map returnMap = new HashMap();

		try {
			URL conn_url = new URL(url);
			URLConnection con = conn_url.openConnection();
			InputStream is = con.getInputStream();
			byte[] b = new byte[22480];
			int readBytes = 0;

			while (readBytes < 22480) {
				int read = is.read(b, readBytes, 22480 - readBytes);
				System.out.println(read);
				if (read == -1) {
					break;
				}
				readBytes += read;
			}
			
			System.out.println("readBytes="+readBytes);
			
			returnMap.put("returnMessage", readBytes);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		System.out.println("returnMap=" + returnMap);

		return returnMap;

	}

	/**
	 * 将普通字符串转换成Hex编码字符串
	 * 
	 * @param dataCoding
	 *            编码格式，15表示GBK编码，8表示UnicodeBigUnmarked编码，0表示ISO8859-1编码
	 * @param realStr
	 *            普通字符串
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
					hexStr = new String(Hex.encodeHex(realStr
							.getBytes("UnicodeBigUnmarked")));
				} else {
					hexStr = new String(Hex.encodeHex(realStr
							.getBytes("ISO8859-1")));
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
	 * @param dataCoding
	 *            反编码格式，15表示GBK编码，8表示UnicodeBigUnmarked编码，0表示ISO8859-1编码
	 * @param hexStr
	 *            Hex编码字符串
	 * @return 普通字符串
	 */
	public static String decodeHexStr(int dataCoding, String hexStr) {
		String realStr = null;
		try {
			if (hexStr != null) {
				if (dataCoding == 15) {
					realStr = new String(Hex.decodeHex(hexStr.toCharArray()),
							"GBK");
				} else if ((dataCoding & 0x0C) == 0x08) {
					realStr = new String(Hex.decodeHex(hexStr.toCharArray()),
							"UnicodeBigUnmarked");
				} else {
					realStr = new String(Hex.decodeHex(hexStr.toCharArray()),
							"ISO8859-1");
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
	 * @param urlstr
	 *            完整的请求url字符串
	 * @return
	 */
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
			// e.printStackTrace();
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

	/**
	 * 将 短信下行 请求响应字符串解析到一个HashMap中
	 * 
	 * @param resStr
	 * @return
	 */
	public static HashMap<String, String> parseResStr(String resStr) {
		HashMap<String, String> pp = new HashMap<String, String>();
		try {
			String[] ps = resStr.split("&");
			for (int i = 0; i < ps.length; i++) {
				int ix = ps[i].indexOf("=");
				if (ix != -1) {
					pp.put(ps[i].substring(0, ix), ps[i].substring(ix + 1));
				}
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return pp;
	}

	/*
	 * 方法名称：getMD5 功 能：字符串MD5加密 参 数：待转换字符串 返 回 值：加密之后字符串
	 */
	public static String getMD5(String sourceStr)
			throws UnsupportedEncodingException {
		String resultStr = "";
		try {
			byte[] temp = sourceStr.getBytes();
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(temp);
			// resultStr = new String(md5.digest());
			byte[] b = md5.digest();
			for (int i = 0; i < b.length; i++) {
				char[] digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
						'9', 'A', 'B', 'C', 'D', 'E', 'F' };
				char[] ob = new char[2];
				ob[0] = digit[(b[i] >>> 4) & 0X0F];
				ob[1] = digit[b[i] & 0X0F];
				resultStr += new String(ob);
			}
			return resultStr;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void main(String[] args) {
		Map resultMap3 = MandaoSmsUtil.singleSms("15712927517", "中文【唯医】");
		// http://sdk.entinfo.cn:8061/webservice.asmx?sn=SDK-BBX-010-20738&pwd=a644bda556008690b1ca58bcc5abacef&mobile=18600565328&content=中文

		// http://sdk2.entinfo.cn:8061/mdsmssend.ashx?sn=SN&pwd=MD5(sn+password)&mobile=mobile&content=content&ext=&stime=&rrid=&msgfmt=

		// http://sdk2.entinfo.cn:8061/mdsmssend.ashx?sn=SDK-BBX-010-20738&pwd=A644BDA556008690B1CA58BCC5ABACEF&mobile=18600565328&content=中文
		// http://sdk2.entinfo.cn:8061/mdsmssend.ashx?sn=SDK-BBX-010-20738&pwd=A644BDA556008690B1CA58BCC5ABACEF&mobile=18600565328&content=中文
		// http://sdk2.entinfo.cn:8061/mdsmssend.ashx?sn=SN&pwd=MD5(sn+password)&mobile=mobile&content=content&ext=&stime=&rrid=&msgfmt=

	}

}
