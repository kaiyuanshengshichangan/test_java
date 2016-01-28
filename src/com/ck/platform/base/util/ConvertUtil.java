package com.ck.platform.base.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.sf.json.JSONObject;

import com.ck.platform.base.util.object.GsonUtil;

/**  
 * @desc: 
 * @author:Administrator 
 * @date 2014-4-22   
 */
public class ConvertUtil {
	@SuppressWarnings({ "rawtypes" })
	public static HashMap objToHashMap(Object object) {
		HashMap<String, Object> data = new HashMap<String, Object>();
		JSONObject jsonObject = toJSONObject(object);
		Iterator it = jsonObject.keys();
		while (it.hasNext()) {
			String key = String.valueOf(it.next());
			Object value = jsonObject.get(key);
			data.put(key, value);
		}

		return data;
	}

	public static Map jsonStringToHashMap(String str) {
		return GsonUtil.parseGsonToMap(str);
	}

	private static JSONObject toJSONObject(Object object) {
		return JSONObject.fromObject(object);
	}


	public static Map<String, String> objToMap(Object obj) {

		Map<String, String> map = new HashMap<String, String>();
		// System.out.println(obj.getClass());
		Field[] fields = obj.getClass().getDeclaredFields();
		for (int i = 0, len = fields.length; i < len; i++) {
			String varName = fields[i].getName();
			try {
				boolean accessFlag = fields[i].isAccessible();
				fields[i].setAccessible(true);
				Object o = fields[i].get(obj);
				if (o != null)
					map.put(varName, o.toString());
				fields[i].setAccessible(accessFlag);
			} catch (IllegalArgumentException ex) {
				ex.printStackTrace();
			} catch (IllegalAccessException ex) {
				ex.printStackTrace();
			}
		}
		return map;

	}
}

  
