package com.ck.platform.base.util.core.page;

import java.io.Serializable;

public class BaseForm implements Serializable {
	
	private static final long serialVersionUID = 67567645651L;

	public Integer page;

	public Integer pageSize;

	public Integer firstResult = 0;

	public Integer maxResult = Integer.MAX_VALUE;
	
	public int sortType;
	
	public String ids;
	
	public String getIds()
	{
		return ids;
	}

	public void setIds(String ids)
	{
		this.ids = ids;
	}

	public int getSortType()
	{
		return sortType;
	}

	public void setSortType(int sortType)
	{
		this.sortType = sortType;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getFirstResult() {
		if (null != page && page > 1) {
			return (page - 1) * pageSize;
		} else {
			return 0;
		}
	}

	public void setFirstResult(Integer firstResult) {
		this.firstResult = firstResult;
	}

	public Integer getMaxResult() {
		return pageSize;
	}

	public void setMaxResult(Integer maxResult) {
		this.maxResult = maxResult;
	}

}
