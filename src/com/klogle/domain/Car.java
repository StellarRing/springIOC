package com.klogle.domain;

/**
 * carBean
 * @author klogle
 *package:com.klogle.domain
 *E-mail:klogle.one@qq.com
 */
public class Car {

	private String name;

	public Car() {
		//用于测试
		System.out.println("Car初始化...");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Car [name=" + name + "]";
	}
}
