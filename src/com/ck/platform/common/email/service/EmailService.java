package com.ck.platform.common.email.service;

import java.util.Map;

import com.ck.platform.base.util.BaseResponseObject;

public interface EmailService {

	@SuppressWarnings("rawtypes")
	public boolean updateSendByHtml(String toMail, String[] ccMail,
			String templateName, String subjectName, Map model);

	public BaseResponseObject updateSendByHtml(Map<String, String> paramMap);

	/**
	 * //1.不能发送附件 //2.不能抄送 //3.对邮件内容要求严格，必须包含html head title body <meta
	 * http-equiv="Content-Type" charset="utf-8" />
	 * 
	 * @param paramMap
	 * @return
	 */
	BaseResponseObject updateSendByMtarget(Map<String, String> paramMap);

	Map sendByEasyEdmHtml(Map<String, String> paramMap);
}
