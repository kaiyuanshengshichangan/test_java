package com.ck.platform.business.cms.dao;

import java.util.Map;

import com.ck.platform.base.util.core.jdbc.dao.GenericDAO;

@SuppressWarnings({ "rawtypes" })
public interface CmsColumnProductDAO extends GenericDAO{
	/**
	 * 删除
	 * @param paramMap
	 */
	void delete(Map paramMap);
	
}

  
