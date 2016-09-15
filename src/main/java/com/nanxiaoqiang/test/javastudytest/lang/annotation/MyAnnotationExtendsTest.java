package com.nanxiaoqiang.test.javastudytest.lang.annotation;

import java.lang.annotation.Annotation;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Annotation继承测试
 * 
 * @author nanxiaoqiang
 * 
 * @version 2016年9月15日
 */
public class MyAnnotationExtendsTest extends MyAnnotationTest {
	private static Logger logger = LogManager.getLogger(MyAnnotationExtendsTest.class.getName());

	public MyAnnotationExtendsTest() {
	}

	public static void main(String[] args) {
		MyAnnotationExtendsTest test = new MyAnnotationExtendsTest();
		Annotation[] annotations = test.getClass().getAnnotations();
		if (ArrayUtils.isNotEmpty(annotations)) {
			logger.info(annotations.toString());
			for (Annotation annotation : annotations) {
				if (annotation.annotationType().getName().equals(MyAnnotation.class.getName())) {
					logger.info(((MyAnnotation) annotation).name());
					test.setName(((MyAnnotation) annotation).name());
					logger.info("finally:MyAnnotationTest.name is :" + test.getName());
				}
			}
		}
	}

}
