package com.ck.platform.business.weixin.entity;
/**
 * 模版消息数据
 * @author lyq
 *
 */
public class TemplateData {

	private String value;
	private String color;
	public TemplateData(String value,String color){
		this.value=value;
		this.color=color;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	
	
}
