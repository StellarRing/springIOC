package com.klogle.domain;

/**
 * HighWay Bean
 * @author klogle
 *package:com.klogle.domain
 *E-mail:klogle.one@qq.com
 */
public class Highway {
	private String name;
	private Integer length;

	public Highway() {
		//用于测试
		System.out.println("highway初始化...");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}
}
