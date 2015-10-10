package com.klogle.test;

import java.util.Map;

import org.junit.Test;

import com.klogle.config.Bean;
import com.klogle.config.parse.ConfigManager;
import com.klogle.domain.Car;
import com.klogle.domain.Highway;
import com.klogle.domain.Person;
import com.klogle.main.ApplicationContext;
import com.klogle.main.ClassPathXmlApplicationContext;

/**
 * 单元测试
 * @author klogle
 *package:com.klogle.test
 *E-mail:klogle.wang@qq.com
 */
public class SpringIOCTest {

	@Test
	/**
	 * 测试解析是否正常
	 */
	public void demo1() {
		Map<String, Bean> config = ConfigManager.getConfig("/applicationContext.xml");
		System.out.println(config);
	}

	@Test
	/**
	 * 测试IOC以及属性注入
	 */
	public void demo2() {
		ApplicationContext context = new ClassPathXmlApplicationContext("/applicationContext.xml");
		Car car = (Car) context.getBean("carBean");
		Person person = (Person) context.getBean("person");
		System.out.println(car);
		System.out.println(person);
	}
	
	@Test
	/**
	 * 测试scope
	 */
	public void demo3(){
		ApplicationContext context = new ClassPathXmlApplicationContext("/applicationContext.xml");
		Person person1 = (Person) context.getBean("person");
		Person person2 = (Person) context.getBean("person");
		Person person3 = (Person) context.getBean("person");
		
		Highway highway1=(Highway) context.getBean("highway");
		Highway highway2=(Highway) context.getBean("highway");
		Highway highway3=(Highway) context.getBean("highway");
		
		Car car1 = (Car) context.getBean("carBean");
		Car car2 = (Car) context.getBean("carBean");
		Car car3 = (Car) context.getBean("carBean");
		
	}
	
	@Test
	/**
	 * 测试类型转换
	 */
	public void demo4(){
		ApplicationContext context = new ClassPathXmlApplicationContext("/applicationContext.xml");
		Highway highway=(Highway) context.getBean("highway");
		System.out.println(highway.getName()+"的长度为："+highway.getLength()+"公里");
	}
}
