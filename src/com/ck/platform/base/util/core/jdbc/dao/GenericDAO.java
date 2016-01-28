package com.ck.platform.base.util.core.jdbc.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ck.platform.base.util.core.page.Page;

/**  
 * @desc: 
 * @author:Administrator 
 * @date：2014-4-24   
 */
public interface GenericDAO {
	/**
	 * 创建一条数据
	 * @param paramMap
	 * @return
	 */
	Long create(Map paramMap);

	/**
	 * 根据主键更新一条数据
	 * @param paramMap
	 */
	void update(Map paramMap);

	/**
	 * 根据主键获取唯一数据
	 * @param id
	 * @return
	 */
	HashMap getById(String id);
	
	/**
	 * 获取分页列表
	 * @param paramMap
	 * @return
	 */
	Page getPageList(Map paramMap);

	/**
	 * 获取列表数据集
	 * @param paramMap
	 * @return
	 */
	List<HashMap> getList(Map paramMap);

	/**
	 * 获取总记录数
	 * @param paramMap
	 * @return
	 */
	int getCount(Map paramMap);
	
	/**
	 * 根据参数获取唯一数据
	 * @param paramMap
	 * @return
	 */
	HashMap getByParam(Map paramMap);
	
	
}

  
