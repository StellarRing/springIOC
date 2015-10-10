package com.klogle.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

import com.klogle.config.Bean;

/**
 * 工具类：获取对象set属性
 * @author klogle
 *package:com.klogle.utils
 *E-mail:klogle.wang@qq.com
 */
public class BeanFactoryUtil {

	/**
	 * 
	 * @param obj 提供set属性的对象
	 * @param filedName 需要获取set属性的属性名称
	 * @return	set属性（set方法）
	 */
	public static Method getWriteMethod(Object obj, String filedName) {

		BeanInfo beanInfo=null;
		Method method = null;
		try {
			//获取属性描述器
			beanInfo = Introspector.getBeanInfo(obj.getClass(), Object.class);
			PropertyDescriptor pds[] = beanInfo.getPropertyDescriptors();
			if (pds != null) {
				//遍历属性描述器获取指定set属性
				for (PropertyDescriptor pd : pds) {
					String filed = pd.getName();
					if (filed.equals(filedName)) {
						method = pd.getWriteMethod();
					}
				}
			}
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}
		return method;
	}
}
