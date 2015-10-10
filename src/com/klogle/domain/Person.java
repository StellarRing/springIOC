package com.klogle.domain;

/**
 * Person Bean
 * @author klogle
 *package:com.klogle.domain
 *E-mail:klogle.one@qq.com
 */
public class Person {

	private String name;
	private Car car;

	public Person() {
		//用于测试
		System.out.println("person初始化...");
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Person [name=" + name + ", car=" + car + "]";
	}
}
