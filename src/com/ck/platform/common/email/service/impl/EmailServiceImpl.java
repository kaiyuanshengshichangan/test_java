package com.ck.platform.common.email.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.activation.CommandMap;
import javax.activation.MailcapCommandMap;
import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Repository;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.ck.platform.base.util.BaseResponseObject;
import com.ck.platform.base.util.HttpRequestUtil;
import com.ck.platform.base.util.MapUtil;
import com.ck.platform.common.email.service.EmailService;

@Repository("emailService")
public class EmailServiceImpl implements EmailService {

	@Value(value = "${easyedm.url}")
	private String easyedm_url = "";

	@Value(value = "${easyedm.user}")
	private String easyedm_user = "";

	@Value(value = "${easyedm.apikey}")
	private String easyedm_apikey = "";

	@Value(value = "${caos.mail.default.from}")
	private String caos_mail_default_from = "";

	@Value(value = "${mail.default.from}")
	private String allin_mail_default_from = "";

	@Resource
	private MailSender mailSender;
	@Resource
	private VelocityEngine velocityEngine;
	@Resource
	private SimpleMailMessage simpleMailMessage;// spring配置中定义

	@Resource
	private MailSender caosMailSender;

	@Resource
	private SimpleMailMessage caosSimpleMailMessage;// spring配置中定义

	private Logger log = Logger.getLogger(EmailServiceImpl.class);

	@SuppressWarnings("rawtypes")
	@Override
	public boolean updateSendByHtml(String toMail, String[] ccMail,
			String templateName, String subjectName, Map model) {

		boolean retBoolean = true;

		try {

			MimeMessage message =

			((JavaMailSenderImpl) mailSender).createMimeMessage();

			MimeMessageHelper helper = new MimeMessageHelper(message, true,
					"UTF-8");

			helper.setTo(toMail);

			helper.setCc(ccMail);

			helper.setFrom(simpleMailMessage.getFrom()); // 发送人,从配置文件中取得
			helper.setSubject(subjectName);

			helper.setSentDate(new Date());

			String result = null;
			try {
				result = VelocityEngineUtils.mergeTemplateIntoString(
						velocityEngine, templateName, "UTF-8", model);
			} catch (Exception e) {
			}

			helper.setText(result, true);

			System.out.println("message=" + message);

			((JavaMailSenderImpl) mailSender).send(message);

		} catch (Exception ex) {
			retBoolean = false;
			System.err.println("邮件发送失败的原因是：" + ex.getMessage());
			System.err.println("具体的错误原因");
			ex.printStackTrace(System.err);
		}
		return retBoolean;
	}

