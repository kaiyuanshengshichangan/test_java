package com.ck.platform.base.util.spring;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.xpath.XPath;
/**
 * 创建 
 * @author lyq
 *
 */
public class ClassPathXmlApplicationContext implements BeanFactory {  
      
    public static Map<String,Object> container = null;  
      
      
    public ClassPathXmlApplicationContext(String path) throws Exception{ 
    		if(container==null){
    			container = new HashMap<String,Object>();  
                SAXBuilder sb = new SAXBuilder(); 
                Document doc = sb.build(this.getClass().getClassLoader().getResourceAsStream(path));  
                Element root = doc.getRootElement();  
                List list = XPath.selectNodes(root, "/beans/bean");  
                for (int i = 0; i < list.size(); i++) {   
                    Element bean = (Element) list.get(i);  
                    String id = bean.getAttributeValue("id"); 
                    if(!container.containsKey(id)){
                    	String className = bean.getAttributeValue("class");  
                        Object o = Class.forName(className).newInstance();  
                        container.put(id, o);  
                    }
                    
                }  
    		}
    } 
    
    
    @Override 
    public Object getBean(String id) {  
    	
        return this.container.get(id);  
    }  
    
}