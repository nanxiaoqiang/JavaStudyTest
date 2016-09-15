package com.nanxiaoqiang.test.javastudytest.lang.annotation;

import java.lang.annotation.Annotation;

import javax.annotation.Resource;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@MyAnnotation(name = "nanxiaoqiang1")// 什么斗没写的话,使用默认值
public class MyAnnotationTest {
	private static Logger logger = LogManager.getLogger(MyAnnotationTest.class
			.getName());
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
			logger.info(annotations.toString());
			for (Annotation annotation : annotations) {
				if (annotation.annotationType().getName().equals(MyAnnotation.class.getName())) {
					logger.info(((MyAnnotation)annotation).name());
					test.setName(((MyAnnotation)annotation).name());
					logger.info("finally:MyAnnotationTest.name is :" + test.getName());
				}
			}
		}
		logger.info("********************");
		MyAnnotationTest test2 = new MyAnnotationTest();
		Annotation myAnnotation = test2.getClass().getAnnotation(MyAnnotation.class);
		Annotation noAnnotation = test2.getClass().getAnnotation(Resource.class);
		logger.info(myAnnotation);
		logger.info(noAnnotation);// 没有的Annotation返回null
		if (myAnnotation.annotationType().equals(MyAnnotation.class)) {
			test.setName(((MyAnnotation)myAnnotation).name());
			logger.info("finally:MyAnnotationTest2.name is :" + test.getName());
		}
	}
}