	@SuppressWarnings("finally")
	@Override
	public BaseResponseObject updateSendByHtml(Map<String, String> paramMap) {

		// System.out.println("sendByHtml paramMap="+paramMap);

		Boolean returnBol = Boolean.FALSE;
		try {

			MimeMessageHelper helper = null;
			int sendChannelId = 0;
			int sendSiteId = 1;
			int sendType = 2;

			if (null != paramMap.get("sendChannelId")) {
				sendChannelId = Integer.parseInt(paramMap.get("sendChannelId")
						.toString());
			}

			if (null != paramMap.get("sendSiteId")) {
				sendSiteId = Integer.parseInt(paramMap.get("sendSiteId")
						.toString());
			}
			if (null != paramMap.get("sendType")) {

				// sendType = Integer.valueOf(paramMap.get("sendType"));
			}

			// System.out.println("email paramMap="+paramMap);

			MimeMessage message = null;
			String toEmailAddress = "";
			String emailSubject = "";
			String emailContent = "";
			System.out.println("sendChannelId=" + sendChannelId);

			switch (sendChannelId) {
			case 1:
				message = ((JavaMailSenderImpl) mailSender).createMimeMessage();
				helper = new MimeMessageHelper(message, true, "UTF-8");
				helper.setFrom(simpleMailMessage.getFrom()); // 发送人,从配置文件中取得
				break;
			case 2:
				System.out.println("caosMailSender=" + caosMailSender);

				message = ((JavaMailSenderImpl) caosMailSender)
						.createMimeMessage();
				helper = new MimeMessageHelper(message, true, "UTF-8");
				helper.setFrom(caosSimpleMailMessage.getFrom()); // 发送人,从配置文件中取得

				System.out.println("helper=" + helper.getMimeMessage());

				break;
			default:
				message = ((JavaMailSenderImpl) mailSender).createMimeMessage();
				helper = new MimeMessageHelper(message, true, "UTF-8");
				helper.setFrom(simpleMailMessage.getFrom()); // 发送人,从配置文件中取得
			}

			if (paramMap.get("toEmailAddress") != null
					&& StringUtils.isNotBlank(paramMap.get("toEmailAddress")
							.toString())) {

				toEmailAddress = paramMap.get("toEmailAddress").toString();
				helper.setTo(toEmailAddress);
			}

			if (paramMap.get("ccEmailAddress") != null
					&& StringUtils.isNotBlank(paramMap.get("ccEmailAddress")
							.toString())) {

				String[] ccList = paramMap.get("ccEmailAddress").toString()
						.split(";");

				helper.setCc(ccList);
			}

			if (paramMap.get("emailSubject") != null
					&& StringUtils.isNotBlank(paramMap.get("emailSubject")
							.toString())) {
				emailSubject = paramMap.get("emailSubject").toString();
				helper.setSubject(emailSubject);
			}

			if (paramMap.get("emailContent") != null
					&& StringUtils.isNotBlank(paramMap.get("emailContent")
							.toString())) {

				emailContent = paramMap.get("emailContent").toString();
				helper.setText(emailContent, true);
			}

			helper.setSentDate(new Date());

			if (paramMap.get("attachFilePath") != null
					&& StringUtils.isNotBlank(paramMap.get("attachFilePath")
							.toString())) {

				String attachFilePath = paramMap.get("attachFilePath")
						.toString();

				UrlResource urlResource = new UrlResource(attachFilePath);

				String attachFileName = attachFilePath.substring(attachFilePath
						.lastIndexOf("/") + 1);

				// 这句代码在填充上面的占位符的同时，还将这张图片作为附件传过去
				helper.addInline("file", urlResource);
				// 解决附件的中文名字乱码问题，这是传递附件
				helper.addAttachment(MimeUtility.encodeWord(attachFileName),
						urlResource);

			}

			System.out.println("message=" + message);

			MailcapCommandMap mc = (MailcapCommandMap) CommandMap
					.getDefaultCommandMap();
			mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
			mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
			mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
			mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
			mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
			CommandMap.setDefaultCommandMap(mc);

			((JavaMailSenderImpl) mailSender).send(message);

			// log email
			Map log_email_param_map = new HashMap();
			log_email_param_map.put("sendType", sendType);
			log_email_param_map.put("email", toEmailAddress);
			log_email_param_map.put("sendSubject", emailSubject);
			log_email_param_map.put("sendContent", emailContent);
			log_email_param_map.put("sendChannelId", sendChannelId);
			log_email_param_map.put("sendSiteId", sendSiteId);
			log_email_param_map.put("sendStatus", 1);

			//logEmailDAO.create(log_email_param_map);

			// paramMap.remove("emailContent");
			System.out.println("sendByHtml paramMap=" + paramMap);

			returnBol = Boolean.TRUE;
		} catch (Exception ex) {

			System.err.println("邮件发送失败的原因是：" + ex.getMessage());
			System.err.println("具体的错误原因");
			ex.printStackTrace(System.err);
			ex.printStackTrace();
		} finally {

			if (returnBol) {
				return new BaseResponseObject(Boolean.TRUE, "", "");
			} else {
				return new BaseResponseObject(Boolean.FALSE, "", "");
			}
		}
	}

