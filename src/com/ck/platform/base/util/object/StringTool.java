package com.ck.platform.base.util.object;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

public class StringTool {

	public static String replace(String mobiles, String srcString,
			String replaceString) {

		return mobiles.replace(srcString, replaceString);
	}

	public static boolean isMobile(String mobiles) {

		Pattern p = Pattern.compile("^((1))\\d{10}$");

		Matcher m = p.matcher(mobiles);

		System.out.println(m.matches() + "---");

		return m.matches();
	}

	public static boolean isEmail(String email) {

		Pattern pattern = Pattern.compile("[\\w[-]]+@[\\w[-]]+\\.[\\w]+");

		/*
		 * Pattern pattern = Pattern
		 * .compile("\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*");
		 */

		Matcher matcher = pattern.matcher(email);
		if (matcher.matches()) {
			return true;
		}
		return false;
	}

	public static String getMapString(Map paramMap, String mapStr) {

		String returnString = (paramMap!=null && paramMap.get(mapStr) != null) ? paramMap.get(
				mapStr).toString() : "";

		return returnString;
	}

	/**
	 * 获取分割后的字符串
	 * 
	 * @param arrString
	 * @param split
	 * @param index
	 *            0 或1
	 * @return
	 */
	public static String arrValue(String arrString, String split, int index) {
		String v = arrString;
		int i = arrString.indexOf(split);
		if (i > -1) {
			int len = arrString.length();
			if (index == 0) {
				v = arrString.substring(0, i);
			} else if (index == 1) {
				v = arrString.substring(i + 1, len);
			}
		}
		return v;
	}

	public static Long convertObjectToLong(Object obj) {
		return Long.valueOf(String.valueOf(obj));
	}

	public static String convertObjectToString(Object obj) {
		return String.valueOf(obj);
	}

	public static boolean isLongEmpty(Long l) {

		if (null != null && l > 0l) {
			return false;
		} else {
			return true;
		}
	}

	public static boolean isStringEmpty(String str) {

		return StringUtils.isEmpty(str);
	}

	public static StringBuffer splitTail(StringBuffer str) {

		int length = str.length();
		System.out.println("length=" + length);
		System.out.println("ad=" + str.lastIndexOf(","));

		if (str.lastIndexOf(",") + 1 == length) {
			return str.delete(length - 1, length);
		} else {
			return str;
		}

	}

	

	public static String toUnicode(String s) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < s.length(); ++i) {
			if (s.charAt(i) <= 256) {
				sb.append("\\u00");
			} else {
				sb.append("\\u");
			}
			sb.append(Integer.toHexString(s.charAt(i)));
		}
		return sb.toString();
	}

	// unicode转为本地
	public static String ascii2Native(String str) {
		StringBuilder sb = new StringBuilder();
		int begin = 0;
		int index = str.indexOf("\\u");
		while (index != -1) {
			sb.append(str.substring(begin, index));
			sb.append(ascii2Char(str.substring(index, index + 6)));
			begin = index + 6;
			index = str.indexOf("\\u", begin);
		}
		sb.append(str.substring(begin));
		return sb.toString();
	}

	private static char ascii2Char(String str) {
		if (str.length() != 6) {
			throw new IllegalArgumentException(
					"Ascii string of a native character must be 6 character.");
		}
		if (!"\\u".equals(str.substring(0, 2))) {
			throw new IllegalArgumentException(
					"Ascii string of a native character must start with \"\\u\".");
		}
		String tmp = str.substring(2, 4);
		int code = Integer.parseInt(tmp, 16) << 8;
		tmp = str.substring(4, 6);
		code += Integer.parseInt(tmp, 16);
		return (char) code;
	}

}
