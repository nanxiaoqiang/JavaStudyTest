package com.nanxiaoqiang.test.javastudytest.lang.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @author nanxiaoqiang
 * 
 * @version 2016年9月12日
 */
@Target(ElementType.TYPE)// 作用于类上
@Retention(RetentionPolicy.RUNTIME)// 一定要写，注解作用域，此处为运行环境
public @interface MyAnnotation {
	public String name() default "nanxiaoqiang";
}
