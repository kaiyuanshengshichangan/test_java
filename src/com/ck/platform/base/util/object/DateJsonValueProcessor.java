package com.ck.platform.base.util.object;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

public class DateJsonValueProcessor implements JsonValueProcessor {

	public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";

	private DateFormat dateFormat;

	/**
	 * 
	 * 构造方法.
	 * 
	 * 
	 * 
	 * @param datePattern
	 *            日期格式
	 */

	public DateJsonValueProcessor(String datePattern) {

		if (null == datePattern)

			dateFormat = new SimpleDateFormat(DEFAULT_DATE_PATTERN);

		else

			dateFormat = new SimpleDateFormat(datePattern);

	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see
	 * net.sf.json.processors.JsonValueProcessor#processObjectValue(java.lang
	 * .String， java.lang.Object， net.sf.json.JsonConfig)
	 */

	public Object processObjectValue(String arg0, Object arg1, JsonConfig arg2) {

		// TODO 自动生成方法存根

		return process(arg1);

	}

	private Object process(Object value) {

		if (value instanceof Timestamp)
			return dateFormat.format((Timestamp) value);
		else if (value instanceof Date)
			return dateFormat.format((Date) value);
		else if (value == null)
			return "";
		else
			return value.toString();

		/*
		 * try { return dateFormat.format((Date) value); } catch (Exception e) {
		 * return ""; }
		 */

	}

	public Object processArrayValue(Object arg0, JsonConfig arg1) {
		// TODO Auto-generated method stub
		return null;
	}

}
