package com.ck.platform.base.util.object;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

public class GsonUtil {

	private static final Logger logger = Logger.getLogger(GsonUtil.class);

	/**
	 * 
	 * 函数名称: gsonToMap 函数描述: 将json字符串转换为map
	 * 
	 * @param data
	 * @return
	 */
	public static Map<String, String> parseGsonToMap(String data) {
		GsonBuilder gb = new GsonBuilder();
		Gson g = gb.create();
		Map<String, String> map = g.fromJson(data,
				new TypeToken<Map<String, String>>() {
				}.getType());
		return map;
	}

	public static <T> T fromJSON(String json, Class<T> clazz) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(json, clazz);
		} catch (JsonParseException e) {
			throw new RuntimeException(e);
		} catch (JsonMappingException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static String toJSON(Object obj) {
		ObjectMapper mapper = new ObjectMapper();
		StringWriter writer = new StringWriter();
		try {
			mapper.writeValue(writer, obj);
		} catch (JsonGenerationException e) {
			throw new RuntimeException(e);
		} catch (JsonMappingException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return writer.toString();
	}

}
