package com.ck.platform.business.customer.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ck.platform.base.entity.RepObject;
import com.ck.platform.base.util.core.page.Page;
/**
 * 会员基本信息Service
 * @author lyq
 * @date 2015-7-30
 */
@SuppressWarnings("rawtypes")
public interface CustomerInfoService {
	
	RepObject create(Map paramMap);

	RepObject updateInfo(Map paramMap);

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
