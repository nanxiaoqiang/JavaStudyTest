package com.nanxiaoqiang.test.javastudytest.lang.annotation;

import java.lang.annotation.Annotation;

import javax.annotation.Resource;

import org.apache.commons.lang3.ArrayUtils;

@MyAnnotation(name = "nanxiaoqiang1")// 什么斗没写的话,使用默认值
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
		System.out.println("********************");
		MyAnnotationTest test2 = new MyAnnotationTest();
		Annotation myAnnotation = test2.getClass().getAnnotation(MyAnnotation.class);
		Annotation noAnnotation = test2.getClass().getAnnotation(Resource.class);
		System.out.println(myAnnotation);
		System.out.println(noAnnotation);// 没有的Annotation返回null
		if (myAnnotation.annotationType().equals(MyAnnotation.class)) {
			test.setName(((MyAnnotation)myAnnotation).name());
			System.out.println("finally:MyAnnotationTest2.name is :" + test.getName());
		}
	}
}
