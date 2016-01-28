package com.ck.platform.common.sms.resource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ck.platform.base.util.BaseResponseObject;
import com.ck.platform.base.util.core.page.Page;
import com.ck.platform.common.sms.service.SmsService;

@Component("smsResource")
@Path("/sms")
public class SmsResource {

	@Autowired
	SmsService smsService;

	/**
	 * 接口说明:发送短信
	 * 
	 * @return
	 */
	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/sendMessage")
	public BaseResponseObject sendMessage(Map paramMap) {

		return smsService.sendMessage(paramMap);
	}

	/**
	 * 接口说明:发送短信
	 * 
	 * @return
	 */
	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/sendMessageByText/{mobileNumber},{smsContent}")
	public BaseResponseObject sendMessageByText(
			@PathParam("mobileNumber") String mobileNumber,
			@PathParam("smsContent") String smsContent) {

		Map<String, Object> paramMap = new HashMap<String, Object>();

		if (StringUtils.isBlank(mobileNumber)) {
			return new BaseResponseObject(Boolean.FALSE, "10002", "手机号码不存在");
		}
		if (StringUtils.isBlank(smsContent)) {
			return new BaseResponseObject(Boolean.FALSE, "10001", "短信内容为空");
		}
		paramMap.put("mobileNumber", mobileNumber);
		paramMap.put("smsContent", smsContent);
		return smsService.sendMessage(paramMap);
	}

	/**
	 * 移通异步反馈短信发送状态，这个是对移通提供的接口
	 * 
	 * @param spid
	 * @param mtmsgid
	 * @param mtstat
	 * @param mterrcode
	 * @return
	 */
	@GET
	@Produces({ "text/html" })
	@Path("/report")
	public String smsReport(@QueryParam("spid") String spid,
			@QueryParam("mtmsgid") String mtmsgid,
			@QueryParam("mtstat") String mtstat,
			@QueryParam("mterrcode") String mterrcode) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("spid", spid);
		paramMap.put("mtmsgid", mtmsgid);
		paramMap.put("mtstat", mtstat);
		paramMap.put("mterrcode", mterrcode);
		smsService.asyncUpdateSmsStatus(paramMap);
		return "command=RT_RESPONSE&spid=" + spid + "&mtmsgid=" + mtmsgid
				+ "&rtstat=ACCEPT&rterrcode=000";
	}

	/**
	 * 查询： 1.发送状态 0|1|2|3|4 必填 2.分页 startPage,pageSize 必填 3.时间段 start,end
	 * 4.手机号匹配 cell
	 */
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/query")
	public Page querySms(
			@DefaultValue("0") @QueryParam("smsStatus") int smsStatus,
			@DefaultValue("1") @QueryParam("startPage") int startPage,
			@DefaultValue("30") @QueryParam("pageSize") int pageSize,
			@QueryParam("startTime") String startTime,
			@QueryParam("endTime") String endTime,
			@QueryParam("cell") String cell) {

		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		Date stime = null;
		Date etime = null;
		try {
			if (startTime != null) {
				stime = fmt.parse(startTime);
			}
			if (endTime != null) {
				etime = fmt.parse(endTime);
			}
		} catch (ParseException e) {
			// TODO default
		}
		return smsService.query(smsStatus, startPage, pageSize, stime,etime, cell);
	}
}
