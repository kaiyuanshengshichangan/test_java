package com.ck.platform.base.util.core.jdbc.dao;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ck.platform.base.util.BaseResponseObject;
import com.ck.platform.base.util.core.page.Page;

public interface GenericRestDAO<T extends Serializable, PK extends Serializable>
		extends Serializable {

	public Page getPageList(String url, Map param);

	public List<T> getList(String url, Map param);

	public T getById(String url);

	public BaseResponseObject create(Map obj, String url);

	public BaseResponseObject update(Map bo, String url);

	public String getJsonList(String url, String param);

	public String getJsonPageList(String url, String param);
	
	
	public int getCount(String url,Map paramMap);
	
	public List<HashMap> getMapList(String url, Map param);
	
	public BaseResponseObject execute(Map bo, String url);
	
	public BaseResponseObject execute(Map bo, String url, Method method);

}
