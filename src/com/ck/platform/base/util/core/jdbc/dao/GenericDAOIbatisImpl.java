package com.ck.platform.base.util.core.jdbc.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ck.platform.base.util.core.page.Page;
import com.ibatis.sqlmap.client.SqlMapClient;

public class GenericDAOIbatisImpl<T extends Object> extends SqlMapClientDaoSupport
{

	// 实体类类型(由构造方法自动赋值)
	private Class<T> entityClass;

	// 构造方法，根据实例类自动获取实体类类型
	public GenericDAOIbatisImpl()
	{
		this.entityClass = null;
		Class c = getClass();
		Type t = c.getGenericSuperclass();
		if (t instanceof ParameterizedType)
		{
			Type[] p = ((ParameterizedType) t).getActualTypeArguments();
			this.entityClass = (Class<T>) p[0];
		}
	}

	@Resource(name = "sqlMapClient")
	SqlMapClient sqlMapClient;

	@PostConstruct
	public void setSqlMapClientBase()
	{
		super.setSqlMapClient(sqlMapClient);
	}

	public Long create(String nsPrefix, Map paramMap)
	{

		Long pk = (Long) getSqlMapClientTemplate().insert(nsPrefix + "insert", paramMap);
		return pk;

	}

	public void update(String nsPrefix, Map paramMap)
	{
		super.getSqlMapClientTemplate().update(nsPrefix + "updateByKey", paramMap);
	}
	
	
	public void delete(String nsPrefix, Map paramMap) {
		super.getSqlMapClientTemplate().delete(nsPrefix + "deleteByKey", paramMap);
	}

	public void truncate(String nsPrefix) {
		super.getSqlMapClientTemplate().update(nsPrefix + "truncate");
	}

	
	public void callProc(String nsPrefix,String procName) {
		super.getSqlMapClientTemplate().update(nsPrefix + procName);
	}
	public T getById(String nsPrefix, String id)
	{

		return (T) getSqlMapClientTemplate().queryForObject(nsPrefix + "selectByKey", id);
	}
	
	public T getByParam(String nsPrefix, Map paramMap)
	{

		return (T) getSqlMapClientTemplate().queryForObject(nsPrefix + "selectByParam", paramMap);
	}
	

	public Page getPageList(String nsPrefix, Map paramMap)
	{
		int pageIndex=1;
		int pageSize=10;
		if(paramMap.get("pageIndex")!=null){
			pageIndex=Integer.valueOf(paramMap.get("pageIndex").toString());
		}
		if(paramMap.get("pageSize")!=null){
			pageSize=Integer.valueOf(paramMap.get("pageSize").toString());
		}
		Page pageObj = new Page(pageIndex,pageSize);
		paramMap.put("firstResult", pageObj.getFirstResult());
		paramMap.put("maxResult", pageSize);
		pageObj.setItems(getList(nsPrefix, paramMap));
		pageObj.setTotal(getCount(nsPrefix, paramMap));

		return pageObj;

	}

	@SuppressWarnings("unchecked")
	public List<T> getList(String nsPrefix, Map paramMap)
	{
		if(!paramMap.containsKey("firstResult") || paramMap.get("firstResult")==null){
			paramMap.put("firstResult", 0) ;
		}
		if(!paramMap.containsKey("maxResult") || paramMap.get("maxResult")==null){
			paramMap.put("maxResult", 20) ;
		}
		return getSqlMapClientTemplate().queryForList(nsPrefix + "getList", paramMap);
	}

	public int getCount(String nsPrefix, Map paramMap)
	{
		return (Integer) this.getSqlMapClientTemplate().queryForObject(nsPrefix + "getCount", paramMap);
	}

	/**
	 * 分页查询
	 * 
	 * @param ibatisSqlName
	 *            sql名称，必须定义一个ibatisSqlName+"Total"的sql
	 * @param filter
	 *            条件
	 * @param page
	 *            第几页
	 * @param pageSize
	 *            每页数据量
	 * @return
	 */
	public Page findPageByFilter(String ibatisSqlName, Map filter, int page, int pageSize)
	{
		Page ps = null;
		int startRow = (page - 1) * pageSize;
		if (filter == null)
		{
			filter = new HashMap();
		}
		filter.put("startRow", startRow);
		filter.put("endRow", pageSize);
		try
		{
			int totalCount = (Integer) getSqlMapClientTemplate().queryForObject(ibatisSqlName + "Total", filter);
			List resultList = getSqlMapClientTemplate().queryForList(ibatisSqlName, filter);
			ps = new Page(resultList, totalCount, pageSize, page);
		} catch (Exception e)
		{
			logger.error("----------GenericDAOIbatisImpl--findPageByFilter(String ibatisSqlName, Map filter, int page, int pageSize):"+e.getMessage());
			//e.printStackTrace();

		}
		return ps;
	}

	/**
	 * 分页查询
	 * 
	 * @param ibatisSqlName
	 *            sql名称，必须定义一个ibatisSqlName+"Total"的sql
	 * @param filter
	 *            条件(已将startRow,endRow放入filter中)
	 * @return
	 */
	public Map<String, Object> findPageByFilter(String ibatisSqlName, Map filter)
	{
		Map<String, Object> pageMap = new HashMap<String, Object>();
		try
		{
			int totalCount = (Integer) getSqlMapClientTemplate().queryForObject(ibatisSqlName + "Total", filter);
			List resultList = getSqlMapClientTemplate().queryForList(ibatisSqlName, filter);
			pageMap.put("total", totalCount);
			pageMap.put("rows", resultList);
		} catch (Exception e)
		{
			logger.error("----------GenericDAOIbatisImpl--findPageByFilter(String ibatisSqlName, Map filter):"+e.getMessage());
			//e.printStackTrace();
		}
		return pageMap;
	}
}
