package com.klogle.config;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author klogle
 *package:com.klogle.config
 *E-mail:klogle.wang@qq.com
 */
public class Bean {
	private String id;
	private String className;
	private String scope="singleton";
	private List<Property> properties = new ArrayList<Property>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public List<Property> getProperties() {
		return properties;
	}

	public void setProperties(List<Property> properties) {
		this.properties = properties;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	@Override
	public String toString() {
		return "Bean [id=" + id + ", className=" + className + ", scope=" + scope + ", properties=" + properties + "]";
	}
}
