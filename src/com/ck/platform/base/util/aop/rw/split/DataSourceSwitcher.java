package com.ck.platform.base.util.aop.rw.split;

import java.util.Date;

import org.apache.commons.httpclient.util.DateUtil;
import org.springframework.util.Assert;

import com.ck.platform.base.util.RandomUtil;

public class DataSourceSwitcher {
	@SuppressWarnings("rawtypes")
	private static final ThreadLocal contextHolder = new ThreadLocal();

	@SuppressWarnings("unchecked")
	public static void setDataSource(String dataSource) {
		Assert.notNull(dataSource, "dataSource cannot be null");
		contextHolder.set(dataSource);
	}

	public static void setMaster() {
		clearDataSource();
	}

	public static void setSlave() {

		// 随机调用slave
		StringBuffer slaveString = new StringBuffer("");

		slaveString.append("slave");
		slaveString.append(RandomUtil.getRandom(2));

		setDataSource(slaveString.toString());

		// 记录随机调用的日志
		StringBuffer logContent = new StringBuffer("");

		logContent.append(DateUtil
				.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
		logContent.append("   ");
		logContent.append(" invoke ");
		logContent.append(slaveString.toString());

	}

	public static String getDataSource() {
		return (String) contextHolder.get();
	}

	public static void clearDataSource() {
		contextHolder.remove();
	}
}
