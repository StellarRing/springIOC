package com.klogle.main;

/**
 * 超接口，用于扩展
 * @author klogle
 *package:com.klogle.main
 *E-mail:klogle.wang@qq.com
 */
public interface ApplicationContext {
	// 通过Bean的ID获取实例对象
	public Object getBean(String ID);
}
