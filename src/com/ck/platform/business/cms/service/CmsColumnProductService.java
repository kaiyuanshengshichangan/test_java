package com.ck.platform.business.cms.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ck.platform.base.entity.RepObject;
import com.ck.platform.base.util.core.page.Page;
/**
 * 栏目－商品Service
 * @author lyq
 * @date 2015-8-3
 */
@SuppressWarnings("rawtypes")
public interface CmsColumnProductService {
	
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
	
	/**
	 * 删除栏目下的商品
	 * @param paramMap
	 * @return
	 */
	RepObject delete(Map paramMap);

	
}
