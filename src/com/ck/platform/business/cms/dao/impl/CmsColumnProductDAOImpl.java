package com.ck.platform.business.cms.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.ck.platform.base.util.core.jdbc.dao.GenericDAOIbatisImpl;
import com.ck.platform.base.util.core.page.Page;
import com.ck.platform.business.cms.dao.CmsColumnProductDAO;


@SuppressWarnings({ "rawtypes" })
@Repository("cmsColumnProductDAO")
public class CmsColumnProductDAOImpl extends GenericDAOIbatisImpl<HashMap> implements CmsColumnProductDAO
	{
		private static final Logger logger = Logger.getLogger(CmsColumnProductDAOImpl.class);

		private static String IBATIS_NS_PREFIX = "cms_column_product.";

		@Override
		public Long create(Map paramMap)
		{
			return create(IBATIS_NS_PREFIX, paramMap);
		}

		@Override
		public void update(Map paramMap)
		{
			update(IBATIS_NS_PREFIX, paramMap);
		}

		@Override
		public HashMap getById(String id)
		{
			return (HashMap) getById(IBATIS_NS_PREFIX, id);
		}
		
		@Override
		public Page getPageList(Map paramMap)
		{
			return getPageList(IBATIS_NS_PREFIX, paramMap);
		}

		@Override
		public List<HashMap> getList(Map paramMap)
		{
			return (List<HashMap>) getList(IBATIS_NS_PREFIX, paramMap);
		}

		@Override
		public int getCount(Map paramMap)
		{
			return (Integer) getCount(IBATIS_NS_PREFIX, paramMap);
		}

		
		@Override
		public HashMap getByParam(Map paramMap)
		{
			return (HashMap) getByParam(IBATIS_NS_PREFIX, paramMap);
		}
		
		public void delete(Map paramMap){
			delete(IBATIS_NS_PREFIX, paramMap);
		}
		
}

  
