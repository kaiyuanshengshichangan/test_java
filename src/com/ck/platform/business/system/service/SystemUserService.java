package com.ck.platform.business.system.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ck.platform.base.entity.RepObject;
import com.ck.platform.base.util.core.page.Page;
/**
 * 系统用户Service
 * @author lyq
 * @date 2015-7-30
 */
@SuppressWarnings("rawtypes")
public interface SystemUserService {
	
	RepObject create(Map paramMap);

	RepObject update(Map paramMap);

	HashMap getById(String id);

	Page getPageList(Map paramMap);

	List<HashMap> getList(Map paramMap);

	Integer getCount(Map paramMap);
	
	/**
	 * 根据参数获取指定信息
	 * @param paramMap
	 * @return
	 */
	HashMap getByParam(Map paramMap);

	
}
