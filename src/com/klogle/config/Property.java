package com.klogle.config;

/**
 * 
 * @author klogle
 *package:com.klogle.config
 *E-mail:klogle.one@qq.com
 */
public class Property {

	//属性名
	private String name;
	//简单属性
	private String value;
	//引用属性
	private String ref;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	@Override
	public String toString() {
		return "Property [name=" + name + ", value=" + value + ", ref=" + ref + "]";
	}
}
