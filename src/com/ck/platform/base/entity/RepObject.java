package com.ck.platform.base.entity;

import java.util.HashMap;
import java.util.Map;

import com.ck.platform.base.constants.ResponseCode;
/**
 * 接口返回数据对象
 * @author lyq
 * @date 2015-8-3
 */
public class RepObject {
	private Boolean status;
	private String code;
	private String msg;
	private Long pk=0L;
	private Object data;
	public Long getPk() {
		return pk;
	}

	public void setPk(Long pk) {
		this.pk = pk;
	}

	public RepObject() {
		this.status=false;
	}

	public RepObject(Boolean status, String code,
			String msg) {
		this.status = status;
		this.code = code;
		this.msg = msg;
		if(data==null){
			data=new HashMap();
		}
	}

	

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
	public static void success(RepObject rep) {
		rep.setStatus(true);
		rep.setCode(ResponseCode.GLOBAL_SUSSCESS);
	}

}
