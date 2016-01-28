package com.ck.platform.base.util.spring;



/**
 * 对象管理工具类
 * @author lyq
 *
 */
public class BeanUtil {
	static BeanFactory bf;
	static{
		try {
			if(bf==null){
				bf = new ClassPathXmlApplicationContext("resources/bean_context.xml");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}	
	
	/**
	 * 获取对象实例
	 * @return
	 */
	public static Object getBean(String beanName){
		return bf.getBean(beanName);
	}
	
	
}
