package com.klogle.config.parse;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.klogle.config.Bean;
import com.klogle.config.Property;

/**
 * 配置信息加载器
 * @author klogle
 *package:com.klogle.config.parse
 *E-mail:klogle.wang@qq.com
 */
public class ConfigManager {

	/**
	 * 加载配置文件信息
	 * @param path	配置文件存放路径
	 * @return	配置文件中的所有配置信息
	 */
	public static Map<String, Bean> getConfig(String path) {
		/**
		 * dom4j读取文件 
		 * 		1、创建解析器 
		 * 		2、加载配置文件生成document对象 
		 * 		3、定义Xpath表达式，取出所有Bean元素
		 * 		4、对Bean元素进行遍历 
		 * 			a、bean元素的name/class属性封装到Bean中
		 * 			b、获取bean的所有property元素，将属性name、value/ref封装到Property
		 * 			c、将Property封装到Bean对象 d、将Bean对象封装到Map中（用于返回的map） 
		 * 5、返回Map结果
		 */
		Map<String, Bean> map = new HashMap<>();
		// 创建解析器
		SAXReader reader = new SAXReader();
		// 加载配置文件
		InputStream in = ConfigManager.class.getResourceAsStream(path);
		Document doc;
		try {
			doc = reader.read(in);
		} catch (DocumentException e) {
			e.printStackTrace();
			throw new RuntimeException("读取配置失败，请检查xml配置！");
		}
		// 定义Xpath表达式
		String Xpath = "//bean";
		// 遍历bean元素
		List<Element> beans = doc.selectNodes(Xpath);
		if (beans != null) {
			for (Element beanEle : beans) {
				Bean bean = new Bean();
				// 将bean元素的id、class属性封装到Bean中
				String id = beanEle.attributeValue("id");
				String className = beanEle.attributeValue("class");
				String scope = beanEle.attributeValue("scope");

				if (scope != null) {
					bean.setScope(scope);
				}
				bean.setClassName(className);
				bean.setId(id);

				// 获取bean的所有property元素，将属性name、value/ref封装到Property
				List<Element> properties = beanEle.elements("property");
				if (properties != null) {
					for (Element el : properties) {
						Property property = new Property();
						String pName = el.attributeValue("name");
						String pValue = el.attributeValue("value");
						String pRef = el.attributeValue("ref");
						property.setName(pName);
						property.setValue(pValue);
						property.setRef(pRef);
						bean.getProperties().add(property);
					}
				}
				map.put(id, bean);
			}
		}
		// 检查非法参数
		Set<Entry<String, Bean>> entrySet = map.entrySet();
		for (Entry<String, Bean> entry : entrySet) {
			List<Property> properties = entry.getValue().getProperties();
			for (Property property : properties) {
				String ref = property.getRef();
				if (ref != null && !map.containsKey(ref)) {
					throw new RuntimeException("非法参数：" + property.getRef());
				}
			}
		}
		return map;
	}
}