	@SuppressWarnings("finally")
	@Override
	public Map sendByEasyEdmHtml(Map<String, String> paramMap) {

		// System.out.println("sendByHtml paramMap="+paramMap);
		BaseResponseObject bo = new BaseResponseObject();

		Boolean returnBol = Boolean.FALSE;
		try {

			Map send_mail_map = new HashMap();

			// MimeMessageHelper helper = null;
			int sendChannelId = 0;
			int sendSiteId = 1;
			int sendType = 2;

			if (null != paramMap.get("sendChannelId")) {
				sendChannelId = Integer.parseInt(paramMap.get("sendChannelId")
						.toString());
			}

			if (null != paramMap.get("sendSiteId")) {
				sendSiteId = Integer.parseInt(paramMap.get("sendSiteId")
						.toString());
			}
			if (null != paramMap.get("sendType")) {

				// sendType = Integer.valueOf(paramMap.get("sendType"));
			}

			// System.out.println("email paramMap="+paramMap);

			// MimeMessage message = null;
			String toEmailAddress = "";
			String emailSubject = "";
			String emailContent = "";
			System.out.println("sendChannelId=" + sendChannelId);

			switch (sendChannelId) {
			case 1:
				// message = ((JavaMailSenderImpl)
				// mailSender).createMimeMessage();
				// helper = new MimeMessageHelper(message, true, "UTF-8");
				// helper.setFrom(simpleMailMessage.getFrom()); // 发送人,从配置文件中取得

				send_mail_map
						.put("fromname", java.net.URLEncoder.encode("唯医网"));
				send_mail_map.put("fromemail", allin_mail_default_from);

				break;
			case 2:
				System.out.println("caosMailSender=" + caosMailSender);

				/*
				 * message = ((JavaMailSenderImpl) caosMailSender)
				 * .createMimeMessage();
				 */// helper = new MimeMessageHelper(message, true, "UTF-8");
					// helper.setFrom(caosSimpleMailMessage.getFrom()); //
					// 发送人,从配置文件中取得

				// System.out.println("helper=" + helper.getMimeMessage());

				send_mail_map.put("fromname",
						java.net.URLEncoder.encode("中国医师协会骨科医师分会"));
				send_mail_map.put("fromemail", caos_mail_default_from);

				break;
			default:
				// message = ((JavaMailSenderImpl)
				// mailSender).createMimeMessage();
				// helper = new MimeMessageHelper(message, true, "UTF-8");
				// helper.setFrom(simpleMailMessage.getFrom()); // 发送人,从配置文件中取得
			}

			if (paramMap.get("toEmailAddress") != null
					&& StringUtils.isNotBlank(paramMap.get("toEmailAddress")
							.toString())) {

				toEmailAddress = paramMap.get("toEmailAddress").toString();
				send_mail_map.put("to", toEmailAddress);

			}

			/*
			 * if (paramMap.get("ccEmailAddress") != null &&
			 * StringUtils.isNotBlank(paramMap.get("ccEmailAddress")
			 * .toString())) {
			 * 
			 * String[] ccList = paramMap.get("ccEmailAddress").toString()
			 * .split(";");
			 * 
			 * // helper.setCc(ccList); }
			 */

			if (paramMap.get("emailSubject") != null
					&& StringUtils.isNotBlank(paramMap.get("emailSubject")
							.toString())) {
				emailSubject = paramMap.get("emailSubject").toString();
				// helper.setSubject(emailSubject);

				send_mail_map.put("subject", java.net.URLEncoder.encode(emailSubject));

			}

			if (paramMap.get("emailContent") != null
					&& StringUtils.isNotBlank(paramMap.get("emailContent")
							.toString())) {

				emailContent = paramMap.get("emailContent").toString();
				send_mail_map.put("htmlbody", java.net.URLEncoder.encode(emailContent));

			}

			System.out.println("send_mail_map=" + send_mail_map);

			StringBuffer str = new StringBuffer("");

			str.append("user=");
			str.append(easyedm_user);

			str.append("&apikey=");
			str.append(easyedm_apikey);

			str.append("&fromname=");
			str.append(send_mail_map.get("fromname").toString());

			str.append("&fromemail=");
			str.append(send_mail_map.get("fromemail").toString());

			str.append("&to=");
			str.append(send_mail_map.get("to").toString());

			str.append("&subject=");
			str.append(send_mail_map.get("subject").toString());

			str.append("&htmlbody=");
			str.append(send_mail_map.get("htmlbody").toString());

			// 发送 POST 请求
			String sr = HttpRequestUtil.sendPost(easyedm_url, str.toString());

			System.out.println(sr);

			// log email
			Map log_email_param_map = new HashMap();
			log_email_param_map.put("sendType", sendType);
			log_email_param_map.put("email", toEmailAddress);
			log_email_param_map.put("sendSubject", emailSubject);
			log_email_param_map.put("sendContent", emailContent);
			log_email_param_map.put("sendChannelId", sendChannelId);
			log_email_param_map.put("sendSiteId", sendSiteId);
			log_email_param_map.put("sendStatus", 1);

			//logEmailDAO.create(log_email_param_map);
			// paramMap.remove("emailContent");
			System.out.println("sendByHtml paramMap=" + paramMap);

			returnBol = Boolean.TRUE;
		} catch (Exception ex) {

			System.err.println("邮件发送失败的原因是：" + ex.getMessage());
			System.err.println("具体的错误原因");
			ex.printStackTrace(System.err);
			ex.printStackTrace();

		} finally {

		}

		bo.setResponseStatus(returnBol);

		return MapUtil.transBean2Map(bo);

	}

	@Override
	public BaseResponseObject updateSendByMtarget(Map<String, String> paramMap) {
		return null;

		/*
		 * Map<String,String> serverResponseMap =
		 * MtargetEmailUtil.taskForSendingEmail(paramMap); boolean result =
		 * false; TbLogEmail email = new TbLogEmail();
		 * email.setEmailContent(paramMap.get("emailContent"));
		 * email.setEmailSubject(paramMap.get("emailSubject"));
		 * email.setEmailTo(paramMap.get("toEmailAddress"));
		 * email.setSendTime(new Date()); try {
		 * email.setCategory(Integer.parseInt(paramMap.get("category"))); }
		 * catch (NumberFormatException e1) { email.setCategory(0); }
		 * //得到服务器反馈，就存储 if(serverResponseMap != null &&
		 * serverResponseMap.size()>0){
		 * email.setReqcode(serverResponseMap.get("reqcode"));
		 * email.setRetcode(serverResponseMap.get("retcode"));
		 * email.setTaskId(serverResponseMap.get("taskid")); } try {
		 * dao.save(email); result = true; } catch (Exception e) {
		 * log.error("save email log error >>> " + e.getMessage()); } if
		 * (result) { return new BaseResponseObject(Boolean.TRUE, "", ""); }
		 * else { return new BaseResponseObject(Boolean.FALSE, "", ""); }
		 */
	}

}