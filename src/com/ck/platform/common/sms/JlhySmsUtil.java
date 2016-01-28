package com.ck.platform.common.sms;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
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

public class JlhySmsUtil {

	// private String mtUrl = "http://esms.etonenet.com/sms/mt";
	private static String mtUrl = "http://118.145.18.124:8806/sms.aspx";
	private static final String action = "send";
	private static final String userid = "580";
	private static final String account = "weiyi";
	private static final String password = "123456aa";
	private static final String checkcontent = "1";

	/**
	 * 单条下行实例
	 * 
	 * @param cellPhone
	 *            手机号
	 * @param smsContent
	 *            短信内容
	 * @return
	 * @throws Exception
	 */
	public static Map singleSms(String cellPhone, String smsContent) {
		// 组成url字符串
		StringBuffer smsUrl = new StringBuffer("");
		try {

			// 下行内容以及编码格式，必填参数
			int dc = 15;
			String ecodeform = "GBK";
			String sm = "";
			try {
				sm = new String(Hex.encodeHex(smsContent.getBytes(ecodeform)));
			} catch (UnsupportedEncodingException e) {
				sm = "";
			}

			smsUrl.append(mtUrl);
			smsUrl.append("?action=" + action);
			smsUrl.append("&account=" + account);
			smsUrl.append("&userid=" + userid);
			smsUrl.append("&password=" + password);
			smsUrl.append("&mobile=" + cellPhone);
			smsUrl.append("&content=" + smsContent);
			// smsUrl.append("&sendTime=");

			// smsUrl.append("&checkcontent=" + checkcontent);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		// 发送http请求，并接收 短信网关响应
		if (smsUrl.length() > 0) {
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
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(is);
			System.out.println("doc=" + doc.getDocumentElement().getNodeName());
			Element root = doc.getDocumentElement();
			System.out.println("root=" + root.getNodeName());

			NodeList nodes = root.getChildNodes();
			System.out.println("nodes=" + nodes.getLength());
			for (int i = 0; i < nodes.getLength(); i++) {
				Node item = (Node) nodes.item(i);

				System.out.println("item=" + item.getNodeName());

				if (item.getNodeName().equalsIgnoreCase("returnstatus")) {
					returnMap.put("returnstatus", item.getTextContent());
				}

				if (item.getNodeName().equalsIgnoreCase("message")) {
					returnMap.put("message", item.getTextContent());

				}

				if (item.getNodeName().equalsIgnoreCase("remainpoint")) {
					returnMap.put("remainpoint", item.getTextContent());

				}

				if (item.getNodeName().equalsIgnoreCase("taskID")) {
					returnMap.put("taskID", item.getTextContent());

				}

				if (item.getNodeName().equalsIgnoreCase("successCounts")) {
					returnMap.put("successCounts", item.getTextContent());

				}

			}
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

	public static void main(String[] args) {
		JlhySmsUtil.singleSms("18901035808", "中文jlhy");
	}

}
