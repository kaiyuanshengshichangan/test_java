package com.ck.platform.base.util.spring;
/**
 * spring 工厂，用来创建对象
 * @author lyq
 *
 */
public interface BeanFactory {
	public Object getBean(String id);  
}
