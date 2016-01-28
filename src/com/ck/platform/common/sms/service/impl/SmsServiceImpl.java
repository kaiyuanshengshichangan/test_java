package com.ck.platform.common.sms.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ck.platform.base.util.BaseResponseObject;
import com.ck.platform.base.util.core.page.Page;
import com.ck.platform.common.sms.JlhySmsUtil;
import com.ck.platform.common.sms.MandaoSmsUtil;
import com.ck.platform.common.sms.WangxintongSmsUtil;
import com.ck.platform.common.sms.YiTongSmsUtil;
import com.ck.platform.common.sms.service.SmsService;

@Service("smsService")
public class SmsServiceImpl implements SmsService {

	@Value(value = "${sms.send.channel}")
	private String sms_send_channel = "";

	@Override
	public BaseResponseObject sendMessage(Map paramMap) {

		BaseResponseObject baseResponseObject = new BaseResponseObject(false,
				"", "");

		boolean smsStatus = false;
		Map smsMap = new HashMap();

		try {

			String mobileNumber = (String) paramMap.get("mobileNumber");
			String smsContent = (String) paramMap.get("smsContent");

			System.out.println("mobileNumber=" + mobileNumber +":"+smsContent);
			if (null != smsContent && smsContent.length() == 0) {
				return new BaseResponseObject(Boolean.FALSE, "10001", "短信内容为空");
			}
			// 1-jlhy 
			switch (Integer.parseInt(sms_send_channel)) {
			case 1:
				// JLHY
				Map resultMap2 = JlhySmsUtil.singleSms(mobileNumber, smsContent);
				String smsDesc = "";
				if (null != resultMap2 && resultMap2.get("returnstatus") != null
						&& "success".equals(resultMap2.get("returnstatus"))) {
					smsStatus = true;
					smsDesc = (String) resultMap2.get("message");

					baseResponseObject.setResponseStatus(Boolean.TRUE);
					baseResponseObject.setResponseMessage(smsDesc);
				}
				break;
			case 2: //2-yitong
				// 给短信供应商发送短信请求
				// 记录短信发送日志
				// command=MT_RESPONSE&spid=100102&mtmsgid=13566666755277480&mtstat=ACCEPTD&mterrcode=000
				String result = YiTongSmsUtil.singleSms((String) paramMap.get("cellPhone"),(String) paramMap.get("smsContent"));
				HashMap<String, String> resultMap = new HashMap<String, String>();
				if (result != null) {
					// 解析Sms供应商服务端响应
					resultMap = YiTongSmsUtil.parseResStr(result);
					System.out.println(resultMap);
					/** ACCEPTD：正常接收 REJECTD：拒绝接收 DELIVRD：短信送到手机 */
					if (resultMap.get("mtstat") != null
							&& "ACCEPTD".equals(resultMap.get("mtstat"))) {
						smsStatus = true;
					}
				}
				break;
			case 3:
				// JLHY
				Map resultMap3 = MandaoSmsUtil.singleSms(mobileNumber,smsContent);
				if (null != resultMap3&& resultMap3.get("returnMessage") != null) {
					smsStatus = true;
					smsDesc = resultMap3.get("returnMessage").toString();
					baseResponseObject.setResponseStatus(Boolean.TRUE);
					baseResponseObject.setResponseMessage(smsDesc);
				}
				break;
			case 4:
				// Wangxintong
				Map resultMap4 = WangxintongSmsUtil.singleSms(mobileNumber,smsContent);
				if (null != resultMap4 && resultMap4.get("returnMessage") != null) {
					smsStatus = true;
					smsDesc = resultMap4.get("returnMessage").toString();
					baseResponseObject.setResponseStatus(Boolean.TRUE);
					baseResponseObject.setResponseMessage(smsDesc);
				}
				break;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("baseResponseObject=" + baseResponseObject);
		if (smsStatus) {
			return new BaseResponseObject(Boolean.TRUE, "SMS0001", "短信发送成功");
		} else {
			return new BaseResponseObject(Boolean.FALSE, "SMS0002", "短信发送失败");
		}

	}

	public void asyncUpdateSmsStatus(Map<String, Object> paramMap) {
		try {
			Map smsMap = new HashMap();
			smsMap.put("callbackTime", new Date());
			smsMap.put("mtmsgid", (String) paramMap.get("mtmsgid"));
			smsMap.put("callbackMtstat", (String) paramMap.get("mtstat"));
			smsMap.put("callbackMterrcode", (String) paramMap.get("mterrcode"));
			//logSmsDao.update(smsMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean validPhoneNumber(String mobileNumber) {
		Pattern p = Pattern
				.compile("^((13[0-9])|(15[^4,\\D])|(17[0-9])|(18[0-9])|(14[0-9]))\\d{8}$");
		Matcher m = p.matcher(mobileNumber);
		return m.matches();
	}

	/**
	 * <select> <option>待发送：表示信息还保存在随意通系统之中，有待发送给移通网络短信平台</option>
	 * <option>发送完成：表示信息已经从随意通系统发送出去</option>
	 * <option>发送成功：表示信息已经从随意通系统发送出去，并且用户成功收到</option>
	 * <option>发送失败：表示信息从随意通系统发送出去的时候失败了</option>
	 * <option>接收失败：表示信息从随意通系统成功发送到移通网络短信平台或者中国移动平台
	 * 。但是从运营商平台发到用户手机时，用户接收失败</option> <option>余额不足：表示发送信息的此用户当前余额已经用完</option>
	 * </select>
	 */
	@Override
	public Page query(int smsStatus, int startPage, int pageSize,
			Date startTime, Date endTime, String cell) {
		return null;// smsDao.query(smsStatus, startPage, pageSize, startTime,
					// endTime, cell);
	}
	
	
}
