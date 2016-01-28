package com.ck.platform.business.order.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ck.platform.base.entity.RepObject;
import com.ck.platform.base.util.core.page.Page;
/**
 * 主订单Service
 * @author lyq
 * @date 2015-8-1
 */
@SuppressWarnings("rawtypes")
public interface OrderInfoService {
	
	RepObject createOrder(Map paramMap)throws Exception;

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
	 * 取消订单
	 * @param paramMap
	 * @return
	 */
	RepObject executeCancel(Map paramMap);
}
