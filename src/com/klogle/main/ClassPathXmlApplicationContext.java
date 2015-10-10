package com.klogle.main;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.BeanUtils;

import com.klogle.config.Bean;
import com.klogle.config.Property;
import com.klogle.config.parse.ConfigManager;
import com.klogle.utils.BeanFactoryUtil;

/**
 * IOC核心
 * @author klogle
 *package:com.klogle.main
 *E-mail:klogle.wang@qq.com
 */
public class ClassPathXmlApplicationContext implements BeanFactory {

	//配置信息对象
	private Map<String, Bean> config;
	// 创建IOC容器，用于存放实例化对象
	private Map<String, Object> context = new HashMap<String, Object>();

	public ClassPathXmlApplicationContext(String path) {
		// 读取配置文件获得Bean初始化信息
		config = ConfigManager.getConfig(path);
		// 初始化配置信息，根据配置信息初始化Bean
		if (config != null) {
			for (Entry<String, Bean> en : config.entrySet()) {
				String beanId = en.getKey();
				Bean bean = en.getValue();
				// 根据配置信息创建Bean对象
				//初始化之前首先判断容器中是否包含Bean，如果已存在则直接从容器中取
				Object existBean = context.get(beanId);
				if (existBean == null && bean.getScope().equals("singleton")) {
					/**
					 * 如果不存在：
					 * 		a、单例模式：创建Bean，注入属性并存入到容器中
					 * 		b、多例模式：创建Bean的过程交个getBean()
					 */
					existBean = createBean(bean);
					context.put(beanId, existBean);
				}
			}
		}
	}

	/**
	 * 属性注入
	 * 根据bean配置信息创建Bean对象
	 * 
	 * @param bean 需要被实例化的对象信息
	 * @return 完成属性注入的实例化对象
	 */
	private Object createBean(Bean bean) {
		// 产生Bean实例化对象
		String className = bean.getClassName();
		Class clazz;
		try {
			clazz = Class.forName(className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("以下类名不存在:" + className);
		}
		Object beanObj;
		try {
			beanObj = clazz.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Bean没有空参构造:" + className);
		}

		/**
		 * 为实例化Bean注入属性
		 * 		a、简单属性注入（注入value对应值） 
		 * 		b、ref注入
		 */
		if (bean.getProperties() != null) {
			for (Property prop : bean.getProperties()) {
				// 获得要注入的属性值和名称
				String name = prop.getName();
				String value = prop.getValue();
				String ref = prop.getRef();
				// 根据属性名获取对应的set方法
				Method writeMethod = BeanFactoryUtil.getWriteMethod(beanObj, name);
				
				// 简单属性，使用BeanUtils进行注入（BeanUtils自带类型转换）
				if (value != null) {
					Map<String, String[]> params = new HashMap<>();
					params.put(name, new String[] { value });
					try {
						BeanUtils.populate(beanObj, params);
					} catch (IllegalAccessException e) {
						e.printStackTrace();
						throw new RuntimeException("属性不可读，属性注入失败:"+e);
					} catch (InvocationTargetException e) {
						e.printStackTrace();
						throw new RuntimeException("属性注入失败:"+e);
					}
				}
				
				// ref属性
				if (ref != null) {
					// 判断引用Bean是否已存在于容器中
					Object refBean = context.get(prop.getRef());
					if (refBean == null) {
						// 如果不存在，创建Bean
						refBean = createBean(config.get(prop.getRef()));
						// 如果是单例，存入IOC容器中
						if (config.get(prop.getRef()).equals("singleton")) {
							context.put(prop.getRef(), refBean);
						}
						// 执行ref属性注入
						try {
							writeMethod.invoke(beanObj, refBean);
						} catch (IllegalAccessException e) {
							e.printStackTrace();
							throw new RuntimeException("写属性权限异常，属性注入失败！" + writeMethod);
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
							throw new RuntimeException("非法参数，属性注入失败！" + refBean);
						} catch (InvocationTargetException e) {
							e.printStackTrace();
							throw new RuntimeException("属性注入异常！" + e);
						}
					}
				}

			}
		}
		// 返回装配完毕的实例化对象
		return beanObj;
	}

	@Override
	/**
	 * 提供实例化获取通道
	 * @param ID 需要获取实例化对象的ID
	 * @return 已完成属性注入的实例化对象
	 */
	public Object getBean(String ID) {
		/**
		 * 从IOC容器中获取实例：
		 * 		a、如果存在，直接返回
		 * 		b、如果容器中不包含该对象，那么其scope一定是prototype，创建新实例
		 */
		Object obj = context.get(ID);
		if (obj == null) {
			obj = createBean(config.get(ID));
		}
		return obj;
	}
}