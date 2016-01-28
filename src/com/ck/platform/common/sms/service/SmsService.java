package com.ck.platform.common.sms.service;

import java.util.Date;
import java.util.Map;

import com.ck.platform.base.util.BaseResponseObject;
import com.ck.platform.base.util.core.page.Page;

public interface SmsService {

	BaseResponseObject sendMessage(Map paramMap);

	boolean validPhoneNumber(String mobileNumber);
	
	void asyncUpdateSmsStatus(Map<String, Object> paramMap);
	
	Page query(int smsStatus, int startPage, int pageSize, Date startTime,Date endTime, String cell);

}
