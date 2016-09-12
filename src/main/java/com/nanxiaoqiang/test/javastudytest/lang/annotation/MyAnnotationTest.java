package com.nanxiaoqiang.test.javastudytest.lang.annotation;

import java.lang.annotation.Annotation;

import org.apache.commons.lang3.ArrayUtils;

@MyAnnotation// 什么斗没写,使用默认值(name = "nanxiaoqiang1")
public class MyAnnotationTest {
	
	private String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static void main(String[] args) {
		MyAnnotationTest test = new MyAnnotationTest();
		Annotation[] annotations = test.getClass().getAnnotations();
		if(ArrayUtils.isNotEmpty(annotations)) {
			System.out.println(annotations);
			for (Annotation annotation : annotations) {
				if (annotation.annotationType().getName().equals(MyAnnotation.class.getName())) {
					System.out.println(((MyAnnotation)annotation).name());
					test.setName(((MyAnnotation)annotation).name());
					System.out.println("finally:MyAnnotationTest.name is :" + test.getName());
				}
			}
		}
	}
}
